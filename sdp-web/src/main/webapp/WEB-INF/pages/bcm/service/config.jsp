<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <head>
        <title>配置文件</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <%@ include file="../../base.jsp"%>
            <link rel="shortcut icon" href="#" />
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/service/service-common.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
            <style>
                .card-child {
                    width: 23%;
                }

                .card-child-inner {
                    height: auto;
                    padding: 30px;
                }

                @media screen and (min-width: 1360px) {
                    .card-child {
                        width: 25%;
                    }
                }

                @media (max-width: 1360px) and (min-width: 1046px) {
                    .card-child {
                        width: 33.333333333333336%;
                    }
                }

                @media (max-width: 1046px) and (min-width: 720px) {
                    .card-child {
                        width: 50%;
                    }
                }

                @media screen and (max-width: 720px) {
                    .card-child {
                        width: 100%;
                    }
                }

                .item-top span {
                    font-size: 20px;
                    color: #333333;
                }

                .item-top {
                    height: 21px;
                }

                .item-top span i {
                    float: right;
                    width: 15px;
                    height: 16px;
                    color: #999999;
                    opacity: 0.8;
                    cursor: pointer;
                }

                .item-top span .delete {
                    background: url('${pageContext.request.contextPath}/resources/img/common/delete.png') no-repeat;
                }

                .item-top span .update {
                    background: url('${pageContext.request.contextPath}/resources/img/common/edit.png') no-repeat;
                }

                .item-top span .delete:hover {
                    background: url('${pageContext.request.contextPath}/resources/img/common/delete_active.png') no-repeat;
                }

                .item-top span .update:hover {
                    background: url('${pageContext.request.contextPath}/resources/img/common/edit_active.png') no-repeat;
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
                    background: url('${pageContext.request.contextPath}/resources/img/bcm/service/relation.png') no-repeat;
                    padding-right: 25px;
                    margin-left: 3px;
                }

                .item-bottom .public-file-size {
                    margin-top: 9px;
                }

                .item-bottom .public-file-size:before {
                    content: "";
                    background: url('${pageContext.request.contextPath}/resources/img/common/user.png') no-repeat;
                    padding-right: 25px;
                    margin-left: 3px;
                }

                .item-bottom .time-label {
                    margin-top: 9px;
                }

                .item-bottom .time-label:before {
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

                .item-bottom .serviceNum {
                    color: #35aae7;
                    cursor: pointer;
                    display: -webkit-inline-box;
                }

                .fixed-table-body {
                    height: auto;
                }

                #env-variate input,
                #env-variate textarea {
                    box-shadow: none;
                    border-radius: 0;
                    border: solid 1px #d9d9d9;
                }

                .table>tbody>tr>td {
                    text-align: center;
                    line-height: 1.42857143;
                    padding: 0;
                    border: 0;
                    vertical-align: middle;
                }

                .tableContent {
                    margin-left: 15px;
                    float: left;
                    padding-right: 0;
                    max-height: 300px;
                }
            </style>
    </head>

    <body class="body-bg">
        <div class="addcode-container bconsole-container-bg">
            <div style="padding: 26px">
                <div class="image-page-title">
                    <h3>配置文件</h3>
                </div>
                <div class="row" style="margin-top: 35px">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                            <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                        </button>
                        <button type="button" class="btn btn-default btn-no-radius btn-bconsole" data-toggle="modal" data-target="#configAddModal">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建
                        </button>
                        <form class="form-inline bconsole-fr">
                            <div class="form-group bconsole-form-group">
                                <input type="text" class="form-control bconsole-form-control bconsole-width-200" placeholder="输入关键字搜索" id="templateNameSearch">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="card-parent-wrap" style="margin-top: 30px">
                    <ul class="card-parent" id="configList">
                    </ul>
                </div>
            </div>
        </div>

        <%--新建model--%>
            <div class="modal fade" id="configAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                <div class="modal-dialog" role="document" style="width:800px;">
                    <div class="modal-content" style="width:800px;">
                        <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#configAddForm')">
                            <a>
                                <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                            </a>
                        </div>
                        <div class="bconsole-modal-title">
                            <span class="green-title-circle title-img-container">
                                <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                            </span>
                            <span class="title">配置文件</span>
                        </div>
                        <div class="bconsole-modal-content" style="padding-left: 69px;padding-right: 110px;">
                            <form class="form-horizontal bv-form" id="configAddForm" novalidate="novalidate">
                                <input type="hidden" name="configId" />
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">模板名称</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control beconsole-biner-input" name="templateName" required placeholder="请输入文件名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="tableContent">
                                        <table class="table table-hover enabled" id="env-variate">
                                            <thead>
                                                <tr>
                                                    <th style="width: 20%">文件名</th>
                                                    <th style="width: 65%">文件内容</th>
                                                    <th style="width: 15%">操作</th>
                                                </tr>
                                            </thead>
                                            <tbody id="env-oper1">
                                                <tr>
                                                    <td>
                                                        <input type="text" class="fileName" style="width: 98%;height: 35px;">
                                                    </td>
                                                    <td>
                                                        <textarea class="file" style="width: 98%;height:200px;margin-bottom: 8px;" wrap="off"></textarea>
                                                    </td>
                                                    <td>
                                                        <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="bconsole-modal-footer">
                            <a class="modal-submit" onclick="addConfigSave()">提交</a>
                        </div>
                    </div>
                </div>
            </div>
            <%--关联服务model--%>
                <div class="modal fade" id="serviceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
                    <div class="modal-dialog" role="document" style="width:650px;">
                        <div class="modal-content" style="width:650px;">
                            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close">
                                <a>
                                    <img src="${pageContext.request.contextPath}/resources/img/common/close.png">
                                </a>
                            </div>
                            <div class="bconsole-modal-title">
                                <span class="green-title-circle title-img-container">
                                    <img src="/xbconsole/resources/img/bcm/service/type-icon.png">
                                </span>
                                <span class="title">关联服务</span>
                            </div>
                            <div class="bconsole-modal-content" style="padding: 20px 40px 40px 40px;">
                                <!-- <div class="bootstrap-table fixed-table-container"> -->
                                <table class="table table-hover" id="serviceTable">
                                    <!-- <thead>
                                            <tr>
                                                <th>服务名称</th>
                                                <th>挂载路径</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <input style="width: 100px" type="text" name="" placeholder="请输入变量名"></input>
                                                </td>
                                                <td>
                                                    <input style="width: 100px" type="text" name="" placeholder="请输入变量值"></input>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input style="width: 100px" type="text" name="" placeholder="请输入变量名"></input>
                                                </td>
                                                <td>
                                                    <input style="width: 100px" type="text" name="" placeholder="请输入变量值"></input>
                                                </td>
                                            </tr>
                                        </tbody> -->
                                </table>
                                <!-- </div> -->
                            </div>
                            <!-- <div class="bconsole-modal-footer">
                                <a class="modal-submit" onclick="addSvcCategorySave()" style="margin-left: 170px;">提交</a>
                            </div> -->
                        </div>
                    </div>
                </div>
                <%--确认框--%>
                    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
                        <div class="modal-dialog" role="document" style="width: 250px;">
                            <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                                <div class="modal-header bconsole-confirm-header">
                                    <h4 class="modal-title">删除信息</h4>
                                </div>
                                <div class="modal-body bconsole-confirm-body">
                                    <p>请问是否删除该模板？</p>
                                    <input type="hidden" name="deleteId" />
                                </div>
                                <div class="modal-footer bconsole-confirm-footer">
                                    <div class="confirm-box-ok" onclick="deleteConfirm()">确定</div>
                                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                                </div>
                            </div>
                        </div>
                    </div>
    </body>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/nicescroll/jquery.nicescroll.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bcm/service/config.js"></script>
    <script type="text/javascript">
        var $webpath = "${pageContext.request.contextPath}";
        $(document).ready(function () { });
        $('.tableContent').niceScroll({ cursorcolor: "#ccc" });
        // 重置表单
        function handleResetForm(ele) {
            // $(ele).data("bootstrapValidator").resetForm();
            $(ele)[0].reset();
            $("input").val('');
            $("textarea").text('');
            $("input[name='templateName']").attr("disabled", false);
        }
        //展示关联的服务
        function showService(configId) {
            console.log("configId:" + configId);
            $("#serviceTable").bootstrapTable({
                url: $webpath + "",
                method: "GET",
                contentType: "application/json",
                dataType: "json",
                classes: 'table-no-bordered',
                cache: false,
                striped: false,
                pagination: false,
                queryParams: function (params) {
                    // return JSON.stringify({
                    //     "page": {
                    //         "pageNo": (params.offset / params.limit) + 1,
                    //         "pageSize": params.limit
                    //     }
                    // })
                    return null;
                },
                queryParamsType: 'limit',
                responseHandler: function (res) {
                    return {
                        "total": res.totalCount,
                        "rows": res.list
                    }
                },
                clickToSelect: true,
                uniqueId: "",
                columns: [{
                    field: '',
                    title: '服务名称',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: '',
                    title: '挂载路径',
                    align: 'center',
                    valign: 'middle'
                }]
            })
        }
    </script>

    </html>