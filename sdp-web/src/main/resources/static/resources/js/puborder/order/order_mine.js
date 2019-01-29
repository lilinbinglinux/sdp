var type;
$(document).ready(function(){
	initOrderTable();
	
	$("#id-searchBtn").bind("click",reloadTableData);
	$("#id-resetBtn").bind("click",resetForm);
	resourceTree.init();
	
	// 为datatable外的父级设置高度
	$('#id-orderMineTable_wrapper').css('height', $('.panel-body').height()-80);
	// 动态为表格添加父级
	$('#id-orderMineTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#id-orderMineTable_wrapper').height()-80);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
	
});

//使用datatable初始化用户表格
function initOrderTable(){
	$("#id-orderMineTable").width("100%").dataTable({
		"columns":[
		           { "data" : "orderName" },
		           { "data" : "orderSerName" },
		           { "data" : "orderCode" },
		           { "data" : "appId" },
		           { "data" : "appResume" },
		           { "data" : "createDateString" },
		           { "data" : "checkStatus" },
		           { "data" : "" },
		 ],
		 ajax: {
			 url : webpath+'/orderInterface/selectMine',
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
				"targets" : 0,//订阅名称
				"data" : null,
				"visible": false,
			},
			{
				 "targets" : 1,//订阅服务
				 "data" : null,
			},
			{
				"targets" : 2,//编码格式
				"data" : null,
			},
			{
				"targets" : 3,//请求格式
				"data" : null,
			},
			{
				"targets" : 4,//说明
				"data" : null,
                "render" : function(data, type, row) {
                    var appResume = row.appResume, html = '';
                    if(appResume==null||appResume==""){
                        html += '同步订阅';
                    }else{
                        html += '异步订阅';
                    }
                    return html;
                }
			},
			{
				"targets" : 5,//订阅时间
				"data" : null,
				"render" : function(data, type, row) {
					 var html = '',date = row.createDateString;
					 if(data==null||data==""){
						 html += '暂无时间';
					 }else{
						 html += date;
					 }
				     return html;
				}
			},
			{
				"targets" : 6,//当前审核状态
				"data" : null,
				"render" : function(data, type, row){
					var html = "";
					if(data == "0"){
						html = "待审核";
					}else if(data == "3"){
						html = "审批中";
					}else if(data == "1"){
						html = "审批通过";
					}else if(data == "2"){
						html = "审批不通过";
					}
					return html;
				}
			},
			{
				"targets" : 7,//操作按钮目标列
				"data" : null,
				"render" : function(data, type,row) {
					var id = row.orderId, appResume = row.appResume, serId = row.serId, serVersion = row.serVersion, appId = row.appId, tenantId=row.tenantId,url = row.url;
                    var html = '',status = '';
                    if(appResume != null&&appResume!=""){
                        html +=  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+id+'\',\''+serId+'\',\''+row.applicationId+'\')" class="icon-wrap" title="修改信息"><i class="iconfont i-btn">&#xe66f;</i></a>';
                        html += '&nbsp;&nbsp;';
                        status = '1';
                    }else{
                        html +=  '<a href="javascript:void(0);" onclick=paramTips("'+id+'","'+serId+'","'+serVersion+'","'+appId+'","'+tenantId+'","'+url+'") class="icon-wrap" title="入参提示"><i class="iconfont i-btn">&#xe789;</i></a>';
                        html += '&nbsp;&nbsp;';
                        html +=  '<a href="javascript:void(0);" onclick=updateOrderInterSynch("'+serId+'","'+row.loginId+'","'+id+'") class="icon-wrap" title="修改信息"><i class="iconfont i-btn">&#xe66f;</i></a>';
                        html += '&nbsp;&nbsp;';
                        status = '0';
					}
					html +=  '<a href="javascript:void(0);" onclick="deleteOrderInter(\''+id+'\')" class="icon-wrap" title="取消订阅"><i class="iconfont i-btn">&#xe614;</i></a>';
					html += '&nbsp;&nbsp;';
				  	// html += '&nbsp;&nbsp;';
				  	// html +=  '<a href="javascript:void(0);" onclick="getInterface(\''+id+'\')" class="icon-wrap" title="接口信息"><i class="iconfont i-btn">&#xe634;</i></a>';
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
function updateOrderInter(id, serId, applicationId){
    var url = webpath + "/orderInterface/displayAsynchService?orderId=" + id + "&serId=" + serId + "&applicationId=" + applicationId;
    window.location.href = url;
}

//取消订阅
function deleteOrderInter(id){
	layer.confirm('是否取消订阅？（取消后可从列表重新订阅）', {
		icon: 3,
        btn: ['是','否'] //按钮
	}, function(index, layero){
		$.ajax({
			url : webpath+"/orderInterface/deleteOrderInterface",
			type: 'POST',
			dataType: "json",
			data:{
				orderId:id
			},
			success:function(data){
				$.ajax({
					url: webpath + "/whiteList/delete",
					type: "POST",
					data:{
                        orderId:id
					}
				});
				layer.close(index);
				reloadTableData(true);
			}
		})
	});
}

//入参提示
function paramTips(id,serId, serVersion, appId, tenantId,url){

	console.log(id);
    layer.open({
        type: 1,
        title: '示例参数',
        area: ['620px', '500px'],
        content: $("#interfaceinfodiv"),
        shade: 0.1,
        success: function (layero, index) {
			// var urlData = getUrl(1000);
			// var urlSync = urlData + '?appId=' + appId + '&tenantId='　+ tenantId;
			$("#urlreqparam-url").html(url);
            var data = getParam(id,serId, serVersion, 0);
            $("#urlreqparam-code").html(data);
            var datas = getParam(id,serId, serVersion, 1);
            $("#urlresponseparam-code").html(datas);
        }
    });
	//location.href = webpath+'/interfaceOrder/indexMineOrderParam';
}

//修改 我的订阅 同步服务
function updateOrderInterSynch(serId, loginId, orderId) {
    var url = webpath + "/apiStore/serviceDes?serId="+serId+"&loginId="+loginId+"&orderId="+orderId;
    window.location.href = url;
}

function getParam(id, serId, serVersion, num){
	var datas;
    $.ajax({
        url : webpath+'/orderInterface/indexMineOrderParam',
        type: 'POST',
        async: false,
        data:{
            "id":id,
            "serId":serId,
            "serVersion":serVersion,
			"num":num
        },
        success:function(data){
        	datas = data;
        }
    });
	return datas;
}
function getUrl(key){
    var datas;
    $.ajax({
        url : webpath+'/orderInterface/getUrl',
        type: 'POST',
        async: false,
        data:{
			"key":key
        },
        success:function(data){
            datas = data;
        }
    });
    return datas;
}
//获取接口信息
function getInterface(id){
	$.ajax({
		url : webpath + "/interfaceOrder/selectPubApiId",
		type : "POST",
		data:{
			orderid:id
		},
		success : function(data){
			var pubid = data;
			$.ajax({
				url : webpath + "/publisher/getPubById",
				type : "POST",
				data : {
					id : pubid,
				},
				success : function(data){
					console.log(data);
					layer.open({
						type: 1,
				        title:'<i class="iconfont">&#xe633;</i>&nbsp;查看接口信息',
				        area: ['300px', '200px'],
				        content: "<p>订阅名称:"+ data.name + "</p>" + 
				        		 "<p>订阅服务接口:"+ data.url + "</p>" +
				        		 "<p>请求方式:"+ data.method + "</p>" +
				        		 "<p>响应格式:"+ data.respformat + "</p>" +
				        		 "<p>描述:"+ data.pubdesc + "</p>" ,
				        btn: ['关闭'],
				        btn1: function(index, layero){//确定按钮回调
				        	layer.close(index);
					    }
				    });
				}
			});
		}
	})
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
