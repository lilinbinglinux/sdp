<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>我的配额</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/serviceList.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body class="body-bg">
  <div class="container-fluid order-container">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">资源使用情况</h3>
      </div>
      <div class="panel-body" id="res-used">
        <%--<div class="quota-detail-list">--%>
          <%--<div class="quota-item quota-item-title">--%>
            <%--CPU--%>
          <%--</div>--%>
          <%--<div class="quota-item quota-item-process">--%>
            <%--<div class="progress">--%>
              <%--<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">--%>
                <%--40%--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</div>--%>
          <%--<div class="quota-item quota-item-content">--%>
            <%--15.75/ 192（个）--%>
          <%--</div>--%>
        <%--</div>--%>
        <%--<div class="quota-detail-list">--%>
          <%--<div class="quota-item quota-item-title">--%>
            <%--CPU--%>
          <%--</div>--%>
          <%--<div class="quota-item quota-item-process">--%>
            <%--<div class="progress">--%>
              <%--<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">--%>
                <%--40%--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</div>--%>
          <%--<div class="quota-item quota-item-content">--%>
            <%--15.75/ 192（个）--%>
          <%--</div>--%>
        <%--</div>--%>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">我的配额</h3>
      </div>
      <div class="panel-body">
        <div class="table-responsive quota-detail-table">
          <div style="margin-bottom: 15px;">
            <button class="btn btn-default" type="button" id="quota-apply-btn">配额申请</button>
          </div>
          <table class="table table-hover" id="quota-detail-table">
            <thead>
            <tr>
              <th>序号</th>
              <th>服务名称</th>
              <th>创建时间</th>
              <th>创建者</th>
              <th>运行状态</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody></tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  <div class="quota-apply-container">
    <div class="quata-container">
      <div class="page-header">
        <h1 style="text-align: center;margin-top: 80px;"><small>配额申请</small></h1>
        <input type="text" hidden id="productId">
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
      <div class="quota-close"><i class="fa fa-times" aria-hidden="true"></i></div>
    </div>
    <div class="quota-result-container" style="display: none;">
      <div class="row">
        <div class="col-md-12 result-word" style="text-align: center">
          配额申请提交成功！前往<span style="color: #1890ff;cursor: pointer;" id="to_process">工单审批</span>查看
        </div>
      </div>
    </div>
  </div>
  <%--服务详情--%>
  <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">配额详情</h4>
        </div>
        <div class="modal-body">

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
  <script src="${pageContext.request.contextPath}/resources/js/order/quotaOfMine.js"></script>
</body>
</html>
