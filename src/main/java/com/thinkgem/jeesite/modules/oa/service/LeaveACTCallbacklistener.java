package com.thinkgem.jeesite.modules.oa.service;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.common.constant.AuditStatusEnum;
import com.thinkgem.jeesite.modules.act.service.ACTCallbacklistener;
import com.thinkgem.jeesite.modules.oa.dao.LeaveDao;
import com.thinkgem.jeesite.modules.oa.entity.Leave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by luyafei on 2016/8/7.
 */
@Service("leaveACTCallbacklistener")
public class LeaveACTCallbacklistener implements ACTCallbacklistener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveACTCallbacklistener.class);

    private final static String ACT_KEY = "deptLeaderPass";//部门领导审核结果key

    @Autowired
    private LeaveDao leaveDao;

    @Override
    public void modifyBussDataBybussId(Map<String, Object> vars, String bussId) {

        if (null != vars && vars.containsKey(ACT_KEY) && !Strings.isNullOrEmpty(bussId)){
            LOGGER.info("修改请假审核状态：{}", vars.toString());
            Boolean deptLeaderPass = (Boolean) vars.get(ACT_KEY);
            Leave leave = new Leave();
            leave.setId(bussId);
            if (deptLeaderPass){
                leave.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_YEAS.getStatus());

            }else{
                leave.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_NO.getStatus());
            }
            leaveDao.updateAutitStatusById(leave);
        }

    }
}
