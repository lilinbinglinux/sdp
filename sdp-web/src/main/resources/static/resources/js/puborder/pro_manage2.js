var protableinfo ,resource ,paramifream_pubid;
$(document).ready(function(){
    resource = {
        treeObj:null,
        setting : {
            data:{
                simpleData: {
                    enable:true,
                    idKey:'id',
                    pIdKey:'parentId'
                },
                key:{
                    name:'name'
                },
                async:{
                    enable: true
                }
            },
            callback:{
                onRightClick:function(e,treeId,treeNode){
                    if(!treeNode) return;
                    resource.treeObj.selectNode(treeNode);
                    $(".bootstrapMenu").hide();
                    if(treeNode.typeId=="root"){
                        $("#blankContextMenu").css("left",e.clientX);
                        $("#blankContextMenu").css("top",e.clientY);
                        $("#blankContextMenu").show();
                    }else if(treeNode.typeId=="0"){
                        $("#folderContextMenu").css("left",e.clientX);
                        $("#folderContextMenu").css("top",e.clientY);
                        $("#folderContextMenu").show();
                    }else if(treeNode.typeId=="1"){
                        $("#linkContextMenu").css("left",e.clientX);
                        $("#linkContextMenu").css("top",e.clientY);
                        $("#linkContextMenu").show();
                    }else if(treeNode.typeId=="2"){
                        $("#link1ContextMenu").css("left",e.clientX);
                        $("#link1ContextMenu").css("top",e.clientY);
                        $("#link1ContextMenu").show();
                    }else if(treeNode.typeId=="3"){
                        $("#link1ContextMenu").css("left",e.clientX);
                        $("#link1ContextMenu").css("top",e.clientY);
                        $("#link1ContextMenu").show();
                    }
                    else if(treeNode.typeId=="4"){
                        $("#link1ContextMenu").css("left",e.clientX);
                        $("#link1ContextMenu").css("top",e.clientY);
                        $("#link1ContextMenu").show();
                    }
                },
                onClick:function(e,treeId,treeNode){
                    if(treeNode.typeId=="root"){
                        $('#panel-childPage').attr('src', "../pubIfream/proIframe");
                    }
                    else if(treeNode.typeId=="0"){
                        $('#panel-childPage').attr('src', "../pubIfream/promodelIframe");
                        //因为存在ifream没有加载完的情况，所以在0.3秒后重新设置一下
                        setTimeout(function(){
                                $("#panel-childPage").contents().find("#promodelparentId").val(treeNode.id);
                                //reloadData("onlyTable");
                            },
                            300);
                    }
                    else if(treeNode.typeId=="1"){
                        //显示所有接口详情
                    }else if(treeNode.typeId=="2"){
                        setParam();
                    }
                    else if(treeNode.typeId=="3"){
                        setParam();
                    }
                }
            }
        },
        initTree:function(){
            if(resource.treeObj!=null){
                resource.treeObj.destroy()
            }
            $.ajax({//初始化组织机构树
                "url":webpath+"/interfacePo/selectAll",
                "type":"POST",
                success:function(data){
                    if(data!=null&&data.length>0){
                        for(var i=0;i<data.length;i++){
                            data[i].icon=webpath+resourceTypeIcon[data[i].typeId];
                            if(data[i].typeId == "root"){
                                data[i].open = "true";
                            };
                        }
                        resource.treeObj = $.fn.zTree.init($("#proTree"), resource.setting, data);
                    }else{
                        layer.msg('暂无数据', {time: 1000, icon:5});
                    }
                }
            });
        }
    };
    resource.initTree();//初始化树
    //取消菜单事件
    $("body").bind({
        click:function(e){
            $(".bootstrapMenu").hide();
        }
    });
    //绑定面板右键事件
    $("#pro-panel").find(".panel-body").bind({
        contextmenu: function(e) {
            var nodes = resource.treeObj.getSelectedNodes();
            if (nodes.length>0) {
                resource.treeObj.cancelSelectedNode(nodes[0]);
            }
            $(".bootstrapMenu").hide();
            $("#blankContextMenu").css("left",e.clientX);
            $("#blankContextMenu").css("top",e.clientY);
            $("#blankContextMenu").show();
            return false;
        }
    });
    //修改事件绑定
    $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').focus()
    })
    $('#m1').on("click", function() {
        $("#modaltype").val("");
        $("#proname").val("");
        $("#proecode").val("");
        $("#proversion").val("");
        $("#prodescribe").val("");
        $('#proModal').modal();
    });
    $('#m2').on("click", function() {
        $("#modaltype").val("");
        $("#promname").val("");
        $("#promecode").val("");
        $('#proModelModal').modal();
    });
    //删除事件绑定
    $(".deleteInterface").bind("click",deleteInterface);
    $(".updateInterface").bind("click",updateInterface);
    $("#setparam").bind("click",setParam);
    $("#id-prodiv").css("display","block");

    //页面打开合并操作
    $("#flowchart-toggleBar").click(function(){
        if($(this).attr("isOpen") == "true"){
            $(".changeopen").css("display","none");
            $("#flowchart-leftContent").attr("style","width:10%");
            $("#flowchart-rightContent").attr("style","width:90%");
            $(this).attr("isOpen","false");
        }else if($(this).attr("isOpen") == "false"){
            $(".changeopen").css("display","inline");
            $("#flowchart-leftContent").attr("style","width:34%");
            $("#flowchart-rightContent").attr("style","width:66%");
            $("#proTree").attr("style","overflow: auto;height: 500px");
            $(this).attr("isOpen","true");
        }
    });
});

