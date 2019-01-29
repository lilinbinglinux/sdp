var storageTypeMap = {
    "storageFile": "文件存储",
    "storageRbd": "块存储",
    "storageLocal": "本地存储"
}
var startingMonitor = {
    "status": false,
    "data": {}
}
var runningMonitor = {
    "status": false,
    "data": {}
}
var allImages = [];

$(document).ready(function () {
    formValidator('#storageAddForm');
    formValidator('#healthCheckForm');
    formValidator('#serviceCreateForm');
    formValidator('#envSaveAsForm');
    //收起、展开
    $('#serviceCreateForm .config-toggle-operate').on('click', function (event) {
        if ($(event.target).parent().next().is(':hidden')) {
            $(event.target).parent().next().show();
            $(event.target).removeClass('config-hide-operate');
            $(event.target).text('收起配置');
        } else {
            $(event.target).parent().next().hide();
            $(event.target).addClass('config-hide-operate');
            $(event.target).text('展开配置');
        }
    });
    //健康监测根据类型切换
    $("select[name='proType']").change(function () {
        var type = $(this).val();
        healthCheckSwitchType(type);
    })
    //禁用启动时监控
    $(".forbidStart").click(function () {
        $(this).hide();
        $(".startTip").text("已禁止");
        $(".startTip").removeClass("green-font ").addClass("red-font");
        startingMonitor.status = false;
    })
    //禁用运行时监控
    $(".forbidRunning").click(function () {
        $(this).hide();
        $(".runningTip").text("已禁止");
        $(".runningTip").removeClass("green-font ").addClass("red-font");
        runningMonitor.status = false;
    });
    //服务名称是否存在
    $('input[name="serviceName"]').blur(function () {
        var serviceName = $('input[name="serviceName"]').val();
        if (serviceName) {
            serviceNameIsExist(serviceName);
        }
    });
    deleteItem();
    addEnv();
    addPort();
    addConfig();
    getConfigList();
    //另存为环境变量
    openSaveModel();
    deleteTr();
    bindSelect();
    changeImage();
    openMonitorModal();
    createService();
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
            //服务基本信息校验
            serviceName: {
                validators: {
                    notEmpty: {
                        message: '请输入服务名称'
                    },
                    regexp: {
                        regexp: /^[a-z]([-a-z0-9]*[a-z0-9])?$/,
                        message: '以小写字母开头，小写字母、数字、下划线组合命名'
                    }
                }
            },
            instance: {
                validators: {
                    notEmpty: {
                        message: '请输入实例数量'
                    }
                }
            },
            //env模板名称校验
            templateName: {
                validators: {
                    notEmpty: {
                        message: '请输入模板名称'
                    }
                }
            },
            /** 存储 */
            //挂载地址
            mountPoint: {
                validators: {
                    notEmpty: {
                        message: '请输入挂载路径'
                    }
                }
            },
            /** 健康监测 */
            initialDelay: {
                validators: {
                    notEmpty: {
                        message: '请输入初始化等待时间'
                    }
                }
            },
            periodDetction: {
                validators: {
                    notEmpty: {
                        message: '请输入间隔时间'
                    }
                }
            },
            timeDection: {
                validators: {
                    notEmpty: {
                        message: '请输入超时时间'
                    }
                }
            },
            successThreshold: {
                validators: {
                    notEmpty: {
                        message: '请输入连接成功次数'
                    }
                }
            },
            tcpPort: {
                validators: {
                    notEmpty: {
                        message: '请输入监测端口'
                    }
                }
            },
            httpPort: {
                validators: {
                    notEmpty: {
                        message: '请输入监测端口'
                    }
                }
            },
            httpHeade: {
                validators: {
                    notEmpty: {
                        message: '请输入请求头'
                    }
                }
            },
            path: {
                validators: {
                    notEmpty: {
                        message: '请输入检查路径'
                    }
                }
            },
            exec: {
                validators: {
                    notEmpty: {
                        message: '请输入命令'
                    }
                }
            },
        }
    });
}

