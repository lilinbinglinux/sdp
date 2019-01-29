<%--
 Version v0.0.2
 User songshuzhong@bonc.com.cn
 Copyright (C) 1997-present BON Corporation All rights reserved.
 ------------------------------------------------------------
 Date         Author          Version            Description
 ------------------------------------------------------------
 2018年9月20日 songshuzhong    v0.0.1            添加移动端预览
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <title>可视化布局</title>
    <jsp:include page="../codeMirror-head.jsp"></jsp:include>
    <link rel="shortcut icon" href="<%= contextPath %>/resources/css/pageModel/favicon.png">
    <link rel="stylesheet" href="<%= contextPath %>/resources/css/pageModel/show-hint.css">
    <link rel="stylesheet" href="<%= contextPath %>/resources/css/pageModel/sidebar.css">
    <script type="text/javascript">
        var pageModel = ${ pageModel };
        var path = '../../..';
        var version = '/v1';
        var projectId = pageModel.projectId;
        var loginId = pageModel.loginId;
        var tenantId = pageModel.tenantId;
    </script>
    <style type="text/css">
        body {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-size: 14px;
            line-height: 1.42857143;
            color: #333;
            background-color: #fff;
            overflow: hidden;
        }
        a {
            color: #777;
        }
        a:hover {
            cursor: pointer;
            color: #003869!important;
        }
        .v-main-sidebar {
            top: 0;
            left: 0;
            width: 230px;
            height: calc(100% - 52px);
            position: absolute;
            transition: width .2s;
            overflow-y: auto;
            z-index: 810;
            background-color: #f8f8f8;
        }
    </style>
</head>
<body id="v-body-drop">
<nav class="navbar navbar-default" style="z-index: 999;margin-bottom: 0;">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <nav>
                <a class="navbar-brand">
                    <img src="<%= contextPath %>/resources/css/pageModel/favicon.png" width="30" height="30" style="display: inline-block; margin: -0.25rem 0.75rem 0 0;" alt="">可视化布局系统
                </a>
            </nav>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a><span class="glyphicon glyphicon-align-justify"></span></a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown">预览<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a onclick="directToTemplate(0)">
                                <span class="glyphicon glyphicon-blackboard"></span>&nbsp;PC端
                            </a>
                        </li>
                        <li>
                            <a onclick="directToTemplate(1)">
                                <span class="glyphicon glyphicon-phone"></span>&nbsp;移动端
                            </a>
                        </li>
                    </ul>
                </li>
                <li><a id="v-cleanLayout">清空</a></li>
                <li><a id="v-savePageModel">保存</a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">搜索</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="javascript:;" id="v-goBack"><span class="glyphicon glyphicon-share-alt"></span>&nbsp;返回</a></li>
            </ul>
        </div>
    </div>
</nav>
<aside class="v-main-sidebar" style="margin-top: 52px">
    <ul id="v-side-bar-menu" class="v-sidebar v-sidebar-menu"></ul>
</aside>
<aside id="v-main-container" class="container drop-helper-container" data-uuid="container"></aside>
<div id="v-editorModal" class="modal fade">
    <ul id="v-previewTabs" class="nav nav-tabs" style="background-color: white">
        <li class="active"><a href="#html" data-toggle="tab">HTML</a></li>
        <li><a href="#js" data-toggle="tab">JS</a></li>
        <li><a href="#cs" data-toggle="tab">CS</a></li>
        <li><a href="#" id="v-saveEditorial">保存</a></li>
        <li><a href="#" id="v-closeEditorial">关闭</a></li>
    </ul>
    <div class="tab-content" style="height: calc(100vh - 42px);">
        <div class="tab-pane fade in active" style="height: 100%;" id="html">
            <textarea id="htmlEditor" name="htmlEditor"></textarea>
        </div>
        <div class="tab-pane fade" style="height: 100%;" id="js">
            <textarea id="jsEditor" name="jsEditor"></textarea>
        </div>
        <div class="tab-pane fade" style="height: 100%;" id="cs">
            <textarea id="csEditor" name="csEditor"></textarea>
        </div>
    </div>
</div>
<div class="modal fade" id="v-shareModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">保存</h4>
            </div>
            <div class="modal-body">保存成功</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<iframe id="v-pureHtmlTemplate" style="display: none"></iframe>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/jquery-ui-1.11.0/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/js/pageModel/visualizeUtil.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/js/pageModel/sidebar.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/js/pageModel/navTools.js"></script>
</body>
</html>
