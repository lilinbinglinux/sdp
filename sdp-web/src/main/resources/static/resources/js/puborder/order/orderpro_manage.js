$(document).ready(function(){
	
	//页面打开合并操作
	$("#flowchart-toggleBar").click(function(){
		if($(this).attr("isOpen") == "true"){
			$(".changeopen").css("display","none");
			$("#flowchart-leftContent").attr("style","width:10%");
			$("#flowchart-rightContent").attr("style","width:90%");
			$(this).attr("isOpen","false");
		}else if($(this).attr("isOpen") == "false"){
			$(".changeopen").css("display","inline");
			$("#flowchart-leftContent").attr("style","width:34%");
			$("#flowchart-rightContent").attr("style","width:66%");
			$("#proTree").attr("style","overflow: auto;height: 500px");
			$(this).attr("isOpen","true");
		}
	})
	
	var protype = "";
	resource.initTree();//初始化树
});

var resource = {
		treeObj:null,
		setting : {
			data:{
				simpleData: {
					enable:true, 
					idKey:'id',
					pIdKey:'parentId'
				},
				key:{
					name:'name'
				}
			},
			callback:{
			    onClick:function(e,treeId,treeNode){
			    	$("#projectmanagepubid").val(" ");
			    	if(!treeNode) return;
					resource.treeObj.selectNode(treeNode);
					$(".bootstrapMenu").hide();
					
					//操作根节点0
					if(treeNode.typeId=="root"){
						$("#order-manageall").attr("src","../orderIfream/orderproject");
						setTimeout(function(){
							$("#order-manageall").contents().find("#projectmanagepubid").val("root");
							document.getElementById("order-manageall").contentWindow.initurlprojecttableTable();
			    		},400);
						
					//操作项目
					}else if(treeNode.typeId=="0"){
						var nodespro = resource.treeObj.getSelectedNodes();
						$("#order-manageall").attr("src","../orderIfream/orderproject");
						setTimeout(function(){
							$("#order-manageall").contents().find("#projectmanagepubid").val(nodespro[0].id);
							$("#order-manageall").contents().find("#projectmanagepubid2").val(nodespro[0].typeId);
							
							document.getElementById("order-manageall").contentWindow.initurlprojecttableTable();
							var isTableNull = document.getElementById("order-manageall").contentWindow.tableIsNull();
							//当没有模块时，自动加载项目接口
							if(isTableNull){
								$("#order-manageall").attr("src","../orderIfream/publishifream");
								setTimeout(function(){
									$("#order-manageall").contents().find("#publishmanagepubid").val(nodespro[0].id);
									$("#order-manageall").contents().find("#flowmanagepubid").val(null);
									document.getElementById("order-manageall").contentWindow.initurlpublishtableTable();
					    		},400);
								
							}
							
			    		},400);
						
						
					//操作模块
					}else if(treeNode.typeId=="1"){
						var nodespub = resource.treeObj.getSelectedNodes();
						$("#order-manageall").attr("src","../orderIfream/publishifream");
						setTimeout(function(){
							$("#order-manageall").contents().find("#publishmanagepubid").val(nodespub[0].id);
							$("#order-manageall").contents().find("#flowmanagepubid").val(null);
							document.getElementById("order-manageall").contentWindow.initurlpublishtableTable();
			    		},400);
					
					//操作接口
					}else if(treeNode.typeId=="2"){
						var nodes = resource.treeObj.getSelectedNodes();
						$("#order-manageall").attr("src","../orderIfream/orderparamifream");
						setTimeout(function(){
							$("#order-manageall").contents().find("#reqmanagepubid").val(nodes[0].id);
							document.getElementById("order-manageall").contentWindow.initParamTable();
			    		},400);
						
					//操作编排后的服务
					}else if(treeNode.typeId=="3"){
						var nodesflow = resource.treeObj.getSelectedNodes();
						$("#order-manageall").attr("src","../orderIfream/publishifream");
						setTimeout(function(){
							$("#order-manageall").contents().find("#publishmanagepubid").val(null);
							$("#order-manageall").contents().find("#flowmanagepubid").val(nodesflow[0].id);
							document.getElementById("order-manageall").contentWindow.initurlpublishtableTable();
			    		},400);
					}
			    }
			}
	    },
		initTree:function(){
			if(resource.treeObj!=null){
				resource.treeObj.destroy()
			}
			$.ajax({//初始化组织机构树
				"url":webpath+"/interfacePo/selectAll",
				"type":"POST",
				dataType:"json",
				success:function(data){
					if(data!=null&&data.length>0){
						for(var i=0;i<data.length;i++){
							data[i].icon=webpath+resourceTypeIcon[data[i].typeId];
							if(data[i].typeId == "root"){
								data[i].open = "true";
							}
						}
						resource.treeObj = $.fn.zTree.init($("#proTree"), resource.setting, data);
						//resource.treeObj.expandAll(false);
					}else{
						layer.msg('暂无数据', {time: 1000, icon:5});
					}
				}
			});
		}
};

var resourceTypeIcon = {
		"0":"/resources/img/icon/16x16/floder1-org.png",
		"1":"/resources/img/icon/16x16/fun-red.png",
		"2":"/resources/img/icon/16x16/resorce.png",
		"3":"/resources/img/icon/16x16/resorce.png",
		"4":"/resources/img/icon/16x16/resorce.png",
		"root":"/resources/img/icon/16x16/folder-red.png",
		"root1":"/resources/img/icon/16x16/folder-red.png",
};

//订阅接口方法
function orderInsert(){
	var nodes = resource.treeObj.getSelectedNodes();
	console.log(nodes[0].id);
	
	var name = $("#name").val();
	var ordercode = $("#ordercode").val();
	var protocal = $("#protocal").val();
	var url = $("#url").val();
	var format = $("#format").val();
	var orderdesc = $("#orderdesc").val();
	var pubapiId;
	
	$.ajax({
		"url":webpath+"/interfaceOrder/insertorderinterface",
		"type":"POST",
		dataType:"json",
//		data:$('#interOrderForm').serialize(),              question：怎么提交表单的同时提交一组json
		data:{
			"name":name,
			"ordercode":ordercode,
			"protocal":protocal,
			"url":url,
			"format":format,
			"orderdesc":orderdesc,
			"pubapiId":nodes[0].id,
		},
		async: false,
		success: function(data) {
        },
        error:function(data){
        	alert("新增失败！");
        },
	});
}

function showApplyInterface(id){
	$("#order-manageall").attr("src","../orderIfream/orderIframe?id="+id);
}

