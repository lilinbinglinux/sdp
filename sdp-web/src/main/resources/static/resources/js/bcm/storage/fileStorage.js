$(document).ready(function () {
    formValidator('#addFileStorageForm');
    initFileStorageTable();

    /*存储大小选择滑动条*/
    $("#fileStoSlider").slider({
        orientation: "horizontal",
        range: "min",
        max: 500,//TODO 当前项目配额存储量
        value: 1,//界面默认100
        slide: function( event, ui ) {
            $( "#size" ).val( ui.value );
        }
    });
    $( "#size" ).val($("#fileStoSlider").slider("value"));
});

/*刷新页面*/
function reloadPage() {
    window.location.reload();
}

/*
* 表单输入验证
* */
function formValidator(id) {
    $(id).bootstrapValidator({
        live: 'enabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            storageFileName: {
                validators: {
                    notEmpty: {
                        message: '请输入存储名称'
                    },
                    stringLength: {
                        min: 5,
                        max: 15,
                        message: '存储卷名称不能小于5位或超过15位'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z][a-zA-Z0-9_]+$/,
                        message: '以字母、数字、下划线组合命名，且以字母开头'
                    }
                }
            }
            // storageFileSize: {
            //     validators: {
            //         notEmpty: {
            //             message: '请选择大小'
            //         },
            //         regexp: {
            //             regexp: /^[0-9]+$/,
            //             message: '请输入数字'
            //         }
            //     }
            // }
        }
    });
}


/*
* 新建共享存储
* */
function saveFileStorage() {
    $('#addFileStorageForm').data('bootstrapValidator').validate();
    if ($('#addFileStorageForm').data('bootstrapValidator').isValid()) {
        var fileSto = formatFormData('#addFileStorageForm');
        fileSto.storageFileSize = fileSto.storageFileSize*1024;
        $.ajax({
            url: webpath + "/bcm/v1/storage/file",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(fileSto),
            success: function (result) {
                if (result.code === 200) {
                    $.message(result.message);
                    $('#addFileStorage').modal('hide');
                    handleResetForm('#addFileStorageForm');
                    $('#fileStorageTable').bootstrapTable('refresh');
                } else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
            }
        });
    }
}

/*
* 共享存储列表
* */
function initFileStorageTable() {
    $("#fileStorageTable").bootstrapTable('destroy');
    $("#fileStorageTable").bootstrapTable({
        url: webpath+'/bcm/v1/storage/file',
        method: 'GET',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        formatNoMatches: formatNoMatch,
        formatLoadingMessage: formatLoading,
        classes: 'table-no-bordered',
        cache: false,
        pagination: true,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        showJumpto: true,
        paginationPreText: '<i class="fa fa-angle-left bconsole-left-arrow" aria-hidden="true"></i>',
        paginationNextText: '<i class="fa fa-angle-right bconsole-left-arrow" aria-hidden="true"></i>',
        formatRecordsPerPage: function (pageNumber) {
            return '' + pageNumber + '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            return '共' + totalRows + '条';
        },
        queryParams: function (params) {
            return {
                "page": (params.offset / params.limit),
                "size": params.limit,
                "storageFileName": $('#searchFileStorage').val()
            }
        },
        queryParamsType:'limit',
        responseHandler : function (res) {
            var total = 0;
            var content = [];
            if(res.code === 200) {
                if(res.data.totalElements && res.data.content){
                    total = res.data.totalElements;
                    content = res.data.content;
                }
            }else {
                $.message({
                    message: res.message,
                    type: 'error'
                });
            }
            return {
                "total": total,
                "rows": content
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        columns: cols
    });
}

var cols = [
    {
        field: 'index',
        title: '序号',
        align: 'left',
        valign: 'middle',
        formatter: function (value, row, index) {
            return index + 1;
        }
    },{
        field: 'index',
        title: '类型',
        align: 'center',
        valign: 'middle',
        formatter: function () {
            return '共享存储';
        }
    }, {
        field: 'name',
        title: '名称',
        align: 'center',
        valign: 'middle'
    }, {
        field: 'used',
        title: '已使用容量(G)',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            return row.used/1024;
        }
    },{
        field: 'size',
        title: '总容量(G)',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            return row.size/1024;
        }
    },{
        field: 'description',
        title: '描述',
        width: '250px',
        align: 'left',
        valign: 'middle'
    }, {
        field: 'tenantName',
        title: '创建人',
        align: 'center',
        valign: 'middle'
    }, {
        field: 'createTime',
        title: '创建时间',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            return new Date(row.createTime).toLocaleDateString().replace(/\//g,"-");
        }
    }, {
        field: 'services',
        title: '挂载服务',
        align: 'center',
        valign: 'middle'
    },{
        field: 'operation',
        title: '操作',
        align: 'right',
        formatter:function(value,row,index) {
            var html = '<span class="icon-hover" onclick="deleteFileStorage(\'' + row.id + '\')">删除</span>'
                        + '<span class="icon-hover" onclick="formatFileStorage(\'' + row.id + '\')">格式化</span>';
                        // + '<span class="icon-hover" onclick="getSingleFileStorage(\'' + row.id + '\')" style="cursor: pointer">编辑</span>';
            return html;
        }
    }
];

