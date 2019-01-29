<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2019/1/21
  Time: 14:47
  Describe: 服务实例-详情页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务实例-详情页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        <%--概览--%>
        .basic-info-show{
            padding: 10px 0 37px 50px;
            border-right: 1px solid #e4e4e4;
        }
        .basic-info-item{
            padding-top: 27px;
            padding-right: 0;
            padding-left: 0;
        }
        .gray-font{
            color: #999999;
        }
        .black-font{
            color: #333333;
        }
        .status-yellow>i,.status-green>i,.status-red>i{
            width: 14px;
            height: 14px;
            display: inline-block;
            border-radius: 50%;
            vertical-align: sub;
            margin-right: 8px;
        }
        .status-yellow>i{
            border: 3px solid #fec400;
        }
        .status-green>i{
            border: 3px solid #29cc97;
        }
        .status-red>i{
            background-color: #da3610;
            border: 3px solid #da3610;
        }
        .dropdown-form-select .dropdown-menu{
            min-width: 105px;
        }
        .dropdown-menu > li > a{
            padding-left: 18px;
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 25px;">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#basic" aria-controls="basic" role="tab" data-toggle="tab">概览</a></li>
        <li role="presentation"><a href="#control" aria-controls="control" role="tab" data-toggle="tab">监控</a></li>
        <li role="presentation"><a href="#log" aria-controls="log" role="tab" data-toggle="tab">日志</a></li>
        <li role="presentation"><a href="#backups" aria-controls="backups" role="tab" data-toggle="tab">备份</a></li>
        <li role="presentation"><a href="#recovery" aria-controls="recovery" role="tab" data-toggle="tab">恢复</a></li>
        <li role="presentation"><a href="#record" aria-controls="record" role="tab" data-toggle="tab">操作记录</a></li>
        <li role="presentation"><a href="#config" aria-controls="config" role="tab" data-toggle="tab">参数配置</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <%--概览--%>
        <div role="tabpanel" class="tab-pane active" id="basic" style="padding-top: 16px">
            <div class="row" style="margin-bottom: 47px" id="serviceBasic">
                <div class="basic-info-show col-xs-4 col-sm-4 col-md-4 row" id="serviceAttr"></div>
                <div class="basic-info-show col-xs-4 col-sm-4 col-md-4 row" id="timeInfo">
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">创建时间：</span>
                        <span class="black-font"></span>
                    </div>
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">创建用户：</span>
                        <span class="black-font"></span>
                    </div>
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">更新时间：</span>
                        <span class="black-font"></span>
                    </div>
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">更新人：</span>
                        <span class="black-font"></span>
                    </div>
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">申请时间：</span>
                        <span class="black-font"></span>
                    </div>
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">到期时间：</span>
                        <span class="black-font"></span>
                    </div>
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">购买人：</span>
                        <span class="black-font"></span>
                    </div>
                </div>
                <div class="basic-info-show col-xs-4 col-sm-4 col-md-4 row" id="resourceInfo" style="border: none">
                    <div class="col-md-6 basic-info-item">
                        <span class="gray-font">服务状态：</span>
                        <span class="status-green"><i></i></span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <button type="button" class="btn btn-default btn-no-radius btn-bconsole" onclick="window.location='${pageContext.request.contextPath}/'">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增实例
                    </button>

                    <form class="form-inline bconsole-fr">
                        <div class="form-group bconsole-form-group">
                            <input type="text" class="form-control bconsole-form-control bconsole-width-200" placeholder="实例名称" id="instanceSearch">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </div>
                    </form>

                    <div class="btn-group dropdown-form-select" id="instanceStatus" style="margin-left: 0;margin-right: 20px">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                                style="width: 105px;">
                            <span class="inner-text">实例状态</span>
                            <span class="caret"></span>
                        </button>
                        <select id="instanceStatusSelect" name="" style="display:none;">
                            <option value=""></option>
                            <option value="10">RUNNING</option>
                            <option value="20">WARNING</option>
                            <option value="30">STOPING</option>
                        </select>
                        <ul class="dropdown-menu" style="height: 126px;">
                            <li><a href="javascript:;" value="">全部</a></li>
                            <li><a href="javascript:;" value="10">RUNNING</a></li>
                            <li><a href="javascript:;" value="20">WARNING</a></li>
                            <li><a href="javascript:;" value="30">STOPING</a></li>
                        </ul>
                    </div>

                    <div class="btn-group dropdown-form-select" id="instanceType" style="margin-left: 0;margin-right: 20px">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                                style="width: 105px;">
                            <span class="inner-text">实例类型</span>
                            <span class="caret"></span>
                        </button>
                        <select id="instanceTypeSelect" name="" style="display:none;">
                            <option value=""></option>
                            <option value="10">master</option>
                            <option value="20">slave</option>
                        </select>
                        <ul class="dropdown-menu" style="height: 97px;">
                            <li><a href="javascript:;" value="">全部</a></li>
                            <li><a href="javascript:;" value="10">master</a></li>
                            <li><a href="javascript:;" value="20">slave</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="table-responsive" style="margin-top: 15px">
                <table class="table table-hover" id="instanceTable"></table>
            </div>
        </div>

        <%--监控--%>
        <div role="tabpanel" class="tab-pane" id="control"></div>

        <%--日志--%>
        <div role="tabpanel" class="tab-pane" id="log"></div>

        <%--备份--%>
        <div role="tabpanel" class="tab-pane" id="backups"></div>

        <%--恢复--%>
        <div role="tabpanel" class="tab-pane" id="recovery"></div>

        <%--操作记录--%>
        <div role="tabpanel" class="tab-pane" id="record"></div>

        <%--参数配置--%>
        <div role="tabpanel" class="tab-pane" id="config"></div>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script type="text/javascript">
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/serviceDetails/basic.js"></script>
</html>

