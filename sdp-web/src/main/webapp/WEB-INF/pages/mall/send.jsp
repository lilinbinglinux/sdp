<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mall/md5.js"></script>


<!--������ϢΪ��׼�� HTML ��ʽ + ASP ���� ƴ�ն��ɵ� �������� ֧���ӿڱ�׼��ʾҳ�� �����޸�-->
<html>

<body>

<form action="https://pay3.chinabank.com.cn/PayGate" method="POST" name="E_FORM">

	<input type="hidden" name="v_md5info" size="100">
    <input type="hidden" name="v_mid" >
	<input type="hidden" name="v_oid" id="v_oid">
	<input type="hidden" name="v_amount">
    <input type="hidden" name="v_moneytype">
    <input type="hidden" name="v_url">

	<!--���¼�����Ϊ����֧����ɺ���֧��������Ϣһͬ������Ϣ����ҳ -->

	<%--<input type="hidden"  name="remark1" value="<%=remark1%>">
    <input type="hidden"  name="remark2" value="<%=remark2%>">--%>
</form>
</body>
<script type="text/javascript">
  var $ctx = "${pageContext.request.contextPath}";

  var v_mid = "1001"; // �̻���
  var v_url = "http://2509c182.nat123.cc/xbconsole/v1/mall/orderReceive"; // �̻��Զ��巵�ؽ���֧�������ҳ��
  var key = "test";
  var orderNo = ${orderNo}; //������
  var orderId = ${orderId}; //����id
  console.log(orderNo,'orderNo');
  console.log(orderId,' orderId');


  var v_amount; // �������
  var v_moneytype ="CNY"; // ����

  $("input[name='v_mid']").val(v_mid);
  $("input[name='v_url']").val(v_url);
  $("#v_oid").val(orderNo);
  $("input[name='v_moneytype']").val(v_moneytype);


  // ��������
  $.ajax({
	type: "GET",
	url: "http://127.0.0.1:8082/bconsole/v1/svcorders/"+orderId,
	dataType: "json",
	success: function (data) {
	  //v_amount = data.orderPrice;
      v_amount = 0.01;
      var text = v_amount+v_moneytype+orderNo+v_mid+v_url+key; // ƴ�ռ��ܴ�
      var v_md5info = text.toUpperCase(); //ת��Ϊ��д

      console.log(hex_md5(v_md5info),'��д');
      console.log(hex_md5(text),'text');

      $("input[name='v_amount']").val(v_amount);
      $("input[name='v_md5info']").val(hex_md5(text));

      document.E_FORM.submit();
	}
  });

</script>

</html>