/*
* 定义全局变量
* */
var tenantId ='';
var projectId ='';
var allUser = [];
var proUsers = [];
var userNumber = '';

$(document).ready(function(){
    var url = window.location.href;
    var array = url.split("/");
    projectId = array[array.length - 1];

    $.ajax({
        url:webpath+"/bcm/v1/project/selectOneProject",
        type:"GET",
        contentType:"application/json;charset=utf-8",
        dataType:"json",
        data:{
            "projectId": projectId
        },
        success:function (result) {
            if(result.code === 0) {
                tenantId = result.data.tenantId;
                /*
                 * 初始化人员
                 * */
                initUsers();
            }
        }
    });
});

/*
* 添加项目成员
* */
function saveProUser() {
    var userId = document.getElementById("searchUser").value;
    var projectRoleId = document.getElementById("searchRole").value;
    if (userId && projectRoleId) {
        var proUser = formatFormData('#addProUser');
        proUser.projectId = projectId;
        $.ajax({
            url:webpath+"/bcm/v1/project/saveProUser",
            type:"POST",
            contentType:"application/json; charset=utf-8",
            data:JSON.stringify(proUser),
            success:function (result) {
                console.log(result);
                if (result.code === 0) {
                    layer.msg(result.message, {time: 1000, icon: 1});
                    proUserNum(++userNumber);
                    // 添加当前用户为该项目的管理员(user_id 对应 用户表的 loginId)
                    var user = {"projectRoleId": "", "roleName": "", "projectRole": ""};
                    if (proUser.projectRoleId === '0') {
                        user.projectRole = "项目管理员";
                    }
                    if (proUser.projectRoleId === '1') {
                        user.projectRole = "访客";
                    }
                    if (proUser.projectRoleId === '2') {
                        user.projectRole = "开发者";
                    }
                    user.projectRoleId = proUser.userId;
                    user.roleName = getProUserName(proUser.userId);
                    proUsers.push(user);
                    createUsersList(proUsers);

                } else {
                    layer.msg(result.message, {time: 1000, icon: 2});
                }
                document.getElementById("addProUser").reset();
            }
        })
    }
}

/*
* 获取项目人员名字
* */
function getProUserName(userId) {
    var targetUser = allUser.filter(checkName);
    function checkName(u) {
        return u.loginId === userId;
    }
    return targetUser[0].userName;
}


/*
* 获取项目成员
* */
function getProjectUsers(projectId) {
    $.ajax({
        url:webpath+"/bcm/v1/project/selectAllUsers",
        type:"GET",
        contentType:"application/json; charset=utf-8",
        data: {
            "projectId": projectId
        },
        success:function (result) {
            if(result.code === 0) {

                userNumber = result.data.length;
                proUserNum(userNumber);

                if (result.data.length > 0) {
                    $.each(result.data, function (i, v) {
                        var user = {"projectRoleId": "", "roleName": "", "projectRole": ""};

                        function filterProUser(one) {
                            return one.loginId === result.data[i].userId;
                        }

                        var u = allUser.filter(filterProUser);
                        if (u.length === 1) {
                            if (result.data[i].projectRoleId === '0') {
                                user.projectRole = "项目管理员";
                            }
                            if (result.data[i].projectRoleId === '1') {
                                user.projectRole = "访客";
                            }
                            if (result.data[i].projectRoleId === '2') {
                                user.projectRole = '开发者';
                            }
                            user.roleName = u[0].userName;
                            user.projectRoleId = u[0].loginId;
                            proUsers.push(user);
                        }
                    });
                    createUsersList(proUsers);
                }
            }
        }
    })
}

