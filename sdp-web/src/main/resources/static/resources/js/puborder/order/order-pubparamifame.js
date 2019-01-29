
$(function (){
	getReqParams();
	
	$("#flag-response").click(function(){
		$("#flag-type").val("1");
		getReqParams();
	})
	
	$("#flag-request").click(function(){
		$("#flag-type").val("0");
		getReqParams();
	})
	//上一步
	$("#step2-pre-step").click(function(){
		$("#id-pubinterface1").addClass("active");
		$("#id-pubparamshow").removeClass("active");
		$("#api-pubparam").css('display','none');
		$("#api-test").css('display','none');
		$("#security-set").css('display','none');
		
		$("#id-interfaceapply1").css('display','block');
	})
	
	//下一步
	$("#step2-next-step").click(function(){
		$("#security-apiTestBtn").addClass("active");
		$("#id-pubparamshow").removeClass("active");
		//step1
		$("#id-interfaceapply1").css('display','none');
		//step2
		$("#api-pubparam").css('display','none');
		//step4
		$("#security-set").css('display','none');
		
		showTestSampledata();
		$("#api-test").css('display','block');
	})
});

function getReqParams(){
	$.ajax({
			"url":webpath+"/reqparam/getJsonParamBypubid",
			"type" : 'POST',
			 data :{
			  "type":$("#flag-type").val(),
			  "pubid":$("#id-interfacepubid").val()
			 } ,
			 async : false,
			success:function(data){
				$("#reqparam-code").html(data);
			}
		})
}     






