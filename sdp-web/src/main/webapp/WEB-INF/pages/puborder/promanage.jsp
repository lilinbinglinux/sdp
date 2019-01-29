<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common-js.jsp"%>
	<%@ include file="../common-head.jsp"%>
	<title>服务注册</title>
	<%@ include file="../common-layer-ext.jsp"%>
	<%@ include file="../common-body-css.jsp"%>
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
		.dataTables_wrapper .dataTables_paginate {
			position: absolute;
			bottom: -300px;
			right: 30px;
		}
		.ztree{
		  overflow: auto;
          height: 95%;
          width: 103%;
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
	     <div class="col-lg-8 col-md-8 row-tab panel-r"  id="flowchart-rightContent">
	     	<div id="con-panel" class="panel panel-default">
	     		<div class="panel-body" id="panel-jsp">
    		 		<iframe id="panel-childPage" name="panel-childPage" 
    		 		src="../pubIfream/proIframe" scrolling="auto" frameborder="0" allowTransparency="true" 
    		 		style="width:100%;height:100%;"></iframe>
	     		</div>
	     	</div>
	     </div>
	    
	</div>

	<!-- 空白的菜单 -->
	<div id="blankContextMenu" class="dropdown bootstrapMenu">
	      <ul class="dropdown-menu">
	          <li>
	              <a href="javascript:void(0);" class="addFolder">
	                 <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i> 
	                 <span id="m1">添加项目</span>
	              </a>
	          </li>
	      </ul>
	</div>
	<!-- 文件夹的菜单 -->
	<div id="folderContextMenu" class="dropdown bootstrapMenu">
	      <ul class="dropdown-menu">
	          <li>
	              <a href="javascript:void(0);" class="addFolder">
	                 <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i> 
	                 <span id="m2">添加模块文件夹</span>
	              </a>
	          </li>
	           <li>
	              <a href="javascript:void(0);" class="updateInterface">
	                 <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i> 
	                 <span data-toggle="modal" data-target="#proModal">修改项目</span>
	              </a>
	          </li>
	          <li>
	              <a href="javascript:void(0);" class="deleteInterface">
	                 <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i> 
	                 <span>删除项目</span>
	              </a>
	          </li>
	      </ul>
	</div>
	<!-- 模块文件夹的菜单 -->
	<div id="linkContextMenu" class="dropdown bootstrapMenu">
	      <ul class="dropdown-menu">
	          <li>
	              <a href="javascript:void(0);" class="updateInterface">
	                 <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i> 
	                 <span data-toggle="modal" data-target="#proModelModal">修改模块</span>
	              </a>
	          </li>
	          <li>
	              <a href="javascript:void(0);" class="deleteInterface">
	                 <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i> 
	                 <span>删除模块</span>
	              </a>
	          </li>
	      </ul>
	</div>
	<%@ include file="./pub-model.jsp"%>
	<script src="<%=webpath %>/resources/js/puborder/pro_manage2.js"></script>
	<script src="<%=webpath %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
</body>
</html>