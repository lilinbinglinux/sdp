var tag;
var idflowChartId;

//存储所有节点对象
var serNodeArray = [];

//存储所有线对象
var serJoinArray = [];

//存菱形节点关系映射信息
var nodeRelation = [];
//存菱形节点正则表达式信息
var nodeRegex = [];
//更新和添加标识
var updateflag = "";
//cas开始节点的默认信息
var baseInfo = null;

$(function () {
    console.log('serFlowType:' + serFlowType);
    console.log('serVerId:' + serVerId);
    console.log('idflowChartId:' + idflowChartId);

    if (idflowChartId == null || idflowChartId == "") {
        updateflag = "";
        //先添加服务
        initStartLayout();
    } else {
        updateflag = "update";
        //serNodeArray = init_serNodeArray;
        serNodeArray = JSON.parse(initserNodeArray);
        serJoinArray = JSON.parse(initserJoinArray);
        console.log(serNodeArray);
        console.log(serJoinArray);
    }

    //判断是否为cas类型，如果是则开始节点cas初始化
    var typeflag = iscasType(serFlowType);
    if (typeflag) {
        baseInfo = {
            "flowChartId": idflowChartId,
            "serVerId": getuuid(),
            "nodeId": null,
            "nodeType": "circle",
            "nodeStyle": "1",
            "componentType": "",
            "clientX": 99,
            "clientY": 100,
            "nodeWidth": 70,
            "nodeHeight": 50,
            "nodeRadius": 25,
            "other": "",
            "startSet": "",
            "endSet": "",
            "port": "",
            "agreement": "http",
            "method": "GET",
            "reqformat": "application/json",
            "respformat": "application/json",
            "inCharset": "UTF-8",
            "outCharset": "UTF-8",
            "reqdatatype": "Object",
            "returndatatype": "Object",
            "serdesc": "",
            "serviceType": "",
            "inparameter": null,
            "outparameter": {
                "list": [{
                    "id": getuuid(),
                    "ecode": "casServerUrlPrefix",
                    "reqdesc": "",
                    "reqtype": "String",
                    "parampos": "3",
                    "isempty": false,
                    "type": "0",
                    "maxlength": "",
                    "namespace": "",
                    "parentId": ""
                }, {
                    "id": getuuid(),
                    "ecode": "returnurl",
                    "reqdesc": "",
                    "reqtype": "String",
                    "parampos": "3",
                    "isempty": false,
                    "type": "0",
                    "maxlength": "",
                    "namespace": "",
                    "parentId": ""
                }, {
                    "id": getuuid(),
                    "ecode": "loginId",
                    "reqdesc": "",
                    "reqtype": "String",
                    "parampos": "3",
                    "isempty": false,
                    "type": "0",
                    "maxlength": "",
                    "namespace": "",
                    "parentId": ""
                }, {
                    "id": getuuid(),
                    "ecode": "password",
                    "reqdesc": "",
                    "reqtype": "String",
                    "parampos": "3",
                    "isempty": false,
                    "type": "0",
                    "maxlength": "",
                    "namespace": "",
                    "parentId": ""
                }]
            },
            "nodeName": "开始",
            "serName": ""
        }
    }


    $("#save_btn").click(function () {
        if (idflowChartId == null || idflowChartId == "") {
            layer.msg('请双击开始节点添加信息', {time: 2000, icon: 5});
            return;
        }
        var data = tag.getData(idflowChartId);
        //更新每个节点的最终的位置
        var finalnodearray = data.nodearray;
        var endnodecount = 0;
        for (var i = 0; i < finalnodearray.length; i++) {
            for (var j = 0; j < serNodeArray.length; j++) {
                if (finalnodearray[i].nodeId == serNodeArray[j].nodeId) {
                    serNodeArray[j].clientX = finalnodearray[i].clientX;
                    serNodeArray[j].clientY = finalnodearray[i].clientY;
                    serNodeArray[j].nodeHeight = finalnodearray[i].nodeHeight;
                    serNodeArray[j].nodeRadius = finalnodearray[i].nodeRadius;
                    serNodeArray[j].nodeWidth = finalnodearray[i].nodeWidth;
                    serNodeArray[j].endSet = finalnodearray[i].endSet;
                    serNodeArray[j].startSet = finalnodearray[i].startSet;
                }
                if (serNodeArray[j].nodeName == "结束" && serNodeArray[j].nodeType == "circle") {
                    serNodeArray.splice(j, 1);
                }
            }
        }
        //统计是否有结束节点，如果没有则为新建，需添加
        var endinitnode = "";
        for (var k = 0; k < serNodeArray.length; k++) {
            if (serNodeArray[k].nodeType == "circle" && serNodeArray[k].nodeName == "开始") {
                endinitnode = serNodeArray[k];
            }
        }
        for (var i = 0; i < finalnodearray.length; i++) {
            //添加结束节点
            if (finalnodearray[i].nodeName == "结束" && finalnodearray[i].nodeType == "circle") {
                var endnode = new startSerNodeObj(data.flowChartId, serVerId, finalnodearray[i].nodeId, "结束", "circle", "4",
                    finalnodearray[i].clientX, finalnodearray[i].clientY, finalnodearray[i].nodeWidth, finalnodearray[i].nodeHeight, finalnodearray[i].nodeRadius, "", finalnodearray[i].endSet, finalnodearray[i].startSet, finalnodearray[i].setline,
                    endinitnode.serName, endinitnode.agreement, endinitnode.reqformat, endinitnode.respformat,
                    endinitnode.reqdatatype, endinitnode.returndatatype, endinitnode.serviceType, endinitnode.serdesc, endinitnode.inparameter, endinitnode.outparameter,
                    endinitnode.inCharset, endinitnode.outCharset);
                serNodeArray.push(endnode);
            }
        }
        
        
        //设置聚合节点之前的接口节点的calltype属性
        for(var i=0;i<serNodeArray.length;i++){
        		if(serNodeArray[i].nodeType == "circle" && (serNodeArray[i].nodeStyle == "5" || serNodeArray[i].nodeStyle == "6")){
        			var startSet = serNodeArray[i].startSet;
        			console.log(typeof(startSet));
        			var startNodeId;
        			if(typeof(startSet) == "string"){
        				startNodeId = startSet;
        				for(var k=0;k<serNodeArray.length;k++){
    						if(serNodeArray[k].nodeId == startNodeId){
    							serNodeArray[k].callType = "1";
    						}else{
    							serNodeArray[k].callType = "0";
    						}
    					}
        			}else{
        				if(startSet != null && startSet != undefined){
            				for(var j=0;j<startSet.length;j++){
            					startNodeId = startSet[j];
            					for(var k=0;k<serNodeArray.length;k++){
            						if(serNodeArray[k].nodeId == startNodeId){
            							serNodeArray[k].callType = "1";
            						}else{
            							serNodeArray[k].callType = "0";
            						}
            					}
            				}
            			}
        			}
        			
        		}
        }

        //修改线节点的基本信息
        if (data.joinarray != null) {
            for (var i = 0; i < (data.joinarray).length; i++) {
                var join = (data.joinarray) [i];
                for (var j = 0; j < serJoinArray.length; j++) {
                    if (join.joinId == serJoinArray[j].joinId) {
                        serJoinArray[j].startNodeId = join.startNodeId;
                        serJoinArray[j].endNodeId = join.endNodeId;
                        serJoinArray[j].joinType = join.joinType;
                        serJoinArray[j].joinDirection = join.joinDirection;
                        serJoinArray[j].path = join.path;
                    }
                }
            }

            console.log(serJoinArray);
        }

        saveBtnLayer();
    });
});


