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

		function auditNo(){

			var path = "${ctx}/yd/ydLeave/audit?auditStatus=no";
			$('#inputForm').attr("action", path).submit();
		}

		function auditPass(){
			var path = "${ctx}/yd/ydLeave/audit?auditStatus=pass";
			$('#inputForm').attr("action", path).submit();
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">

		<%--<li><a href="${ctx}/yd/ydOvertime/">申请加班列表</a></li>
		<li class="active"><a href="${ctx}/yd/ydOvertime/form?id=${ydOvertime.id}">申请加班<shiro:hasPermission name="yd:ydOvertime:edit">${not empty ydOvertime.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="yd:ydOvertime:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ydLeave"  method="post" class="form-horizontal">
		<form:hidden path="id"/>
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
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydLeave.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ydLeave.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<%--<shiro:hasPermission name="yd:ydLeave:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
				<input id="auditPass1" onclick="auditPass()" class="btn btn-primary" type="button" value="同 意"/>
				<input id="auditNo1"  onclick="auditNo()" class="btn btn-primary" type="button" value="驳 回"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>