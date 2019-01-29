<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/puborder/order/order-model.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../../common-head.jsp"%>
	<title>接口订阅管理</title>
	<%@ include file="../../common-layer-ext.jsp"%>
	<%@ include file="../../common-body-css.jsp"%>
	<style type="text/css">
		body{
			overflow:hidden;
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
    		margin-left: 500px;
    		margin-top: 80px;
		}
		.ztree{
		    width:365px;
		    height:500px;
		    overflow:auto;
		}
		
	</style>
</head> 
<body>
	<div class="row">
	     <div class="col-lg-4 col-md-4 row-tab" id="flowchart-leftContent">
		     <div id="pro-panel" class="panel panel-default common-wrapper">
  					<div class="panel-heading common-part">
					  <i class="iconfont changeopen">&#xe6db;</i>
					  <span class="changeopen">项目接口列表</span>
					  <i class="iconfont icon-tip" id="flowchart-toggleBar" isOpen="true" style="margin-left: 15px;">&#xe656;</i>
					  <i class="iconfont icon-tip">&#xe6a8;</i>
					</div>
  					<div class="panel-body common-content">
  						<div id="proTree" class="ztree changeopen"></div>
  					</div>
			 </div>
	     </div>
	     <div class="col-lg-8 col-md-8 row-tab panel-r" id="flowchart-rightContent">
	     	<div id="con-panel" class="panel panel-default">
	     		<div class="panel-body" id="order-pro">
	     		    <iframe id="order-manageall" name="order-manageall"
                        style="width:700px;height:590px;" scrolling="auto" frameborder="0" allowTransparency="true">
                    </iframe>
	     		    <%-- <%@ include file="./order-reqmanage.jsp"%>
	     		    <%@ include file="./order-publishmanage.jsp"%>
	     		    <%@ include file="./order-project.jsp"%> --%>
	     		</div>
	     	</div>
	     </div>
	</div>
	
	<%@ include file="../../common-js.jsp"%>
	<script src="<%=webpath %>/resources/js/puborder/order/orderpro_manage.js"></script>
</body>
</html>