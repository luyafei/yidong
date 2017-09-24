/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqinall.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.yd.entity.YDConstant;
import org.apache.ibatis.annotations.Param;
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
	
	public YdYeukaoqinAll isDaoru(YdYeukaoqinAll ydYeukaoqinAll){
		return ydYeukaoqinAllDao.getYuekaoqinAllByEntity(ydYeukaoqinAll);
	}
	
	
	@Transactional
	public int importOver (User user , List<Yuekaoqin31Import> list , StringBuilder failureMsg, RedirectAttributes redirectAttributes) throws Exception{
		int re = -1; 
		try{
			int successNum = 0;
			int failureNum = 0;
			StringBuffer shibaiNames = new StringBuffer();
			String months = list.get(0).getMonth();
			
			Area area = user.getOffice().getArea();
			System.out.println(area.getId());
			
			for (Yuekaoqin31Import yuekaoqin31 : list){
				AttendanceDay attendanceDay = new AttendanceDay();
				
				
				
				System.out.println("list:"+list.size());
				System.out.println("yuekaoqin31:"+yuekaoqin31.getName());
				
				
				User u = new User();
				System.out.println("");
				u.setNo(yuekaoqin31.getUid());
				User u2 = userDao.get(u);
				if(u2==null || u2.getOffice().getId()==null || u2.getOffice().getId().equals("")){
					if(yuekaoqin31.getUid()!=null && !yuekaoqin31.getUid().equals("")){
						failureNum++;
						shibaiNames.append(yuekaoqin31.getName()+",");
					}
					continue;
				}else{
					successNum++;
				}
				attendanceDay.setUid(yuekaoqin31.getUid());
				attendanceDay.setDeptId(u2.getOffice().getId());
				attendanceDay.setName(yuekaoqin31.getName());
				attendanceDay.setAreaId(area.getId());
				attendanceDay.setMonth(months);
				
				attendanceDay.setCreateDate(new Date());
				attendanceDay.setUpdateDate(new Date());
				
				for(int i=1;i<=31;i++){
					
					try {
					
						Method method = null;
						if(i<10){
							method = Yuekaoqin31Import.class.getMethod("getRi0"+i, null);
						}else{
							method = Yuekaoqin31Import.class.getMethod("getRi"+i, null);
						}
						String riValue = method.invoke(yuekaoqin31).toString().trim();
						if(riValue==null || riValue.equals("")){
							continue;
						}
						String dist_value = DictUtils.getDictValue( riValue , "AttendanceStatus", "");
						if(i<10){
							attendanceDay.setDate(new SimpleDateFormat("yyyyMMdd").parse(months+"0"+i));
						}else{
							attendanceDay.setDate(new SimpleDateFormat("yyyyMMdd").parse(months+i));
						}
						attendanceDay.setStatus(dist_value);
						//判断此用户是否存在这天的考勤状态
						AttendanceDay isuserinfo = ydYeukaoqinAllDao.isUserInfo(attendanceDay.getUid(),attendanceDay.getDate());
	//					System.out.println("riValue:"+riValue);
	//					System.out.println("dist_value:"+dist_value);
						if(isuserinfo==null){
	//						attendanceService.saveOrUpdate(attendanceDay);
							attendanceDayDao.insert(attendanceDay);
						}else if(!riValue.equals("正常")){
							//此次状态不是正常  则进行更新
	//						System.out.println("此次状态不是正常  则进行更新:"+riValue);
							ydYeukaoqinAllDao.updateYuekaoqinStatus(isuserinfo.getId(), dist_value);
						}
					
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				
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
				yda.setAttMonth(months);
				yda.setAreaId(area.getId());
				yda.setOfficeId(office.getId());
				yda.setOfficeName(office.getName());
				yda.setIngStatus(0);
				yda.setAuditStatus("未提交");
				yda.setIsshi(isshi);
				yda.setCreateDate(new Date());
//				yda.setUpdateDate(new Date());
				
				System.out.println("插入待审核数据,att_month:"+months+",office_id:"+office.getId()+",office_name:"+office.getName());
				
				
				YdYeukaoqinAll reInfo = ydYeukaoqinAllDao.isinsertShenhe(months,office.getId());
				
				if(reInfo==null){
					ydYeukaoqinAllDao.insertShenheInfo(yda);
				}
				
				System.out.println("插入完毕!");
				
			}
			
			failureMsg.insert(0, "，成功条数:"+successNum +",失败条数:"+failureNum+ (shibaiNames.length()>0?"失败人:"+shibaiNames:"") );
			
			re = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return re;
	}


	/**
	 * lyf
	 * 判断 月考勤是否存在 如果存在不操作，不存在新增一条
	 * @param months 考勤时间 例如：201709
	 * @param user 考勤人
	 */
	public void isinsertShenhe(String months ,User user){

		YdYeukaoqinAll yda = new YdYeukaoqinAll();
		yda.setAttMonth(months);
		yda.setAreaId(user.getOffice().getArea().getId());
		yda.setOfficeId(user.getOffice().getId());
		yda.setOfficeName(user.getOffice().getName());
		yda.setIngStatus(0);
		yda.setAuditStatus("未提交");
		String isshi = "false";
		if("承德市".equals(user.getOffice().getArea().getName())){
			isshi = "true";
		}
		yda.setIsshi(isshi);
		yda.setCreateDate(new Date());
		YdYeukaoqinAll reInfo = ydYeukaoqinAllDao.isinsertShenhe(months,user.getOffice().getId());
		if(reInfo==null){
			ydYeukaoqinAllDao.insertShenheInfo(yda);
		}

	}
	/**
	 * lyf
	 * true 能变动 yuekaoqin false 审核中 或审核通过 都能操作yuekaoqin
	 */
	public Boolean isAudit(String months,String offiecId){
		YdYeukaoqinAll reInfo = ydYeukaoqinAllDao.isinsertShenhe(months,offiecId);
		if (reInfo != null && (YDConstant.YUEKAOQINSTATUS_AUDITING.equals(reInfo.getAuditStatus()) ||
				YDConstant.YUEKAOQINSTATUS_AUDITED.equals(reInfo.getAuditStatus()))){
			return false;
		}
		return true;
	}


}