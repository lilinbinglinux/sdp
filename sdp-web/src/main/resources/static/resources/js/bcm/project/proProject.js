$(document).ready(function() {
    formValidator('#addProjectForm');
    formValidator('#editProjectForm');

    /*默认图标模式*/
    initProjectGlyph();
});

/*刷新页面*/
function reloadPage() {
    window.location.reload();
}

/*项目展示方式全局变量*/
var switchPro = 0;
function switchProject(switchButton) {
    switchPro = switchButton;
    if(switchPro === 0){
        $(".btn-prolist").children("img").attr("src",webpath+'/resources/img/bcm/project/list.png');
        $(".btn-procard").children("img").attr("src",webpath+'/resources/img/bcm/project/card_active.png');
        document.getElementsByClassName("btn-procard")[0].style.backgroundColor = "#29cc97";
        document.getElementsByClassName("btn-prolist")[0].style.backgroundColor = "#f9f9f9";
        document.getElementById("projectTable").style.display = 'none';
        document.getElementById("projectGlyph").style.display = 'grid';
        /*初始化图标列表*/
        initProjectGlyph();
    }else {
        $(".btn-prolist").children("img").attr("src",webpath+'/resources/img/bcm/project/list_active.png');
        $(".btn-procard").children("img").attr("src",webpath+'/resources/img/bcm/project/card.png');
        document.getElementsByClassName("btn-prolist")[0].style.backgroundColor = "#29cc97";
        document.getElementsByClassName("btn-procard")[0].style.backgroundColor = "#f9f9f9";
        document.getElementById("projectTable").style.display = 'block';
        document.getElementById("projectGlyph").style.display = 'none';
        /*初始化表格*/
        initProjectTable();
    }
}


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
            projectName: {
                validators: {
                    notEmpty: {
                        message: '请输入项目名称'
                    },
                    regexp: {
                        regexp: /^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                        message: '以中文、字母、数字、下划线组合命名，且不以下划线开头'
                    }
                }
            },
            projectId: {
                validators: {
                    notEmpty: {
                        message: '请输入项目编码'
                    },
                    stringLength: {
                        max: 32,
                        message: '项目编码过长'
                    },
                    regexp: {
                        regexp: /^(?!_-)[a-zA-Z0-9_-]+$/,
                        message: '以字母、数字、下划线、中划线组合命名，且不以下划线、中划线开头'
                    }
                }
            },
            projectResume: {
                validators: {
                    notEmpty: {
                        message: '请输入项目说明'
                    }
                }
            },
            hostAddr: {
                validators: {
                    notEmpty: {
                        message: '请输入访问主路径'
                    },
                    stringLength: {
                        max: 200,
                        message: '访问路径长度过长'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9:/]+$/,
                        message: '内容可以由任意数字、字母组成，不支持特殊字符'
                    }
                }
            },
            projectLevel: {
                validators: {
                    notEmpty: {
                        message: '请选择等级'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '请输入数字'
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
                        message: '请输入数字'
                    }
                }
            }
            // stateFlag: {
            //     validators: {
            //         notEmpty: {
            //             message: '请选择状态'
            //         }
            //     }
            // }
        }
    });
}

