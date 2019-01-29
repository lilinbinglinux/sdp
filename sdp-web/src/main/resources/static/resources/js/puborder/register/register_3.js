/**
 * Created by niu on 2018/1/17.
 */
var idflowChartId;

//存储所有节点对象
var serNodeArray = new Array();
//存储所有线对象
var serJoinArray = new Array();

//更新和添加标识
var updateflag = "";
var manager;
var serBaseNodeObj = {};

$(document).ready(function () {
    //定义全局使用的layer样式
    layer.config({skin: 'layui-layer-ext'});
    index = layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.style(index, {
        height: ($(".panel-body").height() ) + 'px',
    });

    $("#id-back-button").on("click", function () {
        window.location.href = webpath + "/mineServiceRegisterController/mineRegister";
    });

    //查询所有类型
    selectSerType();

    if (flowChartId == null || flowChartId == ""){
        idflowChartId = getuuid();
        updateflag = "";
        //先添加服务
        initStartLayout();
        initBaseinfoNull();
    }else{
        idflowChartId = flowChartId;
        updateflag = "update";
        serNodeArray = JSON.parse(initserNodeArray);
        //如果更新，则回显
        initBaseinfo();
    }

    $(".apitab li").each(function () {
       $(this).click(function () {
           $(this).siblings().removeClass("active");
           $(this).addClass("active");
           var menu = $(this).attr("menu");
           $("#"+menu).siblings().hide();
           $("#"+menu).show();
       })
    });

    $("#reqdiv").click(function () {
        changeTag("0");
    });

    $("#respdiv").click(function () {
        changeTag("1");
    });

    //请求和响应参数- 添加
    $("#addParamBtn").on("click", function () {
        $("#parmParentId").val("");
        $("#addParamSetBtn").text("保存");
        $("#paramadd-update").val("");
        $('#paramadd').modal();

        //设置append的div还原
        $("#parammodal-body").attr("style", "overflow-y:visible;");
        if ($("#paramset").find(".deleteparam").length > 0) {
            $("#paramset").find(".jsonparam").each(function (i, e) {
                $(this).remove();
            });
        }
        $(".param-input").val("");
    });

    $("#addparam").bind("click", addInput);
    $("#addParamSetBtn").bind("click", addParam);


    // 为datatable外的父级设置高度
    $('#urlexplainTable_wrapper').css('height', $('.panel-body').height() - 60);
    // 动态为表格添加父级
    $('#urlexplainTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').css('height', $('#urlexplainTable_wrapper').height() - 63);
    $('.tab-wrapper').niceScroll({cursorcolor: "#ccc", horizrailenabled: false});

    $("#reqtype").change(function () {
        if ($(this).val() != "String") {
            $(".maxlength").hide();
        } else {
            $(".maxlength").show();
        }
    });

    $("#id-saveServiceModel-button").bind("click", saveServiceModel);

    $("#id-next-button").bind("click", function(){
        checkNameValue('0');
    });
    $("#id-Model-button").bind("click", function(){
        checkNameValue('1');
    });

});

//查询服务列表
function selectSerType() {
    $.ajax({
        "url": webpath + "/serviceRegisterController/selectSerType",
        "type": "POST",
        async: false,
        success: function (data) {
            var strArray = new Array();
            for (var i = 0; i < data.length; i++){
                var list = data[i].idPath.split("/");
                if(list[1] == "root"){
                    strArray.push(data[i]);
                }
            }
            initTree(strArray);
        }
    });
}

//切换窗口
function changeTag(type) {
    if($("#pubname").val() == "" || $("#pubname").val() == null){
        layer.msg("请填写服务名称", {time: 1000, icon: 2});
    }else if ($("#serviceTypeId").val() == "" || $("#serviceTypeId").val() == null){
        layer.msg("请选择服务类型", {time: 1000, icon: 2});
    }else {
        serBaseNodeObj.serName = $("#pubname").val();
        serBaseNodeObj.agreement = $("#pubprotocal").val();
        serBaseNodeObj.method = $("#pubmethod").val();
        serBaseNodeObj.reqformat = $("#pubreqformat").val();
        serBaseNodeObj.respformat = $("#pubrespformat").val();
        serBaseNodeObj.reqdatatype = $("#pubreqdatatype").val();
        serBaseNodeObj.returndatatype = $("#pubreturndatatype").val();
        serBaseNodeObj.serdesc = $("#pubdesc").val();
        serBaseNodeObj.inCharset = $("#inCharset").val();
        serBaseNodeObj.outCharset = $("#outCharset").val();
        serBaseNodeObj.serviceType = $("#serviceTypeId").attr("idvalue");
        $("#type").val(type);

        initParamTable();
    }
}

