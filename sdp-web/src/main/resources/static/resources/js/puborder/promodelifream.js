$(document).ready(function(){
	setTimeout(function(){
		parent.protableinfo = $("#id-proinfotable");
		initProinfoTable();
		$("#id-addproinfo").on("click", function() {
			$("#modaltype").val("");
			$("#promname").val("");
			$("#promecode").val("");
			$('#proModelModal').modal();
		});
	},
	305);
});

//初始化用户列表表格
function initProinfoTable(){
	$("#id-proinfotable").width("100%").dataTable({
		"columns":[
		           { "data": "proid" },
		           { "data": "name" },
		           { "data": "proecode" },
		           { "data": "createdate" },
		           { "data": "" }
		 ],
		 ajax: {
		     url:webpath+'/pro/selectPage',
		     "type": 'POST',
		     "data": function (d) {//查询参数
		           return $.extend( {}, d, {
		              "jsonStr": form.serializeStr($("#id-promodelintype"))
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
			"targets" : 0,
			"data" : null,
			"visible": false
		},        
		{
			"targets" : 1,
			"data" : null,
			"visible": true
		},
		{
			"targets" : 2,//操作按钮目标列
			"data" : null
			
		},
		{
			"targets" : 3,//操作按钮目标列
			"data" : null,
			"visible": true,
			"render" : function(data, type,row){
                var html = '';
                var newDate = new Date();
                newDate.setTime(row.createdate);
                if (data == null || data == "") {
                    html += '暂无时间';
                } else {
                    html += newDate.toLocaleString();
                }
                return html;
			}
		},
		{
			  "targets" : 4,//操作按钮目标列
			  "data" : null,
			  "render" : function(data, type,row) {
				  var id = row.proid;
				  var html =  '<a href="javascript:void(0);" onclick="updatePro(\''+id+'\')" class="icon-wrap" title="编辑"><i class="iconfont i-btn" data-toggle="modal" data-target="#proModelModal">&#xe66f;</i></a>';
				      html += '&nbsp;&nbsp;';
				      html +=  '<a href="javascript:void(0);" onclick="deletePro(\''+id+'\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
				      html += '&nbsp;&nbsp;';
				      return html;
			   }
				 
		}
	]
	});
}

function updatePro(proid){
	$.ajax({
		"url":webpath+"/pro/getProById",
		"type":"POST",
		dataType:"json",
		data:{
			"id":proid
		},
		async:false, 
		success:function(data){
			$("#modaltype").attr("value","update");
			$("#id-proModelinfoupdate").attr("value",data.proid);
			$("#promname").val(data.name);
			$("#promecode").val(data.proecode);
		}
   });
}

function deletePro(proid){
	layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是','否'] //按钮
  	  }, function(index, layero){
  		  $.ajax({
  				"url":webpath+"/interfacePo/delete",
  				"type":"POST",
  				data:{
  					"id":proid,
  					"typeId":"1"
  				},
  				success:function(data){
  					if(data == "deletesuccess"){
  						layer.msg('删除成功！', {time: 1000, icon:1});
  	  					layer.close(index);
  	  					//刷新项目信息表
  	  					reloadTableData($("#id-proinfotable"));
  	  					var res = parent.resource;
  	  					res.initTree();
  					}else if(data == "deletefalse"){
  						layer.msg('删除失败，请先删除子节点！', {time: 1000, icon:1});
  	  					layer.close(index);
  					}
  					
  				}
  		   });
  	  })
}
