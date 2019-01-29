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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/puborder/warehouse/servicedes.css"
          type="text/css"/>
</head>

<body>
<div class="row">
    <div class="col-lg-12 col-md-12 row-tab">
        <div id="org-panel" class="panel panel-default common-wrapper">
            <div class="panel-heading common-part">
                <form class="bs-example bs-example-form" role="form">
                    <span style="font-size: 2rem; font-weight: 600;color: #cc3333;padding-left: 3rem; line-height: 100%">服务商城 — ${serName}</span>
                    <input id="serVerId" value="${serVerId}" type="hidden"/>
                    <input id="serId" value="${serId}" type="hidden"/>
                </form>
            </div>

            <div class="panel-body common-content" style="padding: 0%">
                <!-- 网页左边栏 -->
                <div id="leftType" class="col-lg-4 col-md-4">
                    <div style="height: 16em;width: 100%;line-height:12em;" align="center">
                        <img src="<%=webpath %>/resources/img/puborder/api-default.png"
                             style=" vertical-align: middle;">
                        <span style="display: block;font-size: 1.8em;font-weight: 600;">${serName}</span>
                    </div>

                    <div class="information">
                        <div class="col-lg-6 col-md-6">
                            <p><span class="information-title-left">版本信息：</span><span
                                    class="information-context-left" id="serVersion">${serVersion}</span></p>
                            <p><span class="information-title-left">发布者：</span><span
                                    class="information-context-left">${loginId}</span></p>
                            <p><span class="information-title-left">当前状态：</span><span class="information-context-left">发布</span>
                            </p>
                            <p><span class="information-title-left">服务编码：</span><span
                                    class="information-context-left">${serCode}</span></p>
                            <p><span class="information-title-left">接口描述：</span></p>
                            <p><span class="information-title-left">&nbsp;</span></p>
                            <p><span class="information-title-left">&nbsp;</span></p>
                        </div>
                        <div class="col-lg-6 col-md-6">
                            <p><span class="information-title-right">订阅量：</span><span
                                    class="information-context-right">${countTime}</span></p>
                            <p><span class="information-title-right">调用方式：</span><span
                                    class="information-context-right">${synchFlag}</span></p>
                            <p><span class="information-title-right">服务类型：</span><span
                                    class="information-context-right">${serType}</span></p>
                        </div>
                    </div>
                    <div>
                        <textarea style="width: 80%;height: 8em;margin-left: 2.5em" readonly>${serResume}</textarea>
                    </div>
                </div>

                <!-- 网页右边栏 -->
                <div class="col-lg-8 col-md-8" style="height: 100%;padding: 0%;">
                    <div class="right-information-div">
                        <!-- 基本信息 -->
                        <div id="right-information-div1" style="display: block;">
                            <div id="" action="" method="post" class="basic-grey test-1" style="width: 500px; overflow-y:auto;">
                                <h1>基本信息
                                    <span>
                                        <span class="title-information-span">基本信息</span> |
                                        <span>接口生成</span> |
                                        <span>参数及测试</span>
                                    </span>
                                </h1>

                                <label>
                                    <span>订阅名称 :</span>
                                    <input id="id-orderName-input" type="text" name="id-orderName-input" placeholder="请对订阅的服务命名"/>
                                </label>

                                <label>
                                    <span>请求协议 :</span><select id="id-protocal-select" name="id-protocal-select">
                                    <option value="http">HTTP</option>
                                    <option value="webservice">webservice</option>
                                    <option value="socket">socket</option>
                                </select>
                                </label>

                                <label>
                                    <span>请求参数格式 :</span><select id="id-reqformat-select" name="id-reqformat-select">
                                    <option value="json">JSON</option>
                                    <option value="xml">XML</option>
                                </select>
                                </label>

                                <label>
                                    <span>响应参数格式 :</span><select id="id-respformat-select" name="id-respformat-select">
                                    <option value="json">JSON</option>
                                    <option value="xml">XML</option>
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

                                <label>
                                    <span>是否限制IP :</span><select id="id-limitIp-select" name="id-limitIp-select">
                                    <option value="0">不限制IP</option>
                                    <option value="1">限制IP</option>
                                </select>
                                </label>

                                <label id="id-whitelist-label" style="display: none;">
                                    <span>白名单 :</span>
                                    <textarea id="id-whitelist-textarea" name="id-whitelist-textarea" placeholder="请输入准许访问的IP，并用逗号隔开"></textarea>
                                </label>

                                <label>
                                    <span>说明 :</span>
                                    <textarea id="id-orderdesc-textarea" name="id-orderdesc-textarea" placeholder="请描述您的订阅服务"></textarea>
                                </label>

                                <label>
                                    <span>&nbsp;</span>
                                    <input id="div1-next-button" type="button" class="btn btn-danger" value="下一步"/>
                                </label>
                            </div>
                        </div>

                        <!-- 令牌生成 -->
                        <div id="right-information-div2" style="display: none;">
                        <div id="security-set" class="basic-grey" style="width: 500px;">
                            <h1>令牌生成
                                <span>
                                        <span>
                                        <span>基本信息</span> |
                                        <span class="title-information-span">令牌生成</span> |
                                        <span>参数及测试</span>
                                    </span>
                                    </span>
                            </h1>

                            <div>
                                <button type="button" class="btn btn-danger" id="getTokenidBtn" style="margin-top:25px">点击生成令牌</button>
                            </div>
                            <div>
                                <textarea id="key_key" class="form-control" style="margin-top:50px;width:100%;background-color: white;" readonly></textarea>
                            </div>
                            <div class="divbtn">
                                <button type="button" class="btn btn-danger"  id="token-previous-step">上一步</button>
                                <button type="button" class="btn btn-danger"  id="addOrderInterface" style="float: right">下一步</button>
                            </div>
                        </div>
                        </div>


                        <!-- 参数设置 -->
                        <div id="right-information-div3" style="display: none;">
                            <div class="basic-grey" style="width: 500px;overflow-y:auto;">
                                <h1>参数及测试
                                    <span>
                                        <span>
                                        <span>基本信息</span> |
                                        <span>接口生成</span> |
                                        <span class="title-information-span">参数及测试</span>
                                    </span>
                                    </span>
                                </h1>

                                <h4>入参值录入：<span>（<a id="param-inputParam-a" onclick="selectInputParam('${serVerId}')" href="javascript:void(0);">入参示例</a>）</span>
                                </h4>
                                <textarea id="inputParam-textarea" style="width: 100%;height: 7em;"></textarea>


                                <h4>出参展示：</h4>
                                <textarea style="width: 100%;height: 7em;" readonly></textarea>


                                <input id="div3-left-button" type="button" class="btn btn-danger" value="上一步"/>
                                <input id="div3-test-button" type="button" class="btn btn-danger" value="在线测试"
                                       style="margin-left: 8em"/>
                                <input id="div3-right-button" type="button" class="btn btn-danger" value="申请"
                                       style="float: right"/>
                            </div>

                            <div id="interfaceinfodiv" style="display:none;" class="panel-body">
                                <div style="margin-top: 5%;">
                                    请求参数格式：
                                    <textarea rows="5" cols="98" id="urlreqparam-code" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
                                </div>
                                <div style="margin-top: 2%">
                                    响应参数格式：
                                    <textarea rows="5" cols="98" id="urlresponseparam-code" readonly style="background-color: whitesmoke; margin-top: 2%;overflow-y:scroll"></textarea>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="../../common-js.jsp" %>
<script src="<%=webpath %>/resources/js/puborder/apistore/servicedes.js"></script>
</body>
</html>