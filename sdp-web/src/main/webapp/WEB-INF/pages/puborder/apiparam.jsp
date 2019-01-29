<% /**
 ----------- 本页面描述右侧面板api的详细信息（设置参数ifream） ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>

<script>
    var webpath = '<%=webpath%>';
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common-head.jsp" %>
    <title>API 详细信息</title>
    <%@ include file="../common-layer-ext.jsp" %>
    <%@ include file="../common-js.jsp" %>

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/css/common.css">

    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>

    <style type="text/css">
        body {
            overflow: hidden;
            background: #fff !important;
        }

        .panel-heading > span {
            top: -2px;
            position: relative;
        }

        .dataTables_wrapper .dataTables_paginate {
            position: absolute;
            bottom: -50px;
            right: 30px;
        }

        .reqbtn {
            margin-top: 19px;
        }

        .pubidtoreq-textarea1 {
            width: 300px;
            margin-top: 20px;
            margin-left: 2px;
        }

        .pubidtoreq-textarea2 {
            border: none;
            width: 300px;
            margin-top: -379px;
        }
        .panel-body {
            border: none;
            height: 700px;
        }
        #id-parambasetable .form-control{
            width:30%;
            margin-right: 10px;
        }
        #id-parambasetable label{
            width:11%;
            text-align: right;
            margin-right: 3px;
        }

    </style>
</head>
<body>
<input type="hidden" id="modaltype">
<input type="hidden" id="clickTag">
<input type="hidden" id="apinodeType">
<div class="panel-body common-content" id="param-div">
    <div>
        <ul class="nav nav-tabs apitab">
            <li role="presentation" id="baseinfo" class="active" menu="id-parambasetable"><a href="#">基本信息</a></li>
            <li role="presentation" id="reqdiv" class="" menu="id-paraminfotable"><a href="#">请求参数</a></li>
            <li role="presentation" id="respdiv" class="" menu="id-paraminfotable"><a href="#">响应参数</a></li>
            <li role="presentation" id="definediv" class="" menu="id-definedparamtable"><a href="#">参数自定义添加</a>
            </li>
        </ul>
    </div>
    <form class="form-inline" id="pubidtoreq">
        <div class="form-group">
            <input type="hidden" id="pubid" name="pubid">
            <input type="hidden" id="type" name="type">
        </div>
    </form>
    <div class="panel-body">
        <!-- 基本信息 -->
        <div class="portlet-body" id="id-parambasetable">
            <div>
                <div class="form-group">
                    <label for="pubname">接口名称:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" name="name" id="pubname"/>
                    <label>接口名称:</label>
                    <input name="typeId" type="radio" value="2" checked/>接口
                    <input name="typeId" type="radio" value="4"/>jar定义
                </div>

                <!-- 接口设置div  -->
                <div id="typeId_api">
                    <div class="form-group">
                        <label for="puburl">url:</label>
                        <input type="text" class="form-control input-sm form-sm rightinput apimodel-input"
                               name="url" id="puburl"/>
                        <label for="pubport">端口:</label>
                        <input type="text" class="form-control input-sm form-sm" name="pubport" id="pubport"/>
                    </div>
                    <div class="form-group">
                        <label for="pubprotocal">请求协议:</label>
                        <select name="pubprotocal" id="pubprotocal" class="apimodel-select apimodel-input form-control input-sm">
                            <option value="http">http</option>
                            <option value="webService">webService</option>
                        </select>
                        <label for="pubmethod">请求方式:</label>
                        <select name="method" id="pubmethod" class="apimodel-select form-control input-sm">
                            <option value="GET">GET</option>
                            <option value="POST">POST</option>
                            <!-- <option value="DELETE">DELETE</option>
                            <option value="PATCH">PATCH</option> -->
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="pubreqformat">请求格式:</label>
                        <select name="reqformat" id="pubreqformat" class="apimodel-select form-control input-sm">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="application/x-www-form-urlencoded">application/x-www-form-urlencoded</option>
                            <option value="application/soap+xml">application/soap+xml</option>
                            <option value="text/xml">text/xml</option>
                            <option value="其他">其他</option>
                        </select>
                        <label for="pubrespformat">响应格式:</label>
                        <select name="respformat" id="pubrespformat" class="apimodel-select form-control input-sm">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="application/soap+xml">application/soap+xml</option>
                            <option value="text/xml">text/xml</option>
                            <option value="其他">其他</option>
                        </select>

                    </div>
                    <div class="form-group">
                        <label for="pubreqdatatype">请求值类型:</label>
                        <select name="reqdatatype" id="pubreqdatatype" class="apimodel-select form-control input-sm">
                            <option value="Object" selected>Object</option>
                        </select>

                        <label for="pubreturndatatype">返回值类型:</label>
                        <select name="returndatatype" id="pubreturndatatype" class="apimodel-select form-control input-sm">
                            <option value="Object">Object</option>
                            <!-- <option value="List">List</option> -->
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="pubdesc">描述:</label>
                        <textarea class="apimodel-area form-control" name="pubdesc" id="pubdesc"></textarea>
                        <label for="addPubBtn"></label>
                        <!-- <button type="button" class="b-redBtn btn-i" id="addPubBtn"><i class="iconfont">&#xe635;</i>下一步
                        </button>
                        <button type="button" class="b-redBtn btn-i" id="cancelPubBtn"><i class="iconfont">&#xe635;</i>取消
                        </button> --> 
                    </div>
                </div>
                <!-- jar包api设置div -->
                <div id="typeId_jar" style="display:none;margin-left: 17px;">
                    <div class="form-group">
                        <input type="file" id="ImportPicInput" name="myfile"/>
                    </div>
                    <div class="form-group">
                        <label for="urlEnv">调用类:&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" class="form-control input-sm form-sm apimodel-input" style="width:480px"
                               name="className" id="className" placeholder="输入需要调用的类名"/>
                    </div>
                    <div class="form-group">
                        <label for="urlEnv">调用方法:</label>
                        <input type="text" class="form-control input-sm form-sm apimodel-input" style="width:480px"
                               name="methodInClass" id="methodInClass" placeholder="输入需要调用的方法名"/>
                    </div>
                </div>

            </div>
        </div>
        <!--请求和响应参数-->
        <div class="portlet-body" id="id-paraminfotable" style="display:none;">
            <div class="searchWrap">
            </div>
            <div style="width: 200px;margin-top: 19px;margin-bottom: 20px;">
                <button type="button" class="b-redBtn btn-i" id="addParamBtn"><i class="iconfont">&#xe635;</i>添加
                </button>
            </div>
            <div id="reqparamtable"></div>
        </div>
        <!-- 参数自定义添加-->
        <div class="portlet-body" id="id-definedparamtable" style="display:none;">
            <button type="button" class="b-redBtn btn-i reqbtn" id="addXmlParamBtn"><i class="iconfont">&#xe635;</i>添加
            </button>
            <form>
                <textarea class="form-control pubidtoreq-textarea1" rows="20" id="xmlstr"
                          style="width:300px;height: 378px;"></textarea>
            </form>
            <div style="float:right">
                <textarea class="form-control pubidtoreq-textarea2" rows="20" id="example-xmlstr" style="height:378px"
                          readonly></textarea>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="paramadd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">参数设置</h4>
            </div>
            <div class="modal-body" id="parammodal-body">
                <input type="hidden" id="parmParentId" value=""/>
                <input type="hidden" id="paramadd-update">
                <form id="urlmodelAddForm" data-validator-option="{timely:2, theme:'yellow_right'}">
                    <input type="hidden" name="pubid" id="param-pubid"/>
                    <input type="hidden" name="paramid" id="paramid-set"/>
                    <div id="allparamset">
                        <div class="form-inline" id="paramset">
                            <label class="control-label">参数:</label><input class="form-control param-input"
                                                                           style="width:80px" type="text" id="ecode"/>
                            <label class="control-label">说明:</label><input class="form-control param-input"
                                                                           style="width:80px" type="text" id="reqdesc"/>
                            <label class="control-label">参数类型:</label>
                            <!-- <input class="form-control param-input" style="width:80px" type="text" id="reqtype"/> -->
                            <select id="reqtype" class="" style="width:80px;height: 25px;">
                                <option value="String" selected="selected">String</option>
                                <option value="int">int</option>
                                <option value="boolean">boolean</option>
                                <option value="Object">Object</option>
                            </select>
                            <label class="control-label">参数位置:</label>
                            <!-- <input class="form-control param-input" style="width:80px" type="text" id="parampos"/>  -->
                            <select id="parampos" class="" style="width:80px;height: 25px;">
                                <option value="0" selected="selected">请求体</option>
                                <option value="4">请求头</option>
                                <option value="1">url上的参数</option>
                                <option value="2">响应头</option>
                                <option value="3">响应体</option>
                                <option value="5">xml请求体属性</option>
                                <option value="6">xml响应体属性</option>
                            </select>
                            <label class="control-label maxlength">最大长度:</label>
                            <input class="form-control param-input maxlength" style="width:80px" type="text" id="maxlength"/>
                            <label class="control-label">是否必传项:</label>
                            <!-- <input class="form-control param-input" style="width:80px" type="text" id="isempty"/> -->
                            <select id="isempty" class="" style="width:80px;height: 25px;">
                                <option value="0" selected="selected">否</option>
                                <option value="1">是</option>
                            </select>
                            <button type="button" class="btn btn-default" aria-label="Left Align" id="addparam">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="addParamSetBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<script src="<%=webpath %>/resources/js/puborder/apiparam.js"></script>
<script src="<%=webpath %>/resources/plugin/ajaxfileupload.js"></script>
<script src="<%=webpath %>/resources/js/util_common.js" type="text/javascript"></script>
</body>
</html>


