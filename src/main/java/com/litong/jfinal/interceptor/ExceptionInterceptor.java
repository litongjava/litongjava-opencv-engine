package com.litong.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.litongjava.utils.vo.JsonBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月29日_下午5:45:26 
 * @version 1.0 
 * @desc
 * 全局异常拦截器
 */
@Slf4j
public class ExceptionInterceptor implements Interceptor {
  @Override
  public void intercept(Invocation invocation) {
    try {
      // 执行方法
      invocation.invoke();
    } catch (Exception e) {
      e.printStackTrace();
      String name = e.getClass().getName();
      String errorMessage=name+":"+e.getMessage();
      log.error(errorMessage);
      Controller c = invocation.getController();
      c.renderJson(new JsonBean<Void>(-1,errorMessage));
    }
  }
}
