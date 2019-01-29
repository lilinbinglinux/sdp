var operations = {
    "delete": "删除",
    "stop": "停止",
    "start": "启动"
}

$(document).ready(function () {
    formValidator('#scaleForm');
    formValidator('#quotaForm');
    formValidator('#upgradeForm');
    getServiceTable();
    //升级方式
    $("input[name='rollingType']").change(function () {
        var rollingType = $("input[name='rollingType']:checked").val();
        if (rollingType == 'numberUpgrade') {
            $("input[name='percentage']").attr("disabled", false);
            $("input[name='stepLength']").attr("disabled", true);
        } else {
            $("input[name='percentage']").attr("disabled", true);
            $("input[name='stepLength']").attr("disabled", false);
        }
    })
});

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
        }
    });
}

function getServiceTable() {
    $("#serviceTable").bootstrapTable({
        url: $webpath + "/bcm/v1/service",
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        classes: 'table-no-bordered',
        cache: false,
        striped: false,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        queryParams: function (params) {
            return {
                "page": params.offset / params.limit,
                "size": params.limit,
                "serviceName": $("#serviceNameSearch").val()
            }
        },
        queryParamsType: 'limit',
        responseHandler: function (res) {
            var total = 0;
            var content = [];
            if (res.code == 200) {
                if (res.data.totalElements) {
                    total = res.data.totalElements;
                }
                if (res.data.content) {
                    content = res.data.content;
                }
            } else {
                if (undefined != res.message && '' != res.message) {
                    $.message({
                        message: res.message,
                        type: 'error'
                    });
                }
            }
            return {
                "total": total,
                "rows": content
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        uniqueId: "",
        columns: [{
            field: 'serviceName',
            title: '服务名称',
            // align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var html = '<a class="inner-ahref" href="' + $webpath + '/bcm/v1/service/serviceDetailPage?serviceId=' + row.id + '">' + row.serviceName + '</a>'
                return html;
            }
        }, {
            // field: 'status',
            title: '服务状态',
            align: 'center',
            valign: 'middle',
            class: 'status',
            formatter: function (value, row, index) {
                return getServiceState(row.status);
            }
        }, {
            field: 'instance',
            title: '实例数目',
            align: 'center',
            valign: 'middle'
        }, {
            title: '实例状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var html = '<span class="running-font">运行 ' + row.runningPodNum + '</span>' +
                    '       <span class="stopped-font">停止 ' + row.stoppedPodNum + '</span>';
                return html;
            }
        }, {
            title: '资源配额',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var html = '<span style="margin-right:5px;">CPU ' + row.cpu + 'C</span>' +
                    '       <span>内存 ' + parseFloat((row.memory / 1024).toFixed(2)) + 'G</span>';
                return html;
            }
        }, {
            field: 'registryImageName',
            title: '镜像',
            align: 'center',
            valign: 'middle'
        }, {
            field: '',
            title: '操作',
            align: 'right',
            formatter: function (value, row, index) {
                var serviceId = row.id;
                var status = row.status;
                var html = '';
                if (status == 1 || status == 4 || status == 6) {
                    //启动(未启动、已停止、启动失败)
                    html += '<button type="button" class="btn btn-default btn-xs start" onclick="operate(\'' + serviceId + '\',\'' + "start" + '\',this)">启动</button>\n';
                } else {
                    html += '<button type="button" class="btn btn-default btn-xs start" title="只有服务状态为未启动、已停止、启动失败时，才能进行该操作" disabled=true>启动</button>\n';
                }
                if (status == 3) {
                    //停止(运行中)
                    html += '<button type="button" class="btn btn-default btn-xs" onclick="operate(\'' + serviceId + '\',\'' + "stop" + '\')">停止</button>\n';
                } else {
                    html += '<button type="button" class="btn btn-default btn-xs" title="只有服务状态为运行中时，才能进行该操作" disabled=true>停止</button>\n';
                }
                if (status == 3 && null == row.hpa) {
                    //实例伸缩(运行中)
                    html += '<button type="button" class="btn btn-default btn-xs" onclick="openScaleModel(\'' + serviceId + '\',\'' + row.serviceName + '\',\'' + row.instance + '\')">实例伸缩</button>\n';
                } else {
                    html += '<button type="button" class="btn btn-default btn-xs" title="只有服务状态为运行中并且没有设置自动伸缩时，才能进行该操作" disabled=true>实例伸缩</button>\n';
                }
                if (status == 1 || status == 4 || status == 6) {
                    //配额更改(未启动、已停止、启动失败)
                    html += '<button type="button" class="btn btn-default btn-xs" onclick="openQuotaModel(\'' + serviceId + '\',\'' + row.serviceName + '\',\'' + row.cpu + '\',\'' + parseFloat((row.memory / 1024).toFixed(2)) + '\')">配额更改</button>\n';
                } else {
                    html += '<button type="button" class="btn btn-default btn-xs" title="只有服务状态为未启动、已停止、启动失败时，才能进行该操作" disabled=true>配额更改</button>\n';
                }
                if (status == 3) {
                    //升级(运行中)
                    html += '<button type="button" class="btn btn-default btn-xs" onclick="openUpgradeModel(\'' + serviceId + '\',\'' + row.serviceName + '\',\'' + row.imageName + '\')">升级</button>\n';
                } else {
                    html += '<button type="button" class="btn btn-default btn-xs" title="只有服务状态为运行中时，才能进行该操作" disabled=true>升级</button>\n';
                }
                html += '<button type="button" class="btn btn-default btn-xs" onclick="openConfirm(\'' + serviceId + '\')">删除</button>';
                return html;
            }
        }]
    })
}

