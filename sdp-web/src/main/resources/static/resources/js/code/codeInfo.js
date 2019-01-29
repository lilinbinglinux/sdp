$(document).ready(function(){
    formValidator('#addCodeSetForm');
    formValidator('#addCodeItemForm');
    formValidator('#addCodeItemForItemForm');
    formValidator('#editCodeItemForm');
    formValidator('#editCodeSetForm');
    /*
        初始化树
    */
    codeinfo.initTree();
});

$(".nav-collapse-left").click(function (e) {
    $(".codeTable-container").addClass("left-to-0");
});
$(".nav-collapse-right").click(function (e) {
    $(".codeTable-container").removeClass("left-to-0");
});

/*
* 验证表单输入
* */
function formValidator(id) {
    $(id).bootstrapValidator({
        live: 'enabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*
            * 字典基本信息校验
            * */
            setId: {
                validators: {
                    notEmpty: {
                        message: '请输入字典类型ID'
                    },
                    stringLength: {
                        min: 2,
                        max: 32,
                        message: 'ID长度不能小于2位或超过32位'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9]+$/,
                        message: '内容可以由任意数字、字母组成'
                    }
                }
            },
            itemId: {
                validators: {
                    notEmpty: {
                        message: '请输入字典ID'
                    },
                    stringLength: {
                        min: 2,
                        max: 32,
                        message: 'ID长度不能小于6位或超过32位'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9]+$/,
                        message: '内容可以由任意数字、字母组成'
                    }
                }
            },
            setName: {
                validators: {
                    notEmpty: {
                        message: '请输入字典类型'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合命名，且不以下划线开头'
                    }
                }
            },
            itemName: {
                validators: {
                    notEmpty: {
                        message: '请输入字典名称'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合命名，且不以下划线开头'
                    }
                }
            },
            itemCode: {
                validators: {
                    notEmpty: {
                        message: '请输入字典值'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合命名，且不以下划线开头'
                    }
                }
            },
            itemResume: {
                validators: {
                    notEmpty: {
                        message: '请输入字典说明'
                    }
                }
            },
            typePath: {
                validators: {
                    notEmpty: {
                        message: '请输入访问路径'
                    },
                    regexp: {
                        regexp: /^(\/)[a-zA-Z0-9/]+$/,
                        message: '以左划线开头，内容可以由任意数字、字母组成'
                    }
                }
            },
            sortId: {
                validators: {
                    notEmpty: {
                        message: '请输入序号'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '请输入分类排序数字'
                    }
                }
            },
            typeStatus: {
                validators: {
                    notEmpty: {
                        message: '请选择状态'
                    }
                }
            }
        }
    });
}


/*获取要新增的codeItem的setId,parentId*/
var newSetId;
var parentSetId;
var parentSetName;
function getSetId(id, fid, name) {
    newSetId = id;
    parentSetId = fid;
    parentSetName = name;
    /*根节点不允许在右侧新建字典*/
    if(id !== 0) {
        $('#addCodeItemForm input[name="parentId"]').attr("value", parentSetName);
        $('#addCodeItemForm input[name="parentId"]').attr("readOnly", "readOnly");
        document.getElementById("createItem").disabled = false;
    }
}
/*获取要新增的子节点codeItem的itemId,parentId*/
var newItemId;
var parentItemId;
var parentItemTypePath;
var parentItemName;
function getItemId(id, fid, path, name) {
    newItemId = id;
    parentItemId = fid;
    parentItemTypePath = path;
    parentItemName = name;
    $('#addCodeItemForItemForm input[name="parentId"]').attr("value", parentItemName);
    $('#addCodeItemForItemForm input[name="parentId"]').attr("readOnly", "readOnly");
}

