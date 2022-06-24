package com.litong.jfinal.render;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ContentType 
 */
public class ContentTypeKit {
  public static Map<String, String> map = new ConcurrentHashMap<String, String>();
  static {
    map.put("jpeg", "image/jpeg");
    map.put("jpg", "image/jpeg");
    map.put("jpe", "image/jpeg");
    map.put("png", "image/png");
  }

  public static String get(String mimetype) {
    String string = map.get(mimetype);
    return string;
  }
}