/*
* 生成项目人员list内容
* */
function createUsersList(userList) {
    var list_obj = '';
    // 测试 loginUser = 'admin'
    // loginUser = 'admin';
    function checkAdmin(u) {
        return u.projectRoleId === loginUser && u.projectRole === '项目管理员';
    }
    var admin = userList.filter(checkAdmin);
    if (admin.length > 0) {
        // 当前用户是管理员
        for (var m = 0; m < userList.length; m++) {
            if (userList[m].projectRoleId === loginUser) {
                list_obj += '<li class="list-group-item" style="height: 45px;">\n'
                    + '<span class="pull-left">' + userList[m].roleName + '</span>\n'
                    + '<span class="badge" style="float:left;">我</span>\n'
                    + '<span class="pull-right">' + userList[m].projectRole + '</span>\n'
                    + '</li>\n';
            } else {
                list_obj += '<li class="list-group-item" style="height: 45px;">\n'
                    + '<span class="pull-left">' + userList[m].roleName + '</span>\n'
                    + '<span class="pull-right">' + userList[m].projectRole + '</span>\n'
                    + '<span class="badge" title="移除人员" onclick="deleteProUser(\'' + userList[m].projectRoleId + '\')" style="float: right;cursor:pointer;background-color: red;">-</span>\n'
                    + '</li>\n';
            }
        }
    } else {
        // 当前用户是访客或者开发者
        document.getElementById("addUser").disabled = true;
        for (var m = 0; m < userList.length; m++) {
            if (userList[m].projectRoleId === loginUser) {
                list_obj += '<li class="list-group-item" style="height: 45px;">\n'
                    + '<span class="pull-left">' + userList[m].roleName + '</span>\n'
                    + '<span class="badge" style="float:left;">我</span>\n'
                    + '<span class="pull-right">' + userList[m].projectRole + '</span>\n'
                    + '</li>\n';
            } else {
                list_obj += '<li class="list-group-item" style="height: 45px;">\n'
                    + '<span class="pull-left">' + userList[m].roleName + '</span>\n'
                    + '<span class="pull-right">' + userList[m].projectRole + '</span>\n'
                    + '</li>\n';
            }
        }
    }
    document.getElementById("usersList").innerHTML = list_obj;
}

/*
* 删除项目人员
* */
function deleteProUser(userId) {
    layer.confirm('确认移除该人员', {
            btn: ['是','否']
        }, function (index) {
            $.ajax({
                url:webpath+"/bcm/v1/project/deleteProUser",
                type:"DELETE",
                contentType:"application/json; charset=utf-8",
                data:JSON.stringify({"userId": userId}),
                success:function (result) {
                    if(result.code === 0) {
                        layer.msg(result.message, {time: 1000, icon: 1});
                        proUserNum(--userNumber);
                        function popUser(u) {
                            return u.projectRoleId !== userId;
                        }
                        proUsers = proUsers.filter(popUser);
                        createUsersList(proUsers);
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 2});
                    }
                }
            });
        }
    )
}

/*
* 计算项目人员个数
* */
function proUserNum(num) {
    document.getElementById("proUserNum").innerHTML = "项目人员 " + '<span class="badge">' + num + '</span>';
}

/*
* 搜索用户触发事件
* */
function searchingUsers() {
    if(allUser!==null) {
        console.log(allUser);
        var module = document.getElementById("searchUser");
        var opt = document.createElement('option');
        opt.appendChild(document.createTextNode(""));
        opt.setAttribute("value", "");
        module.appendChild(opt);

        $.each(allUser, function (i, v) {
            var module = document.getElementById("searchUser");
            var opt = document.createElement('option');
            opt.appendChild(document.createTextNode(v.userName + " " + v.loginId));
            opt.setAttribute("value", v.loginId);
            module.appendChild(opt);
        });

        $("#searchUser").comboSelect();
    }
}

/*
* 获取特定租户下的所有用户
* */
function initUsers() {
    var loading = layer.load(2, {time: 10*1000});
    $.ajax({
        url:webpath + '/propconfig/js.securityServiceURL',
        type: "GET",
        dataType: "text",
        contentType: 'application/json',
        success: function (data) {
            $.ajax({
                url:data+"/rest/user/list",
                type:"GET",
                contentType:"application/json; charset=utf-8",
                dataType:"json",
                data:{
                    "tenant_id":tenantId
                },
                success:function (data) {
                    if(data != null) {
                        allUser = data;
                        /*初始化项目人员*/
                        getProjectUsers(projectId);
                        layer.close(loading);
                    }
                }
            })
        }
    });

    // 直接调用接口
    // $.ajax({
    //     url:"http://bconsole.sdp.com/security/rest/user/list",
    //     type:"GET",
    //     contentType:"application/json; charset=utf-8",
    //     data:{
    //         "tenant_id":tenantId
    //     },
    //     success:function (data) {
    //         if(data != null) {
    //             allUser = data;
    //             /*初始化项目人员*/
    //             getProjectUsers(projectId);
    //             layer.close(loading);
    //         }
    //     }
    // });
}

/*
* 搜索用户角色触发事件
* */
function searchingRole() {
    var role_list = [{roleId:"1", roleName: "访客"},{roleId: "2",roleName:"开发者"},{roleId:"0",roleName :"项目管理员"}];
    var role_obj = document.getElementById("searchRole");
    var opt = document.createElement('option');
    opt.appendChild(document.createTextNode(""));
    opt.setAttribute("value", "");
    role_obj.appendChild(opt);

    $.each(role_list, function (i, v) {
        var module = document.getElementById("searchRole");
        var opt = document.createElement('option');
        opt.appendChild(document.createTextNode(v.roleName));
        opt.setAttribute("value", v.roleId);
        module.appendChild(opt);
    });

    $("#searchRole").comboSelect();
}