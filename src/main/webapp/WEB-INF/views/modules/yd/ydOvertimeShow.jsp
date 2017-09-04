<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请加班管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<!-- 加班申请页 -->
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/yd/ydOvertime/form/">申请详情</a></li>
		<%--<li><a href="${ctx}/yd/ydOvertime/">申请加班列表</a></li>
		<li class="active"><a href="${ctx}/yd/ydOvertime/form?id=${ydOvertime.id}">申请加班<shiro:hasPermission name="yd:ydOvertime:edit">${not empty ydOvertime.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="yd:ydOvertime:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ydOvertime" action="${ctx}/yd/ydOvertime/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<!-- 员工登录名称-->
		<form:hidden path="erpNo" value="${ydOvertime.erpNo}"/>
		<!-- 部门id-->
		<form:hidden path="officeId" value="${ydOvertime.officeId}"/>
		<!-- 开始级别，没有指定人的是0 如果是指定人的 是 1-->
		<form:hidden path="auditLevel" value="0"/>
		<!-- 保存的时候直接是 审核中 -->
		<form:hidden path="auditStatus" value="passing"/>
		<!-- 审核类型，每种审核会定义一种类型 在表中用部门区分 不同部门的审核流程 -->
		<form:hidden path="auditType" value="overtime_audit"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申请人：</label>
			<div class="controls">
				<%--<form:input readonly="readonly" path="erpName" htmlEscape="false" maxlength="50" class="input-xlarge "/>--%>
				<input name="erpName" type="text" readonly="readonly" maxlength="20"
					   value="${ydOvertime.erpName}"/>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">部门名称：</label>
			<div class="controls">
				<input name="officeName" type="text" readonly="readonly" maxlength="20"
					   value="${ydOvertime.officeName}"/>
				<%--<form:input readonly="readonly" path="officeIdName"  htmlEscape="false" maxlength="50" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydOvertime.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydOvertime.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks"  readonly="readonly" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>