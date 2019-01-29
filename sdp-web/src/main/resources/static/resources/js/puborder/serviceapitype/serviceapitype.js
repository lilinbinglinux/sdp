/**
 * Created by niu on 2017/10/24.
 */
var zTreeObj;
var zTreeObj2;
var serTypeId;
var parentId;
$(document).ready(function () {
    initzTree();
    initzTree2();
    initServiceTypeTable();

    //弹出内链接框
    $('.m1').on("click", function () {
        $("#myModalLabel").html("新建服务类型");
        $("#modaltype").attr("value", "insert");
        $("#typeName").val("");
        $("#shareFlag").val("");
        $("#stopFlag").val("");
        $('#proModal').modal();
    });

    $('.updateInterface').on("click", updateInterface);
    $('.deleteInterface').on("click", selectUsed);
});

/**
 * 树的初始化
 */
function initzTree() {
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        treeObj: null,
        async: {
            enable: true
        },
        callback: {
            onRightClick: function (e, treeId, treeNode) {
                if (!treeNode) return;
                zTreeObj.selectNode(treeNode);
                $(".bootstrapMenu").hide();
                $("#apiAndSer").attr("value", "api");
                if (treeNode.apiTypeId == "root") {
                    $("#blankContextMenu").css("left", e.clientX);
                    $("#blankContextMenu").css("top", e.clientY);
                    $("#blankContextMenu").show();
                } else {
                    $("#folderContextMenu").css("left", e.clientX);
                    $("#folderContextMenu").css("top", e.clientY);
                    $("#folderContextMenu").show();
                }
            }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: 'apiTypeId',
                pIdKey: 'parentId'
            },
            key: {
                name: 'typeName'
            },
            async: {
                enable: true
            }
        }
    };

    if (zTreeObj != null) {
        zTreeObj.destroy()
    }
    //接口类型查询
    $.ajax({
        "url": webpath + "/serviceApiType/selectFilterDate",
        "type": "POST",
        success: function (data) {
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].apiTypeId == "root") {
                        data[i].open = "true";
                    }
                }
                zTreeObj = $.fn.zTree.init($("#proTreediv"), setting, data);
            } else {
                layer.msg('暂无数据', {time: 1000, icon: 5});
            }
        }
    });

    //取消菜单事件
    $("body").bind({
        click: function (e) {
            $(".bootstrapMenu").hide();
        }
    });

}

function initzTree2() {
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        treeObj: null,
        async: {
            enable: true
        },
        callback: {
            onClick: function (e, treeId, treeNode) {
                if (!treeNode) return;
                $("#flowchart-middleContent2").css("display", "block");
                initServiceTypeTable(treeNode.serTypeId);
            },
            onRightClick: function (e, treeId, treeNode) {
                if (!treeNode) return;
                zTreeObj.selectNode(treeNode);
                $(".bootstrapMenu").hide();
                $("#apiAndSer").attr("value", "Ser");
                if (treeNode.serTypeId == "root" || treeNode.serTypeId == "root2") {
                    $("#blankContextMenu").css("left", e.clientX);
                    $("#blankContextMenu").css("top", e.clientY);
                    $("#blankContextMenu").show();
                } else {
                    $("#folderContextMenu").css("left", e.clientX);
                    $("#folderContextMenu").css("top", e.clientY);
                    $("#folderContextMenu").show();
                }
            }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: 'serTypeId',
                pIdKey: 'parentId'
            },
            key: {
                name: 'serTypeName'
            },
            async: {
                enable: true
            }
        }
    };

    if (zTreeObj != null) {
        zTreeObj.destroy()
    }
    //服务类型查询
    $.ajax({
        "url": webpath + "/ServiceType/selectFilterDate",
        "type": "POST",
        success: function (data) {
            var reg = new RegExp("(root)", "i");
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (reg.test(data[i].serTypeId)) {        //正则表达式匹配，当存在root时展开
                        data[i].open = "true";
                    }
                    ;
                }
                zTreeObj2 = $.fn.zTree.init($("#proTreediv2"), setting, data);
            } else {
                layer.msg('暂无数据', {time: 1000, icon: 5});
            }
        }
    });

    //取消菜单事件
    $("body").bind({
        click: function (e) {
            $(".bootstrapMenu").hide();
        }
    });

}

/**
 * 类型更新时回显
 */
