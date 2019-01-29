<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/7/13
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单结果</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/mall-common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/footer.css">
    <style>
        .pay-btn {
            margin-top: 30px;
        }

        .result-info {
            text-align: center;
            font-size: 14px;
        }

        .order-result{
            width: 100%;
            padding: 30px;
            background: #fff;
            height: auto;
            min-height: 100%;
        }
        .container{
            width: 85% !important;
        }
    </style>
</head>
<body class="product-outer">
<%@ include file="header.jsp" %>
<div class="container">
    <ol class="breadcrumb" style="margin-top: 10px;padding: 0;background:#f0f2f5">
        <li><a href="${pageContext.request.contextPath}/">首页</a></li>
        <li class="active">订单结果</li>
    </ol>

    <%--订单结果展示--%>
    <div class="row order-result">
        <div class="col-md-12">
            <div class="result-info">
                <p>
                    <i class="glyphicon glyphicon-ok blue-font"></i>
                    <span id="resultInfo"></span>
                    <a class="order-router blue-font" href="${pageContext.request.contextPath}/v1/pro/productOrder/page">订单管理</a>&nbsp;中查看订单
                </p>
                <div id="payBtn"></div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    <%--var productInfosByCat = ${productInfosByCat};--%>
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/mall/header.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mall/orderResult.js"></script>
</html>
