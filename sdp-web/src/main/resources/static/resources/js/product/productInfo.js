var svcRegisterAttrArr = [], //属性列表数据
    relyOnAttrOrmArr = [], //依赖服务列表数据
    attrInitialGrid,
    attrInitialEditGrid,
    oldProductLogo,
    curPruduct;

$(document).ready(function () {
    /*if (window.productStatus == 10) { // 服务为暂存*/
        $('#svcDetailPage').hide();
        $('#svcEditPage').show();

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
            //width: '100%',
            data: {Rows: [{checked: '', key: '', value: ''}]},
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


    /*} else {
        $('#svcDetailPage').show();
        $('#svcEditPage').hide();
    }*/

    getSingleProduct();
});

function getSingleProduct() {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/singleProduct/" + window.productId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        timeout : 5000,
        success: function (data) {
            /*if (window.productStatus == 10) { // 服务为暂存*/
                curPruduct = data;
                oldProductLogo = data.productLogo;
                getAllPorderWays(data.orderType);
                svcRegisterAttrArr = data.productAttrOrm ? data.productAttrOrm : [];
                relyOnAttrOrmArr = data.relyOnAttrOrm ? data.relyOnAttrOrm : [];

                $('#svcRegisterInfo input[name="productName"]').attr("value", data.productName);
                $('#svcRegisterInfo textarea[name="productIntrod"]').val(data.productIntrod);
                $('#svcRegisterInfo img').attr("src", "data:image/jpg;base64," + data.productLogo);
                $('#svcRegisterInfo input[name="productId"]').attr("value", data.productId);
                $('#svcRegisterInfo input[name="productVersion"]').attr("value", data.productVersion);
                $('#svcRegisterInfo input[name="apiAddr"]').attr("value", data.apiAddr);
                $('#svcRegisterInfo input[name="sortId"]').attr("value", data.sortId);
                $("#svcRegisterInfo :radio[name='showInstance'][value='"+data.showInstance+"']").attr("checked",true);
                
                $("#svcRegisterInfo select[name='useTotalType']").val(data.useTotalType ? data.useTotalType : "");

                getAllCategory(data.productTypeId);
                getRegAttrListTable();
                getRelySvcListTable();

            /*} else {
                getProductDetails(data);
                getAttrInfoTable(data.productAttrOrm);
                getRelySvcInfoTable(data.relyOnAttrOrm);
            }*/
        },
        error : function(xhr,textStatus){
            if(textStatus == 'timeout'){
                xhr.abort();
                getSingleProduct();
            }
        }
    });
}

/**
 * 服务详情页面
 */
function getProductDetails(data,pwaysNameShow) {
    var str = '<div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务名称</label>\n' +
        '            <span class="col-md-6">' + data.productName + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务类型</label>\n' +
        '            <span class="col-md-6">' + getProductTypeName(data.productTypeId) + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务描述</label>\n' +
        '            <span class="col-md-6">' + data.productIntrod + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务logo</label>\n' +
        '            <span class="col-md-6">\n' +
        '                <img style="width: 100px;height: 70px" src="data:image/jpg;base64,' + data.productLogo + '" alt="">\n' +
        '            </span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务编码</label>\n' +
        '            <span class="col-md-6">' + data.productId + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务版本</label>\n' +
        '            <span class="col-md-6">' + data.productVersion + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">订购方式</label>\n' +
        '            <span class="col-md-6">' + getOnePorderWays(data.orderType) + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务地址</label>\n' +
        '            <span class="col-md-6">' + data.apiAddr + '</span>\n' +
        '        </div>\n' +
        '        <div class="col-md-6 row">\n' +
        '            <label class="col-md-3">服务排序</label>\n' +
        '            <span class="col-md-6">' + data.sortId + '</span>\n' +
        '        </div>';
    $('#svcBasicInfo').html(str);
}

