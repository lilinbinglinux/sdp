<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/11/10
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-js.jsp"%>
    <%@ include file="../../common-head.jsp"%>
    <title>应用管理</title>
    <%@ include file="../../common-layer-ext.jsp"%>
    <%@ include file="../../common-body-css.jsp"%>
    <style type="text/css">
        body{
            overflow:hidden;
        }
        .panel-heading > i.iconfont:first-child{
            font-size:20px;
            margin-right:4px;
        }
        .common-part .icon-tip{
            float:right;
            margin-top:2px;
        }
        .panel-heading > span{
            top:-2px;
            position:relative;
        }
        .bootstrapMenu{
            z-index: 10000;
            position: absolute;
            display: none;
            height: 23px;
            width: 158px;
        }
        .dropdown-menu{
            position:static;
            display:block;
            font-size:0.9em;
        }
        .dataTables_wrapper .dataTables_paginate {
            position: absolute;
            bottom: -300px;
            right: 30px;
        }
        .ztree{
            overflow: auto;
            height: 100%;
            width: 103%;
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-lg-4 col-md-4 row-tab" id="flowchart-leftContent">
        <div id="pro-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">
                <i class="iconfont changeopen">&#xe6db;</i>
                <span class="changeopen">应用类型</span>
                <i class="iconfont icon-tip">&#xe6a8;</i>
            </div>
            <div class="panel-body common-content">
                <div id="proTreediv" class="ztree changeopen"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-8 col-md-8 row-tab panel-r"  id="flowchart-rightContent">
        <div id="con-panel" class="panel panel-default">
            <div class="panel-body" id="panel-jsp">

            </div>
        </div>
    </div>

</div>

<!-- 服务注册及编排类型 -->
<div id="blankContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="addFolder">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i>
                <span class="m1">添加应用</span>
            </a>
        </li>
    </ul>
</div>
<!-- 文件夹的菜单 -->
<div id="folderContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="updateInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i>
                <span data-toggle="modal" data-target="#proModal">修改类型</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="deleteInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i>
                <span>删除应用</span>
            </a>
        </li>
    </ul>
</div>
<%@ include file="serapplicationmodel.jsp"%>
<script src="<%=webpath %>/resources/js/puborder/serapplication/serapplication.js"></script>
<script src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
</body>
</html>