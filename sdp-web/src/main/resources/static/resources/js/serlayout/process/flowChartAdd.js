$(function ()
{	
	$("form1").ligerForm({
		inputWidth: 220
	});
	$("#cancel").click(function() {
		parent.hideMenu();
		parent.cancelTypelayer();
	});
});
function confirm(){
	var flowChartId = $("#flowChartId").val();
	var flowChartName=$("#flowChartName").val();
	$.ajax({
		url:  action,
		type: "POST",
		async: true,
		data: 
		{
			"flowChartId":flowChartId,
			"flowChartName":flowChartName,
		},
		success: function(data) 
		{
			if(data=="success")
			{
				parent.hideMenu();
				parent.closeTypelayer();
			}else{
				alert("提交失败");
			}
		}
	}); 
}

