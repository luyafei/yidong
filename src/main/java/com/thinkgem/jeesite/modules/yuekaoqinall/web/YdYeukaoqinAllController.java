/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqinall.web;


import java.util.ArrayList;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.yuekaoqin.dao.AttendanceDayDao;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.Yuekaoqin31Import;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.Yuekaoqin31;
import com.thinkgem.jeesite.modules.yuekaoqin.service.YdYuekaoqinService;
import com.thinkgem.jeesite.modules.yuekaoqinall.dao.YdYeukaoqinAllDao;
import com.thinkgem.jeesite.modules.yuekaoqinall.entity.YdYeukaoqinAll;
import com.thinkgem.jeesite.modules.yuekaoqinall.service.YdYeukaoqinAllService;

/**
 * 部门月考勤审核状态Controller
 * @author hyw
 * @version 2016-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/yuekaoqinall/ydYeukaoqinAll")
public class YdYeukaoqinAllController extends BaseController {

	@Autowired
	private YdYeukaoqinAllService ydYeukaoqinAllService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private AttendanceDayDao attendanceDayDao;
	
	@Autowired
	private IDayAttendanceService attendanceService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	YdYeukaoqinAllDao ydYeukaoqinAllDao;
	
	
	@ModelAttribute
	public YdYeukaoqinAll get(@RequestParam(required=false) String id) {
		YdYeukaoqinAll entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ydYeukaoqinAllService.get(id);
		}
		if (entity == null){
			entity = new YdYeukaoqinAll();
		}
		return entity;
	}
	
	
	//审核人员专用
//	@RequiresPermissions("oa:leave:view") //权限控制
	@RequestMapping(value = {"list", ""})
	public String list(YdYeukaoqinAll ydYeukaoqinAll, HttpServletRequest request, HttpServletResponse response, Model model, String pageNo ,  String pageSize , String shenhetongguo,String shenhebutongguo,String shenheid) {
//		ydYeukaoqinAll.seta
		
		if(shenhetongguo!=null && shenhetongguo.equals("true")){
			
			YdYeukaoqinAll yda = ydYeukaoqinAllDao.getYuekaoqinAllByid(shenheid);
			//市里的审核流程
			if(yda.getIsshi().equals("true") && yda.getIngStatus()==2){
				
				YdYeukaoqinAll y = new YdYeukaoqinAll();
				y.setId(shenheid);
				y.setIngStatus(3);
				y.setAuditStatus("通过");
				ydYeukaoqinAllDao.updateIngstatusByid(y);
				System.out.println("市,审核通过,最后审核"+shenheid);
			}else if(yda.getIsshi().equals("false") && yda.getIngStatus()==3){
				YdYeukaoqinAll y = new YdYeukaoqinAll();
				y.setId(shenheid);
				y.setIngStatus(4);
				y.setAuditStatus("通过");
				ydYeukaoqinAllDao.updateIngstatusByid(y);
				System.out.println("县,审核通过,最后审核"+shenheid);
			}else{
				YdYeukaoqinAll y = new YdYeukaoqinAll();
				y.setId(shenheid);
				y.setIngStatus(yda.getIngStatus()+1);
				y.setAuditStatus(yda.getAuditStatus());
				ydYeukaoqinAllDao.updateIngstatusByid(y);
				System.out.println("审核通过,中间人员审核"+shenheid);
			}
			
		}else if(shenhebutongguo!=null && shenhebutongguo.equals("false")){
			System.out.println("审核不通过"+shenheid);
			YdYeukaoqinAll y = new YdYeukaoqinAll();
			y.setId(shenheid);
			y.setIngStatus(0);
			y.setAuditStatus("不通过");
			ydYeukaoqinAllDao.updateIngstatusByid(y);
			
		}
		
		User user = UserUtils.getUser();
		Area area = user.getOffice().getArea();
//		ydYeukaoqinAll.setAreaId(area.getId());
		
		String shiname = area.getName();
		System.out.println("用户所属地区:"+shiname+",部门id:"+user.getOffice().getId());
		String userisshi = "false";
		if(shiname.equals("承德市")){
			userisshi = "true";
		}
		
		
		Role role = ydYeukaoqinAllDao.getRoleByUId(user.getId());
		System.out.println(role);
		System.out.println(ydYeukaoqinAll.getIngStatus());
		String shenfen = "";
		if(role.getName()!=null && role.getName().equals("人资考勤审核员") ){
			System.out.println("人资考勤审核员,只看:2,true;3,false");
//			ydYeukaoqinAll.setIngStatus(3);
			shenfen = "renzikaoqinshenhe";
			
		}else if( role.getName()!=null && role.getName().equals("人资考勤")){
			System.out.println("人资考勤,只看:3,true;4,false");
//			ydYeukaoqinAll.setIngStatus(3);
			shenfen = "renzikaoqin";
		}else if(role.getName()!=null && role.getName().equals("县经理")){
			System.out.println("县经理,只看:2,false");
			shenfen = "xianjingli";
		}else if(role.getName()!=null && role.getName().equals("部门经理")){
			if(userisshi.equals("true")){
				System.out.println("市部门经理,只看:1,本部门");
				shenfen = "shibumenjingli";
				ydYeukaoqinAll.setOfficeId(user.getOffice().getId());
			}else{
				System.out.println("县部门经理,只看:1,本地区,"+area.getId());
				shenfen = "xianbumenjingli";
				ydYeukaoqinAll.setAreaId(area.getId());
			}
			
		}
		
		ydYeukaoqinAll.setIsrenzikaoqin(shenfen);
		String yuefen = "";
		//月份格式清理
		if(ydYeukaoqinAll.getAttMonth()==null || ydYeukaoqinAll.getAttMonth().equals("")){
			ydYeukaoqinAll.setAttMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
			yuefen = new SimpleDateFormat("yyyy-MM").format(new Date());
		}else{
			yuefen = ydYeukaoqinAll.getAttMonth();
			ydYeukaoqinAll.setAttMonth(ydYeukaoqinAll.getAttMonth().replaceAll("-", ""));
		}
		
		Page<YdYeukaoqinAll> page = ydYeukaoqinAllService.findPage(new Page<YdYeukaoqinAll>(request, response), ydYeukaoqinAll); 
		List<YdYeukaoqinAll> deptList = page.getList();
		for(int i=0;i<deptList.size();i++){
			String status_value = deptList.get(i).getAuditStatus();
//			String lable = DictUtils.getDictLabels(status_value, "audit_status", "hyw");
			deptList.get(i).setLable(status_value);
			int ingStatus = deptList.get(i).getIngStatus();
			String isshi = deptList.get(i).getIsshi();
			System.out.println("ingStatus:"+ingStatus);
			System.out.println("isshi:"+isshi);
			System.out.println("shenfen:"+shenfen);
			//市人资考勤审核员
			if(isshi.equals("true") && ingStatus==2 && shenfen.equals("renzikaoqinshenhe") ){
				deptList.get(i).setIsshenhe("true");
			//县人资考勤审核员
			}else if(isshi.equals("false") && ingStatus==3 && shenfen.equals("renzikaoqinshenhe") ){
				deptList.get(i).setIsshenhe("true");
			//县经理
			}else if(isshi.equals("false") && ingStatus==2 && shenfen.equals("xianjingli") ){
				deptList.get(i).setIsshenhe("true");
			//市和县的部门经理 审核状态为1 可进行审核
			}else if( ingStatus==1 && (shenfen.equals("shibumenjingli") || shenfen.equals("xianbumenjingli") )  ){
				deptList.get(i).setIsshenhe("true");
			}else{
				deptList.get(i).setIsshenhe("false");
			}
			
		}
		
		//获取所有审核状态
		List<Dict> dictList = DictUtils.getDictList("audit_status");
		
		
		if(pageNo!=null && !pageNo.equals("")) page.setPageNo(Integer.parseInt(pageNo));
		if(pageSize!=null && !pageSize.equals("")) page.setPageSize(Integer.parseInt(pageSize));
		
		//选择的标签
		if(ydYeukaoqinAll.getAuditStatus()!=null){
			model.addAttribute("selected", ydYeukaoqinAll.getAuditStatus());
		}
		model.addAttribute("page", page);
		model.addAttribute("shenfen", shenfen);
		//审核选择列表
		model.addAttribute("dictList", dictList);
		//当前选择的月份
		model.addAttribute("yuefen", yuefen);
		
		
		return "modules/yuekaoqinall/ydYeukaoqinAllList";
	}
	
//	//TODO 审核完毕
//	@RequestMapping(value = "shenheSucc")
//	public String shenheSucc(YdYeukaoqinAll ydYeukaoqinAll, HttpServletRequest request, HttpServletResponse response, Model model ,String pageNo ,  String pageSize ) {
//		System.out.println("审核通过");
//		System.out.println(ydYeukaoqinAll.getId());
//		System.out.println(ydYeukaoqinAll.getIsshi());
//		System.out.println(ydYeukaoqinAll.getIngStatus());
//		
//		int ingstatus_start = ydYeukaoqinAll.getIngStatus();
//		User user = UserUtils.getUser();
//		Area area = user.getOffice().getArea();
//		ydYeukaoqinAll.setAreaId(area.getId());
//		if(ydYeukaoqinAll.getIsshi().equals("true")){
//			ydYeukaoqinAll.setOfficeId(user.getOffice().getId());
//			ydYeukaoqinAll.setOfficeName(user.getOffice().getName());
//		}
//		
//		
//		//属于市审核流程
//		if(ydYeukaoqinAll.getIsshi()!=null && ydYeukaoqinAll.getIsshi().equals("true") ){
//			
//			if(ingstatus_start<4){
//				ydYeukaoqinAll.setIngStatus(ydYeukaoqinAll.getIngStatus()+1);
//			}else if(ingstatus_start==4){
//				ydYeukaoqinAll.setIngStatus(4) ;
//			}
//			if(ingstatus_start==0){
//				ydYeukaoqinAll.setAuditStatus("审核中");
//			}else if(ingstatus_start==4){
//				ydYeukaoqinAll.setAuditStatus("通过");
//			}
//			ydYeukaoqinAllDao.updateIngstatusByid(ydYeukaoqinAll);
//		}else{
//			if(ingstatus_start<3){
//				ydYeukaoqinAll.setIngStatus(ydYeukaoqinAll.getIngStatus()+1);
//			}else if(ingstatus_start==3){
//				ydYeukaoqinAll.setIngStatus(3) ;
//			}
//			if(ingstatus_start==0){
//				ydYeukaoqinAll.setAuditStatus("审核中");
//			}else if(ingstatus_start==3){
//				ydYeukaoqinAll.setAuditStatus("通过");
//			}
//			ydYeukaoqinAllDao.updateIngstatusByid(ydYeukaoqinAll);
//		}
//		
//		
//		ydYeukaoqinAll.setAuditStatus("");
//		ydYeukaoqinAll.setOfficeId("");
//		ydYeukaoqinAll.setOfficeName("");
//		Page<YdYeukaoqinAll> page = ydYeukaoqinAllService.findPage(new Page<YdYeukaoqinAll>(request, response), ydYeukaoqinAll); 
//		model.addAttribute("page", page);
//		
//		if(ydYeukaoqinAll.getAuditStatus()!=null)
//		model.addAttribute("selected", ydYeukaoqinAll.getAuditStatus());
//		//获取所有审核状态
//				List<Dict> dictList = DictUtils.getDictList("audit_status");
//		//标签集合
//		model.addAttribute("dictList", dictList);
//		//是否属于市
//		model.addAttribute("isshi", ydYeukaoqinAll.getIsshi());
//		
//		
//		return "modules/yuekaoqinall/ydYeukaoqinAllList2";
//	}
	
	
	//TODO 部门考勤 页面初始化位置
	@RequestMapping(value = "deptkaoqin")
	public String deptkaoqin(YdYeukaoqinAll ydYeukaoqinAll, HttpServletRequest request, HttpServletResponse response, Model model, String pageNo ,  String pageSize , String tijiaoshenhe , String shenheid) {
		
		System.out.println("tijiaoshenhe:"+tijiaoshenhe);
		if(tijiaoshenhe!=null && tijiaoshenhe.equals("true")){
			YdYeukaoqinAll yda = ydYeukaoqinAllDao.getYuekaoqinAllByid(shenheid);
			YdYeukaoqinAll y = new YdYeukaoqinAll();
			y.setId(shenheid);
			y.setIngStatus(yda.getIngStatus()+1);
			y.setAuditStatus("审核中");
			ydYeukaoqinAllDao.updateIngstatusByid(y);
			System.out.println("部门考勤员提交审核:"+shenheid);
		}
		
		
		
		User user = UserUtils.getUser();
		Area area = user.getOffice().getArea();
		ydYeukaoqinAll.setAreaId(area.getId());
		
		String shiname = area.getName();
		System.out.println("用户所属地区:"+shiname);
		String isshi = "false";
		if(shiname.equals("承德市")){
			isshi = "true";
			ydYeukaoqinAll.setIsshi("true");
			ydYeukaoqinAll.setOfficeId(user.getOffice().getId());
			ydYeukaoqinAll.setOfficeName(user.getOffice().getName());
		}else{
			ydYeukaoqinAll.setIsshi("false");
		}
		
		//确定身份为部门考勤员
		ydYeukaoqinAll.setIsrenzikaoqin("bumenkaoqin");
		
		//获取所有审核状态
		List<Dict> dictList = DictUtils.getDictList("audit_status");
		
		System.out.println("审核查询状态:"+ydYeukaoqinAll.getAuditStatus());
		String yuefen = "";
		if(ydYeukaoqinAll.getAttMonth()==null || ydYeukaoqinAll.getAttMonth().equals("")){
			ydYeukaoqinAll.setAttMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
			yuefen = new SimpleDateFormat("yyyy-MM").format(new Date());
		}else{
			yuefen = ydYeukaoqinAll.getAttMonth();
			ydYeukaoqinAll.setAttMonth(ydYeukaoqinAll.getAttMonth().replaceAll("-", ""));
		}
		
		Page<YdYeukaoqinAll> page = ydYeukaoqinAllService.findPage(new Page<YdYeukaoqinAll>(request, response), ydYeukaoqinAll); 
		List<YdYeukaoqinAll> deptList = page.getList();
		for(int i=0;i<deptList.size();i++){
			String status_value = deptList.get(i).getAuditStatus();
//			String lable = DictUtils.getDictLabels(status_value, "audit_status", "hyw");
			deptList.get(i).setLable(status_value);
		}
		
		if(pageNo!=null && !pageNo.equals("")) page.setPageNo(Integer.parseInt(pageNo));
		if(pageSize!=null && !pageSize.equals("")) page.setPageSize(Integer.parseInt(pageSize));
		
		model.addAttribute("page", page);
		//选择的标签
		if(ydYeukaoqinAll.getAuditStatus()!=null)
		model.addAttribute("selected", ydYeukaoqinAll.getAuditStatus());
		//标签集合
		model.addAttribute("dictList", dictList);
		//是否属于市
		model.addAttribute("isshi", isshi);
		//当前选择的月份
		model.addAttribute("yuefen", yuefen);
		
		return "modules/yuekaoqinall/ydYeukaoqinAllList2";
	}
	
	
//	public String deptkaoqin_copy(YdYeukaoqinAll ydYeukaoqinAll, HttpServletRequest request, HttpServletResponse response, Model model, String pageNo ,  String pageSize) {
//		
//		//获取所有审核状态
//		List<Dict> dictList = DictUtils.getDictList("audit_status");
//		model.addAttribute("dictList", dictList);
//		
//		System.out.println("审核状态:"+ydYeukaoqinAll.getAuditStatus());
//		
//		if(ydYeukaoqinAll.getAuditStatus()!=null && ydYeukaoqinAll.getAuditStatus().equals("无")){
//			ydYeukaoqinAll.setAuditStatus("");
//		}
//		
//		
//		User user = UserUtils.getUser();
//		String officeid = user.getOffice().getId();
//		ydYeukaoqinAll.setOfficeId(officeid);
//		
//		if(ydYeukaoqinAll!=null && (ydYeukaoqinAll.getAttMonth()==null || ydYeukaoqinAll.getAttMonth().equals("")) ){
//			ydYeukaoqinAll.setAttMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
//		}
//		
//		Page<YdYeukaoqinAll> page = ydYeukaoqinAllService.findPage(new Page<YdYeukaoqinAll>(request, response), ydYeukaoqinAll); 
//		
//		List<YdYeukaoqinAll> deptList = page.getList();
//		for(int i=0;i<deptList.size();i++){
//			String status_value = deptList.get(i).getAuditStatus();
//			String lable = DictUtils.getDictLabels(status_value, "audit_status", "hyw");
//			deptList.get(i).setLable(lable);
//		}
//		
//		if(pageNo!=null && !pageNo.equals("")) page.setPageNo(Integer.parseInt(pageNo));
//		if(pageSize!=null && !pageSize.equals("")) page.setPageSize(Integer.parseInt(pageSize));
//		model.addAttribute("page", page);
//		return "modules/yuekaoqinall/ydYeukaoqinAllList2";
//	}
	

//	@RequiresPermissions("yuekaoqinall:ydYeukaoqinAll:view")
	@RequestMapping(value = "form")
	public String form(YdYeukaoqinAll ydYeukaoqinAll, Model model) {
		model.addAttribute("ydYeukaoqinAll", ydYeukaoqinAll);
		return "modules/yuekaoqinall/ydYeukaoqinAllForm";
	}

//	@RequiresPermissions("yuekaoqinall:ydYeukaoqinAll:edit")
	@RequestMapping(value = "save")
	public String save(YdYeukaoqinAll ydYeukaoqinAll, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ydYeukaoqinAll)){
			return form(ydYeukaoqinAll, model);
		}
		ydYeukaoqinAllService.save(ydYeukaoqinAll);
		addMessage(redirectAttributes, "保存月考勤总览成功");
		return "redirect:"+Global.getAdminPath()+"/yuekaoqinall/ydYeukaoqinAll/?repage";
	}
	
//	@RequiresPermissions("yuekaoqinall:ydYeukaoqinAll:edit")
	@RequestMapping(value = "delete")
	public String delete(YdYeukaoqinAll ydYeukaoqinAll, RedirectAttributes redirectAttributes) {
		ydYeukaoqinAllService.delete(ydYeukaoqinAll);
		addMessage(redirectAttributes, "删除月考勤总览成功");
		return "redirect:"+Global.getAdminPath()+"/yuekaoqinall/ydYeukaoqinAll/?repage";
	}
	
	@Autowired
	YdYuekaoqinService ydYuekaoqinService;
	
	//TODO	导出
	@RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(YdYeukaoqinAll ydYeukaoqinAll,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			if(ydYeukaoqinAll.getAttMonth()==null || ydYeukaoqinAll.getAttMonth().equals("")){
				ydYeukaoqinAll.setAttMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
			}
            String fileName = ydYeukaoqinAll.getAttMonth()+"月考勤数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//            Page<YdYeukaoqinAll> page = ydYeukaoqinAllService.findPage(new Page<YdYeukaoqinAll>(request, response), ydYeukaoqinAll); 

			AttendanceDay ydYuekaoqin = new AttendanceDay();
//            ydYuekaoqin.setDeptId(ydYeukaoqinAll.getOfficeId());
            ydYuekaoqin.setMonth(ydYeukaoqinAll.getAttMonth().replaceAll("-", ""));
            
            //按部门查询就导出此部门* (需要通过部门名称获取ID再进行查询) 
//            if(ydYeukaoqinAll.getOfficeId()!=null){
//            	System.out.println("导出部门:"+ydYeukaoqinAll.getOfficeId());
//            	ydYuekaoqin.setDeptId(ydYeukaoqinAll.getOfficeId());
//            }
            
            ydYuekaoqin.setShenfen("daochu");
            
            Page<AttendanceDay> page = ydYuekaoqinService.findPage(new Page<AttendanceDay>(request, response), ydYuekaoqin);
            List<Yuekaoqin31> ykq31List = new ArrayList<Yuekaoqin31>();
    		List<AttendanceDay> pageList = page.getList();
    		for(AttendanceDay ykq : pageList ){
    			String uid = ykq.getUid();
    			String thisriqi = new SimpleDateFormat("dd").format(ykq.getDate());
    			//获取状态对用的中文
    			String lable = DictUtils.getDictLabels(ykq.getStatus(), "AttendanceStatus", "hyw");
    			int isykq = -1;
    			for( Yuekaoqin31 ykq31 : ykq31List){
    				//存在实体
    				if(ykq31.getUid().equals(uid)) {
    					isykq=0;
    					setRiqiStatus(ykq31,thisriqi,lable);
    				}
    			}
    			//没有此UID对应的实体
    			if(isykq==-1){
    				Yuekaoqin31 ykq31 = new Yuekaoqin31();
    				ykq31.setUid(ykq.getUid());
    				ykq31.setName(ykq.getName());
    				ykq31.setDeptId(ykq.getDeptId());
    				ykq31.setOfficeName(ykq.getOfficeName());
    				
    				setRiqiStatus(ykq31,thisriqi,lable);
    				ykq31List.add(ykq31);
    			}
    			
    		}
            
            System.out.println(" 页数:"+page.getCount());
            System.out.println(" 集合个数:"+page.getList().size());
    		new ExportExcel(ydYeukaoqinAll.getAttMonth()+"月考勤数据", Yuekaoqin31.class).setDataList(ykq31List).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/yuekaoqinall/ydYeukaoqinAll/list";
    }
	
	
	
	
//	TODO添加对应的日期
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
	
	//TODO 导入
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			
			StringBuilder failureMsg = new StringBuilder();
			int re = -1;
			try{
				re = ydYeukaoqinAllService.importOver(file , failureMsg , redirectAttributes);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(re==0){
				addMessage(redirectAttributes, "已成功导入 "+failureMsg);
			}else{
				addMessage(redirectAttributes, "倒入失败,请检查数据格式是否正确 "+failureMsg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		
		System.out.println("跳转:"+adminPath + "/yuekaoqinall/ydYeukaoqinAllList2");
		
		return "redirect:"  + adminPath + "/yuekaoqinall/ydYeukaoqinAll/deptkaoqin";
//		return "modules/yuekaoqinall/ydYeukaoqinAllList2";
		
	}
	
	
	
	
	
	//导出成功例子
//	@RequestMapping(value = "export", method=RequestMethod.POST)
//    public String exportFile(YdYeukaoqinAll ydYeukaoqinAll,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		try {
//            String fileName = "考勤数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//            Page<YdYeukaoqinAll> page = ydYeukaoqinAllService.findPage(new Page<YdYeukaoqinAll>(request, response), ydYeukaoqinAll); 
//            //添加lable的值
//            List<YdYeukaoqinAll> deptList = page.getList();
//    		for(int i=0;i<deptList.size();i++){
//    			String status_value = deptList.get(i).getAuditStatus();
//    			String lable = DictUtils.getDictLabels(status_value, "audit_status", "hyw");
//    			deptList.get(i).setLable(lable);
//    		}
//            System.out.println(" 页数:"+page.getCount());
//            System.out.println(" 集合个数:"+page.getList().size());
//    		new ExportExcel("考勤数据", YdYeukaoqinAll.class).setDataList(page.getList()).write(response, fileName).dispose();
//    		return null;
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
//		}
//		return "redirect:" + adminPath + "/yuekaoqinall/ydYeukaoqinAll/list";
//    }
	
	
}