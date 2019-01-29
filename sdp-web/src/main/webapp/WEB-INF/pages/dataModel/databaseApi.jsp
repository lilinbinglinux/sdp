<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>Title</title>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery.json-viewer/json-viewer/jquery.json-viewer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/accordion.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/myLayerSkin.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/api.css">
</head>
<body>
  <div class="api-container">
    <span class="newApiBtn">新增接口</span>
    <table id="api-list" lay-filter="test"></table>
  </div>
<script>
  var curContext = '<%=path%>';
</script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery.json-viewer/json-viewer/jquery.json-viewer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/uuid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/drawArrow.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/fomartJSON.js"></script>

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script>
  $(function (){

  	var moduleId = '${modelId}';

  	var newApiHtml = '<div class="api-item">\n' +
		  '      <span class="api-name">基本信息<span class="back-list">返回</span></span>\n' +
		  '      <div class="api-content">\n' +
		  '        <form class="layui-form" action="">\n' +
		  '          <div class="layui-row">\n' +
		  '            <div class="layui-col-md4">\n' +
		  '              <div class="layui-form-item">\n' +
		  '                <label class="layui-form-label">接口名称</label>\n' +
		  '                <div class="layui-input-block">\n' +
		  '                  <input id="dataApiName" type="text" autocomplete="off" class="layui-input">\n' +
		  '                </div>\n' +
		  '              </div>\n' +
		  '            </div>\n' +
		  '            <div class="layui-col-md4">\n' +
		  '              <div class="layui-form-item">\n' +
		  '                <label class="layui-form-label">数据源</label>\n' +
		  '                <div class="layui-input-block">\n' +
		  '                  <select name="dataResId" id="apiDataResId">\n' +
		  '\n' +
		  '                  </select>\n' +
		  '                </div>\n' +
		  '              </div>\n' +
		  '            </div>\n' +
		  '            <div class="layui-col-md4">\n' +
		  '              <div class="layui-form-item">\n' +
		  '                <label class="layui-form-label">序号</label>\n' +
		  '                <div class="layui-input-block">\n' +
		  '                  <input id="sortId" type="text" autocomplete="off" class="layui-input">\n' +
		  '                </div>\n' +
		  '              </div>\n' +
		  '            </div>\n' +
		  '          </div>\n' +
		  '        </form>\n' +
		  '      </div>\n' +
		  '    </div>\n' +
		  '    <div class="api-item">\n' +
		  '      <span class="api-name">输入参数格式<span class="show-example" id="showEnterExample">显示示例</span></span>\n' +
		  '      <div class="api-content">\n' +
		  '        <div class="layui-tab layui-tab-card">\n' +
		  '          <ul class="layui-tab-title">\n' +
		  '            <li class="layui-this">参数</li>\n' +
		  '            <li id="show-json">预览</li>\n' +
		  '          </ul>\n' +
		  '          <div class="layui-tab-content">\n' +
		  '            <div class="layui-tab-item layui-show">\n' +
		  '              <div class="layui-form-item layui-form-text">\n' +
		  '                <div class="layui-input-block" style="margin-left: 0;">\n' +
		  '                  <textarea id="inputAttr" name="desc" placeholder="请输入内容" class="layui-textarea" style="min-height: 200px;"></textarea>\n' +
		  '                </div>\n' +
		  '              </div>\n' +
		  '            </div>\n' +
		  '            <div class="layui-tab-item">\n' +
		  '              <pre id="result-json" style="padding: 15px;">\n' +
		  '\n' +
		  '              </pre>\n' +
		  '            </div>\n' +
		  '          </div>\n' +
		  '        </div>\n' +
		  '      </div>\n' +
		  '    </div>\n' +
		  '    <div class="api-item">\n' +
		  '      <span class="api-name">输出结果集</span>\n' +
		  '      <div class="api-content">\n' +
		  '        <form class="layui-form" action="">\n' +
		  '          <div class="layui-row">\n' +
		  '            <div class="layui-col-md4">\n' +
		  '              <div class="layui-form-item">\n' +
		  '                <label class="layui-form-label">结果集</label>\n' +
		  '                <div class="layui-input-block">\n' +
		  '                  <select lay-verify="required" id="outputSample">\n' +
		  '                    <option value="1">String</option>\n' +
		  '                    <option value="2">int</option>\n' +
		  '                    <option value="3">boolean</option>\n' +
		  '                    <option value="4">List</option>\n' +
		  '                    <option value="5">Object</option>\n' +
		  '                    <option value="6">List&lt;Object&gt;</option>\n' +
		  '                  </select>\n' +
		  '                </div>\n' +
		  '              </div>\n' +
		  '            </div>\n' +
		  '          </div>\n' +
		  '        </form>\n' +
		  '        <div class="layui-tab layui-tab-card">\n' +
		  '          <ul class="layui-tab-title">\n' +
		  '            <li class="layui-this">结果集案例</li>\n' +
		  '            <li id="show-result-json">预览</li>\n' +
		  '          </ul>\n' +
		  '          <div class="layui-tab-content">\n' +
		  '            <div class="layui-tab-item layui-show">\n' +
		  '              <div class="layui-form-item layui-form-text">\n' +
		  '                <div class="layui-input-block" style="margin-left: 0;">\n' +
		  '                  <textarea id="outputAttr" name="desc" placeholder="请输入内容" class="layui-textarea" style="min-height: 200px;"></textarea>\n' +
		  '                </div>\n' +
		  '              </div>\n' +
		  '            </div>\n' +
		  '            <div class="layui-tab-item">\n' +
		  '              <pre id="anli-json" style="padding: 15px;">\n' +
		  '\n' +
		  '              </pre>\n' +
		  '            </div>\n' +
		  '          </div>\n' +
		  '        </div>\n' +
		  '      </div>\n' +
		  '    </div>\n' +
		  '    <div class="api-item">\n' +
		  '      <span class="api-name">SQL语句<span class="show-example" style="left: 100px;" id="show-sql-example">显示示例</span></span>\n' +
		  '\n' +
		  '      <div class="api-content">\n' +
		  '        <form class="layui-form" action="">\n' +
		  '          <div class="layui-form-item layui-form-text">\n' +
		  '            <div class="layui-input-block" style="margin-left: 0;">\n' +
		  '              <textarea name="desc" placeholder="请输入SQL语句" class="layui-textarea" id="sqlText"></textarea>\n' +
		  '            </div>\n' +
		  '          </div>\n' +
		  '        </form>\n' +
		  '      </div>\n' +
		  '    </div>\n' +
		  '    <div>\n' +
		  '      <button class="layui-btn layui-btn-normal" id="api-subbtn">提交</button>\n' +
		  '      <button class="layui-btn layui-btn-primary">取消</button>\n' +
		  '    </div>';



  	//行编辑
    function apiEdit(d){

	    var apiEditHtml = '<div class="api-item">\n' +
		    '      <span class="api-name">基本信息<span class="back-list">返回</span></span>\n' +
		    '      <div class="api-content">\n' +
		    '        <form class="layui-form" action="">\n' +
		    '          <div class="layui-row">\n' +
		    '            <div class="layui-col-md4">\n' +
		    '              <div class="layui-form-item">\n' +
		    '                <label class="layui-form-label">接口名称</label>\n' +
		    '                <div class="layui-input-block">\n' +
		    '                  <input id="dataApiName" type="text" autocomplete="off" class="layui-input" value="'+ d.dataApiName +'">\n' +
		    '                  <input id="dataApiId" type="text" hidden="hidden" value="'+ d.dataApiId +'">\n' +
		    '                </div>\n' +
		    '              </div>\n' +
		    '            </div>\n' +
		    '            <div class="layui-col-md4">\n' +
		    '              <div class="layui-form-item">\n' +
		    '                <label class="layui-form-label">数据源</label>\n' +
		    '                <div class="layui-input-block">\n' +
		    '                  <select name="dataResId" id="apiDataResId">\n' +
		    '\n' +
		    '                  </select>\n' +
		    '                </div>\n' +
		    '              </div>\n' +
		    '            </div>\n' +
		    '            <div class="layui-col-md4">\n' +
		    '              <div class="layui-form-item">\n' +
		    '                <label class="layui-form-label">序号</label>\n' +
		    '                <div class="layui-input-block">\n' +
		    '                  <input id="sortId" type="text" autocomplete="off" class="layui-input" value="'+ ( d.sortId ? d.sortId : '' ) +'">\n' +
		    '                </div>\n' +
		    '              </div>\n' +
		    '            </div>\n' +
		    '          </div>\n' +
		    '        </form>\n' +
		    '      </div>\n' +
		    '    </div>\n' +
		    '    <div class="api-item">\n' +
		    '      <span class="api-name">输入参数格式<span class="show-example" id="showEnterExample">显示示例</span></span>\n' +
		    '      <div class="api-content">\n' +
		    '        <div class="layui-tab layui-tab-card">\n' +
		    '          <ul class="layui-tab-title">\n' +
		    '            <li class="layui-this">参数</li>\n' +
		    '            <li id="show-json">预览</li>\n' +
		    '          </ul>\n' +
		    '          <div class="layui-tab-content">\n' +
		    '            <div class="layui-tab-item layui-show">\n' +
		    '              <div class="layui-form-item layui-form-text">\n' +
		    '                <div class="layui-input-block" style="margin-left: 0;">\n' +
		    '                  <textarea id="inputAttr" name="desc" placeholder="请输入内容" class="layui-textarea" style="min-height: 200px;"></textarea>\n' +
		    '                </div>\n' +
		    '              </div>\n' +
		    '            </div>\n' +
		    '            <div class="layui-tab-item">\n' +
		    '              <pre id="result-json" style="padding: 15px;">\n' +
		    '\n' +
		    '              </pre>\n' +
		    '            </div>\n' +
		    '          </div>\n' +
		    '        </div>\n' +
		    '      </div>\n' +
		    '    </div>\n' +
		    '    <div class="api-item">\n' +
		    '      <span class="api-name">输出结果集</span>\n' +
		    '      <div class="api-content">\n' +
		    '        <form class="layui-form" action="">\n' +
		    '          <div class="layui-row">\n' +
		    '            <div class="layui-col-md4">\n' +
		    '              <div class="layui-form-item">\n' +
		    '                <label class="layui-form-label">结果集</label>\n' +
		    '                <div class="layui-input-block">\n' +
		    '                  <select lay-verify="required" id="outputSample">\n' +
		    '                    <option value="1" '+ ( d.outputSample == "1" ? 'selected' : '' ) +' >String</option>\n' +
		    '                    <option value="2" '+ ( d.outputSample == "2" ? 'selected' : '' ) +' >int</option>\n' +
		    '                    <option value="3" '+ ( d.outputSample == "3" ? 'selected' : '' ) +' >boolean</option>\n' +
		    '                    <option value="4" '+ ( d.outputSample == "4" ? 'selected' : '' ) +' >List</option>\n' +
		    '                    <option value="5" '+ ( d.outputSample == "5" ? 'selected' : '' ) +' >Object</option>\n' +
		    '                    <option value="6" '+ ( d.outputSample == "6" ? 'selected' : '' ) +' >List&lt;Object&gt;</option>\n' +
		    '                  </select>\n' +
		    '                </div>\n' +
		    '              </div>\n' +
		    '            </div>\n' +
		    '          </div>\n' +
		    '        </form>\n' +
		    '        <div class="layui-tab layui-tab-card">\n' +
		    '          <ul class="layui-tab-title">\n' +
		    '            <li class="layui-this">结果集案例</li>\n' +
		    '            <li id="show-result-json">预览</li>\n' +
		    '          </ul>\n' +
		    '          <div class="layui-tab-content">\n' +
		    '            <div class="layui-tab-item layui-show">\n' +
		    '              <div class="layui-form-item layui-form-text">\n' +
		    '                <div class="layui-input-block" style="margin-left: 0;">\n' +
		    '                  <textarea id="outputAttr" name="desc" placeholder="请输入内容" class="layui-textarea" style="min-height: 200px;"></textarea>\n' +
		    '                </div>\n' +
		    '              </div>\n' +
		    '            </div>\n' +
		    '            <div class="layui-tab-item">\n' +
		    '              <pre id="anli-json" style="padding: 15px;">\n' +
		    '\n' +
		    '              </pre>\n' +
		    '            </div>\n' +
		    '          </div>\n' +
		    '        </div>\n' +
		    '      </div>\n' +
		    '    </div>\n' +
		    '    <div class="api-item">\n' +
		    '      <span class="api-name">SQL语句<span class="show-example" style="left: 100px;" id="show-sql-example">显示示例</span></span>\n' +
		    '\n' +
		    '      <div class="api-content">\n' +
		    '        <form class="layui-form" action="">\n' +
		    '          <div class="layui-form-item layui-form-text">\n' +
		    '            <div class="layui-input-block" style="margin-left: 0;">\n' +
		    '              <textarea name="desc" placeholder="请输入SQL语句" class="layui-textarea" id="sqlText">'+ d.sqlText +'</textarea>\n' +
		    '            </div>\n' +
		    '          </div>\n' +
		    '        </form>\n' +
		    '      </div>\n' +
		    '    </div>\n' +
		    '    <div>\n' +
		    '      <button class="layui-btn layui-btn-normal" id="api-subbtn">提交</button>\n' +
		    '      <button class="layui-btn layui-btn-primary">取消</button>\n' +
		    '    </div>';

	    $(".api-container").empty();
	    $(".api-container").append(apiEditHtml);

	    $("#inputAttr").text( d.inputAttr );
	    $("#outputAttr").text( d.outputAttr );

	    loadDataResList(d);
	    bindPreviewEvent();
	    apiSubmit('edit');
    }

  	//加载apis表格
  	function loadTable() {
		  $.ajax({
			  url: "<%=path%>/v1/sqlQuery/getProDataApi",
			  data: { "modelId": moduleId },
			  type: "POST",
			  success: function(result) {
				  layui.use('table', function(){
					  var table = layui.table;

					  //第一个实例
					  table.render({
						  elem: '#api-list'
						  ,data: result.data
						  ,page: true //开启分页
						  ,cols: [[ //表头
							  {field: 'sortId', title: '序号', width: '10%', sort: true}
							  ,{field: 'dataApiName', title: '接口名称'}
							  ,{fixed: 'right', width:150, align:'center', toolbar: '#barDemo'}
						  ]]
					  });

					  table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
						  var data = obj.data; //获得当前行数据
						  var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
						  var tr = obj.tr; //获得当前行 tr 的DOM对象

						  if(layEvent === 'del'){ //删除
							  layer.confirm('确认删除该接口么？', function(index){
								  obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
								  layer.close(index);
								  //向服务端发送删除指令
                  $.ajax({
                    url: '<%=path%>/v1/sqlQuery/deleteProDataApi',
                    data: { "dataApiId" : data.dataApiId },
                    type: 'POST',
                    success: function (result) {
                      if( result.code == 200 ) {
	                      layer.msg(result.message, {icon: 6});
                      }else {
	                      layer.msg(result.message, {icon: 1});
                      }
                    }
                  });
							  });
						  } else if(layEvent === 'edit'){ //编辑
							  //do something
                apiEdit(data);
						  }
					  });
				  });
			  }
		  });
	  }

	  loadTable();
	  newApi();

	  //填满数据源select
  	function loadDataResList(curData) {
		  $.ajax({
			  url: '<%=path%>/v1/sqlModel/show/dataResList',
			  success:function ( result ){
				  if( result.data ) {
					  var data = result.data;
					  for( var i = 0; i < data.length; i++ ){
					  	if( curData && data[i].dataResId == curData.dataResId ){
							  $("#apiDataResId").append(
								  '<option value="'+ data[i].dataResId +'" selected>'+ data[i].dataResTypeName +'</option>'
							  );
              } else {
							  $("#apiDataResId").append(
								  '<option value="'+ data[i].dataResId +'" >'+ data[i].dataResTypeName +'</option>'
							  );
              }
					  }
					  layui.form.render('select');
				  }
			  }
		  });
	  }

	  //显示案例Editor
    function showResultEditor() {
	    var E = window.wangEditor;
	    var editor = new E('#result-editor');
	    editor.customConfig.menus = [
		    'undo',  // 撤销
	    ];
	    editor.customConfig.zIndex = 100;
	    editor.customConfig.onchange = function (html) {
		    // html 即变化之后的内容
		    var str = html.replace(/<\/?[^>]*>/g,''); //去除HTML tag
		    str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
		    str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
		    str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
//		console.log(eval('('+ str +')'));
		    $("#anliVal").val(str);
	    };
	    editor.create();
    }

    //新增接口按钮事件
    function newApi() {
	    $(".newApiBtn").click(function (e) {
		    var e = e || window.event;
		    $(".api-container").empty();
		    $(".api-container").append(newApiHtml);

		    loadDataResList();
		    bindPreviewEvent();
		    apiSubmit();
	    });
    }

	  //预览按钮事件
	  function bindPreviewEvent() {
		  $("#show-json").click(function () {
			  var str = $("#inputAttr").val();
			  var json = null;
			  try {
				  json = eval('('+ str +')');
				  $('#result-json').jsonViewer(json);
			  } catch (e) {
				  $('#result-json').text(e.name + ":  " + e.message);
			  }
		  });

		  $("#show-result-json").click(function () {
			  var str = $("#outputSample").val();
			  var json = null;
			  try {
				  json = eval('('+ str +')');
				  $('#anli-json').jsonViewer(json);
			  } catch (e) {
				  $('#anli-json').text(e.name + ":  " + e.message);
			  }
		  });

		  $("#showEnterExample").click(function (e) {
        var html = '<pre id="json-renderer" style="padding: 15px;">\n' +
	        '\n' +
	        '              </pre>';
			  var layerIndex = layer.open({
				  type: 1,
				  shade: 0,
				  maxmin: true,
				  offset: 't',
				  area: "500px",
				  moveOut : true,
				  skin: 'myLayerOpnSkin',
				  title: '输入参数示例',
				  content: html
			  });
			  var data = {
				  "fieldMap": {
					  "createBy": "admin",
					  "fieldLen": "10"
				  },
				  "dataApiId": "1231564164165146500000",
				  "tenantId": "tenant_system",
				  "tableList": [{
					  "id": "2c2720fe745b11e88b091402ec8b0a88"
				  }, {
					  "id": "04d3824775ca11e88b091402ec8b0a88"
				  }, {
					  "id": "2047772d75ca11e88b091402ec8b0a88"
				  }]
			  };
			  $('#json-renderer').jsonViewer(data);
		  });

		  $("#show-sql-example").click(function (e) {

        var html = '<div id="sqlEditor">' +
          '<p>SELECT<br>*<br>FROM<br>sql_field<br>WHERE<br>create_by =' + '$' + '{fieldMap.createBy}' + '<br>AND table_id IN (<br>&lt; #list tableList as tableId&gt;<br>'+ '$'+'{tableId.id}'+' &lt; #if tableId_has_next&gt;,&lt;/#if&gt;<br>&lt;/ #list&gt;<br>)<br>AND tenant_id = '+'$'+'{tenantId}'+' &lt; #if fieldMap.fieldLen??&gt; AND field_len='+ '$'+ '{fieldMap.fieldLen}'+'&lt;/#if&gt;;' +
	        '</p>' +
          '</div>';
			  var layerIndex = layer.open({
				  type: 1,
				  shade: 0,
				  maxmin: true,
				  offset: 't',
				  area: ["500px","400px"],
				  moveOut : true,
				  skin: 'myLayerOpnSkin',
				  title: '输入参数示例',
				  content: html
			  });

			  var E = window.wangEditor;
			  var editor = new E('#sqlEditor');
			  editor.customConfig.menus = [
				  'undo',  // 撤销
			  ];
			  editor.customConfig.zIndex = 100;
			  editor.create();
		  });

		  $(".back-list").click(function (e) {
        var html = '<span class="newApiBtn">新增接口</span>\n' +
	        '    <table id="api-list" lay-filter="test"></table>';
			  $(".api-container").empty();
			  $(".api-container").append(html);

			  loadTable();
			  newApi();
		  });
	  }

	  //api保存
	  function apiSubmit( saveType ) {
		  $("#api-subbtn").click(function (e) {
			  var modelId = '${modelId}';
			  var saveData = null;
			  if( saveType == "edit" ){
				  saveData = {
					  "dataApiName": $("#dataApiName").val()
            , "dataResId": $("#apiDataResId").val()
            , "inputAttr": $("#inputAttr").val()
            , "outputAttr": $("#outputAttr").val()
            , "sqlText": $("#sqlText").val()
            , "modelId": modelId
            , "outputSample": $("#outputSample").val()
            , "sortId": $("#sortId").val()
            , "dataApiId" : $("#dataApiId").val()
          }
        } else {
				  saveData = {
					  "dataApiName": $("#dataApiName").val()
					  , "dataResId": $("#apiDataResId").val()
					  , "inputAttr": $("#inputAttr").val()
					  , "outputAttr": $("#outputAttr").val()
					  , "sqlText": $("#sqlText").val()
					  , "modelId": modelId
					  , "outputSample": $("#outputSample").val()
					  , "sortId": $("#sortId").val()
				  }
        }
			  $.ajax({
				  url: '<%=path%>/v1/sqlQuery/saveProDataApi',
				  data: saveData,
				  type: 'POST',
				  success: function (result) {
					  if( result.dataApiId ) {
						  var html = '<span class="newApiBtn">新增接口</span>\n' +
							  '    <table id="api-list" lay-filter="test"></table>';
						  $(".api-container").empty();
						  $(".api-container").append(html);

						  loadTable();
						  newApi();
            }
				  }
			  })
		  });
	  }
  });
</script>
</body>
</html>