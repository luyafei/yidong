/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ydaudittemp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.ydaudittemp.dao.YdAuditTemplateDao;

/**
 * 移动审核模板Service
 * @author lyf
 * @version 2017-09-09
 */
@Service
@Transactional(readOnly = true)
public class YdAuditTemplateService extends CrudService<YdAuditTemplateDao, YdAuditTemplate> {

	public YdAuditTemplate get(String id) {
		return super.get(id);
	}
	
	public List<YdAuditTemplate> findList(YdAuditTemplate ydAuditTemplate) {
		return super.findList(ydAuditTemplate);
	}
	
	public Page<YdAuditTemplate> findPage(Page<YdAuditTemplate> page, YdAuditTemplate ydAuditTemplate) {
		return super.findPage(page, ydAuditTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(YdAuditTemplate ydAuditTemplate) {
		super.save(ydAuditTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdAuditTemplate ydAuditTemplate) {
		super.delete(ydAuditTemplate);
	}
	
}