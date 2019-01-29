var svcRegisterAttrArr = [], //属性列表数据
    relyOnAttrOrmArr = [], //依赖服务列表数据
    attrInitialGrid,
    attrInitialEditGrid,
    timeValueGrid,
    frequencyValueGrid;

$(document).ready(function () {
    getAllPorderWays();
    getAllCategory();
    getProductTable('', 1, 10);
    formValidator('#svcRegisterInfo');
    formValidator('#svcAddAttrForm');
    formValidator('#svcEditAttrForm');
    formValidator('#relySvcEditForm');

    $('#proSetPoint').on('mouseover',function () {
        $('#proSetPoint').popover('show');
    });
    $('#proSetPoint').on('mouseout',function () {
        $('#proSetPoint').popover('hide');
    });


    $('a#initialParamA').on("click", function () {
        $('#initialUrl input[name="initialUrl"]').val('');
    });
    $('a#initialUrlA').on("click", function () {
        attrInitialGrid.reload();
    });
    $('#svcAddAttrForm select[name="proShowType"]').on('change', function () {
        $(this).val() == 10 || $(this).val() == 50 ? $('#initialAddBox').hide() : $('#initialAddBox').show();
    });

    // 添加属性-初始值表格
    attrInitialGrid = $("#attrInitialTable").ligerGrid({
        columns: [
            {name: 'checked', display: '默认项', width: '150', editor: {type: 'checkbox'}},
            {name: 'key', display: '键值', width: '150', editor: {type: 'text'}},
            {name: 'value', display: '显示项', width: '150', editor: {type: 'text'}},
            {
                display: '操作', width: "150", align: 'center',
                render: function (rowdata, rowindex, value) {
                    var h = "";
                    if (!rowdata._editing)
                    {
                        h += "<a href='javascript:attrInitialGrid.beginEdit(" + rowindex + ")' style='padding-right: 3px'>修改</a>";
                        h += "<a href='javascript:attrInitialGrid.deleteRow(" + rowindex + ")'>删除</a>";
                    }
                    else
                    {
                        h += "<a href='javascript:attrInitialGrid.endEdit(" + rowindex + ")' style='padding-right: 3px'>提交</a>";
                        h += "<a href='javascript:attrInitialGrid.cancelEdit(" + rowindex + ")'>取消</a>";
                    }
                    return h;
                }
            }],
        width: 'auto',
        data: { Rows: [{checked: '', key: '', value: ''}]},
        enabledEdit: true,
        clickToEdit:false,
        usePager: false
    });
    $('#initialUrl input[name="initialUrl"]').on('change',function () {
        var reg = /(http|ftp|https):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/;
        if(!reg.test($(this).val())){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg('请输入正确的初始值地址');
            });
        }
    });


    /**
     * 下一步：价格配置
     */
    $('#producPriceForm input[name="controlType"]').on('change',function () {
        var chackedValue = getCheckboxData("#producPriceForm input[name='controlType']:checked");
        if(chackedValue.indexOf(10) == -1 && chackedValue.indexOf(20) == -1) {
            $('#timeSet, #frequencySet').css('display','none');
        }else if(chackedValue.indexOf(10) != -1 && chackedValue.indexOf(20) != -1){
            $('#timeSet, #frequencySet').css('display','block');
        }else if(chackedValue.indexOf(10) != -1 && chackedValue.indexOf(20) == -1) {
            $('#timeSet').css('display','block');
            $('#frequencySet').css('display','none');
        }else if(chackedValue.indexOf(10) == -1 && chackedValue.indexOf(20) != -1){
            $('#timeSet').css('display','none');
            $('#frequencySet').css('display','block');
        }
    });
    $('#producPriceForm select[name="timeControl"]').on('change',function () {
        timeValueGrid.reload();
        $('#timeRangeSet input[name="minRange"], #timeRangeSet input[name="maxRange"]').val('');
        if($(this).val() == 1){
            $('#timeValueSet').css('display','none');
            $('#timeRangeSet').css('display','inline-block');
        }else{
            $('#timeValueSet').css('display','block');
            $('#timeRangeSet').css('display','none');
        }
    });
    $('#producPriceForm select[name="frequencyControl"]').on('change',function () {
        frequencyValueGrid.reload();
        $('#frequencyRangeSet input[name="minRange"], #frequencyRangeSet input[name="maxRange"]').val('');
        if($(this).val() == 1){
            $('#frequencyValueSet').css('display','none');
            $('#frequencyRangeSet').css('display','inline-block');
        }else{
            $('#frequencyValueSet').css('display','block');
            $('#frequencyRangeSet').css('display','none');
        }
    });

    // 时间-值设置
    timeValueGrid = $('#timeValueSetGrid').ligerGrid({
        columns: [
            {name: 'value', display: '值', width: '180', editor: {type: 'spinner',minValue:'0'}},
            {
                display: '操作', width: "180", align: 'center',
                render: function (rowdata, rowindex, value) {
                    var h = "";
                    if (!rowdata._editing)
                    {
                        h += "<a href='javascript:timeValueGrid.beginEdit(" + rowindex + ")' style='padding-right: 3px'>修改</a>";
                        h += "<a href='javascript:timeValueGrid.deleteRow(" + rowindex + ")'>删除</a>";
                    }
                    else
                    {
                        h += "<a href='javascript:timeValueGrid.endEdit(" + rowindex + ")' style='padding-right: 3px'>提交</a>";
                        h += "<a href='javascript:timeValueGrid.cancelEdit(" + rowindex + ")'>取消</a>";
                    }
                    return h;
                }
            }],
        width: '365px',
        data: { Rows: [{value: ''}] },
        toolbar: {
            items: [
                { text: '增加', click: function (item) {
                        timeValueGrid.addRow({value:''});
                    }, icon: 'add' },
                { line: true }
            ]
        },
        enabledEdit: true,
        clickToEdit:false,
        usePager: false
    });

    // 次数-值设置
    frequencyValueGrid = $('#frequencyValueSetGrid').ligerGrid({
        columns: [
            {name: 'value', display: '值', width: '180', editor: {type: 'spinner',minValue:'0'}},
            {
                display: '操作', width: "180", align: 'center',
                render: function (rowdata, rowindex, value) {
                    var h = "";
                    if (!rowdata._editing)
                    {
                        h += "<a href='javascript:frequencyValueGrid.beginEdit(" + rowindex + ")' style='padding-right: 3px'>修改</a>";
                        h += "<a href='javascript:frequencyValueGrid.deleteRow(" + rowindex + ")'>删除</a>";
                    }
                    else
                    {
                        h += "<a href='javascript:frequencyValueGrid.endEdit(" + rowindex + ")' style='padding-right: 3px'>提交</a>";
                        h += "<a href='javascript:frequencyValueGrid.cancelEdit(" + rowindex + ")'>取消</a>";
                    }
                    return h;
                }
            }],
        width: '365px',
        data: { Rows: [{value: ''}] },
        toolbar: {
            items: [
                { text: '增加', click: function (item) {
                        frequencyValueGrid.addRow({value:''});
                    }, icon: 'add' },
                { line: true }
            ]
        },
        enabledEdit: true,
        clickToEdit:false,
        usePager: false
    });

});

