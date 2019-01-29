$(document).ready(function () {
	var productId = $("#productId").val();

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

	//获取属性
	var sourceAttrs = [];
	var allPorderWays = [];
	$.ajax({
		url: curContext + '/api/product/singleProduct/' + productId,
		type: 'GET',
		contentType : "application/json",
		success: function (result) {
			var quataForm = $("#quata-form");
			var productAttrOrm = result.productAttrOrm;
			$("#orderType").val(result.orderType);
			if( productAttrOrm && productAttrOrm.length > 0 ){
				quataForm.empty();
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
					$(".result-container").css("display","block");
				}else {
					alert("操作失败！");
				}
			}
		});
	});

	$("#to_process").click(function (e) {
		window.location.href = curContext + "/v1/pro/pbmOrderProcess/page";
	});

	$("#go_back").click(function (e) {
		$(".quata-container").css("display","block");
		$(".result-container").css("display","none");
	});
});