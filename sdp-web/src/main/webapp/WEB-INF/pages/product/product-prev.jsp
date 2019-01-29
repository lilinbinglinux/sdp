<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/8/13
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务注册列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
</head>
<body class="product-outer">
<div class="container-fluid product-container" style="padding: 20px;">
    <%--查询--%>
    <form class="form-inline" id="svcManageSearchForm">
        <div class="form-group">
            <select class="form-control" name="productStatus">
                <option value="" selected style='display:none;'>服务状态</option>
                <%--<option value="10">暂存</option>--%>
                <option value="20">已注册</option>
                <option value="30">上线</option>
                <option value="40">下线</option>
            </select>
        </div>
        <div class="form-group">
            <select class="form-control" name="productTypeId">
                <option value="" selected style="display:none;">服务类型</option>
            </select>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="productName" placeholder="服务名称">
        </div>
        <button type="button" class="btn btn-primary" onclick="$('#productTable').bootstrapTable('refresh');">查询</button>
        <button type="button" class="btn btn-default" onclick="$('#svcManageSearchForm')[0].reset();">重置</button>
        <button type="button" class="btn btn-primary" style="float: right"
                onclick="window.location.href='${pageContext.request.contextPath}/product/regProductPage'">
            注册服务
        </button>
    </form>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="productTable"></table>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/product.js"></script>
</html>
