<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.bonc.frame.util.SystemPropertiesUtils"
         language="java" %>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common-head.jsp" %>
    <title>云平台</title>
    <link rel="stylesheet" href="<%=webpath %>/resources/css/login/login.css"/>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css"/>
</head>
<body onkeydown="keyEnter();">
<%-- <div class="login-container">
    <div class="login-bgimg"></div>
    <div class="login-content">
        <h2 class="logo">
            <img src="<%=webpath %>/resources/img/frame/logo.png" title="河南联通大数据精细化营销平台" alt="河南联通大数据精细化营销平台">
            <span><%=SystemPropertiesUtils.getSystemTitle() %></span>
        </h2>
        <div class="login-wrapper">
            <div class="form-wrap">
                <form id="loginForm" method="post"  action="<%=webpath %>/login/actionLogin" >
                    <div class="username inp-wrap">
                        <label for="userName">用户名</label>
                        <div class="txt-wrapper clearfix">
                            <span class="login-img"><i></i></span>
                            <span class="login-inp login-name"><input id="userName" type="text" name="loginId" placeholder="请输入用户名"></span>
                        </div>
                    </div>
                    <div class="password inp-wrap">
                        <label for="password">密码</label>
                        <div class="txt-wrapper clearfix">
                            <span class="login-img"><i></i></span>
                            <span class="login-inp login-pass"><input id="password" type="password" name="password" placeholder="请输入密码"></span>
                        </div>
                    </div>
                    <div class="inp-wrap rem-wrap clearfix">
                        <div class="fl clearfix">
                            <a href="javascript:;" onclick="chooseCheckbox(this);"></a>
                            <span class="rem-txt">记住密码</span>
                        </div>
                        <span class="fr tips"></span>
                    </div>
                    <div class="inp-wrap sub-wrapper">
                        <input type="button" value="登&nbsp录" id="login" onclick="loginFun();">
                    </div>
                </form>
            </div>
        </div>
        <div class="login-foot">
            <a href="javascript:;">关于我们</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:;">联系我们</a>
        </div>
    </div>
</div> --%>

<div class="signin">
    <div class="login-header">
        <div class="login-header-content">
            <ul class="login-logo">
                <li>云控制台</li>
                <%--<li>联&nbsp;通&nbsp;数&nbsp;据&nbsp;共&nbsp;享&nbsp;平&nbsp;台</li>--%>
            </ul>
            <%--<div class="login-home">
                <a href="" class="login-home">首页 &gt&gt</a>
            </div>--%>
        </div>
    </div>


    <div class="login-content">
        <div class="login-container">
            <div class="login-left">
                <ul class="login-left-container">
                    <li class="login-left-title">海量API接口服务&nbsp;全面满足业务需求</li>
                    <li class="login-left-line">—</li>
                    <li class="login-left-describe">让API服务变得简单方便、越来越多的客户可以解决了他们的问题，我们以诚信、透明、公平、高效、创新的特征赢得了用户口碑。</li>
                    <li class="login-left-img">
                        <img src="<%=webpath %>/resources/img/login/group.png" alt="">
                    </li>
                </ul>
            </div>

            <div class="login-right">
                <div class="login-right-container">
                    <div class="login-right-top">
                        <span class="login-number">登录账号</span>
                        <%--<span class="login-register"><a href="">立即注册&gt&gt</a></span>--%>
                    </div>
                    <div class="login-right-bottom">
                        <form class="form-signin" role="form" id="loginForm" method="post" action="<%=webpath %>/login/actionLogin">
                            <div class="form-signin-user"><input id="userName" type="text" name="loginId" class="form-control" placeholder="用户名"/></div>
                            <div class="form-signin-password"><input id="password" type="password" name="password" class="form-control" placeholder="密码"/></div>
                            <input class="btn btn-lg btn-danger btn-block" type="button" value="登&nbsp录" id="login" onclick="loginFun();">
                            <span class="fr tips"></span>
                            <label class="checkboxRex">
                                <input type="checkbox" value="remember-me" onclick="rememberName();"> 记住密码
                            </label>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="login-footer">
        <div class="footer-container">
            <div class="footer-top">
                <ul>
                    <li class="footer-top-title">关于我们</li>
                    <li>—</li>
                    <li><a href="#">平台简介</a></li>
                    <li><a href="#">平台动态</a></li>
                    <li><a href="#">线下活动</a></li>
                </ul>
                <ul>
                    <li class="footer-top-title">保障服务</li>
                    <li>—</li>
                    <li><a href="#">联系我们</a></li>
                    <li><a href="#">监管客服</a></li>
                    <li><a href="#">意见反馈</a></li>
                </ul>
                <ul>
                    <li class="footer-top-title">合作伙伴</li>
                    <li>—</li>
                    <li><a href="#">服务条款</a></li>
                    <li><a href="#">版权声明</a></li>
                    <li><a href="#">服务商入驻流程</a></li>
                </ul>
                <ul>
                    <li class="footer-top-title">帮助中心</li>
                    <li>—</li>
                    <li><a href="#">如何使用API</a></li>
                    <li><a href="#">如何创建服务</a></li>
                    <li><a href="#">用户热门问题</a></li>
                </ul>
                <ul class="right-ul">
                    <li>
                        <ul class="right-ul-content">
                            <li class="footer-top-title1">APIStore</li>
                            <li><a href="#">法律声明及隐私权政策</a></li>
                            <li><a href="#">联系我们</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="footer-bottom">
                <a href="">版权所有&nbsp;&nbsp;中国联通&nbsp;&nbsp;并保留所有权利&nbsp;&nbsp;ICP备案证书号：京ICP备14002*****号-1</a>
            </div>
        </div>
    </div>
</div>


<script src="<%=request.getContextPath() %>/resources/plugin/forIE/browser-fix.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/bootstrap.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/bootstrap-menu/BootstrapMenu.min.js"></script>
<!--[if lte IE 9]>
<script src="<%=request.getContextPath() %>/resources/plugin/forIE/html5shiv.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/forIE/respond.js"></script>
<![endif]-->
<script src="<%=request.getContextPath() %>/resources/plugin/layer/layer.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/frame-fix/ajax-fix.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/utils/jquery-ext.js"></script>
<script src="<%=request.getContextPath() %>/resources/plugin/utils/json.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
<script src="<%=webpath %>/resources/js/login/login.js"></script>
<script src="<%=webpath %>/resources/plugin/jquery/jquery.cookie.js"></script>
<script>
  $(function () {
    var lt = ($(document).width() - 477) / 2;
    var tt = ($(document).height() - 479) / 2;
    $(".signin").css({left: lt + "px"});
    $(".signin").animate({
      top: tt + "px",
      opacity: 'show'
    }, "slow");
  });
</script>
<script>
  var webpath = '<%=webpath%>';
  if (top.location != location) {//解决iframe中跳到登陆页面的问题
    window.parent.location.reload();
  }
  $(function () {
    if ('<%=request.getAttribute("message") %>' == 'notExist') {
      $('.tips').html("<font color='#da392e'>用户名不存在</font>");
    } else if ('<%=request.getAttribute("message")%>' == 'pwdFalse') {
      $('.tips').html("<font color='#da392e'>密码错误</font>");
    } else if ('<%=request.getAttribute("message")%>' == 'isLocked') {
      $('.tips').html("<font color='#da392e'>用户已被锁定</font>");
    } else if ('<%=request.getAttribute("message")%>' == 'isEmpty') {
      $('.tips').html("<font color='#da392e'>用户名为空</font>");
    }
  })
</script>
</body>
</html>