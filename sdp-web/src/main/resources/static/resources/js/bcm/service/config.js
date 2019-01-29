$(document).ready(function () {
    configList();
    setUpdateIcon();
    deleteItem();
    addItem();
    //模板名称是否存在
    $('input[name="templateName"]').blur(function () {
        var templateName = $('input[name="templateName"]').val();
        if (templateName) {
            templateNameIsExist(templateName);
        }
    });
});

function templateNameIsExist(templateName) {
    var flag = true;
    $.ajax({
        url: $webpath + "/bcm/v1/config/templateName?templateName=" + templateName,
        type: 'GET',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.code == 200) {
                if (data.data != null) {
                    $.message({
                        message: '模板名称已存在！',
                        type: 'error'
                    });
                    flag = false;
                }
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })

    return flag;
}

//修改、删除图标显示，隐藏
function setUpdateIcon() {
    $("#configList li").hover(
        function () {
            $(this).find(".update-icon").show();
        },
        function () {
            $(this).find(".update-icon").hide();
        }
    );
}

//环境变量删除一行
function deleteItem() {
    $("body").on("click", ".deleteItem", function () {
        $(this).parents('tr').remove();
    })
}

//环境变量添加一行
function addItem() {
    $("body").on("click", ".addItem", function () {
        var str = '<tr>' +
            '           <td>' +
            '               <input type="text" class="fileName" style="width: 98%;height: 35px;">' +
            '           </td>' +
            '           <td>' +
            '               <textarea class="file" style="width: 98%;height:200px;margin-bottom: 8px;" wrap="off"></textarea>' +
            '           </td>' +
            '           <td>' +
            '               <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
            '               <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
            '           </td>' +
            '       </tr>';
        $(this).parent().parent().after(str);
        addItem;
        deleteItem();
    })
}

//配置文件列表
function configList(templateNameSearch) {
    var templateName = '';
    if (templateNameSearch != undefined) {
        templateName = templateNameSearch;
    }
    $.ajax({
        url: $webpath + "/bcm/v1/config?templateName=" + templateName,
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                var str = '';
                $(data.data).each(function (index, item) {
                    str += '<li class="card-child" data-id=' + item.id + '>' +
                        '       <div class="card-child-inner">' +
                        '           <div class="item-top">' +
                        '               <span>' + item.templateName + '</span>' +
                        '               <span class="update-icon" style="display: none" data-id="' + item.id + '">' +
                        '                  <i class="deleteConfig delete" aria-hidden="true" style="margin-left: 15px;" title="删除"></i>' +
                        '                  <i class="updateConfig update" aria-hidden="true" title="编辑"></i>' +
                        '               </span>' +
                        '           </div>' +
                        '           <div class="item-bottom">' +
                        '               <span class="public-tag">' +
                        '               <span class="serviceNum" data-toggle="modal" data-target="#serviceModal" onclick="showService(' + item.id + ')">0</span>' +
                        '               </span>' +
                        '               <span class="public-file-size">' + item.createdBy + '</span>' +
                        '               <span class="time-label">' + item.createTime + '</span>' +
                        '           </div>' +
                        '        </div>' +
                        '   </li>';
                })
                $("#configList").html(str);
                setUpdateIcon();
                updateConfig();
                deleteConfig();
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })
}