function updateInterface(data, id, pId) {
    parentId = pId;
    var nodes = zTreeObj.getSelectedNodes();
    console.log(nodes[0]);
    if(id != null && id != ""){
        serTypeId = id;
    }else{
        if (nodes.length == 0)return;
        serTypeId = nodes[0].serTypeId;
    }

    if ($("#apiAndSer").val() == "api") {
        //接口类型回显
        $.ajax({
            "url": webpath + "/serviceApiType/selectOne",
            "type": "POST",
            data: {
                apiTypeId: serTypeId
            },
            success: function (data) {
                var shareFlag = data[0].shareFlag;
                var stopFlag = data[0].stopFlag;
                $("#modaltype").attr("value", "update");
                $("#shareFlag").css("display", "block");
                $("#typeName").val(data[0].typeName);
                $("input[name='shareFlag'][value=" + shareFlag + "]").attr("checked", true);
                $("input[name='stopFlag'][value=" + stopFlag + "]").attr("checked", true);
                $('#proModal').modal();
            }
        })
    } else {
        //服务类型回显
        $.ajax({
            "url": webpath + "/ServiceType/selectOne",
            "type": "POST",
            data: {
                serTypeId: serTypeId
            },
            success: function (data) {
                $("#myModalLabel").html("修改服务类型");
                var stopFlag = data[0].stopFlag;
                $("#modaltype").attr("value", "update");
                $("#typeName").val(data[0].serTypeName);
                $("input[name='stopFlag'][value=" + stopFlag + "]").attr("checked", true);
                $("#shareFlag").css("display", "none");
                $('#proModal').modal();
            }
        })
    }

}

/**
 * 删除之前进行判断
 */
function selectUsed(data, id, pId) {
    parentId = pId;
    var nodes = zTreeObj.getSelectedNodes();
    if(id != null && id != ""){
        serTypeId = id;
    }else{
        if (nodes.length == 0)return;
        serTypeId = nodes[0].serTypeId;
    }
    $.ajax({
        "url": webpath + "/ServiceType/selectUsed",
        "type": "POST",
        async:false,
        data: {
            "serTypeId": serTypeId,
        },
        success: function (data) {
            if (data == "unused") {
                deleteInterface(serTypeId);
            } else {
                layer.msg('该类型已被使用，暂无法删除!', {time: 2000, icon: 2});
            }
        }
    });
}

/**
 * 逻辑删除
 */
function deleteInterface(serTypeId) {
    if ($("#apiAndSer").val() == "api") {
        //接口删除
        $.ajax({
            "url": webpath + "/serviceApiType/logicDelete",
            "type": "POST",
            async:false,
            data: {
                apiTypeId: serTypeId
            },
            success: function (data) {
                initzTree();
                initzTree2();
                initServiceTypeTable(parentId);
                layer.msg('成功删除!', {time: 2000, icon: 1});
            }
        })
    } else {
        //接口删除
        $.ajax({
            "url": webpath + "/ServiceType/logicDelete",
            "type": "POST",
            async:false,
            data: {
                serTypeId: serTypeId
            },
            success: function (data) {
                initzTree();
                initzTree2();
                initServiceTypeTable(parentId);
                layer.msg('成功删除!', {time: 2000, icon: 1});
            }
        })
    }

}

/**
 * 服务详情表格初始化
 * @param parentId
 */
function initServiceTypeTable(parentId) {
    $("#id-serviceTypeTable").width('100%').dataTable({
        "destroy": true,
        "paging": false,
        "columns": [
            {"data": "serTypeId"},
            {"data": "serTypeName"},
            {"data": "parentName"},
            {"data": "stopFlag"},
            {"data": "createDateString"},
            {"data": "creatUser"},
            {"data": "tenantId"},
            {"data": ""},
        ],
        ajax: {
            url: webpath + '/ServiceType/getPageData',
            "type": 'POST',
            "data": {
                parentId : parentId,
            },
            "dataSrc": function (json) {
                json.iTotalRecords = json.total;
                json.iTotalDisplayRecords = json.total;
                return json.data;
            }
        },
        columnDefs: [
            {
                "targets": 0,//类型ID
                "data": null,
                "visible": false,
            },
            {
                "targets": 1,//类型名称
                "data": null,
            },
            {
                "targets": 2,//父ID
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    if(data != null && data != ""){
                        data = data.substring(1, data.length);
                        if (data == row.serTypeName){
                            html += "根节点";
                        }else{
                            html += data;
                        }
                    }else {
                        html += "";
                    }
                    return html;
                }
            },
            {
                "targets": 3,//当前状态
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    if (data == "0") {
                        html += '<span style="color: green;">正常</span>';
                    } else {
                        html += '<span style="color: red;">停用</span>';
                    }
                    return html;
                }
            },
            {
                "targets": 4,//创建时间
                "data": null,
                "render": function (data, type, row) {
                    var html = '', date = row.createDateString;
                    if (data == null || data == "") {
                        html += '暂无时间';
                    } else {
                        html += date;
                    }
                    return html;
                }
            },
            {
                "targets": 5,//创建者
                "data": null,
            },
            {
                "targets": 6,//租户ID
                "data": null,
            },
            {
                "targets": 7,//操作
                "data": null,
                "render": function (data, type, row) {
                    var html = '';
                    html += '<a href="javascript:void(0);" onclick="updateInterface(\'\',\'' + row.serTypeId + '\',\'' + row.parentId + '\')" class="icon-wrap" title="修改类型"><i class="iconfont i-btn">&#xe66f;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="selectUsed(\'\',\'' + row.serTypeId + '\',\'' + row.parentId + '\')" class="icon-wrap" title="删除类型"><i class="iconfont i-btn">&#xe614;</i></a>';
                    html += '&nbsp;&nbsp;';
                    return html;
                }
            }
        ],
    });
}

