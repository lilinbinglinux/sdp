<%--
  Created by IntelliJ IDEA.
  User: lumeiling
  Date: 2018/11/23
  Time: 下午1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String webpath = request.getContextPath();
%>
<html>
<head>
    <title>项目表管理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../../base.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bcm/project/proProject.css">
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
        <span>全部项目</span>
    </div>
    <div>
        <button type="button" class="btn btn-default btn-no-radius btn-refresh" aria-label="Left Align" title="刷新" onclick="reloadPage()">
            <img src="${pageContext.request.contextPath}/resources/img/common/refresh.png">
        </button>
        <button id="createProjectBtn" type="button" class="btn btn-default btn-no-radius btn-bconsole" aria-label="Right Align" onclick="$('#addProject.modal').modal('show')">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>创建项目
        </button>
        <button type="button" id="procard" class="btn-procard" onclick="switchProject(0)">
            <img src="${pageContext.request.contextPath}/resources/img/bcm/project/card_active.png">
        </button>
        <button type="button" id="prolist" class="btn-prolist" onclick="switchProject(1)">
            <img src="${pageContext.request.contextPath}/resources/img/bcm/project/list.png">
        </button>
        <form class="form-inline bconsole-fr" style="float: right;">
            <div class="form-group bconsole-form-group">
                <input type="text" class="form-control bconsole-form-control bconsole-width-200 " placeholder="项目名称或编码" id="searchKey" name="searchKey">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </div>
        </form>
    </div>

    <%--列表格式--%>
    <div class="table-responsive" id="projectTable" style="display: none;">
        <table class="table table-hover" id="proProjectTable"></table>
    </div>

    <%-- 图标格式--%>
    <div class="wrapper" id="projectGlyph" style="display: grid;">
    </div>

    <%--清除浮动--%>
    <div class="clear"> </div>
</div>

<!-- 创建项目 -->
<div class="modal fade" id="addProject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#addProjectForm')">
                <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
            </div>
            <div class="bconsole-modal-title">
                <div class="row">
                    <img src="${pageContext.request.contextPath}/resources/img/common/new.png">
                </div>
                <span class="title">新建项目</span>
            </div>
            <div class="bconsole-modal-content">
                <form class="form-horizontal" id="addProjectForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font">＊</span>项目编码</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="projectId" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font">＊</span>项目名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="projectName" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font">＊</span>项目说明</label>
                        <div class="col-sm-9">
                            <textarea type="text" class="form-control" rows="3" name="projectResume" required style="height: 85px;"></textarea>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label"><span class="red-font">＊</span>项目等级</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div class="form-radio-group" style="margin-right: 25px;">--%>
                                <%--<input id="yib" type="radio" name="projectLevel" value="1" required>--%>
                                <%--<label for="yib"></label>--%>
                                <%--<span>一般</span>--%>
                            <%--</div>--%>
                            <%--<div class="form-radio-group" style="margin-right: 25px;">--%>
                                <%--<input id="yanz" type="radio" name="projectLevel" value="2" required>--%>
                                <%--<label for="yanz"></label>--%>
                                <%--<span>重要</span>--%>
                            <%--</div>--%>
                            <%--<div class="form-radio-group" style="margin-right: 25px;">--%>
                                <%--<input id="tebie" type="radio" name="projectLevel" value="3" required>--%>
                                <%--<label for="tebie"></label>--%>
                                <%--<span>特别重要</span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label"><span class="red-font">＊</span>项目编码</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<input type="text" class="form-control" name="projectCode" required>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label"><span class="red-font">＊</span>访问地址</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<input type="text" class="form-control" name="hostAddr" required>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font"></span>项目状态</label>
                        <div class="col-sm-9">
                            <div class="form-radio-group">
                                <input id="zanc" type="radio" name="stateFlag" value="0">
                                <label for="zanc"></label>
                                <span>暂存</span>
                            </div>
                            <div class="form-radio-group">
                                <input id="kaifz" type="radio" name="stateFlag" value="1">
                                <label for="kaifz"></label>
                                <span>开发中</span>
                            </div>
                            <div class="form-radio-group">
                                <input id="shangx" type="radio" name="stateFlag" value="2">
                                <label for="shangx"></label>
                                <span>上线</span>
                            </div>
                            <div class="form-radio-group">
                                <input id="xiax" type="radio" name="stateFlag" value="3">
                                <label for="xiax"></label>
                                <span>下线</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font"></span>项目头像</label>
                        <div class="col-sm-9 profile">
                            <img id="profile1" name="profile" src="${pageContext.request.contextPath}/resources/img/bcm/project/profile1.png" onclick="selectProfile(this)">
                            <img id="profile2" name="profile" src="${pageContext.request.contextPath}/resources/img/bcm/project/profile2.png" onclick="selectProfile(this)">
                            <img id="profile3" name="profile" src="${pageContext.request.contextPath}/resources/img/bcm/project/profile3.png" onclick="selectProfile(this)">
                            <img id="profile4" name="profile" src="${pageContext.request.contextPath}/resources/img/bcm/project/profile4.png" onclick="selectProfile(this)">
                            <img id="profile5" name="profile" src="${pageContext.request.contextPath}/resources/img/bcm/project/profile5.png" onclick="selectProfile(this)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="sortId" required>
                        </div>
                    </div>
                </form>
            </div>
            <div class="bconsole-modal-footer">
                <a class="modal-submit" onclick="saveProject()">提交</a>
            </div>
        </div>
    </div>
