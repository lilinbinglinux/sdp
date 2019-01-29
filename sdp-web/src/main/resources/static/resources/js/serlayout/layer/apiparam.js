var manager;
var baseserNodeArray = parent.serNodeArray;
var baseserJoinArray = parent.serJoinArray;
var baseserNodeObj;
var casFlag = false;//判断是不是cas的开始节点

$(document).ready(function () {
    //定义全局使用的layer样式及弹窗高度
    var layerHeight = $(".panel-body").height() + 50;
    layer.config({skin: 'layui-layer-ext'});
    index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.style(index, {
        height: layerHeight
    });
    
    //如果是开始节点，则不显示URL和端口
    if (nodeType == "circle" && nodeName == "开始") {
        $("#urldiv").attr("style", "display:none");
        $(".apiserviceType").attr("style", "display:none");
        $(".serviceType").attr("style", "display:inline-block");
        $(".pubmethod").attr("style", "display:none");
        $("#pubdescdiv").attr("style", "display:block");

        $("#pubprotocal").find('option[value=socket]').attr("style", "display:none");
        initSerTypeTree();
    } else if(nodeType == "rectangle"){
        $(".apiserviceType").attr("style", "display:inline-block");
        $(".serviceType").attr("style", "display:none");
        $("#pubprotocal").find('option[value=socket]').attr("style", "display:inline-block");
        $("#pubdescdiv").attr("style", "display:none");
    }else if(nodeType == "circle" && (nodeStyle == "6"||nodeStyle == "5")){
    			$("#reqdiv").attr("style", "display:none");
    			$("#pubprotocaldiv").attr("style", "display:none");
    			$("#pubnametype").attr("style", "display:none");
    			$("#urldiv").attr("style", "display:none");
    			$("#pubreqformat").attr("style", "display:none");
    			$("#pubreqdatatype").attr("style", "display:none");
    			$("#inCharset").attr("style", "display:none");
    			$("#pubdescdiv").attr("style", "display:none");
    			
    			$("#pubreqformatdiv").find("label:first-child").attr("style", "display:none");
    			$("#pubreqdatatypediv").find("label:first-child").attr("style", "display:none");
    			$("#pubcharsetdiv").find("label:first-child").attr("style", "display:none");
    	
    }
    
    if (baseserNodeArray != null && baseserNodeArray.length > 0) {
        for (var i = 0; i < baseserNodeArray.length; i++) {
            if (baseserNodeArray[i].nodeId == nodeId) {
                baseserNodeObj = baseserNodeArray[i];
            }
        }
    }
    
    
    //基本信息回显初始化
    initBaseinfo();

    difDisabled();

    $(".apitab li").each(function () {
        $(this).click(function () {
            $(this).siblings().removeClass("active");
            $(this).addClass("active");
            var menu = $(this).attr("menu");
            $("#" + menu).show();
            $("#" + menu).siblings().hide();
        })
    });

    $("#reqdiv").click(function () {
        changeTag("0", true);
    });

    $("#respdiv").click(function () {
	    	if(nodeType == "circle" && (nodeStyle == "6"||nodeStyle == "5")){
	    		changeTag("3", true);
	    }else{
	    		changeTag("1", true);
	    }
    });

    $("#definediv").click(function () {
        changeTag("", false);
    });


    //请求和响应参数- 添加
    $("#addParamBtn").on("click", addParamBtn);

    $(".addparam").bind("click", addInput);
    $("#addParamSetBtn").bind("click", addParam);

    // 为datatable外的父级设置高度
    $('#urlexplainTable_wrapper').css('height', $('.panel-body').height() - 60);
    // 动态为表格添加父级
    $('#urlexplainTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').css('height', $('#urlexplainTable_wrapper').height() - 63);
    $('.tab-wrapper').niceScroll({cursorcolor: "#ccc", horizrailenabled: false});
    $("#cancelPubBtn").bind("click", cancelPub);

    $("input[name=typeId]").click(function () {
        var radioval = $('input[name=typeId]:checked').val();
        if (radioval == 4) {
            $("#typeId_api").attr("style", "display:none");
            $("#typeId_jar").attr("style", "display:block;margin-left: 17px;");
        } else if (radioval == 2) {
            $("#typeId_api").attr("style", "display:block");
            $("#typeId_jar").attr("style", "display:none");
        }
    });
    $(".namespace").hide();
    $(".reqtype").each(function () {
        var $dom = $(this).find('.form-control');
        var $this = $(this);
        $dom.change(function () {
            if ($dom.val() != "String") {
                $this.siblings(".maxlength").hide();
            } else {
                $this.siblings(".maxlength").show();
            }
        });
    });
    $(".parampos").each(function () {
        var $dom = $(this).find('.form-control');
        var $this = $(this);
        $dom.change(function () {
            if ($dom.val() != "7") {
                $this.siblings(".namespace").hide();
            } else {
                $this.siblings(".namespace").show();
            }
        });
    });


});

