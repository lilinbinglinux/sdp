var basicAttrData = [],controlResAttrData = [];

$(document).ready(function () {
    getSinglePackage();
    //formValidator('#packageEditForm');

    $('#packageBasicPoint').on('mouseover',function () {
        $('#packageBasicPoint').popover('show');
    });
    $('#packageBasicPoint').on('mouseout',function () {
        $('#packageBasicPoint').popover('hide');
    });

    laydate.render({
        elem: '#validityPeriod',
        min: 0,
        range: '到',
        //value: '2018-09-11 到 2018-10-25'
    });
});

function getSinglePackage() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productPackage/singlePackage/" + window.packageId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        timeout : 5000,
        success: function (data) {
            $('#packageEditForm input[name="packageName"]').attr('value',data.packageName);
            $('#packageEditForm input[name="packageIntrdo"]').attr('value',data.packageIntrdo);
            $('#packageEditForm #controlTypeShow input[type="radio"]').attr("checked",data.controlType);

            var basicAttrHtml = "",
                controlResAttrHtml = "",
                baseIndex = 0,
                resIndex = 0;
            basicAttrData = data.packageBasicAttrOrm;
            controlResAttrData = data.packageControlResAttrOrm;
            if(data.packageBasicAttrOrm) {
                $.each(data.packageBasicAttrOrm,function (index,item) {
                    basicAttrHtml += getForms(item,baseIndex);
                    baseIndex++;
                });
            }

            if(data.packageControlResAttrOrm) {
                $.each(data.packageControlResAttrOrm,function (index,item) {
                    controlResAttrHtml += getForms(item,resIndex,30);
                    resIndex++;
                });
            }

            if(data.packageBasicAttrOrm) {
                data.packageBasicAttrOrm.length == 0 ? $('#packageBasicAttr').html("暂无") : $('#packageBasicAttr').html(basicAttrHtml);
            }else {
                $('#packageBasicAttr').html("暂无");
            }
            if(data.packageControlResAttrOrm) {
                data.packageControlResAttrOrm.length == 0 ? $('#packageControlResAttr').html("暂无") : $('#packageControlResAttr').html(controlResAttrHtml);
            }else {
                $('#packageControlResAttr').html("暂无");
            }

            $.parser.parse($('.proSlider').parent());//重新渲染easyui滑块

            getAllPorderWays(data.orderType);
            data.orderType == 20 ? $('#controlTypeShow').css('display','block') : $('#controlTypeShow').css('display','none');
        },
        error : function(xhr,textStatus){
            if(textStatus == 'timeout'){
                xhr.abort();
                getSinglePackage();
            }
        }
    });
}

/*function formValidator(id) {
    $(id).bootstrapValidator({
        live: 'enabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            packageName: {
                validators: {
                    notEmpty: {
                        message: '请输入套餐名称'
                    }
                }
            },
            packageIntrdo: {
                validators: {
                    notEmpty: {
                        message: '请输入套餐说明'
                    }
                }
            },
            controlType: {
                validators: {
                    notEmpty: {
                        message: '请选择控制方式'
                    }
                }
            }
        }
    });
}*/