// 获取当前所有服务
function getProductTable(svcName, pageNo, pageSize) {
    $.ajax({
        type: "POST",
        url: $webpath + "/api/product/allProducts",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "page": {
                "pageNo": pageNo,
                "pageSize": pageSize
            },
            "product": {
                "productStatus": '',
                "productTypeId": '',
                "productName": ''
            }
        }),
        success: function (data) {
            if (data.list.length > 0) {
                var str = '';
                $.each(data.list, function (index, item) {
                    str += '<tr>\n' +
                        '   <td><input name="" type="checkbox" value="'+item.productId+'"></td>\n' +
                        '   <td>' + item.productId + '</td>\n' +
                        '   <td>' + item.productName + '</td>\n' +
                        '   <td>' + item.productVersion + '</td>\n' +
                        '</tr>';
                });
                $('#relySvcCheckedTable tbody').html(str);

                // 所有服务分页
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
                                getProductTable('', obj.curr, obj.limit);
                            }
                        }
                    });
                });
            } else {
                $('#relySvcCheckedTable tbody').html('');
            }
        }
    })
}

// 获取所有订购方式
function getAllPorderWays() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({pwaysType: ''}),
        url: $webpath + "/api/porderWays/allPorderWays",
        success: function (result) {
            var str = '',
                processStr = '';
            $.each(result, function (index, item) {
                if (item.pwaysRemark == null) {
                    str += '<label class="radio-inline">\n' +
                        '       <input type="radio" name="orderType" value="' + item.pwaysType + '"> ' + item.pwaysName + '\n' +
                        '</label>';
                }
                if (item.pwaysRemark) {
                    processStr += '<label class="radio-inline">\n' +
                        '       <input type="radio" name="pwaysId" value="' + item.pwaysId + '"> ' + item.pwaysName + '\n' +
                        '</label>';
                }
            });
            $('#orderTypeShow').html(str);
            $('#approvalProcessShow').html(processStr);
            $("#svcRegisterInfo").bootstrapValidator("addField", "orderType", { //对动态生成的表单添加校验
                validators: {
                    notEmpty: {
                        message: '请选择订购方式'
                    }
                }
            });
            $("#svcRegisterInfo").bootstrapValidator("addField", "pwaysId", {
                validators: {
                    notEmpty: {
                        message: '请选择审批流程'
                    }
                }
            });

            /**
             * 下一步：价格配置
             */
            $('#orderTypeShow input[name="orderType"]').on('change',function () {
                /*$(this).val() == 10 ? $('#approvalProcessShow').parent().show() : $('#approvalProcessShow').parent().hide();*/
                if($(this).val() == 20){
                    $('#noPriceSubmit').css('display','none');
                    $('#toNextStep').css('display','block');
                }else{
                    $('#noPriceSubmit').css('display','block');
                    $('#toNextStep').css('display','none');
                }
            });
        }
    });
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
                $.each(data, function (index, item) {
                    if (item.typeStatus != '1') {
                        str += '<option value="' + item.productTypeId + '">' + item.productTypeName + '</option>';
                    }
                });
                $('select[name="productTypeId"]').html(str);
            }
        }
    });
}

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
            //服务基本信息校验
            productName: {
                validators: {
                    notEmpty: {
                        message: '请输入服务名称'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合命名，且不以下划线开头'
                    }
                }
            },
            productIcon: {
                validators: {
                    notEmpty: {
                        message: '请上传服务logo'
                    }
                }
            },
            productIntrod: {
                validators: {
                    notEmpty: {
                        message: '请输入服务描述'
                    }
                }
            },
            productId: {
                validators: {
                    notEmpty: {
                        message: '请输入服务编码'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合填写，且不以下划线开头'
                    }
                }
            },
            productVersion: {
                validators: {
                    notEmpty: {
                        message: '请输入服务版本'
                    }
                }
            },
            productTypeId: {
                validators: {
                    notEmpty: {
                        message: '请选择服务类型'
                    }
                }
            },
            apiAddr: {
                validators: {
                    notEmpty: {
                        message: '请输入服务地址'
                    }
                }
            },
            sortId: {
                validators: {
                    notEmpty: {
                        message: '请输入服务排序'
                    }
                }
            },
            //属性校验
            proName: {
                validators: {
                    notEmpty: {
                        message: '请输入属性名'
                    }
                }
            },
            proCode: {
                validators: {
                    notEmpty: {
                        message: '请输入属性编码'
                    }
                }
            },
            proLabel: {
                validators: {
                    notEmpty: {
                        message: '请选择属性标签'
                    }
                }
            },
            //依赖服务校验
            relyOnOrder: {
                validators: {
                    notEmpty: {
                        message: '请填写顺序'
                    }
                }
            }
        }
    });
}

