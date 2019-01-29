<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/20
  Time: 10:43
  Describe: 公共镜像-详情页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公共镜像-详情页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
</head>
<body class="body-bg">
<div>
    <div class="image-content-left bconsole-container-bg">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#publicDescribe" aria-controls="publicDescribe" role="tab" data-toggle="tab">描述</a></li>
            <li role="presentation"><a href="#publicVersion" aria-controls="publicVersion" role="tab" data-toggle="tab">版本</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="publicDescribe" style="padding-top: 16px">

            </div>

            <div role="tabpanel" class="tab-pane" id="publicVersion" style="padding-top: 16px">
                <div class="table-responsive">
                    <table class="table table-hover" id="publicDetailTable"></table>
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
                镜像名称：<span>Web-Build</span>
            </li>
            <li>
                来源：<span>25</span>
            </li>
            <li>
                版本数目：<span>v1.2.0</span>
            </li>
            <li>
                更新时间：<span>2018-12-5</span>
            </li>
        </ul>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script>
    $(document).ready(function () {
        getPublicDetailTable();
    });
    function getPublicDetailTable() {
        $("#publicDetailTable").bootstrapTable({
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
                title: '镜像名称',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
            }, {
                field: '',
                title: '版本',
                align: 'center',
                valign: 'middle'
            }, {
                field: '',
                title: '大小',
                align: 'center',
                valign: 'middle'
            }, {
                field: '',
                title: '更新时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
            }]
        })
    }
</script>
</html>

