package com.thinkgem.jeesite.modules.yd.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * Created by luyafei on 2016/9/17.
 */
public class LeaveImport {

    private String userNum;

    private String userName;

    private String leaveType;

    private String startDate;

    private String endDate;

    private String auditState;

    @ExcelField(title = "员工编号", align = 2, sort = 1)
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @ExcelField(title = "员工姓名", align = 2, sort = 2)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ExcelField(title = "休假类型", align = 2, sort = 3)
    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @ExcelField(title = "休假开始日期", fieldType = String.class, align = 2, sort = 4)
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @ExcelField(title = "休假结束日期", fieldType = String.class, align = 2, sort = 5)
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @ExcelField(title = "审批环节", align = 2, sort = 6)
    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }
}
