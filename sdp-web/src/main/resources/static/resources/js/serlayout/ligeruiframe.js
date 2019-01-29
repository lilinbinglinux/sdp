var allprenodes = new Array();
var inputArray = [], initinputs = [], nameArray = [], constantArray = [];

var initRegexArray = new Array();
var inputText;
var allTreeNodes = new Array();

//用于映射关系回显
$(function () {
	$("#type").val("0");
    $("#layout-addParamBtn").bind("click", addLayoutParam);
    $("#reqdiv").click(function () {
        $("#respdiv").removeClass();
        $(this).addClass("active");
        $("#layoutparamtable").css("display", "block");
        $("#conditionset").css("display", "none");
        $("#type").val("0");
    })

    $("#respdiv").click(function () {
        $("#reqdiv").removeClass();
        $(this).addClass("active");
        $("#conditionset").css("display", "block");
        $("#layoutparamtable").css("display", "none");
        $("#type").val("1");
    })
});

//1.点击菱形，初始化节点信息，process.js中调用
function initLayoutData(node) {
    allprenodes.splice(0, allprenodes.length);
    getAllPreNodes(node);
    initTree(allprenodes,node);
}

//2.点击菱形，初始化参数表格信息，process.js中调用
function initManager(node) {
	var width = $(document).width()-$(document).width()/4.5;
	console.log(width);
    initinputs = [];
    manager = $("#layoutparamtable").ligerGrid({
        columns: [
            {display: '编码', name: 'ecode', id: 'ecodeName', width: width/6, align: 'center'},
            {display: '描述', name: 'reqdesc', align: 'center'},
            {display: '类型', name: 'reqtype', width: width/5, type: 'String', align: 'center'},
            {
                display: '参数映射', name: 'paramFlowChartBean', align: 'center', width: width/5,
                render: function (item) {

                    var initvalue = ""; 
                    var initid = "";
                    //数据库匹配
                    if (initinputs.length > 0 || nameArray > 0) {
                        for (var i = 0; i < initinputs.length; i++) {
                            if (item.id == initinputs[i].itemid&&initinputs[i].nodeid == node.data("nodeId")) {
                                initvalue = initinputs[i].pre_paramname;
                                initid = initinputs[i].pre_paramid;
                            }
                        }
                        for (var k = 0; k < nameArray.length; k++) {
                            if (nameArray[k].id == item.id) {
                                initvalue = nameArray[k].setvalue;
                                initid = "";
                            }
                        }

                    }
                    //树点击之后匹配
                    if (inputArray != null) {
                		for (var i = 0; i < inputArray.length; i++) {
                            var setting = inputArray[i];
                            console.log(inputArray);
                            if (item.id == setting.id&&setting.diamondnodeid == node.data("nodeId")) {
                                initvalue = setting.value;
                                initid = setting.initidvalue;
                            }
                        }
                        
                    }
                    
                    //树未点击，设置常量
                    if (constantArray != null) {
                        for (var i = 0; i < constantArray.length; i++) {
                            var setting = constantArray[i];
                            if (item.id == setting.id&&constantArray[i].diamondnodeid == node.data("nodeId")) {
                                initvalue = setting.initidvalue;
                            }
                        }
                    }
                    if (initid == null) {
                        initid = "";
                    }
                    if (initvalue == null) {
                        initvalue = "";
                    }
                    
                    var s =
                        "<input type='hidden' value='" + item.ecode + "'>" +
                        "<input type='text' id='real_" + item.__index + "' class='ztreeparam11' placeholder = '设置映射参数' style='border: none;height: 32px;width:160px' " +
                        "inititemid='" + item.id + "'" +
                        " value='" + initvalue + "'" +
                        " initidvalue='" + initid + "'" +
                        " onclick=\"inputClick(this,'" + item.ecode + "','" + item.id + "','" + initid + "')\">";
                    return s;
                }
            },
            {
                display: '映射规则', name: 'regex', width: width/5, type: 'String', align: 'center',
                render: function (item) {
                    var initregex = "", initconstant = "";
                    //数据库匹配
                    if (initinputs.length > 0) {
                        for (var i = 0; i < initinputs.length; i++) {
                            if (item.id == initinputs[i].itemid) {
                                initregex = initinputs[i].regex;
                                initconstant = initinputs[i].constantparam;
                                if (initregex == null)
                                    initregex = "";
                                if (initconstant == null)
                                    initconstant = "";
                            }
                        }
                    }
                    //点击设过值后
                    if (initRegexArray != null && initRegexArray.length > 0) {
                        for (var i = 0; i < initRegexArray.length; i++) {
                        	console.log(initRegexArray[i].id);
                        	console.log(item.id);
                            if (initRegexArray[i].id == item.id) {
                                initregex = initRegexArray[i].initregex;
                                initconstant = initRegexArray[i].initconstant;
                            }
                        }
                    }
                    var str = "<a onclick=\"regexmodel('" + item.__index + "','" + item.id + "')\">" +
                        "<input type='hidden' value='" + initregex + "' " +
                        " initconstant='" + initconstant + "' " +
                        " placeholder = '点击设置' style='border: none;height: 32px;width:160px' readonly" +
                        " id='regex_" + item.__index +
                        "' inititemid='" + item.id + "'\>" +
                        "点击设置</a>";
                    return str;
                }
            },
        ],
        //width: '950px', height: '410px',
        width: width, height: '300px',
        data: getParams(initinputs, node),
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: false,
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId'
        },
        usePager: false,
        initinputs: initinputs

    });
    return manager;
}
//3.点击菱形，初始化条件设置信息process.js调用
function initCondition(flowChartId, nextid,nodeRelation,node) {
    if (nextid == ""||nextid == null) {
        nextid = flowChartId;
    }
    $.ajax({
        "url": webpath + '/paramFlowChart/findLayoutParam',
        "type": 'POST',
        async: false,
        data: {
        	"node_id":node.data("nodeId"),
            "pubid": nextid,
            "flowChartId": flowChartId,
        },
        success: function (data) {
        	var initdatas = new Array();
        	if(data != null&&data.length>0){
        		for(var i=0;i<data.length;i++){
        			if(data[i].node_id == node.data("nodeId")){
        				initdatas.push(data[i]);
        			}
        		}
        	}
        	
            //数据库回显
            $("#conditionset").find(".component").children().remove();
            if (initdatas !== null && initdatas.length > 0) {
                for (var i = 0; i < initdatas.length; i++) {
                    $(".addvar").trigger("click");

                    var j = i + 1;
                    $("#conditionset").find("#parameter" + j).val(initdatas[i].pre_paramname);
                    $("#conditionset").find("#parameter" + j).attr("initidvalue", initdatas[i].pre_paramid);
                }
            } else {
                $(".addvar").trigger("click");
            }

            //变量条件回显
            if (nodeRelation.length > 0) {
                for (var i = 0; i < nodeRelation.length; i++) {
                    if (nodeRelation[i].parm != null) {
                        if (nodeRelation[i].parm.pubid == nextid&&nodeRelation[i].nodeId == node.data("nodeId")) {
                            $("#conditionset").find(".component").children().remove();
                            for (var j = 0; j < nodeRelation[i].parm.arr.length; j++) {
                                var text = nodeRelation[i].parm.arr[j].preparamname;
                                var id = nodeRelation[i].parm.arr[j].preparamid;

                                    $(".addvar").trigger("click");

                                var ii = j + 1;

                                $("#conditionset").find("#parameter" + ii).val(text);
                                $("#conditionset").find("#parameter" + ii).attr("initidvalue", id);
                            }

                        }

                    }
                }
            }
        }
    });
}
//获取之前所有矩形节点
function getAllPreNodes(node) {
    var prenode;
    var startSet = node.data("startSet");
    if (startSet != null && startSet.length > 0) {
        prenode = tag.getNodeById(startSet[0]);
        if (prenode.data("nodeType") != "diamond") {
            allprenodes.push(prenode);
        }
        getAllPreNodes(prenode);
    }
    return allprenodes;
}

