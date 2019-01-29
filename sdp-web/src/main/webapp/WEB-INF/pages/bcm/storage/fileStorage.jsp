<%--
  Created by IntelliJ IDEA.
  User: lumeiling
  Date: 2019/1/14
  Time: 下午8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String webpath = request.getContextPath();
%>
<html>
<head>
    <title>文件存储列表页面</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../../base.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.12.1/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.12.1/jquery-ui.theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.12.1/jquery-ui.structure.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bcm/storage/fileStorage.css">
    <style>
        .fixed-table-body {height: auto;}
        .red-font {float: left;}
        .bootstrap-table {
            margin-right: 24px;
            margin-left: 26px;
        }
    </style>
</head>
<body class="body-bg">
<div class="product-container">
    <div class="bconsole-header">
        <span>全部存储</span>
    </div>
    <div>
        <button type="button" class="btn btn-default btn-no-radius btn-refresh" aria-label="Left Align" title="刷新" onclick="reloadPage()">
            <img src="${pageContext.request.contextPath}/resources/img/common/refresh.png">
        </button>
        <button id="createFileStorageBtn" type="button" class="btn btn-default btn-no-radius btn-bconsole" aria-label="Right Align" onclick="$('#addFileStorage.modal').modal('show')">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>创建
        </button>
        <form class="form-inline bconsole-fr" style="float: right;">
            <div class="form-group bconsole-form-group">
                <input type="text" class="form-control bconsole-form-control bconsole-width-200 " placeholder="存储名称" id="searchFileStorage" name="searchFileStorage">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </div>
        </form>
    </div>

    <%--列表格式--%>
    <div class="table-responsive" id="projectTable">
        <table class="table table-hover" id="fileStorageTable"></table>
    </div>
</div>
<%--创建文件存储--%>
<div class="modal fade" id="addFileStorage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#addFileStorageForm')">
                <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
            </div>
            <div class="bconsole-modal-title">
                <div class="row">
                    <img src="${pageContext.request.contextPath}/resources/img/common/new.png">
                </div>
                <span class="title">新建存储</span>
            </div>
            <div class="bconsole-modal-content">
                <form class="form-horizontal" id="addFileStorageForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 30px;">＊</span>名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="storageFileName" required>
                        </div>
                    </div>
                    <%--TODO 滑动选择存储容量，不超过项目配额的存储限制--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 10px;">＊</span>大小(G)</label>
                        <input type="text" id="size" name="storageFileSize" style="border:0; color:#f6931f; font-weight:bold;">
                        <div id="fileStoSlider" style="width: 266px;margin-left: 94px;"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font"></span>描述</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="description">
                        </div>
                    </div>
                </form>
            </div>
            <div class="bconsole-modal-footer">
                <a class="modal-submit" onclick="saveFileStorage()">提交</a>
            </div>
        </div>
    </div>
</div>

<%--格式化文件存储确认框--%>
<div class="modal fade" id="formatFileStorageConfirmModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 250px;">
        <div class="modal-content bconsole-confirm-box" style="width: 250px;">
            <div class="modal-header bconsole-confirm-header">
                <h4 class="modal-title">删除信息</h4>
            </div>
            <div class="modal-body bconsole-confirm-body">
                <p>请问是否格式化该共享存储？</p>
            </div>
            <div class="modal-footer bconsole-confirm-footer">
                <div class="confirm-box-ok">确定</div>
                <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
            </div>
        </div>
    </div>
</div>

<%--删除文件存储确认框--%>
<div class="modal fade" id="deleteFileStorageConfirmModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 250px;">
        <div class="modal-content bconsole-confirm-box" style="width: 250px;">
            <div class="modal-header bconsole-confirm-header">
                <h4 class="modal-title">删除信息</h4>
            </div>
            <div class="modal-body bconsole-confirm-body">
                <p>请问是否删除该共享存储？</p>
            </div>
            <div class="modal-footer bconsole-confirm-footer">
                <div class="confirm-box-ok">确定</div>
                <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    var webpath = '<%=webpath%>';
</script>

<script src="<%=webpath %>/resources/plugin/layer/layer.js"></script>
<script src="<%=webpath %>/resources/plugin/jquery-ui-1.12.1/jquery-ui.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="<%=webpath %>/resources/js/product/product-common.js"></script>
<script src="<%=webpath %>/resources/js/bcm/storage/fileStorage.js"></script>
</html>
