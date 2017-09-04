package com.thinkgem.jeesite.modules.yd.service;

import com.thinkgem.jeesite.common.constant.AttendanceStatus;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * 判断是否是迟到或者是早退
 * Created by luyafei on 2016/8/21.
 */
@Service
public class IsTardinessHandler extends AttendanceHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(IsTardinessHandler.class);

    @Value("#{APP_PROP['CMCC_ATT_COME_TIME']}")
    private String comeTime;

    @Value("#{APP_PROP['CMCC_ATT_GO_TIME']}")
    private String goTime;

    @Override
    public String handleRequest(AttendanceDay attendanceDay) {
        LOGGER.info("判断{}是否迟到或者是早退！",attendanceDay.getName());
        String ret = null;
        PunchcardRecord query = new PunchcardRecord();
        query.setuId(attendanceDay.getUid());
        query.setAttendanceDate(attendanceDay.getDate());
        PunchcardRecord record = punchcardRecordDao.queryPunchcardRecordByUIdAndDate(query);
        Assert.notNull(record.getCometime(),"上班时间不能是空！");
        Assert.notNull(record.getGotime(),"下班时间不能是空！");
        Date come = DateUtils.parseDate(DateUtils.formatDate(record.getCometime(), "yyyy-MM-dd " + comeTime));
        Date go = DateUtils.parseDate(DateUtils.formatDate(record.getCometime(), "yyyy-MM-dd " + goTime));
        if (DateUtils.getDistanceOfTwoDate2(come,record.getCometime()) > 0){
            LOGGER.info("{},迟到日期{}",attendanceDay.getName(), DateUtils.formatDate(attendanceDay.getDate()));
            attendanceDay.setStatus(AttendanceStatus.LATE.getStatus());
            dayAttendanceService.isDoExistenceSave(attendanceDay);
            ret = "人员:%s,日期:%s,迟到!";
            ret = String.format(ret,attendanceDay.getName(),
                    DateUtils.formatDate(attendanceDay.getDate()));
        }else if (DateUtils.getDistanceOfTwoDate2(go,record.getGotime()) < 0){
            LOGGER.info("{},早退日期{}",attendanceDay.getName(), DateUtils.formatDate(attendanceDay.getDate()));
            attendanceDay.setStatus(AttendanceStatus.LEAVE_EARLY.getStatus());
            dayAttendanceService.isDoExistenceSave(attendanceDay);
            ret = "人员:%s,日期:%s,早退!";
            ret = String.format(ret,attendanceDay.getName(),
                    DateUtils.formatDate(attendanceDay.getDate()));
        }

        return ret;

    }
}
