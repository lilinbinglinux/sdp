$(document).ready(function(){
	
	//定义全局使用的layer样式
	layer.config({
	 	 skin: 'layui-layer-ext'
	});
	
	$("#reqdiv").click(function(){
		$("#respdiv").removeClass();
		$(this).addClass("active");
		$("#res-req").val("0");
		initParamTable();
	})
	
	$("#respdiv").click(function(){
		$("#reqdiv").removeClass();
		$(this).addClass("active");
		$("#res-req").val("1");
		initParamTable();
	})
});

/*//初始化表格
function initParamTable(){
	$('#order_reqparamtable').treegrid({
		data: getParams(),
		iconCls: 'icon-ok',
		rownumbers: true,
		animate: true,
		fitColumns: true,
        idField:'id',    
        treeField:'ecode',
        columns:[[    
            {field:'ecode',title:'编码',width:150,align:'center'},   
            {field:'reqdesc',title:'描述',width:200,align:'center'},
            {field:'parampos',title:'参数位置',width:150,align:'center'},
            {field:'reqtype',title:'类型',width:150,align:'center'},
            {field:'isempty',title:'是否为空',width:100,align:'center'}
        ]]
    });
}*/

function initParamTable(value){
    manager = $("#order_reqparamtable").ligerGrid({
        columns: [
        { display: '编码', name: 'ecode', id: 'ecodeName',width: 150, align: 'left' },
        { display: '描述', name: 'reqdesc', width: 220, align: 'center' },
        { display: '类型', name: 'reqtype',width: 200, type: 'String', align: 'center' }, 
        { display: '是否为空',name:'isempty',align: 'center' ,width: 110,
        	render: function (item)
            {	
        		var s;
        		if(item.isempty == "1"){
        			s = "是";
        		}else if(item.isempty == "0"){
        			s = "否";
        		}
            	return s;
        }},
        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '90%',
        data: getParams(),
        alternatingRow: false, 
        enabledEdit: false, 
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId'
        },
        onSelectRow: function (rowdata, rowindex)
        {
        	//获取当前行的编码id
        //	preinputText = rowdata.ecode;
        	//alert("123");
        },
        usePager:false
        
    })
}

//获取数据
function getParams(){
	var objdata ;
	var jsonStr = form.serializeStr($("#pubidtoreq"));
	var jsonObj = jQuery.parseJSON(jsonStr);
	jsonObj.layout = false;
	jsonStr = JSON.stringify(jsonObj);
	$.ajax({
			"url":webpath+"/reqparam/selectParams",
			"type" : 'POST',
			 data :{
			  "jsonStr":jsonStr,
			 } ,
			 async : false,
			success:function(data){
				objdata = data;
			}
		})
    return objdata;
}   


//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadReqTableData(isCurrentPage){
	$("#order_reqparamtable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}
