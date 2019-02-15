window.operateEvents = {
	'click .svcDetail': function (e, value, row, index) {
		$("#detailModal").find(".modal-body").empty();
		$("#detailModal").find(".modal-body").append('<div class="loading-img"><img src="'+curContext+'/resources/img/dataModel/loading.gif"></div>');
		$("#detailModal").modal("show");
		$.ajax({
			url: curContext + "/api/productCase/singleCase/" + row.caseId,
			type: "GET",
			success: function(result){
				var caseAttrOrm = result.caseAttrOrm;

				if( caseAttrOrm && caseAttrOrm.length > 0 ){
					$("#detailModal").find(".modal-body").empty();
					for( var i = 0; i < caseAttrOrm.length; i++ ){
						$("#detailModal").find(".modal-body").append(
							'<div class="row list-row">\n' +
							'            <div class="col-md-4">\n' +
							'              <span class="binary-text binary-label">'+caseAttrOrm[i].proName+'：</span>\n' +
							'            </div>\n' +
							'            <div class="col-md-8">\n' +
							'              <span class="binary-text">'+( (caseAttrOrm[i].proValue == "null" || caseAttrOrm[i].proValue == null ) ? "没有数据" : caseAttrOrm[i].proValue )+ ( ( caseAttrOrm[i].proUnit == "null" || caseAttrOrm[i].proUnit == null ) ? "" :  caseAttrOrm[i].proUnit  ) + '</span>\n' +
							'            </div>\n' +
							'</div>'
						);
					}
				}
			}
		});
	},
	'click .productName': function (e, value, row, index) {
		window.location.href = "http://bconsole.sdp.com/paas/component/"+row.productId+"/node/?serviceId=" + row.caseId + "&menu=false";
	},
	'click .svcStart': function (e, value, row, index) {
		$(e.target).parent().prev().empty();
		$(e.target).parent().prev().append("<img src='"+curContext+"/resources/img/service/loading.gif'>启动中");
		$(e.target).next().attr("disabled","disabled");
		$(e.target).attr("disabled","disabled");
		$.ajax({
			url: curContext + "/api/productCase/updateCase",
			contentType : "application/json",
			type: "POST",
			data: JSON.stringify({
				"caseId": row.caseId,
				"caseStatus" : "20"
			}),
			success: function (result) {
				// console.log(result);
			}
		});
	},
	'click .svcStop': function (e, value, row, index) {
		$(e.target).parent().prev().empty();
		$(e.target).parent().prev().append("<img src='"+curContext+"/resources/img/service/loading.gif'>停止中");
		$(e.target).prev().attr("disabled","disabled");
		$(e.target).attr("disabled","disabled");
		$.ajax({
			url: curContext + "/api/productCase/updateCase",
			contentType : "application/json",
			type: "POST",
			data: JSON.stringify({
				"caseId": row.caseId,
				"caseStatus" : "30"
			}),
			success: function (result) {
				// console.log(result);
			}
		});
	},
	'click .svcDelete': function (e, value, row, index) {
		$("#deleteModal").find(".modal-body").text("确认删除此服务吗？");
		$("#delete-caseId").val( row.caseId );
		$(".delete-btn-group").css("display","block");
		$(".back-btn").css("display","none");
		$(".back-btn").html('<img src="'+curContext+'/resources/img/dataModel/loading.gif" style="height: 20px;">');
		$("#deleteModal").modal("show");
	}
};

