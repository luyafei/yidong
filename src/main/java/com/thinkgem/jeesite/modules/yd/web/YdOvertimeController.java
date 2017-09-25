/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.kaoqin.entity.YdYuekaoqinAdmin;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.YDConstant;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplate;
import com.thinkgem.jeesite.modules.ydaudittemp.service.YdAuditTemplateService;
import com.thinkgem.jeesite.modules.yuekaoqinall.service.YdYeukaoqinAllService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.yd.entity.YdOvertime;
import com.thinkgem.jeesite.modules.yd.service.YdOvertimeService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
	private IDayAttendanceService attendanceService;

	@Autowired
	private YdYeukaoqinAllService ydYeukaoqinAllService;

	@Autowired
	private YdAuditTemplateService auditTemplateService;

	private String auditType = YDConstant.OVER_TIME_TYPE;

	private final static String bumenkaoqin = "bumenkaoqin";

	
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
		List<Role> roles = user.getRoleList();
		for (Role r : roles){
			if (bumenkaoqin.equals(r.getEnname())){
				ydOvertime.setOfficeId(user.getOffice().getId());
				ydOvertime.setErpNo(null);
			}
		}
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

		YdAuditTemplate qtemp = new YdAuditTemplate();
		qtemp.setAuditLevel(1);
		qtemp.setRoles(user.getRoleIdList());
		qtemp.setDept(user.getOffice().getId());
		qtemp.setBusinessType(auditType);
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qtemp);
		////////////////
		model.addAttribute("templateList",templateList);
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
		Boolean flag = ydYeukaoqinAllService.isAudit(DateUtils.formatDate(ydOvertime.getStartDate(), "yyyyMM"), ydOvertime.getOfficeId());
		if(!flag){
			addMessage(redirectAttributes, "月考勤上报中或者已审核完成不允许操作");
			return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/list?repage";
		}

		if (!beanValidator(model, ydOvertime)){
			return form(ydOvertime, model);
		}
		ydOvertime.setAuditStatus("auditing");
		ydOvertime.setAuditLevel(1);
		ydOvertime.setRemarks(ydOvertime.getRemarks());
		ydOvertime.setStartDate(ydOvertime.getStartDate());
		ydOvertime.setEndDate(ydOvertime.getEndDate());
		ydOvertime.setAuditUserName(UserUtils.getByLoginName(ydOvertime.getAuditUserNo()).getName());
		ydOvertime.setAuditUserNo(ydOvertime.getAuditUserNo());
		ydOvertime.setCreateDate(new Date());
		ydOvertime.setUpdateDate(new Date());
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
		YdOvertime ydOvertime1 = ydOvertimeService.get(ydOvertime.getId());
		Integer nowLeave = ydOvertime.getAuditLevel();
		YdAuditTemplate qtemp = new YdAuditTemplate();
		qtemp.setAuditLevel(nowLeave + 1);//下级
		qtemp.setRoles(UserUtils.getByLoginName(ydOvertime1.getErpNo()).getRoleIdList());
		qtemp.setDept(ydOvertime1.getOfficeId());
		qtemp.setBusinessType(ydOvertime1.getAuditType());
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qtemp);
		model.addAttribute("templateList",templateList);
		model.addAttribute("ydOvertime",ydOvertime1);
		return "modules/yd/ydOvertimeAuditForm";
	}

	//@RequiresPermissions("yd:ydOvertime:view")
	@RequestMapping(value = "audit")
	public String audit(YdOvertime ydOvertime, Model model) {
		User user = UserUtils.getUser();
		YdOvertime ydOvertime1 = ydOvertimeService.get(ydOvertime.getId());
		ydOvertime1.setAuditStatus(ydOvertime.getAuditStatus());
		YdAuditTemplate qydAuditTemplate = new YdAuditTemplate();
		qydAuditTemplate.setBusinessType(ydOvertime1.getAuditType());
		qydAuditTemplate.setDept(ydOvertime.getOfficeId());
		qydAuditTemplate.setRoles(UserUtils.getByLoginName(ydOvertime1.getErpNo()).getRoleIdList());
		qydAuditTemplate.setAuditUserLoginname(user.getLoginName());
		qydAuditTemplate.setAuditLevel(ydOvertime.getAuditLevel());
		List<YdAuditTemplate> templateList = auditTemplateService.findList(qydAuditTemplate);
		//加班审核通过
		if("pass".equals(ydOvertime.getAuditStatus())){
			if (templateList.get(0).isEnd(ydOvertime1.getAuditLevel())){
				ydOvertime1.setAuditStatus("pass");
				ydOvertime1.setRemarks(ydOvertime.getRemarks());
				logger.info("审核通过补充，天考勤记录");
				User overTimeUser = UserUtils.getByLoginName(ydOvertime1.getErpNo());
				try {
					attendanceService.createAttendanceDayByDate(
                            ydOvertime1.getStartDate(),ydOvertime1.getEndDate(), overTimeUser, YDConstant.time_jb,
							StringUtils.isNotBlank(ydOvertime1.getDuration()) ? Double.valueOf(ydOvertime1.getDuration()) : null);
				} catch (ParseException e) {
					logger.info("审核通过生成加班记录失败",e);
					addMessage(model, "审核请假记录失败！");
					return "redirect:"+Global.getAdminPath()+"/yd/ydOvertime/auditList?repage";
				}
			}else {
				ydOvertime1.setAuditStatus("auditing");
				ydOvertime1.setAuditLevel(ydOvertime1.getAuditLevel() + 1);//
				ydOvertime1.setAuditUserNo(ydOvertime.getAuditUserNo());
				ydOvertime1.setRemarks(ydOvertime.getRemarks());
				ydOvertime1.setAuditUserName(UserUtils.getByLoginName(ydOvertime.getAuditUserNo()).getName());
			}
		}
		//加班审核不通过
		if("no".equals(ydOvertime.getAuditStatus())){
			ydOvertime1.setAuditUserName(UserUtils.getUser().getName());
			ydOvertime1.setAuditUserNo((UserUtils.getUser().getLoginName()));
			ydOvertime1.setRemarks(ydOvertime.getRemarks());
			ydOvertime1.setAuditStatus("no");
			logger.info("加班审核不通过：{}", JSON.toJSONString(ydOvertime1));
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



	/**
	 * 导入加班申请
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("yd:overtime:import")
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<YdOvertime> list = ei.getDataList(YdOvertime.class);
			for (YdOvertime overtime : list){
				try{
					//根据导入内容 补充overtime
					ydOvertimeService.violation(overtime);
					ydOvertimeService.completion(overtime);
					ydOvertimeService.save(overtime);
					successNum++;

				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>加班申请 "+overtime.getErpName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>加班申请 "+overtime.getErpName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入加班失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/yd/ydOvertime/list?repage";
	}


	/**
	 * 下载导入加班数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * 工号	姓名	开始时间	结束时间	审核人姓名
	 */
	/*@RequiresPermissions("yd:overtime:import")*/
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "加班导入模板.xlsx";
			List<YdOvertime> list = Lists.newArrayList();
			YdOvertime ydOvertime = new YdOvertime();
			ydOvertime.setUserNo("0000000");
			ydOvertime.setErpName("测试");
			ydOvertime.setImportSTime("2017-9-24 8:00");
			ydOvertime.setImportETime("2017-9-24 17:00");
			ydOvertime.setAuditUserName("管理员");
			list.add(ydOvertime);
			new ExportExcel("", YdOvertime.class, 0).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/yd/ydOvertime/list?repage";
	}

}