/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.yd.entity.YdOvertime;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.yd.service.YdAuditTemplateService;
import org.activiti.engine.impl.util.json.JSONObject;
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
import com.thinkgem.jeesite.modules.yd.entity.YdLeave;
import com.thinkgem.jeesite.modules.yd.service.YdLeaveService;

import java.util.List;

/**
 * 异常申请Controller
 * @author lyf
 * @version 2016-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/yd/ydLeave")
public class YdLeaveController extends BaseController {

	@Autowired
	private YdLeaveService ydLeaveService;

	@Autowired
	private YdAuditTemplateService auditTemplateService;

	@Autowired
	private IDayAttendanceService attendanceService;
	
	@ModelAttribute
	public YdLeave get(@RequestParam(required=false) String id) {
		YdLeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydLeaveService.get(id);
		}
		if (entity == null){
			entity = new YdLeave();
		}
		return entity;
	}
	
	//@RequiresPermissions("yd:ydLeave:view")
	@RequestMapping(value = {"list", ""})
	public String list(YdLeave ydLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		ydLeave.setErpNo(user.getLoginName());
		Page<YdLeave> page = ydLeaveService.findPage(new Page<YdLeave>(request, response), ydLeave); 
		model.addAttribute("page", page);
		return "modules/yd/ydLeaveList";
	}

	@RequestMapping(value = {"auditlist", ""})
	public String auditlist(YdLeave ydLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		ydLeave.setAuditUserNo(user.getLoginName());
		ydLeave.setAuditStatus("auditing");
		Page<YdLeave> page = ydLeaveService.findPage(new Page<YdLeave>(request, response), ydLeave);
		model.addAttribute("page", page);
		model.addAttribute("ydLeave",ydLeave);
		return "modules/yd/ydLeaveAuditList";
	}

	//@RequiresPermissions("yd:ydLeave:view")
	@RequestMapping(value = "form")
	public String form(YdLeave ydLeave, Model model) {
		User user = UserUtils.getUser();
		YdLeave ydLeave1 = new YdLeave();
		ydLeave1.setErpNo(user.getLoginName());
		ydLeave1.setErpName(user.getName());
		ydLeave1.setOfficeId(user.getOffice().getId());
		ydLeave1.setOfficeName(user.getOffice().getName());


		YdAuditTemplate qtemp = new YdAuditTemplate();
		qtemp.setAuditLevel(1);
		qtemp.setBusinessType("ydLeave_audit");
		qtemp.setDept(user.getOffice().getId());
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qtemp);
		model.addAttribute("templateList",templateList);
		model.addAttribute("ydLeave",ydLeave1);
		return "modules/yd/ydLeaveForm";
	}

	@RequestMapping(value = "show")
	public String show(YdLeave ydLeave, Model model) {
		YdLeave ydLeave1 = ydLeaveService.get(ydLeave.getId());
		model.addAttribute("ydLeave",ydLeave1);
		return "modules/yd/ydLeaveShow";
	}

	//@RequiresPermissions("yd:ydLeave:edit")
	@RequestMapping(value = "save")
	public String save(YdLeave ydLeave, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ydLeave)){
			return form(ydLeave, model);
		}
		//指定人审核不用查下级审核人
		ydLeave.setAuditStatus("auditing");
		YdAuditTemplate ydAuditTemplate = new YdAuditTemplate();
		User retUser = UserUtils.getByLoginName(ydLeave.getAuditUserNo());
		ydAuditTemplate.setDept(retUser.getOffice().getId());
		ydAuditTemplate.setBusinessType("ydLeave_audit");
		List<YdAuditTemplate> retydAuditTemplate = auditTemplateService.findList(ydAuditTemplate);
		ydLeave.setAuditUserName(UserUtils.getByLoginName(ydLeave.getAuditUserNo()).getName());
		if (null != retydAuditTemplate){
			YdAuditTemplate two = null;
			for(YdAuditTemplate temp : retydAuditTemplate){
				if ("2".equals(temp.getAuditLevel())){
					two = temp;
				}
				if (temp.getAuditUserLoginname().equals(UserUtils.getUser().getLoginName())
						&& "1".equals(temp.getAuditLevel())){
					ydLeave.setAuditUserName(null == two ? temp.getAuditUserName() : two.getAuditUserName());
				}
			}
		}
		ydLeaveService.save(ydLeave);
		addMessage(redirectAttributes, "保存异常申请成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydLeave/list/?repage";
	}



	/**
	 * 审核和form页面
	 * @param ydLeave
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = "autditform")
	public String autditform(YdLeave ydLeave, Model model) {

		//根据加班id查新加班数据
		YdLeave ydLeave1 = ydLeaveService.get(ydLeave.getId());
		model.addAttribute("ydLeave",ydLeave1);
		return "modules/yd/ydLeaveAuditForm";
	}


	@RequestMapping(value = "audit")
	public String audit(YdLeave ydLeave, Model model) {
		//根据加班id查新加班数据
		User user = UserUtils.getUser();
		YdLeave ydLeave1 = ydLeaveService.get(ydLeave.getId());
		//异常审核通过
		if("pass".equals(ydLeave.getAuditStatus())){
			logger.info("考勤异常审核通过：{}", ydLeave.getId());
			ydLeave1.setAuditStatus("pass");
			ydLeave1.setRemarks(ydLeave.getRemarks());
			logger.info("审核通过补充，天考勤记录");
			User overTimeUser = UserUtils.getByLoginName(ydLeave1.getErpNo());
			attendanceService.createAttendanceDayByDate(
					ydLeave1.getStartDate(),ydLeave1.getEndDate(), overTimeUser,"7");

		}
		//异常审核不通过
		if("no".equals(ydLeave.getAuditStatus())){
			logger.info("考勤异常审核不通过：{}", ydLeave.getId());
			ydLeave1.setAuditStatus("no");
			ydLeave1.setRemarks(ydLeave.getRemarks());
		}
		ydLeaveService.save(ydLeave1);
		return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/auditList?repage";
	}
	
	//@RequiresPermissions("yd:ydLeave:edit")
	@RequestMapping(value = "delete")
	public String delete(YdLeave ydLeave, RedirectAttributes redirectAttributes) {
		YdLeave ydLeave1 = ydLeaveService.get(ydLeave);
		if ("no".equals(ydLeave1.getAuditStatus())
				|| "pass".equals(ydLeave1.getAuditStatus())){
			addMessage(redirectAttributes, "审核完成数据不能删除");
			return "redirect:"+Global.getAdminPath()+"/yd/ydLeave/list?repage";
		}
		ydLeaveService.delete(ydLeave);
		addMessage(redirectAttributes, "删除异常申请成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydLeave/list?repage";
	}

}