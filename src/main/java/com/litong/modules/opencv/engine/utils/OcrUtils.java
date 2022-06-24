package com.litong.modules.opencv.engine.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OcrUtils {
  public static ITesseract instance = new Tesseract();
  static {

    // 设置字库目录
    instance.setDatapath(System.getenv("TESSDATA_PREFIX"));
    // 设置字库文件
    // instance.setLanguage("chi_sim");
    instance.setLanguage("eng");
  }

  public static String doOcr(String dstPath) throws TesseractException {
    // 需要识别的图片
    File imageFile = new File(dstPath);
    // 识别图片
    String result = instance.doOCR(imageFile);
    // 输出识别结果
    return result;
  }

  public static String doOcr(BufferedImage bufferImage) throws TesseractException {
    return instance.doOCR(bufferImage);
  }
}
