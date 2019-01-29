<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/12/17
  Time: 17:05
  Describe: 镜像构建-新建Dokerfile构建
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>新建Dokerfile构建</title>
    <meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8"/>
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/codemirror-5.36.0/lib/codemirror.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/codemirror-5.36.0/theme/dracula.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        #addDokerfileForm{
            width: 64%;
            margin: 40px 0 0 57px;
        }
        #addDokerfileForm label{
            text-align: right;
            font-size: 14px;
            color: #333333;
            font-weight: normal;
        }
        #addDokerfileForm .form-group{
            margin-bottom: 42px;
        }

        .CodeMirror-scroll{
            overflow-y: auto !important;
        }

        button.btn-bconsole,button.cancle-btn{
            width: 150px;
            height: 46px;
            border-radius: 23px;
        }
    </style>
</head>
<body class="body-bg">
<div class="bconsole-container-bg" style="padding: 21px 26px;">
    <div class="image-page-title">
        <h3>
            <span class="green-title-circle title-img-container" style="float: none">
                <img src="${pageContext.request.contextPath}/resources/img/bcm/image/docker-icon.png" alt="">
            </span>
            新建Dokerfile构建
        </h3>
    </div>

    <form class="form-horizontal" id="addDokerfileForm" enctype="multipart/form-data">
        <div class="form-group">
            <label class="col-md-2">任务名称：</label>
            <div class="col-md-10">
                <input type="text" class="form-control" name="ciName" placeholder="输入任务名称">
                <span class="input-prompt">当前Dokerfile构建的项目名称</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2">任务描述：</label>
            <div class="col-md-10">
                <textarea class="form-control" rows="3" name="ciDescription" placeholder="描述……"></textarea>
                <span class="input-prompt">给任务添加一段描述让项目更详细</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2">上传文件：</label>
            <div class="col-md-10">
                <%--<label class="file-upload-custom">
                    <input name="fileName" type="file" style="display: none" id="uploadDockerfile">
                    <span class="file-upload-icon"></span>
                </label>--%>
                    <div class="file-group">
                        <div class="file-icon"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></div>
                        <input type="file" name="dockerFile" class="form-control" id="uploadDockerfile" accept="aplication/zip">
                        <div class="file-text" style="color: #333333;">只支持单文件上传</div>
                    </div>
                <span class="input-prompt">上传的文件与Dockerfile文件在制作镜像时同处于根目录</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2">示例模板：</label>
            <div class="col-md-10">
                <select class="form-control" id="exampleTemplate">
                    <option value="">自定义</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2">Dockerfile：</label>
            <div class="col-md-10">
                <textarea id="commandEditor" class="form-control" name="dockerfileContent"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2">镜像名称：</label>
            <div class="col-md-10">
                <input type="text" class="form-control" name="imageName" placeholder="镜像名称">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2">镜像版本：</label>
            <div class="col-md-10">
                <input type="text" class="form-control" name="imageVersion" placeholder="镜像版本">
            </div>
        </div>
    </form>

    <div class="row" style="margin-top: 67px">
        <div class="col-md-12" style="text-align: center;">
            <button class="btn btn-default btn-bconsole" style="margin-right: 20px" onclick="dokerTaskSubmit()">保存</button>
            <button class="btn btn-default cancle-btn" onclick="window.location.href='${pageContext.request.contextPath}/bcm/v1/ci/dockerfileConstructs'">取消</button>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/codemirror-5.36.0/lib/codemirror.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bcm/ci/addDockerfile.js"></script>
</html>
