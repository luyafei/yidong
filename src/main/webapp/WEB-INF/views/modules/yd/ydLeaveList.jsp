<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异常申请管理</title>
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
		<li ><a href="${ctx}/yd/ydLeave/auditlist">待审核列表</a></li>
		<li class="active"><a href="${ctx}/yd/ydLeave/list">申请列表</a></li>
		<li><a href="${ctx}/yd/ydLeave/form">申请异常</a></li>


		<%--<shiro:hasPermission name="yd:ydLeave:edit"><li><a href="${ctx}/yd/ydLeave/form">异常申请添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="yd:ydLeave:edit"><li><a href="${ctx}/yd/ydLeave/form">审核异常考勤</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="ydLeave" action="${ctx}/yd/ydLeave/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<li><label>创建时间：</label>
			<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${ydLeave.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
			<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${ydLeave.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"></li>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

				<th>申请人账号</th>
				<th>申请人</th>
				<th>部门名称</th>
				<th>审核状态</th>
				<th>更新时间</th>
				<th>操作</th>
				<shiro:hasPermission name="yd:ydLeave:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydLeave">
			<tr>
			<tr>
				<td>
					<a href="${ctx}/yd/ydLeave/show?id=${ydLeave.id}">
						${ydLeave.erpNo}
					</a>
				</td>
				<td>
					${ydLeave.erpName}
				</td>
				<td>
					${ydLeave.officeName}
				</td>
				<td>
					<c:if test="${ydLeave.auditStatus == 'auditing'}">
						审核中
					</c:if>
					<c:if test="${ydLeave.auditStatus == 'pass'}">
						通过
					</c:if>
					<c:if test="${ydLeave.auditStatus == 'no'}">
						驳回
					</c:if>

				</td>

				<td>
					<fmt:formatDate value="${ydLeave.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<a href="${ctx}/yd/ydLeave/show?id=${ydLeave.id}">
							查 看
					</a>
					&nbsp;&nbsp;
					<a href="${ctx}/yd/ydLeave/delete?id=${ydLeave.id}" onclick="return confirmx('确认要删除该异常申请吗？', this.href)">删除</a>
				</td>
				<%--<shiro:hasPermission name="yd:ydLeave:edit"><td>
    				<a href="${ctx}/yd/ydLeave/form?id=${ydLeave.id}">修改</a>
					<a href="${ctx}/yd/ydLeave/delete?id=${ydLeave.id}" onclick="return confirmx('确认要删除该异常申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>