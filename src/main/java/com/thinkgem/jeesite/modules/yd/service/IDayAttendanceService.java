package com.thinkgem.jeesite.modules.yd.service;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by luyafei on 2016/8/13.
 * 天考勤
 */
public interface IDayAttendanceService {


    public void generatRecord_NEW(Date date);
    /**
     * 生成天考勤记录
     */
    public void generatRecord();

    /**
     * 当数据不存在的时候才新增当天考勤记录 uid + 日期
     *
     * @param attendanceDay
     */
    public void isDoExistenceSave(AttendanceDay attendanceDay);

    /**
     * 当数据存在的时候更新记录，如果数据不存在的时候则新增数据 uid + 日期
     */

    public void saveOrUpdate(AttendanceDay attendanceDay);

    public void createAttendanceDayByDate(Date beginDate, Date endDate,User user,String attStatus,Double diff) throws ParseException;
}
