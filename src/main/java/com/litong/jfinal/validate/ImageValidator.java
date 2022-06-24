package com.litong.jfinal.validate;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;
import com.litongjava.utils.vo.JsonBean;

public class ImageValidator extends Validator {
  protected void validate(Controller c) {
    setRet(Ret.fail());
    validateRequired("filename", "msg", "文件名不能为空");
  }

  protected void handleError(Controller c) {
    c.renderJson(new JsonBean<Void>(-1, getRet().getStr("msg")));
  }
}