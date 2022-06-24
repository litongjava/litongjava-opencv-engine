package com.litong.jfinal.utils;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.litongjava.utils.db.DbTypeConstants;

/**
 * @author litong
 * @date 2021年3月3日_上午11:44:40 
 * @version 1.0 
 * @desc 数据库工具类
 */
public class DbUtils {

  private DruidPlugin plugin1;
  private ActiveRecordPlugin arp1;
  /**
   * 启动数据源
   */
  public void start(String dbName,String url,String user,String pswd,String type) {
    plugin1 = new DruidPlugin(url, user, pswd);
    arp1= new ActiveRecordPlugin(dbName, plugin1);
    plugin1.start();
    arp1.start();
  }
  
  public void stop() {
    arp1.stop();
    plugin1.stop();
  }
 
  /**
   * 判断数据源是否连接成功
   */
  public boolean selectForMySql(String dbName) {
    List<Record> find = Db.use(dbName).find("select 1");
    if (find != null) {
      //连接成功
      return true;
    }
    return false;
  }

  /**
   * 生成数据库唯一的名称
   * @param url
   * @param user
   * @param pswd
   * @param type
   * @return
   */
  public String generateDbName(String url, String user, String pswd) {
    String md5Hex = DigestUtils.md5Hex(url+user+pswd); //md5加密
    return md5Hex;
  }

  /**
   * 获取数据库中的所有表
   * @param dbName
   * @param type 
   * @return
   * @throws Exception 
   */
  public List<Record> getAllTables(String dbName, String type) throws Exception {
    if(type.equals(DbTypeConstants.mysql)) {
      List<Record> tableList = Db.use(dbName).find("show tables");
      return tableList;
    }else {
      throw new Exception("目前仅支持mysql数据库");
    }
  }

  /**
   * 返回数据库类型
   * @param url
   * @return
   */
  public String getType(String url) {
    if(url.startsWith("jdbc:mysql")) {
      return DbTypeConstants.mysql;
    }
    return null;
  }
}
