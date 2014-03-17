
<%
	
%>
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
			<div class="span2">
				<img alt="dsf" src="<%=proPath%>/public/pic/error.png">
			</div>
			<div class="span7">
				<h3>操作出错:</h3>
				<s:property value="se.message" />
			</div>
			<div class="span9">
				<h3>Stack trace:</h3>
				<pre>
		        	<s:property value="se.cause" />
		    	</pre>
			</div>
			<!--/span-->
		</div>
		<!--/row-->
	</div>
	<!--/container -->

	<%@ include file="/footer.jsp"%>
</body>
</html>