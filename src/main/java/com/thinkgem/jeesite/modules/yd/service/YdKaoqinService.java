/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;
import com.thinkgem.jeesite.modules.yd.dao.PunchcardRecordDao;

/**
 * ydService
 * @author yd
 * @version 2016-07-28
 */
@Service
@Transactional(readOnly = true)
public class YdKaoqinService extends CrudService<PunchcardRecordDao, PunchcardRecord> {

	@Autowired
	public PunchcardRecordDao punchcardRecordDao;

	public PunchcardRecord get(String id) {
		return super.get(id);
	}
	
	public List<PunchcardRecord> findList(PunchcardRecord punchcardRecord) {
		return super.findList(punchcardRecord);
	}
	
	public Page<PunchcardRecord> findPage(Page<PunchcardRecord> page, PunchcardRecord punchcardRecord) {
		return super.findPage(page, punchcardRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(PunchcardRecord punchcardRecord) {
		super.save(punchcardRecord);
	}
	@Transactional(readOnly = false)
	public void update(PunchcardRecord punchcardRecord){

		punchcardRecordDao.updateById(punchcardRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(PunchcardRecord punchcardRecord) {
		super.delete(punchcardRecord);
	}
	
}