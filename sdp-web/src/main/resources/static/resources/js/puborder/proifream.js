$(document).ready(function () {
    parent.protableinfo = $("#id-proinfotable");
    initProinfoTable();
    $("#id-addproinfo").on("click", function () {
        $("#modaltype").val("");
        $("#proname").val("");
        $("#proecode").val("");
        $("#proversion").val("");
        $("#prodescribe").val("");
        $('#proModal').modal();
    });

});

//初始化用户列表表格
function initProinfoTable() {
    $("#id-proinfotable").width("100%").height("auto").dataTable({
        columns: [
            {"data": "proid"},
            {"data": "name"},
            {"data": "proecode"},
            {"data": "proversion"},
            {"data": "prodescribe"},
            {"data": "createdate"},
            {"data": ""}
        ],
        ajax: {
            url: webpath + '/pro/selectPage',
            "type": 'POST',
            "data": function (d) {//查询参数
                return $.extend({}, d, {
                    "jsonStr": form.serializeStr($("#id-prointype"))
                });
            },
            "dataSrc": function (json) {
                json.iTotalRecords = json.total;
                json.iTotalDisplayRecords = json.total;
                return json.data;
            }
        },
        columnDefs: [
            {
                "targets": 0,
                "data": null,
                "visible": false
            },
            {
                "targets": 1,
                "data": null,
                "visible": true
            },
            {
                "targets": 2,//操作按钮目标列
                "data": null

            },
            {
                "targets": 3,//操作按钮目标列
                "data": null,
                "visible": true,
            },
            {
                "targets": 4,//操作按钮目标列
                "data": null
            },
            {
                "targets": 5,//操作按钮目标列
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    var newDate = new Date();
                    newDate.setTime(row.createdate);
                    if (data == null || data == "") {
                        html += '暂无时间';
                    } else {
                        html += newDate.toLocaleString();
                    }
                    return html;
                }
            },
            {
                "targets": 6,//操作按钮目标列
                "data": null,
                "render": function (data, type, row) {
                    var id = row.proid;
                    var html = '<a href="javascript:void(0);" onclick="updatePro(\'' + id + '\')" class="icon-wrap" title="编辑"><i class="iconfont i-btn" data-toggle="modal" data-target="#proModal">&#xe66f;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="deletePro(\'' + id + '\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
                    html += '&nbsp;&nbsp;';
                    return html;
                }

            }
        ]
    });
}

function updatePro(proid) {
    $.ajax({
        "url": webpath + "/pro/getProById",
        "type": "POST",
        dataType: "json",
        data: {
            "id": proid
        },
        async: false,
        success: function (data) {
            $("#modaltype").attr("value", "update");
            $("#id-proinfoupdate").attr("value", data.proid);
            $("#proname").val(data.name);
            $("#proecode").val(data.proecode);
            $("#proversion").val(data.proversion);
            $("#prodescribe").val(data.prodescribe);
        }
    });
}

function deletePro(proid) {
    layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是', '否'] //按钮
    }, function (index, layero) {
        $.ajax({
            "url": webpath + "/interfacePo/delete",
            "type": "POST",
            data: {
                "id": proid,
                "typeId": "0"
            },
            success: function (data) {
                if(data=='deletefalse'){
                    layer.msg('请删除子集！', {time: 1000, icon: 1});
                }else{
                    layer.msg('删除成功！', {time: 1000, icon: 1});
                }

                layer.close(index);
                //刷新项目信息表
                reloadTableData($("#id-proinfotable"));
                var res = parent.resource;
                res.initTree();
            }
        });
    })
}
