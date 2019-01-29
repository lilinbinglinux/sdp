/**
 * Created by niu on 2017/11/13.
 */
var version;
var paramStr;
var orderId;
var urlparam;
$(document).ready(function () {
    var serId = $("#serId").val();
    var serVerId = $("#serVerId").val();
    var height = $(document).height();

    if(orderName == null || orderName == ""){   //当没有值的时候，回显显示该服务名称
        orderName = serName;
    }
    $("#id-orderName-input").val(orderName);

    $(".basic-grey").css("max-height", height * 0.78);

    $(".name-information-a").on('click', function () {
        $("#right-information-div1").css("display", "block");
        $("#right-information-div2").css("display", "none");
        $("#right-information-div3").css("display", "none");
        $("#right-information-div4").css("display", "none");
    });

    $(".name-paramtest-a").on('click', function () {
        $("#right-information-div1").css("display", "none");
        $("#right-information-div2").css("display", "block");
        $("#right-information-div3").css("display", "none");
        $("#right-information-div4").css("display", "none");
        var datas = getInputParam(serVerId, 0);
        $("#urlreqparam-code").html(datas);
        var datas = getInputParam(serVerId, 1);
        $("#urlresponseparam-code").html(datas);
        $("#urlreqparam").html(urlparam);
    });

    $(".name-codetable-a").on('click', function () {
        $("#right-information-div1").css("display", "none");
        $("#right-information-div2").css("display", "none");
        $("#right-information-div3").css("display", "block");
        $("#right-information-div4").css("display", "none");
    });
    $(".name-parm-a").on('click', function () {
        $("#right-information-div1").css("display", "none");
        $("#right-information-div2").css("display", "none");
        $("#right-information-div3").css("display", "none");
        $("#right-information-div4").css("display", "block");
    });
    $("#return").click(function(){
        window.location.href = webpath+"/apiStore/index";
    });
    $("#returnOrder").click(function(){
        window.location.href = webpath+"/orderInterface/indexMineOrder";
    });
    //参数回显
    if(orderId != null && orderId != ""){
        $("#id-accFreq-input").val(accFreq);
        $("#id-accFreqType-select").val(accFreqType);
        if(limitIp == "0"){
            $("#id-typelist-select").val(limitIp);
        }else {
            var num = parseInt(limitNameType) + 1;
            $("#id-typelist-select").val(num);
            $("#id-typelistcontext-label").css("display","block");
            if(num == 1){
                $("#id-typelistcontext-span").append("白名单：");
            }else {
                $("#id-typelistcontext-span").append("黑名单：");
            }
            $("#id-typelistcontext-textarea").val(ips);
        }

    }

    /**
     * 获取错误码表
     */
    codeTable();

    $("#addOrderInterface").on('click', function () {
        var IPs;
        $("label").remove(".error");
        if ($("#id-typelist-select").val() == "1" || $("#id-typelist-select").val() == "2") {
            //验证IP输入是否正确
            var str = $("#id-typelistcontext-textarea").val();
            if (str.length > 0) {
                IPs = IPSplit(str);
                if (IPs[0] == "false") {
                    return;
                }
            }
        }

        if(orderId == null || orderId == ""){
            //获取appId
            getTokenId();
            //插入订阅表
            addOrderInterface();
            if (orderId == null || orderId == "") {
                layer.alert('订阅失败！<br>请检查服务是否已被该应用订阅', {icon: 2});
            } else {
                //当对IP限制时，插入IP限制表
                if ($("#id-typelist-select").val() == "1" || $("#id-typelist-select").val() == "2") {
                    addSerIPLimit($("#id-typelistcontext-textarea").val(), $("#id-typelist-select").val() - 1);
                }else {
                    layer.msg('申请成功！', {time: 1000, icon: 1},function(){
                        window.location.href = webpath+"/orderInterface/indexMineOrder";
                    });
                }
            }
        }else {
            //删除黑名单白名单表数据
            $.ajax({
                url: webpath + "/whiteList/delete",
                type: "POST",
                async: false,
                data:{
                    orderId: orderId
                }
            });

            //更新订阅表
            updateOrderInterface();

            //插入黑白名单表:当对IP限制时，插入IP限制表
            if ($("#id-typelist-select").val() == "1" || $("#id-typelist-select").val() == "2") {
                addSerIPLimit($("#id-typelistcontext-textarea").val(), $("#id-typelist-select").val() - 1);
                layer.msg('IP限制成功！申请成功！', {time: 1000, icon: 1}, function() {
                    window.location.href = webpath+"/orderInterface/indexMineOrder";
                });
            }else {
                layer.msg('申请成功！', {time: 1000, icon: 1},function(){
                    window.location.href = webpath+"/orderInterface/indexMineOrder";
                });
            }
        }
    });


    $("#id-typelist-select").on("change", function () {
        $("#id-typelistcontext-span").empty();
        if ($("#id-typelist-select").val() == 0) {
            $("#id-typelistcontext-label").css("display", "none");

        } else if ($("#id-typelist-select").val() == 1) {
            $("#id-typelistcontext-span").append("白名单：");
            $("#id-typelistcontext-textarea").attr("placeholder", "请输入准许访问的IP，并用逗号隔开");
            $("#id-typelistcontext-label").css("display", "block");

        } else {
            $("#id-typelistcontext-span").append("黑名单：");
            $("#id-typelistcontext-textarea").attr("placeholder", "请输入禁止访问的IP，并用逗号隔开");
            $("#id-typelistcontext-label").css("display", "block");
        }
    });

    $("#div2-test-button").click(function () {
        testOnline(serId, serVersion);
    });

});

