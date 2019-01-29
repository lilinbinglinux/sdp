<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--order-Modal -->
    <div class="modal fade modalpanl" id="add-orderInterfaceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-keyboard="true">
      <div class="modal-dialog" role="document">

		<div class="modal-content" id="add-orderInterfaceParam">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<span>订阅接口</span> | <span>参数设置</span>
				</h4>
			</div>
			
			<div id="add-orderInterModal" style="display: none;">
				<input type="hidden" id="id-pubapiId" />
				<div class="modal-body">
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
						<label for="format">请求返回格式:</label> <input type="text"
							class="form-control input-sm form-sm" name="format" id="format" />
					</div>
					<div class="form-group">
						<label for="orderdesc">说明:</label>
						<textarea row='3' name="orderdesc" id="orderdesc"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						id="add-orderInterfaceBtn">下一步</button>
				</div>
			</div>
			
			<div id="add-orderParamModal" style="display: none;">
                <div class="modal-body">
                <form id="addSetParamForm" data-validator-option="{timely:2, theme:'yellow_right'}">
                  <label for="name">参数名称:</label>
                  <input type="text" class="form-control input-sm form-sm" style='width:100px' name="name" id="name"/>
              
                  <label for="paramdesc">说明:</label>
                  <input type="text" class="form-control input-sm form-sm" style='width:100px' name="paramdesc" id="paramdesc"/>
              
                  <label for="paramtype">参数类型:</label>
                  <input type="text" class="form-control input-sm form-sm" style='width:100px' name="paramtype" id="paramtype"/>
              
                  <label for="isempty">是否为空:</label>
                  <select class="form-control input-sm" name="isempty" id="isempty">
                    <option value="0">可以为空</option>
                    <option value="1">不可以为空</option>
                  </select>
              
                  <label for="type">参数样式:</label>
                  <select class="form-control input-sm" name="type" id="type">
                    <option value="0">请求参数</option>
                    <option value="1">响应参数</option>
                  </select>
                  
                  <button type="button" class="btn btn-default" aria-label="Left Align" id="addParamBtn">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                  </button>
              </form>
              <div id="orderparamset"></div>
          </div>
          
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger"  data-dismiss="modal" id="add-orderParamBtn">完成</button>
          </div>					     
		  </div>
			
		</div>
	</div>
    </div>
    
