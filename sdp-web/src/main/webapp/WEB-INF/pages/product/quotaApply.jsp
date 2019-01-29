<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>配额管理</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.4.1/themes/default/easyui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.4.1/themes/icon.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order/index.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body class="body-bg">
  <div class="container-fluid order-container">
    <div class="quata-container">
      <div class="page-header">
        <h1 style="text-align: center"><small>配额申请</small></h1>
        <input type="text" hidden value="${productId}" id="productId">
        <input type="text" hidden id="orderType">
        <input type="text" hidden id="pwaysId">
      </div>
      <div class="quata-content">
        <div class="quata-attr-list" style="width: 500px; margin: 0 auto;">
          <form class="form-horizontal" id="quata-form">
            <%--<div class="row">--%>
              <%--<div class="col-md-12">--%>
                <%--<div class="form-group">--%>
                  <%--<label for="inputEmail3" class="col-sm-2 control-label">Email</label>--%>
                  <%--<div class="col-sm-10">--%>
                    <%--<input type="email" class="form-control" id="inputEmail3" placeholder="Email">--%>
                  <%--</div>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
          </form>
        </div>
      </div>
      <div class="quata-btns">
        <button type="button" class="btn btn-primary" id="quota-sub">提交订单</button>
      </div>
    </div>
    <div class="result-container">
      <div class="row">
        <div class="col-md-12 result-word" style="text-align: center">
          配额申请提交成功！前往<span style="color: #1890ff;cursor: pointer;" id="to_process">工单审批</span>查看
        </div>
      </div>
      <div class="row">
        <div class="col-md-12" style="text-align: center">
          <button type="button" class="btn btn-primary" id="go_back">返回</button>
        </div>
      </div>
    </div>
  </div>

  <script>
  	var curContext = '<%=path%>';
  </script>
  <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/order/quotaApply.js"></script>
</body>
</html>