/*
* 新建项目
* */
function saveProject() {
    $('#addProjectForm').data('bootstrapValidator').validate();
    if ($('#addProjectForm').data('bootstrapValidator').isValid()) {
        var proData = formatFormData('#addProjectForm');
        proData.profile = proFile;
        $.ajax({
            url: webpath + "/bcm/v1/project/selectOneProject",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: {
                "projectId": proData.projectId
            },
            success: function (result) {
                if (result.code === 0 && result.data !== null){
                    $.message({
                        message: '项目编码已存在，请重新填写',
                        type: 'error'
                    })
                } else {
                    $.ajax({
                        url: webpath + "/bcm/v1/project/newProProject",
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        data: JSON.stringify(proData),
                        success: function (result) {
                            if (result.code === 0) {
                                $.message(result.message);
                                $('#addProject').modal('hide');
                                handleResetForm('#addProjectForm');
                                if(switchPro===1){
                                    $("#proProjectTable").bootstrapTable('refresh');
                                }else {
                                    initProjectGlyph();
                                }
                                /*执行刷新门户的项目列表*/
                                window.parent.getProjectData(webpath);
                            } else {
                                $.message({
                                    message: result.message,
                                    type: 'error'
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}

/*
* 选择项目头像
* */
var proFile = 'profile1';
function selectProfile(event) {
    proFile = event.id;
    var imgObj = document.getElementById(event.id);
    imgObj.style.backgroundColor = "#5a5a5a";
    imgObj.style.borderRadius="50%";
    imgObj.style.borderColor="#33cc99";
    imgObj.style.borderWidth = "2px";
    imgObj.style.borderStyle = "solid";
    var imgElements = ["profile1","profile2","profile3","profile4","profile5"];
    for (var i=0;i<imgElements.length;i++){
        if(event.id !== imgElements[i]){
            imgObj = document.getElementById(imgElements[i]);
            imgObj.style.backgroundColor = "";
            imgObj.style.borderRadius="";
            imgObj.style.borderColor="";
            imgObj.style.borderWidth = "";
            imgObj.style.borderStyle = "";
        }
    }
}


/*
* 判断查询框的输入
* */
var oTxt = document.getElementById('searchKey');
var cpLock = false;
/*compositionstart 事件触发于一段文字的输入之前（类似于 keydown 事件，但是该事件仅在若干可见字符的输入之前，而这些可见字符的输入可能需要一连串的键盘操作、语音识别或者点击输入法的备选词）。*/
$('#searchKey').on('compositionstart', function () {
    cpLock = true;
});
/*当文本段落的组成完成或取消时, compositionend 事件将被触发 (具有特殊字符的触发, 需要一系列键和其他输入, 如语音识别或移动中的字词建议)。*/
$('#searchKey').on('compositionend', function () {
    cpLock = false;
    if(switchPro === 0){
        searchProGly(oTxt.value);
    }else {
        searchProTable(oTxt.value);
    }
});
$('#searchKey').on('input', function () {
    if (!cpLock) {
        if(switchPro === 0){
            searchProGly(oTxt.value);
        }else {
            searchProTable(oTxt.value);
        }
    }
});

/*
* 条件查询项目，列表模式
* */
function searchProTable(key) {
    $("#proProjectTable").bootstrapTable('destroy');
    $("#proProjectTable").bootstrapTable({
        url: webpath+'/bcm/v1/project/selectProjectByCondition',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        formatNoMatches: formatNoMatch,
        formatLoadingMessage: formatLoading,
        // formatShowingRows: formatShowingRow,
        // formatRecordsPerPage: formatRecordPerPage,
        classes: 'table-no-bordered',
        cache: false,
        // striped: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        showJumpto: true,
        paginationPreText: '<i class="fa fa-angle-left bconsole-left-arrow" aria-hidden="true"></i>',
        paginationNextText: '<i class="fa fa-angle-right bconsole-left-arrow" aria-hidden="true"></i>',
        formatRecordsPerPage: function (pageNumber) {
            // return '每页显示 ' + pageNumber + ' 条记录';
            return '' + pageNumber + '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            // return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
            return '共' + totalRows + '条';
        },
        queryParams: function (params) {
            return JSON.stringify({
                "pageNo": (params.offset / params.limit) + 1,
                "pageSize": params.limit,
                "searchKey": key
            })
        },
        queryParamsType:'limit',
        responseHandler : function (res) {
            console.log(res);
            if (res.code === 0) {
                return {
                    "total": res.data.total,
                    "rows": res.data.data
                }
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        columns: cols
    });
}

/*
* 条件查询项目，图标模式
* */
function searchProGly(key) {
    $.ajax({
        url: webpath+'/bcm/v1/project/selectProjectByCondition',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:JSON.stringify({
            "pageNo": 1,
            "pageSize": 10000,
            "searchKey":key
        }),
        success: function (result) {
            if(result.code===0){
                var html = "";
                var glyphObj = document.getElementById("projectGlyph");
                if(result.data.data.length>0){
                    var proNum = 0;
                    for (var gi = 0; gi<result.data.data.length; gi++){
                        /*获取每个项目的成员数*/
                        $.ajax({
                            url:webpath+"/bcm/v1/project/selectAllUsers",
                            type:"GET",
                            async: false,
                            contentType:"application/json; charset=utf-8",
                            data: {
                                "projectId": result.data.data[gi].projectId
                            },
                            success: function (result) {
                                if(result.code === 0){
                                    proNum = result.data.length;
                                }
                            }
                        });
                        var date = new Date(result.data.data[gi].createTime).toLocaleDateString().replace(/\//g,"-");
                        html += "<div class='project-grid' style='position: relative;'>" +
                            "<img src= " + webpath + "/resources/img/bcm/project/"+result.data.data[gi].profile+".png>" +
                            /*项目操作弹框*/
                            "<div id='proBox_" + gi + "' class='project-box'>" +
                            "</div>" +
                            "<p style='font-size: 22px;margin-top: 20px;'>"+result.data.data[gi].projectName+"</p>" +
                            "<p>" +
                            "<span>创建人"+result.data.data[gi].createUser +"</span>" +
                            "<span>成员" + proNum + "个</span>" +
                            "</p>" +
                            "<p class='project-resume'>"+result.data.data[gi].projectResume+"</p>" +
                            "<p class='row' style='bottom: -15px;position: absolute;'>" +
                            "<span class='glyphicon glyphicon-time'></span><span class='project-date'>"+date+"</span>" +
                            "</p>" +
                            "</div>"
                    }

                    glyphObj.innerHTML = html;

                    for(var i=0;i<result.data.data.length;i++) {
                        /* 查看用户项目角色，只有项目管理员可见编辑、删除，否则这两项不可见*/
                        var isProjectAdmin = false;
                        $.ajax({
                            url: webpath+"/bcm/v1/project/getUserRole",
                            type:"GET",
                            async: false,
                            contentType:"application/json;charset=utf-8",
                            data:{
                                "projectId": result.data.data[i].projectId
                            },
                            success: function (res) {
                                if(res.code === 0){
                                    if(res.data !== null && res.data === "0") {
                                        isProjectAdmin = true;
                                    }
                                }
                            }
                        });
                        /*只有项目管理员可修改、删除项目*/
                        var proboxHtml = '<div class="probox">\n' +
                            '<img src= "'+webpath+'/resources/img/common/operation.png">\n' +
                            '<div class="tip" style="display: none;"><div class="inner"></div>\n' +
                            '     <div class="bord">\n' +
                            '          <li class="row project_edit">\n' +
                            '              <img src="'+webpath+'/resources/img/common/edit.png">\n' +
                            '              <a data-toggle="modal" data-target="#editProject" onclick="changeProject(\'' + result.data.data[i].projectId + '\')">编辑</a>\n' +
                            '          </li>\n' +
                            '          <li class="row project_delete">\n' +
                            '              <img src="'+webpath+'/resources/img/common/delete.png">\n' +
                            '              <a onclick="deleteProject( \'' +result.data.data[i].projectId+'\')">删除</a>\n' +
                            '          </li>\n' +
                            '          <li class="row project_user">\n' +
                            '              <img src="'+webpath+'/resources/img/common/user.png">\n' +
                            '              <a onclick="addUser(\'' + result.data.data[i].projectId + '\')">查看人员</a>\n' +
                            '          </li>\n' +
                            '          <li class="row project_quota">\n' +
                            '              <img src="'+webpath+'/resources/img/bcm/project/project_quota.png">\n' +
                            '              <a onclick="openQuotaPage(\'' + result.data.data[i].projectId + '\')">项目配额</a>\n' +
                            '          </li>\n' +
                            '     </div>\n' +
                            '</div>\n' +
                            '</div>';
                        /*非项目管理员不可修改、删除项目*/
                        if(!isProjectAdmin){
                            proboxHtml = '<div class="probox">\n' +
                                '<img src= "'+webpath+'/resources/img/common/operation.png">\n' +
                                '<div class="tip1" style="display: none;"><div class="inner"></div>\n' +
                                '     <div class="bord">\n' +
                                '          <li class="row project_user">\n' +
                                '              <img src="'+webpath+'/resources/img/common/user.png">\n' +
                                '              <a onclick="addUser(\'' + result.data.data[i].projectId + '\')">查看人员</a>\n' +
                                '          </li>\n' +
                                '          <li class="row project_quota">\n' +
                                '              <img src="'+webpath+'/resources/img/bcm/project/project_quota.png">\n' +
                                '              <a onclick="openQuotaPage(\'' + result.data.data[i].projectId + '\')">项目配额</a>\n' +
                                '          </li>\n' +
                                '     </div>\n' +
                                '</div>\n' +
                                '</div>';
                        }

                        $("#proBox_" + i).html(proboxHtml);
                        /*控制项目操作悬浮框hover事件*/
                        $(".probox").mouseenter(function(){
                            $(this).children(".tip").css("display","block");
                            $(this).children(".tip1").css("display","block");
                        });
                        $(".probox").mouseleave(function(){
                            $(this).children(".tip").css("display","none");
                            $(this).children(".tip1").css("display","none");
                        });
                        $(".bord .project_edit").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/edit_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/edit.png');
                        });
                        $(".bord .project_delete").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/delete_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/delete.png');
                        });
                        $(".bord .project_user").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/user_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/user.png');
                        });
                        $(".bord .project_quota").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/bcm/project/project_quota_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/bcm/project/project_quota.png');
                        });
                    }
                }else {
                    glyphObj.innerHTML = html;
                    // TODO 无项目界面
                }
            }
        }
    });

}

/*
* 查询项目表，图标列表
*/
function initProjectGlyph() {
    var loading = layer.load(2, {time: 3*1000});
    $.ajax({
        url: webpath+'/bcm/v1/project/allProProject',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:JSON.stringify({
            "pageNo": 1,
            "pageSize": 10000
        }),
        success: function (result) {
            if(result.code===0){
                var html = "";
                var glyphObj = document.getElementById("projectGlyph");
                if(result.data.data.length>0){
                    var proNum = 0;
                    for (var gi = 0; gi<result.data.data.length;gi++){
                        /*获取每个项目的成员数*/
                        $.ajax({
                            url:webpath+"/bcm/v1/project/selectAllUsers",
                            type:"GET",
                            async: false,
                            contentType:"application/json; charset=utf-8",
                            dataType:"json",
                            data: {
                                "projectId": result.data.data[gi].projectId
                            },
                            success: function (result) {
                                if(result.code === 0){
                                    proNum = result.data.length;
                                }
                            }
                        });

                        var date = new Date(result.data.data[gi].createTime).toLocaleDateString().replace(/\//g,"-");
                        html += "<div class='project-grid' style='position: relative;'>" +
                            "<img src= " + webpath + "/resources/img/bcm/project/"+result.data.data[gi].profile+".png>" +
                            /*项目操作弹框*/
                            "<div id='proBox_" + gi + "' class='project-box'>" +
                            "</div>" +
                            "<p style='font-size: 22px;margin-top: 20px;'>"+result.data.data[gi].projectName+"</p>" +
                            "<p>" +
                            "<span>创建人"+result.data.data[gi].createUser +"</span>" +
                            "<span>成员" + proNum + "个</span>" +
                            "</p>" +
                            // TODO 设置项目描述超出两行省略显示，不适应连续数字
                            "<p class='project-resume'>"+result.data.data[gi].projectResume+"</p>" +
                            "<p class='row' style='bottom: -15px;position: absolute;'>" +
                            "<span class='glyphicon glyphicon-time'></span><span class='project-date'>"+date+"</span>" +
                            "</p>" +
                            "</div>"
                    }

                    glyphObj.innerHTML = html;

                    for(var i=0;i<result.data.data.length;i++) {
                        /* 查看用户项目角色，只有项目管理员可见编辑、删除，否则这两项不可见*/
                        var isProjectAdmin = false;
                        $.ajax({
                            url: webpath+"/bcm/v1/project/getUserRole",
                            type:"GET",
                            async: false,
                            contentType:"application/json;charset=utf-8",
                            dataType:"json",
                            data:{
                                "projectId": result.data.data[i].projectId
                            },
                            success: function (res) {
                                if(res.code === 0){
                                    if(res.data !== null && res.data === "0") {
                                        isProjectAdmin = true;
                                    }
                                }
                            }
                        });
                        /*只有项目管理员可修改、删除项目*/
                        var proboxHtml = '<div class="probox">\n' +
                            '<img src= "'+webpath+'/resources/img/common/operation.png">\n' +
                            '<div class="tip" style="display: none;"><div class="inner"></div>\n' +
                            '     <div class="bord">\n' +
                            '          <li class="row project_edit">\n' +
                            '              <img src="'+webpath+'/resources/img/common/edit.png">\n' +
                            '              <a data-toggle="modal" data-target="#editProject" onclick="changeProject(\'' + result.data.data[i].projectId + '\')">编辑</a>\n' +
                            '          </li>\n' +
                            '          <li class="row project_delete">\n' +
                            '              <img src="'+webpath+'/resources/img/common/delete.png">\n' +
                            '              <a onclick="deleteProject( \'' +result.data.data[i].projectId+'\')">删除</a>\n' +
                            '          </li>\n' +
                            '          <li class="row project_user">\n' +
                            '              <img src="'+webpath+'/resources/img/common/user.png">\n' +
                            '              <a onclick="addUser(\'' + result.data.data[i].projectId + '\')">查看人员</a>\n' +
                            '          </li>\n' +
                            '          <li class="row project_quota">\n' +
                            '              <img src="'+webpath+'/resources/img/bcm/project/project_quota.png">\n' +
                            '              <a onclick="openQuotaPage(\'' + result.data.data[i].projectId + '\')">项目配额</a>\n' +
                            '          </li>\n' +
                            '     </div>\n' +
                            '</div>\n' +
                            '</div>';
                        /*非项目管理员不可修改、删除项目*/
                        if(!isProjectAdmin){
                            proboxHtml = '<div class="probox">\n' +
                                '<img src= "'+webpath+'/resources/img/common/operation.png">\n' +
                                '<div class="tip1" style="display: none;"><div class="inner"></div>\n' +
                                '     <div class="bord">\n' +
                                '          <li class="row project_user">\n' +
                                '              <img src="'+webpath+'/resources/img/common/user.png">\n' +
                                '              <a onclick="addUser(\'' + result.data.data[i].projectId + '\')">查看人员</a>\n' +
                                '          </li>\n' +
                                '          <li class="row project_quota">\n' +
                                '              <img src="'+webpath+'/resources/img/bcm/project/project_quota.png">\n' +
                                '              <a onclick="openQuotaPage(\'' + result.data.data[i].projectId + '\')">项目配额</a>\n' +
                                '          </li>\n' +
                                '     </div>\n' +
                                '</div>\n' +
                                '</div>';
                        }
                        /*项目操作悬浮弹框*/
                        $("#proBox_" + i).html(proboxHtml);

                        /*控制项目操作悬浮框hover事件*/
                        $(".probox").mouseenter(function(){
                            //方法一 find查找所有的子元素，会一直查找，跨层级查找
                            // var choose_name_1= $(this).find(".tip");
                            // choose_name_1.css("display","block");
                            //方法二 children 查找直接的子元素，不会跨层级
                            $(this).children(".tip").css("display","block");
                            $(this).children(".tip1").css("display","block");
                        });
                        $(".probox").mouseleave(function(){
                            $(this).children(".tip").css("display","none");
                            $(this).children(".tip1").css("display","none");
                        });
                        /*项目操作图标hover切换*/
                        $(".bord .project_edit").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/edit_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/edit.png');
                        });
                        $(".bord .project_delete").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/delete_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/delete.png');
                        });
                        $(".bord .project_user").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/user_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/common/user.png');
                        });
                        $(".bord .project_quota").hover(function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/bcm/project/project_quota_active.png');
                        }, function () {
                            $(this).children("img").attr("src",webpath+'/resources/img/bcm/project/project_quota.png');
                        });
                    }
                    layer.close(loading);
                }
            }
        }
    });
}



