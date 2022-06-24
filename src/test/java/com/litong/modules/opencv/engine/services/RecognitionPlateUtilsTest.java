package com.litong.modules.opencv.engine.services;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.opencv.highgui.HighGui;

import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.model.Plate;
import com.litongjava.opencv.utils.OpenCVLibraryUtils;

public class RecognitionPlateUtilsTest {
  RecognitionPlateUtils recognitionPlateUtils = new RecognitionPlateUtils();
  String tempPath="D:\\opencv-images\\plate\\temp";

  @Before
  public void before() {
    OpenCVLibraryUtils.init();
  }
  @Test
  public void testRecgnizeV4() throws IOException {
    String imagePath="D:\\opencv-images\\plate\\plate-001.png";
    DebugInfo debugInfo = new DebugInfo(imagePath, true, tempPath);
    byte[] imageBytes = FileUtils.readFileToByteArray(new File(imagePath));
    Plate plate = recognitionPlateUtils.recgnizeV4(imageBytes, debugInfo);
    
    HighGui.waitKey();
    HighGui.destroyAllWindows();
    
  }
}
