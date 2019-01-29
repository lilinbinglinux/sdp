<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/8/25
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>配置列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body class="body-bg">
<div class="container-fluid product-container" style="padding: 20px;">
    <div class="page-info-header">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>配置列表
        <span id="configPoint" class="glyphicon glyphicon-question-sign" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right" data-content="勾选用户可看的属性"></span>
    </div>

    <%--服务信息--%>
    <div id="svcBasicInfo" class="row" style="padding: 0 30px 10px;font-size: 14px;"></div>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="configItemTable"></table>
    </div>

    <div class="row" style="margin: 20px 0 0; text-align: right;">
        <button type="button" class="btn btn-primary" onclick="handleSvcConfig()">保存</button>
        <button type="button" class="btn btn-default" onclick="window.location.href='${pageContext.request.contextPath}/product/page'">取消
        </button>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/configItemPage.js"></script>
</html>
