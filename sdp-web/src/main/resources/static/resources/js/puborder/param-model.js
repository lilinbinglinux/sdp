var i = 0;
$(function (){
	
	var proresult;
//	$( document ).tooltip();
	$("#addparam").bind("click",addInput);
	$("#addParamSetBtn").bind("click",addParam);
	
	// 为datatable外的父级设置高度
	$('#urlexplainTable_wrapper').css('height', $('.panel-body').height()-60);
	// 动态为表格添加父级
	$('#urlexplainTable').wrap('<div class="tab-wrapper"></div>');
	$('.tab-wrapper').css('height', $('#urlexplainTable_wrapper').height()-63);
	$('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});


    $("#reqtype").change(function(){
        if($(this).val()!="String"){
            $(".maxlength").hide();
        }else{
            $(".maxlength").show();
        }
    })
	
});


function addInput(){
	var input = "<div style='margin-top: 10px;' class='jsonparam form-inline jsonparamdiv'>" +
					"<label class='control-label'>参数::</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +
					"<label class='control-label'>说明:</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +
					"<label class='control-label'>参数类型:</label>" +
					/*"<input class='form-control form-input param-input' style='width:80px' type='text'/>" +*/
					"<select class='param-input form-input' style='width:80px'> "+
	                    "<option value='String'>String</option>     "+
	                    "<option value='int'>int</option>           "+
	                    "<option value='boolean'>boolean</option>   "+
	                    "<option value='Object'>Object</option>     "+
	                    "<option value='List'>List</option>         "+
                    "</select>" +
					/*"<label class='control-label'>参数条件:</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +*/
                    "  <label class=\"control-label\">参数位置:</label>"+
					" <select id=\"parampos\" class='param-input form-input' style=\"width:80px;height: 25px;\"> "+
                    "       <option value=\"0\" selected = \"selected\">请求体</option>"+
                    "      <option value=\"4\">请求头</option>"+
                    "      <option value=\"1\">url上的参数</option>"+
                    "      <option value=\"2\">响应头</option>"+
                    "      <option value=\"3\">响应体</option>"+
                    "</select>"+
					"<label class=\"control-label maxlength\">最大长度:</label>"+
					"<input class=\"form-control param-input maxlength\" style=\"width:80px\" type=\"text\" id=\"maxlength\"/>"+
					"<label class='control-label'>是否必传项:</label>" +
					/*"<input class='form-control form-input param-input' style='width:80px'  type='text'/>" +*/
					"<select class='param-input form-input' style='width:80px'>"+ 
						"<option value='0'>否</option> "+
                    	"<option value='1'>是</option> "+
                    "</select>"+
					"<button type='button' class='btn btn-default deleteparam' aria-label='Left Align' >" +
						"<span class='glyphicon glyphicon-minus' aria-hidden='true'></span>" +
					"</button>" +
				"</div>";
	$("#paramset").append(input);
	
	$("#paramset").find(".deleteparam").each(function(i,e){
		$(this).unbind("click");
		$(this).bind("click",function(){
			$(this).parent().remove();
			if($("#allparamset").height() < $("#parammodal-body").height()){
				$("#parammodal-body").attr("style","overflow-y:visible;");
			}
		})
	});
	
	if($("#parammodal-body").height() > 400 ){
		$("#parammodal-body").attr("style","overflow-y:scroll;height:40%");
	}
}

function addParam(){
	var divinput = $(".jsonparam");
	var objarr = new Array();
	
	var ecode = $("#ecode").val();
	var reqdesc = $("#reqdesc").val();
	var reqtype = $("#reqtype").val();
	var parampos = $("#parampos").val();
	var isempty = $("#isempty").val();
    var maxlength = $("#maxlength").val();
	
	var type = $("#type").val();
	
	if($("#paramadd-update").val() == "1"){
		$.ajax({
			"url":webpath+"/reqparam/update",
			"type":"POST",
			async : false,
			data: {
				"id":$("#paramid-set").val(),
				"ecode":ecode,
				"reqdesc":reqdesc,
				"reqtype":reqtype,
				"parampos":parampos,
				"isempty":isempty,
				"type":type,
                "maxlength":maxlength
			},
			success:function(data){
				//reloadParamTableData("#reqparamtable");
				initParamTable();
			}
		})
	}else{
		var parentId = $("#parmParentId").val();
		var reqobj1 = new reqObj(ecode,reqdesc,reqtype,parampos,isempty,type,maxlength,parentId);
		objarr.push(reqobj1);
		
		
		for(var i =0;i < divinput.length;i++){
			var arr = new Array(); 
			$(divinput[i]).find(".form-input").each(function(i,e){
				arr.push($(this).val());
			})
			var reqobj = new reqObj(arr[0],arr[1],arr[2],arr[3],arr[4],type,arr[5],parentId);
			objarr.push(reqobj);
		}
		var nodes = resource.treeObj.getSelectedNodes();
		
		$.ajax({
			"url":webpath+"/reqparam/insert",
			"type":"POST",
			dataType:"json",
			async : false,
			data: {
				"reqobj":JSON.stringify(objarr),
				"pubid":parent.paramifream_pubid
				
			},
			success:function(data){
				initParamTable();
			},
			error:function(data){
			}
			
		})
	}
}

function reqObj(ecode,reqdesc,reqtype,parampos,isempty,type,maxlength,parentId){
	this.ecode = ecode; 
	this.reqdesc = reqdesc;
	this.reqtype = reqtype;
	this.parampos = parampos;
	this.isempty = isempty;
	this.type = type;
	this.maxlength = maxlength;
	this.parentId = parentId;
}

function reloadParamTableData(tableid,isCurrentPage){
	$(tableid).dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}


