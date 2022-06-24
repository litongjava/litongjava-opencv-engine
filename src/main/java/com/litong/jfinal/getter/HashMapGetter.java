package com.litong.jfinal.getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.ParaGetter;

/**
 * @author bill robot
 * @date 2020年8月18日_上午9:15:49 
 * @version 1.0 
 * @desc
 */
public class HashMapGetter extends ParaGetter<HashMap<String, String>> {

  public HashMapGetter(String parameterName, String defaultValue) {
    super(parameterName, defaultValue);
  }

  @Override
  public HashMap<String, String> get(Action action, Controller c) {
    HashMap<String, String> retval = new HashMap<String, String>();
    Map<String, String[]> paraMap = c.getParaMap();
    for (Entry<String, String[]> entry : paraMap.entrySet()) {
      String[] values = entry.getValue();
      String value = (values != null && values.length > 0) ? values[0] : null;
      retval.put(entry.getKey(), "".equals(value) ? null : value);
    }
    return retval;
  }

  @Override
  protected HashMap<String, String> to(String v) {
    return null;
  }
}
