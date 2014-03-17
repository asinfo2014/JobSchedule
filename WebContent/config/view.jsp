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
					<li><a href="#">配置流程</a> <span class="divider">/</span></li>
					<li><a href="#">修改流程</a> <span class="divider">/</span></li>
					<li class="active">流程详细信息</li>
				</ul> -->
				<center>
				<div style="height:25px"></div>
				<table class="newtable">
					<tbody>
						<tr>
							<td>流程编码</td>
							<td><s:text name="fbean.flowid" /></td>
						</tr>
						<tr>
							<td>流程计划开始执行时间</td>
							<td><s:date name="fbean.begintime"  format="yyyy-MM-dd HH:mm:ss"/> </td>
						</tr>
						<tr>
							<td>流程计划结束执行时间</td>
							<td><s:date name="fbean.endtime"  format="yyyy-MM-dd HH:mm:ss"/> </td>
						</tr>
						<tr>
							<td>流程执行循环周期类型</td>
							<td><s:if test="fbean.timekind==0">一次</s:if> <s:if
									test="fbean.timekind==1">分钟</s:if> <s:if
									test="fbean.timekind==2">小时</s:if> <s:if
									test="fbean.timekind==3">天</s:if> <s:if
									test="fbean.timekind==4">月</s:if> <s:if
									test="fbean.timekind==5">年</s:if></td>
						</tr>
						<tr>
							<td>流程执行间隔</td>
							<td><s:text name="fbean.timeintv" /></td>
						</tr>
						<tr>
							<td>流程名称</td>
							<td><s:text name="fbean.flowname" /></td>
						</tr>
						<tr>
							<td>流程节点拓扑图</td>
							<td><s:text name="fbean.flowdesc" /></td>
						</tr>
						<tr>
							<td>流程执行失败后处理类型</td>
							<td><s:text name="fbean.flowtype" /></td>
						</tr>
						<tr>
							<td>流程当前是否可编辑</td>
							<td><s:if test="fbean.flowstatus==1">可用</s:if> <s:if
									test="fbean.flowstatus==2">不可用</s:if></td>
						</tr>
						<tr>
							<td>流程执行失败重试次数</td>
							<td><s:text name="fbean.failedRetryCnt" /></td>
						</tr>
						<tr>
							<td>流程目录</td>
							<td><s:text name="fbean.dirid" /></td>
						</tr>
						<tr>
							<td>执行的周期时间</td>
							<td><s:date name="fbean.execyctime"  format="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</tbody>
				</table>
			</center>
			</div>
			<!--/span-->
		</div>
		<!--/row-->
	</div>
	<!--/container -->

	<%@ include file="/footer.jsp"%>
</body>
</html>