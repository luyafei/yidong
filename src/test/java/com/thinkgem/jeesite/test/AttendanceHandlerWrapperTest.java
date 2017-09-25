package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.yd.service.AttendanceHandlerInvoker;

import com.thinkgem.jeesite.modules.ydaudittemp.service.YdAuditTemplateService;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import com.thinkgem.jeesite.test.entity.YdTemplate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by luyafei on 2016/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class AttendanceHandlerWrapperTest {


    @Autowired
    private AttendanceHandlerInvoker handlerWrapper;

    @Autowired
    private YdAuditTemplateService ydAuditTemplateService;

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

    @org.junit.Test
    public void testAddYdTemplate() throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {

        ImportExcel ei = new ImportExcel("target/export.xlsx", 0);
        List<YdTemplate> ydTemplates = ei.getDataList(YdTemplate.class);
//
//		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
//			Row row = ei.getRow(i);
//			for (int j = 0; j < ei.getLastCellNum(); j++) {
//				Object val = ei.getCellValue(row, j);
//				System.out.print(val+", ");
//			}
//			System.out.print("\n");
//		}
    }
}