//流程图初始化开始和结束节点
function initStartLayout() {
    var nodeType = "";
    var startoptions = {
        "x": 100,
        "y": 100,
        "nodeType": "circle",
        "nodeName": "开始",
        'nodeId': getuuid()
    };
    var startSerNode = new startSerNodeObj(idflowChartId, serVerId, startoptions.nodeId, startoptions.nodeName, startoptions.nodeType, "1",
        startoptions.x, startoptions.y, 50, 50, 25, "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "");
    serNodeArray.push(startSerNode);

    var endoptions = {
        "x": 500,
        "y": 100,
        "nodeType": "circle",
        "nodeName": "结束",
        'nodeId': getuuid()
    };

    //设置出参成功的唯一标识
    var result = new reqObj(getuuid(), "success", "选取某个参数作为成功标志", "String", "0", "", "0", "", "", "", "");
    var resultList = new Array();
    resultList[0] = result;
    var objdata = {
        list: ""
    };
    objdata.list = resultList;

    var endSerNode = new startSerNodeObj(idflowChartId, serVerId, endoptions.nodeId, endoptions.nodeName, endoptions.nodeType, "4",
        endoptions.x, endoptions.y, 50, 50, 25, "", "", "", "",
        "", "", "", "", "", "", "", "", "", objdata, "", "");
    serNodeArray.push(endSerNode);
}

//初始化
function initBaseinfo() {
    console.log(serNodeArray);
    for (var i = 0; i < serNodeArray.length; i++) {
        if(serNodeArray[i].nodeName == "开始"){
            serBaseNodeObj = serNodeArray[i];
        }
    }
    if (serNodeArray != null && serNodeArray.length > 0) {
        for (var i = 0; i < serNodeArray.length; i++) {
            if ("开始" == serNodeArray[i].nodeName) {
                $("#pubname").val(serNodeArray[i].serName);
                $("#pubprotocal").val(serNodeArray[i].agreement);
                $("#pubmethod").val(serNodeArray[i].method);
                $("#pubreqformat").val(serNodeArray[i].reqformat);
                $("#pubrespformat").val(serNodeArray[i].respformat);
                $("#pubreqdatatype").val(serNodeArray[i].reqdatatype);
                $("#pubreturndatatype").val(serNodeArray[i].returndatatype);
                $("#pubdescdiv").val(serNodeArray[i].serdesc);
                $("#inCharset").val(serNodeArray[i].inCharset);
                $("#outCharset").val(serNodeArray[i].outCharset);
                $("#serviceTypeId").attr("idvalue",serviceType);
                $("#pubdesc").val(serResume);
                $.ajax({
                    "url": webpath + "/ServiceType/getAllByCondition",
                    "type": "POST",
                    data:{
                        "serTypeId": serviceType,
                    },
                    success:function (data) {
                        $("#serviceTypeId").val(data[0].serTypeName);
                    }
                });
            }
        }
    }
}

//初始化
function initBaseinfoNull() {
    $("#pubname").val("");
    $("#pubprotocal").val("");
    $("#pubmethod").val("");
    $("#pubreqformat").val("");
    $("#pubrespformat").val("");
    $("#pubreqdatatype").val("");
    $("#pubreturndatatype").val("");
    $("#pubdescdiv").val("");
    $("#serviceTypeId").val("");
    $("#inCharset").val("");
    $("#outCharset").val("");
}


//参数表格
function initParamTable(value) {
    manager = $("#reqparamtable").ligerGrid({
        columns: [
            {display: '编码', name: 'ecode', id: 'ecodeName', width: "20%", align: 'left', align: 'center'},
            {display: '描述', name: 'reqdesc', width: "20%", align: 'center'},
            {display: '类型', name: 'reqtype', width: "20%", type: 'String', align: 'center'},
            {
                display: '是否为空', name: 'isempty', align: 'center', width: "20%",
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
                display: '操作', width: "20%", align: 'center',
                render: function (item) {
                    var s =
                        "<a href='javascript:void(0)' onclick='updateParams(\"" + item.id + "\")'>修改</a>" + "-"
                        + "<a href='javascript:void(0)' onclick='deleteParams(\"" + item.id + "\")'>删除</a>";
                    if (item.reqtype != "" && item.reqtype != null &&
                        (item.reqtype.toLowerCase() == "Object".toLowerCase() || item.reqtype.toLowerCase() == "List".toLowerCase() ||
                        item.reqtype.toLowerCase() == "List_Object".toLowerCase() || item.reqtype.toLowerCase() == "List_List".toLowerCase())) {
                        s =
                            "<a href='javascript:void(0)'  onclick='insertParams(\"" + item.id + "\")'>新增</a>" + "-"
                            + s;
                    }
                    return s;
                }
            },
        ], width: '99%', pageSizeOptions: [5, 10, 15, 20], height: '80%',
        alternatingRow: false,
        enabledEdit: false,
        //rownumbers: true,
        data: getParams(),
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId',
            isParent: function (data) {
                if (data.reqtype != "" && data.reqtype != null) {
                    //设置表格之前箭头
                    if (data.reqtype.toLowerCase() == "Object".toLowerCase() || data.reqtype.toLowerCase() == "List".toLowerCase() ||
                        data.reqtype.toLowerCase() == "List_Object".toLowerCase() || data.reqtype.toLowerCase() == "List_List".toLowerCase())
                        return true;
                }
                return false;
            }
        },
        onSelectRow: function (rowdata, rowindex) {
        },
        usePager: false
    });
}

