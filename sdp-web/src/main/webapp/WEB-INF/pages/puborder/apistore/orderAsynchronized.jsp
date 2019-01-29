<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/10/19
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>服务商城</title>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-body-css.jsp" %>
    <%@ include file="../../common-js.jsp" %>

    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/css/puborder/warehouse/servicedes.css"/>
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/plugin/jsPanel-3.10.0/vendor/jquery-ui-1.12.1.complete/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/plugin/jsPanel-3.10.0/jquery.jspanel.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=webpath %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css">

    <script type="text/javascript" src="<%=webpath %>/resources/plugin/jsPanel-3.10.0/vendor/jquery-ui-1.12.1.complete/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/jsPanel-3.10.0/jquery.jspanel-compiled.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/utils/json.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.exhide.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/raphael/js/raphael.js" ></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/raphael/js/mousetrap.min.js"></script>
    <%--<script type="text/javascript" src="<%=webpath %>/resources/plugin/raphael/js/jquery.taglib.js"></script>--%>
    <script type="text/javascript" src="<%=webpath %>/resources/js/serlayout/common/newobj.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/js/puborder/apistore/asynchronized.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/js/util_common.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/js/core/base.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.exhide.js"></script>

    <script type="text/javascript">
        var nodeId = '${nodeId}';
        var nodeName = '${nodeName}';
        var nodeType = '${nodeType}';
        var ssa = '${serviceTypes}';
        var subloginId = '${subloginId}';
        var flowChartId = '${flowChartId}';
        var orderId = '${orderId}';
        var serVerId = '${serVerId}';
        var flowChartName = '${flowChartName}';
        var webapp = '${pageContext.request.contextPath}';
        var flowType = '${flowType}';
        var serFlowType = '${serFlowType}';
        var applicationId = '${applicationId}';
        var tenantId = '${tenantId}';
        var orderDesc = '${orderDesc}';
        var initserNodeArray = '${serNodeArray}';
        var initserJoinArray = '${serJoinArray}';
        idflowChartId = getQueryString("id");
        //当流程图id无法截取时，用serId截取
        if(idflowChartId == null || idflowChartId ==""){
            idflowChartId = getQueryString("serId");
        }
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
        .layer-demo {
            background-color: gray;
        }
        /* 滚动条 */
        .test-1::-webkit-scrollbar {/*滚动条整体样式*/
            width: 8px;     /*高宽分别对应横竖滚动条的尺寸*/
            height: 1px;
        }
        .test-1::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
            background: #b8b8b8;
        }
        .test-1::-webkit-scrollbar-track {/*滚动条里面轨道*/
            -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
            border-radius: 10px;
            background: #EDEDED;
        }
    </style>
</head>

