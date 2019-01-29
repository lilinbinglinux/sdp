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

    <link rel="stylesheet" href="<%=path%>/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/layui-v2.2.6/layui/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/layui-formSelects-master/dist/formSelects-v4.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/databaseModeling/index.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/databaseModeling/accordion.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/databaseModeling/myLayerSkin.css">
    <style>
      .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
    </style>
    <style>
      .ztree li span.button.database_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/database.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.database_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/database.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.subarea_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/subarea.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.subarea_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/subarea.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}

      .ztree li span.button.subarea_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/subarea.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.subarea_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/subarea.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.partition_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/partition.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.partition_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/partition.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}


      .ztree li span.button.table_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/databaseTable.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.type_ico_docu{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/type.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.type_ico_open{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/type.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}
      .ztree li span.button.type_ico_close{margin-right:2px; background: url("<%=path%>/resources/img/databaseModeling/type.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle;background-size: 16px 16px;}


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
    <script src="<%=path%>/resources/plugin/jquery/jquery-1.9.1.js"></script>
    <script>
      document.onreadystatechange = function() {
        if( document.readyState == "complete" ){
          $("#loading").fadeOut();
        }
      }
    </script>
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
        <input type="text" hidden="hidden" id="resType" />
        <div style="width: 200px;height: 40px;display: inline-block;position: absolute;right: 15px;">
          <span class="menu-save-btn">保存</span>
        </div>
    </div>
    <div class="power-main">
        <div class="power-left-nav">
            <div class="stepTwo-module">
              <ul id="curTree" class="ztree">

              </ul>
            </div>

        </div>
        <div class="power-container" id="power-container">
            <div class="power-canvas-container" id="container" >
                <canvas class="power-canvas" id="power-canvas"></canvas>

            </div>
            <div class="power-circle"></div>
        </div>
        <ul class="rMenu">
          <li>新增</li>
          <li>编辑</li>
          <li>删除</li>
        </ul>
    </div>
</div>
<div id="loading">
  <div class="lds-css ng-scope"><div style="width:100%;height:100%;position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    margin: auto;" class="lds-ripple"><div></div><div></div></div><style type="text/css">@keyframes lds-ripple {
                                                                                                                                       0% {
                                                                                                                                         top: 96px;
                                                                                                                                         left: 96px;
                                                                                                                                         width: 0;
                                                                                                                                         height: 0;
                                                                                                                                         opacity: 1;
                                                                                                                                       }
                                                                                                                                       100% {
                                                                                                                                         top: 18px;
                                                                                                                                         left: 18px;
                                                                                                                                         width: 156px;
                                                                                                                                         height: 156px;
                                                                                                                                         opacity: 0;
                                                                                                                                       }
                                                                                                                                     }
  @-webkit-keyframes lds-ripple {
    0% {
      top: 96px;
      left: 96px;
      width: 0;
      height: 0;
      opacity: 1;
    }
    100% {
      top: 18px;
      left: 18px;
      width: 156px;
      height: 156px;
      opacity: 0;
    }
  }
  .lds-ripple {
    position: relative;
  }
  .lds-ripple div {
    box-sizing: content-box;
    position: absolute;
    border-width: 4px;
    border-style: solid;
    opacity: 1;
    border-radius: 50%;
    -webkit-animation: lds-ripple 1s cubic-bezier(0, 0.2, 0.8, 1) infinite;
    animation: lds-ripple 1s cubic-bezier(0, 0.2, 0.8, 1) infinite;
  }
  .lds-ripple div:nth-child(1) {
    border-color: #337ab7;
  }
  .lds-ripple div:nth-child(2) {
    border-color: #5bc0de;
    -webkit-animation-delay: -0.5s;
    animation-delay: -0.5s;
  }
  .lds-ripple {
    width: 200px !important;
    height: 200px !important;
    -webkit-transform: translate(-100px, -100px) scale(1) translate(100px, 100px);
    transform: translate(-100px, -100px) scale(1) translate(100px, 100px);
  }
  </style></div>
</div>
<script>
	var curContext = '<%=path%>';
	var linesArray = [];
</script>
<script src="<%=path%>/resources/plugin/layui-v2.2.6/layui/layui.all.js"></script>
<script src="<%=path%>/resources/plugin/layui-formSelects-master/dist/formSelects-v4.js"></script>
<script src="<%=path%>/resources/js/databaseModeling/uuid.js"></script>
<script src="<%=path%>/resources/js/databaseModeling/drawArrow.js"></script>
<script src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
<script src="<%=path%>/resources/js/databaseModeling/accordion.js"></script>
<script>
	  var dataResId = '${dataResId}';

	  var powerCanvas = document.getElementById("power-canvas");
	  var ctx1 = powerCanvas.getContext("2d");
	  var powerContainerW = $("#power-container").width();
	  var powerContainerH = $("#power-container").height();
	      powerCanvas.width = powerContainerW;
	      powerCanvas.height = powerContainerH;

	  var powerContainer = document.getElementById("container");
	  var powerConContainer = document.getElementById("power-container");

	  var tableNames = [];

	  var isPowerBoxMove = false,
		    powerBoxX = 0,
		    powerBoxY = 0,
		    powerBoxLeft = 0,
		    powerBoxTop = 0,
		    whichPowerMove = null;

    var dataResTypeId = 0;

	  //调用接口 获取该数据源下的所有表类型及表类型下的所有表
	  $.ajax({
		  url: '<%=path%>/v1/sqlModel/show/dataRes',
		  data: { "dataResId": dataResId },
		  success: function( result ) {
			  if( result.code == "000000" ) {
				  loadNewTree( result );

          dataResTypeId = result.sqlDataRes.dataResTypeId;
				  $.ajax({
            url: '<%=path%>/v1/sqlModel/manage/dataSourceType',
            data:{ "dataResTypeId": dataResTypeId },
            success:function(result){
              if( result.dataResTypeId ){
                $("#resType").val( result.resType );
              }
            }
          });
			  }
		  }
	  });

	  //查询数据源下已存在的所有表名
    $.ajax({
      url: '<%=path%>/v1/sqlModel/checkTableName',
      data:{ "dataResId": dataResId },
      success: function( result ) {
	      tableNames = result;
      }
    });

    //工具栏点击事件
	  $(".power-tool-item").each(function () {
		  $(this).on('click', function (e) {

			  $(powerContainer).width(powerCanvas.width);
			  $(powerContainer).height(powerCanvas.height);

			  if( $(this).hasClass("tool-class-box") ) {
				  //建表工具
				  $(".power-canvas-container").unbind('mousedown');

				  if( $(this).hasClass("tool-item-active") ) {

					  powerContainer.style='cursor: default;';
					  $(this).removeClass("tool-item-active");
					  $(".power-canvas-container").unbind('mousedown');

				  }else {
					  $(".power-tool-item").not($(".tool-straight-line")).removeClass("tool-item-active");
					  $(this).addClass("tool-item-active");
					  powerContainer.style='cursor: url("<%=path%>/resources/img/dataModel/table.svg"), pointer;';

					  $(".power-canvas-container").mousedown(function (e) {

						  if( $(e.target).parents(".power-class-box").length > 0 || $(e.target).hasClass("power-class-box") ){

						  } else {

							  var offsetX = e.clientX - $("#power-container")[0].offsetLeft + $("#power-container")[0].scrollLeft;
							  var offsetY = e.clientY - $(".power-main")[0].offsetTop + $("#power-container")[0].scrollTop;
							  var uuid = getuuid(16,16);
							  var html = getPowerBoxHtml(uuid, offsetX, offsetY);

							  $('.power-canvas-container').append(html);

							  powerContainer.style='cursor: default;';
							  $(".tool-class-box").removeClass("tool-item-active");
							  $(".power-canvas-container").unbind('mousedown');

							  //绑定双击事件
							  $("#" + uuid ).dblclick(function (e) {
								  var id = $(this).attr("id");
								  powerClassDBClick(e, offsetX, offsetY, id);
							  });

							  if( $("#power-container").width() - offsetX  < 256 ) {
//								  $("#container").width( $("#container").width() + 300 );
								  powerCanvas.width = $("#container").width() + 300 - 10;
							  }

							  if( $("#power-container").height() - offsetY < 256 ) {
//								  $("#container").height( $("#container").height() + 300 );
								  powerCanvas.height = $("#container").height()+ 300 - 20;
                }
						  }
					  });
				  }
			  }
			  else if( $(this).hasClass("tool-hand") ) {
				  //手势工具
				  $(".power-canvas-container").unbind('mousedown');
				  if( $(this).hasClass("tool-item-active") ) {

					  powerContainer.style='cursor: default;';
					  $(this).removeClass("tool-item-active");
					  $(".power-canvas-container").unbind('mousedown');
				  }else {
					  powerContainer.style='cursor: pointer;';
					  $(".power-tool-item").not($(".tool-straight-line")).removeClass("tool-item-active");
					  $(this).addClass("tool-item-active");
					  $(".power-canvas-container").mousedown(function (e) {
						  e = e || window.event;
						  /*
               *
               * power-container 拖动
               * */
						  var oy = e.clientY;
						  var ox = e.clientX;
						  var oscrollTop = powerConContainer.scrollTop;
						  var oscrollLeft = powerConContainer.scrollLeft;

						  $(this).mousemove(function (e) {
							  e = e || window.event;
							  powerConContainer.scrollTop = oscrollTop - ( e.clientY - oy );
							  powerConContainer.scrollLeft = oscrollLeft - ( e.clientX - ox );
						  });

						  $(this).mouseup(function (e) {
							  $(this).unbind("mousemove");
							  $(this).unbind("mouseup");
						  });
						  /*^^^^^^^^^^^^^^^^^^^^^^^^^^power-container 拖动^^^^^^^^^^^^^^^^^^^^^^^^^^*/
					  });
				  }
			  }
			  else if( $(this).hasClass("tool-mouse") ) {
				  $(".power-canvas-container").unbind('mousedown');
				  if( $(this).hasClass("tool-item-active") ) {
					  $(this).removeClass("tool-item-active");
					  $(".power-canvas-container").unbind('mousedown');
				  }else {
					  $(".power-tool-item").not($(".tool-line")).removeClass("tool-item-active");
					  $(this).addClass("tool-item-active");
					  powerContainer.style='cursor: default;';
				  }
			  }
			  else if( $(this).hasClass("tool-straight-line") ) {
				  //划线
				  $(".power-canvas-container").unbind('mousedown');
				  if( $(this).hasClass("tool-item-active") ) {
					  $(this).removeClass("tool-item-active");
					  $(".power-canvas-container").unbind('mousedown');
				  }else {
					  $(".tool-line").not($(".tool-straight-line")).removeClass("tool-item-active");
					  $(this).addClass("tool-item-active");
				  }
			  }
			  else if( $(this).hasClass("tool-delete") ) {
				  $(".power-canvas-container").unbind('mousedown');
				  if( $(".power-active") ) {
					  var curObj = $(".power-active");

					  if( curObj.hasClass("power-class-box") ) {
						  var tableId = curObj.attr("table-id");
						  var lines = $(".line_box_container");
						  var isHaveForeignKey = false;
						  for( var i = 0; i < lines.length; i++ ) {
							  if( $(lines[i]).attr("fromboxid") == tableId || $(lines[i]).attr("toboxid") == tableId ) {
								  isHaveForeignKey = true;
							  }
						  }
						  if( isHaveForeignKey ) {
							  layer.msg('当前表含有关联字段！请先删除关联外键', {icon: 2});
							  return;
						  }
						  $.ajax({
							  url: '<%=path%>/v1/sqlModel/deletetableinfo/' + tableId,
							  success: function(result){
								  if( result.code == 200 ) {
									  curObj.remove();
								  }
							  }
						  });
					  } else if( curObj.hasClass("line_box_container") ) {
						  var lineId = curObj.attr("foreignKeyId");
						  $.ajax({
							  url: '<%=path%>/v1/sqlModel/deletesqltabletypeforeignkey',
							  data: { "foreignKeyId": lineId },
							  success: function(result){
								  if( result.code == 200 ){
									  curObj.remove();
								  }
							  }
						  });
					  }
				  }
			  }

			  $(powerContainer).width(powerCanvas.width);
			  $(powerContainer).height(powerCanvas.height);
		  });
	  });

	  $(".tool-class-box").mouseenter(function () {
		  var index = layer.tips("建表", $(this),{tips:3});
		  $(this).mouseleave(function () {
			  layer.close(index);
		  });
	  });
	  $(".tool-hand").mouseenter(function () {
		  var index = layer.tips("移动视图", $(this),{tips:3});
		  $(this).mouseleave(function () {
			  layer.close(index);
		  });
	  });
	  $(".tool-mouse").mouseenter(function () {
		  var index = layer.tips("模型移动", $(this),{tips:3});
		  $(this).mouseleave(function () {
			  layer.close(index);
		  });
	  });
	  $(".tool-straight-line").mouseenter(function () {
		  var index = layer.tips("直线", $(this),{tips:3});
		  $(this).mouseleave(function () {
			  layer.close(index);
		  });
	  });
	  $(".tool-delete").mouseenter(function () {
		  var index = layer.tips("删除", $(this),{tips:3});
		  $(this).mouseleave(function () {
			  layer.close(index);
		  });
	  });

	  //统一重新保存所有坐标
	  $(".menu-save-btn").click(function (e) {
		  saveXY();
	  });

	  $(document).click(function(e){
		  var e = e || window.event;
		  if( !$(e.target).hasClass("rMenu-item") ) {
			  $(".rMenu").css("display","none");
		  }
		  $(".power-active").removeClass("power-active");

		  if( $(e.target).parents(".power-class-box").length > 0 || $(e.target).hasClass("power-class-box") ){
		  	if( $(e.target).hasClass("box-delete") ){
          var tableId = $(e.target).find("input[name='tableId']").val();
          var curBox = $($(e.target).parents(".power-class-box")[0]);

	          layer.confirm('确认删除当前表吗？',
		          {
			          icon: 3,
			          shade: 0,
			          skin: 'myLayerOpnSkin'
		          }, function(index){
			          layer.close(index);

			          var lines = $(".line_box_container");
			          var isHaveForeignKey = false;
			          layer.close(index);
			          for( var i = 0; i < lines.length; i++ ) {
				          if( $(lines[i]).attr("fromboxid") == tableId || $(lines[i]).attr("toboxid") == tableId ) {
					          isHaveForeignKey = true;
				          }
			          }
			          if( isHaveForeignKey ) {
				          layer.msg('当前表含有关联字段！请先删除关联外键', {icon: 2});
				          return;
			          }

			          $("#loading").fadeIn();

			          if( tableId != '' ){
				          $.ajax({
					          url:  curContext + '/v1/sqlModel/deletetableinfo/' + tableId,
					          success: function(result){
						          if( result.code == 200 ) {
							          curBox.remove();
							          layer.msg('删除成功！', {icon: 1});
							          var zTree = $.fn.zTree.getZTreeObj("curTree");
							          var node = zTree.getNodeByParam("id", tableId);
							          zTree.removeNode(node);

							          $(".line_box_container").remove();
							          $(".box-item-circle").removeClass("circle-inline");
							          loadFoeignkeys();
						          } else{
							          $("#loading").fadeOut();
							          layer.msg('删除失败！', {icon: 2});
						          }
					          }
				          });
                } else {
				          curBox.remove();
				          $("#loading").fadeOut();
				          layer.msg('删除成功！', {icon: 1});
			          }
		          });
        } else {
				  var powerBox = $(e.target).parents(".power-class-box").length > 0 ? $(e.target).parents(".power-class-box") : $(e.target).hasClass("power-class-box") ? $(e.target) : null;
				  powerBox.addClass("power-active");
        }
		  }

		  var lineContainer = $(".line_box_container");
		  for( var i = 0; i < lineContainer.length; i++ ) {
			  var curLineContainer = $(lineContainer[i]);
			  var curVanvas = curLineContainer.find("canvas")[0];
			  var lineCtx = curVanvas.getContext("2d");
			  var x = e.clientX - $("#power-container")[0].offsetLeft - curLineContainer[0].offsetLeft + $("#power-container").scrollLeft();
			  var y = e.clientY - $(".power-main")[0].offsetTop - curLineContainer[0].offsetTop + $("#power-container").scrollTop();

			  if( lineCtx.isPointInStroke ) {
				  if( lineCtx.isPointInStroke(x,y) ){
					  curLineContainer.addClass("power-active");
					  curLineContainer.addClass("hover-line");
					  if( linesArray.length > 0 ){
						  for( var i = 0; i < linesArray.length; i++ ){
							  if( lineId == linesArray[i].id ){
								  lineCtx.clearRect(0,0, curLineContainer.width(), curLineContainer.height() );
								  drawMelodyArrow( lineCtx, linesArray[i], 2, '#fc0c01', 'shadow' );
							  }
						  }
					  }
				  } else {
					  var hoverLines = $(".hover-line");
					  if(hoverLines.length > 0){
						  for( var j = 0, len = hoverLines.length ; j < len; j++ ){

							  var curLineContainer = $(hoverLines[j]);
							  var curVanvas = curLineContainer.find("canvas")[0];
							  var lineCtx = curVanvas.getContext("2d");
							  var lineId = curLineContainer.attr("foreignKeyId");

							  curLineContainer.removeClass("hover-line");

							  if( linesArray.length > 0 ) {
								  for( var k = 0; k < linesArray.length; k++ ){
									  if( lineId == linesArray[k].id ){
										  lineCtx.clearRect(0,0, curLineContainer.width(),curLineContainer.height() );
										  drawMelodyArrow( lineCtx, linesArray[k], 2, '#96d6fc', null );
									  }
								  }
							  }
						  }
					  }
				  }
			  } else {
				  if( lineCtx.isPointInPath(x,y) ){
					  curLineContainer.addClass("power-active");
					  lineCtx.strokeStyle = "#333";
					  lineCtx.stroke();
				  } else {
					  lineCtx.strokeStyle = "#96d6fc";
					  lineCtx.stroke();
				  }
			  }
		  }
	  });

	  $("#container").mousemove(function (e) {
		  containerMousemove(e);
	  });

	  //判断某个字符串数组中是否包含某个字符串
    function isInArray( str, arr, type ) {
      var isIn = false;
      if( type == "edit" ){
      	var trr = [];
	      for( var i = 0; i < arr.length; i++ ){
		      if( arr[i] == str ){
			      trr.push(arr[i]);
		      }
	      }
	      if( trr.length > 1 ){
		      isIn = true;
        }
      } else {
	      for( var i = 0; i < arr.length; i++ ){
		      if( arr[i] == str ){
			      isIn = true;
			      break;
		      }
	      }
      }

      return isIn;
    }

	  //保存所有表的坐标
	  function saveXY() {
		  var powerBoxs = $(".power-class-box");
		  if( powerBoxs.length == 0 ){
			  return;
		  } else {
			  var data = [];
			  for( var i = 0; i < powerBoxs.length; i++ ){
				  data.push({
					  "tableId" : $(powerBoxs[i]).attr("id"),
					  "tableX" : $(powerBoxs[i]).position().left,
					  "tableY" : $(powerBoxs[i]).position().top
				  })
			  }
			  $.ajax({
				  url: curContext + "/v1/sqlModel/saveCoordinate",
				  type: "POST",
				  data: {"tableIdes" : JSON.stringify(data)},
				  success: function(result) {
					  console.log(result);
				  }
			  });
		  }
	  }

	  /*获取类盒子HTML*/
	  function getPowerBoxHtml(uuid, offsetX, offsetY) {
		  var html =
        '               <div id="'+uuid+'" class="power-class-box" style="top: '+ offsetY +'px;left: '+ offsetX +'px;" onmousedown="classBoxMousedown(this, event)" >\n' +
			  '                    <div class="class-box-title">\n' +
			  '                        表名1\n' +
			  '                    </div>\n' +
			  '                    <ul class="box-ul">\n' +
			  '                        <li>\n' +
			  '                            <span class="box-li-item" style="width: 15px;"></span>\n' +
			  '                            <span class="box-li-item" style="width: 40px;text-align: center;"></span>\n' +
			  '                            <span class="box-li-item" style="width: 130px;">名称</span>\n' +
			  '                            <span class="box-li-item" style="width: 50px;">类型</span>\n' +
			  '                            <span class="box-li-item" style="width: 15px;"></span>\n' +
			  '                        </li>\n' +
			  '                    </ul>\n' +
			  '                    <ul class="box-ul box-ul-list">\n' +
			  '                    </ul>\n' +
        '                <span class="fa fa-times box-delete" aria-hidden="true"><input hidden="hidden" type="text" name="tableId" /></span>' +
			  '                </div>';
		  return html;
	  }

	  //盒子双击事件
	  function powerClassDBClick( e, offsetX, offsetY, boxId ) {
		  var e = e || window.event;
		  var powerBox = $("#" + boxId );
		  if (typeof powerBox.attr("table-id") == 'undefined') {

			  var uuid = getuuid(16, 16);
			  var contentHtml = getContentHtml(uuid);

			  var areaHeight = $(window).height() * 0.8;

			  var layerIndex = layer.open({
				  type: 1,
				  shade: 0,
				  maxmin: true,
				  offset: 't',
				  area: ["1000px", areaHeight ],
				  skin: 'myLayerOpnSkin',
				  title: '表信息',
				  content: contentHtml
			  });
			  spanSelectBindClick();
			  layui.form.render('select');
			  layui.form.render('checkbox');

			  var tableTypeId = $("#tableTypeId").val();
			  $("#save_" + uuid).click(function (){
				  dataModalSave( uuid, tableTypeId, layerIndex, offsetX, offsetY, boxId, "save" );
			  });

			  $("#cancle_" + uuid).click(function () {
				  layer.close(layerIndex);
			  });

			  //不同的操作选项
			  var optionSelect = $("#operation_" + uuid);
        layui.form.on('select(operation)', function(data){

          if( data.value == "1"  ){
	          optionSelect.empty();

	          var html =
		          '<div class="mine-row">' +
		          '    <div class="layui-form-item">\n' +
		          '         <label class="layui-form-label">分区信息</label>\n' +
		          '         <div class="layui-input-block">\n' +
		          '           <div class="mine-row">\n' +
		          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                   <textarea class="layui-textarea" rows="8" id="partitionValue_'+uuid+'"></textarea>'+
		          '                </div>\n' +
		          '           </div>' +
		          '        </div>' +
		          '    </div>\n' +
              '    <div class="layui-form-item">\n' +
		          '         <label class="layui-form-label">模式</label>\n' +
		          '         <div class="layui-input-block">\n' +
		          '           <div class="mine-row">\n' +
		          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                   <input type="radio" name="state" value="0" title="自动" />'+
		          '                   <input type="radio" name="state" value="1" title="手动" />'+
		          '                </div>\n' +
		          '           </div>' +
		          '        </div>' +
		          '    </div>\n' +
		          '</div>';
	          optionSelect.append(html);
	          layui.form.render('radio');
          }
          else if( data.value == "2" ){
            //分表
	          optionSelect.empty();

	          var fieldHtml =
              '<div class="mine-row">' +
		          '    <div class="layui-form-item">\n' +
		          '         <label class="layui-form-label">字段</label>\n' +
		          '         <div class="layui-input-block">\n' +
		          '           <div class="mine-row">\n' +
		          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                    <select name="" lay-filter="fieldsSelect" id="fields_'+uuid+'">\n'+
		          '                        <option></option>\n' +
		          '                    </select>'+
		          '                </div>\n' +
		          '           </div>' +
		          '        </div>' +
              '    </div>\n' +
              '</div>'+
              '<div class="mine-row">' +
		          '    <div class="layui-form-item">\n' +
		          '         <label class="layui-form-label">类型</label>\n' +
		          '         <div class="layui-input-block">\n' +
		          '           <div class="mine-row">\n' +
		          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                    <select name="" lay-filter="typesSelect" id="types_'+uuid+'">\n'+
		          '                        <option></option>\n' +
		          '                        <option value="1">date</option>\n' +
		          '                        <option value="2">string(截取长度)</option>\n' +
		          '                        <option value="3">数值(如：100-1，200-2)</option>\n' +
		          '                    </select>'+
		          '                </div>\n' +
		          '           </div>' +
		          '        </div>' +
              '    </div>\n' +
              '</div>' +
              '<div class="mine-row" id="typeChange_'+uuid+'">' +
              '' +
              '</div>';
	          optionSelect.append(fieldHtml);

	          //遍历表中的字段
	          var content = $("#jspanel_content_" + uuid);
	          var tr = content.find("table > tbody > tr");

	          if( tr.length > 0 ){
              for( var t = 0; t < tr.length; t++ ){
                var name = $(tr[t]).find("input[name='fieldSqlName']").val();
                $("#fields_"+uuid).append('<option value="'+name+'">'+name+'</option>');
              }
            }
	          layui.form.render('select');

	          layui.form.on('select(typesSelect)', function(data){
              if( data.value == "1" ){
                //date
                var dateHtml =
                  '<div class="mine-row" id="dateHtml_'+uuid+'">' +
	                '    <div class="layui-form-item">\n' +
	                '         <label class="layui-form-label">date类型</label>\n' +
	                '         <div class="layui-input-block">\n' +
	                '           <div class="mine-row">\n' +
	                '                <div class="layui-input-block" style="margin-left:0px;">\n' +
	                '                    <select name="" id="dates_'+uuid+'">\n'+
	                '                        <option></option>\n' +
	                '                        <option value="YYYY">YYYY</option>\n' +
	                '                        <option value="YYYY-MM">YYYY-MM</option>\n' +
	                '                        <option value="YYYY-MM-DD">YYYY-MM-DD</option>\n' +
	                '                    </select>'+
	                '                </div>\n' +
	                '           </div>' +
	                '        </div>' +
	                '    </div>\n' +
	                '</div>';
	              $("#typeChange_"+uuid).empty();
	              $("#typeChange_"+uuid).append(dateHtml);
	              layui.form.render('select');
              }
              else if( data.value == "2" ){
                //string
                var stringHtml =
	                '<div class="mine-row" id="stringHtml_'+uuid+'">' +
	                '    <div class="layui-form-item">\n' +
	                '         <label class="layui-form-label">字符串截取起始值</label>\n' +
	                '         <div class="layui-input-block">\n' +
	                '           <div class="mine-row">\n' +
                  '             <div class="mine-row">' +
                  '                <div class="layui-col-md3">' +
	                '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
	                '                     <input type="text" name="subTableTypeValue1"  placeholder="开始" autocomplete="off" class="layui-input">\n' +
	                '                   </div>' +
	                '                </div>' +
                  '                <div class="layui-col-md1">' +
	                '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
                  '                     <span class="seLine"></span>' +
	                '                   </div>' +
	                '                </div>' +
                  '                <div class="layui-col-md3">' +
                  '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
	                '                     <input type="text" name="subTableTypeValue2"  placeholder="结束" autocomplete="off" class="layui-input">\n' +
	                '                   </div>' +
                  '                </div>' +
                  '             </div>'+
	                '           </div>' +
	                '        </div>' +
	                '    </div>\n' +
	                '</div>';
	              $("#typeChange_"+uuid).empty();
	              $("#typeChange_"+uuid).append(stringHtml);
	              layui.form.render('select');
              }
              else if( data.value == "3" ){
                //数值
                var numHtml =
	                '<div class="mine-row" id="numHtml_'+uuid+'">' +
	                '    <div class="layui-form-item">\n' +
	                '         <label class="layui-form-label">数值</label>\n' +
	                '         <div class="layui-input-block">\n' +
	                '           <div class="mine-row">\n' +
	                '              <div class="layui-input-block" style="margin-left: 0;">\n' +
	                '                <input type="text" name="subTableTypeValue1" placeholder="数值" autocomplete="off" class="layui-input">\n' +
	                '              </div>' +
	                '           </div>' +
	                '        </div>' +
	                '    </div>\n' +
	                '</div>';
	              $("#typeChange_"+uuid).empty();
	              $("#typeChange_"+uuid).append(numHtml);
              } else {
              	$("#typeChange_"+uuid).empty();
              }
            });
          }
          else if( data.value == "3" ){
            //分库
	          optionSelect.empty();

	          var fieldHtml =
		          '<div class="mine-row">' +
		          '    <div class="layui-form-item">\n' +
		          '         <label class="layui-form-label">字段</label>\n' +
		          '         <div class="layui-input-block">\n' +
		          '           <div class="mine-row">\n' +
		          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                    <select name="" lay-filter="fieldsSelect" id="fields_'+uuid+'">\n'+
		          '                        <option></option>\n' +
		          '                    </select>'+
		          '                </div>\n' +
		          '           </div>' +
		          '        </div>' +
		          '    </div>\n' +
		          '</div>'+
		          '<div class="mine-row">' +
		          '    <div class="layui-form-item">\n' +
		          '         <label class="layui-form-label">类型</label>\n' +
		          '         <div class="layui-input-block">\n' +
		          '           <div class="mine-row">\n' +
		          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                    <select name="" lay-filter="typesSelect" id="types_'+uuid+'">\n'+
		          '                        <option></option>\n' +
		          '                        <option value="1">date</option>\n' +
		          '                        <option value="2">string(截取长度)</option>\n' +
		          '                        <option value="3">数值</option>\n' +
		          '                    </select>'+
		          '                </div>\n' +
		          '           </div>' +
		          '        </div>' +
		          '    </div>\n' +
		          '</div>' +
		          '<div class="mine-row" id="typeChange_'+uuid+'">' +
		          '' +
		          '</div>' +
		          '<div class="mine-row">' +
		          '                    <div class="layui-form-item">\n' +
		          '                         <label class="layui-form-label">库</label>\n' +
		          '                         <div class="layui-input-block">\n' +
		          '                           <div class="mine-row">\n' +
		          '                                <div class="layui-input-block" style="margin-left:0px;">\n' +
		          '                                  <select xm-select="select1" id="sqlSubTreasury_'+uuid+'">\n' +
		          '                                  </select>'+
		          '                                </div>\n' +
		          '                           </div>' +
		          '                        </div>' +
		          '                    </div>\n' +
		          '</div>' +
              '<div class="mine-row" id="SqlSubTreasuryDivision_'+uuid+'">' +
              '</div>';
	          optionSelect.append(fieldHtml);

	          //遍历表中的字段
	          var content = $("#jspanel_content_" + uuid);
	          var tr = content.find("table > tbody > tr");

	          if( tr.length > 0 ){
		          for( var t = 0; t < tr.length; t++ ){
			          var name = $(tr[t]).find("input[name='fieldSqlName']").val();
			          $("#fields_"+uuid).append('<option value="'+name+'">'+name+'</option>');
		          }
	          }
	          layui.form.render('select');

	          layui.form.on('select(typesSelect)', function(data) {
		          if (data.value == "1") {
			          //date
			          var dateHtml =
				          '<div class="mine-row" id="dateHtml_' + uuid + '">' +
				          '    <div class="layui-form-item">\n' +
				          '         <label class="layui-form-label">date类型</label>\n' +
				          '         <div class="layui-input-block">\n' +
				          '           <div class="mine-row">\n' +
				          '                <div class="layui-input-block" style="margin-left:0px;">\n' +
				          '                    <select name="" id="dates_' + uuid + '">\n' +
				          '                        <option></option>\n' +
				          '                        <option value="YYYY">YYYY</option>\n' +
				          '                        <option value="YYYY-MM">YYYY-MM</option>\n' +
				          '                        <option value="YYYY-MM-DD">YYYY-MM-DD</option>\n' +
				          '                    </select>' +
				          '                </div>\n' +
				          '           </div>' +
				          '        </div>' +
				          '    </div>\n' +
				          '</div>';
			          $("#typeChange_" + uuid).empty();
			          $("#typeChange_" + uuid).append(dateHtml);
			          layui.form.render('select');
		          }
		          else if (data.value == "2") {
			          //string
			          var stringHtml =
				          '<div class="mine-row" id="stringHtml_' + uuid + '">' +
				          '    <div class="layui-form-item">\n' +
				          '         <label class="layui-form-label">字符串截取起始值</label>\n' +
				          '         <div class="layui-input-block">\n' +
				          '           <div class="mine-row">\n' +
				          '             <div class="mine-row">' +
				          '                <div class="layui-col-md3">' +
				          '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
				          '                     <input type="text" name="subTableTypeValue1"  placeholder="开始" autocomplete="off" class="layui-input">\n' +
				          '                   </div>' +
				          '                </div>' +
				          '                <div class="layui-col-md1">' +
				          '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
				          '                     <span class="seLine"></span>' +
				          '                   </div>' +
				          '                </div>' +
				          '                <div class="layui-col-md3">' +
				          '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
				          '                     <input type="text" name="subTableTypeValue2"  placeholder="结束" autocomplete="off" class="layui-input">\n' +
				          '                   </div>' +
				          '                </div>' +
				          '             </div>' +
				          '           </div>' +
				          '        </div>' +
				          '    </div>\n' +
				          '</div>';
			          $("#typeChange_" + uuid).empty();
			          $("#typeChange_" + uuid).append(stringHtml);
			          layui.form.render('select');
		          }
		          else if (data.value == "3") {
			          //数值
//			          var numHtml =
//				          '<div class="mine-row" id="numHtml_' + uuid + '">' +
//				          '    <div class="layui-form-item">\n' +
//				          '         <label class="layui-form-label">数值</label>\n' +
//				          '         <div class="layui-input-block">\n' +
//				          '           <div class="mine-row">\n' +
//				          '              <div class="layui-input-block" style="margin-left: 0;">\n' +
//				          '                <input type="text" name="subTableTypeValue1" placeholder="数值" autocomplete="off" class="layui-input">\n' +
//				          '              </div>' +
//				          '           </div>' +
//				          '        </div>' +
//				          '    </div>\n' +
//				          '</div>';
			          $("#typeChange_" + uuid).empty();
//			          $("#typeChange_" + uuid).append(numHtml);
		          } else {
			          $("#typeChange_" + uuid).empty();
		          }
	          });
//	          var html =
//		          '<div class="mine-row">' +
//		          '                    <div class="layui-form-item">\n' +
//		          '                        <label class="layui-form-label">库</label>\n' +
//		          '                         <div class="layui-input-block">\n' +
//		          '                           <div class="mine-row">\n' +
//		          '                                <div class="layui-input-block" style="margin-left:0px;">\n' +
//		          '                                  <select xm-select="select1" id="sqlSubTreasury_'+uuid+'">\n' +
//		          '                                  </select>'+
//		          '                                </div>\n' +
//		          '                           </div>' +
//		          '                        </div>' +
//		          '                    </div>\n' +
//		          '</div>';

//	          optionSelect.append(html);
	          var formSelects = layui.formSelects;

            $.ajax({
              url : "<%=path%>/v1/sqlModel//show/dataResByTypeIdList",
              data: { "dataResTypeId" : dataResTypeId },
              success: function(result){
                var dataRes = result.data;
                for( var s = 0; s < dataRes.length; s++ ){
                  $("#sqlSubTreasury_"+uuid).append("<option value='"+ dataRes[s].dataResId +"'>"+dataRes[s].dataResTypeName+"</option>");
                }
	              formSelects.render('select1', {
		              skin: "danger",                 //多选皮肤
		              height: "auto",                 //是否固定高度, 38px | auto
		              radio: false,                   //是否设置为单选模式
		              direction: "auto",
		              create: function(id, name){
//			              console.log(id);    //多选id
//			              console.log(name);  //创建的标签名称

			              return Date.now();  //返回该标签对应的val
		              },
		              template: function(name, value, selected, disabled){
//			              console.log(name);      //选项名
//			              console.log(value);     //选项值
//			              console.log(selected);  //是否被选中
//			              console.log(disabled);  //是否被禁用

			              //例如: 反转字符串
			              //return name.split('').reverse().join('');
			              return name;        //返回一个html结构, 用于显示选项
		              },
		              showCount: 0,           //多选的label数量, 0,负值,非数字则显示全部
	              });

	              formSelects.on('select1', function(id, vals, choice, isAdd, isDisabled){
		              //id:           点击select的id
		              //vals:         当前select已选中的值
		              //choice:       当前select点击的值
		              //isAdd:        当前操作选中or取消
		              //isDisabled:   当前选项是否是disabled

		              //如果return false, 那么将取消本次操作
		              for( var s = 0; s < vals.length; s++ ){
		              	if( !isAdd && vals[s].val == choice.val ){
				              $("#"+vals[s].name+'_'+vals[s].val).remove();
                    }
                  }

		              if( isAdd ){

                    var  types_ = $("#types_"+uuid).val();
                    if( types_ == "3" ){
                      var html =
	                      '<div class="layui-form-item" id="'+choice.name+'_'+choice.val+'">\n' +
	                      '     <label class="layui-form-label">'+choice.name+'</label>\n' +
	                      '     <div class="layui-input-block">\n' +
	                      '       <div class="mine-row">\n' +
	                      '            <div class="layui-input-block" style="margin-left:0px;">\n' +
	                      '             <div class="mine-row">' +
	                      '                <div class="layui-col-md3">' +
	                      '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
	                      '                     <input type="text" name="typeValue1"  placeholder="开始" autocomplete="off" class="layui-input">\n' +
	                      '                   </div>' +
	                      '                </div>' +
	                      '                <div class="layui-col-md1">' +
	                      '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
	                      '                     <span class="seLine"></span>' +
	                      '                   </div>' +
	                      '                </div>' +
	                      '                <div class="layui-col-md3">' +
	                      '                   <div class="layui-input-block" style="margin-left: 0;">\n' +
	                      '                     <input type="text" name="typeValue1"  placeholder="结束" autocomplete="off" class="layui-input">\n' +
	                      '                   </div>' +
	                      '                </div>' +
	                      '             </div>' +
	                      '            </div>\n' +
	                      '       </div>' +
	                      '    </div>' +
	                      '</div>\n';
	                    $("#SqlSubTreasuryDivision_"+uuid).append(html);
                    }else {
	                    $("#SqlSubTreasuryDivision_"+uuid).append(
		                    '<div class="layui-form-item" id="'+choice.name+'_'+choice.val+'">\n' +
		                    '     <label class="layui-form-label">'+choice.name+'</label>\n' +
		                    '     <div class="layui-input-block">\n' +
		                    '       <div class="mine-row">\n' +
		                    '            <div class="layui-input-block" style="margin-left:0px;">\n' +
		                    '              <input autocomplete="off" class="layui-input" />\n' +
		                    '            </div>\n' +
		                    '       </div>' +
		                    '    </div>' +
		                    '</div>\n'
	                    );
                    }
		              }
	              });
              }
            });
          }
			  });

		  }else {
			  var tableId = powerBox.attr("table-id");
			  var uuid = getuuid(16,16);
			  reviewTableDetail( tableId, uuid, offsetX, offsetY, boxId, "edit" );
		  }
	  }

	  //表数据回显与编辑
	  function reviewTableDetail( tableId, uuid, offsetX, offsetY, boxId, saveType ) {
		  $.ajax({
			  url: '<%=path%>/v1/sqlModel/show/tableFieldList',
			  data: {"tableId": tableId},
			  success:function (result) {
				  var contentHtml = '<div style="padding: 0 30px 0 15px;height:100%;" id="jspanel_content_'+ uuid +'">\n' +
					  '    <div class="layui-tab layui-tab-brief" style="margin:0;">\n' +
					  '        <ul class="layui-tab-title">\n' +
					  '            <li class="layui-this">基本</li>\n' +
					  '            <li>列</li>\n' +
					  '        </ul>\n' +
					  '        <div class="layui-tab-content">\n' +
					  '            <div class="layui-tab-item layui-show">\n' +
					  '                <form class="layui-form" action="">\n' +
					  '                    <div class="layui-form-item">\n' +
					  '                        <label class="layui-form-label">表名</label>\n' +
					  '                        <div class="layui-input-block">\n' +
					  '                            <input type="text" name="tableName" autocomplete="off" class="layui-input" value="'+ result.tableName +'">\n' +
					  '                            <input type="text" name="tableId" hidden value="'+ result.tableId +'">\n' +
					  '                            <input type="text" name="dataResId" hidden value="'+ result.dataResId +'">\n' +
					  '                            <input type="text" name="tableTypeId" hidden value="'+ result.tableTypeId +'">\n' +
					  '                        </div>\n' +
					  '                    </div>\n' +
					  '                    <div class="layui-form-item">\n' +
					  '                        <label class="layui-form-label">表英文名</label>\n' +
					  '                        <div class="layui-input-block">\n' +
					  '                            <input type="text" name="tableSqlName" autocomplete="off" class="layui-input" value="' + result.tableSqlName + '">\n' +
					  '                        </div>\n' +
					  '                    </div>\n' +
					  '                    <div class="layui-form-item layui-form-text">\n' +
					  '                        <label class="layui-form-label">注释</label>\n' +
					  '                        <div class="layui-input-block">\n' +
					  '                            <textarea name="tableResume" placeholder="请输入内容" class="layui-textarea">'+ ( result.tableResume == null ? '' : result.tableResume ) +'</textarea>\n' +
					  '                        </div>\n' +
					  '                    </div>\n' +
					  '                    <div class="layui-form-item layui-form-text">\n' +
					  '                        <label class="layui-form-label">序号</label>\n' +
					  '                        <div class="layui-input-block">\n' +
					  '                            <input name="sortId" autocomplete="off" class="layui-input" value="'+ ( result.sortId == null ? '' : result.sortId ) +'" />\n' +
					  '                        </div>\n' +
					  '                    </div>\n' +
					  '                </form>\n' +
					  '            </div>\n' +
					  '            <div class="layui-tab-item">\n' +
					  '                <div class="new-line-container">\n' +
					  '                    <span id="column-add" forTable="'+uuid+'" class="fa fa-plus-square line-tool-add" aria-hidden="true" onClick="addTableColumn(event)"></span>\n' +
					  '                    <span id="column-delete" forTable="'+uuid+'" class="fa fa-minus-square line-tool-add" aria-hidden="true" onClick="delteTableColumn(event)"></span>' +
					  '                </div>\n' +
					  '                <div style="max-height: 70%;overflow: auto;">' +
					  '               <table class="power-inner-table layui-table jpanel-inner-table" id="'+ uuid +'">\n' +
					  '                 <colgroup>\n' +
					  '                     <col width="50">\n' +
					  '                     <col width="50">\n' +
					  '                     <col width="120">\n' +
					  '                     <col width="120">\n' +
					  '                     <col width="100">\n' +
					  '                     <col width="100">\n' +
					  '                     <col width="100">\n' +
					  '                     <col width="50">\n' +
					  '                     <col width="50">\n' +
					  '                     <col width="50">\n' +
					  '                     <col>\n' +
					  '                 </colgroup>' +
					  '                    <thead>\n' +
					  '                        <tr>\n' +
					  '                            <th>' +
					  '                            <form class="layui-form">\n' +
					  '                              <div class="layui-form-item" style="margin-bottom: 0;">\n' +
					  '                                 <div class="layui-input-block" style="margin: 0;min-height: 0px;">\n' +
					  '                                     <input type="checkbox" name="" lay-skin="primary" lay-filter="columnCheckBox">\n' +
					  '                                 </div>\n' +
					  '                              </div>' +
					  '                            </form>' +
					  '                            </th>\n' +
					  '                            <th>编号</th>\n' +
					  '                            <th>名称</th>\n' +
					  '                            <th>英文名</th>\n' +
					  '                            <th>类型</th>\n' +
					  '                            <th>长度</th>\n' +
					  '                            <th>精度</th>\n' +
					  '                            <th>主键</th>\n' +
					  '                            <th>索引</th>\n' +
					  '                            <th>非空</th>\n' +
					  '                            <th>说明</th>\n' +
					  '                        </tr>\n' +
					  '                    </thead>\n' +
					  '                    <tbody>\n';

				  var sqlFieldes = result.sqlFieldes;
				  var isHaveFieldKey = false;

				  for( var j = 0; j < sqlFieldes.length; j++ ) {
					  if( sqlFieldes[j].fieldKey == "1" ) {
						  isHaveFieldKey = true;
						  break;
					  }
				  }

				  for( var i = 0; i < sqlFieldes.length; i++ ) {
					  contentHtml += '<tr>\n' +
						  '                            <td class="" >' +
						  '                              <form class="layui-form" lay-filter="columnCheck">\n' +
						  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
						  '                                         <input type="checkbox" class="lineCheckbox" name="lineCheckbox" lay-skin="primary" >\n' +
						  '                                     </div>\n' +
						  '                                 </div>' +
						  '                              </form>' +
						  '                            </td>\n' +
						  '                            <td class="" >'+ (i+1) +
						  '                               <input type="text" name="fieldId" hidden value="'+ sqlFieldes[i].fieldId +'">\n' +
              '                            </td>\n' +
						  '                            <td class="" >' +
						  '                                 <span class="span-select">'+ sqlFieldes[i].fieldName +'</span>' +
						  '                                 <form class="layui-form" action="" style="display: none;">' +
						  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
						  '                                          <input type="text" name="fieldName" autocomplete="off" class="layui-input" value="'+ sqlFieldes[i].fieldName +'">\n' +
						  '                                        </div>\n' +
						  '                                    </div>\n' +
						  '                                 </form>' +
						  '                            </td>\n' +
						  '                            <td class="" >' +
						  '                                 <span class="span-select">'+ sqlFieldes[i].fieldSqlName +'</span>' +
						  '                                 <form class="layui-form" action="" style="display: none;">' +
						  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
						  '                                            <input type="text" name="fieldSqlName" autocomplete="off" class="layui-input" value="'+ sqlFieldes[i].fieldSqlName +'">\n' +
						  '                                        </div>\n' +
						  '                                    </div>\n' +
						  '                                 </form>' +
						  '                            </td>\n' +
						  '                            <td class="" >\n' ;

					  var fieldType = sqlFieldes[i].fieldType;
					  var fieldTypeName = '';
					  var resType = $("#resType").val();
					  var fieldDateType = sqlFieldes[i].dateType;

					  if( resType == "1" ){
              //mysql
						  switch (sqlFieldes[i].fieldType) {
							  case "1":
								  fieldTypeName = "float";
								  break;
							  case "2":
								  fieldTypeName = "varchar";
								  break;
							  case "3":
								  fieldTypeName = "datetime";
								  break;
							  case "4":
								  fieldTypeName = "longtext";
								  break;
							  case "5":
								  fieldTypeName = "blob";
								  break;
							  case "6":
								  fieldTypeName = "int";
								  break;
							  case "7":
								  fieldTypeName = "bigint";
								  break;
							  case "9":
								  fieldTypeName = "tinyint";
								  break;
                case "10":
	                if( fieldDateType == "real" || fieldDateType == "double" || fieldDateType == "decimal" || fieldDateType == "numeric" ){
		                fieldTypeName = "float";
                  } else if( fieldDateType == "char" ) {
		                fieldTypeName = "varchar";
                  } else if( fieldDateType == "date" || fieldDateType == "time" || fieldDateType == "year" || fieldDateType == "timestamp" ){
		                fieldTypeName = "datetime";
                  } else if( fieldDateType == "tinytext" || fieldDateType == "text" || fieldDateType == "mediumtext" || fieldDateType == "longtext"  ){
		                fieldTypeName = "longtext";
                  } else if( fieldDateType == "tinyblob" || fieldDateType == "mediumblob" || fieldDateType == "longblob" ) {
		                fieldTypeName = "blob";
                  } else if( fieldDateType == "smallint" || fieldDateType == "mediumint" || fieldDateType == "int" || fieldDateType == "integer" ){
		                fieldTypeName = "int";
                  } else if( fieldDateType == "bit" ){
		                fieldTypeName = "tinyint";
                  } else if( fieldDateType == "set" ){
		                fieldTypeName = "set";
                  } else if( fieldDateType == "enum" ){
		                fieldTypeName = "enum";
                  } else {
		                fieldTypeName = "其它";
	                }
	                break;
							  default:
								  fieldTypeName = "";
						  }
            }
            else if( resType == "2" ) {
              //oracle
						  switch (sqlFieldes[i].fieldType) {
							  case "1":
								  fieldTypeName = "float";
								  break;
							  case "2":
								  fieldTypeName = "varchar2";
								  break;
							  case "3":
								  fieldTypeName = "date";
								  break;
							  case "4":
								  fieldTypeName = "blob";
								  break;
							  case "6":
								  fieldTypeName = "number";
								  break;
                case "10":
	                if( fieldDateType == "binary_float" || fieldDateType == "binary_double" || fieldDateType == "double precision" || fieldDateType == "real" ) {
		                fieldTypeName = "float";
	                }else if( fieldDateType == "char" || fieldDateType == "nchar" || fieldDateType == "varchar2" || fieldDateType == "character" || fieldDateType == "character varying" || fieldDateType == "char varying" || fieldDateType == "national character" || fieldDateType == "national character varying" || fieldDateType == "nchar varying" ) {
		                fieldTypeName = "varchar2";
	                }else if( fieldDateType == "bfile" || fieldDateType == "clob" ) {
		                fieldTypeName = "blob";
	                }else if( fieldDateType == "int" || fieldDateType == "integer" || fieldDateType == "smallint" || fieldDateType == "numeric" || fieldDateType == "decimal" ) {
		                fieldTypeName = "number";
	                }else{
		                fieldTypeName = "其它";
	                }
	                break;
							  default:
								  fieldTypeName = "";
						  }
            }
            else if(  resType == "3" ) {
              //db2
						  switch (sqlFieldes[i].fieldType) {
							  case "1":
								  fieldTypeName = "decimal";
								  break;
							  case "2":
								  fieldTypeName = "varchar";
								  break;
							  case "3":
								  fieldTypeName = "timestamp";
								  break;
							  case "4":
								  fieldTypeName = "clob";
								  break;
							  case "5":
								  fieldTypeName = "blob";
								  break;
							  case "6":
								  fieldTypeName = "int";
								  break;
							  case "7":
								  fieldTypeName = "bigint";
								  break;
							  case "9":
								  fieldTypeName = "boolean";
								  break;
							  case "10":
								  fieldTypeName = "其它";
								  break;
							  default:
								  fieldTypeName = "";
						  }
            }
            else if( resType == "5" ) {
              //sqlserver
						  switch (sqlFieldes[i].fieldType) {
							  case "1":
								  fieldTypeName = "float";
								  break;
							  case "2":
								  fieldTypeName = "varchar";
								  break;
							  case "3":
								  fieldTypeName = "datetime";
								  break;
							  case "4":
								  fieldTypeName = "nvarchar(max)";
								  break;
							  case "5":
								  fieldTypeName = "varbinary(max)";
								  break;
							  case "6":
								  fieldTypeName = "int";
								  break;
							  case "7":
								  fieldTypeName = "bigint";
								  break;
							  case "9":
								  fieldTypeName = "bit";
								  break;
							  case "10":
								  if( fieldDateType == "decimal" || fieldDateType == "numeric" || fieldDateType == "float" || fieldDateType == "money" || fieldDateType == "smallmoney" ){
									  fieldTypeName = "real";
								  }else if( fieldDateType == "char" || fieldDateType == "nchar" || fieldDateType == "nvarchar" ) {
									  fieldTypeName = "varchar";
								  }else if( fieldDateType == "time" || fieldDateType == "datetime" || fieldDateType == "datetime2" || fieldDateType == "datetimeoffset" || fieldDateType == "smalldatetime" ){
									  fieldTypeName = "date";
								  }else if( fieldDateType == "text" || fieldDateType == "ntext" || fieldDateType == "varchar" || fieldDateType == "nvarchar" ){
									  fieldTypeName = "nvarchar(max)";
								  }else if( fieldDateType == "image" ){
									  fieldTypeName = "varbinary(max)";
								  }else if( fieldDateType == "bigint" || fieldDateType == "tinyint" || fieldDateType == "smallint" ){
									  fieldTypeName = "int";
								  }else {
									  fieldTypeName = "其它";
								  }
								  break;
							  default:
								  fieldTypeName = "";
						  }
            }

					  contentHtml +=
						  '                                <span class="span-select">'+ fieldTypeName +'</span>\n' +
						  '                                <form class="layui-form" action="" style="display: none;">\n' +
						  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
						  '                                            <select name="fieldType" lay-filter="fieldType" disabled>\n' ;

					  if( resType == "1" ){
					  	//mysql
						  contentHtml +=
							  '<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>int</option>\n' +
							  '<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar</option>\n' +
							  '<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>float</option>\n' +
							  '<option value="7" '+ ( fieldType == "7" ? 'selected' : '' ) +'>bigint</option>\n' +
							  '<option value="9" '+ ( fieldType == "9" ? 'selected' : '' ) +'>tinyint</option>\n' +
							  '<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>datetime</option>\n' +
							  '<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>longtext</option>\n' +
							  '<option value="5" '+ ( fieldType == "5" ? 'selected' : '' ) +'>blob</option>\n' +
							  '<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
            }
            else if( resType == "2" ){
              //oracle
						  contentHtml +=
							  '<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>float</option>\n' +
							  '<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar2</option>\n' +
							  '<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>date</option>\n' +
							  '<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>blob</option>\n' +
							  '<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>number</option>\n' +
							  '<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
            }
            else if( resType == "3" ){
              //DB2
						  contentHtml +=
							  '<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>decimal</option>\n' +
							  '<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar</option>\n' +
							  '<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>timestamp</option>\n' +
							  '<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>clob</option>\n' +
							  '<option value="5" '+ ( fieldType == "5" ? 'selected' : '' ) +'>blob</option>\n' +
							  '<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>int</option>\n' +
							  '<option value="7" '+ ( fieldType == "7" ? 'selected' : '' ) +'>bigint</option>\n' +
							  '<option value="9" '+ ( fieldType == "9" ? 'selected' : '' ) +'>boolean</option>\n' +
							  '<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
            }
            else if( resType == "5" ){
              //sqlserver
						  contentHtml +=
							  '<option value="1" '+ ( fieldType == "1" ? 'selected' : '' ) +'>float</option>\n' +
							  '<option value="2" '+ ( fieldType == "2" ? 'selected' : '' ) +'>varchar</option>\n' +
							  '<option value="3" '+ ( fieldType == "3" ? 'selected' : '' ) +'>datetime</option>\n' +
							  '<option value="4" '+ ( fieldType == "4" ? 'selected' : '' ) +'>nvarchar(max)</option>\n' +
							  '<option value="5" '+ ( fieldType == "5" ? 'selected' : '' ) +'>varbinary(max)</option>\n' +
							  '<option value="6" '+ ( fieldType == "6" ? 'selected' : '' ) +'>int</option>\n' +
							  '<option value="7" '+ ( fieldType == "7" ? 'selected' : '' ) +'>bigint</option>\n' +
							  '<option value="9" '+ ( fieldType == "9" ? 'selected' : '' ) +'>bit</option>\n' +
							  '<option value="10" '+ ( fieldType == "10" ? 'selected' : '' ) +'>其它</option>\n' ;
            }

					  contentHtml +=
						  '                                            </select>\n' +
						  '                                        </div>\n' +
						  '                                    </div>\n' +
						  '                                </form>\n' +
						  '                            </td>\n' +
						  '                            <td class="" >\n' +
						  '                                <span class="span-select">'+ ( sqlFieldes[i].fieldLen != null ? sqlFieldes[i].fieldLen : "" )  +'</span>' +
						  '                                <form class="layui-form" action="" style="display: none;">\n' +
						  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
						  '                                            <input type="text" name="fieldLen" autocomplete="off" class="layui-input"  value="'+ ( sqlFieldes[i].fieldLen != null ? sqlFieldes[i].fieldLen : "" ) +'">\n' +
						  '                                        </div>\n' +
						  '                                    </div>\n' +
						  '                                </form>\n' +
						  '                            </td>\n' +
						  '                            <td class="" >\n' +
						  '                                <span class="span-select">'+ ( sqlFieldes[i].fieldDigit != null ? sqlFieldes[i].fieldDigit : "" ) +'</span>' +
						  '                                <form class="layui-form" action="" style="display: none;">\n' +
						  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
						  '                                            <input type="text" name="fieldDigit" autocomplete="off" class="layui-input" value="'+ ( sqlFieldes[i].fieldDigit != null ? sqlFieldes[i].fieldDigit : "" ) +'">\n' +
						  '                                        </div>\n' +
						  '                                    </div>\n' +
						  '                                </form>\n' +
						  '                            </td>\n' ;


					  contentHtml +=
						  '                           <td class="" >\n' +
						  '                              <form class="layui-form">\n' +
						  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
						  '                                         <input class="FieldKey" lay-filter="FieldKey" type="checkbox" name="FieldKey" value="1" lay-skin="primary" ' + ( sqlFieldes[i].fieldKey == "1" ? 'checked' : '' ) + '  '+ ( sqlFieldes[i].fieldKey == "0" ?  isHaveFieldKey ? 'disabled' : '' : '' ) +'>\n' +
						  '                                     </div>\n' +
						  '                                 </div>' +
						  '                              </form>' +
						  '                            </td>\n' ;

					  contentHtml +=
						  '                            <td class="" >\n' +
						  '                              <form class="layui-form">\n' +
						  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
						  '                                         <input class="sortIndex" lay-filter="sortIndex" type="checkbox" name="sortIndex" value="1" lay-skin="primary" ' + ( sqlFieldes[i].sortIndex == "1" ? 'checked' : '' ) + '>\n' +
						  '                                     </div>\n' +
						  '                                 </div>' +
						  '                              </form>' +
						  '                            </td>\n' ;

					  contentHtml +=
						  '                           <td class="" >\n' +
						  '                              <form class="layui-form">\n' +
						  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
						  '                                         <input class="FieldKey" lay-filter="isNull" type="checkbox" name="isNull" value="1" lay-skin="primary" ' + ( sqlFieldes[i]['isNull'] == "0" ? 'checked' : '' ) +'>\n' +
						  '                                     </div>\n' +
						  '                                 </div>' +
						  '                              </form>' +
						  '                            </td>\n' ;

					  contentHtml +=
						  '                            <td class="" >\n' +
						  '                                <span class="span-select">'+ ( sqlFieldes[i].fieldResume == null ? '' : sqlFieldes[i].fieldResume == null ) +'</span>' +
						  '                                <form class="layui-form" action="" style="display: none;">\n' +
						  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
						  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
						  '                                            <input type="text" name="fieldResume" class="layui-input" autocomplete="off" value="'+ ( sqlFieldes[i].fieldResume == null ? '' : sqlFieldes[i].fieldResume )  +'">\n' +
						  '                                        </div>\n' +
						  '                                    </div>\n' +
						  '                                </form>\n' +
						  '                            </td>\n' +
						  '                        </tr>';
				  }

				  contentHtml +=
					  '                    </tbody>\n' +
					  '                </table>' +
					  '                </div>\n' +
					  '           </div>\n' +
					  '        </div>\n' +
					  '    </div>\n' ;

				  if( result.tableTypeId == $("#tableTypeId").val() ){
					  contentHtml +=
						  '  <div style="position: absolute;right: 30px;bottom: 30px;"><button class="layui-btn layui-btn-primary layui-btn-sm" id="cancle_'+ uuid +'">取消</button><button class="layui-btn layui-btn-normal layui-btn-sm" id="save_'+ uuid +'">确认</button></div>' ;
          }

				  contentHtml +=
					  '</div>';
				  var areaHeight = $(window).height() * 0.8;
				  var layerIndex = layer.open({
					  type: 1,
					  shade: 0,
					  maxmin: true,
					  offset: 't',
					  area: ["1000px", areaHeight ],
					  skin: 'myLayerOpnSkin',
					  title: '数据源类型管理',
					  content: contentHtml
				  });

				  layui.form.render('select');
				  layui.form.render('checkbox');

				  var offX = offsetX;
				  var offY = offsetY;

				  if( offsetX == null ) {
					  offX = result.tableX;
				  }

				  if( offsetY == null ) {
					  offY = result.tableY;
				  }

				  $("#save_" + uuid).click(function () {
					  dataModalSave( uuid, tableId, layerIndex, offX, offY, boxId, saveType );
				  });

				  $("#cancle_" + uuid).click(function () {
					  layer.close(layerIndex);
				  });

				  spanSelectBindClick();
				  selectBindChange();
			  }
		  });
	  }

	  /*获取双击盒子后显示的内容*/
	  function getContentHtml( id ) {

		  var html =
        '<div style="padding: 0 30px 0 15px;height:100%;" id="jspanel_content_'+ id +'">\n' +
			  '    <div class="layui-tab layui-tab-brief" lay-filter="test1" style="margin:0;">\n' +
			  '        <ul class="layui-tab-title">\n' +
			  '            <li class="title-basic layui-this">基本</li>\n' +
			  '            <li class="title-column">列</li>\n' +
			  '            <li class="title-column">设置</li>\n' +
			  '        </ul>\n' +
			  '        <div class="layui-tab-content">\n' +
			  '            <div class="layui-tab-item content-basic layui-show">\n' +
			  '                <form class="layui-form" action="">\n' +
			  '                    <div class="layui-form-item">\n' +
			  '                        <label class="layui-form-label">表名</label>\n' +
			  '                        <div class="layui-input-block">\n' +
			  '                            <input type="text" name="tableName" autocomplete="off" class="layui-input">\n' +
			  '                        </div>\n' +
			  '                    </div>\n' +
			  '                    <div class="layui-form-item">\n' +
			  '                        <label class="layui-form-label">表英文名</label>\n' +
			  '                        <div class="layui-input-block">\n' +
			  '                            <input type="text" id="tableSqlName_'+id+'" name="tableSqlName" autocomplete="off" class="layui-input">\n' +
			  '                        </div>\n' +
			  '                    </div>\n' +
			  '                    <div class="layui-form-item layui-form-text">\n' +
			  '                        <label class="layui-form-label">注释</label>\n' +
			  '                        <div class="layui-input-block">\n' +
			  '                            <textarea name="tableResume" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
			  '                        </div>\n' +
			  '                    </div>\n' +
			  '                    <div class="layui-form-item layui-form-text">\n' +
			  '                        <label class="layui-form-label">序号</label>\n' +
			  '                        <div class="layui-input-block">\n' +
			  '                            <input name="sortId" autocomplete="off" class="layui-input" />\n' +
			  '                        </div>\n' +
			  '                    </div>\n' +
			  '                </form>\n' +
			  '            </div>\n' +
			  '            <div class="layui-tab-item content-column">\n' +
			  '                <div class="new-line-container">\n' +
			  '                    <span id="column-add" forTable="'+id+'" class="fa fa-plus-square line-tool-add" aria-hidden="true" onClick="addTableColumn(event)"></span>\n' +
			  '                    <span id="column-delete" forTable="'+id+'" class="fa fa-minus-square line-tool-add" aria-hidden="true" onClick="delteTableColumn(event)"></span>' +
			  '                </div>\n' +
			  '                <div style="min-height: 70%;max-height: 70%;overflow: auto;">' +
			  '                <table class="power-inner-table layui-table jpanel-inner-table" id="'+ id +'">\n' +
			  '                 <colgroup>\n' +
			  '                     <col width="50">\n' +
			  '                     <col width="50">\n' +
			  '                     <col width="120">\n' +
			  '                     <col width="120">\n' +
			  '                     <col width="100">\n' +
			  '                     <col width="100">\n' +
			  '                     <col width="100">\n' +
			  '                     <col width="50">\n' +
			  '                     <col width="50">\n' +
			  '                     <col width="50">\n' +
			  '                     <col>\n' +
			  '                 </colgroup>' +
			  '                    <thead>\n' +
			  '                        <tr>\n' +
			  '                            <th>' +
			  '                            <form class="layui-form column-checkBox">\n' +
			  '                              <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                 <div class="layui-input-block" style="margin: 0;min-height: 0px;">\n' +
			  '                                     <input type="checkbox" name="" lay-skin="primary" lay-filter="columnCheckBox">\n' +
			  '                                 </div>\n' +
			  '                              </div>' +
			  '                            </form>' +
			  '                            </th>\n' +
			  '                            <th>编号</th>\n' +
			  '                            <th>名称</th>\n' +
			  '                            <th>英文名</th>\n' +
			  '                            <th>类型</th>\n' +
			  '                            <th>长度</th>\n' +
			  '                            <th>精度</th>\n' +
			  '                            <th>主键</th>\n' +
			  '                            <th>索引</th>\n' +
			  '                            <th>非空</th>\n' +
			  '                            <th>说明</th>\n' +
			  '                        </tr>\n' +
			  '                    </thead>\n' +
			  '                    <tbody>\n' +
			  '\n' +
			  '                    </tbody>\n' +
			  '                </table>' +
			  '              </div>\n' +
			  '            </div>\n' +
			  '            <div class="layui-tab-item">\n' +
        '              <div style="min-height: 70%;max-height: 70%;overflow: auto;">'+
			  '                <form class="layui-form" action="">\n' +
        '                  <div class="mine-row">'+
			  '                    <div class="layui-form-item">\n' +
			  '                        <label class="layui-form-label">操作类型</label>\n' +
        '                        <div class="layui-input-block">\n' +
			  '                          <select name="shapeType" id="shapeType_'+id+'" lay-filter="operation">\n'+
			  '                              <option ></option>\n' +
			  '                              <option value="1">表分区</option>\n' +
			  '                              <option value="2">分表</option>\n' +
			  '                              <option value="3">分库</option>\n' +
			  '                          </select>'+
        '                        </div>' +
        '                    </div>\n' +
        '                  </div>'+
        '                  <div id="operation_'+ id +'">'+
        '                  </div>'+
			  '                </form>\n' +
        '              </div>'+
        '            </div>' +
			  '        </div>\n' +
			  '    </div>\n' +
			  '  <div style="position: absolute;right: 30px;bottom: 30px;"><button class="layui-btn layui-btn-primary layui-btn-sm" id="cancle_'+ id +'">取消</button><button class="layui-btn layui-btn-normal layui-btn-sm" id="save_'+ id +'">确认</button></div>' +
			  '</div>';
		  return html;
	  }

	  //盒子保存之后的回调函数
	  function callBackReBuildBox( result , boxId ) {
		  var powerBox = $("#" + boxId );
		  var boxUl = powerBox.find(".box-ul-list");
		  var resType = $("#resType").val();
		  powerBox.find("input[name='tableId']").val(result.sqlTable.tableId);
		  powerBox.find(".class-box-title").text(result.sqlTable.tableName);
		  powerBox.attr("id", result.sqlTable.tableId);
		  powerBox.attr("table-id", result.sqlTable.tableId);
		  boxUl.empty();

		  var sqlFieldes = result.sqlTable.sqlFieldes;

		  //回显保存的表里的字段
		  for (var i = 0; i < sqlFieldes.length; i++) {
			  var fieldTypeName = '';


			  if( resType == "1" ){
				  //mysql
				  switch (sqlFieldes[i].fieldType) {
					  case "1":
						  fieldTypeName = "float";
						  break;
					  case "2":
						  fieldTypeName = "varchar";
						  break;
					  case "3":
						  fieldTypeName = "datetime";
						  break;
					  case "4":
						  fieldTypeName = "longtext";
						  break;
					  case "5":
						  fieldTypeName = "blob";
						  break;
					  case "6":
						  fieldTypeName = "int";
						  break;
					  case "7":
						  fieldTypeName = "bigint";
						  break;
					  case "9":
						  fieldTypeName = "tinyint";
						  break;
					  case "10":
						  fieldTypeName = "其它";
						  break;
					  default:
						  fieldTypeName = "";
				  }
			  } else if( resType == "2" ) {
				  //oracle
				  switch (sqlFieldes[i].fieldType) {
					  case "1":
						  fieldTypeName = "float";
						  break;
					  case "2":
						  fieldTypeName = "varchar2";
						  break;
					  case "3":
						  fieldTypeName = "date";
						  break;
					  case "4":
						  fieldTypeName = "blob";
						  break;
					  case "6":
						  fieldTypeName = "number";
						  break;
					  case "10":
						  fieldTypeName = "其它";
						  break;
					  default:
						  fieldTypeName = "";
				  }
			  } else if(  resType == "3" ) {
				  //db2
				  switch (sqlFieldes[i].fieldType) {
					  case "1":
						  fieldTypeName = "decimal";
						  break;
					  case "2":
						  fieldTypeName = "varchar";
						  break;
					  case "3":
						  fieldTypeName = "timestamp";
						  break;
					  case "4":
						  fieldTypeName = "clob";
						  break;
					  case "5":
						  fieldTypeName = "blob";
						  break;
					  case "6":
						  fieldTypeName = "int";
						  break;
					  case "7":
						  fieldTypeName = "bigint";
						  break;
					  case "9":
						  fieldTypeName = "boolean";
						  break;
					  case "10":
						  fieldTypeName = "其它";
						  break;
					  default:
						  fieldTypeName = "";
				  }
			  } else if( resType == "5" ) {
				  //sqlserver
				  switch (sqlFieldes[i].fieldType) {
					  case "1":
						  fieldTypeName = "float";
						  break;
					  case "2":
						  fieldTypeName = "varchar";
						  break;
					  case "3":
						  fieldTypeName = "datetime";
						  break;
					  case "4":
						  fieldTypeName = "nvarchar(max)";
						  break;
					  case "5":
						  fieldTypeName = "varbinary(max)";
						  break;
					  case "6":
						  fieldTypeName = "int";
						  break;
					  case "7":
						  fieldTypeName = "bigint";
						  break;
					  case "9":
						  fieldTypeName = "bit";
						  break;
					  case "10":
						  fieldTypeName = "其它";
						  break;
					  default:
						  fieldTypeName = "";
				  }
			  }

			  if (sqlFieldes[i].fieldKey == "1") {
				  boxUl.append('<li>\n' +
					  '    <span class="box-li-item" style="width: 15px;">\n' +
					  '        <div class="box-item-circle left-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
					  '    </span>\n' +
					  '    <span class="box-li-item" style="width: 40px; text-align: center;"><sapn class="fa fa-key primary-key" aria-hidden="true"></sapn></span>\n' +
					  '    <span class="box-li-item box-li-name" style="width: 130px;">' + sqlFieldes[i].fieldSqlName + '</span>\n' +
					  '    <span class="box-li-item box-li-type" style="width: 50px;">' + fieldTypeName + '</span>\n' +
					  '    <span class="box-li-item" style="width: 15px;">\n' +
					  '        <div class="box-item-circle right-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
					  '    </span>\n' +
					  '</li>');
			  } else {
				  boxUl.append('<li>\n' +
					  '    <span class="box-li-item" style="width: 15px;">\n' +
					  '        <div class="box-item-circle left-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
					  '    </span>\n' +
					  '    <span class="box-li-item" style="width: 40px; text-align: center;"></span>\n' +
					  '    <span class="box-li-item box-li-name" style="width: 130px;">' + sqlFieldes[i].fieldSqlName + '</span>\n' +
					  '    <span class="box-li-item box-li-type" style="width: 50px;">' + fieldTypeName + '</span>\n' +
					  '    <span class="box-li-item" style="width: 15px;">\n' +
					  '        <div class="box-item-circle right-circle" onmousedown="circleMousedown(this,event)"></div>\n' +
					  '    </span>\n' +
					  '</li>');
			  }
		  }

		  //将返回的表添加到左侧菜单
		  var submenu = $(".submenu");
		  var submenuHtml = '';
		  submenuHtml += '<li>\n' +
			  '                                <a href="#">\n' +
			  '                                    <span class="fa fa-building sub-icon" aria-hidden="true"></span>\n' +
			  '                                    <span class="sub-name">'+ result.sqlTable.tableName +'</span>\n' +
			  '                                    <span class="sub-delete">删除</span>\n' +
			  '                                    <input hidden="hidden" type="text" value="'+result.sqlTable.tableId+'">\n' +
			  '                                </a>\n' +
			  '                            </li>';
		  submenu.append(submenuHtml);
	  }

	  //表内容的保存
	  function dataModalSave(uuid, tableTypeId, layerIndex, offsetX, offsetY, boxId, saveType) {

		  var content = $("#jspanel_content_" + uuid);

		  var titleBasic = content.find(".title-basic");
		  var titleColumn = content.find(".title-column");
		  var contentBasic = content.find(".content-basic");
		  var contentColumn = content.find(".content-column");
		  var resType = $("#resType").val();
		  var tableTypeId = $("#tableTypeId").val();
		  var tableNameInput = content.find("input[name='tableName']");
		  var tableName = tableNameInput.val();
		  var tableSqlNameInput = content.find("input[name='tableSqlName']");
		  var tableSqlName = tableSqlNameInput.val();
		  var dataResId = $("#dataResId").val();
		  var tableResume = content.find("textarea[name='tableResume']").val();
		  var sortId = content.find("input[name='sortId']").val();
		  var tableY = offsetY;
		  var tableX = offsetX;
		  var returnData = null;
		  if( saveType == "edit" || saveType == null ) {
			  tableTypeId = content.find("input[name='tableTypeId']").val();
			  dataResId = content.find("input[name='dataResId']").val();
		  }

		  if( tableName == "" ){
			  titleBasic.addClass("layui-this");
			  titleColumn.removeClass("layui-this");
			  contentBasic.addClass("layui-show");
			  contentColumn.removeClass("layui-show");
			  layer.tips('表名不能为空！', tableNameInput, {tips: 2});
			  return;
      }

		  if( tableSqlName == "" ){
			  titleBasic.addClass("layui-this");
			  titleColumn.removeClass("layui-this");
			  contentBasic.addClass("layui-show");
			  contentColumn.removeClass("layui-show");
			  layer.tips('表英文名不能为空！', tableSqlNameInput, {tips: 2});
			  return;
		  }

		  if( saveType == "edit" || saveType == null ){
			  if( isInArray( tableSqlName, tableNames, 'edit' ) ){
				  titleBasic.addClass("layui-this");
				  titleColumn.removeClass("layui-this");
				  contentBasic.addClass("layui-show");
				  contentColumn.removeClass("layui-show");
				  layer.tips('表英文名已存在！', tableSqlNameInput, {tips: 2});
				  return;
			  }
      } else {
			  if( isInArray( tableSqlName, tableNames ) ){
				  titleBasic.addClass("layui-this");
				  titleColumn.removeClass("layui-this");
				  contentBasic.addClass("layui-show");
				  contentColumn.removeClass("layui-show");
				  layer.tips('表英文名已存在！', tableSqlNameInput, {tips: 2});
				  return;
			  }
      }


		  var subData = {
			  "tableName": tableName,
			  "tableSqlName": tableSqlName,
			  "dataResId": dataResId,
			  "tableTypeId": tableTypeId,
			  "tableResume": tableResume,
			  "tableY": tableY,
			  "tableX": tableX,
			  "sortId": sortId
		  };


		  if( saveType == "edit" || saveType == null ) {
			  subData["tableId"] = content.find("input[name='tableId']").val();
		  }

		  //获取表字段
		  var tr = content.find("table > tbody > tr");
		  var sqlFieldes = [];
		  for (var i = 0; i < tr.length; i++) {

		  	var fieldSqlNameInput = $(tr[i]).find("input[name='fieldSqlName']");
		  	var fieldNameInput = $(tr[i]).find("input[name='fieldName']");

			  var fieldName = fieldNameInput.val();
			  var fieldSqlName = fieldSqlNameInput.val();
			  var fieldType = $(tr[i]).find("select[name='fieldType']").val();
			  var dateType = $($(tr[i]).find("select[name='fieldType']").parents("td")[0]).find(".span-select").text();
			  var fieldLen = $(tr[i]).find("input[name='fieldLen']").val();
			  var fieldDigit = $(tr[i]).find("input[name='fieldDigit']").val();
			  var FieldKey = $(tr[i]).find("input[name='FieldKey']").is(':checked') ? "1" : "0";
			  var sortIndex = $(tr[i]).find("input[name='sortIndex']").is(':checked') ? "1" : "0";
			  var isNull = $(tr[i]).find("input[name='isNull']").is(':checked') ? "0" : "1";
			  var fieldResume = $(tr[i]).find("input[name='fieldResume']").val();

			  if( fieldName == "" ){
			  	var fieldNameForm = fieldNameInput.parent().parent().parent();
				  fieldNameForm.css("display","block");
				  fieldNameForm.parent().find(".span-select").css("display","none");
				  layer.tips('字段名称不能为空！', fieldNameInput, {tips: 2});
				  return;
        }

        if( fieldSqlName == "" ){
	        var fieldSqlNameForm = fieldSqlNameInput.parent().parent().parent();
	        fieldSqlNameForm.css("display","block");
	        fieldSqlNameForm.parent().find(".span-select").css("display","none");
	        layer.tips('字段英文名称不能为空！', fieldSqlNameInput, {tips: 2});
	        return;
        }

			  var patt = /^[0-9A-Z]{1,}$/;
			  var testResult = patt.test(fieldSqlName);
			  if( $("#resType").val() == "2" || $("#resType").val() == "3" ) {
          if( !testResult ){
	          layer.tips('Oracle或DB2类型的数据源表字段只能由大写字母、数字组成！', fieldSqlNameInput, {tips: 1});
	          return;
          }
			  }


			  if( resType == "1" ){
				  //mysql
				  switch (fieldType) {
					  case "1":
						  dateType = "float";
						  break;
					  case "2":
						  dateType = "varchar";
						  break;
					  case "3":
						  dateType = "datetime";
						  break;
					  case "4":
						  dateType = "longtext";
						  break;
					  case "5":
						  dateType = "blob";
						  break;
					  case "6":
						  dateType = "int";
						  break;
					  case "7":
						  dateType = "bigint";
						  break;
					  case "9":
						  dateType = "tinyint";
						  break;
					  case "10":
						  dateType = "其它";
						  break;
					  default:
						  dateType = "";
				  }
			  }
			  else if( resType == "2" ) {
				  //oracle
				  switch (fieldType) {
					  case "1":
						  dateType = "float";
						  break;
					  case "2":
						  dateType = "varchar2";
						  break;
					  case "3":
						  dateType = "date";
						  break;
					  case "4":
						  dateType = "blob";
						  break;
					  case "6":
						  dateType = "number";
						  break;
					  case "10":
						  dateType = "其它";
						  break;
					  default:
						  dateType = "";
				  }
			  }
			  else if(  resType == "3" ) {
				  //db2
				  switch (fieldType) {
					  case "1":
						  dateType = "decimal";
						  break;
					  case "2":
						  dateType = "varchar";
						  break;
					  case "3":
						  dateType = "timestamp";
						  break;
					  case "4":
						  dateType = "clob";
						  break;
					  case "5":
						  dateType = "blob";
						  break;
					  case "6":
						  dateType = "int";
						  break;
					  case "7":
						  dateType = "bigint";
						  break;
					  case "9":
						  dateType = "boolean";
						  break;
					  case "10":
						  dateType = "其它";
						  break;
					  default:
						  dateType = "";
				  }
			  }
			  else if( resType == "5" ) {
				  //sqlserver
				  switch (fieldType) {
					  case "1":
						  dateType = "float";
						  break;
					  case "2":
						  dateType = "varchar";
						  break;
					  case "3":
						  dateType = "datetime";
						  break;
					  case "4":
						  dateType = "nvarchar(max)";
						  break;
					  case "5":
						  dateType = "varbinary(max)";
						  break;
					  case "6":
						  dateType = "int";
						  break;
					  case "7":
						  dateType = "bigint";
						  break;
					  case "9":
						  dateType = "bit";
						  break;
					  case "10":
						  dateType = "其它";
						  break;
					  default:
						  dateType = "";
				  }
			  }

			  subData["sqlFieldes[" + i + "].fieldName"] = fieldName;
			  subData["sqlFieldes[" + i + "].fieldSqlName"] = fieldSqlName;
			  subData["sqlFieldes[" + i + "].fieldType"] = fieldType;
			  subData["sqlFieldes[" + i + "].dateType"] = dateType;
			  subData["sqlFieldes[" + i + "].fieldLen"] = fieldLen;
			  subData["sqlFieldes[" + i + "].fieldDigit"] = fieldDigit;
			  subData["sqlFieldes[" + i + "].FieldKey"] = FieldKey;
			  subData["sqlFieldes[" + i + "].sortIndex"] = sortIndex;
			  subData["sqlFieldes[" + i + "].isNull"] = isNull;
			  subData["sqlFieldes[" + i + "].fieldResume"] = fieldResume;

			  if( saveType == "edit" || saveType == null ) {
				  subData["sqlFieldes[" + i + "].fieldId"] = $(tr[i]).find("input[name='fieldId']").val();
				  subData["sqlFieldes[" + i + "].tableId"] = content.find("input[name='tableId']").val();
			  }
		  }

		  //封装表的设置内容
      var SqlShapes = [];
		  var shapeTypeSelect = $("#shapeType_"+uuid); //操作类型select


      if( shapeTypeSelect.val() == "" ){

      }
      else if( shapeTypeSelect.val() == "1" ){
      	//分区
	      subData["sqlShape[0].shapeType"] = shapeTypeSelect.val();
	      subData["sqlShape[0].state"] = $("#operation_"+uuid).find("input[name='state']").val();
	      subData["sqlShape[0].partitionValue"] = $("#partitionValue_"+uuid).val();
      }
      else if( shapeTypeSelect.val() == "2" ){
        //分表
	      subData["sqlShape[0].shapeType"] = shapeTypeSelect.val();

	      var fieldSqlName = $("#fields_"+uuid).val();
	      var subTableType = $("#types_"+uuid).val();
	      subData["sqlShape[0].sqlSubTable.subTableType"] = subTableType;
	      subData["sqlShape[0].sqlSubTable.fieldSqlName"] = fieldSqlName;
	      if( subTableType == "1" ){
          //date
		      subData["sqlShape[0].sqlSubTable.subTableTypeValue1"] = $("#dates_"+uuid).val();
        }else if( subTableType == "2" ){
          //string
		      subData["sqlShape[0].sqlSubTable.subTableTypeValue1"] = $("#stringHtml_"+uuid).find("input[name='subTableTypeValue1']").val();
		      subData["sqlShape[0].sqlSubTable.subTableTypeValue2"] = $("#stringHtml_"+uuid).find("input[name='subTableTypeValue2']").val();
        }else if( subTableType == "3" ){
          //数值
		      subData["sqlShape[0].sqlSubTable.subTableTypeValue1"] = $("#numHtml_"+uuid).find("input[name='subTableTypeValue1']").val()
        }
      }
      else if( shapeTypeSelect.val() == "3" ){
        //分库
	      subData["sqlShape[0].shapeType"] = shapeTypeSelect.val();

	      var sqlSubTreasury = [];

	      var subTableType = $("#types_"+uuid).val();
	      subData["sqlShape[0].sqlSubTreasury.subTreasuryType"] = subTableType;
	      subData["sqlShape[0].sqlSubTable.fieldSqlName"] = fieldSqlName;
	      if( subTableType == "1" ){
		      //date
		      subData["sqlShape[0].sqlSubTreasury.typeValue1"] = $("#dates_"+uuid).val();
	      }else if( subTableType == "2" ){
		      //string
		      subData["sqlShape[0].sqlSubTreasury.typeValue1"] = $("#stringHtml_"+uuid).find("input[name='subTableTypeValue1']").val();
		      subData["sqlShape[0].sqlSubTreasury.typeValue2"] = $("#stringHtml_"+uuid).find("input[name='subTableTypeValue2']").val();
	      }else if( subTableType == "3" ){
		      //数值

	      }

	      var formSelects = layui.formSelects;
	      sqlSubTreasury = formSelects.value('select1', 'vals');

	      for( var p = 0; p < sqlSubTreasury.length; p++ ){
		      if(subTableType == "3"){
			      subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].typeValue1"] = $("#" + sqlSubTreasury[p].name + "_" + sqlSubTreasury[p].name ).find("input[name='typeValue1']").val();
			      subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].typeValue2"] = $("#" + sqlSubTreasury[p].name + "_" + sqlSubTreasury[p].name ).find("input[name='typeValue2']").val();
		      } else {
			      subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].typeValue1"] = $("#" + sqlSubTreasury[p].name + "_" + sqlSubTreasury[p].name ).find("input").val();
		      }
		      subData["sqlShape[0].sqlSubTreasury.SqlSubTreasuryDivision["+p+"].dataResId"] = sqlSubTreasury[p].val;
	      }
      }

		  $.ajax({
			  url: '<%=path%>/v1/sqlModel/saveSqlModel',
			  data: subData,
			  success: function (result) {

				  if ( result.code == "000000" ) {
					  layer.close(layerIndex);
					  var zTree = $.fn.zTree.getZTreeObj("curTree");
					  var node = null;
					  if( saveType == "edit" || saveType == "save" ) {
						  callBackReBuildBox(result,boxId);

						  if( saveType == "save" ) {
							  node = zTree.getNodeByParam( "id", result.sqlTable.tableTypeId );
							  zTree.addNodes( node, { id: result.sqlTable.tableId, name: result.sqlTable.tableName, pId: result.sqlTable.tableTypeId, dataType: 'table', iconSkin: 'table', open:true  } );
						  } else if( saveType == "edit" ) {
							  node = zTree.getNodeByParam("id", result.sqlTable.tableId);
							  node.name = result.sqlTable.tableName;
							  zTree.updateNode(node);
						  }
					  } else if( saveType == null ){
						  var zTree = $.fn.zTree.getZTreeObj("curTree");
						  node = zTree.getNodeByParam("id", result.sqlTable.tableId);
						  node.name = result.sqlTable.tableName;
						  zTree.updateNode(node);
						  if( result.sqlTable.tableTypeId == $("#tableTypeId").val() ) {
							  callBackReBuildBox( result, result.sqlTable.tableId );
						  }
					  }
				  } else if( result.code == "999999" ) {
					  layer.msg('当前表含有关联字段！请先删除关联外键', {icon: 2});
          }
			  }
		  });
	  }

	  //每行的两边的点击事件
	  function circleMousedown(obj, e) {
		  var curCircle = obj;
		  var e = e || window.event;

		  var outerParent = $(curCircle).parent().parent().parent().parent();
		  var curLi = $(curCircle).parent().parent();
		  var fromTableId = outerParent.attr("id");
		  var fromField = curLi.find(".box-li-name").text().trim();

		  var fromX = 0;
		  var fromY = 0;

		  //判断点的是左边还是右边
		  if( $(curCircle).hasClass("left-circle") ) {
			  fromX = outerParent[0].offsetLeft;
			  fromY = outerParent[0].offsetTop + curLi[0].offsetTop + curLi.height()/2 + 1.5;
		  } else if( $(curCircle).hasClass("right-circle") ) {
			  fromX = outerParent[0].offsetLeft + outerParent.width() + 3;
			  fromY = outerParent[0].offsetTop + curLi[0].offsetTop + curLi.height()/2 + 1.5;
		  }

		  $(".power-canvas-container").unbind('mousedown');
		  $(".power-canvas-container").unbind('mouseup');
		  $(".power-tool-item").removeClass("tool-item-active");
		  $("#container").css("cursor", "default");

		  var curLine = null;
		  var lineId = getuuid(16,16);

		  $("#container").append(
			  "<div id='"+ lineId +"' class='line_box_container' style='position: absolute;'>" +
			  "<canvas></canvas>" +
			  "</div>"
		  );

		  var lineContainerLeft = 0; //线container左边距
		  var lineContainerTop = 0; //线container上边距
		  var lineContainerWidth = 0; //线container左边距
		  var lineContainerHeight = 0; //线container左边距

		  $(".power-canvas-container").mousemove(function (e) {
			  var e = e || window.event;
			  $("#container").css("cursor", "crosshair");

			  var toX = e.clientX - $("#power-container")[0].offsetLeft + $("#power-container")[0].scrollLeft;
			  var toY = e.clientY - $(".power-main")[0].offsetTop + $("#power-container")[0].scrollTop;

			  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

			  if( $(e.target).hasClass("box-item-circle") ) {
				  var toOuterParent = $(e.target).parent().parent().parent().parent();
				  var toLi = $(e.target).parent().parent();

				  if( $(curCircle).hasClass("left-circle") ) {
					  //起点为左边的点

					  if( $(e.target).hasClass("left-circle") ) {
						  toX = toOuterParent[0].offsetLeft;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  drawSameLeftArrow(
							  ctx1,
							  fromX,
							  fromY,
							  toX,
							  toY,
							  30,
							  10,
							  2,
							  '#96d6fc',
							  outerParent[0],
							  toOuterParent[0]
						  );
					  }
					  else if( $(e.target).hasClass("right-circle") ) {
						  toX = toOuterParent[0].offsetLeft + toOuterParent.width() + 3;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  drawLeftArrow(
							  ctx1,
							  fromX,
							  fromY,
							  toX,
							  toY,
							  30,
							  10,
							  2,
							  '#96d6fc',
							  outerParent[0],
							  toOuterParent[0]
						  );
					  }
				  }
				  else if( $(curCircle).hasClass("right-circle") ) {
					  //起点为右边的点

					  if( $(e.target).hasClass("left-circle") ) {
						  toX = toOuterParent[0].offsetLeft;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  drawArrow(
							  ctx1,
							  fromX,
							  fromY,
							  toX,
							  toY,
							  30,
							  10,
							  2,
							  '#96d6fc',
							  outerParent[0],
							  toOuterParent[0]
						  );

					  }
					  else if( $(e.target).hasClass("right-circle") ) {
						  toX = toOuterParent[0].offsetLeft + toOuterParent.width() + 3;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  drawSameRightArrow(
							  ctx1,
							  fromX,
							  fromY,
							  toX,
							  toY,
							  30,
							  10,
							  2,
							  '#96d6fc',
							  outerParent[0],
							  toOuterParent[0]
						  );
					  }
				  }
			  }
			  else {
				  if( $(curCircle).hasClass("left-circle") ) {
					  drawLeftArrow(
						  ctx1,
						  fromX,
						  fromY,
						  toX,
						  toY,
						  30,
						  10,
						  2,
						  '#96d6fc',
						  outerParent[0]
					  );
				  }
				  else {
					  drawArrow(
						  ctx1,
						  fromX,
						  fromY,
						  toX,
						  toY,
						  30,
						  10,
						  2,
						  '#96d6fc',
						  outerParent[0]
					  );
				  }
			  }
		  });

		  $(".power-canvas-container").mouseup(function (e) {
			  $(this).unbind("mousemove");
			  $(this).unbind("mouseup");
			  $("#container").css("cursor", "default");
        var lineUUID = getuuid(16,16);
			  var curLineDetail = {
				  "id": lineUUID,
				  "from": {
					  "x": 0,
					  "y": 0
				  },
				  "to":{
					  "x": 0,
					  "y": 0
				  },
				  "turnPoints":[]
			  };

			  var e = e || window.event;
			  var toCircle = null;

			  var toX = e.clientX - $("#power-container")[0].offsetLeft + $("#power-container")[0].scrollLeft;
			  var toY = e.clientY - $(".power-main")[0].offsetTop + $("#power-container")[0].scrollTop;

			  var toOuterParent = null;
			  var toLi = null;

			  curLine = $("#" + lineId );

			  if( $(e.target).hasClass("box-item-circle") ) {

				  toOuterParent = $(e.target).parent().parent().parent().parent();
				  toLi = $(e.target).parent().parent();
				  toCircle = e.target;

				  if( toLi.find("input[name='fieldKey']").val() == "0" ) {
					  layer.msg('目标字段必须为主键！', {icon: 2});
					  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);
					  return;
				  } else if( toLi.find("input[name='fieldKey']").val() == "1" ) {
            if( toLi.find(".box-li-type").text() != curLi.find(".box-li-type").text() ) {
              layer.msg('关联字段类型与主字段类型必须一致！', {icon: 2});
              ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);
              return;
            }
				  }

				  var toTbaleId = toOuterParent.attr("id");
				  var toField = toLi.find(".box-li-name").text().trim();

				  $(curCircle).addClass("circle-inline");
				  $(toCircle).addClass("circle-inline");

				  if( $(curCircle).hasClass("left-circle") ){

					  if( $(e.target).hasClass("right-circle") ){
						  toX = toOuterParent[0].offsetLeft + toOuterParent.width() + 3 ;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  if( toX - fromX < 0 ){

							  lineContainerWidth = Math.abs( toX - fromX ) + 20;
							  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							  lineContainerLeft = toX - 10;
							  if( toY - fromY > 0 ){
								  //右下
								  lineContainerTop = fromY - 10;
							  }else{
								  //右上
								  lineContainerTop = toY - 10;
							  }
							  curLine[0].style.width = lineContainerWidth + 'px';
							  curLine[0].style.height = lineContainerHeight + 'px';
							  curLine[0].style.left = lineContainerLeft + 'px';
							  curLine[0].style.top = lineContainerTop + 'px';
							  curLine.find("canvas")[0].width = lineContainerWidth;
							  curLine.find("canvas")[0].height = lineContainerHeight;

							  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							  var lineCtx = curLine.find("canvas")[0].getContext("2d");

							  var line1CanvasFromX = fromX - curLine[0].offsetLeft;
							  var line1CanvasFromY = fromY - curLine[0].offsetTop;

							  var line1CanvasToX = toX - curLine[0].offsetLeft;
							  var line1CanvasToY = toY - curLine[0].offsetTop;

							  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							  curLine.attr("fromBoxId", outerParent.attr("table-id") );
							  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							  curLine.attr("fromCircle", "left" );
							  curLine.attr("toCircle", "right" );

							  curLineDetail.from.x = line1CanvasFromX;
							  curLineDetail.from.y = line1CanvasFromY;
							  curLineDetail.to.x = line1CanvasToX;
							  curLineDetail.to.y = line1CanvasToY;

							  drawLeftArrow(
								  lineCtx,
								  line1CanvasFromX,
								  line1CanvasFromY,
								  line1CanvasToX,
								  line1CanvasToY,
								  30,
								  10,
								  2,
								  '#96d6fc',
                  null,
                  null,
                  curLineDetail
							  );
						  } else if ( toX - fromX > 0 ){

							  //终点在起点之下
							  if( toY - fromY > 0 ) {

								  var turn1 = {
									  x: fromX - 30 ,
									  y: fromY
								  };

								  var turn2 = {
									  x: turn1.x,
									  y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() )
								  };

								  var turn3 = {
									  x: toX + 30,
									  y: turn2.y
								  };

								  var turn4 = {
									  x: turn3.x,
									  y: toY
								  };

								  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
								  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
								  lineContainerLeft = turn1.x - 10;
								  lineContainerTop = fromY - 10;

								  curLine[0].style.width = lineContainerWidth + 'px';
								  curLine[0].style.height = lineContainerHeight + 'px';
								  curLine[0].style.left = lineContainerLeft + 'px';
								  curLine[0].style.top = lineContainerTop + 'px';
								  curLine.find("canvas")[0].width = lineContainerWidth;
								  curLine.find("canvas")[0].height = lineContainerHeight;

								  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

								  var lineCtx = curLine.find("canvas")[0].getContext("2d");

								  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
								  curLine.attr("fromBoxId", outerParent.attr("table-id") );
								  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
								  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
								  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
								  curLine.attr("fromCircle", "left" );
								  curLine.attr("toCircle", "right" );

								  var turn11 = {
									  x: fromX - 30 - lineContainerLeft,
									  y: fromY - lineContainerTop
								  };

								  var turn22 = {
									  x: turn1.x - lineContainerLeft,
									  y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() ) - lineContainerTop
								  };

								  var turn33 = {
									  x: toX + 30 - lineContainerLeft,
									  y: turn2.y - lineContainerTop
								  };

								  var turn44 = {
									  x: turn3.x - lineContainerLeft,
									  y: toY - lineContainerTop
								  };

                  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
                  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
                  curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
                  curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

								  curLineDetail.from.x = fromX- lineContainerLeft;
								  curLineDetail.from.y = fromY - lineContainerTop;
								  curLineDetail.to.x = toX - lineContainerLeft;
								  curLineDetail.to.y = toY - lineContainerTop;

								  linesArray.push(curLineDetail);

								  var arr = [];
								  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
								  arr.push({"x":turn11.x, "y": turn11.y});
								  arr.push({"x":turn22.x, "y": turn22.y});
								  arr.push({"x":turn33.x, "y": turn33.y});
								  arr.push({"x":turn44.x, "y": turn44.y});
								  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

								  drawInArrow(
									  lineCtx,
									  30,
									  10,
									  2,
									  '#96d6fc',
									  arr
								  );
							  } else if( toY - fromY < 0 ){
								  //终点位于起点之上
								  var turn1 = {
									  x: fromX - 30 ,
									  y: fromY
								  };

								  var turn2 = {
									  x: turn1.x,
									  y: ( outerParent[0].offsetTop - ( toOuterParent[0].offsetTop + $(toOuterParent).height() ) )/2 + ( toOuterParent[0].offsetTop + $(toOuterParent).height() )
								  };

								  var turn3 = {
									  x: toX + 30,
									  y: turn2.y
								  };

								  var turn4 = {
									  x: turn3.x,
									  y: toY
								  };

								  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
								  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
								  lineContainerLeft = turn1.x - 10;
								  lineContainerTop = toY - 10;

								  curLine[0].style.width = lineContainerWidth + 'px';
								  curLine[0].style.height = lineContainerHeight + 'px';
								  curLine[0].style.left = lineContainerLeft + 'px';
								  curLine[0].style.top = lineContainerTop + 'px';
								  curLine.find("canvas")[0].width = lineContainerWidth;
								  curLine.find("canvas")[0].height = lineContainerHeight;

								  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

								  var lineCtx = curLine.find("canvas")[0].getContext("2d");

								  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
								  curLine.attr("fromBoxId", outerParent.attr("table-id") );
								  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
								  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
								  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
								  curLine.attr("fromCircle", "left" );
								  curLine.attr("toCircle", "right" );

								  var turn11 = {
									  x: fromX - 30 - lineContainerLeft,
									  y: fromY - lineContainerTop
								  };

								  var turn22 = {
									  x: turn1.x - lineContainerLeft,
									  y: turn2.y - lineContainerTop
								  };

								  var turn33 = {
									  x: toX + 30 - lineContainerLeft,
									  y: turn2.y - lineContainerTop
								  };

								  var turn44 = {
									  x: turn3.x - lineContainerLeft,
									  y: toY - lineContainerTop
								  };

								  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
								  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
								  curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
								  curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

								  curLineDetail.from.x = fromX- lineContainerLeft;
								  curLineDetail.from.y = fromY - lineContainerTop;
								  curLineDetail.to.x = toX - lineContainerLeft;
								  curLineDetail.to.y = toY - lineContainerTop;

								  linesArray.push(curLineDetail);

								  var arr = [];
								  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
								  arr.push({"x":turn11.x, "y": turn11.y});
								  arr.push({"x":turn22.x, "y": turn22.y});
								  arr.push({"x":turn33.x, "y": turn33.y});
								  arr.push({"x":turn44.x, "y": turn44.y});
								  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

								  drawInArrow(
									  lineCtx,
									  30,
									  10,
									  2,
									  '#96d6fc',
									  arr
								  );
							  }
						  }
					  }
					  else if( $(e.target).hasClass("left-circle") ) {
						  toX = toOuterParent[0].offsetLeft  ;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  //左连左
						  if( toX - fromX > 0 ) {
							  var turn1 = {
								  x: fromX - 30,
								  y: fromY
							  };
							  var turn2 = {
								  x: turn1.x,
								  y:toY
							  };

							  lineContainerWidth = Math.abs( toX - turn1.x ) + 20;
							  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							  lineContainerLeft = fromX - 10 - 30;
							  if( toY - fromY > 0 ) {
								  //右下
								  lineContainerTop = fromY - 10;
							  } else {
								  //右上
								  lineContainerTop = toY - 10;
							  }
							  curLine[0].style.width = lineContainerWidth + 'px';
							  curLine[0].style.height = lineContainerHeight + 'px';
							  curLine[0].style.left = lineContainerLeft + 'px';
							  curLine[0].style.top = lineContainerTop + 'px';
							  curLine.find("canvas")[0].width = lineContainerWidth;
							  curLine.find("canvas")[0].height = lineContainerHeight;

							  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							  var lineCtx = curLine.find("canvas")[0].getContext("2d");

							  var line1CanvasFromX = fromX - curLine[0].offsetLeft;
							  var line1CanvasFromY = fromY - curLine[0].offsetTop;

							  var line1CanvasToX = toX - curLine[0].offsetLeft;
							  var line1CanvasToY = toY - curLine[0].offsetTop;

							  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							  curLine.attr("fromBoxId", outerParent.attr("table-id") );
							  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							  curLine.attr("fromCircle", "left" );
							  curLine.attr("toCircle", "left" );

							  var turn11 = {
								  x: turn1.x - lineContainerLeft,
								  y: turn1.y - lineContainerTop
							  };

							  var turn22 = {
								  x: turn2.x - lineContainerLeft,
								  y: turn2.y - lineContainerTop
							  };

							  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

							  curLineDetail.from.x = fromX- lineContainerLeft;
							  curLineDetail.from.y = fromY - lineContainerTop;
							  curLineDetail.to.x = toX - lineContainerLeft;
							  curLineDetail.to.y = toY - lineContainerTop;

							  linesArray.push(curLineDetail);

							  var arr = [];
							  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							  arr.push({"x":turn11.x, "y": turn11.y});
							  arr.push({"x":turn22.x, "y": turn22.y});
							  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							  drawInArrow(
								  lineCtx,
								  30,
								  10,
								  2,
								  '#96d6fc',
								  arr
							  );
						  }
						  else if( toX - fromX <= 0 ) {
							  var turn1 = {
								  x: toX - 30,
								  y: fromY
							  };
							  var turn2 = {
								  x: turn1.x,
								  y:toY
							  };

							  lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
							  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							  lineContainerLeft = turn1.x - 10;
							  if( toY - fromY > 0 ) {
								  //右下
								  lineContainerTop = fromY - 10;
							  } else {
								  //右上
								  lineContainerTop = toY - 10;
							  }
							  curLine[0].style.width = lineContainerWidth + 'px';
							  curLine[0].style.height = lineContainerHeight + 'px';
							  curLine[0].style.left = lineContainerLeft + 'px';
							  curLine[0].style.top = lineContainerTop + 'px';
							  curLine.find("canvas")[0].width = lineContainerWidth;
							  curLine.find("canvas")[0].height = lineContainerHeight;

							  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							  var lineCtx = curLine.find("canvas")[0].getContext("2d");

							  var line1CanvasFromX = fromX - curLine[0].offsetLeft;
							  var line1CanvasFromY = fromY - curLine[0].offsetTop;

							  var line1CanvasToX = toX - curLine[0].offsetLeft;
							  var line1CanvasToY = toY - curLine[0].offsetTop;

							  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							  curLine.attr("fromBoxId", outerParent.attr("table-id") );
							  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							  curLine.attr("fromCircle", "left" );
							  curLine.attr("toCircle", "left" );

							  var turn11 = {
								  x: turn1.x - lineContainerLeft,
								  y: turn1.y - lineContainerTop
							  };

							  var turn22 = {
								  x: turn2.x - lineContainerLeft,
								  y: turn2.y - lineContainerTop
							  };

							  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

							  curLineDetail.from.x = fromX- lineContainerLeft;
							  curLineDetail.from.y = fromY - lineContainerTop;
							  curLineDetail.to.x = toX - lineContainerLeft;
							  curLineDetail.to.y = toY - lineContainerTop;

							  linesArray.push(curLineDetail);

							  var arr = [];
							  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							  arr.push({"x":turn11.x, "y": turn11.y});
							  arr.push({"x":turn22.x, "y": turn22.y});
							  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							  drawInArrow(
								  lineCtx,
								  30,
								  10,
								  2,
								  '#96d6fc',
								  arr
							  );
						  }
					  }
				  }
				  else if( $(curCircle).hasClass("right-circle") ) {

					  if( $(e.target).hasClass("left-circle") ) {
						  toX = toOuterParent[0].offsetLeft;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  if( toX - fromX > 0 ) {

							  lineContainerWidth = Math.abs( toX - fromX ) + 20;
							  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							  lineContainerLeft = fromX - 10;
							  if( toY - fromY > 0 ) {
								  //右下
								  lineContainerTop = fromY - 10;
							  } else {
								  //右上
								  lineContainerTop = toY - 10;
							  }
							  curLine[0].style.width = lineContainerWidth + 'px';
							  curLine[0].style.height = lineContainerHeight + 'px';
							  curLine[0].style.left = lineContainerLeft + 'px';
							  curLine[0].style.top = lineContainerTop + 'px';
							  curLine.find("canvas")[0].width = lineContainerWidth;
							  curLine.find("canvas")[0].height = lineContainerHeight;

							  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							  var lineCtx = curLine.find("canvas")[0].getContext("2d");

							  var line1CanvasFromX = fromX - curLine[0].offsetLeft;
							  var line1CanvasFromY = fromY - curLine[0].offsetTop;

							  var line1CanvasToX = toX - curLine[0].offsetLeft;
							  var line1CanvasToY = toY - curLine[0].offsetTop;

							  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							  curLine.attr("fromBoxId", outerParent.attr("table-id") );
							  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							  curLine.attr("fromCircle", "right" );
							  curLine.attr("toCircle", "left" );

							  curLineDetail.from.x = line1CanvasFromX;
							  curLineDetail.from.y = line1CanvasFromY;
							  curLineDetail.to.x = line1CanvasToX;
							  curLineDetail.to.y = line1CanvasToY;

							  drawArrow(
								  lineCtx,
								  line1CanvasFromX,
								  line1CanvasFromY,
								  line1CanvasToX,
								  line1CanvasToY,
								  30,
								  10,
								  2,
								  '#96d6fc',
                  null,
                  null,
								  curLineDetail
							  );
						  } else if ( toX - fromX < 0 ) {

							  //终点在起点之下
							  if( toY - fromY > 0 ) {

								  var turn1 = {
									  x: fromX + 30 ,
									  y: fromY
								  };

								  var turn2 = {
									  x: turn1.x,
									  y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() )
								  };

								  var turn3 = {
									  x: toX - 30,
									  y: turn2.y
								  };

								  var turn4 = {
									  x: turn3.x,
									  y: toY
								  };

								  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
								  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
								  lineContainerLeft = turn3.x - 10;
								  lineContainerTop = fromY - 10;

								  curLine[0].style.width = lineContainerWidth + 'px';
								  curLine[0].style.height = lineContainerHeight + 'px';
								  curLine[0].style.left = lineContainerLeft + 'px';
								  curLine[0].style.top = lineContainerTop + 'px';
								  curLine.find("canvas")[0].width = lineContainerWidth;
								  curLine.find("canvas")[0].height = lineContainerHeight;

								  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

								  var lineCtx = curLine.find("canvas")[0].getContext("2d");

								  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
								  curLine.attr("fromBoxId", outerParent.attr("table-id") );
								  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
								  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
								  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
								  curLine.attr("fromCircle", "right" );
								  curLine.attr("toCircle", "left" );

								  var turn11 = {
									  x: fromX + 30 - lineContainerLeft,
									  y: fromY - lineContainerTop
								  };

								  var turn22 = {
									  x: turn1.x - lineContainerLeft,
									  y: ( toOuterParent[0].offsetTop - ( outerParent[0].offsetTop + $(outerParent).height() ) )/2 + ( outerParent[0].offsetTop + $(outerParent).height() ) - lineContainerTop
								  };

								  var turn33 = {
									  x: toX - 30 - lineContainerLeft,
									  y: turn2.y - lineContainerTop
								  };

								  var turn44 = {
									  x: turn3.x - lineContainerLeft,
									  y: toY - lineContainerTop
								  };

								  curLineDetail.from.x = fromX- lineContainerLeft;
								  curLineDetail.from.y = fromY - lineContainerTop;
								  curLineDetail.to.x = toX - lineContainerLeft;
								  curLineDetail.to.y = toY - lineContainerTop;

								  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
								  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
								  curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
								  curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

								  linesArray.push(curLineDetail);

								  var arr = [];
								  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
								  arr.push({"x":turn11.x, "y": turn11.y});
								  arr.push({"x":turn22.x, "y": turn22.y});
								  arr.push({"x":turn33.x, "y": turn33.y});
								  arr.push({"x":turn44.x, "y": turn44.y});
								  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

								  drawInArrow(
									  lineCtx,
									  30,
									  10,
									  2,
									  '#96d6fc',
									  arr
								  );
							  } else if( toX - fromX < 0 ){
								  //终点位于起点之上
								  var turn1 = {
									  x: fromX + 30 ,
									  y: fromY
								  };

								  var turn2 = {
									  x: turn1.x,
									  y: ( outerParent[0].offsetTop - ( toOuterParent[0].offsetTop + $(toOuterParent).height() ) )/2 + ( toOuterParent[0].offsetTop + $(toOuterParent).height() )
								  };

								  var turn3 = {
									  x: toX - 30,
									  y: turn2.y
								  };

								  var turn4 = {
									  x: turn3.x,
									  y: toY
								  };

								  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
								  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
								  lineContainerLeft = turn3.x - 10;
								  lineContainerTop = toY - 10;

								  curLine[0].style.width = lineContainerWidth + 'px';
								  curLine[0].style.height = lineContainerHeight + 'px';
								  curLine[0].style.left = lineContainerLeft + 'px';
								  curLine[0].style.top = lineContainerTop + 'px';
								  curLine.find("canvas")[0].width = lineContainerWidth;
								  curLine.find("canvas")[0].height = lineContainerHeight;

								  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

								  var lineCtx = curLine.find("canvas")[0].getContext("2d");

								  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
								  curLine.attr("fromBoxId", outerParent.attr("table-id") );
								  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
								  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
								  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
								  curLine.attr("fromCircle", "right" );
								  curLine.attr("toCircle", "left" );

								  var turn11 = {
									  x: fromX + 30 - lineContainerLeft,
									  y: fromY - lineContainerTop
								  };

								  var turn22 = {
									  x: turn1.x - lineContainerLeft,
									  y: turn2.y - lineContainerTop
								  };

								  var turn33 = {
									  x: toX - 30 - lineContainerLeft,
									  y: turn2.y - lineContainerTop
								  };

								  var turn44 = {
									  x: turn3.x - lineContainerLeft,
									  y: toY - lineContainerTop
								  };

								  curLineDetail.from.x = fromX- lineContainerLeft;
								  curLineDetail.from.y = fromY - lineContainerTop;
								  curLineDetail.to.x = toX - lineContainerLeft;
								  curLineDetail.to.y = toY - lineContainerTop;

								  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
								  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});
								  curLineDetail.turnPoints.push({"x":turn33.x, "y":turn33.y});
								  curLineDetail.turnPoints.push({"x":turn44.x, "y":turn44.y});

								  linesArray.push(curLineDetail);

								  var arr = [];
								  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
								  arr.push({"x":turn11.x, "y": turn11.y});
								  arr.push({"x":turn22.x, "y": turn22.y});
								  arr.push({"x":turn33.x, "y": turn33.y});
								  arr.push({"x":turn44.x, "y": turn44.y});
								  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

								  drawInArrow(
									  lineCtx,
									  30,
									  10,
									  2,
									  '#96d6fc',
									  arr
								  );
							  }
						  }
					  } else if( $(e.target).hasClass("right-circle") ) {

						  toX = toOuterParent[0].offsetLeft  + toOuterParent.width() + 3;
						  toY = toOuterParent[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 1.5;

						  //右连右
						  if( toX - fromX > 0 ) {
							  var turn1 = {
								  x: toX + 30,
								  y: fromY
							  };
							  var turn2 = {
								  x: turn1.x,
								  y: toY
							  };

							  lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
							  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							  lineContainerLeft = fromX - 10;
							  if( toY - fromY > 0 ) {
								  //右下
								  lineContainerTop = fromY - 10;
							  } else {
								  //右上
								  lineContainerTop = toY - 10;
							  }
							  curLine[0].style.width = lineContainerWidth + 'px';
							  curLine[0].style.height = lineContainerHeight + 'px';
							  curLine[0].style.left = lineContainerLeft + 'px';
							  curLine[0].style.top = lineContainerTop + 'px';
							  curLine.find("canvas")[0].width = lineContainerWidth;
							  curLine.find("canvas")[0].height = lineContainerHeight;

							  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

							  var lineCtx = curLine.find("canvas")[0].getContext("2d");

							  var line1CanvasFromX = fromX - curLine[0].offsetLeft;
							  var line1CanvasFromY = fromY - curLine[0].offsetTop;

							  var line1CanvasToX = toX - curLine[0].offsetLeft;
							  var line1CanvasToY = toY - curLine[0].offsetTop;

							  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							  curLine.attr("fromBoxId", outerParent.attr("table-id") );
							  curLine.attr("fromField", curLi.find(".box-li-name").text().trim() );
							  curLine.attr("toBoxId", toOuterParent.attr("table-id") );
							  curLine.attr("toField", toLi.find(".box-li-name").text().trim() );
							  curLine.attr("fromCircle", "right" );
							  curLine.attr("toCircle", "right" );

							  var turn11 = {
								  x: turn1.x - lineContainerLeft,
								  y: turn1.y - lineContainerTop
							  };

							  var turn22 = {
								  x: turn2.x - lineContainerLeft,
								  y: turn2.y - lineContainerTop
							  };

							  curLineDetail.from.x = fromX- lineContainerLeft;
							  curLineDetail.from.y = fromY - lineContainerTop;
							  curLineDetail.to.x = toX - lineContainerLeft;
							  curLineDetail.to.y = toY - lineContainerTop;

							  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

							  linesArray.push(curLineDetail);

							  var arr = [];
							  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							  arr.push({"x":turn11.x, "y": turn11.y});
							  arr.push({"x":turn22.x, "y": turn22.y});
							  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							  drawInArrow(
								  lineCtx,
								  30,
								  10,
								  2,
								  '#96d6fc',
								  arr
							  );
						  }
						  else if (toX - fromX <= 0) {
							  var turn1 = {
								  x: fromX + 30,
								  y: fromY
							  };
							  var turn2 = {
								  x: turn1.x,
								  y: toY
							  };

							  lineContainerWidth = Math.abs(turn1.x - toX) + 20;
							  lineContainerHeight = Math.abs(toY - fromY) + 20;
							  lineContainerLeft = toX - 10;
							  if (toY - fromY > 0) {
								  //右下
								  lineContainerTop = fromY - 10;
							  } else {
								  //右上
								  lineContainerTop = toY - 10;
							  }
							  curLine[0].style.width = lineContainerWidth + 'px';
							  curLine[0].style.height = lineContainerHeight + 'px';
							  curLine[0].style.left = lineContainerLeft + 'px';
							  curLine[0].style.top = lineContainerTop + 'px';
							  curLine.find("canvas")[0].width = lineContainerWidth;
							  curLine.find("canvas")[0].height = lineContainerHeight;

							  ctx1.clearRect(0, 0, powerCanvas.width, powerCanvas.height);

							  var lineCtx = curLine.find("canvas")[0].getContext("2d");

							  var line1CanvasFromX = fromX - curLine[0].offsetLeft;
							  var line1CanvasFromY = fromY - curLine[0].offsetTop;

							  var line1CanvasToX = toX - curLine[0].offsetLeft;
							  var line1CanvasToY = toY - curLine[0].offsetTop;

							  //暂时在线div上添加起始表id 起始字段名 终点表id 终点字段
							  curLine.attr("fromBoxId", outerParent.attr("table-id"));
							  curLine.attr("fromField", curLi.find(".box-li-name").text().trim());
							  curLine.attr("toBoxId", toOuterParent.attr("table-id"));
							  curLine.attr("toField", toLi.find(".box-li-name").text().trim());
							  curLine.attr("fromCircle", "right");
							  curLine.attr("toCircle", "right");

							  var turn11 = {
								  x: turn1.x - lineContainerLeft,
								  y: turn1.y - lineContainerTop
							  };

							  var turn22 = {
								  x: turn2.x - lineContainerLeft,
								  y: turn2.y - lineContainerTop
							  };

							  curLineDetail.from.x = fromX- lineContainerLeft;
							  curLineDetail.from.y = fromY - lineContainerTop;
							  curLineDetail.to.x = toX - lineContainerLeft;
							  curLineDetail.to.y = toY - lineContainerTop;

							  curLineDetail.turnPoints.push({"x":turn11.x, "y":turn11.y});
							  curLineDetail.turnPoints.push({"x":turn22.x, "y":turn22.y});

							  linesArray.push(curLineDetail);

							  var arr = [];
							  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
							  arr.push({"x":turn11.x, "y": turn11.y});
							  arr.push({"x":turn22.x, "y": turn22.y});
							  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

							  drawInArrow(
								  lineCtx,
								  30,
								  10,
								  2,
								  '#96d6fc',
								  arr
							  );
						  }
					  }
				  }
				  //保存外键
				  $.ajax({
					  url: '<%=path%>/v1/sqlModel/createForeignkey',
					  type:"post",
					  data: JSON.stringify({ "mainTable": toTbaleId, "mainField": toField, "joinTable": fromTableId, "joinField": fromField, "lineStart": curLine.attr("fromCircle"), "lineEnd": curLine.attr("toCircle") }),
					  dataType : "json",
					  contentType:"application/json",
					  success : function (result) {
						  if( result.foreignKeyId ) {
							  $(curLine).attr("foreignKeyId", result.foreignKeyId);
							  console.log(linesArray);
							  console.log(lineUUID);
							  for( var s = 0; s < linesArray.length; s++ ){
                  if( linesArray[s].id == ( lineUUID + '' ) ){
	                  linesArray[s].id = result.foreignKeyId;
                  }
                }
						  } else {
							  layer.msg('添加外键失败！', {icon: 2});
							  curLine.remove();
						  }
					  }
				  });
				  $("#container").mousemove(function (e) {
					  containerMousemove(e);
				  });
			  } else {
				  $(curLine).remove();
				  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);
				  return;
			  }
		  });
	  }

    //盒子移动
	  function classBoxMousedown(obj, e) {
		  var e = e || window.event;

		  var thisBox = obj;
		  whichPowerMove = thisBox;

		  if( $(e.target).hasClass("box-item-circle") ){
			  return;
		  }

		  thisBox.style.cursor = "move";

		  isPowerBoxMove = true;

		  powerBoxX = e.clientX;
		  powerBoxY = e.clientY;
		  powerBoxLeft = thisBox.offsetLeft;
		  powerBoxTop = thisBox.offsetTop;

		  var mouseInnerLeft = e.clientX - thisBox.offsetLeft + $("#power-container").scrollLeft();
		  var mouseInnerTop = e.clientY - thisBox.offsetTop + $("#power-container").scrollTop();

      if( $(e.target).hasClass("class-box-title") ){


		    $("#power-container").mousemove(function (e) {

			  //找到所有经过起点对应的线
			  var lineContainer = $(".line_box_container");

			  if( !isPowerBoxMove ){
				  return;
			  }

			  var e = e || window.event;
			  var container = $("#container");
			  var nx = e.clientX;
			  var ny = e.clientY;

			  var nl = e.clientX + $("#power-container").scrollLeft() - mouseInnerLeft;
			  var nt = e.clientY +  $("#power-container").scrollTop()  - mouseInnerTop;

			  whichPowerMove.style.top = nt + 'px';
			  whichPowerMove.style.left = nl + 'px';

			  //container的宽高
			  var containerHeight = container.height();
			  var containerWidth = container.width();

			  if( ( nt + $(whichPowerMove).height() ) > containerHeight ) {
				  container.height( containerHeight + $(whichPowerMove).height() );
				  powerCanvas.height = containerHeight + $(whichPowerMove).height();
			  }

			  if( ( nl + $(whichPowerMove).width() ) > containerWidth ) {
				  container.width( containerWidth + $(whichPowerMove).width() );
				  powerCanvas.width = containerWidth + $(whichPowerMove).width();
			  }

			  //移动box时 相关联的线的变动
			  for( var i = 0; i < lineContainer.length; i++ ) {

				  var fromField = $(lineContainer[i]).attr("fromField");
				  var toField = $(lineContainer[i]).attr("toField");
				  var fromLi = null;
				  var toLi = null;
				  var fromX = 0;
				  var fromY = 0;
				  var toX = 0;
				  var toY = 0;
				  var lineContainerLeft = 0; //线container左边距
				  var lineContainerTop = 0; //线container上边距
				  var lineContainerWidth = 0; //线container左边距
				  var lineContainerHeight = 0; //线container左边距
				  var line1CanvasFromX = 0;
				  var line1CanvasFromY = 0;
				  var line1CanvasToX = 0;
				  var line1CanvasToY = 0;
				  var lineCtx = $(lineContainer[i]).find("canvas")[0].getContext("2d");
				  var fromBox = null;
				  var toBox = null;
				  var fromCircle = $(lineContainer[i]).attr("fromcircle");
				  var toCircle = $(lineContainer[i]).attr("tocircle");
				  var curLineId = $(lineContainer[i]).attr("foreignkeyid");

				  //共8种情况 左左右右 左右左右
				  if( $(lineContainer[i]).attr("fromBoxId") === $(thisBox).attr("id") || $(lineContainer[i]).attr("toBoxId") === $(thisBox).attr("id") ) {

					  if( $(lineContainer[i]).attr("fromBoxId") === $(thisBox).attr("id") ) {
						  //以所点击div为起点的线

              var toBoxId = $(lineContainer[i]).attr("toBoxId");
						  toBox = $("#" + toBoxId);
						  fromBox = $(thisBox);

						  $(thisBox).find(".box-li-name").each(function () {
							  if( $(this).text() == fromField ) {
								  fromLi = $(this).parent();
							  }
						  });
						  $(toBox).find(".box-li-name").each(function () {
							  if( $(this).text() == toField ) {
								  toLi = $(this).parent();
							  }
						  });

						  var curLineIndex = 0;

						  for( var s=0; s<linesArray.length; s++ ){
                if( linesArray[s].id == curLineId ){
	                curLineIndex = s;
	                break;
                }
              }

						  //计算起点终点
						  if( fromCircle == "left" ) {
							  fromX = $(thisBox)[0].offsetLeft;
							  fromY = $(thisBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							  if( toCircle == "left" ) {
								  toX = toBox[0].offsetLeft;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //当前box左起点连接目标box左边的点
								  //左连左
								  if( toX - fromX > 0 ) {

									  var turn1 = {
										  x: fromX - 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y:toY
									  };

									  lineContainerWidth = Math.abs( toX - turn1.x ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = fromX - 10 - 30;

									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
								  else if( toX - fromX <= 0 ) {

									  var turn1 = {
										  x: toX - 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y:toY
									  };

									  lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = turn1.x - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
							  }
							  else if( toCircle == "right" ) {
								  toX = toBox[0].offsetLeft + toBox.width() + 3;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //当前box左起点连接目标box右边的点
								  if( toX - fromX < 0 ) {
									  //左侧

									  lineContainerWidth = Math.abs( toX - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = toX - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  //计算linContainer宽高，重新划线
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									  line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  linesArray[curLineIndex].turnPoints = [];

									  linesArray[curLineIndex].from.x = line1CanvasFromX;
									  linesArray[curLineIndex].from.y = line1CanvasFromY;
									  linesArray[curLineIndex].to.x = line1CanvasToX;
									  linesArray[curLineIndex].to.y = line1CanvasToY;

									  drawArrow(
										  lineCtx,
										  line1CanvasFromX,
										  line1CanvasFromY,
										  line1CanvasToX,
										  line1CanvasToY,
										  30,
										  10,
										  2,
										  '#96d6fc',
                      null,
                      null,
                      null,
										  curLineIndex
									  );
								  }
								  else if( toX - fromX >= 0 ) {
									  //右侧

									  //终点在起点之下
									  if( toY - fromY > 0 ) {

										  var turn1 = {
											  x: fromX - 30,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										  };

										  var turn3 = {
											  x: toX + 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn2.x - 10;
										  lineContainerTop = fromY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX - 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										  };

										  var turn33 = {
											  x: toX + 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );

									  } else if( toY - fromY <= 0 ) {

										  //终点位于起点之上
										  var turn1 = {
											  x: fromX - 30 ,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										  };

										  var turn3 = {
											  x: toX + 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn1.x - 10;
										  lineContainerTop = toY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX - 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn33 = {
											  x: toX + 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );
									  }
								  }
							  }
						  }
						  else if( fromCircle == "right" ) {

							  fromX = $(thisBox)[0].offsetLeft + $(thisBox).width() + 3;
							  fromY = $(thisBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							  if( toCircle == "left" ) {
								  toX = toBox[0].offsetLeft;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //当前box右起点连接目标box左边的点
								  if( toX - fromX > 0 ) {
									  //右侧

									  lineContainerWidth = Math.abs( toX - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = fromX - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  //计算linContainer宽高，重新划线
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									  line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  linesArray[curLineIndex].turnPoints = [];

									  linesArray[curLineIndex].from.x = line1CanvasFromX;
									  linesArray[curLineIndex].from.y = line1CanvasFromY;
									  linesArray[curLineIndex].to.x = line1CanvasToX;
									  linesArray[curLineIndex].to.y = line1CanvasToY;

									  drawArrow(
										  lineCtx,
										  line1CanvasFromX,
										  line1CanvasFromY,
										  line1CanvasToX,
										  line1CanvasToY,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  null,
										  null,
										  null,
										  curLineIndex
									  );

								  }
								  else if( toX - fromX < 0 ) {

									  //终点在起点之下
									  if( toY - fromY > 0 ) {
										  var turn1 = {
											  x: fromX + 30 ,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										  };

										  var turn3 = {
											  x: toX - 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn3.x - 10;
										  lineContainerTop = fromY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX + 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										  };

										  var turn33 = {
											  x: toX - 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );

									  } else if( toY - fromY <= 0 ) {
										  //终点位于起点之上
										  var turn1 = {
											  x: fromX + 30 ,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										  };

										  var turn3 = {
											  x: toX - 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn3.x - 10;
										  lineContainerTop = toY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX + 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn33 = {
											  x: toX - 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );
									  }
								  }

							  }
							  else if( toCircle == "right" ) {
								  toX = toBox[0].offsetLeft + toBox.width() + 3;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //当前box右起点连接目标box右边的点
								  //右连右
								  if( toX - fromX > 0 ) {
									  var turn1 = {
										  x: toX + 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y: toY
									  };

									  lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = fromX - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
								  else if (toX - fromX <= 0) {
									  var turn1 = {
										  x: fromX + 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y: toY
									  };

									  lineContainerWidth = Math.abs(turn1.x - toX) + 20;
									  lineContainerHeight = Math.abs(toY - fromY) + 20;
									  lineContainerLeft = toX - 10;
									  if (toY - fromY > 0) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX - lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
							  }
						  }
						  /*
              * 以上为计算如果当前box作为起点box的时候
              * */
					  }
					  else if( $(lineContainer[i]).attr("toBoxId") === $(thisBox).attr("id") ) {
						  //以所点击div为终点的线
						  var fromBoxId = $(lineContainer[i]).attr("fromBoxId");
						  fromBox = $("#" + fromBoxId);
						  toBox = $(thisBox);

						  $(thisBox).find(".box-li-name").each(function () {
							  if( $(this).text() == toField ) {
								  toLi = $(this).parent();
							  }
						  });
						  $(fromBox).find(".box-li-name").each(function () {
							  if( $(this).text() == fromField ) {
								  fromLi = $(this).parent();
							  }
						  });

						  var curLineIndex = 0;

						  for( var s=0; s<linesArray.length; s++ ){
							  if( linesArray[s].id == curLineId ){
								  curLineIndex = s;
								  break;
							  }
						  }

						  //计算起点终点
						  if( fromCircle == "left"  ) {
							  fromX = $(fromBox)[0].offsetLeft;
							  fromY = $(fromBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							  if( toCircle == "left"  ) {
								  toX = toBox[0].offsetLeft;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //起点box的左起点到所点击box的左终点
								  //左连左
								  if( toX - fromX > 0 ) {

									  var turn1 = {
										  x: fromX - 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y:toY
									  };

									  lineContainerWidth = Math.abs( toX - turn1.x ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = fromX - 10 - 30;

									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
								  else if( toX - fromX <= 0 ) {

									  var turn1 = {
										  x: toX - 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y:toY
									  };

									  lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = turn1.x - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
							  }
							  else if( toCircle == "right" ) {
								  toX = toBox[0].offsetLeft + toBox.width() + 3;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //起点box的左起点到所点击box的右终点

								  if( toX - fromX < 0 ) {
									  //左侧

									  lineContainerWidth = Math.abs( toX - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = toX - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  //计算linContainer宽高，重新划线
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									  line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  linesArray[curLineIndex].turnPoints = [];

									  linesArray[curLineIndex].from.x = line1CanvasFromX;
									  linesArray[curLineIndex].from.y = line1CanvasFromY;
									  linesArray[curLineIndex].to.x = line1CanvasToX;
									  linesArray[curLineIndex].to.y = line1CanvasToY;

									  drawArrow(
										  lineCtx,
										  line1CanvasFromX,
										  line1CanvasFromY,
										  line1CanvasToX,
										  line1CanvasToY,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  null,
										  null,
										  null,
										  curLineIndex
									  );
								  }
								  else if( toX - fromX >= 0 ) {
									  //右侧

									  //终点在起点之下
									  if( toY - fromY > 0 ) {

										  var turn1 = {
											  x: fromX - 30,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										  };

										  var turn3 = {
											  x: toX + 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn2.x - 10;
										  lineContainerTop = fromY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX - 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										  };

										  var turn33 = {
											  x: toX + 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );
									  } else if( toY - fromY <= 0 ) {

										  //终点位于起点之上
										  var turn1 = {
											  x: fromX - 30 ,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										  };

										  var turn3 = {
											  x: toX + 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn1.x - 10;
										  lineContainerTop = toY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX - 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn33 = {
											  x: toX + 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );
									  }
								  }
							  }
						  }
						  else if( fromCircle == "right" ) {
							  fromX = $(fromBox)[0].offsetLeft + $(fromBox).width() + 3;
							  fromY = $(fromBox)[0].offsetTop + fromLi[0].offsetTop + fromLi.height()/2 + 2;

							  if( toCircle == "left"  ) {
								  toX = toBox[0].offsetLeft;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //起点box的右起点到所点击box的左终点
								  if( toX - fromX > 0 ) {
									  //右侧

									  lineContainerWidth = Math.abs( toX - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = fromX - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }

									  //计算linContainer宽高，重新划线
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  line1CanvasFromX = fromX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasFromY = fromY - $(lineContainer[i])[0].offsetTop;

									  line1CanvasToX = toX - $(lineContainer[i])[0].offsetLeft;
									  line1CanvasToY = toY - $(lineContainer[i])[0].offsetTop;

									  linesArray[curLineIndex].turnPoints = [];

									  linesArray[curLineIndex].from.x = line1CanvasFromX;
									  linesArray[curLineIndex].from.y = line1CanvasFromY;
									  linesArray[curLineIndex].to.x = line1CanvasToX;
									  linesArray[curLineIndex].to.y = line1CanvasToY;

									  drawArrow(
										  lineCtx,
										  line1CanvasFromX,
										  line1CanvasFromY,
										  line1CanvasToX,
										  line1CanvasToY,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  null,
										  null,
										  null,
										  curLineIndex
									  );
								  }
								  else if( toX - fromX < 0 ) {

									  //终点在起点之下
									  if( toY - fromY > 0 ) {
										  var turn1 = {
											  x: fromX + 30 ,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() )
										  };

										  var turn3 = {
											  x: toX - 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn3.x - 10;
										  lineContainerTop = fromY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX + 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: ( toBox[0].offsetTop - ( fromBox[0].offsetTop + $(fromBox).height() ) )/2 + ( fromBox[0].offsetTop + $(fromBox).height() ) - lineContainerTop
										  };

										  var turn33 = {
											  x: toX - 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );
									  } else if( toY - fromY <= 0 ) {
										  //终点位于起点之上
										  var turn1 = {
											  x: fromX + 30 ,
											  y: fromY
										  };

										  var turn2 = {
											  x: turn1.x,
											  y: ( fromBox[0].offsetTop - ( toBox[0].offsetTop + $(toBox).height() ) )/2 + ( toBox[0].offsetTop + $(toBox).height() )
										  };

										  var turn3 = {
											  x: toX - 30,
											  y: turn2.y
										  };

										  var turn4 = {
											  x: turn3.x,
											  y: toY
										  };

										  lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
										  lineContainerLeft = turn3.x - 10;
										  lineContainerTop = toY - 10;

										  lineContainer[i].style.width = lineContainerWidth + 'px';
										  lineContainer[i].style.height = lineContainerHeight + 'px';
										  lineContainer[i].style.left = lineContainerLeft + 'px';
										  lineContainer[i].style.top = lineContainerTop + 'px';
										  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
										  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

										  ctx1.clearRect(0,0, powerCanvas.width, powerCanvas.height);

										  var turn11 = {
											  x: fromX + 30 - lineContainerLeft,
											  y: fromY - lineContainerTop
										  };

										  var turn22 = {
											  x: turn1.x - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn33 = {
											  x: toX - 30 - lineContainerLeft,
											  y: turn2.y - lineContainerTop
										  };

										  var turn44 = {
											  x: turn3.x - lineContainerLeft,
											  y: toY - lineContainerTop
										  };

										  linesArray[curLineIndex].turnPoints = [];
										  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn33.x, "y":turn33.y});
										  linesArray[curLineIndex].turnPoints.push({"x":turn44.x, "y":turn44.y});

										  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
										  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
										  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
										  linesArray[curLineIndex].to.y = toY - lineContainerTop;

										  var arr = [];
										  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
										  arr.push({"x":turn11.x, "y": turn11.y});
										  arr.push({"x":turn22.x, "y": turn22.y});
										  arr.push({"x":turn33.x, "y": turn33.y});
										  arr.push({"x":turn44.x, "y": turn44.y});
										  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

										  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

										  drawInArrow(
											  lineCtx,
											  30,
											  10,
											  2,
											  '#96d6fc',
											  arr
										  );
									  }
								  }
							  }
							  else if( toCircle == "right" ) {
								  toX = toBox[0].offsetLeft + toBox.width() + 3;
								  toY = toBox[0].offsetTop + toLi[0].offsetTop + toLi.height()/2 + 2;

								  //起点box的右起点到所点击box的右终点
								  //右连右
								  if( toX - fromX > 0 ) {
									  var turn1 = {
										  x: toX + 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y: toY
									  };

									  lineContainerWidth = Math.abs( turn1.x - fromX ) + 20;
									  lineContainerHeight = Math.abs( toY -  fromY ) + 20;
									  lineContainerLeft = fromX - 10;
									  if( toY - fromY > 0 ) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
								  else if (toX - fromX <= 0) {
									  var turn1 = {
										  x: fromX + 30,
										  y: fromY
									  };
									  var turn2 = {
										  x: turn1.x,
										  y: toY
									  };

									  lineContainerWidth = Math.abs(turn1.x - toX) + 20;
									  lineContainerHeight = Math.abs(toY - fromY) + 20;
									  lineContainerLeft = toX - 10;
									  if (toY - fromY > 0) {
										  //右下
										  lineContainerTop = fromY - 10;
									  } else {
										  //右上
										  lineContainerTop = toY - 10;
									  }
									  $(lineContainer[i])[0].style.width = lineContainerWidth + 'px';
									  $(lineContainer[i])[0].style.height = lineContainerHeight + 'px';
									  $(lineContainer[i])[0].style.left = lineContainerLeft + 'px';
									  $(lineContainer[i])[0].style.top = lineContainerTop + 'px';
									  $(lineContainer[i]).find("canvas")[0].width = lineContainerWidth;
									  $(lineContainer[i]).find("canvas")[0].height = lineContainerHeight;

									  var turn11 = {
										  x: turn1.x - lineContainerLeft,
										  y: turn1.y - lineContainerTop
									  };

									  var turn22 = {
										  x: turn2.x - lineContainerLeft,
										  y: turn2.y - lineContainerTop
									  };

									  linesArray[curLineIndex].turnPoints = [];
									  linesArray[curLineIndex].turnPoints.push({"x":turn11.x, "y":turn11.y});
									  linesArray[curLineIndex].turnPoints.push({"x":turn22.x, "y":turn22.y});

									  linesArray[curLineIndex].from.x = fromX- lineContainerLeft;
									  linesArray[curLineIndex].from.y = fromY - lineContainerTop;
									  linesArray[curLineIndex].to.x = toX - lineContainerLeft;
									  linesArray[curLineIndex].to.y = toY - lineContainerTop;

									  var arr = [];
									  arr.push({"x":fromX- lineContainerLeft, "y": fromY - lineContainerTop});
									  arr.push({"x":turn11.x, "y": turn11.y});
									  arr.push({"x":turn22.x, "y": turn22.y});
									  arr.push({"x":toX - lineContainerLeft, "y": toY - lineContainerTop});

									  lineCtx.clearRect(0,0, lineContainerWidth, lineContainerHeight);

									  drawInArrow(
										  lineCtx,
										  30,
										  10,
										  2,
										  '#96d6fc',
										  arr
									  );
								  }
							  }
						  }
					  }
				  }
			  }
		  });

      $(thisBox).mouseup(function () {
		      isPowerBoxMove = false;
		      thisBox.style.cursor = "default";
		      $("#power-container").unbind("mousemove");
		      saveXY();
	      });
      }

	  }

	  //增加table中的一行
	  function addTableColumn( e ) {
		  var e = e || window.event;
		  var curTable = $(e.target).parent().parent().find("table");
		  var lastId = $(curTable).find("tbody tr:last td:first").next().text().trim();
		  var index = 1;
		  if( lastId ) {
			  index = parseInt(lastId) + 1;
		  }
		  var resType = $("#resType").val();
		  var html = '<tr>\n' +
			  '                            <td class="" >' +
			  '                              <form class="layui-form">\n' +
			  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
			  '                                         <input type="checkbox" class="lineCheckbox" lay-skin="primary" >\n' +
			  '                                     </div>\n' +
			  '                                 </div>' +
			  '                              </form>' +
			  '                            </td>\n' +
			  '                            <td class="" >'+ index +'</td>\n' +
			  '                            <td class="" >' +
			  '                                 <span class="span-select">column'+ index +'</span>' +
			  '                                 <form class="layui-form" action="" style="display: none;">' +
			  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
			  '                                          <input type="text" name="fieldName" autocomplete="off" class="layui-input" value="column'+ index +'">\n' +
			  '                                        </div>\n' +
			  '                                    </div>\n' +
			  '                                 </form>' +
			  '                            </td>\n' +
			  '                            <td class="" >' +
			  '                                 <span class="span-select"></span>' +
			  '                                 <form class="layui-form" action="" style="display: none;">' +
			  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
			  '                                            <input type="text" name="fieldSqlName" autocomplete="off" class="layui-input">\n' +
			  '                                        </div>\n' +
			  '                                    </div>\n' +
			  '                                 </form>' +
			  '                            </td>\n' +
			  '                            <td class="" >\n' +
			  '                                <span class="span-select"></span>\n' +
			  '                                <form class="layui-form" action="" style="display: none;">\n' +
			  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
			  '                                            <select name="fieldType" lay-filter="fieldType">\n' ;
      if( resType == "1" ) {
      	//mysql
	      html +=
		      '                                                <option value="6">int</option>\n' +
		      '                                                <option value="2">varchar</option>\n' +
		      '                                                <option value="1">float</option>\n' +
		      '                                                <option value="7">bigint</option>\n' +
		      '                                                <option value="9">tinyint</option>\n' +
		      '                                                <option value="3">datetime</option>\n' +
		      '                                                <option value="4">longtext</option>\n' +
		      '                                                <option value="5">blob</option>\n' +
		      '                                                <option value="10" disabled>其它</option>\n' ;
      }else if( resType == "2" ) {
        //oracle
	      html +=
		      '                                                <option value="2">varchar2</option>\n' +
		      '                                                <option value="1">float</option>\n' +
		      '                                                <option value="4">blob</option>\n' +
		      '                                                <option value="6">number</option>\n' +
		      '                                                <option value="3">date</option>\n' +
		      '                                                <option value="10" disabled>其它</option>\n' ;
      } else if( resType == "3" ) {
        //db2
	      html +=
		      '                                                <option value="2">varchar</option>\n' +
		      '                                                <option value="1">decimal</option>\n' +
		      '                                                <option value="3">timestamp</option>\n' +
		      '                                                <option value="4">clob</option>\n' +
		      '                                                <option value="5">blob</option>\n' +
		      '                                                <option value="6">int</option>\n' +
		      '                                                <option value="7">bigint</option>\n' +
		      '                                                <option value="9">boolean</option>\n' +
		      '                                                <option value="10" disabled>其它</option>\n' ;
      } else if( resType == "5" ) {
        //sqlserver
	      html +=
		      '                                                <option value="2">varchar</option>\n' +
		      '                                                <option value="1">float</option>\n' +
		      '                                                <option value="3">datetime</option>\n' +
		      '                                                <option value="4">nvarchar(max)</option>\n' +
		      '                                                <option value="5">varbinary(max)</option>\n' +
		      '                                                <option value="6">int</option>\n' +
		      '                                                <option value="7">bigint</option>\n' +
		      '                                                <option value="9">bit</option>\n' +
		      '                                                <option value="10" disabled>其它</option>\n' ;
      }

		  html +=
			  '                                            </select>\n' +
			  '                                        </div>\n' +
			  '                                    </div>\n' +
			  '                                </form>\n' +
			  '                            </td>\n' +
			  '                            <td class="" >\n' +
			  '                                <span class="span-select"></span>' +
			  '                                <form class="layui-form" action="" style="display: none;">\n' +
			  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
			  '                                            <input type="text" name="fieldLen" autocomplete="off" class="layui-input">\n' +
			  '                                        </div>\n' +
			  '                                    </div>\n' +
			  '                                </form>\n' +
			  '                            </td>\n' +
			  '                            <td class="" >\n' +
			  '                                <span class="span-select"></span>' +
			  '                                <form class="layui-form" action="" style="display: none;">\n' +
			  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
			  '                                            <input type="text" name="fieldDigit" autocomplete="off" class="layui-input">\n' +
			  '                                        </div>\n' +
			  '                                    </div>\n' +
			  '                                </form>\n' +
			  '                            </td>\n' ;

		  var preFieldKey = curTable.find(".FieldKey");
		  var isHasPrekey = false;
		  if(preFieldKey.length > 0) {
			  for( var l = 0; l < preFieldKey.length; l++ ) {
				  if( $(preFieldKey[l]).is(':checked') ) {
					  isHasPrekey = true;
					  break;
				  }
			  }
		  }

		  if( isHasPrekey ) {
			  html += '                            <td class="" >\n' +
				  '                              <form class="layui-form">\n' +
				  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
				  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
				  '                                         <input class="FieldKey" lay-filter="FieldKey" type="checkbox" name="FieldKey" value="1" lay-skin="primary" disabled>\n' +
				  '                                     </div>\n' +
				  '                                 </div>' +
				  '                              </form>' +
				  '                            </td>\n' ;
		  } else {
			  html += '                            <td class="" >\n' +
				  '                              <form class="layui-form">\n' +
				  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
				  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
				  '                                         <input class="FieldKey" lay-filter="FieldKey" type="checkbox" name="FieldKey" value="1" lay-skin="primary">\n' +
				  '                                     </div>\n' +
				  '                                 </div>' +
				  '                              </form>' +
				  '                            </td>\n' ;
		  }
		  html +=
        '                            <td class="" >\n' +
			  '                              <form class="layui-form">\n' +
			  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
			  '                                         <input class="sortIndex" lay-filter="sortIndex" type="checkbox" name="sortIndex" value="1" lay-skin="primary">\n' +
			  '                                     </div>\n' +
			  '                                 </div>' +
			  '                              </form>' +
			  '                            </td>\n' +
        '                            <td class="" >\n' +
			  '                              <form class="layui-form">\n' +
			  '                                 <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                     <div class="layui-input-block" style="margin: 0;">\n' +
			  '                                         <input class="sortIndex" lay-filter="isNull" type="checkbox" name="isNull" value="1" lay-skin="primary">\n' +
			  '                                     </div>\n' +
			  '                                 </div>' +
			  '                              </form>' +
			  '                            </td>\n' +
			  '                            <td class="" >\n' +
			  '                                <span class="span-select"></span>' +
			  '                                <form class="layui-form" action="" style="display: none;">\n' +
			  '                                    <div class="layui-form-item" style="margin-bottom: 0;">\n' +
			  '                                        <div class="layui-input-block" style="margin-left: 0;">\n' +
			  '                                            <input type="text" name="fieldResume" class="layui-input" autocomplete="off">\n' +
			  '                                        </div>\n' +
			  '                                    </div>\n' +
			  '                                </form>\n' +
			  '                            </td>\n' +
			  '                        </tr>';

		  curTable.find("tbody").append(html);

		  layui.form.render('select');
		  layui.form.render('checkbox');

//    spanSelectBindClick();
		  selectBindChange();
	  }

	  //每行中的表格编辑
	  function spanSelectBindClick() {
		  $(".power-inner-table").click(function(e){
			  var e = e || window.event;
			  if( $(e.target).hasClass("span-select") ){
				  var curSpan = $(e.target);
				  var curForm = $(e.target).parent().find("form");
				  var curInput = curForm.find("input").not($(".layui-form-select input"));

				  curSpan.css("display","none");
				  curForm.css("display","block");

				  curInput.val(curSpan.text());
				  curInput.focus();

				  curInput.blur(function () {
					  var text = $(this).val();
				  	if( curInput.attr("name") == "fieldSqlName" ){
              var patt = /^[0-9A-Z]{1,}$/;
						  var testResult = patt.test(text);
              if( $("#resType").val() == "2" || $("#resType").val() == "3" ) {
                if( !testResult ) {
                  layer.tips('Oracle或DB2类型的数据源表字段只能由大写字母、数字组成！', curInput, {tips: 1});
                } else{
                  curForm.css("display","none");
                  curSpan.text(text);
                  curSpan.css("display","inline-block");
                }
              } else{
	              curForm.css("display","none");
	              curSpan.text(text);
	              curSpan.css("display","inline-block");
              }
            }else if( curInput.attr("name") == "fieldDigit" ){

            } else {
						  curForm.css("display","none");
						  curSpan.text(text);
						  curSpan.css("display","inline-block");
            }
				  });
			  }
		  });
	  }

	  //select checkBox change事件
	  function selectBindChange() {
		  layui.form.on('select(fieldType)', function(data){
			  var td = $(data.elem).parent().parent().parent().parent();
			  var curSpan = td.find(".span-select");
			  var curForm = td.find("form");
			  var text = $(data.elem).find("option[value="+ data.value +"]").text();
			  curForm.css("display","none");
			  curSpan.text(text);
			  curSpan.css("display","inline-block");
		  });

		  layui.form.on('checkbox(FieldKey)', function(data){

			  var sortCheckBox = $(data.elem).parent().parent().parent().parent().next().find(".sortIndex");
			  var sortBeautiful = sortCheckBox.next();
			  var notCurCheckBox = $(".FieldKey").not($(data.elem));

			  if( data.elem.checked ) {
				  $(".FieldKey").not($(data.elem)).attr("disabled",true);
				  for( var i = 0; i < notCurCheckBox.length; i++ ){
					  $(notCurCheckBox[i]).attr("disabled",true);
					  $(notCurCheckBox[i]).next().addClass("layui-disabled");
				  }
				  sortCheckBox.attr("checked",true);
				  sortBeautiful.addClass("layui-form-checked");
			  } else {
				  $(".FieldKey").not($(data.elem)).attr("disabled",false);
				  for( var i = 0; i < notCurCheckBox.length; i++ ){
					  $(notCurCheckBox[i]).attr("disabled",false);
					  $(notCurCheckBox[i]).next().removeClass("layui-disabled");
				  }
				  sortCheckBox.attr("checked",false);
				  sortBeautiful.removeClass("layui-form-checked");
			  }
		  });

		  layui.form.on('checkbox(columnCheckBox)', function (data) {
			  var lineCheckBox = $(".lineCheckbox");
			  if( data.elem.checked ){
				  for( var i = 0; i < lineCheckBox.length; i++ ){
					  $(lineCheckBox[i]).attr("checked",true);
					  $(lineCheckBox[i]).next().addClass("layui-form-checked");
          }
        } else {
				  for( var i = 0; i < lineCheckBox.length; i++ ){
					  $(lineCheckBox[i]).attr("checked",false);
					  $(lineCheckBox[i]).next().removeClass("layui-form-checked");
				  }
        }
		  })
	  }

	  //删除table中的一行
	  function delteTableColumn( e ) {
		  var e = e || window.event;

		  var lineCheckboxs = $(".lineCheckbox");
		  for( var i = 0; i < lineCheckboxs.length; i++) {
			  if( $(lineCheckboxs[i]).is(':checked') ){
				  $( lineCheckboxs[i] ).parent().parent().parent().parent().parent().remove();
			  }
		  }
	  }

	  //鼠标移动找经过的线
	  function containerMousemove(e) {
		  var lineContainer = $(".line_box_container");

		  for( var i = 0; i < lineContainer.length; i++ ) {
			  var curLineContainer = $(lineContainer[i]);
			  var curVanvas = curLineContainer.find("canvas")[0];
			  var lineCtx = curVanvas.getContext("2d");
			  var x = e.clientX - $("#power-container")[0].offsetLeft - curLineContainer[0].offsetLeft  + $("#power-container").scrollLeft();
			  var y = e.clientY - $(".power-main")[0].offsetTop - curLineContainer[0].offsetTop + $("#power-container").scrollTop();

			  if( lineCtx.isPointInStroke ){
				  if( lineCtx.isPointInStroke(x,y) ){
					  var lineId = curLineContainer.attr("foreignKeyId");
					  curLineContainer.addClass("hover-line");
					  if( linesArray.length > 0 ){
						  for( var i = 0; i < linesArray.length; i++ ){
							  if( lineId == linesArray[i].id ){
								  lineCtx.clearRect(0,0, curLineContainer.width(), curLineContainer.height() );
								  drawMelodyArrow( lineCtx, linesArray[i], 2, '#fc0c01', 'shadow' );
							  }
						  }
            }
				  }else{
            var hoverLines = $(".hover-line");
            if(hoverLines.length > 0){
	            for( var j = 0, len = hoverLines.length ; j < len; j++ ){
		            var curLineContainer = $(hoverLines[j]);

		            if( !curLineContainer.hasClass("power-active") ){
			            var curVanvas = curLineContainer.find("canvas")[0];
			            var lineCtx = curVanvas.getContext("2d");
			            var lineId = curLineContainer.attr("foreignKeyId");

			            curLineContainer.removeClass("hover-line");

			            if( linesArray.length > 0 ) {
				            for( var k = 0; k < linesArray.length; k++ ){
					            if( lineId == linesArray[k].id ){
						            lineCtx.clearRect(0,0, curLineContainer.width(),curLineContainer.height() );
						            drawMelodyArrow( lineCtx, linesArray[k], 2, '#96d6fc', null );
					            }
				            }
			            }
                }
	            }
            }
				  }
			  }else {

				  if( lineCtx.isPointInPath(x,y) ){
					  lineCtx.strokeStyle = "#333";
					  lineCtx.stroke();
				  }else {
					  lineCtx.strokeStyle = "#96d6fc";
					  lineCtx.stroke();
				  }
			  }
		  }
	  }

</script>
</body>
</html>