// 查询单个服务类型信息
function getProductTypeName(productTypeId) {
    var productTypeName;
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/categoryInfos/" + productTypeId,
        dataType: "json",
        async: false,
        success: function (data) {
            productTypeName = data.productTypeName;
        }
    });
    return productTypeName;
}

// 获取某一订购方式
function getOnePorderWays(pwaysType) {
    var pwaysNameShow;
    // 获取订购方式
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({pwaysType: pwaysType}),
        async: false,
        url: $webpath + "/api/porderWays/allPorderWays",
        success: function (result) {
            pwaysNameShow = result[0].pwaysName;
        }
    });
    return pwaysNameShow;
}

// 获取服务属性详情表格
function getAttrInfoTable(svcRegisterAttrArr) {
    if (svcRegisterAttrArr && svcRegisterAttrArr.length > 0) {
        var str = '';
        $.each(svcRegisterAttrArr, function (index, item) {
            var arr = [];
            $.each(item.proLabel.split(','), function (i, it) {
                arr.push(proLabelConvert(it));
            });

            var proEnName = item.proEnName ? item.proEnName : '-';
            var proDesc = item.proDesc ? item.proDesc : '-';
            var proUnit = item.proUnit ? item.proUnit : '-';
            var proVerfyRule = item.proVerfyRule ? item.proVerfyRule : '-';
            var proVerfyTips = item.proVerfyTips ? item.proVerfyTips : '-';
            var proInitValue = item.proInitValue ? item.proInitValue : '-';

            str += '<tr>\n' +
                '   <td>' + item.proName + '</td>\n' +
                '   <td>' + proEnName + '</td>\n' +
                '   <td>' + formItemConvert(item.proShowType) + '</td>\n' +
                '   <td>' + basicTypeConvert(item.proDataType) + '</td>\n' +
                '   <td>' + proInitValue + '</td>\n' +
                '   <td>' + proVerfyRule + '</td>\n' +
                '   <td>' + proVerfyTips + '</td>\n' +
                '   <td>' + proDesc + '</td>\n' +
                '   <td>' + proUnit + '</td>\n' +
                '   <td>' + arr.join(',') + '</td>\n' +
                '</tr>';
        });
        $('#svcAttrInfoTable tbody').html(str);
    } else {
        $('#svcAttrInfoTable tbody').html('');
    }
}

function getRelySvcInfoTable(relyOnAttrOrmArr) {
    if (relyOnAttrOrmArr.length > 0) {
        var str = '';
        $.each(relyOnAttrOrmArr, function (index, item) {
            str += '<tr>\n' +
                '   <td>' + item.relyOnProductCode + '</td>\n' +
                '   <td>' + isConfRelyOnProsConvert(item.isConfRelyOnPros) + '</td>\n' +
                '   <td>' + isShowRelyOnProsConvert(item.isShowRelyOnPros) + '</td>\n' +
                '   <td>' + item.relyOnOrder + '</td>\n' +
                '</tr>';
        });
        $('#relySvcInfoTable tbody').html(str);
    } else {
        $('#relySvcInfoTable tbody').html('');
    }
}

/*————————————————————————————————*/

/**
 * 服务编辑页面
 */
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

// 查询当前可用的流程
function canuseProcesses(){
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({pwaysType: ''}),
        url: $webpath + "/api/porderWays/allPorderWays",
        success: function (data) {
            var html = 
                        '<label class="label-width"><span class="red-font">＊</span>审批流程</label>'+
                                '<select class="form-control input-width" name="pOrderWaysId">';
            if( data && data.length > 0 ){
                for( var i = 0; i < data.length; i++ ){
                    if (data[i].pwaysRemark != null) {
                        html += '<option value="'+data[i].pwaysId+'" '+ ( curPruduct ? ( curPruduct.pOrderWaysId == data[i].pwaysId ? "selected" : "" ) : "" ) +'>'+data[i].pwaysName+'</option>';
                    }
                }
            }else {
                html += '<option value="">暂无审批流程</option>';
            }

            html += 
                    '</select>';
            $(".causeProcess").empty();
            $(".causeProcess").append(html);
        }
    });
}

