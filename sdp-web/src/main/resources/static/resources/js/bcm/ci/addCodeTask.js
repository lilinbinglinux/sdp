var codeCommandValue = ''; // 命令值
$(document).ready(function () {
    formValidator('#addCodeForm');

    var flag = true;
    $('#languageList li .image-btn').on('click', function () {
        $('#addTaskFirst').hide();
        $('#addTaskSecond').show();
        if (flag) {
            codeCommandValue = CodeMirror.fromTextArea(document.getElementById("commandEditor"), {
                theme: "dracula",
                lineNumbers: true
            });
            codeCommandValue.setSize('height', '105px');
            flag = false;
        }

        $('#addCodeForm input[name="lang"]').attr('value',$(this).data('lang'));
        $('#addTaskSecond .image-page-title .lang').html($(this).data('describe'));
        $('#addCodeForm input[name="ciName"] + .input-prompt .lang').html($(this).data('describe'));
    });

    $('#addCodeForm .config-toggle-operate').on('click', function (event) {
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
            codeControlType: {
                validators: {
                    notEmpty: {
                        message: '请选择代码库'
                    }
                }
            },
            codeUrl: {
                validators: {
                    notEmpty: {
                        message: '请选择仓库'
                    }
                }
            },
            codeBranch: {
                validators: {
                    notEmpty: {
                        message: '请选择分支/tag'
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
            dockerfilePath: {
                validators: {
                    notEmpty: {
                        message: '请输入Dockerfile路径'
                    }
                }
            }
        }
    });
}

function codeTaskSubmit() {
    $('#addCodeForm').data('bootstrapValidator').validate();
    var addCodeData = formatFormData('#addCodeForm');
    addCodeData.buildcmd = codeCommandValue.getValue();
    addCodeData.ciType = 1; // 1：代码构建 2：DockerFile构建
    addCodeData.codeControlType = Number(addCodeData.codeControlType);

    console.log(addCodeData,'新建code---------------');
    if ($('#addCodeForm').data("bootstrapValidator").isValid()) {
        $.ajax({
            type: 'POST',
            url: $webpath + "/v1/ci",
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(addCodeData),
            success: function (result) {
                if(result.code == 200) {
                    $.message(result.message);
                }else{
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
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
                    $("#addCodeForm").data('bootstrapValidator').destroy();
                    formValidator('#addCodeForm');
                },5000);
            }
        })
    }
}