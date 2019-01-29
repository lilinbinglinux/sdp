<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/8
  Time: 15:51
  Describe: 镜像构建-Dokerfile构建
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dokerfile构建</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 25px;">
    <div class="image-page-title">
        <h3>Dokerfile构建</h3>
    </div>
    <div class="row" style="margin-top: 36px">
        <div class="col-md-12">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-default btn-no-radius btn-bconsole" onclick="window.location='${pageContext.request.contextPath}/bcm/v1/ci/addDockerfile'">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建任务
            </button>
            <form class="form-inline bconsole-fr">
                <div class="form-group bconsole-form-group">
                    <input type="text" class="form-control bconsole-form-control bconsole-width-200" placeholder="输入任务名称搜索" id="dokerSearch">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </div>
            </form>
        </div>
    </div>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="dockerfileTable"></table>
    </div>

    <%--删除确认框--%>
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width: 250px;">
            <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                <div class="modal-header bconsole-confirm-header">
                    <h4 class="modal-title">删除信息</h4>
                </div>
                <div class="modal-body bconsole-confirm-body">
                    <p>请问是否删除该任务？</p>
                    <input type="hidden" name="deleteId">
                </div>
                <div class="modal-footer bconsole-confirm-footer">
                    <div class="confirm-box-ok" onclick="deleteDockerTask()">确定</div>
                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bcm/ci/dockerfileConstructs.js"></script>
</html>