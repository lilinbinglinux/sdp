/**
 * Created by niu on 2018/1/9.
 */
var type,serApplicationTable;

$(document).ready(function () {
    initSerApplicationTable();
    $("#id-searchBtn").bind("click", reloadTableData2);
    $("#id-resetBtn").bind("click", resetForm);
    $("#id-addBtn").bind("click", insertSerApplication);

    // 为datatable外的父级设置高度
    $('#id-serApplicationTable_wrapper').css('height', $('.panel-body').height() - 60);
    // 动态为表格添加父级
    $('#id-serApplicationTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').css('height', $('#id-serApplicationTable_wrapper').height() - 63);
    $('.tab-wrapper').niceScroll({cursorcolor: "#ccc", horizrailenabled: false});

});

//使用datatable初始化用户表格
function initSerApplicationTable() {
    serApplicationTable =  $("#id-serApplicationTable").width("100%").dataTable({
        "columns": [
            {"data": "applicationId"},
            {"data": "applicationName"},
            {"data": "createDateString"},
            {"data": "delFlag"},
            {"data": "tenantId"},
        ],
        ajax: {
            url: webpath + '/serApplication/selectPage',
            "type": 'POST',
            "data": function (d) {
                return $.extend({}, d, {
                    "jsonStr": form.serializeStr($("#id-mineSearchForm"))
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
                "targets": 0,//ID
                "data": null,
                "visible": false
            },
            {
                "targets": 1,//应用名称
                "data": null
            },
            {
                "targets": 2,//创建时间
                "data": null
            },
            {
                "targets": 3,//当前状态
                "data": null,
                "render": function (data, type, row) {
                    var html;
                    if (data == "0") {
                        html = "<span style='color:green'>正常</span>";
                    } else {
                        html = "<span style='color:red'>停用</span>"
                    }
                    return html;
                }
            },
            {
                "targets": 4,//租户ID
                "data": null
            },
            {
                "targets": 5,//操作按钮目标列
                "data": null,
                "render": function (data, type, row) {
                    var html = '<a href="javascript:void(0);" onclick="updateSerApplication(\'' + row.applicationId + '\',\'' + row.applicationName + '\',\'' + row.delFlag + '\')" class="icon-wrap" title="取消订阅"><i class="iconfont i-btn">&#xe66f;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="deleteOrderInter(\'' + row.applicationId + '\')" class="icon-wrap" title="取消订阅"><i class="iconfont i-btn">&#xe614;</i></a>';
                    return html;
                }
            }
        ],
    });
}


//暂不使用  刷新数据  true  整个刷新 false 保留当前页刷新   暂不使用
function reloadTableData(isCurrentPage) {
    $("#id-updateOrderInter").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}
//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData2(isCurrentPage) {
    $("#id-serApplicationTable").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}



//重置查询条件
function resetForm() {
    form.clear($("#id-mineSearchForm"));
}

//新增应用
function insertSerApplication() {
    layer.open({
        type: 1,
        title:'新增应用',
        area: ['500px', '200px'], //宽高
        content: $("#id-insertOrderInter"),
        btn: ['保存', '取消'],
        yes: function (index, layero) {
            $.ajax({
                "url": webpath + "/serApplication/insert",
                "type": "POST",
                data: {
                    applicationName: $("#id-insertSerAppplication-name").val(),
                    delFlag: $('#id-insertSerAppplication-select').val()
                },
                success: function (data) {
                    layer.close(index);
                    reloadTableData2();
                }
            });
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

//修改订阅信息
function updateSerApplication(aId, aName, aStatus) {
    $("#id-updateSerAppplication-name").val(aName);
    $("#id-updateSerAppplication-select").val(aStatus);
    layer.open({
        type: 1,
        title:'修改应用',
        area: ['310px', '230px'],
        content: $("#id-updateOrderInter"),
        btn: ['保存', '取消'],
        closeBtn: 1,
        success: function () {
            $('#modifyStatus').val(aStatus);
        },
        btn1: function (index, layero) {//确定按钮回调
            $.ajax({
                "url": webpath + "/serApplication/update",
                "type": "POST",
                data: {
                    applicationId: aId,
                    applicationName: $('#id-updateSerAppplication-name').val(),
                    delFlag: $('#id-updateSerAppplication-select').val()
                },
                success: function (data) {
                    //serApplicationTable.fnDraw();
                    reloadTableData2();
                    layer.close(index);
                }
            });
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

//取消订阅
function deleteOrderInter(id) {
    layer.confirm('是否删除应用？', {
        icon: 3,
        btn: ['是', '否'] //按钮
    }, function (index, layero) {
        $.ajax({
            url: webpath + "/serApplication/delete",
            type: 'POST',
            dataType: "json",
            data: {
                applicationId: id
            },
            success: function (data) {
                layer.close(index);
                //serApplicationTable.fnDraw();
                reloadTableData2();
            }
        })
    });
}