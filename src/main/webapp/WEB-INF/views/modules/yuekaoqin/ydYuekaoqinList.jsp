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
		
		
		function back(){
			
			window.location.href = "/cdyidong/a/yuekaoqinall/ydYeukaoqinAll";
			
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="">月考勤记录列表</a></li>
		<shiro:hasPermission name="yuekaoqin:attendanceDay:edit"><li><a href="${ctx}/yuekaoqin/attendanceDay/form">月考勤记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="attendanceDay" action="${ctx}/yuekaoqin/attendanceDay/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		
			<c:if test="${shenfen=='renzikaoqinshenhe' || shenfen=='renzikaoqin' }">
				<li >
					<%-- <label>部门：</label>
					<form:input  path="officeName" htmlEscape="false" maxlength="255" class="input-medium" value="${deptname}" /> --%>
					<label class="control-label">部门名称：</label>
					<sys:treeselect id="office" name="deptId" value="${deptId}" labelName="office.name" labelValue="${deptname}"
					title="部门" url="/sys/office/treeData?type=2&renzikaoqin=1" cssClass="" allowClear="true" notAllowSelectParent="true"/>
					
				</li>
			</c:if>
			
			
			<c:if test="${shenfen!='renzikaoqinshenhe' && shenfen!='renzikaoqin' }">
				<li >
					<form:input  path="officeName" type="hidden"  htmlEscape="false" maxlength="255" class="input-medium" value="${deptname}" />
				</li>
			</c:if>
			
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>工号：</label>
				<form:input path="uid" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			
			<li style="width:500px"><label>月份：</label>
				<form:input path="month" type="month" value="${month}" htmlEscape="false" maxlength="255" class="input-medium"/>（例：201601）
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/><input onclick="back()" id="btnBack" class="btn btn-primary" type="button"  value="返回"/></li>
			<li class="clearfix"></li>
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
				<th style="text-align: center">工号</th>
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
		<c:forEach items="${nameMap}" var="nmap1">
			<tr>
				<td style="background-color: white">
					${nmap1.key}
				</td>
				<td style="background-color: white">
					${nmap1.value}
				</td>
				<c:forEach items="${statusMap}" var="nmap">
					
						<c:if test="${nmap.key==nmap1.key}">
							
							
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='01'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='02'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='03'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='04'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='05'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='06'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='07'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='08'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='09'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='10'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								
								
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='11'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='12'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='13'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='14'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='15'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='16'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='17'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='18'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='19'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='20'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								
								
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='21'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='22'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='23'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='24'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='25'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='26'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='27'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='28'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='29'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='30'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								<td style="background-color: white">
									<c:forEach items="${nmap.value}" var="ristatus">
										<c:if test="${ristatus.key=='31'}">
											${ristatus.value}
										</c:if>
									</c:forEach>
								</td>
								
						</c:if>
					
				</c:forEach>
				
				
				
			</tr>
			
			
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>