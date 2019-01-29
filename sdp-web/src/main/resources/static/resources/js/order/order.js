
function operateFormatter(value,row,index){
	return [
		'<button title="查看" class="btn btn-default btnsee"><i class="fa fa-eye"></i></button>'].join("");
}

window.operateEvents = {
	'click .btnsee': function (e, value, row, index) {
		var orderId = row.orderId;
		var modalBody = $("#detailModal").find(".modal-body");
		modalBody.empty();
		$("#detailModal").modal("show");
		$.ajax({
			url: curContext + "/v1/pro/productOrder/singleOrder/" + orderId,
			type: "GET",
			contentType : "application/json",
			success:function(result){
				var html =
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">订单编号</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9">\n' +
					'              <span class="binary-text">'+result.orderId+'</span>\n' +
					'            </div>\n' +
					'</div>' +
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">订单申请人</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9">\n' +
					'              <span class="binary-text">'+result.createBy+'</span>\n' +
					'            </div>\n' +
					'</div>' +
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">订单提交时间</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9">\n' +
					'              <span class="binary-text">'+result.createDate+'</span>\n' +
					'            </div>\n' +
					'</div>' +
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">服务名称</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9">\n' +
					'              <span class="binary-text">'+result.productName+'</span>\n' +
					'            </div>\n' +
					'</div>' +
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">基本配置</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9 binary-card-blue">\n' ;

				//遍历基本配置
				var orderBasicAttrOrm = result.orderBasicAttrOrm;
				if( orderBasicAttrOrm && orderBasicAttrOrm.length > 0 ){
					for( var q = 0; q < orderBasicAttrOrm.length; q++ ){
						html +=
							'<div class="row list-row">\n' +
							'            <div class="col-md-3">\n' +
							'              <span class="binary-text binary-label">'+orderBasicAttrOrm[q].proCode+'</span>\n' +
							'            </div>\n' +
							'            <div class="col-md-9">\n' +
							'              <span class="binary-text">'+( orderBasicAttrOrm[q].proValue ? orderBasicAttrOrm[q].proValue : "-" )+'</span>\n' +
							'            </div>\n' +
							'</div>' ;
					}
				}

				html += '            </div>\n' +
					'</div>' ;

				html +=
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">购买量</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9 binary-card">\n' ;

				//遍历资源属性
				var orderControlResAttrOrm = result.orderControlResAttrOrm;
				if( orderControlResAttrOrm && orderControlResAttrOrm.length > 0 ){
					for( var i = 0; i < orderControlResAttrOrm.length; i++ ){
						html +=
							'<div class="row list-row">\n' +
							'            <div class="col-md-3">\n' +
							'              <span class="binary-text binary-label">'+orderControlResAttrOrm[i].proCode+'</span>\n' +
							'            </div>\n' +
							'            <div class="col-md-9">\n' +
							'              <span class="binary-text">'+(orderControlResAttrOrm[i].proValue ? orderControlResAttrOrm[i].proValue : "-")+'</span>\n' +
							'            </div>\n' +
							'</div>' ;
					}
				}

				html +=	'            </div>\n' +
					'</div>' ;

				html +=
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">计费方式</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9">\n' +
					'              <span class="binary-text">'+result.pOrderWays.pwaysName+'</span>\n' +
					'            </div>\n' +
					'</div>' ;
				if( result.pOrderWays.pwaysType !== "10" ){
					html +=
						'<div class="row list-row">\n' +
						'            <div class="col-md-3">\n' +
						'              <span class="binary-text binary-label">总费用</span>\n' +
						'            </div>\n' +
						'            <div class="col-md-9">\n' +
						'              <span class="binary-text">'+result.price+'</span>\n' +
						'            </div>\n' +
						'</div>' ;
				}

				var orderStatus = "";
				switch (result.orderStatus) {
					case 10:
						orderStatus = "待支付";
						break;
					case 20:
						orderStatus = "支付成功";
						break;
					case 30:
						orderStatus = "待审批";
						break;
					case 40:
						orderStatus = "审批中";
						break;
					case 50:
						orderStatus = "通过";
						break;
					case 60:
						orderStatus = "驳回";
						break;
					default :
						orderStatus = "-";
						break;
				}
				html +=
					'<div class="row list-row">\n' +
					'            <div class="col-md-3">\n' +
					'              <span class="binary-text binary-label">订单状态</span>\n' +
					'            </div>\n' +
					'            <div class="col-md-9">\n' +
					'              <span class="binary-text">'+orderStatus+'</span>\n' +
					'            </div>\n' +
					'</div>' ;
				modalBody.append(html);
			}
		});
		return false;
	}
};

