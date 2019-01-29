<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="UTF-8">
  <title>项目管理</title>

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
  <style>
    .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
  </style>
  <style>
    .ztree li span.button.project_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/module.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.project_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/module.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.project_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/module.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.table_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/databaseTable.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.module_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/type.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.module_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/type.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.module_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/type.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.api_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/api.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.api_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/api.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.api_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/api.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.service_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/service.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.service_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/service.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.service_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/service.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.page_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/page.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.page_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/page.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.page_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/dataModel/page.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
    .ztree li span.button.root_open::before {
      content: "\f078";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }
    .ztree li span.button.root_close::before {
      content: "\f054";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }
    .ztree li span.button.root_open, .ztree li span.button.root_close, .ztree li span.button.roots_open, .ztree li span.button.roots_close,
    .ztree li span.button.center_open, .ztree li span.button.center_close, .ztree li span.button.bottom_open, .ztree li span.button.bottom_close{
      background-image: none;
    }

    .ztree li span.button.roots_open::before {
      content: "\f078";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }
    .ztree li span.button.roots_close::before {
      content: "\f054";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }

    .ztree li span.button.center_open::before {
      content: "\f078";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }
    .ztree li span.button.center_close::before {
      content: "\f054";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }

    .ztree li span.button.bottom_open::before {
      content: "\f078";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }
    .ztree li span.button.bottom_close::before {
      content: "\f054";
      padding-top: 10px;
      padding-left: 2px;
      display: inline-block;
    }
    .ztree li span.button::before{
      color: #333;
      font-family: FontAwesome;
      padding-top: 10px;
    }

  </style>
