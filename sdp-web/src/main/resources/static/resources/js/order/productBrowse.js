// $(document).ready(function () {

	function getProductTable(productTypeId) {
		var allPorderWaysArr = getAllPorderWays();

		$("#productTable").bootstrapTable({
			url: $webpath + "/api/product/allProductWithAuth",
			method: "POST",
			contentType: "application/json",
			dataType: "json",
			classes: 'table-no-bordered',
			cache: false,
			striped: false,
			pagination: true,
			pageNumber: 1,
			pageSize: 10,
			pageList: [10, 25, 50, 100],
			paginationDetailHAlign: 'left',
            showJumpto: true,
            paginationPreText: '<i class="fa fa-angle-left bconsole-left-arrow" aria-hidden="true"></i>',
			paginationNextText: '<i class="fa fa-angle-right bconsole-left-arrow" aria-hidden="true"></i>',
			formatRecordsPerPage: function (pageNumber) {
                // return '每页显示 ' + pageNumber + ' 条记录';
                return '' + pageNumber + '';
            },
            formatShowingRows: function (pageFrom, pageTo, totalRows) {
                // return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
                return '共' + totalRows + '条';
            },
			queryParams: function (params) {
				return JSON.stringify({
					"page": {
						"pageNo": (params.offset / params.limit) + 1,
						"pageSize": 10
					},
					"dataAuth": "00",
					"product": {
						"productStatus": "30",
						"productTypeId": productTypeId ? productTypeId : '',
						"productName": $('#productNameInput').val()
					}
				})
			},
			queryParamsType: 'limit',
			responseHandler: function (res) {
				loadProductBoxs(res.list);
				return {
					"total": res.totalCount,
					"rows": res.list
				}
			},
			sidePagination: "server",
			clickToSelect: true,
			uniqueId: "productId",
			columns: [{
				field: 'productName',
				title: '服务名称',
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					return '<a href="javascript:;" onclick="toProductApply(\'' + row.productId + '\',\'' + row.productStatus + '\')">'+ row.productName +'</a>';
				}
			}, {
				field: 'productId',
				title: '服务编码',
				align: 'center',
				valign: 'middle'
			}, {
				field: 'productVersion',
				title: '版本号',
				align: 'center',
				valign: 'middle'
			}, {
				field: 'productStatus ',
				title: '服务状态',
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					return svcStateConvert(row.productStatus);
				}
			}, {
				field: 'productTypeId ',
				title: '服务类型',
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					return row.productTypeName;
				}
			}, {
				field: 'orderType',
				title: '订购方式',
				align: 'center',
				valign: 'middle',
				formatter: function (value, row, index) {
					for (var i = 0; i < allPorderWaysArr.length; i++) {
						if(allPorderWaysArr[i].pwaysType == row.orderType) {
							return allPorderWaysArr[i].pwaysName;
						}
					}
				}
			}, {
				field: 'createBy',
				title: '创建人',
				align: 'center',
				valign: 'middle'
			}, {
				field: 'createDate',
				title: '创建时间',
				align: 'center',
				valign: 'middle'
			}]
		})
	}

	// 获取所有订购方式
	function getAllPorderWays() {
		var arr = [];
		$.ajax({
			type: 'POST',
			contentType: 'application/json',
			dataType: "json",
			data: JSON.stringify({pwaysType: ''}),
			url: $webpath + "/api/porderWays/allPorderWays",
			success: function (data) {
				$.each(data,function (index,item) {
					if(item.pwaysRemark == null) {
						arr.push(item);
					}
				})
			}
		});
		return arr;
	}

	// 获取所有服务分类
	function getAllCategory() {
		$.ajax({
			type: "GET",
			url: $webpath + "/api/productType/allCategory",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function (data) {
				if (data) {
					var treeArray = [];
					for( var i = 0; i < data.length; i++ ){
						treeArray.push({
							"id" : data[i].productTypeId,
							"name" : data[i].productTypeName,
							"pId": data[i].parentId ? data[i].parentId : '' ,
							"sortId" : data[i].sortId,
							"iconSkin" : "productType",
							"type": "productType"
						})
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
							onClick: zTreeOnClick,
							onDblClick: zTreeOnDblClick
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
						zTree.expandNode(treeNode);
						var productTypeId = treeNode.id;
						// $("#title-span").text('-'+treeNode.name);
						$(".table-responsive").empty();
						$(".products-list").empty();
						$(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
						getProductTable(productTypeId);
					}

					function zTreeOnDblClick(event, treeId, treeNode) {
						var productTypeId = treeNode.id;
						$("#title-span").text('-'+treeNode.name);
						$(".table-responsive").empty();
						$(".products-list").empty();
						$(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
						getProductTable(productTypeId);
					}

					var zTreeObj = $.fn.zTree.init($("#serviceTree"), setting, treeArray);
					zTree = $.fn.zTree.getZTreeObj("serviceTree");
				}
			}
		})
	}

	// 判断可否进入产品申请
	function toProductApply(productId) {
		window.open($webpath +'/v1/mall/productDetails?svcId='+ productId);
	}

	//加载boxs
	function loadProductBoxs(boxs){
		$(".products-list").empty();
		if( boxs && boxs.length > 0 ){
			for( var i = 0; i < boxs.length; i++ ){
				var html = '';
				html += '<a class="products-item-container col-xs-12 col-sm-6 col-md-3 col-lg-3" title="单击">\n' +
					'         <input type="text" hidden value="'+boxs[i].productId+'">' +
					'         <div class="products-item">\n' +
					'             <div class="item-img">\n' +
					'                 <span id="product_'+boxs[i].productId+'"><img src="" title="图片"></span>\n' +
					'             </div>\n' +
					'             <div class="item-title">'+boxs[i].productName+'</div>\n' +
					'             <div class="item-info">\n' +
					'                 <p class="info-p">'+boxs[i].productIntrod+'</p>\n' +
					'             </div>\n' +
					'         </div>\n' +
					'     </a>';
				$(".products-list").append(html);
			}

			$(".products-item-container").click(function (e) {
				window.open($webpath +'/v1/mall/productDetails?svcId='+ $(this).find("input").val());
			});

			//加载图片
			for( var j = 0; j < boxs.length; j++ ){
				$.ajax({
					url: $webpath + '/api/product/singleProduct/' + boxs[j].productId,
					type: 'GET',
					success: function (singleProduct) {
						if( singleProduct ){
							$("#product_"+singleProduct.productId).find("img").attr("src","data:image/jpg;base64,"+singleProduct.productLogo);
						}
					}
				});
			}
		}else {
			$(".products-list").append('<span class="no-project-tips">没有查找到相关服务</span>');
		}
	}

	$(".nav-collapse-left").click(function (e) {
		$(".serviceList-container").addClass("left-to-0");
	});
	$(".nav-collapse-right").click(function (e) {
		$(".serviceList-container").removeClass("left-to-0");
	});
	$("#showAllProduct").click(function (e) {
		$("#title-span").text('');
		$(".table-responsive").empty();
		$(".products-list").empty();
		$(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
		getProductTable();
	});

	getProductTable();
	getAllCategory();
// });


$(".bconsole-input-placeholder").click(function(){
    $(this).css("display","none");
    $("#productNameInput").focus();
});

//搜索框
$("#productNameInput").blur(function(e){
    if( $(this).val() === "" ){
        $(".bconsole-input-placeholder").css("display","block");
    }else {
        $(".bconsole-input-placeholder").css("display","none");
    }
});

//回车搜索
$(document).keydown(function(e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if(event.keyCode == "13") {
            $('#productTable').bootstrapTable('refresh');
        }
    }
});

//刷新按钮
$(".btn-refresh").click(function(e){
	$("#productNameInput").val("");
	$(".bconsole-input-placeholder").css("display","block");
	$(".table-responsive").empty();
	$(".products-list").empty();
	$(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
    getProductTable();
});

//搜索按钮
$(".glyphicon-search").click(function(){
    $('#productTable').bootstrapTable('refresh');
});

