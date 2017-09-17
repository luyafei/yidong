/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ydaudittemp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.ydaudittemp.service.YdAuditTemplateService;

import java.util.Date;
import java.util.List;

/**
 * 移动审核模板Controller
 * @author lyf
 * @version 2017-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/ydaudittemp/ydAuditTemplate")
public class YdAuditTemplateController extends BaseController {

	@Autowired
	private YdAuditTemplateService ydAuditTemplateService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public YdAuditTemplate get(@RequestParam(required=false) String id) {
		YdAuditTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydAuditTemplateService.get(id);
		}
		if (entity == null){
			entity = new YdAuditTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("ydaudittemp:ydAuditTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(YdAuditTemplate ydAuditTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<YdAuditTemplate> page = ydAuditTemplateService.findPage(new Page<YdAuditTemplate>(request, response), ydAuditTemplate); 
		model.addAttribute("page", page);
		return "modules/ydaudittemp/ydAuditTemplateList";
	}

	@RequiresPermissions("ydaudittemp:ydAuditTemplate:view")
	@RequestMapping(value = "form")
	public String form(YdAuditTemplate ydAuditTemplate, Model model) {
		model.addAttribute("ydAuditTemplate", ydAuditTemplate);
		model.addAttribute("roleList", systemService.findAllRole());
		return "modules/ydaudittemp/ydAuditTemplateForm";
	}

	@RequiresPermissions("ydaudittemp:ydAuditTemplate:view")
	@RequestMapping(value = "newform")
	public String newAdd(YdAuditTemplate ydAuditTemplate, Model model) {
		ydAuditTemplate.setId(null);
		ydAuditTemplate.setAuditUserLoginname(null);
		ydAuditTemplate.setAuditUserName(null);
		model.addAttribute("ydAuditTemplate", ydAuditTemplate);
		model.addAttribute("roleList", systemService.findAllRole());
		return "modules/ydaudittemp/ydnewAddAuditTemplateForm";
	}

	//@RequiresPermissions("ydaudittemp:ydAuditTemplate:edit")
	@RequestMapping(value = "save")
	public String save(YdAuditTemplate ydAuditTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ydAuditTemplate)){
			return form(ydAuditTemplate, model);
		}

		User auditUser = (UserUtils.get(ydAuditTemplate.getAuditUserLoginname()));//获取审核人信息
		Office office = officeService.get(ydAuditTemplate.getDept());
		if (auditUser == null){
			auditUser = UserUtils.getByLoginName((ydAuditTemplate.getAuditUserLoginname()));
		}
		//auditUser.getRole().get
		ydAuditTemplate.setAuditUserLoginname(auditUser.getLoginName());
		ydAuditTemplate.setAuditUserName(auditUser.getName());
		ydAuditTemplate.setDeptName(office.getName());
		ydAuditTemplate.setDept(ydAuditTemplate.getDept());
		ydAuditTemplate.setCreateDate(new Date());
		ydAuditTemplate.setUpdateDate(new Date());
		ydAuditTemplateService.save(ydAuditTemplate);
		addMessage(redirectAttributes, "保存移动审核模板成功");
		return "redirect:"+Global.getAdminPath()+"/ydaudittemp/ydAuditTemplate/?repage&role="
				+ ydAuditTemplate.getRole() + "&businessType=" + ydAuditTemplate.getBusinessType() ;
	}
	
	//@RequiresPermissions("ydaudittemp:ydAuditTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(YdAuditTemplate ydAuditTemplate, RedirectAttributes redirectAttributes) {
		ydAuditTemplateService.delete(ydAuditTemplate);
		addMessage(redirectAttributes, "删除移动审核模板成功");
		return "redirect:"+Global.getAdminPath()+"/ydaudittemp/ydAuditTemplate/?repage";
	}

}