function initTree(allprenodes,node) {
    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false,
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: showRemoveBtn,
            showRenameBtn: showRenameBtn
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onTreeClick,
            beforeRemove: beforeRemove,
            onRemove: onRemove
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes(idflowChartId, allprenodes));
    
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var newNode = {id:node.data("nodeId"), pId:"", name:"diamond"};
    treeObj.addNodes(null, 0,newNode);
    
    var nodes = treeObj.getNodes();
    treeObj.hideNode(nodes[0]);
}

//点击加号
function addHoverDom(treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if (treeNode.id == idflowChartId || treeNode.pId == idflowChartId) {
        if (treeNode.reqtype == "Object" || treeNode.reqtype == "List" || treeNode.id == idflowChartId) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            $("#addBtn_" + treeNode.tId).css({
                "margin-right": "2px",
                "background-position": "-143px -1px",
                "vertical-align": "top"
            });
            var btn = $("#addBtn_" + treeNode.tId);
            if (btn) btn.bind("click", function () {
                //添加参数弹框
                paramsetmodel(idflowChartId, treeNode, treeObj);
                return false;
            });
        }
    }
};
//删除之前做判断
function beforeRemove(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    if(confirm('确定要删除么')){ //只有当点击confirm框的确定时，该层才会关闭
	    return true;
	  }
	  return false; 
}
//删除节点
function onRemove(e, treeId, treeNode) {
    $.ajax({
        "url": webpath + "/reqparam/delete",
        "type": "POST",
        data: {
            "id": treeNode.id
        },
        success: function (data) {
            if (data == "deletesuccess") {
                layer.msg('删除成功！', {time: 1000, icon: 1});
                layer.close(index);
                //刷新项目信息表
            } else if (data == "deletefalse") {
                layer.msg('删除失败，请先删除子节点！', {time: 1000, icon: 1});
                layer.close(index);
            }
        }
    });
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};
function showRemoveBtn(treeId, treeNode) {
	if(treeNode.pId == null){return false;}
    var btn = $("#removeBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
        //添加参数弹框
        //paramsetmodel(flowChartId,treeNode,treeObj);
        return false;
    });
    return true;
    /*if(treeNode.pId == flowChartId){
     return true;
     }
     return false;*/
}
function showRenameBtn(treeId, treeNode) {
    return false;
}
//点击input框展示树形
function inputClick(vthis, ecode, id, initid) {
    var cityObj = $(vthis);
    //获取到当前的input对象
    inputText = cityObj;
    var cityOffset = $(vthis).offset();
    var x = cityOffset.left - ($(document.body).width()) * 0.1;
    var y = cityOffset.top - ($(document.body).height()) * 0.1;
    $("#menuContent").css({left: x + "px", top: y + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
//设置树的父节点不能点击
function beforeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent && treeNode.flag != "0");
    if (!check) layer.msg('   请选择参数！', {time: 1000, icon: 2});
    return check;
}

//树点击事件给每行数据赋值
function onTreeClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        v = "",
        vid = "";

    nodes.sort(function compare(a, b) {
        return a.id - b.id;
    });
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
        vid += nodes[i].id + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (vid.length > 0) vid = vid.substring(0, vid.length - 1);
    inputText.val(v);
    inputText.attr("initidvalue", vid);

    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var treenodes = treeObj.getNodesByParam("name", "diamond", null);

    var setting = {
        "diamondnodeid": treenodes[0].id,
        "id": inputText.attr("inititemid"),
        "value": v,
        "initidvalue": vid
    };

    inputArray.push(setting);
    $("#menuContent").fadeOut("fast");
}

