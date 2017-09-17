/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ydaudittemp.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 移动审核模板Entity
 * @author lyf
 * @version 2017-09-09
 */
public class YdAuditTemplate extends DataEntity<YdAuditTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String businessType;		// 审批类型
	private Integer auditLevel;		// 审核级别
	private String auditUserName;		// 审核人
	private String auditUserLoginname;		// 审核人登录名称
	private String dept;		// 部门
	private String deptName;		// 部门名称
	private String role;		// 橘色id
	private List<String> roles;		// 橘色ids
	private Integer countLevel;		// 总审核级别
	
	public YdAuditTemplate() {
		super();
	}

	public YdAuditTemplate(String id){
		super(id);
	}

	@Length(min=0, max=100, message="审批类型长度必须介于 0 和 100 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	

	public Integer getAuditLevel() {
		return auditLevel;
	}

	public void setAuditLevel(Integer auditLevel) {
		this.auditLevel = auditLevel;
	}
	
	@Length(min=0, max=100, message="审核人长度必须介于 0 和 100 之间")
	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	
	@Length(min=0, max=100, message="审核人登录名称长度必须介于 0 和 100 之间")
	public String getAuditUserLoginname() {
		return auditUserLoginname;
	}

	public void setAuditUserLoginname(String auditUserLoginname) {
		this.auditUserLoginname = auditUserLoginname;
	}
	
	@Length(min=0, max=100, message="部门长度必须介于 0 和 100 之间")
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@Length(min=0, max=100, message="部门名称长度必须介于 0 和 100 之间")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getCountLevel() {
		return countLevel;
	}

	public void setCountLevel(Integer countLevel) {
		this.countLevel = countLevel;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public boolean isEnd(Integer level){
		return this.countLevel.equals(level) ? true : false;
	}
}