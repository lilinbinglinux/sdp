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

<%@ include file="../../common-js.jsp"%>
<%@ include file="../../common-head.jsp"%>
<%@ include file="../../common-layer-ext.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/core/base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>


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
    <div class="panel-body common-content" id="param-div">
        <div>
        	<ul class="nav nav-tabs">
    		  <li role="presentation" class="active" id="reqdiv"><a href="#">请求参数</a></li>
    		  <li role="presentation" id="respdiv" ><a href="#">响应参数</a></li>
    		</ul>
        </div>
        <div id="id-paraminfotable">
	    	<div class="searchWrap">
	    		<form class="form-inline" id="pubidtoreq">
	    			<div class="form-group">
	    				<input type="hidden" id="reqmanagepubid" name="pubid">
	    				<input type="hidden" id="res-req" name="type" value="0">
	    			</div>
	    		</form>
	         </div>
	   		<!-- <table id="order_reqparamtable"> -->  
	   		<div id="order_reqparamtable" style="margin-top: 20px;"></div>
        </div>
    </div>
	<input type="hidden" value="${type }" id="type"/>
	<script src="<%=webpath %>/resources/js/puborder/order/orderreqparam_manage.js"></script>
</body>
</html>	