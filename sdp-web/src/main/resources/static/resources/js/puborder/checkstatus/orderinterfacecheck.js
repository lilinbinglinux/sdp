var manager, tableData;
$(document).ready(function(){
	initOrderTable();
	$("#id-pubinterface-li").bind("click",function(){
		window.parent.pubinterfacecheck();
	});
	$("#id-orderinterface-li").bind("click",function(){
		window.parent.orderinterfacecheck();
	});
	
	// initOrderTable();
	$("#id-searchBtn").bind("click",reloadTableData);
	$("#id-resetBtn").bind("click",resetForm);
	// resourceTree.init();
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
        "columns":[
            { "data" : "orderId" },
            { "data" : "orderName" },
            { "data" : "appId" },
            { "data" : "loginId" },
            { "data" : "createDateString" },
            { "data" : "checkStatus" },
            { "data" : "" }
        ],
         ajax: {
             url : webpath+'/checkstatus/selectInterfaceOrder',
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
                 "visible": false
             },
             {
                 "targets" : 1,//订阅名称
                 "data" : null
             },
             {
                 "targets" : 2,//url
                 "data" : null
             },
             {
                 "targets" : 3,//用户
                 "data" : null
             },
             {
                 "targets" : 4,//订阅时间
                 "data" : null,
                 "render" : function(data, type, row) {
                     var html = '';
                     if(data==null||data==""){
                         html += '暂无时间';
                     }else{
                         html += data;
                     }
                     return html;
                 }
             },
             {
                 "targets" : 5,//当前审核状态
                 "data" : null,
                 "render" : function(data, type, row){
                     var html = "";
                     if(data == "0"){
                         html = "<span style='color: #9c9c00'>待审批</span>";
                     }else if(data == "3"){
                         html = "<span style='color: #b4b400'>审批中</span>";
                     }else if(data == "1"){
                         html = "<span style='color: green'>审批通过</span>";
                     }else if(data == "2"){
                         html = "<span style='color: red'>审批不通过</span>";
                     }
                     return html;
                 }
             },
             {
                 "targets" : 6,//操作按钮目标列
                 "data" : null,
                 "render" : function(data, type,row) {
                     var id = row.orderId;
                     var html =  
//                    	 '<a href="javascript:void(0);" onclick="updateOrderInter(\''+id+'\',\''+3+'\')" class="icon-wrap" title="正在审批"><i class="iconfont i-btn">&#xe632;</i></a>';
//                     html += '&nbsp;&nbsp;';
//                     html +=  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+id+'\',\''+1+'\')" class="icon-wrap" title="审批通过"><i class="iconfont i-btn">&#xe8c1;</i></a>';
//                     html += '&nbsp;&nbsp;';
//                     html +=  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+id+'\',\''+2+'\')" class="icon-wrap" title="不通过"><i class="iconfont i-btn">&#xe67c;</i></a>';
//                     html += '&nbsp;&nbsp;';
//                     html +=  
                    	 '<a href="javascript:void(0);" onclick="displayOrderInformation(\''+id+'\')" class="icon-wrap" title="接口详情"><i class="iconfont i-btn">&#xe656;</i></a>';
                     return html;
                 }
             }
         ]
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
function updateOrderInter(id, num){
	$.ajax({
		"url" : webpath + "/checkstatus/updatecheckstatus",
		"type" : "POST",
		dataType : "json",
		data:{
			"orderid" : id,
			"checkstatus" : num,
		},
		success:function(data){
            layer.msg('修改成功', {time: 1000, icon:1});
            reloadTableData("false");
		}
	});
}

//展示接口详细信息
function displayOrderInformation(id) {
    $.ajax({
        url: webpath + "/checkstatus/selectGetSerNameByOrderId",
        type: "POST",
        async: false,
        data: {
            orderId : id,
        },
        success: function (data) {
            $("#id-orderName-span").html(data.orderName);
            $("#id-orderName-span").html(data.orderName);
            $("#id-orderSerName-span").html(data.orderSerName);
            $("#id-orderCode-span").html(data.orderCode);
            $("#id-appId-span").html(data.appId);
            $("#id-loginId-span").html(data.loginId);

            //代订阅
            if(data.repFlag == "0"){
                $("#id-serType-span").html("否");
            }else {
                $("#id-serType-span").html("是");
            }
            $("#id-repFlag-span").html(data.repFlag);
            $("#id-repUserId-span").html(data.repUserId);
            $("#id-tenantId-span").html(data.tenantId);
            $("#id-createDateString-span").html(data.createDateString);
            if(data.appResume == null || data.appResume == ""){
                $("#id-serType-span").html("同步服务");
            }else {
                $("#id-serType-span").html("异步服务");
            }

            layer.open({
                type: 1,
                title:'<i class="iconfont">&#xe65c;</i>&nbsp;订阅详细信息',
                area: ['400px', '400px'],
                content: $("#id-orderInformation-div"),
                btn: ['审批通过','审批驳回','审批中'],
                btn1: function(index, layero){//确定按钮回调
                    updateOrderInter(data.orderId, 1);
                    layer.close(index);
                },
                btn2: function(index, layero){//确定按钮回调
                    updateOrderInter(data.orderId, 2);
                    layer.close(index);
                },
                btn3: function(index, layero){//确定按钮回调
                    updateOrderInter(data.orderId, 3);
                    layer.close(index);
                }
            });
        }
    });
}
