package com.litong.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.litongjava.utils.vo.JsonBean;
import com.litongjava.utils.vo.UserModel;
import com.litongjava.utils.web.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月29日_下午10:27:47 
 * @version 1.0 
 * @desc 用户登录验证拦截器
 */
@Slf4j
public class AuthInterceptor implements Interceptor {

  /**
   * 排除url前缀
   */
  private String[] excluede;

  public AuthInterceptor(String... prefix) {
    int len = prefix.length;
    excluede = new String[len];
    for (int i = 0; i < len; i++) {
      excluede[i] = prefix[i];
    }
  }

  @Override
  public void intercept(Invocation inv) {
    // 1.比较排除前缀是否匹配,配置直接放行,
    String actionKey = inv.getActionKey();
    for (String string : excluede) {
      if (actionKey.startsWith(string)) {
        inv.invoke();
        return;
      }
    }

    // 2.不匹配获取session中的值,没有返回有错误信息的json
    Controller controller = inv.getController();
    UserModel user = WebUtils.getUser(controller.getRequest());
    if (user == null) {
      String mesage="user not login";
      log.info(mesage);
      controller.renderJson(new JsonBean<Void>(-1,mesage));
      controller.redirect("/login.html");
    } else {
      inv.invoke();
    }
  }
}
