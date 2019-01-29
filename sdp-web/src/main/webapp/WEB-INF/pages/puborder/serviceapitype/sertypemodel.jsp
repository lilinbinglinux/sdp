<% /**
    ----------- 本页面描述服务注册树弹框model ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=webpath %>/resources/js/puborder/serviceapitype/sertypemodel.js"></script>
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
    <input type="hidden" id="apiAndSer">
    <!--pro-Modal -->
	<div class="modal fade modalpanl" id="proModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <input type="hidden" id="id-proinfoupdate">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">新建类型</h4>
	      </div>
	      <div class="modal-body">
	        <div class="form-group">
	        	<label>类型名称：</label>
	    		<input type="text" class="form-control input-sm form-sm"  name="name" id="typeName"/>
	        </div>
	        <%--<div class="form-group" id="shareFlag">
	        	<label>是否共享：</label>
				<input type="radio" name="shareFlag" value="1" checked> <span>共享</span>
				<input type="radio" name="shareFlag" value="0"> <span>不共享</span>
	        </div>--%>
	        <div class="form-group" id="stopFlag">
	        	<label >是否停用：</label>
				<input type="radio" name="stopFlag" value="0" checked> <span>使用</span>
				<input type="radio" name="stopFlag" value="1"> <span>停用</span>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-danger"  data-dismiss="modal" id="addProBtn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>