var protableinfo,resource,paramifream_pubid,process_pubid;
var i = -1;
$(document).ready(function () {
    resource = {
        treeObj: null,
        setting: {
            data: {
                simpleData: {
                    enable: true,
                    idKey: 'id',
                    pIdKey: 'parentId'
                },
                key: {
                    name: 'name'
                }
            },
            edit: {
                enable: true,
                drag: {
                    prev: false,
                    next: false,
                    inner: false
                },
                showRemoveBtn: false,
                showRenameBtn: false,

            },
            callback: {
                onRightClick: function (e, treeId, treeNode) {
                    if (!treeNode) return;
                    resource.treeObj.selectNode(treeNode);
                    $(".bootstrapMenu").hide();
                    if (treeNode.typeId == "root") {
                        $("#blankContextMenu").css("left", e.clientX);
                        $("#blankContextMenu").css("top", e.clientY);
                        $("#blankContextMenu").show();
                    } else if (treeNode.typeId == "0") {
                        $("#folderContextMenu").css("left", e.clientX);
                        $("#folderContextMenu").css("top", e.clientY);
                        $("#folderContextMenu").show();
                    } else if (treeNode.typeId == "1") {
                        $("#linkContextMenu").css("left", e.clientX);
                        $("#linkContextMenu").css("top", e.clientY);
                        $("#linkContextMenu").show();
                    } else if (treeNode.typeId == "2") {
                        $("#link1ContextMenu").css("left", e.clientX);
                        $("#link1ContextMenu").css("top", e.clientY);
                        $("#link1ContextMenu").show();
                    } else if (treeNode.typeId == "3") {
                        $("#link1ContextMenu").css("left", e.clientX);
                        $("#link1ContextMenu").css("top", e.clientY);
                        $("#link1ContextMenu").show();
                    }
                    else if (treeNode.typeId == "4") {
                        $("#link1ContextMenu").css("left", e.clientX);
                        $("#link1ContextMenu").css("top", e.clientY);
                        $("#link1ContextMenu").show();
                    }
                },
                beforeDrag: function (treeId, treeNodes) {
                    if (treeNodes[0].typeId == "2" || treeNodes[0].typeId == "4") {
                        return true;
                    } else {
                        return false;
                    }
                },
                onDrop: function (event, treeId, treeNodes, targetNode, moveType) {
                    i++;
                    process_pubid = treeNodes[0].id;
                    var xyObj = getMousePos(event);

                    //计算拖拽到画布上矩形放的位置
                    var framex = document.getElementById("flowchart-rightContent").offsetLeft;
                    var x = xyObj.x - framex - 25;
                    var y = xyObj.y - 145;
                    document.getElementById("flowChart-childPage").contentWindow.treeNodeDrop(x, y, treeNodes[0].name, i);
                },
                onClick: function (e, treeId, treeNode) {
                    if (treeNode.typeId == "root") {
                        layer.open({
                            type: 2,
                            title: '',
                            area: ['620px', '440px'],
                            content: webapp + '/pubIfream/proIframe',
                            shade: 0.1,
                            success:function (layero, index) {
                                //隐藏掉添加按钮
                                var body = layer.getChildFrame('body', index);
                                body.find('#id-addproinfo').hide();
                            }
                        });
                    }
                    else if (treeNode.typeId == "0") {
                        layer.open({
                            type: 2,
                            title: '',
                            area: ['620px', '440px'],
                            content: webapp + '/pubIfream/promodelIframe',
                            shade: 0.1,
                            success:function (layero, index) {
                                //隐藏掉添加按钮
                                var body = layer.getChildFrame('body', index);
                                body.find('#id-addproinfo').hide();
                            }
                        });
                    }
                    else if (treeNode.typeId == "1") {
                        //显示所有接口详情
                    } else if (treeNode.typeId == "2") {
                        setParam();
                    }
                    else if (treeNode.typeId == "3") {
                        setParam();
                    }
                }
            }
        },
        initTree: function () {
            if (resource.treeObj != null) {
                resource.treeObj.destroy()
            }
            $.ajax({//初始化组织机构树
                "url": webpath + "/interfacePo/selectAll",
                "type": "POST",
                success: function (data) {
                    if (data != null && data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            data[i].icon = webpath + resourceTypeIcon[data[i].typeId];
                            if (data[i].typeId == "root") {
                                data[i].open = "true";
                            }
                        }
                        resource.treeObj = $.fn.zTree.init($("#proTree"), resource.setting, data);
                        //resource.treeObj.expandAll(true);
                    } else {
                        layer.msg('暂无数据', {time: 1000, icon: 5});
                    }
                }
            });
        }
    };
    resource.initTree();//初始化树
    $("body").bind({
        click:function(e){
            $(".bootstrapMenu").hide();
        }
    });
    //当没有flowchartid时，为新增
    if (flowChartId != null || flowChartId != "") {
        $("#flowChart-childPage").attr("src", "../process/index?id=" + flowChartId);
    } else {
        $("#flowChart-childPage").attr("src", "../flowChartIframe/index");
    }
    //绑定面板右键事件
    $("#pro-panel").find(".panel-body").bind({
        contextmenu: function (e) {
            var nodes = resource.treeObj.getSelectedNodes();
            if (nodes.length > 0) {
                resource.treeObj.cancelSelectedNode(nodes[0]);
            }
            $(".bootstrapMenu").hide();
            $("#blankContextMenu").css("left", e.clientX);
            $("#blankContextMenu").css("top", e.clientY);
            $("#blankContextMenu").show();
            return false;
        }
    });
    //修改事件绑定
    $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').focus()
    })
    $('#m1').on("click", function () {
        $("#modaltype").val("");
        $("#proname").val("");
        $("#proecode").val("");
        $("#proversion").val("");
        $("#prodescribe").val("");
        $('#proModal').modal();
    });
    $('#m2').on("click", function () {
        $("#modaltype").val("");
        $("#promname").val("");
        $("#promecode").val("");
        $('#proModelModal').modal();
    });
    //右键添加API事件
    $('#m3_1').bind("click", apiModal);
    $('#m3_2').bind("click", apiModal);
    $('#m3').bind("click", apiModal);

    //$("#addProBtn").bind("click",addPro);

    //$("#addProModelBtn").bind("click",addPro);
    $("#addPubBtn").bind("click",addPub);
    //删除事件绑定
    $(".deleteInterface").bind("click", deleteInterface);
    $(".updateInterface").bind("click", updateInterface);
    $("#setparam").bind("click", setParam);
    $("#id-prodiv").css("display", "block");

    //页面打开合并操作
    $("#flowchart-toggleBar").click(function () {
        if ($(this).attr("isOpen") == "1") {
            $(".changeopen").css("display", "none");
            $("#flowchart-leftContent").attr("style", "width:10%");
            $("#flowchart-rightContent").attr("style", "width:90%");
            $("#flowchart-toggleBar").attr("isOpen", "2");
        } else if ($(this).attr("isOpen") == "2") {
            $(".changeopen").css("display", "inline");
            $("#flowchart-leftContent").attr("style", "width:34%");
            $("#flowchart-rightContent").attr("style", "width:66%");
            $("#proTree").attr("style", "overflow: auto;height: 500px");
            $("#flowchart-toggleBar").attr("isOpen", "1");
        }
    });
    $("#addProBtn").unbind("click");
    $("#addProBtn").bind("click", addPro);

    $("#addProModelBtn").unbind("click");
    $("#addProModelBtn").bind("click", addPro);

    $("input[name=typeId]").click(function () {
        var radioval = $('input[name=typeId]:checked').val();
        if (radioval == 4) {
            $("#typeId_api").attr("style", "display:none");
            $("#typeId_jar").attr("style", "display:block;margin-left: 17px;");
        } else if (radioval == 2) {
            $("#typeId_api").attr("style", "display:block");
            $("#typeId_jar").attr("style", "display:none");
        }
    });

});
var resourceTypeIcon = {
    "0": "/resources/img/icon/16x16/floder1-org.png",
    "1": "/resources/img/icon/16x16/fun-red.png",
    "2": "/resources/img/icon/16x16/resorce.png",
    "4": "/resources/img/icon/16x16/resorce.png",
    "3": "/resources/img/icon/16x16/resorce.png",
    "root": "/resources/img/icon/16x16/folder-red.png",
    "root1": "/resources/img/icon/16x16/folder-red.png",
};
function apiModal(){
    $("#modaltype").val("");
    $("#pubname").val("");
    $("#puburl").val("");
    $("#pubport").val("");
    $("#pubprotocal").val("");
    $("#pubmethod").val("");
    $("#pubreqformat").val("");
    $("#pubrespformat").val("");
    $("#pubreqdatatype").val("");
    $("#pubreturndatatype").val("");
    $("#pubdesc").val("");
    $("#className").val("");
    $("#methodInClass").val("");
    $('#pruModal').modal();
}
//添加注册接口
function addPub(){
    var nodes = resource.treeObj.getSelectedNodes();

    console.log($("#modaltype").val());
    console.log(nodes);
    if($("#modaltype").val() == "update"){
        var file = $("#ImportPicInput").val();
        var fileName = getFileName(file);
        var filePath = "";
        var pubid = nodes[0].id;
        if(fileName != null&&fileName != ""){
            filePath = getDate()+pubid+"/";
        }
        console.log(filePath);
        uploadFile(pubid,filePath);
        $.ajax({
            "url":webpath+"/publisher/update",
            "type":"POST",
            dataType:"json",
            async:false,
            data:{
                pubid:nodes[0].id,
                name:$("#pubname").val(),
                url:$("#puburl").val(),
                pubport:$("#pubport").val(),
                pubprotocal:$("#pubprotocal").val(),
                method:$("#pubmethod").val(),
                reqformat:$("#pubreqformat").val(),
                respformat:$("#pubrespformat").val(),
                reqdatatype:$("#pubreqdatatype").val(),
                returndatatype:$("#pubreturndatatype").val(),
                pubdesc:$("#pubdesc").val(),
                className:$("#className").val(),
                methodInClass:$("#methodInClass").val(),
                filePath:filePath+fileName
            },
            success:function(data){
                $("#ImportPicInput").val("");
                resource.initTree();
            }
        })
    }else{
        var file = $("#ImportPicInput").val();
        var fileName = getFileName(file);
        var filePath = "";
        var pubid = getuuid();
        if(fileName != null&&fileName != ""){
            filePath = getDate()+pubid+"/";
        }
        console.log("filePath"+filePath)
        uploadFile(pubid,filePath);
        $.ajax({
            "url":webpath+"/publisher/insert",
            "type":"POST",
            dataType:"json",
            data:{
                pubid:pubid,
                name:$("#pubname").val(),
                url:$("#puburl").val(),
                pubport:$("#pubport").val(),
                pubprotocal:$("#pubprotocal").val(),
                method:$("#pubmethod").val(),
                reqformat:$("#pubreqformat").val(),
                respformat:$("#pubrespformat").val(),
                reqdatatype:$("#pubreqdatatype").val(),
                returndatatype:$("#pubreturndatatype").val(),
                pubdesc:$("#pubdesc").val(),
                parentId:nodes[0].id,
                typeId:$('input[name=typeId]:checked').val(),
                className:$("#className").val(),
                methodInClass:$("#methodInClass").val(),
                filePath:filePath+fileName
            },
            success:function(data){
                $("#ImportPicInput").val("");
                resource.initTree();
            }
        })
    }
}

