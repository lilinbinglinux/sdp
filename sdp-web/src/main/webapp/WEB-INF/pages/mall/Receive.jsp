<%@page contentType="text/html; charset=gb2312" language="java"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<jsp:useBean id="MD5" scope="request" class="beartool.MD5"/>
<%
//****************************************	// MD5��ԿҪ�������ύҳ��ͬ����Send.asp��� key = "test" ,�޸�""���� test Ϊ������Կ
											// �������û������MD5��Կ���½����Ϊ���ṩ�̻���̨����ַ��https://merchant3.chinabank.com.cn/
String key = "test";						// ��½��������ĵ�����������ҵ���B2C�����ڶ������������С�MD5��Կ���á�
											// ����������һ��16λ���ϵ���Կ����ߣ���Կ���64λ��������16λ�Ѿ��㹻��
//****************************************

//��ȡ����
	   String v_oid = request.getParameter("v_oid");		// ������
	 String v_pmode = request.getParameter("v_pmode");		// ֧����ʽ����˵������"���г������ÿ�"
   String v_pstatus = request.getParameter("v_pstatus");	// ֧�������20֧����ɣ�30֧��ʧ�ܣ�
   String v_pstring = request.getParameter("v_pstring");	// ��֧�������˵�����ɹ�ʱ��v_pstatus=20��Ϊ"֧���ɹ�"��֧��ʧ��ʱ��v_pstatus=30��Ϊ"֧��ʧ��"
	String v_amount = request.getParameter("v_amount");		// ����ʵ��֧�����
 String v_moneytype = request.getParameter("v_moneytype");	// ����
	String v_md5str = request.getParameter("v_md5str");		// MD5У����
	 String remark1 = request.getParameter("remark1");		// ��ע1
	 String remark2 = request.getParameter("remark2");		// ��ע2


String text = v_oid+v_pstatus+v_amount+v_moneytype+key;
String v_md5text = MD5.getMD5ofStr(text).toUpperCase();
		
if (v_md5str.equals(v_md5text))
{
	if ("30".equals(v_pstatus))
	{
		out.print("֧��ʧ��");
	}else if ("20".equals(v_pstatus)){
		// ֧���ɹ����̻� �����Լ�ҵ������Ӧ�߼�����
		//�˴������̻�ϵͳ���߼����������жϽ��ж�֧��״̬�����¶���״̬�ȵȣ�......
		%>

<div id="pay-result" style="padding: 30px;">
	<div class="row">
		<div class="col-md-12">
			<div style="text-align: center;font-size: 14px;">
				<p>
					<i class="glyphicon glyphicon-ok" style="color: rgb(0, 112, 210);font-size: 16px;"></i>
					<span id="payResult">֧���ɹ�����������</span>
					<a class="order-router" href="" style="color: rgb(0, 112, 210);">��������</a>&nbsp;�в鿴����
				</p>
			</div>
		</div>
	</div>
</div>
		<%--<TABLE width=500 border=0 align="center" cellPadding=0 cellSpacing=0>
		  <TBODY>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE:14px">MD5У����:<%=v_md5str%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">������:<%=v_oid%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">֧������:<%=v_pmode%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">֧�����:<%=v_pstring%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">֧�����:<%=v_amount%></FONT></B></div></TD>
			</TR>
			<TR> 
			  <TD vAlign=top align=middle> <div align="left"><B><FONT style="FONT-SIZE: 14px">֧������:<%=v_moneytype%></FONT></B></div></TD>
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
      console.log(data,'�޸Ķ���֧��״̬----');
    }
  });
</script>
		<%
	}
}else{
    out.print("У��ʧ��,���ݿ���");
}
%>