var allPorderWaysArr = getAllPorderWays(),
    orderSvcName = '',
    orderPackageInfo = '',
    orderCustomInfo = '',
    customBasicAttrData = [],
    customControlResAttrData = [];
var commonProApply = {
    cataId: '',
    orderTitleShow: function (orderType) {
        orderType = Number( orderType );
        switch ( orderType ) {
            case 10 : // 审批
                return '<td style="width: 10%;">产品名称</td>\n' +
                    '    <td style="width: 40%;">基本配置</td>\n' +
                    '    <td style="width: 10%;">计费方式</td>\n' +
                    /*'    <td>审批流程</td>\n' +*/
                    '    <td style="width: 40%;">购买量</td>';
            case 20 : // 付费
                return '<td style="width: 10%;">产品名称</td>\n' +
                    '    <td style="width: 40%;">基本配置</td>\n' +
                    '    <td style="width: 10%;">计费方式</td>\n' +
                    '    <td style="width: 30%;">购买量</td>\n' +
                    '    <td style="width: 10%;">总费用</td>';
            case 30 : // 自动开通
                return '<td style="width: 10%;">产品名称</td>\n' +
                    '    <td style="width: 40%;">基本配置</td>\n' +
                    '    <td style="width: 10%;">计费方式</td>\n' +
                    '    <td style="width: 40%;">购买量</td>';
            default :
                return '-';
        }
    },
    // 计算两个时间间隔月数
    handleIntervalMonths: function (d1, d2) {
        //年*12+月
        var m1 = parseInt(d1.split("-")[1].replace(/^0+/, "")) + parseInt(d1.split("-")[0]) * 12;
        var m2 = parseInt(d2.split("-")[1].replace(/^0+/, "")) + parseInt(d2.split("-")[0]) * 12;
        return m2 - m1;
    }
};

$(document).ready(function () {
    getAllPackage();
    getSingleProduct();

    /*formValidator('#packageForm');*/
    formValidator('#customForm');

    $("#packageCancleBtn").click(function () {
        window.location.href = $webpath + '/v1/mall/productsList?cataId=' + commonProApply.cataId;
    });
    $("#customCancelBtn").click(function () {
        layer.confirm('取消后之前填写的内容全部消失，确定取消吗？', {
            btn: ['确定','取消']
        }, function(index){
            window.location.href = $webpath + '/v1/mall/productsList?cataId=' + commonProApply.cataId;
            layer.close(index);
        }, function(){});
    });

    $('#custom-show').hasClass('active') ? $('#custom-details-info').show() : $('#custom-details-info').hide();

    $('#package-show').on('click', function () {
        $('#package-details-info').show();
        $('#custom-details-info').hide();
    });
    $('#custom-show').on('click', function () {
        $('#package-details-info').hide();
        $('#customDescription').css('display') == 'none' ? $('#custom-details-info').show() : $('#custom-details-info').hide();
    });

    // 个性定制--购买方式
    /*$('input[type = "radio"]').on('change', function () {
        if ($('#timeRadio').is(':checked')) {
            $('#timeRadioInfo').css('display', 'inline-block');
            $('#resourceRadioInfo').css('display', 'none');
        } else if ($('#resourceRadio').is(':checked')) {
            $('#timeRadioInfo').css('display', 'none');
            $('#resourceRadioInfo').css('display', 'inline-block');
        }
    });*/
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
            proName: {
                validators: {
                    notEmpty: {
                        message: '请输入属性名'
                    }
                }
            }
        }
    });
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
            var str = '';
            $.each(data, function (index, item) {
                arr.push(item);
                if (item.pwaysRemark && item.pwaysRemark == '审批') {
                    str += '<label class="radio-inline">\n' +
                        '    <input type="radio" name="bpmCircuitId" value="' + item.pwaysId + '"> ' + item.pwaysName + '\n' +
                        '</label>'
                }
            });
            $('#packageApproval').html(str);
            $('#customApproval').html(str);
            $("#packageApproval input[name='bpmCircuitId']:first").attr('checked', 'checked');
            $("#customApproval input[name='bpmCircuitId']:first").attr('checked', 'checked');
        }
    });
    return arr;
}

// 产品基本信息
function getProBaseInfo(data) {
    var proInfoStr = '<div class="pro-logo" style="display: inline-block">\n' +
        '                <img src="data:image/jpg;base64,' + data.productLogo + '" alt="">\n' +
        '            </div>\n' +
        '            <div style="display: inline-block;width: 68%;margin-left: 30px;">\n' +
        '                <h3>' + data.productName + '</h3>\n' +
        '                <p class="row">\n' +
        '                    <span class="col-md-12">产品概述：' + data.productIntrod + '</span>\n' +
        '                </p>\n' +
        '                <span class="pro-version">版本：' + data.productVersion + '</span>\n' +
        '            </div>';
    return proInfoStr;
}


/**
 * 推荐套餐 展示
 * */
