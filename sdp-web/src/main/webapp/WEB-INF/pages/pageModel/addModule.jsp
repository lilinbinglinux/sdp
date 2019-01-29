<%--
  Created by IntelliJ IDEA.
  User: wangxiang
  Date: 2018/4/23
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String moduleTypeId = (String) request.getAttribute("moduleTypeId");
    String moduleId = (String) request.getAttribute("moduleTypeId");
%>
<html>
<head>
    <title>新增组件</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/plugin/zTree_v3/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%= path %>/resources/plugin/codemirror-5.36.0/lib/codemirror.css">
    <link rel="stylesheet" href="<%= path %>/resources/plugin/codemirror-5.36.0/theme/dracula.css">
    <link rel="stylesheet" href="<%= path %>/resources/plugin/codemirror-5.36.0/addon/fold/foldgutter.css">
    <link rel="stylesheet" href="<%= path %>/resources/plugin/codemirror-5.36.0/theme/eclipse.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/pageModel/addeditmodule.css">
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%= path %>/resources/plugin/codemirror-5.36.0/lib/codemirror.js"></script>
    <script type="text/javascript" src="<%= path %>/resources/js/pageModel/codeMirror-format.js"></script>
    <script type="text/javascript" src="<%= path %>/resources/js/pageModel/codeMirror-cs.js"></script>
    <script type="text/javascript" src="<%= path %>/resources/js/pageModel/codeMirror-xml.js"></script>
    <script type="text/javascript" src="<%= path %>/resources/js/pageModel/codeMirror-js.js"></script>
    <script type="text/javascript" src="<%= path %>/resources/js/pageModel/codeMirror-html.js"></script>
    <script>
      var path = "<%=path%>";
      var moduleTypeId = "<%=moduleTypeId%>"
    </script>
</head>

<body style="overflow-y: hidden">
<div class="container-fluid" style="height: 100%">
    <div class="row" style="height: 100%">
        <div class="col-md-6 col-xs-6 scroll" style="height: 100%; overflow-y: auto; overflow-x: auto;">
            <ul id="previewTabs" class="nav nav-tabs">
                <li class="active"><a href="#html" data-toggle="tab">HTML</a></li>
                <li><a href="#js" data-toggle="tab">JS</a></li>
                <li><a href="#cs" data-toggle="tab">CS</a></li>
                <li><a onclick="javascript:history.back(-1);">取消</a></li>
                <li><a href="#" id="submitBTN" onclick="getCode()">运行</a></li>
                <li><a href="#" onclick="saveCom()">保存</a></li>
                <li><a href="<%=path%>/v1/pageResType/resClassify">上传资源</a></li>
                <li><a href="javascript:void(0);" onclick="openAddResModal()">选择资源</a></li>
            </ul>
            <div class="tab-content" style="height: calc(100vh - 42px);">
                <div class="tab-pane fade in active" style="height: 100%;" id="html">
                    <textarea id="htmlEditor" name="htmlEditor"></textarea>
                </div>
                <div class="tab-pane fade" style="height: 100%;" id="js">
                    <textarea id="jsEditor" name="jsEditor"></textarea>
                </div>
                <div class="tab-pane fade" style="height: 100%;" id="cs">
                    <textarea id="csEditor" name="csEditor"></textarea>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-xs-6" style="height: 100%; padding-right: 0; padding-left: 0; overflow-y: hidden">
            <iframe id="showIframe" src="<%=path%>/v1/pageModule/moduleShow" frameborder="0" width="100%" height="100%"></iframe>
        </div>
    </div>
</div>

<!-- 保存时 添加组件相关信息 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    新增组件资源
                </h4>
            </div>
            <div class="modal-body">
                <form id="addNodeForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="moduleName" class="col-sm-2 control-label">组件名称</label>
                        <div class="col-sm-10">
                            <input type="text" id="moduleName" name="moduleName" class="form-control">
                            <div style="text-align: right;"><span id="moduleNameError" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="moduleTip" class="col-sm-2 control-label">组件说明</label>
                        <div class="col-sm-10">
                            <input type="text" id="moduleTip" name="moduleTip" class="form-control">
                            <div style="text-align: right;"><span id="moduleTipError" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pubFlag" class="col-sm-2 control-label">公共类型</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="pubFlag" id="pubFlag0" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="pubFlag" id="pubFlag" checked value="0"> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sortId" class="col-sm-2 control-label">排序</label>
                        <div class="col-sm-10">
                            <input type="text" id="sortId" name="sortId" class="form-control" placeholder="请输入数字序号">
                            <div style="text-align: right;"><span id="sortIdError" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">依赖资源</label>
                        <div class="col-sm-10">
                            <div id="saveRes"></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="addNodeBtn" class="btn btn-primary">
                    保存
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="myChooseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myChooseModal1">
                    选择依赖资源&nbsp;&nbsp;<span style="font-size: 12px; color: red;">(*注意选择资源的顺序！)</span>
                </h4>
            </div>
            <div class="modal-body row" style="margin: 0;">
                <div class="col-md-6 col-xs-6">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
                <div class="col-md-6 col-xs-6">
                    <div class="choosedResList">
                        <span style="font-weight: bold;">已选资源:</span>
                        <div id="choosedListContent"></div>
                    </div>
                    <br/>
                    <div class="chooseResList">
                        <span style="font-weight: bold;">当前选择:</span>
                        <div id="chooseListContent">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer row" style="margin: 0;">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <button type="button" id="addResBtn" onclick="addResToShowPage()" class="btn btn-primary">
                    确定
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script type="text/javascript" src="<%=path%>/resources/js/pageModel/addmodule.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/pageModel/codeMirror-setting.js"></script>
</body>
</html>
