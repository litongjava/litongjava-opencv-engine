package com.litong.modules.opencv.engine.services;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import com.litong.jfinal.thread.pool.ThreadPoolKit;
import com.litong.jfinal.utils.UploadFileUtils;
import com.litong.modules.opencv.engine.client.PaddleOcrClient;
import com.litong.modules.opencv.engine.utils.OcrUtils;
import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.model.MaxAreaBo;
import com.litongjava.opencv.model.Plate;
import com.litongjava.opencv.utils.ImgprocUtils;
import com.litongjava.opencv.utils.KmeansUtils;
import com.litongjava.opencv.utils.MatUtils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;

/**
 * 识别车牌
 * 
 * @author Ping
 *
 */
@Slf4j
public class RecognitionPlateUtils {

  /**
   * 
   * @param recognize.getFilename()
   * @return
   * @throws IOException
   * @throws FileNotFoundException
   * @throws TesseractException
   */
  public Plate index(DebugInfo recognize) throws FileNotFoundException, IOException, TesseractException {
    // return recgnizeV1(filename);

    // return recgnizeV4(recognize);
    return null;
  }

  /**
   * 第一个识别版本,转为hsv,再使用Tesseract进行识别
   * 
   * @param filename
   * @return
   * @throws IOException
   * @throws FileNotFoundException
   * @throws TesseractException
   */
  @SuppressWarnings("unused")
  private Plate recgnizeV1(String filename) throws FileNotFoundException, IOException, TesseractException {
    String imagePath = UploadFileUtils.getFullPath(filename);
    Mat src = MatUtils.imread(imagePath);
    Mat hsv = new Mat();
    Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
    // 保存文件,使用单独的线程保存文件
    ThreadPoolKit.use("pool1").execute(() -> {
      String baseName = FilenameUtils.getBaseName(imagePath);
      String dstPath = imagePath.replace(baseName, baseName + "_hsv");
      try {
        MatUtils.imwrite(dstPath, hsv);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    BufferedImage bufferImage = MatUtils.mat2BufferImage(hsv);
    // 识别文件
    String number = OcrUtils.doOcr(bufferImage);
    return new Plate(number);
  }

  /**
   * 识别第二版本,发送图片数据到PaddleOCR
   * 
   * @param filename
   * @return
   * @throws IOException
   */
  public Plate recgnizeV2(String filename) throws IOException {
    String imagePath = UploadFileUtils.getFullPath(filename);
    String requestUrl = "http://127.0.0.1:9292/ocr/prediction";
    StringBuffer stringBuffer = PaddleOcrClient.recognize(requestUrl, imagePath);
    log.info("result:{}", stringBuffer.toString());
    return new Plate(stringBuffer.toString());
  }

  /**
   * 1.通过轮廓查找,取出车牌部分 2.发送的服务器识别
   * 
   * @param recognize
   * @return
   * @throws IOException
   * @throws FileNotFoundException
   */
  public Plate recgnizeV3(DebugInfo recognize) throws FileNotFoundException, IOException {
    String imagePath = UploadFileUtils.getFullPath(recognize.getFilename());
    recognize.setImagePath(imagePath);
    // 读入文件
    Mat src = MatUtils.imread(imagePath);
    // Mat submat =imgprocService.extraMaxArea(src,recognize);
    Mat submat = ImgprocUtils.extraMaxArea(src, recognize);
    // 保存到文件
    String name = FilenameUtils.getBaseName(imagePath);
    String newName = name + "-submat";
    String dstPath = imagePath.replace(name, newName);

    log.info("write submat to file:{}", dstPath);
    MatUtils.imwrite(dstPath, submat);

    String extension = FilenameUtils.getExtension(imagePath);
    return recgnizeV2(newName + "." + extension);
    // HighGui.imshow("subMat", submat);
    // Mat plate = extraMaxArea(submat);
    // HighGui.imshow("plate", plate);
    // return null;
  }

  /**
   * 车牌识别第4版 1.裁剪图像获取车牌 2.二值化 3.使用 Tesseract识别车牌
   * 
   * @param recognize
   * @return
   * @throws IOException
   */
  public Plate recgnizeV4(byte[] imageBytes, DebugInfo debugInfo) throws IOException {
    String tempPath = debugInfo.getTempPath();
    String extensionName = debugInfo.getExtensionName();
    String baseName = debugInfo.getBaseName();
    Boolean isSave = debugInfo.getIsSave();

    Boolean isUpload = debugInfo.getIsUpload();
    String uploadHost = debugInfo.getUploadHost();

    // 读取文件
    Mat src = MatUtils.imread(imageBytes);

    // 经过两次裁剪,裁剪出包含图片的边框
    // 裁剪取出最大面积的图像
    MaxAreaBo maxAreaBo = ImgprocUtils.getMaxArea(src, debugInfo);
    log.info("areaMax:{},rectMax:{}", maxAreaBo.getAreaMax(), maxAreaBo.getRectMax());
    double lastAreaMax = maxAreaBo.getAreaMax();
    // 裁剪图像
    Mat lastRectMax = src.submat(maxAreaBo.getRectMax());
    // 保存图片
    String maxAreaName = MatUtils.getBaseName(baseName, "maxArea");
    String maxAreaDstPath = MatUtils.getDstPath(tempPath, maxAreaName, extensionName);
    log.info("save file name:{}", maxAreaName);
    MatUtils.debugToFile(isSave, lastRectMax, maxAreaName, extensionName, maxAreaDstPath, isUpload, uploadHost);

    /**
     * 判断是否进行二次裁剪,查找面积最大的图形,如果图形面积大于指定值,则表名还有一层边框,需要进行二次裁剪,如果没有则不需要进行,多次裁剪
     * 经过多次试验,判断0.3是一个比较不错的值
     */

    debugInfo.setImagePath(maxAreaDstPath);
    // 再次提取最大面积的轮廓
    maxAreaBo = ImgprocUtils.getMaxArea(lastRectMax, debugInfo);
    log.info("areaMax:{},rectMax:{}", maxAreaBo.getAreaMax(), maxAreaBo.getRectMax());
    double ratio = maxAreaBo.getAreaMax() / lastAreaMax;
    log.info("ratio:{}", ratio);
    if (ratio > 0.30) {
      lastRectMax = lastRectMax.submat(maxAreaBo.getRectMax());
      maxAreaName = MatUtils.getBaseName(maxAreaName, "maxArea");
      maxAreaDstPath = MatUtils.getDstPath(tempPath, maxAreaName, extensionName);
      log.info("save file name:{}", maxAreaName);
      MatUtils.debugToFile(isSave, lastRectMax, maxAreaName, extensionName, maxAreaDstPath, isUpload, uploadHost);
      debugInfo.setImagePath(maxAreaDstPath);

      // 因为递归效率太低,判断是否需要再次进行裁剪
      maxAreaBo = ImgprocUtils.getMaxArea(lastRectMax, debugInfo);
      log.info("areaMax:{},rectMax:{}", maxAreaBo.getAreaMax(), maxAreaBo.getRectMax());
      ratio = maxAreaBo.getAreaMax() / lastAreaMax;
      log.info("ratio:{}", ratio);
      if (ratio > 0.30) {
        lastRectMax = lastRectMax.submat(maxAreaBo.getRectMax());
        maxAreaName = MatUtils.getBaseName(maxAreaName, "maxArea");
        maxAreaDstPath = MatUtils.getDstPath(tempPath, maxAreaName, extensionName);
        log.info("save file name:{}", maxAreaName);
        MatUtils.debugToFile(isSave, lastRectMax, maxAreaName, extensionName, maxAreaDstPath, isUpload, uploadHost);
        debugInfo.setImagePath(maxAreaDstPath);

        maxAreaBo = ImgprocUtils.getMaxArea(lastRectMax, debugInfo);
        log.info("areaMax:{},rectMax:{}", maxAreaBo.getAreaMax(), maxAreaBo.getRectMax());
        ratio = maxAreaBo.getAreaMax() / lastAreaMax;
        log.info("ratio:{}", ratio);
        if (ratio > 0.30) {
          lastRectMax = lastRectMax.submat(maxAreaBo.getRectMax());
          maxAreaName = MatUtils.getBaseName(maxAreaName, "maxArea");
          maxAreaDstPath = MatUtils.getDstPath(tempPath, maxAreaName, extensionName);
          log.info("save file name:{}", maxAreaName);
          MatUtils.debugToFile(isSave, lastRectMax, maxAreaName, extensionName, maxAreaDstPath, isUpload, uploadHost);
          debugInfo.setImagePath(maxAreaDstPath);
        }
      }
    }

    // 使用kmeas聚类将车牌背景改为白色
    Mat backgrand2White = KmeansUtils.backgrand2White(lastRectMax);
    // HighGui.imshow("back",backgrand2White);
    maxAreaBo = ImgprocUtils.getMaxArea(backgrand2White, debugInfo);
    Mat submat = backgrand2White.submat(maxAreaBo.getRectMax());
    HighGui.imshow("submat",submat);
    return null;
  }
}
