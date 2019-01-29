<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/10/19
  Time: 10:20
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
    <title>服务商城</title>
    <%@ include file="../../common-layer-ext.jsp"%>
    <%@ include file="../../common-body-css.jsp"%>
    <style>
        html{
            font-size:12px;
        }
        img{
            width: auto;
            height: auto;
            max-width: 100%;
            vertical-align: middle;
            border: 0;
        }
        #id-serviceDisplay-div ul li{
            list-style: none;
            float: left;
            margin-top: 1.5em;
            margin-bottom: 1.5em;
            margin-right: 2em;
            margin-left: 2em;
        }

        #id-selectPage-div ul li{
            margin-right: 0px;
            margin-left: 0px;
            margin-top: 3em;
        }
        .version-provider-wrapper{
            width: 13rem;
            height: 13rem;
            border: 1px solid #ccc;

            display: block;
            font-weight: 100;
            overflow: hidden;
            white-space: nowrap;
            position: relative;
        }
        .thumbnail{
            border: 0px;
        }
        .thumbnail img{
            margin-left: 0px;
            padding: 0px;
            width: 80px;
            height: 80px;
        }
        .pull-left{
            height: 2.5em;
            width: 2.5em;
            position: absolute;
        }
        .ztree{
            overflow: auto;
            height: inherit;
            width: 100%;
            min-width: 180px;
        }
        #search{
            padding-right: 5rem;
            font-size: 1.5rem;
            color: #d6d6d6;
            position: absolute;
            right: 2.8em;
            top: 0.8em;
            color: #e15b52;

        }
        #search-text{
            width: 12em;
            border-radius:1em;
            height: 1.6em;
            float: right;
            border-color: #e15b52;

            /*绑定动画元素s*/
            animation: myfirst 0.5s;
            -moz-animation: myfirst 0.5s;	/* Firefox */
            -webkit-animation: myfirst 0.5s;	/* Safari 和 Chrome */
        }

        @keyframes myfirst
        {
            0%   {left:0em; top:0em; width:0em}
            100% {left:0em; top:0em; width:12em}
        }

        #id-selectPage-div ul li a{
            color: red;
        }
        .pagination .active a{
            background-color: white;
            border-color: red;
        }
        .panel-body{
            height:auto!important;
        }
    </style>
</head>

<body>
<div class="row" style="overflow: auto;height: 100%;">
    <div class="col-lg-12 col-md-12 row-tab" style="height: 98%">
        <div id="org-panel" class="panel panel-default common-wrapper" style="height: 99%">
            <div class="panel-heading common-part">
                <form class="bs-example bs-example-form" role="form">
                    <span style="font-size: 2rem; font-weight: 600;color: #cc3333;padding-left: 3rem; line-height: 100%">服务商城</span>
                    <a class="iconfont" id="search" href="#">&#xe67a;</a>
                    <span style="padding-right: 5em;float: right">&nbsp;</span>
                    <input type="hidden" id="search-text" class="form-control">
                </form>
            </div>

            <div class="panel-body common-content" style="padding: 0%;background-color: white;height: auto">
                <input id="isTypeOpen" type="hidden" value="open"/>

                <!-- 侧边类型栏 -->
                <div id="leftType" class="col-lg-2 col-md-2 col-sm-2 col-xs-2" style="background-color: #ffffff;display: block;height: 29em">
                    <div id="typeDisplayTree" class="ztree" style="background: white;margin-top:40px;border:2px solid #eaeaea;overflow:auto;"></div>
                </div>
                <%--<div id="pull-left" class="pull-left">
                    <a href="javascript:void(0);"><img id="pull-left-img" src="<%=webpath %>/resources/img/puborder/pull-left.png" alt="向右拉"/></a>
                </div>--%>

                <!-- 接口显示 -->
                <div id="id-serviceDisplay-div" class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
                    <div style="margin-top: 2em;margin-left: 3em;">
                        <ul id="serviceDisplayUl">
                            <div style="clear: both;"></div>
                        </ul>
                    </div>

                    <!-- 分页导航栏 -->
                    <div id="id-selectPage-div" style="float: right;padding-right: 3em;">
                        <ul class="pagination">
                        </ul>
                    </div>
                </div>

                <%--<!-- 分页导航栏 -->
                <div id="id-selectPage-div" style="">
                    <ul class="pagination">
                    </ul>
                </div>--%>
            </div>
        </div>
    </div>
</div>


<%@ include file="../../common-js.jsp"%>
<script src="<%=webpath %>/resources/js/puborder/apistore/warehouse.js"></script>
</body>
<script type="text/javascript">
    var loginId = '${loginId}';
    var serFlowType = '${serFlowType}';
</script>
</html>