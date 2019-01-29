var i = 0;
$(document).ready(function(){
	
	var proresult;
	$( document ).tooltip();
	$("#addparam").bind("click",addInput);
	$("#addUrlBtn").bind("click",publish);
	
	// 为datatable外的父级设置高度
	$('#urlexplainTable_wrapper').css('height', $('.panel-body').height()-60);
	// 动态为表格添加父级
	$('#urlexplainTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#urlexplainTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});
	
});


function addInput(){
	var input = "<div style='margin-top: 10px;' class='jsonparam'><div class='form-group'>param:<input class='form-control' type='text' id='param' /> </div><div class='form-group'> explain:<input class='form-control'  type='text' id='explain'/> </div>" +
			"<button type='button' class='btn btn-default deleteparam' aria-label='Left Align' ><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></button>" +
			"</div>";
	$("#paramset").append(input);
	++i;
	
	$("#paramset").find(".deleteparam").each(function(i,e){
		$(this).click(function(){
			$(this).parent().remove();
		})
	});
	
}

function publish(){
	var name = $("#name").val();
	var url = $("#url").val();
	var exdata = $("#exdata").val();
	var dataformat = $("#dataformat").val();
	var jsonstr = "{";
	
	if($("#paramset").find(".jsonparam").length == 0){
		jsonstr = "'"+$("#param0").val()+"':'"+$("#explain0").val()+"'}";
	}else{
		$("#paramset").find(".jsonparam").each(function(i,e){
	//		alert(jsonstr);
			jsonstr +=  "'"+$("#param0").val()+"':'"+$("#explain0").val()+"','"
				+$(this).find("input:first").val()+"':'"+$(this).find("input:last").val()+"',";
		});
	}
	console.log(jsonstr.length-1);
	jsonstr = jsonstr.substr(0,jsonstr.Length-1);
	jsonstr += "}";
	console.log(jsonstr);
	/*$("#paramset").find(".jsonparam").each(function(i,e){
		console.log($(this).find("input:first").val());
		console.log($(this).find("input:last").val());
		
		
	});*/
	
	/*$.ajax({
		"url":webpath+"/pub/insert",
		"type":"POST",
		dataType:"json",
		async : false,
		data: {
			"name":name,
			"url":url,
			"exdata":exdata,
			"dataformat":dataformat
		},
		success:function(data){
		}
		
	})*/
}