/*
* 查询项目表，表格列表
*/
function initProjectTable() {
    $("#proProjectTable").bootstrapTable('destroy');
    $("#proProjectTable").bootstrapTable({
        url: webpath+'/bcm/v1/project/allProProject',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        formatNoMatches: formatNoMatch,
        formatLoadingMessage: formatLoading,
        // formatShowingRows: formatShowingRow,
        // formatRecordsPerPage: formatRecordPerPage,
        classes: 'table-no-bordered',
        cache: false,
        // striped: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        showJumpto: true,
        paginationPreText: '<i class="fa fa-angle-left bconsole-left-arrow" aria-hidden="true"></i>',
        paginationNextText: '<i class="fa fa-angle-right bconsole-left-arrow" aria-hidden="true"></i>',
        formatRecordsPerPage: function (pageNumber) {
            // return '每页显示 ' + pageNumber + ' 条记录';
            return '' + pageNumber + '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            // return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
            return '共' + totalRows + '条';
        },
        queryParams: function (params) {
            return JSON.stringify({
                "pageNo": (params.offset / params.limit) + 1,
                "pageSize": params.limit
            })
        },
        queryParamsType:'limit',
        responseHandler : function (res) {
            if(res.code === 0) {
                return {
                    "total":res.data.total,
                    "rows":res.data.data
                }
            }
        },
        sidePagination: "server",
        clickToSelect: true,
        columns: cols
    });
}