// 获取所有订购方式
function getAllPorderWays(orderType) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({pwaysType: ''}),
        url: $webpath + "/api/porderWays/allPorderWays",
        success: function (result) {
            // var str = '', processStr = '';
            // $.each(result, function (index, item) {
            //     if (item.pwaysRemark == null) {
            //         if (item.pwaysType == orderType) {
            //             str += '<label class="radio-inline">\n' +
            //                 '       <input type="radio" name="orderType" value="' + item.pwaysType + '" checked> ' + item.pwaysName + '\n' +
            //                 '</label>';
            //         } else {
            //             str += '<label class="radio-inline">\n' +
            //                 '       <input type="radio" name="orderType" value="' + item.pwaysType + '"> ' + item.pwaysName + '\n' +
            //                 '</label>';
            //         }
            //     }
            //     if (item.pwaysRemark) {
            //         processStr += '<label class="radio-inline">\n' +
            //             '       <input type="radio" name="pwaysId" value="' + item.pwaysId + '"> ' + item.pwaysName + '\n' +
            //             '</label>';
            //     }
            // });
            if( result && result.length > 0 ){
                for( var i = 0; i < result.length; i++ ){
                    if (result[i].pwaysRemark == null) {
                        $("#orderTypeShow").append(
                            '<label class="radio-inline">\n' +
                               '<input type="radio" name="orderType" value="' + result[i].pwaysType + '" '+ ( orderType ? ( orderType == result[i].pwaysType ? "checked" : "" ) : "" ) +'> ' + result[i].pwaysName + '\n' +
                            '</label>'
                        );
                    }
                }
            }

            if( orderType && orderType == "10" ){
                canuseProcesses();
            }

            // $('#orderTypeShow').html(str);
            // $('#approvalProcessShow').html(processStr);

            $("input[name='orderType']").change(function(){
                var orderType = $("input[name='orderType']:checked").val();
                if(orderType === "10"){
                    //审批
                    canuseProcesses();
                }else {
                    $(".causeProcess").empty();
                }
            });

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
            /*orderType == 10 ? $('#approvalProcessShow').parent().show() : $('#approvalProcessShow').parent().hide();*/
            $('#orderTypeShow input[name="orderType"]').on('change',function () {
                /*$(this).val() == 10 ? $('#approvalProcessShow').parent().show() : $('#approvalProcessShow').parent().hide();*/
                /*if($(this).val() == 20){
                    $('#noPriceSubmit').css('display','none');
                    $('#toNextStep').css('display','block');
                }else{
                    $('#noPriceSubmit').css('display','block');
                    $('#toNextStep').css('display','none');
                }*/
            });
        }
    });
}

