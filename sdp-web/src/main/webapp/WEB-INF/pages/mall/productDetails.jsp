<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/6/15
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品购买</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.1/themes/default/slider.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/mall-common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/footer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/productDetails.css">
</head>
<body class="product-outer">
<%@ include file="header.jsp" %>
<%@ include file="controlGroup.jsp" %>
<div id="products-buy" class="container">
    <ol class="breadcrumb" style="margin-top: 10px;padding: 0;background:#f0f2f5">
        <li><a href="${pageContext.request.contextPath}/">首页</a></li>
        <li class="active">产品申请</li>
    </ol>

    <div class="products-buy">
        <%--产品信息--%>
        <div class="products-details-info" id="proDetailInfo">
            <%--<div class="pro-logo" style="display: inline-block">
                <img src="${pageContext.request.contextPath}/resources/img/mall/default_img.png" alt="">
            </div>
            <div style="display: inline-block;width: 68%;margin-left: 30px;">
                <h3>mysql</h3>
                <p class="row">
                    <span class="col-md-12">产品概述：服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述服务描述</span>
                </p>
                <span class="pro-version">版本：v1</span>
            </div>--%>
        </div>


        <%--产品套餐--%>
        <div class="products-details-package">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li id="package-show" role="presentation" class="active"><a href="#recommend" aria-controls="recommend" role="tab" data-toggle="tab">推荐套餐</a></li>
                <li id="custom-show" role="presentation"><a href="#customize" aria-controls="customize" role="tab" data-toggle="tab">个性定制</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="border: 1px solid #ddd;border-top: none;">
                <div role="tabpanel" class="tab-pane active" id="recommend">
                    <form id="packageForm" class="form-horizontal">
                        <%--<div class="row" id="packageBaseSet">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                套餐配置
                            </div>
                            <div id="packageBaseAttr" style="margin: 4% 0 0 4%;font-size: 14px;"></div>
                        </div>--%>
                        <%--<div class="row">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                套餐说明
                            </div>
                            <div id="prackageDetailInfo" style="margin: 4% 0 0 4%;font-size: 14px;"></div>
                        </div>--%>

                        <div id="prackageDetailInfo" style="margin: 10px 0 0 15px;font-size: 14px;"></div>

                        <div class="row" style="display: none" id="approvalProcess">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                审批流程
                            </div>
                            <div style="margin: 4% 0 0 4%;font-size: 14px;">
                                <div class="row">
                                    <div class="col-md-6" style="margin-left: -15px;" id="packageApproval"></div>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin-top: 30px">
                            <div class="col-md-6" style="float: right" id="priceShow">
                                总价格：
                                <span class="red-font" style="font-size: 26px;" id="packagePrice"></span>
                            </div>
                        </div>

                        <div class="products-details-btn">
                            <button type="button" class="btn btn-primary pro-btn-buy" onclick="packageBuy()">确定</button>
                            <button type="button" class="btn btn-primary pro-btn-cancel" id="packageCancleBtn">取消
                            </button>
                        </div>
                    </form>
                </div>
                <div role="tabpanel" class="tab-pane" id="customize">
                    <form id="customForm" class="form-horizontal">
                        <div class="row" id="customDescription" style="display: none;margin: 4% 0 0 4%;font-size: 15px;color: #777;">
                            该产品无任何配置，无法申请！
                        </div>

                        <div class="row" id="customBaseSet" style="display: none">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                基本配置
                            </div>
                            <div id="customBaseAttr" style="margin-top: 5%;"></div>
                        </div>

                        <div class="row" id="customSet" style="display: none">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                定制配置
                            </div>
                            <div class="col-md-12 row" style="display:none;margin: 30px 0 20px -15px !important;" id="slider-div">
                                <div class="col-md-4" style="margin-left: 4%">
                                    <input class="easyui-slider" style="width:300px" data-options="showTip:true"
                                           id="resource-slider">
                                </div>
                                <div class="col-md-6">
                                    <input class="easyui-numberspinner customSetClass" value="0"
                                           data-options="min:0,max:100,required:true,increment:1" style="width:120px;"
                                           id="numberspinner">&nbsp&nbsp月
                                </div>
                            </div>
                            <div id="customAttr" style="margin-top: 5%;"></div>
                        </div>



                        <%--<div class="row">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                购买方式
                            </div>
                            <div style="margin: 4% 0 0 4%;font-size: 14px;">
                                <div class="row">
                                    <div class="col-md-12" style="margin-left: -15px;">
                                        <label class="radio-inline">
                                            <input type="radio" name="radioName" id="timeRadio">按时间
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="radioName" id="resourceRadio">按用量
                                        </label>
                                    </div>
                                </div>
                                <div id="timeRadioInfo" class="row" style="margin-top: 20px;display: none">
                                    <div class="col-md-12" >
                                        <input type="number" class="form-control" min="0" max="100" style="width:120px;margin-right: 5px;display: inline-block"><span>天</span>
                                    </div>
                                </div>
                                <div id="resourceRadioInfo" class="row" style="margin-top: 20px;display: none">
                                    <div class="col-md-12" >
                                        <input type="number" class="form-control" min="0" max="100" style="width:120px;margin-right: 5px;display: inline-block">次
                                    </div>
                                </div>
                            </div>
                        </div>--%>

                        <div class="row" style="margin-top: 30px;display: none" id="serviceApproval">
                            <div class="col-md-12" style="font-size: 20px;font-weight: bold">
                                <span class="red-font">＊</span>
                                审批流程
                            </div>
                            <div style="margin: 4% 0 0 4%;font-size: 14px;">
                                <div class="row">
                                    <div class="col-md-6" style="margin-left: -15px;" id="customApproval"></div>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin-top: 30px">
                            <div class="col-md-6" style="float: right" id="servicePrice">
                                总价格：
                                <span class="red-font" style="font-size: 26px;" id="totalPrice">￥0</span>
                            </div>
                        </div>

                        <div class="products-details-btn">
                            <button type="button" class="btn btn-primary pro-btn-buy" id="customBuyBtn" onclick="customBuy()">确定</button>
                            <button type="button" class="btn btn-primary pro-btn-cancel" id="customCancelBtn">取消
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <%--套餐详情信息--%>
        <div id="package-details-info" style="display: none">
            <div class="package-details-header">套餐信息</div>
            <div id="package-res-info"></div>
        </div>

        <%--个性定制详情信息--%>
        <div id="custom-details-info" style="display: none">
            <div class="package-details-header">定制信息</div>
            <div id="custom-res-info"></div>
        </div>
    </div>
