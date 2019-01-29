<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="<%=path%>/resources/plugin/jquery/jquery-1.9.1.js"></script>

</head>
<body>
<button id="dd">qqqqq</button>"dataApiId":"1231564164165146500000",
<script >
$("#dd").click(function(){
	debugger;
	$.ajax({
	    url: '<%=path%>/v1/sqlQuery/findDbGet/1231564164165146500000',
	    dataType: 'json', 
	    contentType: 'application/json',
	    data:{"jsonMessage":JSON.stringify({"fieldMap":{"createBy":"admin","fieldLen":"10"},"tenantId":"tenant_system","tableList":[{"id":"2c2720fe745b11e88b091402ec8b0a88"},{"id":"04d3824775ca11e88b091402ec8b0a88"},{"id":"2047772d75ca11e88b091402ec8b0a88"}]})}, 
	    type:'get',
	    success:function (result) {
	    	
	    }
	  });
});
</script>

<c:set var="row_total" value="" />
	<c:forEach items="${item}" var="reward" varStatus="index">
	<c:if test="${empty reward.productnum}">
		<c:choose>
		   <c:when test="${index.last==false}"> 
		   		<c:if test="${not empty reward.aaa}">
					<c:set var="row_total" value="${row_total}${reward.aaa}|" />   
				</c:if>
		   		<c:if test="${empty reward.aaa}">
					 
				</c:if>
		   </c:when>
		   <c:otherwise>
		   		<c:if test="${not empty reward.aaa}">
					<c:set var="row_total" value="${row_total}${reward.aaa}" />
				</c:if>
				<c:if test="${empty reward.aaa}">
					 
				</c:if>
		   </c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${not empty reward.productnum}">
		<c:choose>
		   <c:when test="${index.last==false}"> 
		   		<c:if test="${not empty reward.aaa}">
					<c:set var="row_total" value="${row_total}${reward.aaa},${reward.productnum}|" /> 
				</c:if> 
		   		<c:if test="${empty reward.aaa}">
					<c:set var="row_total" value="${row_total}${reward.productnum}|" /> 
				</c:if> 
		   </c:when>
		   <c:otherwise>
		   		<c:if test="${not empty reward.aaa}">
			 		<c:set var="row_total" value="${row_total}${reward.aaa},${reward.productnum}" /> 
			 	</c:if>
		   		<c:if test="${empty reward.aaa}">
			 		<c:set var="row_total" value="${row_total}${reward.productnum}" /> 
			 	</c:if>
		   </c:otherwise>
		</c:choose>
	</c:if>
	</c:forEach>
	<div>-----------${row_total}</div> 
</body>

</html>