$(document).ready(function () {
	var serviceInterval;
	function loadServiceTree() {

		//获取数据
		$.ajax({
			url: curContext + "/api/product/productsByCase",
			type: "POST",
			contentType : "application/json",
			data: JSON.stringify({
				"product":{},
				"dataAuth":"10"
			}),
			success: function (result) {
				var proTypes = result.proTypes;
				var products = result.products;
				var treeArray = [];

				if( proTypes ){
					for( var i = 0; i < proTypes.length; i++ ){
						if( proTypes[i].parentId ){
							treeArray.push({
								"id" : proTypes[i].productTypeId,
								"name" : proTypes[i].productTypeName,
								"pId": proTypes[i].parentId ,
								"sortId" : proTypes[i].sortId,
								"iconSkin" : "productType",
								"type": "productType"
							})
						} else {
							treeArray.push({
								"id" : proTypes[i].productTypeId,
								"name" : proTypes[i].productTypeName,
								"pId": "" ,
								"sortId" : proTypes[i].sortId,
								"iconSkin" : "productType",
								"type": "productType"
							})
						}
					}
				}

				if( products ){
					for( var j = 0; j < products.length; j++ ) {
						treeArray.push({
							"id" : products[j].productId,
							"name" : products[j].productName,
							"pId": products[j].productTypeId,
							"iconSkin" : "product",
							"type": "product"
						});
					}
				}

				var setting = {
					view: {
						showLine: false,
						// showIcon: false,
						selectedMulti: false,
						dblClickExpand: false,
						addDiyDom: addDiyDom
					},
					data: {
						simpleData: {
							enable: true,
							rootPId: ""
						}
					},
					callback: {
						onClick: zTreeOnClick
					}
				};

				function addDiyDom(treeId, treeNode) {
					var spaceWidth = 5;
					var switchObj = $("#" + treeNode.tId + "_switch"),
						icoObj = $("#" + treeNode.tId + "_ico");
					switchObj.remove();
					icoObj.before(switchObj);

					if (treeNode.level > 1) {
						var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
						switchObj.before(spaceStr);
					}
				}

				function zTreeOnClick(event, treeId, treeNode) {
					if( treeNode.type == "product" ){
						$("#productId").val(treeNode.id);
						$("#service-name").text(treeNode.name);
						$(".table-responsive").empty();
						$(".table-responsive").append(
							'<table class="table table-hover" id="service-table"></table>'
						);
						// console.log(treeNode.id, 'treeNode.id');
						loadServiceTable(treeNode.id);
					} else if( treeNode.type == "productType" ) {
						var zTree = $.fn.zTree.getZTreeObj("serviceTree");
						zTree.expandNode(treeNode);
					}
				}

				var zTreeObj = $.fn.zTree.init($("#serviceTree"), setting, treeArray);
				zTree = $.fn.zTree.getZTreeObj("serviceTree");
			}
		});
	}

	loadServiceTree();

	function operateFormatter(value,row,index){
		var status = row.caseStatus;
		if( status == "10" || status == "30" ){
			// 未启动 停止
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail">详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart">启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop" disabled>停止</button>',
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>'
			].join("");
		} else if( status == "20" ){
			// 运行
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail">详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart" disabled>启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop">停止</button>' ,
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>' ].join("") ;
		} else if( status == "1010" || status == "3010" ){
			// 启动中
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail">详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart" disabled>启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop" disabled>停止</button>' ,
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>'].join("") ;
		} else if( status == "3010" ){
			// 停止中
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail">详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart" disabled>启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop" disabled>停止</button>' ,
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>' ].join("")  ;
		} else if( status == "40" ){
			// 失败
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail">详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart">启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop" disabled>停止</button>' ,
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>' ].join("") ;
		} else if( status == "50" ){
			// 异常
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail">详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart" disabled>启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop" disabled>停止</button>' ,
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>'].join("")  ;
		} else if( status == "60" ){
			// 未创建
			return [
				'<button title="详情" class="btn btn-default btn-xs svcDetail" disabled>详情</button>' ,
				'<button title="启动" class="btn btn-default btn-xs svcStart" disabled>启动</button>' ,
				'<button title="停止" class="btn btn-default btn-xs svcStop" disabled>停止</button>' ,
				'<button title="删除" class="btn btn-default btn-xs svcDelete">删除</button>' ].join("")  ;
		}
	}

	function loadServiceTable(curProductId) {

		$("#service-table").bootstrapTable({
			url: curContext + "/api/productCase/caseInfoByProduct",
			method: "POST",
			contentType : "application/json",
			classes: 'table-no-bordered',
			// data:tableData,
			dataType: "json",
			// data:json.data.dataList,
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
				var page = {
					"pageNo": (params.offset / params.limit) + 1,
					"pageSize": params.limit
				};
				return JSON.stringify({
					"page": page,
					"productId": curProductId,
					"dataAuth": 10
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
				formatter: function (value,row,index) {
					//return index+1; //序号正序排序从1开始
					var pageSize=$('#service-table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#service-table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
				}
			},{
				field: 'applyName',
				title: '服务名称',
				align: 'center',
				valign: 'middle',
				events: operateEvents,
				formatter: function (value,row,index) {
					if( row.applyName ){
						return '<a class="productName">'+row.applyName+'</a>';
					} else {
						return '<a class="productName">-</a>';
					}
				}
			},{
				field: 'createDate',
				title: '创建时间',
				align: 'center',
				valign: 'middle'
			},{
				field: 'createBy',
				title: '创建者',
				align: 'center',
				valign: 'middle'
			},{
				field: 'caseStatus',
				title: '运行状态',
				align: 'center',
				valign: 'middle',
				formatter:function(value,row,index){
					switch (value) {
						case "10":
							return "未启动";
							break;
						case "1010":
							return"<img src='"+curContext+"/resources/img/service/loading.gif'>启动中";
							break;
						case "20":
							return "运行";
							break;
						case "30":
							return "停止";
							break;
						case "3010":
							return "<img src='"+curContext+"/resources/img/service/loading.gif'>停止中";
							break;
						case "40":
							return "失败";
							break;
						case "50":
							return "异常";
							break;
						case "60":
							return "未创建";
							break;
						default :
							return "-";
							break;
					}
				}
			},{
				field: '',
				title: '操作',
				align: 'center',
				width: '200px',
				events: operateEvents,
				formatter: operateFormatter
			}]
		});
	}

	$(".nav-collapse-left").click(function (e) {
		$(".serviceList-container").addClass("left-to-0");
	});
	$(".nav-collapse-right").click(function (e) {
		$(".serviceList-container").removeClass("left-to-0");
	});
	$("#delete-sub").click(function (e) {
		var caseId = $("#delete-caseId").val();
		$(".delete-btn-group").css("display","none");
		$(".back-btn").css("display","block");
		$.ajax({
			url: curContext + "/api/productCase/delete",
			type: "DELETE",
			contentType : "application/json",
			data: JSON.stringify({"caseId": caseId}),
			success: function (result) {
				$("#deleteModal").find(".modal-body").text(result.message);
				$(".back-btn").html("返回");
				$(".table-responsive").empty();
				$(".table-responsive").append(
					'<table class="table table-hover" id="service-table"></table>'
				);
				loadServiceTable($("#productId").val());
			}
		});
	});
});