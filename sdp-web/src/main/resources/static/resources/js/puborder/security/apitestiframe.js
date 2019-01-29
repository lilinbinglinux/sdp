
$(function (){
	//上一步
	$("#step3-pre-step").click(function(){
		$("#security-apiTestBtn").removeClass("active");
		$("#id-pubparamshow").addClass("active");
		
		$("#id-interfaceapply1").css('display','none');
		$("#api-test").css('display','none');
		$("#security-set").css('display','none');
		
		$("#api-pubparam").css('display','block');
	})
	
	//在线测试
	$("#step3-add-progress").bind("click",apiTestOnline);
	
	//下一步
	$("#step3-next-step").click(function(){
		$("#order-interfaceTitleBtn").removeClass("active");
		$("#security-apiTestBtn").removeClass("active");
		$("#security-tokenBtn").addClass("active");
		$("#api-test").css('display','none');
		$("#security-set").css('display','block');
	});
});

function progressbar(starttime,endtime,iserror){
	var total=10000;
	var breaker=100;
	var turn=100/(total/breaker);
	var starttime=starttime;
	var timer = setInterval(function(){
		starttime=starttime+turn;
		
		if(iserror&&starttime == 99){
			$("#processbar").attr("class","progress-bar progress-bar-danger")
			$("#responsediv").css("display","block");
		}
		else if(!iserror&&starttime == 100){
			$("#responsediv").css("display","block");
		}
		
	    $("#aa").html(starttime+"% 完成");
	    $("#processbar").attr("style", "width:"+starttime+"%");
	    if (starttime>=endtime) {
	        clearInterval(timer);
	    }
	}, 100);
}

function showTestSampledata(){
	$.ajax({
		"url":webpath+"/publisher/getPubById",
		"type":"POST",
		async : false,
		data: {
			"id":$("#id-interfacepubid").val(),
		},
		success:function(data){
			$("#test-sampledata").val(data.sampledata);
		}
	})
}

function apiTestOnline(){
	$("#processdiv").css("display","block");
	$("#responsediv").css("display","none");
	$("#processbar").attr("class","progress-bar progress-bar-primary")
	progressbar(0,0,false);
	$.ajax({
		"url":webpath+"/security/apiTestOnline",
		"type":"POST",
		async : true,
		beforeSend:function(){
			setTimeout(function(){
				progressbar(0,50);
			},1000);
		},
		data: {
			"id":$("#id-pubapiId").val(),
			"sampledata":$("#test-sampledata").val()
		},
		success:function(data){
			$("#response-data").val(JSON.stringify(data));
			
			if(data.respCode != "00000"){
				setTimeout(function(){
					progressbar(50,99,true);
				},1400);
			}else{
				setTimeout(function(){
					progressbar(50,100,false);
				},1400);
			}
		},
		error:function(){
			setTimeout(function(){
				progressbar(50,99,true);
			},1400);
		}
		
	})
}





