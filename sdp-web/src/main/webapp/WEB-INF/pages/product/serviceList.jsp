<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>服务列表-实例</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/serviceList.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
</head>
<body>
  <div class="serviceList-container">
    <div class="serviceList-left">
      <div class="left-title">服务分类</div>
      <div class="left-content">
        <ul id="serviceTree" class="ztree">

        </ul>
      </div>
    </div>
    <div class="nav-collapse-left"><i class="fa fa-angle-double-left" aria-hidden="true"></i></div>
    <div class="nav-collapse-right"><i class="fa fa-angle-double-right" aria-hidden="true"></i></div>
    <div class="serviceList-right">
      <div class="" style="padding: 0 15px 15px;">
        <div class="">
          <div class="inner-title"><h5>服务列表</h5></div>
          <input type="text" id="productId" hidden>
          <div class="table-responsive service-table-container">
            <table class="table table-hover" id="service-table">
              <thead>
              <tr>
                <th>序号</th>
                <th>服务名称</th>
                <th>版本</th>
                <th>创建时间</th>
                <th>更新时间</th>
                <th>服务状态</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
          </div>
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

  <%--服务删除--%>
  <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">删除实例</h4>
          <input type="text" hidden id="delete-caseId">
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <div class="delete-btn-group">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" id="delete-sub">确认</button>
          </div>
          <div style="float: right;"><button type="button" class="btn btn-default back-btn" data-dismiss="modal" style="display: none;"><img src="${pageContext.request.contextPath}/resources/img/dataModel/loading.gif" style="height: 20px;"></button></div>
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
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/order/serviceList.js"></script>
</body>
</html>