// 属性列表
function getRegAttrListTable() {
    if (svcRegisterAttrArr.length > 0) {
        var str = '';
        $.each(svcRegisterAttrArr, function (index, item) {
            var arr = [];
            $.each(item.proLabel.split(','), function (i, it) {
                arr.push(proLabelConvert(it));
            });

            var rowData = JSON.stringify(item);
            rowData = escape(rowData);
            var escapeflag = true;

            str += '<tr>\n' +
                '   <td>' + item.proName + '</td>\n' +
                '   <td>' + item.proEnName + '</td>\n' +
                '   <td>' + formItemConvert(item.proShowType) + '</td>\n' +
                '   <td>' + basicTypeConvert(item.proDataType) + '</td>\n' +
                '   <td>' + item.proInitValue + '</td>\n' +
                '   <td>' + item.proVerfyRule + '</td>\n' +
                '   <td>' + item.proVerfyTips + '</td>\n' +
                '   <td>' + item.proDesc + '</td>\n' +
                '   <td>' + item.proUnit + '</td>\n' +
                '   <td>' + arr.join(',') + '</td>\n' +
                '   <td>\n' +
                '       <span class="glyphicon glyphicon-edit icon-hover" title="编辑" data-toggle="modal" data-target="#attrEditModal" onclick="handleEditAttr(\'' + rowData + '\',\'' + index + '\',\'' + escapeflag + '\')"></span>\n' +
                '       <span class="glyphicon glyphicon-trash icon-hover" title="删除" onclick="deleteAttr(\'' + index + '\')"></span>\n' +
                '   </td>\n' +
                '</tr>';
        });
        $('#regAttrListTable tbody').html(str);
    } else {
        $('#regAttrListTable tbody').html('');
    }
}

