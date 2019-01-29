<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/plugin/ligerui/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/laydate.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/logSer/log/logindex.js"></script>

<style>
.slds-form-element__label{
width:130px;}
 .l-grid-row-cell-inner  a{
	display: block;}
.dropdown-menu{
	position: static;
	display: block;
	font-size: 0.9em;
	min-width: 112px;
	top: 100%;
	left: 0;
	z-index: 1000;
	padding: 5px 0;
	margin: 2px 0 0;
	text-align: left;
	list-style: none;
	background-color: #fff;
	background-clip: padding-box;
	border: 1px solid rgba(0, 0, 0, .15);
	border-radius: 4px;
	box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
}
.dropdown-menu li a{
	display: block;
	clear: both;
	font-weight: normal;
	line-height: 1.42857143;
	white-space: nowrap;
	height: 30px;
}
.dropdown-menu li{
	margin-top:10px
}
.widthLimit{
	overflow: hidden;
	text-overflow:ellipsis;
	white-space: nowrap;
	font-family: unset;
}
.unlimit{
	word-wrap : break-word;
	font-family: unset;
	}
.text_field{
	width:174px;
	height:28px;
	border: 1px solid #D0D0D0;
	line-height: 26px;
	padding-left: 15px;
	}
	#maingrid{
		height:600px;
	}
	<%--#statusMsg{--%>
	<%--height:300px;--%>
	<%--}--%>
</style>
</head>
<body>
	<div style="position:relative">
	<div id="form1" class="container">
		<table class="slds-table">
			<tr>
				<td><label for="ser_name" class="slds-form-element__label">服务名称：</label></td>
				<td><input id="ser_name" type="text" class="ui-textbox" />
				</td>
				<td><label for="ser_date" class="slds-form-element__label">日期：</label></td>
				<td><input id="ser_date" type="text" class="ui-textbox" style="cursor: unset!important;" readonly=readonly />
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><button class="l-button l-button-brand btn_search1"> <i class="iconfont">&#xe67a;</i>查询</button></td>
			</tr>
		</table>
	</div>

	<div id="sendOrder" style="display:none">
		<div id="form3" class="container">
			<table class="slds-table">
				<tr>
					<td><label for="name" class="slds-form-element__label">订阅名称：</label></td>
					<td><input id="name" type="text" class="ui-textbox" />
					</td>
					<td><label for="order_date" class="slds-form-element__label">日期：</label></td>
					<td><input id="order_date" type="text" class="ui-textbox" style="cursor: unset!important;" readonly=readonly />
					<input id="ser_id" type="hidden"/>
					<input id="ser_version" type="hidden"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><button class="l-button l-button-brand btn_search b-redBtn">&nbsp;查询</button></td>
					<td><button class="l-button l-button-brand btn_close b-redBtn" style="margin-left:10px">&nbsp;返回</button></td>
				</tr>
			</table>
		</div>
		<div id="orderMsg"></div>
	</div>

	<div class="container">
		<div id="maingrid">  </div>
	</div>
	<div id="rMenu" class="dropdown bootstrapMenu" style="display:none">
		<ul class="dropdown-menu">
		</ul>
	</div>
	<div id="sendstatus" style="display:none">
		<div id="form2" class="container">
			<table class="slds-table">
				<tr>
				<td><label for="order_times" class="slds-form-element__label">请求时间范围：</label></td>
				<td><input id="order_times" type="text" class="text_field"  readonly=readonly />
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><button class="l-button l-button-brand btn_search1">&nbsp;查询</button></td>
				<td><button class="l-button l-button-brand btn_close1 b-redBtn" style="margin-left:10px">&nbsp;返回</button></td>
				</tr>
			</table>
		</div>
		<div id="statusMsg"></div>
	</div>
	<div id="sendDetail" style="display:none">
		<button class="l-button l-button-brand btn_close2 b-redBtn" style="float:right">&nbsp;返回</button>
		<div style="clear:both"></div>
		<div id="logDetail"></div>
	</div>
	</div>
<script>

	var projectName = "${pageContext.request.contextPath}";</script>
</body>
</html>