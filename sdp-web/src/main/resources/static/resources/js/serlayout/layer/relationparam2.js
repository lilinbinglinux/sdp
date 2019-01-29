var allprenodes = new Array();
var inputText;

var manager;

//定义全局base节点
var baseidflowChartId = parent.idflowChartId;
var basetag = parent.tag;
var baseserJoinArray = parent.serJoinArray;
var baseserNodeArray = parent.serNodeArray;
var basejoin;

var baseserJoinObj;

var baserelArray = new Array();


$(function () {
    //初始化页面base数据
    basejoin = basetag.getLineById(joinId);
    for (var i = 0; i < baseserJoinArray.length; i++) {
        if (baseserJoinArray[i].joinId == joinId) {
        		baseserJoinObj = baseserJoinArray[i];
            if (baseserJoinObj.relationSet != null && baseserJoinObj.relationSet != undefined && baseserJoinObj.relationSet != "") {
                baserelArray = baseserJoinObj.relationSet;
            }
        }
    }

    //初始化表格
    initManager();
    //初始化表格中的树
    initTree();
    //初始化条件
    initCondition();
    //添加映射关系
    $("#addRelationBtn").bind("click", addBaserelArray);

    //菜单切换
    $("#reqdiv").click(function () {
        $("#respdiv").removeClass();
        $(this).addClass("active");
        $("#layoutparamtable").css("display", "block");
        $("#conditionset").css("display", "none");
        $("#type").val("0");
    });

    $("#respdiv").click(function () {
        $("#reqdiv").removeClass();
        $(this).addClass("active");
        $("#conditionset").css("display", "block");
        $("#layoutparamtable").css("display", "none");
        $("#type").val("1");
    })

});


// -----------------------参数表格初始化和相关方法-----------------------
//初始化映射表格
function initManager() {
    var width = $('#layoutparam-divlayer').width();
    manager = $("#layoutparamtable").ligerGrid({
        columns: [
            {display: '编码', name: 'ecode', id: 'ecodeName', width: width / 6, align: 'center'},
            {display: '描述', name: 'reqdesc', width: width / 5, align: 'center'},
            {display: '类型', name: 'reqtype', width: width / 5, type: 'String', align: 'center'},
            {
                display: '参数映射', name: 'paramFlowChartBean', align: 'center', width: width / 5,
                render: function (item) {
                    var initvalue = "";
                    var initid = "";

                    //再次点击后回显
                    if (baserelArray != null && baserelArray != undefined) {
                        for (var i = 0; i < baserelArray.length; i++) {
                            if (baserelArray[i].nextparamId == item.id) {
                                initvalue = baserelArray[i].preparamname;
                                initid = baserelArray[i].preparamId;
                            }
                        }
                    }
                    var s =
                        "<input type='hidden' id='ecode_" + item.__index + "' value='" + item.ecode + "'>" +
                        "<input type='text' id='real_" + item.__index + "' class='ztreeparam11' placeholder = '设置映射参数' style='border: none;height: 32px;width:160px' " +
                        "inititemid='" + item.id + "'" +
                        " value='" + initvalue + "'" +
                        " initidvalue='" + initid + "'" +
                        " onclick=\"inputClick(this,'" + item.ecode + "','" + item.id + "','" + initid + "')\">";
                    return s;
                }
            },
            {
                display: '映射规则', name: 'regex', width: width / 5, type: 'String', align: 'center',
                render: function (item) {
                    var initregex = "";
                    var initconstant = "";

                    //再次点击后回显
                    if (baserelArray != null && baserelArray != undefined) {
                        for (var i = 0; i < baserelArray.length; i++) {
                            if (baserelArray[i].nextparamId == item.id) {
                                initregex = baserelArray[i].regex;
                                if (initregex == "undefined"||typeof(initregex) == "undefined") {
                                    initregex = '';
                                }
                                initconstant = baserelArray[i].constantparam;
                                if (initconstant == "undefined"||typeof(initconstant) == "undefined") {
                                    initconstant = '';
                                }
                            }
                        }
                    }
                    var aInputText = "点击设置";
                    if(initconstant !=""&&initconstant!=undefined&&initconstant!=null){
                    	aInputText = initconstant;
                    }

                    var str = "<div onclick=\"regexmodel('" + item.__index + "','" + item.id + "')\">" +
                        "<input type='hidden' value='" + initregex + "' " +
                        " initconstant='" + initconstant + "' " +
                        " placeholder = '点击设置' style='border: none;height: 32px;width:160px' readonly" +
                        " id='regex_" + item.__index +
                        "' inititemid='" + item.id + "'/ \>" + 
                        "<a>"+aInputText+"</a>"+
                        "</div>";
                    return str;
                }
            }
        ],
        width: '100%',
        height: '85%',
        data: getParams(),
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: false,
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId'
        },
        usePager: false
    });
    return manager;
}