//获取表格数据
function getParams() {
    var objdata = {
        list: ""
    };
    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    for (var i = 0; i < serNodeArray.length; i++) {
        if(serNodeArray[i].nodeName == "开始"){
            serBaseNodeObj = serNodeArray[i];
        }
    }
    if (jsonObj.type == "0" && serBaseNodeObj.inparameter != undefined && serBaseNodeObj.inparameter != "") {
        objdata = serBaseNodeObj.inparameter;
    }

    return objdata;
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
    $("#addparam").show();
    var inputs = $(".param-input");
    inputs.val("");
}

//参数添加和修改
function addParam() {
    var divinput = $(".jsonparam");
    var objarr = new Array();
    var ecode = $("#ecode").val();
    var reqdesc = $("#reqdesc").val();
    var reqtype = $("#reqtype").val();
    var parampos = $("#parampos").val();
    var isempty = $("#isempty").val();
    var type = $("#type").val();
    var maxlength = $("#maxlength").val();

    for (var i = 0; i < serNodeArray.length; i++) {
        if(serNodeArray[i].nodeName == "开始"){
            serBaseNodeObj = serNodeArray[i];
        }
    }

    //修改
    if ($("#paramadd-update").val() == "1") {
        var id = $("#paramid-set").val();

        var jsonStr = form.serializeStr($("#pubidtoreq"));
        var jsonObj = jQuery.parseJSON(jsonStr);
        var baseparam;
        var baseparamlist;
        if (jsonObj.type == "0") {
            baseparamlist = serBaseNodeObj.inparameter;
        } else if (jsonObj.type == "1") {
            baseparamlist = serBaseNodeObj.outparameter;
        }

        for (var i = 0; i < (baseparamlist.list).length; i++) {
            if (id == (baseparamlist.list)[i].id) {
                baseparam = (baseparamlist.list)[i];
                removeByValue(baseparamlist.list, (baseparamlist.list)[i]);
            }
        }

        baseparam.ecode = ecode;
        baseparam.reqdesc = reqdesc;
        baseparam.reqtype = reqtype;
        baseparam.parampos = parampos;
        baseparam.isempty = isempty;
        baseparam.type = type;
        baseparam.maxlength = maxlength;
        (baseparamlist.list).push(baseparam);
        if (jsonObj.type == "0") {
            serBaseNodeObj.inparameter = baseparamlist;
            for(var i=0;i<serNodeArray.length;i++){
                if(serNodeArray[i].nodeName == "开始"){
                    (serBaseNodeObj.inparameter).list = orderSet((serBaseNodeObj.inparameter).list);
                    serNodeArray[i].inparameter = serBaseNodeObj.inparameter;
                }
            }
        } else if (jsonObj.type == "1") {
            serBaseNodeObj.outparameter = baseparamlist;
            for(var i=0;i<serNodeArray.length;i++){
                if(serNodeArray[i].nodeName == "开始"){
                    (serBaseNodeObj.inparameter).list = orderSet((serBaseNodeObj.inparameter).list);
                    serNodeArray[i].outparameter = serBaseNodeObj.outparameter;
                }
            }
        }

        console.log(serNodeArray);
        initParamTable();
    }
    //添加
    else {
        var parentId = $("#parmParentId").val();
        var reqobj1 = new reqObj(getuuid(), ecode, reqdesc, reqtype, parampos, isempty, type, maxlength, parentId, "");
        var reqobj1str = JSON.stringify(reqobj1);
        objarr.push(JSON.parse(reqobj1str));
        for (var i = 0; i < divinput.length; i++) {
            var arr = new Array();
            $(divinput[i]).find(".form-input").each(function (i, e) {
                arr.push($(this).val());
            })
            var reqobj = new reqObj(getuuid(), arr[0], arr[1], arr[2], arr[3], arr[5], type, arr[4], parentId, "");
            console.log(arr);
            console.log(reqobj);
            var reqobjstr = JSON.stringify(reqobj);
            objarr.push(JSON.parse(reqobjstr));
        }

        var listobjarr = {
            list: objarr
        };

        var jsonStr = form.serializeStr($("#pubidtoreq"));
        var jsonObj = jQuery.parseJSON(jsonStr);
        //请求参数设置
        if (jsonObj.type == "0") {
            if (serBaseNodeObj.inparameter == null || typeof(serBaseNodeObj.inparameter) == undefined || serBaseNodeObj.inparameter == "") {
                serBaseNodeObj.inparameter = listobjarr;
            } else {
                var baseparamlist = (serBaseNodeObj.inparameter).list;
                for (var i = 0; i < objarr.length; i++) {
                    baseparamlist.push(objarr[i]);
                }
                (serBaseNodeObj.inparameter).list = baseparamlist;
            }
        }

        (serBaseNodeObj.inparameter).list = orderSet((serBaseNodeObj.inparameter).list);

        for(var i=0;i<serNodeArray.length;i++){
            if(serNodeArray[i].nodeName == "开始"){
                serNodeArray[i].inparameter = serBaseNodeObj.inparameter;
                serNodeArray[i].outparameter = serBaseNodeObj.outparameter;
            }
        }
        console.log(serNodeArray);
        initParamTable();
    }
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
            if (serBaseNodeObj.inparameter != null && serBaseNodeObj.inparameter != undefined) {
                var baseparamlist = serBaseNodeObj.inparameter;
                for (var i = 0; i < (baseparamlist.list).length; i++) {
                    if (id == (baseparamlist.list)[i].id) {
                        removeByValue((baseparamlist.list), (baseparamlist.list)[i]);
                    }
                }
                serBaseNodeObj.inparameter = baseparamlist;
                for (var i = 0; i < serNodeArray.length; i++) {
                    if(serNodeArray[i].nodeName == "开始"){
                        (serBaseNodeObj.inparameter).list = orderSet((serBaseNodeObj.inparameter).list);
                        serNodeArray[i].inparameter = serBaseNodeObj.inparameter;
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
                for (var i = 0; i < serNodeArray.length; i++) {
                    if(serNodeArray[i].nodeName == "开始"){
                        (serBaseNodeObj.inparameter).list = orderSet((serBaseNodeObj.inparameter).list);
                        serNodeArray[i].outparameter = serBaseNodeObj.outparameter;
                    }
                }
            }
            initParamTable();
        }
        console.log(serNodeArray);
        layer.close(index);
    })
}

