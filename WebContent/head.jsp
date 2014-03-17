<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 
<div class="header">
	<table>
		<tr>
			<td><div>
					<a href="#"><img src="/public/pic/logo.gif"
						alt="在此处插入徽标" name="Insert_logo" width="132" height="75"
						id="Insert_logo"
						style="background-color: #CCCCCC; display: block;" /></a>
				</div></td>
			<td align="center"><div>大数据调度平台</div></td>
		</tr>
	</table>


</div>
 -->


<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<!-- 
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">大数据调度平台</a>
				 -->
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href="#">首页</a></li>
					<li><a href="<%=proPath%>/config/list.do"> 配置流程</a></li>
					<li><a href="<%=proPath%>/monitor/index.do"> 监控流程</a></li>
					<li><a href="<%=proPath%>/manager/list.do"> 管理流程</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>
