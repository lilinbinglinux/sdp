<%@page contentType="text/html; charset=gb2312" language="java"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<jsp:useBean id="MD5" scope="request" class="beartool.MD5"/>
<%
//****************************************	// MD5密钥要跟订单提交页相同，如Send.asp里的 key = "test" ,修改""号内 test 为您的密钥
											// 如果您还没有设置MD5密钥请登陆我们为您提供商户后台，地址：https://merchant3.chinabank.com.cn/
String key = "test";						// 登陆后在上面的导航栏里可能找到“B2C”，在二级导航栏里有“MD5密钥设置”
											// 建议您设置一个16位以上的密钥或更高，密钥最多64位，但设置16位已经足够了
//****************************************

//获取参数
	   String v_oid = request.getParameter("v_oid");		// 订单号
	 String v_pmode = request.getParameter("v_pmode");		// 支付方式中文说明，如"中行长城信用卡"
   String v_pstatus = request.getParameter("v_pstatus");	// 支付结果，20支付完成；30支付失败；
   String v_pstring = request.getParameter("v_pstring");	// 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败"
	String v_amount = request.getParameter("v_amount");		// 订单实际支付金额
 String v_moneytype = request.getParameter("v_moneytype");	// 币种
	String v_md5str = request.getParameter("v_md5str");		// MD5校验码
	 String remark1 = request.getParameter("remark1");		// 备注1
	 String remark2 = request.getParameter("remark2");		// 备注2


String text = v_oid+v_pstatus+v_amount+v_moneytype+key;
String v_md5text = MD5.getMD5ofStr(text).toUpperCase();
		
if (v_md5str.equals(v_md5text))
{
	if ("30".equals(v_pstatus))
	{
		out.print("支付失败");
	}else if ("20".equals(v_pstatus)){
		// 支付成功，商户 根据自己业务做相应逻辑处理
		//此处加入商户系统的逻辑处理（例如判断金额，判断支付状态，更新订单状态等等）......
		%>

<div id="pay-result" style="padding: 30px;">
	<div class="row">
		<div class="col-md-12">
			<div style="text-align: center;font-size: 14px;">
				<p>
					<i class="glyphicon glyphicon-ok" style="color: rgb(0, 112, 210);font-size: 16px;"></i>
					<span id="payResult">支付成功，您可以在</span>
					<a class="order-router" href="" style="color: rgb(0, 112, 210);">订单管理</a>&nbsp;中查看订单
				</p>
			</div>
		</div>
	</div>
</div>
		<%--<TABLE width=500 border=0 align="center" cellPadding=0 cellSpacing=0>
		  <TBODY>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE:14px">MD5校验码:<%=v_md5str%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">订单号:<%=v_oid%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">支付卡种:<%=v_pmode%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">支付结果:<%=v_pstring%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">支付金额:<%=v_amount%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">支付币种:<%=v_moneytype%></FONT></B></div></TD>
			</TR>
		  </TBODY>
		</TABLE>--%>
<script>
  var $webpath = "${pageContext.request.contextPath}";
  var v_oid= "<%=v_oid%>";
  var v_amount= "<%=v_amount%>";
  var curTime = formatDateTime(new Date());
  $('a.order-router').attr('href', $webpath+'/v1/pro/productOrder/page');
  $.ajax({
    type: "POST",
    url: "http://127.0.0.1:8082/bconsole/v1/svcorders/apply/paynotify",
    dataType: "json",
    data: {
      'appid':'payTest',
      'proOrderNo':v_oid,
      'tradeTime':curTime,
      'amount':v_amount,
      'respCode':'00'
    },
    success: function (data) {
      console.log(data,'修改订单支付状态----');
    }
  });
</script>
		<%
	}
}else{
    out.print("校验失败,数据可疑");
}
%>