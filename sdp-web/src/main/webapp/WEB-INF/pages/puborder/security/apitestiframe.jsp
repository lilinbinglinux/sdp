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

</style>
<script src="<%=webpath %>/resources/js/puborder/security/apitestiframe.js"></script>
        <div id="api-test" style="display:none;margin-top: 25px;">
            <div style="font-size: large;font-weight: 600;">示例测试数据
                <textarea id="test-sampledata" class="form-control" style="margin-top: 13px;width: 640px;height: 100px;overflow:auto;">
                </textarea>
            </div>
            
	        <div class="progress progress-striped" style="display:none" id="processdiv">
	            <div class="progress-bar progress-bar-primary" id="processbar" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:0%">
	                <span id="aa">80% 完成</span>
	            </div>
	        </div>
	        
	        <div id="responsediv" style="font-size: large;font-weight: 600;display:none">
	           <textarea id="response-data" class="form-control" style="margin-top: 13px;width: 640px;height: 100px;overflow:auto;background:white" readonly>
               </textarea>
	        </div>
	        
	        <div class="divbtn">
	           <button type="button" class="btn btn-danger"  id="step3-pre-step" style="margin-left: -43px;">上一步</button>
	           <button type="button" class="btn btn-primary" id="step3-add-progress">在线测试</button>
               <button type="button" class="btn btn-danger"  id="step3-next-step">下一步</button>
            </div>
        </div>
    
