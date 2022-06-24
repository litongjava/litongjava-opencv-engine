package com.litong.modules.opencv.engine.controller;

import org.junit.Test;

public class ImageOperationControllerTest {

  @Test
  public void test() {
    String srcImagePath1="D:\\opencv-images\\others\\01.jpg";
    String srcImagePath2="D:\\opencv-images\\others\\02.jpg";
    String text="数媒201李通2030681876";
    String targetPath="D:\\opencv-images\\others\\03.jpg";
    
    ImageOperationController controller = new ImageOperationController();
    controller.merge(srcImagePath1, srcImagePath2, text, targetPath);
  }
}
