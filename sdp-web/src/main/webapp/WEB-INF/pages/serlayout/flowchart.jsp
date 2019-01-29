<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common-head.jsp"%>
<%@ include file="../common-layer-ext.jsp"%>
<%@ include file="../common-js.jsp"%>
<title>流程图</title>
<link
	href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css">
<link
	href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css"
	rel="stylesheet" type="text/css">
<script>
	var typeId = '${ typeId }';
	var typeName = '${ typeName }';
</script>
<script src="<%=webpath %>/resources/plugin/ajaxfileupload.js"></script>
<script
	src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/serlayout/process/flowchart.js"
	type="text/javascript"></script>
</head>
<style>
textarea {
	border-radius: 3px;
	border: 1px solid #000;
}

.b-blueBtn {
	background-color: #fff;
	color: #0070d2;
	border: 1px solid #d8dde6;
	border-radius: 2px;
	height: 26px;
}

.b-blueBtn:hover {
	color: #005fb2;
	background-color: #f4f6f9;
}

#org-panel .panel-heading {
	border-top: 3px solid #0070d2;
}

#org-panel .panel-heading i {
	color: #0070d2;
}

#id-flowchartTable .l-grid-header {
	background-color: rgb(236, 245, 254);
}
</style>
<body>
	<div class="row">
		<div class="col-lg-12 col-md-12 row-tab">
			<div id="org-panel" class="panel panel-default common-wrapper">
				<div class="panel-heading common-part">
					<i class="iconfont">&#xe6ca;</i><span>流程图</span>
				</div>
				<div class="panel-body common-content">
					<div class="">
						<form class="form-inline" id="id-flowchartSearchForm">
							<div class="form-group">
								<label for="name">服务名称:</label> <input type="text"
									class="form-control inpu-sm" name="serName" id="serName" /> <input
									type="hidden" class="form-control inpu-sm" value="${typeId}" id="typeId" name="typeId"> <input
									type="hidden" class="form-control inpu-sm" value="${typeName}" id="typeName" name="typeName">
							</div>
							<div class="form-group">
								<label for="name">服务编码:</label> <input type="text"
									class="form-control inpu-sm" name="serCode" id="serCode" />
							</div>
							<button type="button" class="b-blueBtn btn-i" id="id-searchBtn">
								<i class="iconfont">&#xe67a;</i>查询
							</button>
							<button type="button" class="b-blueBtn btn-i" id="id-resetBtn">
								<i class="iconfont">&#xe647;</i>重置
							</button>
							<button type="button" class="b-blueBtn btn-i"
								id="id-layoutaddBtn">
								<i class="iconfont">&#xe682;</i>新建
							</button>
							<button type="button" class="b-blueBtn btn-i"
								id="id-layoutimportBtn">
								<i class="iconfont">&#xe669;</i>导入
							</button>
							<button type="button" class="b-blueBtn btn-i"
								id="id-testJsonpBtn" style="display: none">
								<i class="iconfont">&#xe669;</i>jsonP
							</button>
						</form>
					</div>
					<div id="id-flowchartTable" style="margin-top: 3%;"></div>
				</div>

			</div>
		</div>
	</div>

	<div id="importFilediv"
		style="display: none; padding: 20px 10px; overflow: hidden; min-height: 115px;">
		<input id="lefile" type="file" style="display: none" name="file">
		<div class="input-append">
			<div style="font-size: 17px; margin-left: 13%; margin-top: 4%;">请选择上传文件:</div>
			<input id="importFileinput" class="input-large" type="text"
				style="height: 30px; margin-left: 13%; width: 59%; margin-top: 4%;">
			<button type="button" class="btn btn-info"
				onclick="$('input[id=lefile]').click();">Browse</button>
		</div>

		<!--  <td width="30%" align="right">
                     选择要上传的excel文件
                     </td>
                     <td width="70%" style="text-align:center;">
                    <input type="file" id="file1" name="file"/>
                     </td> -->
	</div>

	<div id="interfaceinfodiv" style="display: none;" class="panel-body">
		<div style="margin-top: 3%">
			<p style="font-size: 14px; margin-bottom: 3%;">请求地址：</p>
			<input type="text" id="url-div"
				style="border: none; width: 100%; font-size: 13px;">
		</div>
		<div style="margin-top: 5%;">
			请求参数：
			<textarea rows="5" cols="98" id="urlreqparam-code" readonly
				style="background-color: whitesmoke; margin-top: 2%; overflow-y: scroll">
        </textarea>
			<!-- <pre class="pre-scrollable" id="urlreqparam-code" style="margin-top: 10px;max-height :310px;overflow-x: auto;"> -->
		</div>
		<div style="margin-top: 2%">
			响应参数：
			<textarea rows="5" cols="98" id="urlresponseparam-code" readonly
				style="background-color: whitesmoke; margin-top: 2%; overflow-y: scroll">
        </textarea>
			<!-- <pre class="pre-scrollable" id="urlresponseparam-code" style="margin-top: 10px;max-height :310px;overflow-x: auto;"> -->
		</div>
	</div>

</body>

</html>