/**
 * Created by niu on 2017/10/23.
 */
var pageHeight; //左拉图标高度
var pageWidth;  //左拉图标宽度
var zTreeObj;   //树
var number;     //分页总数
var ders;       //分页内容
var selectPageWidth;    //导航栏位置
$(document).ready(function () {
    initzTree();
    selectPageWidth = $("#id-selectPage-div").width() + 80 + "px";
    pageHeight = document.body.clientHeight / 2 + "px";
    pageWidth = document.body.scrollWidth / 6 + 10 + "px";
    pullLeft();
    x = $("#leftType").css("width");
    $("#pull-left-img").click(function () {
        $("#leftType").animate({width: 'toggle'}, 300);
    })
    $("#pull-left-img").bind("click", function () {
        if ($("#isTypeOpen").val() == "open") {
            pullLeft();
        } else {
            pullRight();
        }
    });
    selectService();
    
    $("#search").click(function () {
        if($("#search-text").attr("type") == "hidden"){
            $("#search-text").attr("type","text");
        }else{
            var context = $("#search-text").val();
            selectService(context);
        }

    });
    //$("#leftType").css('height',$(".panel-body").height());
});

/**
 * 侧边栏默认展开
 */
function pullLeft() {
    //修改图片
    $("#pull-left-img").attr("src", webpath + "/resources/img/puborder/pull-left.png");
    //计算位置
    $(".pull-left").css({
        "top": pageHeight,
        "left": "10px",
    });
    $("#isTypeOpen").attr("value", "close");
}

/**
 * 侧边栏收缩
 */
function pullRight() {
    $("#isTypeOpen").attr("value", "open");
    $("#pull-left-img").attr("src", webpath + "/resources/img/puborder/pull-right.png");
    $(".pull-left").css({
        "top": pageHeight,
        "left": "10px",
    });
    $("#isTypeOpen").attr("value", "open");
}

/**
 * 服务类型——树形展示
 */
function initzTree() {
    var setting = {
        treeObj: null,
        async: {
            enable: true
        },
        callback: {
            onClick:function(e,treeId,treeNode){
                if(!treeNode) return;
                zTreeObj.selectNode(treeNode);
                $.ajax({
                    "url": webpath + "/ServiceType/getAllByCondition",
                    "type": "POST",
                    data: {
                        "parentId": treeNode.serTypeId,
                    },
                    success: function (data) {
                        if (data.length == 0){
                            //当节点为子节点时，查询该节点下服务
                            $("#serviceDisplayUl").empty();
                            $.ajax({
                                "url": webpath + "/apiStore/getAllByCondition",
                                "type": "POST",
                                data: {
                                    "serType": treeNode.serTypeId,
                                },
                                success: function(data){
                                    ders.length = 0;
                                    if (data.length == 0) {
                                        $("#serviceDisplayUl").append("<span>该分类下暂无数据</span>");
                                    }
                                    number = data.length;
                                    ders = data;
                                    getPage(1);
                                    $("#id-selectPage-div").css({"right":selectPageWidth ,"top":"85%"});
                                }
                            })
                        }else{
                            //当节点为父节点时，查询该节点及其子节点下所有服务
                            $("#serviceDisplayUl").empty();
                            number = 0;
                            ders.length = 0;
                            var serTypeIdList = data;
                            for (var i=0; i<serTypeIdList.length; i++){
                                $.ajax({
                                    "url": webpath + "/apiStore/getAllByCondition",
                                    "type": "POST",
                                    data: {
                                        "serType": serTypeIdList[i].serTypeId,
                                    },
                                    success: function(data){
                                        number = data.length + number;
                                        if (data.length > 0) {
                                            for (var j=0; j<data.length; j++) {
                                                ders.push(data[j]);
                                            }
                                        }
                                        getPage(1);
                                        $("#id-selectPage-div").css({"right":selectPageWidth ,"top":"85%"});
                                    }
                                })
                            }
                        }
                    }
                })
            }
        },
        data: {
            simpleData: {
                enable:true,
                idKey:'serTypeId',
                pIdKey:'parentId'
            },
            key:{
                name:'serTypeName'
            },
            async:{
                enable: true
            }
        }
    };

    if (zTreeObj != null) {
        zTreeObj.destroy()
    }
    $.ajax({
        "url": webpath + "/ServiceType/selectFilterDate",
        "type": "POST",
        success: function (data) {
            var datas = new Array();
            if (data != null && data.length > 0) {
                //过滤数据
                for (var i = 0; i < data.length; i++) {
                    var array = data[i].idPath.split("/");
                    if(array[1] == "root" || array[1] == "root2"){
                        datas.push(data[i]);
                    }
                }

                //根节点展开
                for (var i = 0; i < datas.length; i++) {
                    if (datas[i].serTypeId == "root" || datas[i].serTypeId == "root2") {
                        datas[i].open = "true";
                    }
                }
                zTreeObj = $.fn.zTree.init($("#typeDisplayTree"), setting, datas);
            } else {
                layer.msg('暂无数据', {time: 1000, icon: 5});
            }
        }
    });
}

