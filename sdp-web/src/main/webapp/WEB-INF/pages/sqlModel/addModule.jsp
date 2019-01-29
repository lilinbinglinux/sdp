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
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/plugin/codemirror-5.36.0/lib/codemirror.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/plugin/codemirror-5.36.0/theme/eclipse.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/pageModel/addeditmodule.css">

    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/ckeditor-full/ckeditor.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/codemirror-5.36.0/lib/codemirror.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/codemirror-5.36.0/mode/javascript/javascript.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/plugin/codemirror-5.36.0/mode/css/css.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/pageModel/addmodule.js"></script>
    <script>
      var path = "<%=path%>";
      var moduleTypeId = "<%=moduleTypeId%>"
    </script>
</head>

<body style="overflow-y: hidden">
<div class="container-fluid" style="height: 100%">
    <div class="row" style="height: 100%">
        <div class="col-md-6 col-xs-6" style="height: 100%; overflow-y: auto; overflow-x: auto;">
            <div id="code-div" class="row" style="margin-top: 20px;">
                <div>
                    <div class="col-md-6 col-xs-6"><h4>源代码：</h4></div>
                    <div class="col-md-6  col-xs-6 text-right">
                        <button type="button" class="btn btn-success" id="submitBTN" onclick="getCode()"><span class="glyphicon glyphicon-send"></span> 点击运行</button>
                    </div>
                </div>
                <div>
                    <div class="html-div col-md-12 col-xs-12">
                        <div class="col-md-6 col-xs-6" style="padding-right: 0; padding-left: 0;"><span class="code-title">HTML</span>&nbsp;<span class="span-error">(*编写html代码，请点击“源码”按钮再进行编辑)</span></div>
                        <div class="col-md-6 col-xs-6 text-right" style="padding-right: 0; padding-left: 0;">
                            <a href="<%=path%>/v1/page/resClassify" target="_blank">上传资源</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);" onclick="openAddResModal()">选择资源</a>
                        </div>
                    </div>
                    <div class="html-div col-md-12 col-xs-12">
                        <form>
                            <textarea name="editor1" id="editor1" rows="10" cols="80">
                                This is my textarea to be replaced with CKEditor.
                            </textarea>
                            <script type="text/javascript">
                              var ckeditor = CKEDITOR.replace('editor1', {
                                height: 200,
                                allowedContent: true
                                /* toolbar: [
                                         [ 'Source','-','Save','NewPage','DocProps','Preview','Print','-','Templates'  ],
                                         [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ],
                                         [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ],
                                         [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton',
                                                 'HiddenField' ],
                                         '/',
                                         [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ],
                                         [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',
                                                 '-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ],
                                         [ 'Link','Unlink','Anchor' ],
                                         [ 'Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ],
                                         '/',
                                         [ 'Styles','Format','Font','FontSize' ],
                                         [ 'TextColor','BGColor' ],
                                         [ 'Maximize', 'ShowBlocks','-','About' ]
                                     ]*/
                              })
                            </script>
                        </form>
                    </div>

                    <div class="jscss-div">
                        <div class="js-div col-md-6 col-xs-6">
                            <span class="code-title">JS</span>
                            <textarea id="editor2" name="editor2"></textarea>
                            <script type="text/javascript">
                              var myTextarea = document.getElementById('editor2');
                              var CodeMirrorEditor = CodeMirror.fromTextArea(myTextarea, {
                                mode: "text/javascript",
                                lineNumbers: true
                              });
                            </script>
                        </div>
                        <div class="css-div col-md-6 col-xs-6">
                            <span class="code-title">CSS</span>
                            <textarea id="editor3" name="editor3"></textarea>
                            <script type="text/javascript">
                              var myTextarea0 = document.getElementById('editor3');
                              var CodeMirrorEditor0 = CodeMirror.fromTextArea(myTextarea0, {
                                mode: "text/css",
                                lineNumbers: true
                              });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
            <div style="margin-top: 20px;">
                <div class="col-md-6 col-xs-6"></div>
                <div class="col-md-6  col-xs-6 text-right">
                    <button type="button" class="btn btn-success" onclick="saveCom()">确认保存</button>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-xs-6" style="height: 100%; padding-right: 0; padding-left: 0; overflow-y: hidden">
            <iframe id="showIframe" src="<%=path%>/v1/page/moduleShow" frameborder="0" width="100%" height="100%"></iframe>
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
</body>
</html>
