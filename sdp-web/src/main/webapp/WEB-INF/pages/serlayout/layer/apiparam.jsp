<% /**
 ----------- 本页面描述右侧面板api的详细信息（设置参数ifream） ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    var webpath = '<%=webpath%>';
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>API 详细信息</title>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-js.jsp" %>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css"
          type="text/css">

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/css/common.css">
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/utils/json.js"></script>

    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.exhide.js"></script>


    <style type="text/css">
        body {
            overflow: hidden;
            background: #fff !important;
        }

        .panel-heading > span {
            top: -2px;
            position: relative;
        }

        .panel-body {
            border: none;
            height: auto;
        }

        #id-parambasetable .form-control {
            width: 27%;
            margin-right: 10px;
        }

        #id-parambasetable label {
            width: 17%;
            text-align: right;
            margin-right: 3px;
        }

        #allparamset .form-inline {
            margin-bottom: 10px;;
        }

        #allparamset input, #allparamset select {
            width: 80px;
            margin-bottom: 5px;
        }

        .form-inline > div {
            float: left;
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
                    <label for="pubname">接口名称:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" name="name" id="pubname"
                    data-toggle="tooltip" title="该名称已被占用"/>
                    <%--   <label>接口类型:</label>
                       <input name="typeId" type="radio" value="2" checked/>接口
                       <input name="typeId" type="radio" value="4"/>jar定义--%>
                    <%-- <label for="serviceType" class="serviceType">服务类型:</label>
                    <select id="serviceTypeId" name="serviceType"
                            class="apimodel-select form-control input-sm serviceType">
                        <c:forEach var="ServiceTypeBean" items="${serviceTypes}">
                            <option value="${ServiceTypeBean.serTypeId}">
                                    ${ServiceTypeBean.serTypeName}
                            </option>
                        </c:forEach>
                    </select> --%>
                    <label for="serviceType" class="serviceType">服务类型:</label>
                    <input id="serviceTypeIdLOL" type="text" value="${ typeName }"
                           class="form-control input-sm form-sm apimodel-input serviceType" name="serviceType" readonly/>

                  <input id="serviceTypeId" type="hidden" value="${ typeId }"
                         class="form-control input-sm form-sm apimodel-input serviceType" name="serviceTypeId" readonly/>
                    <%--<input id="serviceTypeId" type="text" value=""--%>
                           <%--class="form-control input-sm form-sm apimodel-input serviceType" name="serviceType"--%>
                           <%--onclick="showMenu(); return false;" idvalue=""/>--%>
                    <%--<div id="menuContent" class="menuContent" style="display:none; position: absolute;">--%>
                        <%--<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;background: #4ba7cf;"></ul>--%>
                    <%--</div>--%>


                    <%-- <label for="apiserviceType" class="apiserviceType">接口类型:</label>
                    <select id="apiserviceTypeId" name="apiserviceType"
                            class="apimodel-select form-control input-sm apiserviceType">
                        <c:forEach var="serviceApiTypeBean" items="${apiServiceTypes}">
                            <option value="${serviceApiTypeBean.apiTypeId}">
                                    ${serviceApiTypeBean.typeName}
                            </option>
                        </c:forEach>
                    </select> --%>
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
                        <select name="pubprotocal" id="pubprotocal"
                                class="apimodel-select apimodel-input form-control input-sm">
                            <option value="http">http</option>
                            <option value="webService">webService</option>
                            <option value="socket">socket</option>
                        </select>
                        <label for="pubmethod" class="pubmethod">请求方式:</label>
                        <select name="method" id="pubmethod" class="apimodel-select form-control input-sm pubmethod">
                            <option value="GET">GET</option>
                            <option value="POST">POST</option>
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
                        <label for="reqdatatype">请求值类型:</label>
                        <select name="reqdatatype" id="pubreqdatatype" class="apimodel-select form-control input-sm">
                            <option value="Object" selected>Object</option>
                            <option value="List">List</option>
                        </select>

                        <label for="pubreturndatatype">返回值类型:</label>
                        <select name="returndatatype" id="pubreturndatatype"
                                class="apimodel-select form-control input-sm">
                            <option value="Object" selected>Object</option>
                            <option value="List">List</option>
                        </select>
                    </div>
                    <div class="form-group" id="pubcharsetdiv">
                        <label for="inCharset">请求参数字符集</label>
                        <select id="inCharset" class="apimodel-select form-control input-sm">
                            <option selected value="UTF-8">UTF-8</option>
                            <option value="UTF-16">UTF-16</option>
                            <option value="GBK">GBK</option>
                            <option value="ISO-8859-1">ISO-8859-1</option>
                            <option value="US-ASCII">US-ASCII</option>
                        </select>

                        <label for="outCharset">响应参数字符集</label>
                        <select id="outCharset" class="apimodel-select form-control input-sm ">
                            <option selected value="UTF-8" >UTF-8</option>
                            <option value="UTF-16">UTF-16</option>
                            <option value="GBK">GBK</option>
                            <option value="US-ASCII">ISO-8859-1</option>
                            <option value="US-ASCII">US-ASCII</option>
                        </select>
                    </div>

                    <div class="form-group" id="pubdescdiv">
                        <label for="pubdesc">描述:</label>
                        <textarea class="apimodel-area form-control" name="pubdesc" id="pubdesc" rows="3"
                                  style="width: 76%;"></textarea>
                        <label for="addPubBtn"></label>
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
                            <div class="ecode">
                                <label class="control-label">参数:</label>
                                <input class="form-control param-input" type="text"/>
                            </div>
                            <div class="reqdesc">
                                <label class="control-label">说明:</label>
                                <input class="form-control param-input" type="text"/>
                            </div>
                            <div class="reqtype">
                                <label class="control-label">参数类型:</label>
                                <select class="form-control">
                                    <option value="String" selected="selected">String</option>
                                    <option value="int">int</option>
                                    <option value="boolean">boolean</option>
                                    <option value="Object">Object</option>
                                    <option value="List">List</option>
                                    <option value="List_Object">List_Object</option>
                                    <option value="List_List">List_List</option>
                                </select>
                            </div>
                            <div class="parampos">
                                <label class="control-label">参数位置:</label>
                                <select class="form-control" style="width: 110px;">
                                    <option value="0">请求体</option>
                                    <option value="4">请求头</option>
                                    <option value="1">url上的参数</option>
                                    <option value="2">响应头</option>
                                    <option value="3">响应体</option>
                                    <option value="5">xml请求体属性</option>
                                    <option value="6">xml响应体属性</option>
                                    <option value="7">命名空间</option>
                                </select>
                            </div>
                            <div class="maxlength">
                                <label class="control-label">最大长度:</label>
                                <input class="form-control param-input" type="text" id="maxlength"/>
                            </div>
                            <div class="namespace">
                                <label class="control-label">命名空间:</label>
                                <input class="form-control param-input" type="text"/>
                            </div>
                            <div class="isempty">
                                <label class="control-label">是否必传项:</label>
                                <select class="form-control">
                                    <option value="false" selected="selected">否</option>
                                    <option value="true">是</option>
                                </select>
                            </div>
                            <button type="button" class="btn btn-default addparam" aria-label="Left Align"
                                    style="float: left;">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            </button>
                            <div style="clear: both;"></div>
                        </div>
                    </div>
                </form>
            </div>
            <div style="clear: both;"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="addParamSetBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="<%=webpath %>/resources/js/serlayout/common/newobj.js"></script>
<script src="<%=webpath %>/resources/js/serlayout/layer/apiparam.js"></script>
<script src="<%=webpath %>/resources/plugin/ajaxfileupload.js"></script>
<script src="<%=webpath %>/resources/js/util_common.js" type="text/javascript"></script>

<script type="text/javascript">
    var nodeId = '${nodeId}';
    var nodeName = '${nodeName}';
    nodeName = unescape(unescape(nodeName));
    var nodeStyle = '${nodeStyle}';
    var nodeType = '${nodeType}';
    var serTypesTreedata = '${serviceTypes}';
    var serTypesObjs = JSON.parse(serTypesTreedata);
</script>
</body>
</html>


