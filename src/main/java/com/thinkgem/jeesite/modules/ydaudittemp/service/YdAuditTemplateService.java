/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ydaudittemp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.RoleListType;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.YDConstant;
import com.thinkgem.jeesite.modules.yd.entity.YdOvertime;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplateImport;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

	//导入模板
	public void importAuditTemplate(String filePath) throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {

		 ImportExcel ei =  new ImportExcel(filePath,1);
		 List<YdAuditTemplateImport> list = ei.getDataList(YdAuditTemplateImport.class);
		//判断 市不们 县部门 如果副职栏是空那么就任务是县审批 不

		//1.普通员工市请假（1级别 副 正审批  1级别）  2.副职 请假 正审批 1级别 3.正请假  主管审批

		//1.加班市（1级别 副 正审批  2级别 ）
		List<YdAuditTemplate> all = Lists.newArrayList();
		if (list != null && list.size() > 0){
			for (YdAuditTemplateImport ydAuditTemplateImport : list){
				all.addAll(this.getLeaveTemp(ydAuditTemplateImport));
				all.addAll(this.getOvertTemp(ydAuditTemplateImport));
			}
		}else {
			logger.info("文件中获取数据是空");
		}
		if (all.size() > 0){
			for (YdAuditTemplate temp : all){
				this.save(temp);
			}
		}else {
			logger.info("模板转换数据没值");
		}

	}

	public List<YdAuditTemplate> getLeaveTemp(YdAuditTemplateImport templateImport){

		List<YdAuditTemplate> templates = Lists.newArrayList();

		User buMenFJingli = UserUtils.getByUno(templateImport.buMenFJingliNo);
		User buMenJingli = UserUtils.getByUno(templateImport.buMenJingliNo);
		User zhuGuan = UserUtils.getByUno(templateImport.zhuGuanNo);

		List<Role> puTongrole = (List<Role>) RoleListType.getValue("市普通员工");
		List<Role> xpuTongrole = (List<Role>) RoleListType.getValue("县普通员工");
		List<Role> buMenFJinglirole = (List<Role>) RoleListType.getValue("部门副经理");
		List<Role> buMenJinglirole = (List<Role>) RoleListType.getValue("部门经理");

		Boolean sxFlag = StringUtils.isBlank(templateImport.getBuMenFJingliNo()) ? false : true;
		String roleId = sxFlag ? puTongrole.get(0).getId() : xpuTongrole.get(0).getId();

		Office office = buMenJingli.getOffice();

		YdAuditTemplate putong = new YdAuditTemplate();

		YdAuditTemplate jltong = new YdAuditTemplate();
		//putong
		putong.setCountLevel(1);
		putong.setAuditUserLoginname(buMenJingli.getLoginName());
		putong.setAuditUserName(buMenJingli.getName());
		putong.setDeptName(office.getName());
		putong.setDept(office.getId());
		putong.setRole(roleId);
		putong.setBusinessType(YDConstant.LEAVE_TYPE);
		putong.setAuditLevel(1);
		putong.setCreateDate(new Date());
		putong.setUpdateDate(new Date());
		if (sxFlag){
			YdAuditTemplate putong1 = new YdAuditTemplate();
			putong1.setCountLevel(1);
			putong1.setAuditUserLoginname(buMenFJingli.getLoginName());
			putong1.setAuditUserName(buMenFJingli.getName());
			putong1.setDeptName(office.getName());
			putong1.setDept(office.getId());
			putong1.setRole(roleId);
			putong1.setBusinessType(YDConstant.LEAVE_TYPE);
			putong1.setAuditLevel(1);
			putong1.setCreateDate(new Date());
			putong1.setUpdateDate(new Date());
			//副
			YdAuditTemplate fjltong = new YdAuditTemplate();
			fjltong.setCountLevel(1);
			fjltong.setAuditUserLoginname(buMenJingli.getLoginName());
			fjltong.setAuditUserName(buMenJingli.getName());
			fjltong.setDeptName(office.getName());
			fjltong.setDept(office.getId());
			fjltong.setRole(buMenFJinglirole.get(0).getId());
			fjltong.setBusinessType(YDConstant.LEAVE_TYPE);
			fjltong.setAuditLevel(1);
			fjltong.setCreateDate(new Date());
			fjltong.setUpdateDate(new Date());
			templates.add(putong1);
			templates.add(fjltong);


		}
		//正
		jltong.setCountLevel(1);
		jltong.setAuditUserLoginname(zhuGuan.getLoginName());
		jltong.setAuditUserName(zhuGuan.getName());
		jltong.setDeptName(office.getName());
		jltong.setDept(office.getId());
		jltong.setRole(buMenJinglirole.get(0).getId());
		jltong.setBusinessType(YDConstant.LEAVE_TYPE);
		jltong.setAuditLevel(1);
		jltong.setCreateDate(new Date());
		jltong.setUpdateDate(new Date());

		templates.add(jltong);
		templates.add(putong);
		logger.info("生成审核模板数据：{}", JSON.toJSONString(templates));
		return templates;



	}

	public List<YdAuditTemplate> getOvertTemp(YdAuditTemplateImport templateImport){

		List<YdAuditTemplate> templates = Lists.newArrayList();

		User buMenFJingli = UserUtils.getByUno(templateImport.buMenFJingliNo);
		User buMenJingli = UserUtils.getByUno(templateImport.buMenJingliNo);
		User zhuGuan = UserUtils.getByUno(templateImport.zhuGuanNo);

		List<Role> puTongrole = (List<Role>) RoleListType.getValue("市普通员工");
		List<Role> xpuTongrole = (List<Role>) RoleListType.getValue("县普通员工");
		List<Role> buMenFJinglirole = (List<Role>) RoleListType.getValue("部门副经理");
		List<Role> buMenJinglirole = (List<Role>) RoleListType.getValue("部门经理");

		Boolean sxFlag = StringUtils.isBlank(templateImport.getBuMenFJingliNo()) ? false : true;
		String roleId = sxFlag ? puTongrole.get(0).getId() : xpuTongrole.get(0).getId();

		Office office = buMenJingli.getOffice();

		YdAuditTemplate putong = new YdAuditTemplate();

		YdAuditTemplate jltong = new YdAuditTemplate();
		//putong
		putong.setCountLevel(2);
		putong.setAuditUserLoginname(buMenJingli.getLoginName());
		putong.setAuditUserName(buMenJingli.getName());
		putong.setDeptName(office.getName());
		putong.setDept(office.getId());
		putong.setRole(roleId);
		putong.setBusinessType(YDConstant.OVER_TIME_TYPE);
		putong.setAuditLevel(1);
		putong.setCreateDate(new Date());
		putong.setUpdateDate(new Date());


		YdAuditTemplate putong2 = new YdAuditTemplate();
		putong2.setCountLevel(2);
		putong2.setAuditUserLoginname(zhuGuan.getLoginName());
		putong2.setAuditUserName(zhuGuan.getName());
		putong2.setDeptName(office.getName());
		putong2.setDept(office.getId());
		putong2.setRole(roleId);
		putong2.setBusinessType(YDConstant.OVER_TIME_TYPE);
		putong2.setAuditLevel(2);
		putong2.setCreateDate(new Date());
		putong2.setUpdateDate(new Date());
		if (sxFlag){
			YdAuditTemplate putong1 = new YdAuditTemplate();
			putong1.setCountLevel(2);
			putong1.setAuditUserLoginname(buMenFJingli.getLoginName());
			putong1.setAuditUserName(buMenFJingli.getName());
			putong1.setDeptName(office.getName());
			putong1.setDept(office.getId());
			putong1.setRole(roleId);
			putong1.setBusinessType(YDConstant.OVER_TIME_TYPE);
			putong1.setAuditLevel(1);
			putong1.setCreateDate(new Date());
			putong1.setUpdateDate(new Date());
			//副
			YdAuditTemplate fjltong = new YdAuditTemplate();
			fjltong.setCountLevel(2);
			fjltong.setAuditUserLoginname(buMenJingli.getLoginName());
			fjltong.setAuditUserName(buMenJingli.getName());
			fjltong.setDeptName(office.getName());
			fjltong.setDept(office.getId());
			fjltong.setRole(buMenFJinglirole.get(0).getId());
			fjltong.setBusinessType(YDConstant.OVER_TIME_TYPE);
			fjltong.setAuditLevel(1);
			fjltong.setCreateDate(new Date());
			fjltong.setUpdateDate(new Date());

			YdAuditTemplate fjltong1 = new YdAuditTemplate();
			fjltong.setCountLevel(2);
			fjltong.setAuditUserLoginname(zhuGuan.getLoginName());
			fjltong.setAuditUserName(zhuGuan.getName());
			fjltong.setDeptName(office.getName());
			fjltong.setDept(office.getId());
			fjltong.setRole(buMenFJinglirole.get(0).getId());
			fjltong.setBusinessType(YDConstant.OVER_TIME_TYPE);
			fjltong.setAuditLevel(2);
			fjltong.setCreateDate(new Date());
			fjltong.setUpdateDate(new Date());
			templates.add(putong1);
			templates.add(fjltong);
			templates.add(fjltong1);

		}

		//正
		jltong.setCountLevel(1);
		jltong.setAuditUserLoginname(zhuGuan.getLoginName());
		jltong.setAuditUserName(zhuGuan.getName());
		jltong.setDeptName(office.getName());
		jltong.setDept(office.getId());
		jltong.setRole(buMenJinglirole.get(0).getId());
		jltong.setBusinessType(YDConstant.OVER_TIME_TYPE);
		jltong.setAuditLevel(1);
		jltong.setCreateDate(new Date());
		jltong.setUpdateDate(new Date());

		templates.add(jltong);
		templates.add(putong);
		templates.add(putong2);
		logger.info("生成加班审核模板数据：{}", JSON.toJSONString(templates));
		return templates;
	}
	
}