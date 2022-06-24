package com.litong.jfinal.controler;



import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.litong.jfinal.service.UserService;
import com.litongjava.utils.aes.AESCrtUtils;
import com.litongjava.utils.digest.MD5Utils;
import com.litongjava.utils.digest.SHA1Utils;
import com.litongjava.utils.vo.JsonBean;
import com.litongjava.utils.vo.UserModel;
import com.litongjava.utils.web.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月29日_下午5:32:28 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class UserController extends Controller{
  
  @Inject
  private UserService userService;
  
  /**
   * 添加用户
   * @param kv
   */
  public void add(Kv kv) {
    log.info("kv:{}", kv);
    String loginName = kv.getStr("loginName");
    String password = kv.getStr("password");
    String key = kv.getStr("key");
    //明文密码
    String plainText = AESCrtUtils.decrypt(password, key);
    //密文密码
    String encodeText = MD5Utils.encode(SHA1Utils.encode(plainText));
    boolean b = userService.add(loginName,encodeText);
    renderJson(new JsonBean<Boolean>(b));
  }
  
  /**
   * 修改密码
   * @param kv
   */
  public void updatePswd(Kv kv) {
    log.info("kv:{}", kv);
    JsonBean<Void> jsonBean = new JsonBean<Void>();
    String oldPassword = kv.getStr("oldPassword");
    String password = kv.getStr("password");
    String repassword = kv.getStr("repassword");
    if(!password.equals(repassword)) {
      renderJson(new JsonBean<Void>(-1, "2次输入密码不一致"));
      return;
      
    }
    if(oldPassword.equals(password)) {
      renderJson(new JsonBean<Void>(-1, "新密码和旧密码不能相同"));
      return;
    }
    //获取用户名
    UserModel user = WebUtils.getUser(getRequest());
    if(user==null) {
      jsonBean.setCode(-1);
      jsonBean.setMsg("用户没有登录");
      renderJson(jsonBean);
      return;
    }
    String id = user.getId();
    //更新数据库
    renderJson(userService.updatePswd(id,oldPassword,password));
  }
  
  /**
   * 重置密码
   * 明文 admin 密文 c7540eb7e65b553ec1ba6b20de79608
   */
  public void reset() {
    String sqlString="UPDATE sys_user SET PASSWORD='c7540eb7e65b553ec1ba6b20de79608' WHERE number=1";
    Db.update(sqlString);
    renderJson(new JsonBean<Void>());
  }
}