// 依赖服务列表
function getRelySvcListTable() {
    if (relyOnAttrOrmArr.length > 0) {
        var str = '';
        $.each(relyOnAttrOrmArr, function (index, item) {
            var rowData = JSON.stringify(item);
            rowData = escape(rowData);
            var escapeflag = true;

            str += '<tr>\n' +
                '   <td>' + item.relyOnProductCode + '</td>\n' +
                '   <td>' + isConfRelyOnProsConvert(item.isConfRelyOnPros) + '</td>\n' +
                '   <td>' + isShowRelyOnProsConvert(item.isShowRelyOnPros) + '</td>\n' +
                '   <td>' + item.relyOnOrder + '</td>\n' +
                '   <td>\n' +
                '       <span class="glyphicon glyphicon-edit icon-hover" title="编辑" data-toggle="modal" data-target="#relySvcEditModal" onclick="handleEditRelySvc(\'' + rowData + '\',\'' + index + '\',\'' + escapeflag + '\')"></span>\n' +
                '       <span class="glyphicon glyphicon-trash icon-hover" title="删除" onclick="deleteRelySvc(\'' + index + '\')"></span>\n' +
                '   </td>\n' +
                '</tr>';
        });
        $('#relySvcListTable tbody').html(str);
    } else {
        $('#relySvcListTable tbody').html('');
    }
}

/**
 * 属性增删改
 */
// 重置新增model
function handleResetAddModel() {
    $('#svcAddAttrForm').data("bootstrapValidator").resetForm();
    $('#svcAddAttrForm')[0].reset();
    $('#initialUrl input[name="initialUrl"]').val('');
    attrInitialGrid.reload();
    $('#initialAddBox').css('display', 'none');
}

// 新增属性保存
function addAttrSave() {
    var attrFormData = formatFormData('#svcAddAttrForm');
    attrFormData.proLabel = getCheckboxData("#proLabelSelect :checkbox:checked");
    var initialTable = attrInitialGrid.getData();
    var len = initialTable.length - 1;
    for (var i = len; i >= 0; i--) {
        if (initialTable[i].key == '' || initialTable[i].value == '') {
            initialTable.splice(i, 1);
        }
    }
    attrFormData.initialTable = initialTable;
    attrFormData.initialUrl = $('#initialUrl input[name="initialUrl"]').val();

    if (attrFormData.proShowType == 10 || attrFormData.proShowType == 50) {
        attrFormData.proInitValue = '';
    } else if (attrFormData.proShowType == 20 || attrFormData.proShowType == 30 || attrFormData.proShowType == 40) {
        if ($('#initialUrl input[name="initialUrl"]').val() != '') {
            attrFormData.proInitValue = $('#initialUrl input[name="initialUrl"]').val();
            var reg = /(http|ftp|https):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/;
            if(!reg.test($('#initialUrl input[name="initialUrl"]').val())){
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('请输入正确的初始值地址');
                });
                return;
            }
        } else {
            var checkedNum = 0;
            var newArr = attrInitialGrid.getData();
            newArr.sort(function (a, b) {
                return Number(b.checked) - Number(a.checked);
            });
            var arr = [];
            for (var i = 0; i < newArr.length; i++) {
                var obj = {};
                if (newArr[i].key != '' && newArr[i].value != '') {
                    obj[newArr[i].key] = newArr[i].value;
                    arr.push(obj);
                }
                if (newArr[i].checked == true) {
                    checkedNum++;
                }
            }
            if (checkedNum > 1) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('属性初始值的默认项只能有一个为true！');
                });
                return
            }
            attrFormData.proInitValue = arr.length == 0 ? '' : JSON.stringify(arr);
        }
    }

    console.log(attrFormData, '新增属性------');

    $('#svcAddAttrForm').data('bootstrapValidator').validate();
    if ($('#svcAddAttrForm').data("bootstrapValidator").isValid()) {
        svcRegisterAttrArr.push(attrFormData);
        getRegAttrListTable();
        handleResetAddModel();
        $('#attrAddModal').modal('hide');
    }
}

