    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="../../common-head.jsp"%>
<title>应用管理</title>
<%@ include file="../../common-layer-ext.jsp"%>
<%@ include file="../../common-body-css.jsp"%>
<%@ include file="../../common-js.jsp"%>
<script src="<%=webpath%>/resources/js/puborder/serapplication/serapplication_2.js"></script>
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
#id-serApplicationTable th,td {
  text-align:  center!important;
}
.dataTable a.icon-wrap{
float: unset!important;
}
</style>
</head>
<body>
    <div class="row" style="overflow-x: auto;height: 100%">
        <div class="col-lg-12 col-md-12 row-tab" style="height: 98%">
            <div id="org-panel" class="panel panel-default common-wrapper" style="height: 97%;">
                <div class="panel-heading common-part">
                    <i class="iconfont">&#xe6ca;</i><span>应用管理</span>
                </div>
                <div class="panel-body common-content">
                    <div class="searchWrap">
                        <form class="form-inline" id="id-mineSearchForm">
                            <div class="form-group">
                                <label for="applicationName">应用名称:</label> <input type="text"
                                    class="form-control inpu-sm" name="applicationName" id="applicationName" />
                            </div>
                            <button type="button" class="b-redBtn btn-i" id="id-searchBtn">
                                <i class="iconfont">&#xe67a;</i>查询
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-resetBtn">
                                <i class="iconfont">&#xe647;</i>重置
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-addBtn">
                                <i class="iconfont">&#xe682;</i>新建
                            </button>
                        </form>
                    </div>

                    <table id="id-serApplicationTable">
                        <thead>
                            <tr>
                                <th align="center">应用ID</th>
                                <th align="center">应用名称</th>
                                <th align="center">创建时间</th>
                                <th align="center">当前状态</th>
                                <th align="center">租户</th>
                                <th align="center">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- 新增订阅信息 -->
    <div id="id-insertOrderInter" class="dialog-wrap">
        <form id="id-insertOrderInterForm" class="form-inline"
              data-validator-option="{timely:2, theme:'yellow_right'}">
            <input type="hidden" name="orderid" />
            <table class="form-table">
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="id-insertSerAppplication-name">应用名称:</label>
                            <input type="text" class="form-control input-sm" id="id-insertSerAppplication-name" name="name" placeholder="请输入订阅名称"
                            style="height: 30px;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="id-insertSerAppplication-select">使用状态:</label>
                            <select id="id-insertSerAppplication-select" class="form-control input-sm" style="height: 30px;width: 153px;">
                                <option value="0">正常</option>
                                <option value="1">停用</option>
                            </select>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 修改订阅信息 -->
    <div id="id-updateOrderInter" class="dialog-wrap">
        <form id="id-updateOrderInterForm" class="form-inline"
            data-validator-option="{timely:2, theme:'yellow_right'}">
            <input type="hidden" name="orderid" />
            <table class="form-table">
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="id-updateSerAppplication-name">应用名称:</label>
                            <input type="text" class="form-control input-sm" id="id-updateSerAppplication-name" name="name" placeholder="请输入订阅名称" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="id-updateSerAppplication-select">使用状态:</label>
                            <select id="id-updateSerAppplication-select" class="form-control input-sm" style="height: 30px;width: 153px;">
                                <option value="0">正常</option>
                                <option value="1">停用</option>
                            </select>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
<%@ include file="serapplicationmodel.jsp"%>
</html>