/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqin.entity;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.springframework.util.Assert;

/**
 * 月考勤记录 增删改查Entity
 * @author hyw
 * @version 2017-09-17
 *
 * 导入内容  工 号  姓 名  部 门  考勤日期  上班状态
 */
public class YdYuekaoqinAdmin extends DataEntity<YdYuekaoqinAdmin> {
	
	private static final long serialVersionUID = 1L;
	private String taskno;		// taskno
	private String uid;		// 工号
	private String uno;		// 用户id
	private Area area;		// area_id
	private String deptId;		// dept_id
	private String deptName;		// 部门名称
	private String name;		// name
	private Date date;		// 考勤日期
	private String status;		// status
	private String month;		// 考勤日期
	private Date createtime;		// createtime
	private Date updatetime;		// updatetime
	private String duration;		// 时长 小时 例如请假 2小时
	
	public YdYuekaoqinAdmin() {
		super();
	}

	public YdYuekaoqinAdmin(String id){
		super(id);
	}

	public String getTaskno() {
		return taskno;
	}

	public void setTaskno(String taskno) {
		this.taskno = taskno;
	}
	
	@Length(min=1, max=11, message="uid长度必须介于 1 和 50 之间")
	@ExcelField(title="工 号", fieldType = String.class,align=2, sort=20)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Length(min=0, max=255, message="dept_id长度必须介于 0 和 255 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@ExcelField(title="上班状态", align=2, sort=23,dictType="yuekaoqinadmin")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public void completion(){
		if(StringUtils.isNotBlank(getUno()) &&  UserUtils.get(getUno()) != null){// 用户id
			User user = UserUtils.get(getUno());
			this.setDeptName(user.getOffice().getName());
			this.setDeptId(user.getOffice().getId());
			this.setArea(user.getOffice().getArea());
			this.setMonth((DateUtils.formatDate(getDate(), "yyyyMM")));
			this.setName(user.getName());
			this.setUid(user.getNo());//工号
			this.setCreatetime(new Date());
			this.setUpdateDate(new Date());
		}else if(StringUtils.isNotBlank(getUid()) && UserUtils.getByUno(getUid()) != null){//工号
			User user = UserUtils.getByUno(getUid());
			this.setDeptName(user.getOffice().getName());
			this.setDeptId(user.getOffice().getId());
			this.setArea(user.getOffice().getArea());
			this.setMonth((DateUtils.formatDate(getDate(), "yyyyMM")));
			this.setName(user.getName());
			this.setUno(user.getId());
			this.setCreatetime(new Date());
			this.setUpdateDate(new Date());
		}
	}
}