<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/9/15
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增折扣</title>
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
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>新增折扣
    </div>

    <div class="row" style="padding: 0 30px 10px;">
        <form class="form-horizontal" id="packageAddForm">
            <div class="form-group">
                <label class="col-md-2"><span class="red-font">＊</span>折扣名称</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="packageName" required>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2"><span class="red-font">＊</span>折扣说明</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="packageIntrdo" required>
                </div>
            </div>
            <div style="background: #eee;text-align: center">折扣内容</div>

            <%--<h4 class="row"><span class="red-font">＊</span><label>基本配置</label></h4>
            <div id="packageBasicAttr" style="margin: 2% 0 0 4%;"></div>--%>

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
                <div class="form-group" style="margin: 2% 0 0 4%;">
                    <div id="timeSlider" style="width: 48%;display: none"></div>

                    <div id="timeSelect" style="width: 34%;display: none">
                        <div class="input-group">
                            <div class="input-group-addon">按时间</div>
                            <select name="" class="form-control">
                                <option value="">1</option>
                                <option value="">2</option>
                            </select>
                            <div class="input-group-addon">日</div>
                        </div>
                    </div>

                    <div id="freqSlider" style="width: 48%;display: none"></div>

                    <div id="freqSelect" style="width: 34%;display: none">
                        <div class="input-group">
                            <div class="input-group-addon">按次数</div>
                            <select name="" class="form-control">
                                <option value="">1</option>
                                <option value="">2</option>
                            </select>
                            <div class="input-group-addon">（单位：100次）</div>
                        </div>
                    </div>
                </div>
                <div class="form-group" style="margin: 0 0 0 4%;">
                    <input type="checkbox" name="" value=""> 折扣
                    <input type="text" class="form-control" style="display: inline-block;width: 12%" value="9.0折">
                </div>
            </div>


            <h4 class="row"><span class="red-font">＊</span><label>折扣配置</label></h4>
            <div id="packageControlResAttr" style="margin: 2% 0 0 4%;"></div>

            <h4 class="row"><span class="red-font">＊</span><label>有效期</label></h4>
            <div style="margin: 2% 0 0 4%;">
                <input id="validityPeriod" type="text" class="form-control" name="" style="width: 33.5%" placeholder="请选择有效期范围">
            </div>

            <div class="row" style="margin: 20px 0 0; text-align: right;">
                <button type="button" class="btn btn-primary" onclick="handleSaveDiscount('10')">保存</button>
                <button type="button" class="btn btn-primary" onclick="handleSaveDiscount('20')">发布</button>
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
<script src="${pageContext.request.contextPath}/resources/js/product/createDiscount.js"></script>
</html>