//初始化
function initBaseinfo() {
    var baseInfo = parent.baseInfo;
    if (baseserNodeArray != null && baseserNodeArray.length > 0) {
        for (var i = 0; i < baseserNodeArray.length; i++) {
            if (nodeId == baseserNodeArray[i].nodeId) {
                if (baseInfo != null && baseserNodeArray[i].nodeName == "开始") {
                    baseInfo.flowChartId = baseserNodeArray[i].flowChartId;
                    baseInfo.serName = baseserNodeArray[i].serName;
                    baseInfo.serdesc = baseserNodeArray[i].serdesc;
                    baseInfo.serviceType = baseserNodeArray[i].serviceType;
                    baseInfo.inparameter = baseserNodeArray[i].inparameter;
                    if(baseserNodeArray[i].outparameter != ""&&(baseserNodeArray[i].outparameter) != undefined
                    		&&baseserNodeArray[i].outparameter != null){
                    		baseInfo.outparameter = baseserNodeArray[i].outparameter;
                    }
                    
                    baseserNodeArray[i] = baseInfo;
                    casFlag = true;
                }
                var url = "";
                if (baseserNodeArray[i].nodeName == "开始") {
                    //服务类型
                    if (serTypesObjs != null && serTypesObjs.length > 0) {
                        //给个默认类型
                        $("#serviceTypeId").val(serTypesObjs[0].serTypeName);
                        $("#serviceTypeId").attr("idvalue", serTypesObjs[0].serTypeId);
                        for (var j = 0; j < serTypesObjs.length; j++) {
                            if (baseserNodeArray[i].serviceType == serTypesObjs[j].serTypeId) {
                                $("#serviceTypeId").val(serTypesObjs[j].serTypeName);
                                $("#serviceTypeId").attr("idvalue", serTypesObjs[j].serTypeId);
                            }
                        }
                    }
                } else {
                    $("#puburl").val(baseserNodeArray[i].url);  //url
                    //$("#apiserviceTypeId").val(baseserNodeArray[i].serviceType);//接口类型
                }

                $("#pubname").val(baseserNodeArray[i].serName);//接口名称:
                $("#pubprotocal").val(baseserNodeArray[i].agreement == '' ? 'http' : baseserNodeArray[i].agreement);//请求协议
                $("#pubmethod").val(baseserNodeArray[i].method == '' ? 'GET' : baseserNodeArray[i].method);//请求方式
                $("#pubport").val(baseserNodeArray[i].port);//端口
                $("#pubreqformat").val(baseserNodeArray[i].reqformat == '' ? 'application/json' : baseserNodeArray[i].reqformat);//请求格式
                $("#pubrespformat").val(baseserNodeArray[i].respformat == '' ? 'application/json' : baseserNodeArray[i].respformat);//响应格式
                $("#pubreqdatatype").val(baseserNodeArray[i].reqdatatype == '' ? 'Object' : baseserNodeArray[i].reqdatatype);//请求值类型
                $("#pubreturndatatype").val(baseserNodeArray[i].returndatatype == '' ? 'Object' : baseserNodeArray[i].returndatatype);//返回值类型
                $("#pubdesc").val(baseserNodeArray[i].serdesc);//描述
                $("#inCharset").val(baseserNodeArray[i].inCharset == '' ? 'UTF-8' : baseserNodeArray[i].inCharset);//请求参数字符集
                $("#outCharset").val(baseserNodeArray[i].outCharset == '' ? 'UTF-8' : baseserNodeArray[i].outCharset);//响应参数字符集
            }
        }

    }


}

