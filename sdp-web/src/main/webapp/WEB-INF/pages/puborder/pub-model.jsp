<% /**
    ----------- 本页面描述服务注册树弹框model ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=webpath %>/resources/js/puborder/pub-model.js"></script>
<style>
    .apimodel-select{
        width:168px;
        height:26px;
    }
    .apimodel-input{
        margin-left: 11px
     }
     .apimodel-area{
	    margin-left: 39px;
	    width: 460px;
	    height: 45px;
	    margin-top: 22px;
	    overflow:auto;
     }
</style>
    <input type="hidden" id="modaltype">
    <!--pro-Modal -->
	<div class="modal fade modalpanl" id="proModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <input type="hidden" id="id-proinfoupdate">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">项目新建</h4>
	      </div>
	      <div class="modal-body">
	        <div class="form-group">
	        	<label for="urlEnv">项目名称:</label>
	    		<input type="text" class="form-control input-sm form-sm"  name="name" id="proname"/>
	        </div>
	        <div class="form-group">
	        	<label for="urlEnv">编码:</label>
	    		<input type="text" class="form-control input-sm form-sm"  name="proecode" id="proecode"/>
	        </div>
	        <div class="form-group">
	        	<label for="urlEnv">版本:</label>
	    		<input type="text" class="form-control input-sm form-sm"  name="proversion" id="proversion"/>
	        </div>
	        <div class="form-group">
	        	<label for="urlEnv">项目说明:</label>
	        	<textarea row='3' name="prodescribe" id="prodescribe"></textarea>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-danger"  data-dismiss="modal" id="addProBtn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!--pro-model-Modal -->
	<div class="modal fade modalpanl" id="proModelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<input type="hidden" id="id-proModelinfoupdate">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">模块新建</h4>
	      </div>
	      <div class="modal-body">
	        <div class="form-group">
	        	<label for="urlEnv">模块名称:</label>
	    		<input type="text" class="form-control input-sm form-sm"  name="name" id="promname"/>
	        </div>
	        <div class="form-group">
	        	<label for="urlEnv">编码:</label>
	    		<input type="text" class="form-control input-sm form-sm"  name="proecode" id="promecode"/>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-danger"  data-dismiss="modal" id="addProModelBtn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>