/*
* 插入单条codeSet
* */
function saveAddCodeSet() {
    $('#addCodeSetForm').data('bootstrapValidator').validate();
    if ($('#addCodeSetForm').data('bootstrapValidator').isValid()) {
        var setData = formatFormData('#addCodeSetForm');

        if (checkSetId(setData.setId).length > 0) {
            $.message({
                message: '字典类型ID已存在，请重新填写',
                type: 'error'
            })
        } else {
            setData.parentId = newSetId;
            /*allSetTypes 匹配父节点的 typePath */
            var parentSet = allSetTypes.filter(checkParentSet);

            function checkParentSet(set) {
                return set.code_set_id === newSetId;
            }

            if (parentSet.length > 0) {
                setData.typePath = parentSet[0].type_path + '/' + setData.setId;
            } else {// 根节点上执行添加节点
                setData.typePath = '/' + setData.setId;
            }
            $.ajax({
                url: webpath + "/codeInfo/insertCodeSet",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(setData),
                success: function (result) {
                    if (result.code === 0) {
                        $.message(result.message);
                        $('#addCodeSet').modal('hide');
                        handleResetForm('#addCodeSetForm');
                        /*更新 allSetTypes, 字典不完整，不影响修改类型和创建字典*/
                        allSetTypes.push({"code_set_id": setData.setId, "type_path": setData.typePath, "code_set_name": setData.setName,
                            "type_status": setData.typeStatus,"sort_id": setData.sortId, "del_flag": "0"});
                        // console.log(allSetTypes);
                        /*将新建的类型添加到树结构中*/
                        var treeObj = $.fn.zTree.getZTreeObj("codeTree");
                        var Nodes = treeObj.getNodesByParam("id", newSetId, null);
                        var newNode = {
                            id: setData.setId,
                            name: setData.setName,
                            pId: newSetId,
                            type: "productType"
                        };
                        treeObj.addNodes(Nodes[0], newNode);

                    } else {
                        $.message({
                            message: result.message,
                            type: 'error'
                        })
                    }
                }
            });
        }
    }
}

/*
* 验证codeSet是否已存在
* */
function checkSetId(id) {
    var checkSet = allSetTypes.filter(checkSetId);
    function checkSetId(set) {
        return set.code_set_id === id;
    }
    return checkSet;
}

/*
* 插入单条codeItem
* */
function saveAddCodeItem() {
    $('#addCodeItemForm').data('bootstrapValidator').validate();
    if ($('#addCodeItemForm').data('bootstrapValidator').isValid()) {
        var itemData = formatFormData('#addCodeItemForm');
        if(checkSetId(itemData.itemId).length > 0){
            /*字典ID如果和类型ID重复，暂按照会引起混乱*/
            $.message({
                message: '该字典ID已存在，请重新填写',
                type: 'error'
            })
        }else {
            /*itemData.itemId 调用查询接口，如果有返回值则设置表单 字典ID 警告 */
            var checkItem;
            $.ajax({
                url: webpath + "/codeInfo/getSingleCodeItem",
                type: "GET",
                async: false,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: {
                    "itemId": itemData.itemId
                },
                success: function (result) {
                    if(result.code === 0){
                        checkItem = result.data;
                    }
                }
            });

            if (checkItem) {
                $.message({
                    message: '字典数据ID已存在，请重新填写',
                    type: 'error'
                })
            } else {
                itemData.parentId = newSetId;
                itemData.typePath = '/' + itemData.itemId;
                itemData.setId = newSetId;
                $.ajax({
                    url: webpath + "/codeInfo/insertCodeItem",
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(itemData),
                    success: function (result) {
                        if (result.code === 0) {
                            $.message(result.message);
                            $('#addCodeItem').modal('hide');
                            handleResetForm('#addCodeItemForm');
                            $("#initCodeTable").bootstrapTable('refresh');
                        } else {
                            $.message({
                                message: result.message,
                                type: 'error'
                            })
                        }
                    }
                });
            }
        }
    }

}

/*
* 插入子codeItem
* */
function saveAddCodeItemForItem() {
    $('#addCodeItemForItemForm').data('bootstrapValidator').validate();
    if ($('#addCodeItemForItemForm').data('bootstrapValidator').isValid()) {
        var itemData = formatFormData('#addCodeItemForItemForm');
        /*itemData.itemId 调用查询接口，如果有返回值则设置表单 字典ID 警告 */
        var checkItem;
        $.ajax({
            url: webpath+"/codeInfo/getSingleCodeItem",
            type: "GET",
            async: false,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: {
                "itemId": itemData.itemId
            },
            success: function (result) {
                if (result.code === 0){
                    checkItem = result.data;
                }
            }
        });

        if(checkItem) {
            $.message({
                message: '字典数据ID已存在，请重新填写',
                type: 'error'
            })
        } else {
            itemData.parentId = newItemId;
            itemData.typePath = parentItemTypePath + '/' + itemData.itemId;
            itemData.setId = newSetId;
            $.ajax({
                url: webpath + "/codeInfo/insertCodeItem",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(itemData),
                success: function (result) {
                    if (result.code === 0) {
                        $.message(result.message);
                        $('#addCodeItemForItem').modal('hide');
                        handleResetForm('#addCodeItemForItemForm');
                        $("#initCodeTable").bootstrapTable('refresh');
                    } else {
                        $.message({
                            message: result.message,
                            type: 'error'
                        })
                    }
                }
            });
        }
    }

}

