package com.litong.jfinal.controler;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.upload.UploadFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadController extends Controller {
  

  /**
   * 上传文件
   */
  public void index() {
    v1();
  }
  
  /**
   * 生成到目录
   */
  public void folder() {
    UploadFile uploadFile = getFile();
    String uploadPath = uploadFile.getUploadPath();
    String fileName = uploadFile.getFileName();
    log.info("filename:{}",fileName);
    String contentType = uploadFile.getContentType();
    String originalFileName = uploadFile.getOriginalFileName();
    String parameterName = uploadFile.getParameterName();
    Kv kv = Kv.create();
    kv.set("uploadPath", uploadPath);
    kv.set("fileName", fileName);
    kv.set("contentType", contentType);
    kv.set("originalFileName", originalFileName);
    kv.set("parameterName", parameterName);
    renderJson(kv);
  }

  public void v1() {
    UploadFile uploadFile = getFile();
    String uploadPath = uploadFile.getUploadPath();
    String fileName = uploadFile.getFileName();
    String contentType = uploadFile.getContentType();
    String originalFileName = uploadFile.getOriginalFileName();
    String parameterName = uploadFile.getParameterName();
    Kv kv = Kv.create();
    kv.set("uploadPath", uploadPath);
    kv.set("fileName", fileName);
    kv.set("contentType", contentType);
    kv.set("originalFileName", originalFileName);
    kv.set("parameterName", parameterName);
    renderJson(kv);
  }
}
