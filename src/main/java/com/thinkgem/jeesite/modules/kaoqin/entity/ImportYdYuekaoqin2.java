package com.thinkgem.jeesite.modules.kaoqin.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * Created by luyafei on 2017/9/21.
 */
public class ImportYdYuekaoqin2 {

    //工 号	姓 名	性别	部 门	考勤日期	刷卡时段	上班时间	刷卡时间	上班状态	下班时间	刷卡时间	下班状态

    private String userNO;

    private String userName;

    private String userSex;
    private String userDept;
    private String kaoqiriqi;
    private String shuakashiduan;
    private String shanbanshijian;
    private String shukashijian;
    private String shangbanzhuangtai;
    private String xibanshijian;
    private String shuakashijian;
    private String xiabanzhuangtai;


    @ExcelField(title="工 号", fieldType = String.class,align=2, sort=1)
    public String getUserNO() {
        return userNO;
    }

    public void setUserNO(String userNO) {
        this.userNO = userNO;
    }

    @ExcelField(title="姓 名", fieldType = String.class,align=2, sort=2)
    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
    @ExcelField(title="性别", fieldType = String.class,align=2, sort=3)
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
    @ExcelField(title="部 门", fieldType = String.class,align=2, sort=4)
    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }
    @ExcelField(title="考勤日期", align=2, sort=5)
    public String getKaoqiriqi() {
        return kaoqiriqi;
    }

    public void setKaoqiriqi(String kaoqiriqi) {
        this.kaoqiriqi = kaoqiriqi;
    }

    @ExcelField(title="刷卡时段", fieldType = Date.class,align=2, sort=6)
    public String getShuakashiduan() {
        return shuakashiduan;
    }

    public void setShuakashiduan(String shuakashiduan) {
        this.shuakashiduan = shuakashiduan;
    }

    @ExcelField(title="上班时间", fieldType = String.class,align=2, sort=7)
    public String getShanbanshijian() {
        return shanbanshijian;
    }

    public void setShanbanshijian(String shanbanshijian) {
        this.shanbanshijian = shanbanshijian;
    }
    @ExcelField(title="刷卡时间", fieldType = String.class,align=2, sort=8)
    public String getShukashijian() {
        return shukashijian;
    }

    public void setShukashijian(String shukashijian) {
        this.shukashijian = shukashijian;
    }

    @ExcelField(title="上班状态",align=2, sort=9)
    public String getShangbanzhuangtai() {
        return shangbanzhuangtai;
    }

    public void setShangbanzhuangtai(String shangbanzhuangtai) {
        this.shangbanzhuangtai = shangbanzhuangtai;
    }
    @ExcelField(title="下班时间",align=2, sort=10)
    public String getXibanshijian() {
        return xibanshijian;
    }


    public void setXibanshijian(String xibanshijian) {
        this.xibanshijian = xibanshijian;
    }
    @ExcelField(title="刷卡时间", fieldType = String.class,align=2, sort=11)
    public String getShuakashijian() {
        return shuakashijian;
    }

    public void setShuakashijian(String shuakashijian) {
        this.shuakashijian = shuakashijian;
    }
    @ExcelField(title="下班状态",align=2, sort=12)
    public String getXiabanzhuangtai() {
        return xiabanzhuangtai;
    }

    public void setXiabanzhuangtai(String xiabanzhuangtai) {
        this.xiabanzhuangtai = xiabanzhuangtai;
    }
}