function serviceNameIsExist(serviceName) {
    var flag = true;
    $.ajax({
        url: $webpath + "/bcm/v1/service/info?serviceName=" + serviceName,
        type: 'GET',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.code == 200) {
                if (data.data != null) {
                    $.message({
                        message: '服务名称已存在！',
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

//环境变量、访问配置删除
function deleteItem() {
    $("body").on("click", ".deleteItem", function () {
        $(this).parents('.form-group').remove();
    })
}

//镜像下拉框
function bindSelect() {
    $.ajax({
        url: $webpath + "/bcm/v1/image",
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == 200) {
                allImages = data.data.content;
                //镜像名称
                var str = "";
                $(allImages).each(function (index, item) {
                    str += '<option value="' + item.imageName + '">' + item.imageName + '</option>';
                    var images = item.images;
                })
                if (str != '') {
                    $("#imageName").html(str);
                    $('#imageName').selectpicker('refresh');//动态刷新
                }
                var currentName = $("#imageName option:selected").attr('value');
                //镜像版本和端口
                bindImageVersionAndPort(currentName);
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

function bindImageVersionAndPort(currentName) {
    //镜像版本
    var versionStr = "";
    $(allImages).each(function (index, item) {
        if (currentName == item.imageName) {
            var images = item.images;
            for (var i = 0; i < images.length; i++) {
                versionStr += '<option value="' + images[i].id + '" data-ports="' + images[i].ports + '">' + images[i].imageVersion + '</option>'
            }
        }
    })
    if (versionStr != '') {
        $("#imageVersion").html(versionStr);
        $('#imageVersion').selectpicker('refresh');//动态刷新
    }
    //镜像中默认端口
    var port = $('#imageVersion').find("option:selected").data('ports');
    bindDefaultPorts(port);
}

//镜像选择
function changeImage() {
    $("#imageName").change(function () {
        var currentName = $("option:selected", this).attr('value');
        //镜像版本和端口
        bindImageVersionAndPort(currentName);
    })

    $("#imageVersion").change(function () {
        //镜像中默认端口
        var port = $('#imageVersion').find("option:selected").data('ports');
        bindDefaultPorts(port);
    })
}

/**************************************端口配置************************************* */
//镜像中默认端口
function bindDefaultPorts(port) {
    if (port != null) {
        var ports = [];
        var sear = new RegExp(',');
        if (sear.test(port)) {
            ports = port.split(',');
        } else {
            ports.push(port);
        }
        var portsStr = '';
        $.each(ports, function (index, item) {
            portsStr += '<div class="form-group portItem">' +
                '           <label class="col-md-2">端口/协议</label>' +
                '           <div class="col-md-7">' +
                '               <div class="col-md-4">' +
                '                   <input class="form-control port" type="number" name="port" placeholder="端口" value="' + item + '" disabled=true>' +
                '               </div>' +
                '               <div class="col-md-4">' +
                '                   <select class="form-control protocol" name="protocol">' +
                '                       <option value="TCP">TCP</option>' +
                '                       <option value="UDP">UDP</option>' +
                '                   </select>' +
                '               </div>' +
                '               <div class="col-md-3">' +
                '                   <span class="glyphicon glyphicon glyphicon-plus title-circle addPort" aria-hidden="true"></span>' +
                '               </div>' +
                '           </div>' +
                '       </div>';
        })
        $(".portGroup").html(portsStr);
        addPort;
    }
}

//添加端口配置
function addPort() {
    $("body").on("click", ".addPort", function () {
        var str = '<div class="form-group portItem">' +
            '           <label class="col-md-2">端口/协议</label>' +
            '           <div class="col-md-7">' +
            '               <div class="col-md-4">' +
            '                   <input class="form-control port" type="number" name="port" placeholder="端口">' +
            '               </div>' +
            '               <div class="col-md-4">' +
            '                   <select class="form-control protocol" name="protocol">' +
            '                       <option value="TCP">TCP</option>' +
            '                       <option value="UDP">UDP</option>' +
            '                   </select>' +
            '               </div>' +
            '               <div class="col-md-3">' +
            '                   <span class="glyphicon glyphicon glyphicon-plus title-circle addPort" aria-hidden="true"></span>' +
            '                   <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true"></span>' +
            '               </div>' +
            '           </div>' +
            '       </div>';
        $(".portGroup").append(str);
        addPort;
        deleteItem();
    })
}

var portAndProtocolData;
function checkportAndProtocol() {
    portAndProtocolData = {};
    var flag = true;
    console.log("4545")
    $(".portGroup .portItem").each(function (index, item) {
        var port = $(item).find(".port").val();
        var protocol = $(item).find(".protocol").val();
        console.log("protocol:" + protocol)
        console.log("port:" + port)
        //1、端口不能为空
        if ('' == port) {
            flag = false;
            $.message({
                message: '端口不能为空',
                type: 'error'
            });
            return;
        }
        //2、端口不能重复
        if (port in portAndProtocolData) {
            flag = false;
            $.message({
                message: '端口不能重复',
                type: 'error'
            });
            return;
        }
        portAndProtocolData[port] = protocol;
    })
    console.log(portAndProtocolData)
    console.log(flag)
    return flag;
}

/******************************************************************************** */

/**************************************环境变量************************************* */
//添加env
function addEnv() {
    $("body").on("click", ".addEnv", function () {
        var str = '<div class="form-group envItem">' +
            '       <label class="col-md-2">变量名</label>' +
            '       <div class="col-md-3">' +
            '           <input type="text" class="form-control envKey" name="env">' +
            '       </div>' +
            '       <label class="col-md-1">变量值</label>' +
            '       <div class="col-md-3">' +
            '           <input class="form-control envValue" name="env">' +
            '       </div>' +
            '       <div class="col-md-2">' +
            '           <span class="glyphicon glyphicon glyphicon-plus title-circle addEnv" aria-hidden="true"></span>' +
            '           <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true"></span>' +
            '       </div>' +
            '   </div>';

        // $(".envGroup").append(str);
        $(this).parent().parent().after(str);
        addEnv;
        deleteItem();
    })
}

// 打开模态框
$('#selectTemplatesModal').on('show.bs.modal', function () {
    envList();
    selectEnvTemplate();
});

//选择env模板
function selectEnvTemplate() {
    $(".env").click(function () {
        if ($(this).find(".check-icon").hasClass("triangle-bottomright")) {
            $(this).find(".check-icon").removeClass("triangle-bottomright");
            $(this).css("border", "solid 1px #eeeeee");
        } else {
            $(this).find(".check-icon").addClass("triangle-bottomright");
            $(this).css("border", "solid 1px red");
        }
    })
}

//环境变量列表（导入环境变量）
function envList(envTemplateNameSearch) {
    var templateName = '';
    if (envTemplateNameSearch != undefined) {
        templateName = envTemplateNameSearch;
    }
    $.ajax({
        url: $webpath + "/bcm/v1/env?templateName=" + templateName,
        type: 'GET',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                var str = '';
                $(data.data).each(function (index, item) {
                    str += '<div class="card-child col-md-6" data-id="' + item.id + '" data-env=' + item.envData + '>' +
                        '       <div class="env card-child-inner">' +
                        '           <div class="item-top">' +
                        '               <span>' + item.templateName + '</span>' +
                        '           </div>' +
                        '           <div class="item-bottom">' +
                        '               <span class="public-file-size">' + item.createTime + '</span>' +
                        '           </div>' +
                        '           <span class="check-icon"></span>' +
                        '       </div>' +
                        '   </div>';
                })
                $("#envList").html(str);
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

//搜索（导入环境变量）
$(".glyphicon-search").click(function () {
    envList($("#envTemplateNameSearch").val());
});

//搜索（导入环境变量）
$("#envTemplateNameSearch").keydown(function (e) {
    if (e.keyCode == 13) {
        e.preventDefault();
        if (event.keyCode == "13") {
            envList($("#envTemplateNameSearch").val());
        }
    }
});

//导入已保存环境变量提交
function addEnvSave() {
    $(".card-child").each(function () {
        if ($(this).find(".check-icon").hasClass("triangle-bottomright")) {
            var envId = $(this).data("id");
            var envData = $(this).data("env");
            var str = '';
            $.each(envData, function (key, value) {
                str += '<div class="form-group envItem">' +
                    '       <label class="col-md-2">变量名</label>' +
                    '       <div class="col-md-3">' +
                    '           <input type="text" class="form-control envKey" name="env" value="' + key + '">' +
                    '       </div>' +
                    '       <label class="col-md-1">变量值</label>' +
                    '       <div class="col-md-3">' +
                    '           <input class="form-control envValue" name="env" value="' + value + '">' +
                    '       </div>' +
                    '       <div class="col-md-2">' +
                    '           <span class="glyphicon glyphicon glyphicon-plus title-circle addEnv" aria-hidden="true"></span>' +
                    '           <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true"></span>' +
                    '       </div>' +
                    '   </div>';
            })
            $(".envGroup").append(str);
            // $(this).parent().parent().after(str);
            addEnv;
            deleteItem();
        }
    });
    $('#selectTemplatesModal').modal('hide');
}

//env另存为模态框打开
function openSaveModel() {
    $(".envSaveAs").click(function () {
        if (checkEnv()) {
            $('#envSaveAsModal').modal('show');
            console.log(envData)
        }
    })
}

//env另存为提交
function envSave() {
    var svcCategoryValidator = $("#envSaveAsForm").data('bootstrapValidator');
    svcCategoryValidator.validate();
    console.log(svcCategoryValidator.isValid())
    if (!svcCategoryValidator.isValid()) {
        return;
    }
    var templateName = $("input[name='templateName']").val();
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
                $('#envSaveAsModal').modal('hide');
                handleResetForm('#envSaveAsForm');
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

//环境变量校验
var envData;
function checkEnv() {
    envData = {};
    var flag = true;
    console.log("4545")
    $(".envGroup .envItem").each(function (index, item) {
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
    return flag;
}
/******************************************************************************** */

/**************************************配置文件************************************* */
function getConfigList() {
    $.ajax({
        url: $webpath + "/bcm/v1/config",
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                var str = '';
                $(data.data).each(function (index, item) {
                    str += '<option value="' + item.id + '" data-num="' + item.fileNumber + '">' + item.templateName + '</option>';
                })
                $(".configMap").html(str);
                $("input[name='configNum']").attr("value", $(".configMap option:selected").data('num'));
            } else {
                // if (undefined != data.message && '' != data.message) {
                //     $.message({
                //         message: data.message,
                //         type: 'error'
                //     });
                // }
            }
        },
        error: function () {
            // $.message({
            //     message: 'error',
            //     type: 'error'
            // });
        }
    })
}
//添加配置文件
function addConfig() {
    $(".addConfig").click(function () {
        var str = '<div class="form-group configMapItem">' +
            '           <label class="col-md-2">文件名称</label>';
        str += $("#configMapTemplate")[0].innerHTML;
        str +=
            '           <label class="col-md-1">挂载路径</label>' +
            '           <div class="col-md-2">' +
            '               <input class="form-control configMapPath" name="configMapPath" value="">' +
            '           </div>' +
            '           <div class="col-md-2">' +
            '               <span class="glyphicon glyphicon glyphicon-minus title-circle deleteItem" aria-hidden="true"></span>' +
            '           </div>' +
            '       </div>';
        $(".configMapGroup").append(str);
        deleteItem();
        updateConfigNum();
    })
}

function updateConfigNum() {
    $(".configMap").change(function () {
        $(this).parent().parent().find(".configNum").attr("value", $("option:selected", this).data('num'));
    })
}

//配置文件校验
var configMapData;
function checkConfig() {
    configMapData = {};
    var flag = true;
    console.log("4545")
    $(".configMapGroup .configMapItem").each(function (index, item) {
        var configMap = $(item).find(".configMap").val();
        var configMapPath = $(item).find(".configMapPath").val();
        console.log("configMap:" + configMap)
        console.log("configMapPath:" + configMapPath)
        if (configMap == null) {
            return true;
        }
        if (configMapPath == '') {
            flag = false;
            $.message({
                message: '配置文件路径不能为空',
                type: 'error'
            });
            return;
        }
        configMapData[configMap] = configMapPath;
    })
    console.log(configMapData)
    console.log(flag)
    return flag;
}
/******************************************************************************** */

/***************************************云存储************************************* */
//删除存储
function deleteTr() {
    $('.deleteTr').click(function () {
        $(this).parents('tr').remove();
    })
}

//添加存储提交
function addStorageSave() {
    var svcCategoryValidator = $("#storageAddForm").data('bootstrapValidator');
    svcCategoryValidator.validate();
    if (!svcCategoryValidator.isValid()) {
        return;
    }
    var storageFormData = formatFormData('#storageAddForm');
    storageFormData.name = $("select[name='storageName']").find("option:selected").text();
    console.log(storageFormData, '新增------');
    //页面添加
    addStorageTable(storageFormData);
    $('#storageAddModal').modal('hide');
    handleResetForm('#storageAddForm');
    console.log(getStorageData());
}

function addStorageTable(storageFormData) {
    var storageType = storageFormData.storageType;
    var str = '<tr path="' + storageFormData.mountPoint + '" name="' + (storageType != 'storageLocal' ? storageFormData.storageName : storageFormData.hostPath) + '" type="' + storageType + '">' +
        '           <td>' + storageTypeMap[storageType] + '</td>' +
        '           <td>' +
        '               <span>' + (storageType != 'storageLocal' ? storageFormData.name : storageFormData.hostPath) + '</span>' +
        '           </td>' +
        '           <td>' + storageFormData.mountPoint + '</td>' +
        '           <td>' +
        '               <span class="operation-font deleteTr">删除</span>' +
        '       </td>' +
        '       </tr>';
    $("#storageTable tbody").append(str);
    deleteTr();
}

//创建时获取添加的云存储数据
function getStorageData() {
    var storageFile = {};
    $('#storageContent').children('tr').each(function (index, item) {
        var path = $(item).attr("path");
        var name = $(item).attr("name");
        if ($(item).attr("type") == 'storageFile') {
            storageFile[name] = path;
        }
    })

    return storageFile;
}

//打开模态框
$('#storageAddModal').on('show.bs.modal', function () {
    $.ajax({
        url: $webpath + "/bcm/v1/storage/file",
        type: 'GET',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.code == '200') {
                var content = data.data.content;
                var str = '';
                $(content).each(function (index, item) {
                    str += '<option value="' + item.id + '">' + item.name + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + parseFloat((item.used / 1024).toFixed(2)) + '/' + parseFloat((item.size / 1024).toFixed(2)) + 'GB</span></option>';
                })
                $("#storageName").html(str);
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
});

/******************************************************************************** */

/***************************************健康监测********************************** */
// 健康监测类型切换
function healthCheckSwitchType(type) {
    if (type == 'tcp') {
        $(".tcp").show();
        $(".http").hide();
        $(".exec").hide();
    } else if (type == 'exec') {
        $(".tcp").hide();
        $(".http").hide();
        $(".exec").show();
    } else if (type == 'http') {
        $(".tcp").hide();
        $(".http").show();
        $(".exec").hide();
    }
}

//健康监测设置提交
function healthCheckSave() {

    var svcCategoryValidator = $("#healthCheckForm").data('bootstrapValidator');
    svcCategoryValidator.validate();

    if (!svcCategoryValidator.isValid()) {
        return;
    }
    var healthCheckFormData = formatFormData('#healthCheckForm');
    console.log(healthCheckFormData, '新增------');
    if (healthCheckFormData.probeType == '1') {
        //运行时
        runningMonitor.status = true;
        runningMonitor.data = healthCheckFormData;
        $(".runningMonitor").next().show();
        $(".runningTip").text("已启动");
        $(".runningTip").removeClass("red-font ").addClass("green-font");
        console.log(runningMonitor)
    } else if (healthCheckFormData.probeType == '2') {
        //启动时
        startingMonitor.status = true;
        startingMonitor.data = healthCheckFormData;
        console.log(startingMonitor)
        $(".startMonitor").next().show();
        $(".startTip").text("已启动");
        $(".startTip").removeClass("red-font ").addClass("green-font");
    }

    $('#healthCheckModal').modal('hide');
    handleResetForm('#healthCheckForm');
}

// $('#healthCheckModal').on('show.bs.modal', function () {
//     var exec = '1,2,3'
//  //逗号转为换行
//     var reg=new RegExp(",","g"); 
//     exec= exec.replace(reg,"\r\n"); 
//     console.log(exec)
//     $(".execVal").val(exec);
// });

//打开监控模态框
function openMonitorModal() {
    $(".startMonitor").click(function () {
        $("#healthCheckModal .title").text("设置启动时监控");
        $("input[name='probeType']").val(2);
        $('#healthCheckModal').modal('show');
        //赋值
        if (startingMonitor.status) {
            assignToHealthCkeck(startingMonitor.data)
        }
        //根据类型显示不同的内容
        healthCheckSwitchType($("select[name='proType']").val());
    })
    $(".runningMonitor").click(function () {
        $("#healthCheckModal .title").text("设置运行时监控");
        $("input[name='probeType']").val(1);
        $('#healthCheckModal').modal('show');
        //赋值
        if (runningMonitor.status) {
            assignToHealthCkeck(runningMonitor.data)
        }
        //根据类型显示不同的内容
        healthCheckSwitchType($("select[name='proType']").val());
    })
}

//健康监测模态框赋值
function assignToHealthCkeck(formData) {
    $(".execVal").val(formData.exec);
    $("input[name='httpHeadeName']").val(formData.httpHeadeName);
    $("input[name='httpHeadeValue']").val(formData.httpHeadeValue);
    $("input[name='httpPort']").val(formData.httpPort);
    $("input[name='initialDelay']").val(formData.initialDelay);
    $("input[name='path']").val(formData.path);
    $("input[name='periodDetction']").val(formData.periodDetction);
    $("select[name='proType']").val(formData.proType);
    $("input[name='probeType']").val(formData.probeType);
    $("input[name='successThreshold']").val(formData.successThreshold);
    $("input[name='tcpPort']").val(formData.tcpPort);
    $("input[name='timeDection']").val(formData.timeDection);
}

function getHealthCheckData(data) {
    if (data.proType == 'http') {
        var httpParam = {
            "httpHeade": data.httpHeade,
            "path": data.path,
            "port": data.httpPort
        }
        data.http = httpParam;
        var httpHeade = {};
        httpHeade[data.httpHeadeName] = data.httpHeadeValue;
        data.httpHeade = httpHeade;
        delete data.httpHeadeName;
        delete data.httpHeadeValue;
        delete data.exec;
        delete data.tcpPort;
    } else if (data.proType == 'tcp') {
        var tcpParam = {
            "port": data.tcpPort
        }
        data.tcp = tcpParam;
        delete data.exec;
        delete data.httpHeade;
        delete data.httpPort;
        delete data.path;
    } else if (data.proType == 'exec') {
        var exec = data.exec;
        // 换行转为逗号
        var reg = new RegExp("\r\n", "g");
        exec = exec.replace(reg, ",");
        var execParam = {
            "exec": exec
        }
        data.exec = execParam;
        delete data.httpHeade;
        delete data.httpPort;
        delete data.path;
        delete data.tcpPort;
    }
    console.log(runningMonitor, "running")
    console.log(startingMonitor, "start")
    return data;
}
/**************************************************************************** */

//创建服务
function createService() {
    $(".create-btn").click(function () {
        console.log("1212")
        var svcCategoryValidator = $("#serviceCreateForm").data('bootstrapValidator');
        svcCategoryValidator.validate();
        console.log(svcCategoryValidator.isValid())
        if (!svcCategoryValidator.isValid()) {
            return;
        }
        //访问配置
        if (!checkportAndProtocol()) {
            return;
        }
        //环境变量
        if (!checkEnv()) {
            return;
        }
        //配置文件
        if (!checkConfig()) {
            return;
        }
        var cpu = $("input[name='cpu']").val(),
            serviceName = $("input[name='serviceName']").val(),
            imageId = $('#imageVersion').find("option:selected").attr('value'),
            instance = $("input[name='instance']").val(),
            memory = $("input[name='memory']").val(),
            cmd = $("input[name='cmd']").val(),
            description = $(".description").text();

        //服务名称是否存在
        console.log(serviceNameIsExist(serviceName))
        if (serviceNameIsExist(serviceName) == false) {
            return;
        }
        if (null == imageId || imageId == '' || imageId == undefined) {
            $.message({
                message: '镜像为空！',
                type: 'error'
            });
            return;
        }
        var data = {
            "cpu": cpu,
            "imageId": imageId,
            "instance": instance,
            "memory": memory * 1024,
            "serviceName": serviceName,
            "description": description,
            "cmd": cmd
        }
        //健康监测数据
        if (startingMonitor.status != false || runningMonitor.status != false) {
            var healthCheck = [];
            if (startingMonitor.status != false) {
                healthCheck.push(getHealthCheckData(startingMonitor.data));
            }
            if (runningMonitor.status != false) {
                healthCheck.push(getHealthCheckData(runningMonitor.data));
            }
            data.healthCheck = healthCheck;
        }
        //云存储
        var storageFile = getStorageData();
        if (!$.isEmptyObject(storageFile)) {
            data.storageFile = JSON.stringify(storageFile);
        }
        //访问配置
        if (!$.isEmptyObject(portAndProtocolData)) {
            data.portAndProtocol = JSON.stringify(portAndProtocolData);
        }
        //环境变量
        if (!$.isEmptyObject(envData)) {
            data.envData = JSON.stringify(envData);
        }
        //配置文件
        if (!$.isEmptyObject(configMapData)) {
            data.config = JSON.stringify(configMapData);
        }
        console.log(data)
        $.ajax({
            url: $webpath + "/bcm/v1/service",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                if (data.code == '200') {
                    //跳转页面
                    window.location.href = $webpath + "/bcm/v1/service/servicePage";
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

