package com.litong.jfinal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.litong.modules.monitoring.db.common.model.SysUser;
import com.litongjava.utils.digest.MD5Utils;
import com.litongjava.utils.digest.SHA1Utils;
import com.litongjava.utils.string.UUIDUtils;
import com.litongjava.utils.vo.JsonBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月29日_下午5:25:26 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class UserService {
  private SysUser sysUserDao = new SysUser().dao();

  /**
   * 添加用户
   * @param name
   * @param encodeText
   */
  public boolean add(String loginName, String encodeText) {
    String sql = "SELECT MAX(number) FROM %s";
    sql = format(sql);
    Record record = Db.findFirst(sql);
    Long max = record.getLong("MAX(number)");
    if (max == null) {
      max = 1L;
    } else {
      max += 1;
    }
    SysUser sysUser = new SysUser();
    sysUser.setId(UUIDUtils.random());
    sysUser.setNumber((Long) max);
    sysUser.setLoginName(loginName);
    sysUser.setPassword(encodeText);
    sysUser.setCreateTime(new Date());
    sysUser.setUpdateTime(new Date());
    return sysUser.save();
  }

  /**
   * 登录用户
   * @param loginName
   * @param encodeText
   */
  public SysUser login(String loginName, String encodeText) {
    String sql = "select * from %s where login_name=? and password=?";
    sql = format(sql);
    SysUser sysUser = sysUserDao.findFirst(sql, loginName, encodeText);
    return sysUser;
  }

  private String format(String sql) {
    Table table = TableMapping.me().getTable(sysUserDao.getClass());
    sql = String.format(sql, table.getName());
    return sql;
  }

  /**
   * 返回角色
   * @param uid
   * @return
   */
  public List<JSONObject> getRelationRole(String uid) {
    List<JSONObject> data = new ArrayList<>();
    JSONObject j1 = new JSONObject();
    j1.put("roles_id", "1,2");
    data.add(j1);

    JSONObject j2 = new JSONObject();
    j2.put("roles_id", "3,4");
    data.add(j2);

    return data;
  }

  /**
   * 返回权限
   * @param role
   * @return
   */
  public List<JSONObject> getPermission(String role) {
    List<JSONObject> data = new ArrayList<>();
    JSONObject j1 = new JSONObject();
    j1.put("permissions_code", "1,2");
    data.add(j1);

    JSONObject j2 = new JSONObject();
    j2.put("permissions_code", "3,4");
    data.add(j2);
    return data;
  }

  public JsonBean<Void> updatePswd(String number, String oldPassowrd,String plainText) {
    //验证旧密码是否正确
    String odlPasswordencodeText = MD5Utils.encode(SHA1Utils.encode(oldPassowrd));
    String sql="select count(*) from sys_user where number=? and password=?";
    Record findFirst = Db.findFirst(sql, number,odlPasswordencodeText);
    Integer countUser = findFirst.getInt("count(*)");
    if(countUser<1) {
      return new JsonBean<Void>(-1, "密码不正确");
    }
      
    //对新密码进行加密
    log.info("update password the new password:{}",plainText);
    String encodeText = MD5Utils.encode(SHA1Utils.encode(plainText));
    //
    sql = "update sys_user set password=? where number=?";
    //log.info("{},{}",number,encodeText);
    int update = Db.update(sql, encodeText,number);
    log.info("update sys_user password result:{}",update);
    return new JsonBean<Void>("密码修改成功");
  }
}
