package com.litong.jfinal.utils;

import java.io.File;

public class UploadFileUtils {

  /**
   * 返回全路径
   * @param filename
   * @return
   */
  public static String getFullPath(String filename) {
    //判断目录是否存在,如果不存在,放路径
    String folder=PathKitUtil.getResourcePath()+"/upload/";
    File folderFile = new File(folder);
    if(!folderFile.exists()) {
      return filename;
    }else {
      return folder+filename;
    }
  }
}
