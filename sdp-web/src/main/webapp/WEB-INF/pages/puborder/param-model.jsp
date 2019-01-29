<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		
	<div class="modal fade" id="paramadd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">参数设置</h4>
	      </div>
	      <div class="modal-body" id="parammodal-body">
	      <input  type="hidden" id="parmParentId" value="" /> 
	      <input type="hidden" id="paramadd-update">
				<form id="urlmodelAddForm" data-validator-option="{timely:2, theme:'yellow_right'}">
	       			<input type="hidden" name="pubid" id="param-pubid"/>
	       			<input type="hidden" name="paramid" id="paramid-set"/>
	       			<div id="allparamset">
					<div class="form-inline" id="paramset">
						<label class="control-label">参数:</label><input class="form-control param-input" style="width:80px" type="text" id="ecode"/> 
						<label class="control-label">说明:</label><input class="form-control param-input" style="width:80px" type="text" id="reqdesc"/>
						<label class="control-label">参数类型:</label>
						  <!-- <input class="form-control param-input" style="width:80px" type="text" id="reqtype"/> --> 
						    <select id="reqtype" class="" style="width:80px;height: 25px;"> 
                                <option value="String" selected = "selected">String</option>
                                <option value="int">int</option>
                                <option value="boolean">boolean</option>
                                <option value="Object">Object</option>
                            </select>
                        <label class="control-label maxlength">最大长度:</label>
                        <input class="form-control param-input maxlength" style="width:80px" type="text" id="maxlength"/>

						<label class="control-label">参数位置:</label>
						<!-- <input class="form-control param-input" style="width:80px" type="text" id="parampos"/>  -->
						 <select id="parampos" class="" style="width:80px;height: 25px;"> 
                                <option value="0" selected = "selected">请求体</option>
                                <option value="4" >请求头</option>
                                <option value="1">url上的参数</option>
                                <option value="2">响应头</option>
                                <option value="3">响应体</option>
                            </select>
						
						<label class="control-label">是否必传项:</label>
						<!-- <input class="form-control param-input" style="width:80px" type="text" id="isempty"/> --> 
						    <select id="isempty" class="" style="width:80px;height: 25px;"> 
                                <option value="0" selected = "selected">否</option>
                                <option value="1">是</option>
                            </select>
						<button type="button" class="btn btn-default" aria-label="Left Align" id="addparam">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						</button>
					</div>
					</div>
				</form>
				<div id="paramset"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-danger"  data-dismiss="modal" id="addParamSetBtn">保存</button>
	      </div>
	    </div>
	  </div>
	  </div>
      
	<input type="hidden" value="${type }" id="type"/>
 <script src="<%=webpath %>/resources/js/puborder/param-model.js"></script> 
