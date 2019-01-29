/*获取类盒子HTML*/
function getPowerBoxHtml(uuid, offsetX, offsetY) {
	// var html =
	// 	'               <div id="'+uuid+'" class="power-class-box" style="top: '+ offsetY +'px;left: '+ offsetX +'px;" onmousedown="classBoxMousedown(this, event)" >\n' +
	// 	'                    <div class="class-box-title">\n' +
	// 	'                        表名1\n' +
	// 	'                    </div>\n' +
	// 	'                    <ul class="box-ul">\n' +
	// 	'                        <li>\n' +
	// 	'                            <span class="box-li-item" style="width: 15px;"></span>\n' +
	// 	'                            <span class="box-li-item" style="width: 40px;text-align: center;"></span>\n' +
	// 	'                            <span class="box-li-item" style="width: 130px;">名称</span>\n' +
	// 	'                            <span class="box-li-item" style="width: 50px;">类型</span>\n' +
	// 	'                            <span class="box-li-item" style="width: 15px;"></span>\n' +
	// 	'                        </li>\n' +
	// 	'                    </ul>\n' +
	// 	'                    <ul class="box-ul box-ul-list">\n' +
	// 	'                    </ul>\n' +
	// 	'                <span class="fa fa-times box-delete" aria-hidden="true"><input hidden="hidden" type="text" name="tableId" /></span>' +
	// 	'                </div>';
	var html =
		'<div id="'+uuid+'" class="summer-box" style="top: '+ offsetY +'px;left: '+ offsetX +'px;" data-toggle="summer-box">\n' +
		'     <div class="summer-box-title">表名</div>\n' +
		'     <ul class="summer-column">' +
		'     </ul>\n' +
		'  <div class="drop-arrow-container"><div class="drop-arrow"><i class="fa fa-angle-double-down" aria-hidden="true"></i></div></div>\n' +
		'</div>';
	return html;
}