function getAllPackage() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productPackage/productPackages/" + window.svcId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // 推荐套餐
            $('#prackageDetailInfo').html(commendPackageShow(data.packageInfos));
            $("input[name='packageRadio']:first").attr('checked', 'checked');

            // 套餐配置
            var packageId = $("input[name='packageRadio']:checked").attr("id");
            package_baseAttrControlGroup(packageId);
            /*$("#packageBaseAttr").html(package_baseAttrControlGroup(packageId));
            $.parser.parse($('.proSlider').parent());//重新渲染easyui滑块

            $("#packageForm input[type='text']").each(function (i) {
                if(!$(this).attr("readonly") && $(this).attr("pattern")){
                    $("#packageForm").bootstrapValidator("addField", $(this).attr("name"), {
                        validators: {
                            /!*notEmpty: {
                                message: '必填'
                            },*!/
                            regexp: {
                                regexp: "/"+$(this).attr("pattern")+"/",
                                message: $(this).attr("data-bv-regexp-message")
                            }
                        }
                    });
                }
            });*/

            // 设置价格
            var packagePrice = $("input[name='packageRadio']:checked").val();
            packagePrice = !packagePrice ? "0" : packagePrice;
            $("#packagePrice").text("￥" + packagePrice);

            $("input[name='packageRadio']").click(function () {
                var packageId = $("input[name='packageRadio']:checked").attr("id");
                package_baseAttrControlGroup(packageId);
                /*$("#packageBaseAttr").html(package_baseAttrControlGroup(packageId));
                $.parser.parse($('.proSlider').parent());//重新渲染easyui滑块

                $("#packageForm input[type='text']").each(function (i) {
                    if(!$(this).attr("readonly") && $(this).attr("pattern")){
                        $("#packageForm").bootstrapValidator("addField", $(this).attr("name"), {
                            validators: {
                                /!*notEmpty: {
                                    message: '必填'
                                },*!/
                                regexp: {
                                    regexp: "/"+$(this).attr("pattern")+"/",
                                    message: $(this).attr("data-bv-regexp-message")
                                }
                            }
                        });
                    }
                });*/

                var packagePrice = $("input[name='packageRadio']:checked").val();
                packagePrice = !packagePrice ? "0" : packagePrice;
                $("#packagePrice").text("￥" + packagePrice);
            });
        }
    });
}

// 推荐套餐
function commendPackageShow(packageInfos) {
    var html = "",
        count = 0;
    $.each(packageInfos,function (i,item) {
        if (item.packageStatus == 20) {
            count++;
            var openDate = '', expireDate = '', modelTime;
            html += "<div class='radio row'><label class='col-md-10'>";
            html += "<input type='radio' name='packageRadio' id='" + item.packageId + "' value=''> 套餐" + (i+1) + "：";
            //html += "<input type='radio' name='packageRadio' id='" + item.packageId + "' value='" + item.orderPrice + "'> 套餐" + (i+1) + "：";

            html += '<span>' + item.packageName + '</span>';
            if (openDate && expireDate) {
                modelTime = commonProApply.handleIntervalMonths(openDate, expireDate);
                html += modelTime == 0 ? '' : '<span class="red-font" style="margin-left: 16px">使用' + modelTime + '个月</span>';
            }
            html += "</label></div>";
        }
    });

    if (count == 0) {
        $('#package-show, #recommend, #package-details-info').css('display', 'none');
        $('#custom-show,#customize').addClass('active');
    } else {
        $('#package-details-info').css('display', 'block');
    }
    return html;
}

// 套餐配置及详情
function package_baseAttrControlGroup(packageId) {
    var attrHtml = "", packInfoHtml = "";
    if (packageId) {
        $.ajax({
            type: "GET",
            url: $webpath + "/api/productPackage/singlePackage/" + packageId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                orderPackageInfo = data;

                var pwaysName = '';
                for (var i = 0; i < allPorderWaysArr.length; i++) {
                    if (allPorderWaysArr[i].pwaysRemark == null && allPorderWaysArr[i].pwaysType == data.orderType) {
                        pwaysName = allPorderWaysArr[i].pwaysName;
                    }
                }

                /*data.orderType == 10 ? $('#approvalProcess').css('display', 'block') : $('#approvalProcess').css('display', 'none');*/
                data.orderType == 20 ? $('#priceShow').css('display', 'block') : $('#priceShow').css('display', 'none');

                packInfoHtml += '<div class="row">\n' +
                    '            <div class="package-font">套餐名称：</div>\n' +
                    '            <div class="package-value">' + data.packageName + '</div>\n' +
                    '        </div>\n' +
                    '        <div class="row">\n' +
                    '            <div class="package-font">套餐描述：</div>\n' +
                    '            <div class="package-value">' + data.packageIntrdo + '</div>\n' +
                    '        </div>\n' +
                    '        <div class="row">\n' +
                    '            <div class="package-font">订购方式：</div>\n' +
                    '            <div class="package-value">' + pwaysName + '</div>\n' +
                    '        </div>';

                /*if (data.packageBasicAttrOrm) {
                    $.each(data.packageBasicAttrOrm, function (index, item) {
                        //getresAttrInput("baseSet_" + item.proCode, "baseSetClass", item, index);
                        attrHtml += getOrderForms(item, index, 10);
                    });
                } else {
                    $('#packageBaseSet').hide();
                }*/
                if (data.packageBasicAttrOrm) {
                    $.each(data.packageBasicAttrOrm, function (index, item) {
                        if(item.proLabel.indexOf(10) != -1 && item.proLabel.indexOf(30) == -1) {
                            var proUnit = item.proUnit ? item.proUnit : '';
                            var proValue = item.proValue ? item.proValue : '';
                            packInfoHtml += '<div class="row">\n' +
                                '            <div class="package-font">' + item.proName + '：</div>\n' +
                                '            <div class="package-value">' + proValue + '<span style="padding-left: 5px">' + proUnit + '</span></div>\n' +
                                '        </div>';
                        }
                    })
                }
                if (data.packageControlResAttrOrm) {
                    $.each(data.packageControlResAttrOrm, function (index, item) {
                        var proUnit = item.proUnit ? item.proUnit : '';
                        var proValue = item.proValue ? item.proValue : '';
                        packInfoHtml += '<div class="row">\n' +
                            '            <div class="package-font">' + item.proName + '：</div>\n' +
                            '            <div class="package-value">' + proValue + '<span style="padding-left: 5px">' + proUnit + '</span></div>\n' +
                            '        </div>';
                    })
                }
                $('#package-res-info').html(packInfoHtml);
            }
        });
    }
    return attrHtml;
}

