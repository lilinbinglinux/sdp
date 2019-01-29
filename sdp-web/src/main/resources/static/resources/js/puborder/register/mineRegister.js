/**
 * Created by niu on 2017/12/18.
 */
$(document).ready(function () {
    initPubTable();

    // 为datatable外的父级设置高度
    $('#id-publishMineTable_wrapper').css('height', $('.panel-body').height()-60);
    // 动态为表格添加父级
    $('#id-publishMineTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').css('height', $('#id-publishMineTable_wrapper').height()-63);
    $('.tab-wrapper').niceScroll({ cursorcolor: "#ccc", horizrailenabled: false});


    $("#id-searchBtn").bind("click",reloadTableData);
    $("#id-resetBtn").bind("click",resetForm);
    $("#id-addtBtn").bind("click",insertFrom);
})


//使用datatable初始化用户表格
function initPubTable() {
    $("#id-publishMineTable").width("100%").dataTable({
        "destroy":true,
        "columns": [
            {"data": "serName"},
            {"data": "serTypeName"},
            {"data": "serCode"},
            {"data": "url"},
            {"data": "createDateString"},
            {"data": "stopFlag"},
            {"data": ""}
        ],
        ajax: {
            url: webpath + '/mineServiceRegisterController/selectRegisterAsynch',
            "type": 'POST',
            "data": function (d) {
                return $.extend({}, d, {
                    "jsonStr": form.serializeStr($("#id-mineSearchForm"))
                });
            },
            "dataSrc": function (json) {
                json.iTotalRecords = json.total;
                json.iTotalDisplayRecords = json.total;
                return json.data;
            }
        },
        columnDefs: [
            {
                "targets": 0,//订阅名称
                "data": null,
            },
            {
                "targets": 1,//订阅类型
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    if(data == "" || data == "null" || data == null){
                        html = "暂无类型";
                    }else {
                        html = data;
                    }
                    return html;
                }
            },
            {
                "targets": 2,//编码格式
                "data": null,
            },
            {
                "targets": 3,//url
                "data": null,
            },
            {
                "targets": 4,//订阅时间
                "data": null,
                "render": function (data, type, row) {
                    var html = '',date = row.createDateString;
                    if(data==null||data==""){
                        html += '暂无时间';
                    }else{
                        html += date;
                    }
                    return html;
                }
            },
            {
                "targets": 5,//当前状态
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    if(data == "0"){
                        html = "<span style='color: green'>正常</span>";
                    }else if(data == "1"){
                        html = "<span style='color: red'>停用</span>";
                    }else {
                        html = "<span>暂无信息</span>";
                    }
                    return html;
                }
            },
            {
                "targets": 6,//操作按钮目标列
                "data": null,
                "render": function (data, type, row) {
                    var id = row.serId;
                    var html = '<a href="javascript:void(0);" onclick="updateService(\'' + row.serId + '\',\'' + row.serVersion + '\',\'' + row.serType + '\')" class="icon-wrap" title="修改信息"><i class="iconfont i-btn">&#xe66f;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="deleteServiceIsEmpty(\'' + id + '\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
                    return html;
                }
            }
        ],
    });
}

//更新
function updateService(flowChartId, serVersion, serType) {
    var url = webpath + "/serviceRegisterController/index?flowChartId=" + flowChartId + "&serVersion=" + serVersion + "&serType=" + serType;
    window.location.href = url;
}

//判断是否被订阅
function deleteServiceIsEmpty(serId) {
    $.ajax({
        "url": webpath + "/orderInterface/serviceIsEmpty",
        "type": "POST",
        data: {
            "serId": serId,
        },
        async: false,
        success: function (data) {
            if(data == null || data == ""){
                deleteServiceAsynch(serId);
            }else {
                layer.msg('该服务已被订阅，不可删除！', {time: 2000, icon: 2});
            }
        }
    })
}

//从删除该服务
function deleteServiceAsynch(serId){
    $.ajax({
        "url": webpath + "/mineServiceRegisterController/deleteServiceAsynch",
        "type": "POST",
        data: {
            "serId": serId,
        },
        async: false,
        success: function (data) {
            if(data == 0){
                layer.msg('删除失败！', {time: 2000, icon: 2});
            }else {
                layer.msg('删除成功！', {time: 2000, icon: 1});
            }
            reloadTableData();
        }
    })

}

//重置按钮
function resetForm() {
    form.clear($("#id-mineSearchForm"));
}

//新建服务
function insertFrom() {
    window.location.href = webpath + "/serviceRegisterController/index";
}

//名称查询
//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
    $("#id-publishMineTable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}
