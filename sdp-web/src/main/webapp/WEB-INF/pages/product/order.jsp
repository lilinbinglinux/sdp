<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>订单管理</title>

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
    <div id="toolbar" style="margin-bottom: 15px;">
      <form class="form-inline" style="margin-bottom: 0;">
        <div class="form-group">
          <label>订单状态</label>
          <select class="form-control" id="orderStatus">
            <option value="">请选择</option>
            <option value="10">待支付</option>
            <option value="20">支付成功</option>
            <option value="30">待审批</option>
            <option value="40">审批中</option>
            <option value="50">通过</option>
            <option value="60">驳回</option>
          </select>
        </div>
        <div class="form-group">
          <label >计费方式</label>
          <select class="form-control" id="pwaysId">
          
          </select>
        </div>
        <div class="form-group">
          <label >起始时间</label>
          <input type="text" class="form-control" id="startTime" data-date-format="yyyy-mm-dd hh:ii:ss">
        </div>
        <div class="form-group">
          <label >结束时间</label>
          <input type="text" class="form-control" id="expireTime" data-date-format="yyyy-mm-dd hh:ii:ss">
        </div>
        <button type="submit" class="btn btn-primary" id="orderSearch">搜索</button>
        <button type="submit" class="btn btn-default" id="orderClear">重置</button>
      </form>
    </div>
    <div class="table-responsive">
      <table class="table table-hover" id="order-table">

      </table>
    </div>
  </div>
  <!-- Modal -->
  <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">订单详情</h4>
        </div>
        <div class="modal-body">
          <div class="row">
            <div class="col-md-3">
              <span class="binary-text binary-label">订单编号</span>
            </div>
            <div class="col-md-9">
              <span class="binary-text">20180816150334</span>
            </div>
          </div>
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
  <script src="${pageContext.request.contextPath}/resources/js/order/order.js"></script>
</body>
</html>