package com.thinkgem.jeesite.modules.yd.service;

import com.thinkgem.jeesite.modules.yd.dao.PunchcardRecordDao;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by luyafei on 2016/8/21.
 */

public abstract class AttendanceHandler {

    @Autowired
    protected IDayAttendanceService dayAttendanceService;

    @Autowired
    protected PunchcardRecordDao punchcardRecordDao;




    public abstract String handleRequest(AttendanceDay attendanceDay);




}
