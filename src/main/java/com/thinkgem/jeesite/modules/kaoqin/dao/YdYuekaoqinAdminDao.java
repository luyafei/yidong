/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kaoqin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.kaoqin.entity.YdYuekaoqinAdmin;

/**
 * 月考勤记录 增删改查DAO接口
 * @author hyw
 * @version 2017-09-17
 */
@MyBatisDao
public interface YdYuekaoqinAdminDao extends CrudDao<YdYuekaoqinAdmin> {
	
}