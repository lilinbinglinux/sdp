var oTable;
var str;
jQuery(document).ready(function () {
    //定义全局使用的layer样式
    layer.config({
        skin: 'layui-layer-ext'
    });

    $("#type").val("0");
    $("#pubid").val(parent.paramifream_pubid);
    initParamTable();

    $("#reqdiv").click(function () {
        $("#respdiv").removeClass();
        $("#definediv").removeClass();
        $("#sampledatadiv").removeClass();
        $(this).addClass("active");
        $("#id-paraminfotable").css("display", "block");
        $("#id-definedparamtable").css("display", "none");
        $("#id-sampledata").css("display", "none");
        $("#type").val("0");
        initParamTable();
    })

    $("#respdiv").click(function () {
        $("#reqdiv").removeClass();
        $("#definediv").removeClass();
        $("#sampledatadiv").removeClass();
        $(this).addClass("active");
        $("#id-paraminfotable").css("display", "block");
        $("#id-definedparamtable").css("display", "none");
        $("#id-sampledata").css("display", "none");
        $("#type").val("1");
        initParamTable();
    })

    $("#definediv").click(function () {
        $("#respdiv").removeClass();
        $("#reqdiv").removeClass();
        $("#sampledatadiv").removeClass();
        $(this).addClass("active");

        $("#id-paraminfotable").css("display", "none");
        $("#id-definedparamtable").css("display", "block");
        $("#id-sampledata").css("display", "none");
        $("#xmlstr").val("");
    })

    $("#sampledatadiv").click(function () {
        $("#respdiv").removeClass();
        $("#reqdiv").removeClass();
        $("#definediv").removeClass();
        $(this).addClass("active");
        $("#id-paraminfotable").css("display", "none");
        $("#id-definedparamtable").css("display", "none");
        showsampledata();
        $("#id-sampledata").css("display", "block");
    })

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

        var inputs = $(".param-input");
        inputs.val("");
    })

    $("#example-xmlstr").val(str);
    $("#addXmlParamBtn").on("click", addXmlParam);
    /*	$("#addSampleDatBtn").click(function(){
     $("#sampledataModel").modal();
     });*/

    $("#addSampleDataBtn").bind("click", addSampleData);

});


function getSelected() {
    var row = manager.getSelectedRow();
    if (!row) {
        $("#parmParentId").val("");
    } else {
        $("#parmParentId").val(row.id);
    }
}


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
                    if (item.reqtype.toLowerCase() == "Object".toLowerCase() || item.reqtype.toLowerCase() == "List".toLowerCase()) {
                        s =
                            "<a href='javascript:void(0)'  onclick='insertParams(\"" + item.id + "\")'>新增</a>" + "-"
                            + s;
                    }
                    return s;
                }
            },
            {
                display: '排序', width: "10%", align: 'center',
                render: function (item) {
                    var s =
                        "<a href='javascript:void(0)' onclick='upsort(\"" + item.id + "\")'><img src='../resources/img/process/up.png'></a>" + " "
                        + "<a href='javascript:void(0)' onclick='downsort(\"" + item.id + "\")'><img src='../resources/img/process/down.png'></a>";
                    return s;
                }
            },
        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '100%',
        data: getParams(),
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: true,
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId',
            isParent: function (data) {
                //设置表格之前箭头
                if (data.reqtype.toLowerCase() == "Object".toLowerCase() || data.reqtype.toLowerCase() == "List".toLowerCase())
                    return true;
                return false;
            }
        },
        onSelectRow: function (rowdata, rowindex) {
            //获取当前行的编码id
            //	preinputText = rowdata.ecode;
            //alert("123");
        },
        usePager: false

    })
}

function getParams() {
    var objdata;
    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    jsonObj.layout = false;
    jsonStr = JSON.stringify(jsonObj);
    $.ajax({
        "url": webpath + "/reqparam/selectParams",
        "type": 'POST',
        data: {
            "jsonStr": jsonStr
        },
        async: false,
        success: function (data) {
            objdata = data;
        }
    })
    return objdata;
}

//参数排序——向上/向下移动
function upsort(id) {
    $.ajax({
        url: webpath + "/reqparam/getParamById",
        type: 'POST',
        data: {
            "id": id,
        },
        success: function (data) {
            $.ajax({
                url: webpath + "/reqparam/upsort",
                type: 'POST',
                data: {
                    "sort": data.sort,
                    "pubid": data.pubid,
                    "id": id,
                    "parentId": data.parentId,
                },
                success: function (data) {
                    if (data == 1) {
                        layer.msg('<p>无法向上！</p><p>请检查是否为同层级或已是最上！</p>', {offset: '150px', time: 2000, icon: 2});
                    } else {
                        initParamTable();
                    }
                }
            });
        }
    });
}

