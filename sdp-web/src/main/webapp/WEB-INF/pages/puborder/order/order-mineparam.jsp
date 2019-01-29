<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                    <i class="iconfont">&#xe6ca;</i><span>我的订阅</span>
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

                    <table id="id-orderMineParamTable">
                        <thead>
                            <tr>
                                <th>参数名称</th>
                                <th>参数说明</th>
                                <th>参数类型</th>
                                <th>是否可为空</th>
                                <th>映射注册参数</th>
                                <th>映射订阅接口</th>
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
                            <label for="format">请求格式:</label> <input type="text"
                                class="form-control input-sm" name="format"
                                placeholder="请输入请求格式"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <%@ include file="../../common-js.jsp"%>
    <script src="<%=webpath%>/resources/js/puborder/order/order_mineparam.js"></script>
</body>
</html>