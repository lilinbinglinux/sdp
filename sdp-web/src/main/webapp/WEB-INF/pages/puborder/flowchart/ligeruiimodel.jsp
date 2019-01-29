<% /**
 ----------- 本页面描述右侧面板项目的详细信息（设置参数ifream） ----------------
 */%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css"
      type="text/css">

<link rel="stylesheet" type="text/css"
      href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
<link rel="stylesheet" type="text/css"
      href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">

<script type="text/javascript"
        src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/core/base.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/layer/layer.js"></script>

<script type="text/javascript"
        src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.exedit.js"></script>

<script src="<%=webpath %>/resources/js/flowchart/ligeruiframe.js"></script>

<style>
    .layoutparam-modal-content {
        width: 164%;
        margin-left: -30%;
        height: 80%;
    }

    .layer-demo {
        background-color: gray;
    }

    .layer-demo .layui-layer-btn1 {
        color: #000;
    }
    .input_control {
    margin:0 auto;
    width: 260px;
    padding: 6px 12px;
    margin-left: 10px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
}
.addvar{
    border-color: #4898d5;
    background-color: #2e8ded;
    color: #fff;
}
.deletevar{
    border-color: #4898d5;
    background-color: #2e8ded;
    color: #fff;}
.add_btn a{
	float:right;
	height: 28px;
    line-height: 28px;
    margin: 0 6px;
    padding: 0 15px;
    border: 1px solid #dedede;
    color: #ffffff;
    border-radius: 2px;
    font-weight: 400;
    cursor: pointer;
    text-decoration: none;
    }
.component1{
	margin-top:10px;
} 
</style>

<!--映射关系弹框 -->
<div id="layoutparam-divlayer" style="display: none">
    <div class="searchWrap">
        <form class="form-inline" id="layout-pubidtotype">
            <div class="form-group">
                <input type="hidden" id="pubid" name="pubid">
                <input type="hidden" id="type" name="type">
            </div>
        </form>
    </div>
    <div style="width: 200px;margin-left: 80%;margin-top: 19px;">
    </div>
    <div>
        <ul class="nav nav-tabs">
          <li role="presentation"  id="reqdiv"class="active"><a href="#">参数映射</a></li>
          <li role="presentation" id="respdiv" ><a href="#">条件设置</a></li>
        </ul>
    </div>
    <div id="layoutparamtable" style="margin-top:20px"></div>
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="treeDemo" class="ztree" style="margin-top:0; width:260px;background:#9ecfef;height:200px"></ul>
    </div>
    <div id="conditionset" style="display:none;margin-left: 200px;">
    	<div class="add_btn"><a class="addvar" onclick="addVar()" href="javascript:void(0);">增加变量</a></div>
    	<div class="add_btn"><a class="deletevar" onclick="deleteVar()" href="javascript:void(0);">删除变量</a></div>
    	<div style="display:block"class="component">
			 <div class="component1">
				<label>变量：</label>
                <input type="text" class="input_control" id="parameter1"
                 initidvalue=""   onclick="inputClick(this)"/>
            <br>
           	</div>
    	</div>
    	<div class="component1">
            <label>条件：</label>
            <textarea rows="5" cols="70" id="condition" class="input_control"></textarea>
    	</div>
    </div>
    
    
</div>
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
