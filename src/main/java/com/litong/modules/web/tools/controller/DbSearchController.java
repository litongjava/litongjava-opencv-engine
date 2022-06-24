package com.litong.modules.web.tools.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.litong.jfinal.utils.DbUtils;
import com.litong.modules.web.tools.data.ro.DbSearchRo;
import com.litong.modules.web.tools.validator.DbSearchValidator;
import com.litongjava.utils.vo.JsonBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2021年3月3日_下午4:36:03 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class DbSearchController extends Controller{
  @Inject
  private DbUtils dbUtils;
  @Before(DbSearchValidator.class)
  public void text(String url,String user,String pswd,String text) throws Exception {
    //连接数据库
    String dbName = dbUtils.generateDbName(url, user, pswd);
    String type=dbUtils.getType(url);
    dbUtils.start(dbName, url, user, pswd, type);
    boolean isSuccess = dbUtils.selectForMySql(dbName);
    if(!isSuccess) {
      renderJson(new JsonBean<Void>(-1,"数据库连接失败"));
      return;
    }
    //获取数据库中的表
    List<Record> tableList = dbUtils.getAllTables(dbName,type);
    if(tableList.size()<1) {
      renderJson(new JsonBean<Void>(-1,"数据库中不存在表") );
      dbUtils.stop();
      return;
    }
    List<String> tableNameList=new ArrayList<String>();
    for (Record record : tableList) {
      //取出表名
      String tableName = record.getColumnValues()[0]+"";
      tableNameList.add(tableName);
    }
    
    //遍历所有表,查询内容
    ArrayList<DbSearchRo> dbSearchRos = new ArrayList<DbSearchRo>();
    String selectColumnSql="select %s from %s where %s like '%%%s%%'";
    for (String tableName : tableNameList) {
      try {
        log.info("开始搜索表:{}",tableName);
        List<Record> records = Db.use(dbName).find("show columns from "+tableName);
        for (int i=0; i<records.size();i++) {
          Record record = records.get(i);
          //获取字段类型,如果是datetime类型跳过
          String fieldType = record.getStr("Type");
          if("datetime".equals(fieldType)) {
            continue;
          }
          String columnName = record.getStr("Field");
          //拼接格式化完成sql
          String sql = String.format(selectColumnSql,columnName ,tableName,columnName,text);
          log.info("开始搜索字段:{}",columnName);
          //log.info(sql);
          List<Record> columnsValueList = Db.use(dbName).find(sql);
          //取出其中的值
          if(columnsValueList.size()>0) {
            for(int j=0;j<columnsValueList.size();j++) {
              dbSearchRos.add(new DbSearchRo(tableName,columnName,columnsValueList.get(j).getStr(columnName)));
            }
            
            new String();
          }
        } 
      }catch (Exception e) {
        //连接失败,关闭数据库,并且抛出异常
        dbUtils.stop();
        throw e;
      }
    }
    //关闭配置
    dbUtils.stop();
    //返回表名和字段名
    renderJson(new JsonBean<List<DbSearchRo>>(dbSearchRos));
  }

}
