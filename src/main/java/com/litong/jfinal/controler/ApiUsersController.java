package com.litong.jfinal.controler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.litong.jfinal.service.UserService;
import com.litong.modules.monitoring.db.common.model.SysUser;
import com.litongjava.utils.aes.AESCrtUtils;
import com.litongjava.utils.digest.MD5Utils;
import com.litongjava.utils.digest.SHA1Utils;
import com.litongjava.utils.vo.JsonBean;
import com.litongjava.utils.vo.UserModel;
import com.litongjava.utils.web.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月28日_上午11:38:25 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class ApiUsersController extends Controller {
  @Inject
  private UserService userService;

  public void index(Kv kv) {
    log.info("kv:{}", kv);
    String method = kv.getStr("method");
    List<JSONObject> data = null;
    if ("getRelationRole".equals(method)) {
      String uid = kv.getStr("uid");
      data = userService.getRelationRole(uid);
    }
    JsonBean<List<JSONObject>> jsonBean = new JsonBean<>();
    jsonBean.setData(data);
    renderJson(jsonBean);
    return;
  }

  public void login(Kv kv) {
    log.info("kv:{}", kv);
    String loginName = kv.getStr("name");
    String password = kv.getStr("password");
    String key = kv.getStr("key");
    String plainText = AESCrtUtils.decrypt(password, key);
    // encodeText 2次加密后的密码
    String encodeText = MD5Utils.encode(SHA1Utils.encode(plainText));
    SysUser sysUser = userService.login(loginName, encodeText);
    JsonBean<Void> jsonBean = new JsonBean<Void>();
    if (sysUser == null) {
      jsonBean.setMsg("name or password not current");
    } else {
      Long number = sysUser.getNumber();
      UserModel userModel = new UserModel(number + "");
      WebUtils.setUser(getRequest(), getResponse(), userModel);
    }
    renderJson(jsonBean);
  }

  /**
   * 退出
   * @param kv
   */
  public void logout() {
    HttpServletRequest request = this.getRequest();
    WebUtils.remove(request);
    redirect("/login.html");
  }

  /**
   * 获取我的信息
   * @param kv
   */
  public void mine(Kv kv) {
    log.info("kv:{}", kv);
    String userToken = WebUtils.getUserToken(getRequest());
    UserModel userModel = new UserModel(userToken);
    JsonBean<UserModel> jsonBean = new JsonBean<>(userModel);
    renderJson(jsonBean);
  }
}
