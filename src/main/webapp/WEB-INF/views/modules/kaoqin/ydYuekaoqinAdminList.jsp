<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月考勤记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
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
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/kaoqin/ydYuekaoqinAdmin/import" method="post" enctype="multipart/form-data"
			  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<%--<a href="${ctx}/kaoqin/ydYuekaoqinAdmin/import/template">下载模板</a>--%>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/kaoqin/ydYuekaoqinAdmin/">月考勤记录列表</a></li>
		<shiro:hasPermission name="kaoqin:ydYuekaoqinAdmin:edit"><li><a href="${ctx}/kaoqin/ydYuekaoqinAdmin/form">月考勤记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ydYuekaoqinAdmin" action="${ctx}/kaoqin/ydYuekaoqinAdmin/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门：</label>
				<sys:treeselect id="deptId" name="deptId" value="${ydYuekaoqinAdmin.deptId}" labelName="" labelValue="${ydYuekaoqinAdmin.deptId}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>考勤日期：</label>
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ydYuekaoqinAdmin.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%--<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>--%>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>考勤日期</th>
				<th>考勤类型</th>
				<shiro:hasPermission name="kaoqin:ydYuekaoqinAdmin:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydYuekaoqinAdmin">
			<tr>
				<td>
					${ydYuekaoqinAdmin.name}
				</td>
				<td>
					<fmt:formatDate value="${ydYuekaoqinAdmin.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(ydYuekaoqinAdmin.status, 'AttendanceStatus', '')}
				</td>
				<shiro:hasPermission name="kaoqin:ydYuekaoqinAdmin:edit"><td>
    				<a href="${ctx}/kaoqin/ydYuekaoqinAdmin/form?id=${ydYuekaoqinAdmin.id}">修改</a>
					<a href="${ctx}/kaoqin/ydYuekaoqinAdmin/delete?id=${ydYuekaoqinAdmin.id}" onclick="return confirmx('确认要删除该月考勤记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>