function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}

//参数树的数据
function getParams(initinputs, node) {
    var endSetId = node.data("endSet");
    var endnode = tag.getNodeById(endSetId[0]);
    var pubidobj = endnode.data("other");
    //展示end节点的请求参数
    $("#type").val("0");

    var jsonStr = form.serializeStr($("#layout-pubidtotype"));
    var jsonobj = JSON.parse(jsonStr); //由JSON字符串转换为JSON对象
    if (endnode.data("nodeName") == "结束") {
        jsonobj.type = "1";
        $("#pubid").val(idflowChartId);
        jsonobj.pubid = idflowChartId;
    } else {
        jsonobj.type = "0";
        $("#pubid").val(pubidobj.pubid);
        jsonobj.pubid = pubidobj.pubid;
    }
    jsonobj.flowChartId = idflowChartId;
    jsonobj.layout = true;
    $.ajax({
        "url": webpath + "/reqparam/selectParams",
        "type": 'POST',
        data: {
            "jsonStr": JSON.stringify(jsonobj)
        },
        async: false,
        success: function (data) {
            datas = null;
            var datalist = data.list;
            datas = datalist.length;
            for (var i = 0; i < datalist.length; i++) {
                var beanlist = datalist[i].paramFlowChartBeans;
                if (beanlist != null) {
                    for (var j = 0; j < beanlist.length; j++) {
                        var inputobj = {
                            "nodeid": beanlist[j].node_id,
                            "itemid": beanlist[j].next_paramid,
                            "pre_paramid": beanlist[j].pre_paramid,
                            "pre_paramname": beanlist[j].pre_paramname,
                            "regex": beanlist[j].regex,
                            "constantparam": beanlist[j].constantparam
                        }
                        initinputs.push(inputobj);
                    }
                }

            }
            objdata = data;
        }
    })
    return objdata;
}

