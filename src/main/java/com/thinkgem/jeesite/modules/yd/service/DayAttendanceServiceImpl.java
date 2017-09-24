package com.thinkgem.jeesite.modules.yd.service;


import com.thinkgem.jeesite.common.constant.AttendanceStatus;
import com.thinkgem.jeesite.common.constant.AuditStatusEnum;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.oa.dao.LeaveDao;
import com.thinkgem.jeesite.modules.oa.entity.Leave;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.yd.dao.PunchcardRecordDao;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;
import com.thinkgem.jeesite.modules.yuekaoqin.dao.AttendanceDayDao;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import com.thinkgem.jeesite.modules.yuekaoqinall.service.YdYeukaoqinAllService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * Created by luyafei on 2016/8/13.
 */
@Service
public class DayAttendanceServiceImpl implements IDayAttendanceService {


    private Logger logger = LoggerFactory.getLogger(DayAttendanceServiceImpl.class);

    @Autowired
    private AttendanceHandlerInvoker handlerWrapper;

    @Value("#{APP_PROP['CMCC_WEEK_DAY']}")
    private String weekDay;

    @Value("#{APP_PROP['CMCC_HOLI_DAY']}")
    private String Holiday;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PunchcardRecordDao punchcardRecordDao;

    @Autowired
    private AttendanceDayDao attendanceDayDao;

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private YdYeukaoqinAllService yeukaoqinAllService;


    @Override
    public void isDoExistenceSave(AttendanceDay attendanceDay) {

        List list = attendanceDayDao.findList(this.getQueryAttendanceDay(attendanceDay));
        if (null == list || list.size() == 0){
            attendanceDayDao.insert(attendanceDay);
        }
    }

    @Override
    public void saveOrUpdate(AttendanceDay attendanceDay) {
        List list = attendanceDayDao.findList(this.getQueryAttendanceDay(attendanceDay));
        if (null == list || list.size() == 0){
            attendanceDayDao.insert(attendanceDay);
        }else {
            AttendanceDay updataAttendanceDay = (AttendanceDay) list.get(0);
            updataAttendanceDay.setStatus(attendanceDay.getStatus());
            attendanceDayDao.updateStatusById(updataAttendanceDay);
        }
    }

    private AttendanceDay getQueryAttendanceDay(AttendanceDay attendanceDay){
        Assert.notNull(attendanceDay.getUid(),"uid不能是空！");
        Assert.notNull(attendanceDay.getDate(),"考勤日期不能是空！");
        AttendanceDay qattend = new AttendanceDay();
        qattend.setUid(attendanceDay.getUid());
        qattend.setDate(attendanceDay.getDate());
        //qattend.setStatus(attendanceDay.getStatus());
        return qattend;
    }

    public void generatRecord_NEW(Date date){
        String sdate = DateUtils.formatDate(date, "yyyy-MM-dd");
        logger.info("开始生成人员{}考勤记录", sdate);
        User queryUser = new User();
        List<User> users = userDao.findAllList(queryUser);
        if (users.isEmpty() || users.size() <= 0){
            logger.info("考勤人员为空！");
            return;
        }
        for (User user : users) {
            AttendanceDay attendanceDay = new AttendanceDay();
            attendanceDay.setIsNewRecord(true);
            attendanceDay.preInsert();
            attendanceDay.setUpdatetime(new Date());
            attendanceDay.setRiqi(sdate);
            attendanceDay.setDeptId(user.getOffice().getId());
            attendanceDay.setMonth(DateUtils.formatDate(date,"yyyyMM"));
            attendanceDay.setName(user.getName());
            attendanceDay.setDate(date);
            attendanceDay.setUid(user.getNo());
            handlerWrapper.invoker(attendanceDay);
        }
    }

