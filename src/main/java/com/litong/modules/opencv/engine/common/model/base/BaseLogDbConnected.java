package com.litong.modules.opencv.engine.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseLogDbConnected<M extends BaseLogDbConnected<M>> extends Model<M> implements IBean {

	/**
	 * 主键
	 */
	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	/**
	 * 主键
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}
	
	/**
	 * 连接地址
	 */
	public void setUrl(java.lang.String url) {
		set("url", url);
	}
	
	/**
	 * 连接地址
	 */
	public java.lang.String getUrl() {
		return getStr("url");
	}
	
	/**
	 * 连接结果,1 成功或者0失败
	 */
	public void setResult(java.lang.String result) {
		set("result", result);
	}
	
	/**
	 * 连接结果,1 成功或者0失败
	 */
	public java.lang.String getResult() {
		return getStr("result");
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