//获取表格数据
function getParams() {
    var result;
    var endSetId = basejoin.data("endSet").data("nodeId");
    if (endSetId != null && endSetId != "") {
        var endnode = basetag.getNodeById(endSetId);
        if (endnode != undefined) {
            if (endnode.data("nodeType") == "circle" && endnode.data("nodeName") == "结束") {
                //结束节点之前的映射是响应参数的映射
                var nextOutParamListSet;
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "开始" && baseserNodeArray[i].nodeType == "circle") {
                        nextOutParamListSet = (baseserNodeArray[i].outparameter);
                    }
                }
                result = nextOutParamListSet;
            } else if(endnode.data("nodeType") == "circle" && (endnode.data("nodeStyle") == "6" || endnode.data("nodeStyle") == "5")){
            		var nextOutParamListSet;
            		for (var i = 0; i < baseserNodeArray.length; i++){
            			if (endnode.data("nodeId") == baseserNodeArray[i].nodeId) {
            				nextOutParamListSet = (baseserNodeArray[i].allparameter);
            			}
            			if (nextOutParamListSet != null) {
                            if (nextOutParamListSet.list != null) {
                                for (var j = 0; j < nextOutParamListSet.list.length; j++) {
                                    var obj = nextOutParamListSet.list[j];
                                    if (obj.children != null) {
                                    		nextOutParamListSet.list[j].children = null;
                                    }

                                }
                            }
                        }
            			result = nextOutParamListSet;
            		}
            }else {
                var nextInParamListSet;
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (endnode.data("nodeId") == baseserNodeArray[i].nodeId) {
                        nextInParamListSet = (baseserNodeArray[i].inparameter);
                        if (nextInParamListSet != null) {
                            if (nextInParamListSet.list != null) {
                                for (var j = 0; j < nextInParamListSet.list.length; j++) {
                                    var obj = nextInParamListSet.list[j];
                                    if (obj.children != null) {
                                        nextInParamListSet.list[j].children = null;
                                    }

                                }
                            }
                        }

                    }
                }
                result = nextInParamListSet;
            }
        }
    }
    return result;

}

