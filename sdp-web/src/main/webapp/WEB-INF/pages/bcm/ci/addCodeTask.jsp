<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/12
  Time: 10:20
  Describe: 镜像构建-新建代码任务
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>新建代码任务</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/codemirror-5.36.0/lib/codemirror.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/codemirror-5.36.0/theme/dracula.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .card-child{
            height: 303px;
            width: 16.666666667%;
        }

        .language-item-top {
            position: relative;
            height: 131px;
        }

        .language-item-top {
            position: relative;
            height: 131px;
        }

        .language-item-top img {
            width: 85px;
            height: 85px;
            /*background-color: #ffffff;
            border: solid 1px #cfcfcf;
            border-radius: 50%;*/
            position: absolute;
            left: 50%;
            margin-left: -42.5px;
            top: 50%;
            margin-top: -42.5px;
        }

        .language-item-bottom {
            text-align: center;
            position: relative;
            height: 152px;
        }

        .language-item-bottom span {
            font-size: 18px;
            color: #000000;
            width: 68%;
            display: inline-block;
        }

        .language-item-bottom p {
            font-size: 14px;
            color: #999999;
            width: 68%;
            padding-top: 6px;
            -webkit-line-clamp: 2;
            margin: 0 auto;
        }

        .language-item-bottom button {
            position: absolute;
            left: 50%;
            margin-left: -88px;
            bottom: 24px;
            width: 176px;
            height: 40px;
            border-radius: 20px;
            border: solid 1px #d8d8d8;
            color: #333333;
        }

        #addCodeForm {
            /*width: 64%;*/
            width: 80%;
            margin: 48px 0 0 57px;
        }

        #addCodeForm label {
            text-align: right;
            font-size: 14px;
            color: #333333;
            font-weight: normal;
        }

        #addCodeForm .form-group {
            margin-bottom: 42px;
        }

        .page-info-header .config-toggle-operate {
            float: right;
            cursor: pointer;
        }

        .page-info-header .config-toggle-operate:before {
            content: '\f106';
            display: inline-block;
            font-family: FontAwesome;
            margin-right: 3px;
        }

        .page-info-header .config-toggle-operate.config-hide-operate:before {
            content: '\f107';
        }

        .CodeMirror-scroll {
            overflow-y: auto !important;
        }

        button.btn-bconsole,button.cancle-btn{
            width: 150px;
            height: 46px;
            border-radius: 23px;
        }

        @media screen and (min-width: 1400px) {
            .card-child{
                width: 16.666666667%;
            }
        }
        @media (max-width: 1400px) and (min-width: 1200px){
            .card-child{
                width: 20%;
            }
        }
        @media (max-width: 1200px) and (min-width: 1000px){
            .card-child{
                width: 25%;
            }
        }
        @media (max-width: 1000px) and (min-width: 760px){
            .card-child{
                width: 33.333333333333336%;
            }
        }
        @media (max-width: 760px) and (min-width: 520px){
            .card-child{
                width: 50%;
            }
        }
        @media screen and (max-width: 520px) {
            .card-child{
                width: 100%;
            }
        }
    </style>
