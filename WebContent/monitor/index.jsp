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
					<li class="active">流程列表</li>
				</ul> --><div style="height:25px"></div>
				<table>
					<tr>
						<td width="20%" valign="top">
							<table>
								<tr>
									<td>流程目录</td>
								</tr>
								<tr>
									<td><ul class="easyui-tree"
											data-options="url:'flowdirs.do',method:'get',animate:true,
													onClick: function(node){
																window.location.href='index.do?flowBean.dirid='+$(this).tree('getNode',node.target).id;
															}"></ul>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table class="newtable">
								<thead>
									<tr>
										<th nowrap="nowrap">流程名称</th>
										<th nowrap="nowrap">执行计划时间</th>
										<th nowrap="nowrap">执行开始时间</th>
										<th nowrap="nowrap">执行结束时间</th>
										<th nowrap="nowrap">执行状态</th>
										<!-- <th nowrap="nowrap">描述</th> -->
										<th nowrap="nowrap">查看流程任务</th>
										<th nowrap="nowrap">操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="ifbeans" id="ifBean">
										<tr>
											<td><s:property value='#ifBean.flowname' /></td>
											<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#ifBean.execyctime})}" /></td>
											<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#ifBean.begintime})}" /></td>
											<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{#ifBean.endtime})}" /></td>
											<td><s:if test="#ifBean.status==0">执行成功</s:if> <s:if
													test="#ifBean.status==1">待执行</s:if> <s:if
													test="#ifBean.status==2">正在执行</s:if> <s:if
													test="#ifBean.status==3">执行失败</s:if> <s:if
													test="#ifBean.status==4">无效执行</s:if> <s:if
													test="#ifBean.status==5">正在实例化</s:if> <s:if
													test="#ifBean.status==6">挂起</s:if></td>
											<!-- <td><s:property value='#ifBean.description' /></td> -->
											<%
												InstanceFlowBean ifBean = (InstanceFlowBean) (request
															.getAttribute("ifBean"));
													// String execyctime_str = Tools.timestampToStr(ifBean.getExecyctime());
													long execyctime_l = ifBean.getExecyctime().getTime();
											%>
											<td><a class="btn btn-primary"
												href="instancetask.do?flowid=<s:property value='#ifBean.flowid' />&execyctime_l=<%=execyctime_l%>">查看流程任务</a>
											</td>
											<td><s:if test="#ifBean.status==3">
											    <a class="btn btn-primary"
												href="<%=proPath%>/manager/activeFlow.do?flowid=<s:property value='#ifBean.flowid' />&flag=0">流程启动</a>&nbsp;&nbsp;
												<a class="btn btn-primary"
												href="<%=proPath%>/manager/activeFlow.do?flowid=<s:property value='#ifBean.flowid' />&flag=1">任务启动</a>
											</s:if><s:if test="#ifBean.status==6">
											    <a class="btn btn-primary"
												href="<%=proPath%>/manager/activeFlow.do?flowid=<s:property value='#ifBean.flowid' />&flag=0">流程启动</a>&nbsp;&nbsp;
												<a class="btn btn-primary"
												href="<%=proPath%>/manager/activeFlow.do?flowid=<s:property value='#ifBean.flowid' />&flag=1">任务启动</a>
											</s:if></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</td>
					</tr>
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