// 打开编辑属性modal
function handleEditAttr(rowData, index, escapeflag) {
    if (escapeflag) {
        rowData = unescape(rowData);
    }
    var item = $.parseJSON(rowData);
    /*var proValueArr = getInitArray(item.proInitValue);
    $.each(proValueArr,function (index,item) {
        item.checked = '';
    });*/

    var str = '';
    str += '<div class="form-group col-md-6">\n' +
        '    <label class="label-width"><span class="red-font">＊</span>属性名</label>\n' +
        '    <input type="text" class="form-control input-width" name="proName" required value="' + item.proName + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">属性英文名</label>\n' +
        '    <input type="text" class="form-control input-width" name="proEnName" value="' + item.proEnName + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width"><span class="red-font">＊</span>属性编码</label>\n' +
        '    <input type="text" class="form-control input-width" name="proCode" required value="' + item.proCode + '">\n' +
        '</div>';

    str += '<div class="form-group col-md-6"><label class="label-width"><span class="red-font">＊</span>数据类型</label>';

    str += '<select class="form-control input-width" name="proDataType" required>';
    $.each(proDictionaryObj.proDataTypeArr,function (i, it) {
        if(item.proDataType == it.key) {
            str += '<option value="'+it.key+'" selected>'+it.value+'</option>';
        }else {
            str += '<option value="'+it.key+'">'+it.value+'</option>';
        }
    });
    str += '</select>';

    str += '</div><div class="form-group col-md-6"><label class="label-width"><span class="red-font">＊</span>控件类型</label>';

    str += '<select class="form-control input-width" name="proShowType" required>';
    $.each(proDictionaryObj.proShowTypeArr,function (i, it) {
        if(item.proShowType == it.key) {
            str += '<option value="'+it.key+'" selected>'+it.value+'</option>';
        }else {
            str += '<option value="'+it.key+'">'+it.value+'</option>';
        }
    });
    str += '</select>';

    str += '</div>';

    str += '<div class="form-group col-md-6">\n' +
        '    <label class="label-width"><span class="red-font">＊</span>属性标签</label>\n' +
        '    <span class="input-width" id="proLabelSelect1">\n' +
        '        <label class="checkbox-inline">\n' +
        '            <input type="checkbox" value="10" name="proLabel">申请\n' +
        '        </label>\n' +
        '        <label class="checkbox-inline">\n' +
        '            <input type="checkbox" value="20" name="proLabel">访问\n' +
        '        </label>\n' +
        '        <label class="checkbox-inline">\n' +
        '            <input type="checkbox" value="30" name="proLabel">资源\n' +
        '        </label>\n' +
        '    </span>\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">属性单位</label>\n' +
        '    <input type="text" class="form-control input-width" name="proUnit" value="' + item.proUnit + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">属性描述</label>\n' +
        '    <input type="text" class="form-control input-width" name="proDesc" value="' + item.proDesc + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">校验规则</label>\n' +
        '    <input type="text" class="form-control input-width" name="proVerfyRule" value="' + item.proVerfyRule + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">校验提示</label>\n' +
        '    <input type="text" class="form-control input-width" name="proVerfyTips" value="' + item.proVerfyTips + '">\n' +
        '</div>';

    $('#svcEditAttrForm').html(str);

    $("#svcEditAttrForm").bootstrapValidator("addField", "proName", { //对动态生成的表单添加校验
        validators: {notEmpty: {message: '请输入属性名'}}
    });
    $("#svcEditAttrForm").bootstrapValidator("addField", "proCode", {
        validators: {notEmpty: {message: '请输入属性编码'}}
    });
    $("#svcEditAttrForm").bootstrapValidator("addField", "proLabel", {
        validators: {notEmpty: {message: '请选择属性标签'}}
    });

    //设置复选框的值
    var checkBoxVal = item.proLabel;
    var checkBoxArray = checkBoxVal.split(",");
    for (var i = 0; i < checkBoxArray.length; i++) {
        $("#proLabelSelect1 :checkbox").each(function () {
            if ($(this).val() == checkBoxArray[i]) {
                $(this).attr("checked", true);
            }
        })
    }

    $('#initialEditUrl input[name="initialUrl"]').attr('value',item.initialUrl);
    attrInitialEditGrid = $("#attrInitialEditTable").ligerGrid({
        columns: [
            {name: 'checked', display: '默认项', width: '150', editor: {type: 'checkbox'}},
            {name: 'key', display: '键值', width: '150', editor: {type: 'text'}},
            {name: 'value', display: '显示项', width: '150', editor: {type: 'text'}},
            {
                display: '操作', width: "150", align: 'center',
                render: function (rowdata, rowindex, value) {
                    var h = "";
                    if (!rowdata._editing)
                    {
                        h += "<a href='javascript:attrInitialEditGrid.beginEdit(" + rowindex + ")' style='padding-right: 3px'>修改</a>";
                        h += "<a href='javascript:attrInitialEditGrid.deleteRow(" + rowindex + ")'>删除</a>";
                    }
                    else
                    {
                        h += "<a href='javascript:attrInitialEditGrid.endEdit(" + rowindex + ")' style='padding-right: 3px'>提交</a>";
                        h += "<a href='javascript:attrInitialEditGrid.cancelEdit(" + rowindex + ")'>取消</a>";
                    }
                    return h;
                }
            }],
        //width: '100%',
        //data: { Rows: proValueArr},
        data: {Rows: item.initialTable},
        enabledEdit: true,
        clickToEdit:false,
        usePager: false
    });

    var btnStr = '<button type="button" class="btn btn-default" data-dismiss="modal"\n' +
        '    onclick="handleResetForm(\'#svcEditAttrForm\')">取消\n' +
        '</button>\n' +
        '<button type="button" class="btn btn-primary" onclick="editAttrSave(\'' + index + '\')">保存</button>';
    $('#attrEditModal .modal-footer').html(btnStr);

    item.proShowType == 10 || item.proShowType == 50 ? $('#initialEditBox').hide() : $('#initialEditBox').show();
    $('#svcEditAttrForm select[name="proShowType"]').on('change', function () {
        $(this).val() == 10 || $(this).val() == 50 ? $('#initialEditBox').hide() : $('#initialEditBox').show();
    });

    $('a#initialParamA1').on("click", function () {
        $('#initialEditUrl input[name="initialUrl"]').val('');
    });
    $('a#initialUrlA1').on("click", function () {
        attrInitialEditGrid.reload();
    });
}

