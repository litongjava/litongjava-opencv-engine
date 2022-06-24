package com.litong.jfinal.controler;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.litong.jfinal.utils.PathKitUtil;

public class PathController extends Controller {
  /**
   * 返回系统的中常用路径
   */
  public void index() {
    /**
     *
    {
    "rootClassPath": "D:\\dev_workspace\\java\\litong-project\\litongjava-windows-tts\\target\\classes",
    "realResourcePath": "/D:/dev_workspace/java/litong-project/litongjava-windows-tts/target/classes/litongjava-windows-tts",
    "webRootPath": "D:\\dev_workspace\\java\\litong-project\\litongjava-windows-tts\\target\\classes\\litongjava-windows-tts"
    }
     */
    Map<String, String> map = new HashMap<>();
    map.put("rootClassPath", PathKit.getRootClassPath());
    map.put("webRootPath", PathKit.getWebRootPath());
    map.put("realResourcePath", PathKitUtil.getResourcePath());
    renderJson(map);
  }
}