var proDictionaryObj = {
    proDataTypeArr : [
        {key: '10',value: 'byte'},
        {key: '20',value: 'short'},
        {key: '30',value: 'int'},
        {key: '40',value: 'long'},
        {key: '50',value: 'float'},
        {key: '60',value: 'double'},
        {key: '70',value: 'char'},
        {key: '80',value: 'boolean'}
    ],
    proShowTypeArr: [
        {key: '10',value: '文本框'},
        {key: '20',value: '下拉框'},
        {key: '30',value: '单选框'},
        {key: '40',value: '复选框'},
        {key: '50',value: 'Slider滑块'}
    ],
    reg : /(http|ftp|https):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/
};


// 重置表单
function handleResetForm(ele) {
    $(ele).data("bootstrapValidator").resetForm();
    $(ele)[0].reset();
}

// 格式表单提交数据
function formatFormData(ele) {
    var formData = {};
    var formArr = $(ele).serializeArray();
    $.each(formArr, function (index, item) {
        formData[item.name] = item.value;
    });
    return formData;
}

// 获取复选框的值
function getCheckboxData(ele) {
    var arr = [];
    $(ele).each(function (i) {
        arr[i] = $(this).val();
    });
    return arr.join(",");
}

// 将类似"[{"MS":"MS"},{"MMS":"MMS"}]"转换成[{key: "MS", value: "MS"},{key: "MMS", value: "MMS"}]
function getInitArray( initValue ) {
    var newArr = [];
    if(JSON.parse(initValue) instanceof Array) {
        $.each(JSON.parse(initValue),function (index, item) {
            for(var key in item) {
                newArr.push({'key': key,'value': item[key]});
            }
        });
    }
    return newArr;
}


/**
 * 属性控件
 */
function getInput(inputVal,index,proLabel){
    var inputStr = '',
        unitStr = inputVal.proUnit && inputVal.proUnit != "null" ? "<span class='attr-unit' title='"+inputVal.proUnit+"'>"+inputVal.proUnit+"</span>" : '',
        proValue = inputVal.proValue && inputVal.proValue != "null" ? inputVal.proValue : '',
        proVerfyRule;
    if(inputVal.proVerfyRule == "not null") {
        proVerfyRule = "required";
    }else{
        proVerfyRule = "pattern='"+(inputVal.proVerfyRule ? inputVal.proVerfyRule : "")+"'";
    }

    if(proLabel == 30) {
        inputStr += '<div class="box-set form-group">';
        inputStr += '<span class="attr-name" title="'+inputVal.proName+'">'+inputVal.proName+'：</span>';
        inputStr += '<input type="text" class="form-control input-set" value="'+proValue+'" name="packageControlResAttrOrm['+index+'].proValue" '+proVerfyRule+' data-bv-regexp-message="'+inputVal.proVerfyTips+'">';
    }else {

        inputStr += inputVal.proLabel.indexOf(10) != -1 && inputVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group" style="display: none">' : '<div class="box-set form-group">';
        if(inputVal.proIsFixed) {
            inputStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            if(inputVal.proIsFixed == 10) {
                inputStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10" checked>';
            }else {
                inputStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
            }
        }else {
            inputStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            inputStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
        }
        inputStr += '<span class="attr-name" title="'+inputVal.proName+'">'+inputVal.proName+'：</span>';
        inputStr += '<input type="text" class="form-control input-set" value="'+proValue+'" name="packageBasicAttrOrm['+index+'].proValue" '+proVerfyRule+' data-bv-regexp-message="'+inputVal.proVerfyTips+'">';
    }

    inputStr += unitStr+'</div>';
    return inputStr;
}


function getSelect(selectVal,index,proLabel){
    var selectStr = '',
        unitStr = selectVal.proUnit && selectVal.proUnit != "null" ? "<span class='attr-unit' title='"+selectVal.proUnit+"'>"+selectVal.proUnit+"</span>" : '',
        proValue = selectVal.proValue && selectVal.proValue != "null" ? selectVal.proValue : '';

    if(proLabel == 30) {
        selectStr += '<div class="box-set form-group">';
        selectStr += '<span class="attr-name" title="'+selectVal.proName+'">'+selectVal.proName+'：</span>';
        selectStr += '<select name="packageControlResAttrOrm['+index+'].proValue" class="form-control input-set">';
    }else{

        selectStr += selectVal.proLabel.indexOf(10) != -1 && selectVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group" style="display: none">' : '<div class="box-set form-group">';
        if(selectVal.proIsFixed) {
            selectStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            if(selectVal.proIsFixed == 10) {
                selectStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10" checked>';
            }else {
                selectStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
            }
        }else {
            selectStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            selectStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
        }
        selectStr += '<span class="attr-name" title="'+selectVal.proName+'">'+selectVal.proName+'：</span>';
        selectStr += '<select name="packageBasicAttrOrm['+index+'].proValue" class="form-control input-set">';
    }

    if(selectVal.proInitValue) {
        if(proDictionaryObj.reg.test(selectVal.proInitValue)) {
            selectStr += '<option value="" style="display:none;"></option>';
        }else{
            $.each(getInitArray(selectVal.proInitValue), function (index, item) {
                if(proValue && item.key == proValue) {
                    selectStr += '<option value="'+item.key+'" selected>'+item.value+'</option>';
                }else {
                    selectStr += '<option value="'+item.key+'">'+item.value+'</option>';
                }
            });
        }
    }else{
        selectStr += '<option value="" style="display:none;"></option>';
    }

    selectStr += '</select>'+ unitStr +'</div>';
    return selectStr;
}


