<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style>
    .apimodel-select{
        width:26%;
        height:26px;
    }
    .apimodel-input{
        margin-left: 3%;
     }
     .apimodel-area{
        margin-left: 3%;
        width: 88%;
        height: 45px;
        margin-top: 22px;
        overflow:auto;
     }
     .layui-layer-btn{
        padding-top: 0px;
     } 
</style>
    
    <!--flowChart-model-Modal -->
    <div id="flowChartModal" class="panel-body" style="display: none;   ">
            <input type="hidden" id="id-proModelinfoupdate">
            <div class="form-group">
                <label for="flowChartName">编排服务名称:</label>
                <input type="text" class=""  name="flowChartName" id="flowChartName" style="width:200px"/>
                <label for="pubprotocal">请求协议:</label>
                <select name="pubprotocal" id="pubprotocal" class="apimodel-select apimodel-input">
                    <option value="http">http</option>
                    <option value="webService">webService</option>
                </select>
            </div>
            
            <div class="form-group" style="display:none">
                <label for="pubmethod" style="margin-left: 7%;">请求方式:</label>
                <select name="method" id="pubmethod" class="apimodel-select apimodel-input">
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                    <option value="GET">GET</option>
                    <option value="POST">POST</option>
                    <option value="DELETE">DELETE</option>
                    <option value="PATCH">PATCH</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="pubreqformat">请求参数格式:</label>
                <select name="reqformat" id="pubreqformat" class="apimodel-select" style="width: 23%;">
                    <option selected="selected" disabled="disabled" style='display: none' value=''></option>
                    <option disabled="disabled">--------请选择--------</option>
                    <option value="application/json">application/json</option>
                    <option value="application/xml">application/xml</option>
                    <option value="其他">其他</option>
                </select>
                
                <label for="pubrespformat" style="margin-left: 7%;">响应参数格式:</label>
                <select name="respformat" id="pubrespformat" class="apimodel-select apimodel-input">
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                    <option disabled="disabled">------------请选择-------------</option>
                    <option value="application/json">application/json</option>
                    <option value="application/xml">application/xml</option>
                    <option value="其他">其他</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="pubreqdatatype">请求参数类型:</label>
                <select name="reqdatatype" id="pubreqdatatype" class="apimodel-select">
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                    <option value="Object">Object</option>
                    <!-- <option value="List">List</option> -->
                </select>
            
                <label for="pubreturndatatype" style="margin-left: 6%;">响应参数类型:</label>
                <select name="returndatatype" id="pubreturndatatype" class="apimodel-select"> 
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                    <option value="Object">Object</option>
                    <!-- <option value="List">List</option> -->
                </select>
            </div>
            
            <div class="form-group">
                <label for="pubdesc">描述:</label>
                <!-- <input type="text" class="form-control input-sm form-sm"  name="pubdesc" id="pubdesc"/> -->
                <textarea class="apimodel-area" name="pubdesc" id="pubdesc"></textarea>
            </div>
    </div>
    
    <!-- 规则设置modal -->
    <div id="layoutparamalert" style="margin-left: 40px;display: none;">
    <div style="color: white;font-family: cursive;font-size: 21px;margin-top: 3%;">正则表达式：</div>
        <input type="text" placeholder="请输入正则表达式" id="layoutregex"  style="margin-left: 15%; margin-top: 1%; width: 60%;height: 23%;"/><br>
    <div style="color: white;font-family: cursive;font-size: 21px;margin-top: 3%;">常量参数：</div>
        <input type="text" placeholder="请输入参数常量值" id="layoutconstant"  style="margin-left: 15%; margin-top: 1%; width: 60%;height: 23%;"/>
    </div>
    
    <!--参数添加  -->
   <div id="layoutparamset" style=" margin-top: 5%;margin-left: 10%;font-size: 15px;display: none;">
        <label class="control-label" style="margin-right: 8%;">参数:</label><input class="layout_paramadd" style="width:200px" type="text" id="ecode"/> <br>
        <label class="control-label" style="margin-right: 8%;margin-top: 3%;">说明:</label><input class="layout_paramadd" style="width:200px" type="text" id="reqdesc"/> <br>
        <label class="control-label" style="margin-right: 2%;margin-top: 3%;">参数类型:</label>
            <select id="reqtype" class="layout_paramadd" style="width:80px;height: 25px;width:200px"> 
                <option value="String" selected = "selected">String</option>
                <option value="int">int</option>
                <option value="boolean">boolean</option>
                <option value="Object">Object</option>
                <option value="List">List</option>
             </select><br>
        <label class="control-label" style="margin-right: 2%;margin-top: 3%;">参数位置:</label>
             <select id="parampos" class="layout_paramadd" style="width:80px;height: 25px;width:200px"> 
                 <option value="0" selected = "selected">请求体</option>
                 <option value="4" selected = "selected">请求头</option>
                 <option value="1">url上的参数</option>
                 <option value="2">响应头</option>
                 <option value="3">响应体</option>
                 <option value="5">xml请求体属性</option>
                 <option value="6">xml响应体属性</option>
             </select><br>
               
        <label class="control-label" style="margin-right: 2%;margin-top: 3%;">是否必传项:</label>
               <select id="isempty" class="layout_paramadd" style="width:80px;height: 25px;width:200px"> 
                       <option value="0" selected = "selected">否</option>
                       <option value="1">是</option>
                   </select><br>
         </div>

 <script src="<%=webpath %>/resources/js/serlayout/flowchartmodel.js"></script> 
