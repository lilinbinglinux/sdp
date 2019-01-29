var allCategoryArr = bringSvcCategorySelect();

$(document).ready(function () {
    getProductTypeTable();
    formValidator('#svcCategoryAddForm');
    formValidator('#svcCategoryEditForm');
});

function getProductTypeTable() {
    $("#productTypeTable").bootstrapTable({
        url: $webpath + "/api/productType/categoryInfos",
        method: "POST",
        contentType : "application/json",
        dataType: "json",
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
        queryParams: function (params) {
            return JSON.stringify({
                "page": {
                    "pageNo": (params.offset / params.limit) + 1,
                    "pageSize": params.limit
                },
                "productType": {
                    "productTypeName": $('#svcCategorySearch').val()
                }
            })
        },
        queryParamsType:'limit',
        responseHandler : function (res) {
            return {
                "total":res.totalCount,
                "rows":res.list
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "productTypeCode",
        columns: [{
            field: 'sortId',
            title: '序号',
            align: 'left',
            width: '50px',
            valign: 'middle',
            formatter: function (value,row,index) {
                //return index+1; //序号正序排序从1开始
                var pageSize=$('#productTypeTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#productTypeTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return  '<div">' + (pageSize * (pageNumber - 1) + index + 1) + '</div>';   //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        },{
            field: 'productTypeName',
            title: '分类名称',
            align: 'center',
            valign: 'middle'
        },{
            field: 'productTypeCode',
            title: '分类编码',
            align: 'center',
            valign: 'middle'
        },{
            field: 'typeStatus ',
            title: '分类状态',
            align: 'center',
            valign: 'middle',
            formatter:function(value,row,index){
                return svcCategoryStateConvert(row.typeStatus);
            }
        },{
            field: 'parentId',
            title: '父元素',
            align: 'center',
            valign: 'middle',
            formatter:function(value,row,index){
                for (var i = 0; i < allCategoryArr.length; i++) {
                    if(allCategoryArr[i].productTypeId == row.parentId) {
                        return allCategoryArr[i].productTypeName;
                    }
                }
            }
        },{
            field: 'createBy',
            title: '更新人',
            align: 'center',
            valign: 'middle'
        },{
            field: 'createDate',
            title: '更新时间',
            align: 'center',
            valign: 'middle'
        },{
            field: '',
            title: '操作',
            align: 'center',
            width: '100px',
            formatter:function(value,row,index){
                var html = '<span  title="删除" class="product-delete" onclick="deleteSvcCategory(\'' + row.productTypeId + '\')"></span>'
                        + '<span title="编辑" class="pruduct-edit" data-toggle="modal" data-target="#svcCategoryEditModal" onclick="openEditCategoryModel(\'' + row.productTypeId + '\')"></span>\n';
                return html;
            }
        }]
    })
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
            productTypeName: {
                validators: {
                    notEmpty: {
                        message: '请输入分类名称'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合命名，且不以下划线开头'
                    }
                }
            },
            sortId: {
                validators: {
                    notEmpty: {
                        message: '请输入分类排序数字'
                    }
                }
            },
            typeStatus: {
                validators: {
                    notEmpty: {
                        message: '请选择分类状态'
                    }
                }
            },
            productTypeCode: {
                validators: {
                    notEmpty: {
                        message: '请输入分类编码'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z_]+$/,
                        message: '以字母、下划线组合填写，且不以下划线开头'
                    }
                }
            }
        }
    });
}