    /**
     * 正常上班考勤时间
     */
    @Override
    public void generatRecord() {
        logger.info("开始生成人员{}考勤记录",DateUtils.getDate());
        User queryUser = new User();
        List<User> users = userDao.findAllList(queryUser);
        if (users.isEmpty() || users.size() <= 0){
            logger.info("考勤人员为空！");
            return;
        }
        for (User user : users){
            AttendanceDay attendanceDay = new AttendanceDay();
            attendanceDay.preInsert();
            attendanceDay.setUpdatetime(new Date());
            attendanceDay.setRiqi(DateUtils.getDate());
            attendanceDay.setDeptId(user.getOffice().getId());
            attendanceDay.setMonth(DateUtils.getDate("yyyyMM"));
            attendanceDay.setName(user.getName());
            attendanceDay.setDate(new Date());
            attendanceDay.setUid(user.getNo());

            Leave queryLeave = new Leave();
            queryLeave.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_YEAS.getStatus());
            queryLeave.setCreateDate(new Date());
            Leave leave =leaveDao.getLeaveBydate(queryLeave);
            if (leave != null){
                attendanceDay.setStatus(leave.getLeaveType());
            }else {
                if (isWorkingDay(DateUtils.getDate())){
                    logger.info("工作{}考勤记录",DateUtils.getDate());
                    PunchcardRecord punchcardRecord = new PunchcardRecord();
                    punchcardRecord.setAttendanceDate(new Date());
                    punchcardRecord.setuId(user.getNo());
                    PunchcardRecord punchcardRecord1 = punchcardRecordDao.get(punchcardRecord);
                    //查请假
                    if (null != punchcardRecord1){
                        attendanceDay.setStatus(AttendanceStatus.NORMAL.getStatus());
                        attendanceDayDao.insert(attendanceDay);
                    }else {
                        logger.info("{},{}没有考勤记录",user.getLoginName(),DateUtils.getDate());
                        attendanceDay.setStatus(AttendanceStatus.ABSENC.getStatus());

                    }
                }else {
                    //加班记录
                    logger.info("非工作{}考勤记录",DateUtils.getDate());
                    attendanceDay.setStatus(AttendanceStatus.NORMAL.getStatus());

                }
            }

            attendanceDayDao.insert(attendanceDay);
        }
        logger.info("生成人员{}考勤记录结束",DateUtils.getDate());

    }

    /**
     * 根据时间段生成相应的天考勤记录
     * beginDate 开始时间
     * endDate 结束时间
     * user 考勤人员信息
     * attStatus 考勤状态
     */
    public void createAttendanceDayByDate(Date beginDate, Date endDate,User user,String attStatus, Double duration) throws ParseException {

        //根据请假数据的 开始时间和结束时间查询出 修改
        List<Date> ldata = DateUtils.getDatesBetweenTwoDate(beginDate,endDate);
        for (Date d : ldata) {
            String month = DateUtils.formatDate(d, "yyyyMM");
            yeukaoqinAllService.isinsertShenhe(month,user);
            if (yeukaoqinAllService.isAudit(month,user.getOffice().getId())){
                AttendanceDay yuekaoqin = new AttendanceDay();
                yuekaoqin.setDate(d);
                yuekaoqin.setUid(user.getNo());
                yuekaoqin.setMonth(month);
                yuekaoqin.setAreaId(user.getOffice().getArea().getId());
                yuekaoqin.setName(user.getName());
                yuekaoqin.setDeptId(user.getOffice().getId());
                yuekaoqin.setOfficeName(user.getOffice().getName());
                yuekaoqin.setUpdateDate(new Date());
                yuekaoqin.setCreateDate(new Date());
                yuekaoqin.setStatus(attStatus);
                yuekaoqin.setDuration((double) TimeUtils.getDiffHour(beginDate, endDate));
                this.saveOrUpdate(yuekaoqin);
            }
        }


    }

    /**
     * true 是工作日
     * false 非工作日
     * @return
     */
    private boolean isWorkingDay(String day){

        boolean flag = true;
        String week = DateUtils.getWeek(day);

        if (Holiday.contains(day) || "星期六".equals(week) || "星期日".equals(week)){
            flag = false;
        }

        if (weekDay.contains(day)){
            flag = true;
        }

        return flag;
    }



}
