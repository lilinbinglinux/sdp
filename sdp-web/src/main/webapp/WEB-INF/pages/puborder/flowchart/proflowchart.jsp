<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css"
      type="text/css">
<script type="text/javascript"
        src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/resources/zTree_v3-master/js/jquery.ztree.all.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>流程图和树</title>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-body-css.jsp" %>
    <style type="text/css">
        body {
            overflow: hidden;
        }

        .panel-heading > i.iconfont:first-child {
            font-size: 20px;
            margin-right: 4px;
        }

        .common-part .icon-tip {
            float: right;
            margin-top: 2px;
        }

        .panel-heading > span {
            top: -2px;
            position: relative;
        }

        .bootstrapMenu {
            z-index: 10000;
            position: absolute;
            display: none;
            height: 23px;
            width: 158px;
        }

        .dropdown-menu {
            position: static;
            display: block;
            font-size: 0.9em;
        }

        .modalpanl {
            width: 600px;
            margin-left: 500px;
            margin-top: 80px;
        }

        .dataTables_wrapper{
            position: absolute;
            bottom: -300px;
            right: 30px;
        }

        .ztree {
            overflow: auto;
            height: 95%;
            width: 103%;
        }

    </style>
</head>
<body>
<!--树-->
<div class="row">
    <div class="col-lg-3 col-md-3 row-tab" id="flowchart-leftContent">
        <div id="pro-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">
                <i class="iconfont changeopen">&#xe6db;</i>
                <span class="changeopen">项目接口列表</span>
                <!-- <i class="iconfont icon-tip" id="flowchart-toggleBar" isOpen="1" style="margin-left: 15px;">
                    &#xe656;</i> -->
                <i class="iconfont icon-tip">&#xe6a8;</i>
            </div>
            <div class="panel-body common-content">
                <div id="proTree" class="ztree changeopen"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-9 col-md-9 row-tab panel-r" id="flowchart-rightContent">
        <div id="con-panel" class="panel panel-default">
            <div class="panel-body" id="panel-jsp">
                <iframe id="flowChart-childPage" name="flowChart-childPage"
                        src="../flowChartIframe/index" scrolling="auto" frameborder="0" allowTransparency="true"
                        style="width:100%;height:100%;"></iframe>
            </div>
        </div>
    </div>

</div>
<!-- 空白的菜单 -->
<div id="blankContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="addFolder">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i>
                <span id="m1">添加项目</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="addInnerLink">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-link"></i>
                <span id="m3_1" class=".addApi">添加API</span>
            </a>
        </li>
    </ul>
</div>
<!-- 文件夹的菜单 -->
<div id="folderContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="addFolder">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i>
                <span id="m2">添加模块文件夹</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="addInnerLink">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-link"></i>
                <span id="m3_2" class=".addApi">添加API</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="updateInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i>
                <span data-toggle="modal" data-target="#proModal">修改项目</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="deleteInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i>
                <span>删除项目</span>
            </a>
        </li>
    </ul>
</div>
<!-- 模块文件夹的菜单 -->
<div id="linkContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="addFun">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-th-large"></i>
                <span id="m3" class=".addApi">添加API</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="updateInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i>
                <span data-toggle="modal" data-target="#proModelModal">修改模块</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="deleteInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i>
                <span>删除模块</span>
            </a>
        </li>
    </ul>
</div>
<!-- API菜单 -->
<div id="link1ContextMenu" class="dropdown bootstrapMenu">
    <ul class="dropdown-menu">
        <li>
            <a href="javascript:void(0);" class="updateInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i>
                <span id="setparam">设置参数</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="updateInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i>
                <span data-toggle="modal" data-target="#pruModal">修改API</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" class="deleteInterface">
                <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i>
                <span>删除API</span>
            </a>
        </li>
    </ul>
</div>
<input type="hidden" id="modaltype">
<!--pro-Modal -->
<div class="modal fade modalpanl" id="proModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <input type="hidden" id="id-proinfoupdate">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">项目新建</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="urlEnv">项目名称:</label>
                    <input type="text" class="form-control input-sm form-sm" name="name" id="proname"/>
                </div>
                <div class="form-group">
                    <label for="urlEnv">编码:</label>
                    <input type="text" class="form-control input-sm form-sm" name="proecode" id="proecode"/>
                </div>
                <div class="form-group">
                    <label for="urlEnv">版本:</label>
                    <input type="text" class="form-control input-sm form-sm" name="proversion" id="proversion"/>
                </div>
                <div class="form-group">
                    <label for="urlEnv">项目说明:</label>
                    <textarea row='3' name="prodescribe" id="prodescribe"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="addProBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<!--pro-model-Modal -->
