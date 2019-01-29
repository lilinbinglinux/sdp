$(document).ready(function(){
  var approvalStr = '&nbsp;订单提交成功，正在审批中，您也可以在&nbsp;';
  var payStr = '&nbsp;订单提交成功，请您及时支付，您也可以在&nbsp;';
  var probationStr = '&nbsp;订单提交成功，服务开通中，您也可以在&nbsp;';
  var _waysType = window.waysType;
  var _orderId = window.orderId;
  var _orderNo = window.orderNo;
  var totalPrice = $.cookie('orderPrice');
  console.log(totalPrice,'totalPrice------');

  if(_waysType == 10) {
    $('span#resultInfo').html(approvalStr);
    $('div#payBtn').html('');
  }else if(_waysType == 20) {
    $('span#resultInfo').html(payStr);
    $('div#payBtn').html('<button class="btn btn-primary pay-btn" id="orderPay" type="button">去支付</button>');
  }else if(_waysType == 30) {
    $('span#resultInfo').html(probationStr);
    $('div#payBtn').html('');
  }

  //去支付
  $('#orderPay').on('click',function () {
	  window.location.href = $webpath + '/v1/mall/toPay/'+_orderId+'/'+_orderNo;
  });
});


