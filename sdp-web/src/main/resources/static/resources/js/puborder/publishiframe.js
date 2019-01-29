$(document).ready(function(){
	setTimeout(function(){
		initPublishTable();
	},305);
	
	//添加服务
	$("#id-addpublishinfo").click(function(){
		var parentId = $("#publishparentId").val();
		index = layer.open({
		        type: 1,
		        shade: 0.3,
		        btn: ["确认", "取消"],
		        area: ['70%', '60%'],
		        content: $("#flowChartModal"),
		        yes: function (index, layero) {
		        	
		        	var idflowChartId = getuuid();
		        	//flowchartmodel.js中方法
		        	addInterface(idflowChartId,parentId);
		        	
		        	reloadTableData(true);
		        	layer.close(index);
		        }
		    });
	})
	
});


//初始化用户列表表格
function initPublishTable(){
	$("#id-pubinfotable").width("100%").dataTable({
		"columns":[
		           { "data": "pubid" },
		           { "data": "name" },
		           { "data": "createdate" },
		           { "data": "pubdesc" },
		           { "data": "" }
		 ],
		 ajax: {
		     url:webpath+'/publisher/selectPage',
		     "type": 'POST',
		     "data": function (d) {//查询参数
		           return $.extend( {}, d, {
		              "jsonStr": form.serializeStr($("#id-publishsearchForm"))
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
			"targets" : 0,//id
			"data" : null,
			"visible": false,
		},  
		{
			"targets" : 1,//name
			"data" : null,
			"visible": true,
		},
		{
			"targets" : 2,//createdate
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
			"targets" : 3,//pubdesc
			"data" : null,
			"visible": true,
		},
		{
			  "targets" : 4,//操作按钮目标列
			  "data" : null,
			  "render" : function(data, type,row) {
				  var id = row.pubid;
				  var html =  '<a href="javascript:void(0);" onclick="updateUrl(\''+id+'\')" class="icon-wrap" title="编辑"><i class="iconfont i-btn">&#xe66f;</i></a>';
				      html += '&nbsp;&nbsp;';
				      html +=  '<a href="javascript:void(0);" onclick="layoutSerlayer(\''+id+'\')" class="icon-wrap" title="编排"><i class="iconfont i-btn">&#xe625;</i></a>';
				      html += '&nbsp;&nbsp;';
				      html +=  '<a href="javascript:void(0);" onclick="deleteUrl(\''+id+'\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
				      html += '&nbsp;&nbsp;';
				      return html;
			   }
		}
	]
	});
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
	$("#id-pubinfotable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}


//重置查询条件
function resetForm(){
	form.clear($("#proSearchForm"));
}

//添加角色
function addUrl(){
	location.href = webpath+'/pub/insert';
}

//修改租户
function updateUrl(id){
	location.href = webpath+'/urlModel/update?id='+id+"&type=update";
}

function layoutSerlayer(id){
	
	index = parent.layer.open({
        type: 1,
        shade: 0.3,
        maxmin: true,
        //offset: ['-100px', '-100px'],
        //btn: ["确认", "取消"],
        area: ['100%', '50%'],
        content: $("#processlayer"),
        zIndex :19891015,
        yes: function (index, layero) {
        	
        }
    });
	
}


//删除服务
function deleteUrl(id){
	layer.confirm('删除该服务？（删除后不可恢复）', {
        icon: 3,
        btn: ['是','否'] //按钮
  	  }, function(index, layero){
  		  $.ajax({//初始化组织机构树
  				"url":webpath+"/publisher/delete",
  				"type":"POST",
  				dataType:"json",
  				data:{
                     pubid:id
  				},
  				success:function(data){
  					layer.close(index);
					reloadTableData(true);
  				}
  		   });
  	  });
}





