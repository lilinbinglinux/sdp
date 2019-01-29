$(document).ready(function () {
    initPubTable();
    //查询
    $("#id-searchBtn").bind("click", reloadTableData);
    //重置
    $("#id-resetBtn").bind("click", resetForm);
    // 新增
    $("#id-addtBtn").bind("click", addAPI);


    // 为datatable外的父级设置高度
    $('#id-publishMineTable_wrapper').css('height', $('.panel-body').height() - 60);
    // 动态为表格添加父级
    $('#id-publishMineTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').css('height', $('#id-publishMineTable_wrapper').height() - 63);
    $('.tab-wrapper').niceScroll({cursorcolor: "#ccc", horizrailenabled: false});


});

//使用datatable初始化用户表格
function initPubTable() {
    $("#id-publishMineTable").width("100%").dataTable({
        "columns": [
            {"data": "pubid"},//0
            {"data": "name"},//1
            {"data": "url"},//2
            {"data": "pubdesc"},//3
            {"data": "createdate"},//4
            {"data": "checkstatus"},//5
            {"data": ""},//6
        ],
        ajax: {
            url: webpath + '/publisher/selectPage',
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
                "targets": 0,//订阅编号
                "data": null,
                "visible": false,
            },
            {
                "targets": 1,//订阅名称
                "data": null,
            },
            {
                "targets": 2,//编码格式
                "data": null,
            },
            {
                "targets": 3,//网络协议
                "data": null,
            },
            {
                "targets": 4,//订阅时间
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    var newDate = new Date();
                    newDate.setTime(row.createdate);
                    if (data == null || data == "") {
                        html += '暂无时间';
                    } else {
                        html += newDate.toLocaleString();
                    }
                    return html;
                }
            },
            {
                "targets": 5,//当前审核状态
                "data": null,
                "render": function (data, type, row) {
                    var html = "";
                    if (data == "000") {
                        html = "待审核";
                    } else if (data == "001") {
                        html = "审批中";
                    } else if (data == "100") {
                        html = "审批通过";
                    } else if (data == "999") {
                        html = "审批不通过";
                    }
                    return html;
                }
            },
            {
                "targets": 6,//操作按钮目标列
                "data": null,
                "render": function (data, type, row) {
                    var id = row.pubid;
                    var html = '<a href="javascript:void(0);" onclick="updateInter(\'' + id + '\')" class="icon-wrap" title="修改信息"><i class="iconfont i-btn">&#xe66f;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="deleteInter(\'' + id + '\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
                    html += '<a href="javascript:void(0);" onclick="getInterface(\'' + id + '\')" class="icon-wrap" title="接口信息"><i class="iconfont i-btn">&#xe634;</i></a>';
                    return html;
                }
            }
        ],
    });
}
// 查询  刷新
function reloadTableData() {
    $("#id-publishMineTable").dataTable().fnDraw(false);
}
//重置查询条件
function resetForm() {
    form.clear($("#id-mineSearchForm"));
}
//新增
function addAPI() {
    $('#pruModal').modal();
    $("#myModalLabel").text("接口注册")
    $("#pubid").val("");
    $("#pubname").val("");
    $("#puburl").val("");
    $("#pubport").val("");
    $("#pubprotocal").val("");
    $("#pubmethod").val("");
    $("#pubreqformat").val("");
    $("#pubrespformat").val("");
    $("#pubdesc").val("");
    $("#addPubBtn").bind("click", addPub);
}