//参数回显
function updateParams(id) {
    $("#addParamSetBtn").text("修改");
    $("#paramadd").modal();
    $("#addparam").hide();
    $("#paramadd-update").val("1");

    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    var baseparam;
    var baseparamlist;
    if (jsonObj.type == "0") {
        baseparamlist = serBaseNodeObj.inparameter;

    } else if (jsonObj.type == "1") {
        baseparamlist = serBaseNodeObj.outparameter;
    }

    for (var i = 0; i < (baseparamlist.list).length; i++) {
        if (id == (baseparamlist.list)[i].id) {
            baseparam = (baseparamlist.list)[i];
        }
    }

    $("#paramid-set").val(baseparam.id);
    $("#ecode").val(baseparam.ecode);
    $("#reqdesc").val(baseparam.reqdesc);
    $("#reqtype").val(baseparam.reqtype);
    $("#parampos").val(baseparam.parampos);
    $("#maxlength").val(baseparam.maxlength);
    if (baseparam.isempty == "1") {
        $("#isempty").val("1");
    } else if (baseparam.isempty == "0") {
        $("#isempty").val("0");
    }
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage) {
    $("#reqparamtable").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}


function addInput() {
    var input = "<div style='margin-top: 10px;' class='jsonparam form-inline jsonparamdiv'>" +
        "<label class='control-label'>参数::</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +
        "<label class='control-label'>说明:</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +
        "<label class='control-label'>参数类型:</label>" +
        "<select class='param-input form-input' style='width:80px'> " +
        "<option value='String'>String</option>     " +
        "<option value='int'>int</option>           " +
        "<option value='boolean'>boolean</option>   " +
        "<option value='Object'>Object</option>     " +
        "<option value='List'>List</option>         " +
        "<option value='List_Object'>List_Object</option> " +
        "<option value='List_List'>List_List</option>    " +
        "</select>" +
        "  <label class=\"control-label\">参数位置:</label>" +
        " <select id=\"parampos\" class='param-input form-input' style=\"width:80px;height: 25px;\"> " +
        "       <option value=\"0\" selected = \"selected\">请求体</option>" +
        "      <option value=\"4\">请求头</option>" +
        "      <option value=\"1\">url上的参数</option>" +
        "      <option value=\"5\">xml请求体属性</option>" +
        "</select>" +
        "<label class=\"control-label maxlength \">最大长度:</label>" +
        "<input class=\"form-control param-input maxlength form-input\" style=\"width:80px\" type=\"text\" id=\"maxlength\"/>" +
        "<label class='control-label'>是否必传项:</label>" +
        "<select class='param-input form-input' style='width:80px'>" +
        "<option value='0'>否</option> " +
        "<option value='1'>是</option> " +
        "</select>" +
        "<button type='button' class='btn btn-default deleteparam' aria-label='Left Align' >" +
        "<span class='glyphicon glyphicon-minus' aria-hidden='true'></span>" +
        "</button>" +
        "</div>";
    $("#paramset").append(input);

    $("#paramset").find(".deleteparam").each(function (i, e) {
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


//将基本信息填入serNodeArray
function saveInformation(){
    for (var i = 0; i < serNodeArray.length; i++) {
        if(serNodeArray[i].nodeName == "开始"){
            serNodeArray[i].serName = $("#pubname").val();
            serNodeArray[i].agreement = $("#pubprotocal").val();
            serNodeArray[i].reqformat = $("#pubreqformat").val();
            serNodeArray[i].respformat = $("#pubrespformat").val();
            serNodeArray[i].reqdatatype = $("#pubreqdatatype").val();
            serNodeArray[i].returndatatype = $("#pubreturndatatype").val();
            serNodeArray[i].serviceType = $("#serviceTypeId").attr("idvalue");
            serNodeArray[i].serdesc = $("#pubdesc").val();
            serNodeArray[i].inCharset = $("#inCharset").val();
            serNodeArray[i].outCharset = $("#outCharset").val();
        }else if(serNodeArray[i].nodeName == "结束"){
            serNodeArray[i].serName = $("#pubname").val();
            serNodeArray[i].agreement = $("#pubprotocal").val();
            serNodeArray[i].reqformat = $("#pubreqformat").val();
            serNodeArray[i].respformat = $("#pubrespformat").val();
            serNodeArray[i].reqdatatype = $("#pubreqdatatype").val();
            serNodeArray[i].returndatatype = $("#pubreturndatatype").val();
            serNodeArray[i].serviceType = $("#serviceTypeId").attr("idvalue");
            serNodeArray[i].serdesc = $("#pubdesc").val();
            serNodeArray[i].inCharset = $("#inCharset").val();
            serNodeArray[i].outCharset = $("#outCharset").val();
        }
    }
    console.log(serNodeArray);
}


//保存注册信息
function saveServiceModel() {
    //判断出参是否存在，如果不存在则添加
    for (var i = 0; i < serNodeArray.length; i++) {
        if(serNodeArray[i].nodeName == "结束"){
            if(serNodeArray[i].outparameter == null || serNodeArray[i].outparameter == ""){
                var result = new reqObj(getuuid(), "success", "选取某个参数作为成功标志", "String", "0", "", "0", "", "", "");
                var resultList = new Array();
                resultList[0] = result;
                var objdata = {
                    list: ""
                };
                objdata.list = resultList;
                serNodeArray[i].outparameter = objdata;
            }
        }
    }

    if(updateflag == "update"){
        console.log(idflowChartId);
        //更新数据
        $.ajax({
            "url": webpath + "/serviceRegisterController/updateNode",
            "type": "POST",
            data: {
                "serId": idflowChartId,
                "updateFlag": "isreplace",
                "serNodeArray": JSON.stringify(serNodeArray),
                "serJoinArray": JSON.stringify(serJoinArray),
                "serFlowType": $("#serviceTypeId").attr("idvalue")
            },
            async: false,
            success: function (data) {
                if (data == "success") {
                    layer.msg('更新成功', {time: 2000, icon: 1});
                }
                window.location.href = webpath + "/mineServiceRegisterController/mineRegister";
            }
        });
    }else{
        //保存数据
        $.ajax({
            "url": webpath + "/serviceRegisterController/addNode",
            "type": "POST",
            data: {
                "serNodeArray": JSON.stringify(serNodeArray),
                "serJoinArray": JSON.stringify(serJoinArray),
                "serFlowType": $("#serviceTypeId").attr("idvalue")
            },
            async: false,
            success: function (data) {
                if (data == "success") {
                    layer.msg('添加成功', {time: 2000, icon: 1});
                }
                window.location.href = webpath + "/mineServiceRegisterController/mineRegister";
            }
        });
    }
}

//服务类型属性展示并过滤数据
function initTree(data){
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
    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes(data));
}

//获取树的节点
function getTreeNodes(data){
    var zNodes = new Array();
    var treedatas = data;
    if(treedatas != null&&treedatas.length>0){
        for(var i=0;i<treedatas.length;i++){
            var treedata = {
                id:treedatas[i].serTypeId,
                pId:treedatas[i].parentId,
                name:treedatas[i].serTypeName,
                open:true
            }
            zNodes.push(treedata);
        }
    }
    return zNodes;
}

//展示列表
function showMenu() {
    var cityObj = $("#serviceTypeId");
    var cityOffset = $("#serviceTypeId").offset();
    $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px",width:cityObj.width}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

//点击事件之前发生的动作
function beforeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if (!check){
        layer.msg('只能选择子节点！', {time: 2000, icon: 4});
    }
    return check;
}

//点击事件
function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        v = "";
    t = "";
    nodes.sort(function compare(a,b){return a.id-b.id;});
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
        t += nodes[i].id + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    if (t.length > 0 ) t = t.substring(0, t.length-1);
    var cityObj = $("#serviceTypeId");
    cityObj.val(v);
    cityObj.attr("idvalue",t);
    hideMenu();
}

//隐藏窗口绑定
function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

//隐藏窗口
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
        hideMenu();
    }
}

//检查服务名称及类型是否有值
function checkNameValue(status) {
    console.log($("#serviceTypeId").attr("idvalue"));
    if ($("#pubname").val() == null || $("#pubname").val() == ""){
        layer.msg('请输入服务名称！', {time: 1000, icon: 2});
    }else if ($("#serviceTypeId").attr("idvalue") == null || $("#serviceTypeId").attr("idvalue") == ""){
        layer.msg('请输入服务类型！', {time: 1000, icon: 2});
    }else {
        insertInputParam(status);
    }
}

//跳转到输入参数界面
function insertInputParam(status) {
    if($("#baseinfo").attr("class") == "active"){
        saveInformation();
    }
    if(status == "0"){
        $("#reqdiv").siblings().removeClass("active");
        $("#reqdiv").addClass("active");
        var menu = $("#reqdiv").attr("menu");
        $("#" + menu).show();
        $("#" + menu).siblings().hide();
    }else{
        $("#baseinfo").siblings().removeClass("active");
        $("#baseinfo").addClass("active");
        var menu = $("#baseinfo").attr("menu");
        $("#" + menu).show();
        $("#" + menu).siblings().hide();
    }
    changeTag(status, true);
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
