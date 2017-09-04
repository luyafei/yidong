/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请加班Entity
 * @author lyf
 * @version 2016-10-29
 */
public class YdOvertime extends DataEntity<YdOvertime> {
	
	private static final long serialVersionUID = 1L;
	private String erpNo;		// 申请人登录账号
	private String erpName;		// 申请人名称
	private String officeId;		// 部门id
	private String officeName;		// 部门名称
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String ydType;		// 1 请假  2加班
	private String auditType;		// 审核类型
	private Integer auditLevel;		// 当前审核级别
	private String auditStatus;		// 审核状态
	private String auditUserNo;		// 审核人登录帐号
	private String auditUserName;		// 审核人
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public YdOvertime() {
		super();
	}

	public YdOvertime(String id){
		super(id);
	}

	@Length(min=0, max=50, message="申请人登录账号长度必须介于 0 和 50 之间")
	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}
	
	@Length(min=0, max=50, message="申请人名称长度必须介于 0 和 50 之间")
	public String getErpName() {
		return erpName;
	}

	public void setErpName(String erpName) {
		this.erpName = erpName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=11, message="1 请假  2加班长度必须介于 0 和 11 之间")
	public String getYdType() {
		return ydType;
	}

	public void setYdType(String ydType) {
		this.ydType = ydType;
	}

	public Integer getAuditLevel() {
		return auditLevel;
	}

	public void setAuditLevel(Integer auditLevel) {
		this.auditLevel = auditLevel;
	}

	@Length(min=0, max=10, message="审核状态长度必须介于 0 和 10 之间")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=50, message="审核人登录帐号长度必须介于 0 和 50 之间")
	public String getAuditUserNo() {
		return auditUserNo;
	}

	public void setAuditUserNo(String auditUserNo) {
		this.auditUserNo = auditUserNo;
	}
	
	@Length(min=0, max=50, message="审核人长度必须介于 0 和 50 之间")
	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
}