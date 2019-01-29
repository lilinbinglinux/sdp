<% /**
    ----------- 本页面描述API在线测试 ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
    .divbtn{
        margin-left: 460px;
        margin-top: 16px;
    }
    
    .progress{
       margin-top: 40px;
    }
    
    .panel-body {
            border: none;
            height:700px;
    }
    .datagrid{
        height:300px;
        margin-top: 20px;
    }
    .datagrid-view{
        margin-top: 10px;
    }

</style>
<script src="<%=webpath %>/resources/js/puborder/order/order-pubparamifame.js"></script>
        <div id="api-pubparam" style="display:none;margin-top: 25px;">
	        <div>
	          <input type="hidden" value="0" id="flag-type" name="reqtype">
	          <div class="btn-group" role="group">
			    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                 参数列表
			      <span class="caret"></span>
			    </button>
			    <ul class="dropdown-menu">
			      <li id="flag-request"><a href="#">请求参数结构对象</a></li>
			      <li id="flag-response"><a href="#">返回参数结构对象</a></li>
			    </ul>
			  </div>
			</div>              
	        <pre class="pre-scrollable" id="reqparam-code" style="margin-top: 10px;max-height :310px;overflow-x: auto;">
	        </pre>
	        <div class="divbtn">
	           <button type="button" class="btn btn-primary"  id="step2-pre-step">上一步</button>
               <button type="button" class="btn btn-danger"  id="step2-next-step">下一步</button>
            </div>
        </div>
    
