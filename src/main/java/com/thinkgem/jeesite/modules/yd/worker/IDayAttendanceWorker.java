package com.thinkgem.jeesite.modules.yd.worker;

/**
 * Created by luyafei on 2016/8/13.
 */
public interface IDayAttendanceWorker {


    /**
     * 每天生成考勤记录
     */
    public void generateDaily();

    /**
     * 导入请假记录
     */

    public void importLeave();
}