//新增服务分类保存
function addSvcCategorySave() {
    // $('#svcCategoryAddForm').data('bootstrapValidator').validate();

    var svcCategoryValidator = $("#svcCategoryAddForm").data('bootstrapValidator');
	svcCategoryValidator.validate();
	if( !svcCategoryValidator.isValid() ){
        return;
    }

    var svcCategoryFormData = formatFormData('#svcCategoryAddForm');
    console.log(svcCategoryFormData, '新增------');

    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/categoryCodeVer/" + svcCategoryFormData.productTypeCode +"/default",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            if (data.result == false) {
                // layer.msg('服务分类编码重复，请重新填写！');
                $.message({
                    message:'服务分类编码重复，请重新填写！',
                    type:'error'
                });
            } else{

                $.ajax({
                    type: "GET",
                    url: $webpath + "/api/productType/cagegory/verfy/" + svcCategoryFormData.productTypeName +"/default",
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.result == false) {
                            // layer.msg('服务分类名称重复，请重新填写！');
                            $.message({
                                message:'服务分类名称重复，请重新填写！',
                                type:'error'
                            });
                        }else {
                            if($('#svcCategoryAddForm').data('bootstrapValidator').isValid()){
                                $.ajax({
                                    type: "POST",
                                    url: $webpath + "/api/productType/createCategory",
                                    contentType: "application/json; charset=utf-8",
                                    dataType: "json",
                                    data: JSON.stringify(svcCategoryFormData),
                                    success: function (result) {
                                        if(result.code == 200) {
                                            // layer.msg(result.message,{icon: 1});
                                            $.message(result.message);
                                            $('#svcCategoryAddModal').modal('hide');
                                            handleResetForm('#svcCategoryAddForm');
                                            $("#productTypeTable").bootstrapTable('refresh');
                                        }else{
                                            // layer.msg(result.message,{icon: 2});
                                            $.message(result.message);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

            }
        }
    });
}

//打开编辑服务分类model
function openEditCategoryModel(productTypeId) {
    console.log(productTypeId,'打开编辑model');
    $.ajax({
        url: $webpath + "/api/productType/categoryInfos/"+productTypeId,
        type: 'GET',
        dataType:"json",
        contentType: "application/json",
        success: function (data) {
            $('#svcCategoryEditForm input[name="productTypeName"]').attr("value", data.productTypeName);
            $('#svcCategoryEditForm input[name="productTypeCode"]').attr("value", data.productTypeCode);
            $('#svcCategoryEditForm input[name="sortId"]').attr("value", data.sortId);
            $('#svcCategoryEditForm select[name="typeStatus"]').val(data.typeStatus);

            var newArr = allCategoryArr.filter(function (item,index) {
                return item.productTypeId != data.productTypeId;
            });
            var editStr = '<option value="" disabled selected style="display:none;">请选择……</option>';
            $.each(newArr, function (index, item) {
                if(item.typeStatus != '1') {
                    if(item.productTypeId == data.parentId) {
                        editStr += '<option value="'+ item.productTypeId +'" selected>'+ item.productTypeName +'</option>';
                    }else{
                        editStr += '<option value="'+ item.productTypeId +'">'+ item.productTypeName +'</option>';
                    }
                }
            });
            $('#svcCategoryEditForm select[name="parentId"]').html(editStr);

            $('#svcCategoryEditModal .modal-footer .btn-save').on('click',function () {
                editSvcCategorySave(productTypeId);
            });
        }
    });
}

//修改服务分类保存
function editSvcCategorySave(productTypeId) {
    // $('#svcCategoryEditForm').data('bootstrapValidator').validate();

    var svcCategoryValidator = $("#svcCategoryEditForm").data('bootstrapValidator');
	svcCategoryValidator.validate();
	if( !svcCategoryValidator.isValid() ){
        return;
    }

    var svcCategoryFormData = formatFormData('#svcCategoryEditForm');
    svcCategoryFormData.productTypeId = productTypeId;
    console.log(svcCategoryFormData, '修改------');

    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/categoryCodeVer/" + svcCategoryFormData.productTypeCode+"/"+productTypeId,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            if (data.result == false) {
                // layer.msg('服务分类编码重复，请重新填写！');
                $.message({
                    message:'服务分类编码重复，请重新填写！',
                    type:'error'
                });
            } else{

                $.ajax({
                    type: "GET",
                    url: $webpath + "/api/productType/cagegory/verfy/" + svcCategoryFormData.productTypeName +"/"+productTypeId,
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.result == false) {
                            // layer.msg('服务分类名称重复，请重新填写！');
                            $.message({
                                message:'服务分类名称重复，请重新填写！',
                                type:'error'
                            });
                        }else {
                            if($('#svcCategoryAddForm').data('bootstrapValidator').isValid()){
                                $.ajax({
                                    url: $webpath + "/api/productType/modifyCategory",
                                    type: 'POST',
                                    dataType:"json",
                                    data:JSON.stringify(svcCategoryFormData),
                                    contentType: "application/json",
                                    success: function (result) {
                                        if(result.code == 200) {
                                            // layer.msg(result.message,{icon: 1});
                                            $.message(result.message);
                                            $('#svcCategoryEditModal').modal('hide');
                                            handleResetForm('#svcCategoryEditForm');
                                            $("#productTypeTable").bootstrapTable('refresh');
                                        }else{
                                            // layer.msg(result.message,{icon: 2});
                                            $.message(result.message);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

            }
        }
    });
}

//删除服务分类
function deleteSvcCategory(productTypeId) {
    $('#deleteConfirmModal').modal('show');
    $('#deleteConfirmModal .confirm-box-ok').on('click',function () {
        $.ajax({
            url: $webpath + "/api/productType/deleteCategory",
            type: 'POST',
            dataType:"json",
            data:JSON.stringify({
                productTypeId: productTypeId
            }),
            contentType: "application/json",
            success: function (req) {
                if(req.code == 200) {
                    $.message({
                        message:req.message,
                        type:'success'
                    });
                    $("#productTypeTable").bootstrapTable('refresh');
                }else {
                    $.message({
                        message:req.message,
                        type:'error'
                    });
                }
            }
        });
        $('#deleteConfirmModal').modal('hide');
    });
}

// 生成服务分类下拉框
function bringSvcCategorySelect() {
    var arr = [];
    $.ajax({
        type: "GET",
        url: $webpath + "/api/productType/allCategory",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            arr = data;
            if (data) {
                var str = '<option value="" disabled selected style="display:none;">请选择……</option>';
                $.each(data, function (index, item) {
                    if(item.typeStatus != '1') {
                        str += '<option value="'+ item.productTypeId +'">'+ item.productTypeName +'</option>';
                    }
                });
                $('#svcCategoryAddForm select[name="parentId"]').html(str);
            }
        }
    });
    return arr;
}

$(".glyphicon-search").click(function(){
    $('#productTypeTable').bootstrapTable('refresh');
});

$(".btn-refresh").click(function(e){
    $('#svcCategorySearch').val('');
    $('#productTypeTable').bootstrapTable('refresh');
});

$(".bconsole-input-placeholder").click(function(){
    $(this).css("display","none");
    $("#svcCategorySearch").focus();
});

$("#svcCategorySearch").blur(function(e){
    if( $(this).val() === "" ){
        $(".bconsole-input-placeholder").css("display","block");
    }else {
        $(".bconsole-input-placeholder").css("display","none");
    }
});

$(document).keydown(function(e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if(event.keyCode == "13") {
            $('#productTypeTable').bootstrapTable('refresh');
        }
    }
});