$(document).ready(function(){
	initSerTable();
	// 为datatable外的父级设置高度
	$('#resiframeTable_wrapper').css('height', $('.panel-body').height()-60);
	// 加载日历框
	$('#datepicker').datepicker({changeMonth: true, changeYear: true});
	
	// 动态为表格添加父级
	$('#resiframeTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#resiframeTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
	
	$("#resources-panel").find(".panel-body").niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
	$("#resources-panel").find(".icon-tip").bind({//绑定提示信息
		mouseenter: function(e) {
			// Hover event handler
			this.index = layer.tips("在资源节点右键任意添加资源。", this,{
				 	tips: [2, '#f8e3d1'],
				 	time: 3000
				}
			);
		}
	});
	
	//绑定右键事件
	$("#resources-panel").find(".panel-body").bind({
		contextmenu: function(e) {
			    var nodes = resource.treeObj.getSelectedNodes();
			    if (nodes.length>0) { 
			    	resource.treeObj.cancelSelectedNode(nodes[0]);
			    }
			    $(".bootstrapMenu").hide();
				$("#blankContextMenu").css("left",e.clientX);
				$("#blankContextMenu").css("top",e.clientY);
				$("#blankContextMenu").show();
				return false;
		}
	});
	//取消菜单事件
	$("body").bind({
		 click:function(e){
			 $(".bootstrapMenu").hide();
		 }
	});
	//添加文件夹事件
	$(".addFolder").bind({
		click:function(){
			var resourcesTypeId ="0",parentId="ROOT";
			var formObj = $("#addResourceForm");
			form.clear(formObj);
			form.cleanValidator(formObj);
			var nodes = resource.treeObj.getSelectedNodes();
			if(nodes.length>0){
				var node = nodes[0];
				formObj.find('input[name="parentId"]').val(node.resourcesId);
				formObj.find('input[name="path"]').val(node.path);
			}else{
				formObj.find('input[name="parentId"]').val(parentId);
			}
			formObj.find('input[name="resourcesTypeId"]').val(resourcesTypeId);
			formObj.find('input[name="url"]').prop("readOnly","readOnly");
			layer.open({
				type: 1,
		        title:'<i class="iconfont">&#xe641;</i>&nbsp;新增文件夹',
		        area: ['340px', '400px'],
		        content: $("#addResource"),
		        btn: ['确定','取消'],
		        btn1: function(index, layero){//确定按钮回调
		        	//处理解析&时不好用的问题
		        	var ext = $('#addResourceForm input[name="ext1"]').val();
		        	var newData = form.serializeJson(formObj);
		        	newData.ext1 = ext;
		        	if(form.isValidator(formObj)){
		        		$.ajax({
		    				"url":webpath+"/resource/insert",
		    				"type":"POST",
		    				dataType:"json",
		    				data:newData,
		    				success:function(data){
		    					layer.close(index);
		    					resource.initTree();
		    					reloadTableData();
		    					formObj.find('input[name="url"]').prop("readOnly","");
		    				}
		    			});
		        	}
			    },
			    btn2: function(index, layero){//取消按钮回调
		        	layer.close(index);
		        	formObj.find('input[name="url"]').prop("readOnly","");
			    },
			    cancel:function(index){
			    	formObj.find('input[name="url"]').prop("readOnly","");
			    	return true;
			    }
		    });
		}
	});
	//添加内连接事件
	$(".addInnerLink").bind({
		click:function(){
			var resourcesTypeId ="1",parentId="ROOT";
			var formObj = $("#addResourceForm");
			form.clear(formObj);
			form.cleanValidator(formObj);
			var nodes = resource.treeObj.getSelectedNodes();
			if(nodes.length>0){
				var node = nodes[0];
				formObj.find('input[name="parentId"]').val(node.resourcesId);
				formObj.find('input[name="path"]').val(node.path);
			}else{
				formObj.find('input[name="parentId"]').val(parentId);
			}
			formObj.find('input[name="resourcesTypeId"]').val(resourcesTypeId);
			layer.open({
				type: 1,
		        title:'<i class="iconfont">&#xe641;</i>&nbsp;新增内连接',
		        area: ['340px', '400px'],
		        content: $("#addResource"),
		        btn: ['确定','取消'],
		        btn1: function(index, layero){//确定按钮回调
		        	//处理解析&时不好用的问题
		        	var ext = $('#addResourceForm input[name="ext1"]').val();
		        	var newData = form.serializeJson(formObj);
		        	newData.ext1 = ext;
		        	if(form.isValidator(formObj)){
		        		$.ajax({
		    				"url":webpath+"/resource/insert",
		    				"type":"POST",
		    				dataType:"json",
		    				data:newData,
		    				success:function(data){
		    					layer.close(index);
		    					resource.initTree();
		    					reloadTableData();
		    				}
		    			});
		        	}
			    },
			    btn2: function(index, layero){//取消按钮回调
		        	layer.close(index);
			    }
		    });
		}
	});
	//添加外链接事件
	$(".addOuterLink").bind({
		click:function(){
			var resourcesTypeId ="2",parentId="ROOT";
			var formObj = $("#addResourceForm");
			form.clear(formObj);
			form.cleanValidator(formObj);
			var nodes = resource.treeObj.getSelectedNodes();
			if(nodes.length>0){
				var node = nodes[0];
				formObj.find('input[name="parentId"]').val(node.resourcesId);
				formObj.find('input[name="path"]').val(node.path);
			}else{
				formObj.find('input[name="parentId"]').val(parentId);
			}
			formObj.find('input[name="resourcesTypeId"]').val(resourcesTypeId);
			formObj.find('input[name="url"]').val("http://");
			layer.open({
				type: 1,
		        title:'<i class="iconfont">&#xe641;</i>&nbsp;新增外连接',
		        area: ['340px', '400px'],
		        content: $("#addResource"),
		        btn: ['确定','取消'],
		        btn1: function(index, layero){//确定按钮回调
		        	//处理解析&时不好用的问题
		        	var ext = $('#addResourceForm input[name="ext1"]').val();
		        	var newData = form.serializeJson(formObj);
		        	newData.ext1 = ext;
		        	if(form.isValidator(formObj)){
		        		$.ajax({
		    				"url":webpath+"/resource/insert",
		    				"type":"POST",
		    				dataType:"json",
		    				data:newData,
		    				success:function(data){
		    					layer.close(index);
		    					resource.initTree();
		    					reloadTableData();
		    				}
		    			});
		        	}
			    },
			    btn2: function(index, layero){//取消按钮回调
		        	layer.close(index);
			    }
		    });
		}
	});
	
	
	//添加功能
	$(".addFun").bind({
		click:function(){
			var resourcesTypeId ="3",parentId="ROOT";
			var formObj = $("#addResourceForm");
			form.clear(formObj);
			form.cleanValidator(formObj);
			var nodes = resource.treeObj.getSelectedNodes();
			if(nodes.length>0){
				var node = nodes[0];
				formObj.find('input[name="parentId"]').val(node.resourcesId);
				formObj.find('input[name="path"]').val(node.path);
				formObj.find('input[name="url"]').val(node.url);
			}else{
				formObj.find('input[name="parentId"]').val(parentId);
			}
			formObj.find('input[name="resourcesTypeId"]').val(resourcesTypeId);
			layer.open({
				type: 1,
		        title:'<i class="iconfont">&#xe641;</i>&nbsp;新增功能',
		        area: ['340px', '400px'],
		        content: $("#addResource"),
		        btn: ['确定','取消'],
		        btn1: function(index, layero){//确定按钮回调
		        	if(form.isValidator(formObj)){
		        		$.ajax({
		    				"url":webpath+"/resource/insert",
		    				"type":"POST",
		    				dataType:"json",
		    				data:form.serializeJson(formObj),
		    				success:function(data){
		    					layer.msg('添加成功！', {time: 1000, icon:1});
		    					layer.close(index);
		    					resource.initTree();
		    					reloadTableData();
		    				}
		    			});
		        	}
			    },
			    btn2: function(index, layero){//取消按钮回调
		        	layer.close(index);
			    }
		    });
		}
	});
	//删除事件绑定
	$(".deleteResource").bind({
		click:function(){
			var nodes = resource.treeObj.getSelectedNodes();
			if(nodes.length==0)return;
			var node = nodes[0];
			deleteRes(node.resourcesId);
		}
	});
	//修改资源事件
	$(".updateResource").bind({
		click:function(){
			var nodes = resource.treeObj.getSelectedNodes();
			if(nodes.length==0)return;
			var node = nodes[0];
			var item = JSON.stringify(node); 
			 var escapeflag = false;
			updateRes(item,escapeflag);
		}
			
			
	});
	
	resource.initTree();//初始化树
});


var resource = {
		treeObj:null,
		setting : {
			data:{
				simpleData: {
					enable:true, 
					idKey:'resourcesId',
					pIdKey:'parentId'
				},
				key:{
					name:'resourcesName',
					url:'xurl'
				}
			},
			callback:{
				onRightClick:function(e,treeId,treeNode){
					if(!treeNode) return;
					resource.treeObj.selectNode(treeNode);
					$(".bootstrapMenu").hide();
					if(treeNode.resourcesTypeId=="0"){
						$("#folderContextMenu").css("left",e.clientX);
						$("#folderContextMenu").css("top",e.clientY);
						$("#folderContextMenu").show();
					}else if(treeNode.resourcesTypeId=="1"){
						$("#linkContextMenu").css("left",e.clientX);
						$("#linkContextMenu").css("top",e.clientY);
						$("#linkContextMenu").show();
					}else if(treeNode.resourcesTypeId=="2"){
						$("#link1ContextMenu").css("left",e.clientX);
						$("#link1ContextMenu").css("top",e.clientY);
						$("#link1ContextMenu").show();
					}else if(treeNode.resourcesTypeId=="3"){
						$("#link1ContextMenu").css("left",e.clientX);
						$("#link1ContextMenu").css("top",e.clientY);
						$("#link1ContextMenu").show();
					}
			    },
			    beforeClick:function(treeId, treeNode, clickFlag){
			    		return treeNode.isParent;
			    },
			    onClick: function(event, treeId, treeNode){
			    		initResIframe(treeNode.id);
			    }
			}
	    },
		initTree:function(){
			if(resource.treeObj!=null){
				resource.treeObj.destroy()
			}
			$.ajax({//初始化组织机构树
				"url":webpath+"/resource/selectAll",
				"type":"POST",
				dataType:"json",
				success:function(data){
					if(data!=null&&data.length>0){
						for(var i=0;i<data.length;i++){
							data[i].icon=webpath+resourceTypeIcon[data[i].resourcesTypeId];
						}
						resource.treeObj = $.fn.zTree.init($("#resources-tree"), resource.setting, data);
						resource.treeObj.expandAll(true);
					}else{
						layer.msg('暂无数据', {time: 1000, icon:5});
					}
				}
			});
		}
};

var resourceTypeIcon = {
		"0":"/resources/img/icon/16x16/floder1-org.png",
		"1":"/resources/img/icon/16x16/resorce.png",
		"2":"/resources/img/icon/16x16/resorce.png",
		"3":"/resources/img/icon/16x16/fun-black.png",
};


//初始化用户列表表格
function initSerTable(){
	$("#resiframeTable").width("100%").dataTable({
		"columns":[
		            { "data": "resourcesName" },
		            { "data": "resourcesTypeName" },
		            { "data": "url" },
		            { "data": "createId" },
		 ],
		 ajax: {
		     url:webpath+'/resource/selectPage',
		     "type": 'POST',
		     "data": function (d) {//查询参数
		           return $.extend( {}, d, {
		        	   "jsonStr": form.serializeStr($("#resSearchForm"))
		           });
		     },
		     "dataSrc": function (json) {
		           json.iTotalRecords = json.total;
		           json.iTotalDisplayRecords = json.total;
		           return json.data;
		     }
		},
		columnDefs:[
			{  
			  "targets" : 3,//操作按钮目标列
			  "data" : null,
			  "render" : function(data, type,row) {
				  var typeId = row.resourcesTypeId;
				  var parentId = row.parentId;
				  var item = JSON.stringify(row); 
				  item = escape(item);
				  var escapeflag = true;
				  var html =  '<a href="javascript:void(0);" onclick="updateRes(\''+item+'\',\''+escapeflag+'\')" class="icon-wrap updateResource" title="编辑"><i class="iconfont i-btn">&#xe66f;</i></a>';
				      html += '&nbsp;&nbsp;';
				      html +=  '<a href="javascript:void(0);" onclick="deleteRes(\''+row.id+'\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
				      html += '&nbsp;&nbsp;';
				 return html;
			   }
		}]
	});
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
	$("#resiframeTable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
	//initSerTable();
}

//初始化右侧面板信息
function initResIframe(parentId){
	$("#parentId").val(parentId);
	reloadTableData();
}

//更新资源
function updateRes(item,escapeflag){
	if(escapeflag){
		item = unescape(item);
	}
	var itemobj = $.parseJSON(item); 
		var formObj = $("#updateResourceForm");
		form.clear(formObj);
		form.cleanValidator(formObj);
		form.load(formObj,itemobj);
		if(itemobj.resourcesTypeId=="0"){
			formObj.find('input[name="url"]').prop("readOnly","readOnly");
		}
		if(parentId == null){				
			formObj.find('input[name="parentId"]').val('ROOT');
		}else{
			formObj.find('input[name="parentId"]').val(itemobj.parentId);
		}
		layer.open({
			type: 1,
	        title:'<i class="iconfont">&#xe631;</i>&nbsp;修改资源',
	        area: ['340px', '400px'],
	        content: $("#updateResource"),
	        btn: ['确定','取消'],
	        btn1: function(index, layero){//确定按钮回调
	        	//处理解析&时不好用的问题
	        	var ext = $('#updateResourceForm input[name="ext1"]').val();
	        	var newData = form.serializeJson(formObj);
	        	newData.ext1 = ext;
	        	if(form.isValidator(formObj)){
	        		$.ajax({
	    				"url":webpath+"/resource/update",
	    				"type":"POST",
	    				dataType:"json",
	    				data:newData,
	    				success:function(data){
	    					layer.msg('修改成功！', {time: 1000, icon:1});
	    					layer.close(index);
	    					resource.initTree();
	    					reloadTableData();
	    					formObj.find('input[name="url"]').prop("readOnly","");
	    				}
	    			});
	        	}
		    },
		    btn2: function(index, layero){//取消按钮回调
	        	layer.close(index);
	        	formObj.find('input[name="url"]').prop("readOnly","");
		    }
	    });
}


function deleteRes(resourcesId){
	layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是','否'] //按钮
  	  }, function(index, layero){
  		  $.ajax({
  				"url":webpath+"/resource/delete",
  				"type":"POST",
  				dataType:"json",
  				data:{
  					id:resourcesId
  				},
  				success:function(data){
  					layer.msg('删除成功！', {time: 1000, icon:1});
  					layer.close(index);
  					resource.initTree();
  					reloadTableData();
  				}
  		   });
  	  });
}