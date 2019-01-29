
var curTypeForeignKeys = [];
var curTableTypeId = 0;
/*
  * 回显所有的外键
  * */
function loadFoeignkeys() {
	var result = curTypeForeignKeys;
	var powerCanvas = document.getElementById("power-canvas");
	var canvasCtx = powerCanvas.getContext("2d");
	if( result ){
		for( var i = 0; i < result.length; i++ ) {
			var curLine = null;
			var lineId = getuuid(16,16);

			if( document.getElementById(result[i].mainTable) == null || document.getElementById(result[i].joinTable) == null ){
				continue;
			}

			$("#container").append(
				"<div id='"+ lineId +"' class='line_box_container' style='position: absolute;'>" +
				"<canvas></canvas>" +
				"</div>"
			);

			curLine = $("#" + lineId);

			var fromTable = $("#" + result[i].joinTable );
			var fromField = result[i].joinField;
			var toTable = $("#" + result[i].mainTable );
			var toField = result[i].mainField;
			var fromIndex = 0;
			var toIndex = 0;
			var fromX = 0;
			var fromY = 0;
			var toX = 0;
			var toY = 0;

			curLine.attr("foreignKeyId", result[i].foreignKeyId);
			curLine.data("foreignKey",result[i]);

			var fromTableFields = fromTable.find(".summer-column li");
			for( var j = 0; j < fromTableFields.length; j++ ){
				if( $(fromTableFields[j]).find(".column-inner-name").text() == fromField ){
					// fromLi = $(fromTableFields[j]);
					fromIndex = j;
				}
			}

			var toTableFields = toTable.find(".summer-column li");
			for( var k = 0; k < toTableFields.length; k++ ){
				if( $(toTableFields[k]).find(".column-inner-name").text() == toField ){
					// toLi = $(toTableFields[k]);
					toIndex = k;
				}
			}

			//判断出发点为向左还是向右
			if( toTable.position().left > (fromTable.position().left+fromTable.width()/2) ){
				//向右
				fromX = fromTable.position().left + fromTable.width();
				toX = toTable.position().left
			}else {
				//向左
				fromX = fromTable.position().left;
				toX = toTable.position().left + toTable.width();
			}

			fromY = ( fromIndex + 1 ) * 30 - 15 + 40 + fromTable.position().top;
			toY = ( toIndex + 1 ) * 30 - 15 + 40 + toTable.position().top;

			drawArrow( canvasCtx,
				fromX,
				fromY,
				toX,
				toY,
				fromTable,
				toTable,
				curLine,
				fromIndex,
				toIndex
			)
		}
		$("#loading").fadeOut();
	}
	// $.ajax({
	// 	url: curContext + "/v1/sqlModel/findSqlTableForeignkeyes",
	// 	success: function (result) {
	//
	// 	}
	// });
}
//回显盒子
function loadPowerBoxs(tableTypeId) {
	var container = $("#container");
	container.find(">div").remove();
	$.ajax({
		url: curContext + '/v1/sqlModel/findSqlTableByTableTypeId',
		data: {"tableTypeId": tableTypeId},
		success: function (result) {
			var sqlTable = result.sqlTables;
			curTypeForeignKeys = result.sqlTableForeignkeyes;
			if( sqlTable.length > 0 ) {
				var sqlTables = sqlTable.concat(result.sqlTableIdNew);
				
				var arr4 = [];

				for( var j = 0, len = sqlTables.length; j < len; j+=4 ){
					arr4.push(sqlTables.slice(j, j+4));
				}

				for( var k = 0, len = arr4.length; k < len; k++ ){

					for( var h = 0, lon = arr4[k].length; h < lon; h++ ){

						var html  = '';
						var curObj = arr4[k][h];
						var tableTop = curObj.tableY;
						var tableLeft = curObj.tableX;

						if( tableTop == 0 && tableLeft == 0 ){
							tableTop = tableTop + 30;

							//开始第二行 第三行...
							//完美
							if( k > 0 ){
								var preArr4 = arr4[k-1];
								var preObj = $("#"+preArr4[h].tableId);
								if( preArr4[h] ){
									tableTop = preObj.height() + preObj.position().top + 30;
								}
							}

							tableLeft = 300 * h + 30;
						}

						if( curObj.tableTypeId == tableTypeId ) {
							html += '<div id="' + curObj.tableId +'" class="power-class-box" table-id="'+ curObj.tableId +'" style="top:'+tableTop+'px; left:'+tableLeft+'px;"  onmousedown="classBoxMousedown(this, event)" >\n' +
								'                    <div class="class-box-title">'+ curObj.tableName +'</div>\n' +
								'                    <ul class="box-ul">\n' +
								'                        <li>\n' +
								'                            <span class="box-li-item" style="width: 15px;"></span>\n' +
								'                            <span class="box-li-item" style="width: 40px;"></span>\n' +
								'                            <span class="box-li-item" style="width: 100px;">名称</span>\n' +
								'                            <span class="box-li-item" style="width: 80px;">类型</span>\n' +
								'                            <span class="box-li-item" style="width: 15px;"></span>\n' +
								'                        </li>\n' +
								'                    </ul>\n' +
								'                    <ul class="box-ul box-ul-list">\n' ;
						}
						else {
							html += '<div id="' + curObj.tableId +'" class="power-class-box other-type" table-id="'+ curObj.tableId +'" style="top:'+tableTop+'px; left:'+tableLeft+'px;"  onmousedown="classBoxMousedown(this, event)" >\n' +
								'                    <div class="class-box-title">'+ curObj.tableName +'</div>\n' +
								'                    <ul class="box-ul">\n' +
								'                        <li>\n' +
								'                            <span class="box-li-item" style="width: 15px;"></span>\n' +
								'                            <span class="box-li-item" style="width: 40px;"></span>\n' +
								'                            <span class="box-li-item" style="width: 100px;">名称</span>\n' +
								'                            <span class="box-li-item" style="width: 80px;">类型</span>\n' +
								'                            <span class="box-li-item" style="width: 15px;"></span>\n' +
								'                        </li>\n' +
								'                    </ul>\n' +
								'                    <ul class="box-ul box-ul-list">\n' ;
						}


						if( curObj.sqlFieldes.length > 0 ) {
							var sqlFieldes = curObj.sqlFieldes;
							var resType = $("#resType").val();
							var fieldTypeName = '';
							for (var i = 0, leng = sqlFieldes.length; i < leng; i++) {

								var fieldDateType = sqlFieldes[i].dateType;
								if (resType == "1") {
									//mysql
									switch (sqlFieldes[i].fieldType) {
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
								else if (resType == "2") {
									//oracle
									switch (sqlFieldes[i].fieldType) {
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
								else if (resType == "3") {
									//db2
									switch (sqlFieldes[i].fieldType) {
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
								else if (resType == "5") {
									//sqlserver
									switch (sqlFieldes[i].fieldType) {
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

								if( sqlFieldes[i].fieldKey == "1" ) {
									html += '<li>\n' +
										'    <span class="box-li-item" style="width: 15px;">\n' +
										'        <div class="box-item-circle left-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
										'    </span>\n' +
										'    <span class="box-li-item" style="width: 40px;text-align: center;"><sapn class="fa fa-key primary-key" aria-hidden="true"></sapn></span>\n' +
										'    <span class="box-li-item box-li-name" style="width: 100px;">' + sqlFieldes[i].fieldSqlName + '</span>\n' +
										'    <span class="box-li-item box-li-type" style="width: 80px;">' + fieldTypeName +  '('+ sqlFieldes[i].fieldLen +')' + '</span>\n' +
										'    <span class="box-li-item" style="width: 15px;">\n' +
										'        <div class="box-item-circle right-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
										'    </span>\n' +
										'    <input hidden="hidden" type="text" name="fieldKey" value="1" />' +
										'</li>';
								}
								else {
									html += '<li>\n' +
										'    <span class="box-li-item" style="width: 15px;">\n' +
										'        <div class="box-item-circle left-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
										'    </span>\n' +
										'    <span class="box-li-item" style="width: 40px;text-align: center;"></span>\n' +
										'    <span class="box-li-item box-li-name" style="width: 100px;">' + sqlFieldes[i].fieldSqlName +'</span>\n' +
										'    <span class="box-li-item box-li-type" style="width: 80px;">' + fieldTypeName +  '('+ sqlFieldes[i].fieldLen +')' + '</span>\n' +
										'    <span class="box-li-item" style="width: 15px;">\n' +
										'        <div class="box-item-circle right-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
										'    </span>\n' +
										'    <input hidden="hidden" type="text" name="fieldKey" value="0" />' +
										'</li>';
								}
							}
						}

						html +=
							'                    </ul>\n' ;
						if( curObj.tableTypeId == tableTypeId ) {
							html +=
								'                <span class="fa fa-times box-delete" aria-hidden="true"><input hidden="hidden" type="text" name="tableId" value="'+ curObj.tableId +'" /></span>' ;

						}
						html +=	'                </div>';
						container.append(html);


						//container的宽高
						var containerHeight = container.height();
						var containerWidth = container.width();
						var powerCanvasHeight = container.height();
						var powerCanvasWidth = container.width();
						var curBox = $("#"+ curObj.tableId );

						if( ( curBox.position().top + curBox.height() ) > powerCanvas.height ) {
							container.height( containerHeight + curBox.height() );
							// powerCanvas.height = containerHeight + curBox.height();
							powerCanvas.height = powerCanvas.height + curBox.height();
						}

						if( ( curBox.position().left + curBox.width() ) > powerCanvas.width ) {
							container.width( containerWidth + curBox.width() );
							// powerCanvas.width = containerWidth + curBox.width();
							powerCanvas.width = powerCanvas.width + curBox.width();
						}
					}
				}

				loadFoeignkeys();

				$(".power-class-box").each( function () {
					$(this).dblclick(function (e) {
						var uuid = $(this).attr("id");
						var offsetX = $(this).position().left;
						var offsetY = $(this).position().top;
						powerClassDBClick(e, offsetX, offsetY, uuid);
					});
				});
			} else {
				$("#loading").fadeOut();
			}
		}
	});
}

function loadNewPowerBoxs(tableTypeId) {

	var container = $("#container");
	container.find(".summer-box").remove();
	container.find(".line_box_container").remove();
	$.ajax({
		url: curContext + '/v1/sqlModel/findSqlTableByTableTypeId',
		data: {"tableTypeId": tableTypeId},
		success: function (result) {
			var sqlTable = result.sqlTables;
			curTypeForeignKeys = result.sqlTableForeignkeyes;
			if( sqlTable.length > 0 ) {
				var sqlTables = sqlTable.concat(result.sqlTableIdNew);

				var arr4 = [];

				for( var j = 0, len = sqlTables.length; j < len; j+=4 ){
					arr4.push(sqlTables.slice(j, j+4));
				}

				for( var k = 0, len = arr4.length; k < len; k++ ){

					for( var h = 0, lon = arr4[k].length; h < lon; h++ ){

						var html  = '';
						var curObj = arr4[k][h];
						var tableTop = curObj.tableY;
						var tableLeft = curObj.tableX;

						if( tableTop == 0 && tableLeft == 0 ){
							tableTop = tableTop + 30;

							//开始第二行 第三行...
							//完美
							if( k > 0 ){
								var preArr4 = arr4[k-1];
								var preObj = $("#"+preArr4[h].tableId);
								if( preArr4[h] ){
									tableTop = preObj.height() + preObj.position().top + 30;
								}
							}

							tableLeft = 300 * h + 30;
						}


						html += ' <div id="' + curObj.tableId +'" class="summer-box" style="top:'+tableTop+'px; left:'+tableLeft+'px;" data-toggle="summer-box">\n' +
							'                  <div class="summer-box-title">'+curObj.tableSqlName+'</div>\n' +
							'                  <ul class="summer-column">';

						if( curObj.sqlFieldes && curObj.sqlFieldes.length > 0 ) {
							var sqlFieldes = curObj.sqlFieldes;
							var resType = $("#resType").val();
							var fieldTypeName = '';
							for (var i = 0, leng = sqlFieldes.length; i < leng; i++) {

								var fieldDateType = sqlFieldes[i].dateType;
								if (resType == "1") {
									//mysql
									switch (sqlFieldes[i].fieldType) {
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
								else if (resType == "2") {
									//oracle
									switch (sqlFieldes[i].fieldType) {
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
								else if (resType == "3") {
									//db2
									switch (sqlFieldes[i].fieldType) {
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
								else if (resType == "5") {
									//sqlserver
									switch (sqlFieldes[i].fieldType) {
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

								if( sqlFieldes[i].fieldKey == "1" ) {
									html += '<li class="column-item">\n' +
										'                      <i class="fa fa-arrow-left" aria-hidden="true"></i>\n' +
										'                      <span class="item-content"><img src="'+curContext+'/resources/img/dataModel/key.png" /></span>\n' +
										'                      <sapn class="item-content"><nobr><span class="column-inner-name">'+sqlFieldes[i].fieldSqlName+'</span><sapn class="column-type">:</sapn><sapn class="column-type">' + fieldTypeName +  '('+ sqlFieldes[i].fieldLen +')' + '</sapn></nobr></sapn>\n' +
										'                      <i class="fa fa-arrow-right" aria-hidden="true"></i>\n' +
										'                    </li>';
								}
								else {
									html += '<li class="column-item">\n' +
										'                      <i class="fa fa-arrow-left" aria-hidden="true"></i>\n' +
										'                      <span class="item-content"></span>\n' +
										'                      <sapn class="item-content"><nobr><span class="column-inner-name">'+sqlFieldes[i].fieldSqlName+'</span><sapn class="column-type">:</sapn><sapn class="column-type">' + fieldTypeName +  '('+ sqlFieldes[i].fieldLen +')' + '</sapn></nobr></sapn>\n' +
										'                      <i class="fa fa-arrow-right" aria-hidden="true"></i>\n' +
										'                    </li>';
								}
							}
						}

						html += '</ul>\n' +
										'  <div class="drop-arrow-container"><div class="drop-arrow"><i class="fa fa-angle-double-down" aria-hidden="true"></i></div></div>\n' +
										'</div>';
						container.append(html);


						//container的宽高
						var containerHeight = container.height();
						var containerWidth = container.width();
						var powerCanvasHeight = container.height();
						var powerCanvasWidth = container.width();
						var curBox = $("#"+ curObj.tableId );
						curBox.data("boxDetail", curObj);
						var powerCanvas = document.getElementById("power-canvas");

						if( ( curBox.position().top + curBox.height() ) > powerCanvas.height ) {
							container.height( curBox.position().top + curBox.height() + 300 );
							// powerCanvas.height = containerHeight + curBox.height();
							powerCanvas.height = curBox.position().top + curBox.height() + 300;
						}

						if( ( curBox.position().left + curBox.width() ) > powerCanvas.width ) {
							container.width( curBox.position().left + curBox.width() + 300 );
							// powerCanvas.width = containerWidth + curBox.width();
							powerCanvas.width = curBox.position().left + curBox.width() + 300;
						}
					}
				}

				$(".summer-box").summerBox();

				loadFoeignkeys();

				// $(".power-class-box").each( function () {
				// 	$(this).dblclick(function (e) {
				// 		var uuid = $(this).attr("id");
				// 		var offsetX = $(this).position().left;
				// 		var offsetY = $(this).position().top;
				// 		powerClassDBClick(e, offsetX, offsetY, uuid);
				// 	});
				// });
				// $("#loading").fadeOut();
			} else {
				$("#loading").fadeOut();
			}
		}
	});
}

function loadTree(dataResId, uuid) {
	var curTree =  $("#tree_" + uuid);
	$.ajax({
		url: curContext + '/v1/sqlModel/findtablesbydataresid',
		data: { "dataResId": dataResId },
		success: function (resultData) {
			if( resultData.length == 0 ) {
				curTree.empty();
				curTree.append('<span class="my-loop no-data">无数据</span>');
			} else {
				curTree.empty();

				for( var i = 0; i < resultData.length; i++ ){
					resultData[i].name = resultData[i].tableTypeName;
					resultData[i].id = resultData[i].tableTypeId;
					resultData[i].pId = resultData[i].parentId;
				}

				var setting = {
					view: {
						selectedMulti: false,
						addHoverDom: addHoverDom,
						removeHoverDom: removeHoverDom
					},
					callback: {
						beforeEditName: beforeEditName,
						beforeRemove: beforeRemove,
						onClick: zTreeOnClick,
						beforeExpand: beforeExpand,
						onExpand: onExpand
					},
					data: {
						simpleData: {
							enable: true,
							rootPId: ""
						}
					},
					edit: {
						enable: true
					}
				};

				var curExpandNode = null;

				function addHoverDom(treeId, treeNode) {

					var sObj = $("#" + treeNode.tId + "_span");
					if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
					var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
						+ "' title='add node' onfocus='this.blur();'></span>";
					sObj.after(addStr);
					var btn = $("#addBtn_"+treeNode.tId);
					if (btn) btn.bind("click", function(e){
						var e = e || window.event;
						var formId = getuuid(16,16);
						var html ='<div style="padding: 0 15px 0 0;">'+
							'   <form class="layui-form" action="" id="'+formId+'">' +
							'       <div class="layui-form-item">' +
							'           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
							'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
							'             <input type="text" name="tableTypeName" class="layui-input" autocomplete="off" >\n' +
							'           </div>\n' +
							'       </div>' +
							'       <div class="layui-form-item">\n' +
							'           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
							'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
							'             <input type="text" name="sortId" class="layui-input" autocomplete="off">\n' +
							'           </div>\n' +
							'       </div>' +
							'       <div class="layui-form-item">\n' +
							'         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
							'       </div>' +
							'   </form>' +
							'</div>' ;
						var index = layer.open({
							type: 1,
							shade: 0,
							area: ["200px","200px"],
							offset: [ e.clientY, e.clientX ],
							skin: 'myLayerOpnSkin',
							title: '新增子节点',
							content: html
						});

						$("#" + formId ).find(".layui-btn-normal").click(function (e) {
							e.preventDefault();
							var tableTypeName = $("#" + formId ).find("input[name='tableTypeName']").val();
							var sortId = $("#" + formId ).find("input[name='sortId']").val();
							layer.close(index);
							$.ajax({
								url: curContext + '/v1/sqlModel/savesqltabletype',
								data: {"tableTypeName": tableTypeName, "parentId": treeNode.tableTypeId, "sortId": sortId, "dataResId": dataResId },
								success: function (result) {
									if( result.tableTypeId ){
										var zTree = $.fn.zTree.getZTreeObj("tree_" + uuid);
										zTree.addNodes(treeNode, {id:result.tableTypeId, pId:treeNode.tableTypeId, name: result.tableTypeName });
									}
								}
							});
						});
						$("#" + formId ).find(".layui-btn-primary").click(function(e) {
							e.preventDefault();
							layer.close(index);
						});
						return false;
					});
				};

				function removeHoverDom(treeId, treeNode) {
					$("#addBtn_"+treeNode.tId).unbind().remove();
				};

				function beforeEditName(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("tree_"+ uuid);
					zTree.selectNode(treeNode);
					var formId = getuuid(16,16);
					var e = event || window.event;
					var html ='<div style="padding: 0 15px 0 0;">'+
						'   <form class="layui-form" action="" id="'+formId+'">' +
						'       <div class="layui-form-item">' +
						'           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
						'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						'             <input type="text" name="tableTypeName" class="layui-input" autocomplete="off" value="'+ treeNode.tableTypeName +'" >\n' +
						'           </div>\n' +
						'       </div>' +
						'       <div class="layui-form-item">\n' +
						'           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
						'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						'             <input type="text" name="sortId" class="layui-input" autocomplete="off" value="'+ treeNode.sortId +'">\n' +
						'           </div>\n' +
						'       </div>' +
						'       <div class="layui-form-item">\n' +
						'         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
						'       </div>' +
						'       <input hidden="hidden" name="tableTypeId" value="'+ treeNode.tableTypeId +'">' +
						'   </form>' +
						'</div>' ;

					var index = layer.open({
						type: 1,
						shade: 0,
						area: ["200px","200px"],
						offset: [ e.clientY, e.clientX ],
						skin: 'myLayerOpnSkin',
						title: '编辑子节点',
						content: html
					});

					$("#" + formId ).find(".layui-btn-normal").click(function (e) {
						e.preventDefault();
						var tableTypeName = $("#" + formId ).find("input[name='tableTypeName']").val();
						var sortId = $("#" + formId ).find("input[name='sortId']").val();
						var tableTypeId = $("#" + formId ).find("input[name='tableTypeId']").val();
						layer.close(index);
						$.ajax({
							url: curContext + '/v1/sqlModel/savesqltabletype',
							data: {"tableTypeName": tableTypeName, "parentId": treeNode.parentId, "sortId": sortId, "tableTypeId": tableTypeId, "dataResId": dataResId },
							success: function (result) {
								if( result.tableTypeId ){
									var zTree = $.fn.zTree.getZTreeObj("tree_"+ uuid);
									$("#"+ treeNode.tId +"_span").text(result.tableTypeName);
								}
							}
						});
					});
					$("#" + formId ).find(".layui-btn-primary").click(function(e) {
						e.preventDefault();
						layer.close(index);
					});
					return false;
				}

				function beforeRemove(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("tree_"+ uuid);
					zTree.selectNode(treeNode);
					var e = event || window.event;
					if( treeNode.isParent ){
						layer.msg('父节点不能直接删除', {icon: 2});
						return false;
					}
					layer.confirm('确认删除'+ treeNode.name +'节点吗？',
						{
							icon: 3,
							offset: [ e.clientY, e.clientX ],
							shade: 0,
							skin: 'myLayerOpnSkin'
						}, function(index){
							$.ajax({
								url: curContext + '/v1/sqlModel/deletesqltabletype',
								data: { "tableTypeId": treeNode.id },
								success: function (result) {
									if( result.code == 200 ){
										layer.msg(result.message, {icon: 1});
										zTree.removeNode(treeNode);
									}else {
										layer.msg(result.message, {icon: 2});
									}
								}
							});
						});
					return false;
				}

				function zTreeOnClick(event, treeId, treeNode) {
					var tableTypeId = treeNode.tableTypeId;
					// window.open("<%=path%>/v1/sqlModel/manage/dataBaseCanvas?tableTypeId=" + tableTypeId , "_blank");
					$(".menu-right").css("display","inline-block");
					$(".power-tableType-name").text(treeNode.name);
					$("#dataResId").val(dataResId);
					$("#tableTypeId").val(tableTypeId);

					loadPowerBoxs(tableTypeId);
				}

				function singlePath(newNode) {
					if (newNode === curExpandNode) return;

					var zTree = $.fn.zTree.getZTreeObj("tree_"+ uuid),
						rootNodes, tmpRoot, tmpTId, i, j, n;

					if (!curExpandNode) {
						tmpRoot = newNode;
						while (tmpRoot) {
							tmpTId = tmpRoot.tId;
							tmpRoot = tmpRoot.getParentNode();
						}
						rootNodes = zTree.getNodes();
						for (i=0, j=rootNodes.length; i<j; i++) {
							n = rootNodes[i];
							if (n.tId != tmpTId) {
								zTree.expandNode(n, false);
							}
						}
					} else if (curExpandNode && curExpandNode.open) {
						if (newNode.parentTId === curExpandNode.parentTId) {
							zTree.expandNode(curExpandNode, false);
						} else {
							var newParents = [];
							while (newNode) {
								newNode = newNode.getParentNode();
								if (newNode === curExpandNode) {
									newParents = null;
									break;
								} else if (newNode) {
									newParents.push(newNode);
								}
							}
							if (newParents!=null) {
								var oldNode = curExpandNode;
								var oldParents = [];
								while (oldNode) {
									oldNode = oldNode.getParentNode();
									if (oldNode) {
										oldParents.push(oldNode);
									}
								}
								if (newParents.length>0) {
									zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
								} else {
									zTree.expandNode(oldParents[oldParents.length-1], false);
								}
							}
						}
					}
					curExpandNode = newNode;
				}

				function beforeExpand(treeId, treeNode) {
					var pNode = curExpandNode ? curExpandNode.getParentNode():null;
					var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
					var zTree = $.fn.zTree.getZTreeObj("tree_"+ uuid);
					for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
						if (treeNode !== treeNodeP.children[i]) {
							zTree.expandNode(treeNodeP.children[i], false);
						}
					}
					while (pNode) {
						if (pNode === treeNode) {
							break;
						}
						pNode = pNode.getParentNode();
					}
					if (!pNode) {
						singlePath(treeNode);
					}
				}

				function onExpand(event, treeId, treeNode) {
					curExpandNode = treeNode;
				}

				var zTreeObj = $.fn.zTree.init($("#tree_" + uuid), setting, resultData);
			}
		}
	});
}

function deleteTable(tableId) {

	layer.confirm('确认删除当前表吗？',
		{
			icon: 3,
			shade: 0,
			skin: 'myLayerOpnSkin'
		}, function(index){
			var lines = $(".line_box_container");
			var isHaveForeignKey = false;
			layer.close(index);
			for( var i = 0; i < lines.length; i++ ) {
				if( $(lines[i]).attr("fromboxid") == tableId || $(lines[i]).attr("toboxid") == tableId ) {
					isHaveForeignKey = true;
				}
			}
			if( isHaveForeignKey ) {
				layer.msg('当前表含有关联字段！请先删除关联外键', {icon: 2});
				return;
			}
			$.ajax({
				url:  curContext + '/v1/sqlModel/deletetableinfo/' + tableId,
				success: function(result){
					if( result.code == 200 ) {
						$("#" + tableId ).remove();
						var zTree = $.fn.zTree.getZTreeObj("curTree");
						var node = zTree.getNodeByParam("id", tableId);
						zTree.removeNode(node);
					}
				}
			});
		});
}

function loadNewTree(treeData) {

	var treeArray = [];

	var sqlDataRes = treeData.sqlDataRes;
	var dataResId = sqlDataRes.dataResId;
	treeArray.push({
			"id" : sqlDataRes.dataResId,
			"name" : sqlDataRes.dataResTypeName,
			"pId": "",
			"dataType" : 'source',
			"iconSkin" : 'database',
			open:true
	});

	var tableList = treeData.tableList;
	for( var i = 0; i < tableList.length; i++ ) {
		var type = tableList[i].type;
		var tableType = {
			"id" : type.tableTypeId,
			"name" : type.tableTypeName,
			"pId": ( type.parentId == "" ? sqlDataRes.dataResId : type.parentId ),
			"sortId" : type.sortId,
			"dataType" : 'type',
			"iconSkin" : 'type',
			open:true,
			dropRoot:false
		};
		treeArray.push(tableType);
		var tables = tableList[i].table;
		for( var j = 0; j < tables.length; j++ ){
			var curTable = {
				"id" : tables[j].tableId,
				"name" : tables[j].tableName,
				"pId": tables[j].tableTypeId,
				"dataType" : 'table',
				"iconSkin" : 'table',
				open:true,
				dropRoot:false,
				dropInner:false
			};
			treeArray.push(curTable);
		}
	}

	var setting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				inner: dropInner,
				prev: dropPrev,
				next: dropNext
			}
		},
		callback: {
			onRightClick: OnRightClick,
			onClick: zTreeOnClick,
			beforeDrag: beforeDrag,
			beforeDrop: beforeDrop,
			onDrop: zTreeOnDrop
		},
		data: {
			simpleData: {
				enable: true,
				rootPId: ""
			}
		}
	};

	var curDragNodes;

	function OnRightClick(event, treeId, treeNode) {
		var rMenu = $(".rMenu");
		rMenu.empty();
		var x = event.clientX - $(".power-left-nav")[0].offsetLeft;
		var y = event.clientY - $(".power-main")[0].offsetTop;
		if( treeNode.dataType == "type" ) {
			var html =
				'<li class="rMenu-item rMenu-new">新增子模块</li>\n' +
				'<li class="rMenu-item rMenu-edit">编辑</li>\n' +
				'<li class="rMenu-item rMenu-delete">删除</li>';
			rMenu.append(html);
			rMenu.css("display","block");
			rMenu.css("top", y + "px");
			rMenu.css("left", x + "px");
			$(".rMenu-new").click(function (e) {
				//查询当前节点下的子节点名称集
				var childNames = [];
				$.ajax({
					url: curContext + '/v1/sqlModel/checkTypeName',
					data:{ "parentId": treeNode.id, "dataResId": dataResId },
					success: function (result) {
						childNames = result;
					}
				});

				var formId = getuuid(16,16);
				var html ='<div style="padding: 15px 15px 15px 0;">'+
					'   <form class="layui-form" action="" id="'+formId+'">' +
					'       <div class="layui-form-item">' +
					'           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
					'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
					'             <input type="text" name="tableTypeName" class="layui-input" autocomplete="off" >\n' +
					'           </div>\n' +
					'       </div>' +
					'       <div class="layui-form-item">\n' +
					'           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
					'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
					'             <input type="text" name="sortId" class="layui-input" autocomplete="off">\n' +
					'           </div>\n' +
					'       </div>' +
					'       <div class="layui-form-item">\n' +
					'         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
					'       </div>' +
					'   </form>' +
					'</div>' ;
				var index = layer.open({
					type: 1,
					shade: 0,
					area: "400px",
					skin: 'myLayerOpnSkin',
					title: '新增子模块',
					content: html
				});
				$("#" + formId).find("input[name='tableTypeName']").blur(function(){
					var curVal = $(this).val();
					if(isInArray(curVal, childNames)){
						layer.tips('模块名称重复！', $(this), {tips: 2});
					}
				});
				$("#" + formId ).find(".layui-btn-normal").click(function (e) {
					e.preventDefault();

					var tableTypeNameInput = $("#" + formId ).find("input[name='tableTypeName']");
					var tableTypeName = tableTypeNameInput.val();
					var sortId = $("#" + formId ).find("input[name='sortId']").val();

					if(isInArray(tableTypeName, childNames)){
						layer.tips('节点名称重复！', tableTypeNameInput, {tips: 2});
						return;
					}

					layer.close(index);
					$.ajax({
						url: curContext + '/v1/sqlModel/savesqltabletype',
						data: {"tableTypeName": tableTypeName, "parentId": treeNode.id, "sortId": sortId, "dataResId": dataResId },
						success: function (result) {
							if( result.tableTypeId ){
								var zTree = $.fn.zTree.getZTreeObj("curTree");
								zTree.addNodes(treeNode, {id:result.tableTypeId, pId:treeNode.id, name: result.tableTypeName, dataType : 'type', iconSkin : 'type' });
							}
						}
					});
				});
				$("#" + formId ).find(".layui-btn-primary").click(function(e) {
					e.preventDefault();
					layer.close(index);
				});
			});
			$(".rMenu-edit").click(function (e) {
				var childNames = [];
				$.ajax({
					url: curContext + '/v1/sqlModel/checkTypeName',
					data:{ "parentId": treeNode.pId, "dataResId": dataResId },
					success: function (result) {
						console.log();
						childNames = result;
					}
				});

				var formId = getuuid(16,16);
				var html ='<div style="padding: 15px 15px 15px 0;">'+
					'   <form class="layui-form" action="" id="'+formId+'">' +
					'       <div class="layui-form-item">' +
					'           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
					'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
					'             <input type="text" name="tableTypeName" class="layui-input" autocomplete="off" value="'+ treeNode.name +'" >\n' +
					'           </div>\n' +
					'       </div>' +
					'       <div class="layui-form-item">\n' +
					'           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
					'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
					'             <input type="text" name="sortId" class="layui-input" autocomplete="off" value="'+ treeNode.sortId +'">\n' +
					'           </div>\n' +
					'       </div>' +
					'       <div class="layui-form-item">\n' +
					'         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
					'       </div>' +
					'       <input hidden="hidden" name="tableTypeId" value="'+ treeNode.tableTypeId +'">' +
					'   </form>' +
					'</div>' ;

				var index = layer.open({
					type: 1,
					shade: 0,
					area: "400px",
					skin: 'myLayerOpnSkin',
					title: '编辑节点',
					content: html
				});

				$("#" + formId ).find(".layui-btn-normal").click(function (e) {
					e.preventDefault();
					var tableTypeNameInput = $("#" + formId ).find("input[name='tableTypeName']");
					var tableTypeName = tableTypeNameInput.val();
					var sortId = $("#" + formId ).find("input[name='sortId']").val();

					if( isInArray(tableTypeName,childNames,'edit') ){
						layer.tips('节点名称重复！', tableTypeNameInput, {tips: 2});
						return;
					}

					layer.close(index);
					$.ajax({
						url: curContext + '/v1/sqlModel/savesqltabletype',
						data: {"tableTypeName": tableTypeName, "parentId": treeNode.pId, "sortId": sortId, "tableTypeId": treeNode.id, "dataResId": dataResId },
						success: function (result) {
							if( result.tableTypeId ){
								var zTree = $.fn.zTree.getZTreeObj("curTree");
								$("#"+ treeNode.tId +"_span").text(result.tableTypeName);
								treeNode.name = result.tableTypeName;
								treeNode.sortId = result.sortId;
							}
						}
					});
				});
				$("#" + formId ).find(".layui-btn-primary").click(function(e) {
					e.preventDefault();
					layer.close(index);
				});
			});
			$(".rMenu-delete").click(function (e) {
				if( treeNode.isParent ){
					layer.msg('父节点不能直接删除', {icon: 2});
					return false;
				}
				layer.confirm('确认删除'+ treeNode.name +'节点吗？',
					{
						icon: 3,
						shade: 0,
						skin: 'myLayerOpnSkin'
					}, function(index){
						$.ajax({
							url: curContext + '/v1/sqlModel/deletesqltabletype',
							data: { "tableTypeId": treeNode.id },
							success: function (result) {
								if( result.code == 200 ){
									layer.msg(result.message, {icon: 1});
									zTree.removeNode(treeNode);
								}else {
									layer.msg(result.message, {icon: 2});
								}
							}
						});
					});
			});
		}
		else if(treeNode.dataType == "table") {
			// var html =
			// 	'<li class="rMenu-item rMenu-table-edit">编辑</li>\n' +
			// 	'<li class="rMenu-item rMenu-table-delete">删除</li>';
			// rMenu.append(html);
			// rMenu.css("display","block");
			// rMenu.css("top", y + "px");
			// rMenu.css("left", x + "px");
			// $(".rMenu-table-edit").click(function (e) {
			//
			// });
			// $(".rMenu-table-delete").click(function (e) {
			// 	var tableId = treeNode.id;
			// 	deleteTable(tableId);
			// });
		}
		else if(treeNode.dataType == "source") {
			var html =
				'<li class="rMenu-item rMenu-new">新增类型</li>\n' +
				'<li class="rMenu-item rMenu-refresh">刷新库</li>\n' ;
			rMenu.append(html);
			rMenu.css("display","block");
			rMenu.css("top", y + "px");
			rMenu.css("left", x + "px");

			$(".rMenu-new").click(function (e) {

				//查询当前节点下的子节点名称集
				var childNames = [];
				$.ajax({
					url: curContext + '/v1/sqlModel/checkTypeName',
					data:{ "parentId": treeNode.id, "dataResId": dataResId },
					success: function (result) {
						childNames = result;
					}
				});

				var formId = getuuid(16,16);
				var html ='<div style="padding: 15px 15px 15px 0;">'+
					'   <form class="layui-form" action="" id="'+formId+'">' +
					'       <div class="layui-form-item">' +
					'           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
					'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
					'             <input type="text" name="tableTypeName" class="layui-input" autocomplete="off" >\n' +
					'           </div>\n' +
					'       </div>' +
					'       <div class="layui-form-item">\n' +
					'           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
					'           <div class="layui-input-block" style="margin-left: 70px;">\n' +
					'             <input type="text" name="sortId" class="layui-input" autocomplete="off">\n' +
					'           </div>\n' +
					'       </div>' +
					'       <div class="layui-form-item">\n' +
					'         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
					'       </div>' +
					'   </form>' +
					'</div>' ;
				var index = layer.open({
					type: 1,
					shade: 0,
					area: "400px",
					skin: 'myLayerOpnSkin',
					title: '新增子模块',
					content: html
				});
				$("#" + formId).find("input[name='tableTypeName']").blur(function(){
					var curVal = $(this).val();
					if(isInArray(curVal, childNames)){
						layer.tips('模块名称重复！', $(this), {tips: 2});
					}
				});
				$("#" + formId ).find(".layui-btn-normal").click(function (e) {
					e.preventDefault();

					var tableTypeNameInput = $("#" + formId ).find("input[name='tableTypeName']");
					var tableTypeName = tableTypeNameInput.val();
					var sortId = $("#" + formId ).find("input[name='sortId']").val();

					if(isInArray(tableTypeName, childNames)){
						layer.tips('节点名称重复！', tableTypeNameInput, {tips: 2});
						return;
					}

					layer.close(index);
					$.ajax({
						url: curContext + '/v1/sqlModel/savesqltabletype',
						data: {"tableTypeName": tableTypeName, "parentId": treeNode.id, "sortId": sortId, "dataResId": dataResId },
						success: function (result) {
							if( result.tableTypeId ){
								var zTree = $.fn.zTree.getZTreeObj("curTree");
								zTree.addNodes(treeNode, {id:result.tableTypeId, pId:treeNode.id, name: result.tableTypeName, dataType : 'type', iconSkin : 'type' });
							}
						}
					});
				});
				$("#" + formId ).find(".layui-btn-primary").click(function(e) {
					e.preventDefault();
					layer.close(index);
				});
			});

			$(".rMenu-refresh").click(function(e) {
				var dataResId = treeNode.id;
				rMenu.css("display","none");
				$.ajax({
					url: curContext + '/v1/sqlModel/refreshTable/' + dataResId,
					success:function (result) {
						if( result.code == "000000" ){
							//调用接口 获取该数据源下的所有表类型及表类型下的所有表
							// $("#curTree").empty();
							// $.ajax({
							// 	url: curContext + '/v1/sqlModel/show/dataRes',
							// 	data: { "dataResId": dataResId },
							// 	success: function( result ) {
							// 		if( result.code == "000000" ) {
							// 			loadNewTree( result );
							//
							// 			var dataResTypeId = result.sqlDataRes.dataResTypeId;
							// 			$.ajax({
							// 				url: curContext + '/v1/sqlModel/manage/dataSourceType',
							// 				data:{ "dataResTypeId": dataResTypeId },
							// 				success:function(result){
							// 					if( result.dataResTypeId ){
							// 						$("#resType").val( result.resType );
							// 					}
							// 				}
							// 			});
							// 		}
							// 	}
							// });
						}
					}
				});
			});
		}
		else {
			rMenu.css("display","none");
		}

		zTree.selectNode(treeNode);
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if( treeNode.dataType == "type" ) {
			var tableTypeId = treeNode.id;
			// window.open("<%=path%>/v1/sqlModel/manage/dataBaseCanvas?tableTypeId=" + tableTypeId , "_blank");
			$(".menu-right").css("display","inline-block");
			$(".power-tableType-name").text(treeNode.name);
			$("#dataResId").val(dataResId);
			$("#tableTypeId").val(tableTypeId);
			$("#loading").fadeIn();

			linesArray = [];

			loadNewPowerBoxs(tableTypeId);
		} else if( treeNode.dataType == "table" ){
			// var tableId = treeNode.id;
			// var tableBox = $("#" + tableId);
			// var offsetX = null;
			// var offsetY = null;
			// if( tableBox.length > 0 ) {
			// 	offsetX = tableBox.position().left;
			// 	offsetY = tableBox.position().top;
			// }
			// var uuid = getuuid(16,16);
			// reviewTableDetail( tableId, uuid, offsetX, offsetY, null, null );
		}
	}

	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				curDragNodes = null;
				return false;
			}
		}
		curDragNodes = treeNodes;
		return true;
	}

	function beforeDrop(treeId, treeNodes, targetNode, moveType){
		if( targetNode == null ){
			return false;
		}
		return targetNode ? targetNode.drop !== false : true;
	}

	function dropInner(treeId, nodes, targetNode) {
		if (targetNode && targetNode.dropInner === false) {
			return false;
		}
		if( targetNode == null ){
			return false;
		}
		return true;
	}

	//是否允许拖拽至目标节点之前
	function dropPrev(treeId, nodes, targetNode) {
		if( targetNode && targetNode.pId == "" ){
			return false;
		}

		return true;
	}

	//是否允许拖拽至目标节点之后
	function dropNext(treeId, nodes, targetNode) {
		if( targetNode && targetNode.pId == "" ){
			return false;
		}

		return true;
	}

	function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType){
		if( treeNodes[0].dataType == 'table' ){
			var tableId = treeNodes[0].id;
			var typeId = targetNode.id;

			if( targetNode.dataType == "type" ){
				typeId = targetNode.id;
			} else if( targetNode.dataType = "table" ){
				typeId = targetNode.pId;
			}

			$.ajax({
				url: curContext + '/v1/sqlModel/changeType/' + tableId,
				data: { "tableId": tableId, "tableTypeId": typeId },
				success: function (result){
					console.log(result);
				}
			});
		}
	}

	var zTreeObj = $.fn.zTree.init($("#curTree"), setting, treeArray);
	zTree = $.fn.zTree.getZTreeObj("curTree");
}

//判断某个字符串数组中是否包含某个字符串
function isInArray( str, arr, type ) {
	var isIn = false;
	if( type == "edit" ){
		var trr = [];
		for( var i = 0; i < arr.length; i++ ){
			if( arr[i] == str ){
				trr.push(arr[i]);
			}
		}
		if( trr.length > 1 ){
			isIn = true;
		}
	} else {
		for( var i = 0; i < arr.length; i++ ){
			if( arr[i] == str ){
				isIn = true;
				break;
			}
		}
	}

	return isIn;
}

//保存所有表的坐标
function saveXY() {
	var powerBoxs = $(".summer-box");
	if( powerBoxs.length == 0 ){
		return;
	} else {
		var data = [];
		for( var i = 0; i < powerBoxs.length; i++ ){
			if( $(powerBoxs[i]).data("boxDetail") ){
				data.push({
					"tableId" : $(powerBoxs[i]).data("boxDetail").tableId,
					"tableX" : $(powerBoxs[i]).position().left,
					"tableY" : $(powerBoxs[i]).position().top
				})
			}
		}
		$.ajax({
			url: curContext + "/v1/sqlModel/saveCoordinate",
			type: "POST",
			data: {"tableIdes" : JSON.stringify(data)},
			success: function(result) {
				console.log(result);
			}
		});
	}
}

//每行中的表格编辑
function spanSelectBindClick() {
	$(".inner-text").click(function (e) {
		var text = $(this).text();
		var innerInput = $(e.target).next();
		if( innerInput.is("select") ){
			innerInput.find("option[text='"+text+"']").attr("selected", true);
		}else {
			innerInput.val(text);
		}
		$(e.target).css("display",'none');
		innerInput.css("display",'block');
		innerInput.focus();
	});
	$(".inner-input").blur(function (e) {
		var innerSpan = $(e.target).prev();
		var inputVal = $(this).val();
		if( $(this).is("select") ){
			var selectText = $(this).find("option:selected").text();
			innerSpan.text(selectText);
		} else {
			innerSpan.text(inputVal);
		}
		innerSpan.css("display","block");
		$(e.target).css("display",'none');
	});
	$("#inner-table").find("tbody").find("input[name='FieldKey']").change(function (e) {
		var curTd = $(this).parent().parent();
		if( $(this).is(':checked') ){
			curTd.find("input[name='sortIndex']").prop("checked",true);
			curTd.find("input[name='isNull']").prop("checked",true);
			curTd.find("input[name='sortIndex']").attr("disabled","disabled");
			curTd.find("input[name='isNull']").attr("disabled","disabled");
		}else {
			curTd.find("input[name='sortIndex']").removeAttr("disabled");
			curTd.find("input[name='isNull']").removeAttr("disabled");
		}
	});
}

