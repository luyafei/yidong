/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqinyichang.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.kaoqinyichang.entity.YdKaoqinyichang;
import com.thinkgem.jeesite.modules.kaoqinyichang.service.YdKaoqinyichangService;

/**
 * kaoqinyichangController
 * @author hyw
 * @version 2016-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/kaoqinyichang/ydKaoqinyichang")
public class YdKaoqinyichangController extends BaseController {

	@Autowired
	private YdKaoqinyichangService ydKaoqinyichangService;
	
	@ModelAttribute
	public YdKaoqinyichang get(@RequestParam(required=false) String id) {
		YdKaoqinyichang entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydKaoqinyichangService.get(id);
		}
		if (entity == null){
			entity = new YdKaoqinyichang();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(YdKaoqinyichang ydKaoqinyichang, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<YdKaoqinyichang> page = ydKaoqinyichangService.findPage(new Page<YdKaoqinyichang>(request, response), ydKaoqinyichang); 
		model.addAttribute("page", page);
		return "modules/kaoqinyichang/ydKaoqinyichangList";
	}

	@RequestMapping(value = "form")
	public String form(YdKaoqinyichang ydKaoqinyichang, Model model) {
		model.addAttribute("ydKaoqinyichang", ydKaoqinyichang);
		return "modules/kaoqinyichang/ydKaoqinyichangForm";
	}

	@RequestMapping(value = "save")
	public String save(YdKaoqinyichang ydKaoqinyichang, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ydKaoqinyichang)){
			return form(ydKaoqinyichang, model);
		}
		ydKaoqinyichangService.save(ydKaoqinyichang);
		addMessage(redirectAttributes, "保存异常考勤成功");
		return "redirect:"+Global.getAdminPath()+"/kaoqinyichang/ydKaoqinyichang/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(YdKaoqinyichang ydKaoqinyichang, RedirectAttributes redirectAttributes) {
		ydKaoqinyichangService.delete(ydKaoqinyichang);
		addMessage(redirectAttributes, "删除异常考勤成功");
		return "redirect:"+Global.getAdminPath()+"/kaoqinyichang/ydKaoqinyichang/?repage";
	}

}