/*不允许删除根节点*/
function setRemoveBtn(treeId, treeNode){
    return treeNode.level !== 0;
}

/*删除树节点之前*/
function zTreeBeforeRemove(treeId, treeNode) {
    if(!treeNode.id){
        alert("没有选中节点，请尝试刷新");
        return false;
    }
    return true;
}

/*禁止根节点单击*/
function zTreeBeforeClick(treeId, treeNode, clickFlag) {
    return (treeNode.id !== 0);
}

/*确认删除节点之后*/
function zTreeOnRemove(event, treeId, treeNode) {
    $('#deleteSetConfirmModal').modal("show");
    $('#deleteSetConfirmModal .confirm-box-ok').on('click', function () {
        recursionDeleteSet(treeNode);
        /*删除一个字典类型，右侧表如果是当前类型的字典，则没有更新，所有修改表格refresh为destroy*/
        $('#initCodeTable').bootstrapTable('destroy');
    });
    $('#deleteSetConfirmModal .confirm-box-cancel').on('click', function () {
        // addTreeNode(treeNode.id, treeNode.pId, treeNode.name, treeNode.type);
        codeinfo.initTree();
    });
}

/*确认删除节点之后*/
// function zTreeOnRemove(event, treeId, treeNode) {
//     layer.confirm('删除该字典类型？（删除后不可恢复，包括该类型下的所有字典数据）', {
//             btn: ['是','否']
//         }, function(index){
//             recursionDeleteSet(treeNode);
//             /*删除一个字典类型，右侧表如果是当前类型的字典，则没有更新，所有修改表格refresh为destroy*/
//             $('#initCodeTable').bootstrapTable('destroy');
//         }, function (index) {
//             // addTreeNode(treeNode.id, treeNode.pId, treeNode.name, treeNode.type);
//             codeinfo.initTree();
//         }
//     );
// }

/*删除一个父类型，需要将所有子节点的类型数据递归删除，同时删除每个类型下的字典数据*/
function recursionDeleteSet(treeNode) {
    if (treeNode.children) {
        $.each(treeNode.children, function (i, v) {
            recursionDeleteSet(treeNode.children[i]);
        });
        deleteSetId(treeNode.id);
    } else {
        deleteSetId(treeNode.id);
    }
}

/*根据类型ID删除codeSet*/
function deleteSetId(id) {
    $.ajax({
        url: webpath + "/codeInfo/deleteCodeSetById",
        type: "DELETE",
        contentType: "application/json; charset=utf-8",
        async: false,
        data: JSON.stringify({"setId":id, "delFlag": "1"}),
        success: function (result) {
            if (result.code === 0) {
                $.message(result.message);
                deleteTreeNodeById(id);
                /*删除codeSet, 更新allSetTypes */
                var deleteSet = allSetTypes.filter(checkSetId);
                if (deleteSet.length > 0) {
                    var index = allSetTypes.indexOf(deleteSet[0]);
                    if (index > -1) {
                        allSetTypes.splice(index, 1);
                    }
                }
                /* 删除所有setId === id 的codeItem;类型下面的字典数据可不做删除标记处理*/
                // $.ajax({
                //     url:webpath+"/codeInfo/deleteCodeItemBySetId",
                //     type:"DELETE",
                //     contentType: "application/json; charset=utf-8",
                //     data: id,
                //     success:function(result){
                //         if(result.code === 0) {
                //             deleteTreeNodeById(id);
                //             $.message(result.message);
                //         }else {
                //             $.message({
                //                 message: result.message,
                //                 type: 'error'
                //             })
                //         }
                //     }
                // });
            } else {
                $.message({
                    message: result.message,
                    type: 'error'
                })
            }
        }
    });
    $('#deleteSetConfirmModal').modal("hide");
}

