$(document).ready(function () {
    $("#addProBtn").unbind("click");
    $("#addProBtn").bind("click", addPro);

    $("#addProModelBtn").unbind("click");
    $("#addProModelBtn").bind("click", addPro);

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

});

//添加修改项目及模块方法
function addPro() {
    if ($("#modaltype").val() == "update") {   //修改方法
        var nodes = resource.treeObj.getSelectedNodes();
        var id, name, proecode, proversion, prodescribe, modaltype;
        if (nodes.length > 0) {//在树上面操作
            id = nodes[0].id;
            if (nodes[0].typeId == "0") {   //修改项目
                name = $("#proname").val();
                proecode = $("#proecode").val();
                proversion = $("#proversion").val();
                prodescribe = $("#prodescribe").val();
            } else if (nodes[0].typeId == "1") {  //修改模块文件夹
                name = $("#promname").val();
                proecode = $("#promecode").val();
            }
        } else {  //在右侧面板中操作
            if ($("#id-proinfoupdate").val() != "") {//修改项目
                id = $("#id-proinfoupdate").val();
                name = $("#proname").val();
                proecode = $("#proecode").val();
                proversion = $("#proversion").val();
                prodescribe = $("#prodescribe").val();
            } else if ($("#id-proModelinfoupdate").val() != "") {//修改模块文件夹
                id = $("#id-proModelinfoupdate").val();
                name = $("#promname").val();
                proecode = $("#promecode").val();
            }
        }
        $.ajax({
            "url": webpath + "/pro/update",
            "type": "POST",
            dataType: "json",
            async: false,
            data: {
                proid: id,
                name: name,
                proecode: proecode,
                proversion: proversion,
                prodescribe: prodescribe
            },
            success: function () {
                resource.initTree();
            }
        })
    } else { //添加方法
        var nodes = resource.treeObj.getSelectedNodes();
        if (nodes.length > 0) {//在树上面操作
            if (typeof(nodes[0]) == "undefined" || nodes[0].id == "ROOT") {  //添加项目
                $.ajax({
                    "url": webpath + "/pro/insert",
                    "type": "POST",
                    data: {
                        name: $("#proname").val(),
                        proecode: $("#proecode").val(),
                        proversion: $("#proversion").val(),
                        prodescribe: $("#prodescribe").val(),
                        parentId: "ROOT",
                        typeId: "0"
                    },
                    success: function () {
                        if (nodes[0].typeId == "root") {
                            $('#panel-childPage').attr('src', "../pubIfream/proIframe");
                            $("#id-proinfotable").dataTable().fnDraw(false);
                        }
                        resource.initTree();
                    }
                })
            } else { //添加模块文件夹
                $.ajax({
                    "url": webpath + "/pro/insert",
                    "type": "POST",
                    dataType: "json",
                    data: {
                        name: $("#promname").val(),
                        proecode: $("#promecode").val(),
                        parentId: nodes[0].id,
                        typeId: "1"
                    },
                    success: function () {
                        if (nodes[0].typeId == "0") {
                            $('#panel-childPage').attr('src', "../pubIfream/promodelIframe");
                            $("#id-proinfotable").dataTable().fnDraw(false);
                            setTimeout(function () {
                                $("#panel-childPage").contents().find("#promodelparentId").val(nodes[0].id);
                            }, 300);
                        }
                        resource.initTree();
                    }
                })
            }
        } else {//在右侧面板中操作
            var type;
            var parentId = $('#promodelparentId').val();
            if (parentId == null) {    //项目新增
                name = $("#proname").val();
                proecode = $("#proecode").val();
                proversion = $("#proversion").val();
                prodescribe = $("#prodescribe").val();
                type = '0';
                parentId = "ROOT";
            } else { //新增模块文件夹
                name = $("#promname").val();
                proecode = $("#promecode").val();
                type = '1';
            }
            $.ajax({
                "url": webpath + "/pro/insert",
                "type": "POST",
                dataType: "json",
                data: {
                    name: name,
                    proecode: proecode,
                    proversion: proversion,
                    prodescribe: prodescribe,
                    typeId: type,
                    parentId: parentId,

                },
                success: function () {
                    $("#id-proinfotable").dataTable().fnDraw(false);
                    parent.resource.initTree();
                }
            })
        }
    }
}

//刷新数据,根据传入的table刷新对应table的数据
function reloadTableData(table) {
    $("#id-proinfotable").dataTable().fnDraw(true);
    $("#id-publishMineTable").dataTable().fnDraw(true);
}
