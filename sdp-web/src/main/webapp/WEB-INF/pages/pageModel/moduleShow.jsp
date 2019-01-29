<%--
  Created by IntelliJ IDEA.
  User: wangxiang
  Date: 2018/4/23
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String moduleId = (String) request.getAttribute("moduleId");
%>
<html>
<head id="headId">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>

    <title>组件预览</title>
    <style>
        .show-title {
            font-family: inherit;
            font-weight: 500 !important;
            line-height: 1.1;
            color: inherit;
            font-size: 18px;
            margin-top: 30px;
            margin-bottom: 10px;
        }

        .backicon:hover{
            color: #999;
        }
    </style>
    <style id="cssStyle">

    </style>

    <!-- 相关js -->
    <script type="text/javascript" src="<%=path%>/resources/js/pageModel/moduleshow.js"></script>
    <script>
      var path = "<%=path%>";
      var moduleId = "<%=moduleId%>";
    </script>
</head>
<body id="bodyId" style="overflow-x: auto;">
    <div class="container">
        <div class="row">
            <%
                if (moduleId == null){
            %>
            <div>
                <div class="col-md-6 col-xs-6 show-title"><p>预览：</p></div>
                <div class="col-md-6 col-xs-6"></div>
            </div>
            <%
                } else {
            %>
            <div style="position: fixed; top: 20px; left: 20px;">
                <span class="glyphicon glyphicon-circle-arrow-left backicon" aria-hidden="true" style="font-size: 25px; cursor: pointer;" onclick="javascript:history.back(-1);"></span>
            </div>
            <%
                }
            %>
            <div>
                <div id="html-div" class="col-md-12 col-xs-12">

                </div>
            </div>
        </div>
    </div>
<script>
  var pageResArray = [];
  var scriptHtmls="";

  $(document).ready(function(){
    //console.log("ready--------------------")

     try{
	    if (moduleId != "" && moduleId != "null"){
	      $.ajax({
	        type: "get",
	        url: path+"/v1/pageModule/module/"+moduleId,
	        success: function(data){
	          if (data){
	
	            formatPageResIds(data.pageResIds);
	
	            if (pageResArray.length > 0){
	              getResFromAddModule(pageResArray,'edit');
	            }
	
	            addCode({html:data.moduleText, js: data.moduleJs, css: data.moduleStyle});
	          }
	        }
	      });
	    }
     }catch(e){}
  });
</script>
</body>
</html>
