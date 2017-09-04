package com.thinkgem.jeesite.modules.yd.worker;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.constant.AttendanceStatus;
import com.thinkgem.jeesite.common.constant.AuditStatusEnum;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.yd.entity.LeaveImport;
import com.thinkgem.jeesite.modules.yd.entity.PunchcardRecord;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by luyafei on 2016/8/13.
 */
@Service
public class DayAttendanceWorker implements IDayAttendanceWorker {

    private Logger logger = LoggerFactory.getLogger(DayAttendanceWorker.class);

    @Autowired
    private IDayAttendanceService dayAttendanceService;

    @Override
    public void generateDaily() {
        List<Date> dates = DateUtils.getDatesBetweenTwoDate
                (DateUtils.getFirstDayOfMonth(-1), DateUtils.getLastDayOfMonth(-1));
        logger.info("开始生成当月考勤记录");
       for (Date date : dates){
           dayAttendanceService.generatRecord_NEW(date);
       }
        logger.info("结束生成当月考勤记录");
    }

    @Override
    public void importLeave() {
        String dirPath = Global.getUserfilesBaseDir();
        //c:\aa\2016-09_请假.xls
        String filePath = dirPath +  DateUtils.formatDate(DateUtils.getFirstDayOfMonth(-1), "yyyy-MM") + "_请假.xls";
        //String filePath = "D:\\2016工作\\移动\\请假模板.xls";
        File file = new File(filePath);
        try {
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<LeaveImport> list = ei.getDataList(LeaveImport.class);
            for (LeaveImport leaveImport : list){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date sdate = df.parse(leaveImport.getStartDate());
                Date edate = df.parse(leaveImport.getEndDate());
                List<Date> ldata = DateUtils.getDatesBetweenTwoDate(sdate,edate);
                for (Date d : ldata){
                    AttendanceDay yuekaoqin = new AttendanceDay();
                    yuekaoqin.setDate(d);
                    yuekaoqin.setUid(leaveImport.getUserNum());
                    yuekaoqin.setName(leaveImport.getUserName());
                   // yuekaoqin.setDeptId(leave1.getUser().getOffice().getId());
                    yuekaoqin.setUpdateDate(new Date());
                    yuekaoqin.setCreateDate(new Date());
                    yuekaoqin.setStatus(AttendanceStatus.NORMAL.getStatus());
                    dayAttendanceService.saveOrUpdate(yuekaoqin);
                }
            }

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
