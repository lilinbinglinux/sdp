<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>服务配额</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="../base.jsp"%>
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/extensions/page-jumpto/bootstrap-table-jumpto.css"> -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/skin/layer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/dataModel/summerTab/css/summerTab.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product/common-product.css">
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
<body class="body-bg">
    <div class="quota-top-container">
        <div class="right">
            <div class="wrap-container">
                <img src="${pageContext.request.contextPath}/resources/img/serviceType/peie.png">
                <button type="button" class="btn btn-default btn-no-radius btn-bconsole-hollow-primary" aria-label="Left Align" id="quotaApply">
                    配额申请
                </button>
            </div>
        </div>
        <div class="left">
            <div class="bconsole-header" style="padding-right: 158px;">
                <span>配额使用情况</span>
                <span style="margin-left:10px;">(</span>
                <span style="margin-left:5px;" id="project-name">项目</span>
                <span style="margin-left:5px;">)</span>
                <div class="indicate-point">
                    <span class="color gray"></span>
                    <span class="name">剩余</span>
                </div>
                <div class="indicate-point" style="margin-right:30px;">
                    <span class="color green"></span>
                    <span class="name">已使用</span>
                </div>
            </div>
            <div class="quota-process">
                <div class="quota-process-container">

                </div>
                <!-- <div class="process-item">
                    <div class="item-process">
                        <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                                <span class="percent_number">60%</span>
                            </div>
                            <div class="inner-line line-0"></div>
                            <div class="inner-line  b-line  line-1"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-2"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-3"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-4"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-5"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-6"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-7"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-8"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-9"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line line-10"></div>
                        </div>
                    </div>
                    <span class="item-name">CPU</span>
                    <span class="item-value">140/150<span style="margin-left:10px;margin-right:3px;">(</span><span>个</span><sapn style="margin-left:3px;">)</sapn></span>
                </div>
                <div class="process-item">
                    <div class="item-process">
                        <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                            60%
                            </div>
                            <div class="inner-line line-0"></div>
                            <div class="inner-line  b-line  line-1"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-2"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-3"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-4"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-5"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-6"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-7"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-8"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-9"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line line-10"></div>
                        </div>
                    </div>
                    <span class="item-name">CPU</span>
                    <span class="item-value">140/150<span style="margin-left:10px;margin-right:3px;">(</span><span>个</span><sapn style="margin-left:3px;">)</sapn></span>
                </div>
                <div class="process-item">
                    <div class="item-process">
                        <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                            60%
                            </div>
                            <div class="inner-line line-0"></div>
                            <div class="inner-line  b-line  line-1"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-2"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-3"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-4"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-5"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-6"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-7"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-8"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line  b-line  line-9"><div class="top"></div><div class="bottom"></div></div>
                            <div class="inner-line line-10"></div>
                        </div>
                    </div>
                    <span class="item-name">CPU</span>
                    <span class="item-value">1900/2500<span style="margin-left:10px;margin-right:3px;">(</span><span>个</span><sapn style="margin-left:3px;">)</sapn></span>
                </div> -->
                <div class="process-item value-content">
                    <div class="item-process" style="padding-top:5px;">
                        <div class="progress" style="background-color: #fff">
                            <div class="line-0 c-line" style="border-left:none;"><span class="inner-number">0%</span></div>
                            <div class="line-1 c-line"><span class="inner-number">10%</span></div>
                            <div class="line-2 c-line"><span class="inner-number">20%</span></div>
                            <div class="line-3 c-line"><span class="inner-number">30%</span></div>
                            <div class="line-4 c-line"><span class="inner-number">40%</span></div>
                            <div class="line-5 c-line"><span class="inner-number">50%</span></div>
                            <div class="line-6 c-line"><span class="inner-number">60%</span></div>
                            <div class="line-7 c-line"><span class="inner-number">70%</span></div>
                            <div class="line-8 c-line"><span class="inner-number">80%</span></div>
                            <div class="line-9 c-line"><span class="inner-number">90%</span><span class="lat-number">100%</span></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="quota-bottom-container">
        <div class="summer-tab" id="quotaTab">
            <div class="summer-tabs">
              <div class="tab-item active" id="basic">配额变更记录</div>
              <div class="tab-item" id="column">配额申请记录</div>
              <div class="summer-line"></div>
            </div>
            <div class="summer-tab-content">
                <div class="tab-content" style="padding: 0 26px 26px 26px;">
                    <div class="bconsole-search-container">
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
                                        <span class="inner-text">变更内容</span> 
                                        <span class="caret"></span>
                                    </a>
                                    <select id="recordTypeSelect" style="display:none;">
                                        <option value=""></option>
                                        <option value="10">完成</option>
                                        <option value="20">未通过</option>
                                    </select>
                                    <ul class="dropdown-menu recordTypeDropdown" style="min-width: 110px;">
                                    <li><a href="#" value="">显示全部</a></li>
                                    <li><a href="#" value="10">完成</a></li>
                                    <li><a href="#" value="20">未通过</a></li>
                                    </ul>
                                </div>
                                <div class="beconsole-group-line"></div>
                                <span class="bconsole-input-placeholder">记录单号</span>
                                <input type="text" class="form-control beconsole-biner-input search-input" id="recordIdInput">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            </div>
                        </form>
                    </div>

                    <%--列表--%>
                    <div class="table-responsive">
                      <table class="table table-hover" id="quotaChangeTable"></table>
                    </div>
                </div>
                <div class="tab-content" style="padding: 0 26px 26px 26px;">
                    <div class="bconsole-search-container">
                        <form class="form-inline bconsole-fr" style="margin-bottom:0px;">
                            <div class="input-group beconsole-input-group">
                                <div class="input-group-btn">
                                    <a type="button" class="btn btn-default dropdown-toggle beconsole-biner-button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="inner-text">变更内容</span> 
                                        <span class="caret"></span>
                                    </a>
                                    <select id="orderStatusSelect" style="display:none;">
                                        <option value=""></option>
                                        <option value="10">待支付</option>
                                        <option value="20">支付成功</option>
                                        <option value="30">待审批</option>
                                        <option value="40">审批中</option>
                                        <option value="50">通过</option>
                                        <option value="60">驳回</option>
                                    </select>
                                    <ul class="dropdown-menu orderStatusDropdown" style="min-width: 110px;">
                                        <li><a href="#" value="">显示全部</a></li>
                                        <li><a href="#" value="10">待支付</a></li>
                                        <li><a href="#" value="20">支付成功</a></li>
                                        <li><a href="#" value="30">待审批</a></li>
                                        <li><a href="#" value="40">审批中</a></li>
                                        <li><a href="#" value="50">通过</a></li>
                                        <li><a href="#" value="60">驳回</a></li>
                                    </ul>
                                </div>
                                <div class="beconsole-group-line"></div>
                                <span class="bconsole-input-placeholder">申请单号</span>
                                <input type="text" class="form-control beconsole-biner-input search-input" id="orderIdInput">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            </div>
                        </form>
                    </div>
                    <%--列表--%>
                    <!-- <div class="simulate-table">
                        <div class="simulate-table-thead">
                            <div class="simulate-table-row">
                                <div class="simulate-table-cell">申请单号</div>
                                <div class="simulate-table-cell" style="width: 400px;">申请配额</div>
                                <div class="simulate-table-cell" style="width: 150px;">申请状态</div>
                                <div class="simulate-table-cell">提交时间</div>
                                <div class="simulate-table-cell">生效时间</div>
                                <div class="simulate-table-cell">操作</div>
                            </div>
                        </div>
                        <div class="simulate-table-tbody">
                            <div class="simulate-table-row">
                                <div class="simulate-table-cell">532502559156342784</div>
                                <div class="simulate-table-cell"><div><span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span><span class="quota-inline"><span class="quota-inline-key">内存</span><span class="quota-inline-value">12G</span></span><span class="quota-inline"><span class="quota-inline-key">硬盘</span><span class="quota-inline-value">120G</span></span></div></div>
                                <div class="simulate-table-cell">通过</div>
                                <div class="simulate-table-cell">2019-01-09 10:15:03</div>
                                <div class="simulate-table-cell">2019-01-09 10:15:23</div>
                                <div class="simulate-table-cell"><span class="detail-arrow" title="查看"><img src="${pageContext.request.contextPath}/resources/img/serviceType/arrow-down.png"></sapn></div>
                                <div></div>    
                            </div>
                        </div>
                    </div> -->
                    <!-- <div class="table-responsive">
                        <table class="table table-hover property-table" id="simulateQuotaApplyTable">
                            <thead>
                                <tr>
                                    <th style="width: 250px;text-align: left;padding-left: 20px;">申请单号</th>
                                    <th style="width: 400px;">申请配额</th>
                                    <th style="width: 150px;">申请状态</th>
                                    <th>提交时间</th>
                                    <th>生效时间</th>
                                    <th style="width: 100px;text-align: right;padding-right: 20px;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>532502559156342784</td>
                                    <td><div><span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span><span class="quota-inline"><span class="quota-inline-key">内存</span><span class="quota-inline-value">12G</span></span><span class="quota-inline"><span class="quota-inline-key">硬盘</span><span class="quota-inline-value">120G</span></span></div></td>
                                    <td>通过</td>
                                    <td>2019-01-09 10:15:03</td>
                                    <td>2019-01-09 10:15:23</td>
                                    <td><span class="detail-arrow" title="查看"><img src="${pageContext.request.contextPath}/resources/img/serviceType/arrow-down.png"></sapn></td>
                                </tr>
                                <tr>
                                    <td colspan="6" class="prev-order-detail">
                                        <div class="detail-container">
                                            <div class="detail-item">
                                                <div class="title">订单编号</div>
                                                <div class="value">95983164987955552320</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="title">订单申请人</div>
                                                <div class="value">admin</div>
                                            </div>
                                            <div class="detail-item" style="width:30%;">
                                                <div class="title">订单提交时间</div>
                                                <div class="value">2018-11-08 15:32:42</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="title">服务名称</div>
                                                <div class="value">undefined</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="title">计费方式</div>
                                                <div class="value">流程1</div>
                                            </div>
                                            <div class="detail-item" style="width:10%;text-align: right;">
                                                <div class="title">订单状态</div>
                                                <div class="value">通过</div>
                                            </div>
                                            <div class="detail-one-line">
                                                <div class="title">基本配置</div>
                                                <div class="value">
                                                    <span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span>
                                                    <span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span>
                                                    <span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span>
                                                </div>
                                            </div>
                                            <div class="detail-one-line">
                                                <div class="title">购买量</div>
                                                <div class="value">
                                                    <span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span>
                                                    <span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span>
                                                    <span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>532502559156342784</td>
                                    <td><div><span class="quota-inline"><span class="quota-inline-key">CPU</span><span class="quota-inline-value">12个</span></span><span class="quota-inline"><span class="quota-inline-key">内存</span><span class="quota-inline-value">12G</span></span><span class="quota-inline"><span class="quota-inline-key">硬盘</span><span class="quota-inline-value">120G</span></span></div></td>
                                    <td>通过</td>
                                    <td>2019-01-09 10:15:03</td>
                                    <td>2019-01-09 10:15:23</td>
                                    <td><sapn class="quota-apply-see" title="查看"></sapn></td>
                                </tr>
                            </tbody>
                        </table>
                    </div> -->
                    <div class="table-responsive">
                        <table class="table table-hover" id="quotaApplyTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 配额申请模态框 -->
    <div class="modal fade" id="projectQuotaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:512px;">
            <div class="modal-content" style="width:512px;">
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#projectQuotaForm')">
                    <a><img src="${pageContext.request.contextPath}/resources/img/serviceType/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/serviceType/type-icon.png">
                    </span>
                    <span class="title">配额申请</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 50px;padding-right: 60px;">
                    <input type="text" hidden id="productId">
                    <input type="text" hidden id="orderType">
                    <input type="text" hidden id="pwaysId">
                    <form class="form-horizontal" id="projectQuotaForm">
                        <!-- <div class="form-group">
                            <label class="col-sm-3 control-label">分类名称</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productTypeName">
                            </div>
                        </div> -->
                    </form>
                </div>
                <div class="bconsole-modal-footer">
                    <a class="modal-submit" id="projectQuotaSave">提交</a>
                </div>
            </div>
        </div>
    </div>
    <!-- 详情模态框 -->
    <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document" style="width:512px;">
            <div class="modal-content" style="width:512px;">
                <div class="bconsole-modal-close" data-dismiss="modal" aria-label="Close" onclick="handleResetForm('#projectQuotaForm')">
                    <a><img src="${pageContext.request.contextPath}/resources/img/serviceType/close.png"></a>
                </div>
                <div class="bconsole-modal-title">
                    <span class="green-title-circle title-img-container">
                        <img src="${pageContext.request.contextPath}/resources/img/serviceType/type-icon.png">
                    </span>
                    <span class="title">记录单详情</span>
                </div>
                <div class="bconsole-modal-content" style="padding-left: 50px;padding-right: 15px;">
                    
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/dataModel/summerTab/js/summerTab.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/bootstrapValidator.js"></script>
    <script>
        var quotaTab = $("#quotaTab").tab();
        var curContext = '<%=path%>';
        var curProductId = '${productId}';
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/order/productQuota.js"></script>
</body>
</html>