// 修改属性保存
function editAttrSave(__index) {
    console.log(__index, '__index');
    var attrFormData = formatFormData('#svcEditAttrForm');
    attrFormData.proLabel = getCheckboxData("#proLabelSelect1 :checkbox:checked");

    var initialTable = attrInitialEditGrid.getData();
    var len = initialTable.length - 1;
    for (var i = len; i >= 0; i--) {
        if (initialTable[i].key == '' || initialTable[i].value == '') {
            initialTable.splice(i, 1);
        }
    }
    attrFormData.initialTable = initialTable;

    if (attrFormData.proShowType == 10 || attrFormData.proShowType == 50) {
        attrFormData.proInitValue = '';
    } else if (attrFormData.proShowType == 20 || attrFormData.proShowType == 30 || attrFormData.proShowType == 40) {
        if ($('#initialEditUrl input[name="initialUrl"]').val() != '') {
            attrFormData.proInitValue = $('#initialEditUrl input[name="initialUrl"]').val();
            var reg = /(http|ftp|https):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/;
            if(!reg.test($('#initialEditUrl input[name="initialUrl"]').val())){
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('请输入正确的初始值地址');
                });
                return;
            }
        } else {
            var checkedNum = 0;
            var newArr = attrInitialEditGrid.getData();
            newArr.sort(function (a, b) {
                return Number(b.checked) - Number(a.checked);
            });
            var arr = [];
            for (var i = 0; i < newArr.length; i++) {
                var obj = {};
                if (newArr[i].key != '' && newArr[i].value != '') {
                    obj[newArr[i].key] = newArr[i].value;
                    arr.push(obj);
                }
                if (newArr[i].checked == true) {
                    checkedNum++;
                }
            }
            if (checkedNum > 1) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('属性初始值的默认项只能有一个为true！');
                });
                return
            }
            attrFormData.proInitValue = arr.length == 0 ? '' : JSON.stringify(arr);
        }
    }

    console.log(attrFormData, '修改属性------');

    $('#svcEditAttrForm').data('bootstrapValidator').validate();
    if ($('#svcEditAttrForm').data("bootstrapValidator").isValid()) {
        for (var i = 0; i < svcRegisterAttrArr.length; i++) {
            if (i == __index) {
                svcRegisterAttrArr[i] = attrFormData;
                break;
            }
        }
        console.log(svcRegisterAttrArr, 'svcRegisterAttrArr修改后');
        getRegAttrListTable();
        handleResetForm('#svcEditAttrForm');
        $('#attrEditModal').modal('hide');
    }
}

// 删除属性
function deleteAttr(index) {
    for (var i = 0; i < svcRegisterAttrArr.length; i++) {
        if (i == index) {
            svcRegisterAttrArr.splice(index, 1);
            break;
        }
    }
    getRegAttrListTable();
}



/**
 * 依赖服务增删改
 */
