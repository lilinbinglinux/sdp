<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../../common-head.jsp"%>
	<title>单点流程</title>
	<%@ include file="../../common-layer-ext.jsp"%>
	<%@ include file="../../common-body-css.jsp"%>
	<style type="text/css">
		body{
		  overflow:hidden;
		}
		#userTable{
		  width:100%;
		}
		#text_area{
		  width: 407px;
		  height: 80px;
		  border: 1px solid #ccc;
		}
	</style>
</head> 
<body>
	<div class="row">
	     <div class="col-lg-12 col-md-12 row-tab">
	         <div id="org-panel" class="panel panel-default common-wrapper">
  					<div class="panel-heading common-part"><i class="iconfont">&#xe6ca;</i><span>服务列表</span></div>
  					<div class="panel-body common-content">
   							<div class="searchWrap">
	                    		<form class="form-inline" id="userSearchForm">
  									<div class="form-group">
    									<label for="loginId">流程图名称:</label>
    									<input type="text" class="form-control input-sm" name="spname" id="spname" />
  									</div>
  									<div class="form-group">
    									<label for="userName">编码:</label>
    									<input type="text" class="form-control input-sm" name="spcode" id="spcode"/>
  									</div>
  									<button type="button" class="b-redBtn btn-i" id="searchBtn"><i class="iconfont">&#xe67a;</i>查询</button>
  									<button type="button" class="b-redBtn btn-i" id="resetBtn"><i class="iconfont">&#xe647;</i>重置</button>
  									<button type="button" class="b-redBtn btn-i" id="addSpSer"><i class="iconfont">&#xe635;</i>新建</button>
								</form>
	               			</div>
	               			
	               			<table id="ssoflowchartTable">  
                        		<thead>
            						<tr>
                						<th>单点登录名称</th>
                						<th>单点类型</th>
                						<th>服务编号</th>
                						<!--<th>appId</th>  -->
               						<th>请求协议</th>
                						<th>请求方式</th>
                						<th>创建时间</th>
                						<th>操作</th>
            						</tr>
        						</thead>
                    		</table>  
  					</div>
			 </div>
	     </div>
	</div>
	
	<!-- 修改用户信息 -->
	<div id="updateUser" class="dialog-wrap">
	     <form id="updateUserForm" class="form-inline" data-validator-option="{timely:2, theme:'yellow_right'}">
	                <input type="hidden" name="userId"/>
	                <table class="form-table">
	                   <tr>
	                      <td>
	                           <div class="form-group">
    								<label for="loginId">登录账号:</label>
    								<input type="text" class="form-control input-sm" name="loginId" readonly/>
  							   </div>
	                      </td>
	                      <td>
	                           <div class="form-group">
    								<label for="userName">用户姓名:</label>
    								<input type="text" class="form-control input-sm" name="userName" placeholder="请输入用户姓名" data-rule="required;length(2~32);filter;"/>
  							   </div>
	                      </td>
	                   </tr>
	                   <tr><td>
	                           <div class="form-group">
    								<label for="userName">用户邮箱:</label>
    								<input type="text" class="form-control input-sm" name="emall" placeholder="请输入用户的电子邮箱" data-rule="email"/>
  							   </div>
	                       </td>
	                       <td>
	                           <div class="form-group">
    								<label for="userName">手机号码:</label>
    								<input type="text" class="form-control input-sm" name="mobile" placeholder="请输入11位手机号码" data-rule="mobile"/>
  							   </div>
	                       </td>
	                    </tr>
	                    <tr>
	                       <td>
	                           <div class="form-group">
    								<label for="userName">用户状态:</label>
    								<label>
    									<input type="radio" value="1" name="state" data-rule="checked(1)">
    									未锁定
  									</label>
  									<label>
    									<input type="radio" value="-1" name="state" data-rule="checked(1)">
    									已锁定
  									</label>
  							   </div>
	                       </td>
	                       <td>
                                <div class="form-group">
   									<label for="orgName">所属组织:</label>
   									<input type="text" class="form-control input-sm" name="orgName" readonly onclick="showOrgTree(this);"/>
 							        	<input type="hidden" class="form-control input-sm" name="orgIds" />
 							        </div>
	                       </td>
	                    </tr>
	                    <tr>
	                    	<td colspan=2>
	                    		<label for="text_area" class="text-label">备注:</label>
	                    		<textarea name="textArea" id="text_area"></textarea>
	                    	</td>
	                    </tr>
	                </table>
		</form>
	</div>
	
	
	<!-- 流程图类型弹框 -->
	<div style="display:none;margin-top: 5px" id="sptypediv">
			<p2 style="font-size: 17px;margin-left: 47px;">请选择单点类型：</p2><br>
			
			<select name="sptype" id="sptype" style="width: 200px;height: 30px;margin-left: 50px;margin-top: 12px;background: #a0c5ea;">
				<c:forEach var="it" items="${serFlowTypes}">
				<option value="${it.serTypeId}">${it.serTypeName}</option>
				</c:forEach>
			</select>
	
	</div>
	
	
	<!-- 参数详情弹框 -->
	<div id="interfaceinfodiv" style="display:none;" class="panel-body">
        <div style="margin-top: 5%;">
            url：
            <textarea rows="3" cols="98" id="urlreqparam-url" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
        </div>
        <div style="margin-top: 2%;">
            请求参数格式：
            <textarea rows="5" cols="98" id="urlreqparam-code" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
        </div>
        <div style="margin-top: 2%">
            响应参数格式：
            <textarea rows="5" cols="98" id="urlresponseparam-code" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
        </div>
    </div>
	
	<%@ include file="../../common-js.jsp"%>
	<script src="<%=webpath %>/resources/js/util_common.js"></script>
	<script src="<%=webpath %>/resources/js/serlayout/sso/ssoflowchart.js"></script>
</body>
</html>