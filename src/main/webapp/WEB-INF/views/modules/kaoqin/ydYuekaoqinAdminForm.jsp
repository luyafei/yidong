<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月考勤记录管理</title>
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
		<li><a href="${ctx}/kaoqin/ydYuekaoqinAdmin/">月考勤记录列表</a></li>
		<li class="active"><a href="${ctx}/kaoqin/ydYuekaoqinAdmin/form?id=${ydYuekaoqinAdmin.id}">月考勤记录<shiro:hasPermission name="kaoqin:ydYuekaoqinAdmin:edit">${not empty ydYuekaoqinAdmin.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="kaoqin:ydYuekaoqinAdmin:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ydYuekaoqinAdmin" action="${ctx}/kaoqin/ydYuekaoqinAdmin/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">员工编号：</label>
			<div class="controls">
				<form:input path="uid" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<sys:treeselect id="deptId" name="deptId" value="${ydYuekaoqinAdmin.deptId}" labelName="${ydYuekaoqinAdmin.deptName}" labelValue="${ydYuekaoqinAdmin.deptName}"
					title="部门" url="/sys/office/treeData?type=2&renzikaoqin=1" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<sys:treeselect id="uno" name="uno" value="${ydYuekaoqinAdmin.uno}" labelName="${ydYuekaoqinAdmin.name}" labelValue="${ydYuekaoqinAdmin.name}"
								title="用户" url="/sys/office/treeData?type=3&renzikaoqin=1" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考勤日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydYuekaoqinAdmin.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考勤状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yuekaoqinadmin')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="kaoqin:ydYuekaoqinAdmin:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>