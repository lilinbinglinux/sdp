<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-head.jsp"%>
    <title>我的订阅</title>
    <%@ include file="../../common-layer-ext.jsp"%>
    <%@ include file="../../common-body-css.jsp"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
body {
    overflow: hidden;
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
            <div id="org-panel" class="panel panel-default common-wrapper" style="height: 97%">
                <div class="panel-heading common-part">
                    <i class="iconfont">&#xe6ca;</i><span>我的订阅</span>
                </div>
                <div class="panel-body common-content">
                    <div class="searchWrap">
                        <form class="form-inline" id="id-mineSearchForm">
                            <div class="form-group">
                                <label for="name">订阅名称:</label> <input type="text"
                                    class="form-control inpu-sm" name="name" />
                            </div>
                            <div class="form-group">
                                <label for="name">appId:</label> <input type="text"
                                    class="form-control inpu-sm" name="appId" />
                            </div>
                            <div class="form-group">
                                <label>应用:</label>
                                <select id="id-applicationId-select" name="applicationId" style="height: 34px;width: 179px; border-color: #b8b8b8">
                                    <option value="">所有应用查询</option>
                                    <c:forEach items="${applicationList}" var="item">
                                        <option value="${item.key}">${item.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="button" class="b-redBtn btn-i" id="id-searchBtn">
                                <i class="iconfont">&#xe67a;</i>查询
                            </button>
                            <button type="button" class="b-redBtn btn-i" id="id-resetBtn">
                                <i class="iconfont">&#xe647;</i>重置
                            </button>
                        </form>
                        <br>

                    </div>

                    <table id="id-orderMineTable">
                        <thead>
                            <tr>
                                <th>订阅名称</th>
                                <th>订阅服务</th>
                                <th>编码格式</th>
                                <th>appId</th>
                                <th>订阅类型</th>
                                <th>订阅时间</th>
                                <th>当前状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
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
                            <label for="name">订阅名称:</label> <input type="text"
                                class="form-control input-sm" name="name"
                                placeholder="请输入订阅名称" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="ordercode">编码格式:</label> <input type="text"
                                class="form-control input-sm" name="ordercode"
                                placeholder="请输入编码格式"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="protocal">网络协议:</label> <input type="text"
                                class="form-control input-sm" name="protocal"
                                placeholder="请输入网络协议"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="url">请求地址:</label> <input type="text"
                                class="form-control input-sm" name="url"
                                placeholder="请输入请求地址" data-rule="required;length(4~32);filter;"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <label for="reqformat">请求格式:</label> <input type="text"
                                class="form-control input-sm" name="reqformat"
                                placeholder="请输入请求格式"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div id="interfaceinfodiv" style="display:none;" class="panel-body">
        <div style="margin-top: 5%;">
            url：
            <textarea rows="3" cols="98" id="urlreqparam-url" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
        </div>
        <div style="margin-top: 2%;">
            请求参数格式：
            <textarea rows="5" cols="98" id="urlreqparam-code" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
        </div>
        <div style="margin-top: 2%">
            响应参数格式：
            <textarea rows="5" cols="98" id="urlresponseparam-code" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
        </div>
    </div>
    <%@ include file="../../common-js.jsp"%>
    <script src="<%=webpath%>/resources/js/puborder/order/order_mine.js"></script>
</body>
</html>