//表数据回显与编辑
function reviewTableDetail( tableId, uuid, offsetX, offsetY, boxId, saveType ) {
	$.ajax({
		url: '<%=path%>/v1/sqlModel/show/tableFieldList',
		data: {"tableId": tableId},
		success:function (result) {
			var contentHtml = '<div style="padding: 0 30px 0 15px;height:100%;" id="jspanel_content_'+ uuid +'">\n' +
				'    <div class="layui-tab layui-tab-brief" style="margin:0;">\n' +
				'        <ul class="layui-tab-title">\n' +
				'            <li class="layui-this">基本</li>\n' +
				'            <li>列</li>\n' +
				'        </ul>\n' +
				'        <div class="layui-tab-content">\n' +
				'            <div class="layui-tab-item layui-show">\n' +
				'                <form class="layui-form" action="">\n' +
				'                    <div class="layui-form-item">\n' +
				'                        <label class="layui-form-label">表名</label>\n' +
				'                        <div class="layui-input-block">\n' +
				'                            <input type="text" name="tableName" autocomplete="off" class="layui-input" value="'+ result.tableName +'">\n' +
				'                            <input type="text" name="tableId" hidden value="'+ result.tableId +'">\n' +
				'                            <input type="text" name="dataResId" hidden value="'+ result.dataResId +'">\n' +
				'                            <input type="text" name="tableTypeId" hidden value="'+ result.tableTypeId +'">\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                    <div class="layui-form-item">\n' +
				'                        <label class="layui-form-label">表英文名</label>\n' +
				'                        <div class="layui-input-block">\n' +
				'                            <input type="text" name="tableSqlName" autocomplete="off" class="layui-input" value="' + result.tableSqlName + '">\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                    <div class="layui-form-item layui-form-text">\n' +
				'                        <label class="layui-form-label">注释</label>\n' +
				'                        <div class="layui-input-block">\n' +
				'                            <textarea name="tableResume" placeholder="请输入内容" class="layui-textarea">'+ ( result.tableResume == null ? '' : result.tableResume ) +'</textarea>\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                    <div class="layui-form-item layui-form-text">\n' +
				'                        <label class="layui-form-label">序号</label>\n' +
				'                        <div class="layui-input-block">\n' +
				'                            <input name="sortId" autocomplete="off" class="layui-input" value="'+ ( result.sortId == null ? '' : result.sortId ) +'" />\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                </form>\n' +
				'            </div>\n' +
				'            <div class="layui-tab-item">\n' +
				'                <div class="new-line-container">\n' +
				'                    <span id="column-add" forTable="'+uuid+'" class="fa fa-plus-square line-tool-add" aria-hidden="true" onClick="addTableColumn(event)"></span>\n' +
				'                    <span id="column-delete" forTable="'+uuid+'" class="fa fa-minus-square line-tool-add" aria-hidden="true" onClick="delteTableColumn(event)"></span>' +
				'                </div>\n' +
				'                <div style="max-height: 70%;overflow: auto;">' +
				'               <table class="power-inner-table layui-table jpanel-inner-table" id="'+ uuid +'">\n' +
				'                 <colgroup>\n' +
				'                     <col width="50">\n' +
				'                     <col width="50">\n' +
				'                     <col width="120">\n' +
				'                     <col width="120">\n' +
				'                     <col width="100">\n' +
				'                     <col width="100">\n' +
				'                     <col width="100">\n' +
				'                     <col width="50">\n' +
				'                     <col width="50">\n' +
				'                     <col width="50">\n' +
				'                     <col>\n' +
				'                 </colgroup>' +
				'                    <thead>\n' +
				'                        <tr>\n' +
				'                            <th>' +
				'                            <form class="layui-form">\n' +
				'                              <div class="layui-form-item" style="margin-bottom: 0;">\n' +
				'                                 <div class="layui-input-block" style="margin: 0;min-height: 0px;">\n' +
				'                                     <input type="checkbox" name="" lay-skin="primary" lay-filter="columnCheckBox">\n' +
				'                                 </div>\n' +
				'                              </div>' +
				'                            </form>' +
				'                            </th>\n' +
				'                            <th>编号</th>\n' +
				'                            <th>名称</th>\n' +
				'                            <th>英文名</th>\n' +
				'                            <th>类型</th>\n' +
				'                            <th>长度</th>\n' +
				'                            <th>精度</th>\n' +
				'                            <th>主键</th>\n' +
				'                            <th>索引</th>\n' +
				'                            <th>非空</th>\n' +
				'                            <th>说明</th>\n' +
				'                        </tr>\n' +
				'                    </thead>\n' +
				'                    <tbody>\n';

			var sqlFieldes = result.sqlFieldes;
			var isHaveFieldKey = false;

			for( var j = 0; j < sqlFieldes.length; j++ ) {
				if( sqlFieldes[j].fieldKey == "1" ) {
					isHaveFieldKey = true;
					break;
				}
			}

			for( var i = 0; i < sqlFieldes.length; i++ ) {
				contentHtml += '<tr>\n' +
					'                            <td class="" >' +
					'                              <form class="layui-form" lay-filter="columnCheck">\n' +
					'                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                     <div class="layui-input-block" style="margin: 0;">\n' +
					'                                         <input type="checkbox" class="lineCheckbox" name="lineCheckbox" lay-skin="primary" >\n' +
					'                                     </div>\n' +
					'                                 </div>' +
					'                              </form>' +
					'                            </td>\n' +
					'                            <td class="" >'+ (i+1) +
					'                               <input type="text" name="fieldId" hidden value="'+ sqlFieldes[i].fieldId +'">\n' +
					'                            </td>\n' +
					'                            <td class="" >' +
					'                                 <span class="span-select">'+ sqlFieldes[i].fieldName +'</span>' +
					'                                 <form class="layui-form" action="" style="display: none;">' +
					'                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
					'                                          <input type="text" name="fieldName" autocomplete="off" class="layui-input" value="'+ sqlFieldes[i].fieldName +'">\n' +
					'                                        </div>\n' +
					'                                    </div>\n' +
					'                                 </form>' +
					'                            </td>\n' +
					'                            <td class="" >' +
					'                                 <span class="span-select">'+ sqlFieldes[i].fieldSqlName +'</span>' +
					'                                 <form class="layui-form" action="" style="display: none;">' +
					'                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
					'                                            <input type="text" name="fieldSqlName" autocomplete="off" class="layui-input" value="'+ sqlFieldes[i].fieldSqlName +'">\n' +
					'                                        </div>\n' +
					'                                    </div>\n' +
					'                                 </form>' +
					'                            </td>\n' +
					'                            <td class="" >\n' ;

				var fieldType = sqlFieldes[i].fieldType;
				var fieldTypeName = '';
				var resType = $("#resType").val();
				var fieldDateType = sqlFieldes[i].dateType;

				if( resType == "1" ){
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
				else if( resType == "2" ) {
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
				else if(  resType == "3" ) {
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
				else if( resType == "5" ) {
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

				contentHtml +=
					'                                <span class="span-select">'+ fieldTypeName +'</span>\n' +
					'                                <form class="layui-form" action="" style="display: none;">\n' +
					'                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
					'                                            <select name="fieldType" lay-filter="fieldType" disabled>\n' ;

				if( resType == "1" ){
					//mysql
					contentHtml +=
						'<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>int</option>\n' +
						'<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar</option>\n' +
						'<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>float</option>\n' +
						'<option value="7" '+ ( fieldType == "7" ? 'selected' : '' ) +'>bigint</option>\n' +
						'<option value="9" '+ ( fieldType == "9" ? 'selected' : '' ) +'>tinyint</option>\n' +
						'<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>datetime</option>\n' +
						'<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>longtext</option>\n' +
						'<option value="5" '+ ( fieldType == "5" ? 'selected' : '' ) +'>blob</option>\n' +
						'<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
				}
				else if( resType == "2" ){
					//oracle
					contentHtml +=
						'<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>float</option>\n' +
						'<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar2</option>\n' +
						'<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>date</option>\n' +
						'<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>blob</option>\n' +
						'<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>number</option>\n' +
						'<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
				}
				else if( resType == "3" ){
					//DB2
					contentHtml +=
						'<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>decimal</option>\n' +
						'<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar</option>\n' +
						'<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>timestamp</option>\n' +
						'<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>clob</option>\n' +
						'<option value="5" '+ ( fieldType == "5" ? 'selected' : '' ) +'>blob</option>\n' +
						'<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>int</option>\n' +
						'<option value="7" '+ ( fieldType == "7" ? 'selected' : '' ) +'>bigint</option>\n' +
						'<option value="9" '+ ( fieldType == "9" ? 'selected' : '' ) +'>boolean</option>\n' +
						'<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
				}
				else if( resType == "5" ){
					//sqlserver
					contentHtml +=
						'<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>float</option>\n' +
						'<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar</option>\n' +
						'<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>datetime</option>\n' +
						'<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>nvarchar(max)</option>\n' +
						'<option value="5" '+ ( fieldType == "5" ? 'selected' : '' ) +'>varbinary(max)</option>\n' +
						'<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>int</option>\n' +
						'<option value="7" '+ ( fieldType == "7" ? 'selected' : '' ) +'>bigint</option>\n' +
						'<option value="9" '+ ( fieldType == "9" ? 'selected' : '' ) +'>bit</option>\n' +
						'<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
				}

				contentHtml +=
					'                                            </select>\n' +
					'                                        </div>\n' +
					'                                    </div>\n' +
					'                                </form>\n' +
					'                            </td>\n' +
					'                            <td class="" >\n' +
					'                                <span class="span-select">'+ ( sqlFieldes[i].fieldLen != null ? sqlFieldes[i].fieldLen : "" )  +'</span>' +
					'                                <form class="layui-form" action="" style="display: none;">\n' +
					'                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
					'                                            <input type="text" name="fieldLen" autocomplete="off" class="layui-input"  value="'+ ( sqlFieldes[i].fieldLen != null ? sqlFieldes[i].fieldLen : "" ) +'">\n' +
					'                                        </div>\n' +
					'                                    </div>\n' +
					'                                </form>\n' +
					'                            </td>\n' +
					'                            <td class="" >\n' +
					'                                <span class="span-select">'+ ( sqlFieldes[i].fieldDigit != null ? sqlFieldes[i].fieldDigit : "" ) +'</span>' +
					'                                <form class="layui-form" action="" style="display: none;">\n' +
					'                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
					'                                            <input type="text" name="fieldDigit" autocomplete="off" class="layui-input" value="'+ ( sqlFieldes[i].fieldDigit != null ? sqlFieldes[i].fieldDigit : "" ) +'">\n' +
					'                                        </div>\n' +
					'                                    </div>\n' +
					'                                </form>\n' +
					'                            </td>\n' ;


				contentHtml +=
					'                           <td class="" >\n' +
					'                              <form class="layui-form">\n' +
					'                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                     <div class="layui-input-block" style="margin: 0;">\n' +
					'                                         <input class="FieldKey" lay-filter="FieldKey" type="checkbox" name="FieldKey" value="1" lay-skin="primary" ' + ( sqlFieldes[i].fieldKey == "1" ? 'checked' : '' ) + '  '+ ( sqlFieldes[i].fieldKey == "0" ?  isHaveFieldKey ? 'disabled' : '' : '' ) +'>\n' +
					'                                     </div>\n' +
					'                                 </div>' +
					'                              </form>' +
					'                            </td>\n' ;

				contentHtml +=
					'                            <td class="" >\n' +
					'                              <form class="layui-form">\n' +
					'                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                     <div class="layui-input-block" style="margin: 0;">\n' +
					'                                         <input class="sortIndex" lay-filter="sortIndex" type="checkbox" name="sortIndex" value="1" lay-skin="primary" ' + ( sqlFieldes[i].sortIndex == "1" ? 'checked' : '' ) + '>\n' +
					'                                     </div>\n' +
					'                                 </div>' +
					'                              </form>' +
					'                            </td>\n' ;

				contentHtml +=
					'                           <td class="" >\n' +
					'                              <form class="layui-form">\n' +
					'                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                     <div class="layui-input-block" style="margin: 0;">\n' +
					'                                         <input class="FieldKey" lay-filter="isNull" type="checkbox" name="isNull" value="1" lay-skin="primary" ' + ( sqlFieldes[i]['isNull'] == "0" ? 'checked' : '' ) +'>\n' +
					'                                     </div>\n' +
					'                                 </div>' +
					'                              </form>' +
					'                            </td>\n' ;

				contentHtml +=
					'                            <td class="" >\n' +
					'                                <span class="span-select">'+ ( sqlFieldes[i].fieldResume == null ? '' : sqlFieldes[i].fieldResume == null ) +'</span>' +
					'                                <form class="layui-form" action="" style="display: none;">\n' +
					'                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					'                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
					'                                            <input type="text" name="fieldResume" class="layui-input" autocomplete="off" value="'+ ( sqlFieldes[i].fieldResume == null ? '' : sqlFieldes[i].fieldResume )  +'">\n' +
					'                                        </div>\n' +
					'                                    </div>\n' +
					'                                </form>\n' +
					'                            </td>\n' +
					'                        </tr>';
			}

			contentHtml +=
				'                    </tbody>\n' +
				'                </table>' +
				'                </div>\n' +
				'           </div>\n' +
				'        </div>\n' +
				'    </div>\n' ;

			if( result.tableTypeId == $("#tableTypeId").val() ){
				contentHtml +=
					'  <div style="position: absolute;right: 30px;bottom: 30px;"><button class="layui-btn layui-btn-primary layui-btn-sm" id="cancle_'+ uuid +'">取消</button><button class="layui-btn layui-btn-normal layui-btn-sm" id="save_'+ uuid +'">确认</button></div>' ;
			}

			contentHtml +=
				'</div>';
			var areaHeight = $(window).height() * 0.8;
			var layerIndex = layer.open({
				type: 1,
				shade: 0,
				maxmin: true,
				offset: 't',
				area: ["1000px", areaHeight ],
				skin: 'myLayerOpnSkin',
				title: '数据源类型管理',
				content: contentHtml
			});

			layui.form.render('select');
			layui.form.render('checkbox');

			var offX = offsetX;
			var offY = offsetY;

			if( offsetX == null ) {
				offX = result.tableX;
			}

			if( offsetY == null ) {
				offY = result.tableY;
			}

			$("#save_" + uuid).click(function () {
				dataModalSave( uuid, tableId, layerIndex, offX, offY, boxId, saveType );
			});

			$("#cancle_" + uuid).click(function () {
				layer.close(layerIndex);
			});

			spanSelectBindClick();
			selectBindChange();
		}
	});
}

//盒子保存之后的回调函数
function callBackReBuildBox( result , curBox ){
	var powerBox = curBox;
	var boxUl = powerBox.find(".summer-column");
	var resType = $("#resType").val();
	// powerBox.find("input[name='tableId']").val(result.sqlTable.tableId);
	powerBox.find(".summer-box-title").text(result.sqlTable.tableName);
	powerBox.attr("id", result.sqlTable.tableId);
	powerBox.attr("table-id", result.sqlTable.tableId);
	boxUl.empty();

	var sqlFieldes = result.sqlTable.sqlFieldes;

	//回显保存的表里的字段
	for (var i = 0; i < sqlFieldes.length; i++){
		var fieldTypeName = '';

		if( resType == "1" ){
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
					fieldTypeName = "其它";
					break;
				default:
					fieldTypeName = "";
			}
		}
		else if( resType == "2" ) {
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
					fieldTypeName = "其它";
					break;
				default:
					fieldTypeName = "";
			}
		}
		else if(  resType == "3" ) {
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
		else if( resType == "5" ) {
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
					fieldTypeName = "其它";
					break;
				default:
					fieldTypeName = "";
			}
		}
		var html = '';
		if( sqlFieldes[i].fieldKey === "1" ) {
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
		boxUl.append(html);
	}

	//将返回的表添加到左侧菜单
	// var submenu = $(".submenu");
	// var submenuHtml = '';
	// submenuHtml += '<li>\n' +
	// 	'                                <a href="#">\n' +
	// 	'                                    <span class="fa fa-building sub-icon" aria-hidden="true"></span>\n' +
	// 	'                                    <span class="sub-name">'+ result.sqlTable.tableName +'</span>\n' +
	// 	'                                    <span class="sub-delete">删除</span>\n' +
	// 	'                                    <input hidden="hidden" type="text" value="'+result.sqlTable.tableId+'">\n' +
	// 	'                                </a>\n' +
	// 	'                            </li>';
	// submenu.append(submenuHtml);
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

//表内容的保存
function dataModalSave( curBox, saveType ) {


	//获取所点击power-class-box类盒子的左边距以及上边距
	var tableX = curBox.position().left;
	var tableY = curBox.position().top;

	var content = $("#newTableModal");

	var resType = $("#resType").val(); //数据源类型
	var tableTypeId = $("#tableTypeId").val(); //表类型Id

	var tableNameInput = content.find("input[name='tableName']");
	var tableName = tableNameInput.val(); //表名

	var tableSqlNameInput = content.find("input[name='tableSqlName']");
	var tableSqlName = tableSqlNameInput.val(); //表英文名

	var dataResId = $("#dataResId").val(); //数据源Id
	var tableResume = content.find("input[name='tableResume']").val(); //表注释
	var sortId = content.find("input[name='sortId']").val(); //序号
	var keyWords = "";
	var patt = /^[0-9A-Z]{1,}$/;

	if( saveType === "edit" || saveType === null ) {
		tableTypeId = curBox.data("boxDetail").tableTypeId;
		dataResId = curBox.data("boxDetail").dataResId;
	}

	if( tableName === "" ){
		$.message({
			message:'表名不能为空！',
			type:'error'
		});
		canvasTab.open($("#basic"));
		return;
	}

	if( tableSqlName === "" ){
		$.message({
			message:'表英文名不能为空！',
			type:'error'
		});
		canvasTab.open($("#basic"));
		return;
	}

	if( saveType === "edit" || saveType === null ){
		if( isInArray( tableSqlName, tableNames, 'edit' ) ){
			//表名已存在
			$.message({
				message:'表名重复！',
				type:'error'
			});
			canvasTab.open($("#basic"));
			return;
		}
	} else {
		if( isInArray( tableSqlName, tableNames, 'edit' ) ){
			//表英文名已存在
			$.message({
				message:'表英文名重复！',
				type:'error'
			});
			canvasTab.open($("#basic"));
			return;
		}
	}

	if( resType === "1" ){
	  //mysql
	  keyWords = mysqlKeyWords;
	} else if( resType === "2" ){
		//oracle
		keyWords = oracleKeyWords;
	} else if( resType === "3" ){
		//db2
		keyWords = db2KeyWords;
	} else if( resType === "5" ){
		//sersqlServer
		keyWords = sqlServerKeyWords;
	}

	keyWords = keyWords.replace(/\s+/g, ' ');
	keyWords = keyWords.replace(/(^\s*)|(\s*$)/g, '');
	keyWords = keyWords.split(" ");

	if( isInArray( tableName, keyWords ) ){
		$.message({
			message:'表名不能为关键字！',
			type:'error'
		});
		canvasTab.open($("#basic"));
		return;
	}else if( isInArray( tableSqlName, keyWords ) ){
		$.message({
			message:'表英文名不能为关键字！',
			type:'error'
		});
		canvasTab.open($("#basic"));
		return;
	}

	// if( resType === "1" ){
	// 	//mysql
	// 	var patterName = /^[0-9a-z]{1,}$/;
	// 	if( !patterName.test(tableSqlName)  ){
	// 		$.message({
	// 			message:'mysql表英文名只能为小写字母加数字组合',
	// 			type:'error'
	// 		});
	// 		canvasTab.open($("#basic"));
	// 		return;
	// 	}
	// }

	//校验字段名
	if( $("#resType").val() === "2" || $("#resType").val() === "3" ) {
		if( !patt.test(tableSqlName) ){
			$.message({
				message:'Oracle或DB2类型的数据源表英文名称只能由大写字母、数字组成！',
				type:'error'
			});
			canvasTab.open($("#basic"));
			return;
		}
	}

	//提交的数据
	var subData = {
		"tableName": tableName,
		"tableSqlName": tableSqlName,
		"dataResId": dataResId,
		"tableTypeId": tableTypeId,
		"tableResume": tableResume,
		"tableY": tableY,
		"tableX": tableX,
		"sortId": sortId
	};


	if( saveType === "edit" || saveType === null ) {
		subData["tableId"] = curBox.data("boxDetail").tableId;
		if( curBox.data("boxDetail").sqlShape ){
			subData["sqlShape[0].shapeId"] = curBox.data("boxDetail").sqlShape[0].shapeId;
			subData["sqlShape[0].tableId"] = curBox.data("boxDetail").tableId;
		}
	}

	//获取表字段
	var tr = content.find("table > tbody > tr");

	for (var i = 0; i < tr.length; i++) {

		var fieldSqlNameInput = $(tr[i]).find("input[name='fieldSqlName']");
		var fieldNameInput = $(tr[i]).find("input[name='fieldName']");

		var fieldName = fieldNameInput.val();//字段名
		var fieldSqlName = fieldSqlNameInput.val();//字段英文名
		var fieldType = $(tr[i]).find("select[name='fieldType']").val();//字段类型值
		var dateType = $($(tr[i]).find("select[name='fieldType']").parents("td")[0]).find(".inner-text").text();//字段类型名
		var fieldLen = $(tr[i]).find("input[name='fieldLen']").val();//字段长度
		var fieldDigit = $(tr[i]).find("input[name='fieldDigit']").val();//小数点位数
		var FieldKey = $(tr[i]).find("input[name='FieldKey']").is(':checked') ? "1" : "0";//是否主键
		var sortIndex = $(tr[i]).find("input[name='sortIndex']").is(':checked') ? "1" : "0";//是否索引
		var isNull = $(tr[i]).find("input[name='isNull']").is(':checked') ? "0" : "1";//是否为空
		var fieldResume = $(tr[i]).find("input[name='fieldResume']").val();//说明

		if( fieldName === "" ){
			fieldNameInput.css("display","block");
			fieldNameInput.parent().find(".inner-text").css("display","none");
			$.message({
				message:'字段名称不能为空！',
				type:'error'
			});
			canvasTab.open($("#column"));
			return;
		}

		if( fieldSqlName === "" ){
			fieldSqlNameInput.css("display","block");
			fieldSqlNameInput.parent().find(".inner-text").css("display","none");
			$.message({
				message:'字段英文名称不能为空！',
				type:'error'
			});
			canvasTab.open($("#column"));
			return;
		}

		if( ( isInArray(fieldName.toUpperCase(), keyWords) ) || (isInArray(fieldSqlName.toUpperCase(), keyWords)) ){
			$.message({
				message:'字段名不能为关键字！',
				type:'error'
			});
			canvasTab.open($("#column"));
			return;
		}
		
		var testResult = patt.test(fieldSqlName);
		if( $("#resType").val() === "2" || $("#resType").val() === "3" ) {
			if( !testResult ){
				$.message({
					message:'Oracle或DB2类型的数据源表字段英文名称只能由大写字母、数字组成！',
					type:'error'
				});
				canvasTab.open($("#column"));
				return;
			}
		}

		if( resType === "1" ){
			//mysql
			switch (fieldType) {
				case "1":
					dateType = "float";
					break;
				case "2":
					dateType = "varchar";
					break;
				case "3":
					dateType = "datetime";
					break;
				case "4":
					dateType = "longtext";
					break;
				case "5":
					dateType = "blob";
					break;
				case "6":
					dateType = "int";
					break;
				case "7":
					dateType = "bigint";
					break;
				case "9":
					dateType = "tinyint";
					break;
				case "10":
					dateType = "其它";
					break;
				default:
					dateType = "";
			}
		}
		else if( resType === "2" ) {
			//oracle
			switch (fieldType) {
				case "1":
					dateType = "float";
					break;
				case "2":
					dateType = "varchar2";
					break;
				case "3":
					dateType = "date";
					break;
				case "4":
					dateType = "blob";
					break;
				case "6":
					dateType = "number";
					break;
				case "10":
					dateType = "其它";
					break;
				default:
					dateType = "";
			}
		}
		else if(  resType === "3" ) {
			//db2
			switch (fieldType) {
				case "1":
					dateType = "decimal";
					break;
				case "2":
					dateType = "varchar";
					break;
				case "3":
					dateType = "timestamp";
					break;
				case "4":
					dateType = "clob";
					break;
				case "5":
					dateType = "blob";
					break;
				case "6":
					dateType = "int";
					break;
				case "7":
					dateType = "bigint";
					break;
				case "9":
					dateType = "boolean";
					break;
				case "10":
					dateType = "其它";
					break;
				default:
					dateType = "";
			}
		}
		else if( resType === "5" ) {
			//sqlserver
			switch (fieldType) {
				case "1":
					dateType = "float";
					break;
				case "2":
					dateType = "varchar";
					break;
				case "3":
					dateType = "datetime";
					break;
				case "4":
					dateType = "nvarchar(max)";
					break;
				case "5":
					dateType = "varbinary(max)";
					break;
				case "6":
					dateType = "int";
					break;
				case "7":
					dateType = "bigint";
					break;
				case "9":
					dateType = "bit";
					break;
				case "10":
					dateType = "其它";
					break;
				default:
					dateType = "";
			}
		}

		subData["sqlFieldes[" + i + "].fieldName"] = fieldName;
		subData["sqlFieldes[" + i + "].fieldSqlName"] = fieldSqlName;
		subData["sqlFieldes[" + i + "].fieldType"] = fieldType;
		subData["sqlFieldes[" + i + "].dateType"] = dateType;
		subData["sqlFieldes[" + i + "].fieldLen"] = fieldLen;
		subData["sqlFieldes[" + i + "].fieldDigit"] = fieldDigit;
		subData["sqlFieldes[" + i + "].FieldKey"] = FieldKey;
		subData["sqlFieldes[" + i + "].sortIndex"] = sortIndex;
		subData["sqlFieldes[" + i + "].isNull"] = isNull;
		subData["sqlFieldes[" + i + "].fieldResume"] = fieldResume;

		if( saveType === "edit" || saveType === null ) {
			if( $(tr[i]).find("input[name='fieldId']").length > 0 ){
				subData["sqlFieldes[" + i + "].fieldId"] = $(tr[i]).find("input[name='fieldId']").val();
			}
			subData["sqlFieldes[" + i + "].tableId"] = curBox.data("boxDetail").tableId;
		}
	}

	//封装表的设置内容
	var shapeTypeSelect = $("#shapeType-option"); //操作类型select
	var subArea = $(".sub-area"); //分区
	var subMeter = $(".sub-meter"); //分表
	var subTreasury = $(".sub-treasury"); //分库

	if( shapeTypeSelect.val() === "" ){

	}
	else if( shapeTypeSelect.val() === "1" ){
		//分区
		subData["sqlShape[0].shapeType"] = shapeTypeSelect.val();
		subData["sqlShape[0].state"] = subArea.find("input[name='state']").eq(0).is(":checked") ? "0" : "1";
		subData["sqlShape[0].partitionValue"] = subArea.find("textarea[name='partitionValue']").val();
	}
	else if( shapeTypeSelect.val() === "2" ){
		//分表
		subData["sqlShape[0].shapeType"] = shapeTypeSelect.val();

		var innerFieldSqlName = subMeter.find("select[name='fields']").val(); //字段名
		var innerSubTableType = subMeter.find("select[name='types']").val(); //类型
		subData["sqlShape[0].sqlSubTable.subTableType"] = innerSubTableType;
		subData["sqlShape[0].sqlSubTable.fieldSqlName"] = innerFieldSqlName;
		if( innerSubTableType === "1" ){
			//date
			subData["sqlShape[0].sqlSubTable.subTableTypeValue1"] = subMeter.find("select[name='dates']").val();
		}else if( innerSubTableType === "2" ){
			//string
			subData["sqlShape[0].sqlSubTable.subTableTypeValue1"] = subMeter.find("input[name='subTableTypeValue1']").val();
			subData["sqlShape[0].sqlSubTable.subTableTypeValue2"] = subMeter.find("input[name='subTableTypeValue2']").val();
		}else if( innerSubTableType === "3" ){
			//数值
			subData["sqlShape[0].sqlSubTable.subTableTypeValue1"] = subMeter.find("input[name='subTableTypeValue1']").val();
		}
	}
	else if( shapeTypeSelect.val() === "3" ){
		//分库
		subData["sqlShape[0].shapeType"] = shapeTypeSelect.val();

		var innerFieldSqlName = subTreasury.find("select[name='fields']").val(); //字段名
		var innerSubTableType = subTreasury.find("select[name='types']").val(); //类型
		var typeChangeContent = $(".typeChange-content");

		subData["sqlShape[0].sqlSubTreasury.subTreasuryType"] = innerSubTableType;
		subData["sqlShape[0].sqlSubTreasury.fieldSqlName"] = innerFieldSqlName;
		if( innerSubTableType === "3" ){
			//date
			subData["sqlShape[0].sqlSubTreasury.typeValue1"] = typeChangeContent.find("select[name='dates']").val();
		}else if( innerSubTableType === "2" ){
			//数值
			subData["sqlShape[0].sqlSubTreasury.typeValue1"] = typeChangeContent.find("input[name='subTableTypeValue1']").val();
			subData["sqlShape[0].sqlSubTreasury.typeValue2"] = typeChangeContent.find("input[name='subTableTypeValue2']").val();
		}else if( innerSubTableType === "1" ){
			//string
			subData["sqlShape[0].sqlSubTreasury.typeValue1"] = typeChangeContent.find("input[name='subTableTypeValue1']").val()
		}

		//获取子库list
		var sqlSubTreasuryDivision = subTreasury.find("select[name='sqlSubTreasuryDivision']").val();
		var treasuryForms = $("#dataInputs").find(".form-group");
		for( var p = 0; p < sqlSubTreasuryDivision.length; p++ ){
			subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].dataResId"] = sqlSubTreasuryDivision[p];

			if(innerSubTableType === "2"){
				subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].typeValue1"] = $(treasuryForms[p]).find("input[name='typeValue1']").val();
				subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].typeValue2"] = $(treasuryForms[p]).find("input[name='typeValue2']").val();
			} else{
				subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].typeValue1"] = $(treasuryForms[p]).find("input[name='typeValue1']").val();
			}
		}
	}

	$("#newTableModal").modal("hide");

	$.ajax({
		url: curContext + '/v1/sqlModel/saveSqlModel',
		data: subData,

		success: function (result) {
			if( result.code === "000000" ){
				$.message('保存成功！');
				var node = null;
				var zTree = $.fn.zTree.getZTreeObj("curTree");
				curBox.data("boxDetail",result.sqlTable);
				if( saveType === "edit" || saveType === "save" ) {
					callBackReBuildBox(result,curBox);
					if( saveType === "save" ) {
						node = zTree.getNodeByParam( "id", result.sqlTable.tableTypeId );
						zTree.addNodes( node, { id: result.sqlTable.tableId, name: result.sqlTable.tableName, pId: result.sqlTable.tableTypeId, dataType: 'table', iconSkin: 'table', open:true  } );
					} else if( saveType === "edit" ) {
						node = zTree.getNodeByParam("id", result.sqlTable.tableId);
						node.name = result.sqlTable.tableName;
						zTree.updateNode(node);
					}
				}else {

				}
			}else {
				$.message({
					message:'保存失败',
					type:'error'
				});
			}
		}
	});
}

