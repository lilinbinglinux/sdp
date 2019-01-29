<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程图编辑</title>
 	<link href="${pageContext.request.contextPath}/js/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/ligerui/lib/ligerUI/skins/Gray2014/css/all.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/ligerui/lib/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery/jquery-ui.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/flowchart/process/flowChartAdd.js" type="text/javascript"></script>
	
</head>
<body>
<div align="center" style="padding-top:10px">
		<input id="flowChartId" type="hidden" value="${flowChart.flowChartId}">
		<form id="form1" >
			<table align="center">
				<tr>
					<td><label for="res_type_name">流程图名称：</label></td>
					<td>
						<input id="flowChartName"  type="text"
									name="entity_name"
									validate="{required:true}"
									class="ui-textbox" 
									value="${flowChart.flowChartName}">
					</td>
				</tr>
		</table>
		<br/>
		<button   class="l-button l-button-brand btn_query">&nbsp;确认</button>
		<button  id="cancel" class="l-button l-button-brand btn_query">&nbsp;取消</button>
		</form>
		</div>
</body>
<script type="text/javascript">
//回显
var webapp = "${pageContext.request.contextPath}";
var flowChartId="${flowChart.flowChartId}";
if(flowChartId!=null&&flowChartId.length>0){
	document.getElementById("flowChartId").value=flowChartId;  
}  
var flowChartId = $("#flowChartId").val();
var action;
if(flowChartId!=null&&flowChartId!=""){
	 $("#flowChartId").attr("readOnly",true); 
	action= webapp+"/flowchart/update";
}else{
	action= webapp+"/flowchartId/save";
}
$(function (){
$.metadata.setType("attr", "validate");
var v = $("form").validate({
    debug: true,//不使用form表单提交，只校验
    errorPlacement: function (lable, element)
    {
    	if (element.hasClass("l-textarea"))
        {
            element.ligerTip({ content: lable.html(),width: 95, target: element[0] }); 
        }
        else if (element.hasClass("l-text-field"))
        {
            element.parent().ligerTip({ content: lable.html(),width: 95,  target: element[0] });
        }
        else
        {
            lable.appendTo(element.parents("td:first").next("td"));
        }
    },
    success: function (lable)
    {
        lable.ligerHideTip();
        lable.remove();
    },
    submitHandler: function ()
    {
        $("form .l-text,.l-textarea").ligerHideTip();
        confirm();//校验通过，执行添加或修改
    }
});
});
</script>
</html>