//服务类型初始化树--------------------树形上的操作------------------------------------
function initSerTypeTree() {
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

    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes());
}
function getTreeNodes() {
    var zNodes = new Array();
    var treedatas = JSON.parse(serTypesTreedata);
    if (treedatas != null && treedatas.length > 0) {
        for (var i = 0; i < treedatas.length; i++) {
            var treedata = {
                id: treedatas[i].serTypeId,
                pId: treedatas[i].parentId,
                name: treedatas[i].serTypeName
            };
            zNodes.push(treedata);
        }
    }
    return zNodes;
}
function beforeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if (!check) alert("只能选择子节点...");
    return check;
}
function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        v = "";
    t = "";
    nodes.sort(function compare(a, b) {
        return a.id - b.id;
    });
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
        t += nodes[i].id + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (t.length > 0) t = t.substring(0, t.length - 1);
    var cityObj = $("#serviceTypeId");
    cityObj.val(v);
    cityObj.attr("idvalue", t);
    hideMenu();
}
function showMenu() {
    var cityObj = $("#serviceTypeId");
    var cityOffset = $("#serviceTypeId").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
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

//切换窗口
function changeTag(type, tablefalg) {
    $("#clickTag").val("1");
    if (($("#pubname").val() == "" || $("#pubname").val() == null) && nodeType != "circle") {
        $("#addParamBtn").attr("style", "display:none");
        layer.msg('请填写服务名称！', {time: 1000, icon: 2});
    // } else if (($("#serviceTypeId").val() == "" || $("#serviceTypeId").val() == null) && nodeType == "circle" && nodeName == "开始") {
    } else if (($("#serviceTypeIdLOL").val() == "" || $("#serviceTypeIdLOL").val() == null) && nodeType == "circle" && nodeName == "开始") {
        $("#addParamBtn").attr("style", "display:none");
        layer.msg('请选择服务类型！', {time: 1000, icon: 2});
    } else {
        $("#addParamBtn").attr("style", "display:block");
        baseserNodeObj.serName = $("#pubname").val();       //接口名称:
        baseserNodeObj.agreement = $("#pubprotocal").val();//请求协议
        baseserNodeObj.method = $("#pubmethod").val();      //请求方式
        baseserNodeObj.reqformat = $("#pubreqformat").val();//请求格式
        baseserNodeObj.respformat = $("#pubrespformat").val();//响应格式
        baseserNodeObj.reqdatatype = $("#pubreqdatatype").val();//请求值类型
        baseserNodeObj.returndatatype = $("#pubreturndatatype").val();//返回值类型
        baseserNodeObj.serdesc = $("#pubdesc").val();//描述
        baseserNodeObj.inCharset = $("#inCharset").val();//请求参数字符集
        baseserNodeObj.outCharset = $("#outCharset").val();//响应参数字符集
        $("#type").val(type);

        //开始按钮不需设置url
        if (nodeType != "circle" && nodeName != "开始") {
            baseserNodeObj.url = $("#puburl").val(); //url
            //baseserNodeObj.serviceType = $("#apiserviceTypeId").val();//接口类型
        } else {
            baseserNodeObj.serviceType = $("#serviceTypeId").attr("idvalue");
        }

        initParamTable();
        difDisabled(type);

    }

}

function cancelPub() {
    parent.layer.close(index);
}
function getSelected() {
    var row = manager.getSelectedRow();
    if (!row) {
        $("#parmParentId").val("");
    } else {
        $("#parmParentId").val(row.id);
    }
}

//参数表格
function initParamTable(value) {
    manager = $("#reqparamtable").ligerGrid({
        columns: [
            {display: '编码', name: 'ecode', id: 'ecodeName', width: "28%", align: 'left'},
            {display: '描述', name: 'reqdesc', width: "20%", align: 'center'},
            {display: '类型', name: 'reqtype', width: "15%", type: 'String', align: 'center'},
            {
                display: '是否为空', name: 'isempty', align: 'center', width: "12%",
                render: function (item) {
                    var s;
                    if (item.isempty == "1") {
                        s = "是";
                    } else if (item.isempty == "0") {
                        s = "否";
                    }
                    return s;
                }
            },
            {
                display: '操作', align: 'center', name: 'operation',
                render: function (item) {
                    var s =
                        "<a href='javascript:void(0)'  onclick='insertParams(\"" + item.id + "\")'>新增</a>" + "-"
                        + "<a href='javascript:void(0)' onclick='updateParams(\"" + item.id + "\")'>修改</a>" + "-"
                        + "<a href='javascript:void(0)' onclick='deleteParams(\"" + item.id + "\")'>删除</a>";
                    return s;
                }
            }
        ],

        width: '100%',
        height: '100%',
        alternatingRow: true,
        rownumbers: false,
        data: getParams(),
        usePager: false,
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId',
            isParent: function (data) {
                if (data.reqtype != "" && data.reqtype != null) {
                    if (data.parentId != null && data.isParent == true)  return true;
                }
                return false;
            }
        }
    });
}

