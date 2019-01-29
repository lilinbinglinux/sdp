$(document).ready(function() {
			initUserLoginLogTable();
			resourceTree.init();

			// 为datatable外的父级设置高度
			$('#UserLoginLogTable_wrapper').css('height',
					$('.panel-body').height() - 60);
			// 动态为表格添加父级
			$('#UserLoginLogTable').wrap('<div class="tab-wrapper"></div>');
			$('.tab-wrapper').css('height',
					$('#UserLoginLogTable_wrapper').height() - 63);
			$('.tab-wrapper').niceScroll({
				cursorcolor : "#ccc",
				horizrailenabled : false
			});
		});

// 初始化用户列表表格
function initUserLoginLogTable() {
	$("#UserLoginLogTable").width("100%").dataTable({
		"columns" : [ 
		              {"data" : "userId"}, 
		              {"data" : "LoginDate"}, 
		              {"data" : "loginIp"}
		            ],
		ajax : {
			url : webpath + '/userLoginLog/selectPage',
			"type" : 'POST',
			"dataSrc" : function(json) {
				json.iTotalRecords = json.total;
				json.iTotalDisplayRecords = json.total;
				//console.log(json.data);
				return json.data;
			}
		},
		columnDefs : [ {
			"targets" : 0,
			"data" : null,
			"render" : function(data, type, row) {
				var html = '';
				if (data == null || data == "") {
					html += '暂无登录信息';
				} else {
					html += data;
				}
				return html;
			}
		}, {
			"targets" : 1,
			"data" : null,
			"width" : '30%',
			"render" : function(data, type, row) {
				var html = '';
				var timestamp = row.loginDate;
				var newDate = new Date();
				newDate.setTime(timestamp);
				if (row.loginDate == null || row.loginDate == "") {
					html += '暂无登录信息';
				} else {
					html += newDate.toLocaleString();
				}
				return html;
			}
		}, {
			"targets" : 2,
			"data" : null,
			"render" : function(data, type, row) {
				var html = '';
				if (data == null || data == "") {
					html += '暂无登录信息';
				} else {
					html += data;
				}
				return html;
			}
		} ]
	});
}

// 刷新数据 true 整个刷新 false 保留当前页刷新
function reloadTableData(isCurrentPage) {
	$("#UserLoginLogTable").dataTable().fnDraw(
			isCurrentPage == null ? true : isCurrentPage);
}

var resourceTree = {
	treeObj : null,
	setting : {
		data : {
			simpleData : {
				enable : true,
				idKey : 'resourcesId',
				pIdKey : 'parentId'
			},
			key : {
				name : 'resourcesName',
				url : 'xurl'
			}
		},
		check : {
			enable : true,
			chkboxType : {
				"Y" : "p",
				"N" : "p"
			}
		}
	},
	init : function() {
		if (resourceTree.treeObj != null) {
			resourceTree.treeObj.destroy()
		}
		$.ajax({// 初始化组织机构树
			"url" : webpath + "/role/resources",
			"type" : "POST",
			dataType : "json",
			success : function(data) {
				if (data != null && data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						data[i].icon = webpath
								+ resourceTypeIcon[data[i].resourcesTypeId];
					}
					resourceTree.treeObj = $.fn.zTree.init($("#resourceTree"),
							resourceTree.setting, data);
					resourceTree.treeObj.expandAll(true);
				} else {
					layer.msg('暂无数据', {
						time : 1000,
						icon : 5
					});
				}
			}
		});
	}
}

var resourceTypeIcon = {
	"0" : "/resources/img/icon/16x16/floder1-black.png",
	"1" : "/resources/img/icon/16x16/link1-black.png",
	"2" : "/resources/img/icon/16x16/link-black.png",
	"3" : "/resources/img/icon/16x16/fun-black.png",
};
