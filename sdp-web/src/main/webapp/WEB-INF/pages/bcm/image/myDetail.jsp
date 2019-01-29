<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2019/1/21
  Time: 10:42
  Describe: 我的镜像-详情页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的镜像-详情页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
</head>
<body class="body-bg">
<div>
    <div class="image-content-left bconsole-container-bg">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#myImage" aria-controls="myImage" role="tab" data-toggle="tab">镜像</a></li>
            <li role="presentation"><a href="#myDescribe" aria-controls="myDescribe" role="tab" data-toggle="tab">描述</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="myImage" style="padding-top: 16px">
                <div class="row">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                            <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                        </button>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover" id="myDetailTable"></table>
                </div>
            </div>

            <div role="tabpanel" class="tab-pane" id="myDescribe" style="padding-top: 16px">

            </div>
        </div>
    </div>

    <%--基本信息--%>
    <div class="image-content-right bconsole-container-bg">
        <div class="right-basic-header">
            <span class="green-title-circle title-img-container" style="float: none;width: 49px;height: 49px;margin-right: 0;">
                <img src="${pageContext.request.contextPath}/resources/img/common/basic-icon.png" alt="">
            </span>
            <p>基本信息</p>
        </div>
        <ul class="right-basic-info">
            <li>
                镜像名称：<span></span>
            </li>
            <li>
                来源：<span></span>
            </li>
            <li>
                版本数目：<span></span>
            </li>
            <li>
                更新时间：<span></span>
            </li>
        </ul>
    </div>

    <%--删除确认框--%>
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width: 250px;">
            <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                <div class="modal-header bconsole-confirm-header">
                    <h4 class="modal-title">删除信息</h4>
                </div>
                <div class="modal-body bconsole-confirm-body">
                    <p>请问是否删除该镜像？</p>
                    <input type="hidden" name="deleteId">
                </div>
                <div class="modal-footer bconsole-confirm-footer">
                    <div class="confirm-box-ok" onclick="deleteImage()">确定</div>
                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bcm/image/myDetail.js"></script>
</html>
