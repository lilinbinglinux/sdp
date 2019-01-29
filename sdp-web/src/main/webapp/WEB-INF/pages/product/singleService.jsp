<%--
  User: xumeng
  Update: 2018/1/22
  Time: 14:15
  Describe: 服务列表-实例
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>服务列表-实例</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .dropdown-menu > li > a{
            padding-left: 18px;
        }
        #service-table a.productName{
            cursor: pointer;
        }
        .status-green>span,.status-red>span{
            margin-left: 10px;
        }
        .resource-num{
            margin-left: 8px;
            margin-right: 5px;
        }
        button.svcDetail,button.svcStart,button.svcStop,button.svcEdit{
            margin-right: 5px;
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 25px;">
    <div class="image-page-title">
        <h3>${productId}服务列表</h3>
    </div>
    <div class="row" style="margin-top: 36px">
        <div class="col-md-12">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-default btn-no-radius btn-bconsole" onclick="window.location='${pageContext.request.contextPath}/'" disabled>
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>创建
            </button>

            <div class="input-group beconsole-input-group" style="float: right">
                <div class="input-group-btn">
                    <a type="button" class="btn btn-default dropdown-toggle beconsole-biner-button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="inner-text">运行状态</span>
                        <span class="caret"></span>
                    </a>
                    <select id="caseStatusSelect" style="display:none;">
                        <option value=""></option>
                        <option value="10">未启动</option>
                        <option value="20">运行中</option>
                        <option value="30">停止</option>
                        <option value="40">失败</option>
                        <option value="50">异常</option>
                        <option value="60">未创建</option>
                    </select>
                    <ul class="dropdown-menu" style="min-width: 110px;">
                        <li><a href="javascript:;" value="">显示全部</a></li>
                        <li><a href="javascript:;" value="10">未启动</a></li>
                        <li><a href="javascript:;" value="20">运行中</a></li>
                        <li><a href="javascript:;" value="30">停止</a></li>
                        <li><a href="javascript:;" value="40">失败</a></li>
                        <li><a href="javascript:;" value="50">异常</a></li>
                        <li><a href="javascript:;" value="60">未创建</a></li>
                    </ul>
                </div>
                <div class="beconsole-group-line"></div>
                <input type="text" class="form-control beconsole-biner-input" id="svcNameInput" placeholder="服务名称">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </div>
        </div>
    </div>

    <%--列表--%>
    <div class="table-responsive" style="margin-top: 15px">
        <table class="table table-hover" id="service-table"></table>
    </div>

    <%--删除确认框--%>
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width: 250px;">
            <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                <div class="modal-header bconsole-confirm-header">
                    <h4 class="modal-title">删除信息</h4>
                </div>
                <div class="modal-body bconsole-confirm-body">
                    <p>请问是否删除该服务？</p>
                    <input type="hidden" name="deleteId" id="delete-caseId">
                </div>
                <div class="modal-footer bconsole-confirm-footer">
                    <div class="confirm-box-ok" onclick="deleteCase()">确定</div>
                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--服务详情--%>
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document" style="width:643px;">
        <div class="modal-content" style="width:643px;">
            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close">
                <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
            </div>
            <div class="bconsole-modal-title">
                <span class="green-title-circle title-img-container">
                    <img src="${pageContext.request.contextPath}/resources/img/bcm/image/my-image.png" alt="">
                </span>
                <span class="title">实例详情</span>
            </div>
            <div class="bconsole-modal-content modal-body" style="padding-left: 69px;padding-right: 118px;"></div>
        </div>
    </div>
</div>

<script>
    var productId = '${productId}';
</script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/order/singleService.js"></script>
</body>
</html>