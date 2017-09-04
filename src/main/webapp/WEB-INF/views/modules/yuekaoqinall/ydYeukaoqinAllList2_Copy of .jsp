<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月考勤总览管理</title>
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
		<li class="active"><a href="${ctx}/yuekaoqinall/ydYeukaoqinAll/">部门月考勤查看</a></li>
		<shiro:hasPermission name="yuekaoqinall:ydYeukaoqinAll:edit"><li><a href="${ctx}/yuekaoqinall/ydYeukaoqinAll/form">月考勤总览添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ydYeukaoqinAll" action="${ctx}/yuekaoqinall/ydYeukaoqinAll/deptkaoqin" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		
			<li><label>月份：</label>
				<form:input path="attMonth" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>部门名称</th>
				<th>月份</th>
				<th>审核状态</th>
				<th>操作</th>
				<%-- <shiro:hasPermission name="kaoqinyichang:ydKaoqinyichang:edit"><th>操作</th></shiro:hasPermission>
				 --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydYeukaoqinAll">
			<tr>
				
				<td>
					${ydYeukaoqinAll.officeName}
				</td>
				<td>
					${ydYeukaoqinAll.attMonth}
				</td>
				<td>
					${ydYeukaoqinAll.lable}
				</td>
				<td>
					<a href="">提交审核</a>&nbsp;&nbsp;
    				<a href="${ctx}/yuekaoqin/attendanceDay?isYue=yes&month=${ydYeukaoqinAll.attMonth}&deptId=${ydYeukaoqinAll.officeId}">详细</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>