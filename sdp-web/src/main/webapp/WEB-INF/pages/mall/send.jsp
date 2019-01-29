<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mall/md5.js"></script>


<!--以下信息为标准的 HTML 格式 + ASP 语言 拼凑而成的 网银在线 支付接口标准演示页面 无需修改-->
<html>

<body>

<form action="https://pay3.chinabank.com.cn/PayGate" method="POST" name="E_FORM">

	<input type="hidden" name="v_md5info" size="100">
    <input type="hidden" name="v_mid" >
	<input type="hidden" name="v_oid" id="v_oid">
	<input type="hidden" name="v_amount">
    <input type="hidden" name="v_moneytype">
    <input type="hidden" name="v_url">

	<!--以下几项项为网上支付完成后，随支付反馈信息一同传给信息接收页 -->

	<%--<input type="hidden"  name="remark1" value="<%=remark1%>">
    <input type="hidden"  name="remark2" value="<%=remark2%>">--%>
</form>
</body>
<script type="text/javascript">
  var $ctx = "${pageContext.request.contextPath}";

  var v_mid = "1001"; // 商户号
  var v_url = "http://2509c182.nat123.cc/xbconsole/v1/mall/orderReceive"; // 商户自定义返回接收支付结果的页面
  var key = "test";
  var orderNo = ${orderNo}; //订单号
  var orderId = ${orderId}; //订单id
  console.log(orderNo,'orderNo');
  console.log(orderId,' orderId');


  var v_amount; // 订单金额
  var v_moneytype ="CNY"; // 币种

  $("input[name='v_mid']").val(v_mid);
  $("input[name='v_url']").val(v_url);
  $("#v_oid").val(orderNo);
  $("input[name='v_moneytype']").val(v_moneytype);


  // 订单详情
  $.ajax({
	type: "GET",
	url: "http://127.0.0.1:8082/bconsole/v1/svcorders/"+orderId,
	dataType: "json",
	success: function (data) {
	  //v_amount = data.orderPrice;
      v_amount = 0.01;
      var text = v_amount+v_moneytype+orderNo+v_mid+v_url+key; // 拼凑加密串
      var v_md5info = text.toUpperCase(); //转换为大写

      console.log(hex_md5(v_md5info),'大写');
      console.log(hex_md5(text),'text');

      $("input[name='v_amount']").val(v_amount);
      $("input[name='v_md5info']").val(hex_md5(text));

      document.E_FORM.submit();
	}
  });

</script>

</html>