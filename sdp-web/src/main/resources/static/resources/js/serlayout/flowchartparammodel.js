
var pubSerobjs = new Array();
//var preid = getQueryString("preid");
//var nextid = getQueryString("nextid");
//var pubSer = getQueryString("pubSers");


var preid = "4028ee185d729924015d7299649f0001";
var nextid = "4028ee185d72a9fa015d72aa261a0001";
var idflowChartId = "ac7fc2c6074d42f3b2a707fd33c95de6";
var pubSer = "{'pubid':'4028ee185d729596015d7295ce950002','name':'A','sort':0},{'pubid':'4028ee185d729924015d7299649f0001','name':'B','sort':1},{'pubid':'4028ee185d72a9fa015d72aa261a0001','name':'C','sort':2}";

//设置input框的全局对象
var inputText;
var preinputText;
var relationShips = new Array();

$(function () {
	//节点数据转为对象
	getPubSerObjs(pubSer);
	
	//初始化参数表格
    //initParamTable();
    
	var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};
	
    //初始化参数树形
    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes(preid,nextid));
    
    //参数映射
    $(".ztreeparam").click(function(){
    	//获取当前行的编码id
    	preinputText = $(this).prev().val();
    	
    	var cityObj = $(this);
    	inputText = cityObj;
    	var cityOffset = $(this).offset();
    	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

    	$("body").bind("mousedown", onBodyDown);
    })
    
    $("#layout-addParamBtn").bind("click",addLayoutParam);
    $("#layout-cancelBtn").click(function(){
    	
    })
});

function initParamTable(){
	alert("123");
	$('#layoutparamtable').treegrid({  
		
		data: getParams(),
		iconCls: 'icon-ok',
		rownumbers: true,
		animate: true,
		fitColumns: true,
		width:500,
		height:500,
        idField:'id',    
        treeField:'ecode',     
        
        columns:[[    
            {field:'ecode',title:'编码',width:150,align:'center'},   
            {field:'reqdesc',title:'描述',width:200,align:'center'},   
            {field:'reqtype',title:'类型',width:150,align:'center'},
            {field:'xx',title:'参数映射',width:130,
                formatter: function(value,row,index){
                	var s = "<div>" +
                	"<input type='hidden' value='"+row.ecode+"'>"+
	    			"<input type='text' class='ztreeparam' placeholder = '设置映射参数'>"+
	    			'</div>';
                	return s;
                }
            }
        ]]
    });
}

function getParams(){
	//展示end节点的请求参数
	$("#type").val("0");
	$("#pubid").val(nextid);
	var jsonStr = form.serializeStr($("#layout-pubidtotype"));
	$.ajax({
			"url":webpath+"/reqparam/selectParams",
			"type" : 'POST',
			 data :{
			  "jsonStr":jsonStr
			 } ,
			 async : false,
			success:function(data){
//				console.log(data);
				objdata = data;
				//objdata = "["+JSON.stringify(data)+"]"
			}
		})
    return objdata;
}   

function beforeClick(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	if (!check) layer.msg('   请选择参数！', {time: 1000, icon:2});
	return check;
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getSelectedNodes(),
	v = "";
	vid = "";
	
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (vid.length > 0 ) vid = vid.substring(0, vid.length-1);
	inputText.val(v);
	
	//var relation = "{\"pubid:\""+nextid+"\",\"preparamid\":\""+preinputText+"\",\"nextparamid\":\""+vid+"\"}";
	
	var robj = new relationObj(idflowChartId,nextid,preinputText,vid); 
	relationShips.push(robj);
	
	$("#menuContent").fadeOut("fast");
}

function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

//获取所有服务节点
function getPubSerObjs(pubSer){
	var ss = pubSer.split('},');
	for(var i=0;i<ss.length;i++){
		if(ss[i].indexOf("}")< 0){
			ss[i] = ss[i]+"}";
		}
		ss[i] = ss[i].replace(/'/g, '"');
		pubSerobjs.push(JSON.parse(ss[i]));
	}
	//console.log(pubSers);
	//return pubSerobjs;
}

//获取前几个服务节点的响应参数
function getTreeNodes(preid,nextid){
	var maxsort;
	var pubids = new Array();
	var zNodes;
	
	for(var i=0;i<pubSerobjs.length;i++){
		if(pubSerobjs[i].pubid == nextid){
			maxsort = pubSerobjs[i].sort; 
		}
	}
	
	for(var i=0;i<pubSerobjs.length;i++){
		if(pubSerobjs[i].sort < maxsort){
			pubids.push(pubSerobjs[i].pubid);
		}
	}
	
	$("#type").val("1");
	$("#pubid").val(pubids);
	
	$.ajax({
		"url":webpath+"/reqparam/selectTreeNode",
		"type" : 'POST',
		 data :{
		  "type":$("#type").val(),
		  "pubids":$("#pubid").val()
		 } ,
		 async : false,
		success:function(data){
			zNodes = data;
		}
	})
	
	return zNodes;
}

function addLayoutParam(){
	var pubrobj = new pubrelationObj(nextid,relationShips);
	
	//添加到js对象中保存，在保存流程图时一块保存
	pubrelationObjs.push(pubrobj);
	console.log(pubrobj);
}

//映射关系obdj
function relationObj(flowChartId,pubid,preparamid,nextparamid){
	this.flowChartId = flowChartId;
	this.pubid = pubid;
	this.preparamid = preparamid;
	this.nextparamid = nextparamid;
}

function pubrelationObj(pubid,relationShips){
	this.pubid = pubid;
	this.relationShips = relationShips;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
       return decodeURI(r[2]);
    }else{
       return null;
    } 
 }