/**
 * 查询服务
 */
function selectService(context) {
    if (context == null|| context == undefined || context == "") {
        $.ajax({
            "url": webpath + "/apiStore/getAllByCondition",
            "type": "POST",
            success: function (data) {
                $("#serviceDisplayUl").empty();
                number = data.length;
                ders = data;
                getPage(1);
                $("#id-selectPage-div").css({"right":selectPageWidth ,"top":"85%"});
            }
        })
    }else {
        $.ajax({
            "url": webpath + "/apiStore/getAllByCondition",
            "type": "POST",
            data: {
                "serName":context,
            },
            success: function (data) {
                $("#serviceDisplayUl").empty();
                number = data.length;
                ders = data;
                getPage(1);
                $("#id-selectPage-div").css({"right":selectPageWidth ,"top":"85%"});
            }
        })
    }
}

/**
 * 首页服务增加
 * @param name
 * @param time
 */
function addLi(name, id, synchFlag, time) {
    var nameAll = name;
    if(name.length > 11){
        name = name.substring(0,12) + " ...";
    }

    var li = "<li>" +
        "<div class='version-provider-wrapper'>" +
        "<div class='thumbnail'>" +
        "<a href='#' onclick=onedit('" + id + "','" + synchFlag + "')>" +
        "<img class='thumb responsive' src='" + webpath + "/resources/img/puborder/api-default.png' alt='服务图标'></a>" +
        "<div class='caption' style='text-align: center'>" +
        "<div><span style='font-size: larger;font-weight: 600;'><a href='#' onclick=onedit('" + id + "','" + synchFlag + "')>" +
        "<span style='font-size: 1.2em'>"+ name + "</span></a></span></div>" +
        "<p></p>" +
        "<div><span style='color: red'>订阅量：" + time + "次</span></div>" +
        "</div></div></div></li>";

    $("#serviceDisplayUl").append(li);
}

/**
 * 翻页
 */
function getPage(pn){
    var dataCount = number; //总数据条数
    var pageSize;   //每页显示条数

    if ((window.screen.height < 1000 || window.screen.width < 1300)){
        pageSize = 8;
    }else if ((window.screen.height < 1000 || window.screen.width < 1300)){
        pageSize = 10;
    }else if ((1000 <= window.screen.height < 2200 || 1300 <= window.screen.width < 1200)){
        pageSize = 18;
    }else if ((1000 <= window.screen.height < 2200 || 1300 <= window.screen.width < 1200)){
        pageSize = 21;
    }else {
        pageSize = 30;
    }

    var pageCount= Math.ceil(dataCount / pageSize);     //总页数
    if(pn==0||pn>pageCount){
        return;
    }
    var ul=$("#serviceDisplayUl");
    ul.empty();
    paintPage(pageCount,pn);   //绘制页码
    var startPage = pageSize * (pn - 1);

    if (pageCount == 1) {     // 当只有一页时
        for (var j = 0; j < dataCount; j++) {
            var countTime;  //统计订阅量
            $.ajax({
                "url": webpath + "/apiStore/getAllByConditionOrderInterface",
                "type": "POST",
                async:false,
                data: {
                    "serId":ders[j].serId,
                },
                success: function (data) {
                    countTime = data.length;
                }
            })
            addLi(ders[j].serName, ders[j].serId, ders[j].synchFlag, countTime);
        }
    }else {      // 当超过一页时
        var e="";
        var endPage = pn<pageCount?pageSize * pn:dataCount;
        for (var j = startPage; j < endPage; j++) {
            var countTime;  //统计订阅量
            $.ajax({
                "url": webpath + "/apiStore/getAllByConditionOrderInterface",
                "type": "POST",
                async:false,
                data: {
                    "serId":ders[j].serId,
                },
                success: function (data) {
                    countTime = data.length;
                }
            })
            addLi(ders[j].serName, ders[j].serId, ders[j].synchFlag, countTime);
        }
    }
}

/**
 * 绘制页码
 */
