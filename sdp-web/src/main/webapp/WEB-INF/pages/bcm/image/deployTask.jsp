<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/20
  Time: 15:35
  Describe: 镜像部署-部署任务
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>镜像部署-部署任务</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .green-title-circle{
            width: 49px;
            height: 49px;
            margin-right: 0;
        }
    </style>
</head>
<body class="body-bg">
<div>
    <div class="image-content-left bconsole-container-bg">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#deployTasks" aria-controls="deployTasks" role="tab" data-toggle="tab">部署任务</a></li>
            <li role="presentation"><a href="#deployHistory" aria-controls="deployHistory" role="tab" data-toggle="tab">部署历史</a></li>

            <div class="btn-group dropdown-form-select" id="moreActions">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    更多 <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="actions-item1"><a href="javascript:;" value="1">配置</a></li>
                    <li class="actions-item2"><a href="javascript:;" value="2">删除</a></li>
                    <li class="actions-item3"><a href="javascript:;" value="3">禁用</a></li>
                </ul>
            </div>

            <button class="btn btn-default btn-bconsole" style="width: 110px;border-radius: 18px;float: right">开始部署</button>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="deployTasks" style="padding-top: 16px">
                <div class="image-steps">
                    <div class="image-step-item image-step-active">
                        <div class="image-step-top">
                            <div class="image-step-icon"></div>
                            <div class="image-step-line"></div>
                        </div>
                        <div class="image-step-title">拉取镜像</div>
                    </div>
                    <div class="image-step-item">
                        <div class="image-step-top">
                            <div class="image-step-icon"></div>
                            <div class="image-step-line"></div>
                        </div>
                        <div class="image-step-title">启动镜像</div>
                    </div>
                    <div class="image-step-end">
                        <div class="image-step-top">
                            <div class="image-step-icon"></div>
                        </div>
                        <div class="image-step-title">启动服务</div>
                    </div>
                </div>

            </div>

            <div role="tabpanel" class="tab-pane" id="deployHistory" style="padding-top: 16px">
                <div class="table-responsive">
                    <table class="table table-hover" id="deployHistoryTable"></table>
                </div>
            </div>
        </div>
    </div>
    <div class="image-content-right bconsole-container-bg">
        <div class="right-basic-header">
            <span class="green-title-circle title-img-container" style="float: none;width: 49px;height: 49px;margin-right: 0;">
                <img src="${pageContext.request.contextPath}/resources/img/common/basic-icon.png" alt="">
            </span>
            <p>基本信息</p>
        </div>
        <ul class="right-basic-info">
            <li>
                任务名称：<span>Web-Build</span>
            </li>
            <li>
                部署次数：<span>25</span>
            </li>
            <li>
                总部署时长：<span>107秒 15分 8小时</span>
            </li>
            <li>
                成功次数：<span>9</span>
            </li>
            <li>
                失败次数：<span>2</span>
            </li>
        </ul>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script>
    $(document).ready(function () {
        getDeployHistoryTable();

        /*$("#moreActions .dropdown-menu li").click(function(e){
            $(this).addClass("active").siblings().removeClass("active");
        });*/

        $('.image-steps').on('click','.image-step-icon',function () {
            $(this).parent().parent().addClass('image-step-active');
        });
    });

    function getDeployHistoryTable() {
        $("#deployHistoryTable").bootstrapTable({
            url: $webpath + "/",
            method: "POST",
            contentType: "application/json",
            dataType: "json",
            classes: 'table-no-bordered',
            cache: false,
            striped: false,
            pagination: true,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 25, 50, 100],
            queryParams: function (params) {
                return JSON.stringify({
                    "page": {
                        "pageNo": (params.offset / params.limit) + 1,
                        "pageSize": params.limit
                    }
                })
            },
            queryParamsType: 'limit',
            responseHandler: function (res) {
                return {
                    "total": res.totalCount,
                    "rows": res.list
                }
            },
            sidePagination: "server",
            clickToSelect: true,
            uniqueId: "",
            columns: [{
                field: '',
                title: '部署编码',
                align: 'center',
                valign: 'middle'
            }, {
                field: '',
                title: '部署时间',
                align: 'center',
                valign: 'middle'
            }, {
                field: '',
                title: '状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
            }, {
                field: '',
                title: '部署时长',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
            }, {
                field: '',
                title: '部署人',
                align: 'center',
                valign: 'middle'
            }, {
                field: '',
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = '';
                    return html;
                }
            }]
        })
    }
</script>
</html>
