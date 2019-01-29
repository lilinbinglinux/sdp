<%@page import="com.netflix.governator.annotations.binding.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/puborder/order/order-model.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>

    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common-head.jsp"%>
<%@ include file="../../common-layer-ext.jsp"%>
<%@ include file="../../common-body-css.jsp"%>
<%@ include file="../../common-js.jsp"%>
<style type="text/css">
    body{
            overflow:hidden;
            background:#fff!important;
         }
</style>
<title>申请接口</title>
</head>

<body>
    <!--右侧面板显示参数详情  -->
    <div class="panel-body common-content" id="id-applyinterfacediv">
        <div>
            <ul class="nav nav-tabs">
              <li role="presentation" class="active" id="id-pubinterface1"><a href="#">Step1-无参调用</a></li>
              <!-- <li role="presentation" id="id-pubinterface2"><a href="#">参数匹配</a></li> -->
              <li role="presentation" id="id-pubparamshow"><a href="#">Step2-申请接口参数说明</a></li>
              <li role="presentation" id="security-apiTestBtn"><a href="#">Step3-API测试</a></li>
              <li role="presentation" id="security-tokenBtn"><a href="#">Step4-密钥生成</a></li>
            </ul>
        </div>
        <input id="id-interfacepubid" value="${id}" type="hidden">
        
        <!-- 无参调用 -->
        <div id="id-interfaceapply1" style="margin-top: 30px;">
	        <h4>接口信息</h4>
	        <div>
		        <div class="panel-body common-content" id="publish-div">
		        <div class="searchWrap" style="display: none;">
		            <form class="form-inline" id="pubidtopublish">
		                <div class="form-group">
		                    <input type="hidden" id="publishmanagepubid" name="parentId">
		                    <input type="hidden" id="res-req" name="type" value="0">
		                </div>
		            </form>
		        </div>
		        
		        
		<!-- 接口申请 -->
        <div id="add-orderInterModal">
            <!-- 接口申请 详细设置 -->
            <div id="add-interfaceOrderModel" style="height: auto;">
                <input type="hidden" id="id-pubapiId" />
                <input type="hidden" id="id-appId" />
                    <div class="form-group">
                        <label for="name">申请接口名称:</label> <input type="text"
                            class="form-control input-sm form-sm" name="name" id="name" />
                    </div>
                    <div class="form-group">
                        <label for="ordercode">编码:</label> <input type="text"
                            class="form-control input-sm form-sm" name="ordercode"
                            id="ordercode" />
                    </div>
                    
                    <div class="form-group">
                        <label for="reqformat">请求返回格式:</label>
                        <select  class="form-control input-sm" style="width: 130px;" name="reqformat" id="reqformat">
						  <option value ="application/json" selected="selected">application/json</option>
						  <option value ="application/xml">application/xml</option>
						  <option value="其他">其他</option>
						</select>
                    </div>
                    
                    <div class="form-group" id="id-reqparam-div" style="display: none;">
                        <label for="respformat">请求参数格式:</label>
                        <select  class="form-control input-sm" style="width: 130px;" name="respformat" id="respformat">
                          <option value ="application/json" selected="selected">application/json</option>
                          <option value ="application/xml">application/xml</option>
                          <option value="其他">其他</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                    <label for="setparamselect">设置参数:</label>
                       <select class="form-control input-sm" name="setparamselect" id="setparamselect">
                        <option value="0" selected="selected">不需要设置参数</option>
                        <option value="1">输入参数</option>
                       </select>
                    </div>
             </div>

        </div>
	        <div style="float: right;">
		        <button type="button" class="btn btn-primary" ><a href="javascript:history.go(-1)">取消申请</a></button>
                <button type="button" class="btn btn-danger" id="step1-next-step">下一步</button>
               </div>
		        </div> 
	        </div>
        </div>
        
        
        <!-- 默认参数 -->
        <div id="id-interfaceapply2" style="display: none;">
            <h4>参数匹配
            <div class="btn-group">
                 <button type="button" class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" 
                 id="id-paramname-btn">
				    参数映射方式 <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
				    <li><a href="#" id="id-defaultparam-li">默认参数映射</a></li>
				    <li><a href="#" id="id-setparam-li">自定义参数映射</a></li>
				  </ul>
				  </div>
				  <input type="hidden" id="id-methodparam-input" value="0" />
            </h4> 
            <div>
                <div class="panel-body common-content" id="publish-div">
                <input type="hidden" id="id-isParamSubsist" value="0" />
                <table id="id-interfacetable2">  
                    <thead>
                            <tr>
                                <th>id</th>
                                <th>参数名称</th>
                                <th>是否可为空</th>
                                <th>参数类型</th>
                                <th>请求或响应</th>
                                <th>映射参数名称</th>
                            </tr>
                    </thead>
                </table>
                <div style="float: right;">
                    <button type="button" class="btn btn-primary"><a href="javascript:history.go(-1)">取消申请</a></button>
                    <button type="button" class="btn btn-danger" id="id-applyInterfaceBtn2">确定申请</button>
                </div>
                </div>    
            </div>
        </div>
        
    <div>
        <%@ include file="./order-pubparamifame.jsp"%>
        <%@ include file="../security/securityifream.jsp"%>
        <%@ include file="../security/apitestiframe.jsp"%>
    </div>
        
    </div>
    
        
    <input type="hidden" value="${type }" id="type"/>
    <script src="<%=webpath %>/resources/js/puborder/order/order-interfaceapply.js"></script>
</body>
</html>





