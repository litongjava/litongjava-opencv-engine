package com.litong.modules.opencv.engine.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.core.paragetter.Para;
import com.litong.jfinal.utils.UploadFileUtils;
import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.model.Shape;
import com.litongjava.opencv.utils.RecognitionShapeUtils;
import com.litongjava.utils.vo.JsonBean;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;

@Path("recognition/shape")
@Slf4j
public class RecognitionShapeController extends Controller {

  /**
   * 识别图像
   * 
   * @param debugInfo
   * @throws FileNotFoundException
   * @throws IOException
   * @throws TesseractException
   */
  public void index(@Para("") DebugInfo debugInfo) throws FileNotFoundException, IOException, TesseractException {
    debugInfo.setIsSave(true);
    log.info("debugInfo:{}", debugInfo);
    // 读取图片数据为数据流
    String imagePath = UploadFileUtils.getFullPath(debugInfo.getFilename());
    debugInfo.setImagePath(imagePath);
    byte[] imageBytes = FileUtils.readFileToByteArray(new File(imagePath));
    List<Shape> shapList = RecognitionShapeUtils.index(imageBytes,debugInfo);
    renderJson(new JsonBean<>(RecognitionShapeUtils.formatToArray(shapList)));
  }
}
