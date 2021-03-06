<%--
 Version v0.0.1
 User songshuzhong@bonc.com.cn
 Copyright (C) 1997-present BON Corporation All rights reserved.
 ------------------------------------------------------------
 Date         Author          Version            Description
 ------------------------------------------------------------
 2018年8月9日 songshuzhong    v0.0.1            修复组件通信
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>移动端预览</title>
    <style type="text/css">
        .v-mobile-frame {
            z-index: 1;
            display: inline-block;
            position: relative;
            height: 100%;
            -webkit-transform-origin: center 0 0;
            transform-origin: center 0 0;
            transition: 0.3s height, 0.3s -webkit-transform;
            transition: 0.3s transform, 0.3s height;
            transition: 0.3s transform, 0.3s height, 0.3s -webkit-transform;
        }
        .v-mobile-frame .v-mobile-frame-top {
            left: calc((320px - 448px) / 2);
            width: 448px;
            height: 72px;
            top: -72px;
            background: url(../../../resources/img/pageModel/phone-top.v4.png) center 0 no-repeat;
            z-index: 1;
        }
        .v-mobile-frame .v-site-shadow {
            height: calc(100vh - 200px);;
            width: 448px;
            left: calc((320px - 448px) / 2);
            background: url(../../../resources/img/pageModel/phone-bg.v4.png) center 0 repeat-y;
        }
        .v-mobile-frame .v-mobile-frame-bottom {
            left: calc((320px - 448px) / 2);
            width: 448px;
            height: 127px;
            bottom: -127px;
            background: url(../../../resources/img/pageModel/phone-bottom.v4.png) center 0 no-repeat;
            z-index: 1;
            pointer-events: all;
        }
        .v-preview-container {
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
            user-select: none;
            position: relative;
            width: 100%;
            height: 100%;
        }
        #v-preview {
            top: 0;
            width: 320px;
            height: 100%;
            border: 0;
            overflow: hidden;
        }
    </style>
</head>
<body style="overflow: hidden; text-align: center;">
<div class="v-mobile-frame">
    <div class="v-block-layer v-bottom"></div>
    <div class="v-mobile-frame-top"></div>
    <div class="v-site-shadow">
        <div class="v-preview-container">
            <iframe id="v-preview" src="<%=contextPath%>/v1/pageModel/pageTemplate/pc" frameborder="0" width="100%" height="100%"></iframe>
        </div>
    </div>
    <div class="v-mobile-frame-bottom"></div>
</div>
</body>
</html>