//点击input框展示树形
function inputClick(vthis, ecode, id, initid) {
    var cityObj = $(vthis);
    //获取到当前的input对象
    inputText = cityObj;
    var cityOffset = $(vthis).offset();
    var x = cityOffset.left;
    var y = cityOffset.top + ($(document.body).height()) * 0.05;
    $("#menuContent").css({left: x + "px", top: y + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

//设置规则弹框
function regexmodel(id, itemid) {
    var initregexval = $("#regex_" + id).val();
    if (initregexval == "undefined") {
        initregexval = '';
    }
    var initconstantval = $("#regex_" + id).attr("initconstant");
    if (initconstantval == "undefined") {
        initconstantval = '';
    }
    $("#layoutregex").val(initregexval);
    $("#layoutconstant").val(initconstantval);

    var layoutconstant = $("#layoutconstant").val();
    if (typeof(layoutconstant) == undefined || layoutconstant == "undefined") {
        layoutconstant = "";
    }

    layer.open({
        type: 1,
        shade: false,
        title: '映射规则',
        area: ['500px', '200px'], //宽高
        content: $("#layoutparamalert").css("display", "block"),
        closeBtn: 0,
        skin: 'layer-demo',
        btn: ['保存', '取消'],
        yes: function (index, layero) {
            regex = $("#layoutregex").val();
            layoutconstant = $("#layoutconstant").val();
            if (layoutconstant == '' || layoutconstant == null) {
                $("#regex_" + id).attr("initconstant", '');
                $("#regex_" + id).siblings("a").text('点击设置');
            } else {
                $("#regex_" + id).attr("initconstant", layoutconstant);
                $("#regex_" + id).siblings("a").text(layoutconstant);
            }
            $("#regex_" + id).val(regex);
            layer.close(index);
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

//-----------------------参数表格初始化上树的方法-----------------------
//初始化表格中的树
function initTree() {
    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false
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
    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes());

    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var newNode = {id: basejoin.data("joinId"), pId: "", name: "join"};
    treeObj.addNodes(null, 0, newNode);

    var nodes = treeObj.getNodes();
    treeObj.hideNode(nodes[0]);
}

//获取前几个服务节点的响应参数
function getTreeNodes() {
    var result;
    var endSetId = basejoin.data("endSet").data("nodeId");
    var startnode = basejoin.data("startSet");
    
    if (endSetId != null && endSetId != "") {
        var endnode = basetag.getNodeById(endSetId);
        if (endnode != undefined && endnode.data("nodeType") == "circle" && endnode.data("nodeName") == "结束") {
            if (startnode != null && startnode != "") {

                allprenodes.splice(0, allprenodes.length);
                getAllPreNodes(startnode);
                
                var indexarray = new Array();
                var nodeflag = true;
                for(var i=0;i<allprenodes.length;i++){
                		if(allprenodes[i].data("nodeName") == "开始"&&nodeflag){
                			indexarray.push(allprenodes[i]);
                			nodeflag = false;
                		}else if(allprenodes[i].data("nodeName") != "开始"){
                			if (indexarray.indexOf(allprenodes[i]) == -1) indexarray.push(allprenodes[i]);
                		}
                }
                indexarray.push(startnode);
                allprenodes = indexarray;
                
                var outparam_zNodes = [];
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    for (var k = 0; k < allprenodes.length; k++) {
                        if (allprenodes[k].data("nodeId") == baseserNodeArray[i].nodeId) {
                            var dataname = "";
                            if (baseserNodeArray[i].serName != undefined && baseserNodeArray[i].serName != "") {
                                dataname = baseserNodeArray[i].serName;
                            } else {
                                dataname = baseserNodeArray[i].nodeName;
                            }

                            var out_zsernode = {
                                id: baseserNodeArray[i].nodeId,
                                pId: "ROOT",
                                name: dataname,
                                reqtype: ""
                            };
                            outparam_zNodes.push(out_zsernode);


                            if (baseserNodeArray[i].nodeType == "circle" && baseserNodeArray[i].nodeName == "开始") {
                                var inparameter = (baseserNodeArray[i].inparameter).list;
                                if (inparameter != null && inparameter.length > 0) {
                                    for (var j = 0; j < inparameter.length; j++) {
                                        var pId = baseserNodeArray[i].nodeId;
                                        if (inparameter[j].parentId != "" && inparameter[j].parentId != undefined) {
                                            pId = inparameter[j].parentId;
                                        }
                                        var in_zparamnode = {
                                            id: inparameter[j].id,
                                            pId: pId,
                                            name: inparameter[j].ecode,
                                            reqtype: inparameter[j].reqtype
                                        };
                                        outparam_zNodes.push(in_zparamnode);
                                    }
                                } else {
                                    outparam_zNodes.pop();
                                }

                            } else {
                                var outparameter;
                                if(baseserNodeArray[i].nodeStyle == "5" || baseserNodeArray[i].nodeStyle == "6"){
                                		outparameter = (baseserNodeArray[i].allparameter).list;
                                }else{
                                		outparameter = (baseserNodeArray[i].outparameter).list;
                                }
                                
                                if (outparameter != null && outparameter.length > 0) {
                                    for (var j = 0; j < outparameter.length; j++) {
                                        var pId = baseserNodeArray[i].nodeId;
                                        if (outparameter[j].parentId != "" && outparameter[j].parentId != undefined) {
                                            pId = outparameter[j].parentId;
                                        }
                                        var out_zparamnode = {
                                            id: outparameter[j].id,
                                            pId: pId,
                                            name: outparameter[j].ecode,
                                            reqtype: outparameter[j].reqtype
                                        };
                                        outparam_zNodes.push(out_zparamnode);
                                    }

                                } else {
                                    outparam_zNodes.pop();
                                }
                            }

                        }
                    }
                }
                result = outparam_zNodes;
            }

        } else {
            allprenodes.splice(0, allprenodes.length);
            getAllPreNodes(startnode);
            
            var indexarray = new Array();
            var nodeflag = true;
            
            for(var i=0;i<allprenodes.length;i++){
	        		if(allprenodes[i].data("nodeName") == "开始"&&nodeflag){
	        			indexarray.push(allprenodes[i]);
	        			nodeflag = false;
	        		}else if(allprenodes[i].data("nodeName") != "开始"){
	        			if (indexarray.indexOf(allprenodes[i]) == -1) indexarray.push(allprenodes[i]);
	        		}
            }
            
            indexarray.push(startnode);
            allprenodes = indexarray;

            var inparam_zNodes = [];
            
            for (var i = 0; i < baseserNodeArray.length; i++) {
                for (var k = 0; k < allprenodes.length; k++) {
                    if (allprenodes[k].data("nodeId") == baseserNodeArray[i].nodeId) {
                        if (baseserNodeArray[i].serName != undefined && baseserNodeArray[i].serName != "") {
                            dataname = baseserNodeArray[i].serName;
                        } else {
                            dataname = baseserNodeArray[i].nodeName;
                        }
                        var in_zsernode = {
                            id: baseserNodeArray[i].nodeId,
                            pId: "ROOT",
                            name: dataname,
                            reqtype: ""
                        };
                        inparam_zNodes.push(in_zsernode);
                        if (allprenodes[k].data("nodeType") == "circle" && allprenodes[k].data("nodeName") == "开始") {
                        		if((baseserNodeArray[i].inparameter) != null){
                        			var inparameter = (baseserNodeArray[i].inparameter).list;
                                    if (inparameter != null && inparameter.length > 0) {
                                        for (var j = 0; j < inparameter.length; j++) {

                                            var pId = baseserNodeArray[i].nodeId;
                                            if (inparameter[j].parentId != "" && inparameter[j].parentId != undefined) {
                                                pId = inparameter[j].parentId;
                                            }
                                            var in_zparamnode = {
                                                id: inparameter[j].id,
                                                pId: pId,
                                                name: inparameter[j].ecode,
                                                reqtype: inparameter[j].reqtype
                                            };
                                            inparam_zNodes.push(in_zparamnode);
                                        }

                                    } else {
                                        inparam_zNodes.pop();
                                    }
                        		}
                        } else {
                        		var outparameter;
                        		if(baseserNodeArray[i].nodeStyle == "5" || baseserNodeArray[i].nodeStyle == "6"){
                        			outparameter = (baseserNodeArray[i].allparameter).list;
                        		}else{
                        			outparameter = (baseserNodeArray[i].outparameter).list;
                        		}
                            if (outparameter != null && outparameter.length > 0) {
                                for (var j = 0; j < outparameter.length; j++) {
                                    var pId = baseserNodeArray[i].nodeId;
                                    if (outparameter[j].parentId != "" && outparameter[j].parentId != undefined) {
                                        pId = outparameter[j].parentId;
                                    }

                                    var out_zparamnode = {
                                        id: outparameter[j].id,
                                        pId: baseserNodeArray[i].nodeId,
                                        name: outparameter[j].ecode,
                                        reqtype: outparameter[j].reqtype
                                    };
                                    inparam_zNodes.push(out_zparamnode);
                                }
                            } else {
                                inparam_zNodes.pop();
                            }
                        }

                    }
                }
            }
            result = inparam_zNodes;
        }
    }
    return result;
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

  $("#menuContent").fadeOut("fast");
}

//树添加节点
function addHoverDom(treeId, treeNode) {
  var treeObj = $.fn.zTree.getZTreeObj(treeId);
  if (treeNode.reqtype == "Object" || treeNode.reqtype == "List" || treeNode.id == baseidflowChartId) {
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
          paramsetmodel(treeNode, treeObj);
          return false;
      });
  }
};

//删除之前做判断
function beforeRemove(treeId, treeNode) {
  var zTree = $.fn.zTree.getZTreeObj("treeDemo");
  zTree.selectNode(treeNode);
  if (confirm('确定要删除么')) { //只有当点击confirm框的确定时，该层才会关闭
      return true;
  }
  return false;
}

//树删除节点
function onRemove(e, treeId, treeNode) {
  var rootNode = getCurrentRoot(treeNode);

  for (var i = 0; i < (parent.serNodeArray).length; i++) {
      if ((parent.serNodeArray)[i].nodeId == rootNode.id) {
          var reqObjList = "";
          if ((parent.serNodeArray)[i].inparameter != "" && (parent.serNodeArray)[i].inparameter != "") {
              reqObjList = (parent.serNodeArray)[i].inparameter;

              for (var k = 0; k < (reqObjList.list).length; k++) {
                  if ((reqObjList.list)[k].id == treeNode.id) {
                      removeByValue(reqObjList.list, (reqObjList.list)[k]);
                  }
              }

              (parent.serNodeArray)[i].inparameter == reqObjList;
          }
          initTree();
      }
  }
}

function removeHoverDom(treeId, treeNode) {
  $("#addBtn_" + treeNode.tId).unbind().remove();
}
function showRemoveBtn(treeId, treeNode) {
  if (treeNode.pId == null) {
      return false;
  }
  var btn = $("#removeBtn_" + treeNode.tId);
  if (btn) btn.bind("click", function () {
      //添加参数弹框
      //paramsetmodel(flowChartId,treeNode,treeObj);
      return false;
  });
  return true;
}
function showRenameBtn(treeId, treeNode) {
  return false;
}

//设置树的父节点不能点击
function beforeClick(treeId, treeNode) {
  var check = (treeNode && !treeNode.isParent && treeNode.flag != "0");
  if (!check) layer.msg('   请选择参数！', {time: 1000, icon: 2});
  return check;
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


//-----------------------参数表格上添加映射关系-----------------------


//添加映射关系
function addBaserelArray() {
    //获取表格list 的长度
    var datalength = 0;
    if (getParams() != undefined && getParams() != "") {
        datalength = (getParams().list).length;
    }
    //遍历表格的每一行数据，并且与baserelArray做对比（如果没有则新增，如果有替换）
    for (var i = 0; i < datalength; i++) {
    		var initconstant = $("#regex_" + i).attr("initconstant");
    		if(typeof(initconstant) == "undefined"||initconstant == "undefined"){
    			initconstant == "";
    		}
    		
        var baserelaobj = new paramrelationObj(
        		joinId,
        		getuuid(),
            $("#real_" + i).attr("initidvalue"),
            $("#real_" + i).val(),
            $("#real_" + i).attr("inititemid"),
            $("#ecode_" + i).val(),
            $("#regex_" + i).val(),
            initconstant,
            basejoin.endSet);
        if(baserelaobj.preparamname != ""||initconstant != ""){
        		var a = 0;
            for (var p = 0; p < baserelArray.length; p++) {
                var relationobj = baserelArray[p];
                if (relationobj.joinId == joinId && relationobj.nextparamId == baserelaobj.nextparamId
                    && relationobj.nextparamname == baserelaobj.nextparamname) {
                    baserelArray[p] = baserelaobj;
                } else {
                    a++;
                }
            }
            if (a == baserelArray.length) {
                baserelArray.push(baserelaobj);
            }
        }
        
    }
    
    //替换baserelArray
    var pSerJoinArray = parent.serJoinArray;
    for (var k = 0; k < pSerJoinArray.length; k++) {
        if (pSerJoinArray[k].joinId == joinId) {
        		pSerJoinArray[k].relationSet = baserelArray;
        }
    }

// --------------添加参数条件----------------------
    var paramIdSet = [];
    var paramNameSet = [];
    var $component = $("#conditionset").find(".component");
    var j = $component.children().length;
    for (var int = 0; int < j; int++) {
        var paramName = $component.children().eq(int).children().eq(1).val();
        if(paramName != ""){
        	 	paramNameSet.push(paramName);
        }

        var paramId = $component.children().eq(int).children().eq(1).attr("initidvalue");
        if(paramId != ""){
        		paramIdSet.push(paramId);
        }
        
    }


    var conditionObj = new paramconditionObj(joinId, getuuid(), paramIdSet, paramNameSet, $("#condition").val(),
        basejoin.startSet, basejoin.endSet);
    for (var i = 0; i < (parent.serJoinArray).length; i++) {
        if ((parent.serJoinArray)[i].joinId == joinId) {
            (parent.serJoinArray)[i].conditionObj = conditionObj;
        }
    }
    
    //处理线上的文字
    var line = basetag.getLineById(joinId);
    if(line.data('text')!=null){
        line.data('text').remove();
    }
    if (conditionObj != null) {
        if (conditionObj.preparamIdSet.length > 0&&conditionObj.preparamnameSet.length > 0) {
            if (baserelArray.length > 0) {
                basetag.addLineText(line, '映射条件');
            } else {
                basetag.addLineText(line, '条件');
            }
        }else{
            if (baserelArray.length > 0) {
                basetag.addLineText(line, '映射');
            }
        }
    } else {
        if (baserelArray.length > 0) {
            basetag.addLineText(line, '映射');
        }
    }
    
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}


//获取之前所有矩形节点
function getAllPreNodes(node) {
    var prenode;
    var startSet = node.data("startSet");
    if (startSet != null && startSet.length > 0 && startSet != undefined) {
    		for(var i=0;i<startSet.length;i++){
    			prenode = basetag.getNodeById(startSet[i]);
    			allprenodes.push(prenode);
    	        getAllPreNodes(prenode);
    		}
    }
    return allprenodes;
}

//设置参数弹框
function paramsetmodel(treeNode, treeObj) {
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
            var rootNode = getCurrentRoot(treeNode);
            for (var i = 0; i < (parent.serNodeArray).length; i++) {
                if ((parent.serNodeArray)[i].nodeId == rootNode.id) {
                    var reqtreeObj = new reqObj(getuuid(), $("#ecode").val(), $("#reqdesc").val(), $("#reqtype").val(),
                        $("#parampos").val(), $("#isempty").val(), "0", "", treeNode.id);
                    var reqObjList = {
                        "list": []
                    };

                    if ((parent.serJoinArray)[i].inparameter != "" && (parent.serJoinArray)[i].inparameter != "") {
                        reqObjList = (parent.serJoinArray)[i].inparameter;
                    }

                    (reqObjList.list).push(reqtreeObj);
                    (parent.serJoinArray)[i].inparameter = reqObjList;
                }
            }

            initTree();
            layer.close(index);
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

//获取当前节点的根节点(treeNode为当前节点)  
function getCurrentRoot(treeNode) {
    if (treeNode.getParentNode() != null) {
        var parentNode = treeNode.getParentNode();
        return getCurrentRoot(parentNode);
    } else {
        return treeNode;
    }
}


//---------------------条件设置---------------------

//初始化条件
function initCondition() {
    //数据库回显
    $("#conditionset").find(".component").children().remove();

    if (baseserJoinArray != null) {
        for (var i = 0; i < (baseserJoinArray).length; i++) {
            if ((baseserJoinArray)[i].joinId == joinId) {
                var conditionObj = baseserJoinArray[i].conditionObj;
                if (conditionObj.preparamnameSet != null) {
                    for (var k = 0; k < (conditionObj.preparamnameSet).length; k++) {
                        $(".addvar").trigger("click");
                        var j = k + 1;
                        $("#conditionset").find("#parameter" + j).val((conditionObj.preparamnameSet)[k]);
                        $("#conditionset").find("#parameter" + j).attr("initidvalue", (conditionObj.preparamIdSet)[k]);
                    }
                } else {
                    $(".addvar").trigger("click");
                }
                if (conditionObj.condition != null) {
                    if (conditionObj.condition.indexOf("%*") > 0) {
                        conditionObj.condition.replace(/\%\*/g, "\"");
                    }
                }
                $("#condition").val(conditionObj.condition);
            }
        }

    }
}

//增加变量按钮
function addVar() {
    var j = $("#conditionset").find(".component").children().length + 1;
    var html = '<div class="component1"><label>变量：</label><input type="text" id="parameter' + j + '" initidvalue="" class="input_control" onclick="inputClick(this)" readonly="true"></div>';
    $("#conditionset").find(".component:last").append(html);
}

//删除变量按钮
function deleteVar() {
    var j = $("#conditionset").find(".component").children().length;
    $("#conditionset").find(".component").children().eq(j - 1).remove();
}


function isArray(obj) {
    if (Array.isArray) {
        return Array.isArray(obj);
    } else {
        return Object.prototype.toString.call(obj) === "[object Array]";
    }
}