/**
 * 个性定制 展示
 * */
function getSingleProduct() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/singleProduct/" + window.svcId +'/checkKv',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            orderSvcName = data.productName;
            orderCustomInfo = data;
            commonProApply.cataId = data.productTypeId;

            /*data.orderType == 10 ? $('#serviceApproval').css('display', 'block') : $('#serviceApproval').css('display', 'none');*/
            data.orderType == 20 ? $('#servicePrice').css('display', 'block') : $('#servicePrice').css('display', 'none');

            // 产品基本信息
            $('#proDetailInfo').html(getProBaseInfo(data));

            if(data.productAttrOrm) {
                var count1 = 0, count2 = 0;
                $.each(data.productAttrOrm,function (index, item) {
                    if (item.proLabel.indexOf(10) !== -1) { //申请
                        count1++;
                    }if (item.proLabel.indexOf(30) !== -1) { //资源
                        count2++;
                    }
                });
                var sum = count1 + count2;
                if(sum == 0) {
                    $('#customDescription').show();
                    $('#customBuyBtn').attr('disabled',true);
                    $('#servicePrice,#serviceApproval,#custom-details-info').css('display', 'none');
                }else{
                    $('#customDescription').hide();
                    $('#custom-show').hasClass('active') ? $('#custom-details-info').show() : $('#custom-details-info').hide();

                    // 基本配置
                    $("#customBaseAttr").html(custom_baseAttrControlGroup(data.productAttrOrm));

                    // 定制配置
                    $("#customAttr").html(custom_attrControlGroup(data.productAttrOrm));

                    $("#customForm input[type='text']").each(function (i) {
                        if(!$(this).attr("readonly") && $(this).attr("pattern")){
                            $("#customForm").bootstrapValidator("addField", $(this).attr("name"), {
                                validators: {
                                    /*notEmpty: {
                                        message: '必填'
                                    },*/
                                    regexp: {
                                        regexp: "/"+$(this).attr("pattern")+"/",
                                        message: $(this).attr("data-bv-regexp-message")
                                    }
                                }
                            });
                        }
                    });

                    $.parser.parse($('.proSlider').parent());//重新渲染easyui滑块

                    // 定制详情
                    customDetailShow(data);

                    //总价格
                    $(".customSetClass").on('input', function () {
                        var totalPrice = getTotalcharge(data.productAttrOrm);
                        $("#totalPrice").text("￥" + totalPrice);
                    });

                    //滑块移动
                    $("#resource-slider").slider({
                        onChange: function (newValue, oldValue) {
                            $('#numberspinner').numberspinner('setValue', newValue);
                            var totalPrice = getTotalcharge(data.productAttrOrm);
                            $("#totalPrice").text("￥" + totalPrice);
                        }
                    });

                    $('#numberspinner').numberspinner({
                        onChange: function (newValue, oldValue) {
                            $('#resource-slider').slider('setValue', newValue);
                            var totalPrice = getTotalcharge(data.productAttrOrm);
                            $("#totalPrice").text("￥" + totalPrice);
                        }
                    });

                    // 实时监听动态表单数据（个性定制）
                    listeningForm();
                }

            }else{
                $('#customDescription').show();
                $('#customBuyBtn').attr('disabled',true);
                $('#servicePrice,#serviceApproval').css('display', 'none');
            }
        }
    });
}

// 个性定制(基本配置)
function custom_baseAttrControlGroup(attrInfos) {
    var attrHtml = "",
        count = 0,
        index = 0,
        num = 0;
    if (attrInfos) {
        for (var i = 0; i < attrInfos.length; i++) {
            if (attrInfos[i].proLabel.indexOf(10) !== -1) { //申请
                count++;
                attrHtml += getOrderForms(attrInfos[i], index, 10);
                var basicStr = JSON.stringify(attrInfos[i]);
                customBasicAttrData.push(JSON.parse(basicStr));
                //attrHtml += getresAttrInput("baseSet_" + attrInfos[i].proCode, "baseSetClass", attrInfos[i], index);
                index++;
            }
            if (attrInfos[i].proLabel.indexOf(10) !== -1 && attrInfos[i].proLabel.indexOf(30) !== -1) {
                num++;
            }
        }
    }

    count == 0 || count == num ? $('#customBaseSet').hide() : $('#customBaseSet').show();
    return attrHtml;
}

