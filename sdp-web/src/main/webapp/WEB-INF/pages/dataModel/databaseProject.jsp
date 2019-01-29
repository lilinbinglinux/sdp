<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <title>项目管理</title>

  <link rel="stylesheet" href="<%=path%>/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="<%=path%>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
  <link rel="stylesheet" href="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="<%=path%>/resources/css/dataModel/project.css">
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-3">
        <div class="project-left-menu">
          <div class="hr-line-dashed"></div>
          <button type="button" class="btn btn-primary btn-sm btn-block" id="">添加项目</button>
          <div class="hr-line-dashed"></div>
          <h6>项目结构</h6>
          <ul id="curTree" class="ztree">

          </ul>
        </div>
      </div>
      <div class="col-md-9">
        <div class="project-right-content">
          <div class="inner-word"><h1><small>项目管理</small></h1></div>
          <iframe id="IUFrame" class="IUFrame" scrolling="no"></iframe>
        </div>
      </div>
    </div>
  </div>
  <ul id="menu3" class="dropdown-menu rMenu" aria-labelledby="drop6">

  </ul>
  <%--新增模块--%>
  <div id="addProjectModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">添加模块</h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="newModulesub">提交</button>
        </div>
      </div>
    </div>
  </div>
  <%--编辑项目--%>
  <div id="editProjectModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">项目编辑</h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="editProjectsub">提交</button>
        </div>
      </div>
    </div>
  </div>
  <%--删除项目--%>
  <div id="deleteProjectModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">项目删除</h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="deleteProjectsub">提交</button>
        </div>
      </div>
    </div>
  </div>
  <%--新增子模块--%>
  <div id="addModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">新增模块</h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="addModulesub">提交</button>
        </div>
      </div>
    </div>
  </div>
  <%--编辑子模块--%>
  <div id="editModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">模块编辑</h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="editModulesub">提交</button>
        </div>
      </div>
    </div>
  </div>
  <%--删除子模块--%>
  <div id="deleteModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">模块删除</h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="deleteModulesub">确认</button>
        </div>
      </div>
    </div>
  </div>
  <%--删除节点--%>
  <div id="deleteNode" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title"></h4>
        </div>
        <div class="modal-body">

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="deleteNodeSub">确认</button>
        </div>
      </div>
    </div>
  </div>
  <script>
	  var curContext = '<%=path%>';
  </script>
  <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
  <script src="${pageContext.request.contextPath}/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/dataModel/uuid.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/dataModel/project.js"></script>
</body>
</html>
