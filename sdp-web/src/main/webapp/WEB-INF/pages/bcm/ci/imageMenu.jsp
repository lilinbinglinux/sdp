<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/8
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8"%>
<style>
    .image-menu{
        width: 186px;
        background-color: #e4e8ec;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        overflow-y: auto;
        overflow-x: hidden;
        color: #000000;
        -moz-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    .image-menu .left-title{
        height: 80px;
        line-height: 80px;
        background: #d8dfe4;
        padding-left: 20px;
        font-size: 20px;
        font-weight: bold;
    }
    .image-menu .left-content{
        /*height: calc(100% - 186px);*/
    }
    .image-menu .left-content a{
        color: #000000;
        display: block;
    }
    #imageMenuFirst,.image-menu-item,.image-submenu>li{
        height: 45px;
        line-height: 45px;
        cursor: pointer;
    }
    #imageMenuFirst,.image-menu-item a{
        padding-left: 20px;
    }
    .image-submenu>li a{
        padding-left: 42px;
    }
    #imageMenuFirst:hover,.image-menu-item:hover,.image-submenu>li:hover{
        background: #f9f9f9;
    }

    /*菜单收缩按钮*/
    .nav-collapse-left, .nav-collapse-right{
        position: absolute;
        width: 15px;
        height: 60px;
        top: 50%;
        background-color: #ffffff;
        cursor: pointer;
        z-index: 100;
        color:#999999;
    }
    .nav-collapse-left i, .nav-collapse-right i{
        line-height: 60px;
    }
    .nav-collapse-left{
        left: 0;
    }
    .nav-collapse-right{
        left: 171px;
    }
</style>
<div class="image-menu">
    <div class="left-title">镜像</div>
    <ul class="left-content">
        <li>
            <div id="imageMenuFirst">
                镜像构建
                <span class="glyphicon glyphicon-triangle-bottom" style="padding-right: 25px;float: right;line-height: 45px;color:#999999"></span>
            </div>
            <ul class="image-submenu" style="display: none">
                <li class="image-submenu01">
                    <a href="${pageContext.request.contextPath}/bcm/v1/ci/codeConstructs">代码构建</a>
                </li>
                <li class="image-submenu02">
                    <a href="${pageContext.request.contextPath}/bcm/v1/ci/dockerfileConstructs">Dockerfile构建</a>
                </li>
            </ul>
        </li>
        <li class="image-menu-item image-menu-item01"><a href="${pageContext.request.contextPath}/bcm/v1/ci/codeConstructs">公共镜像</a></li>
        <li class="image-menu-item image-menu-item02"><a href="${pageContext.request.contextPath}/bcm/v1/ci/codeConstructs">我的镜像</a></li>
        <li class="image-menu-item image-menu-item03"><a href="${pageContext.request.contextPath}/bcm/v1/ci/codeConstructs">镜像部署</a></li>
    </ul>
</div>
<div class="nav-collapse-left" style="display: none"><i class="glyphicon glyphicon-triangle-right"></i></div>
<div class="nav-collapse-right"><i class="glyphicon glyphicon-triangle-left"></i></div>