<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/6/20
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8"%>
<%--header--%>
<div class="header other-page-header">
    <div class="header-top">
        <div class="header-top-inner">
            <div class="header-top-logo">
                <a href="${pageContext.request.contextPath}/v1/mall/home">
                    <img src="${pageContext.request.contextPath}/resources/img/mall/h_logo.png" alt="">
                    <span>云平台</span>
                </a>
            </div>
            <div class="header-top-right">
                <span>
                    <a id="consoleUrl" href="${pageContext.request.contextPath}/platform/index">控制台</a>
                </span>
                <div style="display: inline-block" id="before-login">
                    <span>
                        <%--<a href="${pageContext.request.contextPath}/login/toLogin">登录</a>--%>
                        <a id="loginUrl" href="">登录</a>
                    </span>
                    <span>
                        <a href="" id="registerUrl">注册</a>
                    </span>
                </div>
                <div style="display: none" id="after-login">
                    <span class="login-username">
                        <i class="glyphicon glyphicon-user"></i>
                        <span id="loginUserName" style="margin-left: 0">用户名</span>
                    </span>
                    <span class="login-out">
                        <a href="">退出</a>
                    </span>
                </div>
                <%--<div class="login-username">
                    <i class="glyphicon glyphicon-user"></i>
                    <span id="loginUserName" style="margin-left: 0">用户名</span>
                </div>--%>
            </div>
        </div>
        <%--<div class="login-out">
            &lt;%&ndash;<a href="${pageContext.request.contextPath}/login/logout">退出登录</a>&ndash;%&gt;
            <a href="">退出登录</a>
        </div>--%>
    </div>
    <%--<div class="header-bottom">
        <div id="nav-all">
            <span class="glyphicon glyphicon-menu-hamburger" style="margin-right: 5px"></span>
            <span>全部导航</span>
        </div>
        <span id="nav-service">服务</span>
    </div>--%>
</div>
<%--<div id="hover-nav-all" style="display: none;z-index: -2;">
    <div class="nav first">
        <ul>
            <li class="first-li">
                <span>服务</span>
                <span class="glyphicon glyphicon-menu-right gt"></span>
                <div class="nav second">
                    <ul></ul>
                </div>
            </li>
        </ul>
    </div>
</div>
<div id="hover-nav-service" style="display: none;z-index: -2;"></div>--%>

