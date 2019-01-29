/**
 * Created by niu on 2017/12/10.
 */
var tag;
var idflowChartId;

//存储所有节点对象
var serNodeArray = new Array();
//存储所有线对象
var serJoinArray = new Array();

//更新和添加标识
var updateflag = "";
var appId;
var lineId1;
var lineId2;

$(document).ready(function () {
    $("#displayOrder").click(function(){
        if ($("#pubname").val() == "" || $("#pubname").val() == null) {
            layer.msg('请填写服务名称！', {time: 1000, icon: 2});
        } else {
            for (var i = 0; i < serNodeArray.length; i++) {
                if (serNodeArray[i].nodeType == "rectangle") {
                    serNodeArray[i].nodeName = $("#pubname").val();
                    serNodeArray[i].serName = $("#pubname").val();
                    serNodeArray[i].url = $("#puburl").val();
                    serNodeArray[i].port = $("#pubport").val();
                    serNodeArray[i].agreement = $("#pubprotocal").val();
                    serNodeArray[i].reqformat = $("#pubreqformat").val();
                    serNodeArray[i].respformat = $("#pubrespformat").val();
                    serNodeArray[i].method = $("#pubmethod").val();
                    serNodeArray[i].reqdatatype = $("#pubreqdatatype").val();
                    serNodeArray[i].returndatatype = $("#pubreturndatatype").val();
                    serNodeArray[i].serdesc = $("#pubdesc").val();
                    serNodeArray[i].inCharset = $("#inCharset").val();
                    serNodeArray[i].outCharset = $("#outCharset").val();
                }
            }

            saveRelationParam();

            //判断orderId在订阅表中是否存在，存在则更新
            if (orderId == null || orderId == "") {
                addAll("insert");
            } else {
                addAll("update");
            }
        }
    });


    //定义全局使用的layer样式
    layer.config({skin: 'layui-layer-ext'});
    index = layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.style(index, {
        height: ($(".panel-body").height() ) + 'px',
    });

    //查询所有应用id
    selectApplication();

    idflowChartId = flowChartId;
    updateflag = "update";
    serNodeArray = JSON.parse(initserNodeArray);
    serJoinArray = JSON.parse(initserJoinArray);

    //基本信息回显初始化
    initBaseinfo();

    if (serNodeArray.length < 3) {
        //新增条件节点及接口节点
        createLayer();
    }else {
        for (var i=0; i<serJoinArray.length;i++){
            for (var j=0;j<serNodeArray.length;j++){
                //第一根线
                if (serNodeArray[j].nodeType == "rectangle"){
                    if (serJoinArray[i].endNodeId == serNodeArray[j].nodeId){
                        lineId1 = serJoinArray[i].joinId;
                    }
                }

                //第二根线
                if (serNodeArray[j].nodeType == "circle" && serNodeArray[j].nodeName == "结束"){
                    if (serJoinArray[i].endNodeId == serNodeArray[j].nodeId){
                        lineId2 = serJoinArray[i].joinId;
                    }
                }
            }
        }
    }


    $(".apitab li").each(function () {
        $(this).click(function () {
            if ($("#pubname").val() == "" || $("#pubname").val() == null) {
                layer.msg('请填写服务名称！', {time: 1000, icon: 2});
            } else {
                //如果是从入参映射或出参映射离开，则执行该方法
                if ($("#relationdiv").attr("class") == "active" || $("#resprelationdiv").attr("class") == "active") {
                    saveRelationParam();
                }
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                var menu = $(this).attr("menu");
                $("#" + menu).show();
                $("#" + menu).siblings().hide();
            }
        })
    });


    $("#reqdiv").click(function () {
        changeTag("0", true);
    });

    $("#respdiv").click(function () {
        changeTag("1", true);
    });

    $("#relationdiv").click(function () {
        changeTag("2", true);
    });

    $("#resprelationdiv").click(function () {
        changeTag("3", true);
    });
    $(".panel-body").find(".id-next-button").each(function(i, e){
        $(this).click(function(){
            var status = $(this).attr("status");
            if(status == "0"){
                $("#reqdiv").click();
            }else if (status == "1"){
                $("#respdiv").click();
            }else if(status == "2"){
                $("#relationdiv").click();
            }else if(status = "3"){
                $("#resprelationdiv").click();
            }
            // changeTag(status, true);
        })
    });
    $(".panel-body").find(".id-Model-button").each(function(i,e){
       $(this).click(function(){
           var status = $(this).attr("status");
           if(status == "0"){
               $("#baseinfo").click();
           }else if(status == "1"){
               $("#reqdiv").click();
           }else if(status == "2"){
               $("#respdiv").click();
           }else if(status == "3"){
               $("#relationdiv").click();
           }
       })
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
    $("#addParamBtnRes").on("click", function () {
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

    $("#reqtype").change(function () {
        if ($(this).val() != "String") {
            $(".maxlength").hide();
        } else {
            $(".maxlength").show();
        }
    })

    $("#id-saveServiceModel-button").bind("click", saveServiceModel);
    $("#id-prePage-button").bind("click", prePage);
});

//返回到商城页面
function prePage() {
    console.log(orderId);
    if (orderId == null || orderId == ""){
        window.location.href = webpath + "/apiStore/index";
    }else{
        window.location.href = webpath + "/orderInterface/indexMineOrder";
    }
}

function selectApplication() {
    $.ajax({
        "url": webpath + "/serApplication/getAllByCondition",
        "type": "POST",
        daya:{
        },
        async: false,
        success: function (data) {
            var html = "";
            for (var i = 0; i < data.length; i++) {
                $("#applicationId").append("<option value='"+data[i].applicationId+"'>"+data[i].applicationName+"</option>");
            }
        }
    });
}


//初始化
function initBaseinfo() {
    if (serNodeArray != null && serNodeArray.length > 0) {
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "rectangle") {
                $("#pubname").val(serNodeArray[i].serName);
                $("#applicationId").val(applicationId);
                $("#puburl").val(serNodeArray[i].url);
                $("#pubport").val(serNodeArray[i].port);
                $("#pubprotocal").val(serNodeArray[i].agreement);
                $("#pubmethod").val(serNodeArray[i].method);
                $("#pubreqformat").val(serNodeArray[i].reqformat);
                $("#pubrespformat").val(serNodeArray[i].respformat);
                $("#pubreqdatatype").val(serNodeArray[i].reqdatatype);
                $("#pubreturndatatype").val(serNodeArray[i].returndatatype);
                $("#pubdescdiv").val(serNodeArray[i].serdesc);
                $("#inCharset").val(serNodeArray[i].inCharset);
                $("#outCharset").val(serNodeArray[i].outCharset);
                $("#pubdesc").val(orderDesc);
            }
        }
    }

    //如果结束节点没有参数，则赋予一个成功参数
    for (var i = 0; i < serNodeArray.length; i++) {
        if (serNodeArray[i].nodeType == "circle" && serNodeArray[i].nodeName == "结束") {
            if (serNodeArray[i].outparameter.list.length == 0) {
                var result = new reqObj(getuuid(), "success", "选取某个参数作为成功标志", "String", "0", "", "0", "", "");
                serNodeArray[i].outparameter.list[0] = result;
            }
        }
    }
}

//创建节点
function createLayer() {
    //初始化线1
    lineId1 = getuuid();
    var conditionNodeObj1 = new serJoinObj(idflowChartId, $("#serVerId").val(), lineId1, "", "", "r", "2", "1", "" , "", flowType, "", "");
    serJoinArray.push(conditionNodeObj1)

    //初始化线2
    lineId2 = getuuid();
    var conditionNodeObj2 = new serJoinObj(idflowChartId, $("#serVerId").val(), lineId2, "", "", "r", "2", "1", "" , "", flowType, "", "");
    serJoinArray.push(conditionNodeObj2);


    //初始化接口节点
    var interfaceoptions = {
        "x": 265,
        "y": 75,
        "nodeType": "rectangle",
        "nodeName": $("#pubname").val(),
        'nodeId': getuuid()
    };
    var interfacenode = new interfaceSerNodeObj(idflowChartId, $("#serVerId").val(), interfaceoptions.nodeId, "", "rectangle", "3",
        interfaceoptions.x, interfaceoptions.y, 70, 50, 25, "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    serNodeArray.push(interfacenode);


    for (var i = 0; i < serNodeArray.length; i++) {
        for (var j = 0; j < serNodeArray.length; j++){
            //开始节点
            if (serNodeArray[i].nodeType == "circle" && serNodeArray[i].nodeName == "开始"){
                serNodeArray[i].setline = lineId1;
            }
            //结束节点
            if (serNodeArray[i].nodeType == "circle" && serNodeArray[i].nodeName == "结束"){
                serNodeArray[i].setline = lineId2;
            }
            //  矩形节点
            if (serNodeArray[i].nodeType == "rectangle") {
                if (serNodeArray[j].nodeType == "circle" && serNodeArray[j].nodeName == "开始"){
                    serNodeArray[i].startSet = serNodeArray[j].nodeId;
                }
                if (serNodeArray[j].nodeType == "circle" && serNodeArray[j].nodeName == "结束"){
                    serNodeArray[i].endSet = serNodeArray[j].nodeId;;
                }
                serNodeArray[i].setline = lineId2;
            }
        }
    }

    for (var i = 0; i < serJoinArray.length; i++){
        //第一根线
        if (serJoinArray[i].joinId == lineId1){
            for (var j=0; j<serNodeArray.length;j++){
                if (serNodeArray[j].nodeName == "开始" && serNodeArray[j].nodeType == "circle"){
                    serJoinArray[i].startSet = serNodeArray[j].nodeId;
                    serJoinArray[i].startNodeId = serNodeArray[j].nodeId;
                }
                if (serNodeArray[j].nodeType == "rectangle"){
                    serJoinArray[i].endSet = serNodeArray[j].nodeId;
                    serJoinArray[i].endNodeId = serNodeArray[j].nodeId;
                }
            }
        }

        //第二根线
        if (serJoinArray[i].joinId == lineId2){
            for (var j=0; j<serNodeArray.length;j++){
                if (serNodeArray[j].nodeType == "rectangle"){
                    serJoinArray[i].startSet = serNodeArray[j].nodeId;
                    serJoinArray[i].startNodeId = serNodeArray[j].nodeId;
                }
                if (serNodeArray[j].nodeName == "结束" && serNodeArray[j].nodeType == "circle"){
                    serJoinArray[i].endSet = serNodeArray[j].nodeId;
                    serJoinArray[i].endNodeId = serNodeArray[j].nodeId;
                }
            }
        }
    }

    console.log(serNodeArray);
    console.log(serJoinArray);

}

//切换窗口
function changeTag(type, tablefalg) {

    $("#clickTag").val("1");
    if ($("#pubname").val() == "" || $("#pubname").val() == null) {
        layer.msg('请填写服务名称！', {time: 1000, icon: 2});
        $("#addParamBtn").attr("style", "display:none");
    } else {
        $("#addParamBtn").attr("style", "display:block");
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "rectangle") {
                serNodeArray[i].nodeName = $("#pubname").val();
                serNodeArray[i].serName = $("#pubname").val();
                serNodeArray[i].url = $("#puburl").val();
                serNodeArray[i].port = $("#pubport").val();
                serNodeArray[i].agreement = $("#pubprotocal").val();
                serNodeArray[i].reqformat = $("#pubreqformat").val();
                serNodeArray[i].respformat = $("#pubrespformat").val();
                serNodeArray[i].method = $("#pubmethod").val();
                serNodeArray[i].reqdatatype = $("#pubreqdatatype").val();
                serNodeArray[i].returndatatype = $("#pubreturndatatype").val();
                serNodeArray[i].serdesc = $("#pubdesc").val();
                serNodeArray[i].inCharset = $("#inCharset").val();
                serNodeArray[i].outCharset = $("#outCharset").val();
            }
        }
        $("#type").val(type);

        if (type == "0"||type == "1"){
            if(type == "1"){
                $("#id-paraminfotable").find(".id-next-button").attr('status',2);
                $("#id-paraminfotable").find(".id-Model-button").attr('status',1);
            }else{
                $("#id-paraminfotable").find(".id-next-button").attr('status',1);
                $("#id-paraminfotable").find(".id-Model-button").attr('status',0);
            }
            initParamTable();
        } else if (type == "2") {
            initManager();
            //初始化树
            initTree();

            $("#inrelatype").val("1");
            $("#outrelatype").val("1");
        } else if (type == "3") {
            initRespManager();
            initRespTree();
        }
    }
}

//---------------------------------------参数设置--------------------------------------------------------------------------------

//参数表格
function initParamTable(value) {
    manager = $("#reqparamtable").ligerGrid({
        columns: [
            {display: '编码', name: 'ecode', id: 'ecodeName', width: "20%", align: 'left'},
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
                        item.reqtype.toLowerCase() == "List_Object".toLowerCase() || item.reqtype.toLowerCase() == "List_List".toLowerCase())
                    ) {
                        s =
                            "<a href='javascript:void(0)'  onclick='insertParams(\"" + item.id + "\")'>新增</a>" + "-"
                            + s;
                    }
                    return s;
                }
            },
        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '95%',
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
function getSelected() {
    var row = manager.getSelectedRow();
    if (!row) {
        $("#parmParentId").val("");
    } else {
        $("#parmParentId").val(row.id);
    }
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
        "</select>" +
        "  <label class=\"control-label\">参数位置:</label>" +
        " <select id=\"parampos\" class='param-input form-input' style=\"width:80px;height: 25px;\"> " +
        "       <option value=\"0\" selected = \"selected\">请求体</option>" +
        "      <option value=\"4\">请求头</option>" +
        "      <option value=\"1\">url上的参数</option>" +
        "      <option value=\"2\">响应头</option>" +
        "      <option value=\"3\">响应体</option>" +
        "      <option value=\"5\">xml请求体属性</option>" +
        "      <option value=\"6\">xml响应体属性</option>" +
        "      <option value=\"7\">命名空间</option>" +
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


//获取表格数据
function getParams() {
    var objdata = {
        list: ""
    };
    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    var baseserNodeObj;
    for (var i = 0; i < serNodeArray.length; i++) {
        if (serNodeArray[i].nodeType == "rectangle") {
            baseserNodeObj = serNodeArray[i];
        }
    }
    if (jsonObj.type == "0" && baseserNodeObj.inparameter != undefined && baseserNodeObj.inparameter != "") {
        objdata = baseserNodeObj.inparameter;
    } else if (jsonObj.type == "1" && baseserNodeObj.outparameter != undefined && baseserNodeObj.outparameter != "") {
        objdata = baseserNodeObj.outparameter;
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
    var baseserNodeObj;
    for (var i = 0; i < serNodeArray.length; i++) {
        if (serNodeArray[i].nodeType == "rectangle") {
            baseserNodeObj = serNodeArray[i];
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
            baseparamlist = baseserNodeObj.inparameter;
        } else if (jsonObj.type == "1") {
            baseparamlist = baseserNodeObj.outparameter;
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
            for (var i = 0; i < serNodeArray.length; i++) {
                if (serNodeArray[i].nodeType == "rectangle") {
                    baseparamlist.list = orderSet(baseparamlist.list);
                    serNodeArray[i].inparameter = baseparamlist;
                }
            }
        } else if (jsonObj.type == "1") {
            for (var i = 0; i < serNodeArray.length; i++) {
                if (serNodeArray[i].nodeType == "rectangle") {
                    baseparamlist.list = orderSet(baseparamlist.list);
                    serNodeArray[i].outparameter = baseparamlist;
                }
            }
        }
        console.log(serNodeArray);
        initParamTable();

    }
    //添加
    else {
        var parentId = $("#parmParentId").val();
        var reqobj1 = new reqObj(getuuid(), ecode, reqdesc, reqtype, parampos, isempty, type, maxlength, parentId);
        var reqobj1str = JSON.stringify(reqobj1);
        objarr.push(JSON.parse(reqobj1str));
        for (var i = 0; i < divinput.length; i++) {
            var arr = new Array();
            $(divinput[i]).find(".form-input").each(function (i, e) {
                arr.push($(this).val());
            })
            var reqobj = new reqObj(getuuid(), arr[0], arr[1], arr[2], arr[3], arr[5], type, arr[4], parentId);
            var reqobjstr = JSON.stringify(reqobj);
            objarr.push(JSON.parse(reqobjstr));
        }

        var listobjarr = {
            list: objarr
        };

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
                (baseserNodeObj.inparameter).list = orderSet((baseserNodeObj.inparameter).list);
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
                (baseserNodeObj.outparameter).list = orderSet((baseserNodeObj.outparameter).list);
            }
        }

        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "rectangle") {
                serNodeArray[i].inparameter = baseserNodeObj.inparameter;
                serNodeArray[i].outparameter = baseserNodeObj.outparameter;
            }
        }

        initParamTable();

        console.log(serNodeArray);
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
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "rectangle") {
                baseserNodeObj = serNodeArray[i];
            }
        }
        if (jsonObj.type == "0") {
            if (baseserNodeObj.inparameter != null && baseserNodeObj.inparameter != undefined) {
                var baseparamlist = baseserNodeObj.inparameter;
                for (var i = 0; i < (baseparamlist.list).length; i++) {
                    if (id == (baseparamlist.list)[i].id) {
                        removeByValue((baseparamlist.list), (baseparamlist.list)[i]);
                    }
                }
                for (var i = 0; i < serNodeArray.length; i++) {
                    if (serNodeArray[i].nodeType == "rectangle") {
                        baseparamlist.list = orderSet(baseparamlist.list);
                        serNodeArray[i].inparameter = baseparamlist;
                    }
                }
                //baseserNodeObj.inparameter = baseparamlist;
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
                for (var i = 0; i < serNodeArray.length; i++) {
                    if (serNodeArray[i].nodeType == "rectangle") {
                        baseparamlist.list = orderSet(baseparamlist.list);
                        serNodeArray[i].outparameter = baseparamlist;
                    }
                }
                //baseserNodeObj.outparameter = baseparamlist;
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
    for (var i = 0; i < serNodeArray.length; i++) {
        if (serNodeArray[i].nodeType == "rectangle") {
            baseserNodeObj = serNodeArray[i]
        }
    }

    if (jsonObj.type == "0") {
        baseparamlist = baseserNodeObj.inparameter;

    } else if (jsonObj.type == "1") {
        baseparamlist = baseserNodeObj.outparameter;

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


//------------------------------------------参数映射第一个表格-----------------------------------------------------------------------------


//初始化映射表格
function initManager() {

    var width = $(document).width() - $(document).width() / 4.5;
    var result;
    if ($("#relationdiv").attr("class") == "active") {
        result = "id-resplayoutparamtable";
    } else {
        result = "id-resplayoutparamtable-div";
    }
    var baserelArray = new Array();


    if (serJoinArray != null && serJoinArray != undefined) {
        for (var i = 0; i < serJoinArray.length; i++) {
            if ($("#type").val() == "2") {
                if (serJoinArray[i].joinId == lineId1) {
                    if (serJoinArray[i].relationSet != null && serJoinArray[i].relationSet != undefined && (serJoinArray[i].relationSet).length > 0) {
                        baserelArray = serJoinArray[i].relationSet;
                    }
                }
            }
        }
    }


    manager = $("#" + result).ligerGrid({
        columns: [
            {display: '参数名称', name: 'ecode', id: 'ecodeName', width: width / 7, align: 'center'},
            {display: '描述', name: 'reqdesc', width: width / 6, align: 'center'},
            {display: '类型', name: 'reqtype', width: width / 6, type: 'String', align: 'center'},
            {
                display: '参数映射', name: 'paramFlowChartBean', align: 'center', width: width / 6,
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
                        "<input type='hidden' id='ecode_" + item.__index + "' value='" + item.ecode + "' >" +
                        "<input type='text' id='real_" + item.__index + "' class='ztreeparam11' placeholder = '设置映射参数' style='border: none;height: 32px;width:160px' " +
                        "inititemid='" + item.id + "'" +
                        " value='" + initvalue + "'" +
                        " initidvalue='" + initid + "'" +
                        " onclick=\"inputClick(this,'" + item.ecode + "','" + item.id + "','" + initid + "')\">";

                    return s;
                }
            },
            {
                display: '映射规则', name: 'regex', width: width / 6, type: 'String', align: 'center',
                render: function (item) {
                    var initregex = "";
                    var initconstant = "";

                    //再次点击后回显
                    if (baserelArray != null && baserelArray != undefined) {
                        for (var i = 0; i < baserelArray.length; i++) {
                            if (baserelArray[i].nextparamId == item.id) {
                                initregex = baserelArray[i].regex;
                                initconstant = baserelArray[i].constantparam;
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
            }
        ],
        //width: '950px', height: '410px',
        width: width, height: '95%',
        data: getConditionParams(),
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: false,
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId'
        },
        usePager: false,
    });
    return manager;
}

//获取表格数据
function getConditionParams() {
    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    //入参映射
    if (jsonObj.type == "2") {
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "rectangle") {
                return serNodeArray[i].inparameter;
            }
        }
    }
    //出参映射
    else if (jsonObj.type == "3") {
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "circle" && serNodeArray[i].nodeName == "结束") {
                return serNodeArray[i].outparameter;
            }
        }
    }
}

//确定树的位置
function inputClick(vthis, ecode, id, initid) {
    var cityObj = $(vthis);
    //获取到当前的input对象
    inputText = cityObj;
    var cityOffset = $(vthis).offset();
    var x = cityOffset.left * 0.56;
    var y = cityOffset.top * 0.95;
    $("#menuContent").css({left: x + "px", top: y + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}

function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

//初始化表格中的树
function initTree() {
    var setting = {
        view: {
            //addHoverDom: addHoverDom,
            //removeHoverDom: removeHoverDom,
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
            //showRemoveBtn: showRemoveBtn,
            //showRenameBtn: showRenameBtn
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onTreeClick,
            //beforeRemove: beforeRemove,
            //onRemove: onRemove
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes());

    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");

    var nodes = treeObj.getNodes();
    //treeObj.hideNode(nodes[0]);
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

//获取前几个服务节点的参数
function getTreeNodes() {
    var inparam_zNodes = new Array();

    if ($("#type").val() == "2") {
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "circle" && serNodeArray[i].nodeName == "开始") {
                var baseNode = serNodeArray[i];
                if (baseNode.inparameter != undefined) {
                    var out_zsernode = {
                        id: serNodeArray[i].nodeId,
                        pId: "ROOT",
                        name: serNodeArray[i].nodeName,
                        reqtype: ""
                    };
                    inparam_zNodes.push(out_zsernode);

                    var inparameters = (baseNode.inparameter).list;
                    for (var j = 0; j < inparameters.length; j++) {
                        var pId = serNodeArray[i].nodeId;

                        if (inparameters[j].parentId != "" && inparameters[j].parentId != undefined) {
                            pId = inparameters[j].parentId;
                        }
                        var out_zparamnode = {
                            id: inparameters[j].id,
                            pId: pId,
                            name: inparameters[j].ecode,
                            reqtype: inparameters[j].reqtype
                        };
                        inparam_zNodes.push(out_zparamnode);
                    }

                }
            }
        }
    } else if ($("#type").val() == "3") {
        for (var i = 0; i < serNodeArray.length; i++) {
            if (serNodeArray[i].nodeType == "rectangle") {
                var baseNode = serNodeArray[i];
                if (baseNode.outparameter != undefined) {
                    var out_zsernode = {
                        id: serNodeArray[i].nodeId,
                        pId: "ROOT",
                        name: serNodeArray[i].nodeName,
                        reqtype: ""
                    };
                    inparam_zNodes.push(out_zsernode);

                    var outparameters = (baseNode.outparameter).list;

                    if (outparameters != null && outparameters != undefined) {
                        for (var j = 0; j < outparameters.length; j++) {
                            var pId = serNodeArray[i].nodeId;

                            if (outparameters[j].parentId != "" && outparameters[j].parentId != undefined) {
                                pId = outparameters[j].parentId;
                            }
                            var out_zparamnode = {
                                id: outparameters[j].id,
                                pId: pId,
                                name: outparameters[j].ecode,
                                reqtype: outparameters[j].reqtype
                            };
                            inparam_zNodes.push(out_zparamnode);
                        }
                    }


                }
            }
        }
    }

    return inparam_zNodes;
}

//设置树的父节点不能点击
function beforeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent && treeNode.flag != "0");
    if (!check) layer.msg('   请选择参数！', {time: 1000, icon: 2});
    return check;
}

//设置表格一的规则弹框
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
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}


