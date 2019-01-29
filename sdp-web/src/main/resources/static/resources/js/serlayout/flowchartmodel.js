var data;
$(function () {

});

function addInterface(pubid, parentId,flag,body,typeId) {
    var stylecode;
    if (parentId == "" || parentId == null) {
        parentId = "ROOT1";
    }
    if (flag == "" || flag == null) {
    	flag = "add";
    }
    if (flowType == "docker") {
        stylecode = '002';
    } else if(flowType == "openstack"){
        stylecode = '003';
    }else {
        stylecode = "";
    }
    
    if(typeId == ""||typeId == null){
    	typeId = "3";
    }
    if(flag == "add"){
    	$.ajax({
            "url": webpath + "/publisher/insert",
            data: {
                pubid: pubid,
                name: body.find("#pubname").val(),
                url:body.find("#puburl").val(),
                pubport: body.find("#pubport").val(),
                pubprotocal:body.find("#pubprotocal").val(),
                method:body.find("#pubmethod").val(),
                reqformat:body.find("#pubreqformat").val(),
                respformat:body.find("#pubrespformat").val(),
                reqdatatype: body.find("#pubrespformat").val(),
                returndatatype: body.find("#pubreturndatatype").val(),
                pubdesc: body.find("#pubdesc").val(),
                typeId: typeId,
                parentId: parentId,
                checkstatus: "100",
                stylecode:stylecode,
            },
            type: "POST",
            async: false,
            success: function (data) {
                //新增时推送
                //pushApi(idflowChartId);
            }
        });
    }else if(flag == "update"){
        $.ajax({
            "url":webpath+"/publisher/update",
            "type":"POST",
            dataType:"json",
            async:false,
            data:{
                pubid: pubid,
                name: body.find("#pubname").val(),
                url:body.find("#puburl").val(),
                pubport: body.find("#pubport").val(),
                pubprotocal:body.find("#pubprotocal").val(),
                method:body.find("#pubmethod").val(),
                reqformat:body.find("#pubreqformat").val(),
                respformat:body.find("#pubrespformat").val(),
                reqdatatype: body.find("#pubrespformat").val(),
                returndatatype: body.find("#pubreturndatatype").val(),
                pubdesc: body.find("#pubdesc").val(),
                typeId: typeId,
                parentId: parentId,
                checkstatus: "100",
                stylecode:stylecode,
                //className:$("#className").val(),
                //methodInClass:$("#methodInClass").val(),
                //filePath:filePath+fileName
            },
            success:function(data){
                $("#ImportPicInput").val("");
                //layer.msg('修改成功', {offset: '150px', time: 2000, icon: 2});
            }
        })
    }
    
}


//实现订阅（在注册的时候调该方法，使默认订阅）
function addOrderInterface(idflowChartId,body,flag) {
    console.log(body.find("#pubprotocal").val());
    var appIdtoken = getTokenId();
    var stylecode;
    if (flowType == "docker") {
        stylecode = '002';
    } else if (flowType == "openstack") {
        stylecode = '003';
    }else {
        stylecode = "";
    }
    if(flag == "add"){
    	//插入数据
        $.ajax({
            "url": webpath + "/interfaceOrder/insertorderinterface",
            "type": "POST",
            async: false,
            data: {
                "name": body.find("#pubname").val(),
                "url":body.find("#puburl").val(),
                "protocal":body.find("#pubprotocal").val(),
                "reqformat":body.find("#pubreqformat").val(),
                "respformat":body.find("#pubrespformat").val(),
                "ordercode": flowChartName,
                "pubapiId": idflowChartId,
                "type": "2",
                "appId": appIdtoken.appId,
                "token_id": appIdtoken.token_id,
                "stylecode":stylecode
            },
            success: function (data) {
                //$(".diagram_title").text($("#flowChartName").val());
            }
        });
    }else if(flag == "update"){
    	var datas = getOrder(idflowChartId);
    	console.log(datas);
    	//插入数据
        $.ajax({
            "url": webpath + "/interfaceOrder/updateOrderInterface",
            "type": "POST",
            async: false,
            data: {
            	"orderid":datas.orderid,
                "name": body.find("#pubname").val(),
                "url":body.find("#puburl").val(),
                "protocal":body.find("#pubprotocal").val(),
                "reqformat":body.find("#pubreqformat").val(),
                "respformat":body.find("#pubrespformat").val(),
                "ordercode": flowChartName,
                "pubapiId": idflowChartId,
                "type": "2",
                "appId": appIdtoken.appId,
                "token_id": appIdtoken.token_id,
                "stylecode":stylecode
            },
            success: function (data) {
                //$(".diagram_title").text($("#flowChartName").val());
            }
        });
    }
    

}

function getOrder(pubid){
	var datas;
	$.ajax({
        "url": webpath + "/interfaceOrder/selectByPubid",
        "type": "POST",
        async: false,
        data: {
        	"pubid":pubid
        },
        success: function (data) {
        	datas =  data;
        }
    });
	return datas;
}

//获取令牌
function getTokenId() {
    var appIdtoken;
    $.ajax({
        "url": webpath + "/security/getTokenId",
        "type": "GET",
        async: false,
        success: function (data) {
            appIdtoken = {
                "appId": data.appId,
                "token_id": data.token_id
            }
        }
    });
    return appIdtoken;
}


//添加注册接口
function addPub() {
    //console.log(getQueryString("treeNodeid"));
    console.log($("#pubprotocal").val());
    /*var nodes = resource.treeObj.getSelectedNodes();
     if($("#modaltype").val() == "update"){
     $.ajax({
     "url":webpath+"/publisher/update",
     "type":"POST",
     dataType:"json",
     async:false,
     data:{
     pubid:nodes[0].id,
     name:$("#pubname").val(),
     url:$("#puburl").val(),
     pubport:$("#pubport").val(),
     pubprotocal:$("#pubprotocal").val(),
     method:$("#pubmethod").val(),
     reqformat:$("#pubreqformat").val(),
     respformat:$("#pubrespformat").val(),
     reqdatatype:$("#pubreqdatatype").val(),
     returndatatype:$("#pubreturndatatype").val(),
     pubdesc:$("#pubdesc").val()
     },
     success:function(data){
     resource.initTree();
     }
     })
     }else{
     uploadFile();
     $.ajax({
     "url":webpath+"/publisher/insert",
     "type":"POST",
     dataType:"json",
     data:{
     name:$("#pubname").val(),
     url:$("#puburl").val(),
     pubport:$("#pubport").val(),
     pubprotocal:$("#pubprotocal").val(),
     method:$("#pubmethod").val(),
     reqformat:$("#pubreqformat").val(),
     respformat:$("#pubrespformat").val(),
     reqdatatype:$("#pubreqdatatype").val(),
     returndatatype:$("#pubreturndatatype").val(),
     pubdesc:$("#pubdesc").val(),
     parentId:nodes[0].id,
     typeId:"2"
     },
     success:function(data){
     resource.initTree();
     }
     })
     }*/
}





