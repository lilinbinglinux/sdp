<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>服务实例</title>

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
  <div class="page-header">
    <h1><small>服务实例-</small><small id="exampleName"></small></h1>
  </div>
  <div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active"><a href="#basic" aria-controls="home" role="tab" data-toggle="tab">基本信息</a></li>
      <li role="presentation"><a href="#control" aria-controls="control" role="tab" data-toggle="tab">监控</a></li>
      <li role="presentation"><a href="#log" aria-controls="log" role="tab" data-toggle="tab">日志</a></li>
      <li role="presentation"><a href="#backups" aria-controls="backups" role="tab" data-toggle="tab">备份</a></li>
      <li role="presentation"><a href="#recovery" aria-controls="recovery" role="tab" data-toggle="tab">恢复</a></li>
      <li role="presentation"><a href="#operation" aria-controls="operation" role="tab" data-toggle="tab">操作记录</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content" style="padding-top: 30px;">
      <div role="tabpanel" class="tab-pane active" id="basic">
        <div class="table-responsive">
          <table class="table table-hover" id="example-table">

          </table>
        </div>
      </div>
      <div role="tabpanel" class="tab-pane" id="control">
        <%--监控--%>

      </div>
      <div role="tabpanel" class="tab-pane" id="log">
        <%--日志--%>

      </div>
      <div role="tabpanel" class="tab-pane" id="backups">
        <%--备份--%>

      </div>
      <div role="tabpanel" class="tab-pane" id="recovery">
        <%--恢复--%>

      </div>
      <div role="tabpanel" class="tab-pane" id="operation">
        <%--操作记录--%>

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
        <h4 class="modal-title" id="myModalLabel">实例详情</h4>
      </div>
      <div class="modal-body">

      </div>
    </div>
  </div>
</div>
<script>
	var curContext = '<%=path%>';
	var curProductId = '${productId}';
	var curCaseId = '${caseId}';
</script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/order/serviceExample.js"></script>
</body>
</html>
