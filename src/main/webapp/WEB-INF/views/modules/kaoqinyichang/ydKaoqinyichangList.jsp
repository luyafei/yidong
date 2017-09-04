<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异常考勤管理</title>
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
		<li class="active"><a href="${ctx}/kaoqinyichang/ydKaoqinyichang/">异常考勤列表</a></li>
		<shiro:hasPermission name="kaoqinyichang:ydKaoqinyichang:edit"><li><a href="${ctx}/kaoqinyichang/ydKaoqinyichang/form">异常考勤添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ydKaoqinyichang" action="${ctx}/kaoqinyichang/ydKaoqinyichang/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>时间</th>
				<th>状态</th>
				<th>操作</th>
				<shiro:hasPermission name="kaoqinyichang:ydKaoqinyichang:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydKaoqinyichang">
			<tr>
				<td>
					${ydKaoqinyichang.name}
				</td>
				<td>
					${ydKaoqinyichang.date}
				</td>
				<td>
					${ydKaoqinyichang.status}
				</td>
				<td>
    				<a href="${ctx}/kaoqinyichang/ydKaoqinyichang/form?id=${ydKaoqinyichang.id}">修改</a>
					<a href="${ctx}/kaoqinyichang/ydKaoqinyichang/delete?id=${ydKaoqinyichang.id}" onclick="return confirmx('确认要删除该异常考勤吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>