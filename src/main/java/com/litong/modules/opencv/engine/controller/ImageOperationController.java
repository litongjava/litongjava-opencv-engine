package com.litong.modules.opencv.engine.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jfinal.core.Path;
import com.litongjava.utils.image.ImageText;
import com.litongjava.utils.image.ImageUtils;

@Path("image/operation")
public class ImageOperationController {

  public void merge(String srcPath1, String srcPath2, String text, String tarImgPath) {

    BufferedImage bufferdImage = ImageUtils.merge(srcPath1, srcPath2);

    // 获取数据集合；
    ArrayList<ImageText> list = new ArrayList<>();
    list.add(ImageUtils.createImageText(text, new Color(255, 59, 48), new Font("微软雅黑", Font.PLAIN, 80),
        800, 366));
    // 将list写入图片
    ImageUtils.writeImage(bufferdImage, tarImgPath, list);
  }
}