<div class="modal fade modalpanl" id="proModelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <input type="hidden" id="id-proModelinfoupdate">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <%--      <h4 class="modal-title" id="myModalLabel">模块新建</h4>--%>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="urlEnv">模块名称:</label>
                    <input type="text" class="form-control input-sm form-sm" name="name" id="promname"/>
                </div>
                <div class="form-group">
                    <label for="urlEnv">编码:</label>
                    <input type="text" class="form-control input-sm form-sm" name="proecode" id="promecode"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="addProModelBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- API-Modal -->
<div id="pruModal" class="modal fade modalpanl"  tabindex="-1" role="dialog" style=" margin-top: 40px;">
    <div class="" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">接口注册</h4>
            </div>
            <input type="hidden" class="form-control input-sm form-sm"  name="pubid" id="pubid"/>
            <div class="modal-body">
                <div class="form-group">
                    <label for="pubname">接口名称:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input"  name="name" id="pubname"/>
                </div>

                <div class="form-group">
                    服务类型:
                    <label><input name="typeId" type="radio" value="2" checked/>接口 </label>
                    <label><input name="typeId" type="radio" value="4" />jar定义 </label>
                </div>

                <!-- 接口设置div  -->
                <div id="typeId_api">
                    <div class="form-group">
                        <label for="puburl">url:</label>
                        <input type="text" class="form-control input-sm form-sm rightinput apimodel-input" style="margin-left: 48px;" name="url" id="puburl"/>

                        <label for="pubport" style="margin-left: 80px;" >端口:</label>
                        <input type="text" class="form-control input-sm form-sm"  name="pubport" id="pubport"/>
                    </div>
                    <div class="form-group">
                        <label for="pubprotocal">请求协议:</label>
                        <select name="pubprotocal" id="pubprotocal" class="apimodel-select apimodel-input">
                            <option value="http">http</option>
                            <option value="webService">webService</option>
                        </select>

                        <label for="pubmethod" style="margin-left: 57px;">请求方式:</label>
                        <select name="method" id="pubmethod" class="apimodel-select">
                            <option value="GET">GET</option>
                            <option value="POST">POST</option>
                            <option value="DELETE">DELETE</option>
                            <option value="PATCH">PATCH</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="pubreqformat">请求格式:</label>
                        <select name="reqformat" id="pubreqformat" class="apimodel-select" style="width:154px">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="application/x-www-form-urlencoded">application/x-www-form-urlencoded</option>
                            <option value="其他">其他</option>
                        </select>

                        <label for="pubrespformat" style="margin-left: 57px;">响应格式:</label>
                        <select name="respformat" id="pubrespformat" class="apimodel-select">
                            <option disabled="disabled">------------请选择-------------</option>
                            <option value="application/json">application/json</option>
                            <option value="application/xml">application/xml</option>
                            <option value="其他">其他</option>
                        </select>

                    </div>
                    <div class="form-group">
                        <label for="pubreqdatatype">请求值类型:</label>
                        <select name="reqdatatype" id="pubreqdatatype" class="apimodel-select">
                            <option value="Object" selected>Object</option>
                        </select>

                        <label for="pubreturndatatype" style="margin-left: 46px;">返回值类型:</label>
                        <select name="returndatatype" id="pubreturndatatype" class="apimodel-select">
                            <option value="Object">Object</option>
                            <option value="List">List</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="pubdesc">描述:</label>
                        <textarea class="apimodel-area" name="pubdesc" id="pubdesc"></textarea>
                    </div>
                </div>
            </div>

            <!-- jar包api设置div -->
            <div id="typeId_jar" style="display:none;margin-left: 17px;">
                <div class="form-group">
                    <input type ="file" id="ImportPicInput" name= "myfile"/>
                </div>
                <div class="form-group">
                    <label for="className">调用类:&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" style="width:480px"
                           name="className" id="className" placeholder="输入需要调用的类名"/>
                </div>
                <div class="form-group">
                    <label for="methodInClass">调用方法:</label>
                    <input type="text" class="form-control input-sm form-sm apimodel-input" style="width:480px"
                           name="methodInClass" id="methodInClass" placeholder="输入需要调用的方法名"/>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="addPubBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../common-js.jsp" %>
<script src="<%=webpath %>/resources/js/puborder/flowchart/proflowchart.js"></script>
<script src="<%=webpath %>/resources/plugin/ajaxfileupload.js"></script>
<script src="<%=webpath %>/resources/js/util_common.js" type="text/javascript"></script>
</body>
<script>
    var flowChartId = '${flowChartId}';
    var webapp = '${pageContext.request.contextPath}';
</script>
</html>