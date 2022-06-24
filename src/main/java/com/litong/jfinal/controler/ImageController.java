package com.litong.jfinal.controler;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.upload.UploadFile;
import com.litong.jfinal.render.MimeTypeRender;
import com.litong.jfinal.validate.ImageValidator;

public class ImageController extends Controller {
  /**
   * filename可以包含路径,也可以不用包含路径
   * 
   * @param filename
   */
  @Before(ImageValidator.class)
  public void index(String filename) {
    MimeTypeRender mimeTypeRender = new MimeTypeRender("jpg", filename);
    render(mimeTypeRender);
  }

  /**
   * 测试图片上传
   */
  public void upload() {
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