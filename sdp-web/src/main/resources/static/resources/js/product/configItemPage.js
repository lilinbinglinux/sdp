var productDetailInfo;

$(document).ready(function () {
    getConfigItem();

    $('#configPoint').on('mouseover',function () {
        $('#configPoint').popover('show');
    });
    $('#configPoint').on('mouseout',function () {
        $('#configPoint').popover('hide');
    });

});

function getConfigItem() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/singleProduct/"+window.productId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            productDetailInfo = data;

            var str = '<div class="col-md-6 row">\n' +
                '            <label class="col-md-3">服务名称</label>\n' +
                '            <span class="col-md-6">'+data.productName+'</span>\n' +
                '        </div>\n' +
                '        <div class="col-md-6 row">\n' +
                '            <label class="col-md-3">服务版本</label>\n' +
                '            <span class="col-md-6">'+data.productVersion+'</span>\n' +
                '        </div>';
            $('#svcBasicInfo').html(str);

            var productAttrOrm = [];
            if(data.productAttrOrm == null) {
                productAttrOrm = [];
            }else{
                $.each(data.productAttrOrm,function (index,item) {
                    if(item.proLabel.indexOf(20) != -1) { // 访问
                        item.proIsChecked = item.proIsChecked == '10' ? true : false;
                        productAttrOrm.push(item);
                    }
                });
            }

            $("#configItemTable").bootstrapTable({
                data: productAttrOrm,
                classes: 'table-no-bordered',
                cache: false,
                striped: false,
                pagination: true,
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 25, 50, 100],
                sidePagination: "client",
                clickToSelect: true,
                maintainSelected : true,
                columns: [{
                    field:"proIsChecked",
                    checkbox:true,
                    align: 'center'
                },{
                    field: 'proName',
                    title: '属性名',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'proEnName',
                    title: '属性英文名',
                    align: 'center',
                    valign: 'middle'
                },  {
                    field: 'proCode',
                    title: '属性编码',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'proShowType',
                    title: '控件类型',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return formItemConvert(row.proShowType);
                    }
                }, {
                    field: 'proDataType ',
                    title: '属性类型',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return basicTypeConvert(row.proDataType);
                    }
                }, {
                    field: 'proValue',
                    title: '初始值',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return row.proValue == 'null' ? '' :row.proValue;
                    }
                }, {
                    field: 'proVerfyRule ',
                    title: '校验规则',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'proDesc',
                    title: '属性描述',
                    align: 'center',
                    valign: 'middle'
                }]
            });
        }
    });
}

function handleSvcConfig() {
    //console.log($("#configItemTable").bootstrapTable('getAllSelections'),'返回所有被选的行');
    var tableData = $("#configItemTable").bootstrapTable('getData');
    $.each(tableData,function (index,item) {
        item.proIsChecked = item.proIsChecked == true ? "10" : "20";
    });
    console.log(tableData,'配置列表数据');

    var submitData = productDetailInfo.productAttrOrm;
    var count = 0;
    $.each(submitData,function (index,item) {
        if(item.proLabel.indexOf(20) != -1) { // 访问
            item.proIsChecked = tableData[count].proIsChecked;
            count++;
        }
    });

    console.log(submitData,"修改后的属性");
    $.ajax({
        type: "POST",
        url: $webpath + "/api/product/proItems",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "productAttrOrm": submitData,
            "productId":productDetailInfo.productId,
            "productIntrod":productDetailInfo.productIntrod,
            "productName":productDetailInfo.productName,
            "productStatus":productDetailInfo.productStatus,
            "productTypeId":productDetailInfo.productTypeId,
            "productVersion":productDetailInfo.productVersion
        }),
        success: function (result) {
            if (result.code == 200) {
                layer.msg(result.message,{
                    icon: 1,
                    time: 2000
                },function () {
                    window.location.href = $webpath + '/product/page';
                });
            }else{
                layer.msg(result.message,{icon: 2});
            }
        },
        error: function (xhr,textState,message) {
            console.log(xhr, textState, message);
        }
    });
}