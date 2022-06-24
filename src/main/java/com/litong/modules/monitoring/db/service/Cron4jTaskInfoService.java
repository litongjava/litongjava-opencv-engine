package com.litong.modules.monitoring.db.service;

import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.litongjava.utils.vo.JsonBean;

import it.sauronsoftware.cron4j.SchedulingPattern;

/**
 * @author litong
 * @date 2020年9月26日_上午9:51:05 
 * @version 1.0 
 * @desc
 */
public class Cron4jTaskInfoService {

  Cron4jPluginService cron4jPluginService = Aop.get(Cron4jPluginService.class);

  public JsonBean<Void> saveOrUpdateBefore(String tableName, Kv kv) {
    // 1进行字段校验
    JsonBean<Void> jsonBean = new JsonBean<Void>();
    jsonBean.setCode(-1);
    // 1.1验证className是否存在
    String className = kv.getStr("class_name");
    if (!StrKit.isBlank(className)) {
      try {
        Class.forName(className);
      } catch (ClassNotFoundException e) {
        jsonBean.setMsg("类名校验失败:"+e.getClass().getName() + ":" + e.getMessage());
        return jsonBean;
      }
    }
    // 1.2 校验cron是否合法
    String cron = kv.getStr("cron");
    if (!StrKit.isBlank(cron)) {
      try {
        new SchedulingPattern(cron);
      } catch (Exception e) {
        jsonBean.setMsg("cron校验失败:"+e.getClass().getName() + ":" + e.getMessage());
        return jsonBean;
      }
    }
    return null;
  }

  public JsonBean<Void> saveOrUpdateAfter(String tableName, Kv kv) {
    cron4jPluginService.restart();
    return null;
  }
}
