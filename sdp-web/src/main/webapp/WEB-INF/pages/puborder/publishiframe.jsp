<% /**
    ----------- 本页面描述右侧面板模块的详细信息（项目展示ifream） ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/util_common.js" type="text/javascript"></script>
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
        .ztree{
            width:50%;
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
        .parammodel{
                width: 1500px;
        }
        .dataTables_wrapper .dataTables_paginate {
            position: absolute;
            bottom: -300px;
            right: 30px;
        }
        .reqbtn{
            margin-left: 679px;
            margin-bottom: 20px;
            margin-top: 15px;
        }
        .param-input{
            height: 25px;
        }
        .xmlparam-modal{
            margin-left:-170px;
        }
        .addbtn-proinfo{
            margin-left: 600px;
            margin-top: -20px;
        }
    </style>
</head> 
<body>
	<!--右侧面板显示项目详情  -->
	<div class="panel-body common-content" id="id-prodiv" style="">
		<div class="searchWrap">
			<form class="form-inline" id="id-publishsearchForm">
				<div class="form-group">
					<!-- <input type="hidden" name="typeId" value="1"> -->
					<input type="hidden" name="parentId" id="publishparentId">
				</div>
			</form>
    	</div>
    	<button type="button" class="b-redBtn btn-i addbtn-proinfo" id="id-addpublishinfo"><i class="iconfont">&#xe635;</i>添加</button>
	    <div id="id-proinfodiv">
	    	<table id="id-pubinfotable">
	    		<thead>
	    			<tr>
	                    <th>pubid</th>
	    				<th>接口名称</th>
	    				<th>创建日期</th>
	    				<th>说明</th>
	    				<th>操作</th>
	    			</tr>
	    		</thead>
	    	</table>
	    </div>
	</div>
	<%@ include file="./flowchart/flowchartmodel.jsp"%>
	<%@ include file="../process/processmodel.jsp"%>
	
	<script src="<%=webpath %>/resources/js/puborder/publishiframe.js"></script>
</body>
</html>	
	
