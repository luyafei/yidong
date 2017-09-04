/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.yd.entity.YdLeave;
import com.thinkgem.jeesite.modules.yd.dao.YdLeaveDao;

/**
 * 异常申请Service
 * @author lyf
 * @version 2016-10-29
 */
@Service
@Transactional(readOnly = true)
public class YdLeaveService extends CrudService<YdLeaveDao, YdLeave> {

	public YdLeave get(String id) {
		return super.get(id);
	}
	
	public List<YdLeave> findList(YdLeave ydLeave) {
		return super.findList(ydLeave);
	}
	
	public Page<YdLeave> findPage(Page<YdLeave> page, YdLeave ydLeave) {
		return super.findPage(page, ydLeave);
	}
	
	@Transactional(readOnly = false)
	public void save(YdLeave ydLeave) {
		super.save(ydLeave);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdLeave ydLeave) {
		super.delete(ydLeave);
	}
	
}