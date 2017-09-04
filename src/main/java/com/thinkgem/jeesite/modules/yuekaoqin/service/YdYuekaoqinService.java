/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqin.service;

import java.util.List;

import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.yuekaoqin.dao.AttendanceDayDao;

/**
 * 月考勤展示Service
 * @author hyw
 * @version 2016-08-01
 */
@Service
@Transactional(readOnly = true)
public class YdYuekaoqinService extends CrudService<AttendanceDayDao, AttendanceDay> {

	public AttendanceDay get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceDay> findList(AttendanceDay attendanceDay) {
		return super.findList(attendanceDay);
	}
	
	public Page<AttendanceDay> findPage(Page<AttendanceDay> page, AttendanceDay attendanceDay) {
		return super.findPage(page, attendanceDay);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendanceDay attendanceDay) {
		super.save(attendanceDay);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttendanceDay attendanceDay) {
		super.delete(attendanceDay);
	}
	
}