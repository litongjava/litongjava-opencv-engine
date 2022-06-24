package com.litong.jfinal.validate;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;
import com.litongjava.utils.vo.JsonBean;

/**
 * @author litong
 * @date 2020年9月27日_下午5:39:08 
 * @version 1.0 
 * @desc
 */
public class UserValidator extends Validator {
  protected void validate(Controller c) {
    setRet(Ret.fail());
    validateRequired("oldPassword", "msg", "旧密码不能为空");
    validateRequired("password", "msg", "新密码不能为空");
    validateRequired("repassword", "msg", "确认密码不能为空");
  }

  protected void handleError(Controller c) {
    c.renderJson(new JsonBean<Void>(-1, getRet().getStr("msg")));
  }
}
