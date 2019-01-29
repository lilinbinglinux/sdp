<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/8/25
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改套餐</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/theme/default/laydate.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.1/themes/default/slider.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body class="body-bg">
<div class="container-fluid product-container">
    <div class="page-info-header">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>修改套餐
    </div>

    <div class="row" style="padding: 0 30px 10px;">
        <form class="form-horizontal" id="packageEditForm">
            <div class="form-group">
                <label class="col-md-2"><span class="red-font">＊</span>套餐名称</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="packageName"
                           required data-bv-notempty-message="请输入套餐名称">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2"><span class="red-font">＊</span>套餐说明</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="packageIntrdo"
                           required data-bv-notempty-message="请输入套餐说明">
                </div>
            </div>
            <div style="background: #eee;text-align: center">套餐内容</div>

            <h4 class="row">
                <label style="margin-left: 15px">基本配置</label>
                <span id="packageBasicPoint"
                      class="glyphicon glyphicon-question-sign blue-font"
                      data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right"
                      data-content="如果勾选某一属性，则该属性的数值显示为固定值，用户不可修改！"
                ></span>
            </h4>
            <div id="packageBasicAttr" style="margin: 2% 0 0 4%;"></div>

            <h4 class="row"><span class="red-font">＊</span><label>订购方式</label></h4>
            <div class="form-group" style="margin: 2% 0 0 4%;" id="orderTypeShow"></div>

            <div style="display: none">
                <h4 class="row"><span class="red-font">＊</span><label>审批流程</label></h4>
                <div class="form-group" style="margin: 2% 0 0 4%;" id="approvalProcessShow"></div>
            </div>


            <div id="controlTypeShow" style="display: none">
                <h4 class="row"><span class="red-font">＊</span><label>控制方式</label></h4>
                <div class="form-group" style="margin: 2% 0 0 4%;">
                    <label class="radio-inline">
                        <input type="radio" name="controlType" value="10"> 按时间
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="controlType" value="20"> 按次数
                    </label>
                </div>
            </div>

            <h4 class="row"><span class="red-font">＊</span><label>资源配置</label></h4>
            <div id="packageControlResAttr" style="margin: 2% 0 0 4%;"></div>

            <%--<h4 class="row"><span class="red-font">＊</span><label>折扣</label></h4>
            <div style="margin: 2% 0 0 4%;">
                <div class="box-set">
                    <span class="attr-name">原价：</span>
                    <span class="input-set">0</span>
                </div>
                <div class="box-set">
                    <span class="attr-name">折后价格：</span>
                    <input type="text" class="form-control input-set" name="">
                </div>
            </div>

            <h4 class="row"><span class="red-font">＊</span><label>有效期</label></h4>
            <div style="margin: 2% 0 0 4%;">
                <input id="validityPeriod" type="text" class="form-control" name="" style="width: 33.5%" placeholder="请选择有效期范围">
            </div>--%>

            <div class="row" style="margin: 20px 0 0; text-align: right;">
                <button type="button" class="btn btn-primary" onclick="handleSavePackage('10')">保存</button>
                <button type="button" class="btn btn-primary" onclick="handleSavePackage('20')">发布</button>
                <button type="button" class="btn btn-default" onclick="self.location=document.referrer;">取消
                </button>
            </div>
        </form>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/laydate.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/updatePackage.js"></script>
</html>
