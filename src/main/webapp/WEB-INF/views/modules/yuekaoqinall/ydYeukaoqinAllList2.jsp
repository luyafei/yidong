<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月考勤总览管理</title>
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
		

		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出考勤数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","/cdyidong/a/yuekaoqinall/ydYeukaoqinAll/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","/cdyidong/a/yuekaoqinall/ydYeukaoqinAll/deptkaoqin");
			});
		});
		
		
		function tijiaoshenhes(shenheid){
			if(window.confirm('提交本月考勤审核后，将不能再次导入本月考勤')){
				$("#shenheid").val(shenheid);
				$("#tijiaoshenhe").val("true");
				$("#searchForm").submit();
			}
		}
		function tijiaoshenhes_xx(shenheid){
			$("#shenheid").val(shenheid);
			$("#tijiaoshenhe").val("true");
			$("#searchForm").submit();
		}
		function downloadmb(){
			
			window.open("/cdyidong/download/201601.xlsx");
			
		}
		
		
	</script>
</head>
<body>
<div id="importBox" class="hide">
	<form id="importForm" action="${ctx}/yuekaoqinall/ydYeukaoqinAll/import_yulan" method="post" enctype="multipart/form-data"
		  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
		<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		<%--<a href="${ctx}/sys/user/import/template">下载模板</a>--%>
	</form>
