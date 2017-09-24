/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.kaoqin.entity.ImportYdYuekaoqin;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.kaoqin.entity.YdYuekaoqinAdmin;
import com.thinkgem.jeesite.modules.kaoqin.service.YdYuekaoqinAdminService;

import java.util.List;

/**
 * 月考勤记录 增删改查Controller
 * @author lyf
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/kaoqin/ydYuekaoqinAdmin")
public class YdYuekaoqinAdminController extends BaseController {

	@Autowired
	private YdYuekaoqinAdminService ydYuekaoqinAdminService;

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public YdYuekaoqinAdmin get(@RequestParam(required=false) String id) {
		YdYuekaoqinAdmin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydYuekaoqinAdminService.get(id);
		}
		if (entity == null){
			entity = new YdYuekaoqinAdmin();
		}
		return entity;
	}
	
	@RequiresPermissions("kaoqin:ydYuekaoqinAdmin:view")
	@RequestMapping(value = {"list", ""})
	public String list(YdYuekaoqinAdmin ydYuekaoqinAdmin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<YdYuekaoqinAdmin> page = ydYuekaoqinAdminService.findPage(new Page<YdYuekaoqinAdmin>(request, response), ydYuekaoqinAdmin); 
		model.addAttribute("page", page);
		return "modules/kaoqin/ydYuekaoqinAdminList";
	}

	@RequiresPermissions("kaoqin:ydYuekaoqinAdmin:view")
	@RequestMapping(value = "form")
	public String form(YdYuekaoqinAdmin ydYuekaoqinAdmin, Model model) {
		model.addAttribute("ydYuekaoqinAdmin", ydYuekaoqinAdmin);
		return "modules/kaoqin/ydYuekaoqinAdminForm";
	}

	@RequiresPermissions("kaoqin:ydYuekaoqinAdmin:edit")
	@RequestMapping(value = "save")
	public String save(YdYuekaoqinAdmin ydYuekaoqinAdmin, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ydYuekaoqinAdmin)){
			return form(ydYuekaoqinAdmin, model);
		}
		ydYuekaoqinAdmin.completion();
		ydYuekaoqinAdminService.save(ydYuekaoqinAdmin);
		addMessage(redirectAttributes, "保存月考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/kaoqin/ydYuekaoqinAdmin/?repage";
	}
	
	@RequiresPermissions("kaoqin:ydYuekaoqinAdmin:edit")
	@RequestMapping(value = "delete")
	public String delete(YdYuekaoqinAdmin ydYuekaoqinAdmin, RedirectAttributes redirectAttributes) {
		ydYuekaoqinAdminService.delete(ydYuekaoqinAdmin);
		addMessage(redirectAttributes, "删除月考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/kaoqin/ydYuekaoqinAdmin/?repage";
	}

	//考勤记录导入

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<ImportYdYuekaoqin> list = ei.getDataList(ImportYdYuekaoqin.class);
			for (ImportYdYuekaoqin importYdYuekaoqin : list){
				if ("".equals(importYdYuekaoqin.getUserNO()) || null == importYdYuekaoqin.getUserNO()){
					logger.info("UserNO不能是空", JSON.toJSONString(importYdYuekaoqin));
					continue;
				}
				try{
					YdYuekaoqinAdmin yuekaoqinAdmin = new YdYuekaoqinAdmin();
					yuekaoqinAdmin.setUid(importYdYuekaoqin.getUserNO());
					yuekaoqinAdmin.setDate(DateUtils.parseDate(importYdYuekaoqin.getKaoqiriqi()
							, "yyyy-MM-dd"));
					String status = StringUtils.isNotBlank(importYdYuekaoqin.getShangbanzhuangtai()
					)? importYdYuekaoqin.getShangbanzhuangtai() : importYdYuekaoqin.getXiabanzhuangtai();
					yuekaoqinAdmin.setStatus(DictUtils.getDictValue(status,"yuekaoqinadmin",""));
					yuekaoqinAdmin.completion();
					ydYuekaoqinAdminService.save(yuekaoqinAdmin);
					successNum++;

				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>员工 "+importYdYuekaoqin.getUserName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>员工 "+importYdYuekaoqin.getUserName() +" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条考勤，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条考勤"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入考勤失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/kaoqin/ydYuekaoqinAdmin/list?repage";
	}


	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("kaoqin:ydYuekaoqinAdmin:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "考勤导入模板.xlsx";
			List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
			new ExportExcel("考勤导入", YdYuekaoqinAdmin.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/kaoqin/ydYuekaoqinAdmin/list?repage";
	}

}