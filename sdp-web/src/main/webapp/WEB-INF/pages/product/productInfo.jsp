<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/8/22
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务信息</title>
    <meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/ligerui/lib-init8.14/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/ligerui/lib-init8.14/ligerUI/skins/Gray/css/all.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css">
    <style>
        #svcBasicInfo div {
            margin-bottom: 10px;
        }
        .label-width {
            width: 15%;
            vertical-align: top;
            text-align: right;
        }
        .input-width {
            width: 70%;
            display: inline-block !important;
        }
        .inner-order-number{
            width: 60px;
        }
        .inner-order-input{
            display: none;
            margin: 0;
            width: 44px;
        }
        .table > tbody > tr > td{
            height: 36px;
        }
    </style>
</head>
<body class="body-bg">
<%--服务详细信息页面--%>
<!-- <%--<div id="svcDetailPage" class="container-fluid product-container" style="width: 80%;">
    &lt;%&ndash;服务基本信息&ndash;%&gt;
    <div class="page-info-header">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>1.服务基本信息
    </div>
    <div id="svcBasicInfo" class="row" style="padding: 0 30px 10px;font-size: 14px;"></div>

    &lt;%&ndash;服务属性列表&ndash;%&gt;
    <div class="page-info-header">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>2.属性配置
    </div>
    <div class="table-responsive" style="margin: 10px 0">
        <table class="table table-hover" id="svcAttrInfoTable">
            <thead>
            <tr>
                <th>属性名</th>
                <th>属性英文名</th>
                <th>控件类型</th>
                <th>属性类型</th>
                <th>初始值</th>
                <th>校验规则</th>
                <th>校验提示</th>
                <th>属性描述</th>
                <th>属性单位</th>
                <th>属性标签</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    &lt;%&ndash;依赖服务列表&ndash;%&gt;
    <div class="page-info-header" style="display: none">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>3.依赖服务配置
    </div>
    <div class="table-responsive" style="margin-top: 10px;display: none">
        <table class="table table-hover" id="relySvcInfoTable">
            <thead>
            <tr>
                <th>服务编码</th>
                <th>是否默认配置</th>
                <th>是否展示实例</th>
                <th>顺序</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <div class="row" style="margin: 20px 0 0; text-align: right;">
        <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">返回</button>
    </div>
</div>--%> -->


