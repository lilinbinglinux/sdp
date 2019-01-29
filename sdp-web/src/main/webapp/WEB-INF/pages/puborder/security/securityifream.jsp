<% /**
    ----------- 本页面描述TOKEN申请 ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script src="<%=webpath %>/resources/js/puborder/security/securityifream.js"></script>
<!-- 接口订阅 -->
        <div id="security-set" style="display:none">
            <div>
	            <button type="button" class="btn btn-primary"
	                    id="getTokenidBtn" style="margin-top:25px">点击生成令牌</button>
            </div>
            <div>
                <textarea id="key_key" class="form-control" style="margin-top:50px;width:100%;background-color: white;" 
                readonly></textarea>
            </div>
            <div class="divbtn">
                <button type="button" class="btn btn-danger"  id="token-previous-step">上一步</button>
                <button type="button" class="btn btn-primary"  id="addOrderInterface">申请</button>
            </div>
        </div>
    
