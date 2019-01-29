getQueryString();

var dokerCommandValue = ''; // 命令值
$(document).ready(function () {
    getDokerDetail();
    formValidator('#editDokerfileForm');

    dokerCommandValue = CodeMirror.fromTextArea(document.getElementById("commandEditor"), {
        theme: "dracula",
        lineNumbers: true
    });
    dokerCommandValue.setSize('height','296px');

    $('#uploadDockerfile').on('input',function () {
        $(this).next().text($(this).val().substring($(this).val().lastIndexOf("\\")+1));
    });

    getExampleTemplate();
    $('#exampleTemplate').on('input',function () {
        dokerCommandValue.setValue($(this).val());
    });
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
            ciName: {
                validators: {
                    notEmpty: {
                        message: '请输入任务名称'
                    }
                }
            },
            imageName: {
                validators: {
                    notEmpty: {
                        message: '请输入镜像名称'
                    }
                }
            },
            fileName: {
                validators: {
                    notEmpty: {
                        message: '请上传文件'
                    }
                }
            }
        }
    });
}

// 获取构建任务详情
function getDokerDetail() {
    $.ajax({
        url: $webpath + "/bcm/v1/ci/" + window.ciId,
        type: 'GET',
        dataType:"json",
        contentType: "application/json",
        success: function (req) {
            if(req.code == 200) {
                $('#editDokerfileForm input[name="ciName"]').attr("value", req.data.ci.ciName);
                $('#editDokerfileForm textarea[name="ciDescription"]').val(req.data.ci.ciDescription);
                $('#editDokerfileForm input[name="dockerFile"] +.file-text').text(req.data.ciFile.fileName);
                dokerCommandValue.setValue(req.data.ciFile.dockerfileContent);
                $('#customTemplate').attr('value',req.data.ciFile.dockerfileContent);
                $('#editDokerfileForm input[name="imageName"]').attr("value",req.data.ci.imageName);
                $('#editDokerfileForm input[name="imageVersion"]').attr("value", req.data.ci.imageVersion);
                $('#editDokerfileForm input[name="fileNameValue"]').attr("value", req.data.ciFile.fileName);
                $('#editDokerfileForm input[name="filePath"]').attr("value", req.data.ciFile.filePath);
            }
        }
    });
}

// 获取示例模板
function getExampleTemplate() {
    $.ajax({
        url: $webpath + "/bcm/v1/ci/dockerfile?page=0&size=2000&projectId=",
        type: 'GET',
        contentType: "application/json",
        dataType:"json",
        success: function (res) {
            if(res.code == 200) {
                var str = '';
                $.each(res.data.content,function (index,item) {
                    str += '<option value="'+item.dockerfileContent+'">'+item.dockerfileName+'</option>';
                });
                $('#exampleTemplate').append(str);
            }
        }
    });
}

function dokerTaskSubmit() {
    $('#editDokerfileForm').data('bootstrapValidator').validate();

    var editDokerData = formatFormData('#editDokerfileForm');
    editDokerData.dockerfileContent = dokerCommandValue.getValue();
    editDokerData.ciType = 2; // 1：代码构建 2：DockerFile构建
    //editDokerData.operator = "modify";
    editDokerData.fileName = editDokerData.fileNameValue;
    delete editDokerData["fileNameValue"];

    console.log(editDokerData,'配置Dokerfile---------------');

    var fd = new FormData();

    if (document.getElementById('uploadDockerfile').files[0]) {
        var file_obj = document.getElementById('uploadDockerfile').files[0];
        fd.append('dockerFile', file_obj);
    } else {
        fd.append('dockerFile', '');
    }
    fd.append('ciModelString', JSON.stringify(editDokerData));

    if ($('#editDokerfileForm').data("bootstrapValidator").isValid()) {
        $('button.btn-bconsole').attr('disabled',true);
        $.message({
            message: '配置构建任务需要时间，请耐心等待！',
            type: 'info'
        });
        $.ajax({
            type: 'POST',
            url: $webpath + "/bcm/v1/ci/" + window.ciId,
            data: fd,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (result) {
                if(result.code == 200) {
                    $.message(result.message);
                    setTimeout(function () {
                        window.location.href = $webpath + '/bcm/v1/ci/dockerfileConstructs';
                    },2000);
                }else{
                    $('button.btn-bconsole').removeAttr('disabled');
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
            },
            error: function (XMLHttpRequest,textStatus, errorThrown) {
                $('button.btn-bconsole').removeAttr('disabled');
                $.message({
                    message: textStatus,
                    type: 'error'
                });
            }
        });
    }else{
        $('.has-error').each(function (index,item) {
            if(index !== 0) {
                $(item).find('.help-block').hide();
                $(item).find('.form-control').addClass('clear-error-style');
            }else{
                $(item).find('.help-block').show();
                $(item).find('.form-control').removeClass('clear-error-style');
                $(item).find('.form-control').focus();
                setTimeout(function () {
                    $("#editDokerfileForm").data('bootstrapValidator').destroy();
                    formValidator('#editDokerfileForm');
                },5000);
            }
        })
    }
}