var cols = [
    {
        field: 'index',
        title: '序号',
        align: 'left',
        valign: 'middle',
        formatter: function (value, row, index) {
            return index + 1;
        }
    },
    {
        field: 'projectName',
        title: '项目名称',
        align: 'center',
        valign: 'middle'
    },
    // {
    //     field: 'projectCode',
    //     title: '项目编码',
    //     align: 'center',
    //     valign: 'middle'
    // },
    {   /*项目描述可能会很长*/
        field: 'projectResume',
        title: '项目说明',
        width: '350px',
        align: 'left',
        valign: 'middle'
    },
    // {
    //     field: 'hostAddr',
    //     title: '访问地址',
    //     align: 'center',
    //     valign: 'middle'
    // }, {
    //     field: 'projectLevel',
    //     title: '项目等级',
    //     align: 'center',
    //     valign: 'middle',
    //     formatter: function (value, row, index) {
    //         if(row.projectLevel === 3){
    //             return '特别重要';
    //         } else if (row.projectLevel === 2){
    //             return '重要';
    //         } else {
    //             return '一般';
    //         }
    //     }
    //
    // },
    {
        field: 'createTime',
        title: '创建时间',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            return new Date(row.createTime).toLocaleDateString().replace(/\//g,"-");
        }
    }, {
        field: 'stateFlag',
        title: '状态',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            if (row.stateFlag === '0') {
                return '暂存';
            }
            if (row.stateFlag === '1') {
                return '开发中';
            }
            if (row.stateFlag === '2') {
                return '上线';
            }
            if (row.stateFlag === '3') {
                return '下线';
            }
            return value;
        }
    }, {
        field: 'operation',
        title: '操作',
        align: 'right',
        formatter:function(value,row,index) {
            /*访客，开发者 只能查看人员、配额；项目管理员均可操作*/
            var html =
                '<span class="icon-hover" style="cursor:no-drop">编辑</span>\n'
                + '<span class="icon-hover" style="cursor:no-drop">删除</span>\n'
                + '<span class="icon-hover" onclick="addUser(\'' + row.projectId + '\')" style="cursor:pointer">成员</span>\n'
                + '<span class="icon-hover" onclick="openQuotaPage(\'' + row.projectId + '\')" style="cursor:pointer">配额</span>';

            $.ajax({
                url: webpath+"/bcm/v1/project/getUserRole",
                type:"GET",
                async: false,
                contentType:"application/json;charset=utf-8",
                dataType:"json",
                data:{
                    "projectId": row.projectId
                },
                success: function (res) {
                    if(res.code === 0){
                        if(res.data !== null && res.data === "0") {
                            html =
                                '<span class="icon-hover" data-toggle="modal" data-target="#editProject" onclick="changeProject(\'' + row.projectId + '\')" style="cursor:pointer">编辑</span>\n'
                                + '<span class="icon-hover" onclick="deleteProject(\'' + row.projectId + '\')" style="cursor:pointer">删除</span>\n'
                                + '<span class="icon-hover" onclick="addUser(\'' + row.projectId + '\')" style="cursor:pointer">成员</span>\n'
                                + '<span class="icon-hover" onclick="openQuotaPage(\'' + row.projectId + '\')" style="cursor:pointer">配额</span>';
                        }
                    }
                }
            });
            return html;
        }
    }
];

