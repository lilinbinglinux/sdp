$(document).ready(function(){
	initSerTable();
	
	// 为datatable外的父级设置高度
	$('#ssoflowchartTable_wrapper').css('height', $('.panel-body').height()-60);
	// 加载日历框
	$('#datepicker').datepicker({changeMonth: true, changeYear: true});
	
	// 动态为表格添加父级
	$('#ssoflowchartTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#ssoflowchartTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
	
	$("#searchBtn").bind("click",reloadTableData);
//	$("#resetBtn").bind("click",resetForm);
	
	$("#addSpSer").bind("click",addSpSer);
});



//初始化用户列表表格
function initSerTable(){
	$("#ssoflowchartTable").width("100%").dataTable({
		"columns":[
		            { "data": "spname" },
		            { "data": "sptype" },
		            { "data": "spcode" },
		            //{ "data": "sploginid" },
		            { "data": "spagreement" },
		            { "data": "sprestype" },
		            { "data": "creatime" }
		 ],
		 ajax: {
		     url:webpath+'/serspLogin/selectPage',
		     "type": 'POST',
		     "data": function (d) {//查询参数
		           return $.extend( {}, d, {
		              "jsonStr": form.serializeStr($("#userSearchForm"))
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
                "targets": 3,//请求协议
                "data": null,
                "render": function (data, type, row) {
                    var html = '',date = row.spagreement;
                    if(data=="0"){
                        html += 'http';
                    }else if(data=="1"){
                    		html += 'soap';
                    }else if(data=="2"){
                    		html += 'socket';
                    }
                    return html;
                }
            },
            {
                "targets": 4,//请求协议
                "data": null,
                "render": function (data, type, row) {
                    var html = '',date = row.sprestype;
                    if(data=="0"){
                        html += 'GET';
                    }else if(data=="1"){
                    		html += 'POST';
                    }
                    return html;
                }
            },
			{
                "targets": 5,//订阅时间
                "data": null,
                "render": function (data, type, row) {
                    var html = '',date = row.creatime;
                    if(data==null||data==""){
                        html += '暂无时间';
                    }else{
                        html += formatDate(date, "yyyy-MM-dd HH:mm:ss");
                    }
                    return html;
                }
            },
			{  
			  "targets" : 6,//操作按钮目标列
			  "data" : null,
			  "render" : function(data, type,row) {
				  var id = row.sploginid;
				  var spname = row.spname;
				  var serType = row.sptypeId;
				  var url = row.sppath;
				  
				  var html =  '<a href="javascript:void(0);" onclick="updateSer(\'' + id + '\',\'' + spname + '\',\'' + serType + '\')" class="icon-wrap" title="编辑"><i class="iconfont i-btn">&#xe66f;</i></a>';
				      html += '&nbsp;&nbsp;';
				      html +=  '<a href="javascript:void(0);" onclick=paramTips(\'' + id + '\',\'' + url + '\') class="icon-wrap" title="入参提示"><i class="iconfont i-btn">&#xe789;</i></a>';
                      html += '&nbsp;&nbsp;';
				      html +=  '<a href="javascript:void(0);" onclick="deleteSer(\''+id+'\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
				      html += '&nbsp;&nbsp;';
//				      html +=  '<a href="javascript:void(0);" onclick="resetPasswd(\''+id+'\')" class="icon-wrap" title="重置密码"><i class="iconfont i-btn">&#xe637;</i></a>';
//				      html += '&nbsp;&nbsp;';
//				      html +=  '<a href="javascript:void(0);" onclick="roleAuth(\''+id+'\')" class="icon-wrap" title="角色授权"><i class="iconfont i-btn">&#xe646;</i></a>';
				      return html;
			   }
		}]
	});
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
	$("#ssoflowchartTable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

//添加流程图
function addSpSer(){
	layer.open({
		  type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 0, //不显示关闭按钮
		  anim: 2,
		  btn: ["确认", "取消"],
          area: ['350px', '180px'],
		  shadeClose: true, //开启遮罩关闭
		  content: $("#sptypediv"),
		  btn1:function(){
			  location.href = webpath + "/serprocess/index?serFlowType="+$("#sptype").val();
		  },
		  btn2:function(){
			  layer.close();
		  }
		});
	
}

//修改数据
function updateSer(serId,serName,serType){
	 var url = webpath + "/serprocess/index?id=" + serId
	    + '&serFlowType=' + serType;
	 location.href = url;
}

//删除数据
function deleteSer(sploginid){
	layer.confirm('删除该服务？（删除后不可恢复）', {
        icon: 3,
        btn: ['是','否'] //按钮
  	  }, function(index, layero){
  		  $.ajax({//初始化组织机构树
  				"url":webpath+"/serspLogin/delete",
  				"type":"POST",
  				dataType:"json",
  				data:{
  					sploginid:sploginid
  				},
  				success:function(data){
  					layer.close(index);
					reloadTableData(true);
  				}
  		   });
  	  });
}


function paramTips(id){
    layer.open({
        type: 1,
        title: '示例参数',
        area: ['620px', '500px'],
        content: $("#interfaceinfodiv"),
        shade: 0.1,
        success: function (layero, index) {
        		var url = getUrl(id);
			$("#urlreqparam-url").html(url);
            var data = getParam(id);
            $("#urlreqparam-code").html(data.inputparam);
            $("#urlresponseparam-code").html(data.outputparam);
        }
    });
	//location.href = webpath+'/interfaceOrder/indexMineOrderParam';
}

function getParam(id){
	var datas = {
			"inputparam":"",
			"outputparam":""
	};
    $.ajax({
        url : webpath+'/serspLogin/getParams',
        type: 'POST',
        async: false,
        data:{
            "sploginid":id,
        },
        success:function(data){
        		datas.inputparam = data.inputParam;  
        		datas.outputparam = data.outputParam;  
        }
    });
	return datas;
}

function getUrl(id){
	var url;
	 $.ajax({
	        url : webpath+'/serspLogin/getUrl',
	        type: 'POST',
	        async: false,
	        data:{
	            "sploginid":id,
	        },
	        success:function(data){
	        		url = data;
	        }
	    });
	 return url;
}

//重置查询条件
function resetForm(){
	form.clear($("#userSearchForm"));
}
