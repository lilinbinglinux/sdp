<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2019/1/15
  Time: 16:03
  Describe: Dokerfile构建-构建任务
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dokerfile构建-构建任务</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bcm/ci/image-common.css">
    <style>
        .image-step-item{
            width: 76%;
        }
    </style>
</head>
<body class="body-bg">
<div>
    <div class="image-content-left bconsole-container-bg">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#constructsTasks" aria-controls="constructsTasks" role="tab" data-toggle="tab">构建任务</a></li>
            <li role="presentation"><a href="#constructsHistory" aria-controls="constructsHistory" role="tab" data-toggle="tab">构建历史</a></li>

            <div class="btn-group dropdown-form-select" id="moreActions">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    更多 <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" style="height: 68px;">
                    <li class="actions-item1"><a href="javascript:;" onclick="toEditPage()">配置</a></li>
                    <li class="actions-item2"><a href="javascript:;" onclick="$('#deleteConfirmModal').modal('show');">删除</a></li>
                </ul>
            </div>

            <button class="btn btn-default btn-bconsole start-task-btn" style="width: 110px;border-radius: 18px;float: right" onclick="toStartTask()">开始构建</button>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="constructsTasks" style="padding-top: 16px">
                <div class="image-steps">
                    <div class="image-step-item image-step-active image-step-start">
                        <div class="image-step-top">
                            <div class="image-step-icon" data-index="1"></div>
                            <div class="image-step-line"></div>
                        </div>
                        <div class="image-step-title" style="margin-left: -15px;">制作镜像</div>
                    </div>
                    <div class="image-step-end">
                        <div class="image-step-top">
                            <div class="image-step-icon" data-index="2"></div>
                        </div>
                        <div class="image-step-title" style="margin-left: -38px;">推送至我的镜像</div>
                    </div>
                </div>

                <%--日志--%>
                <div id="logWindow">
                    <div class="row log-nav">
                        <div class="col-md-12" style="text-align: right;">
                            <img id="download" src="${pageContext.request.contextPath}/resources/img/bcm/image/download.png" alt="" title="下载" style="margin-right: 10px">
                            <img id="fullScreen" src="${pageContext.request.contextPath}/resources/img/bcm/image/full_screen.png" alt="" title="全屏">
                        </div>
                    </div>

                    <div class="log-container">
                        <pre>
                            <span class="print-log-span"></span>
                        </pre>
                    </div>
                </div>

            </div>

            <div role="tabpanel" class="tab-pane" id="constructsHistory" style="padding-top: 16px">
                <div class="table-responsive">
                    <table class="table table-hover" id="constructsHistoryTable"></table>
                </div>
            </div>
        </div>
    </div>

    <%--基本信息--%>
    <div class="image-content-right bconsole-container-bg">
        <div class="right-basic-header">
            <span class="green-title-circle title-img-container" style="float: none;width: 49px;height: 49px;margin-right: 0;">
                <img src="${pageContext.request.contextPath}/resources/img/common/basic-icon.png" alt="">
            </span>
            <p>基本信息</p>
        </div>
        <ul class="right-basic-info"></ul>
    </div>

    <%--删除任务确认框--%>
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width: 250px;">
            <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                <div class="modal-header bconsole-confirm-header">
                    <h4 class="modal-title">删除信息</h4>
                </div>
                <div class="modal-body bconsole-confirm-body">
                    <p>请问是否删除该任务？</p>
                </div>
                <div class="modal-footer bconsole-confirm-footer">
                    <div class="confirm-box-ok">确定</div>
                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>

    <%--删除构建历史确认框--%>
    <div class="modal fade" id="deleteHistoryConfirm" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width: 250px;">
            <div class="modal-content bconsole-confirm-box" style="width: 250px;">
                <div class="modal-header bconsole-confirm-header">
                    <h4 class="modal-title">删除信息</h4>
                </div>
                <div class="modal-body bconsole-confirm-body">
                    <p>请问是否删除该构建历史？</p>
                    <input type="hidden" name="deleteId">
                </div>
                <div class="modal-footer bconsole-confirm-footer">
                    <div class="confirm-box-ok" onclick="deleteHistoryTask()">确定</div>
                    <div class="confirm-box-cancel" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>

    <%--构建历史日志显示modal--%>
    <div class="modal fade" id="logHistoryModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:643px;">
            <div class="modal-content" style="width:643px;">
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close">
                    <a><img src="${pageContext.request.contextPath}/resources/img/common/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/bcm/image/my-image.png" alt="">
                    </span>
                    <span class="title">日志</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 24px;padding-right: 24px;">
                    <pre style="max-height: 360px;">
                        <span></span>
                    </pre>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bcm/ci/dockerfileTask.js"></script>
<script>
    /**
     * 下载日志
    * */
    $('#logWindow #download').on('click',function () {
        if(isIE()) {
            saveas($('#logWindow pre>span.print-log-span').text());
        }else{
            exportRaw('log.txt', $('#logWindow pre>span.print-log-span').text());
        }
    });

    function fakeClick(obj) {
        var ev = document.createEvent("MouseEvents");
        ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        obj.dispatchEvent(ev);
    }

    function exportRaw(name, data) {
        var urlObject = window.URL || window.webkitURL || window;
        var export_blob = new Blob([data]);
        var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
        save_link.href = urlObject.createObjectURL(export_blob);
        save_link.download = name;
        fakeClick(save_link);
    }

    function saveas(data) {
        var winSave = window.open();
        winSave.document.open("text/html","utf-8");
        winSave.document.write(data);
        winSave.document.execCommand('SaveAs',true,'log.txt','.txt');
        winSave.close();
    }

    // 判断是否为IE浏览器
    function isIE() {
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            return true;
        else
            return false;
    }

    // 下载构建历史的日志
    function downloadLog(data, escapeflag) {
        if (escapeflag) {
            data = unescape(data);
        }
        var item = $.parseJSON(data);
        if(isIE()) {
            saveas(item.logPrint);
        }else{
            exportRaw('log.txt', item.logPrint);
        }
    }
</script>
</html>