/**
 * 入参示例弹窗
 * @param id
 */
function selectInputParam(id) {
    layer.open({
        type: 1,
        title: '示例参数',
        area: ['620px', '440px'],
        content: $("#interfaceinfodiv"),
        shade: 0.1,
        success: function (layero, index) {
            var data1 = getInputParam(id, 0);
            $("#urlreqparam-code").html(data1);
            var data2 = getInputParam(id, 1);
            $("#urlresponseparam-code").html(data2);
        }
    });
}

/**
 * 获取示例参数
 * @param id
 * @param num
 * @returns {*}
 */
function getInputParam(id, num) {
    var datas;
    $.ajax({
        url: webpath + "/apiStore/getAllByConditionVersionParam",
        type: 'POST',
        async: false,
        data: {
            "serVerId": id,
            "num": num
        },
        success: function (data) {
            datas = data;
        }
    });
    return datas;
}

/**
 * 获取appId和tokenId
 */
function getTokenId() {
    $.ajax({
        "url": webpath + "/security/getTokenId",
        "type": "GET",
        async: false,
        success: function (data) {
            var str = "appId=" + data.appId + "\n" + "token_id=" + data.token_id;
            $("#key_key").val(str);
            $("#getTokenidBtn").attr("disabled", "disabled");
            //appIdtoken = new appIdtoken(data.appId,data.token_id);
            appId = data.appId;
        }
    });
}

/**
 * 分割ip地址
 */
function IPSplit(allIP) {
    var IPs = allIP.split(",");
    var regexp = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
    for (var i = 0; i < IPs.length; i++) {
        var IP = IPs[i];
        if ((IP.toLocaleUpperCase() == "NULL" || IP.toLocaleUpperCase() == "ALL") && IPs.length > 1) {
            var str = "<label class='error'><span>&nbsp;</span><p style='color: red;padding-left: 1em;" +
                "font-weight: 400;width: 100%'>错误：只能有一个 ALL 或 NULL 存在！</p></label>";
            $("#id-typelistcontext-label").append(str);
            IPs = ["false"];
            return IPs;
        }

        if ((!regexp.test(IP)) && IP.toLocaleUpperCase != "NULL" && IP.toLocaleUpperCase() != "ALL") {
            var str = "<label class='error'><span>&nbsp;</span><p style='color: red;padding-left: 1em;" +
                "font-weight: 400;width: 100%'>错误：输入的内容有误，请检查！</p></label>";
            $("#id-typelistcontext-label").append(str);
            IPs = ["false"];
            return IPs;
        }
    }
    return IPs;
}

/**
 * 服务订阅表新增
 */
function addOrderInterface() {
    if ($("#id-accFreq-input").val() == null || $("#id-accFreq-input").val() == "" || $("#id-accFreq-input").val() == undefined) {
        accFreq = null;
        accFreqType = null;
    } else {
        accFreq = $("#id-accFreq-input").val();
        accFreqType = $("#id-accFreqType-select").val();
    }

    if($("#id-typelist-select").val() == "0"){
        limitIp = "0";
    }else {
        limitIp = "1";
    }

    var orderData;
    if(subloginId != null && subloginId != ""){
        orderData={
            "orderName": $("#id-orderName-input").val(),
            "protocal": serAgreement,
            "reqformat": serRequest,
            "respformat": serResponse,
            "applicationId": $("#id-applicationId-select").val(),
            "serId": $("#serId").val(),
            "serVersion": $("#serVersion").val(),
            "limitIp": limitIp,
            "appId": appId,
            "accFreq": accFreq,
            "accFreqType": accFreqType,
            "checkStatus": 0,
            "loginId":subloginId,
            "repFlag":1
        }
    }else{
        orderData={
            "orderName": $("#id-orderName-input").val(),
            "protocal": serAgreement,
            "reqformat": serRequest,
            "respformat": serResponse,
            "applicationId": $("#id-applicationId-select").val(),
            "serId": $("#serId").val(),
            "serVersion": $("#serVersion").val(),
            "limitIp": $("#id-typelist-select").val(),
            "appId": appId,
            "accFreq": accFreq,
            "accFreqType": accFreqType,
            "checkStatus": 0,
            "repFlag":0
        }
    }

    //判断该应用下是否已经订阅过该服务
    $.ajax({
        url: webpath + "/apiStore/selectApplicationRepeated",
        type: "POST",
        async: false,
        data: orderData,
        success: function (data) {
            //当应用下无服务时
            if(data){
                //插入数据
                $.ajax({
                    "url": webpath + "/apiStore/insert",
                    "type": "POST",
                    async: false,
                    data: orderData,
                    success: function (data) {
                        orderId = data;
                    },
                    error: function () {
                        layer.msg('服务订阅表新增失败！', {time: 1000, icon: 2});
                    },
                });
            }
            //当应用已经订阅过服务时
            else {
                layer.msg('本应用已订阅该服务，请重新选择应用订阅！', {time: 1500, icon: 2});
            }
        }
    });


}

