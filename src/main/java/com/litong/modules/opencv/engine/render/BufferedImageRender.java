package com.litong.modules.opencv.engine.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jfinal.render.Render;

/**
 * 将bufferedImage返回到浏览显示图片
 * 
 * @author ping
 *
 */
public class BufferedImageRender extends Render {
  private String extension;
  private BufferedImage bufferedImage;

  public BufferedImageRender(String extension, BufferedImage bufferedImage) {
    this.extension = extension;
    this.bufferedImage = bufferedImage;
  }

  @Override
  public void render() {
    // 写入数据到ServetOutputStream
    try {
      ImageIO.write(bufferedImage, extension, response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