function getRadio(radioVal,index,proLabel){
    var radioStr = '',
        unitStr = radioVal.proUnit && radioVal.proUnit != "null" ? "<span class='attr-unit' title='"+radioVal.proUnit+"'>"+radioVal.proUnit+"</span>" : '',
        proValue = radioVal.proValue && radioVal.proValue != "null" ? radioVal.proValue : '';

    if(proLabel == 30) {
        radioStr += '<div class="box-set form-group">';
        radioStr += '<span class="attr-name" title="'+radioVal.proName+'">'+radioVal.proName+'：</span>';
        radioStr += '<div class="input-set">';
        radioStr += '<input type="hidden" name="packageControlResAttrOrm['+index+'].proValue" value="'+proValue+'">';
        if(radioVal.proInitValue) {
            if(proDictionaryObj.reg.test(radioVal.proInitValue)) {
                radioStr += '<input type="text" class="form-control" name="packageControlResAttrOrm['+index+'].proValue" value="" readonly>';
            }else{
                $.each(getInitArray(radioVal.proInitValue), function (i, item) {
                    radioStr += '<label class="radio-inline">';
                    if(proValue && item.key == proValue) {
                        radioStr += '<input type="radio" name="packageControlResAttrOrm['+index+'].proValue" value="'+item.key+'" checked>'+item.value;
                    }else {
                        radioStr += '<input type="radio" name="packageControlResAttrOrm['+index+'].proValue" value="'+item.key+'">'+item.value;
                    }
                    radioStr += '</label>';
                });
            }
        }else{
            radioStr += '<input type="hidden" name="packageControlResAttrOrm['+index+'].proValue" value="">';
        }

    }else{

        radioStr += radioVal.proLabel.indexOf(10) != -1 && radioVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group" style="display: none">' : '<div class="box-set form-group">';
        if(radioVal.proIsFixed) {
            radioStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            if(radioVal.proIsFixed == 10) {
                radioStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10" checked>';
            }else {
                radioStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
            }
        }else {
            radioStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            radioStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
        }
        radioStr += '<span class="attr-name" title="'+radioVal.proName+'">'+radioVal.proName+'：</span>';
        radioStr += '<div class="input-set">';
        radioStr += '<input type="hidden" name="packageBasicAttrOrm['+index+'].proValue" value="'+proValue+'">';
        if(radioVal.proInitValue) {
            if(proDictionaryObj.reg.test(radioVal.proInitValue)) {
                radioStr += '<input type="text" class="form-control" name="packageBasicAttrOrm['+index+'].proValue" value="" readonly>';
            }else{
                $.each(getInitArray(radioVal.proInitValue), function (i, item) {
                    radioStr += '<label class="radio-inline">';
                    if(proValue && item.key == proValue) {
                        radioStr += '<input type="radio" name="packageBasicAttrOrm['+index+'].proValue" value="'+item.key+'" checked>'+item.value;
                    }else{
                        radioStr += '<input type="radio" name="packageBasicAttrOrm['+index+'].proValue" value="'+item.key+'">'+item.value;
                    }
                    radioStr += '</label>';
                });
            }
        }else{
            radioStr += '<input type="hidden" name="packageBasicAttrOrm['+index+'].proValue" value="">';
        }
    }

    radioStr += '</div>'+ unitStr + '</div>';
    return radioStr;
}


