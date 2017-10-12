/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ydaudittemp.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * 主管领导工号	主管领导姓名	部门经理工号	部门经理姓名	部门副经理工号	部门副经理姓名
 */
public class YdAuditTemplateImport extends DataEntity<YdAuditTemplateImport> {

	@ExcelField(title = "主管领导工号",sort = 10,type = 0,fieldType = String.class)
	public String zhuGuanNo;
	@ExcelField(title = "主管领导工号",sort = 10,type = 0,fieldType = String.class)
	public String zhuGuanName;

	@ExcelField(title = "部门副经理工号",sort = 10,type = 0,fieldType = String.class)
	public String buMenFJingliNo;
	@ExcelField(title = "部门副经理姓名",sort = 10,type = 0,fieldType = String.class)
	public String buMenFJingliName;

	@ExcelField(title = "部门经理工号",sort = 10,type = 0,fieldType = String.class)
	public String buMenJingliNo;
	@ExcelField(title = "部门经理姓名",sort = 10,type = 0,fieldType = String.class)
	public String buMenJingliName;

	public String getZhuGuanNo() {
		return zhuGuanNo;
	}

	public void setZhuGuanNo(String zhuGuanNo) {
		this.zhuGuanNo = zhuGuanNo;
	}

	public String getZhuGuanName() {
		return zhuGuanName;
	}

	public void setZhuGuanName(String zhuGuanName) {
		this.zhuGuanName = zhuGuanName;
	}

	public String getBuMenFJingliNo() {
		return buMenFJingliNo;
	}

	public void setBuMenFJingliNo(String buMenFJingliNo) {
		this.buMenFJingliNo = buMenFJingliNo;
	}

	public String getBuMenFJingliName() {
		return buMenFJingliName;
	}

	public void setBuMenFJingliName(String buMenFJingliName) {
		this.buMenFJingliName = buMenFJingliName;
	}

	public String getBuMenJingliNo() {
		return buMenJingliNo;
	}

	public void setBuMenJingliNo(String buMenJingliNo) {
		this.buMenJingliNo = buMenJingliNo;
	}

	public String getBuMenJingliName() {
		return buMenJingliName;
	}

	public void setBuMenJingliName(String buMenJingliName) {
		this.buMenJingliName = buMenJingliName;
	}
}