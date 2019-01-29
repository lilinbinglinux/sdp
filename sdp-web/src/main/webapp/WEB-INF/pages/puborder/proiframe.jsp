<% /**
    ----------- 本页面描述右侧面板项目的详细信息（项目展示ifream） ----------------
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
<script>
var webpath = '<%=webpath%>';
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common-head.jsp"%>
    <title>项目详情ifream</title>
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
        .dataTables_wrapper .dataTables_paginate {
            position: absolute;
            bottom: -55px;
            right: 30px;
        }

        .addbtn-proinfo{
            margin-right: 20px;
            float: right;
        }
    </style>
</head> 
<body>
	<!--右侧面板显示项目详情  -->
	<div class="panel-body common-content" id="id-prodiv">
		<div class="searchWrap">
			<form class="form-inline" id="id-prointype">
				<div class="form-group">
					<input type="hidden" name="typeId" value="0" id="protypeId">
				</div>
			</form>
    	</div>
    	<button type="button" class="b-redBtn btn-i addbtn-proinfo" id="id-addproinfo"><i class="iconfont">&#xe635;</i>添加</button>
	    <div id="id-proinfodiv">
	    	<table id="id-proinfotable">
	    		<thead>
	    			<tr>
	                    <th>id</th>
	    				<th>项目名称</th>
	    				<th>编码</th>
	    				<th>版本</th>
	    				<th>项目说明</th>
	    				<th>创建日期</th>
	    				<th>操作</th>
	    			</tr>
	    		</thead>
	    	</table>
	    </div>
	</div>
	<%@ include file="./pub-model.jsp"%>
	<script src="<%=webpath %>/resources/js/puborder/pro_manage2.js"></script>
	<script src="<%=webpath %>/resources/js/puborder/proifream.js"></script>
	<script src="<%=webpath %>/resources/js/puborder/pub-model.js"></script>
</body>
</html>	
	
