<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <head>
        <title>服务详情</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="#" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/service/service-common.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
        <style>
            .table>tbody>tr>td {
                text-align: center;
                vertical-align: middle;
            }

            .service-tabs {
                width: 77.5%;
                float: left;
                margin-right: 20px;
                padding: 20px;
            }

            .service-detail {
                width: 20%;
                float: left;
                padding: 20px;
                height: 100%;
            }

            body.body-bg {
                padding-left: 1.38%;
                padding-right: 1.38%;
            }

            .service-detail p {
                display: inline-block;
                height: 12px;
                text-align: right;
                font-weight: 400;
                margin: 0 0 20px;
                padding-left: 4.78px;
            }

            .service-detail-label {
                font-weight: bold;
                font-size: 14px;
                color: #da3610;
                float: left;
                position: relative;
                right: 20px;
            }
        </style>
    </head>

    <body class="body-bg">
        <div class="bconsole-container-bg service-tabs">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li id="unProve-li" role="presentation" class="active">
                    <a href="#instanceList" aria-controls="home" role="tab" data-toggle="tab">实例列表</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#topology" aria-controls="profile" role="tab" data-toggle="tab">服务拓扑</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#proxy" aria-controls="profile" role="tab" data-toggle="tab">访问策略</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#dependency" aria-controls="profile" role="tab" data-toggle="tab">服务依赖</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#config" aria-controls="profile" role="tab" data-toggle="tab">配置项</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#storage" aria-controls="profile" role="tab" data-toggle="tab">存储</a>
                </li>
                <li id="proved-li" role="presentation">
                    <a href="#healthCheck" aria-controls="profile" role="tab" data-toggle="tab">健康监测</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="margin-top: 20px">
                <div role="tabpanel" class="tab-pane active" id="instanceList">
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
                            <button type="button" class="btn btn-default btn-bconsole-add" style="margin-left: 7px;">
                                编排创建
                            </button>
                            <button type="button" class="btn btn-default btn-no-radius btn-bconsole-more" style="margin-left: 7px;">
                                更多
                                <i class="fa fa-angle-double-right" style="font-size: 16px"></i>
                            </button>
                            <form class="form-inline bconsole-fr">
                                <div class="form-group bconsole-form-group">
                                    <input type="text" class="form-control bconsole-form-control bconsole-width-200 " placeholder="输入服务名称搜索" id="svcCategorySearch">
                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                </div>
                            </form>
                        </div>

                        <%--列表--%>
                            <div class="table-responsive">
                                <table class="table table-hover" id="serviceTable"></table>
                            </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="topology">
                </div>
                <div role="tabpanel" class="tab-pane" id="healthCheck">
                    <div class="page-info-header">
                        <span>|</span>健康监测
                    </div>
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
                                        <span class="green-font">已启动</span>
                                    </td>
                                    <td></td>
                                    <td>
                                        <span class="operation-font">查看</span>
                                        <span class="operation-font" aria-label="Left Align" data-toggle="modal" data-target="#startMonitorModal">设置</span>
                                        <span class="operation-font">禁用</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>运行时</td>
                                    <td>
                                        <span class="red-font">已禁止</span>
                                    </td>
                                    <td></td>
                                    <td>
                                        <span class="operation-font">查看</span>
                                        <span class="operation-font">设置</span>
                                        <span class="operation-font">禁用</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="bconsole-container-bg service-detail">
            <span class="service-detail-label">|</span>
            <div>
                <p style="padding-left: 0px;">服务名称：</p>
                <span>test-deploy</span>
            </div>
            <div>
                <p>实例数目：</p>
                <span>10</span>
            </div>
            <div>
                <p>资源配置：</p>
                <span> CPU 2C 内存 4G</span>
            </div>
            <div>
                <p>访问地址：</p>
                <span>user/app-server:2.0</span>
            </div>
            <div>
                <p>更新时间：</p>
                <span>2018-12-25 10:54:00</span>
            </div>
        </div>
    </body>
    <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/nicescroll/jquery.nicescroll.js"></script>
    <script type="text/javascript">
        var $webpath = "${pageContext.request.contextPath}";
        $(document).ready(function () { });
        // 重置表单
        function handleResetForm(ele) {
            // $(ele).data("bootstrapValidator").resetForm();
            $(ele)[0].reset();
        }
        $('.envTable').niceScroll({ cursorcolor: "#ccc" });
    </script>

    </html>