//获取表格数据
function getParams() {
    var objdata = {
        list: ""
    };
    var typeflag = iscasType(parent.serFlowType);
    
    if(nodeType == "circle" && (nodeStyle == "6" || nodeStyle == "5")){
    		if(baseserNodeObj.allparameter != undefined && baseserNodeObj.allparameter != null){
    			objdata.list = (baseserNodeObj.allparameter).list;
    		}
    }else{
    	if (nodeName == '开始' && typeflag) {
            if (parent.baseInfo != null) {
                if (parent.baseInfo.outparameter != null) {
                    baseserNodeObj.outparameter = parent.baseInfo.outparameter;

                }
                if (baseserNodeObj.inparameter == null) {
                    baseserNodeObj.inparameter = parent.baseInfo.inparameter;
                }

            }

        }
        var jsonStr = form.serializeStr($("#pubidtoreq"));
        var jsonObj = jQuery.parseJSON(jsonStr);
        if (jsonObj.type == "0" && baseserNodeObj.inparameter != undefined && baseserNodeObj.inparameter != null) {
            objdata.list = (baseserNodeObj.inparameter).list;
        } else if (jsonObj.type == "1" && baseserNodeObj.outparameter != undefined && baseserNodeObj.outparameter != null) {
            objdata.list = (baseserNodeObj.outparameter).list;
        }
    }

    if (objdata.list != null&&objdata.list.length>0) {
        var list = objdata.list;
        for (var i = 0; i < list.length; i++) {
            var list1 = list[i];
            for (var j = 0; j < list.length; j++) {
                var list2 = list[j];
                if(list1.id == list2.parentId ){
                    objdata.list[i].isParent = true;
                }

            }
        }
    }
    
    return objdata;
}

//页面添加按钮
function addParamBtn() {
    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    //参数位置设置默认
    var $paroption = $('.parampos').find('select');
    $paroption.val('');
    if (jsonObj.type == "0") {
        $paroption.find('option').eq(0).show();
        $paroption.find('option').eq(1).show();
        $paroption.find('option').eq(2).show();
        $paroption.find('option').eq(3).hide();
        $paroption.find('option').eq(4).hide();
        $paroption.find('option').eq(5).show();
        $paroption.find('option').eq(6).hide();
        $paroption.find('option').eq(7).show();
        $paroption.val('0');
    } else if (jsonObj.type == "1") {
        $paroption.find('option').eq(0).hide();
        $paroption.find('option').eq(1).hide();
        $paroption.find('option').eq(2).hide();
        $paroption.find('option').eq(3).show();
        $paroption.find('option').eq(4).show();
        $paroption.find('option').eq(5).hide();
        $paroption.find('option').eq(6).show();
        $paroption.find('option').eq(7).show();
        $paroption.val('3');
    }

    $('.reqtype').find('select').val('String');//参数类型设置默认
    $("#parmParentId").val("");
    $("#addParamSetBtn").text("保存");
    $("#paramadd-update").val("");
    //弹窗只允许弹出一条记录。如果有多个form-inline 则移除掉。
    $('#allparamset').find('.form-inline').each(function (index) {
        if (index != 0) {
            $(this).remove();
        } else {
            if ($(this).find('.addparam').css('display') == 'none') {
                $('.addparam').css('display', 'block');
            }
        }

    });
    $('#paramadd').modal();


    //设置append的div还原
    $("#parammodal-body").attr("style", "overflow-y:visible;");
    if ($("#paramset").find(".deleteparam").length > 0) {
        $("#paramset").find(".jsonparam").each(function (i, e) {
            $(this).remove();
        });
    }
    $(".param-input").val("");

}

