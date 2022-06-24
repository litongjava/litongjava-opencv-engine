package com.litong.modules.web.tools.validator;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;
import com.litongjava.utils.vo.JsonBean;

public class DbSearchValidator extends Validator {
  protected void validate(Controller c) {
    setRet(Ret.fail());
    validateRequiredString("text", "textMsg", "请输入Text");
    validateRequiredString("url", "urlMsg", "请输入数据库连接");
  }
  protected void handleError(Controller c) {
    JsonBean<Ret> jsonBean = new JsonBean<Ret>(-1, "请求字段校验失败", getRet());
    c.renderJson(jsonBean);
  }
}