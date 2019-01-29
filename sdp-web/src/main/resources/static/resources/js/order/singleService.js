window.operateEvents = {
    'click .svcDetail': function (e, value, row, index) {
        $("#detailModal").find(".modal-body").empty();
        $("#detailModal").find(".modal-body").append('<div class="loading-img"><img src="' + $webpath + '/resources/img/dataModel/loading.gif"></div>');
        $("#detailModal").modal("show");
        $.ajax({
            url: $webpath + "/api/productCase/singleCase/" + row.caseId,
            type: "GET",
            success: function (result) {
                var caseAttrOrm = result.caseAttrOrm;

                if (caseAttrOrm && caseAttrOrm.length > 0) {
                    $("#detailModal").find(".modal-body").empty();
                    for (var i = 0; i < caseAttrOrm.length; i++) {
                        $("#detailModal").find(".modal-body").append(
                            '<div class="row list-row">\n' +
                            '            <div class="col-md-4">\n' +
                            '              <span class="binary-text binary-label">' + caseAttrOrm[i].proName + '：</span>\n' +
                            '            </div>\n' +
                            '            <div class="col-md-8">\n' +
                            '              <span class="binary-text">' + ((caseAttrOrm[i].proValue == "null" || caseAttrOrm[i].proValue == null) ? "没有数据" : caseAttrOrm[i].proValue) + ((caseAttrOrm[i].proUnit == "null" || caseAttrOrm[i].proUnit == null) ? "" : caseAttrOrm[i].proUnit) + '</span>\n' +
                            '            </div>\n' +
                            '</div>'
                        );
                    }
                }
            }
        });
    },
    'click .productName': function (e, value, row, index) {
        window.location.href = $webpath + '/product/serviceDetailsPage?productId=' + row.productId + '&caseId=' + row.caseId;
    },
    'click .svcStart': function (e, value, row, index) {
        $(e.target).parent().prev().empty();
        $(e.target).parent().prev().append("<img src='" + $webpath + "/resources/img/service/loading.gif'>启动中");
        $(e.target).next().attr("disabled", "disabled");
        $(e.target).attr("disabled", "disabled");
        $.ajax({
            url: $webpath + "/api/productCase/updateCase",
            contentType: "application/json",
            type: "POST",
            data: JSON.stringify({
                "caseId": row.caseId,
                "caseStatus": "20"
            }),
            success: function (result) {
                // console.log(result);
            }
        });
    },
    'click .svcStop': function (e, value, row, index) {
        $(e.target).parent().prev().empty();
        $(e.target).parent().prev().append("<img src='" + $webpath + "/resources/img/service/loading.gif'>停止中");
        $(e.target).prev().attr("disabled", "disabled");
        $(e.target).attr("disabled", "disabled");
        $.ajax({
            url: $webpath + "/api/productCase/updateCase",
            contentType: "application/json",
            type: "POST",
            data: JSON.stringify({
                "caseId": row.caseId,
                "caseStatus": "30"
            }),
            success: function (result) {
                // console.log(result);
            }
        });
    },
    'click .svcEdit': function (e, value, row, index) {},
    'click .svcDelete': function (e, value, row, index) {
        $("#delete-caseId").attr('value',row.caseId);
        $("#deleteConfirmModal").modal("show");
    }
};

$(document).ready(function () {
    loadServiceTable();

    $(".dropdown-menu li").click(function (e) {
        $(this).addClass("active").siblings().removeClass("active");
        var selectVal = $(this).find("a").attr("value");
        var selectText = $(this).find("a").text();

        $("#caseStatusSelect").val(selectVal);
        $(".beconsole-input-group").find(".inner-text").text(selectText);
        $('#service-table').bootstrapTable('refresh');
    });

    $(".btn-refresh").click(function(e){
        $("#caseStatusSelect").val('');
        $(".beconsole-input-group").find(".inner-text").text('运行状态');
        $('.dropdown-menu li').removeClass("active");
        $('#svcNameInput').val('');
        $('#service-table').bootstrapTable('refresh');
    });
    $(".glyphicon-search").click(function(){
        $('#service-table').bootstrapTable('refresh');
    });
    $('#svcNameInput').keydown(function(e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $('#service-table').bootstrapTable('refresh');
        }
    });
});

function operateFormatter(value, row, index) {
    var status = row.caseStatus;
    if (status == "10" || status == "30") {
        // 未启动 停止
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail">详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart">启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop" disabled>停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'
        ].join("");
    } else if (status == "20") {
        // 运行
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail">详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart" disabled>启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop">停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'].join("");
    } else if (status == "1010" || status == "3010") {
        // 启动中
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail">详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart" disabled>启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop" disabled>停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'].join("");
    } else if (status == "3010") {
        // 停止中
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail">详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart" disabled>启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop" disabled>停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'].join("");
    } else if (status == "40") {
        // 失败
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail">详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart">启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop" disabled>停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'].join("");
    } else if (status == "50") {
        // 异常
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail">详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart" disabled>启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop" disabled>停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'].join("");
    } else if (status == "60") {
        // 未创建
        return [
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDetail" disabled>详情</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStart" disabled>启动</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcStop" disabled>停止</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcEdit" disabled>编辑</button>',
            '<button class="btn btn-default btn-xs btn-beconsole-small svcDelete">删除</button>'].join("");
    }
}

