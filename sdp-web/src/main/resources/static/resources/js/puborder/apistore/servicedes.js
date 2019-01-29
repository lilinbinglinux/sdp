/**
 * Created by niu on 2017/11/13.
 */
var version;
var appId;
var paramStr;
$(document).ready(function () {
    var serVerId = $("#serVerId").val();
    var height = $(document).height();
    $(".basic-grey").css("max-height", height*0.78);
    
    $("#div1-next-button").on('click',function () {
        $("#right-information-div1").css("display","none");
        $("#right-information-div2").css("display","block");
        $("#right-information-div3").css("display","none");
    })

    $("#token-previous-step").on('click',function () {
        $("#right-information-div1").css("display","block");
        $("#right-information-div2").css("display","none");
        $("#right-information-div3").css("display","none");
    })

    $("#getTokenidBtn").bind("click",getTokenId);
    $("#addOrderInterface").bind("click",function(){
        if($("#key_key").val() == "" || $("#key_key").val() == null){
            layer.msg('请生成令牌再进行下一步！', {time: 2000, icon:2});
        }else{
            $("#right-information-div1").css("display","none");
            $("#right-information-div2").css("display","none");
            $("#right-information-div3").css("display","block");
        }
    });

    $("#div3-left-button").on('click',function () {
        $("#right-information-div1").css("display","none");
        $("#right-information-div2").css("display","block");
        $("#right-information-div3").css("display","none");
    })

    $("#div3-right-button").on('click',function () {
        addOrderInterface();
    })

    $("#div3-test-button").on('click',function () {
        testOnline(appId);
    })

    $("#id-limitIp-select").on("change",function () {
        if ($("#id-limitIp-select").val() == 0) {
            $("#id-whitelist-label").css("display","none");
        } else {
            $("#id-whitelist-label").css("display","block");
        }
    })
});

/**
 * 入参示例弹窗
 * @param id
 */
function selectInputParam(id){
    layer.open({
        type: 1,
        title: '示例参数',
        area: ['620px', '440px'],
        content: $("#interfaceinfodiv"),
        shade: 0.1,
        success: function (layero,index) {
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
function getInputParam(id, num){
    var datas;
    $.ajax({
        url : webpath+"/apiStore/getAllByConditionVersionParam",
        type: 'POST',
        async: false,
        data:{
            "serVerId":id,
            "num":num
        },
        success:function(data){
            datas = data;
        }
    })
    return datas;
}

/*function jumpParam(){
    $("#right-information-ifream").attr("src","../wareHouseIframe/paramAndTest");
}*/

/**
 * 获取appId和tokenId
 */
function getTokenId(){
    $.ajax({
        "url":webpath+"/security/getTokenId",
        "type":"GET",
        async : true,
        success: function(data) {
            var str = "appId="+data.appId+"\n"+"token_id="+data.token_id;
            $("#key_key").val(str);
            $("#getTokenidBtn").attr("disabled","disabled");
            appIdtoken = new appIdtoken(data.appId,data.token_id);
            appId = data.appId;
        }
    });
}

/**
 * 申请插库
 */
function addOrderInterface(){
    //插入数据
    $.ajax({
        "url":webpath+"/apiStore/insert",
        "type":"POST",
        async:false,
        data:{
            "orderName" : $("#id-orderName-input").val(),
            "protocal" : $("#id-protocal-select").val(),
            "reqformat" : $("#id-reqformat-select").val(),
            "respformat" : $("#id-respformat-select").val(),
            "applicationId": $("#id-applicationId-select").val(),
            "orderDesc": $("#id-orderdesc-textarea").val(),
            "pubApiId": $("#serId").val(),
            "serVersion": $("#serVerId").val(),
            "limitIp": $("#id-limitIp-select").val(),
            "appId": appId,
            "checkStatus": 0,
            "ipContext": $("#id-whitelist-textarea").val(),
        },
        success: function(data) {
            layer.msg('申请成功！', {time: 1000, icon:1});
        },
        error: function(){
            console.log("添加失败");
        },
    });
}

/**
 * appId及token_id构造参数
 * @param appId
 * @param token_id
 */
function appIdtoken(appId,token_id){
    this.appId = appId;
    this.token_id = token_id;
}

function testOnline(appId){
    paramStr = $("#inputParam-textarea").val();
    version = $("#serVersion").html();
    console.log(appId);
    console.log(version);
    console.log(JSON.stringify(paramStr));

    $.ajax({
        "url":webpath+"/"+version+"/apisEmploy/apis/"+appId,
        "type":"POST",
        async:false,
        data:{
            "appId" : $("#name").val(),
            "paramStr" : JSON.stringify(paramStr),
        },
        success: function(data) {
            console.log(data);
            layer.msg('申请成功！', {time: 1000, icon:1});
        },
        error: function(){
            console.log("添加失败");
        },
    })
}
