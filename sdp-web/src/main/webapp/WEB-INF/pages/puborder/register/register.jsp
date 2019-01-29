<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/12/10
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    var webpath = '<%=webpath%>';
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>服务注册</title>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-js.jsp" %>
    <link rel="stylesheet" href="<%=webpath %>/resources/plugin/jsPanel-3.10.0/vendor/jquery-ui-1.12.1.complete/jquery-ui.min.css">
    <link rel="stylesheet" href="<%=webpath %>/resources/plugin/jsPanel-3.10.0/jquery.jspanel.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css"
          type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/css/common.css">

    <script src="<%=webpath %>/resources/plugin/jsPanel-3.10.0/vendor/jquery-ui-1.12.1.complete/jquery-ui.min.js"></script>
    <script src="<%=webpath %>/resources/plugin/jsPanel-3.10.0/jquery.jspanel-compiled.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/utils/json.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.exhide.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/raphael/js/raphael.js" ></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/raphael/js/mousetrap.min.js"></script>
    <%--<script type="text/javascript" src="<%=webpath %>/resources/js/raphael/jquery.taglib4.js"></script>--%>
    <%--<script type="text/javascript" src="<%=webpath %>/resources/js/raphael/taglib_common_asych.js"></script>--%>
    <script type="text/javascript" src="<%=webpath %>/resources/js/serlayout/common/newobj.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/js/puborder/register/register_3.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/js/util_common.js"></script>




    <script type="text/javascript">
        var nodeId = '${nodeId}';
        var nodeName = '${nodeName}';
        var nodeType = '${nodeType}';
        var ssa = '${serviceTypes}';
        var serviceType = '${serviceTypeId}';

        var flowChartId = '${flowChartId}';
        var serVerId = '${serVerId}';
        var flowChartName = '${flowChartName}';
        var webapp = '${pageContext.request.contextPath}';
        var flowType = '${flowType}';
        var serFlowType = '${serFlowType}';
        var tenantId = '${tenantId}';
        var initserNodeArray = '${serNodeArray}';
        var serResume = '${serResume}';
        idflowChartId = getQueryString("id");
        var serFlowType = '${serFlowType}';

        function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(r[2]);
            } else {
                return null;
            }
        }
    </script>

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
<div class="" id="param-div" style="padding: 20px 10px;overflow: hidden;height: 100%;">
    <div>
        <ul class="nav nav-tabs apitab">
            <li role="presentation" id="baseinfo" class="active" menu="id-parambasetable"><a href="#">基本信息</a></li>
            <li role="presentation" id="reqdiv" class="" menu="id-paraminfotable"><a href="#">请求参数</a></li>
            <!-- <li role="presentation" id="respdiv" class="" menu="id-paraminfotable"><a href="#">响应参数</a></li>
            <li role="presentation" id="definediv" class="" menu="id-definedparamtable"><a href="#">参数自定义添加</a> -->
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
        <div class="portlet-body" id="id-parambasetable" style="height: 100%">
            <div style="height: 100%">
                <div class="form-group" id="pubnametype">
                    <label for="pubname">服务名称:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" name="name" id="pubname"/>

                    <label for="serviceTypeId">&nbsp;&nbsp;&nbsp;&nbsp;服务类型:</label>
                    <input id="serviceTypeId" type="text" value="" class="form-control input-sm form-sm apimodel-input serviceType" name="serviceTypeId"
                        onclick="showMenu(); return false;" idvalue=""/>
                    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                    <ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;background: #84d9ee;"></ul>
                    </div>
                </div>

                <!-- 接口设置div  -->
                <div id="typeId_api">

                    <div class="form-group" id="pubprotocaldiv">
                        <label for="pubprotocal">请求协议:</label>
                        <select name="pubprotocal" id="pubprotocal" class="apimodel-select apimodel-input form-control input-sm">
                            <option value="http">http</option>
                            <option value="webService">webService</option>
                        </select>
                        <%--<label for="pubmethod">请求方式:</label>
                        <select name="method" id="pubmethod" class="apimodel-select form-control input-sm">
                            <option value="POST">POST</option>
                            <option value="DELETE">DELETE</option>
                            <option value="PATCH">PATCH</option>
                        </select>--%>
                    </div>
                    <div class="form-group" id="pubreqformatdiv">
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
                        <label for="pubrespformat" id="pubrespformatdiv">响应格式:</label>
                        <select name="respformat" id="pubrespformat" class="apimodel-select form-control input-sm">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="application/soap+xml">application/soap+xml</option>
                            <option value="text/xml">text/xml</option>
                            <option value="其他">其他</option>
                        </select>

                    </div>
                    <div class="form-group" id="pubreqdatatypediv">
                        <label for="pubreqdatatype">请求值类型:</label>
                        <select name="reqdatatype" id="pubreqdatatype" class="apimodel-select form-control input-sm">
                            <option value="Object" selected>Object</option>
                            <option value="List">List</option>
                        </select>

                        <label for="pubreturndatatype">返回值类型:</label>
                        <select name="returndatatype" id="pubreturndatatype" class="apimodel-select form-control input-sm">
                            <option value="Object">Object</option>
                            <option value="List">List</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="inCharset">请求参数字符集</label>
                        <select id="inCharset" class="apimodel-select form-control input-sm">
                            <option selected>UTF-8</option>
                            <option>UTF-16</option>
                            <option>GBK</option>
                            <option>ISO-8859-1</option>
                            <option>US-ASCII</option>
                        </select>

                        <label for="outCharset">响应参数字符集</label>
                        <select id="outCharset" class="apimodel-select form-control input-sm ">
                            <option selected>UTF-8</option>
                            <option>UTF-16</option>
                            <option>GBK</option>
                            <option>ISO-8859-1</option>
                            <option>US-ASCII</option>
                        </select>
                    </div>
                    <div class="form-group" id="pubdescdiv" style="margin-top:8px">
                        <label for="pubdesc">描述:</label>
                        <textarea class="apimodel-area form-control" name="pubdesc" id="pubdesc"></textarea>
                        <!-- <button type="button" class="b-redBtn btn-i" id="cancelPubBtn"><i class="iconfont">&#xe635;</i>取消
                        </button> -->
                    </div>
                </div>
                <label for="id-next-button"></label>
                <button type="button" class="b-redBtn btn-i" id="id-back-button"><i class="iconfont">&#xe608;</i>返回</button>
                <button type="button" class="b-redBtn btn-i" id="id-next-button"><i class="iconfont">&#xe607;</i>下一步</button>

            </div>
        </div>
        <!--请求和响应参数-->
        <div class="portlet-body" id="id-paraminfotable" style="display:none;height: 100%;">
            <div class="searchWrap">
            </div>
            <div style="width: 200px;margin-top: 19px;margin-bottom: 20px;">
                <button type="button" class="b-redBtn btn-i" id="addParamBtn"><i class="iconfont">&#xe682;</i>添加</button>
            </div>
            <div id="reqparamtable"></div>
            <br>
            <button type="button" class="b-redBtn btn-i" id="id-Model-button"><i class="iconfont">&#xe638;</i>上一步</button>
            <button type="button" class="b-redBtn btn-i" id="id-saveServiceModel-button"><i class="iconfont">&#xe638;</i>完成</button>
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
                                <option value="List">List</option>
                                <option value="List_Object">List_Object</option>
                                <option value="List_List">List_List</option>
                            </select>
                            <label class="control-label">参数位置:</label>
                            <!-- <input class="form-control param-input" style="width:80px" type="text" id="parampos"/>  -->
                            <select id="parampos" class="" style="width:80px;height: 25px;">
                                <option value="0" selected="selected">请求体</option>
                                <option value="4">请求头</option>
                                <option value="1">url上的参数</option>
                                <option value="5">xml请求体属性</option>
                            </select>
                            <label class="control-label maxlength">最大长度:</label>
                            <input class="form-control param-input maxlength" style="width:80px" type="text" id="maxlength"/>
                            <label class="control-label">是否必传项:</label>
                            <!-- <input class="form-control param-input" style="width:80px" type="text" id="isempty"/> -->
                            <select id="isempty" class="" style="width:80px;height: 25px;">
                                <option value="false" selected="selected">否</option>
                                <option value="true">是</option>
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
<div id="designer_canvas" style="display: none"></div>
</body>


</html>



