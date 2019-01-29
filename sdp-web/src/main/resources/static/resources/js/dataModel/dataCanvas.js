    //初始化画布
	var powerCanvas = document.getElementById("power-canvas");
	var ctx1 = powerCanvas.getContext("2d");
	var powerContainerW = $("#power-container").width();
	var powerContainerH = $("#power-container").height();
	powerCanvas.width = powerContainerW;
	powerCanvas.height = powerContainerH;

	var canvasContainer = document.getElementById("container");
	var powerContainer = document.getElementById("power-container");

	var tableNames = []; //当前数据源下的所有表名

	var isPowerBoxMove = false,
		  powerBoxX = 0,
		  powerBoxY = 0,
		  powerBoxLeft = 0,
		  powerBoxTop = 0,
		  whichPowerMove = null;

  var dataResTypeId = 0;
  var dbclickBox = null; //鼠标双击的box
  var globaleDatares = null;

  //刷新字段按钮事件
  function fieldsRefresh( refreshType, curValue ) {
  	var curSelect = $(".sub-" + refreshType ).find("select[name='fields']");
	  curSelect.empty();
	  var innerTable = $("#inner-table");
	  var tr = innerTable.find("tbody > tr");
	  curSelect.append('<option value=""></option>');
	  if( tr && tr.length > 0 ){
		  for( var t = 0; t < tr.length; t++ ){
			  var name = $(tr[t]).find("input[name='fieldSqlName']").val();
			  curSelect.append('<option value="'+name+'" '+( curValue === name ? "selected" : "" )+'>'+name+'</option>');
		  }
	  }
  }

  //刷新库按钮事件
  function dataSouceRefresh(sqlSubTreasuryDivision, subTreasuryType) {
	  $("#sqlSubTreasury").empty();
	  $.ajax({
		  url : curContext + "/v1/sqlModel//show/dataResByTypeIdList",
		  data: { "dataResTypeId" : dataResTypeId },
		  success: function(result){
			  var dataRes = result.data;
			  globaleDatares = dataRes;
			  for( var s = 0; s < dataRes.length; s++ ){
			  	if( sqlSubTreasuryDivision && sqlSubTreasuryDivision.length > 0 ){
			  		var sameRes = null;
						for( var u = 0; u < sqlSubTreasuryDivision.length; u++ ){
							if( sqlSubTreasuryDivision[u].dataResId === dataRes[s].dataResId ){
								sameRes = dataRes[s];
								break;
							}
						}
						if( sameRes ){
							$("#sqlSubTreasury").append("<option data-content=\"<span class='label label-info'>"+dataRes[s].dataResTypeName+"</span>\" selected value='"+ dataRes[s].dataResId +"'>"+dataRes[s].dataResTypeName+"</option>");
						}else{
							$("#sqlSubTreasury").append("<option data-content=\"<span class='label label-info'>"+dataRes[s].dataResTypeName+"</span>\" value='"+ dataRes[s].dataResId +"'>"+dataRes[s].dataResTypeName+"</option>");
						}
				  }else{
					  $("#sqlSubTreasury").append("<option data-content=\"<span class='label label-info'>"+dataRes[s].dataResTypeName+"</span>\" value='"+ dataRes[s].dataResId +"'>"+dataRes[s].dataResTypeName+"</option>");
				  }
			  }

			  //遍历字库input
			  if( sqlSubTreasuryDivision && sqlSubTreasuryDivision.length > 0 ){
				  $("#dataInputs").empty();
					for( var m = 0; m < sqlSubTreasuryDivision.length; m++ ){
						var sameInputRes = null;
						for( var n = 0; n < dataRes.length; n++ ){
							if( sqlSubTreasuryDivision[m].dataResId === dataRes[n].dataResId ){
								sameInputRes = dataRes[n];
								break;
							}
						}
						if( subTreasuryType ){
							if( subTreasuryType === "2" ){
								var html =
									'<div class="form-group">\n' +
									'  <label class="col-sm-2 control-label">'+( sameInputRes ? sameInputRes.dataResTypeName : sqlSubTreasuryDivision[m].dataResId )+'</label>\n' +
									'  <div class="col-sm-3">\n' +
									'    <input type="text" name="typeValue1" class="form-control" placeholder="开始" value="'+( sqlSubTreasuryDivision[m].typeValue1 ? sqlSubTreasuryDivision[m].typeValue1 : "" )+'">' +
									'  </div>\n' +
									'  <div class="col-sm-1">\n' +
									'   <span class="seLine"></span>' +
									'  </div>\n' +
									'  <div class="col-sm-3">\n' +
									'   <input type="text" name="typeValue2" class="form-control" placeholder="结束" value="'+( sqlSubTreasuryDivision[m].typeValue2 ? sqlSubTreasuryDivision[m].typeValue2 : "" )+'">' +
									'  </div>\n' +
									'</div>';
								$("#dataInputs").append(html);
							}else {
								var html =
									'<div class="form-group">\n' +
									'  <label class="col-sm-2 control-label">'+( sameInputRes ? sameInputRes.dataResTypeName : sqlSubTreasuryDivision[m].dataResId )+'</label>\n' +
									'  <div class="col-sm-10">\n' +
									'    <input name="typeValue1" type="text" class="form-control" value="'+( sqlSubTreasuryDivision[m].typeValue1 ? sqlSubTreasuryDivision[m].typeValue1 : "" )+'">\n' +
									'  </div>\n' +
									'</div>';
								$("#dataInputs").append(html);
							}
						}
					}
			  }

			  $(".selectpicker").selectpicker('refresh');
		  }
	  });
  }

	//工具栏点击事件
	$(".power-tool-item").each(function () {
		$(this).on('click', function (e) {

			$(canvasContainer).width(powerCanvas.width);
			$(canvasContainer).height(powerCanvas.height);

			if( $(this).hasClass("tool-class-box") ) {
				//建表工具
				$(".power-canvas-container").unbind('mousedown');

				if( $(this).hasClass("tool-item-active") ) {

					canvasContainer.style='cursor: default;';
					$(this).removeClass("tool-item-active");
					$(".power-canvas-container").unbind('mousedown');

				}else {
					$(".power-tool-item").not($(".tool-straight-line")).removeClass("tool-item-active");
					$(this).addClass("tool-item-active");
					canvasContainer.style='cursor: url( "'+curContext+'/resources/img/dataModel/table.svg"), pointer;';

					$(".power-canvas-container").mousedown(function (e) {

						if( $(e.target).parents(".power-class-box").length > 0 || $(e.target).hasClass("power-class-box") ){

						} else {

							var offsetX = e.clientX - $("#power-container")[0].offsetLeft + $("#power-container")[0].scrollLeft;
							var offsetY = e.clientY - $(".power-main")[0].offsetTop + $("#power-container")[0].scrollTop;
							var uuid = getuuid(16,16);
							var html = getPowerBoxHtml(uuid, offsetX, offsetY);

							$('.power-canvas-container').append(html);

							canvasContainer.style='cursor: default;';
							$(".tool-class-box").removeClass("tool-item-active");
							$(".power-canvas-container").unbind('mousedown');

							//绑定双击事件
							// $("#" + uuid ).dblclick(function (e) {
							// 	var id = $(this).attr("id");
							// 	powerClassDBClick(e, offsetX, offsetY, id);
							// });

							//初始化SummerBox
							$("#"+uuid).summerBox();

							if( $("#power-container").width() - offsetX  < 256 ) {
//								  $("#container").width( $("#container").width() + 300 );
								powerCanvas.width = $("#container").width() + 300 - 10;
							}

							if( $("#power-container").height() - offsetY < 256 ) {
//								  $("#container").height( $("#container").height() + 300 );
								powerCanvas.height = $("#container").height()+ 300 - 20;
							}
						}
					});
				}
			}
			else if( $(this).hasClass("tool-hand") ) {
				//手势工具
				$(".power-canvas-container").unbind('mousedown');
				if( $(this).hasClass("tool-item-active") ) {

					canvasContainer.style='cursor: default;';
					$(this).removeClass("tool-item-active");
					$(".power-canvas-container").unbind('mousedown');
				}else {
					canvasContainer.style='cursor: pointer;';
					$(".power-tool-item").not($(".tool-straight-line")).removeClass("tool-item-active");
					$(this).addClass("tool-item-active");
					$(".power-canvas-container").mousedown(function (e) {
						e = e || window.event;
						/*
						 *
						 * power-container 拖动
						 * */
						var oy = e.clientY;
						var ox = e.clientX;
						var oscrollTop = powerContainer.scrollTop;
						var oscrollLeft = powerContainer.scrollLeft;

						$(".power-canvas-container").mousemove(function (e) {
							e = e || window.event;
							powerContainer.scrollTop = oscrollTop - ( e.clientY - oy );
							powerContainer.scrollLeft = oscrollLeft - ( e.clientX - ox );
						});

						$(".power-canvas-container").mouseup(function (e) {
							$(".power-canvas-container").unbind("mousemove");
							$(".power-canvas-container").unbind("mouseup");
						});
						/*^^^^^^^^^^^^^^^^^^^^^^^^^^power-container 拖动^^^^^^^^^^^^^^^^^^^^^^^^^^*/
					});
				}
			}
			else if( $(this).hasClass("tool-mouse") ) {
				$(".power-canvas-container").unbind('mousedown');
				if( $(this).hasClass("tool-item-active") ) {
					$(this).removeClass("tool-item-active");
					$(".power-canvas-container").unbind('mousedown');
				}else {
					$(".power-tool-item").not($(".tool-line")).removeClass("tool-item-active");
					$(this).addClass("tool-item-active");
					canvasContainer.style='cursor: default;';
				}
			}
			else if( $(this).hasClass("tool-straight-line") ) {
				//划线
				$(".power-canvas-container").unbind('mousedown');
				if( $(this).hasClass("tool-item-active") ) {
					$(this).removeClass("tool-item-active");
					$(".power-canvas-container").unbind('mousedown');
				}else {
					$(".tool-line").not($(".tool-straight-line")).removeClass("tool-item-active");
					$(this).addClass("tool-item-active");
				}
			}
			else if( $(this).hasClass("tool-delete") ) {
				$(".power-canvas-container").unbind('mousedown');
				if( $(".power-active") ) {
					var curObj = $(".power-active");

					if( curObj.hasClass("summer-box") ) {
						var tableData = curObj.data("boxDetail");
						if( tableData ){
							var tableId = tableData.tableId;
							var lines = $(".line_box_container");
							var isHaveForeignKey = false;
							for( var i = 0; i < lines.length; i++ ) {
								var curLine = $(lines[i]);
								var curLineData = curLine.data("lineInfo");
								if( curLineData ){
									if( curLineData.fromBoxId === tableId || curLineData.toBoxId === tableId ){
										isHaveForeignKey = true;
										break;
									}
								}
							}
							if( isHaveForeignKey ){
								$.message({
									message:'当前表含有关联字段！请先删除关联外键',
									type:'error'
								});
								return;
							}
							$("#confirmDeleteModal").find(".delete-confirm").unbind("click");
							$("#confirmDeleteModal").find(".modal-body").text("确认删除该表吗？");
							$("#confirmDeleteModal").modal("show");
							$("#confirmDeleteModal").find(".delete-confirm").click(function(){
								$("#confirmDeleteModal").modal("hide");
								$.ajax({
									url: curContext + '/v1/sqlModel/deletetableinfo/' + tableId,
									success: function(result){
										if( result.code == 200 ) {
											curObj.remove();
											var zTree = $.fn.zTree.getZTreeObj("curTree");
											var node = zTree.getNodeByParam("id", tableId);
											zTree.removeNode(node);
											$.message('删除表成功！');
										} else {
											$.message({
												message:'删除表失败！',
												type:'error'
											});
										}
									}
								});
							});
						}
					} else if( curObj.hasClass("line_box_container") ) {
						var curLineData = curObj.data("foreignKey");
						var lineId = curLineData.foreignKeyId;
						$("#confirmDeleteModal").find(".delete-confirm").unbind("click");
							$("#confirmDeleteModal").find(".modal-body").text("确认删除该外键吗？");
							$("#confirmDeleteModal").modal("show");
							$("#confirmDeleteModal").find(".delete-confirm").click(function(){
								$("#confirmDeleteModal").modal("hide");
								$.ajax({
									url: curContext + '/v1/sqlModel/deletesqltabletypeforeignkey',
									data: { "foreignKeyId": lineId },
									success: function(result){
										if( result.code == 200 ){
											curObj.remove();
											$.message('删除外键成功！');
										}else {
											$.message({
												message:'删除外键失败！',
												type:'error'
											});
										}
									}
								});
							});
					}
				}
			}

			$(canvasContainer).width(powerCanvas.width);
			$(canvasContainer).height(powerCanvas.height);
		});
	});

	//校验
	//基础信息校验
	function tableFormValidator(){
		$("#table-info-form").bootstrapValidator({
			message: 'This value is not valid',
            feedbackIcons: {
            	valid: 'glyphicon glyphicon-ok',
            	invalid: 'glyphicon glyphicon-remove',
            	validating: 'glyphicon glyphicon-refresh'
			},
			excluded:[":disabled"],
			fields: {
				tableName: {
					validators: {
						notEmpty: {
							message: '表名不能为空'
						},
						stringLength: {
							message: '表名长度最少四位',
							min: 4
						}
					}
				},
				tableSqlName: {
					validators: {
						notEmpty: {
							message: '表英文不能为空'
						},
						regexp: {
                            regexp: /^[0-9a-zA-Z_]{1,}$/,
                            message: '只能是字母数字下划线组成'
						},
						stringLength: {
							message: '英文名长度最少四位',
							min: 4
						}
					}
				}
			}
		});
	}


	tableFormValidator();

	//分区校验
	function areaValidator(){
		$("#sub_form").bootstrapValidator({
			message: 'This value is not valid',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			excluded:[":disabled"],
			fields: {
				partitionValue: {
					validators:{
						notEmpty: {
							message: '分区信息不能为空'
						}
					}
				}
			}
		});
	}	

	//分表校验
	function meterValidator(){
		$("#sub_form").bootstrapValidator({
			message: 'This value is not valid',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			excluded:[":disabled"],
			fields: {
				subTableTypeValue1: {
					validators: {
						notEmpty: {
							message: '不能为空'
						},
						regexp: {
							regexp: /^[+]{0,1}(\d+)$/,
							message: '请输入正整数'
						},
						callback: {
							message: '起始值必须小于结束值',
							callback: function(value, validator, $field) {
								var otherbox = validator.getFieldElements("subTableTypeValue2").val();
								if (parseInt(otherbox)<parseInt(value)) {
									validator.updateStatus("subTableTypeValue2", validator.STATUS_VALID, 'callback');
									return false;
								}else if( parseInt(value)<parseInt(otherbox) ){
									validator.updateStatus("subTableTypeValue2", "VALID", 'callback');
									return true;
								}
								return true;
							}
						}
					}
				},
				subTableTypeValue2: {
					validators: {
						notEmpty: {
							message: '不能为空'
						},
						regexp: {
							regexp: /^[+]{0,1}(\d+)$/,
							message: '请输入正整数'
						},
						callback: {
							message: '起始值必须小于结束值',
							callback: function(value, validator, $field) {
								var otherbox = validator.getFieldElements("subTableTypeValue1").val();
								if (parseInt(otherbox)>parseInt(value)) {
									validator.updateStatus("subTableTypeValue1", validator.STATUS_VALID, 'callback');
									return false;
								}else if( parseInt(otherbox)<parseInt(value) ){
									validator.updateStatus("subTableTypeValue1", 'VALID', 'callback');
									return true;
								}
								return true;
							}
						}
					}
				},
				dates: {
					validators: {
						notEmpty: {
							message: '选择date类型'
						}
					}
				},
				fields: {
					validators: {
						notEmpty: {
							message: '请选择字段'
						}
					}
				},
				types: {
					validators: {
						notEmpty: {
							message: '请选择类型'
						}
					}
				}
			}
		});
	}

	//分库校验
	function treasuryValidator(){
		$("#sub_form").bootstrapValidator({
			message: 'This value is not valid',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			excluded:[":disabled"],
			fields: {
				subTableTypeValue1: {
					validators: {
						notEmpty: {
							message: '不能为空'
						},
						regexp: {
							regexp: /^[+]{0,1}(\d+)$/,
							message: '请输入正整数'
						},
						callback: {
							message: '起始值必须小于结束值',
							callback: function(value, validator, $field) {
								var otherbox = validator.getFieldElements("subTableTypeValue2").val();
								if (parseInt(otherbox)<parseInt(value)) {
									validator.updateStatus("subTableTypeValue2", validator.STATUS_VALID, 'callback');
									return false;
								}else if( parseInt(value)<parseInt(otherbox) ){
									validator.updateStatus("subTableTypeValue2", "VALID", 'callback');
									return true;
								}
								return true;
							}
						}
					}
				},
				subTableTypeValue2: {
					validators: {
						notEmpty: {
							message: '不能为空'
						},
						regexp: {
							regexp: /^[+]{0,1}(\d+)$/,
							message: '请输入正整数'
						},
						callback: {
							message: '起始值必须小于结束值',
							callback: function(value, validator, $field) {
								var otherbox = validator.getFieldElements("subTableTypeValue1").val();
								if (parseInt(otherbox)>parseInt(value)) {
									validator.updateStatus("subTableTypeValue1", validator.STATUS_VALID, 'callback');
									return false;
								}else if( parseInt(otherbox)<parseInt(value) ){
									validator.updateStatus("subTableTypeValue1", 'VALID', 'callback');
									return true;
								}
								return true;
							}
						}
					}
				},
				fields: {
					validators: {
						notEmpty: {
							message: '请选择字段'
						}
					}
				},
				types: {
					validators: {
						notEmpty: {
							message: '请选择类型'
						}
					}
				},
				sqlSubTreasuryDivision: {
					validators: {
						notEmpty: {
							message: '请选择数据源'
						}
					}
				}
			}
		});
	}

	//字库校验
	function childFormValidator( curChildForm ){
		curChildForm.bootstrapValidator({
			message: 'This value is not valid',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			excluded:[":disabled"],
			fields: {
				typeValue1: {
					validators: {
						notEmpty: {
							message: '不能为空'
						},
						regexp: {
							regexp: /^[+]{0,1}(\d+)$/,
							message: '请输入正整数'
						},
						callback: {
							message: '起始值必须小于结束值',
							callback: function(value, validator, $field) {
								var otherbox = validator.getFieldElements("typeValue2").val();
								if (parseInt(otherbox)<parseInt(value)) {
									validator.updateStatus("typeValue2", validator.STATUS_VALID, 'callback');
									return false;
								}else if( parseInt(value)<parseInt(otherbox) ){
									validator.updateStatus("typeValue2", "VALID", 'callback');
									return true;
								}
								return true;
							}
						}
					}
				},
				typeValue2: {
					validators: {
						notEmpty: {
							message: '不能为空'
						},
						regexp: {
							regexp: /^[+]{0,1}(\d+)$/,
							message: '请输入正整数'
						},
						callback: {
							message: '起始值必须小于结束值',
							callback: function(value, validator, $field) {
								var otherbox = validator.getFieldElements("typeValue1").val();
								if (parseInt(otherbox)>parseInt(value)) {
									validator.updateStatus("typeValue1", validator.STATUS_VALID, 'callback');
									return false;
								}else if( parseInt(otherbox)<parseInt(value) ){
									validator.updateStatus("typeValue1", 'VALID', 'callback');
									return true;
								}
								return true;
							}
						}
					}
				}
			}
		});
	}

	//表格增加一行
	$("#column-add").click(function (e) {
		var resType = $("#resType").val();
		var html =
			'<tr>\n' +
			'  <td><input type="checkbox" class="inner-checkbox"></td>\n' +
			'  <td style="width: 120px;">\n' +
			'    <span class="inner-text"></span>\n' +
			'    <input type="text" name="fieldName" class="form-control inner-input" autocomplete="off">\n' +
			'  </td>\n' +
			'  <td style="width: 120px;">\n' +
			'    <span class="inner-text"></span>\n' +
			'    <input type="text" name="fieldSqlName" class="form-control inner-input" autocomplete="off">\n' +
			'  </td>\n' +
			'  <td style="width: 120px;">\n' +
			'    <span class="inner-text"></span>\n' +
			'    <select name="fieldType" class="form-control inner-input">' ;

		if( resType == "1" ) {
			//mysql
			html +=
				'                                                <option value="6">int</option>\n' +
				'                                                <option value="2">varchar</option>\n' +
				'                                                <option value="1">float</option>\n' +
				'                                                <option value="7">bigint</option>\n' +
				'                                                <option value="9">tinyint</option>\n' +
				'                                                <option value="3">datetime</option>\n' +
				'                                                <option value="4">longtext</option>\n' +
				'                                                <option value="5">blob</option>\n' +
				'                                                <option value="10" disabled>其它</option>\n' ;
		}
		else if( resType == "2" ) {
			//oracle
			html +=
				'                                                <option value="2">varchar2</option>\n' +
				'                                                <option value="1">float</option>\n' +
				'                                                <option value="4">blob</option>\n' +
				'                                                <option value="6">number</option>\n' +
				'                                                <option value="3">date</option>\n' +
				'                                                <option value="10" disabled>其它</option>\n' ;
		}
		else if( resType == "3" ) {
			//db2
			html +=
				'                                                <option value="2">varchar</option>\n' +
				'                                                <option value="1">decimal</option>\n' +
				'                                                <option value="3">timestamp</option>\n' +
				'                                                <option value="4">clob</option>\n' +
				'                                                <option value="5">blob</option>\n' +
				'                                                <option value="6">int</option>\n' +
				'                                                <option value="7">bigint</option>\n' +
				'                                                <option value="9">boolean</option>\n' +
				'                                                <option value="10" disabled>其它</option>\n' ;
		}
		else if( resType == "5" ) {
			//sqlserver
			html +=
				'                                                <option value="2">varchar</option>\n' +
				'                                                <option value="1">float</option>\n' +
				'                                                <option value="3">datetime</option>\n' +
				'                                                <option value="4">nvarchar(max)</option>\n' +
				'                                                <option value="5">varbinary(max)</option>\n' +
				'                                                <option value="6">int</option>\n' +
				'                                                <option value="7">bigint</option>\n' +
				'                                                <option value="9">bit</option>\n' +
				'                                                <option value="10" disabled>其它</option>\n' ;
		}

		html +=
			'    </select>\n' +
			'  </td>\n' +
			'  <td style="width: 120px;">\n' +
			'    <span class="inner-text"></span>\n' +
			'    <input type="text" name="fieldLen" class="form-control inner-input" autocomplete="off">\n' +
			'  </td>\n' +
			'  <td style="width: 120px;">\n' +
			'    <span class="inner-text"></span>\n' +
			'    <input type="text" name="fieldDigit" class="form-control inner-input" autocomplete="off">\n' +
			'  </td>\n' ;

		// var preFieldKey = $("#inner-table").find("input[name='FieldKey']");
		// var isHasPrekey = false;
		// if(preFieldKey.length > 0) {
		// 	for( var l = 0; l < preFieldKey.length; l++ ) {
		// 		if( $(preFieldKey[l]).is(':checked') ) {
		// 			isHasPrekey = true;
		// 			break;
		// 		}
		// 	}
		// }

		// if( isHasPrekey ){
		// 	html +=
		// 		'  <td><input type="checkbox" class="inner-checkbox" name="FieldKey" value="1" disabled></td>\n' ;
		// }else{
		// 	html +=
		// 		'  <td><input type="checkbox" class="inner-checkbox" name="FieldKey" value="1"></td>\n' ;
		// }

		html +=
			'  <td><input type="checkbox" class="inner-checkbox" name="FieldKey" value="1"></td>\n' ;

		html +=
			'  <td><input type="checkbox" class="inner-checkbox" name="sortIndex" value="1"></td>\n' +
			'  <td><input type="checkbox" class="inner-checkbox" name="isNull" value="0"></td>\n' +
			'  <td style="width: 120px;">\n' +
			'    <span class="inner-text"></span>\n' +
			'    <input type="text" name="fieldDigit" class="form-control inner-input" autocomplete="off">\n' +
			'  </td>\n' +
			'</tr>';
		$("#inner-table").find("tbody").append(html);
		spanSelectBindClick();
	});

	//表头checkBox的change事件
	$("#title-checkbox").change(function (e) {
		var trs = $("#inner-table").find("tbody > tr");
		if( $(this).get(0).checked ){
			if( trs && trs.length > 0 ){
				for( var i = 0; i < trs.length; i++ ){
					$(trs[i]).children("td").eq(0).find("input").prop("checked",true);
				}
			}
		} else {
			if( trs && trs.length > 0 ){
				for( var i = 0; i < trs.length; i++ ){
					$(trs[i]).children("td").eq(0).find("input").prop("checked",false);
				}
			}
		}
	});

	//表格删除按钮事件
	$("#column-delete").click(function (e) {
		var trs = $("#inner-table").find("tbody > tr");
		if( trs && trs.length > 0 ){
			for( var j = 0; j < trs.length; j++ ){
				if( $(trs[j]).children("td").eq(0).find("input").get(0).checked ){
					var trSqlName = $(trs[j]).find("input[name='fieldSqlName']").val();
					var shapeType = $("#shapeType-option").val();
					if( shapeType === "2" ){
						//分表或者分区
						if( $(".sub-meter").find("select[name='fields']").val() === trSqlName  ){
							$.message({
								message:'该字段已被使用，无法删除！',
								type:'error'
							});
							return;
						}
					}else if( shapeType === "3" ){
						if( $(".sub-treasury").find("select[name='fields']").val() === trSqlName  ){
							$.message({
								message:'该字段已被使用，无法删除！',
								type:'error'
							});
							return;
						}
					}else{
						$(trs[j]).remove();
					} 
				}
			}
		}
	});
	

	//分表中的类型change事件
  $(".sub-meter").find("select[name='types']").change(function (e) {
	  var curType = $(this).val();
	  var typeChangeContent = $(".sub-meter").find(".typeChange-content");
	  if( curType === "1" ){
		  //date
		  var dateHtml =
			  '<div class="form-group">\n' +
			  '  <label class="col-sm-2 control-label">date类型<span style="color:red;">*</span></label>\n' +
			  '  <div class="col-sm-10">\n' +
			  '    <select name="dates" class="form-control">\n' +
			  '      <option></option>\n' +
			  '      <option value="YYYY">YYYY</option>\n' +
			  '      <option value="YYYY-MM">YYYY-MM</option>\n' +
			  '      <option value="YYYY-MM-DD">YYYY-MM-DD</option>\n' +
			  '    </select>\n' +
			  '  </div>\n' +
			  '</div>';
		  typeChangeContent.empty();
		  typeChangeContent.append(dateHtml);
	  } else if( curType === "2" ){
		  //string
		  var stringHtml =
			  '<div class="form-group">\n' +
			  '  <label class="col-sm-2 control-label">字符串截取起始值<span style="color:red;">*</span></label>\n' +
			  '  <div class="col-sm-3">\n' +
			  '    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="开始">' +
			  '  </div>\n' +
			  '  <div class="col-sm-1">\n' +
			  '   <span class="seLine"></span>' +
			  '  </div>\n' +
			  '  <div class="col-sm-3">\n' +
			  '   <input type="text" name="subTableTypeValue2" class="form-control" placeholder="结束">' +
			  '  </div>\n' +
			  '</div>';
		  typeChangeContent.empty();
		  typeChangeContent.append(stringHtml);
	  } else if( curType === "3" ){
		  //数值
		  var numHtml =
			  '<div class="form-group">\n' +
			  '  <label class="col-sm-2 control-label">数值<span style="color:red;">*</span></label>\n' +
			  '  <div class="col-sm-10">\n' +
			  '    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="数值">'+
			  '  </div>\n' +
			  '</div>';
		  typeChangeContent.empty();
		  typeChangeContent.append(numHtml);
	  }

	  //添加校验
	  if( $("#sub_form").data('bootstrapValidator') ){
		try{
			$("#sub_form").data('bootstrapValidator').destroy();
		}catch( message ){
			
		}
		
		$('#sub_form').data('bootstrapValidator', null);
	  }
	  meterValidator();
  });
  //分表中的字段刷新按钮事件
  $(".sub-meter").find(".refresh-btn").click(function (e) {
	  fieldsRefresh("meter");
  });

  //分库中的类型change事件
  $(".sub-treasury").find("select[name='types']").change(function (e) {
	  var curType = $(this).val();
	  var typeChangeContent = $(".sub-treasury").find(".typeChange-content");
	  if( curType === "3" ){
		  //date
		  var dateHtml =
			  '<div class="form-group">\n' +
			  '  <label class="col-sm-2 control-label">date类型<span style="color:red;">*</span></label>\n' +
			  '  <div class="col-sm-10">\n' +
			  '    <select name="dates" class="form-control">\n' +
			  '      <option></option>\n' +
			  '      <option value="YYYY">YYYY</option>\n' +
			  '      <option value="YYYY-MM">YYYY-MM</option>\n' +
			  '      <option value="YYYY-MM-DD">YYYY-MM-DD</option>\n' +
			  '    </select>\n' +
			  '  </div>\n' +
			  '</div>';
		  typeChangeContent.empty();
		  typeChangeContent.append(dateHtml);
	  } else if( curType === "1" ){
		  //string
		  var stringHtml =
			  '<div class="form-group">\n' +
			  '  <label class="col-sm-2 control-label">字符串截取起始值<span style="color:red;">*</span></label>\n' +
			  '  <div class="col-sm-3">\n' +
			  '    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="开始">' +
			  '  </div>\n' +
			  '  <div class="col-sm-1">\n' +
			  '   <span class="seLine"></span>' +
			  '  </div>\n' +
			  '  <div class="col-sm-3">\n' +
			  '   <input type="text" name="subTableTypeValue2" class="form-control" placeholder="结束">' +
			  '  </div>\n' +
			  '</div>';
		  typeChangeContent.empty();
		  typeChangeContent.append(stringHtml);
	  } else if( curType === "2" ){
		  //数值
		  var numHtml =
			  '<div class="form-group">\n' +
			  '  <label class="col-sm-2 control-label">数值<span style="color:red;">*</span></label>\n' +
			  '  <div class="col-sm-10">\n' +
			  '    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="数值">'+
			  '  </div>\n' +
			  '</div>';
		  typeChangeContent.empty();
		  typeChangeContent.append(numHtml);
	  }
	  var childFormGroups = $("#dataInputs").find("form");
	  if( childFormGroups.length > 0 ){
		  for( var k = 0; k < childFormGroups.length; k++ ){
			  var name = $(childFormGroups[k]).find("label").text();
			  if( curType === "1" || curType === "3" ){
				  $(childFormGroups[k]).empty();
				  $(childFormGroups[k]).append(
					  '<div class="form-group">'+
					  '  <label class="col-sm-2 control-label">'+name+'<span style="color:red;">*</span></label>\n' +
					  '  <div class="col-sm-10">\n' +
					  '    <input type="text" name="typeValue1" class="form-control">\n' +
					  '  </div>\n'+
					  '</div>'
				  );
			  } else if( curType === "2" ){
				  $(childFormGroups[k]).empty();
				  $(childFormGroups[k]).append(
					  '<div class="form-group">'+
					  '  <label class="col-sm-2 control-label">'+name+'<span style="color:red;">*</span></label>\n' +
					  '  <div class="col-sm-3">\n' +
					  '    <input type="text" name="typeValue1" class="form-control" placeholder="开始">' +
					  '  </div>\n' +
					  '  <div class="col-sm-1">\n' +
					  '   <span class="seLine"></span>' +
					  '  </div>\n' +
					  '  <div class="col-sm-3">\n' +
					  '   <input type="text" name="typeValue2" class="form-control" placeholder="结束">' +
					  '  </div>\n'+
					  '</div>'
				  );
			  }
			  //添加字库校验
			  if( $(childFormGroups[k]).data('bootstrapValidator') ){
				try{
					$(childFormGroups[k]).data('bootstrapValidator').destroy();
				}catch( message ){
					
				}
				
				$(childFormGroups[k]).data('bootstrapValidator', null);
			  }
			  childFormValidator($(childFormGroups[k]));
		  }
	  }

	  //添加校验
	  if( $("#sub_form").data('bootstrapValidator') ){
		try{
			$("#sub_form").data('bootstrapValidator').destroy();
		}catch( message ){
			
		}
		
		$('#sub_form').data('bootstrapValidator', null);
	  }
	  treasuryValidator();
  });
	//分库中的数据源select的change事件
  $("#sqlSubTreasury").change(function (e) {
	  var val = $(this).val();
	  var  types_ = $(".sub-treasury").find("select[name='types']").val();

	  if( val && val.length > 0 ){
		  var childFormGroups = $("#dataInputs").find("form");
		  if( childFormGroups.length > 0 ){
			  for( var j = 0; j < childFormGroups.length; j++ ){
				  var curId = $(childFormGroups[j]).attr("id");
				  if( !isInArray(curId, val) ){
					  $(childFormGroups[j]).remove();
				  }
			  }
		  }

		  for( var i = 0; i < val.length; i++ ){
			  var name = $(this).find("option[value="+val[i]+"]").text();
			  if( $( "#"+val[i] ).length > 0 ){

			  }else{
				  if( types_ === "2" ){
					  var html =
						  '<form class="form-horizontal" id="'+val[i]+'">'+
						  '  <div class="form-group">\n' +
						  '    <label class="col-sm-2 control-label">'+name+'<span style="color:red;">*</span></label>\n' +
						  '    <div class="col-sm-3">\n' +
						  '      <input type="text" name="typeValue1" class="form-control" placeholder="开始">' +
						  '    </div>\n' +
						  '    <div class="col-sm-1">\n' +
						  '     <span class="seLine"></span>' +
						  '    </div>\n' +
						  '    <div class="col-sm-3">\n' +
						  '     <input type="text" name="typeValue2" class="form-control" placeholder="结束">' +
						  '    </div>\n' +
						  '  </div>'+
						  '</form>';
					  $("#dataInputs").append(html);
				  } else {
					  var html =
						  '<form class="form-horizontal" id="'+val[i]+'">'+
						  '	 <div class="form-group">\n' +
						  '	   <label class="col-sm-2 control-label">'+name+'<span style="color:red;">*</span></label>\n' +
						  '	   <div class="col-sm-10">\n' +
						  '	     <input name="typeValue1" type="text" class="form-control">\n' +
						  '	   </div>\n' +
						  '	 </div>'+
						  '</form>';
					  $("#dataInputs").append(html);
				  }
			  }
		  }
	  } else {
		  $("#dataInputs").empty();
	  }

	  //添加字库校验
	  var aliveChildForm = $("#dataInputs").find("form");
	  if( aliveChildForm && aliveChildForm.length > 0 ){
		for( var s = 0; s < aliveChildForm.length; s++ ){
			if( $(aliveChildForm[s]).data('bootstrapValidator') ){
				try{
					$(aliveChildForm[s]).data('bootstrapValidator').destroy();
				}catch( message ){
					
				}
				$(aliveChildForm[s]).data('bootstrapValidator', null);
			}
			childFormValidator( $(aliveChildForm[s]) );
		}
	  }
  });
	//分库中的字段刷新按钮事件
  $(".sub-treasury").find(".refresh-btn").click(function (e) {
	  fieldsRefresh("treasury");
	  var treasuryValidate = $("#sub_form").data('bootstrapValidator');
	  treasuryValidate.validate();
  });
	//分库中的数据源刷新按钮事件
  $(".sub-treasury").find(".refresh-source-btn").click(function (e) {
	  dataSouceRefresh();
  });

  $("#cancel-modal").click(function(){
	  $("#confirmCloseModal").modal("hide");
  });

	//操作类型select的change事件
	$("#shapeType-option").change(function (e) {
		var val = $(this).val();
		if( val === "1" ){
			//分区
			$(".option-sub").css("display","none");
			$(".sub-area").css("display","block");

			//添加校验
			if( $("#sub_form").data('bootstrapValidator') ){
				try{
					$("#sub_form").data('bootstrapValidator').destroy();
				}catch( message ){
					
				}
				
				$('#sub_form').data('bootstrapValidator', null);
			  }
			areaValidator();
		}
		else if( val === "2" ){
			//分表
			$(".option-sub").css("display","none");
			$(".sub-meter").css("display","block");

			//遍历表中的字段
			fieldsRefresh("meter");

			//添加分表校验
			if( $("#sub_form").data('bootstrapValidator') ){
				try{
					$("#sub_form").data('bootstrapValidator').destroy();
				}catch( message ){
					
				}
				
				$('#sub_form').data('bootstrapValidator', null);
			  }
			meterValidator();
		}
		else if( val === "3" ){
			//分库
			$(".option-sub").css("display","none");
			$(".sub-treasury").css("display","block");

			//遍历表中的字段
			fieldsRefresh("treasury");

			//加载数据源
			dataSouceRefresh();

			//添加分库校验
			if( $("#sub_form").data('bootstrapValidator') ){
				try{
					$("#sub_form").data('bootstrapValidator').destroy();
				}catch( message ){
					
				}
				
				$('#sub_form').data('bootstrapValidator', null);
			  }
			treasuryValidator();
		}
		else {
			$(".option-sub").css("display","none");
		}
	});

	//盒子双击事件
	function showSummerBoxDetail(boxObj) {
		var curBox = boxObj;
		var tableModal = $("#newTableModal");
		var curBoxData = curBox.data("boxDetail");
		if( curBoxData ){
			//编辑

			tableModal.find("input").val("");
			tableModal.find("textarea").val("");
			tableModal.find("select").val("");
			$("#inner-table").find("tbody").empty();
			$("#dataInputs").empty();
			$("#shapeType-option").val("");
			$(".option-sub").css("display","none");
			canvasTab.open($("#basic"));

			tableModal.find(".modal-title").text("编辑表");
			tableModal.find("input[name='tableName']").val(curBoxData.tableName);
			tableModal.find("input[name='tableSqlName']").val(curBoxData.tableSqlName);
			tableModal.find("input[name='tableResume']").val(curBoxData.tableResume);
			tableModal.find("input[name='sortId']").val(curBoxData.sortId);
		
			//回显字段
			var sqlFieldes = curBoxData.sqlFieldes;
			var isHaveShape = ( curBoxData.sqlShape && curBoxData.sqlShape.length >= 0 ) ? true : false; 
			if( isHaveShape ){
				tableModal.find("input[name='tableSqlName']").attr("readonly","readonly");
			}
			if( sqlFieldes && sqlFieldes.length > 0 ){
				for( var i = 0; i < sqlFieldes.length; i++ ){
					var resType = $("#resType").val();
					var html =
						'<tr>\n' +
						'  <div></div><td><input type="checkbox" class="inner-checkbox"><input type="text" name="fieldId" hidden value="'+ sqlFieldes[i].fieldId +'"></td>\n' +
						'  <td style="width: 120px;">\n' +
						'    <span class="inner-text">'+sqlFieldes[i].fieldName+'</span>\n' +
						'    <input type="text" name="fieldName" class="form-control inner-input" autocomplete="off" value="'+sqlFieldes[i].fieldName+'" '+ ( isHaveShape ? "readonly" : "" ) +' '+( resType === "3" ? "readonly" : "" )+'>\n' +
						'  </td>\n' +
						'  <td style="width: 120px;">\n' +
						'    <span class="inner-text">'+sqlFieldes[i].fieldSqlName+'</span>\n' +
						'    <input type="text" name="fieldSqlName" class="form-control inner-input" autocomplete="off" value="'+sqlFieldes[i].fieldSqlName+'" '+ ( isHaveShape ? "readonly" : "" ) +' '+( resType === "3" ? "readonly" : "" )+'>\n' +
						'  </td>\n' +
						'  <td style="width: 120px;">\n' ;

					var fieldType = sqlFieldes[i].fieldType;
					var fieldTypeName = '';
					var fieldDateType = sqlFieldes[i].dateType;

					if( resType === "1" ){
						//mysql
						switch (fieldType) {
							case "1":
								fieldTypeName = "float";
								break;
							case "2":
								fieldTypeName = "varchar";
								break;
							case "3":
								fieldTypeName = "datetime";
								break;
							case "4":
								fieldTypeName = "longtext";
								break;
							case "5":
								fieldTypeName = "blob";
								break;
							case "6":
								fieldTypeName = "int";
								break;
							case "7":
								fieldTypeName = "bigint";
								break;
							case "9":
								fieldTypeName = "tinyint";
								break;
							case "10":
								if( fieldDateType == "real" || fieldDateType == "double" || fieldDateType == "decimal" || fieldDateType == "numeric" ){
									fieldTypeName = "float";
								} else if( fieldDateType == "char" ) {
									fieldTypeName = "varchar";
								} else if( fieldDateType == "date" || fieldDateType == "time" || fieldDateType == "year" || fieldDateType == "timestamp" ){
									fieldTypeName = "datetime";
								} else if( fieldDateType == "tinytext" || fieldDateType == "text" || fieldDateType == "mediumtext" || fieldDateType == "longtext"  ){
									fieldTypeName = "longtext";
								} else if( fieldDateType == "tinyblob" || fieldDateType == "mediumblob" || fieldDateType == "longblob" ) {
									fieldTypeName = "blob";
								} else if( fieldDateType == "smallint" || fieldDateType == "mediumint" || fieldDateType == "int" || fieldDateType == "integer" ){
									fieldTypeName = "int";
								} else if( fieldDateType == "bit" ){
									fieldTypeName = "tinyint";
								} else if( fieldDateType == "set" ){
									fieldTypeName = "set";
								} else if( fieldDateType == "enum" ){
									fieldTypeName = "enum";
								} else {
									fieldTypeName = "其它";
								}
								break;
							default:
								fieldTypeName = "";
						}
					}
					else if( resType === "2" ) {
						//oracle
						switch (fieldType) {
							case "1":
								fieldTypeName = "float";
								break;
							case "2":
								fieldTypeName = "varchar2";
								break;
							case "3":
								fieldTypeName = "date";
								break;
							case "4":
								fieldTypeName = "blob";
								break;
							case "6":
								fieldTypeName = "number";
								break;
							case "10":
								if( fieldDateType == "binary_float" || fieldDateType == "binary_double" || fieldDateType == "double precision" || fieldDateType == "real" ) {
									fieldTypeName = "float";
								}else if( fieldDateType == "char" || fieldDateType == "nchar" || fieldDateType == "varchar2" || fieldDateType == "character" || fieldDateType == "character varying" || fieldDateType == "char varying" || fieldDateType == "national character" || fieldDateType == "national character varying" || fieldDateType == "nchar varying" ) {
									fieldTypeName = "varchar2";
								}else if( fieldDateType == "bfile" || fieldDateType == "clob" ) {
									fieldTypeName = "blob";
								}else if( fieldDateType == "int" || fieldDateType == "integer" || fieldDateType == "smallint" || fieldDateType == "numeric" || fieldDateType == "decimal" ) {
									fieldTypeName = "number";
								}else{
									fieldTypeName = "其它";
								}
								break;
							default:
								fieldTypeName = "";
						}
					}
					else if(  resType === "3" ) {
						//db2
						switch (fieldType) {
							case "1":
								fieldTypeName = "decimal";
								break;
							case "2":
								fieldTypeName = "varchar";
								break;
							case "3":
								fieldTypeName = "timestamp";
								break;
							case "4":
								fieldTypeName = "clob";
								break;
							case "5":
								fieldTypeName = "blob";
								break;
							case "6":
								fieldTypeName = "int";
								break;
							case "7":
								fieldTypeName = "bigint";
								break;
							case "9":
								fieldTypeName = "boolean";
								break;
							case "10":
								fieldTypeName = "其它";
								break;
							default:
								fieldTypeName = "";
						}
					}
					else if( resType === "5" ) {
						//sqlserver
						switch (fieldType) {
							case "1":
								fieldTypeName = "float";
								break;
							case "2":
								fieldTypeName = "varchar";
								break;
							case "3":
								fieldTypeName = "datetime";
								break;
							case "4":
								fieldTypeName = "nvarchar(max)";
								break;
							case "5":
								fieldTypeName = "varbinary(max)";
								break;
							case "6":
								fieldTypeName = "int";
								break;
							case "7":
								fieldTypeName = "bigint";
								break;
							case "9":
								fieldTypeName = "bit";
								break;
							case "10":
								if( fieldDateType == "decimal" || fieldDateType == "numeric" || fieldDateType == "float" || fieldDateType == "money" || fieldDateType == "smallmoney" ){
									fieldTypeName = "real";
								}else if( fieldDateType == "char" || fieldDateType == "nchar" || fieldDateType == "nvarchar" ) {
									fieldTypeName = "varchar";
								}else if( fieldDateType == "time" || fieldDateType == "datetime" || fieldDateType == "datetime2" || fieldDateType == "datetimeoffset" || fieldDateType == "smalldatetime" ){
									fieldTypeName = "date";
								}else if( fieldDateType == "text" || fieldDateType == "ntext" || fieldDateType == "varchar" || fieldDateType == "nvarchar" ){
									fieldTypeName = "nvarchar(max)";
								}else if( fieldDateType == "image" ){
									fieldTypeName = "varbinary(max)";
								}else if( fieldDateType == "bigint" || fieldDateType == "tinyint" || fieldDateType == "smallint" ){
									fieldTypeName = "int";
								}else {
									fieldTypeName = "其它";
								}
								break;
							default:
								fieldTypeName = "";
						}
					}
					
					html +=
						'    <span class="inner-text">'+fieldTypeName+'</span>\n' +
						'    <select name="fieldType" class="form-control inner-input" disabled>' ;

					if( resType === "1" ) {
						//mysql
						html +=
							'<option value="6" '+ ( fieldType === "6" ? 'selected' : '' ) +'>int</option>\n' +
							'<option value="2" '+ ( fieldType === "2" ? 'selected' : '' ) +'>varchar</option>\n' +
							'<option value="1" '+ ( fieldType === "1" ? 'selected' : '' ) +'>float</option>\n' +
							'<option value="7" '+ ( fieldType === "7" ? 'selected' : '' ) +'>bigint</option>\n' +
							'<option value="9" '+ ( fieldType === "9" ? 'selected' : '' ) +'>tinyint</option>\n' +
							'<option value="3" '+ ( fieldType === "3" ? 'selected' : '' ) +'>datetime</option>\n' +
							'<option value="4" '+ ( fieldType === "4" ? 'selected' : '' ) +'>longtext</option>\n' +
							'<option value="5" '+ ( fieldType === "5" ? 'selected' : '' ) +'>blob</option>\n' +
							'<option value="10" '+ ( fieldType === "10" ? 'selected' : '' ) +'>其它</option>\n' ;
					}
					else if( resType === "2" ) {
						//oracle
						html +=
							'<option value="1" '+ ( fieldType === "1" ? 'selected' : '' ) +'>float</option>\n' +
							'<option value="2" '+ ( fieldType === "2" ? 'selected' : '' ) +'>varchar2</option>\n' +
							'<option value="3" '+ ( fieldType === "3" ? 'selected' : '' ) +'>date</option>\n' +
							'<option value="4" '+ ( fieldType === "4" ? 'selected' : '' ) +'>blob</option>\n' +
							'<option value="6" '+ ( fieldType === "6" ? 'selected' : '' ) +'>number</option>\n' +
							'<option value="10" '+ ( fieldType === "10" ? 'selected' : '' ) +'>其它</option>\n' ;
					}
					else if( resType === "3" ) {
						//db2
						html +=
							'<option value="1" '+ ( fieldType === "1" ? 'selected' : '' ) +'>decimal</option>\n' +
							'<option value="2" '+ ( fieldType === "2" ? 'selected' : '' ) +'>varchar</option>\n' +
							'<option value="3" '+ ( fieldType === "3" ? 'selected' : '' ) +'>timestamp</option>\n' +
							'<option value="4" '+ ( fieldType === "4" ? 'selected' : '' ) +'>clob</option>\n' +
							'<option value="5" '+ ( fieldType === "5" ? 'selected' : '' ) +'>blob</option>\n' +
							'<option value="6" '+ ( fieldType === "6" ? 'selected' : '' ) +'>int</option>\n' +
							'<option value="7" '+ ( fieldType === "7" ? 'selected' : '' ) +'>bigint</option>\n' +
							'<option value="9" '+ ( fieldType === "9" ? 'selected' : '' ) +'>boolean</option>\n' +
							'<option value="10" '+ ( fieldType === "10" ? 'selected' : '' ) +'>其它</option>\n' ;
					}
					else if( resType === "5" ) {
						//sqlserver
						html +=
							'<option value="1" '+ ( fieldType === "1" ? 'selected' : '' ) +'>float</option>\n' +
							'<option value="2" '+ ( fieldType === "2" ? 'selected' : '' ) +'>varchar</option>\n' +
							'<option value="3" '+ ( fieldType === "3" ? 'selected' : '' ) +'>datetime</option>\n' +
							'<option value="4" '+ ( fieldType === "4" ? 'selected' : '' ) +'>nvarchar(max)</option>\n' +
							'<option value="5" '+ ( fieldType === "5" ? 'selected' : '' ) +'>varbinary(max)</option>\n' +
							'<option value="6" '+ ( fieldType === "6" ? 'selected' : '' ) +'>int</option>\n' +
							'<option value="7" '+ ( fieldType === "7" ? 'selected' : '' ) +'>bigint</option>\n' +
							'<option value="9" '+ ( fieldType === "9" ? 'selected' : '' ) +'>bit</option>\n' +
							'<option value="10" '+ ( fieldType === "10" ? 'selected' : '' ) +'>其它</option>\n' ;
					}

					html +=
						'    </select>\n' +
						'  </td>\n' +
						'  <td style="width: 120px;">\n' +
						'    <span class="inner-text">'+( sqlFieldes[i].fieldLen !== null ? sqlFieldes[i].fieldLen : "" )+'</span>\n' +
						'    <input type="text" name="fieldLen" class="form-control inner-input" autocomplete="off" value="'+( sqlFieldes[i].fieldLen !== null ? sqlFieldes[i].fieldLen : "" )+'" '+ ( isHaveShape ? "readonly" : "" ) +' '+( resType === "3" ? "readonly" : "" )+'>\n' +
						'  </td>\n' +
						'  <td style="width: 120px;">\n' +
						'    <span class="inner-text">'+( sqlFieldes[i].fieldDigit !== null ? sqlFieldes[i].fieldDigit : "" )+'</span>\n' +
						'    <input type="text" name="fieldDigit" class="form-control inner-input" autocomplete="off" value="'+( sqlFieldes[i].fieldDigit !== null ? sqlFieldes[i].fieldDigit : "" )+'" '+ ( isHaveShape ? "readonly" : "" ) +' '+( resType === "3" ? "readonly" : "" )+'>\n' +
						'  </td>\n' ;
					// var isHaveFieldKey = false;
					//
					// for( var j = 0; j < sqlFieldes.length; j++ ) {
					// 	if( sqlFieldes[j].fieldKey === "1" ) {
					// 		isHaveFieldKey = true;
					// 		break;
					// 	}
					// }

					// html +=
					// 	'  <td><input type="checkbox" class="inner-checkbox" name="FieldKey" value="1" '+ ( sqlFieldes[i].fieldKey === "1" ? 'checked' : '' ) + '  '+ ( sqlFieldes[i].fieldKey === "0" ?  isHaveFieldKey ? 'disabled' : '' : '' ) +'></td>\n' ;
					html +=
						'  <td><input type="checkbox" class="inner-checkbox" name="FieldKey" value="1" '+ ( sqlFieldes[i].fieldKey === "1" ? 'checked ' : '' ) + ' '+ ( isHaveShape ? "disabled" : "" ) +' '+( resType === "3" ? "disabled" : "" )+'></td>\n' ;
					html +=
						'  <td><input type="checkbox" class="inner-checkbox" name="sortIndex" value="1" '+ ( sqlFieldes[i].sortIndex === "1" ? 'checked disabled' : '' ) +' '+( resType === "3" ? "disabled" : "" )+'></td>\n' +
						'  <td><input type="checkbox" class="inner-checkbox" name="isNull" value="1" '+ ( sqlFieldes[i]['isNull'] === "0" ? 'checked disabled' : '' ) +' '+( resType === "3" ? "disabled" : "" )+'></td>\n' +
						'  <td style="width: 120px;">\n' +
						'    <span class="inner-text">'+( sqlFieldes[i].fieldResume === null ? '' : sqlFieldes[i].fieldResume )+'</span>\n' +
						'    <input type="text" name="fieldDigit" class="form-control inner-input" autocomplete="off" value="'+( sqlFieldes[i].fieldResume === null ? '' : sqlFieldes[i].fieldResume )+'" '+( resType === "3" ? "readonly" : "" )+'>\n' +
						'  </td>\n' +
						'</tr>';
					$("#inner-table").find("tbody").append(html);
				}
			}
			spanSelectBindClick();

			//回显分区表库
			var sqlShape = curBoxData.sqlShape ? curBoxData.sqlShape[0] : null;
			if( sqlShape ){
				$("#shapeType-option").removeAttr("disabled");
				if( sqlShape.shapeType === "1" ){
					//分区
					var subArea = $(".sub-area");
					$("#shapeType-option").val("1");
					subArea.find("textarea[name='partitionValue']").val(sqlShape.partitionValue ? sqlShape.partitionValue : '');
					subArea.find("input[name='state'][value='"+sqlShape.state+"']").attr("checked",true);

					$(".option-sub").css("display","none");
					subArea.css("display","block");
					$(".no-allow-touch").css("display","none");
				}
				else if( sqlShape.shapeType === "2" ){
					//分表
					var subMeter = $(".sub-meter");
					var typeChangeContent = subMeter.find(".typeChange-content");
					var sqlSubTable = sqlShape.sqlSubTable;
					if( sqlSubTable ){
						//遍历表中的字段
						fieldsRefresh("meter", sqlSubTable.fieldSqlName);
						subMeter.find("select[name='types']").val(sqlSubTable.subTableType);
						if( sqlSubTable.subTableType === "1" ){
							//date
							var dateHtml =
								'<div class="form-group">\n' +
								'  <label class="col-sm-2 control-label">date类型</label>\n' +
								'  <div class="col-sm-10">\n' +
								'    <select name="dates" class="form-control">\n' +
								'      <option></option>\n' +
								'      <option value="YYYY" '+( sqlSubTable.subTableTypeValue1 === "YYYY" ? "selected" : "" )+'>YYYY</option>\n' +
								'      <option value="YYYY-MM" '+( sqlSubTable.subTableTypeValue1 === "YYYY-MM" ? "selected" : "" )+'>YYYY-MM</option>\n' +
								'      <option value="YYYY-MM-DD" '+( sqlSubTable.subTableTypeValue1 === "YYYY-MM-DD" ? "selected" : "" )+'>YYYY-MM-DD</option>\n' +
								'    </select>\n' +
								'  </div>\n' +
								'</div>';
							typeChangeContent.empty();
							typeChangeContent.append(dateHtml);
						}
						else if( sqlSubTable.subTableType === "2" ){
							//string
							var stringHtml =
								'<div class="form-group">\n' +
								'  <label class="col-sm-2 control-label">字符串截取起始值</label>\n' +
								'  <div class="col-sm-3">\n' +
								'    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="开始" value="'+( sqlSubTable.subTableTypeValue1 ? sqlSubTable.subTableTypeValue1 : "" )+'">' +
								'  </div>\n' +
								'  <div class="col-sm-1">\n' +
								'   <span class="seLine"></span>' +
								'  </div>\n' +
								'  <div class="col-sm-3">\n' +
								'   <input type="text" name="subTableTypeValue2" class="form-control" placeholder="结束" value="'+( sqlSubTable.subTableTypeValue2 ? sqlSubTable.subTableTypeValue2 : "" )+'">' +
								'  </div>\n' +
								'</div>';
							typeChangeContent.empty();
							typeChangeContent.append(stringHtml);
						}
						else if( sqlSubTable.subTableType === "3" ){
							//数值
							var numHtml =
								'<div class="form-group">\n' +
								'  <label class="col-sm-2 control-label">数值</label>\n' +
								'  <div class="col-sm-10">\n' +
								'    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="数值" value="'+( sqlSubTable.subTableTypeValue1 ? sqlSubTable.subTableTypeValue1 : "" )+'">'+
								'  </div>\n' +
								'</div>';
							typeChangeContent.empty();
							typeChangeContent.append(numHtml);
						}
					}else {
						//遍历表中的字段
						fieldsRefresh("meter");
					}

					$("#shapeType-option").val("2");
					$(".option-sub").css("display","none");
					$(".sub-meter").css("display","block");
					$(".no-allow-touch").css("display","block");
				}
				else if( sqlShape.shapeType === "3" ){
					//分库
					var subTreasury = $(".sub-treasury");
					var typeChangeContent = subTreasury.find(".typeChange-content");
					var sqlSubTreasury = sqlShape.sqlSubTreasury;
					if( sqlSubTreasury ){
						var sqlSubTreasuryDivision = sqlSubTreasury.sqlSubTreasuryDivision; //分库list
						var subTreasuryType = sqlSubTreasury.subTreasuryType;
						if( subTreasuryType === "1" ){
							//string
							var stringHtml =
								'<div class="form-group">\n' +
								'  <label class="col-sm-2 control-label">字符串截取起始值</label>\n' +
								'  <div class="col-sm-3">\n' +
								'    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="开始" value="'+ ( sqlSubTreasury.typeValue1 ? sqlSubTreasury.typeValue1 : "" ) +'">' +
								'  </div>\n' +
								'  <div class="col-sm-1">\n' +
								'   <span class="seLine"></span>' +
								'  </div>\n' +
								'  <div class="col-sm-3">\n' +
								'   <input type="text" name="subTableTypeValue2" class="form-control" placeholder="结束" value="'+ ( sqlSubTreasury.typeValue2 ? sqlSubTreasury.typeValue2 : "" ) +'">' +
								'  </div>\n' +
								'</div>';
							typeChangeContent.empty();
							typeChangeContent.append(stringHtml);
						}
						else if( subTreasuryType === "2" ){
							//数值
							var numHtml =
								'<div class="form-group">\n' +
								'  <label class="col-sm-2 control-label">数值</label>\n' +
								'  <div class="col-sm-10">\n' +
								'    <input type="text" name="subTableTypeValue1" class="form-control" placeholder="数值" value="'+ ( sqlSubTreasury.typeValue1 ? sqlSubTreasury.typeValue1 : "" ) +'">'+
								'  </div>\n' +
								'</div>';
							typeChangeContent.empty();
							typeChangeContent.append(numHtml);
						}
						else if( subTreasuryType === "3" ){
							//date
							var dateHtml =
								'<div class="form-group">\n' +
								'  <label class="col-sm-2 control-label">date类型</label>\n' +
								'  <div class="col-sm-10">\n' +
								'    <select name="dates" class="form-control">\n' +
								'      <option></option>\n' +
								'      <option value="YYYY" '+( sqlSubTreasury.typeValue1 === "YYYY" ? "selected" : "" )+'>YYYY</option>\n' +
								'      <option value="YYYY-MM" '+( sqlSubTreasury.typeValue1 === "YYYY-MM" ? "selected" : "" )+'>YYYY-MM</option>\n' +
								'      <option value="YYYY-MM-DD" '+( sqlSubTreasury.typeValue1 === "YYYY-MM-DD" ? "selected" : "" )+'>YYYY-MM-DD</option>\n' +
								'    </select>\n' +
								'  </div>\n' +
								'</div>';
							typeChangeContent.empty();
							typeChangeContent.append(dateHtml);
						}
						//遍历表中的字段
						fieldsRefresh("treasury", sqlSubTreasury.fieldSqlName);

						//加载数据源
						dataSouceRefresh(sqlSubTreasuryDivision, subTreasuryType);

						subTreasury.find("select[name='types']").val(subTreasuryType);
					}

					//分库
					$("#shapeType-option").val("3");
					$(".option-sub").css("display","none");
					$(".sub-treasury").css("display","block");
					$(".no-allow-touch").css("display","block");
				}
				else {
					$("#shapeType-option").attr("disabled","disabled");
				}
			}else {
				$("#shapeType-option").attr("disabled","disabled");
			}
		}else{
			//保存
			tableModal.find(".modal-title").text("新增表");
			tableModal.find("input").val("");
			tableModal.find("textarea").val("");
			tableModal.find("select").val("");
			$("#inner-table").find("tbody").empty();
			$("#dataInputs").empty();
			$(".typeChange-content").empty();
			$("#shapeType-option").val("");
			$(".option-sub").css("display","none");
			$(".no-allow-touch").css("display","none");
			$("#shapeType-option").removeAttr("disabled");
			canvasTab.open($("#basic"));
		}

		$("#newTableModal").modal("show");
	}

	//调用接口 获取该数据源下的所有表类型及表类型下的所有表
	$.ajax({
		url: curContext + '/v1/sqlModel/show/dataRes',
		data: { "dataResId": dataResId },
		success: function( result ) {
			if( result.code == "000000" ) {
				loadNewTree( result );

				dataResTypeId = result.sqlDataRes.dataResTypeId;
				$.ajax({
					url: curContext + '/v1/sqlModel/manage/dataSourceType',
					data:{ "dataResTypeId": dataResTypeId },
					success:function(result){
						if( result.dataResTypeId ){
							$("#resType").val( result.resType );
						}
					}
				});
			}
		}
	});

	//查询数据源下已存在的所有表名
	$.ajax({
		url: curContext + '/v1/sqlModel/checkTableName',
		data:{ "dataResId": dataResId },
		success: function( result ) {
			tableNames = result;
		}
	});

  $(function () {
	  $('[data-toggle="tooltip"]').tooltip();
  });


