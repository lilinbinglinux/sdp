<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="../common-head.jsp"%>
<title>登录日志查询</title>
<%@ include file="../common-layer-ext.jsp"%>
<%@ include file="../common-body-css.jsp"%>
<style type="text/css">
body {
	overflow: hidden;
}

#UserLoginLogTable {
	width: 100%;
}

#text_area {
	width: 407px;
	height: 80px;
	border: 1px solid #ccc;
}
</style>
</head>
<body>
	<!-- <div class="row">
		<div class="col-lg-12 col-md-12 row-tab">
			<div id="org-panel" class="panel panel-default common-wrapper">
				<div class="panel-heading common-part">
					<i class="iconfont">&#xe6ca;</i><span>登录日志列表</span>
				</div>
				<div class="panel-body common-content">
					</div>
					<table id="UserLoginLogTable">
						<thead>
							<tr>
								<th>登录名称</th>
								<th>登录时间</th>
								<th>登陆IP</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div> -->
		
		
		<div class="row">
	     <div class="col-lg-12 col-md-12 row-tab">
	         <div id="org-panel" class="panel panel-default common-wrapper">
  					<div class="panel-heading common-part"><i class="iconfont">&#xe6ca;</i><span>用户列表</span></div>
  					<div class="panel-body common-content">
	               			<table id="UserLoginLogTable">  
                        		<thead>
            						<tr>
                						<th>登录名称</th>
								`		<th>登录时间</th>
										<th>登陆IP</th>
            						</tr>
        						</thead>
                    		</table>  
  					</div>
			 </div>
	     </div>
	</div>
	
	<%@ include file="../common-js.jsp"%>
	<script src="<%=webpath%>/resources/js/log/userLoginLog.js"></script>
</body>
</html>