</head>
<body>
<div class="container-power">
  <div class="power-menu-list">
        <span class="menu-left">
            <span class="fa fa-list-ul menu-item menu-show" aria-hidden="true"></span>
        </span>
        <span class="menu-right">
            <span class="crow-line"></span>
            <span class="fa fa-table power-tool-item tool-class-box" aria-hidden="true"></span>
            <span class="fa fa-hand-pointer-o power-tool-item tool-hand" aria-hidden="true"></span>
            <span class="fa fa-mouse-pointer power-tool-item tool-mouse" aria-hidden="true"></span>
            <span class="fa fa-long-arrow-right power-tool-item tool-line tool-straight-line" aria-hidden="true"></span>
            <span class="fa fa-times power-tool-item tool-delete" aria-hidden="true"></span>
            <span class="crow-line"></span>
        </span>
    <span class="power-tableType-name"></span>
    <input type="text" hidden="hidden" id="dataResId" />
    <input type="text" hidden="hidden" id="tableTypeId" />
  </div>
  <div class="power-main">
    <div class="power-left-nav">
      <div class="stepTwo-module">
        <div style="height: 30px;line-height: 30px;border-bottom: 1px solid #ddd;box-sizing: border-box;"><a href="#" id="newProject" class="clearButton bigButton"><span>添加项目</span></a></div>
        <ul id="curTree" class="ztree">

        </ul>
      </div>
      <ul class="rMenu">
        <li>新增</li>
        <li>编辑</li>
        <li>删除</li>
      </ul>
    </div>
    <div class="power-container" id="power-container" style="box-sizing: border-box;border-left: 1px solid #ddd;">
      <iframe id="IUFrame" class="IUFrame"></iframe>
    </div>
  </div>
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
<script src="${pageContext.request.contextPath}/resources/plugin/wangEditor-3.1.1/release/wangEditor.js"></script>
<script>
  $(function () {

	  function loadModuleTree( data ) {

		  var treeArray = [];
		  for( var i = 0; i < data.length; i++ ) {
			  var iconSkin = '';
			  if( data[i].modelFlag == "0" ) {
				  iconSkin = "project"
			  }else if(data[i].modelFlag == "1") {
				  iconSkin = "module"
			  } else if( data[i].modelFlag == "2" || data[i].modelFlag == "22" ) {
				  iconSkin = "api"
			  } else if( data[i].modelFlag == "3" || data[i].modelFlag == "33" ) {
				  iconSkin = "service"
			  } else if( data[i].modelFlag == "4" || data[i].modelFlag == "44" ) {
				  iconSkin = "page"
			  }
			  treeArray.push({
				  "id" : data[i].modelId,
				  "name" : data[i].modelName,
				  "pId": data[i].parentId ,
				  "sortId" : data[i].sortId,
          "resume" : data[i].resume,
				  "modelFlag" : data[i].modelFlag,
				  "iconSkin" : iconSkin,
				  open:true
			  })
		  };

		  var setting = {
			  data: {
				  simpleData: {
					  enable: true,
					  rootPId: ""
				  }
			  },
			  callback: {
				  onRightClick: OnRightClick,
				  onClick: zTreeOnClick,
			  }
		  };

		  function OnRightClick(event, treeId, treeNode) {
			  var rMenu = $(".rMenu");
			  rMenu.empty();
			  rMenu.css("display","none");
			  var x = event.clientX - $(".power-left-nav")[0].offsetLeft;
			  var y = event.clientY - $(".power-main")[0].offsetTop;
			  var moduleId = treeNode.id;
			  if( treeNode.modelFlag == "0" ) {
				  //项目
				  var html =
					  '<li class="rMenu-item rMenu-new">新增模块</li>\n' +
					  '<li class="rMenu-item rMenu-edit">编辑</li>\n' +
					  '<li class="rMenu-item rMenu-delete">删除</li>';
				  rMenu.append(html);
				  rMenu.css("display","block");
				  rMenu.css("top", y + "px");
				  rMenu.css("left", x + "px");
				  $(".rMenu-new").click(function (e) {
					  var formId = getuuid(16,16);
					  var html ='<div style="padding: 0 15px 0 0;">'+
						  '   <form class="layui-form" action="" id="'+formId+'">' +
						  '       <div class="layui-form-item">' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="modelName" class="layui-input" autocomplete="off" >\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="sortId" class="layui-input" autocomplete="off">\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">说明</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="resume" class="layui-input" autocomplete="off">\n' +
						  '             <input type="text" name="modelFlag" value="1" hidden="hidden"/>' +
						  '             <input type="text" name="parentId" value="'+ moduleId +'" hidden="hidden"/>' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
						  '       </div>' +
						  '   </form>' +
						  '</div>' ;
					  var index = layer.open({
						  type: 1,
						  shade: 0,
						  area: ["300px","250px"],
						  skin: 'myLayerOpnSkin',
						  title: '新增模块',
						  content: html
					  });

					  $("#" + formId ).find(".layui-btn-normal").click(function (e) {
						  e.preventDefault();
						  var data = $("#" + formId).serialize();
						  layer.close(index);
						  $.ajax({
							  url: curContext + '/v1/sqlQuery/saveProModel',
							  type: 'post',
							  data: data,
							  success: function (result) {
								  if( result.modelId ){
									  var zTree = $.fn.zTree.getZTreeObj("curTree");
									  zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '1', iconSkin : 'module' });
								  }
							  }
						  });
					  });
					  $("#" + formId ).find(".layui-btn-primary").click(function(e) {
						  e.preventDefault();
						  layer.close(index);
					  });
				  });
				  $(".rMenu-edit").click(function (e) {
					  var formId = getuuid(16,16);
					  var html ='<div style="padding: 0 15px 0 0;">'+
						  '   <form class="layui-form" action="" id="'+formId+'">' +
						  '       <div class="layui-form-item">' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="modelName" class="layui-input" autocomplete="off" value="'+ treeNode.name +'">\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="sortId" class="layui-input" autocomplete="off" value="'+ treeNode.sortId +'">\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">说明</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="resume" class="layui-input" autocomplete="off" value="'+ treeNode.resume +'">\n' +
						  '             <input type="text" name="modelFlag" value="0" hidden="hidden"/>' +
						  '             <input type="text" name="parentId" value="'+ treeNode.pId +'" hidden="hidden"/>' +
						  '             <input type="text" name="modelId" value="'+ treeNode.id +'" hidden="hidden"/>' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
						  '       </div>' +
						  '   </form>' +
						  '</div>' ;
					  var index = layer.open({
						  type: 1,
						  shade: 0,
						  area: ["300px","250px"],
						  skin: 'myLayerOpnSkin',
						  title: '编辑',
						  content: html
					  });

					  $("#" + formId ).find(".layui-btn-normal").click(function (e) {
						  e.preventDefault();
						  var data = $("#" + formId).serialize();
						  layer.close(index);
						  $.ajax({
							  url: curContext + '/v1/sqlQuery/saveProModel',
							  data: data,
                type: "POST",
							  success: function (result) {
								  if( result.modelId ){
									  var zTree = $.fn.zTree.getZTreeObj("curTree");
									  node = zTree.getNodeByParam("id", result.modelId);
									  node.name = result.modelName;
									  zTree.updateNode(node);
								  }
							  }
						  });
					  });
					  $("#" + formId ).find(".layui-btn-primary").click(function(e) {
						  e.preventDefault();
						  layer.close(index);
					  });
				  });
				  $(".rMenu-delete").click(function(e) {
					  if( treeNode.isParent ){
						  layer.msg('父节点不能直接删除', {icon: 2});
						  return false;
					  }
					  layer.confirm('确认删除'+ treeNode.name +'节点吗？',
						  {
							  icon: 3,
							  shade: 0,
							  skin: 'myLayerOpnSkin'
						  }, function(index){
							  $.ajax({
								  url: curContext + '/v1/sqlQuery/deleteProModel',
								  data: { "modelId": treeNode.id },
                  type: "POST",
								  success: function (result) {
									  if( result.code == 200 ){
										  layer.msg(result.message, {icon: 1});
										  zTree.removeNode(treeNode);
									  }else {
										  layer.msg(result.message, {icon: 2});
									  }
								  }
							  });
						  });
				  });
			  }
			  else if(treeNode.modelFlag == "1") {
				  //模块
				  var html =
					  '<li class="rMenu-item rMenu-new-module">新增子模块</li>\n' +
					  '<li class="rMenu-item rMenu-new-api">新增接口节点</li>\n' +
					  '<li class="rMenu-item rMenu-new-service">新增服务节点</li>' +
					  '<li class="rMenu-item rMenu-new-page">新增页面节点</li>'+
					  '<li class="rMenu-item rMenu-edit-module">编辑</li>'+
					  '<li class="rMenu-item rMenu-delete-module">删除</li>';

				  rMenu.append(html);
				  rMenu.css("display","block");
				  rMenu.css("top", y + "px");
				  rMenu.css("left", x + "px");

				  $(".rMenu-new-module").click(function (e) {
					  var formId = getuuid(16,16);
					  var html ='<div style="padding: 0 15px 0 0;">'+
						  '   <form class="layui-form" action="" id="'+formId+'">' +
						  '       <div class="layui-form-item">' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="modelName" class="layui-input" autocomplete="off" >\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="sortId" class="layui-input" autocomplete="off">\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">说明</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="resume" class="layui-input" autocomplete="off">\n' +
						  '             <input type="text" name="modelFlag" value="1" hidden="hidden"/>' +
						  '             <input type="text" name="parentId" value="'+ moduleId +'" hidden="hidden"/>' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
						  '       </div>' +
						  '   </form>' +
						  '</div>' ;
					  var index = layer.open({
						  type: 1,
						  shade: 0,
						  area: ["300px","250px"],
						  skin: 'myLayerOpnSkin',
						  title: '新增模块',
						  content: html
					  });

					  $("#" + formId ).find(".layui-btn-normal").click(function (e) {
						  e.preventDefault();
						  var data = $("#" + formId).serialize();
						  layer.close(index);
						  $.ajax({
							  url: curContext + '/v1/sqlQuery/saveProModel',
							  type: 'post',
							  data: data,
							  success: function (result) {
								  if( result.modelId ){
									  var zTree = $.fn.zTree.getZTreeObj("curTree");
									  zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '22', iconSkin : 'api' });
								  }
							  }
						  });
					  });
					  $("#" + formId ).find(".layui-btn-primary").click(function(e) {
						  e.preventDefault();
						  layer.close(index);
					  });
				  });
				  $(".rMenu-new-api").click(function(e) {
					  $.ajax({
						  url: curContext + '/v1/sqlQuery/saveProModel',
						  type: 'post',
						  data: { "modelName" : "接口", "sortId": "1", "modelFlag": "22", "parentId": moduleId },
						  success: function (result) {
							  if( result.modelId ){
								  var zTree = $.fn.zTree.getZTreeObj("curTree");
								  zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '22', iconSkin : 'api' });
							  }
						  }
					  });
				  });
				  $(".rMenu-new-service").click(function (e) {
					  $.ajax({
						  url: curContext + '/v1/sqlQuery/saveProModel',
						  type: 'post',
						  data: { "modelName" : "服务", "sortId": "1", "modelFlag": "33", "parentId": moduleId },
						  success: function (result) {
							  if( result.modelId ){
								  var zTree = $.fn.zTree.getZTreeObj("curTree");
								  zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '33', iconSkin : 'service' });
							  }
						  }
					  });
				  });
				  $(".rMenu-new-page").click(function (e) {
					  $.ajax({
						  url: curContext + '/v1/sqlQuery/saveProModel',
						  type: 'post',
						  data: { "modelName" : "页面", "sortId": "1", "modelFlag": "44", "parentId": moduleId },
						  success: function (result) {
							  if( result.modelId ){
								  var zTree = $.fn.zTree.getZTreeObj("curTree");
								  zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '44', iconSkin : 'page' });
							  }
						  }
					  });
				  });
				  $(".rMenu-edit-module").click(function (e) {
					  var formId = getuuid(16,16);
					  var html ='<div style="padding: 0 15px 0 0;">'+
						  '   <form class="layui-form" action="" id="'+formId+'">' +
						  '       <div class="layui-form-item">' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="modelName" class="layui-input" autocomplete="off" value="'+ treeNode.name +'">\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="sortId" class="layui-input" autocomplete="off" value="'+ treeNode.sortId +'">\n' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '           <label class="layui-form-label" style="width: 40px;text-align: center;">说明</label>\n' +
						  '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
						  '             <input type="text" name="resume" class="layui-input" autocomplete="off" value="'+ treeNode.resume +'">\n' +
						  '             <input type="text" name="modelFlag" value="1" hidden="hidden"/>' +
						  '             <input type="text" name="parentId" value="'+ treeNode.pId +'" hidden="hidden"/>' +
						  '             <input type="text" name="modelId" value="'+ treeNode.id +'" hidden="hidden"/>' +
						  '           </div>\n' +
						  '       </div>' +
						  '       <div class="layui-form-item">\n' +
						  '         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
						  '       </div>' +
						  '   </form>' +
						  '</div>' ;
					  var index = layer.open({
						  type: 1,
						  shade: 0,
						  area: ["300px","250px"],
						  skin: 'myLayerOpnSkin',
						  title: '编辑',
						  content: html
					  });

					  $("#" + formId ).find(".layui-btn-normal").click(function (e) {
						  e.preventDefault();
						  var data = $("#" + formId).serialize();
						  layer.close(index);
						  $.ajax({
							  url: curContext + '/v1/sqlQuery/saveProModel',
							  data: data,
							  type: "POST",
							  success: function (result) {
								  if( result.modelId ){
									  var zTree = $.fn.zTree.getZTreeObj("curTree");
									  node = zTree.getNodeByParam("id", result.modelId);
									  node.name = result.modelName;
									  zTree.updateNode(node);
								  }
							  }
						  });
					  });
					  $("#" + formId ).find(".layui-btn-primary").click(function(e) {
						  e.preventDefault();
						  layer.close(index);
					  });
				  });
				  $(".rMenu-delete-module").click(function (e) {
					  if( treeNode.isParent ){
						  layer.msg('父节点不能直接删除', {icon: 2});
						  return false;
					  }
					  layer.confirm('确认删除'+ treeNode.name +'节点吗？',
						  {
							  icon: 3,
							  shade: 0,
							  skin: 'myLayerOpnSkin'
						  }, function(index){
							  $.ajax({
								  url: curContext + '/v1/sqlQuery/deleteProModel',
								  data: { "modelId": treeNode.id },
								  type: "POST",
								  success: function (result) {
									  if( result.code == 200 ){
										  layer.msg(result.message, {icon: 1});
										  zTree.removeNode(treeNode);
									  }else {
										  layer.msg(result.message, {icon: 2});
									  }
								  }
							  });
						  });
				  });
			  }
			  else if(treeNode.modelFlag == "22") {
				  //接口节点
				  var html =
					  '<li class="rMenu-item rMenu-delete-api">删除</li>\n' ;

				  rMenu.append(html);
				  rMenu.css("display","block");
				  rMenu.css("top", y + "px");
				  rMenu.css("left", x + "px");

          $(".rMenu-delete-api").click(function(e) {
	          layer.confirm('确认删除api节点吗？',
		          {
			          icon: 3,
			          shade: 0,
			          skin: 'myLayerOpnSkin'
		          }, function(index){
			          $.ajax({
				          url: curContext + '/v1/sqlQuery/deleteProModel',
				          data: { "modelId": treeNode.id },
				          type: "POST",
				          success: function (result) {
					          if( result.code == 200 ){
						          layer.msg(result.message, {icon: 1});
						          zTree.removeNode(treeNode);
					          }else {
						          layer.msg(result.message, {icon: 2});
					          }
				          }
			          });
		          });
          });
			  }
			  else if(treeNode.modelFlag == "33") {
				  //服务节点
				  var html =
					  '<li class="rMenu-item rMenu-delete-service">删除</li>\n' ;

				  rMenu.append(html);
				  rMenu.css("display","block");
				  rMenu.css("top", y + "px");
				  rMenu.css("left", x + "px");

				  $(".rMenu-delete-service").click(function(e) {
					  layer.confirm('确认删除service节点吗？',
						  {
							  icon: 3,
							  shade: 0,
							  skin: 'myLayerOpnSkin'
						  }, function(index){
							  $.ajax({
								  url: curContext + '/v1/sqlQuery/deleteProModel',
								  data: { "modelId": treeNode.id },
								  type: "POST",
								  success: function (result) {
									  if( result.code == 200 ){
										  layer.msg(result.message, {icon: 1});
										  zTree.removeNode(treeNode);
									  }else {
										  layer.msg(result.message, {icon: 2});
									  }
								  }
							  });
						  });
				  });
        }
        else if(treeNode.modelFlag == "44") {
          //页面节点
				  var html =
					  '<li class="rMenu-item rMenu-delete-page">删除</li>\n' ;

				  rMenu.append(html);
				  rMenu.css("display","block");
				  rMenu.css("top", y + "px");
				  rMenu.css("left", x + "px");

				  $(".rMenu-delete-page").click(function(e) {
					  layer.confirm('确认删除page节点吗？',
						  {
							  icon: 3,
							  shade: 0,
							  skin: 'myLayerOpnSkin'
						  }, function(index){
							  $.ajax({
								  url: curContext + '/v1/sqlQuery/deleteProModel',
								  data: { "modelId": treeNode.id },
								  type: "POST",
								  success: function (result) {
									  if( result.code == 200 ){
										  layer.msg(result.message, {icon: 1});
										  zTree.removeNode(treeNode);
									  }else {
										  layer.msg(result.message, {icon: 2});
									  }
								  }
							  });
						  });
				  });
        }
        else {
				  rMenu.css("display","none");
			  }

			  zTree.selectNode(treeNode);
		  }

		  function zTreeOnClick(event, treeId, treeNode) {
			  var moduleId = treeNode.pId;
			  var zTree = $.fn.zTree.getZTreeObj("curTree");
			  var pNode = zTree.getNodeByParam("id", moduleId);
			  var pName = pNode.name;
		  	if( treeNode.modelFlag == "22" ) {
		  		//跳转至接口
				  $("#IUFrame").attr("src", "<%=path%>/v1/sqlModel/manage/databaseApi/" + moduleId);
        } else if( treeNode.modelFlag == "33" ) {
          //跳转至服务
				  $("#IUFrame").attr("src", "<%=path%>/processTable/index/" + moduleId + "/" + pName );
        } else if( treeNode.modelFlag == "44" ) {
          //跳转至页面
				  $("#IUFrame").attr("src", "<%=path%>/v1/pageType/databasePagePicker/" + moduleId );
        }
      }

		  var zTreeObj = $.fn.zTree.init($("#curTree"), setting, treeArray);
		  zTree = $.fn.zTree.getZTreeObj("curTree");
	  }

  	//项目模块列表
    $.ajax({
      url : '<%=path%>/v1/sqlQuery/getProModel',
      type: 'post',
      success: function( result ) {
        loadModuleTree(result);
      }
    });

    //新建项目
    $("#newProject").click(function(e) {
	    var formId = getuuid(16,16);
	    var html ='<div style="padding: 0 15px 0 0;">'+
		    '   <form class="layui-form" action="" id="'+formId+'">' +
		    '       <div class="layui-form-item">' +
		    '           <label class="layui-form-label" style="width: 40px;text-align: center;">名称</label>\n' +
		    '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
		    '             <input type="text" name="modelName" class="layui-input" autocomplete="off" >\n' +
		    '           </div>\n' +
		    '       </div>' +
		    '       <div class="layui-form-item">\n' +
		    '           <label class="layui-form-label" style="width: 40px;text-align: center;">序号</label>\n' +
		    '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
		    '             <input type="text" name="sortId" class="layui-input" autocomplete="off">\n' +
		    '           </div>\n' +
		    '       </div>' +
		    '       <div class="layui-form-item">\n' +
		    '           <label class="layui-form-label" style="width: 40px;text-align: center;">说明</label>\n' +
		    '           <div class="layui-input-block" style="margin-left: 70px;">\n' +
		    '             <input type="text" name="resume" class="layui-input" autocomplete="off">\n' +
        '             <input type="text" name="modelFlag" value="0" hidden="hidden"/>' +
        '             <input type="text" name="parentId" value="" hidden="hidden"/>' +
		    '           </div>\n' +
		    '       </div>' +
		    '       <div class="layui-form-item">\n' +
		    '         <button class="layui-btn layui-btn-normal layui-btn-sm" style="margin-left: 15px;">确认</button><button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>'+
		    '       </div>' +
		    '   </form>' +
		    '</div>' ;
	    var index = layer.open({
		    type: 1,
		    shade: 0,
		    area: ["300px","250px"],
		    skin: 'myLayerOpnSkin',
		    title: '添加项目',
		    content: html
	    });
	    $("#" + formId ).find(".layui-btn-normal").click(function (e) {
		    e.preventDefault();
		    var data = $("#" + formId ).serialize();
		    layer.close(index);
		    $.ajax({
			    url: curContext + '/v1/sqlQuery/saveProModel',
          type: 'post',
			    data: data,
			    success: function (result) {
				    if( result.modelId ){
					    var zTree = $.fn.zTree.getZTreeObj("curTree");
					    zTree.addNodes(null, {id:result.modelId, pId:"", name: result.modelName, modelFlag: '0', iconSkin: 'project' });
				    }
			    }
		    });
	    });
    });
  });
</script>
<script>
	$(document).click(function(e){
		var e = e || window.event;
		if( !$(e.target).hasClass("rMenu-item") ) {
			$(".rMenu").css("display","none");
		}
	});
</script>
</body>
</html>
