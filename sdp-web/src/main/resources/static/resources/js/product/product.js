$(document).ready(function () {
    getProductTable();
    getAllCategory();
});

function getProductTable() {
    var allPorderWaysArr = getAllPorderWays();

    $("#productTable").bootstrapTable({
        url: $webpath + "/api/product/allProductWithAuth",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        classes: 'table-no-bordered',
        cache: false,
        striped: false,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        queryParams: function (params) {
            return JSON.stringify({
                "page": {
                    "pageNo": (params.offset / params.limit) + 1,
                    "pageSize": params.limit
                },
                "dataAuth":"20", // （AuthToken）00: 所有，10：租户下所有，20：该用户下所有
                "product": {
                    "productStatus": $('#svcManageSearchForm select[name="productStatus"]').val(),
                    "productTypeId": $('#svcManageSearchForm select[name="productTypeId"]').val(),
                    "productName": $('#svcManageSearchForm input[name="productName"]').val()
                }
            })
        },
        queryParamsType: 'limit',
        responseHandler: function (res) {
            return {
                "total": res.totalCount,
                "rows": res.list
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "productId",
        columns: [{
            field: 'productName',
            title: '服务名称',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<a href="'+ $webpath +'/product/productInfoPage?productId='+ row.productId +'&productStatus='+ row.productStatus +'">'+ row.productName +'</a>';
            }
        }, {
            field: 'productId',
            title: '服务编码',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'productVersion',
            title: '版本号',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'productStatus ',
            title: '服务状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return svcStateConvert(row.productStatus);
            }
        }, {
            field: 'productTypeId ',
            title: '服务类型',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return row.productTypeName;
            }
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
        }, {
            field: 'createBy',
            title: '创建人',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'createDate',
            title: '创建时间',
            align: 'center',
            valign: 'middle'
        }, {
            field: '',
            title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
                var productId = row.productId;
                var html = '';
                if(row.productStatus == 20 || row.productStatus == 30) { // 状态为已注册和上线
                    html += '<button type="button" class="btn btn-default btn-xs" onclick="toPackagePage(\'' + productId + '\')">套餐</button>\n';
                }else{
                    html += '<button type="button" class="btn btn-default btn-xs" title="服务状态为暂存、下线时，不能添加套餐" onclick="toPackagePage(\'' + productId + '\')" disabled="true">套餐</button>\n';
                }
                if (row.productStatus == 20 || row.productStatus == 40) { // 状态为已注册和下线
                    html += '<button type="button" class="btn btn-default btn-xs" id="onBtn' + productId + '" onclick="svcOnline(\'' + productId + '\')">上线</button>\n' +
                        '<button type="button" class="btn btn-default btn-xs" id="downBtn' + productId + '" onclick="svcDownline(\'' + productId + '\')"  disabled="true">下线</button>\n';
                } else if (row.productStatus == 30) { // 状态为上线
                    html += '<button type="button" class="btn btn-default btn-xs" id="onBtn' + productId + '" onclick="svcOnline(\'' + productId + '\')" disabled="true">上线</button>\n' +
                        '<button type="button" class="btn btn-default btn-xs" id="downBtn' + productId + '" onclick="svcDownline(\'' + productId + '\')">下线</button>\n';
                } else {
                    html += '<button type="button" class="btn btn-default btn-xs" id="onBtn' + productId + '" onclick="svcOnline(\'' + productId + '\')" disabled="true">上线</button>\n' +
                        '<button type="button" class="btn btn-default btn-xs" id="downBtn' + productId + '" onclick="svcDownline(\'' + productId + '\')" disabled="true">下线</button>\n';
                }
                html += '<button type="button" class="btn btn-default btn-xs" onclick="toSvcConfigItem(\'' + productId + '\')">配置列表</button>';
                return html;
            }
        }]
    })
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

// 获取所有服务分类
function getAllCategory() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/allCategory",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data) {
                var str = '';
                $.each(data,function (index, item) {
                    if(item.typeStatus != '1') {
                        str += '<option value="'+ item.productTypeId +'">'+ item.productTypeName +'</option>';
                    }
                });
                $('#svcManageSearchForm select[name="productTypeId"]').append(str);
            }
        }
    })
}

// 服务上线
function svcOnline(productId) {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/modifyStatus/" + productId + "/30",
        dataType: "json",
        success: function (data) {
            layer.msg(data.message);
            if (data.code == 200) {
                $('#onBtn' + productId).attr('disabled', true);
                $('#onBtn' + productId).parent().find('#downBtn' + productId).attr('disabled', false);
                $("#productTable").bootstrapTable('refresh');
            }
        }
    });
}

// 服务下线
function svcDownline(productId) {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/modifyStatus/" + productId + "/40",
        dataType: "json",
        success: function (data) {
            layer.msg(data.message);
            if (data.code == 200) {
                $('#downBtn' + productId).attr('disabled', true);
                $('#downBtn' + productId).parent().find('#onBtn' + productId).attr('disabled', false);
                $("#productTable").bootstrapTable('refresh');
            }
        }
    });
}

// 套餐
function toPackagePage(productId) {
    window.location.href = $webpath + "/productPackage/page?productId=" + productId;
}

// 配置列表
function toSvcConfigItem(productId) {
    window.location.href = $webpath + "/product/configItemPage?productId=" + productId;
}
