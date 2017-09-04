package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.modules.yd.service.AttendanceHandlerInvoker;

import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by luyafei on 2016/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class AttendanceHandlerWrapperTest {

    @Autowired
    private AttendanceHandlerInvoker handlerWrapper;

    @org.junit.Test
    public void getAttendanceHandlerTest (){
        AttendanceDay attendanceDay = new AttendanceDay();
        attendanceDay.setUid(1 + "");

        attendanceDay.setDeptId("aadcc8a59a1449ddafd50fb33135fc7d");
        attendanceDay.setName("王丽");
        attendanceDay.setDate(new Date());
        attendanceDay.setMonth("201608");

        attendanceDay.setCreatetime(new Date());
        attendanceDay.setUpdatetime(new Date());
        handlerWrapper.invoker(attendanceDay);
    }
}
