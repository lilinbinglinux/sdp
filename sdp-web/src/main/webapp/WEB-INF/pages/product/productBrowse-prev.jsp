<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/11/2
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务浏览</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
</head>
<body class="product-outer">
<div class="container-fluid product-container" style="padding: 20px;">
    <%--查询--%>
    <form class="form-inline" id="svcManageSearchForm">
        <div class="form-group">
            <select class="form-control" name="productTypeId">
                <option value="" selected style="display:none;">服务类型</option>
            </select>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="productName" placeholder="服务名称">
        </div>
        <button type="button" class="btn btn-primary" onclick="$('#productTable').bootstrapTable('refresh');">查询</button>
        <button type="button" class="btn btn-default" onclick="$('#svcManageSearchForm')[0].reset();">重置</button>
    </form>

    <%--列表--%>
    <div class="table-responsive">
        <table class="table table-hover" id="productTable"></table>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";

    $(document).ready(function () {
        getProductTable();
        getAllCategory();
    });

    function getProductTable() {
        var allPorderWaysArr = getAllPorderWays();

        $("#productTable").bootstrapTable({
            url: $webpath + "/api/product/allProducts",
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
            queryParams: function (params) {
                return JSON.stringify({
                    "page": {
                        "pageNo": (params.offset / params.limit) + 1,
                        "pageSize": params.limit
                    },
                    "product": {
                        "productStatus": "30",
                        "productTypeId": $('#svcManageSearchForm select[name="productTypeId"]').val(),
                        "productName": $('#svcManageSearchForm input[name="productName"]').val()
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
                field: 'productName',
                title: '服务名称',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return '<a href="javascript:;" onclick="toProductApply(\'' + row.productId + '\',\'' + row.productStatus + '\')">'+ row.productName +'</a>';
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
            }]
        })
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
            }
        });
        return arr;
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
                    $.each(data,function (index, item) {
                        if(item.typeStatus != '1') {
                            str += '<option value="'+ item.productTypeId +'">'+ item.productTypeName +'</option>';
                        }
                    });
                    $('#svcManageSearchForm select[name="productTypeId"]').append(str);
                }
            }
        })
    }

    // 判断可否进入产品申请
    function toProductApply(productId) {
        window.open($webpath +'/v1/mall/productDetails?svcId='+ productId);
    }

</script>
</html>
