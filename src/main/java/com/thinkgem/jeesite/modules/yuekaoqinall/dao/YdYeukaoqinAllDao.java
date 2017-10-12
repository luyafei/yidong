/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yuekaoqinall.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import com.thinkgem.jeesite.modules.yuekaoqinall.entity.YdYeukaoqinAll;

/**
 * 部门月考勤审核状态DAO接口
 * @author hyw
 * @version 2016-08-14
 */
@MyBatisDao
public interface YdYeukaoqinAllDao extends CrudDao<YdYeukaoqinAll> {
	
	public void updateIngstatusByid(YdYeukaoqinAll ydYeukaoqinAll);
	
	public void insertShenheInfo(YdYeukaoqinAll yda );
	
	public YdYeukaoqinAll isinsertShenhe(@Param("attMonth") String attMonth , @Param("officeId") String office_id );
	
	public AttendanceDay isUserInfo(@Param("uid") String uid , @Param("date") Date date );
	
	public Role getRoleByUId(@Param("uid") String uid);
	
	public YdYeukaoqinAll getYuekaoqinAllByid(@Param("id") String id);
	
	public YdYeukaoqinAll getYuekaoqinAllByEntity(YdYeukaoqinAll ydYeukaoqinAll);
	
	public void updateYuekaoqinStatus(@Param("id") String id , @Param("status") String status);
	
	public String getAreaName(@Param("areaId") String areaId );
	
	
}