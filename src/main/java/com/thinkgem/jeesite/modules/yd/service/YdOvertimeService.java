/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.constant.AuditStatusEnum;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.YDConstant;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.ydaudittemp.service.YdAuditTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.yd.entity.YdOvertime;
import com.thinkgem.jeesite.modules.yd.dao.YdOvertimeDao;
import org.springframework.util.Assert;

/**
 * 申请加班Service
 * @author lyf
 * @version 2016-10-29
 */
@Service
@Transactional(readOnly = true)
public class YdOvertimeService extends CrudService<YdOvertimeDao, YdOvertime> {

	public Logger logger = LoggerFactory.getLogger(YdOvertimeService.class);

	@Autowired
	private YdAuditTemplateService auditTemplateService;

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

	/**
	 * 加班保存只能覆盖 正常
	 * @param ydOvertime
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(YdOvertime ydOvertime) {
		super.save(ydOvertime);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdOvertime ydOvertime) {
		super.delete(ydOvertime);
	}

	/**
	 * 校验数据 是否合法
	 * 校验数据是否已经存在
	 * @param overtime
	 */
	public void violation(YdOvertime overtime) {

	}

	/**
	 * 补全overtime
	 * @param overtime
	 */
	public void completion(YdOvertime overtime) {

		//补全申请人信息
		User user = UserUtils.getByUno(overtime.getUserNo());
		//补全审核人
		//User auditUser = UserUtils.get(overtime.getAuditNo());
		Assert.notNull(user,"输入员工号码不正确");
		//Assert.notNull(auditUser,"审核人工号码不正确");

		YdAuditTemplate qydAuditTemplate = new YdAuditTemplate();
		qydAuditTemplate.setBusinessType(YDConstant.OVER_TIME_TYPE);
		qydAuditTemplate.setDept(user.getOffice().getId());
		qydAuditTemplate.setRoles(user.getRoleIdList());
		qydAuditTemplate.setAuditUserName(overtime.getAuditUserName());
		qydAuditTemplate.setAuditLevel(1);
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qydAuditTemplate);

		Assert.isTrue(templateList != null && templateList.size() > 0,"无对应审核人");
		YdAuditTemplate ydAuditTemplate = templateList.get(0);

		overtime.setErpNo(user.getLoginName());
		overtime.setErpName(user.getName());
		overtime.setOfficeName(user.getOffice().getName());
		overtime.setOfficeId(user.getOffice().getId());
		overtime.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_PASSING.getStatus());
		overtime.setAuditType(YDConstant.OVER_TIME_TYPE);
		overtime.setAuditLevel(1);
		overtime.setAuditUserName(ydAuditTemplate.getAuditUserName());
		overtime.setAuditUserNo(ydAuditTemplate.getAuditUserLoginname());
		overtime.setStartDate(DateUtils.parseDate(overtime.getImportSTime(), "yyyy/MM/dd HH:mm"));
		overtime.setEndDate(DateUtils.parseDate(overtime.getImportETime(), "yyyy/MM/dd HH:mm"));
		overtime.setCreateDate(new Date());
		overtime.setUpdateDate(new Date());
		logger.info("completion overtime is {}", JSON.toJSONString(overtime));

	}
}