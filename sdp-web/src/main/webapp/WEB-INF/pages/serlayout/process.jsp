<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String webpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程图</title>
<%@ include file="../common-js.jsp"%>
<link
	href="${pageContext.request.contextPath}/resources/plugin/raphael/css/taglib.css"
	type="text/css" rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.12.1/jquery-ui.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/plugin/jquery-left-menu/css/reset.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/plugin/jquery-left-menu/css/style.css"
	rel="stylesheet" />
<style>
	.shape_groups  .shape_img img{
		width: 22px;
    	height: 22px;
    	margin-top: 9px;
    	float: left;
	}
	.shape_groups  .shape_img .tooltips{
		margin-left: 10px;
    	font-family: MicrosoftYaHei;
    	font-size: 14px;
    	font-weight: normal;
    	font-stretch: normal;
    	color: #333333;
    	padding: 0;
    	height: 40px;
    	line-height: 40px;
	}
</style>
</head>
<body>
	<div id="process">
		<div class="designer" id="designer">
			    <div class="designer_header">
			        <div class="toolbar">
			            <div id="btn_back">
			               <img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/tool-return.png" class="toolimg">
			           </div>
			           <div id="save_btn">
			               <img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/tool-save.png" class="toolimg">
			           </div>
					   <div id="shortcutDesc">
							<img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/tool-help.png" class="toolimg">
					   </div>
			           <div class="conHref">
			                <a href="https://code.sdp.com.cn/confluence/pages/viewpage.action?pageId=14581995"
							   target="_blank">
							   <img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/tool-note.png" class="toolimg">
							</a>
			           </div>
			       </div>
			   </div>
			   <div class="designer_body">
			       <div class="shape_panel">
						<div class="shape_header"><div class="title">可视化编辑器</div></div>
						<div class="shape_search"></div>
	
			           <div class="shape_list" >
			           		<!-- <div class="shapes"><div class="shape_img "  nodetype="rectangle" nodeStyle="3">
			           		     <img src=" base + '/icon-setting.png" class=" ">
			           		    <span class="tooltips">接口</span>
			           		</div></div>
			           		<div class="shapes"><div class="shape_img" nodetype="circle" nodeStyle="5">
			           		     <img src=" base + '/icon-aggregationStart.png" class=" "  />
			           		    <span class="tooltips">聚合开始</span>
			           		</div></div>
			           		<div class="shapes"><div class="shape_img" nodetype="circle" nodeStyle = "6">
			           		     <img src=" base + '/icon-aggregationEnd.png" class=" " >
			           		    <span class="tooltips">聚合结束</span>
							   </div></div> -->
							<ul class="cd-accordion-menu animated shape_groups">
								<li class="has-children">
									<input type="checkbox" name ="group-1" id="group-1" checked>
									<label for="group-1">编排类型</label>
									<ul>
										<li>
											<a href="#0" class="shape_img" nodetype="rectangle" nodeStyle="3">
												<img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/icon-api.png">
												<sapn class="tooltips">接口</sapn>
											</a>
										</li>
										<li>
											<a href="#0" class="shape_img" nodetype="rectangle" nodeStyle="3">
												<img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/icon-api.png">
												<sapn class="tooltips">聚合开始</sapn>
											</a>
										</li>
										<li>
											<a href="#0" class="shape_img" nodetype="rectangle" nodeStyle="3">
												<img src="${pageContext.request.contextPath}/resources/plugin/raphael/img/icon-api.png">
												<sapn class="tooltips">聚合结束</sapn>
											</a>
										</li>
									</ul>
								</li>
							</ul>
					   </div>
			           <div class="imgtips"></div>
			       </div>
			       <div class="designer_viewport">
			           <div class="designer_layout">
			               <div id="canvas_container">
			                   <div id="designer_canvas">
			                   </div>
			               </div>
			           </div>
			       </div>
			   </div>
			</div>
	</div>
	<!--   接收子页面传值-->
	<div style="display: none">
		<div id="success"></div>
		<div id="pubname"></div>
		<div id="pubid"></div>
	</div>
	<%@ include file="../puborder/flowchart/flowchartmodel.jsp"%>
</body>
<script>

    var flowChartId = '${flowChartId}';
    var serVerId = '${serVerId}';
    var flowChartName = '${serName}';
    var webapp = '${pageContext.request.contextPath}';
    var flowType = '${flowType}';
    var tenantId = '${tenantId}';
    var initserNodeArray = '${serNodeArray}';
    var initserJoinArray = '${serJoinArray}';
    var serFlowType = '${serFlowType}';
    var serFlowTypeName= '${serFlowTypeName}';

    var typeId = '${ typeId }';
    var typeName = '${ typeName }';


    if (initserJoinArray != null && initserJoinArray != "") {
        if (initserJoinArray.indexOf("%@") > 0) {
        		initserJoinArray = initserJoinArray.replace(/\%\@/g, "\'");
        }
    }
    idflowChartId = getQueryString("id");
    function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		console.log(reg);
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURI(r[2]);
        } else {
            return null;
        }
    }
</script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resources/plugin/utils/json.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resources/plugin/zTree_v3/js/jquery.ztree.exhide.js"></script>
<!--流程图需要引入四个文件 -->
<script
	src="${pageContext.request.contextPath}/resources/plugin/raphael/js/raphael.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/plugin/raphael/js/mousetrap.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/plugin/raphael/js/demo.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/plugin/raphael/js/jquery.taglib.js"
	type="text/javascript"></script>
<!--流程图引入文件结束 -->
<!--flowchartInit.js用于定制流程图 -->
<script
	src="${pageContext.request.contextPath}/resources/js/serlayout/process/processtInit.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/serlayout/common/newobj.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/serlayout/process/process.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/util_common.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/plugin/jquery-left-menu/js/main.js"
	type="text/javascript"></script>	
</html>