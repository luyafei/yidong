/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.yd.entity.YdOvertime;
import com.thinkgem.jeesite.modules.yd.dao.YdOvertimeDao;

/**
 * 申请加班Service
 * @author lyf
 * @version 2016-10-29
 */
@Service
@Transactional(readOnly = true)
public class YdOvertimeService extends CrudService<YdOvertimeDao, YdOvertime> {

	public YdOvertime get(String id) {
		return super.get(id);
	}
	
	public List<YdOvertime> findList(YdOvertime ydOvertime) {
		return super.findList(ydOvertime);
	}
	
	public Page<YdOvertime> findPage(Page<YdOvertime> page, YdOvertime ydOvertime) {
		return super.findPage(page, ydOvertime);
	}
	
	@Transactional(readOnly = false)
	public void save(YdOvertime ydOvertime) {
		super.save(ydOvertime);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdOvertime ydOvertime) {
		super.delete(ydOvertime);
	}
	
}