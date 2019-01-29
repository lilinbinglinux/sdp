<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <head>
        <title>服务创建</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <%@ include file="../../base.jsp"%>
            <link rel="shortcut icon" href="#" />
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.13.2/dist/css/bootstrap-select.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.12.1/jquery-ui.min.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/service/service-common.css">
            <style>
                .triangle-bottomright {
                    width: 0;
                    height: 0;
                    display: inline-block;
                    position: absolute;
                    bottom: 0;
                    right: 16px;
                    border-bottom: 25px solid red;
                    border-left: 25px solid transparent;
                }

                .triangle-bottomright:before {
                    content: "\f00c";
                    font-family: FontAwesome;
                    color: #ffffff;
                    right: 0px;
                    top: 8px;
                    position: absolute;
                }

                .card-child-inner {
                    height: auto;
                    padding: 25px;
                    border-radius: 5px;
                    border: solid 1px #eeeeee;
                }

                .card-child {
                    margin-bottom: 20px;
                }

                .item-top {
                    height: 21px;
                }

                .item-top span {
                    font-size: 20px;
                    color: #333333;
                }

                .item-top span i {
                    float: right;
                    width: 15px;
                    height: 16px;
                    color: #999999;
                    opacity: 0.8;
                    cursor: pointer;
                }

                .item-bottom {
                    text-align: left;
                }

                .item-bottom span {
                    font-size: 16px;
                    color: #999999;
                    display: block;
                }

                .item-bottom .public-tag {
                    margin-top: 20px;
                }

                .item-bottom .public-tag:before {
                    content: "";
                    background: url('${pageContext.request.contextPath}/resources/img/common/user.png') no-repeat;
                    padding-right: 25px;
                    margin-left: 3px;
                }

                .item-bottom .public-file-size {
                    margin-top: 20px;
                }

                .item-bottom .public-file-size:before {
                    content: "";
                    background-color: #ffffff;
                    background: url('${pageContext.request.contextPath}/resources/img/common/time.png') no-repeat;
                    padding-right: 25px;
                }

                .item-bottom p {
                    font-size: 14px;
                    color: #333333;
                    width: 100%;
                    -webkit-line-clamp: 2;
                    margin: 0 auto;
                }

                /************滑块********************/

                .ui-widget-header {
                    background: red;
                }

                /* .ui-widget-header+.ui-slider-handle {
                    display: none;
                } */

                .ui-state-default,
                .ui-widget-content .ui-state-default {
                    background-color: white;
                    border-radius: 50%;
                    border: 5px solid red;

                }

                .ui-state-default,
                .ui-widget-content .ui-state-default:active {
                    outline: none;

                }

                /**********************************/

                .table>tbody>tr>td {
                    text-align: center;
                    vertical-align: middle;
                }

                .operation-font {
                    cursor: pointer;
                }

                .operation-font {
                    color: #0b8de8;
                }

                .red-font {
                    color: #da3610;
                }

                .green-font {
                    color: #29cc97;
                }

                #healthCheckModal .form-group span {
                    font-size: 14px;
                    font-weight: normal;
                    font-stretch: normal;
                    line-height: 40px;
                    letter-spacing: 0px;
                    color: #333333;
                    margin-left: 12px;
                }

                .bootstrap-select .dropdown-toggle .filter-option {
                    position: inherit;
                    padding-top: 0;
                    padding-bottom: 0;
                    height: auto;
                }

                .bconsole-modal-content {
                    padding-left: 45px;
                }

                #serviceCreateForm {
                    width: 85%;
                    margin: 48px 0 0 57px;
                }

                #serviceCreateForm label {
                    text-align: right;
                    font-size: 14px;
                    color: #333333;
                    font-weight: normal;
                    padding-top: 7px;
                }

                .page-info-header .config-toggle-operate {
                    float: right;
                    cursor: pointer;
                }

                .page-info-header .config-toggle-operate:before {
                    content: '\f106';
                    display: inline-block;
                    font-family: FontAwesome;
                    margin-right: 3px;
                }

                .page-info-header .config-toggle-operate.config-hide-operate:before {
                    content: '\f107';
                }
            </style>
    </head>

    <body>

        <div class="service-create-container bconsole-container-bg">
            <div>
                <form class="form-horizontal" id="serviceCreateForm" novalidate="novalidate">
                    <div class="page-info-header">
                        <span>|</span>基本配置
                        <div class="config-toggle-operate">收起配置
                            <!-- <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span> -->
                        </div>
                    </div>
                    <div style="margin-top: 50px">
                        <div class="form-group">
                            <label class="col-md-2">服务名称</label>
                            <div class="col-md-7">
                                <input type="text" class="form-control" name="serviceName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2">服务描述</label>
                            <div class="col-md-7">
                                <textarea class="form-control" rows="3" name="description" class="description"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2">镜像名称</label>
                            <div class="col-md-7">
                                <select name="imageName" class="selectpicker form-control" id="imageName" data-live-search="true">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2">镜像版本</label>
                            <div class="col-md-7">
                                <select name="imageVersion" class="selectpicker form-control" id="imageVersion" data-live-search="true">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2">资源限制</label>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label class="col-md-1">CPU</label>
                                    <div class="col-md-10">
                                        <div id="slider-cpu" class="col-md-10" style="margin-top: 10px;"></div>
                                        <div class="col-md-1">
                                            <input id="cpu" name="cpu" class="form-control" disabled="true" style="width: 40px;padding: 9px;" />
                                        </div>
                                        <label class="col-md-1">个</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-1">内存</label>
                                    <div class="col-md-10">
                                        <div id="slider-memory" class="col-md-10" style="margin-top: 10px;"></div>
                                        <div class="col-md-1">
                                            <input id="memory" name="memory" class="form-control" disabled="true" style="width: 40px;padding: 9px;" />
                                        </div>
                                        <label class="col-md-1">GB</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2">实例数量</label>
                            <div class="col-md-7">
                                <input class="form-control" type="number" min=1 name="instance" style="width: 60px;padding: 5px;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2">自定义启动命令</label>
                            <div class="col-md-7">
                                <input class="form-control" name="cmd">
                            </div>
                        </div>
                    </div>

                    <div class="page-info-header" style="margin-top: 40px">
                        <span>|</span>端口配置
                        <!-- <button type="button" class="btn btn-default save-hollow-btn addPath">
                            新增
                        </button> -->
                        <div class="config-toggle-operate">收起配置</div>
                    </div>
                    <div style="margin-top: 50px" class="portGroup">
                        <div class="form-group portItem">
                            <label class="col-md-2">端口/协议</label>
                            <div class="col-md-7">
                                <div class="col-md-4">
                                    <input class="form-control port" type="number" name="port" placeholder="端口">
                                </div>
                                <div class="col-md-4">
                                    <select class="form-control protocol" name="protocol">
                                        <option value="TCP">TCP</option>
                                        <option value="UDP">UDP</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <span class="glyphicon glyphicon glyphicon-plus title-circle addPort" aria-hidden="true"></span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="page-info-header" style="margin-top: 40px">
                        <span>|</span>配置项
                        <!-- <button type="button" class="btn btn-default save-hollow-btn" aria-label="Left Align" data-toggle="modal" data-target="#svcCategoryAddModal">
                            导入已保存变量
                        </button> -->
                        <div class="config-toggle-operate">收起配置</div>
                    </div>
                    <div>
                        <div class="" style="margin-top: 50px">
                            <label class="col-md-2">环境变量</label>
                            <button type="button" class="btn btn-default save-hollow-btn" style="margin-left: 0px" aria-label="Left Align" data-toggle="modal"
                                data-target="#selectTemplatesModal">
                                导入已保存变量
                            </button>
                            <button type="button" class="btn btn-default save-hollow-btn envSaveAs" aria-label="Left Align">
                                另存为模板模板
                            </button>
                        </div>
                        <div style="margin-top: 20px" class="envGroup">
                            <div class="form-group envItem">
                                <label class="col-md-2">变量名</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control envKey" name="env">
                                </div>
                                <label class="col-md-1">变量值</label>
                                <div class="col-md-3">
                                    <input class="form-control envValue" name="env">
                                </div>
                                <div class="col-md-2">
                                    <span class="glyphicon glyphicon glyphicon-plus title-circle addEnv" aria-hidden="true"></span>
                                </div>
                            </div>
                        </div>
                        <div class="" style="margin-top: 50px">
                            <label class="col-md-2">配置文件</label>
                            <button type="button" class="btn btn-default save-hollow-btn addConfig">
                                导入配置文件模板
                            </button>
                        </div>
                        <div class="configMapGroup" style="margin-top: 50px">
                            <div id="configMapTemplate" style="display: none">
                                <div class="col-md-3">
                                    <select class="form-control configMap" name="configMap">
                                    </select>
                                </div>
                                <label class="col-md-1">文件数量</label>
                                <div class="col-md-1">
                                    <input class="form-control configNum" value="" name="configNum" disabled="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="page-info-header" style="margin-top: 40px">
                        <span>|</span>健康监测
                        <div class="config-toggle-operate">收起配置</div>
                    </div>
                    <div style="margin-top: 50px">
                        <div class="bootstrap-table fixed-table-container">
                            <table class="table table-hover table-no-bordered" id="">
                                <thead>
                                    <tr>
                                        <th>监测时期</th>
                                        <th>状态</th>
                                        <th>说明</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>启动时</td>
                                        <td>
                                            <span class="red-font startTip" style="float: none;">已禁止</span>
                                        </td>
                                        <td></td>
                                        <td>
                                            <!-- <span class="operation-font" style="display: none">查看</span> -->
                                            <span class="operation-font startMonitor">设置</span>
                                            <span class="operation-font forbidStart" style="display: none">禁用</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>运行时</td>
                                        <td>
                                            <span class="red-font runningTip" style="float: none;">已禁止</span>
                                        </td>
                                        <td></td>
                                        <td>
                                            <!-- <span class="operation-font" style="display: none">查看</span> -->
                                            <span class="operation-font runningMonitor">设置</span>
                                            <span class="operation-font forbidRunning" style="display: none">禁用</span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="page-info-header" style="margin-top: 40px">
                        <span>|</span>数据卷
                        <button type="button" class="btn btn-default save-hollow-btn storage-add" aria-label="Left Align" data-toggle="modal" data-target="#storageAddModal">
                            添加云存储
                        </button>
                        <div class="config-toggle-operate">收起配置</div>
                    </div>
                    <div style="margin-top: 50px">
                        <div class="bootstrap-table fixed-table-container">
                            <table class="table table-hover table-no-bordered" id="storageTable">
                                <thead>
                                    <tr>
                                        <th>存储类型</th>
                                        <th>存储名称</th>
                                        <th>挂载地址</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="storageContent">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>

                <div class="row" style="margin-top: 67px">
                    <div class="col-md-12" style="text-align: center;">
                        <button class="btn btn-default save-solid-btn big-btn create-btn" style="margin-right: 20px">保存</button>
                        <button class="btn btn-default save-hollow-btn big-btn start-btn" style="margin-right: 20px" onclick="window.location.href='${pageContext.request.contextPath}/bcm/v1/service/servicePage'">启动</button>
                        <button class="btn btn-default cancle-btn big-btn" onclick="window.location.href='${pageContext.request.contextPath}/bcm/v1/service/servicePage'">取消</button>
                    </div>
                </div>
            </div>
            <%--新增云存储model--%>
                <div class="modal fade" id="storageAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                    <div class="modal-dialog" role="document" style="width:650px;">
                        <div class="modal-content" style="width:650px;">
                            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#storageAddForm')">
                                <a>
                                    <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                </a>
                            </div>
                            <div class="bconsole-modal-title">
                                <span class="green-title-circle title-img-container">
                                    <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                </span>
                                <span class="title">新增云存储</span>
                            </div>
                            <div class="bconsole-modal-content" style="padding-left: 69px;padding-right: 118px;">
                                <form class="form-horizontal bv-form" id="storageAddForm" novalidate="novalidate">
                                    <div class="form-group has-feedback">
                                        <label class="col-sm-3 control-label">存储类型</label>
                                        <div class="col-sm-9">
                                            <select class="form-control beconsole-biner-input" name="storageType">
                                                <option value="storageFile">文件存储</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group has-feedback name">
                                        <label class="col-sm-3 control-label">存储名称</label>
                                        <div class="col-sm-9">
                                            <select class="form-control beconsole-biner-input" name="storageName" id="storageName">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group has-feedback path" style="display: none">
                                        <label class="col-sm-3 control-label">本机路径</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control beconsole-biner-input" name="hostPath" required>
                                        </div>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label class="col-sm-3 control-label">挂载地址</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control beconsole-biner-input" name="mountPoint" required>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="bconsole-modal-footer">
                                <a class="modal-submit" onclick="addStorageSave()">提交</a>
                            </div>
                        </div>
                    </div>
                </div>

                <%--设置启动时监控model--%>
                    <div class="modal fade" id="healthCheckModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                        <div class="modal-dialog" role="document" style="width:650px;">
                            <div class="modal-content" style="width:650px;padding-right: 0">
                                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#healthCheckForm')" style="padding-right: 20px;">
                                    <a>
                                        <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                    </a>
                                </div>
                                <div class="bconsole-modal-title">
                                    <span class="green-title-circle title-img-container">
                                        <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                    </span>
                                    <span class="title">设置启动时监控</span>
                                </div>
                                <div class="bconsole-modal-content" style="padding-left: 19px;padding-right: 68px;">
                                    <form class="form-horizontal bv-form" id="healthCheckForm" novalidate="novalidate">
                                        <input name="probeType" type="hidden" />
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">类型</label>
                                            <div class="col-sm-9">
                                                <select class="form-control beconsole-biner-input" name="proType">
                                                    <option>http</option>
                                                    <option>tcp</option>
                                                    <option>exec</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="http">
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">请求头</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control beconsole-biner-input" name="httpHeadeName" style="width: 47%;float: left;" placeholder="name">
                                                    <input type="text" class="form-control beconsole-biner-input" name="httpHeadeValue" style="width: 47%;float: left;margin-left: 6%;"
                                                        placeholder="value">
                                                    <!-- <input type="text" class="form-control beconsole-biner-input" name="httpHeade" required> -->
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">检查路径</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control beconsole-biner-input" name="path" required>
                                                </div>
                                            </div>
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">监测端口</label>
                                                <div class="col-sm-9">
                                                    <input type="number" class="form-control beconsole-biner-input" name="httpPort" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tcp" style="display: none">
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">监测端口</label>
                                                <div class="col-sm-9">
                                                    <input type="number" class="form-control beconsole-biner-input" name="tcpPort" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="exec" style="display: none">
                                            <div class="form-group has-feedback">
                                                <label class="col-sm-3 control-label">命令</label>
                                                <div class="col-sm-9">
                                                    <textarea class="form-control beconsole-biner-input execVal" name="exec" rows="3" required style="height: auto;"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">初始化等待时间(秒)</label>
                                            <div class="col-sm-9">
                                                <input type="number" class="form-control beconsole-biner-input" name="initialDelay" required>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">间隔时间(秒)</label>
                                            <div class="col-sm-9">
                                                <input type="number" class="form-control beconsole-biner-input" name="periodDetction" required>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">超时时间(秒)</label>
                                            <div class="col-sm-9">
                                                <input type="number" class="form-control beconsole-biner-input" name="timeDection" required>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">连接成功次数</label>
                                            <div class="col-sm-9">
                                                <input type="number" class="form-control beconsole-biner-input" name="successThreshold" required>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                                <div class="bconsole-modal-footer">
                                    <a class="modal-submit" onclick="healthCheckSave()">提交</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--导入已保存模板model--%>
                        <div class="modal fade" id="selectTemplatesModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                            <div class="modal-dialog" role="document" style="width:650px;">
                                <div class="modal-content" style="width:650px;">
                                    <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close">
                                        <a>
                                            <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                        </a>
                                    </div>
                                    <div class="bconsole-modal-title">
                                        <span class="title" style="margin-right: 30px">选择已保存的模板</span>
                                        <form class="form-inline">
                                            <div class="form-group bconsole-form-group">
                                                <input type="text" class="form-control bconsole-form-control bconsole-width-200 " placeholder="输入模板名称搜索" id="envTemplateNameSearch">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="bconsole-modal-content" style="padding-left: 69px;padding-right: 69px;">
                                        <form class="form-horizontal bv-form" id="selectTemplatesForm" novalidate="novalidate">
                                            <div class="card-parent form-group" id="envList" style="max-height: 380px;">
                                            </div>
                                        </form>
                                    </div>
                                    <div class="bconsole-modal-footer">
                                        <a class="modal-submit" onclick="addEnvSave()">提交</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--另存为环境变量model--%>
                    <div class="modal fade" id="envSaveAsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                        <div class="modal-dialog" role="document" style="width:650px;">
                            <div class="modal-content" style="width:650px;padding-right: 0">
                                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#envSaveAsForm')" style="padding-right: 20px;">
                                    <a>
                                        <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                    </a>
                                </div>
                                <div class="bconsole-modal-title">
                                    <span class="green-title-circle title-img-container">
                                        <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                    </span>
                                    <span class="title">另存为环境变量模板</span>
                                </div>
                                <div class="bconsole-modal-content" style="padding-left: 19px;padding-right: 68px;">
                                    <form class="form-horizontal bv-form" id="envSaveAsForm" novalidate="novalidate">
                                        <div class="form-group has-feedback">
                                            <label class="col-sm-3 control-label">模板名称</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="templateName">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="bconsole-modal-footer">
                                    <a class="modal-submit" onclick="envSave()">提交</a>
                                </div>
                            </div>
                        </div>
                    </div>
        </div>
    </body>
    <script src="${pageContext.request.contextPath}/resources/plugin/nicescroll/jquery.nicescroll.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.13.2/dist/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.13.2/dist/js/i18n/defaults-zh_CN.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bcm/service/service-create.js"></script>
    <script type="text/javascript">
        var $webpath = "${pageContext.request.contextPath}";
        $('.card-parent').niceScroll({ cursorcolor: "#ccc" });
        $("#slider-cpu").slider({
            range: "min",
            value: 1,
            min: 1,
            max: 16,
            slide: function (event, ui) {
                $("#cpu").val(ui.value);
            }
        });
        $("#cpu").val($("#slider-cpu").slider("value"));
        $("#slider-memory").slider({
            range: "min",
            value: 1,
            min: 1,
            max: 64,
            slide: function (event, ui) {
                $("#memory").val(ui.value);
            }
        });
        $("#memory").val($("#slider-memory").slider("value"));
        $('.selectpicker').selectpicker({
            language: 'zh-CN', // 中文
            width: '100%',//宽度
            size: 5, // 设置select高度，同时显示5个值
        });
    </script>

    </html>