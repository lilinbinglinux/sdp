$(document).ready(function () {
    envList();
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
        url: $webpath + "/bcm/v1/env/templateName?templateName=" + templateName,
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
    $("#envList li").hover(
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
            '               <input style="width: 100px" type="text" class="envKey" placeholder="请输入变量名"></input>' +
            '           </td>' +
            '           <td>' +
            '               <input style="width: 100px" type="text" class="envValue" placeholder="请输入变量值"></input>' +
            '           </td>' +
            '           <td>' +
            '               <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
            '               <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
            '           </td>' +
            '   </tr>';
        $(this).parent().parent().after(str);
        addItem;
        deleteItem();
    })
}

//环境变量列表
function envList(templateNameSearch) {
    var templateName = '';
    if (templateNameSearch != undefined) {
        templateName = templateNameSearch;
    }
    $.ajax({
        url: $webpath + "/bcm/v1/env?templateName=" + templateName,
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                var str = '';
                $(data.data).each(function (index, item) {
                    str += '    <li class="card-child" data-id=' + item.id + '>' +
                        '          <div class="env card-child-inner">' +
                        '               <div class="item-top">' +
                        '                   <span>' + item.templateName + '</span>' +
                        '                   <span class="update-icon" style="display: none" data-id="' + item.id + '">' +
                        '                       <i class="deleteEnv delete" aria-hidden="true" style="margin-left: 15px;" title="删除"></i>' +
                        '                       <i class="updateEnv update" aria-hidden="true" title="编辑"></i>' +
                        '                   </span>' +
                        '               </div>' +
                        '               <div class="item-bottom">' +
                        '                   <span class="public-tag">' + item.createdBy + '</span>' +
                        '                   <span class="public-file-size">' + item.createTime + '</span>' +
                        '               </div>' +
                        '           </div>' +
                        '       </li>';
                })
                $("#envList").html(str);
                setUpdateIcon();
                updateEnv();
                deleteEnv();
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

//env创建/修改
function addEnvSave() {
    var envId = $("input[name='envId']").val();
    var templateName = $("input[name='templateName']").val();
    //校验
    //模板名称
    if (templateName == '') {
        $.message({
            message: '模板名称不能为空！',
            type: 'error'
        });
        return;
    }
    //模板内容
    var envData = {};
    var flag = true;
    $("#envTable tbody tr").each(function (index, item) {
        var envKey = $(item).find(".envKey").val();
        var envValue = $(item).find(".envValue").val();
        console.log("envKey:" + envKey)
        console.log("envValue:" + envValue)
        if (envKey == '' && envValue == '') {
            return true;
        }
        //1、环境变量Key只能是字母数字下划线；
        reg = /^[A-Za-z_][A-Za-z0-9_]*$/;
        if (!reg.test(envKey)) {
            flag = false;
            $.message({
                message: '环境变量key只能是字母数字下划线，不能以数字开头',
                type: 'error'
            });
            return;
        }
        //2、判断envKey长度
        if (envKey.length >= 4096) {
            flag = false;
            $.message({
                message: 'key字符长度不能超过4096',
                type: 'error'
            });
            return;
        }
        //3、访问路径不能重复
        if (envKey in envData) {
            flag = false;
            $.message({
                message: '环境变量Key不能重复',
                type: 'error'
            });
            return;
        }
        //1、判断envValue长度
        if (envValue.length >= 4096) {
            flag = false;
            $.message({
                message: 'value字符长度不能超过4096',
                type: 'error'
            });
            return;
        }
        envData[envKey] = envValue;
    })
    console.log(envData)
    console.log(flag)
    if (flag) {
        if (envId == '') {
            if (templateNameIsExist(templateName)) {
                var data = {
                    "templateName": templateName,
                    "envData": JSON.stringify(envData)
                }
                $.ajax({
                    url: $webpath + "/bcm/v1/env",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function (data) {
                        if (data.code == '200') {
                            $('#envAddModal').modal('hide');
                            handleResetForm('#envAddForm');
                            envList();
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
                "envData": JSON.stringify(envData)
            }
            $.ajax({
                url: $webpath + "/bcm/v1/env/" + envId,
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (data) {
                    if (data.code == '200') {
                        $('#envAddModal').modal('hide');
                        handleResetForm('#envAddForm');
                        envList();
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

//env编辑
function updateEnv() {
    $(".updateEnv").click(function () {
        var envId = $(this).parent().data("id");
        $.ajax({
            url: $webpath + "/bcm/v1/env/" + envId,
            type: 'GET',
            contentType: "application/json",
            success: function (data) {
                console.log(data)
                if (data.code == '200') {
                    var data = data.data;
                    assignToEnvModal(data);
                    $('#envAddModal').modal('show');
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
//env删除
function deleteEnv() {
    $(".deleteEnv").click(function () {
        var envId = $(this).parent().data("id");
        $("input[name='deleteId']").val(envId);
        $('#deleteConfirmModal').modal('show');
    })
}

//删除确认
function deleteConfirm() {
    var envId = $("input[name='deleteId']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/env/" + envId,
        type: 'DELETE',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                $.message({
                    message: data.message,
                    type: 'success'
                });
                envList();
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
function assignToEnvModal(data) {
    var envData = JSON.parse(data.envData);
    // var envData = JSON.parse(data.envData.replace(/[']/g, '"'));
    $("input[name='envId']").val(data.id);
    $("input[name='templateName']").val(data.templateName);
    $("input[name='templateName']").attr("disabled", true);
    var str = '';
    var index = 0;
    $.each(envData, function (key, value) {
        if (index == 0) {
            str += '<tr>' +
                '       <td>' +
                '           <input style="width: 100px" type="text" class="envKey" placeholder="请输入变量名" value="' + key + '"></input>' +
                '       </td>' +
                '       <td>' +
                '           <input style="width: 100px" type="text" class="envValue" placeholder="请输入变量值" value="' + value + '"></input>' +
                '       </td>' +
                '       <td>' +
                '           <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
                '       </td>' +
                '    </tr>';
        } else {
            str += '<tr>' +
                '       <td>' +
                '           <input style="width: 100px" type="text" class="envKey" placeholder="请输入变量名" value="' + key + '"></input>' +
                '       </td>' +
                '       <td>' +
                '           <input style="width: 100px" type="text" class="envValue" placeholder="请输入变量值" value="' + value + '"></input>' +
                '       </td>' +
                '       <td>' +
                '           <span class="glyphicon glyphicon glyphicon-plus title-circle addItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
                '           <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true" style="margin-left: 10px;"></span>' +
                '       </td>' +
                '    </tr>';
        }
        index++;
        addItem;
        deleteItem();
    })
    $("#envTable tbody").html(str);
}

//刷新
$(".btn-refresh").click(function (e) {
    $('#svcCategorySearch').val('');
    envList();
});

//搜索
$(".glyphicon-search").click(function () {
    envList($("#templateNameSearch").val());
});

//搜索
$("#templateNameSearch").keydown(function (e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if (event.keyCode == "13") {
            envList($("#templateNameSearch").val());
        }
    }
});