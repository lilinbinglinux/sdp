<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/19
  Time: 20:06
  Describe: 公共镜像
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>公共镜像</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .card-child{
            height: 245px;
        }
        .card-child-inner{
            padding: 30px;
            display: block;
        }

        .public-item-top {
            height: 66px;
        }

        .public-item-top img {
            width: 48px;
            height: 48px;
            background-color: #ffffff;
            border: solid 1px #cfcfcf;
            border-radius: 50%;
            margin-right: 14px;
        }
        .public-item-top span{
            font-size: 20px;
            color: #000000;
            vertical-align: middle;
        }

        .public-item-bottom {
            text-align: left;
            height: 99px;
        }

        .public-item-bottom span {
            font-size: 14px;
            color: #999999;
            display: block;
        }
        .public-item-bottom .public-tag{
            margin-top: 20px;
        }
        .public-item-bottom .public-tag:before{
            content: "\f02b";
            font-family: FontAwesome;
            padding-right: 11px;
        }
        .public-item-bottom .public-file-size{
            margin-top: 9px;
        }
        .public-item-bottom .public-file-size:before{
            content: "\f02f";
            font-family: FontAwesome;
            padding-right: 11px;
        }

        .public-item-bottom p {
            font-size: 14px;
            color: #333333;
            width: 100%;
            -webkit-line-clamp: 2;
            margin: 0 auto;
        }

        @media screen and (min-width: 1360px) {
            .card-child{
                width: 25%;
            }
        }
        @media (max-width: 1360px) and (min-width: 1046px){
            .card-child{
                width: 33.333333333333336%;
            }
        }
        @media (max-width: 1046px) and (min-width: 720px){
            .card-child{
                width: 50%;
            }
        }
        @media screen and (max-width: 720px) {
            .card-child{
                width: 100%;
            }
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 21px 26px;">
    <div class="image-page-title">
        <h3>公共镜像</h3>
    </div>
    <div class="row" style="margin-top: 35px">
        <div class="col-md-12">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
            </button>
            <form class="form-inline bconsole-fr">
                <div class="form-group bconsole-form-group">
                    <input type="text" class="form-control bconsole-form-control bconsole-width-200"
                           placeholder="输入关键字搜索" id="publicSearch">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </div>
            </form>
        </div>
    </div>

    <div class="card-parent-wrap" style="margin-top: 15px">
        <ul id="publicList" class="card-parent">
            <li class="card-child">
                <a class="card-child-inner" href="${pageContext.request.contextPath}/bcm/v1/image/public/detailPage">
                    <div class="public-item-top">
                        <img src="" alt="" style="">
                        <span>Tomcat</span>
                    </div>
                    <div class="public-item-bottom">
                        <p class="mult_line_ellipsis">Tomcat服务器是一个免费的开放源代码的Web应用服务器，属于轻量级应用
                            Tomcat服务器是一个免费的开放源代码的Web应用服务器，属于轻量级应用</p>
                        <span class="public-tag">Dockerhub</span>
                        <span class="public-file-size">400M</span>
                    </div>
                </a>
            </li>
            <li class="card-child">
                <div class="card-child-inner">

                </div>
            </li>
            <li class="card-child">
                <div class="card-child-inner">

                </div>
            </li>
            <li class="card-child">
                <div class="card-child-inner">

                </div>
            </li>
        </ul>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {});
</script>
</html>

