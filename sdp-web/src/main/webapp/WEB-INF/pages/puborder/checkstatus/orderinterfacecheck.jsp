<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>我的订阅</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/datatables/media/css/jquery.dataTables.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/puborder/order/order-model.css" type="text/css">
<link href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
<%@ include file="../../common-head.jsp"%>
<%@ include file="../../common-js.jsp"%>
<%@ include file="../../common-layer-ext.jsp"%>
<%@ include file="../../common-body-css.jsp"%>
<style type="text/css">
body {
    overflow: hidden;
    padding: 0px;
}
.checkInformation{
    display: block;
}
.checkInformation span{
    text-align:  center!important;
    font-size: medium;
    text-shadow: 1px 1px 1px #FFF;
}
#id-orderMineTable th,td {
  text-align:  center!important;
}
.dataTable a.icon-wrap{
float: unset!important;
}
</style>
</head>
<body>
    <div class="row" style="overflow: auto;height: 100%">
        <div class="col-lg-12 col-md-12 row-tab" style="height: 98%">
            <div id="org-panel" class="panel panel-default common-wrapper"  style="height: 97%">
                <div class="panel-heading common-part">
                        <i class="iconfont">&#xe6ca;</i><span>服务审核</span>
                </div>
                <div class="panel-body common-content">
                    <div class="searchWrap">
                        <form class="form-inline" id="id-mineSearchForm">
                            <div class="form-group">
                                <label>订阅名称:</label> <input type="text"
                                    class="form-control inpu-sm" name="name" />
                            </div>
                            <div class="form-group">
                                <label>appId:</label> <input type="text"
                                    class="form-control inpu-sm" name="appId" />
                            </div>
                            <div class="form-group">
                                <label>审批状态:</label>
                                <select id="id-checkstatus-select" name="checkStatus" style="height: 34px;width: 179px; border-color: #b8b8b8">
                                    <option value="">所有状态查询</option>
                                    <option value="0">待审批</option>
                                    <option value="3">审批中</option>
                                    <option value="1">审批通过</option>
                                    <option value="2">审批未通过</option>
                                </select>
                            </div>
                            <button type="button" class="b-redBtn btn-i" id="id-searchBtn">
                                <i class="iconfont">&#xe67a;</i>查询
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-resetBtn">
                                <i class="iconfont">&#xe647;</i>重置
                            </button>
                        </form>
                    </div>
                    <div id="" style="margin-top: 3%;"></div>
                    <table id="id-orderMineTable">
                        <thead>
                            <tr>
                                <th>服务ID</th>
                                <th>订阅名称</th>
                                <th>appId</th>
                                <th>申请用户</th>
                                <th>申请时间</th>
                                <th>当前状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>

</body>

<!-- 订阅服务   订阅编码  服务类型   订阅版本  订阅人  是否代订阅  代订阅人  租户ID  申请时间  appid   -->
<div id="id-orderInformation-div" style="padding: 1em 3em;">
    <label class="checkInformation">
        <span>订阅名称：</span>
        <span id="id-orderName-span"></span>
    </label>

    <label class="checkInformation">
        <span>服务名称：</span>
        <span id="id-orderSerName-span"></span>
    </label>

    <label class="checkInformation">
        <span>订阅编码：</span>
        <span id="id-orderCode-span"></span>
    </label>

    <label class="checkInformation">
        <span>appId：</span>
        <span id="id-appId-span"></span>
    </label>

    <label class="checkInformation">
        <span>服务类型：</span>
        <span id="id-serType-span"></span>
    </label>

    <label class="checkInformation">
        <span>申请用户：</span>
        <span id="id-loginId-span"></span>
    </label>

    <label class="checkInformation">
        <span>是否代订阅：</span>
        <span id="id-repFlag-span"></span>
    </label>

    <label class="checkInformation">
        <span>代订阅人：</span>
        <span id="id-repUserId-span"></span>
    </label>

    <label class="checkInformation">
        <span>租户D：</span>
        <span id="id-tenantId-span"></span>
    </label>

    <label class="checkInformation">
        <span>申请时间：</span>
        <span id="id-createDateString-span"></span>
    </label>
</div>

<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
<script src="<%=webpath%>/resources/js/puborder/checkstatus/orderinterfacecheck.js"></script>
<script type="text/javascript">
function changecolor(){
    $(".dropdown-toggle").css("background-color","white");
}
</script>
</html>