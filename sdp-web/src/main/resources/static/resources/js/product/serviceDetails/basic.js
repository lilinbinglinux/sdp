/**
 * 概览
 * */
$(document).ready(function () {
    getInstanceTable();

    // 实例类型
    $("#instanceType .dropdown-menu li").click(function(e){
        $(this).addClass("active").siblings().removeClass("active");
        var selectVal = $(this).find("a").attr("value");
        var selectText = $(this).find("a").text();
        $("#instanceTypeSelect").val(selectVal);
        $("#instanceType").find(".inner-text").text(selectText);
        $('#instanceTable').bootstrapTable('refresh');
    });

    // 实例状态
    $("#instanceStatus .dropdown-menu li").click(function(e){
        $(this).addClass("active").siblings().removeClass("active");
        var selectVal = $(this).find("a").attr("value");
        var selectText = $(this).find("a").text();
        $("#instanceStatusSelect").val(selectVal);
        $("#instanceStatus").find(".inner-text").text(selectText);
        $('#instanceTable').bootstrapTable('refresh');
    });
});

function getInstanceTable() {
    $("#instanceTable").bootstrapTable({
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
        ajax: function(request) {
            $.ajax({
                type: "POST",
                url: $webpath + "/api/productNode/nodesByCase",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    "page": {
                        "pageNo": 1,
                        "pageSize": 10
                    },
                    "productCase": {
                        "caseId": window.caseId
                    }
                }),
                success: function (req) {
                    basicInfo(req.caseInfo);

                    request.success({ row : req.nodesInfo.list});
                    $('#instanceTable').bootstrapTable('load', req.nodesInfo.list);
                }
            });
        },
        queryParamsType: 'limit',
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "",
        columns: [{
            field: '',
            title: '实例名称',
            align: 'left',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<a class="inner-ahref" href=""></a>';
            }
        }, {
            field: '',
            title: '实例类型',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '';
            }
        }, {
            field: '',
            title: '实例状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                /*var constructionStatus = Number(row.ci.constructionStatus);
                switch (constructionStatus) {
                    case 1 :
                        return '<span class="status-gray">未构建</span>';
                    case 2 :
                        return '<span class="status-yellow">构建中</span>';
                    case 3 :
                        return '<span class="status-green">构建完成</span>';
                    case 4 :
                        return '<span class="status-red">构建失败</span>';
                    default :
                        return '-';
                }*/
            }
        }, {
            field: '',
            title: '资源',
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
            formatter: function (value, row, index) {
                var html = '';
                return html;
            }
        }]
    })
}

function basicInfo(data) {
    var attrStr = '',timeStr = '',resourceStr = '';
    $.each(data.caseAttrOrm,function (index,item) {
        if(item.proLabel.indexOf(30) === -1) {
            attrStr += '<div class="col-md-6 basic-info-item">\n' +
                '            <span class="gray-font">'+item.proName+'：</span>\n' +
                '            <span class="black-font">'+item.proValue+'</span>\n' +
                '    </div>';
        }
    });
    timeStr += '<div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">创建时间：</span>\n' +
        '                        <span class="black-font">'+data.createDate+'</span>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">创建用户：</span>\n' +
        '                        <span class="black-font">'+data.createBy+'</span>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">更新时间：</span>\n' +
        '                        <span class="black-font"></span>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">更新人：</span>\n' +
        '                        <span class="black-font"></span>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">申请时间：</span>\n' +
        '                        <span class="black-font"></span>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">到期时间：</span>\n' +
        '                        <span class="black-font"></span>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6 basic-info-item">\n' +
        '                        <span class="gray-font">购买人：</span>\n' +
        '                        <span class="black-font"></span>\n' +
        '                    </div>';


    $.each(data.caseAttrOrm,function (index,item) {
        if(item.proLabel.indexOf(30) !== -1) {
            resourceStr += '<div class="col-md-6 basic-info-item">\n' +
                '            <span class="gray-font">'+item.proName+'：</span>\n' +
                '            <span class="black-font">'+item.proValue+'<i style="margin-right: 10px"></i>'+(item.proUnit ? item.proUnit : "")+'</span>\n' +
                '    </div>';
        }
    });
    resourceStr += '<div class="col-md-6 basic-info-item"><span class="gray-font">服务状态：</span>';
    resourceStr += caseStatusShow(data.caseStatus);
    resourceStr += '</div>';

    $('#serviceAttr').html(attrStr);
    $('#timeInfo').html(timeStr);
    $('#resourceInfo').html(resourceStr);

    var basicTopH = $('#serviceBasic').outerHeight();
    $('#serviceAttr').css('height',basicTopH+'px');
    $('#timeInfo').css('height',basicTopH+'px');
    $('#resourceInfo').css('height',basicTopH+'px');

    $(window).resize(function() {
        $('#serviceAttr').css('height','');
        $('#timeInfo').css('height','');
        $('#resourceInfo').css('height','');

        var basicTopH = $('#serviceBasic').outerHeight();
        $('#serviceAttr').css('height',basicTopH+'px');
        $('#timeInfo').css('height',basicTopH+'px');
        $('#resourceInfo').css('height',basicTopH+'px');
    });
}

function caseStatusShow(data) {
    var caseStatus = Number( data );
    switch ( caseStatus ) {
        case 10 :
            return '<span class="black-font"><i></i>未启动</span>';
        case 1010 :
            return '<span class="status-yellow"><i></i>启动中</span>';
        case 20 :
            return '<span class="status-green"><i></i>运行中</span>';
        case 30 :
            return '<span class="status-red"><i></i>停止</span>';
        case 3010 :
            return '<span class="black-font"><i></i>停止中</span>';
        case 40 :
            return '<span class="black-font"><i></i>失败</span>';
        case 50 :
            return '<span class="black-font"><i></i>异常</span>';
        case 60 :
            return '<span class="black-font"><i></i>未创建</span>';
        default :
            return '';
    }
}