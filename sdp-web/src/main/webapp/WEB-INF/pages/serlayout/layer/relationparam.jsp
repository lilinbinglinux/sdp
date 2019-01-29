<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>API 详细信息</title>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-js.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css"
          type="text/css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">

    <script src="${pageContext.request.contextPath}/resources/plugin/raphael/js/raphael.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/raphael/js/mousetrap.min.js"
            type="text/javascript"></script>
    <script src="<%=webpath %>/resources/js/serlayout/common/newobj.js" type="text/javascript"></script>
    <script src="<%=webpath %>/resources/js/util_common.js" type="text/javascript"></script>
    <%-- <script src="<%=webpath %>/resources/js/serlayout/layer/relationparam.js"></script> --%>
    <script src="<%=webpath %>/resources/js/serlayout/layer/relationparam2.js"></script>

    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/core/base.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/layer/layer.js"></script>

    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.exhide.js"></script>


    <style>
        .layoutparam-modal-content {
            width: 164%;
            margin-left: -30%;
            height: 80%;
        }

        .layer-demo {
            background-color: gray;
        }
        .layer-demo .layui-layer-btn1 {
            color: #000;
        }
        .input_control {
            margin: 0 auto;
            width: 260px;
            padding: 6px 12px;
            margin-left: 10px;
            font-size: 14px;
            line-height: 1.42857143;
            color: #555;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
        }

        .component1 {
            margin-top: 10px;
        }

    </style>
</head>
<body>
<div id="layoutparam-divlayer">
    <div class="searchWrap">
        <form class="form-inline" id="layout-pubidtotype">
            <div class="form-group">
                <input type="hidden" id="pubid" name="pubid">
                <input type="hidden" id="type" name="type">
            </div>
        </form>
    </div>
    <div style="width: 200px;margin-left: 80%;margin-top: 19px;"></div>
    <div>
        <ul class="nav nav-tabs">
            <li role="presentation" id="reqdiv" class="active"><a href="#">参数映射</a></li>
            <li role="presentation" id="respdiv"><a href="#">条件设置</a></li>
        </ul>
    </div>
    <div id="layoutparamtable" style="margin-top:20px"></div>
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;background:#9ecfef;height:200px"></ul>
    </div>
    <div id="conditionset" style="display:none;margin-left: 200px;">
        <div style="float: right;">
            <a class="addvar btn btn-primary  btn-sm" onclick="addVar()" href="javascript:void(0);"  role="button">增加变量</a>
            <a class="deletevar btn btn-primary  btn-sm" onclick="deleteVar()" href="javascript:void(0);" role="button">删除变量</a></div>
        <div style="display:block" class="component">
            <div class="component1">
                <label>变量：</label>
                <input type="text" class="input_control" id="parameter1"
                       initidvalue="" onclick="inputClick(this)"/>
                <br>
            </div>
        </div>
        <div class="component1">
            <label>条件：</label>
            <textarea rows="5" cols="70" id="condition" class="input_control"></textarea>
        </div>
    </div>
    <div class="rows">
        <button type="button" class="btn btn-primary btn-sm" style="position: fixed; bottom: 10px; right: 20px;"
                id="addRelationBtn">确认
        </button>
    </div>
    <div style="clear: both;"></div>
</div>

<!-- 规则设置modal -->
<div id="layoutparamalert" style="margin-left: 40px;display: none;">
    <!-- <div style="color: white;font-family: cursive;font-size: 21px;margin-top: 3%;">正则表达式：</div>
        <input type="text" placeholder="请输入正则表达式" id="layoutregex"  style="margin-left: 15%; margin-top: 1%; width: 60%;height: 23%;"/><br> -->
    <div style="color: white;font-family: cursive;font-size: 21px;margin-top: 3%;">常量参数：</div>
    <input type="text" placeholder="请输入参数常量值" id="layoutconstant"
           style="margin-left: 15%; margin-top: 1%; width: 60%;height: 23%;"/>
</div>

<!--参数添加  -->
<div id="layoutparamset" style=" margin-top: 5%;margin-left: 10%;font-size: 15px;display: none;">
    <label class="control-label" style="margin-right: 8%;">参数:</label><input class="layout_paramadd" style="width:200px"
                                                                             type="text" id="ecode"/> <br>
    <label class="control-label" style="margin-right: 8%;margin-top: 3%;">说明:</label><input class="layout_paramadd"
                                                                                            style="width:200px"
                                                                                            type="text" id="reqdesc"/>
    <br>
    <label class="control-label" style="margin-right: 2%;margin-top: 3%;">参数类型:</label>
    <select id="reqtype" class="layout_paramadd" style="width:80px;height: 25px;width:200px">
        <option value="String" selected="selected">String</option>
        <option value="int">int</option>
        <option value="boolean">boolean</option>
        <option value="Object">Object</option>
        <option value="List">List</option>
    </select><br>
    <label class="control-label" style="margin-right: 2%;margin-top: 3%;">参数位置:</label>
    <select id="parampos" class="layout_paramadd" style="width:80px;height: 25px;width:200px">
        <option value="0" selected="selected">请求体</option>
        <option value="4" selected="selected">请求头</option>
        <option value="1">url上的参数</option>
        <option value="2">响应头</option>
        <option value="3">响应体</option>
        <option value="5">xml请求体属性</option>
        <option value="6">xml响应体属性</option>
    </select><br>

    <label class="control-label" style="margin-right: 2%;margin-top: 3%;">是否必传项:</label>
    <select id="isempty" class="layout_paramadd" style="width:80px;height: 25px;width:200px">
        <option value="0" selected="selected">否</option>
        <option value="1">是</option>
    </select><br>
</div>


<script type="text/javascript">
   /*  var nodeId = '${nodeId}';
    var nodeName = '${nodeName}';
    var nodeType = '${nodeType}'; */
	var joinId = '${joinId}';
</script>
</body>
</html>
        
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

