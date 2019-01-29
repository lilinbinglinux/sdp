var pubid = $("#id-interfacepubid").val();
var type;
var str = new Array();
$(document).ready(function(){
	selectInterface();
	/*$("#id-pubinterface1").click(function(){
		$("#id-pubinterface2").removeClass();
		$(this).addClass("active");
		selectInterface();
	})*/
	
	$("#id-pubinterface2").click(function(){
		$("#id-pubinterface1").removeClss();
		$(this).addClass("active");
		insertApplyDefaultParam();
	})
	
	$("#step1-next-step").bind("click",function(){
		$("#id-isParamSubsist").val("0");
		//applyInterface();
		var pubapiId = $("#id-interfacepubid").val();
		validateOrder(pubapiId);
		
	});
	
	
	$("#id-applyInterfaceBtn2").bind("click",function(){
		$("#id-isParamSubsist").val("1");
		applyInterface();
	});
	
	$("#id-defaultparam-li").bind("click",function(){
		$("#id-methodparam-input").val("0");
		$("#id-paramname-btn").text($("#id-defaultparam-li").text());
		insertApplyDefaultParam();
	});
	$("#id-setparam-li").bind("click",function(){
		$("#id-methodparam-input").val("1");
		$("#id-paramname-btn").text($("#id-setparam-li").text());
		insertApplyDefaultParam();
	});
	
})


//不添加参数
function selectInterface(){
	$("#id-interfaceapply2").hide();
	$("#id-interfaceapply1").show();
	
	$.ajax({
		"url":webpath+"/reqparam/getcountreqparam",
		"type":"POST",
		async:false,
		data:{
			"pubid": pubid,
		},
		success: function(data){
			if(data > 0){
				$("#id-reqparam-div").show();
			}else{
				$("#id-reqparam-div").hide();
				$('#respformat').val(' ');
			}
		}
	});
	
}


//默认参数匹配
function insertApplyDefaultParam(){
	$("#id-interfaceapply1").hide();
	$("#id-interfaceapply2").show();
	
	$("#id-interfacetable2").width("100%").dataTable({
		destroy: true,
		"scrollY": "300px",
		"scrollCollapse": "true",
	     "paging": "false",
		"columns":[
		           { "data": "id" },
		           { "data": "ecode" },
		           { "data": "isempty" },
		           { "data": "reqtype" },
		           { "data": "type" },
		           { "data": "ecode" },
		 ],
		 ajax: {
			 	"url":webpath+"/reqparam/selectParams",
				"type":"POST",
				dataType:"json",
				data:{
					"jsonStr" : '{"pubid":"'+pubid+'"}'
				},
				"dataSrc": function (json) {
			           json.iTotalRecords = json.total;
			           json.iTotalDisplayRecords = json.total;
			           return json;
			     }
		},
		columnDefs:[
		    		{
		    			"targets" : 0,
		    			"data" : null,
		    			"visible" : false,
		    			"render" : function(data,type,row){
		    				return  '<input type="text" class="ids" value="'+data+'"/>';
		    			}
		    		},        
		    		{
		    			"targets" : 1,
		    			"data" : null,
		    			"visible": true
		    		},
		    		{
		    			"targets" : 2,
		    			"data" : null,
		    			"render" : function(data,type,row){
		    				if(data == "1"){
		    					return "是"; 
		    				}else if(data == "0"){
		    					return "否";
		    				}
		    			}
		    		},
		    		{
		    			"targets" : 3,
		    			"data" : null,
		    			"visible": true,
		    		},
		    		{
		    			"targets" : 4,
		    			"data" : null,
		    			"render" : function(data,type,row){
		    				if(data == "1"){
		    					return "响应参数"; 
		    				}else if(data == "0"){
		    					return "请求参数";
		    				}
		    			}
		    		},
		    		{
		    			"targets" : 5,
		    			"data" : null,
		    			"render" : function(data,type,row){
		    				if($("#id-methodparam-input").val() == 0){
		    					return data;
		    				}else{
		    					var html = "<input style='width:80px;' />";
		    					return html;
		    				}
		    			}
		    		}
		    	]
	});
	$("#id-interfacetable2_paginate").hide();
	str.length = 0;
	$('#id-interfacetable2').find('.ids').each(function(){  
        str.push($(this).val());
    });
}


