/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.yd.entity.YdLeave;

/**
 * 异常申请DAO接口
 * @author lyf
 * @version 2016-10-29
 */
@MyBatisDao
public interface YdLeaveDao extends CrudDao<YdLeave> {
	
}