<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>亚信大数据任务调度平台</title>
<%@ include file="/common.jsp"%>
<script>
	$(document).ready(function() {
		initradio("fbean.timekind", <s:property value="fbean.timekind"/>);
		initradio("fbean.flowstatus", <s:property value="fbean.flowstatus"/>);
	});
</script>
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
					<li class="active">修改流程</li>
				</ul> -->
				<form action="edit.do" method="post">
					<table class="newtable">
						<tbody>
							<tr>
								<td>流程编码</td>
								<td><s:text name="fbean.flowid" /> <input type="text"
									name="fbean.flowid" value='<s:property value="fbean.flowid"/>'
									style="display: none;"></td>
							</tr>
							<tr>
								<td>流程计划开始执行时间</td>
								<td><s:textfield name="fbean.begintime" /></td>
							</tr>
							<tr>
								<td>流程计划结束执行时间</td>
								<td><s:textfield name="fbean.endtime" /></td>
							</tr>
							<tr>
								<td>流程执行循环周期类型</td>
								<td><label><input name="fbean.timekind"
										type="radio" value="0" />一次任务 </label> <label><input
										name="fbean.timekind" type="radio" value="1" />分钟 </label> <label><input
										name="fbean.timekind" type="radio" value="2" />小时 </label> <label><input
										name="fbean.timekind" type="radio" value="3" />天</label> <label><input
										name="fbean.timekind" type="radio" value="4" />月 </label> <label><input
										name="fbean.timekind" type="radio" value="5" />年 </label></td>
							</tr>
							<tr>
								<td>流程执行间隔</td>
								<td><s:textfield name="fbean.timeintv" /></td>
							</tr>
							<tr>
								<td>流程名称</td>
								<td><s:textfield name="fbean.flowname" /></td>
							</tr>
							<tr>
								<td>流程节点拓扑图</td>
								<td><s:textfield name="fbean.flowdesc" /></td>
							</tr>
							<tr>
								<td>流程执行失败后处理类型</td>
								<td><s:textfield name="fbean.flowtype" /></td>
							</tr>
							<tr>
								<td>流程当前状态</td>
								<td><s:if test="fbean.flowstatus==1">可用</s:if> <s:if
									test="fbean.flowstatus==2">不可用</s:if>
									<s:hidden name="fbean.flowstatus"></s:hidden>
									</td>

							</tr>
							<tr>
								<td>流程执行失败重试次数</td>
								<td><s:textfield name="fbean.failedRetryCnt" /></td>
							</tr>
							<tr>
								<td>流程目录</td>
								<td><s:textfield name="fbean.dirid" /></td>
							</tr>
							<tr>
								<td>执行的周期时间</td>
								<td><s:textfield name="fbean.execyctime" /></td>
							</tr>
							<tr>
								<td colspan="2"><s:submit value="保存" cssClass="btn btn-primary"></s:submit>
									<s:reset value="重置" cssClass="btn btn-primary"></s:reset></td>
							</tr>
					</table>
				</form>
			</div>
			<!--/span-->
		</div>
		<!--/row-->
	</div>
	<!--/container -->

	<%@ include file="/footer.jsp"%>
</body>
</html>