function deleteInterface() {
    var nodes = resource.treeObj.getSelectedNodes();
    if (nodes.length == 0)return;
    var node = nodes[0];
    layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是', '否'] //按钮
    }, function (index, layero) {
        $.ajax({
            "url": webpath + "/interfacePo/delete",
            "type": "POST",
            data: {
                "id": node.id,
                "typeId": node.typeId,
                "tenant_id": node.tenant_id
            },
            success: function (data) {
            	console.log(data);
                if (data == "deletesuccess") {
                    layer.msg('删除成功！', {time: 1000, icon: 1});
                    layer.close(index);
                    resource.initTree();
                } else if (data == "deletefalse") {
                    layer.msg('删除失败，请先删除子节点！', {time: 1000, icon: 2});
                    layer.close(index);
                }else if(data == "flowchartdeletefalse"){
                	layer.msg('删除失败，编排服务中已存在！', {time: 1000, icon: 2});
                    layer.close(index);
                }
            }
        });
    })
}
//修改事件
function updateInterface() {
    var nodes = resource.treeObj.getSelectedNodes();
    if (nodes.length == 0)return;
    var node = nodes[0];
    var url;

    if (node.typeId == '0' || node.typeId == '1') {
        url = webpath + "/pro/getProById";
    } else if (node.typeId == '2' || node.typeId == '4') {
        url = webpath + "/publisher/getPubById";
    }

    $.ajax({
        "url": url,
        "type": "POST",
        dataType: "json",
        data: {
            "id": node.id
        },
        async: false,
        success: function (data) {
            $("#modaltype").attr("value", "update");
            console.log(node.typeId);
            if (node.typeId == '0') {
                $("#proname").val(data.name);
                $("#proecode").val(data.proecode);
                $("#proversion").val(data.proversion);
                $("#prodescribe").val(data.prodescribe);
            } else if (node.typeId == '1') {
                $("#promname").val(data.name);
                $("#promecode").val(data.proecode);
            } else if (node.typeId == '2') {
                $(":radio[name='typeId'][value='2']").attr("checked", "checked");
                $("#pubname").val(data.name);
                $("#puburl").val(data.url);
                $("#pubport").val(data.pubport);
                $("#pubprotocal").val(data.pubprotocal);
                $("#pubmethod").val(data.method);
                $("#pubreqformat").val(data.reqformat);
                $("#pubrespformat").val(data.respformat);
                $("#pubdesc").val(data.pubdesc);
                $("#typeId_api").attr("style", "display:block");
                $("#typeId_jar").attr("style", "display:none");
            } else if (node.typeId == '4') {
                $(":radio[name='typeId'][value='4']").attr("checked", "checked");
                $("#pubname").val(data.name);
                $("#className").val(data.className);
                $("#methodInClass").val(data.methodInClass);
                $("#typeId_api").attr("style", "display:none");
                $("#typeId_jar").attr("style", "display:block;margin-left: 17px;");
            }
        }
    });
}
//iframe子页面显示modal隐藏树结构
function closeTree() {
    $(".changeopen").css("display", "none");
    $("#flowchart-leftContent").attr("style", "width:10%");
    $("#flowchart-rightContent").attr("style", "width:90%");
    $(this).attr("isOpen", "false");
}
function setParam() {
    var nodes = resource.treeObj.getSelectedNodes();
    paramifream_pubid = nodes[0].id;
    layer.open({
        type: 2,
        title: '',
        area: ['620px', '440px'],
        content: webapp + '/pubIfream/paramIframe',
        shade: 0.1,
    });
}
//刷新数据,根据传入的table刷新对应table的数据
function reloadTableData(tableid, isCurrentPage) {
    $(tableid).dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}