//表格上的“新增”按钮
function insertParams(id) {
    $("#parammodal-body").attr("style", "overflow-y:visible;");
    if ($("#paramset").find(".deleteparam").length > 0) {
        $("#paramset").find(".jsonparam").each(function (i, e) {
            $(this).remove();
        });
    }

    $("#parmParentId").val(id);
    $("#addParamSetBtn").text("保存");
    $("#paramadd-update").val("");
    $('#paramadd').modal();
    $(".addparam").show();
    var inputs = $(".param-input");
    inputs.val("");
}

//参数添加和修改
function addParam() {
    var objarr = new Array();
    var type = $("#type").val();
    var ecode = $(".ecode .form-control").val();
    if (ecode == null || ecode == '') {
        layer.msg('参数不能为空', {icon: 1, time: 2000});
        return;
    }
    var reqdesc = $(".reqdesc .form-control").val();
    var reqtype = $(".reqtype .form-control").val();
    var parampos = $(".parampos .form-control").val();
    var isempty = $(".isempty .form-control").val();
    var maxlength = $(".maxlength .form-control").val();
    var namespace = $(".namespace .form-control").val();

    //修改
    if ($("#paramadd-update").val() == "1") {
        var id = $("#paramid-set").val();
        var jsonStr = form.serializeStr($("#pubidtoreq"));
        var jsonObj = jQuery.parseJSON(jsonStr);
        var baseparam;
        var baseparamlist;
        if (jsonObj.type == "0") {
            baseparamlist = baseserNodeObj.inparameter;
        } else if (jsonObj.type == "1") {
            baseparamlist = baseserNodeObj.outparameter;
        }else if(jsonObj.type == "3"){
        	 	baseparamlist = baseserNodeObj.allparameter;
        }
        
        for (var i = 0; i < (baseparamlist.list).length; i++) {
            if (id == (baseparamlist.list)[i].id) {
                baseparam = (baseparamlist.list)[i];
                //removeByValue(baseparamlist.list, (baseparamlist.list)[i]);
                baseparam.ecode = ecode;
                baseparam.reqdesc = reqdesc;
                baseparam.reqtype = reqtype;
                baseparam.parampos = parampos;
                baseparam.isempty = isempty;
                baseparam.type = type;
                baseparam.maxlength = maxlength;
                baseparam.namespace = namespace;
                //(baseparamlist.list).push(baseparam);
                (baseparamlist.list).splice(i,1,baseparam);
                updateParamsRelation(baseparam);
            }
        }
       
        if (jsonObj.type == "0") {
            baseserNodeObj.inparameter = baseparamlist;

            //如果有结束节点，则结束节点也需进行修改
            if (baseserNodeObj.nodeName == "开始") {
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "结束") {
                        baseserNodeArray[i].inparameter = baseparamlist;
                    }
                }
            }
        } else if (jsonObj.type == "1") {
            baseserNodeObj.outparameter = baseparamlist;

            //如果有结束节点，则结束节点也需进行修改
            if (baseserNodeObj.nodeName == "开始") {
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "结束") {
                        baseserNodeArray[i].outparameter = baseparamlist;
                    }
                }
            }
        }else if(jsonObj.type == "3"){
        		baseserNodeObj.allparameter = baseparamlist;
        }
        initParamTable();
    }
    //添加
    else {
        var divinput = $('#allparamset').find('.form-inline')
        var parentId = $("#parmParentId").val();
        for (var i = 0; i < divinput.length; i++) {
            var arr = new Array();
            $(divinput[i]).find(".form-control").each(function (i, e) {
                arr.push($(this).val());
            });
            var reqobj = new reqObj(getuuid(), arr[0], arr[1], arr[2], arr[3], arr[5], type, arr[4], parentId, namespace,"");
            if (reqobj.parentId == "" && reqobj.parampos == "6") {
                layer.msg('请在xml属性参数设置父参数节点！', {time: 2000, icon: 5});
                return;
            }
            var reqobjstr = JSON.stringify(reqobj);
            objarr.push(JSON.parse(reqobjstr));
        }
        var listobjarr = {list: objarr};
        var jsonStr = form.serializeStr($("#pubidtoreq"));
        var jsonObj = jQuery.parseJSON(jsonStr);
        console.log(jsonObj);

        //请求参数设置
        if (jsonObj.type == "0") {
            if (baseserNodeObj.inparameter == null || typeof(baseserNodeObj.inparameter) == undefined || baseserNodeObj.inparameter == "") {
                baseserNodeObj.inparameter = listobjarr;
            } else {
                var baseparamlist = (baseserNodeObj.inparameter).list;
                for (var i = 0; i < objarr.length; i++) {
                    baseparamlist.push(objarr[i]);
                }
                (baseserNodeObj.inparameter).list = baseparamlist;
            }

            //如果有结束节点，则结束节点也需进行修改
            if (baseserNodeObj.nodeName == "开始") {
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "结束") {
                        (baseserNodeArray[i].inparameter).list = baseparamlist;
                    }
                }
            }

        }
        //响应参数设置
        else if (jsonObj.type == "1") {
            if (baseserNodeObj.outparameter == null || typeof(baseserNodeObj.outparameter) == undefined || baseserNodeObj.outparameter == "") {
                baseserNodeObj.outparameter = listobjarr;
            } else {
                var baseparamlist = (baseserNodeObj.outparameter).list;
                for (var i = 0; i < objarr.length; i++) {
                    baseparamlist.push(objarr[i]);
                }
                (baseserNodeObj.outparameter).list = baseparamlist;
            }

            //如果有结束节点，则结束节点也需进行修改
            if (baseserNodeObj.nodeName == "开始") {
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "结束") {
                        (baseserNodeArray[i].outparameter).list = baseparamlist;
                    }
                }
            }
        }else if(jsonObj.type == "3"){
        	 if (baseserNodeObj.allparameter == null || typeof(baseserNodeObj.allparameter) == undefined || baseserNodeObj.allparameter == "") {
                 baseserNodeObj.allparameter = listobjarr;
             } else {
                 var baseparamlist = (baseserNodeObj.allparameter).list;
                 for (var i = 0; i < objarr.length; i++) {
                     baseparamlist.push(objarr[i]);
                 }
                 (baseserNodeObj.allparameter).list = baseparamlist;
             }
        }
        initParamTable();
    }
}