//-----------------------------------------参数映射第二个表格-----------------------------------------------------------------------


//初始化出参映射表格
function initRespManager() {
    var width = $(document).width() - $(document).width() / 4.5;
    var result;
    var baserelArray = new Array();
    if (serJoinArray != null && serJoinArray != undefined) {
        for (var i = 0; i < serJoinArray.length; i++) {
            if ($("#type").val() == "3") {
                if (serJoinArray[i].joinId == lineId2) {
                    if (serJoinArray[i].relationSet != null && serJoinArray[i].relationSet != undefined && (serJoinArray[i].relationSet).length > 0) {
                        baserelArray = serJoinArray[i].relationSet;
                    }
                }
            }
        }
    }
    manager = $("#id-resplayoutparamtable-div").ligerGrid({
        columns: [
            {display: '参数名称', name: 'ecode', id: 'ecodeName', width: width / 7, align: 'center'},
            {display: '描述', name: 'reqdesc', width: width / 6, align: 'center'},
            {display: '类型', name: 'reqtype', width: width / 6, type: 'String', align: 'center'},
            {
                display: '参数映射', name: 'paramFlowChartBean', align: 'center', width: width / 6,
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
                        "<input type='hidden' id='ecode_resp_" + item.__index + "' value='" + item.ecode + "' >" +
                        "<input type='text' id='real_resp_" + item.__index + "' class='ztreeparam11' placeholder = '设置映射参数' style='border: none;height: 32px;width:160px' " +
                        "inititemid='" + item.id + "'" +
                        " value='" + initvalue + "'" +
                        " initidvalue='" + initid + "'" +
                        " onclick=\"inputClick(this,'" + item.ecode + "','" + item.id + "','" + initid + "')\">";

                    return s;
                }
            },
            {
                display: '映射规则', name: 'regex', width: width / 6, type: 'String', align: 'center',
                render: function (item) {
                    var initregex = "";
                    var initconstant = "";

                    //再次点击后回显
                    if (baserelArray != null && baserelArray != undefined) {
                        for (var i = 0; i < baserelArray.length; i++) {
                            if (baserelArray[i].nextparamId == item.id) {
                                initregex = baserelArray[i].regex;
                                initconstant = baserelArray[i].constantparam;
                            }
                        }
                    }

                    var str = "<a onclick=\"regexmodelResp('" + item.__index + "','" + item.id + "')\">" +
                        "<input type='hidden' value='" + initregex + "' " +
                        " initconstant='" + initconstant + "' " +
                        " placeholder = '点击设置' style='border: none;height: 32px;width:160px' readonly" +
                        " id='regex_resp_" + item.__index +
                        "' inititemid='" + item.id + "'\>" +
                        "点击设置</a>";

                    return str;
                }
            }
        ],
        //width: '950px', height: '410px',
        width: width, height: '95%',
        data: getConditionParams(),
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: false,
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId'
        },
        usePager: false,
    });
    return manager;
}

