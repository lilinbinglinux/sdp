<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/8/13
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务分类信息</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../base.jsp"%>
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css"> -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <style>
           ::-webkit-scrollbar-track-piece{
               background-color:#fff;
               -webkit-border-radius:0;
           }
           ::-webkit-scrollbar{
               width:8px;
               height:8px;
           }
           ::-webkit-scrollbar-thumb:vertical{
               height:50px;
               background-color:#999;
               -webkit-border-radius:4px;
               outline:2px solid #fff;
               outline-offset:-2px;
               border:2px solid #fff;
           }
           ::-webkit-scrollbar-thumb:hover{
               height:50px;
               background-color:#9f9f9f;
               -webkit-border-radius:4px;
           }
           ::-webkit-scrollbar-thumb:horizontal{
               width:5px;
               background-color:#CCCCCC;
               -webkit-border-radius:6px;
           }
    </style>
</head>
<body class="body-bg">
<div class="product-container">
    <div class="bconsole-header">
        <span>产品分类</span>
    </div>
    <div class="bconsole-container">
        <div class="bconsole-search-container">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" aria-label="Left Align" title="刷新">
                <img src="${pageContext.request.contextPath}/resources/img/serviceType/refresh.png">
            </button>
            <button type="button" class="btn btn-default btn-no-radius btn-bconsole" aria-label="Left Align" data-toggle="modal" data-target="#svcCategoryAddModal">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <form class="form-inline bconsole-fr" style="margin-bottom:0px;">
                <div class="form-group bconsole-form-group">
                    <input type="text" class="form-control bconsole-form-control bconsole-width-200" id="svcCategorySearch">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    <span class="bconsole-input-placeholder" style="width:150px;left: 15px;">分类名称</span>
                </div>
            </form>
        </div>
            
            <%--查询--%>
            <!-- <form class="form-inline" style="display:none;">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="分类名称" id="svcCategorySearch">
                </div>
                <button type="button" class="btn btn-primary" onclick="$('#productTypeTable').bootstrapTable('refresh');">查询
                </button>
                <button type="button" class="btn btn-default" onclick="$('#svcCategorySearch').val('')">重置</button>
                <button type="button" class="btn btn-primary" style="float: right" data-toggle="modal"
                        data-target="#svcCategoryAddModal">新增
                </button>
            </form> -->

            <%--列表--%>
            <div class="table-responsive">
                <table class="table table-hover" id="productTypeTable"></table>
            </div>
    </div>
    

   

    <%--新增服务分类model--%>
    <div class="modal fade" id="svcCategoryAddModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:650px;">
            <div class="modal-content" style="width:650px;">
                <!-- <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close"
                            onclick="handleResetForm('#svcCategoryAddForm')">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增服务分类</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="svcCategoryAddForm">
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red- font">＊</span>分类名称</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="productTypeName" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类编码</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="productTypeCode" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类排序</label>
                            <div class="col-md-8">
                                <input type="number" class="form-control" style="padding-right:12px" name="sortId"
                                       min="0" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类状态</label>
                            <div class="col-md-8">
                                <select class="form-control" name="typeStatus" required>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">父元素</label>
                            <div class="col-md-8">
                                <select class="form-control" name="parentId"></select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            onclick="handleResetForm('#svcCategoryAddForm')">取消
                    </button>
                    <button type="button" class="btn btn-primary" onclick="addSvcCategorySave()">保存</button>
                </div> -->
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#svcCategoryAddForm')">
                    <a><img src="${pageContext.request.contextPath}/resources/img/serviceType/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/serviceType/type-icon.png">
                    </span>
                    <span class="title">新增服务分类</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 69px;padding-right: 118px;">
                    <form class="form-horizontal" id="svcCategoryAddForm">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类名称</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productTypeName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类编码</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productTypeCode">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类排序</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="number" class="form-control" name="sortId"
                                       min="0">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类状态</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <select class="form-control" name="typeStatus">
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">父元素</label>
                            <div class="col-sm-9">
                                <select class="form-control" name="parentId"></select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="bconsole-modal-footer">
                    <a class="modal-submit" onclick="addSvcCategorySave()">提交</a>
                </div>
            </div>
        </div>
    </div>

    <%--修改服务分类model--%>
    <div class="modal fade" id="svcCategoryEditModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:650px;">
            <div class="modal-content" style="width:650px;">
                <!-- <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close"
                            onclick="handleResetForm('#svcCategoryEditForm')">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">编辑服务分类</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="svcCategoryEditForm">
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类名称</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="productTypeName" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类编码</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="productTypeCode" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类排序</label>
                            <div class="col-md-8">
                                <input type="number" class="form-control" style="padding-right:12px" name="sortId"
                                       min="0" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><span class="red-font">＊</span>分类状态</label>
                            <div class="col-md-8">
                                <select class="form-control input-width" name="typeStatus" required>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">父元素</label>
                            <div class="col-md-8">
                                <select class="form-control input-width" name="parentId"></select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            onclick="handleResetForm('#svcCategoryEditForm')">取消
                    </button>
                    <button type="button" class="btn btn-primary btn-save">保存</button>
                </div> -->
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#svcCategoryAddForm')">
                    <a><img src="${pageContext.request.contextPath}/resources/img/serviceType/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/serviceType/type-icon.png">
                    </span>
                    <span class="title">编辑服务分类</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 69px;padding-right: 118px;">
                    <form class="form-horizontal" id="svcCategoryEditForm">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类名称</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control beconsole-biner-input" name="productTypeName" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类编码</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control beconsole-biner-input" name="productTypeCode" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类排序</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="number" class="form-control beconsole-biner-input" style="padding-right:12px" name="sortId"
                                       min="0" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">分类状态</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <select class="form-control input-width beconsole-biner-input" name="typeStatus" required>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class=" col-sm-3 control-label">父元素</label>
                            <div class="col-sm-9">
                                <select class="form-control input-width beconsole-biner-input" name="parentId"></select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="bconsole-modal-footer">
                    <a class="modal-submit btn-save">提交</a>
                </div>
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
                    <p>请问是否删除该分类？</p>
                </div>
                <div class="modal-footer bconsole-confirm-footer">
                    <div class="confirm-box-ok">确定</div>
                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/message/message.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/productType.js"></script>
</html>
