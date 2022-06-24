package com.litong.modules.monitoring.db.task;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Aop;
import com.litong.jfinal.thread.pool.ThreadPoolKit;
import com.litong.modules.monitoring.db.common.model.DbConnectInfo;
import com.litong.modules.monitoring.db.common.model.LogDbConnected;
import com.litong.modules.monitoring.db.common.model.MailReceiver;
import com.litong.modules.monitoring.db.service.MailService;
import com.litongjava.utils.db.JdbcUtils;
import com.litongjava.utils.mail.MailUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2020年9月26日_下午3:24:34 
 * @version 1.0 
 * @desc 连接数据库,出现异常,发送邮件
 */
@Slf4j
public class DbConnectService implements Runnable {
  private MailReceiver mailReceiverDao = new MailReceiver().dao();
  private DbConnectInfo dbConnectInfoDao = new DbConnectInfo().dao();

  private MailService mailService = Aop.get(MailService.class);

  @Override
  public void run() {
    // 1.从数据库中查询查收出数据库连接
    String sqlString = "SELECT name,jdbc_url,jdbc_user,jdbc_pswd FROM t_db_connect_info WHERE is_del=0 AND STATUS=1;";
    List<DbConnectInfo> dbConnectList = dbConnectInfoDao.find(sqlString);
    // 2.从数据库中查询数邮件接受人
    sqlString = "SELECT `to` FROM mail_receiver WHERE is_del=0 AND STATUS=1";
    List<MailReceiver> mailReceiverList = mailReceiverDao.find(sqlString);
    // 3.检测数据库连接,检测失败,发送邮件报警
    for (DbConnectInfo dbConnect : dbConnectList) {
      String name = dbConnect.getName();
      String url = dbConnect.getStr("jdbc_url");
      String user = dbConnect.getStr("jdbc_user");
      String pswd = dbConnect.getStr("jdbc_pswd");
      String jdbcType = JdbcUtils.getJdbcType(url);
      String driverClass = JdbcUtils.getDriverClass(jdbcType);
      String validateSql = JdbcUtils.getValidateSql(jdbcType);
      // 数据库连接日志
      LogDbConnected logDbConnected = new LogDbConnected();
      logDbConnected.setCreateTime(new Date());
      logDbConnected.setUrl(url);

      try {
        boolean connect = JdbcUtils.connect(driverClass, url, user, pswd, validateSql);
        if (connect) {
          log.info("连接成功 {}:{}", name, url);
          logDbConnected.setResult("成功");
        } else {
          logDbConnected.setResult("失败");
          log.info("连接失败 {}:{}", name, url);
        }
      } catch (Exception e) {
        logDbConnected.setResult("失败");
        log.info("连接失败:{},{},{},{}",name ,url,user,pswd);
        e.printStackTrace();
        String exceptionName = e.getClass().getName();
        String exceptionMessage = e.getMessage();
        StringBuffer content = new StringBuffer();
        content.append("出现异常:");
        content.append(exceptionName + ":");
        content.append(exceptionMessage + "\r\n");
        content.append("连接失败 :" + name + " " + url + "\r\n");
        content.append("用户名:"+user+",密码"+pswd+"\r\n");
        content.append("请速联系管理员检测数据库是否正常");
        // 使用新线程发送邮件
        for (MailReceiver m : mailReceiverList) {
          ThreadPoolKit.use("pool1").execute(() -> {
            mailService.sendMail(m.getTo(), "错误 检测数据库连接失败", content.toString(), false);
          });
        }
      }
      logDbConnected.save();
    }
  }

//  public void test1() {
//    Cron4jPlugin cp = new Cron4jPlugin();
//    cp.addTask("* * * * *", new MyTask());
//  }

  public static void main(String[] args) {
    String driverClass = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost/monitoring_db?characterEncoding=UTF8";
    String user = "root";
    String pswd = "";
    String validateSql = "select 1";
    try {
      boolean connect = JdbcUtils.connect(driverClass, url, user, pswd, validateSql);
      if (connect) {
        System.out.println("连接成功");
      } else {
        System.out.println("连接失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      String name = e.getClass().getName();
      String message = e.getMessage();
      String content = "出现异常:" + name + ":" + message;
      MailUtils.sendMail("litongjava@qq.com", "错误 检测数据库连接失败", content, false);
    }
  }
}
