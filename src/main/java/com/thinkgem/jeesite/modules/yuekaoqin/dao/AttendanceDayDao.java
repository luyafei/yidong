/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;

import java.util.List;

/**
 * 每天考勤状态记录
 * @author hyw
 * @version 2016-08-01
 */
@MyBatisDao
public interface AttendanceDayDao extends CrudDao<AttendanceDay> {

    public List<AttendanceDay> getYdYuekaoqin(AttendanceDay attendanceDay);

    public void updateStatusById(AttendanceDay attendanceDay);

    public String getRoleNameByUid(String uid);

	
}