/*移除增加节点*/
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
    $("#editBtn_"+treeNode.tId).unbind().remove();
}
/*显示增加节点*/
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='新增' onfocus='this.blur();' data-toggle=\"modal\" data-target=\"#addCodeSet\"></span>"
        + "<span class='button edit' id='editBtn_" + treeNode.tId
        + "' title='修改' onfocus='this.blur();' data-toggle=\"modal\" data-target=\"#codeSetEditModal\"></span>";
    sObj.after(addStr);
    /*
    * 执行插入节点，不需要绑定事件
    * */
    var addBtn = $("#addBtn_" + treeNode.tId);
    if (addBtn) addBtn.bind("click", function () {
        // getSetName(treeNode.name);
        $('#addCodeSetForm input[name="parentId"]').attr("value", treeNode.name);
        $('#addCodeSetForm input[name="parentId"]').attr("readOnly", "readOnly");
        /*判断根节点和普通节点，根节点不需要右侧新建字典*/
        if(treeNode.id !== 0) {
            getSetId(treeNode.id, treeNode.parent_id, treeNode.name);
        }else {
            getSetId(0, 0, "根节点");
        }
    });
    /*修改字典类型*/
    var editBtn = $("#editBtn_" + treeNode.tId);
    if(editBtn) editBtn.bind("click", function () {
        for (var i=0; i<allSetTypes.length; i++) {
            if(allSetTypes[i].code_set_id === treeNode.id) {
                $('#editCodeSetForm input[name="setName"]').attr("value", allSetTypes[i].code_set_name);
                $("#editCodeSetForm :radio[name='typeStatus'][value='" + allSetTypes[i].type_status + "']").attr("checked", true);
                $('#editCodeSetForm input[name="sortId"]').attr("value", allSetTypes[i].sort_id);
                $('#codeSetEditModal .bconsole-modal-footer .modal-submit').unbind('click').click(function () {
                    saveCodeSetChange(treeNode.id);
                });
            }
        }
    })
}

/*
* 获取所有字典类型
* */
var allSetTypes = [];

var codeinfo = {
    setting: {
        treeId: "",
        treeObj: null,

        edit: {
            enable: true,
            editNameSelectAll: true,
            removeTitle: "删除",
            showRemoveBtn: setRemoveBtn,
            showRenameBtn: false
        },

        view: {
            selectedMulti: false,
            dblClickExpand: false,
            /*
            * 增加一个添加节点的控件
            * */
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom

        },

        check: {
            enable: true
        },

        data: {
            key: {
                name: "name"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey : "pId",
                rootPId: 0
            }
        },

        callback: {
            beforeClick: zTreeBeforeClick,
            onClick: function(event, treeId, treeNode) {
                /*设置 新建 按钮生效*/
                getSetId(treeNode.id, treeNode.pId, treeNode.name);
                initSetTable(treeNode.id, treeNode.name);
            },
            beforeRemove: zTreeBeforeRemove,
            onRemove: zTreeOnRemove
        }
    },

    initTree: function() {
        // 设置新建按钮失效
        document.getElementById("createItem").disabled = true;

        var zNodes = [];
        /* 设置一个根节点 */
        zNodes.push({'id':0,'name':'根节点','pId':0,type:"productType"});
        if(codeinfo.treeObj!=null) {
            codeinfo.treeObj.destroy();
        }

        $.ajax({
            url: webpath+"/codeInfo/getCodeSet",
            async: false,
            type: "GET",
            contentType:"application/json;charset=utf-8",
            dataType: "json",
            success: function(result){
                if(result.code === 0) {
                    /*给全局变量付值，记录当前所有codeSet*/
                    allSetTypes = result.data;
                    for (var i=0; i<result.data.length; i++){
                        if(result.data[i].del_flag === "0") {
                            zNodes.push(
                                {
                                    "id": result.data[i].code_set_id,
                                    "name": result.data[i].code_set_name,
                                    "pId": result.data[i].parent_id,
                                    "type": "productType"
                                }
                            )
                        }
                    }
                } else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    })
                }
            }

        });

        codeinfo.treeObj = $.fn.zTree.init($("#codeTree"), codeinfo.setting, zNodes);
        /*默认展开ztree；显示根节点下第一级数据，即唯一标识tId=codeTree_2 */
        codeinfo.treeObj.expandAll(true);
        initSetTable(codeinfo.treeObj.getNodeByTId("codeTree_2").id,codeinfo.treeObj.getNodeByTId("codeTree_2").name);
    }
};

