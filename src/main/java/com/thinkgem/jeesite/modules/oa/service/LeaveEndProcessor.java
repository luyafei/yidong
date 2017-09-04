package com.thinkgem.jeesite.modules.oa.service;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.common.constant.AttendanceStatus;
import com.thinkgem.jeesite.common.constant.AuditStatusEnum;
import com.thinkgem.jeesite.common.constant.LeaveTypeEnum;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.oa.dao.LeaveDao;
import com.thinkgem.jeesite.modules.oa.entity.Leave;
import com.thinkgem.jeesite.modules.yd.service.IDayAttendanceService;
import com.thinkgem.jeesite.modules.yuekaoqin.dao.AttendanceDayDao;
import com.thinkgem.jeesite.modules.yuekaoqin.entity.AttendanceDay;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 销假后处理器
 * @author liuj
 */
@Service
@Transactional
public class LeaveEndProcessor implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(LeaveEndProcessor.class);

	private final static String ACT_KEY = "deptLeaderPass";//部门领导审核结果key

	@Autowired
	private LeaveDao leaveDao;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private AttendanceDayDao yuekaoqinDao;

	@Autowired
	private IDayAttendanceService attendanceService;


	@Override
	public void notify(DelegateExecution execution) throws Exception {

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
				processInstanceId(execution.getProcessInstanceId()).singleResult();
		Map vars = execution.getVariables();
		if (null != vars && vars.containsKey(ACT_KEY) && !Strings.isNullOrEmpty(processInstance.getBusinessKey())){
			LOGGER.info("修改请假审核状态：{}", vars.toString());
			Leave qLeave = new Leave();
			qLeave.setProcessInstanceId(processInstance.getId());
			Leave leave1 = leaveDao.getLeaveByProcessInstanceId(qLeave);
			Boolean deptLeaderPass = (Boolean) vars.get(ACT_KEY);
			Leave leave = new Leave();
			leave.setId(processInstance.getBusinessKey());
			if (deptLeaderPass){
				leave.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_YEAS.getStatus());
				//根据请假数据的 开始时间和解释时间查询出 修改
				List<Date> ldata = DateUtils.getDatesBetweenTwoDate(leave1.getStartTime(),leave1.getEndTime());
				for (Date d : ldata){
					AttendanceDay yuekaoqin = new AttendanceDay();
					yuekaoqin.setDate(d);
					yuekaoqin.setUid(leave1.getUser().getNo());
					yuekaoqin.setName(leave1.getUser().getName());
					yuekaoqin.setDeptId(leave1.getUser().getOffice().getId());
					yuekaoqin.setUpdateDate(new Date());
					yuekaoqin.setCreateDate(new Date());
					if(AttendanceStatus.OVER_TIME.getStatus().equals(leave1.getLeaveType())){
						yuekaoqin.setStatus(AttendanceStatus.OVER_TIME.getStatus());
					}else {
						yuekaoqin.setStatus(AttendanceStatus.NORMAL.getStatus());
					}
					attendanceService.saveOrUpdate(yuekaoqin);
				}



			}else{
				leave.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_NO.getStatus());
			}
			leaveDao.updateAutitStatusById(leave);
		}
	}
}