/*
* 按照存储名称查询，判断查询框的输入
* */
var cpLock = false;
/*compositionstart 事件触发于一段文字的输入之前（类似于 keydown 事件，但是该事件仅在若干可见字符的输入之前，而这些可见字符的输入可能需要一连串的键盘操作、语音识别或者点击输入法的备选词）。*/
$('#searchFileStorage').on('compositionstart', function () {
    cpLock = true;
});
/*当文本段落的组成完成或取消时, compositionend 事件将被触发 (具有特殊字符的触发, 需要一系列键和其他输入, 如语音识别或移动中的字词建议)。*/
$('#searchFileStorage').on('compositionend', function () {
    cpLock = false;
    initFileStorageTable();
});
$('#searchFileStorage').on('input', function () {
    if (!cpLock) {
        initFileStorageTable();
    }
});

/*删除一个共享存储*/
function deleteFileStorage(storageFileId) {
    $('#deleteFileStorageConfirmModal').modal('show');
    $('#deleteFileStorageConfirmModal .confirm-box-ok').on('click',function () {
        $.ajax({
            url:webpath+"/bcm/v1/storage/file/"+storageFileId,
            type:"DELETE",
            contentType: "application/json; charset=utf-8",
            dataType:"json",
            data:{"storageFileId": storageFileId},
            success:function(result){
                if(result.code === 200) {
                    $.message(result.message);
                    $('#fileStorageTable').bootstrapTable('refresh');
                }else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
            }
        });
        $('#deleteFileStorageConfirmModal').modal('hide');
    });
}

/*格式化一个共享存储*/
function formatFileStorage(storageFileId) {
    $('#formatFileStorageConfirmModal').modal('show');
    $('#formatFileStorageConfirmModal .confirm-box-ok').on('click',function () {
        $.ajax({
            url:webpath+"/bcm/v1/storage/file/"+storageFileId,
            type:"PUT",
            contentType: "application/json; charset=utf-8",
            dataType:"json",
            data:{"storageFileId": storageFileId},
            success:function(result){
                if(result.code === 200) {
                    $.message(result.message);
                    $('#fileStorageTable').bootstrapTable('refresh');
                }else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
            }
        });
        $('#formatFileStorageConfirmModal').modal('hide');
    });
}

/*查询共享存储详情*/
function getSingleFileStorage(storageFileId) {
    $.ajax({
        url:webpath+"/bcm/v1/storage/file/"+storageFileId,
        type:"GET",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data:{"storageFileId": storageFileId},
        success:function(result){
            if(result.code === 200) {
                $.message(result.message);
            }else {
                $.message({
                    message: result.message,
                    type: 'error'
                });
            }
        }
    });
}

/*设置bootstrap中文提示信息*/
function formatNoMatch() {
    return '暂无匹配存储';
}
function formatLoading() {
    return '正在加载，请耐心等待......';
}

