$(document).ready(function () {
    /**
     * 构建任务
     * */
    getBuildTasksTable();

    $(".btn-refresh").click(function(e){
        $('#tasksSearch').val('');
        $('#buildTasksTable').bootstrapTable('refresh');
    });
    $(".glyphicon-search").click(function(){
        $('#buildTasksTable').bootstrapTable('refresh');
    });
    $('#tasksSearch').keydown(function(e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $('#buildTasksTable').bootstrapTable('refresh');
        }
    });

    /**
     * 代码托管
     * */
    $('#codeTrusList .trus-item-bottom li').on('click','.unbind-icon',function (e) {
        /*$(this).parent().css({
            'background':'#33cc66',
            'color':'#ffffff'
        });*/
    });


});

/**
 * 构建任务
 * */
function getBuildTasksTable() {
    $("#buildTasksTable").bootstrapTable({
        url: $webpath + "/bcm/v1/ci",
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        classes: 'table-no-bordered',
        cache: false,
        striped: false,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        paginationDetailHAlign: 'left',
        showJumpto: true,
        paginationPreText: '<i class="fa fa-angle-left bconsole-left-arrow" aria-hidden="true"></i>',
        paginationNextText: '<i class="fa fa-angle-right bconsole-left-arrow" aria-hidden="true"></i>',
        formatRecordsPerPage: function (pageNumber) {
            // return '每页显示 ' + pageNumber + ' 条记录';
            return '' + pageNumber + '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            // return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
            return '共' + totalRows + '条';
        },
        queryParams: function (params) {
            return {
                "ciName": $('#tasksSearch').val(),
                "ciType": 1,
                "page": (params.offset / params.limit),
                "size": params.limit,
                "projectId": ""
            }
        },
        queryParamsType: 'limit',
        responseHandler: function (res) {
            return {
                "total": res.data ? res.data.totalPages : 0,
                "rows": res.data ? res.data.content : []
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "",
        columns: [{
            field: 'ciName',
            title: '构建任务',
            align: 'left',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<a class="inner-ahref" href="'+ $webpath +'/bcm/v1/ci/?ciId='+ row.ci.id +'">'+ row.ci.ciName +'</a>';
            }
        }, {
            field: 'codeControlType',
            title: '代码来源',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                console.log(row,'row');
                return '';
            }
        }, {
            field: '',
            title: '代码库',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'imageName',
            title: '目标镜像',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if(row.ci.constructionStatus === 3) {
                    return '<a class="inner-ahref" href="'+ $webpath +'/bcm/v1/image/individual/detailPage?imageName='+ row.ci.imageName +'">'+ row.ci.imageName +'</a>';
                }else{
                    return row.ci.imageName;
                }
            }
        }, {
            field: 'constructionStatus',
            title: '状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var constructionStatus = Number( row.ci.constructionStatus );
                switch ( constructionStatus ) {
                    case 1 :
                        return '<span class="status-gray">未构建</span>';
                    case 2 :
                        return '<span class="status-yellow">构建中</span>';
                    case 3 :
                        return '<span class="status-green">构建完成</span>';
                    case 4 :
                        return '<span class="status-red">构建失败</span>';
                    default :
                        return '-';
                }
            }
        }, {
            field: 'constructionTime',
            title: '最新构建时间',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return row.ci.constructionTime;
            }
        },{
            field: '',
            title: '操作',
            align: 'right',
            formatter: function (value, row, index) {
                var ciId = row.ci.id;
                var html = '<button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="toStartTask(\'' + ciId + '\')">构建</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="toEditPage(\'' + ciId + '\')">配置</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="toStopTask(\'' + ciId + '\')">禁用</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" onclick="openDeleteConfirm(\'' + ciId + '\')">删除</button>';
                return html;
            }
        }]
    })
}

// 构建(启动构建)
function toStartTask(ciId) {
    $.ajax({
        url: $webpath + "/bcm/v1/ci/" + ciId,
        type: 'PUT',
        dataType:"json",
        data:JSON.stringify({
            operator: 'start'
        }),
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $.message(req.message);
                $('#buildTasksTable').bootstrapTable('refresh');
            }else {
                $.message({
                    message:req.message,
                    type:'error'
                });
            }
        }
    });
}

// 配置
function toEditPage(ciId) {
    window.location.href = $webpath + "/bcm/v1/ci/?ciId=" + ciId;
}

// 禁用
function toStopTask(ciId) {
    $.ajax({
        url: $webpath + "/bcm/v1/ci/" + ciId,
        type: 'PUT',
        dataType:"json",
        data:JSON.stringify({
            operator: 'stop'
        }),
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $.message(req.message);
                $('#buildTasksTable').bootstrapTable('refresh');
            }else {
                $.message({
                    message:req.message,
                    type:'error'
                });
            }
        }
    });
}

// 打开删除确认框
function openDeleteConfirm(ciId) {
    $("input[name='deleteId']").val(ciId);
    $('#deleteConfirmModal').modal('show');
}

// 删除构建
function deleteDockerTask() {
    var ciId = $("input[name='deleteId']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/ci/" + ciId,
        type: 'DELETE',
        dataType:"json",
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $.message(req.message);
                $('#buildTasksTable').bootstrapTable('refresh');
            }else {
                $.message({
                    message:req.message,
                    type:'error'
                });
            }
        }
    });
    $('#deleteConfirmModal').modal('hide');
}