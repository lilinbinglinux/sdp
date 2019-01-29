<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: niu
  Date: 2017/10/19
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>服务商城</title>
    <%@ include file="../../common-layer-ext.jsp" %>
    <%@ include file="../../common-body-css.jsp" %>

    <link rel="stylesheet"  type="text/css"href="<%=request.getContextPath() %>/resources/css/puborder/warehouse/servicedes.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Aqua/css/ligerui-all.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/skins/Gray2014/css/all.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/common.css">

    <script type="text/javascript">
        var orderId = '${orderId}';
        var accFreq = '${accFreq}';
        var accFreqType = '${accFreqType}';
        var limitIp = '${limitIp}';
        var limitNameType = '${nameType}';
        var ips = '${ips}';
        var appId = '${appId}';
        var urlparam = '${url}';
        var serName = '${serName}';
        var serVerId = '${serVerId}';
        var serVersion = '${serVersion}';
        var serId = '${serId}';
        var serAgreement = '${serAgreement}'; //服务协议
        var serRestype = '${serRestype}';
        var serRequest = '${serRequest}';     //请求参数格式
        var serResponse = '${serResponse}';   //响应参数格式
        var serResponse = '${serResponse}';   //响应参数格式
        var orderName = '${orderName}';
    </script>
    <style>
        #security-set{
            max-height: unset!important;
        }
        .basic_height{
            max-height: unset!important;
        }
    </style>
</head>

