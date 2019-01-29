<% /**
    ----------- 本页面描述右侧面板项目的详细信息（设置参数ifream） ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>

<script>
var webpath = '<%=webpath%>';
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common-head.jsp"%>
    <title>单点管理</title>
    <%@ include file="../common-layer-ext.jsp"%>
    <%@ include file="../common-js.jsp"%>
    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>

    <style type="text/css">
    body{
            overflow:hidden;
            background:#fff!important;
        }
        .panel-heading > i.iconfont:first-child{
            font-size:20px;
            margin-right:4px;
        }
        .common-part .icon-tip{
           float:right;
           margin-top:2px;
        }
        .panel-heading > span{
            top:-2px;
            position:relative;
        }
        .bootstrapMenu{
            z-index: 10000;
            position: absolute;
            display: none;
            height: 23px;
            width: 158px;
        }
        .dropdown-menu{
            position:static;
            display:block;
            font-size:0.9em;
        }
         .help-block{
            display:block;
            float:right;
            color:#e15b52;
        }
        .modalpanl{
            width: 600px;
            margin-left: 50px;
            margin-top: 80px;
        }
        .dataTables_wrapper .dataTables_paginate {
            position: absolute;
            bottom: -50px;
            right: 30px;
        }
        .reqbtn{
            margin-top: 19px;
        }
        .param-input{
            height: 25px;
        }
        .xmlparam-modal{
            margin-left:-170px;
        }
        .pubidtoreq-textarea1{
            width: 300px;
            margin-top: 20px;
            margin-left: 2px;
        }
        .pubidtoreq-textarea2{
            border:none;
            width: 300px;
            margin-top: -379px;
        }
        .icon-tip {
            display:inline;   
         }
        .panel-body {
            border: none;
            height:700px;
        }
        
    </style>
</head> 
<body class="">    
    <!--右侧面板显示参数详情  -->
    <div class="panel-body common-content" id="param-div">
        <div>
            <ul class="nav nav-tabs">
              <li role="presentation" class="active" id="reqdiv"><a href="#">请求参数</a></li>
              <li role="presentation" id="respdiv"><a href="#">响应参数</a></li>
              <li role="presentation" id="definediv"><a href="#">参数自定义添加</a></li>
              <li role="presentation" id="sampledatadiv"><a href="#">示例数据</a></li>
            </ul>
        </div>
        <input type="hidden" id="res-req" name="type" value="0">
        <div class="portlet-body" id="id-paraminfotable">
            <div class="searchWrap">
                <form class="form-inline" id="pubidtoreq">
                    <div class="form-group">
                        <input type="hidden" id="pubid" name="pubid">
                        <input type="hidden" id="type" name="type">
                    </div>
                </form>
            </div>
            <div style="width: 200px;margin-right: 20px;  margin-top: 10px; margin-bottom: 10px;">
                <button type="button" class="b-redBtn btn-i" id="addParamBtn"><i class="iconfont">&#xe635;</i>添加</button>
                <button type="button" class="b-redBtn btn-i" id="addSampleDatBtn"><i class="iconfont">&#xe635;</i>添加测试数据</button>
            </div>
            
             <!-- <table id="reqparamtable"></table> -->
             <div id="reqparamtable"></div>
             
         </div>
         
         <div id="id-definedparamtable" style="display:none;">
                <button type="button" class="b-redBtn btn-i reqbtn" id="addXmlParamBtn"><i class="iconfont">&#xe635;</i>添加</button>
                <form>
                    <textarea class="form-control pubidtoreq-textarea1" rows="20" id="xmlstr" style="width:300px;height: 378px;"></textarea>
                </form>
                <div style="float:right">
                    <textarea class="form-control pubidtoreq-textarea2" rows="20" id="example-xmlstr" style="height:378px" readonly></textarea>
                </div>
         </div>
         
         <div id="id-sampledata" style="display:none">
            <button type="button" class="b-redBtn btn-i reqbtn" id="addSampleDataBtn"><i class="iconfont">&#xe635;</i>添加</button>
            <div style="font-size: large;font-weight: 600;">请提供测试数据
                  <i class="iconfont icon-tip" id="id-icontipdata" onmouseover="mouseOver()">&#xe6a8;</i>
            </div>
                <textarea id="sampledata" class="form-control" style="margin-top: 13px;width: 640px;height: 100px;overflow:auto;">
                </textarea>
         </div>
         
         
         </div>
    <input type="hidden" value="${type }" id="type"/>
    <%@ include file="./param-model.jsp"%> 
    <script src="<%=webpath %>/resources/js/puborder/paramiframe.js"></script>
    <script src="<%=webpath %>/resources/js/puborder/pro_manage2.js"></script>
    <script src="<%=webpath %>/resources/js/puborder/pub-model.js"></script>
</body>
</html> 
    
    
