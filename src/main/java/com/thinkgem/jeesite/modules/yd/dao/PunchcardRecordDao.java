/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yd.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;

/**
 * ydDAO接口
 * @author yd
 * @version 2016-07-28
 */
@MyBatisDao
public interface PunchcardRecordDao extends CrudDao<PunchcardRecord> {

    public PunchcardRecord queryPunchcardRecordByUIdAndDate(PunchcardRecord record);

    public void updateById(PunchcardRecord record);
	
}