//每行的两边的点击事件
function circleMousedown(obj, e) {
	var curCircle = obj;
	var e = e || window.event;

	var outerParent = $(curCircle).parent().parent().parent().parent();
	var curLi = $(curCircle).parent().parent();
	var fromTableId = outerParent.attr("id");
	var fromField = curLi.find(".box-li-name").text().trim();

	var fromX = 0;
	var fromY = 0;

	//判断点的是左边还是右边
	if( $(curCircle).hasClass("left-circle") ) {
		fromX = outerParent[0].offsetLeft;
		fromY = outerParent[0].offsetTop + curLi[0].offsetTop + curLi.height()/2 + 1.5;
	} else if( $(curCircle).hasClass("right-circle") ) {
		fromX = outerParent[0].offsetLeft + outerParent.width() + 3;
		fromY = outerParent[0].offsetTop + curLi[0].offsetTop + curLi.height()/2 + 1.5;
	}

	$(".power-canvas-container").unbind('mousedown');
	$(".power-canvas-container").unbind('mouseup');
	$(".power-tool-item").removeClass("tool-item-active");
	$("#container").css("cursor", "default");

	var curLine = null;
	var lineId = getuuid(16,16);

	$("#container").append(
		"<div id='"+ lineId +"' class='line_box_container' style='position: absolute;'>" +
		"<canvas></canvas>" +
		"</div>"
	);

	var lineContainerLeft = 0; //线container左边距
	var lineContainerTop = 0; //线container上边距
	var lineContainerWidth = 0; //线container左边距
	var lineContainerHeight = 0; //线container左边距

	$(".power-canvas-container").mousemove(function (e) {
		var e = e || window.event;
		$("#container").css("cursor", "crosshair");

		var toX = e.clientX - $("#power-container")[0].offsetLeft + $("#power-container")[0].scrollLeft;
		var toY = e.clientY - $(".power-main")[0].offsetTop + $("#power-container")[0].scrollTop;

		ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

		if( $(e.target).hasClass("box-item-circle") ) {
			var toOuterParent = $(e.target).parent().parent().parent().parent();
			var toLi = $(e.target).parent().parent();

			if( $(curCircle).hasClass("left-circle") ) {
				//起点为左边的点

				if( $(e.target).hasClass("left-circle") ) {
					toX = toOuterParent[0].offsetLeft;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					drawSameLeftArrow(
						ctx1,
						fromX,
						fromY,
						toX,
						toY,
						30,
						10,
						2,
						'#96d6fc',
						outerParent[0],
						toOuterParent[0]
					);
				}
				else if( $(e.target).hasClass("right-circle") ) {
					toX = toOuterParent[0].offsetLeft + toOuterParent.width() + 3;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					drawLeftArrow(
						ctx1,
						fromX,
						fromY,
						toX,
						toY,
						30,
						10,
						2,
						'#96d6fc',
						outerParent[0],
						toOuterParent[0]
					);
				}
			}
			else if( $(curCircle).hasClass("right-circle") ) {
				//起点为右边的点

				if( $(e.target).hasClass("left-circle") ) {
					toX = toOuterParent[0].offsetLeft;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					drawArrow(
						ctx1,
						fromX,
						fromY,
						toX,
						toY,
						30,
						10,
						2,
						'#96d6fc',
						outerParent[0],
						toOuterParent[0]
					);

				}
				else if( $(e.target).hasClass("right-circle") ) {
					toX = toOuterParent[0].offsetLeft + toOuterParent.width() + 3;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					drawSameRightArrow(
						ctx1,
						fromX,
						fromY,
						toX,
						toY,
						30,
						10,
						2,
						'#96d6fc',
						outerParent[0],
						toOuterParent[0]
					);
				}
			}
		}
		else {
			if( $(curCircle).hasClass("left-circle") ) {
				drawLeftArrow(
					ctx1,
					fromX,
					fromY,
					toX,
					toY,
					30,
					10,
					2,
					'#96d6fc',
					outerParent[0]
				);
			}
			else {
				drawArrow(
					ctx1,
					fromX,
					fromY,
					toX,
					toY,
					30,
					10,
					2,
					'#96d6fc',
					outerParent[0]
				);
			}
		}
	});

	$(".power-canvas-container").mouseup(function (e) {
		$(this).unbind("mousemove");
		$(this).unbind("mouseup");
		$("#container").css("cursor", "default");
		var lineUUID = getuuid(16,16);
		var curLineDetail = {
			"id": lineUUID,
			"from": {
				"x": 0,
				"y": 0
			},
			"to":{
				"x": 0,
				"y": 0
			},
			"turnPoints":[]
		};

		var e = e || window.event;
		var toCircle = null;

		var toX = e.clientX - $("#power-container")[0].offsetLeft + $("#power-container")[0].scrollLeft;
		var toY = e.clientY - $(".power-main")[0].offsetTop + $("#power-container")[0].scrollTop;

		var toOuterParent = null;
		var toLi = null;

		curLine = $("#" + lineId );

		if( $(e.target).hasClass("box-item-circle") ) {

			toOuterParent = $(e.target).parent().parent().parent().parent();
			toLi = $(e.target).parent().parent();
			toCircle = e.target;

			if( toLi.find("input[name='fieldKey']").val() == "0" ) {
				layer.msg('目标字段必须为主键！', {icon: 2});
				ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);
				return;
			} else if( toLi.find("input[name='fieldKey']").val() == "1" ) {
				if( toLi.find(".box-li-type").text() != curLi.find(".box-li-type").text() ) {
					layer.msg('关联字段类型与主字段类型必须一致！', {icon: 2});
					ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);
					return;
				}
			}

			var toTbaleId = toOuterParent.attr("id");
			var toField = toLi.find(".box-li-name").text().trim();

			$(curCircle).addClass("circle-inline");
			$(toCircle).addClass("circle-inline");

			if( $(curCircle).hasClass("left-circle") ){

				if( $(e.target).hasClass("right-circle") ){
					toX = toOuterParent[0].offsetLeft + toOuterParent.width() + 3 ;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					if( toX - fromX < 0 ){

						lineContainerWidth = Math.abs( toX - fromX ) + 20;
						lineContainerHeight = Math.abs( toY -  fromY ) + 20;
						lineContainerLeft = toX - 10;
						if( toY - fromY > 0 ){
							//右下
							lineContainerTop = fromY - 10;
						}else{
							//右上
							lineContainerTop = toY - 10;
						}
						curLine[0].style.width = lineContainerWidth + 'px';
						curLine[0].style.height = lineContainerHeight + 'px';
						curLine[0].style.left = lineContainerLeft + 'px';
						curLine[0].style.top = lineContainerTop + 'px';
						curLine.find("canvas")[0].width = lineContainerWidth;
						curLine.find("canvas")[0].height = lineContainerHeight;

						ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

						var lineCtx = curLine.find("canvas")[0].getContext("2d");

						var line1CanvasFromX = fromX - curLine[0].offsetLeft;
						var line1CanvasFromY = fromY - curLine[0].offsetTop;

						var line1CanvasToX = toX - curLine[0].offsetLeft;
						var line1CanvasToY = toY - curLine[0].offsetTop;

						//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
						curLine.attr("fromBoxId", outerParent.attr("table-id") );
						curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
						curLine.attr("toBoxId", toOuterParent.attr("table-id") );
						curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
						curLine.attr("fromCircle", "left" );
						curLine.attr("toCircle", "right" );

						curLineDetail.from.x = line1CanvasFromX;
						curLineDetail.from.y = line1CanvasFromY;
						curLineDetail.to.x = line1CanvasToX;
						curLineDetail.to.y = line1CanvasToY;

						drawLeftArrow(
							lineCtx,
							line1CanvasFromX,
							line1CanvasFromY,
							line1CanvasToX,
							line1CanvasToY,
							30,
							10,
							2,
							'#96d6fc',
							null,
							null,
							curLineDetail
						);
					} else if ( toX - fromX > 0 ){

						//终点在起点之下
						if( toY - fromY > 0 ) {

							var turn1 = {
								x: fromX - 30 ,
								y: fromY
							};

							var turn2 = {
								x: turn1.x,
								y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() )
							};

							var turn3 = {
								x: toX + 30,
								y: turn2.y
							};

							var turn4 = {
								x: turn3.x,
								y: toY
							};

							lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = turn1.x - 10;
							lineContainerTop = fromY - 10;

							curLine[0].style.width = lineContainerWidth + 'px';
							curLine[0].style.height = lineContainerHeight + 'px';
							curLine[0].style.left = lineContainerLeft + 'px';
							curLine[0].style.top = lineContainerTop + 'px';
							curLine.find("canvas")[0].width = lineContainerWidth;
							curLine.find("canvas")[0].height = lineContainerHeight;

							ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							var lineCtx = curLine.find("canvas")[0].getContext("2d");

							//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							curLine.attr("fromBoxId", outerParent.attr("table-id") );
							curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							curLine.attr("fromCircle", "left" );
							curLine.attr("toCircle", "right" );

							var turn11 = {
								x: fromX - 30 - lineContainerLeft,
								y: fromY - lineContainerTop
							};

							var turn22 = {
								x: turn1.x - lineContainerLeft,
								y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() ) - lineContainerTop
							};

							var turn33 = {
								x: toX + 30 - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};

							var turn44 = {
								x: turn3.x - lineContainerLeft,
								y: toY - lineContainerTop
							};

							curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
							curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
							curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

							curLineDetail.from.x = fromX- lineContainerLeft;
							curLineDetail.from.y = fromY - lineContainerTop;
							curLineDetail.to.x = toX - lineContainerLeft;
							curLineDetail.to.y = toY - lineContainerTop;

							linesArray.push(curLineDetail);

							var arr = [];
							arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							arr.push({"x":turn11.x, "y": turn11.y});
							arr.push({"x":turn22.x, "y": turn22.y});
							arr.push({"x":turn33.x, "y": turn33.y});
							arr.push({"x":turn44.x, "y": turn44.y});
							arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							drawInArrow(
								lineCtx,
								30,
								10,
								2,
								'#96d6fc',
								arr
							);
						} else if( toY - fromY < 0 ){
							//终点位于起点之上
							var turn1 = {
								x: fromX - 30 ,
								y: fromY
							};

							var turn2 = {
								x: turn1.x,
								y: ( outerParent[0].offsetTop - ( toOuterParent[0].offsetTop + $(toOuterParent).height() ) )/2 + ( toOuterParent[0].offsetTop + $(toOuterParent).height() )
							};

							var turn3 = {
								x: toX + 30,
								y: turn2.y
							};

							var turn4 = {
								x: turn3.x,
								y: toY
							};

							lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = turn1.x - 10;
							lineContainerTop = toY - 10;

							curLine[0].style.width = lineContainerWidth + 'px';
							curLine[0].style.height = lineContainerHeight + 'px';
							curLine[0].style.left = lineContainerLeft + 'px';
							curLine[0].style.top = lineContainerTop + 'px';
							curLine.find("canvas")[0].width = lineContainerWidth;
							curLine.find("canvas")[0].height = lineContainerHeight;

							ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							var lineCtx = curLine.find("canvas")[0].getContext("2d");

							//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							curLine.attr("fromBoxId", outerParent.attr("table-id") );
							curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							curLine.attr("fromCircle", "left" );
							curLine.attr("toCircle", "right" );

							var turn11 = {
								x: fromX - 30 - lineContainerLeft,
								y: fromY - lineContainerTop
							};

							var turn22 = {
								x: turn1.x - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};

							var turn33 = {
								x: toX + 30 - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};

							var turn44 = {
								x: turn3.x - lineContainerLeft,
								y: toY - lineContainerTop
							};

							curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
							curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
							curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

							curLineDetail.from.x = fromX- lineContainerLeft;
							curLineDetail.from.y = fromY - lineContainerTop;
							curLineDetail.to.x = toX - lineContainerLeft;
							curLineDetail.to.y = toY - lineContainerTop;

							linesArray.push(curLineDetail);

							var arr = [];
							arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							arr.push({"x":turn11.x, "y": turn11.y});
							arr.push({"x":turn22.x, "y": turn22.y});
							arr.push({"x":turn33.x, "y": turn33.y});
							arr.push({"x":turn44.x, "y": turn44.y});
							arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							drawInArrow(
								lineCtx,
								30,
								10,
								2,
								'#96d6fc',
								arr
							);
						}
					}
				}
				else if( $(e.target).hasClass("left-circle") ) {
					toX = toOuterParent[0].offsetLeft  ;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					//左连左
					if( toX - fromX > 0 ) {
						var turn1 = {
							x: fromX - 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y:toY
						};

						lineContainerWidth = Math.abs( toX - turn1.x ) + 20;
						lineContainerHeight = Math.abs( toY -  fromY ) + 20;
						lineContainerLeft = fromX - 10 - 30;
						if( toY - fromY > 0 ) {
							//右下
							lineContainerTop = fromY - 10;
						} else {
							//右上
							lineContainerTop = toY - 10;
						}
						curLine[0].style.width = lineContainerWidth + 'px';
						curLine[0].style.height = lineContainerHeight + 'px';
						curLine[0].style.left = lineContainerLeft + 'px';
						curLine[0].style.top = lineContainerTop + 'px';
						curLine.find("canvas")[0].width = lineContainerWidth;
						curLine.find("canvas")[0].height = lineContainerHeight;

						ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

						var lineCtx = curLine.find("canvas")[0].getContext("2d");

						var line1CanvasFromX = fromX - curLine[0].offsetLeft;
						var line1CanvasFromY = fromY - curLine[0].offsetTop;

						var line1CanvasToX = toX - curLine[0].offsetLeft;
						var line1CanvasToY = toY - curLine[0].offsetTop;

						//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
						curLine.attr("fromBoxId", outerParent.attr("table-id") );
						curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
						curLine.attr("toBoxId", toOuterParent.attr("table-id") );
						curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
						curLine.attr("fromCircle", "left" );
						curLine.attr("toCircle", "left" );

						var turn11 = {
							x: turn1.x - lineContainerLeft,
							y: turn1.y - lineContainerTop
						};

						var turn22 = {
							x: turn2.x - lineContainerLeft,
							y: turn2.y - lineContainerTop
						};

						curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
						curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

						curLineDetail.from.x = fromX- lineContainerLeft;
						curLineDetail.from.y = fromY - lineContainerTop;
						curLineDetail.to.x = toX - lineContainerLeft;
						curLineDetail.to.y = toY - lineContainerTop;

						linesArray.push(curLineDetail);

						var arr = [];
						arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
						arr.push({"x":turn11.x, "y": turn11.y});
						arr.push({"x":turn22.x, "y": turn22.y});
						arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

						drawInArrow(
							lineCtx,
							30,
							10,
							2,
							'#96d6fc',
							arr
						);
					}
					else if( toX - fromX <= 0 ) {
						var turn1 = {
							x: toX - 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y:toY
						};

						lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
						lineContainerHeight = Math.abs( toY -  fromY ) + 20;
						lineContainerLeft = turn1.x - 10;
						if( toY - fromY > 0 ) {
							//右下
							lineContainerTop = fromY - 10;
						} else {
							//右上
							lineContainerTop = toY - 10;
						}
						curLine[0].style.width = lineContainerWidth + 'px';
						curLine[0].style.height = lineContainerHeight + 'px';
						curLine[0].style.left = lineContainerLeft + 'px';
						curLine[0].style.top = lineContainerTop + 'px';
						curLine.find("canvas")[0].width = lineContainerWidth;
						curLine.find("canvas")[0].height = lineContainerHeight;

						ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

						var lineCtx = curLine.find("canvas")[0].getContext("2d");

						var line1CanvasFromX = fromX - curLine[0].offsetLeft;
						var line1CanvasFromY = fromY - curLine[0].offsetTop;

						var line1CanvasToX = toX - curLine[0].offsetLeft;
						var line1CanvasToY = toY - curLine[0].offsetTop;

						//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
						curLine.attr("fromBoxId", outerParent.attr("table-id") );
						curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
						curLine.attr("toBoxId", toOuterParent.attr("table-id") );
						curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
						curLine.attr("fromCircle", "left" );
						curLine.attr("toCircle", "left" );

						var turn11 = {
							x: turn1.x - lineContainerLeft,
							y: turn1.y - lineContainerTop
						};

						var turn22 = {
							x: turn2.x - lineContainerLeft,
							y: turn2.y - lineContainerTop
						};

						curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
						curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

						curLineDetail.from.x = fromX- lineContainerLeft;
						curLineDetail.from.y = fromY - lineContainerTop;
						curLineDetail.to.x = toX - lineContainerLeft;
						curLineDetail.to.y = toY - lineContainerTop;

						linesArray.push(curLineDetail);

						var arr = [];
						arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
						arr.push({"x":turn11.x, "y": turn11.y});
						arr.push({"x":turn22.x, "y": turn22.y});
						arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

						drawInArrow(
							lineCtx,
							30,
							10,
							2,
							'#96d6fc',
							arr
						);
					}
				}
			}
			else if( $(curCircle).hasClass("right-circle") ) {

				if( $(e.target).hasClass("left-circle") ) {
					toX = toOuterParent[0].offsetLeft;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					if( toX - fromX > 0 ) {

						lineContainerWidth = Math.abs( toX - fromX ) + 20;
						lineContainerHeight = Math.abs( toY -  fromY ) + 20;
						lineContainerLeft = fromX - 10;
						if( toY - fromY > 0 ) {
							//右下
							lineContainerTop = fromY - 10;
						} else {
							//右上
							lineContainerTop = toY - 10;
						}
						curLine[0].style.width = lineContainerWidth + 'px';
						curLine[0].style.height = lineContainerHeight + 'px';
						curLine[0].style.left = lineContainerLeft + 'px';
						curLine[0].style.top = lineContainerTop + 'px';
						curLine.find("canvas")[0].width = lineContainerWidth;
						curLine.find("canvas")[0].height = lineContainerHeight;

						ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

						var lineCtx = curLine.find("canvas")[0].getContext("2d");

						var line1CanvasFromX = fromX - curLine[0].offsetLeft;
						var line1CanvasFromY = fromY - curLine[0].offsetTop;

						var line1CanvasToX = toX - curLine[0].offsetLeft;
						var line1CanvasToY = toY - curLine[0].offsetTop;

						//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
						curLine.attr("fromBoxId", outerParent.attr("table-id") );
						curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
						curLine.attr("toBoxId", toOuterParent.attr("table-id") );
						curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
						curLine.attr("fromCircle", "right" );
						curLine.attr("toCircle", "left" );

						curLineDetail.from.x = line1CanvasFromX;
						curLineDetail.from.y = line1CanvasFromY;
						curLineDetail.to.x = line1CanvasToX;
						curLineDetail.to.y = line1CanvasToY;

						drawArrow(
							lineCtx,
							line1CanvasFromX,
							line1CanvasFromY,
							line1CanvasToX,
							line1CanvasToY,
							30,
							10,
							2,
							'#96d6fc',
							null,
							null,
							curLineDetail
						);
					} else if ( toX - fromX < 0 ) {

						//终点在起点之下
						if( toY - fromY > 0 ) {

							var turn1 = {
								x: fromX + 30 ,
								y: fromY
							};

							var turn2 = {
								x: turn1.x,
								y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() )
							};

							var turn3 = {
								x: toX - 30,
								y: turn2.y
							};

							var turn4 = {
								x: turn3.x,
								y: toY
							};

							lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = turn3.x - 10;
							lineContainerTop = fromY - 10;

							curLine[0].style.width = lineContainerWidth + 'px';
							curLine[0].style.height = lineContainerHeight + 'px';
							curLine[0].style.left = lineContainerLeft + 'px';
							curLine[0].style.top = lineContainerTop + 'px';
							curLine.find("canvas")[0].width = lineContainerWidth;
							curLine.find("canvas")[0].height = lineContainerHeight;

							ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							var lineCtx = curLine.find("canvas")[0].getContext("2d");

							//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							curLine.attr("fromBoxId", outerParent.attr("table-id") );
							curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							curLine.attr("fromCircle", "right" );
							curLine.attr("toCircle", "left" );

							var turn11 = {
								x: fromX + 30 - lineContainerLeft,
								y: fromY - lineContainerTop
							};

							var turn22 = {
								x: turn1.x - lineContainerLeft,
								y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() ) - lineContainerTop
							};

							var turn33 = {
								x: toX - 30 - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};

							var turn44 = {
								x: turn3.x - lineContainerLeft,
								y: toY - lineContainerTop
							};

							curLineDetail.from.x = fromX- lineContainerLeft;
							curLineDetail.from.y = fromY - lineContainerTop;
							curLineDetail.to.x = toX - lineContainerLeft;
							curLineDetail.to.y = toY - lineContainerTop;

							curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
							curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
							curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

							linesArray.push(curLineDetail);

							var arr = [];
							arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							arr.push({"x":turn11.x, "y": turn11.y});
							arr.push({"x":turn22.x, "y": turn22.y});
							arr.push({"x":turn33.x, "y": turn33.y});
							arr.push({"x":turn44.x, "y": turn44.y});
							arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							drawInArrow(
								lineCtx,
								30,
								10,
								2,
								'#96d6fc',
								arr
							);
						} else if( toX - fromX < 0 ){
							//终点位于起点之上
							var turn1 = {
								x: fromX + 30 ,
								y: fromY
							};

							var turn2 = {
								x: turn1.x,
								y: ( outerParent[0].offsetTop - ( toOuterParent[0].offsetTop + $(toOuterParent).height() ) )/2 + ( toOuterParent[0].offsetTop + $(toOuterParent).height() )
							};

							var turn3 = {
								x: toX - 30,
								y: turn2.y
							};

							var turn4 = {
								x: turn3.x,
								y: toY
							};

							lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = turn3.x - 10;
							lineContainerTop = toY - 10;

							curLine[0].style.width = lineContainerWidth + 'px';
							curLine[0].style.height = lineContainerHeight + 'px';
							curLine[0].style.left = lineContainerLeft + 'px';
							curLine[0].style.top = lineContainerTop + 'px';
							curLine.find("canvas")[0].width = lineContainerWidth;
							curLine.find("canvas")[0].height = lineContainerHeight;

							ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							var lineCtx = curLine.find("canvas")[0].getContext("2d");

							//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							curLine.attr("fromBoxId", outerParent.attr("table-id") );
							curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							curLine.attr("fromCircle", "right" );
							curLine.attr("toCircle", "left" );

							var turn11 = {
								x: fromX + 30 - lineContainerLeft,
								y: fromY - lineContainerTop
							};

							var turn22 = {
								x: turn1.x - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};

							var turn33 = {
								x: toX - 30 - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};

							var turn44 = {
								x: turn3.x - lineContainerLeft,
								y: toY - lineContainerTop
							};

							curLineDetail.from.x = fromX- lineContainerLeft;
							curLineDetail.from.y = fromY - lineContainerTop;
							curLineDetail.to.x = toX - lineContainerLeft;
							curLineDetail.to.y = toY - lineContainerTop;

							curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
							curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
							curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

							linesArray.push(curLineDetail);

							var arr = [];
							arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							arr.push({"x":turn11.x, "y": turn11.y});
							arr.push({"x":turn22.x, "y": turn22.y});
							arr.push({"x":turn33.x, "y": turn33.y});
							arr.push({"x":turn44.x, "y": turn44.y});
							arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							drawInArrow(
								lineCtx,
								30,
								10,
								2,
								'#96d6fc',
								arr
							);
						}
					}
				} else if( $(e.target).hasClass("right-circle") ) {

					toX = toOuterParent[0].offsetLeft  + toOuterParent.width() + 3;
					toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

					//右连右
					if( toX - fromX > 0 ) {
						var turn1 = {
							x: toX + 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y: toY
						};

						lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
						lineContainerHeight = Math.abs( toY -  fromY ) + 20;
						lineContainerLeft = fromX - 10;
						if( toY - fromY > 0 ) {
							//右下
							lineContainerTop = fromY - 10;
						} else {
							//右上
							lineContainerTop = toY - 10;
						}
						curLine[0].style.width = lineContainerWidth + 'px';
						curLine[0].style.height = lineContainerHeight + 'px';
						curLine[0].style.left = lineContainerLeft + 'px';
						curLine[0].style.top = lineContainerTop + 'px';
						curLine.find("canvas")[0].width = lineContainerWidth;
						curLine.find("canvas")[0].height = lineContainerHeight;

						ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

						var lineCtx = curLine.find("canvas")[0].getContext("2d");

						var line1CanvasFromX = fromX - curLine[0].offsetLeft;
						var line1CanvasFromY = fromY - curLine[0].offsetTop;

						var line1CanvasToX = toX - curLine[0].offsetLeft;
						var line1CanvasToY = toY - curLine[0].offsetTop;

						//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
						curLine.attr("fromBoxId", outerParent.attr("table-id") );
						curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
						curLine.attr("toBoxId", toOuterParent.attr("table-id") );
						curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
						curLine.attr("fromCircle", "right" );
						curLine.attr("toCircle", "right" );

						var turn11 = {
							x: turn1.x - lineContainerLeft,
							y: turn1.y - lineContainerTop
						};

						var turn22 = {
							x: turn2.x - lineContainerLeft,
							y: turn2.y - lineContainerTop
						};

						curLineDetail.from.x = fromX- lineContainerLeft;
						curLineDetail.from.y = fromY - lineContainerTop;
						curLineDetail.to.x = toX - lineContainerLeft;
						curLineDetail.to.y = toY - lineContainerTop;

						curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
						curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

						linesArray.push(curLineDetail);

						var arr = [];
						arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
						arr.push({"x":turn11.x, "y": turn11.y});
						arr.push({"x":turn22.x, "y": turn22.y});
						arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

						drawInArrow(
							lineCtx,
							30,
							10,
							2,
							'#96d6fc',
							arr
						);
					}
					else if (toX - fromX <= 0) {
						var turn1 = {
							x: fromX + 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y: toY
						};

						lineContainerWidth = Math.abs(turn1.x - toX) + 20;
						lineContainerHeight = Math.abs(toY - fromY) + 20;
						lineContainerLeft = toX - 10;
						if (toY - fromY > 0) {
							//右下
							lineContainerTop = fromY - 10;
						} else {
							//右上
							lineContainerTop = toY - 10;
						}
						curLine[0].style.width = lineContainerWidth + 'px';
						curLine[0].style.height = lineContainerHeight + 'px';
						curLine[0].style.left = lineContainerLeft + 'px';
						curLine[0].style.top = lineContainerTop + 'px';
						curLine.find("canvas")[0].width = lineContainerWidth;
						curLine.find("canvas")[0].height = lineContainerHeight;

						ctx1.clearRect(0, 0, powerCanvas.width, powerCanvas.height);

						var lineCtx = curLine.find("canvas")[0].getContext("2d");

						var line1CanvasFromX = fromX - curLine[0].offsetLeft;
						var line1CanvasFromY = fromY - curLine[0].offsetTop;

						var line1CanvasToX = toX - curLine[0].offsetLeft;
						var line1CanvasToY = toY - curLine[0].offsetTop;

						//暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
						curLine.attr("fromBoxId", outerParent.attr("table-id"));
						curLine.attr("fromField", curLi.find(".box-li-name").text().trim());
						curLine.attr("toBoxId", toOuterParent.attr("table-id"));
						curLine.attr("toField", toLi.find(".box-li-name").text().trim());
						curLine.attr("fromCircle", "right");
						curLine.attr("toCircle", "right");

						var turn11 = {
							x: turn1.x - lineContainerLeft,
							y: turn1.y - lineContainerTop
						};

						var turn22 = {
							x: turn2.x - lineContainerLeft,
							y: turn2.y - lineContainerTop
						};

						curLineDetail.from.x = fromX- lineContainerLeft;
						curLineDetail.from.y = fromY - lineContainerTop;
						curLineDetail.to.x = toX - lineContainerLeft;
						curLineDetail.to.y = toY - lineContainerTop;

						curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
						curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

						linesArray.push(curLineDetail);

						var arr = [];
						arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
						arr.push({"x":turn11.x, "y": turn11.y});
						arr.push({"x":turn22.x, "y": turn22.y});
						arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

						drawInArrow(
							lineCtx,
							30,
							10,
							2,
							'#96d6fc',
							arr
						);
					}
				}
			}
			//保存外键
			$.ajax({
				url: '<%=path%>/v1/sqlModel/createForeignkey',
				type:"post",
				data: JSON.stringify({ "mainTable": toTbaleId, "mainField": toField, "joinTable": fromTableId, "joinField": fromField, "lineStart": curLine.attr("fromCircle"), "lineEnd": curLine.attr("toCircle") }),
				dataType : "json",
				contentType:"application/json",
				success : function (result) {
					if( result.foreignKeyId ) {
						$(curLine).attr("foreignKeyId", result.foreignKeyId);
						console.log(linesArray);
						console.log(lineUUID);
						for( var s = 0; s < linesArray.length; s++ ){
							if( linesArray[s].id == ( lineUUID + '' ) ){
								linesArray[s].id = result.foreignKeyId;
							}
						}
					} else {
						layer.msg('添加外键失败！', {icon: 2});
						curLine.remove();
					}
				}
			});
			$("#container").mousemove(function (e) {
				containerMousemove(e);
			});
		} else {
			$(curLine).remove();
			ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);
			return;
		}
	});
}