/*
* 添加项目人员，跳转到人员管理
* */
function addUser(projectId) {
    window.location.href = "user/" + projectId;
}

//打开项目配额
function openQuotaPage(projectId){
    window.location.href = webpath + "/product/projectQuota/" + projectId;
}

/*
* 删除一个项目
* */
function deleteProject(projectId){
    $('#deleteProConfirmModal').modal('show');
    $('#deleteProConfirmModal .confirm-box-ok').on('click',function () {
        $.ajax({
            url:webpath+"/bcm/v1/project/deleteProject/",
            type:"DELETE",
            contentType: "application/json; charset=utf-8",
            dataType:"json",
            data:JSON.stringify({"projectId": projectId, "delFlag": "1"}),
            success:function(result){
                if(result.code === 0) {
                    $.message(result.message);
                    if(switchPro === 1){
                        $('#proProjectTable').bootstrapTable('refresh');
                    }else {
                        initProjectGlyph();
                    }
                    /*执行刷新门户的项目列表*/
                    window.parent.getProjectData(webpath);
                }else {
                    $.message({
                        message: result.message,
                        type: 'error'
                    });
                }
            }
        });
        $('#deleteProConfirmModal').modal('hide');
    });
}

/*
* 删除项目的所有人员
function deleteProUsers(projectId) {
    $.ajax({
        url:webpath+"/bcm/v1/project/deleteProAllUsers",
        type:"DELETE",
        contentType:"application/json; charset=utf-8",
        data:JSON.stringify({"projectId": projectId}),
        success:function (result) {
            if(result.code === 200) {
                layer.msg(result.message, {time: 1000, icon: 1});
            } else {
                layer.msg(result.message, {time: 1000, icon: 2});
            }
        }
    });
}
* */

