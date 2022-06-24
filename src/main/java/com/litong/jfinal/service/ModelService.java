package com.litong.jfinal.service;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author bill robot
 * @date 2020年8月27日_下午6:22:36 
 * @version 1.0 
 * @desc
 */
public class ModelService {
  /**
   * 比较是否有不同的字段,如果有,返回false
   * @param jsonObject
   * @param keySet 
   * @param recordOfDb
   * @return 字段值完全相同返回true,有不同的返回false
   */
  public boolean equalsJsonAndModel(JSONObject jsonObject, @SuppressWarnings("rawtypes") Model<? extends Model> m) {
    Set<String> keySet = jsonObject.keySet();
    for (String key : keySet) {
      if (!jsonObject.getString(key).equalsIgnoreCase(m.getStr(key))) {
        return false;
      }
    }
    return true;
  }
}
