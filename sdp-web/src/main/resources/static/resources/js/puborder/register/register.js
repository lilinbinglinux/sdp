/**
 * Created by niu on 2017/12/10.
 */
var tag;
var idflowChartId;

//存储所有节点对象
var serNodeArray = new Array();
//存储所有线对象
var serJoinArray = new Array();

//存菱形节点关系映射信息
var nodeRelation = new Array();
//存菱形节点正则表达式信息
var nodeRegex = new Array();
//更新和添加标识
var updateflag = "";

var manager;
var baseserNodeArray = serNodeArray;
var baseserNodeObj = {};

$(document).ready(function () {
    $("#jsPanel-1").hide();

    //定义全局使用的layer样式
    layer.config({skin: 'layui-layer-ext'});
    index = layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.style(index, {
        height: ($(".panel-body").height() ) + 'px',
    });

    //基本信息回显初始化
    initBaseinfo();

    $(".apitab li").each(function () {
        $(this).click(function () {
            $(this).siblings().removeClass("active");
            $(this).addClass("active");
            var menu = $(this).attr("menu");
            $("#" + menu).show();
            $("#" + menu).siblings().hide();
        })
    });

    $("#id-next-button").click(function () {
        if ($("#pubname").val() == "" || $("#pubname").val() == null) {
            layer.msg('请填写服务基本信息！', {time: 1000, icon: 2});
        } else {
            $("#baseinfo").removeClass("active");
            $("#reqdiv").addClass("active");
            $("#id-parambasetable").hide();
            $("#id-paraminfotable").show();
        }
    });

    $("#reqdiv").click(function () {
        changeTag("0", true);
    });

    $("#respdiv").click(function () {
        changeTag("1", true);
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
    })

    $("#addparam").bind("click", addInput);
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

    $("#reqtype").change(function () {
        if ($(this).val() != "String") {
            $(".maxlength").hide();
        } else {
            $(".maxlength").show();
        }
    })

    $("#id-saveServiceModel-button").bind("click", saveServiceModel);

});

//初始化
function initBaseinfo() {
    if (baseserNodeArray != null && baseserNodeArray.length > 0) {
        for (var i = 0; i < baseserNodeArray.length; i++) {
            if (nodeId == baseserNodeArray[i].nodeId) {
                var url = "";
                if (baseserNodeArray[i].nodeName != "开始") {
                    url = baseserNodeArray[i].url;
                    $("#serviceTypeId").val(baseserNodeArray[i].serviceType);
                } else {
                    $("#apiserviceTypeId").val(baseserNodeArray[i].serviceType);
                }
                $("#pubname").val(baseserNodeArray[i].serName);
                $("#pubprotocal").val(baseserNodeArray[i].agreement);
                $("#pubmethod").val(baseserNodeArray[i].method);
                $("#pubreqformat").val(baseserNodeArray[i].reqformat);
                $("#pubrespformat").val(baseserNodeArray[i].respformat);
                $("#pubreqdatatype").val(baseserNodeArray[i].reqdatatype);
                $("#pubreturndatatype").val(baseserNodeArray[i].returndatatype);
                $("#pubdescdiv").val(baseserNodeArray[i].serdesc);
            }
        }
    } else {
        $("#pubname").val("");
        $("#puburl").val("");
        $("#pubprotocal").val("");
        $("#pubmethod").val("");
        $("#pubreqformat").val("");
        $("#pubrespformat").val("");
        $("#pubreqdatatype").val("");
        $("#pubreturndatatype").val("");
        $("#pubdescdiv").val("");
        $("#apiserviceTypeId").val("");
    }

}

