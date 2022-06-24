package com.litong.jfinal.render;

import java.io.File;
import java.io.IOException;

import com.jfinal.render.Render;
import com.litongjava.utils.io.StreamUtils;

/**
 * @author bill robot
 * @date 2020年9月6日_上午9:56:51 
 * @version 1.0 
 * @desc
 */
public class InputStreamRender extends Render {

  private File srcfile;

  public InputStreamRender(File srcfile) {
    this.srcfile = srcfile;
  }

  @Override
  public void render() {
    if (srcfile != null) {
      try {
        StreamUtils.copy(srcfile, response.getOutputStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
