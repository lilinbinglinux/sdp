
var svcRegisterAttrArr = [], //属性列表数据
    regProductFormData = {}, //服务基本信息
    finalFormData = new FormData() //最终提交的数据

function operateFormatter(value,row,index){
	var productId = row.productId;
    var html = '';
    html += '<button type="button" class="btn btn-default btn-xs peizhi btn-beconsole-small">配置列表</button>';

    if(row.productStatus == 20 || row.productStatus == 30) { // 状态为已注册和上线
		html += '<button type="button" class="btn btn-default btn-xs taocan btn-beconsole-small" style="margin-right: 5px;">套餐</button>\n';
	}else{
		html += '<button type="button" class="btn btn-default btn-xs taocan btn-beconsole-small" title="服务状态为暂存、下线时，不能添加套餐" disabled="true" style="margin-right: 5px;">套餐</button>\n';
    }

    if (row.productStatus == 20 || row.productStatus == 40) { // 状态为已注册和下线
		// html += '<button type="button" class="btn btn-default btn-xs shangxian btn-beconsole-small" id="onBtn' + productId + '" >上线</button>\n' +
        //     '<button type="button" class="btn btn-default btn-xs xiaxian btn-beconsole-small" id="downBtn' + productId + '"   disabled="true">下线</button>\n';
        html += '<div class="beconsole-switch">'+
                '   <div class="slider round"></div>'+
                '</div>';
	} else if (row.productStatus == 30) { // 状态为上线
		// html += '<button type="button" class="btn btn-default btn-xs shangxian btn-beconsole-small" id="onBtn' + productId + '"  disabled="true">上线</button>\n' +
        // 	'<button type="button" class="btn btn-default btn-xs xiaxian btn-beconsole-small" id="downBtn' + productId + '" >下线</button>\n';
        html += '<div class="beconsole-switch beconsole-switch-open">'+
                '   <div class="slider round"></div>'+
                '</div>';
	} else {
		// html += '<button type="button" class="btn btn-default btn-xs shangxian btn-beconsole-small" id="onBtn' + productId + '" disabled="true">上线</button>\n' +
        // 	'<button type="button" class="btn btn-default btn-xs xiaxian btn-beconsole-small" id="downBtn' + productId + '" disabled="true">下线</button>\n';
        html += '<div class="beconsole-switch beconsole-switch-disabled">'+
                '   <div class="slider round"></div>'+
                '</div>';
    }
    return html;
}

window.operateEvents = {
	'click .taocan': function (e, value, row, index) {
		window.location.href = $webpath + "/productPackage/page?productId=" + row.productId;
    },
    'click .beconsole-switch': function (e, value, row, index) {
        var productId = row.productId;

        if( $(this).hasClass("beconsole-switch-disabled") ){
            return;
        }

        if( $(this).hasClass("beconsole-switch-open") ){
            $(this).removeClass("beconsole-switch-open");
            //下线
            $.ajax({
                type: "GET",
                url: $webpath + "/api/product/modifyStatus/" + productId + "/40",
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        $.message('下线成功！');
                        $('#downBtn' + productId).attr('disabled', true);
                        $('#downBtn' + productId).parent().find('#onBtn' + productId).attr('disabled', false);
                        $("#productTable").bootstrapTable('refresh');
                    }else {
                        $.message({
                            message:'下线失败！',
                            type:'error'
                        });
                    }
                }
            });
        }else{
            $(this).addClass("beconsole-switch-open");
            //上线
		    $.ajax({
		    	type: "GET",
		    	url: $webpath + "/api/product/modifyStatus/" + productId + "/30",
		    	dataType: "json",
		    	success: function (data) {
		    		if (data.code == 200) {
                        $.message('上线成功！');
		    			$('#onBtn' + productId).attr('disabled', true);
		    			$('#onBtn' + productId).parent().find('#downBtn' + productId).attr('disabled', false);
		    			$("#productTable").bootstrapTable('refresh');
		    		}else {
                        $.message({
                            message:'上线失败！',
                            type:'error'
                        });
                    }
		    	}
		    });
        }
        
    },
	'click .peizhi': function (e, value, row, index) {
		window.location.href = $webpath + "/product/configItemPage?productId=" + row.productId;
	}
};

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
                        '<div class="form-group ">'+
                            '<label class="col-sm-3 control-label">审批流程</label>'+
                            '<div class="col-sm-9">'+
                                '<select class="form-control" name="pOrderWaysId">';
            if( data && data.length > 0 ){
                for( var i = 0; i < data.length; i++ ){
                    if (data[i].pwaysRemark != null) {
                        html += '<option value="'+data[i].pwaysId+'">'+data[i].pwaysName+'</option>';
                    }
                }
            }else {
                html += '<option value="">暂无审批流程</option>';
            }

            html += 
                    '</select>'+
                    '</div>'+
                    '</div>';
            $(".causeProcess").empty();
            $(".causeProcess").append(html);
        }
    });

    // $.ajax({
    //     url: $webpath + "/api/porderWays/processes",
    //     success: function( result ){
    //         var html = 
    //                     '<div class="form-group ">'+
    //                         '<label class="col-sm-3 control-label">审批流程</label>'+
    //                         '<div class="col-sm-9">'+
    //                             '<select class="form-control" name="pOrderWaysId">';
    //         if( result.data && result.data.length > 0 ){
    //             var processes = result.data;
                
    //             for( var i = 0; i < processes.length; i++ ){
    //                 html += '<option value="'+processes[i]["ID"]+'">'+processes[i]["NAME"]+'</option>';
    //             }
    //         }else {
    //             html += '<option value="">暂无审批流程</option>'
    //         }

    //         html += 
    //                 '</select>'+
    //                 '</div>'+
    //                 '</div>';
    //         $(".causeProcess").empty();
    //         $(".causeProcess").append(html);
    //     }
    // });
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


            //订购方式radio
            $("#product-payways").find(".product-payways").empty();

            if( data && data.length > 0 ){
                for( var i = 0; i < data.length; i++ ){
                    if (data[i].pwaysRemark == null) {
                        $("#product-payways").find(".product-payways").append(
                            '<div class="form-radio-group">'+
                            '<input id="payway_'+ i +'" type="radio" name="orderType" value="' + data[i].pwaysType + '">'+
                            '<label for="payway_'+ i +'"></label>'+
                            '<span>' + data[i].pwaysName + '</span>'+
                            '</div>'
                        );
                    }
                }
            }

            $("input[name='orderType']").change(function(){
                var orderType = $("input[name='orderType']:checked").val();
                if(orderType === "10"){
                    //审批
                    canuseProcesses();
                }else {
                    $(".causeProcess").empty();
                }
            });
        }
    });
    return arr;
}

