<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>Title</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/css/bootstrapValidator.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/message/message.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/accordion.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/myLayerSkin.css">
</head>
<body>
<div class="container-power">
  <div class="power-menu-list" style="padding: 0 15px;">
    <span class="new-folder">新建</span>
    <ol class="breadcrumb">
      <li> <a href="#" class="goDataTypeManage">数据源类型管理</a></li>
      <li><a href="#" id="dataSource"></a><input hidden="hidden" type="text" id="dataResTypeId" ></li>
    </ol>
  </div>
  <div class="power-main" style="padding: 15px;">
    <div class="folder-list">
      <%--<div class="folder-box">--%>
        <%--<span class="folder-ico"><i class="fa fa-folder" aria-hidden="true"></i></span>--%>
        <%--<span class="folder-name">文件夹</span>--%>
      <%--</div>--%>
    </div>
    <ul class="menu-context dropdown-menu">
      <li><a href="#" class="menu-con-item con-edit">编辑</a></li>
      <li role="separator" class="divider"></li>
      <li><a href="#" class="menu-con-item con-delete">删除</a></li>
    </ul>
    <ul class="background-context dropdown-menu">
      <li><a href="#" class="menu-con-item con-new">新增</a></li>
      <li role="separator" class="divider"></li>
      <li><a href="#" class="menu-con-item con-refresh">刷新</a></li>
    </ul>
  </div>
</div>

<%--新增数据源类型--%>
<div id="newDataType" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增数据源类型</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="dataForm">
          <div class="form-group">
            <label for="dataResTypeName" class="col-sm-2 control-label">类型名称</label>
            <div class="col-sm-10">
              <input type="text" name="dataResTypeName" autocomplete="off" class="form-control" id="dataResTypeName">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">类型分类</label>
            <div class="col-sm-10">
              <select class="form-control" name="resType" id="resType">
                <option value="1">MySql</option>
                <option value="2">Oracle</option>
                <option value="5">SqlServer</option>
                <option value="3">DB2</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="dataDriver" class="col-sm-2 control-label">驱动</label>
            <div class="col-sm-10">
              <input type="text" name="dataDriver" autocomplete="off" class="form-control" id="dataDriver">
            </div>
          </div>
          <div class="form-group">
            <label for="resume" class="col-sm-2 control-label">说明</label>
            <div class="col-sm-10">
              <input type="text" name="resume" autocomplete="off" class="form-control" id="resume">
            </div>
          </div>
          <div class="form-group">
            <label for="sortNum" class="col-sm-2 control-label">序号</label>
            <div class="col-sm-10">
              <input type="text" name="sortNum" autocomplete="off" class="form-control" id="sortNum">
            </div>
          </div>
          <div class="form-group">
            <label for="sortNum" class="col-sm-2 control-label">是否启用</label>
            <div class="col-sm-10">
              <label class="checkbox-inline">
                <input type="radio" name="dataStutas" value="1" checked> 启用
              </label>
              <label class="checkbox-inline">
                <input type="radio" name="dataStutas"  value="0"> 停用
              </label>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="newDataSub">提交</button>
      </div>
    </div>
  </div>
</div>

<%--编辑数据源类型--%>
<div id="editDataType" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">编辑数据源类型</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="editForm">
          <div class="form-group">
            <label for="dataResTypeName" class="col-sm-2 control-label">类型名称</label>
            <div class="col-sm-10">
              <input type="text" name="dataResTypeName" autocomplete="off" class="form-control">
              <input hidden name="dataResTypeId">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">类型分类</label>
            <div class="col-sm-10">
              <select class="form-control" name="resType">
                <option value="1">MySql</option>
                <option value="2">Oracle</option>
                <option value="5">SqlServer</option>
                <option value="3">DB2</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="dataDriver" class="col-sm-2 control-label">驱动</label>
            <div class="col-sm-10">
              <input type="text" name="dataDriver" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resume" class="col-sm-2 control-label">说明</label>
            <div class="col-sm-10">
              <input type="text" name="resume" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="sortNum" class="col-sm-2 control-label">序号</label>
            <div class="col-sm-10">
              <input type="text" name="sortNum" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="sortNum" class="col-sm-2 control-label">是否启用</label>
            <div class="col-sm-10">
              <label class="checkbox-inline">
                <input type="radio" name="dataStutas" value="1"> 启用
              </label>
              <label class="checkbox-inline">
                <input type="radio" name="dataStutas"  value="0"> 停用
              </label>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="editDataSub">提交</button>
      </div>
    </div>
  </div>
</div>

<%--数据源模态框--%>
<div id="sourceModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增数据源</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="sourceForm">
          <div class="form-group">
            <label for="dataResTypeName" class="col-sm-2 control-label">数据源名称</label>
            <div class="col-sm-10">
              <input type="text" name="dataResTypeName" autocomplete="off" class="form-control">
              <input hidden name="dataResTypeId">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">数据源类型</label>
            <div class="col-sm-10">
              <input type="text" autocomplete="off" class="form-control" readonly id="sourceTypeName">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">数据源链接</label>
            <div class="col-sm-10">
              <input type="text" name="dataResUrl" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">Schema名称</label>
            <div class="col-sm-10">
              <input type="text" name="tableSchema" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">数据源用户名</label>
            <div class="col-sm-10">
              <input type="text" name="username" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">数据源密码</label>
            <div class="col-sm-10">
              <input type="text" name="password" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">状态</label>
            <div class="col-sm-10">
              <label class="checkbox-inline">
                <input type="radio" name="dataStatus" value="1" checked> 启用
              </label>
              <label class="checkbox-inline">
                <input type="radio" name="dataStatus"  value="0"> 停用
              </label>
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">是否默认数据源</label>
            <div class="col-sm-10">
              <label class="checkbox-inline">
                <input type="radio" name="isDefault" value="1" checked> 是
              </label>
              <label class="checkbox-inline">
                <input type="radio" name="isDefault" value="0"> 否
              </label>
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">数据库说明</label>
            <div class="col-sm-10">
              <input type="text" name="dataResDesc" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">备注</label>
            <div class="col-sm-10">
              <input type="text" name="resume" autocomplete="off" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="resType" class="col-sm-2 control-label">序号</label>
            <div class="col-sm-10">
              <input type="text" name="sortNum" autocomplete="off" class="form-control">
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="sourceSub">提交</button>
      </div>
    </div>
  </div>
</div>

<%--删除模态框--%>
<div id="deleteModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">删除</h4>
      </div>
      <div class="modal-body">

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="deleteSub">提交</button>
      </div>
    </div>
  </div>
</div>

<script>
	var curContext = '<%=path%>';
</script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/message/message.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/js/language/zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/uuid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/dataIndex.js"></script>