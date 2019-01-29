<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>工单管理</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order/index.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body class="body-bg">
<div class="container-fluid order-container">
  <div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
      <li id="unProve-li" role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">待审批</a></li>
      <li id="proved-li" role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">已审批</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
      <div role="tabpanel" class="tab-pane active" id="home">
        <table class="table table-hover" id="unProve">

        </table>
      </div>
      <div role="tabpanel" class="tab-pane" id="profile">
        <div id="toolbar" style="margin-bottom: 15px;margin-top: 15px;">
          <form class="form-inline" style="margin-bottom: 0;">
            <div class="form-group">
              <label>订单编号</label>
              <input type="text" class="form-control" id="orderId">
            </div>
            <button type="submit" class="btn btn-primary" id="orderSearch">搜索</button>
            <button type="submit" class="btn btn-default" id="orderClear">重置</button>
          </form>
        </div>
        <table class="table table-hover" id="proved">

        </table>
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
<script src="${pageContext.request.contextPath}/resources/js/order/process.js"></script>
</body>
</html>
