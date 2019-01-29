<%--
  Created by IntelliJ IDEA.
  User: lumeiling
  Date: 2018/11/23
  Time: 下午1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>码表管理</title>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@include file="../base.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-ztree3/css/bootstrapStyle/bootstrapStyle.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order/index.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/code/codeTable.css">

	<style>
		.red-font {float: left;}
	</style>
</head>
<body>
<div class="codeTable-container">
	<!-- 结构树 -->
	<div class="codeTableList-left">
		<div class="left-title">字典结构
		</div>
		<div class="left-content">
			<ul id="codeTree" class="ztree">

			</ul>
		</div>
	</div>
	<!-- 右侧表 -->
	<div class="nav-collapse-left"><i class="fa fa-angle-double-left" aria-hidden="true"></i></div>
	<div class="nav-collapse-right"><i class="fa fa-angle-double-right" aria-hidden="true"></i></div>
	<div class="codeTableList-right">
		<div class="row">
			<div class="col-md-12">
				<%--<div class="inner-title">--%>
				<%--<h5>字典列表</h5>--%>
				<%--</div>--%>
				<div class="bconsole-header" style="height: 60px;">
					<span>字典列表</span>
				</div>
				<div class="bconsole-container">
					<form class="form-inline">
						<button type="button" class="btn btn-default btn-no-radius btn-bconsole" id="createItem" aria-label="Right Align" data-toggle="modal"
								data-target="#addCodeItem" style="float: left;">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建
						</button>
						<%--<button type="button" class="btn btn-primary" id="createItem" data-toggle="modal" data-target="#addCodeItem" style="float: right;">新建</button>--%>
					</form>
					<%--右侧列表表格--%>
					<div class="table-responsive" style="margin-top: 10px">
						<table class="table table-hover" id="initCodeTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 创建字典类型 -->
<div class="modal fade" id="addCodeSet" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#addCodeSetForm')">
				<a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
			</div>
			<div class="bconsole-modal-title">
				<div class="row">
					<img src="${pageContext.request.contextPath}/resources/img/common/new.png">
				</div>
				<span class="title">新增字典类型</span>
			</div>
			<div class="bconsole-modal-content">
				<form class="form-horizontal" id="addCodeSetForm">
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: -10px;">＊</span>字典类型ID</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="setId" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: 15px;">＊</span>父节点</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="parentId" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>类型名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="setName" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>状态</label>
						<div class="col-sm-9">
							<div class="form-radio-group">
								<input id="qid" type="radio" name="typeStatus" value="0" required>
								<label for="qid"></label>
								<span>启用</span>
							</div>
							<div class="form-radio-group">
								<input id="tingy" type="radio" name="typeStatus" value="1" required>
								<label for="tingy"></label>
								<span>停用</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="sortId" required>
						</div>
					</div>
				</form>
			</div>
			<div class="bconsole-modal-footer">
				<a class="modal-submit" onclick="saveAddCodeSet()">提交</a>
			</div>
		</div>
	</div>
</div>

<%--修改码表类型--%>
<div class="modal fade" id="codeSetEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#editCodeSetForm')">
				<a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
			</div>
			<div class="bconsole-modal-title">
				<div class="row">
					<img src="${pageContext.request.contextPath}/resources/img/common/basic-icon.png">
				</div>
				<span class="title">修改字典类型</span>
			</div>
			<div class="bconsole-modal-content">
				<form class="form-horizontal" id="editCodeSetForm">
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>类型名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="setName" required>
						</div>
					</div>
					<%--<div class="form-group">--%>
					<%--<label class="col-md-3 control-label"><span class="red-font">＊</span>访问路径</label>--%>
					<%--<div class="col-md-8">--%>
					<%--<input type="text" class="form-control" name="typePath" required>--%>
					<%--</div>--%>
					<%--</div>--%>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>状态</label>
						<div class="col-sm-9">
							<div class="form-radio-group">
								<input id="qid1" type="radio" name="typeStatus" value="0" required>
								<label for="qid1"></label>
								<span>启用</span>
							</div>
							<div class="form-radio-group">
								<input id="tingy1" type="radio" name="typeStatus" value="1" required>
								<label for="tingy1"></label>
								<span>停用</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="sortId" required>
						</div>
					</div>
				</form>
			</div>
			<div class="bconsole-modal-footer">
				<a class="modal-submit">保存</a>
			</div>
		</div>
	</div>
</div>

<%--修改字典值--%>
<div class="modal fade" id="codeItemEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#editCodeItemForm')">
				<a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
			</div>
			<div class="bconsole-modal-title">
				<div class="row">
					<img src="${pageContext.request.contextPath}/resources/img/common/basic-icon.png">
				</div>
				<span class="title">修改字典值</span>
			</div>
			<div class="bconsole-modal-content">
				<form class="form-horizontal" id="editCodeItemForm">
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemName" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: 15px;">＊</span>字典值</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemCode" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典说明</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemResume" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>状态</label>
						<div class="col-sm-9">
							<div class="form-radio-group">
								<input id="qid2" type="radio" name="typeStatus" value="0" required>
								<label for="qid2"></label>
								<span>启用</span>
							</div>
							<div class="form-radio-group">
								<input id="tingy2" type="radio" name="typeStatus" value="1" required>
								<label for="tingy2"></label>
								<span>停用</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="sortId" required>
						</div>
					</div>
				</form>
			</div>
			<div class="bconsole-modal-footer">
				<a class="modal-submit">保存</a>
			</div>
		</div>
	</div>
