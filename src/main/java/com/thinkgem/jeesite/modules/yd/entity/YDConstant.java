package com.thinkgem.jeesite.modules.yd.entity;

/**
 * Created by luyafei on 2017/9/10.
 */
public class YDConstant {


    //////////////start月考勤类型  请假类型
    public final  static String leave_sbt = "leave_sbt";//上半天
    public final  static String leave_xbt = "leave_xbt";//下半天
    public final  static String leave_qt = "leave_qt";//全天
    public final static String time_jb = "6"; //加班
    /////////////end月考勤类型




    //审核业务类型
    public final static String OVER_TIME_TYPE = "overtime_audit"; //加班审核

    public final static String LEAVE_TYPE = "ydLeave_audit";//请假审核

    //月考勤审核状态

    public final  static String YUEKAOQINSTATUS_AUDITING = "审核中";//审核中
    public final  static String YUEKAOQINSTATUS_AUDITED = "通过";//审核中

}
