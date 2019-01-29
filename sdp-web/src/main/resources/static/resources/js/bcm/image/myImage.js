$(document).ready(function () {
    getMyImageTable();
    formValidator('#uploadImageForm');

    $(".btn-refresh").click(function(e){
        $('#myImageSearch').val('');
        $('#myImageTable').bootstrapTable('refresh');
    });
    $(".glyphicon-search").click(function(){
        $('#myImageTable').bootstrapTable('refresh');
    });
    $('#myImageSearch').keydown(function(e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $('#myImageTable').bootstrapTable('refresh');
        }
    });

    $('#uploadImagefile').on('input',function () {
        $(this).next().text($(this).val().substring($(this).val().lastIndexOf("\\")+1));
    });
});

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
            imageName: {
                validators: {
                    notEmpty: {
                        message: '请输入镜像名称'
                    }
                }
            },
            imageFilePath: {
                validators: {
                    notEmpty: {
                        message: '请上传文件'
                    }
                }
            }

        }
    });
}

function handleResetImageForm() {
    $('#uploadImageForm').data("bootstrapValidator").resetForm();
    $('#uploadImageForm')[0].reset();
    $('#uploadImageForm #uploadImagefile + .file-text').html('');
}

function getMyImageTable() {
    $("#myImageTable").bootstrapTable({
        url: $webpath + "/bcm/v1/image",
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
                "size": params.limit,
                "imageName": $('#myImageSearch').val(),
                "projectId": ""
            }
        },
        queryParamsType: 'limit',
        responseHandler: function (res) {
            loadImageCards(res.data.content);
            return {
                "total": res.data ? res.data.totalPages : 0,
                "rows": res.data ? res.data.content : []
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "productId",
        columns: [{
            field: 'imageName',
            title: '镜像名称',
            align: 'left',
            valign: 'middle',
            formatter: function (value, row, index) {
                if(row.images.length !== 0) {
                    return '<a class="inner-ahref" href="'+ $webpath +'/bcm/v1/image/individual/detailPage?imageName='+ row.images[0].imageName +'">'+ row.imageName +'</a>';
                }
            }
        }, {
            field: 'imageVersion',
            title: '最新版本',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if(row.images.length !== 0) {
                    return row.images[0].imageVersion;
                }
            }
        }, {
            field: '',
            title: '版本数目',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return row.images.length;
            }
        }, {
            field: 'createTime',
            title: '更新时间',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if(row.images.length !== 0) {
                    return row.images[0].createTime;
                }
            }
        }, {
            field: 'ciType',
            title: '来源',
            align: 'right',
            valign: 'middle',
            formatter: function (value, row, index) {
                if(row.images.length !== 0) {
                    return imageCiTypeConvert(row.images[0].ciType);
                }
            }
        }]
    })
}

// card展示
function loadImageCards(boxs){
    if( boxs && boxs.length > 0 ){
        var str = '';
        $.each(boxs,function (index,item) {
            str += '<a class="card-item-container col-xs-12 col-sm-6 col-md-3 col-lg-3"\n' +
                '           href="'+$webpath+'/bcm/v1/image/individual/detailPage?imageName='+item.images[0].imageName+'">\n' +
                '            <div class="card-item">\n' +
                '                <div class="my-item-top">\n' +
                '                    镜像名称：<span>'+item.imageName+'</span>\n' +
                '                </div>\n' +
                '                <div class="my-item-bottom">\n' +
                '                    <span class="my-time">'+item.images[0].createTime+'</span>\n' +
                '                    <span class="my-version">最新版本：'+item.images[0].imageVersion+' 版本数目：'+item.images.length+'</span>\n' +
                '                    <span class="my-from">'+imageCiTypeConvert(item.images[0].ciType)+'</span>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </a>';
        });
        $(".image-card-list").html(str);
    }else {
        $(".image-card-list").html('<span class="no-project-tips">没有查找到相关镜像</span>');
    }
}

function uploadImageSubmit() {
    $('#uploadImageForm').data('bootstrapValidator').validate();

    var uploadImageData = formatFormData('#uploadImageForm');
    uploadImageData.imageType = 2; //1公用2私有
    uploadImageData.projectId = '';
    console.log(uploadImageData,'新建Dokerfile---------------');

    var file_obj = document.getElementById('uploadImagefile').files[0];
    var fd = new FormData();
    fd.append('imageString', JSON.stringify(uploadImageData));
    fd.append('imageFile', file_obj);

    if ($('#uploadImageForm').data("bootstrapValidator").isValid()) {
        $('a.modal-submit').addClass('disabled');
        $.ajax({
            type: 'POST',
            url: $webpath + "/bcm/v1/image",
            data: fd,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (result) {
                if(result.code == 200) {
                    $.message(result.message);
                    $('#uploadImageModal').modal('hide');
                    handleResetImageForm();
                    $("#myImageTable").bootstrapTable('refresh');
                }else{
                    $('a.modal-submit').removeClass('disabled');
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
            },
            error: function (XMLHttpRequest,textStatus, errorThrown) {
                $('a.modal-submit').removeClass('disabled');
                $.message({
                    message: textStatus,
                    type: 'error'
                });
            }
        });
    }else{
        $('.has-error').each(function (index,item) {
            if(index !== 0) {
                $(item).find('.help-block').hide();
                $(item).find('.form-control').addClass('clear-error-style');
            }else{
                $(item).find('.help-block').show();
                $(item).find('.form-control').removeClass('clear-error-style');
                $(item).find('.form-control').focus();
                setTimeout(function () {
                    $("#uploadImageForm").data('bootstrapValidator').destroy();
                    formValidator('#uploadImageForm');
                },5000);
            }
        })
    }
}