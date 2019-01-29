<%@ page pageEncoding="UTF-8" %>
<script type="text/javascript">
    var proDictionaryObj = {
        reg : /(http|ftp|https):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/
    };

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

    // 将类似"[{"MS":"MS"},{"MMS":"MMS"}]"转换成[{text: "MS", value: "MS"},{text: "MMS", value: "MMS"}]
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
     * 产品详情-属性控件
     */
    function getOrderInput(inputVal, index, proLabel) {
        var inputStr = '',
            unitStr = inputVal.proUnit && inputVal.proUnit != "null" ? "<span class='attr-unit' title='" + inputVal.proUnit + "'>" + inputVal.proUnit + "</span>" : '',
            proValue = inputVal.proValue && inputVal.proValue != "null" ? inputVal.proValue : '',
            proVerfyRule;
        if (inputVal.proVerfyRule == "not null") {
            proVerfyRule = "required";
        } else {
            proVerfyRule = "pattern='" + (inputVal.proVerfyRule ? inputVal.proVerfyRule : "") + "'";
        }

        if (proLabel == 30) {
            inputStr += '<div class="box-set form-group col-md-6">';
            inputStr += '<span class="attr-name" title="' + inputVal.proName + '"><span style="color: red">*</span>' + inputVal.proName + '：</span>';
            inputStr += '<input type="text" class="form-control input-set customSetClass form-child" value="' + proValue + '" name="orderControlResAttrOrm[' + index + '].proValue" '+proVerfyRule+' data-bv-regexp-message="'+inputVal.proVerfyTips+'">';
            //inputStr += '<input type="text" class="form-control input-set customSetClass" value="' + proValue + '" name="orderControlResAttrOrm[' + index + '].proValue" pattern="^[0-9]$" data-bv-regexp-message="输入错误">';
        } else {

            inputStr += inputVal.proLabel.indexOf(10) != -1 && inputVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group col-md-6" style="display: none">' : '<div class="box-set form-group col-md-6">';
            inputStr += '<span class="attr-name" title="' + inputVal.proName + '">' + inputVal.proName + '：</span>';
            if(inputVal.proIsFixed == 10) {
                inputStr += '<input type="text" class="form-control input-set baseSetClass form-child" value="' + proValue + '" name="orderBasicAttrOrm[' + index + '].proValue" readonly>';
            }else{
                inputStr += '<input type="text" class="form-control input-set baseSetClass form-child" value="' + proValue + '" name="orderBasicAttrOrm[' + index + '].proValue" '+proVerfyRule+' data-bv-regexp-message="'+inputVal.proVerfyTips+'">';
            }

        }

        inputStr += unitStr + '</div>';
        return inputStr;
    }


    function getOrderSelect(selectVal, index, proLabel) {
        var selectStr = '',
            unitStr = selectVal.proUnit && selectVal.proUnit != "null" ? "<span class='attr-unit' title='" + selectVal.proUnit + "'>" + selectVal.proUnit + "</span>" : '',
            proValue = selectVal.proValue && selectVal.proValue != "null" ? selectVal.proValue : '';

        if (proLabel == 30) {
            selectStr += '<div class="box-set form-group col-md-6">';
            selectStr += '<span class="attr-name" title="' + selectVal.proName + '"><span style="color: red">*</span>' + selectVal.proName + '：</span>';
            selectStr += '<select name="orderControlResAttrOrm[' + index + '].proValue" class="form-control input-set customSetClass form-child">';
        } else {

            selectStr += selectVal.proLabel.indexOf(10) != -1 && selectVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group col-md-6" style="display: none">' : '<div class="box-set form-group col-md-6">';
            selectStr += '<span class="attr-name" title="' + selectVal.proName + '">' + selectVal.proName + '：</span>';
            if(selectVal.proIsFixed == 10) {
                selectStr += '<select name="orderBasicAttrOrm[' + index + '].proValue" class="form-control input-set baseSetClass" onfocus="this.defaultIndex=this.selectedIndex;"\n' +
                    'onchange="this.selectedIndex=this.defaultIndex;" readonly>';
            }else{
                selectStr += '<select name="orderBasicAttrOrm[' + index + '].proValue" class="form-control input-set baseSetClass form-child">';
            }

        }

        if (selectVal.proInitValue) {
            if(proDictionaryObj.reg.test(selectVal.proInitValue)) {
                selectStr += '<option value="" style="display:none;"></option>';
            }else{
                $.each(getInitArray(selectVal.proInitValue), function (index, item) {
                    if (proValue && item.key == proValue) {
                        selectStr += '<option value="' + item.key + '" selected>' + item.value + '</option>';
                    } else {
                        selectStr += '<option value="' + item.key + '">' + item.value + '</option>';
                    }
                });
            }
        } else {
            selectStr += '<option value="" style="display:none;"></option>';
        }

        selectStr += '</select>' + unitStr + '</div>';
        return selectStr;
    }


    function getOrderRadio(radioVal, index, proLabel) {
        var radioStr = '',
            unitStr = radioVal.proUnit && radioVal.proUnit != "null" ? "<span class='attr-unit' title='" + radioVal.proUnit + "'>" + radioVal.proUnit + "</span>" : '',
            proValue = radioVal.proValue && radioVal.proValue != "null" ? radioVal.proValue : '';

        if (proLabel == 30) {
            radioStr += '<div class="box-set form-group col-md-6">';
            radioStr += '<span class="attr-name" title="' + radioVal.proName + '"><span style="color: red">*</span>' + radioVal.proName + '：</span>';
            radioStr += '<div class="input-set">';
            radioStr += '<input type="hidden" name="orderControlResAttrOrm[' + index + '].proValue" value="' + proValue + '">';
            if (radioVal.proInitValue) {
                if(proDictionaryObj.reg.test(radioVal.proInitValue)) {
                    radioStr += '<input type="text" class="form-control" name="orderControlResAttrOrm['+index+'].proValue" value="" readonly>';
                }else{
                    $.each(getInitArray(radioVal.proInitValue), function (i, item) {
                        radioStr += '<label class="radio-inline form-child">';
                        if (proValue && item.key == proValue) {
                            radioStr += '<input type="radio" name="orderControlResAttrOrm[' + index + '].proValue" value="' + item.key + '" checked>' + item.value;
                        } else {
                            radioStr += '<input type="radio" name="orderControlResAttrOrm[' + index + '].proValue" value="' + item.key + '">' + item.value;
                        }
                        radioStr += '</label>';
                    });
                }
            } else {
                radioStr += '<input type="hidden" name="orderControlResAttrOrm[' + index + '].proValue" value="">';
            }

        } else {

            radioStr += radioVal.proLabel.indexOf(10) != -1 && radioVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group col-md-6" style="display: none">' : '<div class="box-set form-group col-md-6">';
            radioStr += '<span class="attr-name" title="' + radioVal.proName + '">' + radioVal.proName + '：</span>';
            radioStr += '<div class="input-set">';
            radioStr += '<input type="hidden" name="orderBasicAttrOrm[' + index + '].proValue" value="' + proValue + '">';
            if (radioVal.proInitValue) {
                if(proDictionaryObj.reg.test(radioVal.proInitValue)) {
                    radioStr += '<input type="text" class="form-control" name="orderBasicAttrOrm['+index+'].proValue" value="" readonly>';
                }else{
                    $.each(getInitArray(radioVal.proInitValue), function (i, item) {
                        radioStr += '<label class="radio-inline form-child">';
                        if(radioVal.proIsFixed == 10) {
                            if (proValue && item.key == proValue) {
                                radioStr += '<input type="radio" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '" checked disabled>' + item.value;
                            } else {
                                radioStr += '<input type="radio" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '" disabled>' + item.value;
                            }
                        }else {
                            if (proValue && item.key == proValue) {
                                radioStr += '<input type="radio" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '" checked>' + item.value;
                            } else {
                                radioStr += '<input type="radio" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '">' + item.value;
                            }
                        }
                        radioStr += '</label>';
                    });
                }
            } else {
                radioStr += '<input type="hidden" name="orderBasicAttrOrm[' + index + '].proValue" value="">';
            }
        }

        radioStr += '</div>' + unitStr + '</div>';
        return radioStr;
    }


    function getOrderCheckbox(checkboxVal, index, proLabel) {
        var checkboxStr = '',
            unitStr = checkboxVal.proUnit && checkboxVal.proUnit != "null" ? "<span class='attr-unit' title='" + checkboxVal.proUnit + "'>" + checkboxVal.proUnit + "</span>" : '',
            proValue = checkboxVal.proValue && checkboxVal.proValue != "null" ? checkboxVal.proValue : '';

        if (proLabel == 30) {
            checkboxStr += '<div class="box-set form-group col-md-6">';
            checkboxStr += '<span class="attr-name" title="' + checkboxVal.proName + '"><span style="color: red">*</span>' + checkboxVal.proName + '：</span>';
            checkboxStr += '<div class="input-set proCheckControl" data-index="' + index + '">';
            checkboxStr += '<input type="hidden" name="orderControlResAttrOrm[' + index + '].proValue" value="' + proValue + '">';
            if (checkboxVal.proInitValue) {
                if(proDictionaryObj.reg.test(checkboxVal.proInitValue)) {
                    checkboxStr += '<input type="text" class="form-control" name="orderControlResAttrOrm['+index+'].proValue" value="" readonly>';
                }else{
                    $.each(getInitArray(checkboxVal.proInitValue), function (i, item) {
                        checkboxStr += '<label class="checkbox-inline form-child">';
                        if (proValue && proValue.indexOf(item.key) != -1) {
                            checkboxStr += '<input type="checkbox" name="orderControlResAttrOrm[' + index + '].proValue" value="' + item.key + '" checked>' + item.value;
                        } else {
                            checkboxStr += '<input type="checkbox" name="orderControlResAttrOrm[' + index + '].proValue" value="' + item.key + '">' + item.value;
                        }
                        checkboxStr += '</label>';
                    });
                }
            } else {
                checkboxStr += '<input type="hidden" name="orderControlResAttrOrm[' + index + '].proValue" value="">';
            }

        } else {

            checkboxStr += checkboxVal.proLabel.indexOf(10) != -1 && checkboxVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group col-md-6" style="display: none">' : '<div class="box-set form-group col-md-6">';
            checkboxStr += '<span class="attr-name" title="' + checkboxVal.proName + '">' + checkboxVal.proName + '：</span>';
            checkboxStr += '<div class="input-set proCheckControl" data-index="' + index + '">';
            checkboxStr += '<input type="hidden" name="orderBasicAttrOrm[' + index + '].proValue" value="' + proValue + '">';
            if (checkboxVal.proInitValue) {
                if(proDictionaryObj.reg.test(checkboxVal.proInitValue)) {
                    checkboxStr += '<input type="text" class="form-control" name="orderBasicAttrOrm['+index+'].proValue" value="" readonly>';
                }else{
                    $.each(getInitArray(checkboxVal.proInitValue), function (i, item) {
                        checkboxStr += '<label class="checkbox-inline form-child">';
                        if(checkboxVal.proIsFixed == 10) {
                            if (proValue && proValue.indexOf(item.key) != -1) {
                                checkboxStr += '<input type="checkbox" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '" checked disabled>' + item.value;
                            } else {
                                checkboxStr += '<input type="checkbox" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '" disabled>' + item.value;
                            }
                        }else{
                            if (proValue && proValue.indexOf(item.key) != -1) {
                                checkboxStr += '<input type="checkbox" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '" checked>' + item.value;
                            } else {
                                checkboxStr += '<input type="checkbox" name="orderBasicAttrOrm[' + index + '].proValue" value="' + item.key + '">' + item.value;
                            }
                        }
                        checkboxStr += '</label>';
                    });
                }
            } else {
                checkboxStr += '<input type="hidden" name="orderBasicAttrOrm[' + index + '].proValue" value="">';
            }
        }

        checkboxStr += '</div>' + unitStr + '</div>';
        return checkboxStr;
    }

    //slider
    function getOrderSlider(sliderVal, index, proLabel) {
        var sliderStr = '',
            unitStr = sliderVal.proUnit && sliderVal.proUnit != "null" ? "<span class='attr-unit' title='" + sliderVal.proUnit + "'>" + sliderVal.proUnit + "</span>" : '',
            proValue = sliderVal.proValue && sliderVal.proValue != "null" ? sliderVal.proValue : '';

        if (proLabel == 30) {
            sliderStr += '<div class="box-set form-group col-md-6">';
            sliderStr += '<span class="attr-name" title="' + sliderVal.proName + '"><span style="color: red">*</span>' + sliderVal.proName + '：</span>';
            sliderStr += '<div class="input-set">' +
                '<input name="orderControlResAttrOrm[' + index + '].proValue" value="' + proValue + '" class="easyui-slider proSlider customSetClass form-child" data-options="showTip:true">' +
                '</div>';
        } else {

            sliderStr += sliderVal.proLabel.indexOf(10) != -1 && sliderVal.proLabel.indexOf(30) != -1 ? '<div class="box-set form-group col-md-6" style="display: none">' : '<div class="box-set form-group col-md-6">';
            sliderStr += '<span class="attr-name" title="' + sliderVal.proName + '">' + sliderVal.proName + '：</span>';
            if(sliderVal.proIsFixed == 10) {
                sliderStr += '<div class="input-set">' +
                    '<input name="orderBasicAttrOrm[' + index + '].proValue" value="' + proValue + '" class="easyui-slider proSlider baseSetClass" data-options="showTip:true" disabled>' +
                    '</div>';
            }else{
                sliderStr += '<div class="input-set">' +
                    '<input name="orderBasicAttrOrm[' + index + '].proValue" value="' + proValue + '" class="easyui-slider proSlider baseSetClass form-child" data-options="showTip:true">' +
                    '</div>';
            }

        }

        sliderStr += unitStr + '</div>';
        return sliderStr;
    }

    function getOrderForms(val, index, proLabel) {
        switch (Number(val.proShowType)) {
            case 10:
                return getOrderInput(val, index, proLabel);
            case 20:
                return getOrderSelect(val, index, proLabel);
            case 30:
                return getOrderRadio(val, index, proLabel);
            case 40:
                return getOrderCheckbox(val, index, proLabel);
            case 50:
                return getOrderSlider(val, index, proLabel);
        }
    }


</script>


<style>
    .input-set {
        width: 50%;
        float: left;
    }

    .box-set {
        /*width: 48%;
        display: inline-block;
        margin: 0 2% 20px 0 !important;*/
    }

    .attr-name {
        width: 24%;
        float: left;
        text-align: right;
    }

    .attr-unit {
        width: 24%;
        float: right
    }

    @media screen and (min-width: 1440px) {
        .input-set{width: 60%;}
        .attr-unit{width: 14%;}
    }
    @media screen and (min-width: 1280px) and (max-width: 1440px) {
        .input-set{width: 54%;}
        .attr-unit{width: 20%;}
    }
    @media screen and (min-width: 1024px) and (max-width: 1280px) {
        .input-set{width: 45%;}
        .attr-unit{width: 25%;}
    }
    @media screen and (max-width: 991px) {
        .input-set{width: 40%;}
        .attr-unit{width: 32%;}
    }
</style>