/*表格展开详情*/
function detailFormatter(index, row) {
    // var parentItemName = row.code_item_name;
    var html = [];
    html.push('<div class="table-responsive" style="margin-top: 10px"><table class="table table-hover" id="tableItem'+row.code_item_id+'"></table></div>');
    var loading = layer.load(2, {time: 10*1000});

    /*
    * 点击展开内嵌表格
    * */
    $('#initCodeTable').on('expand-row.bs.table', function (e, index, row, $detail) {
        var parentName = row.code_item_name;
        var tableId = 'tableItem' + row.code_item_id;
        $("#" + tableId).bootstrapTable('destroy');
        $("#" + tableId).bootstrapTable({
            url: webpath + '/codeInfo/initDetailTable',
            method: 'GET',
            contentType: "application/json;charset-utf-8",
            dataType: "json",
            classes: 'table table-hover table-no-bordered',
            detailView: true,
            detailFormatter: "detailFormatter",
            formatNoMatches: function () {
                return '暂无下一级字典';
            },
            formatLoadingMessage: function () {
                return '正在加载下一级字典，请耐心等待......';
            },
            queryParams: function (params) {
                return {"parent_id": row.code_item_id};
            },
            responseHandler: function (res) {
                return res;
            },
            columns: [{
                field: 'index',
                title: '序号',
                align: 'center',
                valign: 'left',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
                {
                    field: 'parent_id',
                    title: '父级',
                    align: 'center',
                    valign: 'middle',
                    formatter:function(value,row,index) {
                        return parentName;
                    }
                }, {
                    field: 'code_item_name',
                    title: '字典名称',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'create_date',
                    title: '创建时间',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return new Date(row.create_date).toLocaleDateString();
                    }
                }, {
                    field: 'type_status',
                    title: '状态',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        if (row.type_status === '0') {
                            return '启动';
                        }
                        if (row.type_status === '1') {
                            return '停用';
                        }
                        return value;
                    }
                },{
                    field: 'operation',
                    title: '操作',
                    align: 'right',
                    formatter:function(value,row,index) {
                        var html =
                            // '<span class="glyphicon glyphicon-record icon-hover" title="启动" onclick="setStart(\'' + row.code_set_id + '\', \'' + row.parent_id + '\')"></span>\n'
                            // + '<span class="glyphicon glyphicon-ban-circle icon-hover" title="停用" onclick="setStop(\'' + row.code_set_id + '\', \'' + row.parent_id + '\')"></span>\n'
                            '<span class="glyphicon glyphicon-plus icon-hover" title="新增" data-toggle="modal" data-target="#addCodeItemForItem" onclick="getItemId(\'' + row.code_item_id + '\', \'' + row.parent_id + '\', \'' + row.type_path + '\', \'' + row.code_item_name + '\')"></span>\n'
                            + '<span class="glyphicon glyphicon-edit icon-hover" title="编辑" data-toggle="modal" data-target="#codeItemEditModal" onclick="updateCodeInfo(\'' + row.code_item_id + '\')"></span>\n'
                            + '<span class="glyphicon glyphicon-trash icon-hover" title="删除" onclick="deleteCodeItemById(\'' + row.code_item_id + '\', \'' + row.code_set_id + '\')"></span>';
                        return html;
                    }
                }
            ]
        });
    });
    layer.close(loading);
    return html.join('');
}