// 个性定制(定制配置)
function custom_attrControlGroup(attrInfos) {
    var attrHtml = "",
        count = 0,
        index = 0;
    if (attrInfos) {
        $.each(attrInfos, function (i, item) {
            if (item.proLabel.indexOf(30) != -1) { //资源
                count++;
                attrHtml += getOrderForms(item, index, 30);
                var basicStr = JSON.stringify(item);
                customControlResAttrData.push(JSON.parse(basicStr));
                /*var chargeAttr = data[i].cChargeAttrValueInfo;
                //如果资源属性有时间控制，则显示时间进度条
                if (chargeAttr != null &&
                    (chargeAttr.chargeType == ChargeType.TIME ||
                        chargeAttr.chargeType == ChargeType.ALLTIMERES)) {
                  $("#slider-div").css("display", "block");
                }
                if (chargeAttr != null && (chargeAttr.chargeType == ChargeType.TIME)) {
                  attrHtml += "<div>" + data[i].attrName + "</div><br/>";
                } else if (chargeAttr != null && (chargeAttr.chargeType == ChargeType.RESOURCE || chargeAttr.chargeType == ChargeType.ALLTIMERES)) {
                  attrHtml += getresAttrInput("customSet_" + data[i].attrCode, "customSetClass", data[i], index, 20);
                }*/
                index++;
            }
        });
    }

    count == 0 ? $('#customSet').hide() : $('#customSet').show();
    return attrHtml;
}

// 个性定制(定制信息)
function customDetailShow(data) {
    var pwaysName = '', customInfoHtml = '';
    for (var i = 0; i < allPorderWaysArr.length; i++) {
        if (allPorderWaysArr[i].pwaysRemark == null && allPorderWaysArr[i].pwaysType == data.orderType) {
            pwaysName = allPorderWaysArr[i].pwaysName;
        }
    }
    console.log(data,'data');
    customInfoHtml += '<div class="row">\n' +
        '    <div class="package-font">订购方式：</div>\n' +
        '    <div class="package-value">' + pwaysName + '</div>\n' +
        '</div>';
    if (data.productAttrOrm) {
        var num1 = 0, num2 = 0;
        $.each(data.productAttrOrm, function (index, item) {
            if(item.proLabel.indexOf(10) != -1) {
                if(item.proLabel.indexOf(30) == -1) {
                    var proUnit = item.proUnit ? item.proUnit : '';
                    var proValue = item.proValue ? item.proValue : '';
                    customInfoHtml += '<div class="row">\n' +
                        '            <div class="package-font">' + item.proName + '：</div>\n' +
                        '            <div class="package-value"><span class="pro-value" data-proUnit="'+proUnit+'" data-name="orderBasicAttrOrm['+num1+'].proValue">' + proValue + '</span></div>\n' +
                        '        </div>';
                }
                num1++;
            }
        });
        $.each(data.productAttrOrm, function (index, item) {
            if(item.proLabel.indexOf(30) != -1) {
                var proUnit = item.proUnit ? item.proUnit : '';
                var proValue = item.proValue ? item.proValue : '';
                customInfoHtml += '<div class="row">\n' +
                    '            <div class="package-font">' + item.proName + '：</div>\n' +
                    '            <div class="package-value"><span class="pro-value" data-proUnit="'+proUnit+'" data-name="orderControlResAttrOrm['+num2+'].proValue">' + proValue + '</span></div>\n' +
                    '        </div>';
                num2++;
            }
        })
    }
    $('#custom-res-info').html(customInfoHtml);
}

