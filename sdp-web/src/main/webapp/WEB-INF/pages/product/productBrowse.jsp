<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/11/2
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务浏览</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../base.jsp"%>
    <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css"> -->
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css"> -->
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css"> -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/serviceList.css">
    <style>
        table{
            display: none;
        }
        .pagination-detail{
            display: none;
        }
    </style>
    <style>
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
    </style>
    <style>
        @media (min-width: 1400px){
            .col-lg-3 {
                width: 20%;
            }
        }
    </style>
</head>
<body>
    <div class="serviceList-container">
        <div class="serviceList-left">
            <div class="left-title">服务分类</div>
            <div class="left-content">
                <ul id="serviceTree" class="ztree">

                </ul>
            </div>
        </div>
        <div class="nav-collapse-left">
          <img class="collapse-background" src="${pageContext.request.contextPath}/resources/img/serviceType/m1.png">
          <img class="collapse-arrow" src="${pageContext.request.contextPath}/resources/img/serviceType/m2.png">
        </div>
        <div class="nav-collapse-right">
          <img class="collapse-background" src="${pageContext.request.contextPath}/resources/img/serviceType/m1.png">
          <img class="collapse-arrow" src="${pageContext.request.contextPath}/resources/img/serviceType/m2.png">
        </div>
        <div class="serviceList-right">
            <div class="">
                <div class="bconsole-header">
                    <span>产品浏览-服务列表</span>
                </div>
                <div class="bconsole-container">
                    <div class="bconsole-search-container">
                        <button type="button" class="btn btn-default btn-no-radius btn-refresh" aria-label="Left Align" title="刷新">
                            <img src="${pageContext.request.contextPath}/resources/img/serviceType/refresh.png">
                        </button>
                        <form class="form-inline bconsole-fr" style="margin-bottom:0px;">
                            <div class="form-group bconsole-form-group">
                                <input type="text" class="form-control bconsole-form-control bconsole-width-200" id="productNameInput">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                <span class="bconsole-input-placeholder" style="width:150px;left: 15px;">服务名称</span>
                            </div>
                        </form>
                    </div>
                    <%--查询--%>
                    <!-- <form class="form-inline" id="svcManageSearchForm">
                        <div class="form-group">
                            <input type="text" class="form-control" name="productName" placeholder="服务名称">
                        </div>
                        <button type="button" class="btn btn-primary" onclick="$('#productTable').bootstrapTable('refresh');">查询</button>
                        <button type="button" class="btn btn-default" onclick="$('#svcManageSearchForm')[0].reset();">重置</button>
                        <button type="button" class="btn btn-primary" id="showAllProduct">显示全部</button>
                    </form> -->
    
                    <div class="products-list row">
                        <%--<a class="products-item-container">--%>
                            <%--<div class="products-item">--%>
                                <%--<div class="item-img">--%>
                                    <%--<span><img src="${pageContext.request.contextPath}/resources/img/dataModel/database.png"></span>--%>
                                <%--</div>--%>
                                <%--<div class="item-title">--%>
                                    <%--数据库--%>
                                <%--</div>--%>
                                <%--<div class="item-info">--%>
                                    <%--<p></p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</a>--%>
                    </div>
    
                    <%--列表--%>
                    <div class="table-responsive">
                        <table class="table table-hover" id="productTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script type="text/javascript">
	var $webpath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/js/order/productBrowse.js"></script>
</html>