/**
 * 服务订阅表更新
 */
function updateOrderInterface() {
    if ($("#id-accFreq-input").val() == null || $("#id-accFreq-input").val() == "" || $("#id-accFreq-input").val() == undefined) {
        accFreq = null;
        accFreqType = null;
    } else {
        accFreq = $("#id-accFreq-input").val();
        accFreqType = $("#id-accFreqType-select").val();
    }

    if($("#id-typelist-select").val() == "0"){
        limitIp = "0";
    }else {
        limitIp = "1";
    }
    var orderData;
    if(subloginId != null && subloginId != ""){
        orderData={
            "orderId":orderId,
            "orderName": $("#id-orderName-input").val(),
            "protocal": serAgreement,
            "reqformat": serRequest,
            "respformat": serResponse,
            "applicationId": $("#id-applicationId-select").val(),
            "serId": $("#serId").val(),
            "serVersion": $("#serVersion").val(),
            "limitIp": limitIp,
            "appId": appId,
            "accFreq": accFreq,
            "accFreqType": accFreqType,
            "checkStatus": 0,
            "loginId":subloginId,
            "repFlag":1
        }
    }else{
        orderData={
            "orderId":orderId,
            "orderName": $("#id-orderName-input").val(),
            "protocal": serAgreement,
            "reqformat": serRequest,
            "respformat": serResponse,
            "applicationId": $("#id-applicationId-select").val(),
            "serId": $("#serId").val(),
            "serVersion": $("#serVersion").val(),
            "limitIp": limitIp,
            "appId": appId,
            "accFreq": accFreq,
            "accFreqType": accFreqType,
            "checkStatus": 0,
            "repFlag":0
        }
    }

    //更新数据
    $.ajax({
        "url": webpath + "/apiStore/update",
        "type": "POST",
        async: false,
        data: orderData,
        success: function (data) {
            if (data > 0){
                layer.msg('服务订阅表更新成功！', {time: 1000, icon: 1});
            }else {
                layer.msg('服务订阅表更新失败！', {time: 1000, icon: 2});
            }
        },
        error: function () {
            layer.msg('服务订阅表更新失败！', {time: 1000, icon: 2});
        },
    });
}

/**
 * IP白名单黑名单表 新增
 */
function addSerIPLimit(ip, type) {
    //插入数据
    $.ajax({
        "url": webpath + "/whiteList/insert",
        "type": "POST",
        async: false,
        data: {
            "appId": appId,
            "orderId": orderId,
            "ipAddrs": ip,
            "nameType": type,
        },
        success: function (data) {
            layer.msg('IP限制成功！申请成功！', {time: 1000, icon: 1}, function() {
                window.location.href = webpath+"/orderInterface/indexMineOrder";
            });
        },
        error: function () {
            console.log("添加失败");
        },
    })
}

/**
 * appId及token_id构造参数
 * @param appId
 * @param token_id
 */
function appIdtoken(appId, token_id) {
    this.appId = appId;
    this.token_id = token_id;
}

/**
 * 在线测试
 * @param serId
 */
function testOnline(serId, serVersion){
    //paramStr = $("#inputParam-textarea").val().replace(/\n/g,"").replace(/\s/g,"").replace(/\\\"/g, "");
    paramStr = $("#inputParam-textarea").val();
    //console.log(paramStr.substr(1, paramStr.length - 2));

    $.ajax({
        "url":webpath+"/apiStore/testOnline",
        "type":"POST",
        async:false,
        data:{
            "serId" : serId,
            "serVersion": serVersion,
            "paramStr" : paramStr,
        },
        success: function(data) {
            console.log(data);
            if(data.respCode == "undefined" || data.respCode == null){
                $("#id-outputParam-textarea").val(data);
            }else{
                var html = "{\"respCode\": \""+data.respCode+"\", "+
                    "\"respDesc\": \""+data.respDesc+"\", "+
                    "\"result\": \""+data.result+"\", "+
                    "\"requestId\": \""+data.requestId+"\"}";
                $("#id-outputParam-textarea").val(html);
            }
        },
        error: function(){
            console.log("调用失败！");
            layer.msg('调用失败！', {time: 1000, icon:2});
        },
    })
}


/**
 * 错误码表展示
 */
function codeTable() {
    $("#id-codetable-div").ligerGrid({
        columns: [
            {display: '错误码(code)', name: 'respCode', width: "50%", align: 'left'},
            {display: '错误描述(description)', name: 'respDesc', width: "48%", align: 'left'}
        ],
        width:'39em',
        height:'100%',
        alternatingRow: false,
        enabledEdit: false,
        url: webpath + "/apiStore/getAllConditionCodeTable",
        usePager: false,
        isScroll: false
    })
}