//初始化开始节点和结束节点
function initStartLayout() {
    var startoptions = {
        "x": 100,
        "y": 100,
        "nodeType": "circle",
        "nodeName": "开始",
        "nodeStyle":"1"
    };
    tag.addNode(startoptions);
    var endoptions = {
        "x": 500,
        "y": 100,
        "nodeType": "circle",
        "nodeName": "结束",
        "nodeStyle":"4"
    };
    tag.addNode(endoptions);

}

//节点双击
function nodeClick(node) {
    var nodeType = node.data("nodeType");
    var nodeName = node.data("nodeName");
    var nodeId = node.data("nodeId");
    var nodeStyle = node.data("nodeStyle");

    var data = tag.getData(idflowChartId);
    var nodearray = data.nodearray;
    var componentType;
    for (var i = 0; i < nodearray.length; i++) {
        var startnode = nodearray[i];

        //从画布中获取到开始节点基本参数
        if (startnode.nodeName == "开始" && startnode.nodeType == "circle" && (idflowChartId == "" || idflowChartId == null)) {
            var startSerNode = new startSerNodeObj(data.flowChartId, serVerId, startnode.nodeId, "开始", "circle", "1",
                startnode.clientX, startnode.clientY, startnode.nodeWidth, startnode.nodeHeight, startnode.nodeRadius, "", "", "", startnode.setline,
                "", "", "", "", "", "", "", "", "", "", "", "");
            if (serNodeArray.length > 0) {
                for (var j = 0; j < serNodeArray.length; j++) {
                    if (startSerNode.nodeId == serNodeArray[j].nodeId) {
                        //如果已经存在，则不进行添加
                        //removeByValue(serNodeArray,serNodeArray[j]);
                    } else if (startSerNode.nodeId != serNodeArray[j].nodeId && serNodeArray[j].nodeType == "circle" && startnode.nodeName == "开始") {
                        //添加开始节点
                        serNodeArray.push(startSerNode);
                    }
                }
            } else {
                serNodeArray.push(startSerNode);
            }

        }
    }
    for (var k = 0; k < serNodeArray.length; k++) {
        var serNoode = serNodeArray[k];
        if (serNoode.nodeId == nodeId) {
            componentType = serNoode.componentType;
        }
    }
    if (componentType == '1') {
        peosonalLayer(node);
    }
    //开始节点和矩形节点
    else if ((nodeType == "circle" && nodeName == "开始") || nodeType == "rectangle") {
            serInfoLayer(node);
    }
    //聚合节点弹框
//    else if(nodeType == "circle"&&(nodeStyle == "6" || nodeStyle == "5")){
//    			serInfoLayer(node);
//    }

    //菱形节点
//    if (nodeType == "diamond") {
//        realtionLayer(node);
//    }
    
}

