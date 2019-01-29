var y;
var win_y;
function comExtraInfo(obj){
	var comExtraInfo = $(obj).attr('comExtraInfo');
	var op = win_y/y;
	if(win_y/y > 1.8){
		layer.tips('<div class="more">'+comExtraInfo+'</div>', $(obj) ,{
			  tips: [4, 'rgba(220,0,0,.7)'],area: ['800px', 'auto'],time:60000}
		);
	}
	if(win_y/y < 1.8){
		layer.tips('<div class="more">'+comExtraInfo+'</div>', $(obj) ,{
			  tips: [1, 'rgba(220,0,0,.7)'],area: ['800px', 'auto'],time:60000}
		);
	}
}

function receiverInfo(obj){
	var comExtraInfo = $(obj).attr('comExtraInfo');
	layer.tips('<div class="receiverInfo">'+comExtraInfo+'</div>', $(obj) ,{
		  tips: [3, 'rgba(220,0,0,.7)'],area: ['200px', 'auto'],time:60000}
	);

}
function contentInfo(obj){
	var comExtraInfo = $(obj).attr('comExtraInfo');
	layer.tips('<div class="contentInfo">'+comExtraInfo+'</div>', $(obj) ,{
		  tips: [3, 'rgba(220,0,0,.7)'],area: ['400px', 'auto'],time:60000}
	);
}
function ExtraInfo(obj){
	layer.closeAll('tips');
}
$(document).ready(function () {
	//搜索
	$("#searchBtn").click(function(e) {
		table.api().ajax.reload(); 
	});
	//清空
	$("#resetBtn").click(function(e) {
		$("#appName").val("");
	    $("#sendType").val("");
	    $("#startTime").val("");
	    $("#endTime").val("");
	});	
	

	var table = $('#operateLog_table').dataTable({
	 	/*"aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0 ,3] }],*/
	    /*fixedHeader: {
	        header: true
	    },*/
	    "dom": '<<t>ilp>',
	    "pagingType": "simple_numbers",//设置分页控件的模式
        "processing": true,
        "serverSide": true,
        "ordering":false,
        /* 分页设置 */
        "bPaginate": true,
        "bLengthChange": true,
        /* 搜索设置 */
        "bFilter": false,
        "bSort": false,
        /* 显示总条数 */
        "bInfo": true,
        "bAutoWidth": false,
        "language": {
            "paginate": {
                "previous": "首页",
                "next": "下一页"
            },
            "info": "显示_START_到_END_, 共计_TOTAL_条数据",
            "emptyTable": "无记录",
            "infoEmpty": "共计0",
             "lengthMenu": "每页显示 _MENU_ 记录",
            // "infoFiltered": "",
            // "zeroRecords": "没有找到相关内容",
            // "search": "搜索 : "
        },
        "ajax":{
         	"url": ctx+"rest/api/log/pager",
         	"data":function(d){
         		var appname = $("#appName").val();
         		var startTime = $("#startTime").val();
         		var endTime = $("#endTime").val();
         		var type = $("#sendType").val();
         		d.appname=appname;
         		d.startTime=startTime,
         		d.endTime=endTime,
         		d.type=type
         	}
         },
        //序号
        "fnDrawCallback" : function(){
				                		this.api().column(0).nodes().each(function(cell, i) {
				                			cell.innerHTML =  i + 1;
				                		});
				                },
        "columns": [
					{   
						data : null,
						width : "5%"
					},
					{   
                    	data : "appname",
                    	width : "8%",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "";
                    		}
                    		return data;
                    	}	
                    },
            		{   
                    	data : "sender",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "";
                    		}
                    		return data;
                    	}	
                    },
                    {   
                    	data : "receiver",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "";
                    		}else if(data.indexOf(',') == -1){
                    			return "<a onmouseover='receiverInfo(this)' onmouseout='ExtraInfo(this)' comExtraInfo='"+data.substring(1,data.lastIndexOf(']'))+"'>"+data.substring(1,data.lastIndexOf(']'))+"</a>";
                    		}
                    		var aa =data.indexOf(',');
                    		return "<a onmouseover='receiverInfo(this)' onmouseout='ExtraInfo(this)' comExtraInfo='"+data.substring(1,data.lastIndexOf(']'))+"'>"+data.substring(1,8)+"..</a>";
                    	}	
                    },
                    {   
                    	data : "content",
                    	width : "12%",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "";
                    		}
                    		return '<a onmouseover="contentInfo(this)" onmouseout="ExtraInfo(this)" comExtraInfo="'+data+'">'+data.substring(0,10)+'..</a>';
                    	}	
                    },
                    {   
                    	data : "type",
                    	width : "5%",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "<a class='a-data-type'></a>";
                    		}if(data == "shortMessage"){
                    			return "<a class='a-data-type'>短信</a>"
                    		}else if(data == "email"){
                    			return "<a class='a-data-type'>Email</a>"
                    		}
                    		return data;
                    	}	
                    },
                    {   
                    	data : "sendtime",
                    	width : "12%",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "";
                    		}
                    		return data;
                    	}	
                    },
                    {   
                    	data : "errorlog",
                		render : function ( data, type, row ) {
                    		if (data == null || data == "") {
                    			return "";
                    		}
                    		return "<a onmouseover='comExtraInfo(this)' onmouseout='ExtraInfo(this)' comExtraInfo='"+data.substring(0,1500)+"'>"+data.substring(0,50)+"..</a>";
                    	}	
                    }
                   ]
	});
	setTimeout(() => { win_y = document.body.clientHeight;},100);
	function mousePosition(ev){    
		if(ev.pageX || ev.pageY){      
			return {x:ev.pageX, y:ev.pageY};    
		}    
		return {x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,y:ev.clientY + document.body.scrollTop - document.body.clientTop}; 
	} 
	document.onmousemove = mouseMove; 
	function mouseMove(ev){  
		ev = ev || window.event;  
		var mousePos = mousePosition(ev);    
		y = mousePos.y; 
	} 
});
