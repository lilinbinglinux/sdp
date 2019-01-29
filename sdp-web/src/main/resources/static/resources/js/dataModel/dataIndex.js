$(document).ready(function () {
	var curState = "dataType"; //当前是操作数据源类型还是数据源
	var dataOfType; //数据源类型的数据
	var dataOfSource; //数据源数据

	//加载数据源类型
	function loadResourceType() {
		curState = "dataType";
		$.ajax({
			url: curContext + '/v1/sqlModel/show/dataResTypeList',
			success: function (resultData) {
				if (resultData.msg == "success") {
					var data = resultData.data;
					dataOfType = data; //刷新数据源类型的数据
					$(".folder-list").empty();
					for(var i = 0; i < data.length; i++){

						var html =
							'<div class="folder-box" title="双击打开">\n' ;


						if( data[i].resType == "1" ){
							//mysql
							html += '<span class="folder-ico ico-mysql"></span>\n' ;
						} else if( data[i].resType == "2" ){
							//Oracle
							html += '<span class="folder-ico ico-Oracle"></span>\n' ;
						} else if( data[i].resType == "3" ){
							//DB2
							html += '<span class="folder-ico ico-DB2"></span>\n' ;
						} else if( data[i].resType == "5" ){
							//SqlServer
							html += '<span class="folder-ico ico-SqlServer"></span>\n' ;
						}

						html +=
							'        <span class="folder-name">'+ data[i].dataResTypeName +'</span>\n' +
							'        <input hidden="hidden" value="'+data[i].dataResTypeId+'" />' +
							'      </div>';

						$(".folder-list").append(html);
					}
				}
			}
		});
	}

	//加载数据源
	function loadResouce( dataResTypeId ) {
		curState = "dataResource";
		$.ajax({
			url: curContext + '/v1/sqlModel/show/dataResByTypeIdList',
			data: { "dataResTypeId": dataResTypeId },
			success: function (resultData) {
				if( resultData.msg == "success" ) {
					var dataSources = resultData.data;
					dataOfSource = dataSources; //刷新数据源的数据
					$(".folder-list").empty();
					for( var i = 0; i < dataSources.length; i++ ) {
						$(".folder-list").append(
							'<div class="folder-box" title="双击打开">\n' +
							'        <span class="folder-ico ico-database"></span>\n' +
							'        <span class="folder-name">'+ dataSources[i].dataResTypeName +'</span>\n' +
							'        <input hidden="hidden" value="'+ dataSources[i].dataResId +'" />' +
							'</div>'
						);
					}
				}
			}
		});
	}

	//新增
	function newFoler() {
		if( curState == "dataType" ) {
			//新增数据源类型
			var newDataTypeModal = $("#newDataType");
			newDataTypeModal.find("input[name='dataResTypeName']").val("");
			newDataTypeModal.find("input[name='resume']").val("");
			newDataTypeModal.find("input[name='sortNum']").val("");

			$("#dataDriver").val('com.mysql.jdbc.Driver');
			$("#resType").change(function () {
				switch ( $(this).val() ) {
					case "1":
						$("#dataDriver").val('com.mysql.jdbc.Driver');
						break;
					case "2":
						$("#dataDriver").val('oracle.jdbc.OracleDriver');
						break;
					case "5":
						$("#dataDriver").val('com.microsoft.sqlserver.jdbc.SQLServerDriver');
						break;
					case "3":
						$("#dataDriver").val('com.ibm.db2.jcc.DB2Driver');
						break;
					default:
						$("#dataDriver").val('');
				}
			});
			$("#dataForm").bootstrapValidator({
				fields: {
					dataResTypeName: {
						validators: {
							notEmpty: {
								message: '类型名称不能为空'
							}
						}
					},
					dataDriver: {
						validators: {
							notEmpty: {
								message: '驱动不能为空'
							}
						}
					}
				}
			});

			var dataTypeValidator = $("#dataForm").data('bootstrapValidator');
			newDataTypeModal.find(".has-error").removeClass("has-error");
			newDataTypeModal.find(".help-block").css("display", "none");

			newDataTypeModal.modal("show");

			$("#newDataSub").unbind("click");
			$("#newDataSub").click(function (e) {

				dataTypeValidator.validate();

				if( dataTypeValidator.isValid() ){
					$.ajax({
						url: curContext + '/v1/sqlModel/saveSqlDataResType',
						data: $("#dataForm").serialize(),
						success: function (result) {
							if( result.dataResTypeId ){
								$("#newDataType").modal("hide");
								$.message('新增数据源类型成功！');
								loadResourceType();
							}else{
								$.message({
									message:'新增数据源类型失败！',
									type:'error'
								});
							}
						}
					});
				}
			});
		}
		else if( curState == "dataResource" ) {
			//新增数据源
			var dataResTypeId = $("#dataResTypeId").val();
			var sourceModal = $("#sourceModal");
			sourceModal.find("input[name='dataResId']").remove();
			$("#sourceTypeName").val($("#dataSource").text());
			sourceModal.find("input[name='dataResTypeId']").val(dataResTypeId);
			sourceModal.find("input[name='dataResTypeName']").val("");
			sourceModal.find("input[name='dataResUrl']").val("");
			sourceModal.find("input[name='tableSchema']").val("");
			sourceModal.find("input[name='username']").val("");
			sourceModal.find("input[name='password']").val("");
			sourceModal.find("input[name='dataResDesc']").val("");
			sourceModal.find("input[name='resume']").val("");
			sourceModal.find("input[name='sortNum']").val("");

			sourceModal.find(".modal-title").text("新增数据源");

			$("#sourceForm").bootstrapValidator({
				fields: {
					dataResTypeName: {
						validators: {
							notEmpty: {
								message: '数据源名称不能为空'
							}
						}
					},
					dataResUrl: {
						validators: {
							notEmpty: {
								message: '数据源连接不能为空'
							}
						}
					},
					tableSchema: {
						validators: {
							notEmpty: {
								message: 'Schema名称不能为空'
							}
						}
					},
					username: {
						validators: {
							notEmpty: {
								message: '用户名不能为空'
							}
						}
					},
					password: {
						validators: {
							notEmpty: {
								message: '密码不能为空'
							}
						}
					}
				}
			});

			var sourceValidator = $("#sourceForm").data('bootstrapValidator');
			sourceModal.find(".has-error").removeClass("has-error");
			sourceModal.find(".help-block").css("display", "none");


			sourceModal.modal("show");

			$("#sourceSub").unbind("click");
			$("#sourceSub").click(function (e) {

				sourceValidator.validate();
				if( sourceValidator.isValid() ){
					sourceModal.modal("hide");
					$.ajax({
						url: curContext + '/v1/sqlModel/saveSqlDataRes',
						data: $("#sourceForm").serialize(),
						success: function (result) {
							if( result.dataResId ) {
								$.message('新增数据源成功！');
								loadResouce(dataResTypeId);
							}else{
								$.message({
									message:'新增数据源失败！',
									type:'error'
								});
							}
						}
					});
				}
			});
		}
	}

	$(".new-folder").click(function (e) {
		newFoler();
	});

	$(".goDataTypeManage").click(function () {
		loadResourceType();
		$("#dataSource").text("");
	});

	//右键菜单事件
	$(".power-main").contextmenu(function (e) {
		var e = e || window.event;
		if( $(e.target).parents(".folder-box").length > 0 ) {
			var _this_ = $(e.target).parents(".folder-box")[0];
			var x = e.clientX - $(".power-main")[0].offsetLeft;
			var y = e.clientY - $(".power-main")[0].offsetTop;
			var menuContext = $(".menu-context");
			var menuEdit = menuContext.find(".con-edit");
			var menuDelete = menuContext.find(".con-delete");
			var rowId = $(_this_).find("input").val();

			menuContext.css("top", y + "px");
			menuContext.css("left", x + "px");
			menuContext.css("display","block");
			$(".background-context").css("display","none");

			menuEdit.unbind("click");
			menuDelete.unbind("click");

			menuEdit.click(function (e) {



				if (curState == "dataResource") {
					//遍历数据源数据
					var row;
					if (dataOfSource && dataOfSource.length > 0) {
						for (var i = 0; i < dataOfSource.length; i++) {
							if (dataOfSource[i].dataResId == rowId) {
								row = dataOfSource[i];
							}
						}
					}

					var sourceModal = $("#sourceModal");
					$("#sourceForm").append('<input hidden name="dataResId" value="' + row.dataResId + '">');
					sourceModal.find("input[name='dataResTypeId']").val(row.dataResTypeId);
					sourceModal.find("input[name='dataResTypeName']").val(row.dataResTypeName);
					$("#sourceTypeName").val($("#dataSource").text());
					sourceModal.find("input[name='dataResUrl']").val(row.dataResUrl);
					sourceModal.find("input[name='tableSchema']").val(row.tableSchema);
					sourceModal.find("input[name='username']").val(row.username);
					sourceModal.find("input[name='password']").val(row.password);
					sourceModal.find("input[type='radio'][name='dataStutas'][value='" + row.dataStutas + "']").attr("checked", 'checked');
					sourceModal.find("input[type='radio'][name='isDefault'][value='" + row.isDefault + "']").attr("checked", 'checked');
					sourceModal.find("input[name='dataResDesc']").val(row.dataResDesc);
					sourceModal.find("input[name='resume']").val(row.resume);
					sourceModal.find("input[name='sortNum']").val(row.sortNum);

					sourceModal.find(".modal-title").text("编辑数据源");

					$("#sourceForm").bootstrapValidator({
						fields: {
							dataResTypeName: {
								validators: {
									notEmpty: {
										message: '数据源名称不能为空'
									}
								}
							},
							dataResUrl: {
								validators: {
									notEmpty: {
										message: '数据源连接不能为空'
									}
								}
							},
							tableSchema: {
								validators: {
									notEmpty: {
										message: 'Schema名称不能为空'
									}
								}
							},
							username: {
								validators: {
									notEmpty: {
										message: '用户名不能为空'
									}
								}
							},
							password: {
								validators: {
									notEmpty: {
										message: '密码不能为空'
									}
								}
							}
						}
					});
		
					var sourceValidator = $("#sourceForm").data('bootstrapValidator');
					sourceModal.find(".has-error").removeClass("has-error");
					sourceModal.find(".help-block").css("display", "none");


					sourceModal.modal("show");

					$("#sourceSub").unbind("click");
					$("#sourceSub").click(function (e) {
						sourceValidator.validate();
						if(sourceValidator.validate()){
							sourceModal.modal("hide");
							$.ajax({
								url: curContext + '/v1/sqlModel/saveSqlDataRes',
								data: $("#sourceForm").serialize(),
								success: function (result) {
									if( result.dataResId ) {
										loadResouce(row.dataResTypeId);
										$.message('修改数据源信息成功！');
									}else{
										$.message({
											message:'修改数据源信息失败！',
											type:'error'
										});
									}
								}
							});
						}
					});
					}
				else if( curState == "dataType" ) {
					//遍历数据源类型数据
					var row;
					if (dataOfType && dataOfType.length > 0) {
						for (var i = 0; i < dataOfType.length; i++) {
							if (dataOfType[i].dataResTypeId == rowId) {
								row = dataOfType[i];
							}
						}
					}

					var editModal = $("#editDataType");
					editModal.find("input[name='dataResTypeId']").val(row.dataResTypeId);
					editModal.find("input[name='dataResTypeName']").val(row.dataResTypeName);
					editModal.find("input[name='dataDriver']").val(row.dataDriver);
					editModal.find("input[name='resume']").val(row.resume);
					editModal.find("input[name='sortNum']").val(row.sortNum);
					editModal.find("input[type='radio'][name='dataStutas'][value='" + row.dataStutas + "']").attr("checked", 'checked');
					editModal.find("select[name='resType']").val(row.resType);

					editModal.modal("show");

					editModal.find("select[name='resType']").change(function () {
						switch ($(this).val()) {
							case "1":
								$("#dataDriver").val('com.mysql.jdbc.Driver');
								break;
							case "2":
								$("#dataDriver").val('oracle.jdbc.OracleDriver');
								break;
							case "5":
								$("#dataDriver").val('com.microsoft.sqlserver.jdbc.SQLServerDriver');
								break;
							case "3":
								$("#dataDriver").val('com.ibm.db2.jcc.DB2Driver');
								break;
							default:
								$("#dataDriver").val('');
						}
					});
					$("#editForm").bootstrapValidator({
						fields: {
							dataResTypeName: {
								validators: {
									notEmpty: {
										message: '类型名称不能为空'
									}
								}
							},
							dataDriver: {
								validators: {
									notEmpty: {
										message: '驱动不能为空'
									}
								}
							}
						}
					});

					var bootstrapValidator = $("#editForm").data('bootstrapValidator');
					$("#editForm").find(".has-error").removeClass("has-error");
					$("#editForm").find(".help-block").css("display", "none");

					$("#editDataSub").unbind("click");
					$("#editDataSub").click(function (e) {

						bootstrapValidator.validate();

						if (bootstrapValidator.isValid()) {
							$.ajax({
								url: curContext + '/v1/sqlModel/saveSqlDataResType',
								data: $("#editForm").serialize(),
								success: function (result) {
									if (result.dataResTypeId) {
										editModal.modal("hide");
										$.message('修改数据源类型成功！');
										loadResourceType();
									} else {
										$.message({
											message:'修改数据源类型失败！',
											type:'error'
										});
									}
								}
							});
						}
					});
				}
			});

			menuDelete.click(function (e) {

				if( curState == "dataType" ) {
					var row;
					if (dataOfType && dataOfType.length > 0) {
						for (var i = 0; i < dataOfType.length; i++) {
							if (dataOfType[i].dataResTypeId == rowId) {
								row = dataOfType[i];
							}
						}
					}
					var id = row.dataResTypeId;

					var deleteModal = $("#deleteModal");
					deleteModal.find(".modal-body").text("确认删除" + row.dataResTypeName + "?");
					deleteModal.modal("show");
					$("#deleteSub").unbind("click");
					$("#deleteSub").click(function (e) {
						deleteModal.modal("hide");
						$.ajax({
							url: curContext + '/v1/sqlModel/delete/byDataResTypeId',
							data: { "dataResTypeId" : id },
							success: function (result) {
								if( result.code === 200 ){
									toastr.success('删除成功！');
									$(_this_).remove();
								} else{
									toastr.error('删除失败！');
								}
							}
						});
					});
				}
				else if( curState == "dataResource" ) {
					var row;
					if (dataOfSource && dataOfSource.length > 0) {
						for (var i = 0; i < dataOfSource.length; i++) {
							if (dataOfSource[i].dataResId == rowId) {
								row = dataOfSource[i];
							}
						}
					}
					var id = row.dataResId;
					var dataResTypeId = row.dataResTypeId;

					var deleteModal = $("#deleteModal");
					deleteModal.find(".modal-body").text("确认删除" + row.dataResTypeName + "?");
					deleteModal.modal("show");
					$("#deleteSub").unbind("click");
					$("#deleteSub").click(function (e) {
						deleteModal.modal("hide");
						$.ajax({
							url: curContext + '/v1/sqlModel/delete/byDataResId',
							data: { "dataResId" : id },
							success: function (result) {
								if( result.code === 200 ){
									toastr.success('删除成功！');
									$(_this_).remove();
								} else{
									toastr.error('删除失败！');
								}
							}
						});
					});
				}
			});
		}
		else {
			var backgroundContext = $(".background-context");
			var menuNew = backgroundContext.find(".con-new");
			var menuRefresh = backgroundContext.find(".con-refresh");
			var x = e.clientX - $(".power-main")[0].offsetLeft;
			var y = e.clientY - $(".power-main")[0].offsetTop;

			backgroundContext.css("top", y + "px");
			backgroundContext.css("left", x + "px");
			backgroundContext.css("display","block");
			$(".menu-context").css("display","none");

			menuNew.unbind("click");
			menuRefresh.unbind("click");

			menuNew.click(function (e) {
				newFoler();
			});

			menuRefresh.click(function (e) {
				if( curState == "dataType" ) {
					loadResourceType();
				}else {
					var dataResTypeId = $("#dataResTypeId").val();
					loadResouce(dataResTypeId);
				}
				$(".background-context").css("display","none");
			});
		}

		return false;
	});

	//双击事件
	$(".power-main").dblclick(function (e) {
		var e = e || window.event;

		if( $(e.target).parents(".folder-box").length > 0 ) {
			var _this_ = $(e.target).parents(".folder-box")[0];
			var rowId = $(_this_).find("input").val();
			var row;

			if( curState == "dataType" ) {
				if ( dataOfType && dataOfType.length > 0) {
					for (var i = 0; i < dataOfType.length; i++) {
						if (dataOfType[i].dataResTypeId == rowId) {
							row = dataOfType[i];
							break;
						}
					}
				}
				var dataTypeId = row.dataResTypeId;
				$("#dataSource").text(row.dataResTypeName);
				$("#dataResTypeId").val(dataTypeId);
				loadResouce(dataTypeId);
			} else if( curState == "dataResource" ) {
				if ( dataOfSource && dataOfSource.length > 0) {
					for (var i = 0; i < dataOfSource.length; i++) {
						if (dataOfSource[i].dataResId == rowId) {
							row = dataOfSource[i];
							break;
						}
					}
				}
				var height = window.screen.availHeight;
				var width = window.screen.availWidth;
				window.open( curContext + '/v1/sqlModel/manage/dataBaseDraw/'+ row.dataResId ,'_blank','left=0,top=0,width='+width+',height='+height+',menubar=no,toolbar=no,status=no,scrollbars=yes,resizable=yes,location=no')
			}
		}
	});

	//开篇加载数据源类型
	loadResourceType();

	$(document).click(function (e) {
		var e = e || window.event;
		if( $(e.target).hasClass("menu-con-item") ) {
			return;
		}
		$(".menu-context").css("display","none");
		$(".background-context").css("display","none");
	});
});





