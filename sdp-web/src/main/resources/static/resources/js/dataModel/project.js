
$(document).ready(function(){
	var apiInterval ;
	var serviceInterval ;
	var pageInterval ;

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
			var x = event.clientX;
			var y = event.clientY + $("body").scrollTop() ;
			var moduleId = treeNode.id;
			if( treeNode.modelFlag == "0" ) {
				//项目
				var html =
					'<li class="rMenu-item rMenu-new"><a href="#">新增模块</a></li>\n' +
					'<li class="rMenu-item rMenu-edit"><a href="#">编辑</a></li>\n' +
					'<li role="separator" class="divider"></li>' +
					'<li class="rMenu-item rMenu-delete"><a href="#">删除</a></li>';
				rMenu.append(html);
				rMenu.css("display","block");
				rMenu.css("top", y + "px");
				rMenu.css("left", x + "px");
				$(".rMenu-new").click(function (e) {
					var formId = getuuid(16,16);
					$("#addProjectModule").find(".modal-body").empty();
					var contentHtml =
						'<form class="form-horizontal" id="'+formId+'">' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">名称</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="modelName" class="form-control">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">序号</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="sortId" class="form-control">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">说明</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="resume" class="form-control">\n' +
						'             <input type="text" name="modelFlag" value="1" hidden="hidden"/>' +
						'             <input type="text" name="parentId" value="'+ moduleId +'" hidden="hidden"/>' +
						'    </div>\n' +
						'  </div>' +
						'</form>';
					$("#addProjectModule").find(".modal-body").append(contentHtml);
					$("#addProjectModule").modal("show");
					$("#newModulesub").click(function (e) {
						e.preventDefault();
						$("#editProjectModule").modal("hide");
						var data = $("#" + formId).serialize();
						$.ajax({
							url: curContext + '/v1/sqlQuery/saveProModel',
							type: 'post',
							data: data,
							success: function (result) {
								$("#addProjectModule").modal("hide");
								if( result.modelId ){
									var zTree = $.fn.zTree.getZTreeObj("curTree");
									zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '1', iconSkin : 'module' });
								}
							}
						});
					});
				});
				$(".rMenu-edit").click(function (e) {
					var formId = getuuid(16, 16);
					$("#editProjectModule").find(".modal-body").empty();
					var contentHtml =
						'<form class="form-horizontal" id="' + formId + '">' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">名称</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="modelName" class="form-control" value="' + treeNode.name + '">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">序号</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="sortId" class="form-control" value="' + treeNode.sortId + '">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">说明</label>\n' +
						'    <div class="col-sm-10">\n' +
						'       <input type="text" name="resume" class="form-control" value="' + treeNode.resume + '">\n' +
						'       <input type="text" name="modelFlag" value="0" hidden="hidden"/>' +
						'       <input type="text" name="parentId" value="' + treeNode.pId + '" hidden="hidden"/>' +
						'       <input type="text" name="modelId" value="' + treeNode.id + '" hidden="hidden"/>' +
						'    </div>\n' +
						'  </div>' +
						'</form>';
					$("#editProjectModule").find(".modal-body").append(contentHtml);
					$("#editProjectModule").modal("show");
					$("#editProjectsub").click(function (e) {
						e.preventDefault();
						$("#editProjectModule").modal("hide");
						var data = $("#" + formId).serialize();
						$.ajax({
							url: curContext + '/v1/sqlQuery/saveProModel',
							data: data,
							type: "POST",
							success: function (result) {
								if (result.modelId) {
									var zTree = $.fn.zTree.getZTreeObj("curTree");
									node = zTree.getNodeByParam("id", result.modelId);
									node.name = result.modelName;
									zTree.updateNode(node);
								}
							}
						});
					});
				});
				$(".rMenu-delete").click(function (e) {
					if (treeNode.isParent) {
						return false;
					}

					$("#deleteProjectModule").find(".modal-body").text("确认删除项目" + treeNode.name + "?" );
					$("#deleteProjectModule").modal("show");
					$("#deleteProjectsub").click(function(e){
						$("#deleteProjectModule").modal("hide");
						$.ajax({
							url: curContext + '/v1/sqlQuery/deleteProModel',
							data: {"modelId": treeNode.id},
							type: "POST",
							success: function (result) {
								if (result.code == 200) {
									zTree.removeNode(treeNode);
								} else {

								}
							}
						});
					});
				});
			}
			else if(treeNode.modelFlag == "1") {
				//模块
				var html =
					'<li class="rMenu-item rMenu-new-module"><a href="#">新增子模块</a></li>\n' +
					'<li class="rMenu-item rMenu-new-api"><a href="#">新增接口节点</a></li>\n' +
					'<li class="rMenu-item rMenu-new-service"><a href="#">新增服务节点</a></li>' +
					'<li class="rMenu-item rMenu-new-page"><a href="#">新增页面节点</a></li>'+
					'<li class="rMenu-item rMenu-edit-module"><a href="#">编辑</a></li>'+
					'<li role="separator" class="divider"></li>' +
					'<li class="rMenu-item rMenu-delete-module"><a href="#">删除</a></li>';

				rMenu.append(html);
				rMenu.css("display","block");
				rMenu.css("top", y + "px");
				rMenu.css("left", x + "px");

				$(".rMenu-new-module").click(function (e) {
					var formId = getuuid(16,16);
					$("#addModule").find(".modal-body").empty();
					var contentHtml =
						'<form class="form-horizontal" id="'+formId+'">' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">名称</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="modelName" class="form-control">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">序号</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="sortId" class="form-control">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">说明</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="resume" class="form-control">\n' +
						'             <input type="text" name="modelFlag" value="1" hidden="hidden"/>' +
						'             <input type="text" name="parentId" value="'+ moduleId +'" hidden="hidden"/>' +
						'    </div>\n' +
						'  </div>' +
						'</form>';
					$("#addModule").find(".modal-body").append(contentHtml);
					$("#addModule").modal("show");
					$("#addModulesub").click(function (e) {
						e.preventDefault();
						$("#addModule").modal("hide");
						var data = $("#" + formId).serialize();
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
				});
				$(".rMenu-new-api").click(function(e) {
					var zTree = $.fn.zTree.getZTreeObj("curTree");
					var childNodes = zTree.getNodesByParam("pId",treeNode.id);
					for( var n = 0; n < childNodes.length; n++ ){
						if( childNodes[n].modelFlag == "22" ){
							return;
						}
					}
					$.ajax({
						url: curContext + '/v1/sqlQuery/saveProModel',
						type: 'post',
						data: { "modelName" : "接口", "sortId": "1", "modelFlag": "22", "parentId": moduleId },
						success: function (result) {
							if( result.modelId ){
								zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '22', iconSkin : 'api' });
							}
						}
					});
				});
				$(".rMenu-new-service").click(function (e) {
					var zTree = $.fn.zTree.getZTreeObj("curTree");
					var childNodes = zTree.getNodesByParam("pId",treeNode.id);
					for( var n = 0; n < childNodes.length; n++ ){
						if( childNodes[n].modelFlag == "33" ){
							return;
						}
					}
					$.ajax({
						url: curContext + '/v1/sqlQuery/saveProModel',
						type: 'post',
						data: { "modelName" : "服务", "sortId": "1", "modelFlag": "33", "parentId": moduleId },
						success: function (result) {
							if( result.modelId ){
								zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '33', iconSkin : 'service' });
							}
						}
					});
				});
				$(".rMenu-new-page").click(function (e) {
					var zTree = $.fn.zTree.getZTreeObj("curTree");
					var childNodes = zTree.getNodesByParam("pId",treeNode.id);
					for( var n = 0; n < childNodes.length; n++ ){
						if( childNodes[n].modelFlag == "44" ){
							return;
						}
					}
					$.ajax({
						url: curContext + '/v1/sqlQuery/saveProModel',
						type: 'post',
						data: { "modelName" : "页面", "sortId": "1", "modelFlag": "44", "parentId": moduleId },
						success: function (result) {
							if( result.modelId ){
								zTree.addNodes(treeNode, {id:result.modelId, pId:treeNode.id, name: result.modelName, modelFlag : '44', iconSkin : 'page' });
							}
						}
					});
				});
				$(".rMenu-edit-module").click(function (e) {
					var formId = getuuid(16,16);
					$("#editModule").find(".modal-body").empty();
					var contentHtml =
						'<form class="form-horizontal" id="'+formId+'">' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">名称</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="modelName" class="form-control" value="'+ treeNode.name +'">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">序号</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="sortId" class="form-control" value="'+ treeNode.sortId +'">\n' +
						'    </div>\n' +
						'  </div>' +
						'  <div class="form-group">\n' +
						'    <label for="inputEmail3" class="col-sm-2 control-label">说明</label>\n' +
						'    <div class="col-sm-10">\n' +
						'      <input type="text" name="resume" class="form-control" value="'+ treeNode.resume +'">\n' +
						'             <input type="text" name="modelFlag" value="1" hidden="hidden"/>' +
						'             <input type="text" name="parentId" value="'+ treeNode.pId +'" hidden="hidden"/>' +
						'             <input type="text" name="modelId" value="'+ treeNode.id +'" hidden="hidden"/>' +
						'    </div>\n' +
						'  </div>' +
						'</form>';
					$("#editModule").find(".modal-body").append(contentHtml);
					$("#editModule").modal("show");
					$("#editModulesub").click(function(e){
						e.preventDefault();
						$("#editModule").modal("hide");
						var data = $("#" + formId).serialize();
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
				});
				$(".rMenu-delete-module").click(function (e) {
					if( treeNode.isParent ){
						return false;
					}
					$("#deleteModule").find(".modal-body").text('确认删除模块'+ treeNode.name +'？');
					$("#deleteModule").modal("show");
					$("#deleteModulesub").click(function (e) {
						$("#deleteModule").modal("hide");
						$.ajax({
							url: curContext + '/v1/sqlQuery/deleteProModel',
							data: { "modelId": treeNode.id },
							type: "POST",
							success: function (result) {
								if( result.code == 200 ){
									zTree.removeNode(treeNode);
								}else {

								}
							}
						});
					});
				});
			}
			else if(treeNode.modelFlag == "22" || treeNode.modelFlag == "33" || treeNode.modelFlag == "44" ) {
				//接口节点
				var html =
					'<li class="rMenu-item rMenu-delete-api"><a href="#">删除</a></li>\n' ;

				rMenu.append(html);
				rMenu.css("display","block");
				rMenu.css("top", y + "px");
				rMenu.css("left", x + "px");

				$(".rMenu-delete-api").click(function(e) {
					if( treeNode.modelFlag == "22" ){
						$("#deleteNode").find(".modal-title").text("删除api节点");
						$("#deleteNode").find(".modal-body").text("确认删除api节点？");
					} else if( treeNode.modelFlag == "33" ){
						$("#deleteNode").find(".modal-title").text("删除service节点");
						$("#deleteNode").find(".modal-body").text("确认删除service节点？");
					} else if( treeNode.modelFlag == "44" ){
						$("#deleteNode").find(".modal-title").text("删除page节点");
						$("#deleteNode").find(".modal-body").text("确认删除page节点？");
					}

					$("#deleteNode").modal("show");
					$("#deleteNodeSub").click(function (e){
						$("#deleteNode").modal("hide");
						$.ajax({
							url: curContext + '/v1/sqlQuery/deleteProModel',
							data: { "modelId": treeNode.id },
							type: "POST",
							success: function (result) {
								if( result.code == 200 ){
									zTree.removeNode(treeNode);
								}else {

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
				$(".inner-word").css("display","none");
				$("#IUFrame").attr("src", curContext + "/v1/sqlModel/manage/databaseApi/" + moduleId);
				window.clearInterval(serviceInterval);
				window.clearInterval(pageInterval);
				apiInterval = setInterval(function () {
					var IUFrame = document.getElementById("IUFrame");
					IUFrame.style.height = IUFrame.contentWindow.document.documentElement.scrollHeight + "px";
				},1000);
			} else if( treeNode.modelFlag == "33" ) {
				//跳转至服务
				window.clearInterval(apiInterval);
				window.clearInterval(pageInterval);
				// serviceInterval = setInterval(function () {
				// 	var IUFrame = document.getElementById("IUFrame");
				// 	IUFrame.style.height = IUFrame.contentWindow.document.documentElement.scrollHeight + "px";
				// },1000);
				$(".inner-word").css("display","none");
				$("#IUFrame").attr("src", curContext + "/processTable/index/" + moduleId + "/" + pName );
			} else if( treeNode.modelFlag == "44" ) {
				//跳转至页面
				window.clearInterval(apiInterval);
				window.clearInterval(serviceInterval);
				pageInterval = setInterval(function () {
					var IUFrame = document.getElementById("IUFrame");
					IUFrame.style.height = IUFrame.contentWindow.document.documentElement.scrollHeight + "px";
				},1000);
				$(".inner-word").css("display","none");
				$("#IUFrame").attr("src", curContext + "/v1/pageType/databasePagePicker/" + moduleId );
			}
		}

		var zTreeObj = $.fn.zTree.init($("#curTree"), setting, treeArray);
		zTree = $.fn.zTree.getZTreeObj("curTree");
	}


  //项目模块列表
	$.ajax({
		url : curContext + '/v1/sqlQuery/getProModel',
		type: 'post',
		success: function( result ) {
			loadModuleTree(result);
		}
	});

	$(document).click(function(e){
		var e = e || window.event;
		if( !$(e.target).hasClass("rMenu-item") ) {
			$(".rMenu").css("display","none");
		}
	});
	$(window).scroll(function (e) {
		if( $("body").scrollTop() > 20 ){
			var leftWidth = $(".project-left-menu").width();
			$(".project-left-menu").css("position","fixed");
			$(".project-left-menu").css("top","20px");
			$(".project-left-menu").width(leftWidth);
		}
	});
});




