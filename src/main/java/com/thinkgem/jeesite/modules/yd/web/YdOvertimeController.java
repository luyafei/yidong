/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.yd.service.YdAuditTemplateService;
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
import com.thinkgem.jeesite.modules.yd.entity.YdOvertime;
import com.thinkgem.jeesite.modules.yd.service.YdOvertimeService;

/**
 * 申请加班Controller
 * @author lyf
 * @version 2016-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/yd/ydOvertime")
public class YdOvertimeController extends BaseController {

	@Autowired
	private YdOvertimeService ydOvertimeService;

	@Autowired
	private YdAuditTemplateService ydAuditTemplateService;

	@Autowired
	private IDayAttendanceService attendanceService;
	
	@ModelAttribute
	public YdOvertime get(@RequestParam(required=false) String id) {
		YdOvertime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydOvertimeService.get(id);
		}
		if (entity == null){
			entity = new YdOvertime();
		}
		return entity;
	}
	
	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = {"list", ""})
	public String list(YdOvertime ydOvertime, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		ydOvertime.setErpNo(user.getLoginName());
		Page<YdOvertime> page = ydOvertimeService.findPage(new Page<YdOvertime>(request, response), ydOvertime); 
		model.addAttribute("page", page);
		return "modules/yd/ydOvertimeList";
	}
	@RequestMapping(value = {"auditList", ""})
	public String auditList(YdOvertime ydOvertime, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		ydOvertime.setAuditUserNo(user.getLoginName());
		ydOvertime.setAuditStatus("auditing");
		Page<YdOvertime> page = ydOvertimeService.findPage(new Page<YdOvertime>(request, response), ydOvertime);
		model.addAttribute("page", page);
		return "modules/yd/ydOvertimeAuditList";
	}

	/**
	 * 添加加班审核页面
	 * @param ydOvertime
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = "form")
	public String form(YdOvertime ydOvertime, Model model) {
		User user = UserUtils.getUser();
		ydOvertime.setErpNo(user.getLoginName());
		ydOvertime.setErpName(user.getName());
		ydOvertime.setOfficeId(user.getOffice().getId());
		ydOvertime.setOfficeName(user.getOffice().getName());
		model.addAttribute("ydOvertime",ydOvertime);
		return "modules/yd/ydOvertimeForm";
	}

	@RequestMapping(value = "show")
	public String show(YdOvertime ydOvertime, Model model) {
		User user = UserUtils.getUser();
		YdOvertime ydOvertime1 = ydOvertimeService.get(ydOvertime.getId());
		model.addAttribute("ydOvertime",ydOvertime1);
		return "modules/yd/ydOvertimeShow";
	}


	/**
	 * 提交加班审核
	 * @param ydOvertime
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	//@RequiresPermissions("yd:ydOvertime:edit")
	@RequestMapping(value = "save")
	public String save(YdOvertime ydOvertime, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ydOvertime)){
			return form(ydOvertime, model);
		}
		/**
		 * 开启审核
		 */
		//根据加班id查新加班数据
		User user = UserUtils.getUser();
		YdAuditTemplate qydAuditTemplate = new YdAuditTemplate();
		qydAuditTemplate.setBusinessType(ydOvertime.getAuditType());
		qydAuditTemplate.setDept(user.getOffice().getId());
		qydAuditTemplate.setAuditLevel(ydOvertime.getAuditLevel() + 1);//	下级审核级别

		YdAuditTemplate auditTemplate = ydAuditTemplateService.get(qydAuditTemplate);
		ydOvertime.setAuditUserNo(auditTemplate.getAuditUserLoginname());
		ydOvertime.setAuditUserName(auditTemplate.getAuditUserName());
		ydOvertime.setAuditLevel(auditTemplate.getAuditLevel());
		ydOvertime.setAuditStatus("auditing");
		ydOvertime.setRemarks(ydOvertime.getRemarks());

		ydOvertimeService.save(ydOvertime);
		addMessage(redirectAttributes, "保存申请加班成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/list?repage";
	}

	/**
	 * 审核和form页面
	 * @param ydOvertime
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = "autditform")
	public String autditform(YdOvertime ydOvertime, Model model) {

		//根据加班id查新加班数据
		YdOvertime ydOvertime1 = ydOvertimeService.get(ydOvertime.getId());
		model.addAttribute("ydOvertime",ydOvertime1);
		return "modules/yd/ydOvertimeAuditForm";
	}

	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = "audit")
	public String audit(YdOvertime ydOvertime, Model model) {
		//根据加班id查新加班数据
		User user = UserUtils.getUser();
		YdOvertime ydOvertime1 = ydOvertimeService.get(ydOvertime.getId());
		ydOvertime1.setAuditStatus(ydOvertime.getAuditStatus());
		/**
		 * 审核通过执行以下逻辑
		 * 根据类型 级别查询下面审核人信息
		 * 查询出下级更新  auditUserNo  auditUserName  auditLevel
		 */
		YdAuditTemplate qydAuditTemplate = new YdAuditTemplate();
		qydAuditTemplate.setBusinessType(ydOvertime1.getAuditType());
		qydAuditTemplate.setDept(user.getOffice().getId());
		qydAuditTemplate.setAuditLevel(ydOvertime.getAuditLevel() + 1);//	下级审核级别
		YdAuditTemplate auditTemplate = ydAuditTemplateService.get(qydAuditTemplate);
		//加班审核通过
		if("pass".equals(ydOvertime.getAuditStatus())){
			//判断是否还有下级
			if (auditTemplate != null){
				ydOvertime1.setAuditUserNo(auditTemplate.getAuditUserLoginname());
				ydOvertime1.setAuditUserName(auditTemplate.getAuditUserName());
				ydOvertime1.setAuditLevel(auditTemplate.getAuditLevel());
				ydOvertime1.setAuditStatus("auditing");
				ydOvertime1.setRemarks(ydOvertime.getRemarks());
			}else {
				ydOvertime1.setAuditStatus("pass");
				ydOvertime1.setRemarks(ydOvertime.getRemarks());
				//补充考勤录入
				logger.info("审核通过补充，天考勤记录");
				User overTimeUser = UserUtils.getByLoginName(ydOvertime1.getErpNo());
				attendanceService.createAttendanceDayByDate(
						ydOvertime1.getStartDate(),ydOvertime1.getEndDate(),
						overTimeUser,"6");
			}
		}
		//加班审核不通过
		if("no".equals(ydOvertime.getAuditStatus())){
			ydOvertime1.setAuditUserNo(auditTemplate.getAuditUserLoginname());
			ydOvertime1.setAuditUserName(auditTemplate.getAuditUserName());
			ydOvertime1.setAuditLevel(auditTemplate.getAuditLevel());
			ydOvertime1.setRemarks(ydOvertime.getRemarks());
			ydOvertime1.setAuditStatus("no");
		}
		ydOvertimeService.save(ydOvertime1);
		return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/auditList?repage";
	}


	/**
	 * 删除加班申请 只有申请在待审核状态下才能删除
	 * @param ydOvertime
	 * @param redirectAttributes
	 * @return
	 */
	//@RequiresPermissions("yd:ydOvertime:edit")
	@RequestMapping(value = "delete")
	public String delete(YdOvertime ydOvertime, RedirectAttributes redirectAttributes) {
		YdOvertime ydOvertime1 = ydOvertimeService.get(ydOvertime);
		if ("no".equals(ydOvertime1.getAuditStatus())
				|| "pass".equals(ydOvertime1.getAuditStatus())){
			addMessage(redirectAttributes, "审核完成数据不能删除");
			return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/list?repage";
		}
		ydOvertimeService.delete(ydOvertime);
		addMessage(redirectAttributes, "删除申请加班成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/list?repage";
	}

}