function downsort(id) {
    $.ajax({
        url: webpath + "/reqparam/getParamById",
        type: 'POST',
        data: {
            "id": id,
        },
        success: function (data) {
            $.ajax({
                url: webpath + "/reqparam/downSort",
                type: 'POST',
                data: {
                    "sort": data.sort,
                    "pubid": data.pubid,
                    "id": id,
                    "parentId": data.parentId,
                },
                success: function (data) {
                    if (data == 2) {
                        layer.msg('<p>无法向下！</p><p>请检查是否为同层级或已是最上！</p>', {offset: '150px', time: 2000, icon: 2});
                    } else {
                        initParamTable();
                    }
                }
            });
        }
    });
}

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
function deleteParams(id) {
    layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是', '否'],//按钮
        offset: '150px'
    }, function (index, layero) {
        $.ajax({
            "url": webpath + "/reqparam/delete",
            "type": "POST",
            dataType: "json",
            async: false,
            data: {
                "id": id
            },
            success: function (data) {
                if (data) {
                    layer.msg('删除成功！', {offset: '150px', time: 1000, icon: 1});
                    layer.close(index);
                    initParamTable();
                } else if (!data) {
                    layer.msg('请先删除子级参数', {offset: '150px', time: 2000, icon: 2});
                }
            }

        })
    })
}


function updateParams(id) {
    $("#addParamSetBtn").text("修改");
    $("#paramadd").modal();
    $("#addparam").hide();
    $("#paramadd-update").val("1");

    $.ajax({
        "url": webpath + "/reqparam/getParamById",
        "type": "POST",
        dataType: "json",
        async: false,
        data: {
            "id": id
        },
        success: function (data) {
            $("#paramid-set").val(data.id);
            $("#ecode").val(data.ecode);
            $("#reqdesc").val(data.reqdesc);
            $("#reqtype").val(data.reqtype);
            $("#parampos").val(data.parampos);
            if (data.isempty == "1") {
                $("#isempty").val("是");
            } else if (data.isempty == "0") {
                $("#isempty").val("否");
            }

        }

    })
}


function addXmlParam() {
    $.ajax({
        "url": webpath + "/reqparam/xmlinsert",
        "type": "POST",
        dataType: "json",
        async: false,
        data: {
            "pubid": parent.paramifream_pubid,
            "xmlStr": $("#xmlstr").val()
        },
        success: function (data) {
            layer.msg('添加成功', {offset: '150px', time: 3000, icon: 1});
            $("#definediv").removeClass();
            $(this).addClass("active");
            $("#id-paraminfotable").css("display", "block");
            $("#id-definedparamtable").css("display", "none");
            $("#type").val("0");
            initParamTable();
        }
    })
}

function showsampledata() {
    $.ajax({
        "url": webpath + "/publisher/getPubById",
        "type": "POST",
        async: false,
        data: {
            "id": parent.paramifream_pubid
        },
        success: function (data) {
            if (data.sampledata != "") {
                $("#sampledata").val(data.sampledata);
                $("#addSampleDataBtn").text("修改");
            }
        }
    })
}

function addSampleData() {
    $.ajax({
        "url": webpath + "/publisher/update",
        "type": "POST",
        async: false,
        data: {
            "pubid": parent.paramifream_pubid,
            "sampledata": $("#sampledata").val()
        },
        success: function (data) {
            layer.msg('添加成功', {time: 3000, icon: 1});

            $("#respdiv").removeClass();
            $("#definediv").removeClass();
            $("#sampledatadiv").removeClass();
            $("#reqdiv").addClass("active");
            $("#id-paraminfotable").css("display", "block");
            $("#id-definedparamtable").css("display", "none");
            $("#id-sampledata").css("display", "none");
            $("#type").val("0");
            initParamTable();
        }
    })
}

function mouseOver() {
    layer.tips('如果有请求参数请提供测试数据，保证申请时可在线测试该接口', '#id-icontipdata', {
        tips: [1, '#EEC591'] //可配置颜色
    });
}


function reloadTableData(isCurrentPage) {
    $("#reqparamtable").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}

str =
    "<params>                                             " +
    "  <type>request</type>                               " +
    "  <allparams>                                        " +
    "    <param>                                          " +
    "      <ecode>content</ecode>                         " +
    "      <reqdesc>内容</reqdesc>                        " +
    "      <reqtype>Object</reqtype>                      " +
    "      <isempty>0</isempty>                           " +
    "      <subparam>                                     " +
    "        <listParam>                                  " +
    "          <param>                                    " +
    "            <ecode>contactCode</ecode>               " +
    "            <reqdesc>接触点号</reqdesc>              " +
    "            <reqtype>Object</reqtype>                " +
    "            <isempty>0</isempty>                     " +
    "            <subparam>                               " +
    "              <listParam>                            " +
    "                <param>                              " +
    "                  <ecode>channelId</ecode>           " +
    "                  <reqdesc>渠道id1111</reqdesc>      " +
    "                  <reqtype>String</reqtype>          " +
    "                  <isempty>0</isempty>               " +
    "                </param>                             " +
    "                <param>                              " +
    "                  <ecode>channelId222</ecode>        " +
    "                  <reqdesc>渠道id</reqdesc>          " +
    "                  <reqtype>String</reqtype>          " +
    "                  <isempty>0</isempty>               " +
    "                </param>                             " +
    "              </listParam>                           " +
    "            </subparam>                              " +
    "          </param>                                   " +
    "        </listParam>                                 " +
    "      </subparam>                                    " +
    "    </param>                                         " +
    "    <param>                                          " +
    "      <ecode>message</ecode>                         " +
    "      <reqdesc>返回信息</reqdesc>                    " +
    "      <reqtype>String</reqtype>                      " +
    "      <isempty>0</isempty>                           " +
    "    </param>                                         " +
    "  </allparams>                                       " +
    "</params>                                            ";
                                                      