/*
* 修改项目信息
* */
function changeProject(projectId) {
    // 跳转修改项目信息新页面
    // window.location.href = "editProject/" + projectId;

    $.ajax({
        url: webpath + "/bcm/v1/project/selectOneProject",
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType:"json",
        data: {
            "projectId": projectId
        },
        success: function (result) {
            if (result.code === 0 && result.data !== null) {
                $('#editProjectForm input[name="projectName"]').attr("value", result.data.projectName);
                $('#editProjectForm input[name="projectCode"]').attr("value", result.data.projectCode);
                $('#editProjectForm textarea[name="projectResume"]').val(result.data.projectResume);
                $('#editProjectForm input[name="hostAddr"]').attr("value", result.data.hostAddr);
                // $("#editProjectForm :radio[name='projectLevel'][value='" + result.data.projectLevel + "']").attr("checked", true);
                if(result.data.stateFlag) {
                    $("#editProjectForm :radio[name='stateFlag'][value='" + result.data.stateFlag + "']").attr("checked", true);
                }else {//取消选中
                    $("#editProjectForm :radio[name='stateFlag']").attr("checked", false);
                }
                $('#editProjectForm input[name="sortId"]').attr("value", result.data.sortId);
                $('#editProject .bconsole-modal-footer .modal-submit').unbind('click').click(function () {
                    saveProjectChange(projectId);
                });
            }
        }
    });
}