// 实时监听动态表单数据（个性定制）
function listeningForm() {
    $("#customForm .form-child").each(function () {
        if($(this).parents('.box-set.form-group').css('display') != 'none') {

            if($(this).prop("tagName") == 'INPUT') {
                var inputName = $(this).attr('name');
                $(this).on('input',function () {
                    var value = $(this).val();
                    $('#custom-res-info .pro-value').each(function (i) {
                        if($('#custom-res-info .pro-value').eq(i).attr('data-name') == inputName) {
                            $('#custom-res-info .pro-value').eq(i).html(value != '' ? value+'<span style="padding-left: 5px">'+$('#custom-res-info .pro-value').eq(i).attr('data-proUnit')+'</span>' : value);
                        }
                    })
                });
            }

            if($(this).prop("tagName") == 'SELECT') {
                var inputName = $(this).attr('name');
                var value = $(this).children('option:selected').text();
                $('#custom-res-info .pro-value').each(function (i) {
                    if($('#custom-res-info .pro-value').eq(i).attr('data-name') == inputName) {
                        $('#custom-res-info .pro-value').eq(i).html(value != '' ? value+'<span style="padding-left: 5px">'+$('#custom-res-info .pro-value').eq(i).attr('data-proUnit')+'</span>' : value);
                    }
                });
                $(this).on('change',function () {
                    var value = $(this).children('option:selected').text();
                    $('#custom-res-info .pro-value').each(function (i) {
                        if($('#custom-res-info .pro-value').eq(i).attr('data-name') == inputName) {
                            $('#custom-res-info .pro-value').eq(i).html(value != '' ? value+'<span style="padding-left: 5px">'+$('#custom-res-info .pro-value').eq(i).attr('data-proUnit')+'</span>' : value);
                        }
                    })
                });
            }

            if($(this).hasClass('radio-inline')) {
                var inputName = $(this).children('input[type="radio"]').attr('name');
                $(this).children('input[type="radio"]').click(function () {
                    var value = $(this).val();
                    $('#custom-res-info .pro-value').each(function (i) {
                        if($('#custom-res-info .pro-value').eq(i).attr('data-name') == inputName) {
                            $('#custom-res-info .pro-value').eq(i).html(value != '' ? value+'<span style="padding-left: 5px">'+$('#custom-res-info .pro-value').eq(i).attr('data-proUnit')+'</span>' : value);
                        }
                    })
                })
            }

            if($(this).hasClass('checkbox-inline')) {
                var inputName = $(this).children('input[type="checkbox"]').attr('name');
                $(this).children('input[type="checkbox"]').click(function () {
                    var value = getCheckboxData($(this).parents('.input-set.proCheckControl').find(':checkbox:checked'));
                    $('#custom-res-info .pro-value').each(function (i) {
                        if($('#custom-res-info .pro-value').eq(i).attr('data-name') == inputName) {
                            $('#custom-res-info .pro-value').eq(i).html(value != '' ? value+'<span style="padding-left: 5px">'+$('#custom-res-info .pro-value').eq(i).attr('data-proUnit')+'</span>' : value);
                        }
                    })
                })
            }


            if($(this).hasClass('easyui-slider proSlider')) {
                var inputName = $(this).attr('slidername');
                $(this).slider({
                    onChange: function (newValue, oldValue) {
                        $('#custom-res-info .pro-value').each(function (i) {
                            if($('#custom-res-info .pro-value').eq(i).attr('data-name') == inputName) {
                                $('#custom-res-info .pro-value').eq(i).html(newValue != '' || newValue == 0 ? newValue+'<span style="padding-left: 5px">'+$('#custom-res-info .pro-value').eq(i).attr('data-proUnit')+'</span>' : newValue);
                            }
                        })
                    }
                });
            }


        }
    })
}


/**
 * 计费与购买
 * */
//计算总价
function getTotalcharge(attrInfos) {
    var totalPrice = 0;
    /*for (var i = 0; i < attrInfos.length; i++) {
        //资源属性进行计费
        if (attrInfos[i].proLabel.indexOf(30) !== -1) {
            var chargeAttr = attrInfos[i];
            if (chargeAttr != null) {
                //只按时间来计算
                if (chargeAttr.proChargeType == ChargeType.TIME) {
                    if (chargeAttr.proChargeTimeType == ChargeTimeType.DAY) {
                        totalPrice += chargeAttr.proChargePrice * ($("#numberspinner").val() * 30);
                    } else if (chargeAttr.proChargeTimeType == ChargeTimeType.MONTH) {
                        totalPrice += chargeAttr.proChargePrice * $("#numberspinner").val();
                    } else if (chargeAttr.proChargeTimeType == ChargeTimeType.YEAR) {
                        totalPrice += chargeAttr.proChargePrice * ($("#numberspinner").val() / 12);
                    }
                }

                //只按资源来计算
                if (chargeAttr.proChargeType == ChargeType.RESOURCE) {
                    totalPrice += chargeAttr.proChargePrice * $("#customSet_" + attrInfos[i].attrCode).val();
                }

                //按时间和资源同时计算
                if (chargeAttr.proChargeType == ChargeType.ALLTIMERES) {
                    if (chargeAttr.proChargeTimeType == ChargeTimeType.DAY) {
                        totalPrice += chargeAttr.proChargePrice * ($("#numberspinner").val() * 30) * $("#customSet_" + attrInfos[i].proCode).val();
                    } else if (chargeAttr.chargeTimeType == ChargeTimeType.MONTH) {
                        totalPrice += chargeAttr.proChargePrice * $("#numberspinner").val() * $("#customSet_" + attrInfos[i].proCode).val();
                    } else if (chargeAttr.chargeTimeType == ChargeTimeType.YEAR) {
                        totalPrice += chargeAttr.proChargePrice * ($("#numberspinner").val() / 12) * $("#customSet_" + attrInfos[i].proCode).val();
                    }
                }
            }
        }
    }*/
    return totalPrice;
}

