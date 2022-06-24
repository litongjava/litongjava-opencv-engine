package com.litong.jfinal.getter;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.ParaGetter;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;

/**
 *  转换为 JSONObject 对象
 */
public class JsonGetter extends ParaGetter<JSONObject> {

  public JsonGetter(String parameterName, String defaultValue) {
    super(parameterName, defaultValue);
  }

  @Override
  protected JSONObject to(String v) {
    return parse(v);
  }

  protected static JSONObject parse(String v) {
    if (StrKit.notBlank(v)) {
      return JsonKit.parse(v, JSONObject.class);
    }
    return null;
  }

  @Override
  public JSONObject get(Action action, Controller c) {
    JSONObject ret = getRawDataToJson(c);
    return null != ret ? ret : getDefaultValue();
  }

  /**
   * 获取请求中的JSON对象
   * @param c
   * @return
   */
  public static JSONObject getRawDataToJson(Controller c) {
    String name = "_rawDataJson";
    JSONObject rawDataJson = c.getAttr(name);
    if (null == rawDataJson) {
      rawDataJson = parse(c.getRawData());
      if (null != rawDataJson) {
        c.setAttr(name, rawDataJson);
      }
    }
    return rawDataJson;
  }

}
