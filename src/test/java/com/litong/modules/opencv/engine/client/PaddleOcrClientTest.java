package com.litong.modules.opencv.engine.client;

import java.io.IOException;

import org.junit.Test;

public class PaddleOcrClientTest {

  @Test
  public void testRecognize() throws IOException {
    String requestUrl = "http://127.0.0.1:9292/ocr/prediction";
    String imagePath="D:\\PlateDetect\\06_文字\\181006_effc13e7_8688860.png";
    StringBuffer stringBuffer = PaddleOcrClient.recognize(requestUrl,imagePath);
    System.out.println(stringBuffer);
  }

}
