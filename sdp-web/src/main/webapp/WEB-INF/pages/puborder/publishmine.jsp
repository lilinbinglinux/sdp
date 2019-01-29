<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common-head.jsp" %>
    <title>我的注册</title>
    <%@ include file="../common-layer-ext.jsp" %>
    <%@ include file="../common-body-css.jsp" %>
</head>
<body>
<div class="row">
    <div class="col-lg-12 col-md-12 row-tab">
        <div id="org-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">
                <i class="iconfont">&#xe6ca;</i><span>我的注册</span>
            </div>
            <div class="panel-body common-content">
                <div class="searchWrap">
                    <form class="form-inline" id="id-mineSearchForm">
                        <div class="form-group">
                            <label for="name">订阅名称:</label>
                            <input type="text" class="form-control inpu-sm" id="name" name="name"/>
                        </div>
                        <button type="button" class="b-redBtn btn-i" id="id-searchBtn">
                            <i class="iconfont">&#xe67a;</i>查询
                        </button>
                        <button type="button" class="b-redBtn btn-i" id="id-resetBtn">
                            <i class="iconfont">&#xe647;</i>重置
                        </button>
                        <button type="button" class="b-redBtn btn-i" id="id-addtBtn">
                            <i class="iconfont"></i>新建
                        </button>
                    </form>
                </div>

                <table id="id-publishMineTable">
                    <thead>
                    <tr>
                        <th>订阅ID</th>
                        <th>注册接口名称</th>
                        <th>注册接口url</th>
                        <th>描述</th>
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

<!-- 修改订阅信息 -->
<%--<div id="id-updateOrderInter" class="dialog-wrap">
    <form id="id-updateOrderInterForm" class="form-inline"
          data-validator-option="{timely:2, theme:'yellow_right'}">
        <input type="hidden" name="orderid"/>
        <table class="form-table">
            <tr>
                <td>
                    <div class="form-group">
                        <label for="name">订阅名称:</label>
                        <input type="text" class="form-control input-sm" name="name" placeholder="请输入订阅名称"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <label for="ordercode">编码格式:</label>
                        <input type="text" class="form-control input-sm" name="ordercode" placeholder="请输入编码格式"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <label for="protocal">网络协议:</label>
                        <input type="text" class="form-control input-sm" name="protocal" placeholder="请输入网络协议"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <label for="url">请求地址:</label>
                        <input type="text" class="form-control input-sm" name="url" placeholder="请输入请求地址" data-rule="required;length(4~32);filter;"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <label for="format">请求格式:</label>
                        <input type="text" class="form-control input-sm" name="format" placeholder="请输入请求格式"/>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>--%>

<!-- API-Modal -->
<div id="pruModal" class="modal fade modalpanl"  tabindex="-1" role="dialog" style=" margin-top: 40px;">
    <div class="" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">接口注册</h4>
            </div>
            <input type="hidden" class="form-control input-sm form-sm"  name="pubid" id="pubid"/>
            <div class="modal-body">
                <div class="form-group">
                    <label for="pubname">接口名称:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input"  name="name" id="pubname"/>
                </div>

                <div class="form-group">
                    服务类型:
                    <label><input name="typeId" type="radio" value="2" checked/>接口 </label>
                    <label><input name="typeId" type="radio" value="4" />jar定义 </label>
                </div>

                <!-- 接口设置div  -->
                <div id="typeId_api">
                    <div class="form-group">
                        <label for="puburl">url:</label>
                        <input type="text" class="form-control input-sm form-sm rightinput apimodel-input" style="margin-left: 48px;" name="url" id="puburl"/>

                        <label for="pubport" style="margin-left: 80px;" >端口:</label>
                        <input type="text" class="form-control input-sm form-sm"  name="pubport" id="pubport"/>
                    </div>
                    <div class="form-group">
                        <label for="pubprotocal">请求协议:</label>
                        <select name="pubprotocal" id="pubprotocal" class="apimodel-select apimodel-input">
                            <option value="http">http</option>
                            <option value="webService">webService</option>
                        </select>

                        <label for="pubmethod" style="margin-left: 57px;">请求方式:</label>
                        <select name="method" id="pubmethod" class="apimodel-select">
                            <option value="GET">GET</option>
                            <option value="POST">POST</option>
                            <option value="DELETE">DELETE</option>
                            <option value="PATCH">PATCH</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="pubreqformat">请求格式:</label>
                        <select name="reqformat" id="pubreqformat" class="apimodel-select" style="width:154px">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="application/x-www-form-urlencoded">application/x-www-form-urlencoded</option>
                            <option value="其他">其他</option>
                        </select>

                        <label for="pubrespformat" style="margin-left: 57px;">响应格式:</label>
                        <select name="respformat" id="pubrespformat" class="apimodel-select">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="其他">其他</option>
                        </select>

                    </div>
                    <div class="form-group">
                        <label for="pubreqdatatype">请求值类型:</label>
                        <select name="reqdatatype" id="pubreqdatatype" class="apimodel-select">
                            <option value="Object" selected>Object</option>
                        </select>

                        <label for="pubreturndatatype" style="margin-left: 46px;">返回值类型:</label>
                        <select name="returndatatype" id="pubreturndatatype" class="apimodel-select">
                            <option value="Object">Object</option>
                            <option value="List">List</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="pubdesc">描述:</label>
                        <textarea class="apimodel-area" name="pubdesc" id="pubdesc"></textarea>
                    </div>
                </div>
            </div>

            <!-- jar包api设置div -->
            <div id="typeId_jar" style="display:none;margin-left: 17px;">
                <div class="form-group">
                    <input type ="file" id="ImportPicInput" name= "myfile"/>
                </div>
                <div class="form-group">
                    <label for="className">调用类:&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" style="width:480px"
                           name="className" id="className" placeholder="输入需要调用的类名"/>
                </div>
                <div class="form-group">
                    <label for="methodInClass">调用方法:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" style="width:480px"
                           name="methodInClass" id="methodInClass" placeholder="输入需要调用的方法名"/>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="addPubBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common-js.jsp" %>
<%@ include file="./pub-model.jsp" %>
<script src="<%=webpath%>/resources/js/puborder/publisher_mine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ajaxfileupload.js"></script>
<script src="<%=webpath %>/resources/js/util_common.js" type="text/javascript"></script>
</body>
</html>