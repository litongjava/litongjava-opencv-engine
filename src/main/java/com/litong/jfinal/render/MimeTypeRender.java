package com.litong.jfinal.render;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.IOUtils;

import com.jfinal.render.Render;

import lombok.extern.slf4j.Slf4j;

/**
 * 用来render输出图片到浏览器
 */
@Slf4j
public class MimeTypeRender extends Render {
  private String exName;
  private String filename;
  private boolean isDwoload;

  public MimeTypeRender(String exName, String filename, boolean isDwoload) {
    this.exName = exName;
    this.filename = filename;
    this.isDwoload = isDwoload;
  }

  public MimeTypeRender(String exName, String filename) {
    this.exName=exName;
    this.filename=filename;
  }

  public void render() {
    File file = new File(filename);
    if (!file.exists()) {
      String msg = "file not exists:" + file.getAbsolutePath();
      log.info(msg);
      try {
        response.getWriter().print(msg);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }
    // 设置头信息,内容处理的方式,attachment以附件的形式打开,就是进行下载,并设置下载文件的命名
    if (isDwoload) {
      response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
    }
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setContentType(ContentTypeKit.get(exName));
    // 写入数据到ServetOutputStream
    write(file);
  }

  private void write(File file) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      ServletOutputStream sos = response.getOutputStream();
      IOUtils.copy(fis, sos);
      sos.flush();
    } catch (Exception e) {
      log.error("图片render出错:" + e.getLocalizedMessage(), e);
      throw new RuntimeException(e);
    } finally {
      IOUtils.closeQuietly(fis);
    }
  }
}