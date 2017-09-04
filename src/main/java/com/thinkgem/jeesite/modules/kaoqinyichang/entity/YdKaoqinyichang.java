/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqinyichang.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * kaoqinyichangEntity
 * @author hyw
 * @version 2016-08-09
 */
public class YdKaoqinyichang extends DataEntity<YdKaoqinyichang> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// uid
	private String deptId;		// dept_id
	private String name;		// name
	private Date date;		// 考勤日期
	private String status;		// status
	private Date createtime;		// createtime
	private Date updatetime;		// updatetime
	
	public YdKaoqinyichang() {
		super();
	}

	public YdKaoqinyichang(String id){
		super(id);
	}

	@Length(min=1, max=11, message="uid长度必须介于 1 和 11 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=11, message="dept_id长度必须介于 0 和 11 之间")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Length(min=0, max=100, message="status长度必须介于 0 和 100 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}