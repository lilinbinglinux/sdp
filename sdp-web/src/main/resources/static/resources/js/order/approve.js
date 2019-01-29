$(document).ready(function () {

	var pendtaskId = 0;
	//查询订单详情
	$.ajax({
		url: curContext + '/api/productOrder/singleOrder/' + orderId,
		contentType : "application/json",
		type: "GET",
		success:function (result) {
			$("#orderId").text(result.orderId);
			$("#applyUserName").text(result.createBy);
			$("#orderStartDate").text(result.createDate);
			pendtaskId = result.bpmModelNo;
			var orderStatus = "";
			switch (result.orderStatus) {
				case 10:
					orderStatus = "待支付";
					break;
				case 20:
					orderStatus = "支付成功";
					break;
				case 30:
					orderStatus = "待审批";
					break;
				case 40:
					orderStatus = "审批中";
					break;
				case 50:
					orderStatus = "通过";
					break;
				case 60:
					orderStatus = "驳回";
					break;
				default :
					orderStatus = "-";
					break;
			}
			$("#orderStatus").text(orderStatus);

			$("#productName").text((result.applyName ? result.applyName : '-' ));
			$("#approveName").text(result.pOrderWays.pwaysName);
			$("#payWayName").text(result.pOrderWays.pwaysRemark);

			var orderBasicAttrOrm = result.orderBasicAttrOrm;
			if( orderBasicAttrOrm && orderBasicAttrOrm.length > 0 ){
				for( var q = 0; q < orderBasicAttrOrm.length; q++ ){
					var html =
						'<div>' +
						' <div style="text-align: right;width: 50%;float: left;">' +
						'   <span>'+ orderBasicAttrOrm[q].proName +':</span>' +
						' </div>' +
						' <div style="text-align: left;width: 50%;float: left;padding-left: 10px;">' +
						'   <span>'+ wrapShow(orderBasicAttrOrm[q].proValue) + (orderBasicAttrOrm[q].proUnit ? orderBasicAttrOrm[q].proUnit : ""  ) + '</span>'+
						' </div>' +
						'</div>';
					$("#basicAttr").append(html);
				}
			}

			var orderControlResAttrOrm = result.orderControlResAttrOrm;
			if( orderControlResAttrOrm && orderControlResAttrOrm.length > 0 ){
				for( var i = 0; i < orderControlResAttrOrm.length; i++ ){
					var html =
						'<div>' +
						' <div style="text-align: right;width: 50%;float: left;">' +
						'   <span>'+ orderControlResAttrOrm[i].proName +':</span>' +
						' </div>' +
						' <div style="text-align: left;width: 50%;float: left;padding-left: 10px;">' +
						'   <span>'+ wrapShow(orderControlResAttrOrm[i].proValue) + (orderBasicAttrOrm[i].proUnit ? orderBasicAttrOrm[i].proUnit : ""  ) +'</span>' +
						' </div>' +
						'</div>';
					$("#controlAttr").append(html);
				}
			}
		}
	});
	
	$("#approveSub").click(function () {
		var checkAgreement = $("input[name='isAgreement']:checked");
		if( checkAgreement.length === 0 ){
			toastr.info('请选择同意与否！');
			return;
		}
		var isAgreement = parseInt(checkAgreement.val());
		var remark = $("#remark").val();

		$.ajax({
			url: curContext + '/api/bpmOrderProcess/' + orderId + '/approval',
			type: "POST",
			contentType : "application/json",
			data: JSON.stringify({
				"orderId": orderId,
				"isAgreement": isAgreement,
				"remark": remark,
				"pendtaskId": taskId
			}),
			success: function (result) {
				if( result.status && result.status.code == 201 ){
					toastr.success('审批成功！');

                    window.parent.OrderNotApprovedNum(curContext);

					setTimeout(function () {
						window.location.href = curContext + "/v1/pro/pbmOrderProcess/page";
					},2000);
				} else {
					toastr.error('操作失败！');
				}
			}
		});
	});

	$("#approveCancle").click(function () {
		window.location.href = curContext + "/v1/pro/pbmOrderProcess/page";
	});

	//查询历史审批意见
	$.ajax({
		url: curContext + '/api/bpmOrderProcess/' + orderId + '/hisApproval',
		contentType : "application/json",
		type: "GET",
		success: function (result) {
			if( result && result.length > 0 ){
				$("#approveTr").empty();
				for( var i = 0; i < result.length; i++ ){
					var html =
						'<td>'+result[i].flowId+'</td>'+
						'<td>'+( result[i].isAgreement == 1 ? "同意" : "不同意" )+'</td>'+
						'<td>'+( result[i].remark == "" ? "无" : result[i].remark)+'</td>'+
						'<td>'+result[i].updateDate+'</td>';
					$("#approveTr").append(html);
				}
			}
		}
	});
});

// 多行文本换行显示
function wrapShow(s) {
    var re= '';
    var length = s.length;
    for (var i = 0,j=1; i < length; i++,j++) {
        if (j&&j % 20 == 0) {
            re += '<br />';
        } else {
            re += s[i];
        }
    }
    return re;
}