//修改和参数有关的线上的映射
function updateParamsRelation(baseparam){
	if(baseserJoinArray != null && baseserJoinArray != undefined){
		for(var i=0;i<baseserJoinArray.length;i++){
			baseserJoin = baseserJoinArray[i];
			baseserrelationSet = baseserJoinArray[i].relationSet;
			if(baseserrelationSet != null && baseserrelationSet != undefined){
				for(var j=0;j<baseserrelationSet.length;j++){
					if(baseserrelationSet[j].nextparamId == baseparam.id){
						baseserrelationSet[j].nextparamname = baseparam.ecode;
					}
					else if(baseserrelationSet[j].preparamId == baseparam.id){
						baseserrelationSet[j].preparamname = baseparam.ecode;
					}
				}
			}
			baseserJoinArray[i].relationSet = baseserrelationSet;
		}
	}
	parent.serJoinArray = baseserJoinArray;
}

//删除参数
function deleteParams(id) {
    layer.confirm('删除该参数？（删除后不可恢复）', {
        icon: 3,
        btn: ['是', '否'],//按钮
        offset: '150px'
    }, function (index, layero) {
        var jsonStr = form.serializeStr($("#pubidtoreq"));
        var jsonObj = jQuery.parseJSON(jsonStr);
        if (jsonObj.type == "0") {
            if (baseserNodeObj.inparameter != null && baseserNodeObj.inparameter != undefined) {
                var baseparamlist = baseserNodeObj.inparameter;
                for (var i = 0; i < (baseparamlist.list).length; i++) {
                    if (id == (baseparamlist.list)[i].id) {
                        removeByValue((baseparamlist.list), (baseparamlist.list)[i]);
                    }
                }
                baseserNodeObj.inparameter = baseparamlist;
            }

            //如果有结束节点，则结束节点也需进行修改
            if (baseserNodeObj.nodeName == "开始") {
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "结束") {
                        baseserNodeArray[i].inparameter = baseparamlist;
                    }
                }
            }

            initParamTable();
        } else if (jsonObj.type == "1") {
            if (baseserNodeObj.outparameter != null && baseserNodeObj.outparameter != undefined) {
                var baseparamlist = baseserNodeObj.outparameter;
                for (var i = 0; i < (baseparamlist.list).length; i++) {
                    if (id == (baseparamlist.list)[i].id) {
                        removeByValue((baseparamlist.list), (baseparamlist.list)[i]);
                    }
                }
                baseserNodeObj.outparameter = baseparamlist;
            }

            //如果有结束节点，则结束节点也需进行修改
            if (baseserNodeObj.nodeName == "开始") {
                for (var i = 0; i < baseserNodeArray.length; i++) {
                    if (baseserNodeArray[i].nodeName == "结束") {
                        baseserNodeArray[i].outparameter = baseparamlist;
                    }
                }
            }

            initParamTable();
        }else if(jsonObj.type == "3"){
        	if (baseserNodeObj.allparameter != null && baseserNodeObj.allparameter != undefined) {
                var baseparamlist = baseserNodeObj.allparameter;
                for (var i = 0; i < (baseparamlist.list).length; i++) {
                    if (id == (baseparamlist.list)[i].id) {
                        removeByValue((baseparamlist.list), (baseparamlist.list)[i]);
                    }
                }
                baseserNodeObj.allparameter = baseparamlist;
            }

            initParamTable();
        }
        deleteParamsRelation(id);
        layer.close(index);
    })
}

