/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 每天打卡记录
 * @author yd
 * @version 2016-07-28
 */
public class PunchcardRecord extends DataEntity<PunchcardRecord> {

	private static final long serialVersionUID = 1L;
	private String id;
	private String uId;        // 员工工号
	private String userName;        // 用户名
	private String deptId;        // 部门号
	private Date cometime;        // 上班打卡时间
	private Date gotime;        // 下班打卡时间
	private Date recordTime;        // 下班打卡时间
	private String addr;        // 打卡机号码
	private String jiqiNum;        // 打卡机器编号
	private String kqType;			// 0--上午 1--下午
	private Date createtime;        // 创建时间
	private Date updatetime;        // 更新时间

	private Date beginDate; //开始时间
	private Date endDate;//结束时间

	private Date attendanceDate;//考勤日期

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "工号", align = 2, sort = 24)
	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	@ExcelField(title = "姓名", align = 2, sort = 25)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "部门名称", align = 2, sort = 26)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@ExcelField(title = "刷卡时间", fieldType = Date.class, align = 2, sort = 27)
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Date getCometime() {
		return cometime;
	}
	public void setCometime(Date cometime) {
		this.cometime = cometime;
	}

	public Date getGotime() {
		return gotime;
	}
	public void setGotime(Date gotime) {
		this.gotime = gotime;
	}


	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getJiqiNum() {
		return jiqiNum;
	}

	public void setJiqiNum(String jiqiNum) {
		this.jiqiNum = jiqiNum;
	}

	public String getKqType() {
		return kqType;
	}

	public void setKqType(String kqType) {
		this.kqType = kqType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}

