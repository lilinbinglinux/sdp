$(document).ready(function () {
    //getPackageList();

    getPackageTable();
});

/*function getPackageList() {
    var allPorderWaysArr = getAllPorderWays();
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productPackage/productPackages/"+window.productId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            var str = '<div class="col-md-6 row">\n' +
                '            <label class="col-md-3">服务名称</label>\n' +
                '            <span class="col-md-6">'+data.productInfo.productName+'</span>\n' +
                '        </div>\n' +
                '        <div class="col-md-6 row">\n' +
                '            <label class="col-md-3">服务版本</label>\n' +
                '            <span class="col-md-6">'+data.productInfo.productVersion+'</span>\n' +
                '        </div>\n' +
                '        <div class="col-md-6 row">\n' +
                '            <label class="col-md-3">服务描述</label>\n' +
                '            <span class="col-md-9">'+data.productInfo.productIntrod+'</span>\n' +
                '        </div>';
            $('#svcBasicInfo').html(str);

            if(data.packageInfos.length == 0) {
                $('#packageShow').html('<div class="package-null">暂无套餐信息</div>');
            }else {
                var packageStr = '';
                $.each(data.packageInfos, function (index,item) {
                    for (var i = 0; i < allPorderWaysArr.length; i++) {
                        if(allPorderWaysArr[i].pwaysType == item.orderType) {
                            var pwaysName = allPorderWaysArr[i].pwaysName;
                        }
                    }

                    packageStr += '<div class="package-item">\n' +
                        '            <div class="row">\n' +
                        '                <div class="col-md-12" style="margin-bottom: 10px;">\n' +
                        '                    <label style="font-size: 16px">'+item.packageName+'</label>\n' +
                        '                    <span style="float: right;color: #ff4d4f;font-size: 16px">'+packageStatusConvert(item.packageStatus)+'</span>\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '            <div class="row">\n' +
                        '                <div class="col-md-12" style="margin-bottom: 10px;">\n' +
                        '                    <label>套餐说明：</label>\n' +
                        '                    <span>'+item.packageIntrdo+'</span>\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '            <div class="row">\n' +
                        '                <div class="col-md-12" style="margin-bottom: 10px;">\n' +
                        '                    <label>订购方式：</label>\n' +
                        '                    <span>'+pwaysName+'</span>\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '            <div class="row">\n' +
                        '                <div class="col-md-12" style="margin-bottom: 10px;">\n' +
                        '                    <label>控制方式：</label>\n' +
                        '                    <span>'+controlTypeConvert(item.controlType)+'</span>\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '            <div class="package-buttons">\n' +
                        '                <div class="package-edit" onclick="handleEditPackage(\''+item.packageId+'\')">\n' +
                        '                    <span class="glyphicon glyphicon-edit" title="编辑" style="line-height: 30px;"></span>\n' +
                        '                </div>\n' +
                        '                <div class="package-delete" onclick="handleDeletePackage(\''+item.packageId+'\')">\n' +
                        '                    <span class="glyphicon glyphicon-trash" title="删除" style="line-height: 30px;"></span>\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>';
                });
                $('#packageShow').html(packageStr);
            }
        }
    });
}*/

