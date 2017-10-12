package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.modules.yd.service.DayAttendanceServiceImpl;
import com.thinkgem.jeesite.modules.ydaudittemp.service.YdAuditTemplateService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by luyafei on 2016/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class YdAuitTemplateServiceImplTest {

    @Autowired
    private YdAuditTemplateService templateService;

    @org.junit.Test
    public void testImportAuditTemplate() throws InvalidFormatException, InstantiationException, IllegalAccessException, IOException {

        templateService.importAuditTemplate("");
    }





}