<body style="background: white">
<div class="row" style="height:auto;background: white;">
    <div class="col-lg-12 col-md-12 row-tab">
        <div id="org-panel" class="panel panel-default common-wrapper" style="height:95%">
            <div class="panel-heading common-part">
                <form class="bs-example bs-example-form" role="form">
                    <span style="font-size: 2rem; font-weight: 600;color: #cc3333;padding-left: 3rem; line-height: 100%">服务商城 — ${serName}</span>
                    <button type="button" id="returnOrder" class="b-redBtn btn-i" style="float:right;line-height: 100%;"><i class="iconfont">&#xe608;</i>返回</button>
                    <input id="serVerId" value="${serVerId}" type="hidden"/>
                    <input id="serVersion" value="${serVersion}" type="hidden"/>
                    <input id="serId" value="${serId}" type="hidden"/>
                </form>
            </div>

            <div class="common-content" style="padding: 0%;height:auto!important;">
                <!-- 网页左边栏 -->
                <div id="leftType" class="col-lg-4 col-md-4 col-xs-4 col-sm-4" style="height:auto;border: none">
                    <div style="height: 20em;width: 100%;line-height:12em;" align="center">
                        <img src="<%=webpath %>/resources/img/puborder/api-default.png"
                             style=" vertical-align: middle;margin-top: 13%">
                        <span style="display: block;font-size: 1.8em;font-weight: 600;margin-top:5%">${serName}</span>
                    </div>

                    <div id="" action="" method="post" class="basic-grey test-1"
                         style="width: 100%;background-color: #ffffff;border: 0px solid #E4E4E4;height:auto;">
                        <label>
                            <span>订阅名称 :</span>
                            <input id="id-orderName-input" type="text" name="id-orderName-input"/>
                        </label>

                        <label>
                            <span>流量限制 :</span>
                            <input id="id-accFreq-input" type="text" name="id-accFreq-input" placeholder="流量频率限制" style="width: 40%;height: 35px;"/>
                            <select id="id-accFreqType-select" name="id-accFreqType-select" style="width: 17%">
                                <option value="0" selected="selected">/ 秒</option>
                                <option value="1">/ 分</option>
                                <option value="2">/ 时</option>
                                <option value="3">/ 天</option>
                            </select>
                        </label>

                        <label>
                            <span>应用分类 :</span>
                            <select id="id-applicationId-select" name="id-applicationId-select">
                                <c:forEach items="${applicationList}" var="item">
                                    <option value="${item.key}">${item.value}</option>
                                </c:forEach>
                            </select>
                        </label>

                        <label id="id-typelist-label">
                            <span>限制IP类型 :</span><select id="id-typelist-select" name="id-typelist-select">
                            <option value="0" selected="selected">不限制IP</option>
                            <option value="1">输入白名单</option>
                            <option value="2">输入黑名单</option>
                        </select>
                        </label>

                        <label id="id-typelistcontext-label" style="display: none;">
                            <span id="id-typelistcontext-span"></span>
                            <textarea id="id-typelistcontext-textarea" name="id-typelistcontext-textarea"></textarea>
                        </label>

                    </div>
                    <div for="id-goal-span" style="margin-top:20px">
                        <span id="id-goal-span" style="float: left;width: 40%;text-align: right;padding-right: 1em;margin-top: 10px;color: #888;">&nbsp;</span>
                        <button type="button" id="addOrderInterface" class="btn btn-danger">修改订阅</button>
                    </div>

                </div>

                <!-- 网页右边栏 -->
                <div class="col-lg-8 col-md-8 col-xs-8 col-sm-8" style="height:auto;padding: 0%;background: white;border-left: thin solid rgb(191, 191, 191);">
                    <div class="right-information-div">

                        <!-- 基本信息 -->
                        <div id="right-information-div1" style="display: block; height:100%;">
                            <div action="" method="post" class="basic-grey test-1 basic-right basic_height" style="height:100%;overflow-y:auto;">
                                <h1>${serName}
                                </h1>
                                <ul class = "navtab">
                                    <li  class="name-information-a active"><a href="#">基本信息</a></li>
                                    <li class="name-paramtest-a"><a href="#">参数及测试</a></li>
                                    <li class="name-codetable-a"><a href="#">错误码参照</a></li>
                                    <div style="clear:both"></div>
                                </ul>

                                <label>
                                    <span>服务名称 :</span>
                                    <p>${serName}</p>
                                </label>

                                <label>
                                    <span>服务编码 :</span>
                                    <p>${serCode}</p>
                                </label>

                                <label>
                                    <span>创建者 :</span>
                                    <p>${loginId}</p>
                                </label>

                                <label>
                                    <span>创建时间 :</span>
                                    <p>${createDateString}</p>
                                </label>

                                <label>
                                    <span>订阅量 :</span>
                                    <p>${countTime}次</p>
                                </label>

                                <label>
                                    <span>调用方式 :</span>
                                    <p>${synchFlag}</p>
                                </label>

                                <label>
                                    <span>服务协议 :</span>
                                    <p>${serAgreement}</p>
                                </label>

                                <label>
                                    <span>请求方式 :</span>
                                    <p>${serRestype}</p>
                                </label>

                                <label>
                                    <span>请求参数格式 :</span>
                                    <p>${serRequest}</p>
                                </label>

                                <label>
                                    <span>响应参数格式 :</span>
                                    <p>${serResponse}</p>
                                </label>

                                <label>
                                    <span>说明 :</span>
                                    <p>${serResume}</p>
                                </label>

                            </div>
                        </div>

                        <!-- 参数及测试 -->
                        <div id="right-information-div2" style="display: none;height:auto">
                            <div id="security-set" class="basic-grey test-1" style="height:auto;max-height: unset!important;">
                                <h1>${serName}
                                </h1>
                                <ul class = "navtab">
                                    <li  class="name-information-a"><a href="#">基本信息</a></li>
                                    <li class="name-paramtest-a active"><a href="#">参数及测试</a></li>
                                    <li class="name-codetable-a"><a href="#">错误码参照</a></li>
                                    <div style="clear:both"></div>
                                </ul>

                                <h4 style="margin-top: 2%;">入参值录入：
                                </h4>
                                <textarea id="inputParam-textarea" style="width: 50%;height: 7em;"></textarea>

                                <h4>出参展示：</h4>
                                <textarea id="id-outputParam-textarea" style="width: 50%;height: 7em;" readonly></textarea>
                                <input id="div2-test-button" type="button" class="btn btn-danger" style="display:block;" value="在线测试"/>
                                <h4 style="margin-top: 2%;">入参示例：</h4>
                                <div style = "height:auto;width: 50%;overflow-x: hidden; margin-bottom: 1%;">
                                    <div style="margin-top: 2%;">
                                        <h5>url：</h5>
                                        <textarea rows="5" cols="98" id="urlreqparam" readonly style="background-color: whitesmoke; width:100%;overflow-y:scroll"></textarea>
                                    </div>
                                    <div style="margin-top: 2%;">
                                        <h5>请求参数格式：</h5>
                                        <textarea rows="5" cols="98" id="urlreqparam-code" readonly style="background-color: whitesmoke; width:100%;overflow-y:scroll"></textarea>
                                    </div>
                                    <div style="margin-top: 2%">
                                        <h5>响应参数格式：</h5>
                                        <textarea rows="5" cols="98" id="urlresponseparam-code" readonly style="background-color: whitesmoke; width:100%;overflow-y:scroll"></textarea>
                                    </div>
                                </div>
                            </div>

                        </div>


                        <!-- 错误码参照 -->
                        <div id="right-information-div3" style="display: none;height:auto">
                            <div class="basic-grey test-1 basic_height" style="height:100%;">
                                <h1>${serName}
                                </h1>
                                <ul class = "navtab">
                                    <li  class="name-information-a"><a href="#">基本信息</a></li>
                                    <li class="name-paramtest-a"><a href="#">参数及测试</a></li>
                                    <li class="name-codetable-a active"><a href="#">错误码参照</a></li>
                                    <div style="clear:both"></div>
                                </ul>
                                <div id="id-codetable-div" style="width: 500px;"></div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="../../common-js.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugin/ligerui/lib/ligerUI/js/plugins/ligerGrid.js"></script>
<script src="<%=webpath %>/resources/js/puborder/apistore/synchronized.js"></script>
<script>
var subloginId = '${subloginId}';
</script>
</body>
</html>