//获取前几个服务节点的响应参数
function getTreeNodes(idflowChartId, prenodes) {
    allTreeNodes.splice(0, allTreeNodes.length);
    var pubids = new Array();
    var zNodes;
    for (var i = 0; i < prenodes.length; i++) {
        if (prenodes[i].data("nodeType") == "rectangle") {
            pubids.push((prenodes[i].data("other")).pubid);
        }
    }

    $("#type").val("1");
    $("#pubid").val(pubids);
    $.ajax({
        "url": webpath + "/reqparam/selectTreeNode",
        "type": 'POST',
        async: false,
        data: {
            "flowChartId": idflowChartId,
            "type": $("#type").val(),
            "pubids": pubids.toString()
        },
        success: function (data) {
            zNodes = data;
        }
    })
    allTreeNodes = zNodes;
    return zNodes;
}

//添加映射关系
function addLayoutParam(node) {
    constantArray.splice(0, constantArray.length);
    var allinfos = new Array();
    var relationShips = new Array();
    var regexs = new Array();

    var endSetId = node.data("endSet");
    var endnode = tag.getNodeById(endSetId[0]);
    var pubidobj = endnode.data("other");

    var startSetId = node.data("startSet");
    var startnode = tag.getNodeById(startSetId[0]);
    var prepubidobj = startnode.data("other");

    if (datas != null && datas > 0) {
        for (var i = 0; i < datas; i++) {
            //获取所有参数映射
            dvalue = $("#real_" + i).attr("initidvalue");
            dname = $("#real_" + i).val();
            did = $("#real_" + i).attr("inititemid");
            dregx = $("#regex_" + i).val();
            dconstant = $("#regex_" + i).attr("initconstant");
            if (dname == "") {
                for (var j = 0; j < inputArray.length; j++) {
                	console.log(did);
                	console.log(inputArray[j]);
                    if (inputArray[j].id == did) {
                        removeByValue(inputArray, inputArray[j]);
                    }
                }
                for (var k = 0; k < initinputs.length; k++) {
                    if (did == initinputs[k].itemid) {
                        var setting = {
                            "id": initinputs[k].itemid,
                        };
                        nameArray.push(setting);
                    }
                }

            }
            if ((dname != "" && dname != null) || (dregx != "" && dregx != null) || (dconstant != "" && dconstant != null)) {
            	var pubid = "";
            	var prepubid = "";
            	if(pubidobj != null){
            		if(pubidobj.pubid == ""){
            			pubid = idflowChartId;
            		}else{
            			pubid = pubidobj.pubid;
            		}
            	}
            	if(prepubidobj != null){
            		if(prepubidobj.pubid == ""){
            			prepubid = idflowChartId;
            		}else{
            			prepubid = prepubidobj.pubid;
            		}
            	}else{
            		prepubid = idflowChartId;
            	}
            	
            	//结束节点默认pubid为idflowChartId
            	if(endnode.data("nodeName") == "结束"){
            		pubid = idflowChartId;
            	}
            	
            	var pre_paramid = $("#real_" + i).attr("initidvalue");
            	if($("#real_" + i).val() == ""||$("#real_" + i).val() == null){
            		pre_paramid = "";
            	}
                var robj = new relationObj(
                    node.data("nodeId"),
                    getuuid(),
                    prepubid,
                    pubid,
                    idflowChartId,
                    pre_paramid,
                    $("#real_" + i).val(),
                    manager.getRow(i).id,
                    manager.getRow(i).ecode,
                    $("#regex_" + i).val(),
                    $("#regex_" + i).attr("initconstant"),
                    ""
                );
                
                console.log(robj);
                relationShips.push(robj);
            }
            //设置常量回显
            if (dvalue == "" && dname != "") {
                var setting = {
                	"diamondnodeid":node.data("nodeId"),
                    "id": did,
                    "initidvalue": $("#real_" + i).val()
                };
                constantArray.push(setting);
            }
            if (dvalue != "" && dname != "") {
                var count = 0;
                for (var j = 0; j < allTreeNodes.length; j++) {
                    if (dname == allTreeNodes[j].name) {
                        count++;
                    }
                }
                if (count == 0) {
                    var setting = {
                        "id": did,
                        "initidvalue": dname
                    };
                    constantArray.push(setting);
                }
            }
        }
    }
    return relationShips;
}