var resourceTypeIcon = {
    "0":"/resources/img/icon/16x16/floder1-org.png",
    "1":"/resources/img/icon/16x16/fun-red.png",
    "2":"/resources/img/icon/16x16/resorce.png",
    "4":"/resources/img/icon/16x16/resorce.png",
    "3":"/resources/img/icon/16x16/resorce.png",
    "root":"/resources/img/icon/16x16/folder-red.png",
    "root1":"/resources/img/icon/16x16/folder-red.png",
};

function deleteInterface(){
    var nodes = resource.treeObj.getSelectedNodes();
    if(nodes.length==0)return;
    var node = nodes[0];
    layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是','否'] //按钮
    }, function(index, layero){
        $.ajax({
            "url":webpath+"/interfacePo/delete",
            "type":"POST",
            data:{
                "id":node.id,
                "typeId":node.typeId,
                "tenant_id":node.tenant_id
            },
            success:function(data){
                if(data == "deletesuccess"){
                    layer.msg('删除成功！', {time: 1000, icon:1});
                    layer.close(index);
                    //刷新项目信息表
                    reloadTableData($("#id-proinfotable"));
                    resource.initTree();
                    $('#panel-childPage').attr('src', "../pubIfream/proIframe");
                }else if(data == "deletefalse"){
                    layer.msg('删除失败，请先删除子节点！', {time: 1000, icon:2});
                    layer.close(index);
                }
            }
        });
    })
}

//修改事件
function updateInterface(){
    var nodes = resource.treeObj.getSelectedNodes();
    if(nodes.length==0)return;
    var node = nodes[0];
    var url;

    if(node.typeId == '0'||node.typeId == '1'){
        url = webpath+"/pro/getProById";
    }else if(node.typeId == '2'||node.typeId == '4'){
        url = webpath+"/publisher/getPubById";
    }

    $.ajax({
        "url":url,
        "type":"POST",
        dataType:"json",
        data:{
            "id":node.id
        },
        async:false,
        success:function(data){
            $("#modaltype").attr("value","update");
            if(node.typeId == '0'){
                $("#proname").val(data.name);
                $("#proecode").val(data.proecode);
                $("#proversion").val(data.proversion);
                $("#prodescribe").val(data.prodescribe);
            }else if(node.typeId == '1'){
                $("#promname").val(data.name);
                $("#promecode").val(data.proecode);
            }else if(node.typeId == '2'){
                $(":radio[name='typeId'][value='2']").attr("checked","checked");
                $("#pubname").val(data.name);
                $("#puburl").val(data.url);
                $("#pubport").val(data.pubport);
                $("#pubprotocal").val(data.pubprotocal);
                $("#pubmethod").val(data.method);
                $("#pubreqformat").val(data.reqformat);
                $("#pubrespformat").val(data.respformat);
                $("#pubdesc").val(data.pubdesc);
                $("#typeId_api").attr("style","display:block");
                $("#typeId_jar").attr("style","display:none");
            }else if(node.typeId == '4'){
                $(":radio[name='typeId'][value='4']").attr("checked","checked");
                $("#pubname").val(data.name);
                $("#className").val(data.className);
                $("#methodInClass").val(data.methodInClass);
                $("#typeId_api").attr("style","display:none");
                $("#typeId_jar").attr("style","display:block;margin-left: 17px;");
            }
        }
    });
}



function setParam(){
    var nodes = resource.treeObj.getSelectedNodes();
    paramifream_pubid = nodes[0].id;
    $('#panel-childPage').attr('src', "../pubIfream/paramIframe");
}

//获取当前时间
function getDate(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear();
    if(mydate.getMonth()<9){
        str += 0;
        str += (mydate.getMonth()+1);
    }else{
        str += (mydate.getMonth()+1);
    }
    str += mydate.getDate() + "/";
    return str;
}

//刷新数据,根据传入的table刷新对应table的数据
function reloadTableData(tableid,isCurrentPage){
    tableid.dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}