// 添加依赖服务保存
function saveRelySvcChecked(index) {
    var arr = getCheckboxData("#relySvcCheckedTable :checkbox:checked").split(',');
    console.log(arr,'勾选的服务编码');

    var repeatArr=[];
    for(var i in arr){
        for(var j in relyOnAttrOrmArr){
            if(arr[i]==relyOnAttrOrmArr[j].relyOnProductCode){
                repeatArr.push(arr[i]);
            }
        }
    }
    console.log(repeatArr,'重复勾选的服务');

    if(repeatArr.length == 0) {
        $.each(arr,function (index,item) {
            if(item) {
                var obj = {};
                obj.relyOnProductCode = item;
                obj.isShowRelyOnPros = '10'; //是否展示实例
                obj.isConfRelyOnPros = '20'; //是否默认配置
                obj.relyOnOrder = index +1;
                relyOnAttrOrmArr.push(obj);
            }
        });
        console.log(relyOnAttrOrmArr,'relyOnAttrOrmArr');
        getRelySvcListTable();
        $('#relySvcCheckedTable :checkbox').prop('checked',false);
        $('#relySvcCheckedModal').modal('hide');
    }else{
        var msgStr = repeatArr.join(',');
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.msg(msgStr+'重复勾选！');
        });
    }
}

// 打开编辑依赖服务modal
function handleEditRelySvc(rowData, index, escapeflag) {
    if (escapeflag) {
        rowData = unescape(rowData);
    }
    var item = $.parseJSON(rowData);

    var str = '<div class="form-group">\n' +
        '    <label class="col-md-3 control-label"><span class="red-font">＊</span>服务编码</label>\n' +
        '    <div class="col-md-8">\n' +
        '        <input type="text" class="form-control" name="relyOnProductCode" value="'+item.relyOnProductCode+'" readonly>\n' +
        '    </div>\n' +
        '</div>';
    str += '<div class="form-group">\n' +
        '    <label class="col-md-3 control-label"><span class="red-font">＊</span>是否默认配置</label>\n' +
        '    <div class="col-md-8">\n' +
        '        <select class="form-control" name="isConfRelyOnPros" required>\n' +
        '            <option value="20" '+( item.isConfRelyOnPros == 20 ? "selected" : "" )+'>是</option>\n' +
        '            <option value="10" '+( item.isConfRelyOnPros == 10 ? "selected" : "" )+'>否</option>\n' +
        '        </select>\n' +
        '    </div>\n' +
        '</div>';
    str += '<div class="form-group">\n' +
        '    <label class="col-md-3 control-label"><span class="red-font">＊</span>是否展示实例</label>\n' +
        '    <div class="col-md-8">\n' +
        '        <select class="form-control" name="isShowRelyOnPros" required>\n' +
        '            <option value="10" '+( item.isShowRelyOnPros == 10 ? "selected" : "" )+'>是</option>\n' +
        '            <option value="20" '+( item.isShowRelyOnPros == 20 ? "selected" : "" )+'>否</option>\n' +
        '        </select>\n' +
        '    </div>\n' +
        '</div>';
    str += '<div class="form-group">\n' +
        '    <label class="col-md-3 control-label"><span class="red-font">＊</span>顺序</label>\n' +
        '    <div class="col-md-8">\n' +
        '        <input type="number" class="form-control" style="padding-right:12px" name="relyOnOrder" min="0" value="'+item.relyOnOrder+'" required>\n' +
        '    </div>\n' +
        '</div>';

    $('#relySvcEditForm').html(str);

    $("#relySvcEditForm").bootstrapValidator("addField", "relyOnOrder", {
        validators: {notEmpty: {message: '顺序不能为空'}}
    });

    var btnStr = '<button type="button" class="btn btn-default" data-dismiss="modal"\n' +
        '    onclick="handleResetForm(\'#relySvcEditForm\')">取消\n' +
        '</button>\n' +
        '<button type="button" class="btn btn-primary" onclick="editRelySvcSave(\'' + index + '\')">保存</button>';
    $('#relySvcEditModal .modal-footer').html(btnStr);
}

// 修改依赖服务保存
function editRelySvcSave(index) {
    var relySvcFormData = formatFormData('#relySvcEditForm');
    console.log(relySvcFormData);
    for (var i = 0; i < relyOnAttrOrmArr.length; i++) {
        if (i == index) {
            relyOnAttrOrmArr[i] = relySvcFormData;
            break;
        }
    }
    console.log(relyOnAttrOrmArr, 'relyOnAttrOrmArr修改后');
    $('#relySvcEditForm').data('bootstrapValidator').validate();
    if ($('#relySvcEditForm').data("bootstrapValidator").isValid()){
        getRelySvcListTable();
        handleResetForm('#relySvcEditForm');
        $('#relySvcEditModal').modal('hide');
    }
}

// 删除依赖服务
function deleteRelySvc(index) {
    for (var i = 0; i < relyOnAttrOrmArr.length; i++) {
        if (i == index) {
            relyOnAttrOrmArr.splice(index, 1);
            break;
        }
    }
    getRelySvcListTable();
}


