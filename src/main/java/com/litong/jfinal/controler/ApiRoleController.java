package com.litong.jfinal.controler;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.litong.jfinal.service.UserService;
import com.litongjava.utils.vo.JsonBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月31日_下午3:36:02 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class ApiRoleController extends Controller{
  @Inject
  private UserService userService;
  
  public void index(Kv kv) {
    log.info("kv:{}",kv);
    String method = kv.getStr("method");
    List<JSONObject> data = null;
    if("getPermission".equals(method)) {
      String role = kv.getStr("role");
      data=userService.getPermission(role);
    }
    JsonBean<List<JSONObject>> jsonBean = new JsonBean<>();
    jsonBean.setData(data);
    renderJson(jsonBean);
    return;
  }

}
