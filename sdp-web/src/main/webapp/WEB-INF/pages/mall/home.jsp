<%--
  Created by IntelliJ IDEA.
  User: xumeng
  Date: 2018/6/12
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/swiper/Swiper-2.7.6/idangerous.swiper.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/mall-common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/footer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mall/home.css">
</head>
<body>
<div>
    <%@ include file="header.jsp"%>

    <%--banner--%>
    <div class="swiper-container banner">
        <div class="swiper-wrapper">
            <div class="swiper-slide" style="background: url('${pageContext.request.contextPath}/resources/img/mall/banner001.png') 50% no-repeat;">
                <h1>大数据开放共享生态体系</h1>
                <p>「大数据+互联网」创客模式，以上百PB级数据储备及动态资源服务为依托，为合作伙伴提供资源、数据、工具等即时服务，支撑「大众创新、万众创业」的新里程。</p>
                <a href="">立即体验</a>
            </div>
            <div class="swiper-slide" style="background: url('${pageContext.request.contextPath}/resources/img/mall/banner002.png') 50% no-repeat;">
                <h1>容器服务灵活部署</h1>
                <p>一键式构建容器镜像，快速部署容器化服务新选择，简化云上自动化容器运行环境的搭建，打造云上使用Docker的一体化体验。</p>
                <a href="">立即体验</a>
            </div>
            <div class="swiper-slide" style="background: url('${pageContext.request.contextPath}/resources/img/mall/banner003.png') 50% no-repeat;">
                <h1>多维度模型在线构建</h1>
                <p>海量数据支持多维度模型构建，利用数据、算法、模型进行深度数据挖掘。</p>
                <a href="">立即体验</a>
            </div>
        </div>
        <div class="pagination banner-pagination"></div>

        <%--Sidebar--%>
        <div class="sideBar" style="display: none">
            <div class="sideBarChild1">
                <div class="sideBarTop">
                    <h4>产品分类</h4>
                    <span class="glyphicon glyphicon-indent-right"></span>
                </div>
                <ul class="sideBarBottom">
                    <%--<li>
                        <a href="" class="product-cate">云数据库</a>
                        <div class="product-item">
                            <a href="">mysql</a>
                            <a href="">ftp</a>
                            <a href="">redis</a>
                        </div>
                    </li>--%>
                </ul>
            </div>
            <div class="sideBarTop sideBarChild2" style="display: none;position: absolute;top: 0;left: 0">
                <span class="glyphicon glyphicon-indent-left" style="float:left"></span>
            </div>
        </div>

        <div class="banner-bottom">
            <ul class="banner-bottom-innner">
                <li>
                    <div>安全、高效、稳定的云计算能力</div>
                    <p>丰富多样的大数据计算框架，海量数据批量计算、复杂逻辑关联、流式数据处理、高并发低延迟海量数据处理。</p>
                </li>
                <li>
                    <div>优质、多维度的数据服务能力</div>
                    <p>
                        一键获取标签数据产品
                        <br/>
                        快速推送脱敏样例数据
                        <br/>
                        多方位用户特征行为API接口
                    </p>
                </li>
                <li>
                    <div>海量、标准化的API服务能力</div>
                    <p>基于大数据，提供标准化API服务，提供征信、位置、偏好等几十种接口的对外开放。</p>
                </li>
                <li>
                    <div>简单、易用的容器云服务能力</div>
                    <p>通过Docker引擎实现基于应用程序的快速部署，可以打包应用以及依赖包到一个可移植容器中。</p>
                </li>
                <li>
                    <div>高效、实用的可视化服务能力</div>
                    <p>能力开放平台为租户提供丰富的生产工具和组件，可视化快速构建灵活、高效的数据模型。</p>
                </li>
            </ul>
        </div>
    </div>


    <%--第一个服务类型--%>
    <div class="firstCateItem" style="display: none">
        <div class="content-body">
            <div class="content-header">
                <div class="content-title"></div>
            </div>
            <ul>
                <%--<li>
                    <div class="proByCateTop">
                        <img src="${pageContext.request.contextPath}/resources/img/mall/pro1.png" alt="">
                    </div>
                    <div class="proByCateBottom">
                        <h4>mysql</h4>
                        <div>
                            <p>开源数据库，作为开源软件组合LAMP中的重要一环，广泛应用于各类应用场景。</p>
                        </div>
                        <a href="">立即申请</a>
                    </div>
                </li>--%>
            </ul>
        </div>
    </div>


    <%--第二个服务类型--%>
    <div class="secondCateItem" style="display: none">
        <div class="content-body">
            <div class="content-header">
                <div class="content-title"></div>
            </div>
            <ul>
                <%--<li>
                    <div class="proByCateTop">
                        <img src="${pageContext.request.contextPath}/resources/img/mall/second1.png" alt="">
                    </div>
                    <div class="proByCateBottom">
                        <h4>mysql</h4>
                        <p>开源数据库，作为开源软件组合LAMP中的重要一环，广泛应用于各类应用场景。</p>
                    </div>
                </li>--%>
            </ul>
        </div>
    </div>

    <%--第三个服务类型--%>
    <div class="thirdCateItem" style="display: none">
        <div class="content-body" >
            <div class="content-header">
                <div class="content-title"></div>
            </div>
            <ul>
                <li>
                    <div class="proByCateTop">
                        <img src="${pageContext.request.contextPath}/resources/img/mall/pro_item01.png" alt="">
                    </div>
                    <div class="proByCateBottom">
                        <h4>mysql</h4>
                        <p>开源数据库，作为开源软件组合LAMP中的重要一环，广泛应用于各类应用场景。</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <%--热门服务--%>
    <div class="content-body" style="display: none">
        <div class="content-header">
            <div class="content-title">热门产品</div>
        </div>
        <div id="hotSvc" class="row">
            <%--<div class="svc-item">
                <div class="hotSvcCat">
                    <img src="${pageContext.request.contextPath}/resources/img/mall/cate_img.png" alt="">
                    <a href="">容器云</a>
                </div>
                <div class="hotSvcInfo">
                    <a href="">
                        <div class="svc-name">mysql</div>
                        <img src="${pageContext.request.contextPath}/resources/img/mall/cate_img.png" alt="">
                        <div class="orderNum">
                            订购 <span>0</span> 次
                        </div>
                    </a>
                </div>
                <div class="hotSvcInfo"></div>
            </div>--%>
        </div>
    </div>

    <%--最新上线--%>
    <div class="index-online">
        <div class="online-title">&lt; 最新上线 &gt;</div>
        <div>
            <div class="online-content">
                <div class="swiper-container latest" style="height: 260px">
                    <div id="newServer" class="swiper-wrapper">
                        <%--<div class="swiper-slide">
                            <div class="online-module">
                                <img src="${pageContext.request.contextPath}/resources/img/mall/cate_img.png" class="online-apiimg" alt="">
                                <div class="online-apiname">
                                    <div>云数据库</div>
                                    <div style="font-size: 12px;color:#868488;padding-top: 10px">mysql</div>
                                </div>
                                <div class="online-apinumtext">订阅
                                    <div class="online-apinum">1</div>次
                                </div>
                            </div>
                        </div>--%>
                    </div>
                    <span class="glyphicon glyphicon-chevron-left arrow-left"></span>
                    <span class="glyphicon glyphicon-chevron-right arrow-right"></span>
                </div>
            </div>
        </div>
    </div>

    <%--马上注册--%>
    <div class="toRegister">
        <h3 style="margin-bottom: 45px;">马上注册，更多精选体验等你来尝试！</h3>
        <a href="">立即注册</a>
    </div>

    <%@ include file="footer.jsp"%>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery.lazyload.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/swiper/Swiper-2.7.6/idangerous.swiper.js"></script>
<script type="text/javascript">
  var $webpath= "${pageContext.request.contextPath}";
  <%--var productInfosByCat = ${productInfosByCat};--%>
  $('.header').addClass('home-page-header').removeClass('other-page-header');
</script>
<script src="${pageContext.request.contextPath}/resources/js/mall/header.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mall/home.js"></script>
</html>
