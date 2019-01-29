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
	$("#searchBtn").bind("click",reloadTableData);
	$("#resetBtn").bind("click",resetForm);

	// 为datatable外的父级设置高度
	$('#userTable_wrapper').css('height', $('.panel-body').height()-60);
	// 动态为表格添加父级
	$('#userTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#userTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
});

//初始化用户表格
function initOrderTable(){
    $("#userTable").width("100%").dataTable({
        "columns":[
            { "data": "userName" },
            { "data": "loginId" },
        ],
        ajax: {
            url:webpath+'/subscription/list',
            "type": 'POST',
            "data": function (d) {//查询参数
                return $.extend( {}, d, {
                    "jsonStr": form.serializeStr($("#userSearchForm"))
                });
            },
            "dataSrc": function (json) {
                console.log(json);
                json.iTotalRecords = json.total;
                json.iTotalDisplayRecords = json.total;
                return json.data;
            }
        },
        columnDefs:[{
            "targets" : 2,//操作按钮目标列
            "data" : null,
            "render" : function(data, type,row) {
                var id = row.loginId;
                var html =  '<a href="javascript:void(0);" onclick="subScription(\''+id+'\')" class="icon-wrap" title="代订阅"><i class="iconfont i-btn">&#xe66f;</i></a>';
                html += '&nbsp;&nbsp;';
                return html;
            }
        }]
    });
    // manager = $("#id-orderinterfaceTable").ligerGrid({
    //     columns:[
    //         { display:"用户姓名",name : "loginId" },
    //         { display:"登陆账号",name : "userName" },
    //         { display:"订阅人",name : "userName" },
    //         // { display:"代订阅人",name : "loginId" },
    //         { display:"操作",name : "",render : function(item) {
    //             var id = item.loginId;
    //             var html =  '<a href="javascript:void(0);" onclick="updateOrderInter(\''+id+'\',\''+3+'\')" class="icon-wrap" title="正在审批"><i class="iconfont i-btn">&#xe632;</i></a>';
    //             html += '&nbsp;&nbsp;';
    //             return html;
    //         } }
    //     ],
    //     width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '100%',
    //     alternatingRow: false,
    //     enabledEdit: false,
    //     rownumbers: false,
		// url:webpath+'/subscription/list',//'/interfaceOrder/selectMine',
    //     usePager: false,
    //     parms:{orgIds:""}
    // });
}


//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
    $("#userTable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

//重置查询条件
function resetForm(){
	form.clear($("#userSearchForm"));
}

//跳转服务商城
function subScription(id){
    window.location.href = webpath + '/apiStore/index?loginId='+id
}


