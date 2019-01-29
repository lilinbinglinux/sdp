<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>服务列表</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <%@ include file="../../base.jsp"%>
            <link rel="shortcut icon" href="#" />
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/service/service.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/service/service-common.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
            <style>
                .stopped-font {
                    color: #da3610;
                }

                .running-font {
                    color: #29cc97;
                    margin-right: 10px;
                }

                .btn-bconsole-add {
                    height: 35px;
                    color: #da3610;
                    border: solid 1px #cc3300;
                    font-family: MicrosoftYaHei;
                    font-size: 14px;
                    border-radius: 0px;
                }

                .btn-bconsole-add:hover,
                .btn-bconsole-add:active,
                .btn-bconsole-add:focus,
                .btn-bconsole-add:active:hover,
                .btn-bconsole-add:focus:hover {
                    color: #cc3300;
                    background: #f5d6cc;
                    border-color: #cc3300;
                }

                .btn-bconsole-more {
                    height: 35px;
                    font-family: MicrosoftYaHei;
                    font-size: 14px;
                    border-radius: 0px;
                    background-color: #ebebeb;
                    border-color: #ebebeb;
                }

                .btn-bconsole-more:hover,
                .btn-bconsole-more:active,
                .btn-bconsole-more:focus,
                .btn-bconsole-more:active:hover,
                .btn-bconsole-more:focus:hover {
                    color: #6f6f6f;
                    background: #cfcfcf;
                    border-color: #cfcfcf;
                }
            </style>
    </head>

    <body class="body-bg">
        <div class="service-container">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li id="unProve-li" role="presentation" class="active">
                    <a href="#serviceList" aria-controls="home" role="tab" data-toggle="tab">服务列表</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#topology" aria-controls="profile" role="tab" data-toggle="tab">应用拓扑</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="margin-top: 20px">
                <div role="tabpanel" class="tab-pane active" id="serviceList">
                    <div class="bconsole-container">
                        <div class="bconsole-search-container">
                            <button type="button" class="btn btn-default btn-no-radius btn-refresh" aria-label="Left Align" title="刷新">
                                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                            </button>
                            <a href="${pageContext.request.contextPath}/bcm/v1/service/serviceCreatePage">
                                <button type="button" class="btn btn-default btn-no-radius btn-bconsole">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>创建服务
                                </button>
                            </a>
                            <button type="button" class="btn btn-default btn-bconsole-add" style="margin-left: 7px;display: none">
                                编排创建
                            </button>
                            <button type="button" class="btn btn-default btn-no-radius btn-bconsole-more" style="margin-left: 7px;display: none">
                                更多
                                <i class="fa fa-angle-double-right" style="font-size: 16px"></i>
                            </button>
                            <form class="form-inline bconsole-fr">
                                <div class="form-group bconsole-form-group">
                                    <input type="text" class="form-control bconsole-form-control bconsole-width-200 " placeholder="输入服务名称搜索" id="serviceNameSearch">
                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                </div>
                            </form>
                        </div>

                        <%--列表--%>
                            <div class="table-responsive" style="margin-top: 15px;">
                                <table class="table table-hover" id="serviceTable"></table>
                            </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="topology">

                </div>
            </div>
        </div>
        <%--确认框--%>
            <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document" style="width: 250px;">
                    <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                        <div class="modal-header bconsole-confirm-header">
                            <h4 class="modal-title">确认信息</h4>
                        </div>
                        <div class="modal-body bconsole-confirm-body">
                            <p class="optInfo">请问是否删除该服务？</p>
                            <input type="hidden" name="serviceId" />
                        </div>
                        <div class="modal-footer bconsole-confirm-footer">
                            <div class="confirm-box-ok" onclick="operationConfirm()">确定</div>
                            <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                        </div>
                    </div>
                </div>
            </div>
            <%--弹性伸缩model--%>
                <div class="modal fade" id="scaleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                    <div class="modal-dialog" role="document" style="width:650px;">
                        <div class="modal-content" style="width:650px;padding-right: 0">
                            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#scaleForm')" style="padding-right: 20px;">
                                <a>
                                    <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                </a>
                            </div>
                            <div class="bconsole-modal-title">
                                <span class="green-title-circle title-img-container">
                                    <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                </span>
                                <span class="title">实例伸缩</span>
                            </div>
                            <div class="bconsole-modal-content" style="padding-left: 19px;padding-right: 68px;">
                                <form class="form-horizontal bv-form" id="scaleForm" novalidate="novalidate">
                                    <input type="hidden" name="serviceId">
                                    <div class="form-group has-feedback">
                                        <label class="col-sm-3 control-label">服务名称</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" name="serviceName" disabled=true>
                                        </div>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label class="col-sm-3 control-label">实例个数</label>
                                        <div class="col-sm-9">
                                            <input type="number" class="form-control" name="instance" min=1>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="bconsole-modal-footer">
                                <a class="modal-submit" onclick="scaleSave()">提交</a>
                            </div>
                        </div>
                    </div>
                </div>
                <%--更改配额model--%>
                    <div class="modal fade" id="quotaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                        <div class="modal-dialog" role="document" style="width:650px;">
                            <div class="modal-content" style="width:650px;padding-right: 0">
                                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#quotaForm')" style="padding-right: 20px;">
                                    <a>
                                        <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                    </a>
                                </div>
                                <div class="bconsole-modal-title">
                                    <span class="green-title-circle title-img-container">
                                        <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                    </span>
                                    <span class="title">更改配额</span>
                                </div>
                                <div class="bconsole-modal-content" style="padding-left: 19px;padding-right: 68px;">
                                    <form class="form-horizontal bv-form" id="quotaForm" novalidate="novalidate">
                                        <input type="hidden" name="serviceId">
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">服务名称</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="serviceName" disabled=true>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">单实例CPU</label>
                                            <div class="col-sm-9">
                                                <input type="number" class="form-control" name="cpu" min=1 step="0.5">
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">单实例内存</label>
                                            <div class="col-sm-9">
                                                <input type="number" class="form-control" name="memory" min=1 step="0.5">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="bconsole-modal-footer">
                                    <a class="modal-submit" onclick="quotaSave()">提交</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--升级model--%>
                        <div class="modal fade" id="upgradeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                            <div class="modal-dialog" role="document" style="width:650px;">
                                <div class="modal-content" style="width:650px;padding-right: 0">
                                    <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#upgradeForm')" style="padding-right: 20px;">
                                        <a>
                                            <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                        </a>
                                    </div>
                                    <div class="bconsole-modal-title">
                                        <span class="green-title-circle title-img-container">
                                            <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                        </span>
                                        <span class="title">滚动升级</span>
                                    </div>
                                    <div class="bconsole-modal-content" style="padding-left: 19px;padding-right: 68px;">
                                        <form class="form-horizontal bv-form" id="upgradeForm" novalidate="novalidate">
                                            <input type="hidden" name="serviceId">
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">服务名称</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" name="serviceName" disabled=true>
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">镜像名称</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" name="imageName" disabled=true>
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">镜像版本</label>
                                                <div class="col-sm-9">
                                                    <select name="imageVersion" id="imageVersion" class="form-control"></select>
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">升级方式</label>
                                                <div class="col-sm-9">
                                                    <div class="form-radio-group" style="margin-right: 25px;">
                                                        <input id="scaleUpgrade" type="radio" name="rollingType" value="scaleUpgrade" checked="checked" data-bv-field="rollingType">
                                                        <label for="scaleUpgrade"></label>
                                                        <span>步长</span>
                                                    </div>
                                                    <div class="form-radio-group" style="margin-right: 25px;">
                                                        <input id="numberUpgrade" type="radio" name="rollingType" value="numberUpgrade" data-bv-field="rollingType">
                                                        <label for="numberUpgrade"></label>
                                                        <span>比例</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label"></label>
                                                <div class="col-sm-9">
                                                    <input type="number" class="form-control" name="stepLength" min=1 style="width: 35%;display: -webkit-inline-box;padding-right: 12px;">
                                                    <span>个</span>
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label"></label>
                                                <div class="col-sm-9">
                                                    <input type="number" class="form-control" name="percentage" min=1 disabled=true style="width: 35%;display: -webkit-inline-box;padding-right: 12px;">%
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="bconsole-modal-footer">
                                        <a class="modal-submit" onclick="upgradeSave()">提交</a>
                                    </div>
                                </div>
                            </div>
                        </div>
    </body>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
    <script type="text/javascript">
        var $webpath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/bcm/service/service.js"></script>

    </html>