<body>
<div class="row">
    <div class="col-lg-12 col-md-12 row-tab">
        <div id="org-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">
                <form class="bs-example bs-example-form" role="form">
                    <span style="font-size: 2rem; font-weight: 600;color: #cc3333;padding-left: 3rem; line-height: 100%">服务商城 — ${serName}</span>
                    <button type="button" class="b-redBtn btn-i" id="id-prePage-button" style="text-align: right;float:right">&nbsp;<i class="iconfont">&#xe608;</i>返回&nbsp;</button>
                    <input id="serVerId" value="${serVerId}" type="hidden"/>
                    <input id="serId" value="${serId}" type="hidden"/>
                </form>
            </div>

            <div class="panel-body common-content" style="padding: 0%">
                <!-- 网页左边栏 -->
                <div id="leftType" class="col-lg-4 col-md-4 test-1" style="height: 90%; overflow-y:auto;">
                    <div style="height: 16em;width: 100%;line-height:12em;" align="center">
                        <img src="<%=webpath %>/resources/img/puborder/api-default.png"
                             style=" vertical-align: middle;">
                        <span style="display: block;font-size: 1.8em;font-weight: 600;">${serName}</span>
                    </div>

                    <div class="information">
                        <div class="col-lg-6 col-md-6">
                            <p><span class="information-title-left">版本信息：</span><span
                                    class="information-context-left" id="serVersion">${serVersion}</span></p>
                            <p><span class="information-title-left">发布者：</span><span
                                    class="information-context-left">${loginId}</span></p>
                            <p><span class="information-title-left">当前状态：</span><span class="information-context-left">发布</span>
                            </p>
                            <p><span class="information-title-left">服务编码：</span><span
                                    class="information-context-left">${serCode}</span></p>
                            <p><span class="information-title-left">接口描述：</span></p>
                            <p><span class="information-title-left">&nbsp;</span></p>
                            <p><span class="information-title-left">&nbsp;</span></p>
                        </div>
                        <div class="col-lg-6 col-md-6">
                            <p><span class="information-title-right">订阅量：</span><span
                                    class="information-context-right">${countTime}</span></p>
                            <p><span class="information-title-right">调用方式：</span><span
                                    class="information-context-right">${synchFlag}</span></p>
                            <p><span class="information-title-right">服务类型：</span><span
                                    class="information-context-right">${flowTypeName}</span></p>
                        </div>
                    </div>
                    <div>
                        <textarea style="width: 85%;height: 8em;margin-left: 2.5em" readonly>${serResume}</textarea>
                    </div>
                    <br>
                    <div>
                        <span style="float: right;padding-right: 5em;">&nbsp;</span>
                        <button type="button" id="displayOrder" class="btn btn-danger" style="float: right;padding-right: 1em;">完成</button>
                    </div>
                </div>

                <!-- 网页右边栏 -->
                <div id="id-orderIterface-div" class="col-lg-8 col-md-8" style="height: 100%;padding: 0%;">



                    <input type="hidden" id="modaltype">
                    <input type="hidden" id="clickTag">
                    <input type="hidden" id="apinodeType">
                    <div class="panel-body common-content" id="param-div">
                        <div>
                            <ul class="nav nav-tabs apitab">
                                <li role="presentation" id="baseinfo" class="active" menu="id-parambasetable"><a href="#">基本信息</a></li>
                                <li role="presentation" id="reqdiv" class="" menu="id-paraminfotable"><a href="#">请求参数</a></li>
                                <li role="presentation" id="respdiv" class="" menu="id-paraminfotable"><a href="#">响应参数</a></li>
                                <li role="presentation" id="relationdiv" class="" menu="layoutparamtable"><a href="#">入参映射</a></li>
                                <li role="presentation" id="resprelationdiv" class="" menu="resplayoutparamtable"><a href="#">出参映射</a></li>
                                <!-- <li role="presentation" id="definediv" class="" menu="id-definedparamtable"><a href="#">参数自定义添加</a> -->
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
                                    <div class="form-group" id="pubnametype">
                                        <label for="pubname">服务名称:</label>
                                        <input type="text" class="form-control input-sm form-sm apimodel-input" name="name" id="pubname"/>
                                        <label for="applicationId">应用分类:</label>
                                        <select id="applicationId" name="applicationId" class="apimodel-select form-control input-sm"></select>
                                    </div>

                                    <!-- 接口设置div  -->
                                    <div id="typeId_api">
                                        <div class="form-group" id="urldiv">
                                            <label for="puburl">url:</label>
                                            <input type="text" class="form-control input-sm form-sm rightinput apimodel-input"
                                                   name="url" id="puburl"/>

                                            <label for="pubport">端口:</label>
                                            <input type="text" class="form-control input-sm form-sm" name="pubport" id="pubport"/>
                                        </div>
                                        <div class="form-group" id="pubprotocaldiv">
                                            <label for="pubprotocal">请求协议:</label>
                                            <select name="pubprotocal" id="pubprotocal" class="apimodel-select apimodel-input form-control input-sm">
                                                <option value="http">HTTP</option>
                                                <option value="webService">webService</option>
                                                <option value="socket">Socket</option>
                                            </select>
                                            <label for="pubmethod">请求方式:</label>
                                            <select name="method" id="pubmethod" class="apimodel-select form-control input-sm">
                                                <option value="GET">GET</option>
                                                <option value="POST">POST</option>
                                                <!-- <option value="DELETE">DELETE</option>
                                                <option value="PATCH">PATCH</option> -->
                                            </select>
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
                                            </select>

                                            <label for="pubreturndatatype">返回值类型:</label>
                                            <select name="returndatatype" id="pubreturndatatype" class="apimodel-select form-control input-sm">
                                                <option value="Object">Object</option>
                                                <!-- <option value="List">List</option> -->
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
                                        </div>

                                        <!-- 隐藏在大山深处的流程图，对外不可见 -->
                                        <div id="designer_canvas" style="display: none;"></div>
                                    </div>

                                </div>
                            </div>
                            <!--请求和响应参数-->
                            <div class="portlet-body" id="id-paraminfotable" style="display:none;">
                                <div class="searchWrap">
                                </div>
                                <button type="button" class="b-redBtn btn-i" id="addParamBtn"><i class="iconfont">&#xe682;</i>添加
                                </button>
                                <br>
                                <div id="reqparamtable"></div>
                                <div style="margin-bottom: 20px;">
                                    <%--<button type="button" class="b-redBtn btn-i" id="id-saveServiceModel-button"><i class="iconfont">&#xe638;</i>完成
                                    </button>--%>
                                </div>
                            </div>

                            <!-- 入参映射 -->
                            <!--  <div id="layoutparamtable" style="margin-top:20px;display: none;">123</div> -->
                            <div id="layoutparamtable" style="margin-top:20px;display: none;">
                                <div id="id-resplayoutparamtable" style="margin-top:20px;"></div>
                            </div>
                            <!-- 出参映射 -->
                            <div id="resplayoutparamtable" style="display: none;">
                                <div id="id-resplayoutparamtable-div" style="margin-top:20px;"></div>
                                <!--<button type="button" class="b-redBtn btn-i" id="id-saveServiceModel-button" style="float: right"><i class="iconfont">&#xe66a;</i>完成</button>-->
                            </div>
                            <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                                <ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;background:#9ecfef;height:200px"></ul>
                            </div>
                        </div>
                    </div>


                    <!-- 规则设置modal -->
                    <div id="layoutparamalert" style="margin-left: 40px;display: none;">
                        <!-- <div style="color: white;font-family: cursive;font-size: 21px;margin-top: 3%;">正则表达式：</div>
                            <input type="text" placeholder="请输入正则表达式" id="layoutregex"  style="margin-left: 15%; margin-top: 1%; width: 60%;height: 23%;"/><br> -->
                        <div style="color: white;font-family: cursive;font-size: 21px;margin-top: 3%;">常量参数：</div>
                        <input type="text" placeholder="请输入参数常量值" id="layoutconstant"  style="margin-left: 15%; margin-top: 1%; width: 60%;height: 2.5em;"/>
                    </div>


                    <!-- 参数设置 -->
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
                                                    <option value="7">命名空间</option>
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
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>