//删除和参数有关的线上的映射
function deleteParamsRelation(id){
	if(baseserJoinArray != null && baseserJoinArray != undefined){
		for(var i=0;i<baseserJoinArray.length;i++){
			baseserJoin = baseserJoinArray[i];
			baseserrelationSet = baseserJoinArray[i].relationSet;
			if(baseserrelationSet != null && baseserrelationSet != undefined){
				for(var j=0;j<baseserrelationSet.length;j++){
					if(baseserrelationSet[j].nextparamId == id || baseserrelationSet[j].preparamId == id){
						baseserrelationSet.splice(j,1);
						baseserJoinArray[i].relationSet = baseserrelationSet;
						break;
					}
				}
			}
		}
	}
	
	parent.serJoinArray = baseserJoinArray;
}

//参数回显
function updateParams(id) {
    $('#allparamset').find('.form-inline').each(function (index) {
        if (index != 0) {
            $(this).remove();
        }
    });
    $("#addParamSetBtn").text("修改");
    $("#paramadd").modal();
    $(".addparam").hide();
    $("#paramadd-update").val("1");

    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    var baseparam;
    var baseparamlist;
    if (jsonObj.type == "0") {
        baseparamlist = baseserNodeObj.inparameter;

    } else if (jsonObj.type == "1") {
        baseparamlist = baseserNodeObj.outparameter;
    }else if(jsonObj.type == "3"){
    	 	baseparamlist = baseserNodeObj.allparameter;
    }

    for (var i = 0; i < (baseparamlist.list).length; i++) {
        if (id == (baseparamlist.list)[i].id) {
            baseparam = (baseparamlist.list)[i];
        }
    }

    $("#paramid-set").val(baseparam.id);
    $(".ecode .form-control").val(baseparam.ecode);
    $(".reqdesc .form-control").val(baseparam.reqdesc);
    $(".reqtype .form-control").val(baseparam.reqtype);
    $(".parampos .form-control").val(baseparam.parampos);
    $(".maxlength .form-control").val(baseparam.maxlength);
    $(".namespace .form-control").val(baseparam.namespace);
    if ($(".reqtype .form-control").val() == 'String') {
        $(".maxlength").show();
    } else {
        $(".maxlength").hide();
    }
    if ($(".parampos .form-control").val() == '7') {
        $(".namespace").show();
    } else {
        $(".namespace").hide();
    }
    if (baseparam.isempty == "1") {
        $("#isempty .form-control").val("1");
    } else if (baseparam.isempty == "0") {
        $(".isempty .form-control").val("0");
    }
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage) {
    $("#reqparamtable").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}