function getCheckbox(checkboxVal,index,proLabel){
    var checkboxStr = '',
        unitStr = checkboxVal.proUnit && checkboxVal.proUnit != "null" ? "<span class='attr-unit' title='"+checkboxVal.proUnit+"'>"+checkboxVal.proUnit+"</span>" : '',
        proValue = checkboxVal.proValue && checkboxVal.proValue != "null" ? checkboxVal.proValue : '';

    if(proLabel == 30) {
        checkboxStr += '<div class="box-set form-group">';
        checkboxStr += '<span class="attr-name" title="'+checkboxVal.proName+'">'+checkboxVal.proName+'：</span>';
        checkboxStr += '<div class="input-set proCheckControl" data-index="'+index+'">';
        checkboxStr += '<input type="hidden" name="packageControlResAttrOrm['+index+'].proValue" value="'+proValue+'">';
        if(checkboxVal.proInitValue) {
            if(proDictionaryObj.reg.test(checkboxVal.proInitValue)) {
                checkboxStr += '<input type="text" class="form-control" name="packageControlResAttrOrm['+index+'].proValue" value="" readonly>';
            }else{
                $.each(getInitArray(checkboxVal.proInitValue), function (i, item) {
                    checkboxStr += '<label class="checkbox-inline">';
                    if(proValue && proValue.indexOf(item.key) != -1) {
                        checkboxStr += '<input type="checkbox" name="packageControlResAttrOrm['+index+'].proValue" value="'+item.key+'" checked>'+item.value;
                    }else{
                        checkboxStr += '<input type="checkbox" name="packageControlResAttrOrm['+index+'].proValue" value="'+item.key+'">'+item.value;
                    }
                    checkboxStr += '</label>';
                });
            }
        }else{
            checkboxStr += '<input type="hidden" name="packageControlResAttrOrm['+index+'].proValue" value="">';
        }

    }else{

        checkboxStr += checkboxVal.proLabel.indexOf(10) != -1 && checkboxVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group" style="display: none">' : '<div class="box-set form-group">';
        if(checkboxVal.proIsFixed) {
            checkboxStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            if(checkboxVal.proIsFixed == 10) {
                checkboxStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10" checked>';
            }else {
                checkboxStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
            }
        }else {
            checkboxStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            checkboxStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
        }
        checkboxStr += '<span class="attr-name" title="'+checkboxVal.proName+'">'+checkboxVal.proName+'：</span>';
        checkboxStr += '<div class="input-set proCheckControl" data-index="'+index+'">';
        checkboxStr += '<input type="hidden" name="packageBasicAttrOrm['+index+'].proValue" value="'+proValue+'">';
        if(checkboxVal.proInitValue) {
            if(proDictionaryObj.reg.test(checkboxVal.proInitValue)) {
                checkboxStr += '<input type="text" class="form-control" name="packageBasicAttrOrm['+index+'].proValue" value="" readonly>';
            }else{
                $.each(getInitArray(checkboxVal.proInitValue), function (i, item) {
                    checkboxStr += '<label class="checkbox-inline">';
                    if(proValue && proValue.indexOf(item.key) != -1) {
                        checkboxStr += '<input type="checkbox" name="packageBasicAttrOrm['+index+'].proValue" value="'+item.key+'" checked>'+item.value;
                    }else{
                        checkboxStr += '<input type="checkbox" name="packageBasicAttrOrm['+index+'].proValue" value="'+item.key+'">'+item.value;
                    }
                    checkboxStr += '</label>';
                });
            }
        }else{
            checkboxStr += '<input type="hidden" name="packageBasicAttrOrm['+index+'].proValue" value="">';
        }
    }

    checkboxStr += '</div>'+ unitStr +'</div>';
    return checkboxStr;
}


function getSlider(sliderVal,index,proLabel) {
    var sliderStr = '',
        unitStr = sliderVal.proUnit && sliderVal.proUnit != "null" ? "<span class='attr-unit' title='"+sliderVal.proUnit+"'>"+sliderVal.proUnit+"</span>" : '',
        proValue = sliderVal.proValue && sliderVal.proValue != "null" ? sliderVal.proValue : '';

    if(proLabel == 30) {
        sliderStr += '<div class="box-set form-group">';
        sliderStr += '<span class="attr-name" title="'+sliderVal.proName+'">'+sliderVal.proName+'：</span>';
        sliderStr += '<div class="input-set">' +
            '<input name="packageControlResAttrOrm['+index+'].proValue" value="'+proValue+'" class="easyui-slider proSlider" data-options="showTip:true">' +
            '</div>';
    }else {

        sliderStr += sliderVal.proLabel.indexOf(10) != -1 && sliderVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group" style="display: none">' : '<div class="box-set form-group">';
        if(sliderVal.proIsFixed) {
            sliderStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            if(sliderVal.proIsFixed == 10) {
                sliderStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10" checked>';
            }else {
                sliderStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
            }
        }else {
            sliderStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="hidden" value="20">';
            sliderStr += '<input name="packageBasicAttrOrm['+index+'].proIsFixed" type="checkbox" class="checked-set" value="10">';
        }
        sliderStr += '<span class="attr-name" title="'+sliderVal.proName+'">'+sliderVal.proName+'：</span>';
        sliderStr += '<div class="input-set">' +
            '<input name="packageBasicAttrOrm['+index+'].proValue" value="'+proValue+'" class="easyui-slider proSlider" data-options="showTip:true">' +
            '</div>';
    }

    sliderStr += unitStr+'</div>';
    return sliderStr;
}

