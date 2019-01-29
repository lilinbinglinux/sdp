<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/19
  Time: 20:07
  Describe: 我的镜像
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>我的镜像</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .help-block{
            width: 130px;
            right: -130px;
        }
        /*.card-child{
            height: 190px;
        }
        .card-child-inner{
            padding: 27px;
            display: block;
        }*/

        .btn-bconsole span{
            font-family: 'Glyphicons Halflings';
            font-size: 14px;
        }

        .my-item-top {
            font-size: 20px;
            color: #333333;
        }
        .my-item-top span{
            color: #35aae7;
        }

        .my-item-bottom {
            text-align: left;
        }

        .my-item-bottom span {
            font-size: 14px;
            color: #666666;
            display: block;
        }
        .my-item-bottom .my-time{
            margin-top: 19px;
        }
        .my-item-bottom .my-time:before,
        .my-item-bottom .my-version:before,
        .my-item-bottom .my-from:before{
            content: "";
            display: inline-block;
            width: 18px;
            height: 18px;
            margin-right: 11px;
            vertical-align: sub;
            background: url('${pageContext.request.contextPath}/resources/img/bcm/image/my-image-icon.png') no-repeat;
        }
        .my-item-bottom .my-time:before{
            background-position: 0 0;
        }
        .my-item-bottom .my-version,.my-item-bottom .my-from{
            margin-top: 12px;
        }
        .my-item-bottom .my-version:before{
            background-position: -24px 0;
        }
        .my-item-bottom .my-from:before{
            background-position: -47px 0;
        }

        /*@media screen and (min-width: 1360px) {
            .card-child{
                width: 25%;
            }
        }
        @media (max-width: 1360px) and (min-width: 1046px){
            .card-child{
                width: 33.333333333333336%;
            }
        }
        @media (max-width: 1046px) and (min-width: 720px){
            .card-child{
                width: 50%;
            }
        }
        @media screen and (max-width: 720px) {
            .card-child{
                width: 100%;
            }
        }*/

        .card-item-container{
            height: 190px;
        }
        .card-item {
            padding: 27px;
        }

        table{
            display: none;
        }
        .table-responsive {
            margin-top: 30px;
        }
        .pagination-detail{
            display: none;
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 21px 26px;">
    <div class="image-page-title">
        <h3>我的镜像</h3>
    </div>
    <div class="row" style="margin-top: 35px">
        <div class="col-md-12">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-default btn-no-radius btn-bconsole" data-toggle="modal" data-target="#uploadImageModal">
                <span class="glyphicon glyphicon-open" aria-hidden="true"></span>上传镜像
            </button>
            <form class="form-inline bconsole-fr">
                <div class="form-group bconsole-form-group">
                    <input type="text" class="form-control bconsole-form-control bconsole-width-200"
                           placeholder="输入镜像名称搜索" id="myImageSearch">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </div>
            </form>
        </div>
    </div>

    <%--<div class="card-parent-wrap" style="margin-top: 15px">
        <ul id="myImageList" class="card-parent">
            <li class="card-child">
                <a class="card-child-inner" href="${pageContext.request.contextPath}/bcm/v1/">
                    <div class="my-item-top">
                        镜像名称：<span>server</span>
                    </div>
                    <div class="my-item-bottom">
                        <span class="my-time">2018-04-18 15:32:42</span>
                        <span class="my-version">最新版本：v1.1 版本数目：10</span>
                        <span class="my-from">代码构建</span>
                    </div>
                </a>
            </li>
        </ul>
    </div>--%>


    <%--镜像卡片展示--%>
    <div class="image-card-list row" style="margin-top: 15px">
        <%--<a class="card-item-container col-xs-12 col-sm-6 col-md-3 col-lg-3"
           href="${pageContext.request.contextPath}/bcm/v1/image/individual/detailPage">
            <div class="card-item">
                <div class="my-item-top">
                    镜像名称：<span>server</span>
                </div>
                <div class="my-item-bottom">
                    <span class="my-time">2018-04-18 15:32:42</span>
                    <span class="my-version">最新版本：v1.1 版本数目：10</span>
                    <span class="my-from">代码构建</span>
                </div>
            </div>
        </a>--%>
    </div>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="myImageTable"></table>
    </div>

    <%--上传镜像model--%>
    <div class="modal fade" id="uploadImageModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:643px;">
            <div class="modal-content" style="width:643px;">
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetImageForm()">
                    <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/bcm/image/my-image.png" alt="">
                    </span>
                    <span class="title">上传镜像</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 69px;padding-right: 118px;">
                    <form class="form-horizontal" id="uploadImageForm">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">镜像名称</span>
                                <span class="red-font">＊</span>
                            </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="imageName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">镜像版本</span>
                            </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="imageVersion">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">描述</span>
                            </label>
                            <div class="col-sm-9">
                                <textarea class="form-control" style="height: 160px;" name="description"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">上传文件</span>
                                <span class="red-font">＊</span>
                            </label>
                            <div class="col-sm-9">
                                <div class="file-group">
                                    <div class="file-icon"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></div>
                                    <input type="file" name="imageFilePath" class="form-control" id="uploadImagefile" accept="aplication/zip">
                                    <div class="file-text" style="color: #333333;"></div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="bconsole-modal-footer">
                    <a class="modal-submit" onclick="uploadImageSubmit()">提交</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bcm/image/myImage.js"></script>
</html>