function saveProjectChange(projectId) {
    $('#editProjectForm').data('bootstrapValidator').validate();
    if ($('#editProjectForm').data('bootstrapValidator').isValid()) {
        var data = formatFormData('#editProjectForm');
        data.projectId = projectId;

        // $.ajax({
        //     url: webpath + "/bcm/v1/project/checkProjectCode",
        //     type: "GET",
        //     contentType: "application/json; charset=utf-8",
        //     dataType: "json",
        //     data: {
        //         "projectCode": data.projectCode
        //     },
        //     success: function (result) {
        //         if (result.code === 0 && result.data !== null && result.data.projectId !== projectId){
        //             $.message({
        //                 message: '项目编码已存在，请重新填写',
        //                 type: 'error'
        //             })
        //         }else {
                    $.ajax({
                        url: webpath + "/bcm/v1/project/updateProProject",
                        type: "PUT",
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        data: JSON.stringify(data),
                        success: function (result) {
                            if (result.code === 0) {
                                $.message(result.message);
                                $('#editProject').modal('hide');
                                handleResetForm('#editProjectForm');
                                // window.location.href = webpath + "/bcm/v1/project/";
                                if(switchPro === 1){
                                    $("#proProjectTable").bootstrapTable('refresh');
                                }else {
                                    initProjectGlyph();
                                }
                                /*执行刷新门户的项目列表*/
                                window.parent.getProjectData(webpath);
                            } else {
                                $.message({
                                    message: result.message,
                                    type: 'error'
                                });
                            }
                        }
                    });
                // }
            // }
        // });
    }
}

/*设置bootstrap中文提示信息*/
function formatNoMatch() {
    return '暂无匹配项目';
}
function formatLoading() {
    return '正在加载，请耐心等待......';
}