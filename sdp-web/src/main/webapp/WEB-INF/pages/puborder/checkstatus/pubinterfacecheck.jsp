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
</style>
</head>
<body>
    <div class="row">
        <div class="col-lg-12 col-md-12 row-tab">
            <div id="org-panel" class="panel panel-default common-wrapper">
                <div class="panel-heading common-part">
					<div class="dropdown">
                        <i class="iconfont">&#xe6ca;</i>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" onmouseover="changecolor();">
		                                    注册服务审核
	                        <b class="caret"></b>
		                </a>
						<ul class="dropdown-menu">
						   <li><a href="#" id="id-pubinterface-li">注册服务审核</a></li>
						   <li><a href="#" id="id-orderinterface-li">订阅服务审核</a></li>
						</ul>
					</div>
                </div>
                <div class="panel-body common-content">
                    <div class="searchWrap">
                        <form class="form-inline" id="id-mineSearchForm">
                            <div class="form-group">
                                <label for="name">订阅名称:</label> <input type="text"
                                    class="form-control inpu-sm" name="name" />
                            </div>
                            <button type="button" class="b-redBtn btn-i" id="id-searchBtn">
                                <i class="iconfont">&#xe67a;</i>查询
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-resetBtn">
                                <i class="iconfont">&#xe647;</i>重置
                            </button>
                        </form>
                    </div>

                    <table id="id-orderMineTable">
                        <thead>
                            <tr>
                                <th>服务ID</th>
                                <th>服务名称</th>
                                <th>url</th>
                                <th>注册用户</th>
                                <th>租户</th>
                                <th>注册时间</th>
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

<script src="<%=webpath%>/resources/js/puborder/checkstatus/pubinterfacecheck.js"></script>
<script type="text/javascript">
function changecolor(){
	$(".dropdown-toggle").css("background-color","white");
}
</script>
</html>