//停止、启动操作
function operate(serviceId, btnValue, obj) {
    var text = $(obj).parent().parent().find(".status").text(operations[btnValue] + "中");
    $.ajax({
        url: $webpath + "/bcm/v1/service/" + serviceId,
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify({
            "operation": btnValue
        }),
        success: function (data) {
            if (data.code == 200) {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'success'
                    });
                }
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
            $('#serviceTable').bootstrapTable('refresh');
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })
}

//删除操作
function openConfirm(serviceId) {
    $("input[name='serviceId']").val(serviceId);
    $('#confirmModal').modal('show');
}

//删除确认
function operationConfirm() {
    var serviceId = $("input[name='serviceId']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/service/" + serviceId,
        type: 'DELETE',
        contentType: "application/json",
        success: function (data) {
            $('#confirmModal').modal('hide');
            if (data.code == '200') {
                $.message({
                    message: data.message,
                    type: 'success'
                });
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
            $('#serviceTable').bootstrapTable('refresh');
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
            $('#serviceTable').bootstrapTable('refresh');
        }
    })
}

//弹性伸缩
function openScaleModel(serviceId, serviceName, instance) {
    $("#scaleModal input[name='serviceId']").val(serviceId);
    $("#scaleModal input[name='serviceName']").val(serviceName);
    $("#scaleModal input[name='instance']").val(instance);
    $('#scaleModal').modal('show');
}