$(document).ready(function () {
    
    function getProductTable(productTypeId) {
        var allPorderWaysArr = getAllPorderWays(); //订购方式
    
        //获取服务类型

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
                    "dataAuth":"20", // （AuthToken）00: 所有，10：租户下所有，20：该用户下所有
                    "product": {
                        "productStatus": $("#productStatusSelect").val(),
                        "productTypeId": $("#productTypeIdInput").val(),
                        "productName": $('#productNameInput').val()
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
	            field: 'productId',
	            title: '序号',
	            valign: 'middle',
                width: '50px',
                align: "left",
	            formatter: function (value,row,index) {
		            //return index+1; //序号正序排序从1开始
		            var pageSize=$('#productTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
		            var pageNumber=$('#productTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
		            return  '<div>' + (pageSize * (pageNumber - 1) + index + 1) + '</div>';    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
	            }
            },{
                field: 'productName',
                title: '服务名称',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '<a class="inner-ahref" href="'+ $webpath +'/product/productInfoPage?productId='+ row.productId +'&productStatus='+ row.productStatus +'">'+ row.productName +'</a>';
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
                align: 'right',
	            width: '170px',
	            events: operateEvents,
	            formatter: operateFormatter
            }]
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
                var treeArray = [];
                var html = '';
	            for( var i = 0; i < data.length; i++ ){
		            treeArray.push({
			            "id" : data[i].productTypeId,
			            "name" : data[i].productTypeName,
			            "pId": data[i].parentId ? data[i].parentId : '' ,
			            "sortId" : data[i].sortId,
			            "iconSkin" : "productType",
			            "type": "productType"
                    });
                    html += 
                        '<option value="' + data[i].productTypeId + '">' + data[i].productTypeName + '</option>';
                }
	            var setting = {
		            view: {
			            showLine: false,
			            // showIcon: false,
			            selectedMulti: false,
			            dblClickExpand: false,
			            addDiyDom: addDiyDom
		            },
		            data: {
			            simpleData: {
				            enable: true,
				            rootPId: ""
			            }
		            },
		            callback: {
			            onClick: zTreeOnClick,
			            onDblClick: zTreeOnDblClick
		            }
	            };

	            function addDiyDom(treeId, treeNode) {
		            var spaceWidth = 5;
		            var switchObj = $("#" + treeNode.tId + "_switch"),
                    icoObj = $("#" + treeNode.tId + "_ico");
		            switchObj.remove();
		            icoObj.before(switchObj);

		            if (treeNode.level > 1) {
			            var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
			            switchObj.before(spaceStr);
		            }
	            }

	            function zTreeOnClick(event, treeId, treeNode) {
		            // if( treeNode.type == "product" ){
			           //  $("#productId").val(treeNode.id);
			           //  $("#service-name").text(treeNode.name);
		            // } else if( treeNode.type == "productType" ) {
			           //  var zTree = $.fn.zTree.getZTreeObj("serviceTree");
			           //  zTree.expandNode(treeNode);
		            // }
		            zTree.expandNode(treeNode);
		            var productTypeId = treeNode.id;
		            $("#title-span").text('-'+treeNode.name);
		            $(".table-responsive").empty();
                    $(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
                    $("#productStatusSelect").val("");
                    $("#productNameInput").val("");
                    $("#productTypeIdInput").val(productTypeId);
                    $(".beconsole-input-group").find(".inner-text").text("服务状态");
                    $(".dropdown-menu li").removeClass("active");
		            getProductTable();
	            }
	            
	            function zTreeOnDblClick(event, treeId, treeNode) {
		            var productTypeId = treeNode.id;
		            $("#title-span").text('-'+treeNode.name);
		            $(".table-responsive").empty();
		            $(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
		            getProductTable(productTypeId);
	            }

	            var zTreeObj = $.fn.zTree.init($("#serviceTree"), setting, treeArray);
                zTree = $.fn.zTree.getZTreeObj("serviceTree");
                

                //添加服务类型到select
                $("select[name='productTypeId']").empty();
                $("select[name='productTypeId']").append(html);
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

    $(".nav-collapse-left").click(function (e) {
	  	$(".serviceList-container").addClass("left-to-0");
    });
    $(".nav-collapse-right").click(function (e) {
	  	$(".serviceList-container").removeClass("left-to-0");
    });

    $("#showAllProduct").click(function (e) {
			$("#title-span").text('');
			$(".table-responsive").empty();
			$(".table-responsive").append('<table class="table table-hover" id="productTable"></table>');
			getProductTable();
    });

    getProductTable();
    getAllCategory();
});
// canuseProcesses();

function dropdownMenuBindEvent(){
    $(".dropdown-menu li").click(function(e){
        $(this).addClass("active").siblings().removeClass("active");
        var selectVal = $(this).find("a").attr("value");
        var selectText = $(this).find("a").text();

        $("#productStatusSelect").val(selectVal);
        $(".beconsole-input-group").find(".inner-text").text(selectText);
        $('#productTable').bootstrapTable('refresh');
    });
}
dropdownMenuBindEvent();

//服务基本信息校验
function productBasicValidator(){
    $("#regProductForm").bootstrapValidator({
        message: '请正确输入',
        excluded:[":disabled"],
        fields: {
            productName: {
                validators:{
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

productBasicValidator();

//服务属性校验
function productPropertyValidator(){
    $("#propertyForm").bootstrapValidator({
        message: '请正确输入',
        excluded:[":disabled"],
        fields: {
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
            }
        }
    });
}

productPropertyValidator();

//重置服务注册表单
function handleResetForm( formSelector ){

    $(formSelector).data('bootstrapValidator').destroy();
    $(formSelector).data('bootstrapValidator', null);

    $(formSelector).find("input[type='text']").val("");
    $(formSelector).find("input[type='number']").val("");
    $(formSelector).find("input[type='file']").val("");
    $(formSelector).find("input[type='radio']").attr('checked',false);
    $(formSelector).find("input[type='checkbox']").attr('checked',false);
    $(formSelector).find(".file-text").text("添加logo");
    $(".causeProcess").empty();
    productBasicValidator();
}

function handleResetPropertyForm(formSelector){

    $(formSelector).data('bootstrapValidator').destroy();
    $(formSelector).data('bootstrapValidator', null);

    $(formSelector).find("input[type='text']").val("");
    $(formSelector).find("input[type='number']").val("");
    $(formSelector).find("input[type='file']").val("");
    $(formSelector).find("input[type='radio']").not("input[name='initialType']").attr('checked',false);
    $(formSelector).find("input[type='checkbox']").attr('checked',false);

    $(formSelector).find(".proMappingCode").empty();

    $(formSelector).find("select[name='proShowType']")[0].options[0].selected = true;

    $(".basic-table").find("tbody").empty();

    $(".basic-table").find("tbody").append(
        '<tr>'+
        '<td>'+
        '    <div class="form-radio-group">'+
        '        <input id="item1" type="radio" name="defaultEntry">'+
        '        <label for="item1"></label>'+
        '        <span></span>'+
        '    </div>'+
        '</td>'+
        '<td>'+
        '    <span class="basic-inner-text"></span>'+
        '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue1">'+
        '</td>'+
        '<td>'+
        '    <span class="basic-inner-text"></span>'+
        '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue2">'+
        '</td>'+
        '<td>'+
        '    <span class="glyphicon glyphicon-plus basic-edit" aria-hidden="true"></span>'+
        '    <i class="fa fa-trash-o basic-delete" aria-hidden="true"></i>'+
        '</td>'+
        '</tr>'
    );
    $(".basic-inner-input").unbind("blur");
    $(".basic-inner-input").blur(function(){
        $(this).prev().text($(this).val());
        $(this).css("display","none");
        $(this).prev().css("display","block");
    });


    $(".initial-container").css("display","none");

    productPropertyValidator();

    $(".property-submit").unbind("click");
    $(".property-submit").click(function(e){
        propertySubmit();
    });
}

//搜索
$(".glyphicon-search").click(function(){
    $('#productTable').bootstrapTable('refresh');
});

//回车搜索
$(document).keydown(function(e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if(event.keyCode == "13") {
            $('#productTable').bootstrapTable('refresh');
        }
    }
});

$(".btn-refresh").click(function(e){
    $("#productStatusSelect").val("");
    $("#productNameInput").val("");
    $("#productTypeIdInput").val("");
    $(".beconsole-input-group").find(".inner-text").text("服务状态");
    $(".dropdown-menu li").removeClass("active");
    $('#productTable').bootstrapTable('refresh');
});

$(".bconsole-input-placeholder").click(function(){
    $(this).css("display","none");
    $("#productNameInput").focus();
});

$("#productNameInput").blur(function(e){
    if( $(this).val() === "" ){
        $(".bconsole-input-placeholder").css("display","block");
    }else {
        $(".bconsole-input-placeholder").css("display","none");
    }
});

//服务注册
$("#regProductPage-btn").click(function(){
    $("#regProductModal").modal("show");
});

//服务注册基本信息提交
$(".product-basic-submit").click(function(){
    //保存服务基本信息

    //校验
    var basicValidator = $("#regProductForm").data('bootstrapValidator');
    basicValidator.validate();
    if (!basicValidator.isValid()) {
        $('.has-error').each(function (index,item) {
            if(index !== 0) {
                $(item).find('.help-block').hide();
                $(item).find('.form-control').addClass('clear-error-style');
            }else{
                $(item).find('.help-block').show();
                $(item).find('.form-control').removeClass('clear-error-style');
                $(item).find('.form-control').focus();
                setTimeout(function () {
                    $("#regProductForm").data('bootstrapValidator').destroy();
                    productBasicValidator();
                },5000);
            }
        })
    }
	if(  !basicValidator.isValid() ){
		return;
    }
    
    //校验图片大小
    var file_obj = document.getElementById('productIcon').files[0];
    if(Math.round(file_obj.size/1024*100)/100 >= 200){
        var fileHelp = $("#productIcon").parents(".file-group").next();
        fileHelp.text("上传图片过大，请上传小于200kb的图片！");
        fileHelp.css("display","block");
        return;
    }
    
    var productBasicData = $("#regProductForm").serializeArray();
    for( var i = 0; i < productBasicData.length; i++ ){
        regProductFormData[productBasicData[i].name] = productBasicData[i].value;
    }

    //添加数据到最终提交数据
    // finalFormData.append("product", JSON.stringify(productBasicData));
    finalFormData.append("productIcon", file_obj);
    
    $("#info-productName").text(regProductFormData.productName);
    $("#info-productVersion").text(regProductFormData.productVersion);
    $("#info-productIntrod").text(regProductFormData.productIntrod);

    $("#regProductModal").modal("hide");
    $(".serviceList-container").css("display","none");
    $(".property-container").css("display","block");

    handleResetForm("#regProductForm");
});

//选取文件change
$("#productIcon").change(function(){
    $(this).next().text($(this).val().substring($(this).val().lastIndexOf("\\")+1));
});

//添加属性按钮
$("#property-add-btn").click(function(){
    $("#propertyModal").find(".bconsole-modal-title .title").text("添加属性");
    $("#propertyModal").modal("show");
});

//属性点击
// $(".basic-inner-text").click(function(){
//     $(this).css("display","none");
//     $(this).next().css("display","block");
//     $(this).next().focus();
// });

//input 鼠标离开/---------
$(".basic-inner-input").blur(function(){
    $(this).prev().text($(this).val());
    $(this).css("display","none");
    $(this).prev().css("display","block");
});

//属性模态框点击事件
$("#propertyModal").click(function(e){
    
    if( $(e.target).hasClass("basic-inner-text") ){
        //属性点击
        $(e.target).css("display","none");
        $(e.target).next().css("display","block");
        $(e.target).next().focus();
    }else if( $(e.target).hasClass("basic-edit") ){
        var parentTr = $(e.target).parent().parent();
        parentTr.after(
            '<tr>'+
            '<td>'+
            '    <div class="form-radio-group">'+
            '        <input id="item1" type="radio" name="defaultEntry">'+
            '        <label for="item1"></label>'+
            '        <span></span>'+
            '    </div>'+
            '</td>'+
            '<td>'+
            '    <span class="basic-inner-text"></span>'+
            '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue1">'+
            '</td>'+
            '<td>'+
            '    <span class="basic-inner-text"></span>'+
            '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue2">'+
            '</td>'+
            '<td>'+
            '    <span class="glyphicon glyphicon-plus basic-edit" aria-hidden="true"></span>'+
            '    <i class="fa fa-trash-o basic-delete" aria-hidden="true"></i>'+
            '</td>'+
            '</tr>'
        );
        $(".basic-inner-input").unbind("blur");
        $(".basic-inner-input").blur(function(){
            $(this).prev().text($(this).val());
            $(this).css("display","none");
            $(this).prev().css("display","block");
        });
    }else if( $(e.target).hasClass("basic-delete") ){
        var parentTr = $(e.target).parent().parent();
        if( $(".basic-table").find("tbody tr").length === 1 ){
            var lastTr = $(".basic-table").find("tbody tr")[0];
            $(lastTr).find(".basic-inner-text").text("");
            $(lastTr).find(".basic-inner-input").val("");
            return;
        }
        parentTr.remove();
    } 
});

//属性编辑 删除
$(".property-container").click(function(e){
    if( $(e.target).hasClass("property-delete") ){
        var ptr = $(e.target).parent().parent();
        ptr.remove();
    }else if( $(e.target).hasClass("property-edit") ){
        var ptr = $(e.target).parent().parent();
        var pData = ptr.data("propertyInfo");

        var propertyForm = $("#propertyForm");
        propertyForm.find("input[name='proName']").val(pData.proName);
        propertyForm.find("input[name='proCode']").val(pData.proCode);
        propertyForm.find("input[name='proEnName']").val(pData.proEnName);
        propertyForm.find("select[name='proDataType']").val(pData.proDataType);
        propertyForm.find("select[name='proShowType']").val(pData.proShowType);
        propertyForm.find("input[name='proUnit']").val(pData.proUnit);
        propertyForm.find("input[name='proDesc']").val(pData.proDesc);
        propertyForm.find("input[name='proVerfyRule']").val(pData.proVerfyRule);
        propertyForm.find("input[name='proVerfyTips']").val(pData.proVerfyTips);

        var itemProLabel = pData.proLabel.split(",");
        if( itemProLabel.length > 0 ){
            for( var t = 0; t < itemProLabel.length; t++ ){
                switch( itemProLabel[t] ){
                    case "30":
                        propertyForm.find("input[name='proLabel'][value='30']").prop("checked",true);
                        $(".proMappingCode").empty();
                        $(".proMappingCode").append(
                            '<div class="form-group">'+
                                '<label class="col-sm-3 control-label">映射资源属性</label>'+
                                '<div class="col-sm-9">'+
                                    '<select class="form-control" name="proMappingCode">'+
                                        '<option value="CPU" '+ ( pData.proMappingCode == "CPU" ? "selected" : "" ) +'>CPU</option>'+
                                        '<option value="Memory" '+ ( pData.proMappingCode == "Memory" ? "selected" : "" ) +'>Memory</option>'+
                                        '<option value="Storage" '+ ( pData.proMappingCode == "Storage" ? "selected" : "" ) +'>Storage</option>'+
                                    '</select>'+
                                '</div>'+
                            '</div>'
                        );
                        $(".proMappingCode").css("display","block");
                        break;
                    case "20":
                        propertyForm.find("input[name='proLabel'][value='20']").prop("checked",true);
                        break;
                    case "10":
                        propertyForm.find("input[name='proLabel'][value='10']").prop("checked",true);
                        break;
                    default :
                        break;
                }
            }
        }
        if( pData.proShowType != "10" ){
            $(".initial-container").css("display","block");
            if( pData.proInitValue !== "" ){
                //初始值类型为手动输入
                propertyForm.find("input[name='initialType'][value='10']").attr("checked",true);
                $(".basic-key-value").css("display","block");
                $(".basic-url").css("display","none");
                $(".basic-key-value .basic-table tbody").empty();
                for( var s = 0; s < pData.initialTable.length; s++ ){
                    $(".basic-key-value .basic-table tbody").append(
                        '<tr>'+
                            '<td>'+
                            '    <div class="form-radio-group">'+
                            '        <input id="item1" type="radio" name="defaultEntry" '+ ( pData.initialTable[s].checked ? "checked" : "" ) +'>'+
                            '        <label for="item1"></label>'+
                            '        <span></span>'+
                            '    </div>'+
                            '</td>'+
                            '<td>'+
                            '    <span class="basic-inner-text">'+ pData.initialTable[s]["key"] +'</span>'+
                            '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue1" value="'+  pData.initialTable[s]["key"] +'">'+
                            '</td>'+
                            '<td>'+
                            '    <span class="basic-inner-text">'+ pData.initialTable[s]["value"] +'</span>'+
                            '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue2" value="'+ pData.initialTable[s]["value"] +'">'+
                            '</td>'+
                            '<td>'+
                            '    <span class="glyphicon glyphicon-plus basic-edit" aria-hidden="true"></span>'+
                            '    <i class="fa fa-trash-o basic-delete" aria-hidden="true"></i>'+
                            '</td>'+
                        '</tr>'
                    );
                }

                $(".basic-inner-input").unbind("blur");
                $(".basic-inner-input").blur(function(){
                    $(this).prev().text($(this).val());
                    $(this).css("display","none");
                    $(this).prev().css("display","block");
                });
            } else {
                //初始值类型是url 
                propertyForm.find("input[name='initialType'][value='20']").attr("checked",true);
                $(".basic-key-value").css("display","none");
                $(".basic-url").css("display","block");
                $(".basic-key-value .basic-table tbody").empty();
                $(".basic-url").find("input").val(pData.initialUrl);
            }
        }
        $("#propertyModal").find(".bconsole-modal-title .title").text("编辑属性");
        $("#propertyModal").modal("show");

        $(".property-submit").unbind("click");
        $(".property-submit").click(function(e){
            propertySubmit( ptr );
        });
    }
});

//属性标签change
$("#propertyForm").find("input[name='proLabel']").change(function(){
    var proLabels = $("input[name='proLabel']:checked");
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
            if( $(".proMappingCode").find("select[name='proMappingCode']").length > 0 ){
                return;
            }
            $(".proMappingCode").empty();
            $(".proMappingCode").append(
                '<div class="form-group">'+
                    '<label class="col-sm-3 control-label">映射资源属性</label>'+
                    '<div class="col-sm-9">'+
                        '<select class="form-control" name="proMappingCode">'+
                            '<option value="CPU">CPU</option>'+
                            '<option value="Memory">Memory</option>'+
                            '<option value="Storage">Storage</option>'+
                        '</select>'+
                    '</div>'+
                '</div>'
            )
        }else{
            $(".proMappingCode").empty();
        }
    }
});

//初始值change事件
$("input[name='initialType']").change(function(){
    var initialType = $("input[name='initialType']:checked").val();
    if( initialType === "10" ){
        //手动输入
        $(".border-up-empty").removeClass("up-empty-url");
        $(".basic-key-value").css("display","block");
        $(".basic-url").css("display","none");


    }else {
        //URL
        $(".border-up-empty").addClass("up-empty-url");
        $(".basic-key-value").css("display","none");
        $(".basic-url").css("display","block");


    }
});

//控件类型改变
$("select[name='proShowType']").change(function(){
    if( $(this).val() === "10" ){
        $(".initial-container").css("display","none");    
    }else {
        //清空数据
        $(".basic-table tbody").empty();
        $(".basic-table tbody").append(
            '<tr>'+
            '<td>'+
            '    <div class="form-radio-group">'+
            '        <input id="item1" type="radio" name="defaultEntry">'+
            '        <label for="item1"></label>'+
            '        <span></span>'+
            '    </div>'+
            '</td>'+
            '<td>'+
            '    <span class="basic-inner-text"></span>'+
            '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue1">'+
            '</td>'+
            '<td>'+
            '    <span class="basic-inner-text"></span>'+
            '    <input type="text" class="form-control beconsole-biner-input basic-inner-input" name="initValue2">'+
            '</td>'+
            '<td>'+
            '    <span class="glyphicon glyphicon-plus basic-edit" aria-hidden="true"></span>'+
            '    <i class="fa fa-trash-o basic-delete" aria-hidden="true"></i>'+
            '</td>'+
            '</tr>'
        );
        $(".basic-inner-input").unbind("blur");
        $(".basic-inner-input").blur(function(){
            $(this).prev().text($(this).val());
            $(this).css("display","none");
            $(this).prev().css("display","block");
        });
        $(".initial-container").css("display","block");
    }
});

// 属性提交
function propertySubmit( tr ){
    //校验属性
    var propertyValidator = $("#propertyForm").data('bootstrapValidator');
    propertyValidator.validate();
    if(  !propertyValidator.isValid() ){
        return;
    }

    var propertyForm = $("#propertyForm");
    var proLabel = [];
    propertyForm.find("input[name='proLabel']:checked").each(function(){
        proLabel.push($(this).val());
    })
    var curProperty = {
        "proName": propertyForm.find("input[name='proName']").val(),
        "proCode": propertyForm.find("input[name='proCode']").val(),
        "proEnName": propertyForm.find("input[name='proEnName']").val(),
        "proLabel": proLabel.join(","),
        "proDataType": propertyForm.find("select[name='proDataType']").val(),
        "proShowType": propertyForm.find("select[name='proShowType']").val(),
        "proUnit": propertyForm.find("input[name='proUnit']").val(),
        "proDesc": propertyForm.find("input[name='proDesc']").val(),
        "proVerfyRule": propertyForm.find("input[name='proVerfyRule']").val(),
        "proVerfyTips": propertyForm.find("input[name='proVerfyTips']").val()
    };

    //proMappingCode
    for( var i = 0; i < proLabel.length; i++ ){
        if( proLabel[i] === "30" ){
            curProperty.proMappingCode = $("select[name='proMappingCode']").val();
        }
    }

    if( curProperty.proShowType !== "10" ){
        //除文本框之外
        if( propertyForm.find("input[name='initialType']:checked").val() === "10" ){
            //手动
            var proInitArray = [];
            var proInitArray2 = [];
            var trs = $(".basic-table").find("tbody tr");
            if( trs.length > 0 ){
                if( trs.length === 1 ){
                    if( $(trs[0]).find("input[name='initValue1']").val() !== "" || $(trs[0]).find("input[name='initValue2']").val() !== "" ){
                        proInitArray.push({
                            "checked": $(trs[0]).find("input[type='radio']").is(':checked'),
                            "key": $(trs[0]).find("input[name='initValue1']").val(),
                            "value": $(trs[0]).find("input[name='initValue2']").val()
                        });
                        proInitArray2.push({
                            "checked": $(trs[0]).find("input[type='radio']").is(':checked'),
                            "key": $(trs[0]).find("input[name='initValue1']").val(),
                            "value": $(trs[0]).find("input[name='initValue2']").val()
                        });
                    }
                }else if( trs.length > 1 ){
                    for( var i = 0; i < trs.length; i++  ){
                        proInitArray.push({
                            "checked": $(trs[i]).find("input[type='radio']").is(':checked'),
                            "key": $(trs[i]).find("input[name='initValue1']").val(),
                            "value": $(trs[i]).find("input[name='initValue2']").val()
                        })
                        proInitArray2.push({
                            "checked": $(trs[i]).find("input[type='radio']").is(':checked'),
                            "key": $(trs[i]).find("input[name='initValue1']").val(),
                            "value": $(trs[i]).find("input[name='initValue2']").val()
                        })
                    }
                }
            }

            var proInitValue = [];
            if( proInitArray2.length > 0 ){
                var cutItem = null;
                for( var j = 0; j < proInitArray2.length; j++ ){
                    if( proInitArray2[j].checked ){
                        cutItem = proInitArray2[j];
                        proInitArray2.splice(j,1);
                        break;
                    }
                }
                if( cutItem ){
                    proInitArray2.unshift(cutItem);
                }
                for( var s = 0; s < proInitArray2.length; s++ ){
                    var key1 = proInitArray2[s]["key"];
                    var value1 = proInitArray2[s]["value"];
                    var obj = {};
                    obj[key1] = value1;
                    proInitValue.push(obj);
                }
            }
        
            curProperty.initialTable = proInitArray;
            curProperty.proInitValue = JSON.stringify(proInitValue);
            curProperty.initialUrl = "";
        } else if( propertyForm.find("input[name='initialType']:checked").val() === "20" ){
            //url
            curProperty.initialUrl = $(".basic-url").find("input").val();
            curProperty.initialTable = [];
            curProperty.proInitValue = "";
        }
    }else {
        curProperty.initialTable = [];
        curProperty.proInitValue = "";
        curProperty.initialUrl = "";
    }

    $("#propertyModal").modal("hide");
    handleResetPropertyForm('#propertyForm');

    if( tr ){
        //编辑
        var editHtml = '';
        editHtml += '<td>' + curProperty.proName + '</td>\n' ;

         //属性类型
        switch( curProperty.proShowType ){
            case "10":
                editHtml += '<td>byte</td>\n';
                break;
            case "20":
                editHtml += '<td>short</td>\n';
                break;
            case "30":
                editHtml += '<td>int</td>\n';
                break;
            case "40":
                editHtml += '<td>long</td>\n';
                break;
            case "50":
                editHtml += '<td>float</td>\n';
                break;
            case "60":
                editHtml += '<td>double</td>\n';
                break;
            case "70":
                editHtml += '<td>char</td>\n';
                break;
            case "80":
                editHtml += '<td>boolean</td>\n';
                break;
            default :
                editHtml += '<td>-</td>\n';
                break;
        }
        var itemProLabel = curProperty.proLabel.split(",");
        var itemProLabelArray = [];
        if( itemProLabel.length > 0 ){
            for( var t = 0; t < itemProLabel.length; t++ ){
                switch( itemProLabel[t] ){
                    case "10":
                        itemProLabelArray.push('申请');
                        break;
                    case "20":
                        itemProLabelArray.push('访问');
                        break;
                    case "30":
                        itemProLabelArray.push('资源');
                        break;
                    default :
                        itemProLabelArray.push('');
                        break;
                }
            }
        }  

        var proInitValue = "";
        if( curProperty.proInitValue != "" ){
            proInitValue = curProperty.proInitValue
        }else {
            proInitValue = curProperty.initialUrl
        }

        editHtml += 
            '   <td>' + proInitValue + '</td>\n' +
            '   <td>' + curProperty.proVerfyRule + '</td>\n' +
            // '   <td>' + item.proVerfyTips + '</td>\n' +
            '   <td>' + curProperty.proDesc + '</td>\n' +
            '   <td>' + curProperty.proUnit + '</td>\n' ;
        
        editHtml +=       
            '   <td>' + itemProLabelArray.join(",") + '</td>\n' +
            '   <td>\n' +
            '       <span class="product-delete property-delete" title="删除"></span>\n' +
            '       <span class="pruduct-edit property-edit" title="编辑"></span>\n' +
            '   </td>\n' ;
        $(tr).empty();
        $(tr).append(editHtml);
        $(tr).data("propertyInfo", curProperty);
    }else{
        //保存
        var uuid = getuuid(16,16);
        var html = 
                    '<tr id="'+ uuid +'">\n' +
                    '   <td>' + curProperty.proName + '</td>\n' ;
                    // '   <td>' + item.proEnName + '</td>\n' ;
                //控件类型
                // switch( item.proShowType ){
                //     case "10":
                //         html += '<td>文本框</td>\n';
                //         break;
                //     case "20":
                //         html += '<td>下拉框</td>\n';
                //         break;
                //     case "30":
                //         html += '<td>单选框</td>\n';
                //         break;
                //     case "40":
                //         html += '<td>复选框</td>\n';
                //         break;
                //     case "50":
                //         html += '<td>Slider滑块</td>\n';
                //         break;
                //     default :
                //         html += '<td>-</td>\n';
                //         break;
                // }

                //属性类型
                switch( curProperty.proShowType ){
                    case "10":
                        html += '<td>byte</td>\n';
                        break;
                    case "20":
                        html += '<td>short</td>\n';
                        break;
                    case "30":
                        html += '<td>int</td>\n';
                        break;
                    case "40":
                        html += '<td>long</td>\n';
                        break;
                    case "50":
                        html += '<td>float</td>\n';
                        break;
                    case "60":
                        html += '<td>double</td>\n';
                        break;
                    case "70":
                        html += '<td>char</td>\n';
                        break;
                    case "80":
                        html += '<td>boolean</td>\n';
                        break;
                    default :
                        html += '<td>-</td>\n';
                        break;
                }

                var proInitValue = "";
                if( curProperty.proInitValue != "" ){
                    proInitValue = curProperty.proInitValue
                }else {
                    proInitValue = curProperty.initialUrl
                }

                html += 
                    '   <td>' + proInitValue + '</td>\n' ;
                html +=     
                    '   <td>' + curProperty.proVerfyRule + '</td>\n' +
                    // '   <td>' + item.proVerfyTips + '</td>\n' +
                    '   <td>' + curProperty.proDesc + '</td>\n' +
                    '   <td>' + curProperty.proUnit + '</td>\n' ;
                var itemProLabel = curProperty.proLabel.split(",");
                var itemProLabelArray = [];
                if( itemProLabel.length > 0 ){
                    for( var t = 0; t < itemProLabel.length; t++ ){
                        switch( itemProLabel[t] ){
                            case "10":
                                itemProLabelArray.push('申请');
                                break;
                            case "20":
                                itemProLabelArray.push('访问');
                                break;
                            case "30":
                                itemProLabelArray.push('资源');
                                break;
                            default :
                                itemProLabelArray.push('');
                                break;
                        }
                    }
                }         
                html +=       
                    '   <td>' + itemProLabelArray.join(",") + '</td>\n' +
                    '   <td>\n' +
                    '       <span class="product-delete property-delete" title="删除"></span>\n' +
                    '       <span class="pruduct-edit property-edit" title="编辑"></span>\n' +
                    '   </td>\n' +
                    '</tr>';

        $("#propertyTable tbody").append(html);
            
        $("#"+ uuid).data("propertyInfo", curProperty);
    }
}

//属性按钮提交
$(".property-submit").click(function(){
    // svcRegisterAttrArr.push(curProperty);
    propertySubmit();
});

//最终提交数据
$("#final-submit").click(function(){
    regProductFormData.productStatus = "20";

    //遍历服务属性
    var propertyTtrs = $("#propertyTable").find("tbody tr");
    if( propertyTtrs.length > 0 ){
        for( var i = 0; i < propertyTtrs.length; i++  ){
            var curProperty = $(propertyTtrs[i]).data("propertyInfo");
            svcRegisterAttrArr.push(curProperty);
        }
    }

    regProductFormData.productAttrOrm = svcRegisterAttrArr;
    finalFormData.append("product", JSON.stringify(regProductFormData));

    $.ajax({
        type: 'POST',
        data: finalFormData,
        processData: false,
        contentType: false,
        url: $webpath + "/api/product/resgProduct",
        dataType: "json",
        success: function (result) {
            if (result.code == 200) {
                $.message('服务创建成功！');
                $(".property-container").css("display","none");
                $(".serviceList-container").css("display","block");
                
                //清空数据
                svcRegisterAttrArr = [];
                regProductFormData = {}; 
                finalFormData = new FormData();

                //刷新表格
                $('#productTable').bootstrapTable('refresh');
            } else {
                $.message({
					message:'服务创建失败！',
					type:'error'
				});
            }
        }
    });
});