// 保存及发布套餐
function handleSavePackage(packageStatus) {
    $('#packageEditForm').data('bootstrapValidator').validate();
    var packageFormData = formatFormData('#packageEditForm');
    var submitData = {};

    $("#packageBasicAttr .proCheckControl").each(function (i) {
        var index = $(this).attr('data-index');
        packageFormData['packageBasicAttrOrm['+index+'].proValue'] = getCheckboxData($(this).find(':checkbox:checked'));
    });
    $("#packageControlResAttr .proCheckControl").each(function (i) {
        var index = $(this).attr('data-index');
        packageFormData['packageControlResAttrOrm['+index+'].proValue'] = getCheckboxData($(this).find(':checkbox:checked'));
    });

    var repeatProArr = [],repeatNum = 0;
    if(controlResAttrData) {
        for (var i = 0; i < controlResAttrData.length; i++) {
            controlResAttrData[i].proValue = packageFormData['packageControlResAttrOrm['+i+'].proValue'];
            if(controlResAttrData[i].proLabel.indexOf(10) != -1 && controlResAttrData[i].proLabel.indexOf(30) != -1) {
                repeatProArr.push(controlResAttrData[i].proValue);
            }
        }
    }
    if(basicAttrData) {
        for (var i = 0; i < basicAttrData.length; i++) {
            basicAttrData[i].proValue = packageFormData['packageBasicAttrOrm['+i+'].proValue'];
            basicAttrData[i].proIsFixed = packageFormData['packageBasicAttrOrm['+i+'].proIsFixed'];
            if(basicAttrData[i].proLabel.indexOf(10) != -1 && basicAttrData[i].proLabel.indexOf(30) != -1){
                basicAttrData[i].proValue = repeatProArr[repeatNum];
                repeatNum++;
            }
        }
    }

    submitData.productId = window.productId;
    submitData.packageName = packageFormData.packageName;
    submitData.packageIntrdo = packageFormData.packageIntrdo;
    submitData.controlType = packageFormData.controlType;
    submitData.orderType = packageFormData.orderType;
    submitData.packageBasicAttrOrm = basicAttrData;
    submitData.packageControlResAttrOrm = controlResAttrData;
    submitData.packageStatus = packageStatus;
    submitData.packageId = window.packageId;
    console.log(packageFormData,'packageFormData');
    console.log(submitData,'submitData');

    if(submitData.packageBasicAttrOrm) {
        for (var i = 0; i < submitData.packageBasicAttrOrm.length; i++) {
            var cur = submitData.packageBasicAttrOrm[i];
            /*if(cur.proIsFixed == 10 && cur.proValue == '') {
                layer.msg('基本配置中，勾选的属性必须填值！');
                return;
            }*/
            if(cur.proValue == '') {
                layer.msg('基本配置中，属性必须填值！');
                return;
            }
        }
    }

    if(submitData.packageControlResAttrOrm) {
        for (var i = 0; i < submitData.packageControlResAttrOrm.length; i++) {
            var cur = submitData.packageControlResAttrOrm[i];
            if(cur.proValue == '') {
                layer.msg('资源配置中，属性必须填值！');
                return;
            }
        }
    }

    if($('#packageEditForm').data('bootstrapValidator').isValid()){
        $.ajax({
            type: "POST",
            url: $webpath + '/api/productPackage/updatePackage',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(submitData),
            success: function (result) {
                if (result.code == 200) {
                    layer.msg(result.message,{
                        icon: 1,
                        time: 2000
                    },function () {
                        window.location.href = $webpath+"/productPackage/page?productId="+window.productId;
                    });
                }else{
                    layer.msg(result.message,{icon: 2});
                }
            }
        })
    }
}

// 获取订购方式
function getAllPorderWays(orderType) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({pwaysType: ''}),
        url: $webpath + "/api/porderWays/allPorderWays",
        success: function (result) {
            var str = '',
                processStr = '';
            var newArr = orderType == 20 ? result : result.filter(function (item,index) { return item.pwaysType != 20;});

            $.each(newArr,function (index,item) {
                if(item.pwaysRemark == null) {
                    if(item.pwaysType == orderType) {
                        str += '<label class="radio-inline">\n' +
                            '       <input type="radio" name="orderType" value="'+item.pwaysType+'" checked> '+item.pwaysName+'\n' +
                            '</label>';
                    }else{
                        str += '<label class="radio-inline">\n' +
                            '       <input type="radio" name="orderType" value="'+item.pwaysType+'"> '+item.pwaysName+'\n' +
                            '</label>';
                    }
                }

                if (item.pwaysRemark) {
                    processStr += '<label class="radio-inline">\n' +
                        '       <input type="radio" name="pwaysId" value="' + item.pwaysId + '"> ' + item.pwaysName + '\n' +
                        '</label>';
                }
            });
            $('#orderTypeShow').html(str);
            $('#approvalProcessShow').html(processStr);
            $("#packageEditForm").bootstrapValidator("addField", "orderType", { //对动态生成的表单添加校验
                validators: {
                    notEmpty: {
                        message: '请选择订购方式'
                    }
                }
            });
            $("#packageEditForm").bootstrapValidator("addField", "pwaysId", {
                validators: {
                    notEmpty: {
                        message: '请选择审批流程'
                    }
                }
            });
            /*$('#orderTypeShow input[name="orderType"]').on('change',function () {
                $(this).val() == 10 ? $('#approvalProcessShow').parent().show() : $('#approvalProcessShow').parent().hide();
            });*/
        }
    });
}