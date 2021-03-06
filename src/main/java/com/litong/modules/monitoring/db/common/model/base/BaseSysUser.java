package com.litong.modules.monitoring.db.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysUser<M extends BaseSysUser<M>> extends Model<M> implements IBean {

	/**
	 * 主键
	 */
	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	/**
	 * 主键
	 */
	public java.lang.String getId() {
		return getStr("id");
	}
	
	/**
	 * 编号
	 */
	public void setNumber(java.lang.Long number) {
		set("number", number);
	}
	
	/**
	 * 编号
	 */
	public java.lang.Long getNumber() {
		return getLong("number");
	}
	
	/**
	 * 登录名
	 */
	public void setLoginName(java.lang.String loginName) {
		set("login_name", loginName);
	}
	
	/**
	 * 登录名
	 */
	public java.lang.String getLoginName() {
		return getStr("login_name");
	}
	
	/**
	 * 密码
	 */
	public void setPassword(java.lang.String password) {
		set("password", password);
	}
	
	/**
	 * 密码
	 */
	public java.lang.String getPassword() {
		return getStr("password");
	}
	
	/**
	 * 昵称
	 */
	public void setNickName(java.lang.String nickName) {
		set("nick_name", nickName);
	}
	
	/**
	 * 昵称
	 */
	public java.lang.String getNickName() {
		return getStr("nick_name");
	}
	
	/**
	 * 注册ip
	 */
	public void setRegisterIp(java.lang.String registerIp) {
		set("register_ip", registerIp);
	}
	
	/**
	 * 注册ip
	 */
	public java.lang.String getRegisterIp() {
		return getStr("register_ip");
	}
	
	/**
	 * 是否删除,1删除,0未删除
	 */
	public void setIsDel(java.lang.String isDel) {
		set("is_del", isDel);
	}
	
	/**
	 * 是否删除,1删除,0未删除
	 */
	public java.lang.String getIsDel() {
		return getStr("is_del");
	}
	
	/**
	 * 创建者
	 */
	public void setCreateBy(java.lang.String createBy) {
		set("create_by", createBy);
	}
	
	/**
	 * 创建者
	 */
	public java.lang.String getCreateBy() {
		return getStr("create_by");
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}
	
	/**
	 * 更新者
	 */
	public void setUpdateBy(java.lang.String updateBy) {
		set("update_by", updateBy);
	}
	
	/**
	 * 更新者
	 */
	public java.lang.String getUpdateBy() {
		return getStr("update_by");
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime() {
		return get("update_time");
	}
	
	/**
	 * 备注
	 */
	public void setRemarks(java.lang.String remarks) {
		set("remarks", remarks);
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getRemarks() {
		return getStr("remarks");
	}
	
}
