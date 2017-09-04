/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqinyichang.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.kaoqinyichang.entity.YdKaoqinyichang;
import com.thinkgem.jeesite.modules.kaoqinyichang.dao.YdKaoqinyichangDao;

/**
 * kaoqinyichangService
 * @author hyw
 * @version 2016-08-09
 */
@Service
@Transactional(readOnly = true)
public class YdKaoqinyichangService extends CrudService<YdKaoqinyichangDao, YdKaoqinyichang> {

	public YdKaoqinyichang get(String id) {
		return super.get(id);
	}
	
	public List<YdKaoqinyichang> findList(YdKaoqinyichang ydKaoqinyichang) {
		return super.findList(ydKaoqinyichang);
	}
	
	public Page<YdKaoqinyichang> findPage(Page<YdKaoqinyichang> page, YdKaoqinyichang ydKaoqinyichang) {
		return super.findPage(page, ydKaoqinyichang);
	}
	
	@Transactional(readOnly = false)
	public void save(YdKaoqinyichang ydKaoqinyichang) {
		super.save(ydKaoqinyichang);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdKaoqinyichang ydKaoqinyichang) {
		super.delete(ydKaoqinyichang);
	}
	
}