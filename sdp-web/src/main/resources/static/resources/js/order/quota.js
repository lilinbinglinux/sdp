$(document).ready(function () {
	var sourceAttrs = [];
	var allPorderWays = [];

	window.operateEvents = {
		'click .tenantName': function (e, value, row, index) {
			$(".quota-table").css("display","none");
			$(".quota-detail-table").css("display","block");
			$(".detail-table-container").empty();
			$(".detail-table-container").append(
				'<table class="table table-hover" id="quota-detail-table"></table>'
			);
			loadquotaDetailTable(row.tenantId);
		},
		'click .quotaDetail': function (e, value, row, index) {
			$("#detailModal").find(".modal-body").empty();
			$("#detailModal").find(".modal-body").append('<div class="loading-img"><img src="'+curContext+'/resources/img/dataModel/loading.gif"></div>');
			$("#detailModal").modal("show");
			var caseAttrOrm = row.caseAttrOrm;

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
	};

	function operateFormatter(value,row,index){
		return [
			'<button title="详情" class="btn btn-default btn-xs quotaDetail">详情</button>'
		].join("");
	}

	function loadquotaDetailTable(tenantId) {
		$("#quota-detail-table").bootstrapTable({
			url: curContext + "/resource/quota/" + tenantId,
			method: "GET",
			// contentType : "application/json",
			// data:tableData,
			// dataType: "json",
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
				return {
					"pageNo": (params.offset / params.limit) + 1 + '',
					"pageSize": params.limit + ''
				}
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
					var pageSize=$('#quota-detail-table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#quota-detail-table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
				}
			},{
				field: 'productName',
				title: '服务名称',
				align: 'center',
				valign: 'middle',
				formatter: function (value,row,index) {
					return '配额';
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
				title: '使用量',
				align: 'center',
				formatter: function (value,row,index) {
					var caseAttrOrm = row.caseAttrOrm;
					if( caseAttrOrm && caseAttrOrm.length > 0 ){
						var html = '';
						for( var i = 0; i < caseAttrOrm.length; i++ ){
							html += '<div><span class="quota-key">'+caseAttrOrm[i].proName+'</span><span class="quota-maohao">:</span><span class="quota-value">'+( (caseAttrOrm[i].proValue == "null" || caseAttrOrm[i].proValue == null ) ? "-" : caseAttrOrm[i].proValue )+'('+( ( caseAttrOrm[i].proUnit == "null" || caseAttrOrm[i].proUnit == null ) ? "" :  caseAttrOrm[i].proUnit  )+')</span></div>';
						}
						return html;
					}
				}
			}]
		});
	}

	function loadquotaTable() {
		$("#quota-table").bootstrapTable({
			url: curContext + "/resource/tenants",
			method: "GET",
			// contentType : "application/json",
			// data:tableData,
			// dataType: "json",
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
				return {
					"pageNo": (params.offset / params.limit) + 1 + '',
					"pageSize": params.limit + ''
				}
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
					var pageSize=$('#quota-table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#quota-table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
				}
			},{
				field: 'tenantName',
				title: '租户名称',
				align: 'center',
				valign: 'middle',
				events: operateEvents,
				formatter: function (value,row,index) {
					return '<a class="tenantName">'+row.tenantName+'</a>';
				}
			},{
				field: 'resQuota',
				title: '资源',
				align: 'center',
				valign: 'middle',
				formatter: function (value,row,index) {
					var resQuota = row.resQuota;
					if( resQuota ){
						var html = '';
						for(var key in row.resQuota){
							var unit = "";
							if( key === "CPU" ){
								unit = "G";
							}else if( key === "内存" ){
								unit = "G";
							}else if( key === "硬盘" ){
								unit = "个"
							}
							html += '<div><span class="quota-key">'+key+'</span><span class="quota-maohao">:</span><span class="quota-value">'+resQuota[key]+'('+unit+')</span></div>';
						}
						return html;
					}
				}
			},{
				field: 'usedQuota',
				title: '使用量',
				align: 'center',
				valign: 'middle',
				formatter: function (value,row,index) {
					var usedQuota = row.usedQuota;
					if( usedQuota ){
						var html = '';
						for(var key in row.usedQuota){
							var unit = "";
							if( key === "CPU" ){
								unit = "G";
							}else if( key === "内存" ){
								unit = "G";
							}else if( key === "硬盘" ){
								unit = "个"
							}
							html += '<div><span class="quota-key">'+key+'</span><span class="quota-maohao">:</span><span class="quota-value">'+usedQuota[key]+'('+unit+')</span></div>';
						}
						return html;
					}
				}
			}]
		});
	}

	loadquotaTable();


	//配额申请相关
	function getInitJsonArray(proInitValue) {
		var initVal = eval('('+proInitValue+')');
		var initJsonArray = [];
		if( initVal && initVal.length > 0 ){
			for( var i = 0; i < initVal.length; i++ ){
				for( var key in initVal[i] ){
					initJsonArray.push({
						"text": initVal[i][key],
						"value": key
					})
				}
			}
		}
		return initJsonArray;
	}

	function getDynamicForm(attr, index) {
		var proShowType = Number(attr.proShowType);

		switch ( proShowType ) {
			case 10 :
				return '<div class="form-group" id="'+ attr.proName + index +'">\n' +
					'  <label class="col-sm-3 control-label">'+attr.proName+'</label>\n' +
					'  <div class="col-sm-9">\n' +
					'    <div class="input-group"><input type="text" class="form-control" ><span class="input-group-addon">'+ ( attr.proUnit ? attr.proUnit : '' )+'</span></div>\n' +
					'  </div>\n' +
					'</div>';
				break;
			case 20 :
				var html = '<div class="form-group" id="'+ attr.proName + index +'">\n' +
					'  <label class="col-sm-3 control-label">'+attr.proName+'</label>\n' +
					'  <div class="col-sm-9">\n' +
					'    <div class="input-group"><select class="form-control">\n' ;
				var proInitValue = attr.proInitValue;
				var initArray = getInitJsonArray(proInitValue);
				if( initArray && initArray.length > 0 ){
					for( var i = 0; i < initArray.length; i++ ){
						html += '<option value="'+initArray[i].value+'">'+initArray[i].text+'</option>';
					}
				}
				html += '    </select><span class="input-group-addon">'+ ( attr.proUnit ? attr.proUnit : '' )+'</span></div>\n' +
					'  </div>\n' +
					'</div>';

				return html;
				break;
			case 30 :
				var html = '<div class="form-group" id="'+ attr.proName + index +'">\n' +
					'  <label class="col-sm-3 control-label">'+attr.proName+'</label>\n' +
					'  <div class="col-sm-9" style="padding-right: 0;">\n' ;
				var proInitValue = attr.proInitValue;
				var initArray = getInitJsonArray(proInitValue);
				if( initArray && initArray.length > 0 ){
					for( var i = 0; i < initArray.length; i++ ){
						html += '<label class="checkbox-inline">'+
							'<input type="radio" name="'+attr.proName+'" value="'+initArray[i].value+'"> '+ initArray[i].text +
							'</label>';
					}
				}
				html +=
					'  </div>\n' + '<div class="col-sm-1">' +( attr.proUnit ? attr.proUnit : '' ) + '</div>' +
					'</div>';

				return html;
				break;
			case 40 :
				var html = '<div class="form-group" id="'+ attr.proName + index +'">\n' +
					'  <label class="col-sm-3 control-label">'+attr.proName+'</label>\n' +
					'  <div class="col-sm-9" style="padding-right: 0;">\n' ;
				var proInitValue = attr.proInitValue;
				var initArray = getInitJsonArray(proInitValue);
				if( initArray && initArray.length > 0 ){
					for( var i = 0; i < initArray.length; i++ ){
						html += '<label class="checkbox-inline">'+
							'<input type="checkbox" name="'+attr.proName+'" value="'+initArray[i].value+'"> '+ initArray[i].text +
							'</label>';
					}
				}
				html +=
					'  </div>\n' + '<div class="col-sm-1">' +( attr.proUnit ? attr.proUnit : '' ) + '</div>' +
					'</div>';

				return html;
				break;
			case 50 :
				var html = '<div class="form-group" id="'+ attr.proName + index +'">\n' +
					'  <label class="col-sm-4 control-label">'+attr.proName+'</label>\n' +
					'  <div class="col-sm-7" style="padding-right: 0;">\n' ;
				html += '<input class="form-control easyui-slider" data-options="showTip: true,rule: [0,\'|\',25,\'|\',50,\'|\',75,\'|\',100]">' ;
				html +=
					'  </div>\n' + '<div class="col-sm-1">' +( attr.proUnit ? attr.proUnit : '' ) + '</div>' +
					'</div>';

				return html;
				break;
			default :
				return '-';
		}
	}

	$("#quota-apply-btn").click(function (e) {
		$(".quota-detail-table").fadeOut();
		$(".quota-apply-container").fadeIn();
		var quataForm = $("#quata-form");
		quataForm.empty();
		sourceAttrs = [];
		allPorderWays = [];
		$.ajax({
			url: curContext + '/api/product/singleProduct/bcm',
			type: 'GET',
			contentType : "application/json",
			success: function (result) {
				var productAttrOrm = result.productAttrOrm;
				$("#orderType").val(result.orderType);
				$("#productId").val(result.productId);
				if( productAttrOrm && productAttrOrm.length > 0 ){
					//算几行
					for( var k = 0; k < productAttrOrm.length; k++ ){
						if( productAttrOrm[k].proLabel && productAttrOrm[k].proLabel.indexOf(10) !== -1 ){
							sourceAttrs.push(productAttrOrm[k]);
						}
					}
					var linesNumber = sourceAttrs.length;
					if( linesNumber > 0 ){
						for( var s = 0; s < linesNumber; s++ ){
							var html =
								'<div class="row">';
							html += '<div class="col-md-12">';
							html += getDynamicForm(sourceAttrs[s], s);
							html += '</div>';
							html += '</div>';
							quataForm.append(html);
						}
					}
				}

				//获取订购方式
				$.ajax({
					url: curContext + '/api/porderWays/allPorderWays',
					type: 'POST',
					contentType : "application/json",
					data: JSON.stringify({

					}),
					success:function (result) {
						allPorderWays = result;
						var orderType = $("#orderType").val();
						if( orderType === "10" ){
							$(".ways-content").css("display","block");
							if(result && result.length > 0){
								for( var i = 0; i < result.length; i++ ){
									if( result[i].pwaysRemark && result[i].pwaysRemark === "审批" ){
										$("#pwaysId").val(result[i].pwaysId);
									}
								}
							}
						}
					}
				});
			}
		});
	});

	$("#quota-sub").click(function (e) {
		for( var i = 0; i < sourceAttrs.length; i++ ){
			var attrName = sourceAttrs[i].proName;
			var proShowType = Number(sourceAttrs[i].proShowType);
			switch ( proShowType ) {
				case 10 :
					sourceAttrs[i].proValue = $("#" + attrName + String(i)).find("input").val();
					break;
				case 20:
					sourceAttrs[i].proValue = $("#" + attrName + String(i)).find("select").val();
					break;
				case 30:
					sourceAttrs[i].proValue = $("#" + attrName + String(i)).find("input:radio:checked").val();
					break;
				case 40:
					sourceAttrs[i].proValue = $("#" + attrName + String(i)).find("input:checkbox:checked").val();
					break;
				case 50:
					sourceAttrs[i].proValue = $("#" + attrName + String(i)).find("input").val();
					break;
				default:
					sourceAttrs[i].proValue = '';
			}
		}
		var pwaysId = $("#pwaysId").val();
		var productId = $("#productId").val();
		$.ajax({
			url : curContext + '/api/productOrder/apply',
			type: 'POST',
			contentType : "application/json",
			data : JSON.stringify({
				"productId": productId,
				"orderBasicAttrOrm": sourceAttrs,
				"pwaysId": pwaysId
			}),
			success: function (result) {
				if( result.code === 200 ){
					$(".quata-container").css("display","none");
					$(".quota-result-container").css("display","block");
				}else {
					toastr.error('操作失败！');
				}
			}
		});
	});

	$(".quota-close i").click(function (e) {
		$(".quota-apply-container").fadeOut();
		$(".quota-detail-table").fadeIn();
	});

	$("#to_process").click(function (e) {
		window.location.href = curContext + "/v1/pro/pbmOrderProcess/page";
	});

	$("#quota-apply-back").click(function (e) {
		$(".quota-table").css("display","block");
		$(".quota-detail-table").css("display","none");
	});
});