    <% /**
    --- 本页面分模块写服务注册树显示右侧面板详细信息 ---
    */%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/datatables/media/css/jquery.dataTables.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/puborder/order/order-model.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
<%@ include file="../../common-head.jsp"%>
<%@ include file="../../common-layer-ext.jsp"%>
<%@ include file="../../common-body-css.jsp"%>
<%@ include file="../../common-js.jsp"%>

    <style>
    body{
            overflow:hidden;
            background:#fff!important;
         }
    .dataTables_wrapper .dataTables_paginate {
        position: absolute;
        bottom: -70px;
        right: 30px;
    }
    </style>
</head>   

<body> 
    <!--右侧面板显示参数详情  -->
    <div class="panel-body common-content" id="publish-div">
        <div class="" id="loadOrderParam">
		<div class="searchWrap">
			<form class="form-inline" id="pubidtopublish">
				<div class="form-group">
					<input type="hidden" id="publishmanagepubid" name="parentId">
					<input type="hidden" id="flowmanagepubid" name="pubid">
					<input type="hidden" id="res-req" name="type" value="0">
				</div>
			</form>
		</div>
   		<table id="publishtable">  
           	<thead>
      				<tr>
						<th>id</th>
						<th>接口名称</th>
						<th>服务接口</th>
						<th>请求方式</th>
						<th>响应格式</th>
						<th>描述</th>
						<th>操作</th>
      				</tr>
            </thead>
        </table>
        </div>
        <%@ include file="./order-interfaceadd.jsp"%>
        <br>
        
    </div>
	<input type="hidden" value="${type }" id="type"/>
	<script src="<%=webpath %>/resources/js/puborder/order/orderpublish_manage.js"></script>
</body>	
</html>