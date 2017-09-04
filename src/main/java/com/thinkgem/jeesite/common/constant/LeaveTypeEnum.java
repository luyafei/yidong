package com.thinkgem.jeesite.common.constant;

/**
 * Created by luyafei on 2016/8/21.
 */
public enum LeaveTypeEnum {



    OVER_TIME("6","加班");


    private String status;

    private String def;


    LeaveTypeEnum(String status,String def){
        this.status = status;

        this.def = def;
    }

    public String getStatus() {
        return status;
    }

    public String getDef() {
        return def;
    }
}
