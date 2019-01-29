<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/8
  Time: 14:07
  Describe: 镜像构建-代码构建
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代码构建</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .card-child{
            height: 470px;
        }
        .card-child-inner{
            padding: 50px 40px;
        }

        .trus-item-top {
            height: 40px;
            margin-bottom: 30px;
        }
        .trus-item-top>div {
            margin-right: 10px;
            float: left;
            width: 41px;
            height: 41px;
            background: url('${pageContext.request.contextPath}/resources/img/bcm/image/code-trus.png') no-repeat;
        }
        .trus-item-top span{
            font-family: MicrosoftYaHei-Bold;
            font-size: 28px;
            font-weight: bold;
            color: #010101;
        }
        .trus-item-top .bind-btn{
            float: right;
            width: 70px;
            height: 30px;
            border-radius: 15px;
            margin-top: 5px;
        }

        .trus-item-bottom {
            text-align: center;
            height: 280px;
        }
        .trus-item-bottom li{
            height: 40px;
            line-height: 40px;
            padding: 0 20px;
            background-color: #f6f6f6;
            border-radius: 20px;
            font-size: 14px;
            color: #000000;
            margin-bottom: 20px;
        }
        .trus-item-bottom li:before{
            content: '';
            width: 17px;
            height: 20px;
            margin-right: 10px;
            margin-top: 9.5px;
            float: left;
            background: url('${pageContext.request.contextPath}/resources/img/bcm/image/user.png') no-repeat;
        }
        .trus-item-bottom li:after{
            content: '';
            width: 12px;
            height: 12px;
            margin-left: 10px;
            margin-top: 14px;
            float: right;
            cursor: pointer;
            background: url('${pageContext.request.contextPath}/resources/img/common/close.png') no-repeat;
            background-size: 12px 12px;
        }
        .trus-item-bottom span.user-name {
            font-size: 18px;
            width: 68%;
            display: inline-block;
            text-align: left;
        }

        @media screen and (min-width: 1360px) {
            .card-child{
                width: 25%;
            }
        }
        @media (max-width: 1360px) and (min-width: 1046px){
            .card-child{
                width: 33.333333333333336%;
            }
        }
        @media (max-width: 1046px) and (min-width: 720px){
            .card-child{
                width: 50%;
            }
        }
        @media screen and (max-width: 720px) {
            .card-child{
                width: 100%;
            }
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 25px;">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#buildTasks" aria-controls="buildTasks" role="tab" data-toggle="tab">构建任务</a></li>
        <li role="presentation"><a href="#codeTrusteeship" aria-controls="codeTrusteeship" role="tab" data-toggle="tab">代码托管</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="buildTasks" style="padding-top: 16px">
            <div class="row">
                <div class="col-md-12">
                    <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                        <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                    </button>
                    <button type="button" class="btn btn-default btn-no-radius btn-bconsole" onclick="window.location='${pageContext.request.contextPath}/bcm/v1/ci/addCodeTask'">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>创建任务
                    </button>
                    <form class="form-inline bconsole-fr">
                        <div class="form-group bconsole-form-group">
                            <input type="text" class="form-control bconsole-form-control bconsole-width-200" placeholder="输入任务名称搜索 " id="tasksSearch">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </div>
                    </form>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover" id="buildTasksTable"></table>
            </div>
        </div>

        <div role="tabpanel" class="tab-pane" id="codeTrusteeship" style="padding-top: 16px">
            <div class="row">
                <div class="col-md-12">
                    <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                        <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                    </button>
                    <button type="button" class="btn btn-default btn-no-radius btn-bconsole" onclick="window.location='${pageContext.request.contextPath}/bcm/v1/'">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>创建任务
                    </button>
                    <form class="form-inline bconsole-fr">
                        <div class="form-group bconsole-form-group">
                            <input type="text" class="form-control bconsole-form-control bconsole-width-200" placeholder="输入关键字搜索" id="codeSearch">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card-parent-wrap" style="margin-top: 15px">
                <ul id="codeTrusList" class="card-parent">
                    <li class="card-child">
                        <div class="card-child-inner">
                            <div class="trus-item-top">
                                <div style="background-position: 0 0"></div>
                                <span>Github</span>
                                <button class="btn btn-default save-hollow-btn bind-btn">绑定</button>
                            </div>
                            <ul class="trus-item-bottom">
                                <li>
                                    <span class="ellipsis user-name">user1</span>
                                </li>
                                <li>
                                    <span class="ellipsis user-name">user2user2user2user2user2u</span>
                                </li>
                                <li>
                                    <span class="ellipsis user-name">user2user2user2user2user2u</span>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="card-child">
                        <div class="card-child-inner">
                            <div class="trus-item-top">
                                <div style="background-position: -49px 0"></div>
                                <span>Gitlab</span>
                                <button class="btn btn-default save-hollow-btn bind-btn">绑定</button>
                            </div>
                            <ul class="trus-item-bottom">
                                <li>
                                    <span class="ellipsis user-name">user1</span>
                                </li>
                                <li>
                                    <span class="ellipsis user-name">user2user2user2user2user2u</span>
                                </li>
                                <li>
                                    <span class="ellipsis user-name">user2user2user2user2user2u</span>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="card-child">
                        <div class="card-child-inner">
                            <div class="trus-item-top">
                                <div style="background-position: -99px 0"></div>
                                <span>SVN</span>
                                <button class="btn btn-default save-hollow-btn bind-btn">绑定</button>
                            </div>
                            <ul class="trus-item-bottom">
                                <li>
                                    <span class="ellipsis user-name">user1</span>
                                </li>
                                <li>
                                    <span class="ellipsis user-name">user2user2user2user2user2u</span>
                                </li>
                                <li>
                                    <span class="ellipsis user-name">user2user2user2user2user2u</span>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
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
<script src="${pageContext.request.contextPath}/resources/js/bcm/ci/codeConstructs.js"></script>
</html>