$(document).ready(function(){
	function loadOrderTable() {
		$("#order-table").bootstrapTable({
			url: curContext + "/api/productOrder/allOrders",
			method: "POST",
			contentType : "application/json",
			// data:tableData,
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
			pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
			queryParams: function (params) {
				var orderStatus = parseInt($("#orderStatus").val());
				var pwaysId = $("#pwaysId").val();
				var startTime = $("#startTime").val();
				var expireTime = $("#expireTime").val();
				var productOrder = {};
				var page = {
					"pageNo": (params.offset / params.limit) + 1,
					"pageSize": params.limit
				};
				// if( orderStatus && orderStatus != "" ){
					productOrder.orderStatus = orderStatus;
				// }
				// if( pwaysId && pwaysId != "" ){
					productOrder.pwaysId = pwaysId;
				// }
				// if( startTime && startTime != "" ){
					productOrder.queryStartTime = startTime;

				// }
				// if( expireTime && expireTime != "" ){
					productOrder.queryEndTime = expireTime;
				// }
				return JSON.stringify({
					"page": page,
					"productOrder": productOrder,
					"dataAuth": "00"
				})
			},
			queryParamsType:'limit',
			responseHandler : function (res) {
				return {
					"total":res.totalCount,
					"rows":res.list
				}
			},
			// toolbar: $("#toolbar"),
			sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
			minimumCountColumns: 2, // 设置最少显示列个数
			clickToSelect: true, // 单击行即可以选中
			uniqueId: "orderId", //每一行的唯一标识，一般为主键列
			// sortName: 'id', // 设置默认排序为 name
			// sortOrder: 'desc', // 设置排序为反序 desc
			columns: [{
				field: 'orderId',
				title: '序号',
				align: 'center',
				valign: 'middle',
				width: '50px',
				sortable: true, // 开启排序功能
				formatter: function (value,row,index) {
					//return index+1; //序号正序排序从1开始
					var pageSize=$('#order-table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#order-table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
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
				field: 'orderStatus',
				title: '订单状态',
				align: 'center',
				valign: 'middle',
				formatter:function(value,row,index){
					switch (value) {
						case 10:
							return "待支付";
							break;
						case 20:
							return "支付成功";
							break;
						case 30:
							return "待审批";
							break;
						case 40:
							return "审批中";
							break;
						case 50:
							return "通过";
							break;
						case 60:
							return "驳回";
							break;
						default :
							return "-";
							break;
					}
				}
			},{
				field: 'pOrderWays',
				title: '计费方式',
				align: 'center',
				valign: 'middle',
				formatter:function(value,row,index){
					return value ? value.pwaysName : '-'
				}
			},{
				field: 'createBy',
				title: '订单申请人',
				align: 'center',
				valign: 'middle'
			},{
				field: 'createDate',
				title: '提交时间',
				align: 'center',
				valign: 'middle'
			},{
				field: '',
				title: '操作',
				align: 'center',
				width: '120px',
				events: operateEvents,
				formatter: operateFormatter
			}]
		});
	}

	loadOrderTable();

	$('#startTime').datetimepicker({
		language: 'zh-CN',
		autoclose: true,
		todayHighlight: true,
		minView: "month",
		format: 'yyyy-mm-dd'
	});
	$('#expireTime').datetimepicker({
		language: 'zh-CN',
		autoclose: true,
		todayHighlight: true,
		minView: "month",
		format: 'yyyy-mm-dd'
	});

	$("#orderSearch").click(function (e) {
		var e = e || window.event;
		e.preventDefault();
		$(".table-responsive").empty();
		$(".table-responsive").append(
			'<table class="table table-hover" id="order-table">\n' +
			'\n' +
			'      </table>'
		);
		loadOrderTable();
	});

	$("#orderClear").click(function (e) {
		var e = e || window.event;
		e.preventDefault();
		$("#orderStatus").val("");
		$("#pwaysId").val("");
		$("#startTime").val("");
		$("#expireTime").val("");
	});

	//服务注册
	$("#regProductPage-btn").click(function(){
		
	});

	//获取订购方式
	$.ajax({
		url : curContext + "/api/porderWays/allPorderWays",
		type: "POST",
		contentType : "application/json",
		data: JSON.stringify({

		}),
		success: function( result ){
			if( result && result.length > 0 ){
				$("#pwaysId").empty();
				$("#pwaysId").append('<option value="">请选择</option>');
				for( var i = 0; i < result.length; i++ ){
					$("#pwaysId").append('<option value="'+ result[i].pwaysId +'">'+result[i].pwaysName+'</option>');
				}
			}
		}
	});
});
