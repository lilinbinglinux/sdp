var allPorderWaysArr = getAllPorderWays();

$(document).ready(function () {
    var waysTypesValue = '',sortTypeValue = '';

    bindProData('', 1, 10, '', '');
    getCategoryInfo();

    // 产品搜索
    $('#pro-search-btn').on('click', function () {
        bindProData($('#pro-search-keyword').val(), 1, 10, waysTypesValue, sortTypeValue);
    });

    // 服务费用选择
    $('#pro-content-select').on('click', function (e) {
        if($(e.target).prop("tagName").toLowerCase() == 'span') {
            $(e.target).addClass('pro-select').siblings().removeClass('pro-select');
            waysTypesValue = e.target.dataset.value;
            console.log(e.target.dataset.value,'当前点击的类型');
            bindProData($('#pro-search-keyword').val(), 1, 10, waysTypesValue, sortTypeValue);
        }
    });

    // 默认排序
    $('#default-sequence').on('click', function () {
        bindProData($('#pro-search-keyword').val(), 1, 10, waysTypesValue, '');
    });

    // 发布时间排序
    $('#release-time').on('click', function () {
        $(this).children(".sequence-unselect").addClass('sequence-select').removeClass('sequence-unselect').siblings().addClass('sequence-unselect').removeClass('sequence-select');
        console.log($(this).children('.sequence-select').attr('data-value'), '当前点击的排序');
        sortTypeValue = $(this).children('.sequence-select').attr('data-value');
        bindProData($('#pro-search-keyword').val(), 1, 10, waysTypesValue, sortTypeValue);
    });

    // 价格排序
    /*$('#pro-price').on('click', function () {
      $(this).children(".sequence-unselect").addClass('sequence-select').removeClass('sequence-unselect').siblings().addClass('sequence-unselect').removeClass('sequence-select');
      console.log($(this).children('.sequence-select').prop('title'), '当前点击的排序');
    });*/
});

// 获取产品列表
function bindProData(svcName, pageNo, pageSize, waysTypes, sortType) {
    //var proLoading = '<div class="loading">Loading……</div>';
    var proLoading = '<div class="loading"></div>';
    $('#proListShow').html(proLoading);

    $.ajax({
        type: "POST",
        url: $webpath + "/api/product/allProducts",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "page": {
                "pageNo": pageNo,
                "pageSize": pageSize
            },
            "product": {
                "productStatus": 30,
                "productTypeId": window.cataId,
                "productName": svcName,
                "orderType": waysTypes,
                "sortType": sortType
            }
        }),
        success: function (data) {
            $('#proNumber').html(data.totalCount);
            var proListStr = '';
            if (waysTypes == '' && svcName == '' && data.totalCount == 0) {
                proListStr += '<div class="pro-naught">\n' +
                    '                    <span>该服务类型下暂无服务！</span>\n' +
                    '                </div>';
            } else if ((waysTypes != '' || svcName != '') && data.totalCount == 0) {
                proListStr += '<div class="pro-naught">\n' +
                    '                    <span>没有查询到符合条件的商品，请尝试更改筛选条件！</span>\n' +
                    '                </div>';
            } else if (data.totalCount != 0) {
                $.each(data.list, function (index, item) {
                    var orderType = '';
                    for (var i = 0; i < allPorderWaysArr.length; i++) {
                        if(allPorderWaysArr[i].pwaysType == item.orderType) {
                            orderType = allPorderWaysArr[i].pwaysName;
                        }
                    }

                    proListStr += '<div class="pro-list-item">\n' +
                        '                <div class="pro-img">\n' +
                        '                    <img id="'+item.productId+'" src="" class="lazy" data-original="data:image/jpg;base64,'+getSvcLogo(item.productId)+'" alt="">\n' +
                        '                </div>\n' +
                        '                <div class="pro-left">\n' +
                        '                    <div class="pro-name">'+item.productName+'</div>\n' +
                        '                    <div class="pro-describe">'+item.productIntrod+'</div>\n' +
                        '                    <div class="pro-info">\n' +
                        '                        <span>发布时间： '+item.createDate+'</span>\n' +
                        '                        <span>版本号： '+item.productVersion+'</span>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <div class="pro-right">\n' +
                        '                    <div style="float: right">\n' +
                        '                        <h3>'+orderType+'</h3>\n' +
                        '                        <a href="'+$webpath+'/v1/mall/productDetails?svcId='+item.productId+'">\n' +
                        '                            <button class="btn btn-primary">立即申请</button>\n' +
                        '                        </a>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '            </div>';
                });
            }
            $('#proListShow').html(proListStr);
            $("img.lazy").lazyload({
                placeholder : $webpath+"/resources/img/mall/default_img.png",
                effect : "fadeIn",
                container: $("#proListShow")
            });

            // 产品分页
            layui.use('laypage', function () {
                var laypage = layui.laypage;
                laypage.render({
                    elem: 'pro-pagination',
                    count: data.totalCount, //数据总数，从服务端得到
                    limit: pageSize,
                    theme: '#1890ff',
                    curr: pageNo, // 起始页
                    layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
                    jump: function (obj, first) {
                        //首次不执行
                        if (!first) {
                            bindProData(svcName, obj.curr, obj.limit);
                        }
                    }
                });
            });


            /*if (!headerVariable.loginId) {
                $('.pro-right a').each(function () {
                    var curUrl = $(this).attr("href");
                    $(this).attr('href', headerVariable.url.casUrl + '/login?service=' + headerVariable.url.projectUrl + curUrl);
                });
            }*/
        }
    });
}

// 获取服务logo
function getSvcLogo(productId) {
    var logoUrl = '';
    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/singleProduct/"+productId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            logoUrl = data.productLogo;
        }
    });
    return logoUrl;
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
            var proPaidStr = '<div class="pro-sequence">服务费用：</div>\n' +
                '                <span class="pro-paid pro-select">全部</span>';
            $.each(data,function (index,item) {
                if(item.pwaysRemark == null) {
                    arr.push(item);
                    proPaidStr += '<span class="pro-paid" data-value="'+item.pwaysType+'">'+item.pwaysName+'</span>';
                }
            });
            $('#pro-content-select').html(proPaidStr);
        }
    });
    return arr;
}

// 查询单个服务类型
function getCategoryInfo() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/categoryInfos/"+window.cataId,
        contentType : "application/json",
        dataType: "json",
        success: function (data) {
            $('#proCataName').html(data.productTypeName);
        }
    });
}

