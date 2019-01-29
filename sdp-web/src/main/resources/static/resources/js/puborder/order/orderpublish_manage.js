$(document).ready(function(){
	//initurlpublishtableTable();
	$("#order-backstep1").on("click",backstep);
	$("#order-backstep2").on("click",backstep);
	$("#order-backstep3").on("click",backstep);
	$("#id-addproinfo").on("click", function() {
		$("#modaltype").val("");
		$("#proname").val("");
		$("#proecode").val("");
		$("#proversion").val("");
		$("#prodescribe").val("");
		$('#proModal').modal();
	});
	$("#add-orderInterfaceBtn").click(function(){
		var setparamselect = $("#setparamselect").val();
		var pubapiId = $("#id-pubapiId").val();
		
		//不需要设置参数时，点击下一步显示API在线测试
		if(setparamselect == 0){
			$("#order-div").css('display','none');
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
						$("#order-interfaceTitleBtn").removeClass("active");
						$("#security-apiTestBtn").addClass("active");
						showTestSampledata();//该方法在apitestiframe.js中
						$("#api-test").css('display','block');
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
		
		//正常设置参数
		if(setparamselect == 1){
			$("#order-interfaceTitleBtn").removeClass();
			$("#order-paramTitleBtn").addClass("active");
			doinsertOrderInterface();
		}
		
		//以xml格式设置参数
		if(setparamselect == 2){
			$("#order-interfaceTitleBtn").removeClass();
			$("#order-paramTitleBtn").addClass("active");
			$("#loadOrderParam").css('display','none');
			$("#add-interfaceParamOrderModel").css('display','block');
			xmlParamOpen();
		}
	})
	
});

//初始化用户列表表格
function initurlpublishtableTable(){
	$("#publishtable").width("100%").dataTable({
		destroy: true,
		"columns":[
		           { "data": "pubid" },
		           { "data": "name" },
		           { "data": "url" },
		           { "data": "method" },
		           { "data": "respformat" },
		           { "data": "pubdesc" },
		           { "data": "" },
		 ],
		 ajax: {
		     url:webpath+'/publisher/selectPage',
		     "type": 'POST',
		     "data": function (d) {//查询参数
		           return $.extend( {}, d, {
		              "jsonStr": form.serializeStr($("#pubidtopublish"))
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
			"targets" : 0,
			"data" : null,
			"visible": false
		},     
		{
			"targets" : 1,
			"data" : null,
			"visible": true
		},
		{
			"targets" : 2,
			"data" : null,
			"visible": false
			
		},
		{
			"targets" : 3,
			"data" : null,
			"visible": true,
		},
		{
			"targets" : 4,
			"data" : null
		},
		{
			"targets" : 5,
			"data" : null,
		},
		{
			"targets" : 6,
			"data" : null,
			"render" : function(data, type,row) {
			var id = row.pubid;
			var appId = row.appId; 
			var html =  '<a href="javascript:void(0);" onclick="insertApplyInterface(\''+id+'\')" class="icon-wrap" title="申请调用"><i class="iconfont i-btn">&#xe648;</i></a>';
			html +=  '<a href="javascript:void(0);" onclick="insertOrderInterface(\''+id+'\')" class="icon-wrap" title="点击订阅"><i class="iconfont i-btn">&#xe696;</i></a>';
			return html;
   }
		}
	]
	});
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadReqTableData(isCurrentPage){
	$("#publishtable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

function insertOrderInterface(id){
	//获取pubid,存input框里
	$("#id-pubapiId").val(id);
	
	//模态框清除上次数据
	$("#name").val("");
	$("#ordercode").val("");
	$("#protocal").val("");
	$("#url").val("");
	$("#reqformat").val("");
	$("#orderdesc").val("");
	
	//弹出加载页面
	$("#loadOrderParam").css('display','none');
	$("#add-interfaceParamOrderModel").css('display','block');
	
	$('#add-orderParamModal').css('display','none');
	$('#id-xmlparam').css('display','none');
	$('#add-orderInterModal').css('display','block');
	
	//标题栏样式
	$("#order-paramTitleBtn").removeClass();
	$("#order-interfaceTitleBtn").addClass("active");
	//$('#add-orderInterfaceModal').modal();
}

function doinsertOrderInterface(){
	//插入数据
	$.ajax({
		"url":webpath+"/interfaceOrder/insertorderinterface",
		"type":"POST",
		async:false,
		data:{
			"name":$("#name").val(),
			"ordercode":$("#ordercode").val(),
			"protocal":$("#protocal").val(),
			"url":$("#url").val(),
			"reqformat":$("#reqformat").val(),
			"orderdesc": $("#orderdesc").val(),
			"pubapiId": $("#id-pubapiId").val(),
		},
		success: function(data) {
			/*$.ajax({
				"url":webpath+"/reqparam/selectAll",
				"type":"POST",
				data:{
					"pubid": $("#id-pubapiId").val(),
				},
				success: function(data){
					console.log(data);
				}
			});*/
			$('#add-orderInterModal').css('display','none');
			$('#add-orderParamModal').css('display','block');
			
			//设置按钮颜色
			$('#order-interfaceTitleBtn').css('color','#9f9f9f');
			$('#order-paramTitleBtn').css('color','#000000');
			$("#addParamBtn").on("click",addInputDiv);
			$("#add-orderParamBtn").bind("click", {data : data}, setOrderParam);
        },
        error: function(){
        	console.log("添加失败");
        },
	});
}

/**
 * 控制参数个数
 */
function addInputDiv(){
	var input = "<div style='margin-top: 10px;' class='jsonparam form-inline'>" +
					"<label class='control-label'>参数名称:</label><input class='form-control form-input input-sm form-sm' style='width:100px' type='text'/>" +
					"<label class='control-label'>说明:</label><input class='form-control form-input input-sm form-sm' style='width:100px' type='text'/>" +
					"<label class='control-label'>参数类型:</label><input class='form-control form-input input-sm form-sm' style='width:100px' type='text'/>" +
					"<label class='control-label'>是否为空:</label><select class='form-control form-input input-sm'><option value='0'>可以为空</option>" +
						"<option value='1'>不可以为空</option></select>" +
					"<label class='control-label'>参数样式:</label><select class='form-control form-input input-sm'><option value='0'>请求参数</option>" +
						"<option value='1'>响应参数</option></select>" +
					"<button type='button' class='btn btn-default deleteparam' aria-label='Left Align' >" +
						"<span class='glyphicon glyphicon-minus' aria-hidden='true'></span>" +
					"</button>" +
				"</div>";
	$("#orderparamset").append(input);
	
	$("#orderparamset").find(".deleteparam").each(function(i,e){
		$(this).click(function(){
			$(this).parent().remove();
		})
	});
}

/**
 * 设置参数
 */
function setOrderParam(event){
	var divinput = $(".jsonparam");
	var objarr = new Array();
	
	var name = $("#paramname").val();
	var paramdesc = $("#paramdesc").val();
	var paramtype = $("#paramtype").val();
	var isempty = $("#isempty").val();
	var type = $("#reqorresp").val();

	var reqobj1 = new reqObj(name,paramdesc,paramtype,isempty,type);
	objarr.push(reqobj1);
	
	for(var i =0;i < divinput.length;i++){
		var arr = new Array(); 
		$(divinput[i]).find(".form-input").each(function(i,e){
			arr.push($(this).val());
		})
		var reqobj = new reqObj(arr[0],arr[1],arr[2],arr[3],arr[4]);
		objarr.push(reqobj);
	}
	
	var nodes = resource.treeObj.getSelectedNodes();
	$.ajax({
		"url":webpath+"/orderparambeancontroller/orderinsert",
		"type":"POST",
		async : false,
		data: {
			"reqobj":JSON.stringify(objarr),
			"orderid":event.data.data,
		},
		success:function(){
			reloadReqTableData();
			$("#add-interfaceParamOrderModel").css('display','none');
			$("#loadOrderParam").css('display','block');
		},
		error: function(){
			alert("fail");
		}
	})
	
}


/**
 * 取消事件
 */
function backstep(){
	$("#add-interfaceParamOrderModel").css('display','none');
	$("#loadOrderParam").css('display','block');
}

/**
 * reqObj方法的构造函数
 */
function reqObj(name,paramdesc,paramtype,isempty,type){
	this.name = name; 
	this.paramdesc = paramdesc;
	this.paramtype = paramtype;
	this.isempty = isempty;
	this.type = type;
}


/**
 * xml参数弹框
 */
function xmlParamOpen(){
	var str = 
		"<params>										"+  
		"  <type>request</type>							"+  
		"  <allparams>									"+  
		"    <param>									"+  
		"      <name>名称</name>							"+  
		"      <paramdesc>描述</paramdesc>				"+ 
		"      <paramtype>参数类型</paramtype>			"+  
		"      <isempty>0</isempty>						"+  
		"      <pubparamId>订阅参数</pubparamId>			"+
		"      <orderParamBean>							"+
		"        <orderParamBeanList>                   "+  
		"          <param>                      		"+  
		"            <name>名称</name>			 		"+  
		"            <paramdesc>描述</paramdesc>	  		"+	
   		"            <paramtype>参数类型</paramtype>    	"+  
   		"            <isempty>0</isempty>           	"+
   		"            <pubparamId>订阅参数</pubparamId>	"+
   		"          </param>                         	"+  
   		"          <param>                          	"+  
   		"            <name>名称</name> 			    	"+  
   		"            <paramdesc>描述</paramdesc>       	"+ 
   		"            <paramtype>参数类型</paramtype>    	"+  
   		"            <isempty>0</isempty>           	"+
   		"            <pubparamId>订阅参数</pubparamId>	"+
   		"          </param>                         	"+  
   		"        </orderParamBeanList>                  "+  
   		"      </orderParamBean>                        "+  
   		"    </param>                               	"+  
   		"    <param>                                	"+  
   		"      <name>message</name>           			"+  
		"      <paramdesc>返回信息</paramdesc>			"+
		"      <paramtype>String</paramtype>           	"+  
		"      <isempty>0</isempty>                 	"+
		"      <pubparamId>订阅参数</pubparamId>			"+
		"    </param>                               	"+  
		"  </allparams>                             	"+  
		"</params>                                  	";
	                                               
	$.ajax({
		"url":webpath+"/interfaceOrder/insertorderinterface",
		"type":"POST",
		data:{
			"name":$("#name").val(),
			"ordercode":$("#ordercode").val(),
			"protocal":$("#protocal").val(),
			"url":$("#url").val(),
			"reqformat":$("#reqformat").val(),
			"orderdesc": $("#orderdesc").val(),
			"pubapiId": $("#id-pubapiId").val(),
		},
		success: function(data) {
			$("#add-orderInterModal").css('display','none');
			$("#add-orderParamModal").css('display','none');
			$("#id-xmlparam").css('display','block');
			$("#example-xmlcontext").val(str);
			$("#id-addParamXmlBtn").on("click", {data : data}, addXmlParam);
        },
	});
}

/**
 * xml格式添加订阅参数
 */
function addXmlParam(event){
	$.ajax({
		"url":webpath+"/orderparambeancontroller/xmlorderparam",
		"type":"POST",
		dataType:"json",
		async : false,
		data: {
			"xmlcontext":$("#id-xmlcontext").val(),
			"orderid":event.data.data,
		},
		success:function(data){
			layer.msg('添加成功', {time:5000, icon:1});
			$("#id-xmlcontext").val("");
			reloadReqTableData();
			$("#add-interfaceParamOrderModel").css('display','none');
			$("#loadOrderParam").css('display','block');
		}
	})
}

function insertApplyInterface(id){
	window.parent.showApplyInterface(id); 
	//$("#order-panelContent").attr("src","../orderIfream/orderIframe?id="+id);
}