// 套餐购买
/*function packageBuy() {
    $('#packageForm').data('bootstrapValidator').validate();

    if (headerVariable.loginId) {
        var packageFormData = formatFormData('#packageForm'),
            submitData = {},
            basicAttrData = orderPackageInfo.packageBasicAttrOrm;

        $("#packageBaseAttr .proCheckControl").each(function (i) {
            var index = $(this).attr('data-index');
            packageFormData['orderBasicAttrOrm[' + index + '].proValue'] = getCheckboxData($(this).find(':checkbox:checked'));
        });

        if(basicAttrData){
            for (var i = 0; i < basicAttrData.length; i++) {
                basicAttrData[i].proValue = packageFormData['orderBasicAttrOrm[' + i + '].proValue'];
            }
        }

        submitData["productId"] = window.svcId;
        submitData["packageId"] = $("input[name='packageRadio']:checked").attr("id");
        submitData["productName"] = newUuid();
        //submitData["productName"] = orderSvcName;
        submitData["startTime"] = '';
        submitData["expireTime"] = '';
        submitData["orderBasicAttrOrm"] = basicAttrData;
        submitData["orderControlResAttrOrm"] = orderPackageInfo.packageControlResAttrOrm;
        submitData["orderStatus"] = '';
        submitData["price"] = Number(packageFormData["packageRadio"]);
        if (orderPackageInfo.orderType == 10) {
            submitData["pwaysId"] = packageFormData["bpmCircuitId"];
            $.each(allPorderWaysArr, function (index, item) {
                if (item.pwaysId == submitData["pwaysId"]) {
                    submitData["bpmModelConfig"] = item.pwaysconfig;
                }
            });
        } else {
            $.each(allPorderWaysArr, function (index, item) {
                if (item.pwaysRemark == null && item.pwaysType == orderPackageInfo.orderType) {
                    submitData["pwaysId"] = item.pwaysId;
                }
            });
            submitData["bpmModelConfig"] = '';
        }

        console.log(submitData, 'submitData----');

        // 校验为审批方式时，审批流程是否选择
        if (orderPackageInfo.orderType == 10 && !submitData["pwaysId"]) {
            layer.msg('当前为审批方式，需选择审批流程！');
            return;
        }
        if($('#packageForm').data('bootstrapValidator').isValid()){
            $('#products-buy').css('display', 'none');
            $('#products-Check').css('display', 'block');
            packageOrderInfo(orderPackageInfo, submitData);
            $('.popWindow').css('height', $('#products-Check').css('height'));
        }
    } else {
        window.location.href = headerVariable.url.casUrl + '/login?service=' + headerVariable.url.projectUrl + $webpath + '/v1/mall/productDetails?svcId=' + window.svcId;
    }
}*/
function packageBuy() {
    if (headerVariable.loginId) {
    var packageFormData = formatFormData('#packageForm'),
        submitData = {};

    console.log(packageFormData,'packageFormData');

    submitData["productId"] = window.svcId;
    submitData["packageId"] = $("input[name='packageRadio']:checked").attr("id");
    submitData["productName"] = newUuid();
    //submitData["productName"] = orderSvcName;
    submitData["startTime"] = '';
    submitData["expireTime"] = '';
    submitData["orderBasicAttrOrm"] = orderPackageInfo.packageBasicAttrOrm;
    submitData["orderControlResAttrOrm"] = orderPackageInfo.packageControlResAttrOrm;
    submitData["orderStatus"] = '';
    submitData["price"] = Number(packageFormData["packageRadio"]);
    if (orderPackageInfo.orderType == 10) {
        submitData["pwaysId"] = packageFormData["bpmCircuitId"];
        $.each(allPorderWaysArr, function (index, item) {
            if (item.pwaysId == submitData["pwaysId"]) {
                submitData["bpmModelConfig"] = item.pwaysconfig;
            }
        });
    } else {
        $.each(allPorderWaysArr, function (index, item) {
            if (item.pwaysRemark == null && item.pwaysType == orderPackageInfo.orderType) {
                submitData["pwaysId"] = item.pwaysId;
            }
        });
        submitData["bpmModelConfig"] = '';
    }

    console.log(submitData, 'submitData----');

    $('#products-buy').css('display', 'none');
    $('#products-Check').css('display', 'block');
    packageOrderInfo(orderPackageInfo, submitData);
    $('.popWindow').css('height', $('#products-Check').css('height'));

    } else {
        window.location.href = headerVariable.url.casUrl + '/login?service=' + headerVariable.url.projectUrl + $webpath + '/v1/mall/productDetails?svcId=' + window.svcId;
    }
}