//获取事件中鼠标点击的坐标
function getMousePos(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft
        || document.body.scrollLeft;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var x = e.pageX || e.clientX + scrollX;
    var y = e.pageY || e.clientY + scrollY;
    //console.log(e.pageX);
    return {
        'x': x,
        'y': y
    };
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    } else {
        return null;
    }
}
//添加修改项目及模块方法
function addPro() {
    if ($("#modaltype").val() == "update") {   //修改方法
        var nodes = resource.treeObj.getSelectedNodes();
        var id, name, proecode, proversion, prodescribe, modaltype;
        if (nodes.length > 0) {//在树上面操作
            id = nodes[0].id;
            if (nodes[0].typeId == "0") {   //修改项目
                name = $("#proname").val();
                proecode = $("#proecode").val();
                proversion = $("#proversion").val();
                prodescribe = $("#prodescribe").val();
            } else if (nodes[0].typeId == "1") {  //修改模块文件夹
                name = $("#promname").val();
                proecode = $("#promecode").val();
            }
        }
        $.ajax({
            "url": webpath + "/pro/update",
            "type": "POST",
            dataType: "json",
            async: false,
            data: {
                proid: id,
                name: name,
                proecode: proecode,
                proversion: proversion,
                prodescribe: prodescribe
            },
            success: function () {
                resource.initTree();
            }
        })
    } else { //添加方法
        var nodes = resource.treeObj.getSelectedNodes();
        if (nodes.length > 0) {//在树上面操作
            if (typeof(nodes[0]) == "undefined" || nodes[0].id == "ROOT") {  //添加项目
                $.ajax({
                    "url": webpath + "/pro/insert",
                    "type": "POST",
                    data: {
                        name: $("#proname").val(),
                        proecode: $("#proecode").val(),
                        proversion: $("#proversion").val(),
                        prodescribe: $("#prodescribe").val(),
                        parentId: "ROOT",
                        typeId: "0"
                    },
                    success: function () {
                        resource.initTree();
                    }
                })
            } else { //添加模块文件夹
                $.ajax({
                    "url": webpath + "/pro/insert",
                    "type": "POST",
                    dataType: "json",
                    data: {
                        name: $("#promname").val(),
                        proecode: $("#promecode").val(),
                        parentId: nodes[0].id,
                        typeId: "1"
                    },
                    success: function () {
                        resource.initTree();
                    }
                })
            }
        }
    }
}

//刷新数据,根据传入的table刷新对应table的数据
function reloadTableData(table) {
    $("#id-proinfotable").dataTable().fnDraw(true);
    $("#id-publishMineTable").dataTable().fnDraw(true);
}
function uploadFile(pubid,filePath){
    $.ajaxFileUpload({
        type: "POST",
        url: webpath+"/file/importPicFile.do?pubid="+pubid+"&filePath="+filePath,
        // data:{pubid:pubid},//要传到后台的参数，没有可以不写
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
//获取文件名称
function getFileName(o){
    var pos=o.lastIndexOf("\\");
    return o.substring(pos+1);
}
