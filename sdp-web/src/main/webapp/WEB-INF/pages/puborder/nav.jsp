<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<script src="<%=request.getContextPath() %>/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/bootstrap-menu/BootstrapMenu.min.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Insert title here</title>
</head>
<body>
	<div>
		<ul class="nav nav-tabs">
  			<li role="presentation" class="active"><a href="#">Home</a></li>
  			<li role="presentation"><a href="#">Profile</a></li>
  			 <li role="presentation" class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
      Dropdown <span class="caret"></span>
    </a>
    <ul class="dropdown-menu">
      <li>123</li>
      <li>456</li>
    </ul>
  </li>
		</ul>
	</div>
</body>
</html>