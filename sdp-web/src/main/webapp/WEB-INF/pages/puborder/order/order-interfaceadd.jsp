<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 全部信息列表 -->
	<div id="add-interfaceParamOrderModel" style="display: none;">
		<!-- 标题栏 -->
		<div>
			<ul class="nav nav-tabs">
              <li role="presentation" class="active" id="order-interfaceTitleBtn"><a href="#">接口订阅</a></li>
              <li role="presentation" id="order-paramTitleBtn"><a href="#">参数设置</a></li>
              <li role="presentation" id="security-apiTestBtn"><a href="#">API测试</a></li>
              <li role="presentation" id="security-tokenBtn"><a href="#">密钥生成</a></li>
            </ul>
		</div>
        <br>
        
        <div id="order-div">
		<!-- 接口订阅 -->
		<div id="add-orderInterModal">
			<!-- 接口订阅详细设置 -->
			<div id="add-interfaceOrderModel" style="height: auto;">
				<input type="hidden" id="id-pubapiId" />
				<input type="hidden" id="id-appId" />
				<div class="">
					<div class="form-group">
						<label for="name">订阅接口名称:</label> <input type="text"
							class="form-control input-sm form-sm" name="name" id="name" />
					</div>
					<div class="form-group">
						<label for="ordercode">编码:</label> <input type="text"
							class="form-control input-sm form-sm" name="ordercode"
							id="ordercode" />
					</div>
					<div class="form-group">
						<label for="protocal">网络协议:</label> <input type="text"
							class="form-control input-sm form-sm" name="protocal"
							id="protocal" />
					</div>
					<div class="form-group">
						<label for="url">请求地址:</label> <input type="text"
							class="form-control input-sm form-sm" name="url" id="url" />
					</div>
					<div class="form-group">
                        <label for="reqformat">请求返回格式:</label>
                        <select  class="form-control input-sm" style="width: 100px;" name="reqformat" id="reqformat">
                          <option value ="json" selected="selected">json</option>
                          <option value ="xml">xml</option>
                          <option value="其他">其他</option>
                        </select>
                    </div>
					<div class="form-group">
						<label for="orderdesc">说明:</label>
						<textarea row='3' name="orderdesc" id="orderdesc"></textarea>
					</div>
					<div class="form-group">
					<label for="setparamselect">设置参数:</label>
                       <select class="form-control input-sm" name="setparamselect" id="setparamselect">
                        <option value="0">不需要设置参数</option>
                        <option value="1">输入参数</option>
                        <option value="2">输入参数（xml格式）</option>
	                   </select>
                    </div>
				</div>
			 </div>
            <div>
               <hr color="#e5e5e5">
            </div>

			<!-- 接口订阅按钮 -->
			<div>
				<button type="button" class="btn btn-primary" id="order-backstep1">取消</button>
				<button type="button" class="btn btn-danger"
					id="add-orderInterfaceBtn">下一步</button>
			</div>
		</div>
		
		<!-- 正常参数设置 -->
		<div id="add-orderParamModal" style="display: none;">
			<!-- 参数详细设置 -->
			<div>
                <form id="addSetParamForm" data-validator-option="{timely:2, theme:'yellow_right'}">
                  <label for="paramname">参数名称:</label>
                  <input type="text" class="form-control input-sm form-sm" style='width:100px' name="paramname" id="paramname"/>
              
                  <label for="paramdesc">说明:</label>
                  <input type="text" class="form-control input-sm form-sm" style='width:100px' name="paramdesc" id="paramdesc"/>
              
                  <label for="paramtype">参数类型:</label>
                  <input type="text" class="form-control input-sm form-sm" style='width:100px' name="paramtype" id="paramtype"/>
              
                  <label for="isempty">是否为空:</label>
                  <select class="form-control input-sm" name="isempty" id="isempty">
                    <option value="0">可以为空</option>
                    <option value="1">不可以为空</option>
                  </select>
              
                  <label for="reqorresp">请求或响应:</label>
                  <select class="form-control input-sm" name="reqorresp" id="reqorresp">
                    <option value="0">请求参数</option>
                    <option value="1">响应参数</option>
                  </select>
                  
                  <button type="button" class="btn btn-default" aria-label="Left Align" id="addParamBtn">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                  </button>
              </form>
              <div id="orderparamset"></div>
			</div>
			
            <div>
                <hr color="#e5e5e5">
            </div>
			<!-- 参数确定按钮 -->
			<div>
                <button type="button" class="btn btn-primary" id="order-backstep2">取消</button>
                <button type="button" class="btn btn-danger" id="add-orderParamBtn">完成</button>
			</div>
		</div>
		
		<!-- xml参数格式设置 -->
		<div>
            <div id="id-xmlparam" style="display:none; width: 100%;">
                <form>
                    <textarea class="form-control pubidtoreq-textarea1" rows="15" id="id-xmlcontext" style="width:300px"></textarea>
                </form>
                <div style="float:right">
                    <textarea class="form-control pubidtoreq-textarea2" rows="15" id="example-xmlcontext" 
                    style="width:300px; margin-top: -314px; margin-right: 100px;" readonly></textarea>
                </div>
                <br>
                <hr />
                <div>
	                <button type="button" class="btn btn-primary" id="order-backstep3">取消</button>
	                <button type="button" class="btn btn-danger" id="id-addParamXmlBtn">完成</button>
                </div>
            </div>
		</div>
		</div>
		
		<div>
			<%@ include file="../security/securityifream.jsp"%>
			<%@ include file="../security/apitestiframe.jsp"%>
		</div>
		
		
	</div>
</body>
</html>