// 个性定制购买
function customBuy() {
    $('#customForm').data('bootstrapValidator').validate();

    if (headerVariable.loginId) {
        var customFormData = formatFormData('#customForm'),
            submitData = {};

        $("#customBaseAttr .proCheckControl").each(function (i) {
            var index = $(this).attr('data-index');
            customFormData['orderBasicAttrOrm[' + index + '].proValue'] = getCheckboxData($(this).find(':checkbox:checked'));
        });
        $("#customAttr .proCheckControl").each(function (i) {
            var index = $(this).attr('data-index');
            customFormData['orderControlResAttrOrm[' + index + '].proValue'] = getCheckboxData($(this).find(':checkbox:checked'));
        });

        var repeatProArr = [],repeatNum = 0;
        for (var i = 0; i < customControlResAttrData.length; i++) {
            customControlResAttrData[i].proValue = customFormData['orderControlResAttrOrm[' + i + '].proValue'];
            if(customControlResAttrData[i].proLabel.indexOf(10) != -1 && customControlResAttrData[i].proLabel.indexOf(30) != -1) {
                repeatProArr.push(customControlResAttrData[i].proValue);
            }
        }
        for (var i = 0; i < customBasicAttrData.length; i++) {
            customBasicAttrData[i].proValue = customFormData['orderBasicAttrOrm[' + i + '].proValue'];
            if(customBasicAttrData[i].proLabel.indexOf(10) != -1 && customBasicAttrData[i].proLabel.indexOf(30) != -1){
                customBasicAttrData[i].proValue = repeatProArr[repeatNum];
                repeatNum++;
            }
        }

        submitData["productId"] = window.svcId;
        submitData["packageId"] = '';
        submitData["productName"] = newUuid();
        //submitData["productName"] = orderSvcName;
        submitData["startTime"] = '';
        submitData["expireTime"] = '';
        submitData["orderBasicAttrOrm"] = customBasicAttrData;
        submitData["orderControlResAttrOrm"] = customControlResAttrData;
        submitData["orderStatus"] = '';
        submitData["price"] = '';
        if (orderCustomInfo.orderType == 10) {
            submitData["pwaysId"] = customFormData["bpmCircuitId"];
            $.each(allPorderWaysArr, function (index, item) {
                if (item.pwaysId == submitData["pwaysId"]) {
                    submitData["bpmModelConfig"] = item.pwaysconfig;
                }
            });
        } else {
            $.each(allPorderWaysArr, function (index, item) {
                if (item.pwaysRemark == null && item.pwaysType == orderCustomInfo.orderType) {
                    submitData["pwaysId"] = item.pwaysId;
                }
            });
            submitData["bpmModelConfig"] = '';
        }

        console.log(submitData, 'submitData----');

        // 校验资源属性值是否填写
        /*for (var key in customFormData) {
          if (key.indexOf('orderControlResAttrOrm') !== -1) {
            if (customFormData[key] == '') {
              layer.msg('定制配置中资源属性用量必须填写！');
              return;
            }
          }
        }*/

        // 校验为审批方式时，审批流程是否选择
        if (orderCustomInfo.orderType == 10 && !submitData["pwaysId"]) {
            layer.msg('当前为审批方式，需选择审批流程！');
            return;
        }

        // 校验当属性有按时间，滑块无值
        if ($("#slider-div").css("display") == 'block' && $("#numberspinner").val() == 0) {
            layer.msg('当前有按时间控制的资源，需选择时间！');
            return;
        }

        if($('#customForm').data('bootstrapValidator').isValid()){
            $('#products-buy').css('display', 'none');
            $('#products-Check').css('display', 'block');
            customOrderInfo(orderCustomInfo, submitData);
            $('.popWindow').css('height', $('#products-Check').css('height'));
        }
    } else {
        window.location.href = headerVariable.url.casUrl + '/login?service=' + headerVariable.url.projectUrl + $webpath + '/v1/mall/productDetails?svcId=' + window.svcId;
    }
}

// 套餐 订单信息
function packageOrderInfo(orderPackageInfo, submitData) {
    $('#order-title').html(commonProApply.orderTitleShow(orderPackageInfo.orderType));

    var orderStr = '';
    for (var i = 0; i < allPorderWaysArr.length; i++) {
        if (allPorderWaysArr[i].pwaysRemark == null && allPorderWaysArr[i].pwaysType == orderPackageInfo.orderType) {
            var orderType = allPorderWaysArr[i].pwaysName;
        }
        if (orderPackageInfo.orderType == 10 && allPorderWaysArr[i].pwaysId == submitData.pwaysId) {
            var approvalName = allPorderWaysArr[i].pwaysName;
        }
    }

    orderStr += '<td style="font-size: 20px; font-weight: 600;">' + orderSvcName + '</td>';
    orderStr += '<td><ul>';

    if (submitData.orderBasicAttrOrm) {
        $.each(submitData.orderBasicAttrOrm, function (index, item) {
            if(item.proLabel.indexOf(30) == -1) {
                var proValue = item.proValue ? item.proValue : '',
                    proUnit = item.proUnit ? item.proUnit : '';
                orderStr += '<li class="row"><span class="col-md-5 title">' + item.proName + '：</span>\n' +
                    '<span class="col-md-7 text">' + wrapShow(proValue) + '<span style="padding-left: 5px">' + proUnit + '</span></span></li>';
            }
        });
    }

    orderStr += '</td></ul>';
    orderStr += '<td>' + orderType + '</td>';
    /*if (orderPackageInfo.orderType == 10) {
        orderStr += '<td>' + approvalName + '</td>';
    }*/
    orderStr += '<td><ul>';

    if (submitData.orderControlResAttrOrm) {
        $.each(submitData.orderControlResAttrOrm, function (index, item) {
            var proValue = item.proValue ? item.proValue : '',
                proUnit = item.proUnit ? item.proUnit : '';
            orderStr += '<li class="row"><span class="col-md-5 title">' + item.proName + '：</span>\n' +
                '<span class="col-md-7 text">' + wrapShow(proValue) + '<span style="padding-left: 5px">' + proUnit + '</span></span></li>';
        });
    }

    orderStr += '</td></ul>';
    if (orderPackageInfo.orderType == 20) {
        orderStr += '<td>' + submitData.price + '</td>';
    }
    $('#order-details').html(orderStr);
    $("#orderButton .pro-btn-buy").click(function () {
        layer.confirm('是否申请该产品？', {
            btn: ['确定','取消']
        }, function(index){
            $('#maskLayer').css('display', 'block');
            orderSubmit(submitData);
            layer.close(index);
        }, function(){});

    });
}