function getForms( val,index,proLabel ) {
    switch ( Number(val.proShowType) ) {
        case 10:
            return getInput( val,index,proLabel );
        case 20:
            return getSelect( val,index,proLabel );
        case 30:
            return getRadio( val,index,proLabel );
        case 40:
            return getCheckbox( val,index,proLabel );
        case 50:
            return getSlider( val,index,proLabel );
    }
}


/**
 * 属性单价配置
 */
function setOnePrice(val,index) {
    var str = '<div class="form-group" style="margin: 2% 0 0 4%;">\n' +
        '    <label class="pro-width">'+val.proName+'：</label>\n' +
        '    <span>单价：</span>\n' +
        '    <input type="number" min="0" class="form-control price-input" name="proChargePrice['+index+']">\n' +
        '</div>';
    return str;
}

function setMuchPrice(val,index) {
    var str = '<div class="form-group" style="margin: 2% 0 0 4%;">\n'+
        '<label class="pro-width" style="vertical-align: top;">'+val.proName+'：</label>\n' +
        '    <ul class="much-price-box">';

    if(val.proInitValue) {
        $.each(getInitArray(val.proInitValue), function (i, item) {
            str += '<li>\n' +
                '    <label class="pro-width" style="font-weight: normal;">'+item.value+'</label>\n' +
                '    <span>单价：</span>\n' +
                '    <input type="number" min="0" class="form-control price-input" name="proChargePrice['+index+'].price['+i+']">\n' +
                '</li>';
        });
    }

    str += '</ul></div>';
    return str;
}

function setResPrice(val,index) {
    switch ( Number(val.proShowType) ) {
        case 10:
            return setOnePrice( val,index );
        case 20:
            return setMuchPrice( val,index );
        case 30:
            return setMuchPrice( val,index );
        case 40:
            return setMuchPrice( val,index );
        case 50:
            return setOnePrice( val,index );
    }
}






/**
 * 属性折扣配置
 */
function setOneDiscount(val,index) {
    var str = '<div class="form-group" style="margin: 2% 0 0 4%;">\n' +
        '    <label class="pro-width">'+val.proName+'：</label>\n' +
        '    <span>单价：1000</span>\n' +
        '    <div class="checkbox" style="display: inline-block;margin: 0 10px">\n' +
        '        <label>\n' +
        '          <input type="checkbox">设置折扣\n' +
        '        </label>\n' +
        '    </div>\n'+
        '    <div class="newPrice" style="display: none">\n'+
        '        <span>现价：</span>\n' +
        '        <input type="number" min="0" class="form-control discount-input" name="">\n' +
        '    </div>\n'+
        '</div>';
    return str;
}

function setMuchDiscount(val,index) {
    var str = '<div class="form-group" style="margin: 2% 0 0 4%;">\n'+
        '<label class="pro-width" style="vertical-align: top;">'+val.proName+'：</label>\n' +
        '    <ul class="much-price-box">';

    if(val.proInitValue) {
        $.each(getInitArray(val.proInitValue), function (i, item) {
            str += '<li>\n' +
                '    <label class="pro-width" style="font-weight: normal;">'+item.value+'</label>\n' +
                '    <span>单价：1000</span>\n' +
                '    <div class="checkbox" style="display: inline-block;margin: 0 10px">\n' +
                '        <label>\n' +
                '          <input type="checkbox">设置折扣\n' +
                '        </label>\n' +
                '    </div>\n'+
                '    <div class="newPrice" style="display: none">\n'+
                '        <span>现价：</span>\n' +
                '        <input type="number" min="0" class="form-control discount-input" name="">\n' +
                '    </div>\n'+
                '</li>';
        });
    }

    str += '</ul></div>';
    return str;
}

function setResDiscount(val,index) {
    switch ( Number(val.proShowType) ) {
        case 10:
            return setOneDiscount( val,index );
        case 20:
            return setMuchDiscount( val,index );
        case 30:
            return setMuchDiscount( val,index );
        case 40:
            return setMuchDiscount( val,index );
        case 50:
            return setOneDiscount( val,index );
    }
}