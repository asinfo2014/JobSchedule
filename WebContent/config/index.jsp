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
	<div style="height:25px"></div>
	<div class="container-fluid"   >
		<div class="row-fluid">
			<%@ include file="/left.jsp"%>
			<div class="span9">
			<!-- content begin
			<ul class="breadcrumb">
				<li><a href="#">首页</a> <span class="divider">/</span></li>
				<li><a href="#">配置流程</a> <span class="divider">/</span></li>
				<li class="active">所有流程</li>
			</ul> -->
			<table class="newtable">
				 
				<thead>
					<tr>
					    <th>流程名称</th>
<!-- 						<th>流程编码</th> -->
						<th>计划开始时间</th>
						<th>计划结束时间</th>
						<th>执行周期</th>
						<th>间隔</th>
<!-- 						<th>拓扑图</th> -->
						<th>失败处理</th>
						<th>状态</th>
						<th>重试次数</th>
						<th>流程目录</th>
						<th>账期时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="flist" id="fbean">
						<tr>
						    <td><s:property value='#fbean.flowname' /></td>
<%-- 							<td><s:property value='#fbean.flowid' /></td> --%>
							<!--  td><s:property value='#fbean.begintime ' /></td -->
							<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#fbean.begintime})}" /> </td>
							<td><s:if test="#fbean.endtime!=''"><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#fbean.endtime})}" /></s:if>
								<s:if test="#fbean.endtime!=''"><center> ---- </center></s:if></td>
							<td><s:if test="#fbean.timekind==0">一次</s:if> <s:if
									test="#fbean.timekind==1">分钟</s:if> <s:if
									test="#fbean.timekind==2">小时</s:if> <s:if
									test="#fbean.timekind==3">天</s:if> <s:if
									test="#fbean.timekind==4">月</s:if> <s:if
									test="#fbean.timekind==5">年</s:if></td>
							<td><s:property value='#fbean.timeintv' /></td>
<%-- 							<td><s:property value='#fbean.flowdesc' /></td> --%>
							<td><s:if test="#fbean.flowtype==1">失败后停止</s:if></td>
							<td><s:if test="#fbean.flowstatus==1">可用</s:if> <s:if
									test="#fbean.flowstatus==2">不可用</s:if></td>
							<td><s:property value='#fbean.failedRetryCnt' /></td>
							<td><s:property value='#fbean.dirname' /></td>
							<td><!-- s:property value='#fbean.execyctime' / -->
							<s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#fbean.execyctime})}" /> </td>
							<td><s:if test="#fbean.flowstatus==2">
									<a class="btn btn-primary"
										href="toEdit.do?flowid=<s:property value='#fbean.flowid' />">修改</a>&nbsp;&nbsp;
							<a class="btn btn-primary" href="delete.do?flowid=<s:property value='#fbean.flowid' />">删除</a>
								</s:if>
								<a class="btn btn-primary" href="view.do?flowid=<s:property value='#fbean.flowid' />">查看</a>
								</td>
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