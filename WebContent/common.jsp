<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.common.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	// String realPath = getServletContext().getRealPath("/");//项目绝对路径
	String proPath = request.getContextPath(); // 项目相对地址      /JobSchedule
%>

<link rel="stylesheet" type="text/css"
	href="<%=proPath%>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=proPath%>/public/easyui/themes/icon.css">
<script type="text/javascript"
	src="<%=proPath%>/public/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=proPath%>/public/easyui/jquery.easyui.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=proPath%>/public/bootstrap2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=proPath%>/public/bootstrap2/css/docs.css">
<script type="text/javascript"
	src="<%=proPath%>/public/bootstrap2/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=proPath%>/public/css/common.css">
<script type="text/javascript" src="<%=proPath%>/public/js/common.js"></script>