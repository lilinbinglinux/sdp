$(document).ready(function(){
  initOrgTable();

  // 为datatable外的父级设置高度
  $('#orgstructureTable_wrapper').css('height', $('.panel-body').height()-60);

  // 动态为表格添加父级
  $('#orgstructureTable').wrap('<div class="tab-wrapper"></div>');
  $('.tab-wrapper').css('height', $('#orgstructureTable_wrapper').height()-63);
  $('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});

	$("#org-panel").find(".panel-body").niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
	$("#org-panel").find(".icon-tip").bind({//绑定提示信息
		mouseenter: function(e) {
			// Hover event handler
			this.index = layer.tips("在树节点上右键编辑组织结构节点信息。右键空白区域新建一级节点。", this,{
				 	tips: [2, '#f8e3d1'],
				 	time: 3000
				}
			);
		}
//		mouseout:function(e){
//			// Hover event handler
//			layer.close(this.index);
//		}
	});
	//初始化一级菜单
	var levelOneMenu = new BootstrapMenu('#org-panel>.panel-body', {
		  menuEvent: 'right-click',
		  actions: [{
		      name: '添加一级组织',
		      iconClass: 'glyphicon glyphicon-plus',
		      onClick: function() {
		    	  orgTree.addOrg('','');
		      }
		  }]
	});
	orgTree.initTree();//初始化树
	orgTree.initContextMenu();//初始化菜单
});

var orgStructureList;

var orgTree = {
		treeObj : null,
		setting : {
				data:{
					simpleData: {
						enable:true, 
						idKey:'orgId',
						pIdKey:'parentId'
					},
					key:{
						name:'orgName',
            url:'xurl'
					}
				},
				callback:{
					onRightClick:function(e,treeId,treeNode){
						orgTree.treeObj.selectNode(treeNode);
						$("#orgTreeContextMenu").css("left",e.clientX);
						$("#orgTreeContextMenu").css("top",e.clientY);
						$("#orgTreeContextMenu").show();
				    },
          beforeClick:function(treeId, treeNode, clickFlag){
            return treeNode.isParent;
          },
          onClick: function(event, treeId, treeNode){
            initOrgStructure(treeNode.orgId);
          }
				}
		},
		initTree:function(){
			if(orgTree.treeObj!=null){
				orgTree.treeObj.destroy()
			}
			$.ajax({//初始化组织机构树
				"url":webpath+"/org/selectAll",
				"type":"POST",
				dataType:"json",
				success:function(data){
          orgStructureList = data;
          if(data!=null&&data.length>0){
						orgTree.treeObj = $.fn.zTree.init($("#org-tree"), orgTree.setting, data);
						orgTree.treeObj.expandAll(true);
					}else{
						layer.msg('暂无数据', {time: 1000, icon:5});
					}
				}
			});
		},
		getSelectedNode:function(){
			return orgTree.treeObj.getSelectedNodes()[0]
		},
		initContextMenu:function(){
			$("body").bind({
				 click:function(e){
					 $("#orgTreeContextMenu").hide();
				 }
			});
			$("#orgTreeContextMenu").find(".addOrgBtn").bind({
				click:function(e){
					var node = orgTree.getSelectedNode();
					orgTree.addOrg(node.path, node.orgId);
				}
			});
			$("#orgTreeContextMenu").find(".updateOrgBtn").bind({
				click:function(e){
					var node = orgTree.getSelectedNode();
					orgTree.updateOrg(node);
				}
			});
			$("#orgTreeContextMenu").find(".delOrgBtn").bind({
				click:function(e){
					var node = orgTree.getSelectedNode();
					orgTree.deleteOrg(node.orgId);
				}
			});
		},
		addOrg:function(path,pid){
			var formObj = $("#addOrgForm");
			form.clear(formObj);
			form.cleanValidator(formObj);
			formObj.find("input[name='path']").val(path);
			formObj.find("input[name='parentId']").val(pid);
			layer.open({
	    		type: 1,
	            title:'<i class="iconfont">&#xe641;</i>&nbsp;新增组织结构',
	            area: ['300px', '330px'],
	            content: $("#addOrg"),
	            btn: ['确定','取消'],
	            btn1: function(index, layero){//确定按钮回调
	            	if(form.isValidator(formObj)){
	            		$.ajax({//初始化组织机构树
	        				"url":webpath+"/org/addOrg",
	        				"type":"POST",
	        				dataType:"json",
	        				data:form.serializeJson(formObj),
	        				success:function(data){
	        					layer.close(index);
	        					orgTree.initTree();
                    reloadTableData();
	        				}
	        			});
	            	}
	    	    },
	    	    btn2: function(index, layero){//确定按钮回调
	            	layer.close(index);
	    	    }
	        });
		},
		updateOrg:function(node, escapeflag){
      if(escapeflag){
        for (var i = 0; i < orgStructureList.length; i++) {
          var cur = orgStructureList[i];
          if(node == cur.orgId) {
            var rowData = cur;
            break;
          }
        }
      }
      var formObj = $("#updateOrgForm");
			form.clear(formObj);
			form.cleanValidator(formObj);
			form.load(formObj,node);
      if(escapeflag){
        form.load(formObj,rowData);
			}
			layer.open({
	    		type: 1,
	            title:'<i class="iconfont">&#xe633;</i>&nbsp;修改组织结构',
	            area: ['300px', '330px'],
	            content: $("#updateOrg"),
	            btn: ['确定','取消'],
	            btn1: function(index, layero){//确定按钮回调
	            	if(form.isValidator(formObj)){
	            		$.ajax({//初始化组织机构树
	        				"url":webpath+"/org/updateOrg",
	        				"type":"POST",
	        				dataType:"json",
	        				data:form.serializeJson(formObj),
	        				success:function(data){
	        					layer.close(index);
	        					orgTree.initTree();
                    reloadTableData();
	        				}
	        			});
	            	}
	    	    },
	    	    btn2: function(index, layero){//确定按钮回调
	            	layer.close(index);
	    	    }
	        });
		},
		deleteOrg:function(orgId){
			layer.confirm('删除该节点？（删除后不可恢复）', {
		          icon: 3,
		          btn: ['是','否'] //按钮
		    	  }, function(index, layero){
		    		  $.ajax({//初始化组织机构树
	        				"url":webpath+"/org/deleteByOrgId",
	        				"type":"POST",
	        				dataType:"json",
	        				data:{
	        					orgId:orgId
	        				},
	        				success:function(data){
	        					layer.msg('删除成功！', {time: 1000, icon:1});
	        					layer.close(index);
	        					orgTree.initTree()
                    reloadTableData();
	        				}
	        		   });
		    	  });
		}
}


//初始化组织结构表格
function initOrgTable(){
  $("#orgstructureTable").width("100%").dataTable({
    "columns":[
      { "data": "orgName" },
      { "data": "orgId" }
    ],
    ajax: {
      url:webpath+'/org/selectPage',
      "type": 'POST',
      "data": function (d) {//查询参数
        return $.extend( {}, d, {
          "jsonStr": form.serializeStr($("#orgSearchForm"))
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
        "targets" : 1,//操作按钮目标列
        "data" : null,
        "render" : function(data, type,row) {
          var escapeflag = true;
          var html =  '<a href="javascript:void(0);" onclick="orgTree.updateOrg(\''+row.orgId+'\',\''+escapeflag+'\')" class="icon-wrap" title="编辑"><i class="iconfont i-btn">&#xe66f;</i></a>';
          html += '&nbsp;&nbsp;';
          html +=  '<a href="javascript:void(0);" onclick="orgTree.deleteOrg(\''+row.orgId+'\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
          html += '&nbsp;&nbsp;';
          return html;
        }
      }]
  });
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
  $("#orgstructureTable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

//初始化右侧面板信息
function initOrgStructure(parentId){
  $("#parentId").val(parentId);
  reloadTableData();
}