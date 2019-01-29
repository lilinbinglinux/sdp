$(document).ready(function () {
    $("#addProBtn").unbind("click");
    $("#addProBtn").bind("click", insertAndUpdateService);
});

/**
 * 添加类型
 */
function insertAndUpdateService(){
    var nodes = zTreeObj.getSelectedNodes();
    if ($("#modaltype").val() == "update"){
        $.ajax({
            "url": webpath + "/serApplication/update",
            "type": "POST",
            data: {
                applicationId: nodes[0].applicationId,
                applicationName : $("#typeName").val(),
            },
            success: function (data) {
                initzTree();
            }
        })
    }else {
        $.ajax({
            "url": webpath + "/serApplication/insert",
            "type": "POST",
            data: {
                applicationName : $("#typeName").val(),
            },
            success: function (data) {
                initzTree();
            }
        })
    }
}

