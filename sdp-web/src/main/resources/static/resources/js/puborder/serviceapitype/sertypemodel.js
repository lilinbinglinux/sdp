$(document).ready(function () {
    $("#addProBtn").unbind("click");
    $("#addProBtn").bind("click", insertAndUpdateService);

    $("input[name=typeId]").click(function () {
        var shareFlag = $('input[name="shareFlag"]:checked').val();
        var stopFlag = $('input[name="stopFlag"]:checked').val();
    });

});

/**
 * 添加类型
 */
function insertAndUpdateService(){
    //当其为接口类型时
    if($("#apiAndSer").val() == "api"){
        var nodes = zTreeObj.getSelectedNodes();
        if ($("#modaltype").val() == "update"){
            $.ajax({
                "url": webpath + "/serviceApiType/update",
                "type": "POST",
                data: {
                    apiTypeId: nodes[0].apiTypeId,
                    typeName: $("#typeName").val(),
                    shareFlag: $('input[name="shareFlag"]:checked').val(),
                    stopFlag: $('input[name="stopFlag"]:checked').val(),
                },
                success: function (data) {
                    initzTree();
                    initzTree2();
                }
            })
        }else {
            $.ajax({
                "url": webpath + "/serviceApiType/insert",
                "type": "POST",
                data: {
                    typeName: $("#typeName").val(),
                    parentId: nodes[0].apiTypeId,
                    shareFlag: $('input[name="shareFlag"]:checked').val(),
                    delFlag: "0",
                    stopFlag: $('input[name="stopFlag"]:checked').val(),
                },
                success: function (data) {
                    initzTree();
                    initzTree2();
                }
            })
        }
    }else {
        //当其实服务类型时
        var nodes = zTreeObj.getSelectedNodes();
        if(nodes[0] != null && nodes[0] != "" && nodes[0] != "undefined"){
            serTypeId = nodes[0].serTypeId
        }
        if ($("#modaltype").val() == "update"){
            $.ajax({
                "url": webpath + "/ServiceType/update",
                "type": "POST",
                data: {
                    serTypeId: serTypeId,
                    serTypeName: $("#typeName").val(),
                    stopFlag: $('input[name="stopFlag"]:checked').val(),
                },
                success: function (data) {
                    initzTree();
                    initzTree2();
                    initServiceTypeTable(parentId);
                }
            })
        }else {
            $.ajax({
                "url": webpath + "/ServiceType/insert",
                "type": "POST",
                data: {
                    serTypeName: $("#typeName").val(),
                    parentId: nodes[0].serTypeId,
                    delFlag: "0",
                    stopFlag: $('input[name="stopFlag"]:checked').val(),
                },
                success: function (data) {
                    initzTree();
                    initzTree2();
                    initServiceTypeTable(serTypeId);
                }
            })
        }
    }

}

