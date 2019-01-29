$(document).ready(function(){
	initOrderTable();
	
	$("#id-searchBtn").bind("click",reloadTableData);
	$("#id-resetBtn").bind("click",resetForm);
	resourceTree.init();
	
	// 为datatable外的父级设置高度
	$('#id-orderMineParamTable_wrapper').css('height', $('.panel-body').height()-60);
	// 动态为表格添加父级
	$('#id-orderMineParamTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#id-orderMineParamTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
});

function initOrderParamTable(){
	$("#id-orderMineParamTable").width("100%").dataTable({
		"columns":[
		           { "data" : "order_paramId" },
		           { "data" : "name" },
		           { "data" : "paramdesc" },
		           { "data" : "paramtype" },
		           { "data" : "isempty" },
		           { "data" : "pubparamname" },
		           { "data" : "ordername" },
		           { "data" : "" },
		 ],
		 ajax: {
			 url : webpath+'/interfaceOrder/selectMine',
			 "type" : 'POST',
			 "data" : function(d){
				 return $.extend( {}, d, {
					"jsonStr": form.serializeStr($("#id-mineSearchForm")) 
				 });
			 },
			 "dataSrc": function(json) {
				 json.iTotalRecords = json.total;
		         json.iTotalDisplayRecords = json.total;
		         return json.data;
			 }
		 },
		 columnDefs:[
		 			{
		 				"targets" : 0,//参数id
		 				"data" : null,
		 				"visible": false,
		 			},
		 			{
		 				"targets" : 1,//参数名称
		 				"data" : null,
		 			},
		 			{
		 				"targets" : 2,//参数说明
		 				"data" : null,
		 			},
		 			{
		 				"targets" : 3,//参数类型
		 				"data" : null,
		 			},
		 			{
		 				"targets" : 4,//是否可为空
		 				"data" : null,
		 				"render" : function(data,type,row){
		 					if(data == "1"){
		 						return "是"; 
		 					}else if(data == "0"){
		 						return "否";
		 					}
		 				}
		 			},
		 			{
		 				"targets" : 5,//映射的注册参数
		 				"data" : null,
		 			},
		 			{
		 				"targets" : 6,//映射的订阅接口
		 				"data" : null,
		 			},
		 			{
		 				"targets" : 7,//操作按钮目标列
		 				"data" : null,
		 				"render" : function(data, type,row) {
		 					var id = row.orderid;
		 					var html =  '<a href="javascript:void(0);" onclick="updateOrderParam(\''+id+'\')" class="icon-wrap" title="修改信息"><i class="iconfont i-btn">&#xe66f;</i></a>';
		 					html += '&nbsp;&nbsp;';
		 					html +=  '<a href="javascript:void(0);" onclick="deleteOrderParam(\''+id+'\')" class="icon-wrap" title="取消订阅"><i class="iconfont i-btn">&#xe614;</i></a>';
		 				  	return html;
		 				}
		 			}
		 		 ],
	})
}

//更新参数
function updateOrderParam(id){
	
}

//删除参数
function deleteOrderParam(id){
	
}