/*检查字典是否有下一级*/
function checkItemChildNodes(row) {
    $.ajax({
        url:webpath+'/codeInfo/initSetTable',
        method:'GET',
        contentType:"appliccation/json;charset-utf-8",
        dataType:"json",
        data:{
            "parent_id": row.code_item_id
        },
        success:function (result) {
            if(result.code === 0){
                if(result.data.length>0){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
    })
}

/*获取一个字典类型的字典数据，展开右侧表格*/
function initSetTable(id, name) {
    $("#initCodeTable").bootstrapTable('destroy');
    $("#initCodeTable").bootstrapTable({
        url: webpath+'/codeInfo/initSetTable',
        method: 'GET',
        contentType: "application/json;charset-utf-8",
        dataType: "json",
        classes: 'table-no-bordered',
        detailView: true,
        detailFormatter: "detailFormatter",
        formatNoMatches: function () {
            return '暂无字典数据';
        },
        formatLoadingMessage: function() {
            return '正在加载，请耐心等待......';
        },
        queryParams: function (params) {
            return {"parent_id": id};

        },
        responseHandler: function (res) {
            return res;
        },
        columns: [{
            field: 'index',
            title: '序号',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return index + 1;
            }
        },
            {
                field: 'parent_id',
                title: '字典类型',
                align: 'center',
                valign: 'middle',
                formatter:function(value,row,index) {
                    return name;
                }
            }, {
                field: 'code_item_name',
                title: '字典名称',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'create_date',
                title: '创建时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return new Date(row.create_date).toLocaleDateString();
                }
            }, {
                field: 'type_status',
                title: '状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (row.type_status === '0') {
                        return '启动';
                    }
                    if (row.type_status === '1') {
                        return '停用';
                    }
                    return value;
                }
            },{
                field: 'operation',
                title: '操作',
                align: 'right',
                formatter:function(value,row,index) {
                    var html =
                        '<span class="glyphicon glyphicon-plus icon-hover" title="新增" data-toggle="modal" data-target="#addCodeItemForItem" onclick="getItemId(\'' + row.code_item_id + '\', \'' + row.parent_id + '\', \'' + row.type_path + '\', \'' + row.code_item_name + '\')"></span>\n'
                        + '<span class="glyphicon glyphicon-edit icon-hover" title="编辑" data-toggle="modal" data-target="#codeItemEditModal" onclick="updateCodeInfo(\'' + row.code_item_id + '\')"></span>\n'
                        + '<span class="glyphicon glyphicon-trash icon-hover" title="删除" onclick="deleteCodeItemById(\'' + row.code_item_id + '\', \'' + row.code_set_id + '\')"></span>';
                    return html;
                }
            }
        ]
    });
}

/*
* 更新单条字典类型数据到表
* */
function saveCodeSetChange(id) {
    $('#editCodeSetForm').data('bootstrapValidator').validate();
    if ($('#editCodeSetForm').data('bootstrapValidator').isValid()) {
        var setData = formatFormData('#editCodeSetForm');
        setData.setId = id;
        $.ajax({
            url: webpath + "/codeInfo/updateCodeSet",
            type: "PUT",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(setData),
            success: function (result) {
                if (result.code === 0) {
                    $.message(result.message);
                    $('#codeSetEditModal').modal('hide');
                    handleResetForm('#editCodeSetForm');
                    /*更新 allSetTypes 中修改的字典类型*/
                    $.each(allSetTypes, function (i, v) {
                        if (allSetTypes[i].code_set_id === id){
                            allSetTypes[i].code_set_name = setData.setName;
                            allSetTypes[i].type_status = setData.typeStatus;
                            allSetTypes[i].sort_id = setData.sortId;
                        }
                    });
                    /*如果右侧表格展示当前类型的字典数据，修改后表格内容没有更新，所以修改表格reflesh为destroy*/
                    $("#initCodeTable").bootstrapTable('destroy');
                    /*更新树节点名字*/
                    var treeObj = $.fn.zTree.getZTreeObj("codeTree");
                    var nodes = treeObj.getNodesByParam("id", id, null);
                    if (nodes.length>0) {
                        nodes[0].name = setData.setName;
                        treeObj.updateNode(nodes[0]);
                    }
                } else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    })
                }
            }
        });
    }
}

/*
* 删除单条字典类型数据，及归属该类型的item, set
* */
// function deleteCodeSet(id, pid, name) {
//     layer.confirm('删除该字典类型？（删除后不可恢复，包括该类型下的所有字典数据）', {
//         btn: ['是', '否']
//     }, function (index) {
//         layer.close(index);
//         var treeObj = $.fn.zTree.getZTreeObj("codeTree");
//         var Nodes = treeObj.getNodesByParam("id", id, null);
//         if(Nodes.length > 0) {
//             recursionDeleteSet(Nodes[0]);
//             $('#initCodeTable').bootstrapTable('refresh');
//         }
//     });
// }

/*
* 删除一条表中的字典
* */
function deleteCodeItemById(itemId, setId){
    $('#deleteItemConfirmModal').modal("show");
    $('#deleteItemConfirmModal .confirm-box-ok').on('click', function () {
        $.ajax({
            url:webpath+"/codeInfo/deleteCodeItemById",
            type:"DELETE",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"itemId": itemId, "delFlag": "1"}),
            success:function(result){
                if(result.code === 0) {
                    $('#initCodeTable').bootstrapTable('refresh');
                    /*将该字典的所有子字典删除，typath已itemId开头*/
                    $.ajax({
                        url:webpath+"/codeInfo/deleteCodeItemByTypePath",
                        type:"DELETE",
                        contentType:"application/json;charset=utf-8",
                        data: JSON.stringify({"itemId": itemId, "delFlag": "1"}),
                        success:function (result1) {
                            if(result1.code === 0){
                                $.message(result.message);
                            }else {
                                $.message({
                                    message: result.message,
                                    type: 'error'
                                })
                            }
                        }
                    });
                }else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    })
                }
            }
        });
        $('#deleteItemConfirmModal').modal("hide");
    });
}

