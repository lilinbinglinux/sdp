
var appIdtoken ;
var type;

$(function (){
	$("#getTokenidBtn").bind("click",getTokenId);
	$("#addOrderInterface").bind("click",function(){
		if($("#key_key").val() == "" || $("#key_key").val() == null){
			layer.msg('请生成令牌再申请！', {time: 2000, icon:2});
		}else{
			addOrderInterface();
		}
	});
	$("#token-previous-step").click(function(){
		$("#order-interfaceTitleBtn").removeClass("active");
		$("#security-tokenBtn").removeClass("active");
		$("#security-apiTestBtn").addClass("active");
		$("#api-test").css('display','block');
		$("#security-set").css('display','none');
	})
});

function getTokenId(){
	$.ajax({
		"url":webpath+"/security/getTokenId",
		"type":"GET",
		async : true,
		success: function(data) {
			var str = "appId="+data.appId+"\n"+"token_id="+data.token_id;
			$("#key_key").val(str);
			$("#getTokenidBtn").attr("disabled","disabled");
			appIdtoken = new appIdtoken(data.appId,data.token_id);
        }
	});
}

function addOrderInterface(){
	if($("#id-interfacepubid").val() == "" || $("#id-interfacepubid").val() == null){
		var pubid = $("#id-pubapiId").val();
	}else{
		var pubid = $("#id-interfacepubid").val();
	}
	
	//查询type
	$.ajax({
		"url":webpath+"/publisher/getPubById",
		"type":"POST",
		async:false,
		data:{
			"id" : pubid,
		},
		success: function(data) {
			if(data.typeId == "3"){
				type = "2";
			}else if($("#id-interfacepubid").val() == "" || $("#id-interfacepubid").val() == null){
				type = "1";
			}else{
				type = "0";
			}
        }
	});
	
	//插入数据
	$.ajax({
		"url":webpath+"/interfaceOrder/insertorderinterface",
		"type":"POST",
		async:false,
		data:{
			"name" : $("#name").val(),
			"ordercode" : $("#ordercode").val(),
			"reqformat" : $("#reqformat").val(),
			"respformat" : $("#respformat").val(),
			"pubapiId": pubid,
			"type": type,
			"appId":appIdtoken.appId,
			"token_id":appIdtoken.token_id,
		},
		success: function(data) {
			layer.msg('申请成功！', {time: 1000, icon:1});
        },
        error: function(){
        	console.log("添加失败");
        },
	});
}

function appIdtoken(appId,token_id){
	this.appId = appId;
	this.token_id = token_id;
}



