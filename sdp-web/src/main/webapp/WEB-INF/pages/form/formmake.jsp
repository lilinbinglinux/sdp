<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>表单制作</title>
<%@ include file="../common-head.jsp"%>
<%@ include file="../common-layer-ext.jsp"%>
<%@ include file="../common-body-css.jsp"%>
<style type="text/css">
body {
	overflow: hidden;
}

#tenantTable {
	width: 100%;
}

#text_area {
	width: 407px;
	height: 80px;
	border: 1px solid #ccc;
}

.webfx-menu--button {
	cursor: pointer;
	float: left;
	clear: none;
	height: 25px;
	white-space: nowrap;
	padding-top: 3px;
	padding-right: 8px;
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-lg-12 col-md-12 row-tab">
			<div id="org-panel" class="panel panel-default common-wrapper">
				<div class="panel-heading common-part">
					<i class="iconfont">&#xe6ca;</i><span>表单列表</span>
				</div>
				<div class="panel-body common-content">
					<div class="searchWrap">
						<form class="form-inline" id="formSearchForm">
							<div class="form-group">
								<label for="tablecnname">表单名称:</label> <input type="text"
									class="form-control input-sm" name="tablecnname" />
							</div>
							<button type="button" class="b-redBtn btn-i" id="searchBtn">
								<i class="iconfont">&#xe67a;</i>查询
							</button>
							<button type="button" class="b-redBtn btn-i" id="resetBtn">
								<i class="iconfont">&#xe647;</i>重置
							</button>
							<button type="button" class="b-redBtn btn-i" id="addBtn">
								<i class="iconfont">&#xe635;</i>新建
							</button>
						</form>
					</div>

					<table id="formTable">
						<thead>
							<tr>
								<th>表单名称</th>
								<th>说明</th>
								<th>操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>


	<!--新建表单dialog  -->
	<div id="addForm" class="dialog-wrap">
		<form name="frm" id="frm" class="form-inline"
			data-validator-option="{timely:2, theme:'yellow_right'}">
			<table class="form-table">
				<tr>
					<td>
						<div class="form-group">
							<label for="tablecnname">表单名称:</label> <input name="tablecnname"
								class="form-control input-sm" type="text" placeholder="请输入表单"
								data-rule="required;length(4~32);filter;">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="udtemplateCode">表单代码:</label> <input
								name="udtemplateCode" class="form-control input-sm" type="text"
								placeholder="请输入表单代码">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="remark">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
							<textarea name="remark" class="form-control input-sm" rows="3"
								placeholder="请输入备注"></textarea>
						</div>
					</td>
				</tr>

				<tr>
					<td width="40%" align="right">&nbsp;</td>

				</tr>
			</table>
		</form>
	</div>


	<!--  修改表单dialog-->
	<div id="updateForm" class="dialog-wrap">
		<form id="updateFormTable" class="form-inline"
			data-validator-option="{timely:2, theme:'yellow_right'}">
			<input type="hidden" name="id" />
			<table class="form-table">
				<tr>
					<td>
						<div class="form-group">
							<label for="tablecnname">表单名称:</label> <input name="tablecnname"
								align="left" class="form-control input-sm" type="text"
								aria-required="true" placeholder="请输入表单名称">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="remark">说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明:</label>
							<textarea name="remark" cols="20" rows="3" id="remark"
								class="form-control input-sm n-valid"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="exampletitle" style="float: left">表单字段:</label>
							<div style="float: left">&nbsp;</div>
							<div style="float: left; margin-top: 8px">
								<div id="btn"></div>
								<textarea id="exampletitle"
									class="form-control input-sm n-valid" name="exampletitle"
									cols="20" rows="3"></textarea>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!--插入字段  -->
	<div id="insertField" class="dialog-wrap">
		<table id="fieldTable" class="form-table" width="100%">
			<thead>
				<tr>
					<th>选中</th>
					<th>字段名称</th>
					<th>字段类型</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 字段管理 -->
	<div id="fieldManage" class="dialog-wrap">
		<div id="fieldBtn">
			<table id="ManageTable" class="form-table">
				<tbody>
					<tr>
						<div id="webfx-menu-object-1" class="webfx-menu-bar">
							<button type="button" class="b-redBtn btn-i" id="start-file-btn">
								<i class="iconfont">&#xe67a;</i>启用字段
							</button>
							<button type="button" class="b-redBtn btn-i" id="stop-file-btn">
								<i class="iconfont">&#xe67a;</i>停用字段
							</button>
							<button type="button" class="b-redBtn btn-i" id="add-file-btn">
								<i class="iconfont">&#xe67a;</i>新增
							</button>
						</div>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="tbodyid">
			<table id="fieldManageTable" width="100%">
				<thead>
					<tr class="sort">
						<th>字段名称</th>
						<th>类 型</th>
						<th>长 度</th>
						<th>是否为空</th>
						<!-- <td>说明</td> -->
						<th>操 作</th>
					</tr>
				</thead>

			</table>
		</div>
	</div>
	<!-- 新增字段 -->
	<div id="addField" class="dialog-wrap">
		<form class="form-inline" id="addFieldForm"
			data-validator-option="{timely:2, theme:'yellow_right'}">
			<input name="id" type="hidden">
			<table class="form-table">
				<tr>
					<td>
						<div class="form-group">
							<label for="fieldcname">字段名称：</label> <input name="fieldcname"
								class="form-control input-sm" placeholder="请输入字段" type="text">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="fieldename">英文名称：</label> <input name="fieldename"
								class="form-control input-sm" placeholder="请输入英文名称" type="text">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="fieldtype">字段属性：</label> <select name="fieldtype"
								id="fieldtype" class="form-control input-sm">
								<option value="1">字符型</option>
								<option value="2">大字符型</option>
								<option value="3">数字型</option>
								<option value="4">日期型(yyyy-MM-dd)</option>
								<option value="5">部门</option>
								<option value="6">用户</option>
								<option value="7">码表</option>
								<option value="8">流程意见</option>
								<option value="10">取值</option>
								<option value="11">联动取值（主）</option>
								<option value="12">联动取值（辅）</option>
								<option value="13">联动枚举</option>
								<option value="14">复选类型</option>
								<option value="15">时间型(HH:mm)</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="datalenth">长&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</label> <input name="datalenth"
								class="form-control input-sm" type="text">
					</td>
				</tr>
				<tr>
				<td>
					<div class="form-group">
						  <label for="need">是否是否为空:</label>
					      <label>
						  <input type="radio" value="1" name="need" data-rule="checked(1)">
						    是
						  </label>
						  <label>
						  <input type="radio" value="0" name="need" data-rule="checked(1)">
						   否
						  </label>
				   		</div>
				</td>

				<td id="templatecodeHide"></td>
				</tr>
			</table>
		</form>
	</div>


	<%@ include file="../common-js.jsp"%>
	<script src="<%=webpath%>/resources/js/form/form_manage.js"></script>

</body>
</html>