//切换窗口
function changeTag(type, tablefalg) {
    $("#clickTag").val("1");
    if ($("#pubname").val() == "" || $("#pubname").val() == null) {
        layer.msg('请填写服务名称！', {time: 1000, icon: 2});
    } else {
        baseserNodeObj.serName = $("#pubname").val();
        baseserNodeObj.agreement = $("#pubprotocal").val();
        baseserNodeObj.method = $("#pubmethod").val();
        baseserNodeObj.reqformat = $("#pubreqformat").val();
        baseserNodeObj.respformat = $("#pubrespformat").val();
        baseserNodeObj.reqdatatype = $("#pubreqdatatype").val();
        baseserNodeObj.returndatatype = $("#pubreturndatatype").val();
        baseserNodeObj.serdesc = $("#pubdesc").val();
        $("#type").val(type);

        //不是开始按钮需设置url
        if (nodeType != "circle" && nodeName != "开始") {
            baseserNodeObj.url = $("#puburl").val();
            baseserNodeObj.serviceType = $("#serviceTypeId").val();
        } else {
            baseserNodeObj.serviceType = $("#apiserviceTypeId").val();
        }

        initParamTable();

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
            {display: '编码', name: 'ecode', id: 'ecodeName', width: "20%", align: 'left'},
            {display: '描述', name: 'reqdesc', width: "20%", align: 'center'},
            {display: '类型', name: 'reqtype', width: "15%", type: 'String', align: 'center'},
            {
                display: '是否为空', name: 'isempty', align: 'center', width: "10%",
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
                        (item.reqtype.toLowerCase() == "Object".toLowerCase() || item.reqtype.toLowerCase() == "List".toLowerCase())
                    ) {
                        s =
                            "<a href='javascript:void(0)'  onclick='insertParams(\"" + item.id + "\")'>新增</a>" + "-"
                            + s;
                    }
                    return s;
                }
            },
        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '100%',
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: true,
        data: getParams(),
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId',
            isParent: function (data) {
                if (data.reqtype != "" && data.reqtype != null) {
                    //设置表格之前箭头
                    if (data.reqtype.toLowerCase() == "Object".toLowerCase() || data.reqtype.toLowerCase() == "List".toLowerCase())
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
            baseserNodeObj.inparameter = baseparamlist;
        } else if (jsonObj.type == "1") {
            baseserNodeObj.outparameter = baseparamlist;
        }
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
            if (baseserNodeObj.inparameter == null || typeof(baseserNodeObj.inparameter) == undefined || baseserNodeObj.inparameter == "") {
                baseserNodeObj.inparameter = listobjarr;
            } else {
                var baseparamlist = (baseserNodeObj.inparameter).list;
                for (var i = 0; i < objarr.length; i++) {
                    baseparamlist.push(objarr[i]);
                }
                (baseserNodeObj.inparameter).list = baseparamlist;
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
        }

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
            if (baseserNodeObj.inparameter != null && baseserNodeObj.inparameter != undefined) {
                var baseparamlist = baseserNodeObj.inparameter;
                for (var i = 0; i < (baseparamlist.list).length; i++) {
                    if (id == (baseparamlist.list)[i].id) {
                        removeByValue((baseparamlist.list), (baseparamlist.list)[i]);
                    }
                }
                baseserNodeObj.inparameter = baseparamlist;
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
            initParamTable();
        }
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

//保存注册信息
function saveServiceModel() {
    //初始化开始与结束节点
    initStartLayout();
    //初始化画布
    saveNodeInformation();
    //保存数据
    addAll();

}


//初始化开始节点和结束节点
function initStartLayout() {
    var nodeType = "";
    var startoptions = {
        "x": 100,
        "y": 100,
        "nodeType": "circle",
        "nodeName": "开始"
    };
    tag.addNode(startoptions);

    var endoptions = {
        "x": 535,
        "y": 100,
        "nodeType": "circle",
        "nodeName": "结束"
    };
    tag.addNode(endoptions);
}

//保存节点信息
function saveNodeInformation() {
    var data = tag.getData(idflowChartId);
    //idflowChartId = tag.getData(idflowChartId).flowChartId;
    var nodearray = data.nodearray;
    var endinitnode = "";

    for (var i = 0; i < nodearray.length; i++) {
        var startnode = nodearray[i];
        var endnodecount = 0;

        //从画布中获取到开始节点基本参数
        if (startnode.nodeName == "开始" && startnode.nodeType == "circle" && (idflowChartId == "" || idflowChartId == null)) {
            var startSerNode = new startSerNodeObj(data.flowChartId, serVerId, startnode.nodeId, "开始", "circle", "1",
                startnode.clientX, startnode.clientY, startnode.nodeWidth, startnode.nodeHeight, startnode.nodeRadius, "", "", "",
                $("#pubname").val(),
                $("#pubprotocal").val(),
                $("#pubreqformat").val(),
                $("#pubrespformat").val(),
                $("#pubreqdatatype").val(),
                $("#pubreturndatatype").val(),
                $("#serviceTypeId").val(),
                $("#pubdesc").val(),
                baseserNodeObj.inparameter, "");
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

        if (startnode.nodeName == "结束" && startnode.nodeType == "circle") {
            var result = new reqObj(getuuid(), "success", "选取某个参数作为成功标志", "String", "0", "", "0", "","");
            var resultList = new Array();
            resultList[0] = result;
            var objdata2 = {
                list: ""
            };
            objdata2.list = resultList;
            var startSerNode = new startSerNodeObj(data.flowChartId, serVerId, startnode.nodeId, "结束", "circle", "4",
                startnode.clientX, startnode.clientY, startnode.nodeWidth, startnode.nodeHeight, startnode.nodeRadius, "", "",
                "", "", "", "", "", "", "", "", "", "", objdata2);
            serNodeArray.push(startSerNode);
            endnodecount++;
        }
    }
    console.log(serNodeArray);
    console.log("结束节点个数为" + endnodecount);
    //统计是否有结束节点，如果没有则为新建，需添加
    if (endnodecount == 0) {
        for (var k = 0; k < serNodeArray.length; k++) {
            if (serNodeArray[k].nodeType == "circle" && serNodeArray[k].nodeName == "开始") {
                endinitnode = serNodeArray[k];
            }
        }

        for (var i = 0; i < nodearray.length; i++) {
            var result = new reqObj(getuuid(), "success", "选取某个参数作为成功标志", "String", "0", "", "0", "","");
            var resultList = new Array();
            resultList[0] = result;
            var objdata2 = {
                list: ""
            };
            objdata2.list = resultList;
            //添加结束节点
            if (nodearray[i].nodeName == "结束" && nodearray[i].nodeType == "circle") {
                var endnode = new startSerNodeObj(data.flowChartId, serVerId, nodearray[i].nodeId, "结束", "circle", "4",
                    nodearray[i].clientX, nodearray[i].clientY, nodearray[i].nodeWidth, nodearray[i].nodeHeight, nodearray[i].nodeRadius, "", nodearray[i].endSet, nodearray[i].startSet,
                    endinitnode.serName, endinitnode.agreement, endinitnode.reqformat, endinitnode.respformat, endinitnode.reqdatatype, endinitnode.returndatatype, endinitnode.serviceType, endinitnode.serdesc, "", objdata2);
                serNodeArray.push(endnode);
            }
        }
    }
}


//保存所有信息
function addAll() {
    console.log(serNodeArray);
    $.ajax({
        "url": webpath + "/serviceRegisterController/addNode",
        "type": "POST",
        data: {
            "serNodeArray": JSON.stringify(serNodeArray),
            "serJoinArray": JSON.stringify(serJoinArray)
        },
        async: false,
        success: function (data) {
            if (data == "success") {
                layer.msg('添加成功', {time: 2000, icon: 1});
            }
            window.location.href == webpath + "/mineServiceRegisterController/mineRegister";
        }
    });
}