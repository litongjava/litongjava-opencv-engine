package com.litong.modules.monitoring.db.service;

import java.util.List;

import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.litong.jfinal.utils.Cron4jPluginUtils;
import com.litong.modules.monitoring.db.common.model.Cron4jTaskInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2020年9月26日_下午7:10:41 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class Cron4jPluginService {
  Cron4jTaskInfo cron4jTaskInfoDao = new Cron4jTaskInfo().dao();

  public boolean restart() {
    // 1.从数据库中查询出定时任务信息
    String sql = "SELECT cron,class_name FROM t_cron4j_task_info WHERE STATUS=1 AND is_del=0";
    List<Cron4jTaskInfo> find = cron4jTaskInfoDao.find(sql);
    // 停止旧插件
    log.info("关闭旧的定时任务");
    Cron4jPlugin oldPlugin = Cron4jPluginUtils.getPlugin();
    if(oldPlugin!=null) {
      oldPlugin.stop();
    }
    // 创建新的插件
    Cron4jPlugin cron4jPlugin = new Cron4jPlugin();
    // 保存新插件
    Cron4jPluginUtils.setPlugin(cron4jPlugin);
    // 添加任务
    for (Cron4jTaskInfo cron4jTaskInfo : find) {
      String className = cron4jTaskInfo.getClassName();
      Class<?> forName = null;
      Object newInstance = null;
      try {
        forName = Class.forName(className);
        newInstance = forName.newInstance();
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      String cron = cron4jTaskInfo.getCron();
      log.info("添加定时任务,{},{}", cron, className);
      cron4jPlugin.addTask(cron, (Runnable) newInstance);
//      DbConnectService dbConnectService = new com.litong.modules.monitoring.db.task.DbConnectService();
//      cron4jPlugin.addTask(cron4jTaskInfo.getCron(),dbConnectService );
    }

    log.info("启动新定时任务");
    // 开启新插件
    return cron4jPlugin.start();
  }
}
