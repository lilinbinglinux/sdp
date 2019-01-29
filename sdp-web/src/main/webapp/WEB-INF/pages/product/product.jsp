<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务注册列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../base.jsp"%>
    <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/css/bootstrapValidator.css">
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css"> -->
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css"> -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/message/message.css"> -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/serviceList.css">
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/themes/black/theme.css"> -->
    <style>
        ::-webkit-scrollbar-track-piece{
            background-color:#fff;
            -webkit-border-radius:0;
        }
        ::-webkit-scrollbar{
            width:8px;
            height:8px;
        }
        ::-webkit-scrollbar-thumb:vertical{
            height:50px;
            background-color:#999;
            -webkit-border-radius:4px;
            outline:2px solid #fff;
            outline-offset:-2px;
            border:2px solid #fff;
        }
        ::-webkit-scrollbar-thumb:hover{
            height:50px;
            background-color:#9f9f9f;
            -webkit-border-radius:4px;
        }
        ::-webkit-scrollbar-thumb:horizontal{
            width:5px;
            background-color:#CCCCCC;
            -webkit-border-radius:6px;
        }
    </style>
</head>
<body>
  <div class="serviceList-container" style="display: block;">
    <div class="serviceList-left">
      <div class="left-title">服务分类</div>
      <div class="left-content">
        <ul id="serviceTree" class="ztree">

        </ul>
      </div>
    </div>
    <div class="nav-collapse-left">
      <img class="collapse-background" src="${pageContext.request.contextPath}/resources/img/serviceType/m1.png">
      <img class="collapse-arrow" src="${pageContext.request.contextPath}/resources/img/serviceType/m2.png">
    </div>
    <div class="nav-collapse-right">
      <img class="collapse-background" src="${pageContext.request.contextPath}/resources/img/serviceType/m1.png">
      <img class="collapse-arrow" src="${pageContext.request.contextPath}/resources/img/serviceType/m2.png">
    </div>
    <div class="serviceList-right">
      <div class="">
        <div class="bconsole-header">
          <span>服务列表</span>
        </div>
        <!-- <div class="inner-title"><h5>服务列表<span id="title-span" style="color: #0b93d5"></span></h5></div> -->
        <div class="bconsole-container">
          <div class="bconsole-search-container">
            <button type="button" class="btn btn-default btn-no-radius btn-refresh" aria-label="Left Align" title="刷新">
              <img src="${pageContext.request.contextPath}/resources/img/serviceType/refresh.png">
            </button>
            <!-- <button type="button" class="btn btn-default btn-no-radius btn-bconsole" aria-label="Left Align" onclick="window.location.href='${pageContext.request.contextPath}/product/regProductPage'">
                    注册服务
            </button> -->
            <button type="button" class="btn btn-default btn-no-radius btn-bconsole" aria-label="Left Align" id="regProductPage-btn">
                    注册服务
            </button>
            <input hidden type="text" id="productTypeIdInput">
            <!-- <button type="button" class="btn btn-default btn-no-radius btn-bconsole" aria-label="Left Align" onclick="window.location.href='${pageContext.request.contextPath}/product/regProductPage'">
                注册服务
            </button> -->
            <form class="form-inline bconsole-fr" style="margin-bottom:0px;">
                <!-- <div class="form-group bconsole-form-group  bconsole-width-280">
                    <select class="form-control bconsole-form-control bconsole-width-110 beconsole-form-select">
                        <option disabled selected style='display:none;'>服务状态</option>
                        <option value="20">已注册</option>
                        <option value="30">上线</option>
                        <option value="40">下线</option>
                    </select>
                    <div class="beconsole-combine-line"></div>
                    <input type="text" class="form-control bconsole-form-control bconsole-width-170 beconsole-combin-input" placeholder="分类名称" id="svcCategorySearch">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </div> -->
                <div class="input-group beconsole-input-group">
                    <div class="input-group-btn">
                      <a type="button" class="btn btn-default dropdown-toggle beconsole-biner-button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="inner-text">服务状态</span> 
                        <span class="caret"></span>
                      </a>
                      <select id="productStatusSelect" name="productStatus" style="display:none;">
                          <option value=""></option>
                          <option value="20">已注册</option>
                          <option value="30">上线</option>
                          <option value="40">下线</option>
                      </select>
                      <ul class="dropdown-menu" style="min-width: 110px;">
                        <li><a href="#" value="">显示全部</a></li>
                        <li><a href="#" value="20">已注册</a></li>
                        <li><a href="#" value="30">上线</a></li>
                        <li><a href="#" value="40">下线</a></li>
                      </ul>
                    </div>
                    <div class="beconsole-group-line"></div>
                    <span class="bconsole-input-placeholder">服务名称</span>
                    <input type="text" class="form-control beconsole-biner-input" id="productNameInput">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                  </div>
            </form>
          </div>
          

          <%--列表--%>
          <div class="table-responsive">
            <table class="table table-hover" id="productTable"></table>
          </div>
        </div>
        <!-- <form class="form-inline" id="svcManageSearchForm" style="display:none;">
          <div class="form-group">
            <select class="form-control" name="productStatus">
              <option value="" selected style='display:none;'>服务状态</option>
              <%--<option value="10">暂存</option>--%>
              <option value="20">已注册</option>
              <option value="30">上线</option>
              <option value="40">下线</option>
            </select>
          </div>
          <div class="form-group">
            <input type="text" class="form-control" name="productName" placeholder="服务名称">
          </div>
          <button type="button" class="btn btn-primary" onclick="$('#productTable').bootstrapTable('refresh');">查询</button>
          <button type="button" class="btn btn-default" onclick="$('#svcManageSearchForm')[0].reset();">重置</button>
          <button type="button" class="btn btn-primary" id="showAllProduct">显示全部</button>
          <button type="button" class="btn btn-primary" style="float: right"
                  onclick="window.location.href='${pageContext.request.contextPath}/product/regProductPage'">
            注册服务
          </button>
        </form> -->
        
        

      </div>
    </div>
  </div>


    <!-- 属性配置 -->
    <div class="property-container" style="display: none;">
        <div class="product-package-left">
            <div class="bconsole-header">
                <span>属性配置</span>
            </div>
            <div class="bconsole-container">
                <div class="bconsole-search-container package-search-container">
                    <button type="button" class="btn btn-default btn-no-radius btn-bconsole" id="property-add-btn">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加属性
                    </button>
                </div>
    
                <%--列表--%>
                <div class="table-responsive">
                    <table class="table table-hover property-table" id="propertyTable">
                            <thead>
                                <tr>
                                    <th style="width: 100px;text-align: left;padding-left: 20px;">属性名</th>
                                    <!-- <th style="width: 100px;">属性英文名</th> -->
                                    <!-- <th style="width: 80px;">控件类型</th> -->
                                    <th style="width: 80px;">属性类型</th>
                                    <th>初始值</th>
                                    <th style="min-width:100px;">校验规则</th>
                                    <!-- <th style="width: 100px;">校验提示</th> -->
                                    <th style="width: 100px;">属性描述</th>
                                    <th style="width: 80px;">属性单位</th>
                                    <th style="width: 110px;">属性标签</th>
                                    <th style="width: 75px;text-align: right;padding-right: 20px;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                
                            </tbody>
                    </table>
                </div>

                <!-- 提交按钮 -->
                <div>
                    <button id="final-submit" type="button" class="btn btn-default btn-no-radius btn-bconsole-hollow" aria-label="Left Align" style="float: right;
                    margin-left: 20px;
                    width: 80px;">
                        提交
                    </button>
                    <button type="button" class="btn btn-default btn-no-radius btn-bconsole-hollow-primary" aria-label="Left Align" style="float: right;
                    width: 80px;">
                        取消
                    </button>
                </div>
            </div>
        </div>
        <div class="product-package-right">
            <div class="package-right-content">
                <div class="package-product-icon">
                    <img src="${pageContext.request.contextPath}/resources/img/serviceType/product.png" >
                </div>
                <span class="package-product-text">基本信息</span>
                <div class="package-product-info">
                    <div class="info-item">
                        <span class="info-title">服务名称:</span>
                        <span class="info-content" id="info-productName"></span>
                    </div>
                    <div class="info-item">
                        <span class="info-title">服务版本:</span>
                        <span class="info-content" id="info-productVersion"></span>
                    </div>
                    <div class="info-item">
                        <span class="info-title">服务描述:</span>
                        <span class="info-content" id="info-productIntrod"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    


    <!-- 服务注册模态框 -->
    <div class="modal fade" id="regProductModal" tabindex="-1" role="dialog"
  aria-labelledby="myModalLabel" data-backdrop="static">
 <div class="modal-dialog" role="document" style="width:960px;">
     <div class="modal-content" style="width:960px;">
         <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#regProductForm')">
             <a><img src="${pageContext.request.contextPath}/resources/img/serviceType/close.png"></a>
         </div>
         <div class="bconsole-modal-title">
                <span class="green-title-circle title-img-container">
                    <img src="${pageContext.request.contextPath}/resources/img/serviceType/basic-icon.png">
                </span>    
                <span class="title">服务基本信息</span>
         </div>
         <div class="bconsole-modal-content" style="padding-left: 30px;padding-right: 60px;">
             <form class="form-horizontal" id="regProductForm"  enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">服务名称</span>
                                <span class="red-font">＊</span>
                            </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productName" required pattern="^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">服务编码</span>
                                <span class="red-font">＊</span>
                            </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productId" required pattern="^(?!_)[a-zA-Z0-9_\u4e00-\u9fa5]+$">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <span class="name">服务描述</span>
                                <span class="red-font">＊</span>
                            </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productIntrod" required>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">服务地址</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="apiAddr" required>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group ">
                            <label class="col-sm-3 control-label"><span class="name">服务类型</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <select class="form-control" name="productTypeId" required></select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group ">
                            <label class="col-sm-3 control-label"><span class="name">服务排序</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="number" name="sortId" min="0" style="padding-right:12px" class="form-control" required>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">服务logo</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <div class="file-group">
                                    <div class="file-icon"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></div>
                                    <input type="file" name="productIcon" class="form-control" id="productIcon" accept='image/*' required>
                                    <div class="file-text">添加logo</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">服务版本</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productVersion" required>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group" id="product-payways">
                            <label class="col-sm-3 control-label"><span class="name">订购方式</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9 product-payways">
                                <!-- <span class="" id="orderTypeShow"></span> -->
                                <!-- <div class="form-radio-group">
                                    <input id="item1" type="radio" name="item" value="水果" checked>
                                    <label for="item1"></label>
                                    <span>水果</span>
                                </div> -->
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="name">实例显示</span><span class="red-font">＊</span></label>
                            <div class="col-sm-9">
                                <div class="form-radio-group">
                                    <input id="item1" type="radio" name="showInstance" value="10">
                                    <label for="item1"></label>
                                    <span>是</span>
                                </div>
                                <div class="form-radio-group">
                                    <input id="item1" type="radio" name="showInstance" value="20">
                                    <label for="item1"></label>
                                    <span>否</span>
                                </div>
                                <!-- <span class="">
                                    <label class="radio-inline">
                                        <input type="radio" name="showInstance" value="10">是
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="showInstance" value="20">否
                                    </label>
                                </span> -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group ">
                            <label class="col-sm-3 control-label">配额选择</label>
                            <div class="col-sm-9">
                                <select class="form-control" name="useTotalType">
                                    <option value="10">使用租户总配额</option>
                                    <option value="20">使用公共配额</option>
                                    <option value="30">使用项目配额</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 causeProcess">

                    </div>
                </div>
             </form>
         </div>
         <div class="bconsole-modal-footer" style="text-align:center;">
             <a class="modal-submit product-basic-submit">保存</a>
         </div>
     </div>
 </div>
    </div>

    <!-- 添加属性模态框 -->
    <div class="modal fade" id="propertyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:960px;">
            <div class="modal-content" style="width:960px;">
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetPropertyForm('#propertyForm')">
                    <a><img src="${pageContext.request.contextPath}/resources/img/serviceType/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/serviceType/property.png">
                    </span>
                    <span class="title">添加属性</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 30px;padding-right: 60px;">
                    <form class="form-horizontal" id="propertyForm">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="name">属性名</span><span class="red-font">＊</span></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="proName">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="name">属性编码</span><span class="red-font">＊</span></label>
                                    <div class="col-sm-9">
                                      <input type="text" class="form-control" name="proCode">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">英文名</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="proEnName">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group ">
                                    <label class="col-sm-3 control-label">数据类型</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="proDataType">
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
                                </div>
                            </div>
                            
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">属性单位</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="proUnit">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">属性描述</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="proDesc">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">校验规则</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="proVerfyRule">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group ">
                                    <label class="col-sm-3 control-label">校验提示</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="proVerfyTips">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">属性标签</label>
                                    <div class="col-sm-9">
                                        <div class="form-group-container">
                                            <div class="form-chekbox-group">
                                                <input id="item1" type="checkbox" name="proLabel" value="10">
                                                <label for="item1"></label>
                                                <span>申请</span>
                                            </div>
                                            <div class="form-chekbox-group">
                                                <input id="item1" type="checkbox" name="proLabel" value="20">
                                                <label for="item1"></label>
                                                <span>访问</span>
                                            </div>
                                            <div class="form-chekbox-group">
                                                <input id="item1" type="checkbox" name="proLabel" value="30">
                                                <label for="item1"></label>
                                                <span>资源</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">控件类型</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="proShowType">
                                            <option value="10">文本框</option>
                                            <option value="20">下拉框</option>
                                            <option value="30">单选框</option>
                                            <option value="40">复选框</option>
                                            <option value="50">Slider滑块</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 proMappingCode">
                                <!-- <div class="form-group">
                                    <label class="col-sm-3 control-label">映射资源属性</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="proMappingCode">
                                            <option value="CPU">文本框</option>
                                            <option value="Memory">Memory</option>
                                            <option value="Storage">Storage</option>
                                        </select>
                                    </div>
                                </div> -->
                            </div>
                        </div>
                       <div class="initial-container">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group" style="margin-bottom:0;">
                                        <label class="col-sm-3 control-label">初始值</label>
                                        <div class="col-sm-9">
                                            <div class="form-radio-group">
                                              <input id="item1" type="radio" name="initialType" value="10" checked>
                                              <label for="item1"></label>
                                              <span>手动输入</span>
                                            </div>
                                            <div class="form-radio-group">
                                              <input id="item1" type="radio" name="initialType" value="20">
                                              <label for="item1"></label>
                                              <span>URL</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group" style="width:100%;">
                                        <label class="col-sm-3 control-label" style="display:none;"></label>
                                        <div class="col-sm-9 control-basic-container">
                                            <div class="border-up-empty"><span></span></div>
                                            <div class="basic-key-value">
                                                <div class="table-responsive">
                                                    <table class="table table-hover property-table basic-table">
                                                            <colgroup>
                                                                <col width="100px;"/>
                                                                <col width="225px;" />
                                                                <col width="225px;" />
                                                                <col width="100px;" />
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th style="width: 100px;">默认项</th>
                                                                    <th style="width: 225px;">键值</th>
                                                                    <th style="width: 225px;">显示项</th>
                                                                    <th style="width: 100px;">操作</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                        <div class="form-radio-group">
                                                                            <input id="item1" type="radio" name="defaultEntry">
                                                                            <label for="item1"></label>
                                                                            <span></span>
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <span class="basic-inner-text"></span>
                                                                        <input type="text" class="form-control beconsole-biner-input basic-inner-input">
                                                                    </td>
                                                                    <td>
                                                                        <span class="basic-inner-text"></span>
                                                                        <input type="text" class="form-control beconsole-biner-input basic-inner-input">
                                                                    </td>
                                                                    <td>
                                                                        <span class="glyphicon glyphicon-plus basic-edit" aria-hidden="true"></span>
                                                                        <i class="fa fa-trash-o basic-delete" aria-hidden="true"></i>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="basic-url">
                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label">请输入初始值地址</label>
                                                    <div class="col-sm-9">
                                                        <input type="text" class="form-control beconsole-biner-input" style="width:502px;">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                       </div>
                    </form>
                </div>
                <div class="bconsole-modal-footer" style="text-align:center;">
                    <a class="modal-submit property-submit">添加</a>
                </div>
            </div>
        </div>
    </div>
</body>
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<!-- <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/message/message.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dynamicConvert.js"></script>
<script type="text/javascript">
    var $webpath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/uuid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/order/product.js"></script>
</html>