// function deleteCodeItemById(itemId, setId){
//     layer.confirm('删除该字典值？（删除后不可恢复）', {
//         btn: ['是','否']
//     }, function(index){
//         layer.close(index);
//         $.ajax({
//             url:webpath+"/codeInfo/deleteCodeItemById",
//             type:"DELETE",
//             contentType: "application/json; charset=utf-8",
//             data: JSON.stringify({"itemId": itemId, "delFlag": "1"}),
//             success:function(result){
//                 if(result.code === 0) {
//                     $('#initCodeTable').bootstrapTable('refresh');
//                     /*将该字典的所有子字典删除，typath已itemId开头*/
//                     $.ajax({
//                         url:webpath+"/codeInfo/deleteCodeItemByTypePath",
//                         type:"DELETE",
//                         contentType:"application/json;charset=utf-8",
//                         data: JSON.stringify({"itemId": itemId, "delFlag": "1"}),
//                         success:function (result1) {
//                             if(result1.code === 0){
//                                 $.message(result.message);
//                             }else {
//                                 $.message({
//                                     message: result.message,
//                                     type: 'error'
//                                 })
//                             }
//                         }
//                     });
//                 }else {
//                     $.message({
//                         message: result.message,
//                         type: 'error'
//                     })
//                 }
//             }
//         });
//     });
// }

/*删除字典数据的所有子级字典*/
// function deleteCodeItemByTypePath(itemId) {
//     $.ajax({
//         url:webpath+"/codeInfo/deleteCodeItemByTypePath",
//         type:"DELETE",
//         contentType:"application/json;charset=utf-8",
//         data: JSON.stringify({"itemId": itemId, "delFlag": "1"}),
//         success:function (result) {
//             if(result.code === 0){
//                 $.message(result.message);
//             }else {
//                 $.message({
//                     message: result.message,
//                     type: 'error'
//                 })
//             }
//         }
//     })
// }

/*
* 删除id树节点
* */
function deleteTreeNodeById(id) {
    var treeObj = $.fn.zTree.getZTreeObj("codeTree");
    var Nodes = treeObj.getNodesByParam("id", id, null);
    for (var i=0, l=Nodes.length; i < l; i++) {
        treeObj.removeNode(Nodes[i]);
    }
}

/*
* 获取单条字典数据
* */
function updateCodeInfo(id){
    $.ajax({
        url: webpath + "/codeInfo/getSingleCodeItem",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: {
            "itemId": id
        },
        success: function (result) {
            if(result.code === 0) {
                $('#editCodeItemForm input[name="itemName"]').attr("value", result.data.itemName);
                $('#editCodeItemForm input[name="itemCode"]').attr("value", result.data.itemCode);
                $('#editCodeItemForm input[name="itemResume"]').attr("value", result.data.itemResume);
                $("#editCodeItemForm :radio[name='typeStatus'][value='" + result.data.typeStatus + "']").attr("checked", true);
                $('#editCodeItemForm input[name="sortId"]').attr("value", result.data.sortId);

                $('#codeItemEditModal .bconsole-modal-footer .modal-submit').unbind('click').click(function () {
                    saveCodeItemChange(id);
                });
            }
        }
    })
}

/*
* 更新单条字典数据
* */
function saveCodeItemChange(itemId) {
    $('#editCodeItemForm').data('bootstrapValidator').validate();
    if ($('#editCodeItemForm').data('bootstrapValidator').isValid()) {
        var itemData = formatFormData('#editCodeItemForm');
        itemData.itemId = itemId;
        // itemData.setId = setId;
        // itemData.parentId = itemId;
        $.ajax({
            url: webpath + "/codeInfo/updateCodeItem",
            type: "PUT",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(itemData),
            success: function (result) {
                if (result.code === 0) {
                    $.message(result.message);
                    $('#codeItemEditModal').modal('hide');
                    handleResetForm('#editCodeItemForm');
                    $("#initCodeTable").bootstrapTable('refresh');
                    // updateTreeNode(itemId, itemData.itemName);
                } else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    })
                }
            }
        });
    }
}

