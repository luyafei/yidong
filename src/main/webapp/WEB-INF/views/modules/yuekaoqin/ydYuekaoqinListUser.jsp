<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月考勤记录管理</title>
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
		<li class="active"><a href="${ctx}/yuekaoqin/ydYuekaoqin/">月考勤记录列表</a></li>
		<shiro:hasPermission name="yuekaoqin:ydYuekaoqin:edit"><li><a href="${ctx}/yuekaoqin/ydYuekaoqin/form">月考勤记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ydYuekaoqin" action="${ctx}/yuekaoqin/ydYuekaoqin/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<%-- <li><label>部门：</label>
				<form:input path="officeName" htmlEscape="false" maxlength="255" class="input-medium" value="${deptId}"/>
			</li> --%>
			<%-- <li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>工号：</label>
				<form:input path="uid" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li style="width:500px"><label>月份：</label>
				<form:input path="month" htmlEscape="false" maxlength="255" class="input-medium"/>（例：201601）
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li> --%>
		</ul>
		
	</form:form>
	<sys:message content="${message}"/>
	<strong>
		<%-- <c:choose>
			<c:when test="${shuoming==null || shuoming==''}">
				本月，月考勤数据：
			</c:when>
			<c:otherwise>
				${shuoming}
			</c:otherwise>
		</c:choose> --%>
		${shuoming}
	</strong>
	<br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">工号1</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">1</th>
				<th style="text-align: center">2</th>
				<th style="text-align: center">3</th>
				<th style="text-align: center">4</th>
				<th style="text-align: center">5</th>
				<th style="text-align: center">6</th>
				<th style="text-align: center">7</th>
				<th style="text-align: center">8</th>
				<th style="text-align: center">9</th>
				<th style="text-align: center">10</th>
				<th style="text-align: center">11</th>
				<th style="text-align: center">12</th>
				<th style="text-align: center">13</th>
				<th style="text-align: center">14</th>
				<th style="text-align: center">15</th>
				<th style="text-align: center">16</th>
				<th style="text-align: center">17</th>
				<th style="text-align: center">18</th>
				<th style="text-align: center">19</th>
				<th style="text-align: center">20</th>
				<th style="text-align: center">21</th>
				<th style="text-align: center">22</th>
				<th style="text-align: center">23</th>
				<th style="text-align: center">24</th>
				<th style="text-align: center">25</th>
				<th style="text-align: center">26</th>
				<th style="text-align: center">27</th>
				<th style="text-align: center">28</th>
				<th style="text-align: center">29</th>
				<th style="text-align: center">30</th>
				<th style="text-align: center">31</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ykq31List}" var="ykq31">
			<tr>
				<td style="background-color: white">
					${ykq31.uid}
				</td>
				<td style="background-color: white">
					${ykq31.name}
				</td>
				<td style="background-color: white">
					${ykq31.ri01}
				</td>
				<td style="background-color: white">
					${ykq31.ri02}
				</td>
				<td style="background-color: white">
					${ykq31.ri03}
				</td>
				<td style="background-color: white">
					${ykq31.ri04}
				</td>
				<td style="background-color: white">
					${ykq31.ri05}
				</td>
				<td style="background-color: white">
					${ykq31.ri06}
				</td>
				<td style="background-color: white">
					${ykq31.ri07}
				</td>
				<td style="background-color: white">
					${ykq31.ri08}
				</td>
				<td style="background-color: white">
					${ykq31.ri09}
				</td>
				<td style="background-color: white">
					${ykq31.ri10}
				</td>
				<td style="background-color: white">
					${ykq31.ri11}
				</td>
				<td style="background-color: white">
					${ykq31.ri12}
				</td>
				<td style="background-color: white">
					${ykq31.ri13}
				</td>
				<td style="background-color: white">
					${ykq31.ri14}
				</td>
				<td style="background-color: white">
					${ykq31.ri15}
				</td>
				<td style="background-color: white">
					${ykq31.ri16}
				</td>
				<td style="background-color: white">
					${ykq31.ri17}
				</td>
				<td style="background-color: white">
					${ykq31.ri18}
				</td>
				<td style="background-color: white">
					${ykq31.ri19}
				</td>
				<td style="background-color: white">
					${ykq31.ri20}
				</td>
				<td style="background-color: white">
					${ykq31.ri21}
				</td>
				<td style="background-color: white">
					${ykq31.ri22}
				</td>
				<td style="background-color: white">
					${ykq31.ri23}
				</td>
				<td style="background-color: white">
					${ykq31.ri24}
				</td>
				<td style="background-color: white">
					${ykq31.ri25}
				</td>
				<td style="background-color: white">
					${ykq31.ri26}
				</td>
				<td style="background-color: white">
					${ykq31.ri27}
				</td>
				<td style="background-color: white">
					${ykq31.ri28}
				</td>
				<td style="background-color: white">
					${ykq31.ri29}
				</td>
				<td style="background-color: white">
					${ykq31.ri30}
				</td>
				<td style="background-color: white">
					${ykq31.ri31}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>