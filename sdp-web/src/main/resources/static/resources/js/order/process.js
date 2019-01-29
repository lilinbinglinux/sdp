$(document).ready(function(){

	function unProveFormatter(value,row,index) {
		return [
			'<button title="办理" class="btn btn-default banli"><i class="fa fa-paper-plane"></i></button>&nbsp;&nbsp;',
			'<button title="跟踪" class="btn btn-default genzong"><i class="fa fa-bullseye"></i></button>'
		].join("");
	}
	
	function provedFormatter(value,row,index) {
		return [
			'<button title="查看" class="btn btn-default aproveSee"><i class="fa fa-eye"></i></button>&nbsp;&nbsp;',
			'<button title="跟踪" class="btn btn-default genzong"><i class="fa fa-bullseye"></i></button>',
		].join("");
	}

	window.unProveEvents = {
		'click .banli': function (e, value, row, index) {
			window.location.href = curContext + "/v1/pro/pbmOrderProcess/approve/" + row.orderId + "/" + row.taskId;
		},
		'click .aproveSee': function (e, value, row, index) {
			window.location.href = curContext + "/v1/pro/pbmOrderProcess/approved/" + row.orderId;
		},
		'click .genzong': function (e, value, row, index) {
			window.open(row.monitorProcessUrl,'target','');
		}
	};

	//未审批
	function loadUnProve() {
		$.ajax({
			url: curContext + "/api/bpmOrderProcess/pendingTask",
			method: "POST",
			contentType : "application/json",
			data: JSON.stringify({
				"page": {
					"pageNo": 0,
					"pageSize": 10000
				},
				"bpmOrderProcess":{},
				"dataAuth": "00"
			}),
			success:function(result){
				$("#unProve").bootstrapTable({
					data:result.list,
					dataType: "json",
					// data:json.data.dataList,
					classes: 'table-no-bordered',
					cache: false, // 不缓存
					// striped: true, // 隔行加亮
					pagination: true, // 开启分页功能
					search: false, // 开启搜索功能
					showColumns: false, // 开启自定义列显示功能
					showRefresh: false, // 开启刷新功能
					showToggle: false,
					pageNumber: 1, //初始化加载第一页，默认第一页
					pageSize: 10, //每页的记录行数（*）
					pageList: [10,25,50], //可供选择的每页的行数（*）
					sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
					minimumCountColumns: 2, // 设置最少显示列个数
					clickToSelect: true, // 单击行即可以选中
					uniqueId: "procId", //每一行的唯一标识，一般为主键列
					columns: [{
						field: 'orderId',
						title: '序号',
						align: 'center',
						valign: 'middle',
						width: '50px',
						sortable: true, // 开启排序功能
						formatter: function (value,row,index) {
							return index + 1;
						}
					},{
						field: 'orderId',
						title: '订单编号',
						align: 'center',
						valign: 'middle'
					},{
						field: 'applyName',
						title: '服务名称',
						align: 'center',
						valign: 'middle'
					},{
						field: 'applyUserName',
						title: '订单申请人',
						align: 'center',
						valign: 'middle'
					},{
						field: 'orderStartDate',
						title: '订单提交时间',
						align: 'center',
						valign: 'middle'
					},{
						field: '',
						title: '操作',
						align: 'center',
						width: '150px',
						events: unProveEvents,
						formatter: unProveFormatter
					}]
				});
			}
		});
	}

	//已审批
	function loadProved() {
		var orderId = $("#orderId").val();
		var bpmOrderProcess = {};
		if( orderId && orderId != "" ){
			bpmOrderProcess.orderId = orderId;
		}
		$.ajax({
			url: curContext + "/api/bpmOrderProcess/finishTask",
			method: "POST",
			contentType : "application/json",
			data: JSON.stringify({
				"page": {
					"pageNo": 0,
					"pageSize": 10000
				},
				"bpmOrderProcess": bpmOrderProcess,
				"dataAuth": "00"
			}),
			success:function(result){
				console.log(result);
				$("#proved").bootstrapTable({
					data:result.list,
					dataType: "json",
					// data:json.data.dataList,
					classes: 'table-no-bordered',
					cache: false, // 不缓存
					striped: true, // 隔行加亮
					pagination: true, // 开启分页功能
					search: false, // 开启搜索功能
					showColumns: false, // 开启自定义列显示功能
					showRefresh: false, // 开启刷新功能
					showToggle: false,
					pageNumber: 1, //初始化加载第一页，默认第一页
					pageSize: 10, //每页的记录行数（*）
					pageList: [10,25,50], //可供选择的每页的行数（*）
					sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
					minimumCountColumns: 2, // 设置最少显示列个数
					clickToSelect: true, // 单击行即可以选中
					uniqueId: "procId", //每一行的唯一标识，一般为主键列
					columns: [{
						field: 'orderId',
						title: '序号',
						align: 'center',
						valign: 'middle',
						width: '50px',
						formatter: function (value,row,index) {
							return index + 1;
						}
					},{
						field: 'orderId',
						title: '订单编号',
						align: 'center',
						valign: 'middle',
						sortable: true // 开启排序功能
					},{
						field: 'applyName',
						title: '服务名称',
						align: 'center',
						valign: 'middle'
					},{
						field: 'applyUserName',
						title: '订单申请人',
						align: 'center',
						valign: 'middle'
					},{
						field: 'orderStartDate',
						title: '订单提交时间',
						align: 'center',
						valign: 'middle'
					},{
						field: 'approvalDate',
						title: '审批时间',
						align: 'center',
						valign: 'middle'
					},{
						field: '',
						title: '操作',
						align: 'center',
						width: '150px',
						events: unProveEvents,
						formatter: provedFormatter
					}]
				});
			}
		});
	}

	loadUnProve();

	$("#unProve-li").click(function () {
		loadUnProve();
	});

	$("#proved-li").click(function () {
		loadProved();
	});

	$("#orderSearch").click(function (e) {
		e.preventDefault();
		$("#profile").find(".bootstrap-table").remove();
		$("#profile").append(
			'<table class="table table-hover" id="proved">\n' +
			'\n' +
			'        </table>'
		);
		loadProved();
	});

	$("#orderClear").click(function (e) {
		e.preventDefault();
		$("#orderId").val("");
	});

	$('#startTime').datetimepicker();
	$('#expireTime').datetimepicker();
});