</head>
<body class="body-bg">
<div>
    <%--选择语言--%>
    <div class="bconsole-container-bg" id="addTaskFirst" style="padding: 21px 26px;">
        <div class="image-page-title">
            <h3>
                新建任务
            </h3>
        </div>
        <div class="row" style="margin-top: 35px">
            <div class="col-md-12">
                <button type="button" class="btn btn-default btn-no-radius btn-refresh" title="刷新">
                    <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                </button>
                <form class="form-inline bconsole-fr">
                    <div class="form-group bconsole-form-group">
                        <input type="text" class="form-control bconsole-form-control bconsole-width-200"
                               placeholder="输入关键字搜索" id="languageSearch">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </div>
                </form>
            </div>
        </div>

        <div class="card-parent-wrap" style="margin-top: 30px">
            <ul class="card-parent" id="languageList">
                <li class="card-child">
                    <div class="card-child-inner">
                        <div class="language-item-top">
                            <img src="${pageContext.request.contextPath}/resources/img/bcm/image/lang_icon1.png" alt="" style="">
                        </div>
                        <div class="language-item-bottom">
                            <span class="ellipsis">Java语言</span>
                            <p class="mult_line_ellipsis">使用Apache Maven构建Java项目</p>
                            <button class="btn btn-default image-btn" data-lang="java" data-describe="Java语言">我要构建</button>
                        </div>
                    </div>
                </li>
                <li class="card-child">
                    <div class="card-child-inner">
                        <div class="language-item-top">
                            <img src="${pageContext.request.contextPath}/resources/img/bcm/image/lang_icon2.png" alt="" style="">
                        </div>
                        <div class="language-item-bottom">
                            <span class="ellipsis">Go语言</span>
                            <p class="mult_line_ellipsis">使用Go语言环境构建</p>
                            <button class="btn btn-default image-btn" data-lang="go" data-describe="Go语言">我要构建</button>
                        </div>
                    </div>
                </li>
                <li class="card-child">
                    <div class="card-child-inner">
                        <div class="language-item-top">
                            <img src="${pageContext.request.contextPath}/resources/img/bcm/image/lang_icon3.png" alt="" style="">
                        </div>
                        <div class="language-item-bottom">
                            <span class="ellipsis">Npm构建</span>
                            <p class="mult_line_ellipsis">使用Npm构建</p>
                            <button class="btn btn-default image-btn" data-lang="npm" data-describe="Npm">我要构建</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>

    </div>

    <%--配置--%>
    <div class="bconsole-container-bg" id="addTaskSecond" style="padding: 21px 26px;display: none">
        <div class="image-page-title">
            <h3>
                <span class="green-title-circle title-img-container" style="float: none">
                    <img src="${pageContext.request.contextPath}/resources/img/bcm/image/lang_icon4.png" alt="">
                </span>
                <span class="lang"></span>构建项目
            </h3>
        </div>

        <form class="form-horizontal" id="addCodeForm">
            <input type="hidden" name="lang">
            <div class="page-info-header">
                <span>|</span>第01步：基本配置
                <div class="config-toggle-operate">收起配置</div>
            </div>
            <div style="margin-top: 50px">
                <div class="form-group">
                    <label class="col-md-2">任务名称：</label>
                    <div class="col-md-7">
                        <input type="text" class="form-control" name="ciName" placeholder="输入任务名称">
                        <span class="input-prompt">当前<span class="lang"></span>构建的项目名称</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">任务描述：</label>
                    <div class="col-md-7">
                        <textarea class="form-control" rows="3" name="ciDescription" placeholder="描述……"></textarea>
                        <span class="input-prompt">给任务添加一段描述让项目更详细</span>
                    </div>
                </div>
            </div>

            <div class="page-info-header" style="margin-top: 40px">
                <span>|</span>第02步：源代码
                <div class="config-toggle-operate">收起配置</div>
            </div>
            <div style="margin-top: 50px">
                <div class="form-group">
                    <label class="col-md-2">选择代码库：</label>
                    <div class="col-md-7">
                        <select class="form-control" name="codeControlType">
                            <option value="0">none</option>
                            <option value="1">git</option>
                            <option value="2">svn</option>
                        </select>
                        <span class="input-prompt">选择之前的代码库进行配置</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">选择用户：</label>
                    <div class="col-md-7">
                        <select class="form-control" name="">
                            <option value="">选择用户</option>
                        </select>
                        <span class="input-prompt">选择哪个用户</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">选择仓库：</label>
                    <div class="col-md-7">
                        <select class="form-control" name="codeUrl">
                            <option value="">选择仓库</option>
                        </select>
                        <span class="input-prompt">选择你需要的仓库</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">选择分支/tag：</label>
                    <div class="col-md-7">
                        <select class="form-control" name="codeBranch">
                            <option value="">选择分支/tag</option>
                        </select>
                        <span class="input-prompt">选择一个分支</span>
                    </div>
                </div>
            </div>

            <div class="page-info-header" style="margin-top: 40px">
                <span>|</span>第03步：Maven构建
                <div class="config-toggle-operate">收起配置</div>
            </div>
            <div style="margin-top: 50px">
                <div class="form-group">
                    <label class="col-md-2">版本：</label>
                    <div class="col-md-7">
                        <select class="form-control" name="langVersion">
                            <option value="">JDK-1.7</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">命令：</label>
                    <div class="col-md-7">
                        <textarea id="commandEditor" class="form-control" name="buildcmd"></textarea>
                    </div>
                </div>
            </div>

            <div class="page-info-header" style="margin-top: 40px">
                <span>|</span>第04步：制作镜像并推送至仓库
                <div class="config-toggle-operate">收起配置</div>
            </div>
            <div style="margin-top: 50px">
                <div class="form-group">
                    <label class="col-md-2">镜像名称：</label>
                    <div class="col-md-7">
                        <input type="text" class="form-control" name="imageName" placeholder="镜像名称">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">镜像版本：</label>
                    <div class="col-md-7">
                        <input type="text" class="form-control" name="imageVersion" placeholder="镜像版本">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">Dockerfile路径：</label>
                    <div class="col-md-7">
                        <input type="text" class="form-control" value="./Dockerfile" name="dockerfilePath" placeholder="Dockerfile路径">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">自动构建：</label>
                    <div class="col-md-7">
                        <div class="form-radio-group">
                            <input type="radio" name="" value="">
                            <label></label>
                            <span>提交代码到分支</span>
                        </div>
                        <div class="form-radio-group">
                            <input type="radio" name="" value="">
                            <label ></label>
                            <span>新建tag</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">自动生成版本前缀：</label>
                    <div class="col-md-7">
                        <input type="text" class="form-control" name="imageVersionPre">
                        <span class="input-prompt">自动构建在版本前缀后从1起始增加整数，不填写则覆盖当前版本</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2">执行计划：</label>
                    <div class="col-md-7">
                        <%--<label class="radio-inline">
                            <input type="radio" name="cron" value="">手动执行
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="cron" value="">每日执行
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="cron" value="">每周执行
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="cron" value="">每月执行
                        </label>--%>

                        <div class="form-radio-group">
                            <input type="radio" name="cron" value="10">
                            <label></label>
                            <span>手动执行</span>
                        </div>
                        <div class="form-radio-group">
                            <input type="radio" name="cron" value="20">
                            <label ></label>
                            <span>每日执行</span>
                        </div>
                        <div class="form-radio-group">
                            <input type="radio" name="cron" value="30">
                            <label></label>
                            <span>每周执行</span>
                        </div>
                        <div class="form-radio-group">
                            <input type="radio" name="cron" value="40">
                            <label></label>
                            <span>每月执行</span>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <div class="row" style="margin-top: 67px">
            <div class="col-md-12" style="text-align: center;">
                <button class="btn btn-default btn-bconsole" style="margin-right: 20px" onclick="codeTaskSubmit()">保存</button>
                <button class="btn btn-default cancle-btn"
                        onclick="window.location.href='${pageContext.request.contextPath}/bcm/v1/ci/codeConstructs'">取消
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/codemirror-5.36.0/lib/codemirror.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bcm/ci/addCodeTask.js"></script>
</html>