</div>

<%--修改项目信息--%>
<div class="modal fade" id="editProject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#editProjectForm')">
                <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
            </div>
            <div class="bconsole-modal-title">
                <div class="row">
                    <img src="${pageContext.request.contextPath}/resources/img/common/basic-icon.png">
                </div>
                <span class="title">修改项目</span>
            </div>
            <div class="bconsole-modal-content">
                <form class="form-horizontal" id="editProjectForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font">＊</span>项目名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="projectName" required>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label"><span class="red-font">＊</span>项目编码</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<input type="text" class="form-control" name="projectCode" required>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font">＊</span>项目说明</label>
                        <div class="col-sm-9">
                            <textarea type="text" class="form-control" rows="3" name="projectResume" required style="height: 85px;"></textarea>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label"><span class="red-font">＊</span>访问地址</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<input type="text" class="form-control" name="hostAddr" required>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label"><span class="red-font">＊</span>项目等级</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div class="form-radio-group">--%>
                                <%--<input id="yib1" type="radio" name="projectLevel" value="1" required>--%>
                                <%--<label for="yib1"></label>--%>
                                <%--<span>一般</span>--%>
                            <%--</div>--%>
                            <%--<div class="form-radio-group">--%>
                                <%--<input id="yanz1" type="radio" name="projectLevel" value="2" required>--%>
                                <%--<label for="yanz1"></label>--%>
                                <%--<span>重要</span>--%>
                            <%--</div>--%>
                            <%--<div class="form-radio-group">--%>
                                <%--<input id="tebie1" type="radio" name="projectLevel" value="3" required>--%>
                                <%--<label for="tebie1"></label>--%>
                                <%--<span>特别重要</span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font"></span>项目状态</label>
                        <div class="col-sm-9">
                            <div class="form-radio-group">
                                <input id="zanc1" type="radio" name="stateFlag" value="0">
                                <label for="zanc1"></label>
                                <span>暂存</span>
                            </div>
                            <div class="form-radio-group">
                                <input id="kaifz1" type="radio" name="stateFlag" value="1">
                                <label for="kaifz1"></label>
                                <span>开发中</span>
                            </div>
                            <div class="form-radio-group">
                                <input id="shangx1" type="radio" name="stateFlag" value="2">
                                <label for="shangx1"></label>
                                <span>上线</span>
                            </div>
                            <div class="form-radio-group">
                                <input id="xiax1" type="radio" name="stateFlag" value="3">
                                <label for="xiax1"></label>
                                <span>下线</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="sortId" required>
                        </div>
                    </div>
                </form>
            </div>
            <div class="bconsole-modal-footer">
                <a class="modal-submit">保存</a>
            </div>
        </div>
    </div>
</div>
<%--确认框--%>
<div class="modal fade" id="deleteProConfirmModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 250px;">
        <div class="modal-content bconsole-confirm-box" style="width: 250px;">
            <div class="modal-header bconsole-confirm-header">
                <h4 class="modal-title">删除信息</h4>
            </div>
            <div class="modal-body bconsole-confirm-body">
                <p>请问是否删除该项目？</p>
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
<script src="<%=webpath %>/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="<%=webpath %>/resources/js/product/product-common.js"></script>
<script src="<%=webpath %>/resources/js/bcm/project/proProject.js"></script>

</html>
