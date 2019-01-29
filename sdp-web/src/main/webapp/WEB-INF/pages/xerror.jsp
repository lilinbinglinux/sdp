<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>default error</title>
<head>
    <style type="text/css">
		/* CSS Document */
		html, body, div, span, object, iframe, h1, h2, h3, h4, h5, h6, p, a, img, dl, dt, dd, ol, ul, li, form, table { margin:0; padding:0; border:0; list-style:none;}
		div, span, h1, h2, h3, h4, h5, h6, p, a, img, dl, dt, dd, ol, ul, li, form { overflow:hidden;}
		input { margin:0; padding:0; }
		h1  {font-size:32px; font-weight:normal;}
		h2,h3,h4,h5,h6 {font-size:12px; font-weight:normal;}
		a,area{blr:expression(this.onFocus=this.blur())}
		a { text-decoration:none;}
		a:hover { text-decoration:underline;}
		html, body { font-family:"å¾®è½¯éé»", "å®ä½" }
		.boxcenter { margin:0 auto;}
		
		.gxj_404Block { width:1480px; height:303px; padding-left:276px; padding-top:66px; background:url(./img/errors/503bg.png) top left no-repeat; position:absolute; top:50%; left:50%; margin:-180px 0 0 -324px;}
		.gxj_404Block h2 { font-size:24px; color:#333; line-height:50px;}
		.gxj_404Block p { line-height:26px; font-size:14px; color:#666;}
		
    </style>
</head>
<body>
    <div class="gxj_404Block">
    	<h1>出错啦~</h1>
    	<div style="margin-top:30px">
    	   <%=request.getAttribute("exception")%> <br/>
    	</div>
    	<div style="margin-top:10px">
    	       出错地址：<%=request.getAttribute("url")%>
    	</div> 
    </div>
</body>

</html>