function paintPage(number,currNum){  //number 总页数,currNum 当前页
    var pageUl=$(".pagination");
    pageUl.empty();
    var ulDetail="";

    if(number==1){
        ulDetail= "<li class='disabled'><a href='javascript:void(0)'>上页</a></li>" +
            "<li><a href='javascript:getPage(1)'>1</a></li>" +
            "<li class='disabled'><a href='javascript:void(0)'>下页</a></li>";
    }else if(number==2){
        ulDetail= "<li><a href=\"javascript:getPage(1)\">上页</a></li>"+
            "<li class='" +choosele(currNum,1)+ "'><a href=\"javascript:getPage(1)\">1</a></li>"+
            "<li class='" +choosele(currNum,2)+ "'><a href=\"javascript:getPage(2)\">2</a></li>"+
            "<li><a href=\"javascript:getPage(2)\">下页</a></li>";
    }else if(number==3){
        ulDetail= "<li><a href='javascript:getPage(1)'>首页</a></li>" +
            "<li><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">上页</a></li>"+
            "<li class=''"+choosele(currNum,1)+"'><a href='javascript:getPage(1)'>1</a></li>"+
            "<li class=''"+choosele(currNum,2)+"'><a href='javascript:getPage(2)'>2</a></li>"+
            "<li class=''"+choosele(currNum,3)+"'><a href='javascript:getPage(3)'>3</a></li>"+
            "<li><a href=\"javascript:getPage("+parseInt(currNum+1)+")\">下页</a></li>" +
            "<li><a href=\"javascript:getPage("+parseInt(number)+")\">末页</a></li>";
    }else if(number==currNum&&currNum>3){
        ulDetail= "<li><a href='javascript:getPage(1)'>首页</a></li>" +
            "<li><a href='javascript:getPage("+parseInt(currNum-1)+")'>上页</a></li>"+
            "<li><a href='javascript:void(0)'>...</a></li>" +
            "<li><a href='javascript:getPage("+parseInt(currNum-2)+")'>"+parseInt(currNum-2)+"</a></li>"+
            "<li><a href='javascript:getPage("+parseInt(currNum-1)+")'>"+parseInt(currNum-1)+"</a></li>"+
            "<li class='active'><a href='javascript:getPage("+currNum+")'>"+currNum+"</a></li>"+
            "<li class='disabled'><a href='javascript:getPage("+currNum+")'>下页</a></li>" +
            "<li class='disabled'><a href='javascript:getPage("+parseInt(number)+")'>末页</a></li>";
    }else if(currNum==1&&number>3){
        ulDetail= "<li class='disabled'><a href='javascript:getPage(1)'>首页</a></li>" +
            "<li class='disabled'><a href='javascript:void(0)'>上页</a></li>"+
            "<li class='active'><a href='javascript:void(0)'>1</a></li>"+
            "<li><a href='javascript:getPage(2)'>2</a></li>"+
            "<li><a href='javascript:getPage(3)'>3</a></li>" +
            "<li><a href='javascript:void(0)'>...</a></li>"+
            "<li><a href=\"javascript:getPage(2)\">下页</a></li>" +
            "<li><a href='javascript:getPage("+parseInt(number)+")'>末页</a></li>";
    }else{
        ulDetail= "<li><a href='javascript:getPage(1)'>首页</a></li>" +
            "<li><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">上页</a></li>" +
            "<li><a href='javascript:void(0)'>...</a></li>"+
            "<li><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">"+parseInt(currNum-1)+"</a></li>"+
            "<li class='active'><a href=\"javascript:getPage("+currNum+")\">"+currNum+"</a></li>"+
            "<li><a href=\"javascript:getPage("+parseInt(currNum+1)+")\">"+parseInt(currNum+1)+"</a></li>" +
            "<li><a href='javascript:void(0)'>...</a></li>"+
            "<li><a href=\"javascript:getPage("+parseInt(currNum+1)+")\">下页</a></li>" +
            "<li><a href='javascript:getPage("+parseInt(number)+")'>末页</a></li>";
    }

    $(".pagination").append(ulDetail);

}

/**
 * 判断当前页，增添样式
 * @param num
 * @param cur
 * @returns {*}
 */
function choosele(num,cur){
    if(num==cur){
        return "active";
    }else{
        return null;
    }
}

/**
 * 接口详情跳转
 * @param id
 */
function onedit(id, synchFlag){
    var serVerId;
    if(loginId != null && loginId != ""){
        if (synchFlag == "0") {
            window.location.href = webpath+"/apiStore/serviceDes?serId="+id+"&loginId="+loginId;
        } else {
            $.ajax({
                "url": webpath + "/apiStore/getAllByConditionVersion",
                "type": "POST",
                async:false,
                data: {
                    "serId":id
                },
                success: function (data) {
                    serVerId = data[0].serVerId
                }
            });
            var url = webpath + "/apiStore/asynchronized?id=" + id + "&serVerId=" + serVerId +"&loginId="+ loginId;
            window.location.href = url;
        }
    }else{
        if (synchFlag == "0") {
            window.location.href = webpath+"/apiStore/serviceDes?serId="+id;
        } else {
            $.ajax({
                "url": webpath + "/apiStore/getAllByConditionVersion",
                "type": "POST",
                async:false,
                data: {
                    "serId":id,
                },
                success: function (data) {
                    serVerId = data[0].serVerId
                }
            });
            var url = webpath + "/apiStore/asynchronized?id=" + id + "&serVerId=" + serVerId+"&loginId="+ loginId;
            window.location.href = url;
        }
    }
}