//初始化出参表格的树
function initRespTree() {
    var setting = {
        view: {
            //addHoverDom: addHoverDom,
            //removeHoverDom: removeHoverDom,
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
            //showRemoveBtn: showRemoveBtn,
            //showRenameBtn: showRenameBtn
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onTreeClick,
            //beforeRemove: beforeRemove,
            //onRemove: onRemove
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting, getTreeNodes());

    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");

    var nodes = treeObj.getNodes();
    //treeObj.hideNode(nodes[0]);
}

//设置规则弹框
function regexmodelResp(id, itemid) {
    var initregexval = $("#regex_resp_" + id).val();
    var initconstantval = $("#regex_resp_" + id).attr("initconstant");
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
            $("#regex_resp_" + id).val(regex);
            $("#regex_resp_" + id).attr("initconstant", constantparam);
            layer.close(index);
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}



//-----------------------------------------保存信息-----------------------------------------------------------------------

//将映射参数保存到serJoinArray
function saveRelationParam() {
    var datalength = 0;
    var basenode;
    var relationArray = new Array();
    relationArray.splice(0, relationArray.length);


    if (getConditionParams() != undefined) {
        if(getConditionParams() != null && getConditionParams() != ""){
            datalength = (getConditionParams().list).length;
        }else {
            datalength = 0;
        }

    }

    if ($("#type").val() == "2") {
        for (var i = 0; i < serJoinArray.length; i++) {
            console.log(serJoinArray);
            console.log(lineId1);
            if (serJoinArray[i].joinId == lineId1) {
                basenode = serJoinArray[i];
            }
        }

        for (var j = 0; j < datalength; j++) {
            var relationobj
            if($("#regex_" + j).attr("initconstant") == null ||
                $("#regex_" + j).attr("initconstant") == "" ||
                $("#regex_" + j).attr("initconstant") == "undefined"){
                relationobj = new paramrelationObj(
                    basenode.joinId,
                    getuuid(),
                    $("#real_" + j).attr("initidvalue"),
                    $("#real_" + j).val(),
                    $("#real_" + j).attr("inititemid"),
                    $("#ecode_" + j).val(),
                    //$("#regex_" + j).val(),
                    //$("#regex_" + j).attr("initconstant"),
                    basenode.endSet);
            }else {
                relationobj = new paramrelationObj(
                    basenode.joinId,
                    getuuid(),
                    $("#real_" + j).attr("initidvalue"),
                    $("#real_" + j).val(),
                    $("#real_" + j).attr("inititemid"),
                    $("#ecode_" + j).val(),
                    $("#regex_" + j).val(),
                    $("#regex_" + j).attr("initconstant"),
                    basenode.endSet);
            }

            relationArray.push(relationobj);
        }
    } else if ($("#type").val() == "3") {
        for (var i = 0; i < serJoinArray.length; i++) {
            if (serJoinArray[i].joinId == lineId2) {
                basenode = serJoinArray[i];
            }
        }

        for (var j = 0; j < datalength; j++) {
            var relationobj
            if($("#real_resp_" + j).attr("initconstant") == null ||
                $("#real_resp_" + j).attr("initconstant") == "" ||
                $("#real_resp_" + j).attr("initconstant") == "undefined"){
                relationobj = new paramrelationObj(
                    basenode.joinId,
                    getuuid(),
                    $("#real_resp_" + j).attr("initidvalue"),
                    $("#real_resp_" + j).val(),
                    $("#real_resp_" + j).attr("inititemid"),
                    $("#ecode_resp_" + j).val(),
                    //$("#regex_resp_" + j).val(),
                    //$("#regex_resp_" + j).attr("initconstant"),
                    basenode.endSet);
            }else {
                relationobj = new paramrelationObj(
                    basenode.joinId,
                    getuuid(),
                    $("#real_resp_" + j).attr("initidvalue"),
                    $("#real_resp_" + j).val(),
                    $("#real_resp_" + j).attr("inititemid"),
                    $("#ecode_resp_" + j).val(),
                    $("#regex_resp_" + j).val(),
                    $("#regex_resp_" + j).attr("initconstant"),
                    basenode.endSet);
            }

            relationArray.push(relationobj);
        }
    }

    for (var i = 0; i < serJoinArray.length; i++) {
        if(basenode != undefined && basenode != null && basenode != ""){
            if (serJoinArray[i].joinId == basenode.joinId) {
                serJoinArray[i].relationSet = relationArray;
            }
        }
    }

    console.log(serNodeArray);
    console.log(serJoinArray);
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage) {
    $("#reqparamtable").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}

//保存注册信息
function saveServiceModel() {
    saveRelationParam();
    console.log(serNodeArray);
    console.log(serJoinArray);

    //判断orderId在订阅表中是否存在，存在则更新
    if(orderId == null || orderId == ""){
        addAll("insert");
    }else {
        addAll("update");
    }

}

/**
 * 获取appId和tokenId
 */
function getTokenId() {
    $.ajax({
        "url": webpath + "/security/getTokenId",
        "type": "GET",
        async: false,
        success: function (data) {
            var str = "appId=" + data.appId + "\n" + "token_id=" + data.token_id;
            $("#key_key").val(str);
            $("#getTokenidBtn").attr("disabled", "disabled");
            //appIdtoken = new appIdtoken(data.appId,data.token_id);
            appId = data.appId;
        }
    });
}


//保存所有信息
function addAll(insertOrUpdateFlag) {
    var data;
    if(insertOrUpdateFlag == "insert"){                     //当为新增时
        getTokenId();
        if (subloginId != null && subloginId != "") {       //代理订阅
            data = {
                "orderName": $("#pubname").val(),
                "applicationId": $("#applicationId").val(),
                "serId": $("#serId").val(),
                "serVersion": $("#serVersion").val(),
                "limitIp": "0",
                "appId": appId,
                "accFreq": "",
                "accFreqType": "",
                "checkStatus": 1,
                "repUserId": "",
                "serNodeArray": JSON.stringify(serNodeArray),
                "serJoinArray": JSON.stringify(serJoinArray),
                "loginId": subloginId,
                "repFlag": 1,
                "orderDesc": $("#pubdesc").val(),
                "insertOrUpdateFlag": insertOrUpdateFlag
            };
        } else {        //本人订阅
            data = {
                "orderName": $("#pubname").val(),
                "applicationId": $("#applicationId").val(),
                "serId": $("#serId").val(),
                "serVersion": $("#serVersion").val(),
                "limitIp": "0",
                "appId": appId,
                "accFreq": "",
                "accFreqType": "",
                "checkStatus": 1,
                "repUserId": "",
                "serNodeArray": JSON.stringify(serNodeArray),
                "serJoinArray": JSON.stringify(serJoinArray),
                "repFlag": 0,
                "orderDesc": $("#pubdesc").val(),
                "insertOrUpdateFlag": insertOrUpdateFlag
            };
        }
    }else{                                                  //当为更新时
        if (subloginId != null || subloginId != '') {       //代理订阅
            data = {
                "orderId": orderId,
                "orderName": $("#pubname").val(),
                "applicationId": $("#applicationId").val(),
                "serId": $("#serId").val(),
                "serVersion": $("#serVersion").val(),
                "limitIp": "0",
                "accFreq": "",
                "accFreqType": "",
                "checkStatus": 1,
                "repUserId": "",
                "serNodeArray": JSON.stringify(serNodeArray),
                "serJoinArray": JSON.stringify(serJoinArray),
                "loginId": subloginId,
                "repFlag": 1,
                "orderDesc": $("#pubdesc").val(),
                "insertOrUpdateFlag": insertOrUpdateFlag
            };
        } else {
            data = {                                           //本人订阅
                "orderId": orderId,
                "orderName": $("#pubname").val(),
                "applicationId": $("#applicationId").val(),
                "serId": $("#serId").val(),
                "serVersion": $("#serVersion").val(),
                "limitIp": "0",
                "accFreq": "",
                "accFreqType": "",
                "checkStatus": 1,
                "repUserId": "",
                "serNodeArray": JSON.stringify(serNodeArray),
                "serJoinArray": JSON.stringify(serJoinArray),
                "repFlag": 0,
                "orderDesc": $("#pubdesc").val(),
                "insertOrUpdateFlag": insertOrUpdateFlag
            };
        }
    }

    $.ajax({
        "url": webpath + "/apiStore/changeAsynchronized",
        "type": "POST",
        data: data,
        async: false,
        success: function (data) {
            if (data == "success") {
                layer.msg('操作成功!', {time: 1000, icon: 1});
                setTimeout("window.location.href = webpath + '/orderInterface/indexMineOrder'",1000);
            }else {
                layer.alert('订阅失败！<br>请检查服务是否已被该应用订阅!', {icon: 2});
            }
        }
    });

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