//盒子移动
function classBoxMousedown(obj, e) {
	var e = e || window.event;

	var thisBox = obj;
	whichPowerMove = thisBox;

	if( $(e.target).hasClass("box-item-circle") ){
		return;
	}

	thisBox.style.cursor = "move";

	isPowerBoxMove = true;

	powerBoxX = e.clientX;
	powerBoxY = e.clientY;
	powerBoxLeft = thisBox.offsetLeft;
	powerBoxTop = thisBox.offsetTop;

	var mouseInnerLeft = e.clientX - thisBox.offsetLeft + $("#power-container").scrollLeft();
	var mouseInnerTop = e.clientY - thisBox.offsetTop + $("#power-container").scrollTop();

	if( $(e.target).hasClass("class-box-title") ){


		$("#power-container").mousemove(function (e) {

			//找到所有经过起点对应的线
			var lineContainer = $(".line_box_container");

			if( !isPowerBoxMove ){
				return;
			}

			var e = e || window.event;
			var container = $("#container");
			var nx = e.clientX;
			var ny = e.clientY;

			var nl = e.clientX + $("#power-container").scrollLeft() - mouseInnerLeft;
			var nt = e.clientY + $("#power-container").scrollTop()  - mouseInnerTop;

			whichPowerMove.style.top = nt + 'px';
			whichPowerMove.style.left = nl + 'px';

			//container的宽高
			var containerHeight = container.height();
			var containerWidth = container.width();

			if( ( nt + $(whichPowerMove).height() ) > containerHeight ) {
				container.height( containerHeight + $(whichPowerMove).height() );
				powerCanvas.height = containerHeight + $(whichPowerMove).height();
			}

			if( ( nl + $(whichPowerMove).width() ) > containerWidth ) {
				container.width( containerWidth + $(whichPowerMove).width() );
				powerCanvas.width = containerWidth + $(whichPowerMove).width();
			}

			//移动box时 相关联的线的变动
			for( var i = 0; i < lineContainer.length; i++ ) {

				var fromField = $(lineContainer[i]).attr("fromField");
				var toField = $(lineContainer[i]).attr("toField");
				var fromLi = null;
				var toLi = null;
				var fromX = 0;
				var fromY = 0;
				var toX = 0;
				var toY = 0;
				var lineContainerLeft = 0; //线container左边距
				var lineContainerTop = 0; //线container上边距
				var lineContainerWidth = 0; //线container左边距
				var lineContainerHeight = 0; //线container左边距
				var line1CanvasFromX = 0;
				var line1CanvasFromY = 0;
				var line1CanvasToX = 0;
				var line1CanvasToY = 0;
				var lineCtx = $(lineContainer[i]).find("canvas")[0].getContext("2d");
				var fromBox = null;
				var toBox = null;
				var fromCircle = $(lineContainer[i]).attr("fromcircle");
				var toCircle = $(lineContainer[i]).attr("tocircle");
				var curLineId = $(lineContainer[i]).attr("foreignkeyid");

				//共8种情况 左左右右 左右左右
				if( $(lineContainer[i]).attr("fromBoxId") === $(thisBox).attr("id") || $(lineContainer[i]).attr("toBoxId") === $(thisBox).attr("id") ) {

					if( $(lineContainer[i]).attr("fromBoxId") === $(thisBox).attr("id") ) {
						//以所点击div为起点的线

						var toBoxId = $(lineContainer[i]).attr("toBoxId");
						toBox = $("#" + toBoxId);
						fromBox = $(thisBox);

						$(thisBox).find(".box-li-name").each(function () {
							if( $(this).text() == fromField ) {
								fromLi = $(this).parent();
							}
						});
						$(toBox).find(".box-li-name").each(function () {
							if( $(this).text() == toField ) {
								toLi = $(this).parent();
							}
						});

						var curLineIndex = 0;

						for( var s=0; s<linesArray.length; s++ ){
							if( linesArray[s].id == curLineId ){
								curLineIndex = s;
								break;
							}
						}

						//计算起点终点
						if( fromCircle == "left" ) {
							fromX = $(thisBox)[0].offsetLeft;
							fromY = $(thisBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							if( toCircle == "left" ) {
								toX = toBox[0].offsetLeft;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//当前box左起点连接目标box左边的点
								//左连左
								if( toX - fromX > 0 ) {

									var turn1 = {
										x: fromX - 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y:toY
									};

									lineContainerWidth = Math.abs( toX - turn1.x ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = fromX - 10 - 30;

									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
								else if( toX - fromX <= 0 ) {

									var turn1 = {
										x: toX - 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y:toY
									};

									lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = turn1.x - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
							}
							else if( toCircle == "right" ) {
								toX = toBox[0].offsetLeft + toBox.width() + 3;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//当前box左起点连接目标box右边的点
								if( toX - fromX < 0 ) {
									//左侧

									lineContainerWidth = Math.abs( toX - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = toX - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									//计算linContainer宽高，重新划线
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									linesArray[curLineIndex].turnPoints = [];

									linesArray[curLineIndex].from.x = line1CanvasFromX;
									linesArray[curLineIndex].from.y = line1CanvasFromY;
									linesArray[curLineIndex].to.x = line1CanvasToX;
									linesArray[curLineIndex].to.y = line1CanvasToY;

									drawArrow(
										lineCtx,
										line1CanvasFromX,
										line1CanvasFromY,
										line1CanvasToX,
										line1CanvasToY,
										30,
										10,
										2,
										'#96d6fc',
										null,
										null,
										null,
										curLineIndex
									);
								}
								else if( toX - fromX >= 0 ) {
									//右侧

									//终点在起点之下
									if( toY - fromY > 0 ) {

										var turn1 = {
											x: fromX - 30,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										};

										var turn3 = {
											x: toX + 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn2.x - 10;
										lineContainerTop = fromY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX - 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										};

										var turn33 = {
											x: toX + 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);

									} else if( toY - fromY <= 0 ) {

										//终点位于起点之上
										var turn1 = {
											x: fromX - 30 ,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										};

										var turn3 = {
											x: toX + 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn1.x - 10;
										lineContainerTop = toY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX - 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn33 = {
											x: toX + 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);
									}
								}
							}
						}
						else if( fromCircle == "right" ) {

							fromX = $(thisBox)[0].offsetLeft + $(thisBox).width() + 3;
							fromY = $(thisBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							if( toCircle == "left" ) {
								toX = toBox[0].offsetLeft;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//当前box右起点连接目标box左边的点
								if( toX - fromX > 0 ) {
									//右侧

									lineContainerWidth = Math.abs( toX - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = fromX - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									//计算linContainer宽高，重新划线
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									linesArray[curLineIndex].turnPoints = [];

									linesArray[curLineIndex].from.x = line1CanvasFromX;
									linesArray[curLineIndex].from.y = line1CanvasFromY;
									linesArray[curLineIndex].to.x = line1CanvasToX;
									linesArray[curLineIndex].to.y = line1CanvasToY;

									drawArrow(
										lineCtx,
										line1CanvasFromX,
										line1CanvasFromY,
										line1CanvasToX,
										line1CanvasToY,
										30,
										10,
										2,
										'#96d6fc',
										null,
										null,
										null,
										curLineIndex
									);

								}
								else if( toX - fromX < 0 ) {

									//终点在起点之下
									if( toY - fromY > 0 ) {
										var turn1 = {
											x: fromX + 30 ,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										};

										var turn3 = {
											x: toX - 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn3.x - 10;
										lineContainerTop = fromY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX + 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										};

										var turn33 = {
											x: toX - 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);

									} else if( toY - fromY <= 0 ) {
										//终点位于起点之上
										var turn1 = {
											x: fromX + 30 ,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										};

										var turn3 = {
											x: toX - 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn3.x - 10;
										lineContainerTop = toY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX + 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn33 = {
											x: toX - 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);
									}
								}

							}
							else if( toCircle == "right" ) {
								toX = toBox[0].offsetLeft + toBox.width() + 3;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//当前box右起点连接目标box右边的点
								//右连右
								if( toX - fromX > 0 ) {
									var turn1 = {
										x: toX + 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y: toY
									};

									lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = fromX - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
								else if (toX - fromX <= 0) {
									var turn1 = {
										x: fromX + 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y: toY
									};

									lineContainerWidth = Math.abs(turn1.x - toX) + 20;
									lineContainerHeight = Math.abs(toY - fromY) + 20;
									lineContainerLeft = toX - 10;
									if (toY - fromY > 0) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
							}
						}
						/*
              * 以上为计算如果当前box作为起点box的时候
              * */
					}
					else if( $(lineContainer[i]).attr("toBoxId") === $(thisBox).attr("id") ) {
						//以所点击div为终点的线
						var fromBoxId = $(lineContainer[i]).attr("fromBoxId");
						fromBox = $("#" + fromBoxId);
						toBox = $(thisBox);

						$(thisBox).find(".box-li-name").each(function () {
							if( $(this).text() == toField ) {
								toLi = $(this).parent();
							}
						});
						$(fromBox).find(".box-li-name").each(function () {
							if( $(this).text() == fromField ) {
								fromLi = $(this).parent();
							}
						});

						var curLineIndex = 0;

						for( var s=0; s<linesArray.length; s++ ){
							if( linesArray[s].id == curLineId ){
								curLineIndex = s;
								break;
							}
						}

						//计算起点终点
						if( fromCircle == "left"  ) {
							fromX = $(fromBox)[0].offsetLeft;
							fromY = $(fromBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							if( toCircle == "left"  ) {
								toX = toBox[0].offsetLeft;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//起点box的左起点到所点击box的左终点
								//左连左
								if( toX - fromX > 0 ) {

									var turn1 = {
										x: fromX - 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y:toY
									};

									lineContainerWidth = Math.abs( toX - turn1.x ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = fromX - 10 - 30;

									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
								else if( toX - fromX <= 0 ) {

									var turn1 = {
										x: toX - 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y:toY
									};

									lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = turn1.x - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
							}
							else if( toCircle == "right" ) {
								toX = toBox[0].offsetLeft + toBox.width() + 3;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//起点box的左起点到所点击box的右终点

								if( toX - fromX < 0 ) {
									//左侧

									lineContainerWidth = Math.abs( toX - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = toX - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									//计算linContainer宽高，重新划线
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									linesArray[curLineIndex].turnPoints = [];

									linesArray[curLineIndex].from.x = line1CanvasFromX;
									linesArray[curLineIndex].from.y = line1CanvasFromY;
									linesArray[curLineIndex].to.x = line1CanvasToX;
									linesArray[curLineIndex].to.y = line1CanvasToY;

									drawArrow(
										lineCtx,
										line1CanvasFromX,
										line1CanvasFromY,
										line1CanvasToX,
										line1CanvasToY,
										30,
										10,
										2,
										'#96d6fc',
										null,
										null,
										null,
										curLineIndex
									);
								}
								else if( toX - fromX >= 0 ) {
									//右侧

									//终点在起点之下
									if( toY - fromY > 0 ) {

										var turn1 = {
											x: fromX - 30,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										};

										var turn3 = {
											x: toX + 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn2.x - 10;
										lineContainerTop = fromY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX - 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										};

										var turn33 = {
											x: toX + 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);
									} else if( toY - fromY <= 0 ) {

										//终点位于起点之上
										var turn1 = {
											x: fromX - 30 ,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										};

										var turn3 = {
											x: toX + 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn1.x - 10;
										lineContainerTop = toY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX - 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn33 = {
											x: toX + 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);
									}
								}
							}
						}
						else if( fromCircle == "right" ) {
							fromX = $(fromBox)[0].offsetLeft + $(fromBox).width() + 3;
							fromY = $(fromBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							if( toCircle == "left"  ) {
								toX = toBox[0].offsetLeft;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//起点box的右起点到所点击box的左终点
								if( toX - fromX > 0 ) {
									//右侧

									lineContainerWidth = Math.abs( toX - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = fromX - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}

									//计算linContainer宽高，重新划线
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									linesArray[curLineIndex].turnPoints = [];

									linesArray[curLineIndex].from.x = line1CanvasFromX;
									linesArray[curLineIndex].from.y = line1CanvasFromY;
									linesArray[curLineIndex].to.x = line1CanvasToX;
									linesArray[curLineIndex].to.y = line1CanvasToY;

									drawArrow(
										lineCtx,
										line1CanvasFromX,
										line1CanvasFromY,
										line1CanvasToX,
										line1CanvasToY,
										30,
										10,
										2,
										'#96d6fc',
										null,
										null,
										null,
										curLineIndex
									);
								}
								else if( toX - fromX < 0 ) {

									//终点在起点之下
									if( toY - fromY > 0 ) {
										var turn1 = {
											x: fromX + 30 ,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										};

										var turn3 = {
											x: toX - 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn3.x - 10;
										lineContainerTop = fromY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX + 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										};

										var turn33 = {
											x: toX - 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);
									} else if( toY - fromY <= 0 ) {
										//终点位于起点之上
										var turn1 = {
											x: fromX + 30 ,
											y: fromY
										};

										var turn2 = {
											x: turn1.x,
											y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										};

										var turn3 = {
											x: toX - 30,
											y: turn2.y
										};

										var turn4 = {
											x: turn3.x,
											y: toY
										};

										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										lineContainerLeft = turn3.x - 10;
										lineContainerTop = toY - 10;

										lineContainer[i].style.width = lineContainerWidth + 'px';
										lineContainer[i].style.height = lineContainerHeight + 'px';
										lineContainer[i].style.left = lineContainerLeft + 'px';
										lineContainer[i].style.top = lineContainerTop + 'px';
										$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										var turn11 = {
											x: fromX + 30 - lineContainerLeft,
											y: fromY - lineContainerTop
										};

										var turn22 = {
											x: turn1.x - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn33 = {
											x: toX - 30 - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};

										var turn44 = {
											x: turn3.x - lineContainerLeft,
											y: toY - lineContainerTop
										};

										linesArray[curLineIndex].turnPoints = [];
										linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										linesArray[curLineIndex].to.y = toY - lineContainerTop;

										var arr = [];
										arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										arr.push({"x":turn11.x, "y": turn11.y});
										arr.push({"x":turn22.x, "y": turn22.y});
										arr.push({"x":turn33.x, "y": turn33.y});
										arr.push({"x":turn44.x, "y": turn44.y});
										arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										drawInArrow(
											lineCtx,
											30,
											10,
											2,
											'#96d6fc',
											arr
										);
									}
								}
							}
							else if( toCircle == "right" ) {
								toX = toBox[0].offsetLeft + toBox.width() + 3;
								toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								//起点box的右起点到所点击box的右终点
								//右连右
								if( toX - fromX > 0 ) {
									var turn1 = {
										x: toX + 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y: toY
									};

									lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									lineContainerLeft = fromX - 10;
									if( toY - fromY > 0 ) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
								else if (toX - fromX <= 0) {
									var turn1 = {
										x: fromX + 30,
										y: fromY
									};
									var turn2 = {
										x: turn1.x,
										y: toY
									};

									lineContainerWidth = Math.abs(turn1.x - toX) + 20;
									lineContainerHeight = Math.abs(toY - fromY) + 20;
									lineContainerLeft = toX - 10;
									if (toY - fromY > 0) {
										//右下
										lineContainerTop = fromY - 10;
									} else {
										//右上
										lineContainerTop = toY - 10;
									}
									$(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									$(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									$(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									$(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									$(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									$(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									var turn11 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};

									var turn22 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};

									linesArray[curLineIndex].turnPoints = [];
									linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									linesArray[curLineIndex].to.y = toY - lineContainerTop;

									var arr = [];
									arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									arr.push({"x":turn11.x, "y": turn11.y});
									arr.push({"x":turn22.x, "y": turn22.y});
									arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									drawInArrow(
										lineCtx,
										30,
										10,
										2,
										'#96d6fc',
										arr
									);
								}
							}
						}
					}
				}
			}
		});

		$(thisBox).mouseup(function () {
			isPowerBoxMove = false;
			thisBox.style.cursor = "default";
			$("#power-container").unbind("mousemove");
			saveXY();
		});
	}

}

//鼠标移动找经过的线
function containerMousemove(e) {
	var lineContainer = $(".line_box_container");

	for( var i = 0; i < lineContainer.length; i++ ) {
		var curLineContainer = $(lineContainer[i]);
		var curVanvas = curLineContainer.find("canvas")[0];
		var lineCtx = curVanvas.getContext("2d");
		var x = e.clientX - $("#power-container")[0].offsetLeft - curLineContainer[0].offsetLeft  + $("#power-container").scrollLeft();
		var y = e.clientY - $(".power-main")[0].offsetTop - curLineContainer[0].offsetTop + $("#power-container").scrollTop();
		var curLineData = curLineContainer.data("lineInfo");
		if( lineCtx.isPointInStroke ){
			if( lineCtx.isPointInStroke(x,y) ){
					lineCtx.clearRect(0,0, curLineContainer.width(), curLineContainer.height() );
					if(curLineData){
						drawArrow( curLineData.canvas,
							curLineData.fromX,
							curLineData.fromY,
							curLineData.toX,
							curLineData.toY,
							$("#"+curLineData.fromBoxId),
							$("#"+curLineData.toBoxId),
							curLineContainer,
							curLineData.fromIndex,
							curLineData.toIndex ,
							'shadow');
					}
			}else{
				lineCtx.clearRect(0,0, curLineContainer.width(), curLineContainer.height() );
				if( curLineData ){
					if( curLineContainer.hasClass("power-active") ){
						drawArrow( curLineData.canvas,
							curLineData.fromX,
							curLineData.fromY,
							curLineData.toX,
							curLineData.toY,
							$("#"+curLineData.fromBoxId),
							$("#"+curLineData.toBoxId),
							curLineContainer,
							curLineData.fromIndex,
							curLineData.toIndex,
							'shadow');
					}else{
						drawArrow( curLineData.canvas,
							curLineData.fromX,
							curLineData.fromY,
							curLineData.toX,
							curLineData.toY,
							$("#"+curLineData.fromBoxId),
							$("#"+curLineData.toBoxId),
							curLineContainer,
							curLineData.fromIndex,
							curLineData.toIndex);
					}
				}
			}
		}else {

			if( lineCtx.isPointInPath(x,y) ){
				lineCtx.strokeStyle = "#333";
				lineCtx.stroke();
			}else {
				lineCtx.strokeStyle = "#96d6fc";
				lineCtx.stroke();
			}
		}
	}
}

//鼠标的点击事件
function documentMouseDown(e) {

	$(".power-active").removeClass("power-active");

	if( $(e.target).parents(".summer-box").length > 0 || $(e.target).hasClass("summer-box") ){
		var curBox = $($(e.target).parents(".summer-box")[0]);
		curBox.addClass("power-active");
	}else {
		$(".position-circle").css("display","none");
	}

	var lineContainer = $(".line_box_container");

	for( var i = 0; i < lineContainer.length; i++ ) {
		var curLineContainer = $(lineContainer[i]);
		var curVanvas = curLineContainer.find("canvas")[0];
		var lineCtx = curVanvas.getContext("2d");
		var x = e.clientX - $("#power-container")[0].offsetLeft - curLineContainer[0].offsetLeft  + $("#power-container").scrollLeft();
		var y = e.clientY - $(".power-main")[0].offsetTop - curLineContainer[0].offsetTop + $("#power-container").scrollTop();
		var curLineData = curLineContainer.data("lineInfo");
		if( lineCtx.isPointInStroke ){
			if( lineCtx.isPointInStroke(x,y) ){
				lineCtx.clearRect(0,0, curLineContainer.width(), curLineContainer.height() );
				curLineContainer.addClass("power-active");
				if(curLineData){
					drawArrow( curLineData.canvas,
						curLineData.fromX,
						curLineData.fromY,
						curLineData.toX,
						curLineData.toY,
						$("#"+curLineData.fromBoxId),
						$("#"+curLineData.toBoxId),
						curLineContainer,
						curLineData.fromIndex,
						curLineData.toIndex ,
						'shadow');
				}
			}else{
				lineCtx.clearRect(0,0, curLineContainer.width(), curLineContainer.height() );
				if( curLineData ){
					drawArrow( curLineData.canvas,
						curLineData.fromX,
						curLineData.fromY,
						curLineData.toX,
						curLineData.toY,
						$("#"+curLineData.fromBoxId),
						$("#"+curLineData.toBoxId),
						curLineContainer,
						curLineData.fromIndex,
						curLineData.toIndex);
				}
			}
		}else {

			if( lineCtx.isPointInPath(x,y) ){
				lineCtx.strokeStyle = "#333";
				lineCtx.stroke();
			}else {
				lineCtx.strokeStyle = "#96d6fc";
				lineCtx.stroke();
			}
		}
	}
}