function getPackageTable() {
    var allPorderWaysArr = getAllPorderWays();

    $("#packageTable").bootstrapTable({
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
        ajax: function(request) {
            $.ajax({
                type: "GET",
                url: $webpath + "/api/productPackage/productPackages/"+window.productId,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    // console.log(data,'data');
                    // var str = '<div class="col-md-6 row">\n' +
                    //     '            <label class="col-md-3">服务名称</label>\n' +
                    //     '            <span class="col-md-6">'+data.productInfo.productName+'</span>\n' +
                    //     '        </div>\n' +
                    //     '        <div class="col-md-6 row">\n' +
                    //     '            <label class="col-md-3">服务版本</label>\n' +
                    //     '            <span class="col-md-6">'+data.productInfo.productVersion+'</span>\n' +
                    //     '        </div>\n' +
                    //     '        <div class="col-md-6 row">\n' +
                    //     '            <label class="col-md-3">服务描述</label>\n' +
                    //     '            <span class="col-md-9">'+data.productInfo.productIntrod+'</span>\n' +
                    //     '        </div>';
                    // $('#svcBasicInfo').html(str);
                    $("#info-productName").text(data.productInfo.productName ? data.productInfo.productName : "-" );
                    $("#info-productVersion").text(data.productInfo.productVersion ? data.productInfo.productVersion : "-");
                    $("#info-productIntrod").text(data.productInfo.productIntrod ? data.productInfo.productIntrod : "-");
                    request.success({ row : data.packageInfos});
                    $('#packageTable').bootstrapTable('load', data.packageInfos);
                }
            });
        },
        sidePagination: "client",
        //sidePagination: "server",
        clickToSelect: true,
        uniqueId: "packageId",
        columns: [{
            field: 'packageName',
            title: '产品名称',
            align: 'left',
            valign: 'middle'
        }, {
            field: '',
            title: '产品类型',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'packageStatus',
            title: '产品状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return packageStatusConvert(row.packageStatus);
            }
        }, {
            field: '',
            title: '有效期',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'packageIntrdo',
            title: '产品说明',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'orderType',
            title: '订购方式',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                for (var i = 0; i < allPorderWaysArr.length; i++) {
                    if(allPorderWaysArr[i].pwaysType == row.orderType) {
                        return allPorderWaysArr[i].pwaysName;
                    }
                }
            }
        },{
            field: 'controlType',
            title: '控制方式',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return controlTypeConvert(row.controlType);
            }
        }, {
            field: '',
            title: '操作',
            align: 'right',
            formatter: function (value, row, index) {
                var packageId = row.packageId;
                var html = '';
                /*if (row.packageStatus == 20 || row.packageStatus == 40) {
                    html += '<button type="button" class="btn btn-default btn-xs" id="onBtn' + packageId + '" onclick="handleOnShelves(\'' + packageId + '\')">上架</button>\n' +
                        '<button type="button" class="btn btn-default btn-xs" id="downBtn' + packageId + '" onclick="handleDownShelves(\'' + packageId + '\')"  disabled="true">下架</button>\n';
                } else if (row.packageStatus == 30) {
                    html += '<button type="button" class="btn btn-default btn-xs" id="onBtn' + packageId + '" onclick="handleOnShelves(\'' + packageId + '\')" disabled="true">上架</button>\n' +
                        '<button type="button" class="btn btn-default btn-xs" id="downBtn' + packageId + '" onclick="handleDownShelves(\'' + packageId + '\')">下架</button>\n';
                } else{
                    html += '<button type="button" class="btn btn-default btn-xs" id="onBtn' + packageId + '" onclick="handleOnShelves(\'' + packageId + '\')" disabled="true">上架</button>\n' +
                        '<button type="button" class="btn btn-default btn-xs" id="downBtn' + packageId + '" onclick="handleDownShelves(\'' + packageId + '\')" disabled="true">下架</button>\n';
                }*/
                html += '<button type="button" class="btn btn-default btn-xs" onclick="handleEditPackage(\'' + packageId + '\')">编辑</button>\n';
                html += '<button type="button" class="btn btn-default btn-xs" onclick="handleDeletePackage(\'' + packageId + '\')">删除</button>';
                return html;
            }
        }]
    })


}

// 新增套餐
function toCreatePackage() {
    window.location.href=$webpath+"/productPackage/createPackagePage?productId="+window.productId;
}

// 新增试用
function toCreateTryPackage() {
    window.location.href=$webpath+"/productPackage/createPackagePage?productId="+window.productId;
}

// 新增折扣
function toCreateDiscountPackage() {
    window.location.href=$webpath+"/productPackage/createDiscountPage?productId="+window.productId;
}

// 套餐上架
function handleOnShelves(packageId) {
    alert('套餐上架');
    /*$.ajax({
        type: "GET",
        url: '',
        dataType: "json",
        success: function (data) {
            layer.msg(data.message);
            if (data.code == 200) {
                $('#onBtn' + packageId).attr('disabled', true);
                $('#onBtn' + packageId).parent().find('#downBtn' + packageId).attr('disabled', false);
                $("#packageTable").bootstrapTable('refresh');
            }
        }
    });*/
}

// 套餐下架
function handleDownShelves(packageId) {
    alert('套餐下架');
    /*$.ajax({
        type: "GET",
        url: '',
        dataType: "json",
        success: function (data) {
            layer.msg(data.message);
            if (data.code == 200) {
                $('#downBtn' + packageId).attr('disabled', true);
                $('#downBtn' + packageId).parent().find('#onBtn' + packageId).attr('disabled', false);
                $("#packageTable").bootstrapTable('refresh');
            }
        }
    });*/
}

// 编辑套餐
function handleEditPackage(packageId) {
    window.location.href=$webpath+"/productPackage/updatePackagePage?productId="+window.productId+"&packageId="+packageId;
}

// 删除套餐
function handleDeletePackage(packageId) {
    layer.confirm('请问是否删除该套餐？', {
        btn: ['确定','取消']
    }, function(index){
        $.ajax({
            url: $webpath + "/api/productPackage/deletePackage",
            type: 'POST',
            dataType:"json",
            data:JSON.stringify({
                packageId: packageId
            }),
            contentType: "application/json",
            success: function (req) {
                if(req.code == 200) {
                    layer.msg(req.message,{icon:1});
                    //getPackageList();
                    $("#packageTable").bootstrapTable('refresh');
                }else {
                    layer.msg(req.message,{icon:2});
                }
            },
            error: function () {}
        });
        layer.close(index);
    }, function(){});
}

// 获取所有订购方式
function getAllPorderWays() {
    var arr = [];
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({pwaysType: ''}),
        url: $webpath + "/api/porderWays/allPorderWays",
        success: function (data) {
            $.each(data,function (index,item) {
                if(item.pwaysRemark == null) {
                    arr.push(item);
                }
            })
        }
    });
    return arr;
}