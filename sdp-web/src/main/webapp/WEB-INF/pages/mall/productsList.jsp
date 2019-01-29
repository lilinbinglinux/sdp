<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/6/15
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/mall-common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/footer.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/mall/productsList.css">
</head>
<body class="product-outer">
<%@ include file="header.jsp" %>
<div class="container">
    <ol class="breadcrumb" style="margin-top: 10px;padding: 0;background:#f0f2f5">
        <li><a href="${pageContext.request.contextPath}/">首页</a></li>
        <li class="active">产品列表</li>
    </ol>

    <%--产品筛选--%>
    <div class="pro-all-filter">
        <div id="pro-content-select"></div>
        <div class="pro-content-sequence">
            <span class="glyphicon glyphicon-th-list" style="float: left;padding-right: 5px;line-height: 60px;"></span>
            <span id="proCataName" style="float: left;padding-right: 10px">全部</span>
            <div class="input-group pro-search-input">
                <input type="text" class="form-control" id="pro-search-keyword" placeholder="请输入服务名称">
                <span class="input-group-addon" id="pro-search-btn">
                    <span class="glyphicon glyphicon-search"></span>
                </span>
            </div>

            <div class="pro-sequence pro-sequence-hover" id="default-sequence">默认排序</div>
            <div class="pro-sequence pro-sequence-hover" id="release-time">
                发布时间
                <span class="glyphicon glyphicon-arrow-down" data-value="desc"></span>
                <span class="glyphicon glyphicon-arrow-up sequence-unselect" data-value="asc"></span>
            </div>
            <%--<div class="pro-sequence pro-sequence-hover" id="pro-price">
                价格
                <span class="glyphicon glyphicon-arrow-down" title="降序"></span>
                <span class="glyphicon glyphicon-arrow-up sequence-unselect" title="升序"></span>
            </div>--%>
            <div style="display: inline-block;float: right;margin-right: 30px">
                为您找到
                <span style="color:#ff1c29;padding: 0 5px" id="proNumber"></span>
                个服务产品
            </div>
        </div>
    </div>

    <%--产品展示--%>
    <div class="products-list-content product-container">
        <%--产品列表--%>
        <div id="proListShow">
            <%--<div class="pro-list-item">
                <div class="pro-img">
                    <img src="${pageContext.request.contextPath}/resources/img/mall/cate_img.png" alt="">
                </div>
                <div class="pro-left">
                    <div class="pro-name">容器云</div>
                    <div class="pro-describe">产品描述产品描述产品描述产品描述产品描述产品描述产品描述产品描述产品描述产品描述产品描述产品描述产品描述</div>
                    <div class="pro-info">
                        <span>发布时间： 2018-09-13</span>
                        <span>版本号： v1.0</span>
                    </div>
                </div>
                <div class="pro-right">
                    <div style="float: right">
                        <h3>免费提供</h3>
                        <a href="">
                            <button class="btn btn-primary">立即申请</button>
                        </a>
                    </div>
                </div>
            </div>--%>
        </div>

        <%--产品分页--%>
        <div id="pro-pagination"></div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery.lazyload.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    <%--var productInfosByCat = ${productInfosByCat};--%>
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/mall/header.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mall/productsList.js"></script>
</html>