</div>

<%--核对订单信息--%>
<div id="products-Check" class="container" style="display: none;">
    <ol class="breadcrumb" style="margin-top: 10px;padding: 0;background:#f0f2f5">
        <li><a href="${pageContext.request.contextPath}/">首页</a></li>
        <li class="active">核对订单信息</li>
    </ol>

    <div class="products-Check">
        <table class="order-list">
            <thead>
            <tr id="order-title">
                <td>产品名称</td>
                <td>基本配置</td>
                <td>计费方式</td>
                <td>购买量</td>
                <td>总费用</td>
            </tr>
            </thead>
            <tbody>
            <tr id="order-details"></tr>
            </tbody>
        </table>

        <div id="orderButton" class="products-details-btn" style="margin-bottom: 50px">
            <button type="button" class="btn btn-primary pro-btn-buy">提交订单</button>
            <button type="button" class="btn btn-primary pro-btn-cancel" onclick="$('#products-buy').show();$('#products-Check').hide();formValidator('#packageForm');
    formValidator('#customForm');">返回</button>
        </div>

        <%--蒙版遮罩层--%>
        <div id="maskLayer" style="display: none">
            <div class="popWindow"></div>
            <div class="maskLayer">正在提交订单，请稍等……</div>
        </div>
    </div>
</div>

<%--嵌入bcm--%>
<iframe src="http://bconsole.sdp.com/paas/singleSignOn" style="height: 50%;width: 70%;display: none"></iframe>

<%@ include file="footer.jsp" %>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    <%--var productInfosByCat = ${productInfosByCat};--%>
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/mall/header.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mall/productDetails.js"></script>
</html>