/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqinall.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.yuekaoqin.dao.AttendanceDayDao;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.Yuekaoqin31Import;
import com.thinkgem.jeesite.modules.yuekaoqinall.entity.YdYeukaoqinAll;
import com.thinkgem.jeesite.modules.yuekaoqinall.dao.YdYeukaoqinAllDao;

/**
 * 部门月考勤审核状态Service
 * @author hyw
 * @version 2016-08-14
 */
@Service
public class YdYeukaoqinAllService extends CrudService<YdYeukaoqinAllDao, YdYeukaoqinAll>  {

	@Autowired
	private UserDao userDao;
	@Autowired
	private IDayAttendanceService attendanceService;
	
	@Autowired
	YdYeukaoqinAllDao ydYeukaoqinAllDao;
	@Autowired
	AttendanceDayDao attendanceDayDao;
	
	public YdYeukaoqinAll get(String id) {
		return super.get(id);
	}
	
	public List<YdYeukaoqinAll> findList(YdYeukaoqinAll ydYeukaoqinAll) {
		return super.findList(ydYeukaoqinAll);
	}
	
	public Page<YdYeukaoqinAll> findPage(Page<YdYeukaoqinAll> page, YdYeukaoqinAll ydYeukaoqinAll) {
		return super.findPage(page, ydYeukaoqinAll);
	}
	
	@Transactional(readOnly = false)
	public void save(YdYeukaoqinAll ydYeukaoqinAll) {
		super.save(ydYeukaoqinAll);
	}
	
	@Transactional(readOnly = false)
	public void delete(YdYeukaoqinAll ydYeukaoqinAll) {
		super.delete(ydYeukaoqinAll);
	}
	
	
	@Transactional
	public int importOver (MultipartFile file , StringBuilder failureMsg, RedirectAttributes redirectAttributes) throws Exception{
		int re = -1; 
//		try{
			int successNum = 0;
			int failureNum = 0;
			String filenamess = file.getOriginalFilename().replaceFirst("[.].*", "").trim();
			
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<Yuekaoqin31Import> list = ei.getDataList(Yuekaoqin31Import.class);
			
			User user = UserUtils.getUser();
			
			Area area = user.getOffice().getArea();
			System.out.println(area.getId());
			
			for (Yuekaoqin31Import yuekaoqin31 : list){
				AttendanceDay attendanceDay = new AttendanceDay();
					
					System.out.println("list:"+list.size());
					System.out.println("yuekaoqin31:"+yuekaoqin31.getName());
					
					User u = new User();
					u.setNo(yuekaoqin31.getUid());
					User u2 = userDao.get(u);
					attendanceDay.setUid(yuekaoqin31.getUid());
					attendanceDay.setDeptId(u2.getOffice().getId());
					attendanceDay.setName(yuekaoqin31.getName());
					attendanceDay.setAreaId(area.getId());
					attendanceDay.setMonth(filenamess);
					
					attendanceDay.setCreateDate(new Date());
					attendanceDay.setUpdateDate(new Date());
					
					for(int i=1;i<=31;i++){
						
						Method method = null;
						if(i<10){
							method = Yuekaoqin31Import.class.getMethod("getRi0"+i, null);
						}else{
							method = Yuekaoqin31Import.class.getMethod("getRi"+i, null);
						}
						String riValue = method.invoke(yuekaoqin31).toString();
						if(riValue==null || riValue.equals("")){
							continue;
						}
						String dist_value = DictUtils.getDictValue( riValue , "AttendanceStatus", "");
						if(i<10){
							attendanceDay.setDate(new SimpleDateFormat("yyyyMMdd").parse(filenamess+"0"+i));
						}else{
							attendanceDay.setDate(new SimpleDateFormat("yyyyMMdd").parse(filenamess+i));
						}
						attendanceDay.setStatus(dist_value);
						AttendanceDay isuserinfo = ydYeukaoqinAllDao.isUserInfo(attendanceDay.getUid(),attendanceDay.getDate());
						if(isuserinfo==null){
//							attendanceService.saveOrUpdate(attendanceDay);
							attendanceDayDao.insert(attendanceDay);
						}
						
					}
					successNum++;
					
				
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			
			if(successNum>0){
				//插入待审核数据
				Office office = user.getOffice();
				String shiname = area.getName();
				System.out.println("用户所属地区:"+shiname);
				String isshi = "false";
				if(shiname.equals("承德市")){
					isshi = "true";
				}
				YdYeukaoqinAll yda = new YdYeukaoqinAll();
				yda.setAttMonth(filenamess);
				yda.setAreaId(area.getId());
				yda.setOfficeId(office.getId());
				yda.setOfficeName(office.getName());
				yda.setIngStatus(0);
				yda.setAuditStatus("未提交");
				yda.setIsshi(isshi);
				yda.setCreateDate(new Date());
//				yda.setUpdateDate(new Date());
				
				System.out.println("插入待审核数据,att_month:"+filenamess+",office_id:"+office.getId()+",office_name:"+office.getName());
				
				
				YdYeukaoqinAll reInfo = ydYeukaoqinAllDao.isinsertShenhe(filenamess,office.getId());
				
				if(reInfo==null){
					ydYeukaoqinAllDao.insertShenheInfo(yda);
				}
				
				System.out.println("插入完毕!");
			}
			
			re = 0;
//		}catch(Exception e){
//			e.printStackTrace();
//		}贺宜文
		 
		return re;
	}
	
	
	
}