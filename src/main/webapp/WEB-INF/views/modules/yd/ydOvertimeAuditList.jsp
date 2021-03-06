<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请加班管理</title>
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
<!-- 加班审核页面 -->
<body>
	<ul class="nav nav-tabs">
		<li  class="active"><a href="${ctx}/yd/ydOvertime/auditList">待审列表</a></li>
		<li ><a href="${ctx}/yd/ydOvertime/list">申请加班列表</a></li>
		<li ><a href="${ctx}/yd/ydOvertime/form/">申请加班</a></li>

		<%--<shiro:hasPermission name="yd:ydOvertime:edit"><li><a href="${ctx}/yd/ydOvertime/form">申请加班添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="ydOvertime" action="${ctx}/yd/ydOvertime/auditList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请人名称：</label>
				<sys:treeselect id="erpName" name="erpName" value="${ydOvertime.erpName}" labelName="" labelValue="${ydOvertime.erpNo}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>部门名称：</label>
				<sys:treeselect id="officeName" name="officeName" value="${ydOvertime.officeName}" labelName="" labelValue="${ydOvertime.officeId}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>

			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ydOvertime.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ydOvertime.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
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
				<%--<shiro:hasPermission name="yd:ydOvertime:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydOvertime">
			<tr>
				<td><a href="${ctx}/yd/ydOvertime/form?id=${ydOvertime.id}">
					${ydOvertime.erpNo}
				</a></td>
				<td>
					${ydOvertime.erpName}
				</td>
				<td>
					${ydOvertime.officeName}
				</td>
				<td>
					<c:if test="${ydOvertime.auditStatus == 'auditing'}">
						审核中
					</c:if>
					<c:if test="${ydOvertime.auditStatus == 'pass'}">
						通过
					</c:if>
					<c:if test="${ydOvertime.auditStatus == 'no'}">
						驳回
					</c:if>
				</td>

				<td>
					<fmt:formatDate value="${ydOvertime.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<a href="${ctx}/yd/ydOvertime/autditform?id=${ydOvertime.id}">审核</a>
				</td>
				<%--<shiro:hasPermission name="yd:ydOvertime:edit"><td>
    				<a href="${ctx}/yd/ydOvertime/form?id=${ydOvertime.id}">修改</a>
					<a href="${ctx}/yd/ydOvertime/delete?id=${ydOvertime.id}" onclick="return confirmx('确认要删除该申请加班吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>