//线连接事件
function afterDrawLine(line) {
    if (idflowChartId == null || idflowChartId == "") {
        layer.msg('请双击开始节点添加信息', {time: 2000, icon: 5});
        return;
    }
    var startSet = line.data('startSet');
    var endSet = line.data('endSet');
    var data = tag.getData(idflowChartId);

    var join_serviceType = "";
    if (serNodeArray != null && serNodeArray.length > 0) {
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "circle" && serNodeArray[i].nodeName == "开始") {
                // join_serviceType = serNodeArray[i].serviceType;
                join_serviceType = serNodeArray[i].serviceType;
            }
        }
    }

    var nodejoinObj = new serJoinObj(idflowChartId, serVerId, line.data('joinId'), startSet.data('nodeId'), endSet.data('nodeId'), line.data('joinType'), "2", line.data('joinDirection'), line.data('path'),
        startSet.data('nodeId'), endSet.data('nodeId'), join_serviceType, "", "");

    serJoinArray.push(nodejoinObj);
}

//线点击事件
function lineClick(line) {
    realtionLayer(line);
}

//图形拖拽增加节点
function addShapNode(ui) {
    if (idflowChartId == null || idflowChartId == "") {
        layer.msg('请双击开始节点添加信息', {time: 2000, icon: 5});
        return;
    }
    else {
        var obj = ui.draggable;
        var offset = ui.offset;
        var x = offset.left - $("#designer_canvas").offset().left;
        var y = offset.top - $("#designer_canvas").offset().top;
        var nodeType = $(obj).attr("nodetype");
        var nodeStyle = $(obj).attr('nodeStyle');
        var nodeId = getuuid();
        var options = {
            "nodeId": nodeId,
            "x": x,
            "y": y,
            "nodeType": nodeType,
            "nodeStyle":nodeStyle
        };
        if (nodeType == 'circle'&&nodeStyle=='5') {
            options.nodeName = '聚合开始';
        }else if (nodeType == 'circle'&&nodeStyle=='6') {
            options.nodeName = '聚合结束';
        }

        tag.addNode(options);
        var data = tag.getData(idflowChartId);
        var nodearray = data.nodearray;
        for (var i = 0; i < nodearray.length; i++) {
            //拖拽过来添加接口节点
            if (nodeType == "rectangle" && nodeId == nodearray[i].nodeId) {
                var internode = nodearray[i];
                var componentType = '0';//组件类型 1自定义组件 0普通
                var interSerNode = new interfaceSerNodeObj(data.flowChartId, serVerId, internode.nodeId, "组件", "rectangle", "3",
                    internode.clientX, internode.clientY, internode.nodeWidth, internode.nodeHeight, internode.nodeRadius, "",internode.startSet, internode.endSet, internode.setline,
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", componentType);
                serNodeArray.push(interSerNode);
            }
            
            if(nodeType == "circle" && nodeId == nodearray[i].nodeId){
            		var asynchronnode = nodearray[i];
                var componentType = '0';//组件类型 1自定义组件 0普通
                var asynchronSerNode = new groupSerNodeObj(data.flowChartId, serVerId, asynchronnode.nodeId, asynchronnode.nodeName, "circle", asynchronnode.nodeStyle,
                		asynchronnode.clientX, asynchronnode.clientY, asynchronnode.nodeWidth, asynchronnode.nodeHeight, asynchronnode.nodeRadius, asynchronnode.startSet, asynchronnode.endSet, asynchronnode.setline,
                    "","","","");
                serNodeArray.push(asynchronSerNode);
            }

            //拖拽过来添加条件节点
//            if (nodeType == "diamond" && nodeId == nodearray[i].nodeId) {
//                var diamondnode = nodearray[i];
//                var diamondSerNode = new diamondSerObj(data.flowChartId, serVerId, diamondnode.nodeId, "条件", "diamond", "2",
//                    diamondnode.clientX, diamondnode.clientY, diamondnode.nodeWidth, diamondnode.nodeHeight, diamondnode.nodeRadius, diamondnode.startSet, diamondnode.endSet,
//                    "", "");
//                serNodeArray.push(diamondSerNode);
//            }
        }

        if (nodeType == "rectangle") {
            layer.open({
                type: 2,
                title: '',
                btn: ["确认", "取消"],
                area: ['620px', '440px'],
                content: webapp + '/processlayer/apiparam?nodeId=' + nodeId + "&nodeType=" + nodeType + "&nodeName=" + "" + "&serFlowType=" + "" + "&typeName=" + typeName,
                shade: 0.1,
                success: function (layero, index) {
                },
                yes: function (index, layero) {
                    var body = layer.getChildFrame('body', index);
                    //是否点击过参数菜单
                    var initparamflag = body.find("#clickTag").val();
                    //说明没有点击过参数菜单，需要父页面来手动初始化
                    var childbaseserNodeObj;
                    if (initparamflag != "1") {
                        var baseserNodeObj;
                        for (var i = 0; i < serNodeArray.length; i++) {
                            if (serNodeArray[i].nodeId == nodeId) {
                                baseserNodeObj = serNodeArray[i];
                                removeByValue(serNodeArray, serNodeArray[i]);
                            }
                        }

                        var serviceType = "";
                        if (nodeType == "circle" && nodeName == "开始") {
                            serviceType = body.find("#serviceTypeId").val();
                        }
//                        else {
//                            serviceType = body.find("#apiserviceTypeId").val();
//                        }

                        baseserNodeObj.serName = body.find("#pubname").val();
                        baseserNodeObj.agreement = body.find("#pubprotocal").val();
                        baseserNodeObj.url = body.find("#puburl").val();
                        baseserNodeObj.port = body.find("#pubport").val();
                        baseserNodeObj.method = body.find("#pubmethod").val();
                        baseserNodeObj.reqformat = body.find("#pubreqformat").val();
                        baseserNodeObj.respformat = body.find("#pubrespformat").val();
                        baseserNodeObj.reqdatatype = body.find("#pubreqdatatype").val();
                        baseserNodeObj.returndatatype = body.find("#pubreturndatatype").val();

                        // baseserNodeObj.serviceType = serviceType;
                        baseserNodeObj.serviceType = typeId;

                        baseserNodeObj.serdesc = body.find("#pubdesc").val();
                        baseserNodeObj.inCharset = body.find("#inCharset").val();//请求参数字符集
                        baseserNodeObj.outCharset = body.find("#outCharset").val();//响应参数字符集
                        serNodeArray.push(baseserNodeObj);
                        childbaseserNodeObj = baseserNodeObj;
                    } else {
                        idflowChartId = tag.getData(idflowChartId).flowChartId;
                        var iframeWin = window[layero.find('iframe')[0].name];
                        for (var i = 0; i < serNodeArray.length; i++) {
                            if (serNodeArray[i].nodeId == nodeId) {
                                removeByValue(serNodeArray, serNodeArray[i]);
                            }
                        }

                        serNodeArray.push(iframeWin.baseserNodeObj);
                        childbaseserNodeObj = iframeWin.baseserNodeObj;
                    }

                    tag.updateName(nodeId, childbaseserNodeObj.serName);
                    layer.close(index);
                },
                btn2: function (index, layero) {
                    //直接关闭弹窗，画布上不添加矩形
                    layer.close(index);
                }
            });
        } else if (nodeType == "diamond") {
            options.nodeName = "条件";
        } else if (nodeType == 'personal') {

        }
    }
}

//圆形和矩形上点击弹框
function serInfoLayer(node) {
    var nodeType = node.data("nodeType");
    var nodeName = node.data("nodeName");
    var nodeId = node.data("nodeId");
    var nodeStyle = node.data("nodeStyle");
    var data = tag.getData(idflowChartId);
    var nodearray = data.nodearray;
    var baseserNodeObj;
    layer.open({
        type: 2,
        title: '设置',
        btn: ["确认", "取消"],
        area: ['620px', '500px'],
        content: [webapp + '/processlayer/apiparam?nodeId=' + nodeId + "&nodeType=" + nodeType + "&nodeName=" + escape(escape(nodeName))
        + "&serFlowType=" + serFlowType + "&nodeStyle=" + nodeStyle + "&typeName=" + typeName, 'no'],
        shade: 0.1,
        success: function (layero, index) {
            var typeflag = iscasType(serFlowType);
            if (typeflag) {
                baseInfo.nodeId = nodeId;
            }

        },
        yes: function (index, layero) {
            idflowChartId = tag.getData(idflowChartId).flowChartId;
            var body = layer.getChildFrame('body', index);
            var serName = body.find("#pubname").val();
            if (nodeStyle != "6"&& nodeStyle != "5"&& !checkName(serName)) {
                return;
            }
            if(nodeStyle == "1" && nodeType=="circle" &&  updateflag == ""){
            		var flag = checkSerName(serName);
            		console.log(flag);
            		if(!flag){
//            			layer.open({
//            				type: 1,
//            				title: false, //不显示标题栏
//            				btn:false,
//            				content: '<div style="    height: 26px;width: 200px;font-size: 15px;font-style: normal;">该名称已被占用</div>'
//            			});
            			//打酱油
            			layer.msg('该名称已被占用！请修改后重试', {time: 1500, icon: 2});
            			return;
            		}
            }
            //是否点击过参数菜单
            var initparamflag = body.find("#clickTag").val();
            //说明没有点击过参数菜单，需要父页面来手动初始化
            if (initparamflag != "1") {
                for (var i = 0; i < serNodeArray.length; i++) {
                    if (serNodeArray[i].nodeId == node.data("nodeId")) {
                        baseserNodeObj = serNodeArray[i];
                        removeByValue(serNodeArray, serNodeArray[i]);
                    }
                }
                var serviceType = "";
                var url = '';
                if (nodeType == "circle" && nodeName == "开始") {
                    serviceType = body.find("#serviceTypeId").attr("idvalue");
                } else {
                    //serviceType = body.find("#apiserviceTypeId").val();
                    url = body.find('#puburl').val();
                }

                baseserNodeObj.serName = body.find("#pubname").val();
                baseserNodeObj.agreement = body.find("#pubprotocal").val();
                baseserNodeObj.port = body.find("#pubport").val();
                baseserNodeObj.method = body.find("#pubmethod").val();
                baseserNodeObj.reqformat = body.find("#pubreqformat").val();
                baseserNodeObj.respformat = body.find("#pubrespformat").val();
                baseserNodeObj.reqdatatype = body.find("#pubreqdatatype").val();
                baseserNodeObj.returndatatype = body.find("#pubreturndatatype").val();
                baseserNodeObj.serdesc = body.find("#pubdesc").val();
                baseserNodeObj.inCharset = body.find("#inCharset").val();
                baseserNodeObj.outCharset = body.find("#outCharset").val();

                // baseserNodeObj.serviceType = serviceType;

	              baseserNodeObj.serviceType = typeId;

                baseserNodeObj.url = url;

                if (nodeType == "rectangle") {
                    baseserNodeObj.nodeName = body.find("#pubname").val();
                }
                serNodeArray.push(baseserNodeObj);
            } else {
                idflowChartId = tag.getData(idflowChartId).flowChartId;
                var iframeWin = window[layero.find('iframe')[0].name];
                for (var i = 0; i < serNodeArray.length; i++) {
                    if (serNodeArray[i].nodeId == node.data("nodeId")) {
                        removeByValue(serNodeArray, serNodeArray[i]);
                    }
                }
                baseserNodeObj = iframeWin.baseserNodeObj;
                if (nodeType == "rectangle") {
                    baseserNodeObj.nodeName = body.find("#pubname").val();
                }
                
                if(nodeType == "circle" && (nodeStyle == "6" || nodeStyle == "5")){
            			if (baseserNodeObj.allparameter != null
                                && typeof(baseserNodeObj.allparameter) != undefined && baseserNodeObj.allparameter != "") {	
            				var allparameter = (baseserNodeObj.allparameter).list;
            				allparameter = orderSet(allparameter);
                        (baseserNodeObj.allparameter).list = allparameter;
            			}
            			serNodeArray.push(baseserNodeObj);
                }else{
                	//设置入参的order
                    if (baseserNodeObj.inparameter != null
                        && typeof(baseserNodeObj.inparameter) != undefined && baseserNodeObj.inparameter != "") {
                        var inparameter = (baseserNodeObj.inparameter).list;
                        inparameter = orderSet(inparameter);
                        (baseserNodeObj.inparameter).list = inparameter;
                    }

                    //设置出参的order
                    if (baseserNodeObj.outparameter != null
                        && typeof(baseserNodeObj.outparameter) != undefined && baseserNodeObj.outparameter != "") {
                        var outparameter = (baseserNodeObj.outparameter).list;
                        outparameter = orderSet(outparameter);
                        (baseserNodeObj.outparameter).list = outparameter;
                    }

                    serNodeArray.push(baseserNodeObj);
                }
                if (nodeType == "rectangle") {
                    tag.updateName(nodeId, baseserNodeObj.serName);
                }
                }

            console.log(serNodeArray);
            layer.close(index);
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

function checkSerName(serName){
	var flag = "";
    $.ajax({
        "url": webpath + "/processlayer/checkSreName",
        "type": "POST",
        data: {
            "serName": serName,
        },
        async: false,
        success: function (data) {
        		flag = data;
        }
    });
    return flag;
}


//设置参数的order属性
function getOrderParam(paramters) {
    var order = 0;
    var parentId = "";
    for (var i = 0; i < paramters.length; i++) {
        if (paramters[i].reqtype == "List" || paramters[i].reqtype == "List_Object" || paramters[i].reqtype == "List_List") {
            order = 0;
        } else {
            if (paramters[i].parentId != "" && paramters[i].parentId != null) {
                if (paramters[i].parentId == parentId) {
                    paramters[i].order = order;
                    order++;
                } else {
                    for (var j = 0; j < paramters.length; j++) {
                        if (paramters[j].id == paramters[i].parentId
                            && (paramters[j].reqtype == "List" || paramters[j].reqtype == "List_Object" || paramters[j].reqtype == "List_List")) {
                            parentId = paramters[i].parentId;
                            paramters[i].order = order;
                            order++;
                        }
                    }
                }

            }
        }
    }

    return paramters;
}

//设置list，list_object，list_list的order
function orderSet(paramters) {
    var order = 0;
    var parentId = "";
    for (var i=0; i<paramters.length; i++){
        if (paramters[i].reqtype == "List"||paramters[i].reqtype == "List_Object"||paramters[i].reqtype == "List_List"){
            order = 0;
            for (var j=0; j<paramters.length; j++){
                if (paramters[j].parentId == paramters[i].id) {
                    if (paramters[j].reqtype != "List" && paramters[j].reqtype != "List_Object" && paramters[j].reqtype != "List_List"){
                        paramters[j].order = order;
                        order++;
                    }
                }
            }
        }
    }
    return paramters;
}

//菱形上点击弹框
function realtionLayer(line) {
//    var nodeType = node.data("nodeType");
//    var nodeName = node.data("nodeName");
//    var nodeId = node.data("nodeId");
//    var data = tag.getData(idflowChartId);
//    var nodearray = data.nodearray;
    var joinId = line.data("joinId");
    layer.open({
        type: 2,
        title: '设置',
        area: ['1000px', '500px'],
        offset: 'auto',
        fixed: false,
        content: [webapp + '/processlayer/relationparam?joinId=' + joinId, 'no']
    });
}

//自定义组件上点击弹窗
function peosonalLayer(node) {
    var personalHtml = initPersonalHtml();
    layer.open({
        type: 1,
        title: '组件信息',
        area: ['500px', '600px'],
        content: personalHtml,
        shade: 0.1
    });
}


//保存时弹框
function saveBtnLayer() {
    console.log(serNodeArray);
    console.log(serJoinArray);
    console.log(updateflag);
    if (updateflag == "update") {
        var typeflag = iscasType(serFlowType);
        if (typeflag) {
            updateAll("");
            window.location.href = webpath + "/processTable/index/" + typeId +"/" + typeName;
        } else {
            layer.confirm('是否覆盖当前版本？', {
                btn: ['新建版本', '覆盖当前版本'],
                area: ['300px', '180px'],
            }, function (index, layero) {
                //新建版本：ser_main更新，ser_version添加
                updateAll("noreplace");
                //layer.close(index);
                window.location.href = webpath + "/processTable/index/" + typeId +"/" + typeName;
            }, function (index, layero) {
                //覆盖版本：ser_main更新，ser_version更新
                layer.msg('确定覆盖当前版本吗，覆盖后不可恢复', {
                    btn: ['确定', '取消'],
                    btn1: function (index, layero) {
                        updateAll("isreplace");
                        window.location.href = webpath + "/processTable/index/" + typeId +"/" + typeName;
                    },
                    btn2: function (index, layero) {
                        window.location.href = webpath + "/processTable/index/" + typeId +"/" + typeName;
                    }
                });
            });
        }

    }
    else {
        addAll();
        var typeflag = iscasType(serFlowType);
        if (typeflag) {
            window.location.href = webpath + "/serspLogin/index";
        } else {
            window.location.href = webpath + "/processTable/index/" + typeId +"/" + typeName;
        }

    }


}

//保存所有信息
function addAll() {
    $.ajax({
        "url": webpath + "/serprocess/addNode",
        "type": "POST",
        data: {
            "serNodeArray": JSON.stringify(serNodeArray),
            "serJoinArray": JSON.stringify(serJoinArray),
            "serFlowType": serFlowType
        },
        async: false,
        success: function (data) {
            if (data == "success") {
                layer.msg('添加成功', {time: 2000, icon: 5});
            }
        }
    });
}

//更新流程图
function updateAll(updateInfo) {
    $.ajax({
        "url": webpath + "/serprocess/updateNode",
        "type": "POST",
        data: {
            "serId": idflowChartId,
            "updateFlag": updateInfo,
            "serNodeArray": JSON.stringify(serNodeArray),
            "serJoinArray": JSON.stringify(serJoinArray),
            "serFlowType": serFlowType
        },
        async: false,
        success: function (data) {
            if (data == "success") {
                layer.msg('更新成功', {time: 2000, icon: 5});
            }
        }
    });
}

function nodeClose(element) {
    var select_node = tag.getSelectedNode();
    var select_nodeId, select_nodetype;
    if (select_node.data('nodeId') != null) {
        select_nodeId = select_node.data('nodeId');
        select_nodetype = select_node.data("nodeType");
    } else {
        select_nodeId = select_node.data('joinId');
        select_nodetype = 'line';
    }
    var outparameterIds = [];
    var preNode, nextNode;
    //1、如果删除的节点有出参，则把出参id保存到outparameterIds数组中
    //2、serNodeArray移除该节点
    for (var i = 0; i < serNodeArray.length; i++) {
        var serNoode = serNodeArray[i];
        if (serNoode.nodeId == select_nodeId) {
            if (serNoode.outparameter != null) {
                if (serNoode.outparameter.list != null) {
                    for (var j = 0; j < serNoode.outparameter.list.length; j++) {
                        var outobj = serNoode.outparameter.list[j];
                        outparameterIds.push(outobj.id);
                    }
                }

            }
            serNodeArray.splice(i, 1);
        }
    }
    //3、对图形的判断，并做出对应的处理
    //对圆形的判断 开始节点和结束节点不能删除
    //对菱形的判断 因为其他节点不保存菱形上的信息 ，所以只是删除该节点的信息，其他节点不做处理
    //对矩形的判断 删除前后两个节点的信息
    //      因为startSet,endSet只保存前一个和后一个的节点nodeId,所以
    //      如果前面是菱形，菱形节点的参数relationSet，conditionObj全部清空
    //      如果后面是菱形，菱形节点的参数relationSet，conditionObj如果涉及到矩形的响应参数，则删除。


    //      因为startSet,endSet只保存前一个和后一个的节点nodeId,所以
    //      取出前面节点的setline，菱形节点的参数relationSet，conditionObj全部清空
    //      如果后面是菱形，菱形节点的参数relationSet，conditionObj如果涉及到矩形的响应参数，则删除。
    if (select_nodetype == "circle") {
        if (select_node.data("nodeName") == "开始" || select_node.data("nodeName") == "结束") {
            layer.msg('开始或结束节点不能移除', {time: 2000, icon: 5});
            return false;
        }
    } else if (select_nodetype == "rectangle") {
        var startSet = select_node.data("startSet");
        if (startSet != null && startSet.length > 0 && startSet != undefined) {
            preNode = tag.getNodeById(startSet[0]);
            if (preNode.data("setline") != null) {
                var setline = preNode.data("setline");
                for (var i = 0; i < setline.length; i++) {
                    var joinId = setline[i].data('joinId');
                    for (var j = 0; j < serJoinArray.length; j++) {
                        if (joinId == serJoinArray[j].joinId) {
                            serJoinArray.splice(j, 1);
                        }
                    }
                }
            }
        }
        var nextArr = select_node.data("setline");
        if (nextArr != null && nextArr.length > 0) {
            for (var i = 0; i < nextArr.length; i++) {
                var nextJoinId = nextArr[i].data('joinId');
                for (var j = 0; j < serJoinArray.length; j++) {
                    if (nextJoinId == serJoinArray[j].joinId) {
                        serJoinArray.splice(j, 1);
                    }
                }
            }
        }

    }
    else if (select_nodetype == 'line') {
        for (var i = 0; i < serJoinArray.length; i++) {
            var serJoin = serJoinArray[i];
            if (serJoin.joinId == select_nodeId) {
                serJoinArray.splice(i, 1);
            }

        }
    }
    console.log(serNodeArray);
    console.log(serJoinArray);
    return true;
}

//线修改位置后处理映射关系
function changeLine(line){
	if(serJoinArray != null){
		for(var i=0;i<serJoinArray.length;i++){
			if(serJoinArray[i].joinId == line.data("joinId")){
			  removeByValue(serJoinArray, serJoinArray[i]);
			  break;
			}
		}
	}
	
	console.log(serJoinArray);
}


////删除流程图上的某个节点
//function backspacedelete(){
//	var element = tag.getSelectedNode();
//    if (element != null) {
//    	if(!nodeClose(element)){
//    		return;
//    	};
//        tag.closeElement(element);
//    }
//    // $("#save_btn").trigger("click");
//    return true;
//}

function initPersonalHtml() {
    var personalHtml =
        '<div class="panel panel-body" id="personalComponent">' +
        '<h4>基本信息</h4>' +
        '<div id="pubprotocal" class=""><span>请求协议：</span><span>http</span></div>' +
        '<div id="pubreqformat" class=""><span>请求格式：</span><span>application/json</span></div>' +
        '<div id="pubrespformat" class=""><span>响应格式：</span><span>application/json</span></div>' +
        '<div id="pubreqdatatype" class=""><span>请求值类型：</span><span>Object</span></div>' +
        '<div id="pubreturndatatype" class=""><span>返回值类型：</span><span>Object</span></div>' +
        '<div id="pubreturndatatype" class=""><span>返回值类型：</span><span>Object</span></div>' +
        '<h4>请求参数</h4>'
        + '<table class="table table-bordered table-striped" id="">'
        + '<tr><td>编码</td><td>描述</td><td>类型</td><td>是否为空</td></tr>'
        + '<tr><td>casServierUrlPrefix</td><td></td><td></td><td></td></tr>'
        + '<tr><td>loginId</td><td></td><td></td><td></td></tr>'
        + '<tr><td>password</td><td></td><td></td><td></td></tr>'
        + '<tr><td>returnurl</td><td></td><td></td><td></td></tr>'
        + '</table>'
        + '<h4>响应参数</h4>'
        + '<table class="table table-bordered">'
        + '<tr><td>编码</td><td>描述</td><td>类型</td><td>是否为空</td></tr>'
        + '<tr><td>message</td><td></td><td></td><td></td></tr>'
        + '</table>'
        + '</div>';
    return personalHtml;
}

//流程图加载完之后，进行判断，如果条件不如为空，在线上面追加文字
function initLineText() {
    for (var i = 0; i < serJoinArray.length; i++) {
        var serJoinobj = serJoinArray[i];
        var line = tag.getLineById(serJoinobj.joinId);
        var conditionObj = serJoinobj.conditionObj;
        var relationSet = serJoinobj.relationSet;

        if (conditionObj != null) {
            if (conditionObj.preparamIdSet.length > 0 &&conditionObj.preparamnameSet.length > 0) {
                if (relationSet.length > 0) {
                    tag.addLineText(line, '映射条件');
                } else {
                    tag.addLineText(line, '条件');
                }
            }else{
                if (relationSet.length > 0) {
                    tag.addLineText(line, '映射');
                }
            }
        } else {
            if (relationSet.length > 0) {
                tag.addLineText(line, '映射');
            }
        }
    }
}