</div>
	<ul class="nav nav-tabs">
		<!--  <li class="active"><a href="${ctx}/yuekaoqinall/ydYeukaoqinAll/">月考勤待审核列表</a></li>-->
		<!--<li class="active"><a href="${ctx}/yuekaoqinall/ydYeukaoqinAll/deptkaoqin">部门月考勤列表</a></li>
		-->
		<li class="active"><a href="">部门月考勤列表</a></li>
		
		<shiro:hasPermission name="yuekaoqinall:ydYeukaoqinAll:edit"><li><a href="${ctx}/yuekaoqinall/ydYeukaoqinAll/form">部门月考勤列表</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ydYeukaoqinAll" action="${ctx}/yuekaoqinall/ydYeukaoqinAll/deptkaoqin" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<input id="shenheid" name="shenheid" type="hidden" value=""/>
		<input id="tijiaoshenhe" name="tijiaoshenhe" type="hidden" value=""/>
		
		<!-- 部门查询模版 -->
		<!--  <div id="jbox" class="jbox" style="position: absolute; width: auto; height: auto; top: 78.375px; left: 527px; z-index: 1985;">
		<div class="jbox-help-title jbox-title-panel" style="height: 25px; display: none; cursor: move;"></div>
		<div class="jbox-help-button jbox-button-panel" style="height: 25px; padding: 5px 0px; display: none;"></div>
		<table border="0" cellpadding="0" cellspacing="0" style="margin:0px;padding:0px;border:none;"><tbody>
		<tr><td class="jbox-border" style="margin:0px;padding:0px;border:none;border-radius:5px 0 0 0;width:5px;height:5px;"></td>
		<td class="jbox-border" style="margin:0px;padding:0px;border:none;height:5px;overflow: hidden;"></td><td class="jbox-border" style="margin:0px;padding:0px;border:none;border-radius:0 5px 0 0;width:5px;height:5px;"></td></tr><tr>
		<td class="jbox-border" style="margin:0px;padding:0px;border:none;"></td><td valign="top" style="margin:0px;padding:0px;border:none;"><div class="jbox-container" style="width:auto; height:auto;"><a class="jbox-close" title="关闭" onmouseover="$(this).addClass('jbox-close-hover');" onmouseout="$(this).removeClass('jbox-close-hover');" style="position:absolute; display:block; cursor:pointer; top:11px; right:11px; width:15px; height:15px;"></a><div class="jbox-title-panel" style="height: 25px; cursor: move;"><div class="jbox-title" style="float:left; width:250px; line-height:24px; padding-left:5px;overflow:hidden;text-overflow:ellipsis;word-break:break-all;">选择部门</div></div><div id="jbox-states"><div id="jbox-state-state0" class="jbox-state" style="display: block;"><div style="min-width:50px;width:300px; height:341px;"><div id="jbox-content-loading" class="jbox-content-loading" style="min-height: 70px; height: 341px; text-align: center; display: none;"><div class="jbox-content-loading-image" style="display:block; margin:auto; width:220px; height:19px; padding-top: 136.8px;"></div></div><div id="jbox-content" class="jbox-content" style="height: 341px; overflow: hidden; position: static; left: -10000px;">
		<iframe name="jbox-iframe-jBox_345985" id="jbox-iframe" width="100%" height="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" src="/cdyidong/a/tag/treeselect?url=/sys/office/treeData?type=2&amp;module=&amp;checked=&amp;extId=&amp;isAll=&amp;___t0.9694853977966567"><input type="hidden" name="selectIds" value=""></form>
		</div></div><div class="jbox-button-panel" style="height:25px;padding:5px 0 5px 0;text-align: right;"><span class="jbox-bottom-text" style="float:left;display:block;line-height:25px;"></span><button class="jbox-button jbox-button-focus" value="ok" style="padding:0px 10px 0px 10px;">确定</button><button class="jbox-button" value="clear" style="padding:0px 10px 0px 10px;">清除</button><button class="jbox-button" value="true" style="padding:0px 10px 0px 10px;">关闭</button></div></div></div></div></td><td class="jbox-border" style="margin:0px;padding:0px;border:none;"></td></tr><tr><td class="jbox-border" style="margin:0px;padding:0px;border:none;border-radius:0 0 0 5px; width:5px; height:5px;">
		</td><td class="jbox-border" style="margin:0px;padding:0px;border:none;height:5px;overflow: hidden;"></td><td class="jbox-border" style="margin:0px;padding:0px;border:none;border-radius:0 0 5px 0; width:5px; height:5px;"></td></tr></tbody></table>
		</div>
		-->
		
		
		<ul class="ul-form">
		
		
			<c:if test="${isshi=='false'}">
				<li><label>部门名称：</label>
					<%-- <form:input path="officeName" htmlEscape="false" maxlength="255" class="input-medium"/> --%>
					<sys:treeselect id="office" name="deptId" value="${testData.office.id}" labelName="office.name" labelValue="${testData.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				</li>
			</c:if>
			
			<li style="width:500px"><label>月份：</label>
				<form:input path="attMonth" htmlEscape="false" maxlength="255" class="input-medium" type="month"  value="${yuefen}" />（格式：201601）
			</li>
			<!--  <li style="width:500px"><label>审核状态：</label>
				<form:input path="auditStatus" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			-->
			
			<li style="width:500px"><label>审核状态：</label>
			<select class="input-medium" style="margin-left:30px" name="auditStatus">
				<option value ="">全部</option>
				
				<c:forEach items="${dictList}" var="dict">
				
					<c:if test="${dict.label==selected}">
						<option value ="${dict.label}" selected="selected">${dict.label}</option>
					</c:if>
					<c:if test="${dict.label!=selected}">
						<option value ="${dict.label}">${dict.label}</option>
					</c:if>
					
				</c:forEach>
				
			</select>
			
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns">
				<!-- <input id="btnExport"  class="btn btn-primary" type="button" value="导出">
				 -->
				<input id="btnImport" class="btn btn-primary" type="button" value="导 入"/>
				</li>
				<li class="btns"><input id="downmb" onclick="downloadmb()" class="btn btn-primary" type="button" value="模版下载"/>
			</li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>区域</th>
				<th>部门名称</th>
				<th>月份</th>
				<th>审核状态</th>
				<th>操作</th>
				<!-- <th>查看</th> -->
				<%-- <shiro:hasPermission name="kaoqinyichang:ydKaoqinyichang:edit"><th>操作</th></shiro:hasPermission>
				 --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ydYeukaoqinAll">
			<tr>
				<td>
					${ydYeukaoqinAll.areaName}
				</td>
				<td>
					${ydYeukaoqinAll.officeName}
				</td>
				 <td>
					${ydYeukaoqinAll.attMonth}
				</td>
				<td>
				${ydYeukaoqinAll.auditStatus}
					<c:if test="${ydYeukaoqinAll.ingStatus=='1' && ydYeukaoqinAll.isshi=='true'}">
						(等待部门经理审核)
					</c:if>
					<c:if test="${ydYeukaoqinAll.ingStatus=='2' && ydYeukaoqinAll.isshi=='true'}">
						(等待人资考勤审核员审核)
					</c:if>
					<c:if test="${ydYeukaoqinAll.ingStatus=='1' && ydYeukaoqinAll.isshi=='false'}">
						(等待部门经理审核)
					</c:if>
					<c:if test="${ydYeukaoqinAll.ingStatus=='2' && ydYeukaoqinAll.isshi=='false'}">
						(等待县经理审核)
					</c:if>
					<c:if test="${ydYeukaoqinAll.ingStatus=='3' && ydYeukaoqinAll.isshi=='false'}">
						(等待人资考勤审核员审核)
					</c:if>
				</td>
				<td>
					<c:if test="${ydYeukaoqinAll.lable=='未提交' || ydYeukaoqinAll.lable=='不通过'}">
						<input onclick="tijiaoshenhes(${ydYeukaoqinAll.id})" id="tijiaoshenhe" class="btn btn-primary" type="button" value="提交审核"/>
					</c:if>
					<!--<c:if test="${ydYeukaoqinAll.lable!='未提交' && ydYeukaoqinAll.lable!='不通过'}">
						<input disabled="disabled" id="yitijiao" class="btn btn-primary" type="button" value="已提交"/>
					</c:if>
					-->
						<a href="${ctx}/yuekaoqin/attendanceDay/renziyuekaoqin?month=${ydYeukaoqinAll.attMonth}&deptId=${ydYeukaoqinAll.officeId}"><input onclick="tijiaoshenhes_xx(${ydYeukaoqinAll.id})" id="xiangxi" class="btn btn-primary" type="button" value="详细"/></a>
				
					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>