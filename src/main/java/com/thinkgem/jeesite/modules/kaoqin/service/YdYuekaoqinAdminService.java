/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqin.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.kaoqin.entity.YdYuekaoqinAdmin;
import com.thinkgem.jeesite.modules.kaoqin.dao.YdYuekaoqinAdminDao;

/**
 * 月考勤记录 增删改查Service
 * @author hyw
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class YdYuekaoqinAdminService extends CrudService<YdYuekaoqinAdminDao, YdYuekaoqinAdmin> {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private SystemService systemService;

	public YdYuekaoqinAdmin get(String id) {
		return super.get(id);
	}
	
	public List<YdYuekaoqinAdmin> findList(YdYuekaoqinAdmin ydYuekaoqinAdmin) {
		return super.findList(ydYuekaoqinAdmin);
	}
	
	public Page<YdYuekaoqinAdmin> findPage(Page<YdYuekaoqinAdmin> page, YdYuekaoqinAdmin ydYuekaoqinAdmin) {
		return super.findPage(page, ydYuekaoqinAdmin);
	}
	
	@Transactional(readOnly = false)
	public void save(YdYuekaoqinAdmin ydYuekaoqinAdmin) {
		if(StringUtils.isNoneBlank(ydYuekaoqinAdmin.getDeptId())){
			Office office = officeService.get(ydYuekaoqinAdmin.getDeptId());
			ydYuekaoqinAdmin.setDeptName(office.getName());

		}
		if (StringUtils.isBlank(ydYuekaoqinAdmin.getId())){



		}
		super.save(ydYuekaoqinAdmin);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdYuekaoqinAdmin ydYuekaoqinAdmin) {
		super.delete(ydYuekaoqinAdmin);
	}
	
}