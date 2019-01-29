<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common-head.jsp"%>
	<title>租户管理</title>
	<%@ include file="../common-layer-ext.jsp"%>
	<%@ include file="../common-body-css.jsp"%>
	<style type="text/css">
		body{
		  overflow:hidden;
		}
		#tenantTable{
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
  					<div class="panel-heading common-part"><i class="iconfont">&#xe6ca;</i><span>租户列表</span></div>
  					<div class="panel-body common-content">
   							<div class="searchWrap">
	                    		<form class="form-inline" id="tenantSearchForm">
  									<div class="form-group">
    									<label for="tenantName">租户名称:</label>
    									<input type="text" class="form-control input-sm" name="tenantName" />
  									</div>
  									<div class="form-group">
    									<label for="state">是否系统租户:</label>
    									<input type="text" class="form-control input-sm" name="state"/>
  									</div>
  									<button type="button" class="b-redBtn btn-i" id="searchBtn"><i class="iconfont">&#xe67a;</i>查询</button>
  									<button type="button" class="b-redBtn btn-i" id="resetBtn"><i class="iconfont">&#xe647;</i>重置</button>
  									<button type="button" class="b-redBtn btn-i" id="addTenantBtn"><i class="iconfont">&#xe635;</i>新建租户</button>
								</form>
	               			</div>
	               			
	               			<table id="tenantTable">  
                        		<thead>
            						<tr>
                						<th>租户名称</th>
                						<th>是否系统租户</th>
               							<th>备注</th>
                						<th>排序</th>
                						<th>操作</th>
            						</tr>
        						</thead>
                    		</table>  
  					</div>
			 </div>
	     </div>
	</div>
	
	
	<!-- 新建用户dialog -->
	<div id="addTenant" class="dialog-wrap">
	     <form id="addTenantForm" class="form-inline" data-validator-option="{timely:2, theme:'yellow_right'}">
	         <table class="form-table">
	            <tr>
	               <td>
	                 <div class="form-group">
					   <label for="tenantName">租户名称:</label>
					   <input type="text" class="form-control input-sm" name="tenantName" placeholder="请输入租户" data-rule="required;length(4~32);filter;"/>
		             </div>
	               </td>
	            </tr>
	            <tr>
	               <td>
	                 <div class="form-group">
					    <label for="memo">备注:</label>
					    <input type="text" class="form-control input-sm" name="memo" placeholder="请输入备注" data-rule="email"/>
		             </div>
	                </td>
	             </tr>
	             <tr>
	                <td>
	                    <div class="form-group">
						  <label for="state">是否系统租户:</label>
					      <label>
						  <input type="radio" value="1" name="state" data-rule="checked(1)">
						    是
						  </label>
						  <label>
						  <input type="radio" value="-1" name="state" data-rule="checked(1)">
						   否
						  </label>
				   		</div>
	                </td>
	             </tr>
	         </table>
		</form>
	</div>
	
	
	<!-- 修改用户信息 -->
	<div id="updateTenant" class="dialog-wrap">
	     <form id="updateTenantForm" class="form-inline" data-validator-option="{timely:2, theme:'yellow_right'}">
	                <input type="hidden" name="tenant_id"/>
	                <table class="form-table">
	                   <tr>
	                      <td>
	                           <div class="form-group">
    								<label for="tenantName">租户名称:</label>
    								<input type="text" class="form-control input-sm" name="tenantName" placeholder="请输入租户名称" data-rule="required;length(2~32);filter;"/>
  							   </div>
	                      </td>
	                   </tr>
	                    <tr>
	                    	<td>
	                 <div class="form-group">
					    <label for="memo">备注:</label>
					    <input type="text" class="form-control input-sm" name="memo" placeholder="请输入备注" data-rule="email"/>
		             </div>
	                </td>
	                    </tr>
	                   <tr>
	                <td>
	                    <div class="form-group">
						  <label for="state">是否系统租户:</label>
					      <label>
						  <input type="radio" value="1" name="state" data-rule="checked(1)">
						    是
						  </label>
						  <label>
						  <input type="radio" value="-1" name="state" data-rule="checked(1)">
						   否
						  </label>
				   		</div>
	                </td>
	                
	             </tr>
	                </table>
		</form>
	</div>
	<%@ include file="../common-js.jsp"%>
	<script src="<%=webpath %>/resources/js/systemconfig/tenant_manage.js"></script>
</body>
</html>