//config创建/修改
function addConfigSave() {
    var configId = $("input[name='configId']").val();
    var templateName = $("input[name='templateName']").val();
    //校验
    //模板名称校验
    var nameReg = /^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$/;
    if (templateName == null || templateName == "") {
        $.message({
            message: '模板名称不能为空！',
            type: 'error'
        });
        return;
    } else if (templateName.length > 20) {
        $.message({
            message: '模板名称字符长度不能超过20',
            type: 'error'
        });
        return;
    } else if (templateName.match(nameReg) == null) {
        $.message({
            message: '模版名称只能包含:数字"0-9",小写字母"a-z","-","."等字符, 且必须以字母或数字为开头和结尾!',
            type: 'error'
        });
        return;
    }
    //模板内容
    var configData = {};
    var flag = true;
    $("#env-variate tbody tr").each(function (index, item) {
        var fileName = $(item).find(".fileName").val();
        var file = $(item).find(".file").val();
        console.log("fileName:" + fileName)
        console.log("file:" + file)
        if (fileName == '' && file == '') {
            return true;
        }
        //1、文件名称正则校验；
        var keyReg = /^[-._a-zA-Z0-9]+$/;
        if (!keyReg.test(fileName)) {
            flag = false;
            $.message({
                message: '文件名称只能包含:数字"0-9",字母"a-z, A-Z","-",".", "_"等字符!',
                type: 'error'
            });
            return;
        }
        //2、文件名称不能重复
        if (fileName in configData) {
            flag = false;
            $.message({
                message: '文件名称不能重复',
                type: 'error'
            });
            return;
        }
        //1、文件内容不能为空
        if (fileName == '') {
            flag = false;
            $.message({
                message: '文件内容不能为空！',
                type: 'error'
            });
            return;
        }
        configData[fileName] = file;
    })
    console.log(configData)
    console.log(flag)
    if (flag) {
        if (configId == '') {
            if (templateNameIsExist(templateName)) {
                var data = {
                    "templateName": templateName,
                    "configData": JSON.stringify(configData)
                }
                $.ajax({
                    url: $webpath + "/bcm/v1/config",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function (data) {
                        if (data.code == '200') {
                            $('#configAddModal').modal('hide');
                            handleResetForm('#configAddForm');
                            configList();
                        } else {
                            if (undefined != data.message && '' != data.message) {
                                $.message({
                                    message: data.message,
                                    type: 'error'
                                });
                            }
                        }
                    }
                })
            }
        } else {
            var data = {
                "configData": JSON.stringify(configData)
            }
            $.ajax({
                url: $webpath + "/bcm/v1/config/" + configId,
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (data) {
                    if (data.code == '200') {
                        $('#configAddModal').modal('hide');
                        handleResetForm('#configAddForm');
                        configList();
                    } else {
                        if (undefined != data.message && '' != data.message) {
                            $.message({
                                message: data.message,
                                type: 'error'
                            });
                        }
                    }
                }
            })
        }

    }
}

//config编辑
function updateConfig() {
    $(".updateConfig").click(function () {
        var configId = $(this).parent().data("id");
        $.ajax({
            url: $webpath + "/bcm/v1/config/" + configId,
            type: 'GET',
            contentType: "application/json",
            success: function (data) {
                console.log(data)
                if (data.code == '200') {
                    var data = data.data;
                    assignToConfigModal(data);
                    $('#configAddModal').modal('show');
                } else {
                    if (undefined != data.message && '' != data.message) {
                        $.message({
                            message: data.message,
                            type: 'error'
                        });
                    }
                }
            },
            error: function () {
                $.message({
                    message: 'error',
                    type: 'error'
                });
            }
        })
    })
}
//config删除
function deleteConfig() {
    $(".deleteConfig").click(function () {
        var configId = $(this).parent().data("id");
        $("input[name='deleteId']").val(configId);
        $('#deleteConfirmModal').modal('show');

    })
}

//删除确认
function deleteConfirm() {
    var configId = $("input[name='deleteId']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/config/" + configId,
        type: 'DELETE',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                $.message({
                    message: data.message,
                    type: 'success'
                });
                configList();
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })
    $('#deleteConfirmModal').modal('hide');
}

//修改环境变量模态框赋值
function assignToConfigModal(data) {
    console.log(data.configData);
    var configData = JSON.parse(data.configData);
    // var configData = JSON.parse(data.configData.replace(/[']/g, '"'));
    console.log(configData);
    $("input[name='configId']").val(data.id);
    $("input[name='templateName']").val(data.templateName);
    $("input[name='templateName']").attr("disabled", true);
    var str = '';
    var index = 0;
    $.each(configData, function (key, value) {
        if (index == 0) {
            str += '<tr>' +
                '       <td>' +
                '           <input type="text" class="fileName" style="width: 98%;height: 35px;" value="' + key + '">' +
                '       </td>' +
                '       <td>' +
                '           <textarea class="file" style="width: 98%;height:200px;margin-bottom: 8px;" wrap="off">' + value + '</textarea>' +
                '       </td>' +
                '       <td>' +
                '           <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
                '       </td>' +
                '   </tr>';
        } else {
            str += '<tr>' +
                '       <td>' +
                '           <input type="text" class="fileName" style="width: 98%;height: 35px;" value="' + key + '">' +
                '       </td>' +
                '       <td>' +
                '           <textarea class="file" style="width: 98%;height:200px;margin-bottom: 8px;" wrap="off">' + value + '</textarea>' +
                '       </td>' +
                '       <td>' +
                '           <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
                '       </td>' +
                '   </tr>';
        }
        index++;
        addItem;
        deleteItem();
    })
    $("#env-variate tbody").html(str);
}

//刷新
$(".btn-refresh").click(function (e) {
    $('#svcCategorySearch').val('');
    configList();
});

//搜索
$(".glyphicon-search").click(function () {
    configList($("#templateNameSearch").val());
});

//搜索
$("#templateNameSearch").keydown(function (e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if (event.keyCode == "13") {
            configList($("#templateNameSearch").val());
        }
    }
});