/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqin.web;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.yuekaoqin.dao.AttendanceDayDao;

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
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.Yuekaoqin31;
import com.thinkgem.jeesite.modules.yuekaoqin.service.YdYuekaoqinService;
import com.thinkgem.jeesite.modules.yuekaoqinall.dao.YdYeukaoqinAllDao;

/**
 * 月考勤展示Controller
 * @author hyw
 * @version 2016-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/yuekaoqin/attendanceDay")
public class YdYuekaoqinController extends BaseController {

	@Autowired
	private YdYuekaoqinService ydYuekaoqinService;
	@Autowired
	private AttendanceDayDao ydYuekaoqinDao;
	@Autowired
	YdYeukaoqinAllDao ydYeukaoqinAllDao;
	
	@ModelAttribute
	public AttendanceDay get(@RequestParam(required=false) String id) {
		AttendanceDay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydYuekaoqinService.get(id);
		}
		if (entity == null){
			entity = new AttendanceDay();
		}
		return entity;
	}
	
//	@RequiresPermissions("admin:ydYuekaoqin:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(YdYuekaoqin ydYuekaoqin, HttpServletRequest request, HttpServletResponse response, Model model , String pageNo ,  String pageSize ,String isYue) {
////		if(isYue==null ){
////			System.out.println("只能看本人考勤.");
////			User user = UserUtils.getUser();
////			ydYuekaoqin.setUid(user.getNo());
////		}else{
//			User user = UserUtils.getUser();
//			try{
//				String roleName = ydYuekaoqinDao.getRoleNameByUid(user.getId());
//				if(roleName.equals("考勤员")){
//					System.out.println("只能看本部门考勤.");
//					ydYuekaoqin.setDeptId(user.getOffice().getId());
//				}else{
//					System.out.println("只能看本人考勤.");
////					User user = UserUtils.getUser();
//					ydYuekaoqin.setUid(user.getNo());
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
////		}
//		
//		String shuoming = new SimpleDateFormat("yyyy年MM").format(new Date())+"月考勤数据：";
//		String month = ydYuekaoqin.getMonth();
//		if(month!=null && !month.equals("")){
//			if(month.length()==6 && month.matches("\\d{6}")){
//				shuoming = month.substring(0,4)+"年"+month.substring(4)+"月考勤数据：";
//			}else{
//				shuoming = month;
//			}
//		}
//		model.addAttribute("shuoming", shuoming);
//		
//		Page<YdYuekaoqin> page = ydYuekaoqinService.findPage(new Page<YdYuekaoqin>(request, response), ydYuekaoqin); 
//		
//		
//		
//		Map<String,Map<String,String>> statusMap = new HashMap<String, Map<String,String>>();
//		Map<String,String> nameMap = new HashMap<String,String>();
//		
//		List<YdYuekaoqin> pageList = page.getList();
//		for(YdYuekaoqin ykq : pageList ){
//			String uid = ykq.getUid();
//			ykq.setRiqi(new SimpleDateFormat("dd").format(ykq.getDate()));
//			Map<String, String> lmap = statusMap.get(uid);
//			if(lmap==null){
//				lmap = new HashMap<String, String>();
//			}
//			//获取状态对用的中文
//			String lable = DictUtils.getDictLabels(ykq.getStatus(), "AttendanceStatus", "hyw");
//			lmap.put(ykq.getRiqi(),lable);
//			statusMap.put(uid , lmap);
//			nameMap.put(uid,ykq.getName());
////			System.out.println("uid:"+uid);
//		}
//		page.setCount(nameMap.size());
//		if(pageNo!=null && !pageNo.equals("")) page.setPageNo(Integer.parseInt(pageNo));
//		if(pageSize!=null && !pageSize.equals("")) page.setPageSize(Integer.parseInt(pageSize));
//		
//		page.setOrderBy("uid asc");
//		
////		System.out.println(page);
//		
//		model.addAttribute("nameMap", nameMap);
//		model.addAttribute("statusMap", statusMap);
//		model.addAttribute("page", page);
////		model.addAttribute("message", "");
//		
//		return "modules/yuekaoqin/ydYuekaoqinList";
//	}
	
//	public static String[] root31 = {"01","02","03","04","05","06","07","08","09","10"
//									  ,"11","12","13","14","15","16","17","18","19","20"
//									  ,"21","22","23","24","25","26","27","28","29","30","31"
//									  };
	
	//TODO	审核人员月考勤详情页跳转
	
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceDay attendanceDay, HttpServletRequest request, HttpServletResponse response, Model model , String pageNo ,  String pageSize ) {
		
		
		System.out.println("=========审核人员月考勤详情页action=========");
		User user = UserUtils.getUser();
		System.out.println(user.getOffice().getArea());
		Area area = user.getOffice().getArea();
		String shiname = area.getName();
		System.out.println("用户所属地区:"+shiname);
		String isshi = "false";
		if(shiname.equals("承德市")){
			isshi = "true";
		}
		
		Role role = ydYeukaoqinAllDao.getRoleByUId(user.getId());
		System.out.println(role);
		String shenfen = "";
		if(role.getName()!=null && role.getName().equals("人资考勤审核员") ){
			System.out.println("人资考勤审核员,看所有地区,所有部门");
//			ydYeukaoqinAll.setIngStatus(3);
			shenfen = "renzikaoqinshenhe";
			
		}else if( role.getName()!=null && role.getName().equals("人资考勤")){
			System.out.println("人资考勤,看所有地区,所有部门");
//			ydYeukaoqinAll.setIngStatus(3);
			shenfen = "renzikaoqin";
		}else if(role.getName()!=null && role.getName().equals("县经理")){
			System.out.println("县经理,只看:2,false");
			shenfen = "xianjingli";
		}else if(role.getName()!=null && role.getName().equals("部门经理")){
			System.out.println("部门经理,只看:1");
			shenfen = "bumenjingli";
		}
		model.addAttribute("shenfen", shenfen);
		
		
		System.out.println("访问用户的部门id："+attendanceDay.getDeptId());
		
		if(attendanceDay!=null && attendanceDay.getDeptId()!=null && !attendanceDay.getDeptId().equals("")){
			Office office = officeDao.getOffById(attendanceDay.getDeptId());
			attendanceDay.setOfficeName(office.getName());
//			model.addAttribute("testData", office);
		}else{
			System.out.println("注意！！ 部门id为空...");
//			if(attendanceDay!=null && attendanceDay.getOfficeName()!=null && !attendanceDay.getOfficeName().equals("")){
//				try{
//					Office office = officeDao.getOffByName(attendanceDay.getOfficeName());
//					System.out.println("office.getId():"+office.getId());
//					
//					attendanceDay.setDeptId(office.getId());
//				}catch(Exception e){
//					
//					attendanceDay.setDeptId("-1");
//				}
//			}
			
			
		}
		
		System.out.println("ydYuekaoqin.getDeptId():"+attendanceDay.getDeptId());
		


		String shuoming = new SimpleDateFormat("yyyy年MM").format(new Date())+"月考勤数据：";
		String month = attendanceDay.getMonth();
		System.out.println("查询月份信息："+month);
		if(month!=null && !month.equals("")){
			if(month.length()==6 && month.matches("\\d{6}")){
				shuoming = month.substring(0,4)+"年"+month.substring(4)+"月考勤数据：";
			}else{
				shuoming = month;
			}
			if(month.contains("-"))month = month.replaceAll("-", "");
		}else{
			month = new SimpleDateFormat("yyyy-MM").format(new Date());
		}
		attendanceDay.setMonth(month);
		model.addAttribute("shuoming", shuoming);

//		System.out.println("开始查询"+shenfen);
		attendanceDay.setShenfen(shenfen);
		
		Page<AttendanceDay> page = ydYuekaoqinService.findPage(new Page<AttendanceDay>(request, response), attendanceDay);

//		System.out.println("查询完毕");

		Map<String,Map<String,String>> statusMap = new HashMap<String, Map<String,String>>();
		Map<String,String> nameMap = new HashMap<String,String>();

		List<AttendanceDay> pageList = page.getList();
		for(AttendanceDay ykq : pageList ){
			String uid = ykq.getUid();
			ykq.setRiqi(new SimpleDateFormat("dd").format(ykq.getDate()));
			Map<String, String> lmap = statusMap.get(uid);
			if(lmap==null){
				lmap = new HashMap<String, String>();
			}
			//获取状态对用的中文
			String lable = DictUtils.getDictLabels(ykq.getStatus(), "AttendanceStatus", "unknow");
			if(lable.equals("加班")){
				lable += ykq.getDuration()==null?"":ykq.getDuration();
			}
			lmap.put(ykq.getRiqi(),lable);
			statusMap.put(uid , lmap);
			nameMap.put(uid,ykq.getName());
//			System.out.println("uid:"+uid);
		}
		page.setCount(nameMap.size());
		if(pageNo!=null && !pageNo.equals("")) page.setPageNo(Integer.parseInt(pageNo));
		if(pageSize!=null && !pageSize.equals("")) page.setPageSize(Integer.parseInt(pageSize));

		page.setOrderBy("uid asc");

//		System.out.println(page);

		model.addAttribute("nameMap", nameMap);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("page", page);
		model.addAttribute("deptname", attendanceDay.getOfficeName());
		model.addAttribute("deptId", attendanceDay.getDeptId());
		
		if(!month.contains("-"))month = month.substring(0,4)+"-"+month.substring(4,6);
		model.addAttribute("month", month);
		
		
//		model.addAttribute("message", "");

		//是否属于市
		model.addAttribute("isshi", isshi);
		
		
		
		return "modules/yuekaoqin/ydYuekaoqinList";
	}
	
	//TODO 添加对应的日期
	public static int setRiqiStatus(Yuekaoqin31 is31 , String riqi ,String lable){
		int re = -1;
		try{
			if(riqi==null)return -1;
			if(riqi.equals("01"))is31.setRi01(lable);
			if(riqi.equals("02"))is31.setRi02(lable);
			if(riqi.equals("03"))is31.setRi03(lable);
			if(riqi.equals("04"))is31.setRi04(lable);
			if(riqi.equals("05"))is31.setRi05(lable);
			if(riqi.equals("06"))is31.setRi06(lable);
			if(riqi.equals("07"))is31.setRi07(lable);
			if(riqi.equals("08"))is31.setRi08(lable);
			if(riqi.equals("09"))is31.setRi09(lable);
			if(riqi.equals("10"))is31.setRi10(lable);
			if(riqi.equals("11"))is31.setRi11(lable);
			if(riqi.equals("12"))is31.setRi12(lable);
			if(riqi.equals("13"))is31.setRi13(lable);
			if(riqi.equals("14"))is31.setRi14(lable);
			if(riqi.equals("15"))is31.setRi15(lable);
			if(riqi.equals("16"))is31.setRi16(lable);
			if(riqi.equals("17"))is31.setRi17(lable);
			if(riqi.equals("18"))is31.setRi18(lable);
			if(riqi.equals("19"))is31.setRi19(lable);
			if(riqi.equals("20"))is31.setRi20(lable);
			if(riqi.equals("21"))is31.setRi21(lable);
			if(riqi.equals("22"))is31.setRi22(lable);
			if(riqi.equals("23"))is31.setRi23(lable);
			if(riqi.equals("24"))is31.setRi24(lable);
			if(riqi.equals("25"))is31.setRi25(lable);
			if(riqi.equals("26"))is31.setRi26(lable);
			if(riqi.equals("27"))is31.setRi27(lable);
			if(riqi.equals("28"))is31.setRi28(lable);
			if(riqi.equals("29"))is31.setRi29(lable);
			if(riqi.equals("30"))is31.setRi30(lable);
			if(riqi.equals("31"))is31.setRi31(lable);
			re = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return re;
	}
	
	
	
	@Autowired
	private OfficeDao officeDao;

	//TODO 部门月考勤详情
	@RequestMapping(value ="renziyuekaoqin")
	public String renziyuekaoqin(AttendanceDay ydYuekaoqin, HttpServletRequest request, HttpServletResponse response, Model model , String pageNo ,  String pageSize ) {
		
		User user = UserUtils.getUser();
		System.out.println(user.getOffice().getArea());
		Area area = user.getOffice().getArea();
		String shiname = area.getName();
		System.out.println("用户所属地区:"+shiname);
		String isshi = "false";
		if(shiname.equals("承德市")){
			isshi = "true";
		}
		
		System.out.println(ydYuekaoqin.getName());
		System.out.println(ydYuekaoqin.getOfficeName());
		
		if(ydYuekaoqin!=null && ydYuekaoqin.getDeptId()!=null){
			Office office = officeDao.getOffById(ydYuekaoqin.getDeptId());
			ydYuekaoqin.setOfficeName(office.getName());
		}else{
			System.out.println("注意！！ 部门id为空...");
//			if(ydYuekaoqin!=null && ydYuekaoqin.getOfficeName()!=null && !ydYuekaoqin.getOfficeName().equals("")){
//				try{
//					Office office = officeDao.getOffByName(ydYuekaoqin.getOfficeName());
//					System.out.println("office.getId():"+office.getId());
//					
//					ydYuekaoqin.setDeptId(office.getId());
//				}catch(Exception e){
//					
//					ydYuekaoqin.setDeptId("-1");
//				}
//			}
		}
		
		System.out.println("ydYuekaoqin.getDeptId():"+ydYuekaoqin.getDeptId());
		


		String shuoming = new SimpleDateFormat("yyyy年MM").format(new Date())+"月考勤数据：";
		String month = ydYuekaoqin.getMonth();
		if(month!=null && !month.equals("")){
			if(month.length()==6 && month.matches("\\d{6}")){
				shuoming = month.substring(0,4)+"年"+month.substring(4)+"月考勤数据：";
			}else{
				shuoming = month;
			}
			ydYuekaoqin.setMonth(ydYuekaoqin.getMonth().replaceAll("-", "") );
		}else{
			ydYuekaoqin.setMonth(new SimpleDateFormat("yyyy-MM").format(new Date()));
		}
		model.addAttribute("shuoming", shuoming);

		
		Page<AttendanceDay> page = ydYuekaoqinService.findPage(new Page<AttendanceDay>(request, response), ydYuekaoqin);



		Map<String,Map<String,String>> statusMap = new HashMap<String, Map<String,String>>();
		Map<String,String> nameMap = new HashMap<String,String>();

		List<AttendanceDay> pageList = page.getList();
		for(AttendanceDay ykq : pageList ){
			String uid = ykq.getUid();
			ykq.setRiqi(new SimpleDateFormat("dd").format(ykq.getDate()));
			Map<String, String> lmap = statusMap.get(uid);
			if(lmap==null){
				lmap = new HashMap<String, String>();
			}
			//获取状态对用的中文
			String lable = DictUtils.getDictLabels(ykq.getStatus(), "AttendanceStatus", "unknow");
			if(lable.equals("加班")){
				lable += ykq.getDuration()==null?"":ykq.getDuration();
			}
			lmap.put(ykq.getRiqi(),lable);
			statusMap.put(uid , lmap);
			nameMap.put(uid,ykq.getName());
//			System.out.println("uid:"+uid);
		}
		page.setCount(nameMap.size());
		if(pageNo!=null && !pageNo.equals("")) page.setPageNo(Integer.parseInt(pageNo));
		if(pageSize!=null && !pageSize.equals("")) page.setPageSize(Integer.parseInt(pageSize));

		page.setOrderBy("uid asc");

//		System.out.println(page);

		model.addAttribute("nameMap", nameMap);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("page", page);
		model.addAttribute("deptname", ydYuekaoqin.getOfficeName());
		
		String yuefen = "";
		
		//当前选择的月份
		model.addAttribute("month", ydYuekaoqin.getMonth().substring(0,4)+"-"+ydYuekaoqin.getMonth().substring(4,6));
		
		System.out.println("详情查看的月份:"+ydYuekaoqin.getMonth());
		
//		model.addAttribute("message", "");

		//是否属于市
		model.addAttribute("isshi", isshi);
		
		return "modules/yuekaoqin/ydYuekaoqinList2";
	}

	@RequestMapping(value = "form")
	public String form(AttendanceDay attendanceDay, Model model) {
		model.addAttribute("ydYuekaoqin", attendanceDay);
		return "modules/yuekaoqin/ydYuekaoqinForm";
	}

//	@RequiresPermissions("admin:ydYuekaoqin:view")
	@RequestMapping(value = "save")
	public String save(AttendanceDay attendanceDay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendanceDay)){
			return form(attendanceDay, model);
		}
		ydYuekaoqinService.save(attendanceDay);
		addMessage(redirectAttributes, "保存月考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/yuekaoqin/ydYuekaoqin/?repage";
	}

//	@RequiresPermissions("admin:ydYuekaoqin:view")
	@RequestMapping(value = "delete")
	public String delete(AttendanceDay attendanceDay, RedirectAttributes redirectAttributes) {
		ydYuekaoqinService.delete(attendanceDay);
		addMessage(redirectAttributes, "删除月考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/yuekaoqin/ydYuekaoqin/?repage";
	}

}