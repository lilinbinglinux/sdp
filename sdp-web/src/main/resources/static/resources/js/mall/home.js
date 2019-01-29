$(document).ready(function () {
    //changeWidth();

    $.ajax({
        type: "GET",
        url: $webpath + "/api/product/productInfosByCat",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (productInfosByCat) {
            console.log(productInfosByCat,'导航数据--------------');
            if(productInfosByCat && productInfosByCat[0] != null) {

                $.each(productInfosByCat, function (index, item) {
                    if(item.productResults) {
                        item.productResults = item.productResults.filter(function (it,i) {
                            return it.productStatus == '30';
                        });
                    }
                });
                var arr = productInfosByCat.filter(function (item,index) {
                    return item.productResults != null && ( item.productResults && item.productResults.length != 0 );
                });
                console.log(arr,'筛选后的productInfosByCat');

                sideBarShow(arr);
                productByCataShow(arr);
                newOnlineShow(arr);
            }
        },
        error : function(xhr,textStatus){
            //console.log(xhr, textStatus);
        }
    });

    /*if(!headerVariable.loginId) {
        $('.product-item a, .firstCateItem .proByCateBottom a, .secondCateItem a, .thirdCateItem .proByCateTop a,.online-apiname a').each(function(){
            var curUrl = $(this).attr("href");
            $(this).attr('href', headerVariable.url.casUrl +'/login?service='+ headerVariable.url.projectUrl + curUrl);
        });
    }*/

    // banner
    new Swiper('.banner',{
        loop: true,
        autoplay : 2500,
        pagination: '.banner-pagination',
        paginationClickable: true
    });

    // sideBar
    $('.sideBarTop .glyphicon-indent-right').on('click',function () {
       $('.sideBar .sideBarChild1').hide("slow");
       $('.sideBar .sideBarChild2').show("slow");
    });
    $('.sideBarTop .glyphicon-indent-left').on('click',function () {
        $('.sideBar .sideBarChild2').hide("slow");
        $('.sideBar .sideBarChild1').show("slow");
    });


    // 最新上线
    var swiper = new Swiper('.latest', {
        direction: 'horizontal',
        spaceBetween: 30,
        slidesPerView: 3
    });
    $('.latest .arrow-left').on('click', function(e){
        e.preventDefault();
        swiper.swipePrev();
    });
    $('.latest .arrow-right').on('click', function(e){
        e.preventDefault();
        swiper.swipeNext();
    });

});

// 获取服务logo
function getSvcLogo(productId) {
    var logoUrl = '';
    /*$.ajax({
        type: "GET",
        url: $webpath + "/api/product/singleProduct/"+productId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            logoUrl = data.productLogo;
        }
    });*/
    return logoUrl;
}

function getBannerShow() {
    var str = '',
        imgArr = [
        {
            imgUrl: $webpath+ '/resources/img/mall/banner001.png',
            title: '大数据开放共享生态体系',
            describe:'「大数据+互联网」创客模式，以上百PB级数据储备及动态资源服务为依托，为合作伙伴提供资源、数据、工具等即时服务，支撑「大众创新、万众创业」的新里程。',
            url: ''
        },
        {
            imgUrl: $webpath+ '/resources/img/mall/banner002.png',
            title: '容器服务灵活部署',
            describe: '一键式构建容器镜像，快速部署容器化服务新选择，简化云上自动化容器运行环境的搭建，打造云上使用Docker的一体化体验。',
            url: ''
        },
        {
            imgUrl: $webpath+ '/resources/img/mall/banner003.png',
            title: '多维度模型在线构建',
            describe: '海量数据支持多维度模型构建，利用数据、算法、模型进行深度数据挖掘。',
            url: ''
        }
    ];
    $.each(imgArr,function (index,item) {
        str += '<div class="swiper-slide" style="background: url('+item.imgUrl+') 50% no-repeat;">\n' +
            '    <h1>'+item.title+'</h1>\n' +
            '    <p>'+item.describe+'</p>\n' +
            '    <a href="">立即体验</a>\n' +
            '</div>';
    });
    $('.banner .swiper-wrapper').html(str);
}

