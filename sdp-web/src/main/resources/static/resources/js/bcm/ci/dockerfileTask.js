getQueryString();

var constructsTask = {
    basicInterval: ''
};

$(document).ready(function () {
    getConstructsHistoryTable();
    basicInfo();
    constructsTask.basicInterval = setInterval(function(){
        basicInfo();
    }, 30000);
    
    // 删除构建
    $('#deleteConfirmModal .confirm-box-ok').on('click',function () {
        $.ajax({
            url: $webpath + "/bcm/v1/ci/" + window.ciId,
            type: 'DELETE',
            dataType:"json",
            contentType: "application/json",
            success: function (req) {
                if(req.code == 200) {
                    $.message(req.message);
                    setTimeout(function () {
                        window.location = $webpath + '/bcm/v1/ci/dockerfileConstructs';
                    },2000);
                }else {
                    $.message({
                        message:req.message,
                        type:'error'
                    });
                }
            }
        });
        $('#deleteConfirmModal').modal('hide');
    });

    // 步骤条
    /*$('.image-steps').on('click','.image-step-icon',function () {
        if($(this).data('index') == 1) {
            $(this).parent().parent().addClass('image-step-active');
            $(this).parent().parent().next().removeClass('image-step-active');
            $(this).next().removeClass('green-line');
        }else if($(this).data('index') == 2) {
            $(this).parent().parent().addClass('image-step-active');
            $(this).parent().parent().prev().find('.image-step-line').addClass('green-line');
        }
    });*/

    // 日志全屏
    $('#logWindow #fullScreen').on('click',function () {
        $('#logWindow').toggleClass('all');
        var title = $(this).attr('title');
        if (title == '全屏') {
            $(this).attr('title', '退出全屏');
            $(this).attr('src', $webpath + "/resources/img/bcm/image/full_screen.png");
        } else {
            $(this).attr('title', '全屏');
            $(this).attr('src', $webpath + "/resources/img/bcm/image/full_screen.png");
        }
    })
});

// 开始构建(启动构建)
function toStartTask() {
    $.ajax({
        url: $webpath + "/bcm/v1/ci/" + window.ciId,
        type: 'PUT',
        dataType:"json",
        data:JSON.stringify({
            operator: 'start'
        }),
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $.message(req.message);
                $('#logWindow .log-container .print-log-span').html('');
                basicInfo();
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
function toEditPage() {
    window.location.href = $webpath + "/bcm/v1/ci/editDockerfile?ciId=" + window.ciId;
}

// 基本信息
function basicInfo() {
    $.ajax({
        url: $webpath + "/bcm/v1/ci/"+ window.ciId,
        type: 'GET',
        contentType: "application/json",
        dataType:"json",
        success: function (res) {
            if(res.code == 200) {
                $('#constructsHistoryTable').bootstrapTable('refresh');

                var sum = Number(res.data.constructionOkTotal) + Number(res.data.constructionFailTotal);
                var str = '<li>任务名称：<span>'+res.data.ci.ciName+'</span></li>\n' +
                    '      <li>总构建次数：<span>'+sum+'</span></li>\n' +
                    '      <li>总构建时长：<span>'+res.data.constructionDurationTotal+'</span></li>\n' +
                    '      <li>构建成功次数：<span>'+res.data.constructionOkTotal+'</span></li>\n' +
                    '      <li>构建失败次数：<span>'+res.data.constructionFailTotal+'</span></li>';
                $('.image-content-right .right-basic-info').html(str);

                var constStatus = res.data.ci.constructionStatus;
                var constResult = res.data.ciRecords.length !== 0 ? res.data.ciRecords[0].constructionResult : '';
                if(constStatus !== 1) { // 1:未构建

                    if(constResult === 3) { // 3:构建完成
                        $('.image-step-end').addClass('image-step-active');
                        $('.image-step-start .image-step-line').addClass('green-line');
                        clearInterval(constructsTask.basicInterval);
                        $('.start-task-btn').removeAttr('disabled');
                    }else if(constResult === 4) { // 4:构建失败
                        $('.image-step-end').removeClass('image-step-active');
                        $('.image-step-start .image-step-line').removeClass('green-line');
                        clearInterval(constructsTask.basicInterval);
                        $('.start-task-btn').removeAttr('disabled');
                    }else{
                        constructsTask.basicInterval = setInterval(function(){
                            basicInfo();
                        }, 30000);
                        $('.image-step-end').removeClass('image-step-active');
                        $('.image-step-start .image-step-line').removeClass('green-line');
                        $('.start-task-btn').attr('disabled',true);
                    }
                    $('#logWindow .log-container .print-log-span').html(res.data.ciRecords[0].logPrint);

                }else{
                    $('#logWindow .log-container .print-log-span').html('');
                    clearInterval(constructsTask.basicInterval);
                }
            }
        }
    });
}

// 构建历史列表
function getConstructsHistoryTable() {
    $("#constructsHistoryTable").bootstrapTable({
        url: $webpath + "/bcm/v1/ci/" + window.ciId +"/record",
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
                "page": (params.offset / params.limit),
                "size": params.limit
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
            field: 'id',
            title: '构建编码',
            align: 'left',
            valign: 'middle'
        }, {
            field: 'constructionTime',
            title: '构建时间',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'constructionResult',
            title: '状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var constructionResult = Number( row.constructionResult );
                switch ( constructionResult ) {
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
            field: 'constructionDuration',
            title: '构建时长',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'createdBy',
            title: '构建人',
            align: 'center',
            valign: 'middle'
        }, {
            field: '',
            title: '操作',
            align: 'right',
            formatter: function (value, row, index) {
                var id = row.id;
                var rowData = JSON.stringify(row);
                rowData = escape(rowData);
                var escapeflag = true;
                var html = '<button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="openLogModal(\'' + rowData + '\',\'' + escapeflag + '\')">日志</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="downloadLog(\'' + rowData + '\',\'' + escapeflag + '\')">下载日志</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" onclick="openDeleteConfirm(\'' + id + '\')">删除</button>';
                return html;
            }
        }]
    })

}

// 打开删除构建历史确认框
function openDeleteConfirm(id) {
    $("#deleteHistoryConfirm input[name='deleteId']").val(id);
    $('#deleteHistoryConfirm').modal('show');
}

// 删除构建历史
function deleteHistoryTask() {
    var id = $("#deleteHistoryConfirm input[name='deleteId']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/ci/record/" + id,
        type: 'DELETE',
        dataType:"json",
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $.message(req.message);
                $('#constructsHistoryTable').bootstrapTable('refresh');
            }else {
                $.message({
                    message:req.message,
                    type:'error'
                });
            }
        }
    });
    $('#deleteHistoryConfirm').modal('hide');
}

// 打开构建历史日志modal
function openLogModal(data, escapeflag) {
    if (escapeflag) {
        data = unescape(data);
    }
    var item = $.parseJSON(data);
    $("#logHistoryModal pre>span").html(item.logPrint);
    $('#logHistoryModal').modal('show');
}