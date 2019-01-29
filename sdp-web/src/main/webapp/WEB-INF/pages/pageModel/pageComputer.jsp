<%--
 Version v0.0.1
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
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Template</title>
    <link rel="shortcut icon" href="<%= contextPath %>/resources/img/favicon.ico">
    <link rel="stylesheet" href="<%= contextPath %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
    <style type="text/css">
        ::-webkit-scrollbar-track-piece{
            background-color:#fff;
            -webkit-border-radius:0;
        }
        ::-webkit-scrollbar{
            width:8px;
            height:8px;
        }
        ::-webkit-scrollbar-thumb:vertical{
            height:50px;
            background-color:#999;
            -webkit-border-radius:4px;
            outline:2px solid #fff;
            outline-offset:-2px;
            border:2px solid #fff;
        }
        ::-webkit-scrollbar-thumb:hover{
            height:50px;
            background-color:#9f9f9f;
            -webkit-border-radius:4px;
        }
        ::-webkit-scrollbar-thumb:horizontal{
            width:5px;
            background-color:#CCCCCC;
            -webkit-border-radius:6px;
        }
        .v-toolBar {
            display: none;
        }
        .drag {
            border: 0;
        }
    </style>
</head>
<body class="page-model-bg">
<div class="container-fluid"></div>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/jquery/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var projectId = '${pageModel.projectId}';
    var loginId = '${pageModel.loginId}';
    var tenantId = '${pageModel.tenantId}';

    $(function(){
        $( document ).attr( 'title', sessionStorage.getItem( 'pageName' ) );
        $( 'head' ).append( sessionStorage.getItem( 'pageStyle' ) );
        $( 'div' ).append( sessionStorage.getItem( 'pagePureText' ) );
        $( 'body' ).append( sessionStorage.getItem( 'pageJs' ) );
    })
</script>
</body>
</html>
