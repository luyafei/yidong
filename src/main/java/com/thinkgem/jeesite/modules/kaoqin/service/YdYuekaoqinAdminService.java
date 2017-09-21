/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqin.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
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
import org.springframework.util.Assert;

/**
 * 月考勤记录 增删改查Service  此serivce 和YdYuekaoqinService 操作表相同
 * @author hyw
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class YdYuekaoqinAdminService extends CrudService<YdYuekaoqinAdminDao, YdYuekaoqinAdmin> {

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
		//根据工号查询 员工信息
		//如果是更新则只能更新状态是 正常  迟到 早退 矿工 四种
		//如果是新增 按日期   员工号  状态 判断是否已经存在  如果存在状态是 正常则更新  其它状态不做处理
		Assert.isTrue(StringUtils.isNotBlank(ydYuekaoqinAdmin.getStatus()),"考勤状态不能是空");
		Assert.notNull(ydYuekaoqinAdmin.getDate(),"考勤日期不能是空");
		YdYuekaoqinAdmin yuekaoqinAdmin = dao.getYuekaoqinAdminBydud(ydYuekaoqinAdmin);
		if (yuekaoqinAdmin == null){
			logger.info("新增考勤：{}", JSON.toJSONString(ydYuekaoqinAdmin));
			ydYuekaoqinAdmin.preInsert();
			dao.insert(ydYuekaoqinAdmin);
		}else if (yuekaoqinAdmin != null && yuekaoqinAdmin.getStatus().equals("200")){
			logger.info("更新考勤：{}",JSON.toJSONString(ydYuekaoqinAdmin));
			ydYuekaoqinAdmin.setId(yuekaoqinAdmin.getId());
			ydYuekaoqinAdmin.preUpdate();
			dao.update(ydYuekaoqinAdmin);
		} else {
			logger.info("未处理考勤{}", JSON.toJSONString(ydYuekaoqinAdmin));
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(YdYuekaoqinAdmin ydYuekaoqinAdmin) {
		super.delete(ydYuekaoqinAdmin);
	}
	
}