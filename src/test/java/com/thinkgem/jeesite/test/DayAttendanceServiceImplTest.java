package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.yd.service.AttendanceHandlerInvoker;
import com.thinkgem.jeesite.modules.yd.service.DayAttendanceServiceImpl;
import com.thinkgem.jeesite.modules.yd.worker.DayAttendanceWorker;
import com.thinkgem.jeesite.modules.yd.worker.IDayAttendanceWorker;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by luyafei on 2016/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class DayAttendanceServiceImplTest {

    @Autowired
    private DayAttendanceServiceImpl dayAttendanceService;


    @org.junit.Test
    public void generatRecord_NEWTest (){
       /* List<Date> dates = DateUtils.getDatesBe                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                tweenTwoDate
                (DateUtils.getFirstDayOfMonth(-1), DateUtils.getLastDayOfMonth(-1));

        for (Date date : dates){
            dayAttendanceService.generatRecord_NEW(date);
        }*/

    }

    @org.junit.Test
    public void importLeave (){
      //  DayAttendanceWorker

    }


}
