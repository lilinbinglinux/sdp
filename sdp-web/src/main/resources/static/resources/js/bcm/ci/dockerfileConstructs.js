var dockerfile = {
    dockerInterval: ''
};
$(document).ready(function () {
    getDockerfileTable();

    $(".btn-refresh").click(function(e){
        $('#dokerSearch').val('');
        $('#dockerfileTable').bootstrapTable('refresh');
    });
    $(".glyphicon-search").click(function(){
        $('#dockerfileTable').bootstrapTable('refresh');
    });
    $('#dokerSearch').keydown(function(e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $('#dockerfileTable').bootstrapTable('refresh');
        }
    });
});

function getDockerfileTable() {
    $("#dockerfileTable").bootstrapTable({
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
                "ciName": $('#dokerSearch').val(),
                "ciType": 2,
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
            title: '名称',
            align: 'left',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<a class="inner-ahref" href="'+ $webpath +'/bcm/v1/ci/dockerfileTask?ciId='+ row.ci.id +'">'+ row.ci.ciName +'</a>';
            }
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
            field: 'createTime',
            title: '构建时间',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return row.ci.createTime;
            }
        },{
            field: '',
            title: '操作',
            align: 'right',
            formatter: function (value, row, index) {
                var ciId = row.ci.id;
                var html = '<button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="toStartTask(\'' + ciId + '\')">构建</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="toEditPage(\'' + ciId + '\')">配置</button>\n' +
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
                $('#dockerfileTable').bootstrapTable('refresh');

                dockerfile.dockerInterval = setInterval(function () {
                    var constructionStatus = getDokerDetail(ciId);
                    if(constructionStatus !== 2) {
                        console.log(2);
                        $('#dockerfileTable').bootstrapTable('refresh');
                        clearInterval(dockerfile.dockerInterval);
                    }
                },30000);
            }else {
                $.message({
                    message:req.message,
                    type:'error'
                });
            }
        }
    });
}

// 获取构建任务详情
function getDokerDetail(ciId) {
    var constructionStatus = '';
    $.ajax({
        url: $webpath + "/bcm/v1/ci/" + ciId,
        type: 'GET',
        dataType:"json",
        async: false,
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                constructionStatus = req.data.ci.constructionStatus;
            }
        }
    });
    return constructionStatus;
}

// 配置
function toEditPage(ciId) {
    window.location.href = $webpath + "/bcm/v1/ci/editDockerfile?ciId=" + ciId;
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
                $('#dockerfileTable').bootstrapTable('refresh');
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