<%--服务暂存时的编辑页面--%>
<div id="svcEditPage" class="container-fluid product-container">
    <%--服务基本信息--%>
    <div class="page-info-header">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>1.服务基本信息
        <span style="float: right;cursor: pointer;font-size: 21px;"
              class="glyphicon glyphicon-circle-arrow-left" title="返回"
              onclick="window.location.href='${pageContext.request.contextPath}/product/page'"></span>
    </div>
    <form class="form-horizontal row" id="svcRegisterInfo"
          style="margin: 0;font-size: 14px;" enctype="multipart/form-data">
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务名称</label>
            <input type="text" class="form-control input-width"
                   name="productName" required
                   pattern="^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$">
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务类型</label>
            <select class="form-control input-width" name="productTypeId" required>

            </select>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务描述</label>
            <textarea type="text" class="form-control input-width"
                      name="productIntrod" rows="3" required></textarea>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务logo</label>
            <span class="input-width">
                <img style="width: 100px;height: 70px" src="" alt="">
                <input type="file" name="productIcon" class="form-control" id="productIcon" accept='image/*'>
            </span>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务编码</label>
            <input type="text" class="form-control input-width" name="productId"
                   required pattern="^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$" readonly>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务版本</label>
            <input type="text" class="form-control input-width"
                   name="productVersion" required>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>订购方式</label>
            <span class="input-width" id="orderTypeShow"></span>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务地址</label>
            <input type="text" class="form-control input-width" name="apiAddr" required>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>服务排序</label>
            <input type="number" name="sortId" min="0" style="padding-right:12px" class="form-control input-width" required>
        </div>
        <div class="form-group col-md-6 causeProcess">
            <!-- <label class="label-width"><span class="red-font">＊</span>审批流程</label>
            <span class="input-width" id="approvalProcessShow"></span> -->
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>实例显示</label>
            <span class="input-width">
                <label class="radio-inline">
                    <input type="radio" name="showInstance" value="10">是
                </label>
                <label class="radio-inline">
                    <input type="radio" name="showInstance" value="20">否
                </label>
            </span>
        </div>
        <div class="form-group col-md-6">
            <label class="label-width"><span class="red-font">＊</span>配额选择</label>
            <select class="form-control input-width" name="useTotalType">
                <option value="10">使用租户总配额</option>
                <option value="20">使用公共配额</option>
                <option value="30">使用项目配额</option>
            </select>
        </div>
    </form>

    <%--服务属性列表--%>
    <div class="page-info-header">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>2.属性配置
        <span id="proSetPoint" class="glyphicon glyphicon-question-sign" data-container="body" data-toggle="popover"
              data-trigger="hover" data-placement="right" data-content="校验规则应填写正则表达式，例如^\d+$"></span>
    </div>
    <div class="row" style="margin: 0">
        <button type="button" class="btn btn-primary" data-toggle="modal"
                data-target="#attrAddModal" style="float: right;">添加属性
        </button>
    </div>
    <div class="table-responsive" style="margin: 10px 0">
        <table class="table table-hover" id="regAttrListTable">
            <thead>
            <tr>
                <th style="width: 60px;">序号</th>
                <th style="width: 100px;">属性名</th>
                <th style="width: 100px;">属性英文名</th>
                <th style="width: 80px;">控件类型</th>
                <th style="width: 80px;">属性类型</th>
                <th>初始值</th>
                <th>校验规则</th>
                <th style="width: 100px;">校验提示</th>
                <th style="width: 100px;">属性描述</th>
                <th style="width: 80px;">属性单位</th>
                <th style="width: 110px;">属性标签</th>
                <th style="width: 60px;">操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <%--依赖服务列表--%>
    <div class="page-info-header" style="display: none">
        <span style="font-weight: bold; font-size: 18px;margin-right: 10px">|</span>3.依赖服务配置
    </div>
    <div class="row" style="margin: 0;display: none">
        <button type="button" class="btn btn-primary" data-toggle="modal"
                data-target="#relySvcCheckedModal" style="float: right;">添加依赖服务
        </button>
    </div>
    <div class="table-responsive" style="margin-top: 10px;display: none">
        <table class="table table-hover" id="relySvcListTable">
            <thead>
            <tr>
                <th>服务编码</th>
                <th>是否默认配置</th>
                <th>是否展示实例</th>
                <th>顺序</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <div class="row" style="margin: 20px 0 0; text-align: right;">
        <%--<button type="button" class="btn btn-primary" onclick="handleSvcRegister('20')">注册</button>
        <button type="button" class="btn btn-primary" onclick="handleSvcRegister('10')">暂存</button>--%>
        <button type="button" class="btn btn-primary" onclick="handleSvcRegister('20')">保存</button>
        <button type="button" class="btn btn-default"
                onclick="window.location.href='${pageContext.request.contextPath}/product/page'">取消
        </button>
    </div>

    <!-- 添加属性Modal -->
    <div class="modal fade" id="attrAddModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close"
                            onclick="handleResetAddModel()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" style="font-size: 18px">添加属性</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal row" id="svcAddAttrForm" style="margin: 0">
                        <div class="form-group col-md-6">
                            <label class="label-width"><span class="red-font">＊</span>属性名</label>
                            <input type="text" class="form-control input-width" name="proName" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width">属性英文名</label>
                            <input type="text" class="form-control input-width" name="proEnName">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width"><span class="red-font">＊</span>属性编码</label>
                            <input type="text" class="form-control input-width" name="proCode" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width"><span class="red-font">＊</span>数据类型</label>
                            <select class="form-control input-width" name="proDataType"
                                    required>
                                <option value="10">byte</option>
                                <option value="20">short</option>
                                <option value="30">int</option>
                                <option value="40">long</option>
                                <option value="50">float</option>
                                <option value="60">double</option>
                                <option value="70">char</option>
                                <option value="80">boolean</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width"><span class="red-font">＊</span>控件类型</label>
                            <select class="form-control input-width" name="proShowType" required>
                                <option value="10">文本框</option>
                                <option value="20">下拉框</option>
                                <option value="30">单选框</option>
                                <option value="40">复选框</option>
                                <option value="50">Slider滑块</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width"><span class="red-font">＊</span>属性标签</label>
                            <span class="input-width" id="proLabelSelect">
                                <label class="checkbox-inline">
								    <input type="checkbox" value="10" name="proLabel"> 申请
								</label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" value="20" name="proLabel"> 访问
								</label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" value="30" name="proLabel"> 资源
								</label>
							</span>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width">属性单位</label>
                            <input type="text" class="form-control input-width" name="proUnit">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width">属性描述</label>
                            <input type="text" class="form-control input-width" name="proDesc">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width">校验规则</label>
                            <input type="text" class="form-control input-width" name="proVerfyRule">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="label-width">校验提示</label>
                            <input type="text" class="form-control input-width" name="proVerfyTips">
                        </div>
                        <div class="form-group col-md-6 proMappingCode">
                            
                        </div>
                    </form>

                    <div class="row" id="initialAddBox" style="display: none">
                        <div class="col-md-12">
                            <label class="label-width">初始值</label>
                            <!-- Nav tabs -->
                            <div class="input-width">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active">
                                        <a id="initialParamA" href="#initialParam" aria-controls="initialParam"
                                           role="tab" data-toggle="tab">手动输入</a>
                                    </li>
                                    <li role="presentation">
                                        <a id="initialUrlA" href="#initialUrl" aria-controls="initialUrl" role="tab"
                                           data-toggle="tab">URL</a>
                                    </li>
                                </ul>
                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div role="tabpanel" class="tab-pane active" id="initialParam">
                                        <div id="attrInitialTable" style="margin: 10px 0"></div>
                                        <button type="button" class="btn btn-primary btn-block"
                                                onclick="attrInitialGrid.addRow({checked:'',key:'',value:''});">添加参数
                                        </button>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="initialUrl">
                                        <div style="margin: 10px 0">
                                            <label class="label-width">请输入初始值地址</label>
                                            <input type="text" class="form-control input-width" name="initialUrl">
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            onclick="handleResetAddModel()">取消
                    </button>
                    <button type="button" class="btn btn-primary" onclick="addAttrSave()">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改属性Modal -->
    <div class="modal fade" id="attrEditModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close"
                            onclick="handleResetForm('#svcEditAttrForm')">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" style="font-size: 18px">修改属性</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal row" id="svcEditAttrForm" style="margin: 0"></form>

                    <div class="row" id="initialEditBox" style="display: none">
                        <div class="col-md-12">
                            <label class="label-width">初始值</label>
                            <!-- Nav tabs -->
                            <div class="input-width">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active">
                                        <a id="initialParamA1" href="#initialEditParam" aria-controls="initialEditParam"
                                           role="tab" data-toggle="tab">手动输入</a>
                                    </li>
                                    <li role="presentation">
                                        <a id="initialUrlA1" href="#initialEditUrl" aria-controls="initialEditUrl"
                                           role="tab" data-toggle="tab">URL</a>
                                    </li>
                                </ul>
                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div role="tabpanel" class="tab-pane active" id="initialEditParam">
                                        <div id="attrInitialEditTable" style="margin: 10px 0"></div>
                                        <button type="button" class="btn btn-primary btn-block"
                                                onclick="attrInitialEditGrid.addRow({checked:'',key:'',value:''});">添加参数
                                        </button>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="initialEditUrl">
                                        <div style="margin: 10px 0">
                                            <label class="label-width">请输入初始值地址</label>
                                            <input type="text" class="form-control input-width" name="initialUrl">
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer"></div>
            </div>
        </div>
    </div>


    <%--勾选依赖服务Modal--%>
    <div class="modal fade" id="relySvcCheckedModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"
                            onclick="$('#relySvcCheckedTable :checkbox').prop('checked',false);">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" style="font-size: 18px;">添加依赖的服务</h4>
                </div>
                <div class="modal-body">
                    <div class="table-responsive" style="margin-top: 10px">
                        <table class="table table-hover" id="relySvcCheckedTable">
                            <thead>
                            <tr>
                                <th></th>
                                <th>服务编码</th>
                                <th>服务名称</th>
                                <th>服务版本</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>

                        <%--勾选依赖服务分页--%>
                        <div id="pro-pagination" style="float: right;"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            onclick="$('#relySvcCheckedTable :checkbox').prop('checked',false);">取消
                    </button>
                    <button type="button" class="btn btn-primary" onclick="saveRelySvcChecked()">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改依赖服务Modal -->
    <div class="modal fade" id="relySvcEditModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close"
                            onclick="handleResetForm('#relySvcEditForm')">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" style="font-size: 18px">修改依赖服务配置</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal row" id="relySvcEditForm" style="margin: 0"></form>
                </div>
                <div class="modal-footer"></div>
            </div>
        </div>
    </div>


</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layui-v2.2.6/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/ligerui/lib-init8.14/ligerUI/js/ligerui.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/product/product-common.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
    getQueryString();
</script>
<script src="${pageContext.request.contextPath}/resources/js/product/productInfo.js"></script>
</html>