//弹性伸缩提交
function scaleSave() {
    var svcCategoryValidator = $("#scaleForm").data('bootstrapValidator');
    svcCategoryValidator.validate();
    console.log(svcCategoryValidator.isValid())
    if (!svcCategoryValidator.isValid()) {
        return;
    }
    var serviceId = $("#scaleModal input[name='serviceId']").val();
    var instance = $("#scaleModal input[name='instance']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/service/" + serviceId + "/scale",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify({
            "instance": instance
        }),
        success: function (data) {
            $('#scaleModal').modal('hide');
            handleResetForm('#scaleForm');
            if (data.code == '200') {
                $.message({
                    message: data.message,
                    type: 'success'
                });
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
            $('#serviceTable').bootstrapTable('refresh');
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })
}

//修改配额
function openQuotaModel(serviceId, serviceName, cpu, memory) {
    $("#quotaModal input[name='serviceId']").val(serviceId);
    $("#quotaModal input[name='serviceName']").val(serviceName);
    $("#quotaModal input[name='cpu']").val(cpu);
    $("#quotaModal input[name='memory']").val(memory);
    $('#quotaModal').modal('show');
}

//修改配额提交
function quotaSave() {
    var svcCategoryValidator = $("#quotaForm").data('bootstrapValidator');
    svcCategoryValidator.validate();
    console.log(svcCategoryValidator.isValid())
    if (!svcCategoryValidator.isValid()) {
        return;
    }
    var serviceId = $("#quotaModal input[name='serviceId']").val();
    var cpu = $("#quotaModal input[name='cpu']").val();
    var memory = $("#quotaModal input[name='memory']").val();
    $.ajax({
        url: $webpath + "/bcm/v1/service/" + serviceId + "/quota",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify({
            "cpu": cpu,
            "memory": memory * 1024
        }),
        success: function (data) {
            $('#quotaModal').modal('hide');
            handleResetForm('#quotaForm');
            if (data.code == '200') {
                $.message({
                    message: data.message,
                    type: 'success'
                });
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
            $('#serviceTable').bootstrapTable('refresh');
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })
}

//升级
function openUpgradeModel(serviceId, serviceName, imageName) {
    $.ajax({
        url: $webpath + "/bcm/v1/image/" + imageName,
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                if (data.data) {
                    var str = '';
                    $(data.data).each(function (index, item) {
                        str += '<option value="' + item.id + '">' + item.imageVersion + '</option>'
                    })
                    $("#imageVersion").html(str);
                }
                $("#upgradeModal input[name='serviceId']").val(serviceId);
                $("#upgradeModal input[name='serviceName']").val(serviceName);
                $("#upgradeModal input[name='imageName']").val(imageName);
                $('#upgradeModal').modal('show');
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

//升级提交
function upgradeSave() {
    var svcCategoryValidator = $("#upgradeForm").data('bootstrapValidator');
    svcCategoryValidator.validate();
    console.log(svcCategoryValidator.isValid())
    if (!svcCategoryValidator.isValid()) {
        return;
    }
    var serviceId = $("#upgradeModal input[name='serviceId']").val();
    var imageVersion = $("#upgradeModal select[name='imageVersion']").val();
    if (imageVersion == undefined || imageVersion == '') {
        $.message({
            message: '请选择镜像版本！',
            type: 'error'
        });
        return;
    }
    var data = {
        "imageId": imageVersion
    }
    var memory = $("#upgradeModal input[name='memory']").val();
    var rollingType = $("input[name='rollingType']:checked").val();
    if (rollingType == 'numberUpgrade') {
        var percentage = $("input[name='percentage']").val();
        if (percentage == undefined || percentage == '') {
            $.message({
                message: '请填写比例！',
                type: 'error'
            });
            return;
        } else {
            data.percentage = percentage;
        }
    } else {
        var stepLength = $("input[name='stepLength']").val();
        if (stepLength == undefined || stepLength == '') {
            $.message({
                message: '请填写步长！',
                type: 'error'
            });
            return;
        } else {
            data.stepLength = stepLength;
        }
    }
    $.ajax({
        url: $webpath + "/bcm/v1/service/" + serviceId + "/upgrade",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            $('#upgradeModal').modal('hide');
            handleResetForm('#upgradeForm');
            if (data.code == '200') {
                $.message({
                    message: data.message,
                    type: 'success'
                });
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
            $('#serviceTable').bootstrapTable('refresh');
        },
        error: function () {
            $.message({
                message: 'error',
                type: 'error'
            });
        }
    })
}

function getServiceState(state) {
    var stateInfo = '';
    switch (state) {
        case 1:
            stateInfo = '未启动';
            break;
        case 2:
            stateInfo = '启动中';
            break;
        case 3:
            stateInfo = '运行中';
            break;
        case 4:
            stateInfo = '已停止';
            break;
        case 5:
            stateInfo = '升级中';
            break;
        case 6:
            stateInfo = '启动失败';
            break;
        case 7:
            stateInfo = '升级失败';
            break;
    }

    return stateInfo;
}

$(".glyphicon-search").click(function () {
    $('#serviceTable').bootstrapTable('refresh');
});

$(".btn-refresh").click(function (e) {
    $('#svcCategorySearch').val('');
    $('#serviceTable').bootstrapTable('refresh');
});

$("#serviceNameSearch").keydown(function (e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if (event.keyCode == "13") {
            $('#serviceTable').bootstrapTable('refresh');
        }
    }
});