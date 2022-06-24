package com.litong.modules.opencv.engine.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.litongjava.opencv.utils.ImgprocUtils;

@Path("imgproc")
public class ImgprocController extends Controller{

  
  public void index() {
    renderText("imgproc");
  }
  public void boundingRect() {
    ImgprocUtils.boundingRect();
  }
}