//获取编辑时订阅信息
function updateInter(id) {
    $.ajax({
        "url": webpath + "/publisher/getPubById",
        "type": "POST",
        data: {
            "id": id
        },
        async: false,
        success: function (data) {
            $('#pruModal').modal();
            $("#myModalLabel").text("接口修改")
            $("#modaltype").attr("value", "update");
            $("#pubid").val(data.pubid);
            $("#pubname").val(data.name);
            $("#puburl").val(data.url);
            $("#pubport").val(data.pubport);
            $("#pubprotocal").val(data.pubprotocal);
            $("#pubmethod").val(data.method);
            $("#pubreqformat").val(data.reqformat);
            $("#pubrespformat").val(data.respformat);
            $("#pubdesc").val(data.pubdesc);
        }
    })
    $("#addPubBtn").bind("click", updatePub);
}
//修改注册接口
function updatePub() {
    $.ajax({
        "url": webpath + "/publisher/update",
        "type": "POST",
        async: false,
        data: {
            pubid: $("#pubid").val(),
            name: $("#pubname").val(),
            url: $("#puburl").val(),
            pubport: $("#pubport").val(),
            pubprotocal: $("#pubprotocal").val(),
            method: $("#pubmethod").val(),
            reqformat: $("#pubreqformat").val(),
            respformat: $("#pubrespformat").val(),
            reqdatatype: $("#pubreqdatatype").val(),
            returndatatype: $("#pubreturndatatype").val(),
            pubdesc: $("#pubdesc").val()
        },
        success: function () {
            reloadTableData();
        }
    })
}
//添加注册接口
function addPub() {
    var file = $("#ImportPicInput").val();
    var fileName = getFileName(file);
    var filePath = "";
    var pubid = getuuid();
    if (fileName != null && fileName != "") {
        filePath = getDate() + pubid + "/";
    }
    uploadFile(pubid, filePath);
    $.ajax({
        "url": webpath + "/publisher/insert",
        "type": "POST",
        dataType: "json",
        data: {
            pubid: pubid,
            name: $("#pubname").val(),
            url: $("#puburl").val(),
            pubport: $("#pubport").val(),
            pubprotocal: $("#pubprotocal").val(),
            method: $("#pubmethod").val(),
            reqformat: $("#pubreqformat").val(),
            respformat: $("#pubrespformat").val(),
            reqdatatype: $("#pubreqdatatype").val(),
            returndatatype: $("#pubreturndatatype").val(),
            pubdesc: $("#pubdesc").val(),
            parentId: 'normalRoot',
            typeId: $('input[name=typeId]:checked').val(),
            className: $("#className").val(),
            methodInClass: $("#methodInClass").val(),
            filePath: filePath + fileName
        },
        success: function () {
            $("#ImportPicInput").val("");
            reloadTableData();
        }
    })
}
//取消注册
function deleteInter(id) {
    layer.confirm('是否删除？', {
        icon: 3,
        btn: ['是', '否'] //按钮
    }, function (index, layero) {
        $.ajax({
            "url": webpath + "/interfacePo/delete",
            "type": "POST",
            data: {
                "id": id,
                "typeId": "2"
            },
            success: function (data) {
                if (data == "deletesuccess") {
                    layer.msg('删除成功！', {time: 1000, icon: 1});
                    layer.close(index);
                    //刷新项目信息表
                    reloadTableData();
                } else if (data == "deletefalse") {
                    layer.msg('删除失败，请先删除子节点！', {time: 1000, icon: 1});
                    layer.close(index);
                }else if("flowchartdeletefalse"){
                	layer.msg('删除失败，请先删除编排服务！', {time: 1000, icon: 1});
                    layer.close(index);
                }
            }
        });
    });
}

var resourceTypeIcon = {
    "0": "/resources/img/icon/16x16/floder1-black.png",
    "1": "/resources/img/icon/16x16/link1-black.png",
    "2": "/resources/img/icon/16x16/link-black.png",
    "3": "/resources/img/icon/16x16/fun-black.png",
};

//获取文件名称
function getFileName(o){
    var pos=o.lastIndexOf("\\");
    return o.substring(pos+1);
}
function uploadFile(pubid,filePath){
    $.ajaxFileUpload({
        type: "POST",
        url: webpath+"/file/importPicFile.do?pubid="+pubid+"&filePath="+filePath,
        secureuri : false,//是否启用安全提交，默认为false
        fileElementId:'ImportPicInput',//文件选择框的id属性
        dataType: 'json',//服务器返回的格式
        async : false,
        success: function(data){
            if(data.result=='success'){
                //coding
            }else{
                //coding
            }
        }
    });

}
