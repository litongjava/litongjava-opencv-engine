package com.litong.modules.monitoring.db.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.litong.modules.monitoring.db.task.DbConnectService;
import com.litongjava.utils.vo.JsonBean;

/**
 * @author litong
 * @date 2020年9月26日_下午4:27:34 
 * @version 1.0 
 * @desc
 */
public class DbController extends Controller {
  private DbConnectService dbConnectService = Aop.get(DbConnectService.class);

  /**
   * 测试connect连接
   */
  public void taskConnect() {
    new Thread(dbConnectService).start();
    JsonBean<Void> jsonBean = new JsonBean<Void>();
    jsonBean.setMsg("检测数据库连接中,请查看数据库连接记录");
    renderJson(jsonBean);
  }
}
