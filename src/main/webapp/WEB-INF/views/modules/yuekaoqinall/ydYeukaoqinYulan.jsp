<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月考勤总览管理</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
	
	
	
	</style>
	
	<script type="text/javascript">
		
		function queding(){
			if(window.confirm('确定上传此批数据?')){
				$("#queding").click();
		    }
			
		}
		function quxiao(){
			if(window.confirm('确定放弃?')){
				$("#quxiao").click();
			}
			
		}
		
	</script>
</head>
<body>
<div id="importBox" class="hide">
	<form id="importForm" action="${ctx}/yuekaoqinall/ydYeukaoqinAll/import" method="post" enctype="multipart/form-data"
		  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
		<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		<%--<a href="${ctx}/sys/user/import/template">下载模板</a>--%>
	</form>
</div>
	<ul class="nav nav-tabs">
		<!--  <li class="active"><a href="${ctx}/yuekaoqinall/ydYeukaoqinAll/">月考勤待审核列表</a></li>-->
		<li class="active">月考勤导入预览</li>
	</ul>
	<sys:message content="${message}"/>
	
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
		<c:forEach items="${list}" var="y31">
			<%-- <c:if test="${y31.name!=null and y31.name!='' }"> --%>
				<tr>
					<td style="background-color: white">
						${y31.uid}
					</td>
					<td style="background-color: white">
						${y31.name}
					</td>
						
					<td style="background-color: white">
						${y31.ri01}
					</td>
					<td style="background-color: white">
						${y31.ri02}
					</td>
					<td style="background-color: white">
						${y31.ri03}
					</td>
					<td style="background-color: white">
						${y31.ri04}
					</td>
					<td style="background-color: white">
						${y31.ri05}
					</td>
					<td style="background-color: white">
						${y31.ri06}
					</td>
					<td style="background-color: white">
						${y31.ri07}
					</td>
					<td style="background-color: white">
						${y31.ri08}
					</td>
					<td style="background-color: white">
						${y31.ri09}
					</td>
					<td style="background-color: white">
						${y31.ri10}
					</td>
					<td style="background-color: white">
						${y31.ri11}
					</td>
					<td style="background-color: white">
						${y31.ri12}
					</td>
					<td style="background-color: white">
						${y31.ri13}
					</td>
					<td style="background-color: white">
						${y31.ri14}
					</td>
					<td style="background-color: white">
						${y31.ri15}
					</td>
					<td style="background-color: white">
						${y31.ri16}
					</td>
					<td style="background-color: white">
						${y31.ri17}
					</td>
					<td style="background-color: white">
						${y31.ri18}
					</td>
					<td style="background-color: white">
						${y31.ri19}
					</td>
					<td style="background-color: white">
						${y31.ri20}
					</td>
					<td style="background-color: white">
						${y31.ri21}
					</td>
					<td style="background-color: white">
						${y31.ri22}
					</td>
					<td style="background-color: white">
						${y31.ri23}
					</td>
					<td style="background-color: white">
						${y31.ri24}
					</td>
					<td style="background-color: white">
						${y31.ri25}
					</td>
					<td style="background-color: white">
						${y31.ri26}
					</td>
					<td style="background-color: white">
						${y31.ri27}
					</td>
					<td style="background-color: white">
						${y31.ri28}
					</td>
					<td style="background-color: white">
						${y31.ri29}
					</td>
					<td style="background-color: white">
						${y31.ri30}
					</td>
					<td style="background-color: white">
						${y31.ri31}
					</td>
					
				</tr>
			
			<%-- </c:if> --%>
		</c:forEach>
		</tbody>
		
	</table>
	<div style="text-align: center;">
	<input onclick='queding()' class="btn btn-primary" type="button" value="确定上传" />
	<input onclick='quxiao()' class="btn btn-primary" type="button" value="放弃上传" style="margin-left:50px;" />
	</div>		
				
	<form action="${ctx}/yuekaoqinall/ydYeukaoqinAll/import" method="post" style="display: none;" >
		<input type="text" name='isimport' value="true" />
		<input id='queding' type="submit"  value="确定上传" />
	</form>
	<form  action="${ctx}/yuekaoqinall/ydYeukaoqinAll/import" method="post"  style="display: none;"  >
		<input type="text" name='isimport' value="false" />
		<input id='quxiao' type="submit"  value="放弃上传" />
	</form>
				
				
	<div class="pagination">${page}</div>
</body>
</html>