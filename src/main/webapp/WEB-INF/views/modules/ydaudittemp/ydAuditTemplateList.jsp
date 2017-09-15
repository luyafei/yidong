<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移动审核模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ydaudittemp/ydAuditTemplate/">移动审核模板列表</a></li>
		<shiro:hasPermission name="ydaudittemp:ydAuditTemplate:edit">
		</shiro:hasPermission>
		<li><a href="${ctx}/ydaudittemp/ydAuditTemplate/form">移动审核模板添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="ydAuditTemplate" action="${ctx}/ydaudittemp/ydAuditTemplate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>审批类型：</label>
				<form:select path="businessType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_yd_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>部门：</label>
				<sys:treeselect id="dept" name="dept" value="${ydAuditTemplate.dept}" labelName="ydAuditTemplate.deptName" labelValue="${ydAuditTemplate.deptName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>审批类型</th>
				<th>审核级别</th>
				<th>审核人</th>
				<th>部门名称</th>
				<th>总审核级别</th>
				<shiro:hasPermission name="ydaudittemp:ydAuditTemplate:edit"></shiro:hasPermission>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydAuditTemplate">
			<tr>
				<td><a href="${ctx}/ydaudittemp/ydAuditTemplate/form?id=${ydAuditTemplate.id}">
					${fns:getDictLabel(ydAuditTemplate.businessType, 'audit_yd_type', '')}
				</a></td>
				<td>
					${ydAuditTemplate.auditLevel}
				</td>
				<td>
					${ydAuditTemplate.auditUserName}
				</td>
				<td>
					${ydAuditTemplate.deptName}
				</td>
				<td>
					${ydAuditTemplate.countLevel}
				</td>
				<shiro:hasPermission name="ydaudittemp:ydAuditTemplate:edit"></shiro:hasPermission><td>
    				<a href="${ctx}/ydaudittemp/ydAuditTemplate/form?id=${ydAuditTemplate.id}">修改</a>
					<a href="${ctx}/ydaudittemp/ydAuditTemplate/newform?id=${ydAuditTemplate.id}">新增</a>
					<a href="${ctx}/ydaudittemp/ydAuditTemplate/delete?id=${ydAuditTemplate.id}" onclick="return confirmx('确认要删除该移动审核模板吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>