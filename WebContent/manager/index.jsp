<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>亚信大数据任务调度平台</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<%@ include file="/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<%@ include file="/left.jsp"%>
			<div style="height:25px"></div>
			<div class="span9">
			<!-- 
		<ul class="breadcrumb">
				<li><a href="#">首页</a> <span class="divider">/</span></li>
				<li><a href="#">管理流程</a> <span class="divider">/</span></li>
				<li class="active">动态管理</li>
			</ul> -->
		    <table class="newtable">
			<thead>
				<tr>
<!-- 					<th>流程编码</th> -->
					<th>流程名称</th>
					<th>流程状态</th>
					<th>操作</th>
				</tr>
			</thead>
            <tbody>
				<s:iterator value="flist" id="fbean">
					<tr>
<%-- 						<td><s:property value='#fbean.flowid' /></td> --%>
						<td><s:property value='#fbean.flowname' /></td>
						<td><s:if test="#fbean.flowstatus==1">可用</s:if> <s:if
								test="#fbean.flowstatus==2">不可用</s:if></td>
						<td>
						    <a class="btn btn-primary"
							href="activeFlow.do?flowid=<s:property value='#fbean.flowid' />&flag=0">流程启动</a>&nbsp;&nbsp;
							<a class="btn btn-primary"
							href="activeFlow.do?flowid=<s:property value='#fbean.flowid' />&flag=1">任务启动</a>&nbsp;&nbsp;
							<s:if test="#fbean.flowstatus==1">
							<a class="btn btn-primary"
							href="closeFlow.do?flowid=<s:property value='#fbean.flowid' />&flag=1">流程关闭</a></s:if>
						</td>
					</tr>
				</s:iterator>
			</tbody>
			</table>
		<!-- end .container -->
			</div>
			<!--/span-->
		</div>
		<!--/row-->
	</div>
	<!--/container -->
	
	<%@ include file="/footer.jsp"%>
</body>
</html>