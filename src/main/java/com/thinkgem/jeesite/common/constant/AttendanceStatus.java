package com.thinkgem.jeesite.common.constant;

/**
 * Created by luyafei on 2016/8/14.
 */
public enum AttendanceStatus {

    NORMAL("200","正常"),
    ABSENC("402","缺勤"),
    ABS_ENTEEISM("403","旷工"),
    LATE("404","迟到"),
    LEAVE_EARLY("405","早退"),
    OVER_TIME("6","加班");


    private String status;

    private String def;

    AttendanceStatus(String status, String def) {
        this.status = status;
        this.def = def;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }
}
