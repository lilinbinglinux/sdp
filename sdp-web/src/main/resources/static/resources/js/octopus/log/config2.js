$(document).ready(function() {
    var table = $('#configure2_table').dataTable({
        /*"aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0 ,3] }],*/
        /*fixedHeader: {
	        header: true
	    },*/
        "dom": '<<t>ilp>',
        "pagingType": "simple_numbers",
        //设置分页控件的模式
        "processing": true,
        "serverSide": false,
        "ordering": false,
        /* 分页设置 */
        "bPaginate": true,
        "bLengthChange": true,
        /* 搜索设置 */
        "bFilter": false,
        "bSort": false,
        /* 显示总条数 */
        "bInfo": true,
        "bAutoWidth": false,
        "language": {
            "paginate": {
                "previous": "首页",
                "next": "下一页"
            },
            "info": "显示_START_到_END_, 共计_TOTAL_条数据",
            "emptyTable": "无记录",
            "infoEmpty": "共计0",
            "lengthMenu": "每页显示 _MENU_ 记录",
            // "infoFiltered": "",
            // "zeroRecords": "没有找到相关内容",
            // "search": "搜索 : "
        },
        "ajax": {
            "url": ctx + "rest/api/department/app/relation/data"
        },
        //序号
        "fnDrawCallback": function() {
            this.api().column(0).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1;
            });
        },
        "columns": [{
            data: null,
            width: "5%"
        },
        {
            data: "app",
            width: "8%",
            render: function(data, type, row) {
                if (data == null || data == "") {
                    return "";
                }
                return data;
            }
        },
        {
            data: "departments",
            render: function(data, type, row) {
                if (data == null || data == "") {
                    return "";
                }
                return data;
            }
        },
        {
            data: null,
            render: function(data, type, row) {
                return '<a class="chart_option_edit" id="editButton" onclick="editButton(\'' + row.app + '\',\'' + row.departments + '\')" >编辑</a><a class="chart_option_delete" data-toggle="modal" onclick="deleteButton(\'' + row.app + '\')" >删除</a>';
            }
        }]
    })
});
function addSubmit() {
    $.ajax({
        url: ctx + "rest/api/department/bind",
        type: "POST",
        data: $("#addForm").serialize(),
        success: function(data) {
            data = eval("(" + data + ")");
            if (data.status == "200") {
                layer.alert("保存成功");
                setTimeout(function() {
                    window.location.reload();
                },
                500);
            } else if (data.status == "500") {
                layer.alert("保存失败");
                setTimeout(function() {
                    window.location.reload();
                },
                1000);
            }
        }
    });
}
//编辑
function editButton(app, departments) {
    $("#editForm input[name='departments']").prop("checked", false);
    var departments = departments.split(',');
    $("#editForm input[id='app']").val(app);
    $("#editForm input[name='departments']").each(function() {
        for (var i = 0; i < departments.length; i++) {
            if (departments[i] == $(this).attr("id")) {
                $(this).prop("checked", true);
            }
        }
    });
    $("#config_update_Modal").modal('show');
}
//编辑提交
function editSubmit(app, departments) {
    $.ajax({
        url: ctx + "rest/api/department/update",
        type: "POST",
        data: $("#editForm").serialize(),
        success: function(data) {
            data = eval("(" + data + ")");
            if (data.status == "200") {
                layer.alert("修改成功");
                setTimeout(function() {
                    window.location.reload();
                },
                500);
            } else if (data.status == "500") {
                layer.alert("修改失败");
                setTimeout(function() {
                    window.location.reload();
                },
                500);
            }
        }
    });
}

//删除按钮
function deleteButton(app) {
    layer.confirm('确定删除么？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
        $.ajax({
            url: ctx + "rest/api/department/unbind?app=" + app,
            type: "get",
            success: function(data) {
                data = eval("(" + data + ")");
                if (data.status == "200") {
                    layer.alert("删除成功");
                    setTimeout(function() {
                        window.location.reload();
                    },
                    500);
                } else if (data.status == "500") {
                    layer.alert("删除失败");
                    setTimeout(function() {
                        window.location.reload();
                    },
                    500);
                }
            }
        });
    },
    function() {
        layer.close();
    });
}