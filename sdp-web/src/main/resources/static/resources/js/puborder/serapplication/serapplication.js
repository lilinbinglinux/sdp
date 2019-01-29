/**
 * Created by niu on 2017/10/24.
 */
var zTreeObj;
$(document).ready(function () {
    initzTree();

    //弹出内链接框
    $('.m1').on("click", function() {
        $("#modaltype").attr("value","insert");
        $("#typeName").val("");
        $('#proModal').modal();
    });

    $('.updateInterface').on("click", updateInterface);
    $('.deleteInterface').on("click", deleteInterface);

    //绑定右键事件
    $("#pro-panel").find(".panel-body").bind({contextmenu: function () {
        return false;
    }});

    $("#proTreediv").bind({
        contextmenu: function(e) {
            $(".bootstrapMenu").hide();
            $("#blankContextMenu").css("left",e.clientX);
            $("#blankContextMenu").css("top",e.clientY);
            $("#blankContextMenu").show();
            return false;
        }
    });

    //取消菜单事件
    $("body").bind({
        click:function(e){
            $(".bootstrapMenu").hide();
        }
    });

})

/**
 * 树的初始化
 */
function initzTree(){
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        treeObj:null,
        async:{
            enable: true
        },
        callback:{
            onRightClick:function (e,treeId,treeNode) {
                if(!treeNode) return;
                zTreeObj.selectNode(treeNode);
                $(".bootstrapMenu").hide();
                $("#apiAndSer").attr("value","api");
                $("#folderContextMenu").css("left",e.clientX);
                $("#folderContextMenu").css("top",e.clientY);
                $("#folderContextMenu").show();
            }
        },
        data:{
            simpleData: {
                enable:true,
                idKey:'applicationId',
            },
            key:{
                name:'applicationName'
            },
            async:{
                enable: true
            }
        }
    };

    if(zTreeObj!=null){
        zTreeObj.destroy()
    }
    $.ajax({
        "url":webpath+"/serApplication/getAllByCondition",
        "type":"POST",
        success:function(data){
            if(data!=null&&data.length>0){
                zTreeObj = $.fn.zTree.init($("#proTreediv"), setting, data);
            }else{
                layer.msg('暂无数据', {time: 1000, icon:5});
            }
        }
    });


}

/**
 * 类型更新时回显
 */
function updateInterface(){
    var nodes = zTreeObj.getSelectedNodes();
    if(nodes.length==0)return;
    $.ajax({
        "url": webpath + "/serApplication/getAllByCondition",
        "type":"POST",
        data: {
            applicationId: nodes[0].applicationId
        },
        success:function(data){
            $("#modaltype").attr("value","update");
            $("#typeName").val(data[0].applicationName);
            $('#proModal').modal();
        }
    })
}

/**
 * 逻辑删除
    */
    function deleteInterface(){
        var nodes = zTreeObj.getSelectedNodes();
        if(nodes.length==0)return;
        $.ajax({
            "url": webpath + "/serApplication/update",
            "type":"POST",
            data: {
                applicationId: nodes[0].applicationId,
                delFlag: "1"
            },
            success:function(data){
                initzTree();
            }
        })
}


