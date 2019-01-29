var manager,statusManager,detaileManager,urltype,ordertype,rMenu,orderlayer, countSend,statusLayer;
$(function (){
    rMenu = $("#rMenu");
	height = $(window).height();
	$("#form1").ligerForm();
    $("#form3").ligerForm();
	manager = $("#maingrid").ligerGrid(
			{
				columns : [
						{
							display : '服务名称',
							type : 'String',
							align : 'center',
							name : 'ser_name',
							id : 'orderName'
						},
						{
							display : '日期时间',
							type : 'String',
							align : 'center',
							name : 'sendDate'
						},
						{
							display : '发送总量',
							type : 'String',
							align : 'center',
							name : 'sendCount'
						},
						{
							display : '发送成功数量',
							type : 'String',
							align : 'center',
							name : 'successCount'
						}, 
						{
							display : '发送失败数量',
							type : 'String',
							align : 'center',
							name : 'failCount'
						},
                        {
                            display : '操作',
                            type : 'String',
                            align : 'center',
                            name : 'responseMsg',
                            render:function(item){
                                var id = item.ser_id, ser_version = item.ser_version,sendDate = item.sendDate;
                                if(ser_version == "null" || ser_version == null){
                                    ser_version = '';
                                }
                                var html = '';
                                html += '<a onclick=sendDetail("'+id+'","'+sendDate+'","'+ser_version+'") >发送详情</a>'
                                return html;
                            }
                        }],

				url : projectName+'/TBtest/serinfo',
				dataAction : 'server',
				checkbox : false,
				usePager : true,
				enabledSort :false,
				alternatingRow : false,
				isScroll: true,
			});
    laydate.render({
        elem: '#ser_date', //指定元素
        format:'yyyyMMdd'
    });
	$(".btn_search1").click(function(){
		var name = $("#ser_name").val();
		var aname = $("#ser_date").val();
		manager.options.parms = {"name":name,
				"sendDate":aname};
		manager.reload(1);
	});

});
function sendDetail(id ,sendDate, ser_version) {
    orderlayer=layer.open({
        type: 1,
        title: '发送详情信息',
        area: ['920px', '550px'],
        shade: 0.1,
        zIndex :10,
        content: $("#sendOrder"),
        success: function (layero, index) {
            $("#ser_id").val(id);
            $("#ser_version").val(ser_version);
            $("#order_date").val("");
            $("#name").val("");
            $("#rMenu").css("z-index",index+1);
            if(countSend != null){
                countSend.reload(1);
            }
            countSend = $("#orderMsg").ligerGrid({
                columns : [
                    {
                        display : '订阅名称',
                        type : 'String',
                        align : 'center',
                        name : 'order_name',
                        id : 'orderName'
                    },
                    {
                        display : '日期时间',
                        type : 'String',
                        align : 'center',
                        name : 'sendDate'
                    },
                    {
                        display : '发送总量',
                        type : 'String',
                        align : 'center',
                        name : 'sendCount'
                    },
                    {
                        display : '发送成功数量',
                        type : 'String',
                        align : 'center',
                        name : 'successCount',
                        render : function(item) {
                            var a = "";
                            urltype="success";
                            a = "<a onclick=\"getSendCount('successSend','"+item.orderid+"','"+item.sendDate+"','"+item.tenant_id+"','"+item.successCount+"')\">" + item.successCount + "</a>"
                            return a;
                        }
                    },
                    {
                        display : '发送失败数量',
                        type : 'String',
                        align : 'center',
                        name : 'failCount',
                        render : function(item) {
                            var a = "";
                            urltype='fail';
                            a = "<a onclick=\"viewMenu(event,'failSend','"+item.orderid+"','"+item.sendDate+"','"+item.tenant_id+"')\">" + item.failCount + "</a>";
                            return a;
                        }
                    }],
                url : projectName+'/TBtest/listyes',
                dataAction : 'server',
                checkbox : false,
                usePager : true,
                enabledSort :false,
                alternatingRow : false,
                isScroll: true,
                parms:{
                    "ser_id":id,
                    "ser_version":ser_version,
                    "sendDate":sendDate
                }
            });
            laydate.render({
                elem: '#order_date', //指定元素
                format:'yyyyMMdd'
            });
            $(".btn_search").click(function(){
                var name = $("#name").val();
                var aname = $("#order_date").val();
                var ser_id =  $("#ser_id").val();
                 var version = $("#ser_version").val();
                countSend.options.parms = {"name":name,
                    "sendDate":aname,
                    "ser_id":ser_id,
                    "ser_version":version,
                    "sendDate":sendDate};
                countSend.reload(1);
            });
            $(".btn_close").click(function(){
                layer.close(orderlayer);
                orderlayer = null;
            });
        }
    })
}
//操作
function viewMenu(event,sendStatus,orderid,sendDate,tenantId){
	getMsg(sendStatus,orderid,sendDate,tenantId);
    $("#rMenu").show();
    var xyObj = getMousePos(event);
    x = xyObj.x-100;
//    x = xyObj.x-10;
    y = xyObj.y+10;
    var index = document.getElementById('layui-layer'+orderlayer).style.zIndex + 1;
    rMenu.css({"position":"absolute","z-index":index,"top":y+"px", "left":x+"px", "visibility":"visible", "height":"auto"});
    $(document).bind("mousedown",onBodyDown);

}
function hideMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $(document).unbind("mousedown",onBodyDown);
}
function onBodyDown(event) {
    if (!( event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        hideMenu();
    }
}
function getMousePos(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var x = e.pageX || e.clientX + scrollX;
    var y = e.pageY || e.clientY + scrollY;
    return { 'x': x, 'y': y };
}
function getMsg(sendStatus,orderid,sendDate,tenant_id){
    $.ajax({
        url : projectName+'/TBtest/getMsg',
        type: 'POST',
        async: false,
        data:{
            "orderid":orderid,
            "sendDate":sendDate,
            "tenant_id":tenant_id
        },
        success:function(data){
        	var html = '';
        	for(var i = 0; i < data.length; i++){
        		if(sendStatus == 'failSend' && data[i].code != '00000'){
                    html += '<li>'
                        +'<a style="height:15px;float: inherit;margin-left: 10px;margin-right: 10px;" onclick=getSendCount(\"'+sendStatus+'\",\"'+orderid+'\",\"'+sendDate+'\",\"'+tenant_id+'\",\"'+data[i].code+'\",\"'+data[i].sendCount+'\")>'+"原因 : "+data[i].desc+', 数量 : '+data[i].sendCount+'</a>'
                        +'</li>';
                }
			}
            $(".dropdown-menu").children().remove();
			if(html != ''){
                $(".dropdown-menu").append(html);
            }else{
                html += '<li>'
                    +'<div style="height:15px;float: inherit;margin-left: 10px;margin-right: 10px;">没有失败发送</div>'
                    +'</li>';
                $(".dropdown-menu").append(html);
			}
        }
    });
}

//打开发送状态窗口
function getSendCount(sendStatus,orderid,sendDate,tenant_id,code,count){
    var msgData = [];
	if(sendStatus == 'successSend'){
	    count = code;
	    code = '00000';
    }
    $("#order_times").val("");
     statusLayer = layer.open({
        type: 1,
		title: '发送状态信息',
        area: ['920px', '550px'],
        shade: 0.1,
		content: $("#sendstatus"),
		success:function(layero, index){
            $(".btn_search1").click(function(){
                var time = $("#order_times").val();
                statusManager.options.parms = {
                    "time":time,
                    "orderid":orderid,
                    "dayTime":sendDate,
                    "tenant_id":tenant_id,
                    "code":code,
                    "logType":1,
                    "count":count};
                statusManager.reload(1);
            });
            $(".btn_close1").click(function(){
                layer.close(statusLayer);
                statusLayer = null;
            });
            laydate.render({
                elem: '#order_times', //指定元素
                type: 'time',
                range: true
            });
            if(statusManager != null){
                statusManager.reload(1);
            }
        	showStatusMsg(orderid,sendDate,tenant_id,code,msgData,count);
		}
	})

}

function showStatusMsg(orderid,sendDate,tenant_id,code,msgData,count){
	var htmlurl = [];
	var htmlReqMsg = [];
    var htmlRespMsg = [];
    statusManager = $("#statusMsg").ligerGrid({
        columns : [
            {
                display : '请求时间',
                type : 'String',
                align : 'center',
                width:'17%',
                name : 'startTimeStr'
            },
            {
                display : '响应时间',
                type : 'String',
                align : 'center',
                width:'17%',
                name : 'endTimeStr'
            },
            {
                display : 'url',
                type : 'String',
                align : 'center',
                width:'23%',
                name : 'url',
	            render:function(item){
	                var msg = item.url;
	                if(msg == "null" || msg == null){
	                    msg = '';
	                }
	                htmlurl.push(msg);
	                var html = '<div class = "widthLimit orderurl" onclick="test1(event)"></div>';
	                return html;
	            }
            },
            {
                display : '请求报文',
                type : 'String',
                align : 'center',
                width:'23%',
                name : 'requestMsg',
	            render:function(item){
	                var msg = item.requestMsg;
	                if(msg == "null" || msg == null){
	                    msg = '';
	                }
	                htmlReqMsg.push(msg);
	                var html = '<div class = "widthLimit orderRequestLimit" onclick="test1(event)"></div>';
	                return html;
	            }
            },
            {
                display : '响应报文',
                type : 'String',
                align : 'center',
                width:'25%',
                name : 'responseMsg',
                render:function(item){
                    var msg = item.responseMsg;
                    if(msg == "null" || msg == null){
                        msg = '';
                    }
                    htmlRespMsg.push(msg);
                    var html = '<div class = "widthLimit orderResponseLimit" onclick="test1(event)"></div>';
                    return html;
                }
            },
            {
                display : '耗时(毫秒)',
                type : 'String',
                align : 'center',
                width:'8%',
                name : 'useTime'
            },
            {
                display : '操作',
                type : 'String',
                width:'8%',
                align : 'center',
                name : 'responseMsg',
				render:function(item){
                	var id = item.requestId;
                	var html = '';
                	html += '<a onclick=statusDetail("'+id+'","'+item.orderid+'","'+item.dayTime+'","'+item.tenant_id+'") >服务详情</a>'
                	return html;
				}
            }],
        url : projectName+'/TBtest/getSendStatus',
        checkbox : false,
        usePager : true,
        enabledSort :false,
        alternatingRow : false,
        isScroll: true,
        parms:{
            "orderid":orderid,
            "dayTime":sendDate,
            "tenant_id":tenant_id,
            "code":code,
            "logType":1,
            "count":count
        },
        height:'400px',
        onAfterShowData  :function(){
        	$('.orderurl').each(function(index){
      		   $(this).text(htmlurl[index]);
      	   });
        	$('.orderRequestLimit').each(function(index){
     		   $(this).text(htmlReqMsg[index]);
     	   });
        	$('.orderResponseLimit').each(function(index){
    		   $(this).text(htmlRespMsg[index]);
    	   });
        }
    })
}
function test1(event){
    var th = event.srcElement;
    if($(th).hasClass('widthLimit')){
        $(th).removeClass('widthLimit').addClass('unlimit');
    }else{
        $(th).addClass('widthLimit').addClass('unlimit');
    }
}
function statusDetail(id,orderid,dayTime,tenant_id) {
	var htmlurl = [];
	var htmlReqMsg = [];
    var htmlRespMsg = [];
    detailLayer=layer.open({
        type: 1,
        title: '发送详细信息',
        area: ['920px', '550px'],
        shade: 0.1,
        content: $("#sendDetail"),
        success:function(layero, index){
            if(detaileManager!=null){
                detaileManager.reload(1);
            }
            $(".btn_close2").click(function(){
                layer.close(detailLayer);
                detailLayer = null;
            });
            detaileManager = $("#logDetail").ligerGrid({
                columns : [
                    {
                        display : '请求时间',
                        width:'17%',
                        type : 'String',
                        align : 'center',
                        name : 'startTimeStr'
                    },
                    {
                        display : '响应时间',
                        type : 'String',
                        width:'17%',
                        align : 'center',
                        name : 'endTimeStr'
                    },
                    {
                        display : 'url',
                        width:'25%',
                        type : 'String',
                        align : 'center',
                        name : 'url',
	                    render:function(item){
	                        var msg = item.url;
	                        if(msg == "null" || msg == null){
	                            msg = '';
	                        }
	                        htmlurl.push(msg);
	                        var html = '<div class = "widthLimit puburlLimit" onclick="test1(event)"></div>';
	                        return html;
	                    }
                    },
                    {
                        display : '请求报文',
                        width:'25%',
                        type : 'String',
                        align : 'center',
                        name : 'requestMsg',
	                    render:function(item){
	                        var msg = item.requestMsg;
	                        if(msg == "null" || msg == null){
	                            msg = '';
	                        }
	                        htmlReqMsg.push(msg);
	                        var html = '<div class = "widthLimit pubRequestLimit" onclick="test1(event)"></div>';
	                        return html;
	                    }
                    },
                    {
                        display : '响应报文',
                        width:'25%',
                        type : 'String',
                        align : 'center',
                        name : 'responseMsg',
                        render:function(item){
                            var msg = item.responseMsg;
                            if(msg == "null" || msg == null){
                                msg = '';
                            }
                            htmlRespMsg.push(msg);
                            var html = '<div class = "widthLimit pubResponseLimit" onclick="test1(event)"></div>';
                            return html;
                        }
                    },
                    {
                        display : '耗时(毫秒)',
                        type : 'String',
                        width:'10%',
                        align : 'center',
                        name : 'useTime'
                    }],
                url : projectName+'/TBtest/getLogDetail',
                parms:{
                    "orderid":orderid,
                    "dayTime":dayTime,
                    "tenant_id":tenant_id,
                    "requestId":id,
                    "logType":0
                },
                checkbox : false,
                usePager : true,
                enabledSort :false,
                alternatingRow : false,
                isScroll: true,
                height:'400px',
                onAfterShowData  :function(){
                	$('.puburlLimit').each(function(index){
              		   $(this).text(htmlurl[index]);
              	   });
                	$('.pubRequestLimit').each(function(index){
             		   $(this).text(htmlReqMsg[index]);
             	   });
                	$('.pubResponseLimit').each(function(index){
            		   $(this).text(htmlRespMsg[index]);
            	   });
                }
            })
        }
    });
}