function loadServiceTable() {
    $("#service-table").bootstrapTable({
        url: $webpath + "/api/productCase/caseInfoByProduct",
        method: "POST",
        contentType: "application/json",
        classes: 'table-no-bordered',
        dataType: "json",
        cache: false,
        striped: false,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
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
                "productId": productId,
                "applyName": $("#svcNameInput").val(),
                "caseStatus": $("#caseStatusSelect").val()
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
        uniqueId: "orderId",
        columns: [{
            field: 'orderId',
            title: '序号',
            align: 'left',
            valign: 'middle',
            width: '50px',
            formatter: function (value, row, index) {
                //return index+1; //序号正序排序从1开始
                var pageSize = $('#service-table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber = $('#service-table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        }, {
            field: 'applyName',
            title: '服务名称',
            align: 'center',
            valign: 'middle',
            events: operateEvents,
            formatter: function (value, row, index) {
                if (row.applyName) {
                    return '<a class="productName inner-ahref">' + row.applyName + '</a>';
                } else {
                    return '<a class="productName inner-ahref">-</a>';
                }
            }
        }, {
            field: 'pNodesCounts',
            title: '实例',
            align: 'center',
            valign: 'middle',
            events: operateEvents,
            formatter: function (value, row, index) {
                row.pNodesCounts.runCount = 0;
                row.pNodesCounts.failCount = 0;
                row.pNodesCounts.stopCount = 0;
                if (row.pNodesCounts.runCount != -1 && row.pNodesCounts.failCount != -1 && row.pNodesCounts.stopCount != -1) {
                    return '<span class="status-green col-md-6 col-sm-12 col-xs-12">运行中<span>'+row.pNodesCounts.runCount+'</span></span>\n' +
                        '    <span class="status-red col-md-6 col-sm-12 col-xs-12">停止中<span>'+row.pNodesCounts.runCount+'</span></span>';
                } else {
                    return '<span>' + row.pNodesCounts.error + '</span>';
                }
            }
        }, {
                field: 'caseAttrOrm',
                title: '资源',
                align: 'center',
                valign: 'middle',
                events: operateEvents,
                formatter: function (value, row, index) {
                    var str = '';
                    var serAttr = row.caseAttrOrm;
                    for (var i = 0; i < serAttr.length; i++) {
                        if (serAttr[i].proCode.toUpperCase() == 'cpu'.toUpperCase()) {
                            str += '<span class="col-md-4 col-sm-12 col-xs-12">CPU<span class="resource-num">' + serAttr[i].proValue + '</span>核</span>';
                        }
                        if (serAttr[i].proCode.toUpperCase() == 'memory'.toUpperCase()) {
                            str += '<span class="col-md-4 col-sm-12 col-xs-12">内存<span class="resource-num">' + serAttr[i].proValue + '</span>GB</span>';
                        }
                        if (serAttr[i].proCode.toUpperCase() == 'capacity'.toUpperCase()) {
                            str += '<span class="col-md-4 col-sm-12 col-xs-12">储存<span class="resource-num">' + serAttr[i].proValue + '</span>GB</span>';
                        }
                    }
                    return str;
                }
            }, {
                field: 'createDate',
                title: '创建时间',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'caseStatus',
                title: '运行状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    switch (value) {
                        case "10":
                            return "<span class='status-gray'>未启动</span>";
                            break;
                        case "1010":
                            return "<img src='" + $webpath + "/resources/img/service/loading.gif'>启动中";
                            break;
                        case "20":
                            return "<span class='status-green'>运行中</span>";
                            break;
                        case "30":
                            return "<span class='status-gray'>停止</span>";
                            break;
                        case "3010":
                            return "<img src='" + $webpath + "/resources/img/service/loading.gif'>停止中";
                            break;
                        case "40":
                            return "<span class='status-red'>失败</span>";
                            break;
                        case "50":
                            return "<span class='status-red'>异常</span>";
                            break;
                        case "60":
                            return "<span class='status-gray'>未创建</span>";
                            break;
                        default :
                            return "-";
                            break;
                    }
                }
            }, {
                field: '',
                title: '操作',
                align: 'right',
                width: '240',
                events: operateEvents,
                formatter: operateFormatter
            }]
    });
}

// 删除服务
function deleteCase() {
    var caseId = $("#delete-caseId").val();
    $.ajax({
        url: $webpath + "/api/productCase/delete",
        type: "DELETE",
        contentType: "application/json",
        data: JSON.stringify({"caseId": caseId}),
        success: function (result) {
            if(result.code == 200) {
                $.message(result.message);
                $('#service-table').bootstrapTable('refresh');
            }else {
                $.message({
                    message:result.message,
                    type:'error'
                });
            }
        }
    });
    $('#deleteConfirmModal').modal('hide');
}