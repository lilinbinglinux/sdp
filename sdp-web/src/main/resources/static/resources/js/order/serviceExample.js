$(document).ready(function () {
	$.ajax({
		url: curContext + "/api/productNode/nodesByCase",
		type: "POST",
		contentType : "application/json",
		data: JSON.stringify({
			"page": {
				"pageNo": 1,
				"pageSize": 1
			},
			"productCase": {
				"caseId": curCaseId
			}
		}),
		success: function (result) {
			var column = [];
			var nodesList = result.nodesInfo.list[0] ? result.nodesInfo.list[0].caseAttrOrm : null;
			if( nodesList && nodesList.length > 0 ){
				for( var i = 0; i < nodesList.length; i++ ){
					column.push({
						field: 'caseAttrOrm['+i+'][proValue]',
						title: nodesList[i].proName,
						align: 'center',
						valign: 'middle'
					});
				}
				column.push({
					field: 'createDate',
					title: '创建时间',
					align: 'center',
					valign: 'middle'
				});
				column.push({
					field: 'caseStatus',
					title: '服务状态',
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
				});
			}
			loadNodes(column);
		}
	});

	function loadNodes( columns ) {
		$("#example-table").bootstrapTable({
			url: curContext + "/api/productNode/nodesByCase",
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
					"productCase": {
						"caseId": curCaseId
					}
				})
			},
			queryParamsType:'limit',
			responseHandler : function (res) {
				$("#exampleName").text(res.caseInfo.productName);
				var nodeList = res.nodesInfo.list;
				for( var i = 0; i < nodeList.length; i++ ){
					var caseAttrOrm = nodeList[i].caseAttrOrm;
					if( caseAttrOrm && caseAttrOrm.length > 0 ){
						for( var j = 0; j < caseAttrOrm.length; j++ ){
							nodeList[i]['caseAttrOrm['+j+'][proValue]'] = caseAttrOrm[j].proValue
						}
					}
				}
				console.log(nodeList);
				return {
					"total":nodeList.length,
					"rows":nodeList
				}
			},
			// toolbar: $("#toolbar"),
			sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
			minimumCountColumns: 2, // 设置最少显示列个数
			clickToSelect: true, // 单击行即可以选中
			uniqueId: "orderId", //每一行的唯一标识，一般为主键列
			// sortName: 'id', // 设置默认排序为 name
			// sortOrder: 'desc', // 设置排序为反序 desc
			columns: columns
		});
	}

});