/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqinall.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 部门月考勤审核状态Entity
 * @author hyw
 * @version 2016-08-14
 */
public class YdYeukaoqinAll extends DataEntity<YdYeukaoqinAll> {
	
	private static final long serialVersionUID = 1L;
	private String areaId;
	private String procInsId;		// 流程实力id
	private String attMonth;		// 考勤月份
	private String officeId;		// 部门id
	private String officeName;		// 部门名称
	private int ingStatus;
	private String auditStatus;		// 审核状态 0 未通过  1通过 2 审核中
	private String lable;
	private String isshi;
	private String isrenzikaoqin;
	private String isshenhe;	//告诉审核人员是否可以审核 true:可以	false:不可以
	
	
	
	
	public String getIsshenhe() {
		return isshenhe;
	}

	public void setIsshenhe(String isshenhe) {
		this.isshenhe = isshenhe;
	}

	public String getIsrenzikaoqin() {
		return isrenzikaoqin;
	}

	public void setIsrenzikaoqin(String isrenzikaoqin) {
		this.isrenzikaoqin = isrenzikaoqin;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public int getIngStatus() {
		return ingStatus;
	}

	public void setIngStatus(int ingStatus) {
		this.ingStatus = ingStatus;
	}
	
	
	public String getIsshi() {
		return isshi;
	}

	public void setIsshi(String isshi) {
		this.isshi = isshi;
	}

	@ExcelField(title="状态", type=1, align=2, sort=20)
	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public YdYeukaoqinAll() {
		super();
	}

	public YdYeukaoqinAll(String id){
		super(id);
	}

	@Length(min=0, max=100, message="流程实力id长度必须介于 0 和 100 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@ExcelField(title="月份", type=1, align=2, sort=30)
	@Length(min=0, max=15, message="考勤月份长度必须介于 0 和 15 之间")
	public String getAttMonth() {
		return attMonth;
	}

	public void setAttMonth(String attMonth) {
		this.attMonth = attMonth;
	}
	
	
	
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@ExcelField(title="部门", type=1, align=2, sort=10)
	@Length(min=0, max=50, message="部门名称长度必须介于 0 和 50 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=0, max=50, message="审核状态 0 未通过  1通过 2 审核中长度必须介于 0 和 50 之间")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}