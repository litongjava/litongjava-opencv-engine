package com.litong.modules.opencv.engine.controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.litong.modules.opencv.engine.render.BufferedImageRender;
import com.litong.modules.opencv.engine.services.OpencvService;

import lombok.extern.slf4j.Slf4j;

@Path("/api/opencv")
@Slf4j
public class ApiOpenCvController extends Controller {

  @Inject
  private OpencvService opencvService;

  public void index() {
    renderJson(opencvService.info());
  }

  /**
   * Imgproc.COLOR_BGR2GRAY=6
   * 
   * @param imagePath
   * @param code
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void cvtColor(String imagePath, int code) throws FileNotFoundException, IOException {
    BufferedImage bufferedImage = opencvService.cvtColor(imagePath, code);
    String extension = FilenameUtils.getExtension(imagePath);
    BufferedImageRender bufferedImageRender = new BufferedImageRender(extension, bufferedImage);
    render(bufferedImageRender);
  }
}
