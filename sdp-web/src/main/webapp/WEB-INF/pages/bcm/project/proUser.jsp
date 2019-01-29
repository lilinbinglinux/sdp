<%--
  Created by IntelliJ IDEA.
  User: lumeiling
  Date: 2018/11/27
  Time: 上午9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String webpath = request.getContextPath();
    String loginUser = request.getRemoteUser();
%>
<%
    String ref = request.getHeader("REFERER");
%>
<html>
<head>
    <title>项目人员管理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="../../base.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table-master/dist/bootstrap-table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jQuery-ComboSelect/css/combo.select.css">
    <style>
        .combo-select {max-width: 100%;}
        .combo-input {
            margin: 1px;
            margin-left: 0px;
        }
    </style>
</head>
<body>
<div class="panel panel-default" style="margin-top: 5%;margin-left: 10%;margin-right: 10%;">
    <div class="panel-heading" style="margin-left: 1px;">添加新成员到项目
        <a class="pull-right" onclick="javascript:window.location='<%=ref%>';" style="cursor:pointer">返回</a>
    </div>
    <div class="panel-body">
        <form id="addProUser">
            <div class="form-group">
                <label>用户</label>
                <select id='searchUser' class="form-control" name='userId' onclick="searchingUsers()" style="height: 44px;">

                </select>
                <div class="help-block">
                    搜索已存在的用户名或注册ID
                </div>
            </div>
            <div class="form-group">
                <label>项目角色</label>
                <select id="searchRole" class="form-control" name="projectRoleId" onclick="searchingRole()" style="height: 44px;">

                </select>
            </div>
        </form>
        <div class="form-actions">
            <button id="addUser" type="submit" class="btn btn-primary" onclick="saveProUser()">添加用户到项目</button>
        </div>
    </div>
</div>
<div class="panel panel-default" style="margin-top: 3%;margin-left: 10%;margin-right: 10%;">
    <div class="panel-heading" id="proUserNum" style="margin-left: 1px;">

    </div>
    <div class="panel-body">
        <ul class="list-group" id="usersList" style="padding-left: 2px;">

        </ul>
    </div>
</div>
</body>

<script type="text/javascript">
    var webpath = '<%=webpath%>';
    var loginUser = '<%=loginUser%>';
</script>

<script src="<%=webpath %>/resources/plugin/layer/layer.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-table-master/dist/bootstrap-table.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="<%=webpath %>/resources/js/product/product-common.js"></script>
<script src="<%=webpath %>/resources/plugin/jQuery-ComboSelect/js/jquery-1.7.min.js"></script>
<script src="<%=webpath %>/resources/plugin/jQuery-ComboSelect/js/jquery.combo.select.js"></script>
<script src="<%=webpath %>/resources/js/bcm/project/proUser.js"></script>
</html>