// 个性定制 订单信息
function customOrderInfo(orderCustomInfo, submitData) {
    $('#order-title').html(commonProApply.orderTitleShow(orderCustomInfo.orderType));

    var orderStr = '';
    for (var i = 0; i < allPorderWaysArr.length; i++) {
        if (allPorderWaysArr[i].pwaysRemark == null && allPorderWaysArr[i].pwaysType == orderCustomInfo.orderType) {
            var orderType = allPorderWaysArr[i].pwaysName;
        }
        if (orderCustomInfo.orderType == 10 && allPorderWaysArr[i].pwaysId == submitData.pwaysId) {
            var approvalName = allPorderWaysArr[i].pwaysName;
        }
    }

    orderStr += '<td style="font-size: 20px; font-weight: 600;">' + orderSvcName + '</td>';
    orderStr += '<td><ul>';

    if (submitData.orderBasicAttrOrm) {
        $.each(submitData.orderBasicAttrOrm, function (index, item) {
            if(item.proLabel.indexOf(30) == -1) {
                var proValue = item.proValue ? item.proValue : '',
                    proUnit = item.proUnit ? item.proUnit : '';
                orderStr += '<li class="row"><span class="col-md-5 title">' + item.proName + '：</span>\n' +
                    '<span class="col-md-7 text">' + wrapShow(proValue) + '<span style="padding-left: 5px">' + proUnit + '</span></span></li>';
            }
        });
    }

    orderStr += '</td></ul>';
    orderStr += '<td>' + orderType + '</td>';
    /*if (orderCustomInfo.orderType == 10) {
        orderStr += '<td>' + approvalName + '</td>';
    }*/
    orderStr += '<td><ul>';

    if (submitData.orderControlResAttrOrm) {
        $.each(submitData.orderControlResAttrOrm, function (index, item) {
            var proValue = item.proValue ? item.proValue : '',
                proUnit = item.proUnit ? item.proUnit : '';
            orderStr += '<li class="row"><span class="col-md-5 title">' + item.proName + '：</span>\n' +
                '<span class="col-md-7 text">' + wrapShow(proValue) + '<span style="padding-left: 5px">' + proUnit + '</span></span></li>';
        });
    }

    orderStr += '</td></ul>';
    if (orderCustomInfo.orderType == 20) {
        orderStr += '<td>' + submitData.price + '</td>';
    }
    $('#order-details').html(orderStr);
    $("#orderButton .pro-btn-buy").click(function () {
        layer.confirm('是否申请该产品？', {
            btn: ['确定','取消']
        }, function(index){
            $('#maskLayer').css('display', 'block');
            orderSubmit(submitData);
            layer.close(index);
        }, function(){});

    });
}

// 提交订单
function orderSubmit(formData) {
    for (var i = 0; i < allPorderWaysArr.length; i++) {
        if (allPorderWaysArr[i].pwaysId == formData.pwaysId) {
            var orderType = allPorderWaysArr[i].pwaysType;
        }
    }
    console.log(formData, 'formData------');

    $.ajax({
        type: "POST",
        url: $webpath + "/api/productOrder/apply",
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function (result) {
            $('#maskLayer').css('display', 'none');
            layer.msg(result.message);
            if (result.code == 200) {
                window.location.href = $webpath + '/v1/mall/orderResult?waysType=' + orderType;
                $.cookie('orderPrice', formData.price);
            }
        },
        error : function(xhr,textStatus){
            if(textStatus == 'timeout'){
                console.log('请求超时');
                /*xhr.abort();
                orderSubmit(formData);*/
            }
        }
    });
}

function newUuid() {
    var uuidStr = uuid(16, 16), newUuid = '';

    if((/[0-9]/).test(uuidStr.substring(0,1)) === true) {
        var str = 'abcdefghijklmnopqrstuvwxyz',s = "";
        for(var i = 0; i < 1; i++){
            var rand = Math.floor(Math.random() * str.length);
            s += str.charAt(rand);
        }
        newUuid = s + uuidStr.toLowerCase().substring(1);
    }else{
        newUuid = uuidStr.toLowerCase();
    }

    return newUuid;
}

// 多行文本换行显示
function wrapShow(s) {
    var re= '';
    var length = s.length;
    for (var i = 0,j=1; i < length; i++,j++) {
        if (j&&j % 20 == 0) {
            re += '<br />';
        } else {
            re += s[i];
        }
    }
    return re;
}

