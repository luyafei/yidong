package com.thinkgem.jeesite.common.constant;

/**
 * Created by luyafei on 2016/8/7.
 */
public enum AuditStatusEnum {

    AUDIT_STATUS_YEAS("1","通过"),
    AUDIT_STATUS_NO("0","不通过"),
    AUDIT_STATUS_PASSING("2","审核中"),
    AUDIT_STATUS_NOT_AUDITED("0","未审核");


    private String status;

    private String def;


    AuditStatusEnum(String status,String def){
        this.status = status;

        this.def = def;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
