/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.yd.service.YdKaoqinService;

import java.util.List;

/**
 * ydController
 * @author yd
 * @version 2016-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/yd/punchcardRecord")
public class PunchcardRecordController extends BaseController {

	@Autowired
	private YdKaoqinService ydKaoqinService;


	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public PunchcardRecord get(@RequestParam(required=false) String id) {
		PunchcardRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydKaoqinService.get(id);
		}
		if (entity == null){
			entity = new PunchcardRecord();
		}
		return entity;
	}
	
	/*@RequiresPermissions("yd:ydKaoqin:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(PunchcardRecord punchcardRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		//punchcardRecord.setuId(UserUtils.getUser().getNo());
		Page qpage = new Page<PunchcardRecord>(request, response);
		qpage.setOrderBy("attendance_date desc");
		Page<PunchcardRecord> page = ydKaoqinService.findPage(qpage, punchcardRecord);
		model.addAttribute("page", page);
		model.addAttribute("punchcardRecord", punchcardRecord);
		return "modules/yd/ydKaoqinList";
	}

	/*@RequiresPermissions("yd:ydKaoqin:view")*/
	@RequestMapping(value = "form")
	public String form(PunchcardRecord punchcardRecord, Model model) {
		model.addAttribute("ydKaoqin", punchcardRecord);
		return "modules/yd/ydKaoqinForm";
	}

	/*@RequiresPermissions("yd:ydKaoqin:edit")*/
	@RequestMapping(value = "save")
	public String save(PunchcardRecord punchcardRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, punchcardRecord)){
			return form(punchcardRecord, model);
		}
		ydKaoqinService.save(punchcardRecord);
		addMessage(redirectAttributes, "保存考勤成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydKaoqin/?repage";
	}
	
	/*@RequiresPermissions("yd:ydKaoqin:edit")*/
	@RequestMapping(value = "delete")
	public String delete(PunchcardRecord punchcardRecord, RedirectAttributes redirectAttributes) {
		ydKaoqinService.delete(punchcardRecord);
		addMessage(redirectAttributes, "删除考勤成功");
		return "redirect:"+Global.getAdminPath()+"/yd/ydKaoqin/?repage";
	}


	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PunchcardRecord> list = ei.getDataList(PunchcardRecord.class);
			for (PunchcardRecord punchcardRecord : list){

				try{
					int flag = DateUtils.getPMorAM(punchcardRecord.getRecordTime());

					PunchcardRecord queryPunchCard = new PunchcardRecord();
					queryPunchCard.setUserName(punchcardRecord.getUserName());
					queryPunchCard.setAttendanceDate(punchcardRecord.getRecordTime());
					List<PunchcardRecord> retList = ydKaoqinService.findList(queryPunchCard);
					if (CollectionUtils.isNotEmpty(retList) && retList.size() > 0 ){
						PunchcardRecord punchcardRecord1 = retList.get(0);
						if (1 == flag){
							punchcardRecord1.setGotime(punchcardRecord.getRecordTime());
						}else if (0 == flag){
							punchcardRecord1.setCometime(punchcardRecord.getRecordTime());
						}
						logger.info("更新打卡时间：{}:时间：{}",
								punchcardRecord.getUserName(),
								DateUtils.formatDate(punchcardRecord.getRecordTime(),"yyyy-MM-dd"));
						ydKaoqinService.update(punchcardRecord1);
						continue;
					}else {
						punchcardRecord.setAttendanceDate(punchcardRecord.getRecordTime());
						if (1 == flag){
							punchcardRecord.setGotime(punchcardRecord.getRecordTime());
						}else if (0 == flag){
							punchcardRecord.setCometime(punchcardRecord.getRecordTime());
						}
						ydKaoqinService.save(punchcardRecord);
					}



				}catch(ConstraintViolationException ex){
					ex.printStackTrace();
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					ex.printStackTrace();
					failureMsg.append("<br/>导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/yd/punchcardRecord/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

}