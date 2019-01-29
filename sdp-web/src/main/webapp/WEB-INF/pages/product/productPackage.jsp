<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/8/24
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务套餐列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/message/message.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
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
</head>
<body class="body-bg">
<div class="">
    <!-- <div class="product-package-left">
        <div class="bconsole-header">
                <span>套餐</span>
        </div>
        <div class="bconsole-container">
            <div class="bconsole-search-container package-search-container">
                <button type="button" class="btn btn-default btn-no-radius btn-bconsole" onclick="toCreatePackage()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增套餐
                </button>
                <button type="button" class="btn btn-default btn-no-radius btn-bconsole-hollow" aria-label="Left Align" onclick="toCreateTryPackage()" disabled>
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增试用
                </button>
                <button type="button" class="btn btn-default btn-no-radius btn-bconsole-hollow" aria-label="Left Align" onclick="toCreateDiscountPackage()" disabled>
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增折扣
                </button>
            </div>

            <%--列表--%>
            <div class="table-responsive">
                <table class="table table-hover" id="packageTable"></table>
            </div>
        </div>
    </div>
    <div class="product-package-right">
        <div class="package-right-content">
            <div class="package-product-icon">
                <img src="${pageContext.request.contextPath}/resources/img/serviceType/product.png" >
            </div>
            <span class="package-product-text">基本信息</span>
            <div class="package-product-info">
                <div class="info-item">
                    <span class="info-title">服务名称:</span>
                    <span class="info-content" id="info-productName"></span>
                </div>
                <div class="info-item">
                    <span class="info-title">服务版本:</span>
                    <span class="info-content" id="info-productVersion"></span>
                </div>
                <div class="info-item">
                    <span class="info-title">服务描述:</span>
                    <span class="info-content" id="info-productIntrod"></span>
                </div>
            </div>
        </div>
    </div> -->
    
    <div class="page-info-header" style="height: 35px;">
        <span class="glyphicon glyphicon-chevron-left" style="cursor: pointer" onclick="window.location.href='${pageContext.request.contextPath}/product/page'"></span>
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>套餐
        <div style="float: right" >
            <button type="button" class="btn btn-primary btn-sm" onclick="toCreatePackage()">新增套餐</button>
            <button type="button" class="btn btn-primary btn-sm" onclick="toCreateTryPackage()" disabled>新增试用</button>
            <button type="button" class="btn btn-primary btn-sm" onclick="toCreateDiscountPackage()" disabled>新增折扣</button>
        </div>
    </div>

    <div id="svcBasicInfo" class="row" style="padding: 0 30px 10px;font-size: 14px;"></div>

    <%--<div  style="color: #337ab7;margin-bottom: 20px;">套餐</div>
    <div id="packageShow">
        &lt;%&ndash;套餐列表&ndash;%&gt;
        &lt;%&ndash;<div class="package-item">
            <div class="row">
                <div class="col-md-12" style="margin-bottom: 10px;">
                    <label style="font-size: 16px">套餐一</label>
                    <span style="float: right;color: red;font-size: 16px">发布</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" style="margin-bottom: 10px;">
                    <label>套餐说明：</label>
                    <span>套餐一</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" style="margin-bottom: 10px;">
                    <label>订购方式：</label>
                    <span>审批</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" style="margin-bottom: 10px;">
                    <label>控制方式：</label>
                    <span>按资源</span>
                </div>
            </div>
            <div class="package-buttons">
                <div class="package-edit">
                    <span class="glyphicon glyphicon-edit icon-hover" title="编辑" onclick="" style="line-height: 30px;"></span>
                </div>
                <div class="package-delete">
                    <span class="glyphicon glyphicon-trash icon-hover" title="删除" onclick="" style="line-height: 30px;"></span>
                </div>
            </div>
        </div>&ndash;%&gt;
    </div>

    <div style="color: #337ab7;margin-bottom: 20px;">试用</div>
    <div id="tryPackageShow">
        <div class="package-null">暂无试用信息</div>
    </div>

    <div style="color: #337ab7;margin-bottom: 20px;">折扣</div>
    <div id="discountPackageShow">
        <div class="package-null">暂无折扣信息</div>
    </div>--%>

    <%--查询--%>
    <form class="form-inline" id="packageSearchForm" style="display: none">
        <div class="form-group">
            <select class="form-control" name="packageStatus">
                <option value="" selected style='display:none;'>产品状态</option>
                <option value="10">暂存</option>
                <option value="20">发布</option>
                <option value="30">上架</option>
                <option value="40">下架</option>
            </select>
        </div>
        <div class="form-group">
            <select class="form-control" name="">
                <option value="" selected style="display:none;">产品类型</option>
                <option value="">套餐</option>
                <option value="">折扣</option>
                <option value="">试用</option>
            </select>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="productName" placeholder="产品名称">
        </div>
        <button type="button" class="btn btn-primary" onclick="$('#packageTable').bootstrapTable('refresh');">查询</button>
        <button type="button" class="btn btn-default" onclick="$('#packageSearchForm')[0].reset();">重置</button>
    </form>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="packageTable"></table>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/productPackage.js"></script>
</html>

