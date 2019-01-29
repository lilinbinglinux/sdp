<% /**
    ----------- 本页面描述申请面板项目的详细信息（项目展示ifream） ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/datatables/media/css/jquery.dataTables.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/puborder/order/order-model.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
<!DOCTYPE html>
<html>
<head>
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
    <div class="panel-body common-content" id="project-div">
        <div class="searchWrap">
            <form class="form-inline" id="pubidtoproject">
                <div class="form-group">
                    <input type="hidden" id="projectmanagepubid" name="pubid">
                    <input type="hidden" id="res-req" name="type" value="0">
                </div>
            </form>
        </div>
        <table id="projecttable">  
            <thead>
                    <tr>
                        <th>id</th>
                        <th>模块名称</th>
                        <th>英语名称</th>
                        <th>版本号</th>
                    </tr>
            </thead>
        </table>  
    </div>
    <input type="hidden" value="${type }" id="type"/>
    
    <script src="<%=webpath %>/resources/js/puborder/order/orderproject_manage.js"></script>
</body>
</html>