//reqObject构造方法
function reqObject(name,isempty,paramtype,type,pubparamId){
	this.name = name; 
	this.isempty = isempty;
	this.paramtype = paramtype;
	this.type = type;
	this.pubparamId = pubparamId;
}

//带参数申请调用
function applyInterface(){
	var objarr = new Array();
	var arr = new Array();
	
	//查询type
/*	$.ajax({
		"url":webpath+"",
		"type":"POST",
		async:false,
		data:{
			pubid : pubid,
		},
		success: function(data){
			
		}
	});*/
	
	$.ajax({
		"url":webpath+"/interfaceOrder/insertapplyorderinterface",
		"type":"POST",
		async:false,
		data:{
			"name" : $("#name").val(),
			"ordercode" : $("#ordercode").val(),
			"reqformat" : $("#reqformat").val(),
			"respformat" : $("#respformat").val(),
			"pubapiId": pubid,
			"type": "0",
		},
		success: function(data) {
			//id-isParamSubsist用来判断是否申请时带参数
			if($("#id-isParamSubsist").val() == 0){
				layer.msg('申请已发送！', {time: 1000, icon:1});
			}else{
			
				var i = 0;
				$("#id-interfacetable2").find("tr").each(function(){
					$(this).find("td").each(function(){
						arr.push($(this).text());
						$(this).find("input").each(function(){
							arr.push($(this).val());
						});
					});
					arr.push(str[i-1]);
					i = i + 1;
					if(arr.length != 0){
						if($("#id-methodparam-input").val() == 0){
							var reqobject = new reqObject(arr[4],arr[1],arr[2],arr[3],arr[5]);
							arr.length = 0;
							objarr.push(reqobject);
						}else{
							var reqobject = new reqObject(arr[5],arr[1],arr[2],arr[3],arr[6]);
							arr.length = 0;
							objarr.push(reqobject);
						}
						
					}
				})
				
				//传递参数
				$.ajax({
					"url":webpath+"/orderparambeancontroller/orderinsert",
					"type":"POST",
					async:false,
					data:{
						"reqobj" : JSON.stringify(objarr),
						"orderid" : data,
					},
					success: function(data) {
						layer.msg('申请已发送！', {time: 1000, icon:1});
					}
				});
				
			}
		},
        error: function(){
        	console.log("添加失败");
        },
	});
}

function validateOrder(pubapiId){
	$.ajax({
		"url":webpath+"/interfaceOrder/isValidation",
		"type":"POST",
		"data":{
			"pubapiId":pubapiId,
			"name":$("#name").val(),
			"ordercode":$("#ordercode").val(),
			"protocal":$("#protocal").val(),
			"format":$("#format").val()
		},
		success: function(data) {
			if(data == "isValidate"){
				/*$("#id-pubinterface1").removeClass("active");
				$("#security-apiTestBtn").addClass("active");
				showTestSampledata();//该方法在apitestiframe.js中
				$("#id-interfaceapply1").css('display','none');
				$("#api-test").css('display','block');*/
				
				$("#id-pubinterface1").removeClass("active");
				$("#id-pubparamshow").addClass("active");
				$("#id-interfaceapply1").css('display','none');
				$("#api-pubparam").css('display','block');
			}
			else if(data == "inValidate"){
				layer.msg('请不要相同条件重复申请！', {time:5000, icon:7});
				$("#loadOrderParam").css('display','block');
				$("#add-interfaceParamOrderModel").css('display','none');
				initurlpublishtableTable();
			}
        },
	});
}


//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadReqTableData(isCurrentPage){
	$("#interfacetable1").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
	$("#interfacetable2").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

//取消
function backstep(){
	
}

