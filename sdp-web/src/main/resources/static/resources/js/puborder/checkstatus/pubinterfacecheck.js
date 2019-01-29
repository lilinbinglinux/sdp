$(document).ready(function(){
	initOrderTable();
	$("#id-pubinterface-li").bind("click",function(){
		window.parent.pubinterfacecheck();
	});
	$("#id-orderinterface-li").bind("click",function(){
		window.parent.orderinterfacecheck();
	});
	
	$("#id-searchBtn").bind("click",reloadTableData);
	$("#id-resetBtn").bind("click",resetForm);
	resourceTree.init();
	
	// 为datatable外的父级设置高度
	$('#id-orderMineTable_wrapper').css('height', $('.panel-body').height()-60);
	// 动态为表格添加父级
	$('#id-orderMineTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#id-orderMineTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
});

//使用datatable初始化用户表格
function initOrderTable(){
	$("#id-orderMineTable").width("100%").dataTable({
		destroy: true,
		"columns":[
		           { "data" : "pubid" },
		           { "data" : "name" },
		           { "data" : "url" },
		           { "data" : "login_id" },
		           { "data" : "tenant_id" },
		           { "data" : "createdate" },
		           { "data" : "checkstatus" },
		           { "data" : "" },
		 ],
		 ajax: {
			 url : webpath+'/publisher/selectPage',
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
				"targets" : 0,//订阅编号
				"data" : null,
				"visible": false,
			},
			{
				"targets" : 1,//订阅名称
				"data" : null,
			},
			{
				"targets" : 2,//url
				"data" : null,
			},
			{
				"targets" : 3,//用户
				"data" : null,
			},
			{
				"targets" : 4,//租户
				"data" : null,
			},
			{
				"targets" : 5,//订阅时间
				"data" : null,
				"render" : function(data, type, row) {
					 var html = '';
					 var newDate = new Date();
					 newDate.setTime(row.createdate);
					 if(data==null||data==""){
						 html += '暂无时间';
					 }else{
						 html += newDate.toLocaleString();
					 }
				     return html;
				}
			},
			{
				"targets" : 6,//当前审核状态
				"data" : null,
				"render" : function(data, type, row){
					var html = "";
					if(data == "000"){
						html = "待审核";
					}else if(data == "001"){
						html = "审批中";
					}else if(data == "100"){
						html = "审批通过";
					}else if(data == "999"){
						html = "审批不通过";
					}
					return html;
				}
			},
			{
				"targets" : 7,//操作按钮目标列
				"data" : null,
				"render" : function(data, type,row) {
					var pubid = row.pubid;
					var html =  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+pubid+'\',\''+100+'\')" class="icon-wrap" title="审批通过"><i class="iconfont i-btn">&#xe8c1;</i></a>';
					html += '&nbsp;&nbsp;';
					html +=  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+pubid+'\',\''+001+'\')" class="icon-wrap" title="正在审批"><i class="iconfont i-btn">&#xe632;</i></a>';
					html += '&nbsp;&nbsp;';
					html +=  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+pubid+'\',\''+999+'\')" class="icon-wrap" title="不通过"><i class="iconfont i-btn">&#xe67c;</i></a>';
				  	return html;
				}
			}
		 ],
	});
}


//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
	$("#id-orderMineTable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

//重置查询条件
function resetForm(){
	form.clear($("#id-mineSearchForm"));
}

//修改订阅信息
function updateOrderInter(pubid, num){
	if(num==1){
		num="00"+num;
	}
	console.log(num);
	$.ajax({
		"url" : webpath + "/publisher/update",
		"type" : "POST",
		dataType : "json",
		data:{
			"pubid" : pubid,
			"checkstatus" : num,
		},
		success:function(data){
			reloadTableData();
		},
	});
}



var resourceTree ={
		treeObj:null,
		setting : {
			data:{
				simpleData: {
					enable:true, 
					idKey:'resourcesId',
					pIdKey:'parentId'
				},
				key:{
					name:'resourcesName',
					url:'xurl'
				}
			},
			check: {
				enable: true,
				chkboxType: {"Y":"p", "N":"p"}
			}
		},
		init:function(){
			if(resourceTree.treeObj!=null){
				resourceTree.treeObj.destroy()
			}
			$.ajax({//初始化组织机构树
				"url":webpath+"/role/resources",
				"type":"POST",
				dataType:"json",
				success:function(data){
					if(data!=null&&data.length>0){
						for(var i=0;i<data.length;i++){
							data[i].icon=webpath+resourceTypeIcon[data[i].resourcesTypeId];
						}
						resourceTree.treeObj = $.fn.zTree.init($("#resourceTree"), resourceTree.setting, data);
						resourceTree.treeObj.expandAll(true);
					}else{
						layer.msg('暂无数据', {time: 1000, icon:5});
					}
				}
			});
		}
}


var resourceTypeIcon = {
		"0":"/resources/img/icon/16x16/floder1-black.png",
		"1":"/resources/img/icon/16x16/link1-black.png",
		"2":"/resources/img/icon/16x16/link-black.png",
		"3":"/resources/img/icon/16x16/fun-black.png",
};
