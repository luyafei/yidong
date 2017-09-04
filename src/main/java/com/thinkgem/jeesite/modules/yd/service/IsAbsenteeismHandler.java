package com.thinkgem.jeesite.modules.yd.service;

import com.thinkgem.jeesite.common.constant.AttendanceStatus;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.yd.dao.PunchcardRecordDao;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 判断是否旷工
 * Created by luyafei on 2016/8/21.
 */
@Service
public class IsAbsenteeismHandler extends AttendanceHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(IsAbsenteeismHandler.class);

    @Override
    public String handleRequest(AttendanceDay attendanceDay) {
        LOGGER.info("判断{},是否旷工！", attendanceDay.getName());
        String ret = null;
        PunchcardRecord query = new PunchcardRecord();
        query.setuId(attendanceDay.getUid());
        query.setAttendanceDate(attendanceDay.getDate());
        PunchcardRecord record = punchcardRecordDao.queryPunchcardRecordByUIdAndDate(query);
        //获取打卡时间
        if (record == null || record.getCometime() == null || record.getGotime() == null) {
            LOGGER.info("{},旷工日期{}", attendanceDay.getName(), DateUtils.formatDate(attendanceDay.getDate()));
            attendanceDay.setStatus(AttendanceStatus.ABS_ENTEEISM.getStatus());
            dayAttendanceService.isDoExistenceSave(attendanceDay);
            ret = "人员:%s,日期:%s,矿工";
            ret = String.format(ret,attendanceDay.getName(),
                    DateUtils.formatDate(attendanceDay.getDate()));
        }
        return ret;
    }
}
