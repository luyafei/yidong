/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ydaudittemp.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ydaudittemp.entity.YdAuditTemplate;

/**
 * 移动审核模板DAO接口
 * @author lyf
 * @version 2017-09-09
 */
@MyBatisDao
public interface YdAuditTemplateDao extends CrudDao<YdAuditTemplate> {
	
}