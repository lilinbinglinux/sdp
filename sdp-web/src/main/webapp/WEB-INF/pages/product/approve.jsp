<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>工单审批</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/CodeSeven-toastr/build/toastr.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order/index.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body class="body-bg">
  <div class="container-fluid order-container">
    <div class="page-header">
      <h1><small>订单审批</small></h1>
    </div>
    <div class="row">
      <div class="col-md-3">
        <span>订单编号：</span><span id="orderId"></span>
      </div>
      <div class="col-md-3">
        <span>订单申请人：</span><span id="applyUserName"></span>
      </div>
      <div class="col-md-3">
        <span>订单提交时间：</span><span id="orderStartDate"></span>
      </div>
      <div class="col-md-3">
        <span>订单状态：</span><span id="orderStatus"></span>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-hover">
          <caption></caption>
          <thead style="background-color: #eee">
          <tr>
            <th style="width: 10%;">产品名称</th>
            <th style="width: 40%;">基本配置</th>
            <th style="width: 10%;">计费方式</th>
            <th style="width: 10%;">审批流程</th>
            <th style="width: 30%;">购买量</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <th id="productName"></th>
            <td id="basicAttr"></td>
            <td id="payWayName"></td>
            <td id="approveName"></td>
            <td id="controlAttr"></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-hover">
          <caption>历史审批意见</caption>
          <thead style="background-color: #eee">
          <tr>
            <th>审批人</th>
            <th>审批结果</th>
            <th>审批意见</th>
            <th>审批时间</th>
          </tr>
          </thead>
          <tbody>
          <tr id="approveTr">

          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <form class="form-horizontal">
          <div class="form-group">
            <label class="col-sm-1 control-label">审批结果</label>
            <div class="col-sm-11">
              <label class="radio-inline">
                <input type="radio" name="isAgreement" id="inlineRadio1" value="1">同意
              </label>
              <label class="radio-inline">
                <input type="radio" name="isAgreement" id="inlineRadio2" value="2">不同意
              </label>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-1 control-label">审批意见</label>
            <div class="col-sm-11">
              <textarea class="form-control" rows="3" id="remark"></textarea>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <button type="button" class="btn btn-primary" style="float: right" id="approveSub">提交</button>
        <button type="button" class="btn btn-default" style="float: right; margin-right: 15px;" id="approveCancle">返回</button>
      </div>
    </div>
  </div>
<script>
  var curContext = '<%=path%>';
  var orderId = '${orderId}';
  var taskId = '${taskId}';
</script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/CodeSeven-toastr/build/toastr.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/order/approve.js"></script>
</body>
</html>
