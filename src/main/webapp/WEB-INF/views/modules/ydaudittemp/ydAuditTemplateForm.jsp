<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移动审核模板管理</title>
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
	<li><a href="${ctx}/ydaudittemp/ydAuditTemplate/">移动审核模板列表</a></li>
	<li class="active"><a href="${ctx}/ydaudittemp/ydAuditTemplate/form?id=${ydAuditTemplate.id}">移动审核模板<shiro:hasPermission name="ydaudittemp:ydAuditTemplate:edit">${not empty ydAuditTemplate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ydaudittemp:ydAuditTemplate:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="ydAuditTemplate" action="${ctx}/ydaudittemp/ydAuditTemplate/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>

	<div class="control-group">
		<label class="control-label">审核人：</label>
		<div class="controls">
			<sys:treeselect id="auditUserLoginname" name="auditUserLoginname" value="${ydAuditTemplate.auditUserLoginname}" labelName="${ydAuditTemplate.auditUserName}" labelValue="${ydAuditTemplate.auditUserName}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">部门：</label>
		<div class="controls">
			<sys:treeselect id="dept" name="dept" value="${ydAuditTemplate.dept}" labelName="${ydAuditTemplate.deptName}" labelValue="${ydAuditTemplate.deptName}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">审批类型：</label>
		<div class="controls">
			<form:select path="businessType" class="input-xlarge ">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('audit_yd_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">审核级别：</label>
		<div class="controls">
			<form:input path="auditLevel" htmlEscape="false" maxlength="100" class="input-xlarge "/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">总审核级别：</label>
		<div class="controls">
			<form:input path="countLevel" htmlEscape="false" maxlength="100" class="input-xlarge "/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">申请人角色:</label>
		<div class="controls">
			<form:select path="role" class="input-xlarge">
				<form:option value="" label="请选择"/>
				<form:options  items="${roleList}"  itemLabel="name" itemValue="id" htmlEscape="false"/>
			</form:select>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="ydaudittemp:ydAuditTemplate:edit"></shiro:hasPermission>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>