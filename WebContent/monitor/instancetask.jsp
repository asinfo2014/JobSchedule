<%@page import="com.bean.*"%>
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
			 
			<div class="span9">
<!-- 
				<ul class="breadcrumb">
					<li><a href="#">首页</a> <span class="divider">/</span></li>
					<li><a href="#">监控流程</a> <span class="divider">/</span></li>
					<li><a href="#">流程列表</a> <span class="divider">/</span></li>
					<li class="active">任务执行状态</li>
				</ul> -->
				<br>
				<!-- content begin 	 -->	 		
				<table class="newtable"   width="100%" style="table-layout:fixed;"  > 			
					<thead>
						<tr align="center" valign="middle">
							<td width="50px">任务名称</td>
							<td width="50px">流程名称</td>
							<td width="80px">执行计划时间</td>
							<td width="80px">执行开始时间</td>
							<td width="80px">执行结束时间</td>
							<td width="30px">执行状态</td>
							<td width="150px">执行日志信息</td>
							<td width="30px">执行次数</td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="itbeans" id="itbean">
							<tr align="center" valign="middle">
								<td><s:property value='#itbean.taskname' /></td>
								<td><s:property value='#itbean.flowname' /></td> 
								<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#itbean.execyctime})}" /></td>
								<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#itbean.begintime})}" /></td>
								<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#itbean.endtime})}" /></td> 
								<td><s:if test="#itbean.status==0">执行成功</s:if> <s:if
										test="#itbean.status==1">待执行</s:if> <s:if
										test="#itbean.status==2">正在执行</s:if> <s:if
										test="#itbean.status==3">执行失败</s:if> <s:if
										test="#itbean.status==4">无效执行</s:if> <s:if
										test="#itbean.status==5">正在实例化</s:if> <s:if
										test="#itbean.status==6">挂起</s:if></td>
								<td><s:if test="%{#itbean.log.length() > 30}">
									<s:property value="%{#itbean.log.substring(0, 30) + \"...\"}"/>
									</s:if>
									<s:else>
										<s:property value="#itbean.log"/>
									</s:else></td><!-- s:property value='#itbean.log' / -->
								<td><s:property value='#itbean.execcnt' /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<!-- content end -->
			</div>
			<!--/span-->
		</div>
		<!--/row-->
	</div>
	<!--/container -->
	<%@ include file="/footer.jsp"%>
</body>
</html>