function sideBarShow(productInfosByCat) {
    var sideBarStr = '',
        hotSvcStr = "";
    $.each(productInfosByCat, function (index, item) {
        if(item.productResults) {

            /**
             * sideBar
             * */
            sideBarStr += '<li>\n' +
                '    <a href="'+$webpath+'/v1/mall/productsList?cataId='+item.productTypeId+'" class="product-cate">'+item.productTypeName+'</a>\n' +
                '    <div class="product-item">';
            $.each(item.productResults, function (i, it) {
                sideBarStr += '<a href="'+$webpath+'/v1/mall/productDetails?svcId='+it.productId+'" title="'+it.productName+'">'+it.productName+'</a>';
            });
            sideBarStr += '</div></li>';

            /**
             * 热门服务
             * */
            hotSvcStr += '<div class="svc-item">\n' +
                '    <div class="hotSvcCat">\n' +
                '        <img src="' + $webpath + '/resources/img/mall/cate_img.png" alt="">\n' +
                '        <a href="' + $webpath + '/v1/mall/productsList?cataId=' + item.productTypeId + '">' + item.productTypeName + '</a>\n' +
                '    </div>';
            var hotSvcArr = item.productResults.sort(function (a,b) {
                return b.orderSuccessCtn-a.orderSuccessCtn;
            });
            $.each(hotSvcArr.slice(0, 2), function (i, it) {
                hotSvcStr += '<div class="hotSvcInfo">\n' +
                    '    <a href="' + $webpath + '/v1/mall/productDetails?svcId=' + it.productId + '">\n' +
                    '        <div class="svc-name" title="' + it.productName + '">' + it.productName + '</div>\n' +
                    '        <img class="img-lazy" src="'+$webpath+'/resources/img/mall/pro_item.png" data-original="data:image/jpg;base64,'+getSvcLogo(it.productId)+'" alt="">\n' +
                    '        <div class="orderNum">\n' +
                    '            订购 <span>' + it.orderSuccessCtn + '</span> 次\n' +
                    '        </div>\n' +
                    '    </a>\n' +
                    '</div>';
            });
            hotSvcStr +='</div>';

        }
    });

    if(productInfosByCat.length !== 0) {
        $('.banner .sideBar').show();
        $('.sideBar .sideBarBottom').html(sideBarStr);
        $('#hotSvc').html(hotSvcStr);
        $("img.img-lazy").lazyload({
            placeholder : $webpath+"/resources/img/mall/default_img.png",
            effect : "fadeIn",
            container: $("#hotSvc")
        });

        var liHeight = 325 - $(".sideBarBottom").outerHeight();
        if(liHeight > 0) {
            $('.sideBar .sideBarBottom').append('<li style="height: '+liHeight+'px;background: hsla(0,0%,93%,.3);"></li>')
        }
    }else{
        $('.banner .sideBar').hide();
    }

}

// 最新上线
function newOnlineShow(productInfosByCat) {
    var str = "",
        imgArr = [
            { imgUrl : $webpath+ '/resources/img/mall/pro_item01.png' },
            { imgUrl : $webpath+ '/resources/img/mall/pro_item02.png' },
            { imgUrl : $webpath+ '/resources/img/mall/pro_item03.png' },
            { imgUrl : $webpath+ '/resources/img/mall/pro_item04.png' },
            { imgUrl : $webpath+ '/resources/img/mall/pro_item05.png' }
        ];
    var newImgArr = imgArr.concat(imgArr),
        newData = [];
    $.each(productInfosByCat, function (index, item) {
        if(item.productResults) {
            $.each(item.productResults, function (i, it) {
                newData.push(it);
            });
        }
    });
    var newSortArr = newData.sort(function (a,b) {
        return b.createDate-a.createDate;
    });

    $.each(newSortArr.slice(0, 10), function (i, it) {
        //var icon = "data:image/jpg;base64,"+getSvcLogo(it.productId);
        var icon = newImgArr[i].imgUrl;
        str += '<div class="swiper-slide">\n' +
            '    <div class="online-module">\n' +
            '        <img src="" data-original="'+icon+'" class="online-apiimg" alt="">\n' +
            '        <div class="online-apiname">\n' +
            '            <a class="svcName" href="'+$webpath+'/v1/mall/productDetails?svcId='+it.productId+'" title="'+it.productName+'">' + it.productName + '</a>\n' +
            '            <p>' + textOverflowHiding(it.productIntrod) + '</p>\n' +
            '        </div>\n' +
            '        <div class="online-apinumtext">订购\n' +
            '            <div class="online-apinum">' + it.orderSuccessCtn + '</div>次\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>';
    });

    if(productInfosByCat.length !== 0) {
        $('.index-online').css('display','block');
        $('#newServer').html(str);
        $("img").lazyload({
            placeholder : $webpath+"/resources/img/mall/default_img.png",
            effect : "fadeIn",
            failurelimit : 20,
            container: $("#newServer")
        });
    }else{
        $('.index-online').css('display','none');
    }
}

