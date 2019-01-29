<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>代订阅</title>
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
}

#envTable {
    width: 100%;
}

#text_area {
    width: 407px;
    height: 80px;
    border: 1px solid #ccc;
}
#userTable th,td {
  text-align:  center!important;
}
.dataTable a.icon-wrap{
float: unset!important;
}
</style>
</head>
<body>
    <div class="row">
        <div class="col-lg-12 col-md-12 row-tab">
            <div id="org-panel" class="panel panel-default common-wrapper">
                <div class="panel-heading common-part"><i class="iconfont">&#xe6ca;</i><span>代订阅</span></div>
                <div class="panel-body common-content">
                    <div class="searchWrap">
                        <form class="form-inline" id="userSearchForm">
                            <div class="form-group">
                                <label for="userName">订阅人:</label>
                                <input type="text" class="form-control input-sm" name="userName" id="userName"/>
                            </div>
                            <div class="form-group">
                                <label for="loginId">登录账号:</label>
                                <input type="text" class="form-control input-sm" name="loginId" id="loginId" />
                            </div>
                            <div class="form-group" style="display:none">
                                <label for="orgName">所属组织:</label>
                                <input type="text" class="form-control input-sm" name="orgName" readonly="readonly" onclick="showOrgTree(this);"/>
                                <input type="hidden" class="form-control input-sm" name="orgIds" />
                            </div>
                            <button type="button" class="b-redBtn btn-i" id="searchBtn"><i class="iconfont">&#xe67a;</i>查询</button>
                            <button type="button" class="b-redBtn btn-i" id="resetBtn"><i class="iconfont">&#xe647;</i>重置</button>
                        </form>

                    </div>
                    <table id="userTable">
                        <thead>
                            <tr>
                                <th>订阅人</th>
                                <th>登录账号</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                    <div id="id-orderinterfaceTable" style="margin-top: 3%;"></div>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
<script src="<%=webpath%>/resources/js/puborder/order/order-subscriptions.js"></script>
<script type="text/javascript">
function changecolor(){
    $(".dropdown-toggle").css("background-color","white");
}
</script>
</html>