/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqin.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 *
 * 员工天考勤实体，记录员工每天考勤的状态
 * @author hyw
 * @version 2016-08-01
 */
public class AttendanceDay extends DataEntity<AttendanceDay> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		//员工编号
	private String areaId;	//地区编号
	private String deptId;		// 部门id
	private String officeName;  //部门名称
	private String name;		//姓名
	private String month;		//月份
	private Date date;		// 考勤日期
	private String status;		// 考勤状态
	private Date createtime;		// 创建时间
	private Date updatetime;		// 更新时间
	private String riqi;  //考勤日期简写
	private String shenfen; //角色身份
	private Double duration;//时间长度

	///

	private Date startDate;
	private Date endDate;
	
	
	
	
	public String getShenfen() {
		return shenfen;
	}

	public void setShenfen(String shenfen) {
		this.shenfen = shenfen;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}

	public AttendanceDay() {
		super();
	}

	public AttendanceDay(String id){
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
}