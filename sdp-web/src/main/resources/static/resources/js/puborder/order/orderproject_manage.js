$(document).ready(function(){
		
});

//初始化用户列表表格
function initurlprojecttableTable(){
	$("#projecttable").width("100%").dataTable({
		destroy: true,
		"columns":[
		           { "data": "proid" },
		           { "data": "name" },
		           { "data": "proecode" },
		           { "data": "proversion" },
		 ],
		 ajax: {
		     url:webpath+'/pro/selectPage',
		     "type": 'POST',
		     async:false,
		     "data": function (d) {//查询参数
		           return $.extend( {}, d, {
		              "jsonStr": form.serializeStr($("#projectmanagepubid"))
		           });
		     },
		     "dataSrc": function (json) {
		           json.iTotalRecords = json.total;
		           json.iTotalDisplayRecords = json.total;
		           return json.data;
		     }
		},
		
		columnDefs:[
		{
			"targets" : 0,
			"data" : null,
			"visible": false
		},        
		{
			"targets" : 1,
			"data" : null,
			"visible": true
		},
		{
			"targets" : 2,//操作按钮目标列
			"data" : null
			
		},
		{
			"targets" : 3,//操作按钮目标列
			"data" : null,
			"visible": true,
		}
	]
	});
}

function reloadTableData(table,isCurrentPage){
	table.dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

//判断table是否为空
function tableIsNull(){
	var tbody = document.getElementById('projecttable');
	for(var i = 1;i<=tbody.rows.length;i++){
	   for(var j= 0;j<=tbody.rows[i].cells.length;j++){
		   if("表中数据为空" == tbody.rows[i].cells[j].innerHTML){
			   return true;
		   }
		   return false;
	   }
	}
}

