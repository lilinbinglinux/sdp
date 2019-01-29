<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/11/28
  Time: 14:39
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
    <%@ include file="../../common-head.jsp"%>
    <title>代订阅</title>
    <%@ include file="../../common-layer-ext.jsp"%>
    <%@ include file="../../common-body-css.jsp"%>

</head>

<body>
<div class="row">
    <div class="col-lg-12 col-md-12 row-tab">
        <div id="org-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">

                <form class="bs-example bs-example-form" role="form">
                    <span style="font-size: 2rem; font-weight: 600;color: #cc3333;padding-left: 3rem; line-height: 100%">代订阅</span>
                    <a class="iconfont" id="search" href="#">&#xe67a;</a>
                    <span style="padding-right: 5em;float: right">&nbsp;</span>
                    <input type="hidden" id="search-text" class="form-control">

                </form>

            </div>

            <div class="panel-body common-content" style="padding: 0%">
                <input id="isTypeOpen" type="hidden" value="open"/>

                <!-- 侧边类型栏 -->
                <div id="leftType" class="col-lg-2 col-md-2" style="height: 97%;background-color: #eaeaea;display: block;margin-right: 3em;">
                    <div id="typeDisplayTree" class="ztree"></div>
                </div>
                <div id="pull-left" class="pull-left">
                    <a href="javascript:void(0);"><img id="pull-left-img" src="<%=webpath %>/resources/img/puborder/pull-left.png" alt="向右拉"/></a>
                </div>

                <!-- 接口显示 -->
                <div id="id-serviceDisplay-div">
                    <div style="margin-top: 2em;margin-left: 3em;">
                        <ul id="serviceDisplayUl">
                            <div style="clear: both;"></div>
                        </ul>

                    </div>
                </div>

                <!-- 分页导航栏 -->
                <div id="id-selectPage-div" style="position: absolute;">
                    <ul class="pagination">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="../../common-js.jsp"%>
<script src="<%=webpath %>/resources/js/puborder/apistore/warehouse.js"></script>
</body>
</html>
