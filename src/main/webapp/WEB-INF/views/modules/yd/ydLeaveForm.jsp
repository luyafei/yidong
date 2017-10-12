<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异常申请管理</title>
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
<body>
	<ul class="nav nav-tabs">
		<%--<li class="active"><a href="${ctx}/yd/ydLeave/form?id=${ydLeave.id}">异常申请<shiro:hasPermission name="yd:ydLeave:edit">${not empty ydLeave.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="yd:ydLeave:edit">查看</shiro:lacksPermission></a></li>--%>
		<li><a href="${ctx}/yd/ydLeave/auditlist">待审核列表</a></li>
		<li><a href="${ctx}/yd/ydLeave/list">申请列表</a></li>
		<li class="active"><a href="${ctx}/yd/ydLeave/auditlist">申请异常</a></li>

	</ul><br/>
	<form:form id="inputForm" modelAttribute="ydLeave" action="${ctx}/yd/ydLeave/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<!-- 员工登录名称-->
		<form:hidden path="erpNo" value="${ydLeave.erpNo}"/>
		<!-- 部门id-->
		<form:hidden path="officeId" value="${ydLeave.officeId}"/>
		<!-- 开始级别，没有指定人的是0 如果是指定人的 是 1-->
		<form:hidden path="auditLevel" value="1"/>
		<!-- 保存的时候直接是 审核中 -->
		<form:hidden path="auditStatus" value="auditing"/>
		<!-- 审核类型，每种审核会定义一种类型 在表中用部门区分 不同部门的审核流程 -->
		<form:hidden path="auditType" value="${auditType}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申请人：</label>
			<div class="controls">
					<%--<form:input readonly="readonly" path="erpName" htmlEscape="false" maxlength="50" class="input-xlarge "/>--%>
				<input name="erpName" type="text" readonly="readonly" maxlength="20"
					   value="${ydLeave.erpName}"/>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">部门名称：</label>
			<div class="controls">
				<input name="officeName" type="text" readonly="readonly" maxlength="20"
					   value="${ydLeave.officeName}"/>
					<%--<form:input readonly="readonly" path="officeIdName"  htmlEscape="false" maxlength="50" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假类型：</label>
			<div class="controls">
				<form:select path="leaveType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydLeave.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydLeave.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:select path="auditUserNo" class="input-xxlarge">
					<form:options items="${templateList}" itemLabel="auditUserName" itemValue="auditUserLoginname" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<shiro:hasPermission name="yd:ydLeave:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>