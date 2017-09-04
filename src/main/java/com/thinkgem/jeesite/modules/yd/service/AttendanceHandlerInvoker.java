package com.thinkgem.jeesite.modules.yd.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by luyafei on 2016/8/21.
 */
@Service
public class AttendanceHandlerInvoker {

    @Value("#{APP_PROP['CMCC_ATTEDANCE_HANDLER']}")
    private String handlers;


    public List<AttendanceHandler> list;

    @Autowired
    private SpringContextHolder springContextHolder;

    @PostConstruct
    public void init(){

        this.getAttendanceHandlers(getHandlers());

    }

    /**
     * 处理考勤结果
     * @param attendanceDay
     */
    public void invoker(AttendanceDay attendanceDay){

        Assert.notNull(list,"bulidAttendanceHandler list 不能为空!");

        for (AttendanceHandler handler : list){
            if (!Strings.isNullOrEmpty(handler.handleRequest(attendanceDay))){
                break;
            }
        }

    }


    private List<AttendanceHandler> getAttendanceHandlers(String handlers){

        Assert.isTrue(!Strings.isNullOrEmpty(handlers), "handlers不能是空!");

        String[] handler = handlers.split(",");

        if (handler != null && handler.length > 0){
            list = Lists.newLinkedList();
            for (String temp : handler){
                AttendanceHandler handler1 = springContextHolder.getBean(temp);
                list.add(handler1);

            }
        }


        return list;
    }

    public String getHandlers() {
        return handlers;
    }

    public static void main(String[] args) {

        String str = "1,2,3";
        String strs[] = str.split(",");

        List<String> list = Lists.newLinkedList();
        for (String temp : strs){
            list.add(temp);

        }

        System.out.println(list);

        for (String straa : list){

            System.out.println(straa);
        }
    }
}