</div>

<!-- 添加字典值 -->
<div class="modal fade" id="addCodeItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#addCodeItemForm')">
				<a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
			</div>
			<div class="bconsole-modal-title">
				<div class="row">
					<img src="${pageContext.request.contextPath}/resources/img/common/new.png">
				</div>
				<span class="title">添加字典值</span>
			</div>
			<div class="bconsole-modal-content">
				<form class="form-horizontal" id="addCodeItemForm">
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: 15px;">＊</span>字典ID</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemId" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典类型</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="parentId" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemName" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: 15px;">＊</span>字典值</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemCode" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典说明</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemResume" required>
						</div>
					</div>
					<%--<div class="form-group">--%>
					<%--<label class="col-md-3 control-label"><span class="red-font">＊</span>访问路径</label>--%>
					<%--<div class="col-md-8">--%>
					<%--<input type="text" class="form-control" name="typePath" required>--%>
					<%--</div>--%>
					<%--</div>--%>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>状态</label>
						<div class="col-sm-9">
							<div class="form-radio-group">
								<input id="qid3" type="radio" name="typeStatus" value="0" required>
								<label for="qid3"></label>
								<span>启用</span>
							</div>
							<div class="form-radio-group">
								<input id="tingy3" type="radio" name="typeStatus" value="1" required>
								<label for="tingy3"></label>
								<span>停用</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="sortId" required>
						</div>
					</div>
				</form>
			</div>
			<div class="bconsole-modal-footer">
				<a class="modal-submit" onclick="saveAddCodeItem()">提交</a>
			</div>
		</div>
	</div>
</div>
<%--插入字典的子字典--%>
<div class="modal fade" id="addCodeItemForItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#addCodeItemForItemForm')">
				<a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
			</div>
			<div class="bconsole-modal-title">
				<div class="row">
					<img src="${pageContext.request.contextPath}/resources/img/common/new.png">
				</div>
				<span class="title">添加字典值</span>
			</div>

			<div class="bconsole-modal-content">
				<form class="form-horizontal" id="addCodeItemForItemForm">
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: 15px;">＊</span>字典ID</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemId" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>父级</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="parentId" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemName" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="margin-left: 15px;">＊</span>字典值</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemCode" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font">＊</span>字典说明</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="itemResume" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>状态</label>
						<div class="col-sm-9">
							<div class="form-radio-group">
								<input id="qid4" type="radio" name="typeStatus" value="0" required>
								<label for="qid4"></label>
								<span>启用</span>
							</div>
							<div class="form-radio-group">
								<input id="tingy4" type="radio" name="typeStatus" value="1" required>
								<label for="tingy4"></label>
								<span>停用</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"><span class="red-font" style="padding-left: 29px;">＊</span>序号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="sortId" required>
						</div>
					</div>
				</form>
			</div>
			<div class="bconsole-modal-footer">
				<a class="modal-submit" onclick="saveAddCodeItemForItem()">提交</a>
			</div>
		</div>
	</div>
</div>

<%--确认框:删除字典类型--%>
<div class="modal fade" id="deleteSetConfirmModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document" style="width: 250px;">
		<div class="modal-content bconsole-confirm-box" style="width: 250px;">
			<div class="modal-header bconsole-confirm-header">
				<h4 class="modal-title">删除信息</h4>
			</div>
			<div class="modal-body bconsole-confirm-body">
				<p>请问是否删除该字典类型？</p>
			</div>
			<div class="modal-footer bconsole-confirm-footer">
				<div class="confirm-box-ok">确定</div>
				<div class="confirm-box-cancel" data-dismiss="modal">取消</div>
			</div>
		</div>
	</div>
</div>

<%--确认框:删除字典数据--%>
<div class="modal fade" id="deleteItemConfirmModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document" style="width: 250px;">
		<div class="modal-content bconsole-confirm-box" style="width: 250px;">
			<div class="modal-header bconsole-confirm-header">
				<h4 class="modal-title">删除信息</h4>
			</div>
			<div class="modal-body bconsole-confirm-body">
				<p>请问是否删除该字典数据？</p>
			</div>
			<div class="modal-footer bconsole-confirm-footer">
				<div class="confirm-box-ok">确定</div>
				<div class="confirm-box-cancel" data-dismiss="modal">取消</div>
			</div>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
    var webpath = '<%=webpath%>';
</script>
<script src="<%=webpath %>/resources/plugin/layer/layer.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-ztree3/js/jquery.ztree.core.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-ztree3/js/jquery.ztree.excheck.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-ztree3/js/jquery.ztree.exedit.js"></script>
<script src="<%=webpath %>/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="<%=webpath %>/resources/js/product/product-common.js"></script>
<script src="<%=webpath %>/resources/js/code/codeInfo.js"></script>
</html>