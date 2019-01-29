<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/19
  Time: 20:08
  Describe: 镜像部署
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>镜像部署</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .bconsole-modal-content>div{
            width: 213px;
            height: 183px;
            background-color: #ffffff;
            border: solid 1px #d9d9d9;
            text-align: center;
            padding: 39px 60px 41px 58px;
            cursor: pointer;
        }
        .bconsole-modal-content>div p{
            font-size: 18px;
            color: #333333;
            padding-top: 13px;
        }
        .bconsole-modal-content .selectImage{
            float: left;
        }
        .bconsole-modal-content .choreographySvc{
            float:right;
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 21px 26px;">
    <div class="image-page-title">
        <h3>镜像部署</h3>
    </div>
    <div class="row" style="margin-top: 36px">
        <div class="col-md-12">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-default btn-no-radius btn-bconsole" data-toggle="modal" data-target="#deploySelectModal">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建任务
            </button>
            <form class="form-inline bconsole-fr">
                <div class="form-group bconsole-form-group">
                    <input type="text" class="form-control bconsole-form-control bconsole-width-200" placeholder="输入关键字搜索" id="deploySearch">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </div>
            </form>
        </div>
    </div>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="imageDeployTable"></table>
    </div>

    <%--部署任务选择modal--%>
    <div class="modal fade" id="deploySelectModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:596px;">
            <div class="modal-content" style="width:596px;">
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close">
                    <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/bcm/image/my-image.png" alt="">
                    </span>
                    <span class="title">部署任务选择</span>
                </div>
                <div class="bconsole-modal-content clearfix" style="padding: 107px 40px;">
                    <div class="selectImage">
                        <img src="${pageContext.request.contextPath}/resources/img/bcm/image/deploy-img1.png" alt="">
                        <p>选择镜像</p>
                    </div>
                    <div class="choreographySvc">
                        <img src="${pageContext.request.contextPath}/resources/img/bcm/image/deploy-img2.png" alt="">
                        <p>编排服务</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script>
    $(document).ready(function () {
        getImageDeployTable();
    });

    function getImageDeployTable() {
        $("#imageDeployTable").bootstrapTable({
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
                title: '部署任务',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
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
                title: '健康度',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
            }, {
                field: '',
                title: '创建人',
                align: 'center',
                valign: 'middle'
            }, {
                field: '',
                title: '最新部署时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '';
                }
            },{
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