// 获取所有服务分类
function getAllCategory(productTypeId) {
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/allCategory",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data != null) {
                var str = '';
                $.each(data, function (index, item) {
                    if (item.typeStatus != '1') {
                        if (productTypeId == item.productTypeId) {
                            str += '<option value="' + item.productTypeId + '" selected>' + item.productTypeName + '</option>';
                        } else {
                            str += '<option value="' + item.productTypeId + '">' + item.productTypeName + '</option>';
                        }
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

function sortNumber(a,b)
{
	return a - b
}

function regAttrListTableBindNumClick() {
	$(".inner-order-number").click(function (e) {
		var innerSpan = $(this).find(".serial-number");
		var innerInput = $(this).find("input");
		var formGroup = $(this).find(".inner-order-input");
		var num = innerSpan.text();
		innerInput.val(num);

		innerSpan.css("display","none");
		formGroup.css("display","block");
		innerInput.focus();
		innerInput.blur(function(){
			var newNum = parseInt($(this).val());
			innerSpan.text(newNum);
			innerSpan.css("display","block");
			formGroup.css("display","none");
			var trs = $("#regAttrListTable tbody").find("tr");
			var indexArr = [];
			for( var i = 0; i < trs.length; i++ ){
				indexArr.push(parseInt($(trs[i]).find(".inner-order-number input").val()));
			}
			var newIndexArr = indexArr.sort(sortNumber);
			$('#regAttrListTable tbody').html("");
			for( var j = 0; j < newIndexArr.length; j++ ){
				for( var k = 0; k < trs.length; k++ ){
					if( parseInt($(trs[k]).find(".inner-order-number input").val()) === newIndexArr[j] ){
						$('#regAttrListTable tbody').append(trs[k]);
					}
				}
			}
			regAttrListTableBindNumClick();
		});
	});
}

// 属性列表
function getRegAttrListTable() {
    if (svcRegisterAttrArr.length > 0) {
        console.log(svcRegisterAttrArr);
        var str = '';
        $.each(svcRegisterAttrArr, function (index, item) {
            var arr = [];
            $.each(item.proLabel.split(','), function (i, it) {
                arr.push(proLabelConvert(it));
            });

            var rowData = JSON.stringify(item);
            rowData = escape(rowData);
            var escapeflag = true;

            var proEnName = item.proEnName ? item.proEnName : '-';
            var proDesc = item.proDesc ? item.proDesc : '-';
            var proUnit = item.proUnit ? item.proUnit : '-';
            var proVerfyRule = item.proVerfyRule ? item.proVerfyRule : '-';
            var proVerfyTips = item.proVerfyTips ? item.proVerfyTips : '-';
            var proInitValue = item.proInitValue ? item.proInitValue : '-';

            str += '<tr>\n' +
                '   <td class="inner-order-number"><span class="serial-number">' + ( index + 1 ) + '</span>' +
                '     <div class="form-group inner-order-input">\n' +
                '       <label class="sr-only">序号</label>\n' +
                '       <input style="width: 44px;height: 20px;" name="sortNum" value="'+( index + 1 )+'" type="text" class="form-control">\n' +
                '     </div>' +
                '   </td>\n' +
                '   <td>' + item.proName + 
                '       <input type="text" hidden="hidden" name="proIndex" value="'+ index +'">'+   
                '   </td>\n' +
                '   <td>' + proEnName + '</td>\n' +
                '   <td>' + formItemConvert(item.proShowType) + '</td>\n' +
                '   <td>' + basicTypeConvert(item.proDataType) + '</td>\n' +
                '   <td>' + proInitValue + '</td>\n' +
                '   <td>' + proVerfyRule + '</td>\n' +
                '   <td>' + proVerfyTips + '</td>\n' +
                '   <td>' + proDesc + '</td>\n' +
                '   <td>' + proUnit + '</td>\n' +
                '   <td>' + arr.join(',') + '</td>\n' +
                '   <td>\n' +
                '       <span class="glyphicon glyphicon-edit icon-hover" title="编辑" data-toggle="modal" data-target="#attrEditModal" onclick="handleEditAttr(\'' + rowData + '\',\'' + index + '\',\'' + escapeflag + '\')"></span>\n' +
                '       <span class="glyphicon glyphicon-trash icon-hover" title="删除" onclick="deleteAttr(\'' + index + '\')"></span>\n' +
                '   </td>\n' +
                '</tr>';
        });
        $('#regAttrListTable tbody').html(str);

	      regAttrListTableBindNumClick();
        
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

//属性标签change事件 添加属性模态框中
$("#svcAddAttrForm").find("input[name='proLabel']").change(function(){
    var proLabels = $("#svcAddAttrForm input[name='proLabel']:checked");
    if( proLabels.length > 0 ){
        var isHaveSource = false;
        for( var i = 0; i < proLabels.length; i++ ){
            if( $(proLabels[i]).val() === "30" ){
                isHaveSource = true;
                break;
            }
        }
        if( isHaveSource ){
            //有则不需要append
            if( $("#svcAddAttrForm .proMappingCode").find("select[name='proMappingCode']").length > 0 ){
                return;
            }
            $("#svcAddAttrForm .proMappingCode").empty();
            $("#svcAddAttrForm .proMappingCode").append(
                '<label class="label-width"><span class="red-font">＊</span>映射资源属性</label>'+
                        '<select class="form-control input-width" name="proMappingCode">'+
                            '<option value="CPU">CPU</option>'+
                            '<option value="Memory">Memory</option>'+
                            '<option value="Storage">Storage</option>'+
                        '</select>'+
                '</div>'
            )
        }else{
            $("#svcAddAttrForm .proMappingCode").empty();
        }
    }
});


/**
 * 属性增删改
 */
//重置新增model
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
    /*var initialTable = attrInitialGrid.getData();
    var len = initialTable.length-1;
    for(var i=len;i>=0;i--){
        if(initialTable[i].key == '' || initialTable[i].value == ''){
            initialTable.splice(i,1);
        }
    }
    attrFormData.initialTable = initialTable;*/

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
    var proValueArr;
    var reg = /(http|ftp|https):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/;
    if(reg.test(item.proInitValue)) {
       $('#initialEditUrl input[name="initialUrl"]').attr('value',item.proInitValue);
        proValueArr = [];
    }else{
        if (item.proInitValue == 'null' || item.proInitValue == '' || item.proInitValue == null) {
            proValueArr = [];
        } else {
            proValueArr = getInitArray(item.proInitValue);
            $.each(proValueArr, function (index, item) {
                item.checked = '';
            });
        }
    }

    var proEnName = item.proEnName ? item.proEnName : '';
    var proDesc = item.proDesc ? item.proDesc : '';
    var proUnit = item.proUnit ? item.proUnit : '';
    var proVerfyTips = item.proVerfyTips ? item.proVerfyTips : '';
    var proVerfyRule = item.proVerfyRule ? item.proVerfyRule : '';

    var str = '';
    str += '<div class="form-group col-md-6">\n' +
        '    <label class="label-width"><span class="red-font">＊</span>属性名</label>\n' +
        '    <input type="text" class="form-control input-width" name="proName" required value="' + item.proName + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">属性英文名</label>\n' +
        '    <input type="text" class="form-control input-width" name="proEnName" value="' + proEnName + '">\n' +
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
        '    <input type="text" class="form-control input-width" name="proUnit" value="' + proUnit + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">属性描述</label>\n' +
        '    <input type="text" class="form-control input-width" name="proDesc" value="' + proDesc + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">校验规则</label>\n' +
        '    <input type="text" class="form-control input-width" name="proVerfyRule" value="' + proVerfyRule + '">\n' +
        '</div>\n' +
        '<div class="form-group col-md-6">\n' +
        '    <label class="label-width">校验提示</label>\n' +
        '    <input type="text" class="form-control input-width" name="proVerfyTips" value="' + proVerfyTips + '">\n' +
        '</div>'+
        '<div class="form-group col-md-6 proMappingCode"></div>';

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
        if( checkBoxArray[i] == "30" ){
            $("#svcEditAttrForm").find("#svcEditAttrForm input[name='proLabel'][value='30']").prop("checked",true);
                        $("#svcEditAttrForm .proMappingCode").empty();
                        $("#svcEditAttrForm .proMappingCode").append(
                            '<label class="label-width"><span class="red-font">＊</span>映射资源属性</label>'+
                                    '<select class="form-control input-width" name="proMappingCode">'+
                                        '<option value="CPU" '+ ( item.proMappingCode == "CPU" ? "selected" : "" ) +'>CPU</option>'+
                                        '<option value="Memory" '+ ( item.proMappingCode == "Memory" ? "selected" : "" ) +'>Memory</option>'+
                                        '<option value="Storage" '+ ( item.proMappingCode == "Storage" ? "selected" : "" ) +'>Storage</option>'+
                                    '</select>'+
                            '</div>'
                        );
                        $("#svcEditAttrForm .proMappingCode").css("display","block");
        }
        $("#proLabelSelect1 :checkbox").each(function () {
            if ($(this).val() == checkBoxArray[i]) {
                $(this).attr("checked", true);
            }
        })
    }


    //属性标签change事件 添加属性模态框中
    $("#svcEditAttrForm").find("input[name='proLabel']").change(function(){
        var proLabels = $("#svcEditAttrForm input[name='proLabel']:checked");
        if( proLabels.length > 0 ){
            var isHaveSource = false;
            for( var i = 0; i < proLabels.length; i++ ){
                if( $(proLabels[i]).val() === "30" ){
                    isHaveSource = true;
                    break;
                }
            }
            if( isHaveSource ){
                //有则不需要append
                if( $("#svcEditAttrForm .proMappingCode").find("select[name='proMappingCode']").length > 0 ){
                    return;
                }
                $("#svcEditAttrForm .proMappingCode").empty();
                $("#svcEditAttrForm .proMappingCode").append(
                    '<label class="label-width"><span class="red-font">＊</span>映射资源属性</label>'+
                            '<select class="form-control input-width" name="proMappingCode">'+
                                '<option value="CPU">CPU</option>'+
                                '<option value="Memory">Memory</option>'+
                                '<option value="Storage">Storage</option>'+
                            '</select>'+
                    '</div>'
                )
            }else{
                $("#svcEditAttrForm .proMappingCode").empty();
            }
        }
    });

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
        data: {Rows: proValueArr},
        //data: { Rows: item.initialTable},
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
    var attrFormData = formatFormData('#svcEditAttrForm');
    attrFormData.proLabel = getCheckboxData("#proLabelSelect1 :checkbox:checked");

    /*var initialTable = attrInitialEditGrid.getData();
    var len = initialTable.length-1;
    for(var i=len;i>=0;i--){
        if(initialTable[i].key == '' || initialTable[i].value == ''){
            initialTable.splice(i,1);
        }
    }
    attrFormData.initialTable = initialTable;*/

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
    var svcRegisterAttrArrForSub = [];
    product.productStatus = productStatus;

    
    
    //遍历服务属性dom
    var currentTrs = $('#regAttrListTable tbody').find("tr");
    if( currentTrs && currentTrs.length > 0 ){
        for( var c = 0; c < currentTrs.length; c++ ){
            if( svcRegisterAttrArr && svcRegisterAttrArr.length > 0 ){
                var proAttrIndex = $(currentTrs[c]).find("input[name='proIndex']").val();
                svcRegisterAttrArrForSub.push(svcRegisterAttrArr[proAttrIndex]);
            }
        }
    }

    product.productAttrOrm = svcRegisterAttrArrForSub;

    product.relyOnAttrOrm = relyOnAttrOrmArr;

    console.log(product,'product');

    var fd = new FormData();
    var file_obj;
    if (document.getElementById('productIcon').files[0]) {
        file_obj = document.getElementById('productIcon').files[0];
        fd.append('productIcon', file_obj);
        if(Math.round(file_obj.size/1024*100)/100 >= 200){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg('上传图片过大，请上传小于200kb的图片！');
            });
            return;
        }
    } else {
        // var $Blob = getBlobBydataURI(oldProductLogo, 'image/jpg');
        // fd.append("productIcon", $Blob, "file_" + Date.parse(new Date()) + ".jpg");    
        // fd.append('productIcon', null); 
    }
    fd.append('product', JSON.stringify(product));

    
    console.log(fd, '保存服务---------------');

    if ($('#svcRegisterInfo').data('bootstrapValidator').isValid()) {
        $.ajax({
            type: 'POST',
            data: fd,
            processData: false,
            contentType: false,
            url: $webpath + "/api/product/modifyProduct",
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


/**
 * 根据base64 内容 取得 blob
 * @param dataURI
 * @returns Blob
 */
function getBlobBydataURI(dataURI, type) {
    //var binary = atob(dataURI.split(',')[1]);
    var binary = dataURI;
    var array = [];
    for (var i = 0; i < binary.length; i++) {
        array.push(binary.charCodeAt(i));
    }
    return new Blob([new Uint8Array(array)], {type: type});
}