function addInput() {
    var input = '<div style="margin-top: 10px;" class="form-inline">' + $("#allparamset").find('.form-inline:first').html() +
        '</div>';
    $("#allparamset").append(input);

    $('#allparamset').find('.form-inline').each(function (index) {
        if ($(this).find(".reqtype .form-control").val() != 'String') {
            $(this).find('.maxlength').hide();
        }
        if ($(this).find(".parampos .form-control").val() != '7') {
            $(this).find('.namespace').hide();
        }
        if (index != 0) {
            $(this).find('.btn-default').removeClass('addparam').addClass('deleteparam');
            $(this).find('.btn-default').children('span').addClass('glyphicon-minus').removeClass('glyphicon-plus');
        }

    });

    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    //设置默认值
    var $paroption = $('.parampos').find('select');
  
    //$paroption.val('');
    if (jsonObj.type == "1") {
    	 $(".parampos").each(function () {
    		 if($(this).find('select').val() == "0"){
    			 $(this).find('select').val("3");
    		 }
    	 })
    		
    }
    $(".reqtype").each(function () {
        var $dom = $(this).find('.form-control');
        var $this = $(this);
        $dom.change(function () {
            if ($dom.val() != "String") {
                $this.siblings(".maxlength").hide();
            } else {
                $this.siblings(".maxlength").show();
            }
        });
    });
    $(".parampos").each(function () {
        var $dom = $(this).find('.form-control');
        var $this = $(this);
        $dom.change(function () {
            if ($dom.val() != "7") {
                $this.siblings(".namespace").hide();
            } else {
                $this.siblings(".namespace").show();
            }
        });
    });
    $("#allparamset").find(".deleteparam").each(function (i, e) {
        $(this).unbind("click");
        $(this).bind("click", function () {
            $(this).parent().remove();
            if ($("#allparamset").height() < $("#parammodal-body").height()) {
                $("#parammodal-body").attr("style", "overflow-y:visible;");
            }
        })
    });

    if ($("#parammodal-body").height() > 400) {
        $("#parammodal-body").attr("style", "overflow-y:scroll;height:40%");
    }
}

function difDisabled(type) {
    if (type != null && type == '1') {
        var typeflag = iscasType(parent.serFlowType);
        if (nodeName == '开始' && typeflag) {
            if (manager != null) {
                manager.toggleCol('operation', false);
            }
            $("#addParamBtn").attr("style", "display:none");
            manager.setColumnWidth('isempty', '200px');

        } else {
            $("#addParamBtn").attr("style", "display:block");
            if (manager != null) {
                manager.toggleCol('operation', true);
                manager.setColumnWidth('isempty', '63%');
            }
        }
    } else {
        $("#addParamBtn").attr("style", "display:block");
        if (manager != null) {
            manager.toggleCol('operation', true);
            manager.setColumnWidth('isempty', '63%');
        }
    }
    var typeflag = iscasType(parent.serFlowType);
    if (nodeName == '开始' && typeflag) {
        //如果baseInfo 不为空说明基本信息不允许自己配置
        $("#pubprotocal").attr('disabled', 'true');//请求协议
        $("#pubmethod").attr('disabled', 'true');//请求方式
        $("#pubreqformat").attr('disabled', 'true');//请求格式
        $("#pubrespformat").attr('disabled', 'true');//响应格式
        $("#pubreqdatatype").attr('disabled', 'true');//请求值类型
        $("#pubreturndatatype").attr('disabled', 'true');//返回值类型
        $("#inCharset").attr('disabled', 'true');//请求参数字符集
        $("#outCharset").attr('disabled', 'true');//响应参数字符集
    } else {
        $("#pubprotocal").attr('disabled', false);//请求协议
        $("#pubmethod").attr('disabled', false);//请求方式
        $("#pubreqformat").attr('disabled', false);//请求格式
        $("#pubrespformat").attr('disabled', false);//响应格式
        $("#pubreqdatatype").attr('disabled', false);//请求值类型
        $("#pubreturndatatype").attr('disabled', false);//返回值类型
        $("#inCharset").attr('disabled', false);//请求参数字符集
        $("#outCharset").attr('disabled', false);//响应参数字符集
    }


}
