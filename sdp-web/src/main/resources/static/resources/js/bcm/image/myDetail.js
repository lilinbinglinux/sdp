getQueryString();

$(document).ready(function () {
    getMyDetailTable();
    basicInfo();

    $(".btn-refresh").click(function(e){
        $('#myDetailTable').bootstrapTable('refresh');
    });
});

function getMyDetailTable() {
    $("#myDetailTable").bootstrapTable({
        url: $webpath + "/bcm/v1/image/"+ window.imageName +"/page",
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
                "projectId": ""
            }
        },
        queryParamsType: 'limit',
        responseHandler: function (res) {
            if(res.data && res.data.content.length === 0) {
                console.log(33);
                window.location.href = $webpath + '/bcm/v1/ci/dockerfileConstructs';
            }else{
                return {
                    "total": res.data ? res.data.totalPages : 0,
                    "rows": res.data ? res.data.content : []
                }
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "",
        columns: [{
            field: 'imageVersion',
            title: '版本',
            align: 'left',
            valign: 'middle'
        }, {
            field: 'createTime',
            title: '更新时间',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'imageSize',
            title: '大小',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<span style="margin-right: 6px">'+row.imageSize+'</span>M';
            }
        }, {
            field: 'registryImageName',
            title: '镜像地址',
            align: 'center',
            valign: 'middle'
        }, {
            field: '',
            title: '操作',
            align: 'right',
            formatter: function (value, row, index) {
                var imageId = row.id;
                var html = '<button type="button" class="btn btn-default btn-xs btn-beconsole-small" style="margin-right: 15px;" onclick="openSecurityModal(\'' + imageId + '\')">安全扫描</button>\n' +
                    '    <button type="button" class="btn btn-default btn-xs btn-beconsole-small" onclick="openDeleteConfirm(\'' + imageId + '\')">删除</button>';
                return html;
            }
        }]
    })
}

function basicInfo() {
    $.ajax({
        url: $webpath + "/bcm/v1/image/"+ window.imageName +"?projectId=",
        type: 'GET',
        contentType: "application/json",
        dataType:"json",
        success: function (res) {
            if(res.code == 200) {
                var str = '<li>镜像名称：<span>'+(res.data.length !== 0 ? res.data[0].imageName : "")+'</span></li>\n' +
                    '      <li>版本数目：<span>'+res.data.length+'</span></li>\n' +
                    '      <li>更新时间：<span>'+(res.data.length !== 0 ? res.data[0].createTime : "")+'</span></li>';
                $('.image-content-right .right-basic-info').html(str);
            }
        }
    });
}

// 安全扫描
function openSecurityModal() {

}

// 打开删除确认框
function openDeleteConfirm(imageId) {
    $("input[name='deleteId']").val(imageId);
    $('#deleteConfirmModal').modal('show');
}

// 删除构建
function deleteImage() {
    var imageId = $("input[name='deleteId']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/image/" + imageId,
        type: 'DELETE',
        dataType:"json",
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $.message(req.message);
                $('#myDetailTable').bootstrapTable('refresh');
                basicInfo();
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