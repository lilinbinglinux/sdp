var headerVariable = {
    url: getUrl(),
    loginId: getCurUser()
};
$(document).ready(function () {
    /*$('.login-username,.login-out').on('mouseover', function () {
        $('.login-out').css('display', 'block');
    });
    $('.login-username,.login-out').on('mouseout', function () {
        $('.login-out').css('display', 'none');
    });*/

    $('.header-top a#consoleUrl').attr('href',headerVariable.url.portalUrl);
    $('.header-top a#loginUrl').attr('href', headerVariable.url.casUrl +'/login?service='+ headerVariable.url.projectUrl + $webpath);
    $('.header-top a#registerUrl').attr('href', headerVariable.url.securityUrl +'/userinfo!registerByEmail.action');
    $('.header-top .login-out a').attr('href', headerVariable.url.casUrl +'/logout?service='+ headerVariable.url.projectUrl + $webpath);
    $('.toRegister a').attr('href', headerVariable.url.securityUrl +'/userinfo!registerByEmail.action');

    /*var allServerStr = "",
        serverStr = "";
    $.each(productInfosByCat, function (index, item) {
        if(item.productResults) {
            // 全部导航
            allServerStr += '<li class="second-li">\n'+
                '    <div class="second-div" style="width: 100%;">\n' +
                '        <span id="' + item.productTypeId + '" class="second-service-name" title="'+item.productTypeName+'">' + item.productTypeName + '</span>\n' +
                '        <span class="glyphicon glyphicon-menu-right gt"></span>\n' +
                '    </div>\n'+
                '    <div class="nav third"><ul>';
            $.each(item.productResults, function (i, it) {
                allServerStr += '<li class="third-li">\n' +
                    '<a href="' + $webpath + '/v1/mall/productDetails?svcId=' + it.productId + '">\n' +
                    '<span title="'+it.productName+'">' + it.productName + '</span>\n' +
                    '</a>\n' +
                    '</li>';
            });
            allServerStr += '</ul></div></li>';

            // 服务导航
            serverStr += '<div class="nav-item">\n'+
                '    <h4><a href="' + $webpath + '/v1/mall/productsList?cataId=' + item.productTypeId + '" title="'+item.productTypeName+'">' + item.productTypeName + '</a></h4>\n'+
                '    <ul>';
            $.each(item.productResults, function (i, it) {
                serverStr += '<li><a href="' + $webpath + '/v1/mall/productDetails?svcId=' + it.productId + '" title="'+it.productName+'">' + it.productName + '</a></li>';
            });
            serverStr += '</ul></div>';
        }
    });
    $('#hover-nav-all .nav.second>ul').html(allServerStr);
    $('#hover-nav-service').html(serverStr);
    navMouse();

    $('.second-service-name').click(function () {
        var cataId = $(this).attr('id');
        window.location.href = $webpath + '/v1/mall/productsList?cataId=' + cataId;
    });*/

});

function getCurUser() {
    var loginId;
    $.ajax({
        type: "GET",
        url: $webpath + "/user/findCurUser",
        dataType: "json",
        async: false,
        success: function (data) {
            loginId = data.loginId;
            if (data) {
                $('#loginUserName').html(data.userName);
                /*$('.login-username').css('display', 'inline-block');*/
                $('#before-login').css('display', 'none');
                $('#after-login').css('display', 'inline-block');
            }
        }
    });
    return loginId;
}

/*
function navMouse() {
    $('#nav-service,#hover-nav-service').on('mouseover', function () {
        $('#hover-nav-service').css({
            'display': 'block',
            'z-index': 100
        });
        $('#nav-service').css({'color': '#337ab7', 'border-bottom': '2px solid #337ab7'});
    });
    $('#nav-service,#hover-nav-service').on('mouseout', function () {
        $('#hover-nav-service').css({
            'display': 'none',
            'z-index': 0
        });
        $('#nav-service').removeAttr('style');
    });

    $('#nav-all,#hover-nav-all .nav.first').on('mouseover', function () {
        $('#hover-nav-all .nav.first').css('display', 'block');
        $('#hover-nav-all').css({
            'display': 'block',
            'z-index': 100
        });
    });
    $('#nav-all').on('mouseout', function () {
        $('#hover-nav-all .nav.first,#hover-nav-all .nav.second,#hover-nav-all .nav.third').css('display', 'none');
        $('#hover-nav-all').css({
            'display': 'none',
            'z-index': 0
        });
    });
    $('#hover-nav-all .first-li').on('mouseover', function () {
        $('#hover-nav-all .nav.first,#hover-nav-all .nav.second').css('display', 'block');
    });
    $('#hover-nav-all .second-div').on('mouseover', function () {
        $('#hover-nav-all .nav.first,#hover-nav-all .nav.second').css('display', 'block');
        $(this).siblings().css('display', 'block').parent().siblings().children('.nav.third').css('display', 'none');
    });
    $('#hover-nav-all').on('mouseout', function () {
        $('#hover-nav-all .nav.first').css('display', 'none');
        $('#hover-nav-all').css({
            'display': 'none',
            'z-index': 0
        });
    });
}*/


function getUrl() {
    var casUrl, projectUrl, securityUrl, portalUrl;
    $.ajax({
        url: $webpath + '/propconfig/js.casServerUrlPrefix',
        type: "GET",
        dataType: "text",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            casUrl = data;
        }
    });

    $.ajax({
        url: $webpath + '/propconfig/js.serverName',
        type: "GET",
        dataType: "text",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            projectUrl = data;
        }
    });

    $.ajax({
        url: $webpath + '/propconfig/js.securityServiceURL',
        type: "GET",
        dataType: "text",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            securityUrl = data;
        }
    });
    $.ajax({
        url: $webpath + '/propconfig/js.portalUrl',
        type: "GET",
        dataType: "text",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            portalUrl = data;
        }
    });
    return { casUrl: casUrl, projectUrl: projectUrl, securityUrl: securityUrl, portalUrl: portalUrl };
}
