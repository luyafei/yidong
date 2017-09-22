/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.YDConstant;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.ydaudittemp.service.YdAuditTemplateService;
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

import java.text.ParseException;
import java.util.Date;
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


	private String auditType = YDConstant.LEAVE_TYPE;

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

		//获取审核流程
		YdAuditTemplate qtemp = new YdAuditTemplate();
		qtemp.setAuditLevel(1);
		qtemp.setRoles(user.getRoleIdList());
		qtemp.setDept(user.getOffice().getId());
		qtemp.setBusinessType(auditType);
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qtemp);
		////////////////
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
		ydLeave.setStartDate(ydLeave.getStartDate());
		ydLeave.setEndDate(ydLeave.getEndDate());
		ydLeave.setAuditUserName(UserUtils.getByLoginName(ydLeave.getAuditUserNo()).getName());
		ydLeave.setCreateDate(new Date());
		ydLeave.setUpdateDate(new Date());
		ydLeaveService.save(ydLeave);
		addMessage(redirectAttributes, "保存异常申请成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydLeave/list/?repage";
	}



	/**
	 * 审核form页面
	 * @param ydLeave
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = "autditform")
	public String autditform(YdLeave ydLeave, Model model) {

		YdLeave ydLeave1 = ydLeaveService.get(ydLeave.getId());
		//根据当前审核级别查询出下级审核人
		Integer nowLeave = ydLeave1.getAuditLevel();

		YdAuditTemplate qtemp = new YdAuditTemplate();
		qtemp.setAuditLevel(nowLeave + 1);//下级
		qtemp.setRoles(UserUtils.getByLoginName(ydLeave.getErpNo()).getRoleIdList());
		qtemp.setDept(ydLeave1.getOfficeId());
		qtemp.setBusinessType(auditType);
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qtemp);
		model.addAttribute("templateList",templateList);
		model.addAttribute("ydLeave",ydLeave1);
		return "modules/yd/ydLeaveAuditForm";
	}

	/**
	 * 审核
	 * @param ydLeave
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "audit")
	public String audit(YdLeave ydLeave, Model model) {
		//根据加班id查新加班数据
		User user = UserUtils.getUser();
		YdLeave ydLeave1 = ydLeaveService.get(ydLeave.getId());
		//异常审核通过
		if("pass".equals(ydLeave.getAuditStatus())){
			logger.info("考勤异常审核通过：{}", ydLeave.getId());
			//判断当前审核人是否是最后一级 如果是审核状态改给通过 pass  不是 是审核中 auditing
			YdAuditTemplate qtemp = new YdAuditTemplate();
			qtemp.setAuditLevel(ydLeave1.getAuditLevel());
			qtemp.setRoles(UserUtils.getByLoginName(ydLeave.getErpNo()).getRoleIdList());
			qtemp.setDept(ydLeave1.getOfficeId());
			qtemp.setBusinessType(auditType);
			qtemp.setAuditUserLoginname(user.getLoginName());
			List<YdAuditTemplate> templateList = auditTemplateService.findList(qtemp);
			ydLeave1.setRemarks(ydLeave.getRemarks());
			YdAuditTemplate template = templateList.get(0);
			if (template.isEnd(ydLeave1.getAuditLevel())){//判断当权审核级别 是否达到最后一级
				ydLeave1.setAuditStatus("pass");
				logger.info("异常审核通过，审核通过补充，天考勤记录:{}", JSON.toJSONString(ydLeave1));
				User overTimeUser = UserUtils.getByLoginName(ydLeave1.getErpNo());
				try {
					attendanceService.createAttendanceDayByDate(
                            ydLeave1.getStartDate(), ydLeave1.getEndDate(), overTimeUser, ydLeave1.getLeaveType(),
							StringUtils.isNotBlank(ydLeave1.getDuration()) ? Double.valueOf(ydLeave1.getDuration()) : null);
				} catch (ParseException e) {
					logger.info("审核通过生成异常记录失败",e);
					addMessage(model,"审核请假记录失败！");
					return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/auditList?repage";
				}
			}else {//指定下级
				ydLeave1.setAuditStatus("auditing");
				ydLeave1.setAuditLevel(ydLeave1.getAuditLevel() + 1);//审核中当前级别 加1
				ydLeave1.setAuditUserNo(ydLeave.getAuditUserNo());
				ydLeave1.setRemarks(ydLeave.getRemarks());
				ydLeave1.setAuditUserName(UserUtils.getByLoginName(ydLeave.getAuditUserNo()).getName());
				logger.info("异常审核中:{}", JSON.toJSONString(ydLeave1));
			}
		}
		//异常审核不通过
		if("no".equals(ydLeave.getAuditStatus())){
			ydLeave1.setAuditUserName(UserUtils.getUser().getName());
			ydLeave1.setAuditUserNo((UserUtils.getUser().getLoginName()));
			ydLeave1.setAuditStatus("no");
			ydLeave1.setRemarks(ydLeave.getRemarks());
			logger.info("考勤异常审核不通过：{}", JSON.toJSONString(ydLeave1));
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