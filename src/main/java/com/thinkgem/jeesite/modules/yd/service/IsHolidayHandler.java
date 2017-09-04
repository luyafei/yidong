package com.thinkgem.jeesite.modules.yd.service;

import com.thinkgem.jeesite.common.constant.AttendanceStatus;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.WorkDayUtils;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * 判断是否是节假日(只是正常班)
 * Created by luyafei on 2016/8/21.
 */
@Service
public class IsHolidayHandler extends AttendanceHandler {


    private final static Logger LOGGER = LoggerFactory.getLogger(IsHolidayHandler.class);



    @Override
    public String handleRequest(AttendanceDay attendanceDay) {
        LOGGER.info("判断{},{}是否是假期！",attendanceDay.getName(),DateUtils.formatDate(attendanceDay.getDate()));
        String ret = null;
        WorkDayUtils workDayUtils = new WorkDayUtils();
        Calendar cal = Calendar.getInstance();
        cal.setTime(attendanceDay.getDate());
        if(!workDayUtils.isWorkDay(cal)){

            attendanceDay.setStatus(AttendanceStatus.NORMAL.getStatus());

            LOGGER.info("{},正常日期{}", attendanceDay.getName(), DateUtils.formatDate(attendanceDay.getDate()));
            dayAttendanceService.isDoExistenceSave(attendanceDay);
            ret = "人员:%s,日期:%s,正常!";
            ret = String.format(ret,attendanceDay.getName(),
                    DateUtils.formatDate(attendanceDay.getDate()));
        }
        return ret;
    }


}
