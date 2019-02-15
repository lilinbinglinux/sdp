<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/19
  Time: 16:04
  Describe: 公共引入
--%>
<%-- <%@ page import="com.sdp.security.client.*,com.sdp.security.entity.*,com.sdp.security.util.*"%>
<%
    String skinPath = "red-skin";
    String loginId = LoginUtil.getLoginId(request);
    if(loginId != null && !"".equals(loginId)) {
        Userinfo user = SecurityClient.findUserByLoginId(loginId);
        if(user!=null) {
            if(user.getReserve3() != null && !"".equals(user.getReserve3())){
                skinPath = user.getReserve3();
            }
        }
    }
%> --%>
<%
    String skinPath = "red-skin";
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/message/message.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/<%=skinPath%>/theme.css">
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/message/message.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
</script>