// 暂存及注册服务
function handleSvcRegister(productStatus) {
    $('#svcRegisterInfo').data('bootstrapValidator').validate();

    var product = formatFormData('#svcRegisterInfo');
    product.productStatus = productStatus;
    product.productAttrOrm = svcRegisterAttrArr;
    product.relyOnAttrOrm = relyOnAttrOrmArr;

    console.log(product,'product');

    var file_obj = document.getElementById('productIcon').files[0];
    var fd = new FormData();
    fd.append('product', JSON.stringify(product));
    fd.append('productIcon', file_obj);

    if(Math.round(file_obj.size/1024*100)/100 >= 200){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.msg('上传图片过大，请上传小于200kb的图片！');
        });
        return;
    }
    console.log(fd, '保存服务---------------');


    // 校验服务编码是否唯一
    $.ajax({
        type: "POST",
        url: $webpath + "/api/product/productCodeVerfy/" + product.productId,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            if (data == false) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('服务编码重复，请重新填写！');
                });
            } else if (data == true) {
                if ($('#svcRegisterInfo').data('bootstrapValidator').isValid()) {
                    $.ajax({
                        type: 'POST',
                        data: fd,
                        processData: false,
                        contentType: false,
                        url: $webpath + "/api/product/resgProduct",
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 200) {
                                layui.use('layer', function(){
                                    var layer = layui.layer;
                                    layer.msg(result.message, {
                                        icon: 1,
                                        time: 2000
                                    }, function () {
                                        window.location.href = $webpath + '/product/page';
                                    });
                                });
                            } else {
                                layui.use('layer', function(){
                                    var layer = layui.layer;
                                    layer.msg(result.message, {icon: 2});
                                });
                            }
                        }
                    });
                }
            }
        }
    });
}


/**
 * 下一步：价格配置
 */
// 下一步
function toNextStep() {
    $('#svcRegisterInfo').data('bootstrapValidator').validate();
    if($('#svcRegisterInfo').data('bootstrapValidator').isValid()){
        $('#producBasicDeploy').css('display','none');
        $('#producPriceDeploy').css('display','block');
    }
    /*$('#producBasicDeploy').css('display','none');
    $('#producPriceDeploy').css('display','block');*/

    console.log(svcRegisterAttrArr,'下一步svcRegisterAttrArr');
    var priceStr = '';
    $.each(svcRegisterAttrArr,function (index,item) {
        if(item.proLabel.indexOf(30) !== -1) { //30 资源属性
            priceStr += setResPrice(item,index);
        }
        $('#proControlResPrice').html(priceStr);
    })
}

// 上一步
function toPreviousStep() {
    $('#producBasicDeploy').css('display','block');
    $('#producPriceDeploy').css('display','none');

    // 提交
    /*var priceFormData = formatFormData('#producPriceForm');
    console.log(priceFormData,'priceFormData');

    if(priceFormData.timeControl == 1) {
        var timeArr = [].concat(priceFormData.minTimeRange,priceFormData.maxTimeRange);
    }else{
        var timeValueArr = [];
        $.each(timeValueGrid.getData(),function (index,item) {
            timeValueArr.push({[""+item.value+""] : item.value});
        });
    }
    if(priceFormData.frequencyControl == 1) {
        var frequencyArr = [].concat(priceFormData.minFreqRange,priceFormData.maxFreqRange);
    }else{
        var frequencyValueArr = [];
        $.each(frequencyValueGrid.getData(),function (index,item) {
            frequencyValueArr.push({[""+item.value+""] : item.value});
        });
    }
    console.log(timeArr,frequencyArr,'timeArr');
    console.log(timeValueArr,frequencyValueArr,'123');

    $.each(svcRegisterAttrArr,function (index,item) {
        if(item.proLabel.indexOf(30) !== -1) { //30 资源属性
            if(item.proShowType == 10 || item.proShowType == 50) {
                item.proChargePrice = priceFormData['proChargePrice['+index+']'];
            }else if(item.proShowType == 20 || item.proShowType == 30 || item.proShowType == 40) {
                var priceItem = {};
                var proChargePrice = [];
                $.each(getInitArray(item.proInitValue),function (i,it) {
                    priceItem = it;
                    priceItem.price = priceFormData['proChargePrice['+index+'].price['+i+']'];
                    proChargePrice.push(priceItem);
                });
                item.proChargePrice = proChargePrice;
            }
        }
    });

    console.log(svcRegisterAttrArr,'加入单价后');*/
}

