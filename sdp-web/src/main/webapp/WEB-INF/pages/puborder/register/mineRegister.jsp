<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/12/18
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%
    String webpath = request.getContextPath();
%>
<head>
    <title>我的注册</title>
    <%@ include file="../../common-head.jsp" %>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-body-css.jsp" %>
    <%@ include file="../../common-js.jsp" %>
    <script type="text/javascript" src="<%=webpath %>/resources/js/puborder/register/mineRegister.js"></script>
</head>

<script>
    var serFlowType = '${serFlowType}';
</script>

<style>
    #id-publishMineTable th,td {
        text-align:  center!important;
    }
    .dataTable a.icon-wrap{
        float: unset!important;
    }
    .dataTables_paginate {
        position: absolute;
        bottom: 20px;
        right: 30px;
    }
</style>
<body>
    <div class="row">
        <div class="col-lg-12 col-md-12 row-tab">
            <div id="org-panel" class="panel panel-default common-wrapper">
                <div class="panel-heading common-part">
                    <i class="iconfont">&#xe6ca;</i><span>我的注册</span>
                </div>
                <div class="panel-body common-content">
                    <div class="searchWrap">
                        <form class="form-inline" id="id-mineSearchForm">
                            <div class="form-group">
                                <label for="serName">名称:</label>
                                <input type="text" class="form-control inpu-sm" id="serName" name="serName"/>
                            </div>
                            <button type="button" class="b-redBtn btn-i" id="id-searchBtn">
                                <i class="iconfont">&#xe67a;</i>查询
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-resetBtn">
                                <i class="iconfont">&#xe647;</i>重置
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-addtBtn">
                                <i class="iconfont"></i>新建
                            </button>
                        </form>
                    </div>

                    <table id="id-publishMineTable">
                        <thead>
                            <tr>
                                <th>服务名称</th>
                                <th>服务类型</th>
                                <th>服务编码</th>
                                <th>url</th>
                                <th>注册时间</th>
                                <th>当前状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>

                </div>
            </div>
        </div>
    </div>
</body>
</html>
