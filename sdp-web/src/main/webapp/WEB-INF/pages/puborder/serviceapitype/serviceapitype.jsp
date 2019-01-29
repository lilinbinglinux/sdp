<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/10/24
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">



    <%@ include file="../../common-js.jsp"%>
    <%@ include file="../../common-head.jsp"%>
    <title>服务类型</title>
    <%@ include file="../../common-layer-ext.jsp"%>
    <%@ include file="../../common-body-css.jsp"%>
    <script src="<%=request.getContextPath() %>/resources/plugin/datatables/media/js/jquery.dataTables.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/puborder/serviceapitype/serviceapitype.js"></script>
    <script src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>

    <style type="text/css">
        body{
            overflow:hidden;
        }
        .panel-heading > i.iconfont:first-child{
            font-size:20px;
            margin-right:4px;
        }s
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
            height: 95%;
            width: 103%;
        }
        #id-serviceTypeTable th,td {
            text-align:  center!important;
            vertical-align: middle;
        }
        .dataTable a.icon-wrap{
            float: unset!important;
        }
    </style>
</head>
<body>
<div class="row" style="overflow: auto;height: 100%">
    <%--<div class="col-lg-4 col-md-4 row-tab" id="flowchart-leftContent">
        <div id="pro-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">
                <i class="iconfont changeopen">&#xe6db;</i>
                <span class="changeopen">接口类型</span>
                <i class="iconfont icon-tip">&#xe6a8;</i>
            </div>
            <div class="panel-body common-content">
                <div id="proTreediv" class="ztree changeopen"></div>
            </div>
        </div>
    </div>--%>
    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 row-tab" id="flowchart-middleContent" style="height: 98%">
        <div id="pro-panel2" class="panel panel-default common-wrapper" style="height: 97%">
            <div class="panel-heading common-part">
                <i class="iconfont changeopen">&#xe6db;</i>
                <span class="changeopen">服务类型</span>
                <i class="iconfont icon-tip">&#xe6a8;</i>
            </div>
            <div class="panel-body common-content">
                <div id="proTreediv2" class="ztree changeopen"></div>
            </div>
        </div>
    </div>

    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 row-tab" id="flowchart-middleContent2" style="padding-left: 0px;height: 98%">
        <div id="con-panel" class="panel panel-default common-wrapper" style="height: 97%">
            <div class="panel-heading common-part">
                <i class="iconfont changeopen">&#xe6db;</i>
                <span class="changeopen">服务类型详情</span>
            </div>
            <div class="panel-body common-content">
                <table id="id-serviceTypeTable" >
                    <thead>
                    <tr>
                        <th>服务类型ID</th>
                        <th>类型名称</th>
                        <th>父节点</th>
                        <th>当前状态</th>
                        <th>注册时间</th>
                        <th>创建者</th>
                        <th>租户ID</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>

    <%--<div class="col-lg-8 col-md-8 row-tab panel-r"  id="flowchart-rightContent">
        <div id="con-panel" class="panel panel-default">
            <div class="panel-body common-content" id="panel-jsp">
            </div>
        </div>
    </div>--%>

</div>

<!-- 服务注册及编排类型 -->
<div id="blankContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="addFolder">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i>
                <span class="m1">添加内链接</span>
            </a>
        </li>
    </ul>
</div>
<!-- 文件夹的菜单 -->
<div id="folderContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="addFolder">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i>
                <span class="m1">添加内链接</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="updateInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i>
                <span data-toggle="modal" data-target="#proModal">修改类型</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="deleteInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i>
                <span>删除类型</span>
            </a>
        </li>
    </ul>
</div>
<%@ include file="sertypemodel.jsp"%>

</body>
</html>