//映射关系obdj
function relationObj(diamondid, relationid, prepubid, pubid, flowChartId, preparamid, preparamname, nextparamid, nextparamname, regex, constantparam, type) {
    //获取当前点击菱形的id
    this.diamondid = diamondid;
    this.relationid = relationid;
    this.prepubid = prepubid;
    this.pubid = pubid;
    this.flowChartId = flowChartId;
    this.preparamid = preparamid;
    this.preparamname = preparamname;
    this.nextparamid = nextparamid;
    this.nextparamname = nextparamname;
    this.regex = regex;
    this.constantparam = constantparam;
    this.type = type;
}

//设置规则弹框
function regexmodel(id, itemid) {
    var initregexval = $("#regex_" + id).val();
    var initconstantval = $("#regex_" + id).attr("initconstant");
    $("#layoutregex").val(initregexval);
    $("#layoutconstant").val(initconstantval);
    layer.open({
        type: 1,
        shade: false,
        title: false,
        area: ['500px', '200px'], //宽高
        content: $("#layoutparamalert").css("display", "block"),
        closeBtn: 0,
        skin: 'layer-demo',
        btn: ['保存', '取消'],
        yes: function (index, layero) {
            regex = $("#layoutregex").val();
            constantparam = $("#layoutconstant").val();
            $("#regex_" + id).val(regex);
            $("#regex_" + id).attr("initconstant", constantparam);
            layer.close(index);
            var obj = {
                "id": itemid,
                "initregex": regex,
                "initconstant": constantparam
            }
            initRegexArray.push(obj);
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

//设置参数弹框
function paramsetmodel(pubid, treeNode, treeObj) {
    $("#layoutparamset").find(".layout_paramadd").each(function (i) {
        $(this).val("");
    });
    layer.open({
        type: 1,
        shade: 0.3,
        title: false,
        area: ['500px', '300px'], //宽高
        content: $("#layoutparamset"),
        closeBtn: 0,
        btn: ['保存', '取消'],
        yes: function (index, layero) {
            var objarr = new Array();
            var id = getuuid();
            var obj = {
                "id": id,
                "ecode": $("#ecode").val(),
                "reqdesc": $("#reqdesc").val(),
                "reqtype": $("#reqtype").val(),
                "parampos": $("#parampos").val(),
                "isempty": $("#isempty").val(),
                "type": 0,
                "parentId": treeNode.id
            };
            objarr.push(obj);
            $.ajax({
                "url": webpath + "/reqparam/insert",
                "type": "POST",
                dataType: "json",
                async: false,
                data: {
                    "reqobj": JSON.stringify(objarr),
                    "pubid": pubid

                },
                success: function (data) {
                    layer.close(index);
                    treeObj.addNodes(treeNode, {id: id, pId: treeNode.id, name: $("#ecode").val()});
                },
                error: function (data) {
                    layer.close(index);
                }
            })
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}
function addVar() {
    var j = $("#conditionset").find(".component").children().length + 1;
    var html = '<div class="component1"><label>变量：</label><input type="text" id="parameter' + j + '" initidvalue="" class="input_control" onclick="inputClick(this)" readonly="true"></div>';
    $("#conditionset").find(".component:last").append(html);
}
function deleteVar(){
	var j = $("#conditionset").find(".component").children().length;
	$("#conditionset").find(".component").children().eq(j-1).remove();
}
//保存条件设置
function saveDate(node) {
    var startSetId = node.data("startSet");
    var startnode = tag.getNodeById(startSetId[0]);
    var prepubid = "";
    if(startnode.data("nodeName") == "开始"){
    	prepubid = idflowChartId;
    }
    
    if(startnode.data("other") != null&&startnode.data("nodeName")!="开始"){
    	prepubid = startnode.data("other").pubid;
    }
    
    var parm = {}, param = {};
    var condition;
    var arr = new Array();
    var j = $("#conditionset").find(".component").children().length;
    for (var int = 0; int < j; int++) {
        param["preparamname"] = $("#conditionset").find(".component").children().eq(int).children().eq(1).val();
        param["preparamid"] = $("#conditionset").find(".component").children().eq(int).children().eq(1).attr("initidvalue");
        param["prepubid"] = prepubid;
        arr[int] = param;
        param = {};
    }
    
    condition = $("#conditionset").find("#condition").val();
    parm["arr"] = arr;
    parm["condition"] = condition;
    parm["node_id"] = node.data("nodeId");
    return parm;
}