function productByCataShow(productInfosByCat) {
    var firstStr = '', secondStr = '', thirdStr = '';
    /**
     * 每个服务类型最多展示5个服务
     * */
    var allCateArr = productInfosByCat.filter(function (item,index) {
        return item.productResults && item.productResults.length != 0;
    });
    var firstImgArr = [
        { imgUrl : $webpath+ '/resources/img/mall/pro1.png' },
        { imgUrl : $webpath+ '/resources/img/mall/pro2.png' },
        { imgUrl : $webpath+ '/resources/img/mall/pro3.png' },
        { imgUrl : $webpath+ '/resources/img/mall/pro4.png' },
        { imgUrl : $webpath+ '/resources/img/mall/pro5.png' }
    ];
    var secondImgArr = [
        { imgUrl : $webpath+ '/resources/img/mall/second1.png' },
        { imgUrl : $webpath+ '/resources/img/mall/second2.png' },
        { imgUrl : $webpath+ '/resources/img/mall/second3.png' },
        { imgUrl : $webpath+ '/resources/img/mall/second4.png' }
    ];
    var thirdImgArr = [
        { imgUrl : $webpath+ '/resources/img/mall/cate_img.png' },
        { imgUrl : $webpath+ '/resources/img/mall/cate_img1.png' },
        { imgUrl : $webpath+ '/resources/img/mall/cate_img2.png' },
        { imgUrl : $webpath+ '/resources/img/mall/cate_img3.png' },
        { imgUrl : $webpath+ '/resources/img/mall/cate_img.png' }
    ];
    /**
     * 第一个服务类型
     * */
    if(allCateArr.length > 0) {
        $('.firstCateItem').show();
        $('.firstCateItem .content-title').html(allCateArr[0].productTypeName);
        var firstData = allCateArr[0].productResults.sort(function (a,b) {
            return b.orderSuccessCtn-a.orderSuccessCtn;
        });
        $.each(firstData.slice(0, 5),function (i,it) {
            firstStr += '<li>\n' +
                '                <div class="proByCateTop">\n' +
                '                    <img src="'+firstImgArr[i].imgUrl+'" alt="">\n' +
                '                </div>\n' +
                '                <div class="proByCateBottom">\n' +
                '                    <h4 title="'+it.productName+'">'+it.productName+'</h4>\n' +
                '                    <div>\n' +
                '                        <p>'+textOverflowHiding(it.productIntrod)+'</p>\n' +
                '                    </div>\n' +
                '                    <a href="'+$webpath+'/v1/mall/productDetails?svcId='+it.productId+'">立即申请</a>\n' +
                '                </div>\n' +
                '            </li>';
        });
        $('.firstCateItem ul').html(firstStr);
    }

    /**
     * 第二个服务类型
     * */
    if(allCateArr.length > 1) {
        $('.secondCateItem').show();
        $('.secondCateItem .content-title').html(allCateArr[1].productTypeName);
        var secondData = allCateArr[1].productResults.sort(function (a,b) {
            return b.orderSuccessCtn-a.orderSuccessCtn;
        });
        $.each(secondData.slice(0, 4),function (i,it) {
            secondStr += '<li>\n' +
                '             <a href="'+$webpath+'/v1/mall/productDetails?svcId='+it.productId+'">\n' +
                '                <div class="proByCateTop">\n' +
                '                    <img src="'+secondImgArr[i].imgUrl+'" alt="">\n' +
                '                </div>\n' +
                '                <div class="proByCateBottom">\n' +
                '                    <h4 title="'+it.productName+'">'+it.productName+'</h4>\n' +
                '                    <p>'+textOverflowHiding(it.productIntrod)+'</p>\n' +
                '                </div>\n' +
                '            </a>\n' +
                '            </li>';
        });
        $('.secondCateItem ul').html(secondStr);
    }

    /**
     * 第三个服务类型
     * */
    if(allCateArr.length > 2) {
        $('.thirdCateItem').show();
        $('.thirdCateItem .content-title').html(allCateArr[2].productTypeName);
        var thirdData = allCateArr[2].productResults.sort(function (a,b) {
            return b.orderSuccessCtn-a.orderSuccessCtn;
        });
        $.each(thirdData.slice(0, 5),function (i,it) {
            thirdStr += '<li>\n' +
                '                <div class="proByCateTop">\n' +
                '                <a href="'+$webpath+'/v1/mall/productDetails?svcId='+it.productId+'">\n' +
                '                    <img src="'+thirdImgArr[i].imgUrl+'" alt="">\n' +
                '                </a>\n' +
                '                </div>\n' +
                '                <div class="proByCateBottom">\n' +
                '                    <h4 title="'+it.productName+'">'+it.productName+'</h4>\n' +
                '                    <p>'+textOverflowHiding(it.productIntrod)+'</p>\n' +
                '                </div>\n' +
                '            </li>';
        });
        $('.thirdCateItem ul').html(thirdStr);
    }
}


function changeWidth() {
    if (window.innerWidth < 1124) {
        $('.header-top-inner,.banner-bottom-innner,.online-content,.content-body').css('width','100%');
        $('.sideBar').css('left','0');
        $('.login-out').css('right','0');
    } else {
        $('.header-top-inner,.banner-bottom-innner,.online-content,.content-body').css('width','85%');
        $('.sideBar').css('left','7.5%');
        $('.login-out').css('right','7.5%');
    }
    window.onresize = function(){
        if (window.innerWidth < 1124) {
            $('.header-top-inner,.banner-bottom-innner,.online-content,.content-body').css('width','100%');
            $('.sideBar').css('left','0');
            $('.login-out').css('right','0');
        } else {
            $('.header-top-inner,.banner-bottom-innner,.online-content,.content-body').css('width','85%');
            $('.sideBar').css('left','7.5%');
            $('.login-out').css('right','7.5%');
        }
    };
}

// 多行文本溢出隐藏
function textOverflowHiding(productIntrod) {
    var str = '';
    var maxwidth = 50;
    if(productIntrod.length>maxwidth){
        str = productIntrod.substring(0,maxwidth) + '...';
    }else{
        str = productIntrod;
    }
    return str;
}
