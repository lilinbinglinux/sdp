var str, i = 0, manager, index;
var apiflowchartid;
$(document).ready(function () {
    //定义全局使用的layer样式
    layer.config({skin: 'layui-layer-ext'});
    index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.style(index, {
        height: ($(".panel-body").height() ) + 'px',
    });
  
    $(".apitab li").each(function () {
        $(this).click(function () {
            $(this).siblings().removeClass("active");
            $(this).addClass("active");
            var menu = $(this).attr("menu");
            $("#" + menu).show();
            $("#" + menu).siblings().hide();
        })
    });
    
    $("#reqdiv").click(function () {
    	changeTag("0",true);
    })

    $("#respdiv").click(function () {
    	changeTag("1",true);
    })
    
     $("#definediv").click(function () {
    	changeTag("",false);
    })
    
   
    //请求和响应参数- 添加
    $("#addParamBtn").on("click", function () {
        $("#parmParentId").val("");
        $("#addParamSetBtn").text("保存");
        $("#paramadd-update").val("");
        $('#paramadd').modal();

        //设置append的div还原
        $("#parammodal-body").attr("style", "overflow-y:visible;");
        if ($("#paramset").find(".deleteparam").length > 0) {
            $("#paramset").find(".jsonparam").each(function (i, e) {
                $(this).remove();
            });
        }
        $(".param-input").val("");
    })
    $("#example-xmlstr").val(str);
    $("#addXmlParamBtn").on("click", addXmlParam);
    $("#addSampleDataBtn").bind("click", addSampleData);

    $("#addparam").bind("click", addInput);
    $("#addParamSetBtn").bind("click", addParam);

    // 为datatable外的父级设置高度
    $('#urlexplainTable_wrapper').css('height', $('.panel-body').height() - 60);
    // 动态为表格添加父级
    $('#urlexplainTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').css('height', $('#urlexplainTable_wrapper').height() - 63);
    $('.tab-wrapper').niceScroll({cursorcolor: "#ccc", horizrailenabled: false});
    $("#addPubBtn").bind("click", addPub);
    $("#cancelPubBtn").bind("click", cancelPub);

    $("input[name=typeId]").click(function () {
        var radioval = $('input[name=typeId]:checked').val();
        if (radioval == 4) {
            $("#typeId_api").attr("style", "display:none");
            $("#typeId_jar").attr("style", "display:block;margin-left: 17px;");
        } else if (radioval == 2) {
            $("#typeId_api").attr("style", "display:block");
            $("#typeId_jar").attr("style", "display:none");
        }
    });

    $("#reqtype").change(function () {
        if ($(this).val() != "String") {
            $(".maxlength").hide();
        } else {
            $(".maxlength").show();
        }
    })


});

//添加注册接口
function addPub(parentId,typeId,pubid) {
    if($("#modaltype").val() == "update"){
        var file = $("#ImportPicInput").val();
        var fileName = getFileName(file);
        var filePath = "";
        var pubid = $("#pubid").val();
        if(fileName != null&&fileName != ""){
            filePath = getDate()+pubid+"/";
        }
        
        if($('input[name=typeId]:checked').val() == "4"){
        	uploadFile(pubid, filePath);
        }
        
        $.ajax({
            "url":webpath+"/publisher/update",
            "type":"POST",
            dataType:"json",
            async:false,
            data:{
                pubid:pubid,
                name:$("#pubname").val(),
                url:$("#puburl").val(),
                pubport:$("#pubport").val(),
                pubprotocal:$("#pubprotocal").val(),
                method:$("#pubmethod").val(),
                reqformat:$("#pubreqformat").val(),
                respformat:$("#pubrespformat").val(),
                reqdatatype:$("#pubreqdatatype").val(),
                returndatatype:$("#pubreturndatatype").val(),
                pubdesc:$("#pubdesc").val(),
                className:$("#className").val(),
                methodInClass:$("#methodInClass").val(),
                filePath:filePath+fileName
            },
            success:function(data){
                $("#ImportPicInput").val("");
                layer.msg('修改成功', {offset: '150px', time: 2000, icon: 2});
            }
        })
    }else{
        var file = $("#ImportPicInput").val();
        var fileName = getFileName(file);
        var filePath = "";
        if ($("#pubid").val() == null || $("#pubid").val() == "") {
            var pubid = getuuid();
        }
        if (fileName != null && fileName != "") {
            filePath = getDate() + pubid + "/";
        }
        if($('input[name=typeId]:checked').val() == "4"){
        	uploadFile(pubid, filePath);
        }
        if(typeId == ""||typeId == null){
        	typeId = $('input[name=typeId]:checked').val();
        }
        if(parentId == ""||parentId == null){
        	parentId = "normalRoot";
        }
        $.ajax({
            "url": webpath + "/publisher/insert",
            "type": "POST",
            dataType: "json",
            data: {
                pubid: pubid,
                name: $("#pubname").val(),
                url: $("#puburl").val(),
                pubport: $("#pubport").val(),
                pubprotocal: $("#pubprotocal").val(),
                method: $("#pubmethod").val(),
                reqformat: $("#pubreqformat").val(),
                respformat: $("#pubrespformat").val(),
                reqdatatype: $("#pubreqdatatype").val(),
                returndatatype: $("#pubreturndatatype").val(),
                pubdesc: $("#pubdesc").val(),
                parentId: parentId,
                typeId: typeId,
                className: $("#className").val(),
                methodInClass: $("#methodInClass").val(),
                filePath: filePath + fileName
            },
            success: function () {
                $("#pubid").val(pubid);
                if($("#apinodeType").val() == "circle"){
                	parent.idflowChartId = pubid;
                	addOrderInterface(parent.idflowChartId);
                }
                parent.$("#success").text(true);
                parent.$("#pubname").text($("#pubname").val());
                parent.$("#pubid").text(pubid);
                console.log($("#pubid").val());
            }
        })
    }
}

function cancelPub() {
    parent.layer.close(index);
}
function getSelected() {
    var row = manager.getSelectedRow();
    if (!row) {
        $("#parmParentId").val("");
    } else {
        $("#parmParentId").val(row.id);
    }
}

function initParamTable(value) {
    manager = $("#reqparamtable").ligerGrid({
        columns: [
            {display: '编码', name: 'ecode', id: 'ecodeName', width: "20%", align: 'left'},
            {display: '描述', name: 'reqdesc', width: "20%", align: 'center'},
            {display: '类型', name: 'reqtype', width: "15%", type: 'String', align: 'center'},
            {
                display: '是否为空', name: 'isempty', align: 'center', width: "10%",
                render: function (item) {
                    var s;
                    if (item.isempty == "1") {
                        s = "是";
                    } else if (item.isempty == "0") {
                        s = "否";
                    }
                    return s;
                }
            },
            {
                display: '操作', width: "20%", align: 'center',
                render: function (item) {
                    var s =
                        "<a href='javascript:void(0)' onclick='updateParams(\"" + item.id + "\")'>修改</a>" + "-"
                        + "<a href='javascript:void(0)' onclick='deleteParams(\"" + item.id + "\")'>删除</a>";
                    if (item.reqtype != ""&&item.reqtype != null&&
                    		(item.reqtype.toLowerCase() == "Object".toLowerCase() || item.reqtype.toLowerCase() == "List".toLowerCase())
                    		) {
                        s =
                            "<a href='javascript:void(0)'  onclick='insertParams(\"" + item.id + "\")'>新增</a>" + "-"
                            + s;
                    }
                    return s;
                }
            },
            {
                display: '排序', width: "10%", align: 'center',
                render: function (item) {
                    var s =
                        "<a href='javascript:void(0)' onclick='upsort(\"" + item.id + "\")'><img src='../resources/img/process/up.png'></a>" + " "
                        + "<a href='javascript:void(0)' onclick='downsort(\"" + item.id + "\")'><img src='../resources/img/process/down.png'></a>";
                    return s;
                }
            },
        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '100%',
        alternatingRow: false,
        enabledEdit: false,
        rownumbers: true,
        data: getParams(),
        tree: {
            columnId: 'ecodeName',
            idField: 'id',
            parentIDField: 'parentId',
            isParent: function (data) {
            	if(data.reqtype != ""&&data.reqtype != null){
            		//设置表格之前箭头
            		if (data.reqtype.toLowerCase() == "Object".toLowerCase() || data.reqtype.toLowerCase() == "List".toLowerCase())
                        return true;
            	}
                return false;
            }
        },
        onSelectRow: function (rowdata, rowindex) {
        },
        usePager: false
    });
}

function getParams() {
    var objdata;
    var jsonStr = form.serializeStr($("#pubidtoreq"));
    var jsonObj = jQuery.parseJSON(jsonStr);
    jsonObj.layout = false;
    jsonStr = JSON.stringify(jsonObj);
    if ($("#pubid").val() != null && $("#pubid").val() != "") {
        $.ajax({
            "url": webpath + "/reqparam/selectParams",
            "type": 'POST',
            data: {
                "jsonStr": jsonStr
            },
            async: false,
            success: function (data) {
                objdata = data;
            }
        })
    }
    return objdata;
}

//参数排序——向上/向下移动
function upsort(id) {
    $.ajax({
        url: webpath + "/reqparam/getParamById",
        type: 'POST',
        data: {
            "id": id,
        },
        success: function (data) {
            $.ajax({
                url: webpath + "/reqparam/upsort",
                type: 'POST',
                data: {
                    "sort": data.sort,
                    "pubid": data.pubid,
                    "id": id,
                    "parentId": data.parentId,
                },
                success: function (data) {
                    if (data == 1) {
                        layer.msg('<p>无法向上！</p><p>请检查是否为同层级或已是最上！</p>', {offset: '150px', time: 2000, icon: 2});
                    } else {
                        initParamTable();
                    }
                }
            });
        }
    });
}

function downsort(id) {
    $.ajax({
        url: webpath + "/reqparam/getParamById",
        type: 'POST',
        data: {
            "id": id,
        },
        success: function (data) {
            $.ajax({
                url: webpath + "/reqparam/downSort",
                type: 'POST',
                data: {
                    "sort": data.sort,
                    "pubid": data.pubid,
                    "id": id,
                    "parentId": data.parentId,
                },
                success: function (data) {
                    if (data == 2) {
                        layer.msg('<p>无法向下！</p><p>请检查是否为同层级或已是最上！</p>', {offset: '150px', time: 2000, icon: 2});
                    } else {
                        manager.reload(1);
                    }
                }
            });
        }
    });
}

function insertParams(id) {
    $("#parammodal-body").attr("style", "overflow-y:visible;");
    if ($("#paramset").find(".deleteparam").length > 0) {
        $("#paramset").find(".jsonparam").each(function (i, e) {
            $(this).remove();
        });
    }

    $("#parmParentId").val(id);
    $("#addParamSetBtn").text("保存");
    $("#paramadd-update").val("");
    $('#paramadd').modal();
    $("#addparam").show();
    var inputs = $(".param-input");
    inputs.val("");
}
function deleteParams(id) {
    layer.confirm('删除该资源？（删除后不可恢复）', {
        icon: 3,
        btn: ['是', '否'],//按钮
        offset: '150px'
    }, function (index, layero) {
        $.ajax({
            "url": webpath + "/reqparam/delete",
            "type": "POST",
            dataType: "json",
            async: false,
            data: {
                "id": id
            },
            success: function (data) {
                if (data) {
                    layer.msg('删除成功！', {offset: '150px', time: 1000, icon: 1});
                    layer.close(index);
                    initParamTable();
                } else if (!data) {
                    layer.msg('请先删除子级参数', {offset: '150px', time: 2000, icon: 2});
                }
            }

        })
    })
}

function updateParams(id) {
    $("#addParamSetBtn").text("修改");
    $("#paramadd").modal();
    $("#addparam").hide();
    $("#paramadd-update").val("1");

    $.ajax({
        "url": webpath + "/reqparam/getParamById",
        "type": "POST",
        dataType: "json",
        async: false,
        data: {
            "id": id
        },
        success: function (data) {
            $("#paramid-set").val(data.id);
            $("#ecode").val(data.ecode);
            $("#reqdesc").val(data.reqdesc);
            $("#reqtype").val(data.reqtype);
            $("#parampos").val(data.parampos);
            $("#maxlength").val(data.maxlength);
            if (data.isempty == "1") {
                $("#isempty").val("1");
            } else if (data.isempty == "0") {
                $("#isempty").val("0");
            }
        }

    })
}

//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData(isCurrentPage){
	$("#reqparamtable").dataTable().fnDraw(isCurrentPage==null?true:isCurrentPage);
}

function addXmlParam() {
    $.ajax({
        "url": webpath + "/reqparam/xmlinsert",
        "type": "POST",
        dataType: "json",
        async: false,
        data: {
            "pubid": $("#pubid").val(),
            "xmlStr": $("#xmlstr").val()
        },
        success: function (data) {
            layer.msg('添加成功', {offset: '150px', time: 3000, icon: 1});
            $("#definediv").removeClass("active");
            $("#reqdiv").addClass("active");
            $("#id-paraminfotable").css("display", "block");
            $("#id-definedparamtable").css("display", "none");
            $("#type").val("0");
            initParamTable();
        }
    })
}

function showsampledata() {
    $.ajax({
        "url": webpath + "/publisher/getPubById",
        "type": "POST",
        async: false,
        data: {
            "id": parent.paramifream_pubid
        },
        success: function (data) {
            if (data.sampledata != "") {
                $("#sampledata").val(data.sampledata);
                $("#addSampleDataBtn").text("修改");
            }
        }
    })
}

function addSampleData() {
    $.ajax({
        "url": webpath + "/publisher/update",
        "type": "POST",
        async: false,
        data: {
            "pubid": $("#pubid").val(),
            "sampledata": $("#sampledata").val()
        },
        success: function (data) {
            layer.msg('添加成功', {time: 3000, icon: 1})
            $("#baseinfo").addClass("active");
            $("#reqdiv").removeClass();
            $("#respdiv").removeClass();
            $("#sampledatadiv").removeClass();
            $("#id-paraminfotable").css("display", "block");
            $("#id-sampledata").css("display", "none");
            $("#type").val("0");
            initParamTable();
        }
    })
}

function mouseOver() {
    layer.tips('如果有请求参数请提供测试数据，保证申请时可在线测试该接口', '#id-icontipdata', {
        tips: [1, '#EEC591'] //可配置颜色
    });
}

function reloadTableData(isCurrentPage) {
    $("#reqparamtable").dataTable().fnDraw(isCurrentPage == null ? true : isCurrentPage);
}

str =
    "<params>                                             " +
    "  <type>request</type>                               " +
    "  <allparams>                                        " +
    "    <param>                                          " +
    "      <ecode>content</ecode>                         " +
    "      <reqdesc>内容</reqdesc>                        " +
    "      <reqtype>Object</reqtype>                      " +
    "      <isempty>0</isempty>                           " +
    "      <subparam>                                     " +
    "        <listParam>                                  " +
    "          <param>                                    " +
    "            <ecode>contactCode</ecode>               " +
    "            <reqdesc>接触点号</reqdesc>              " +
    "            <reqtype>Object</reqtype>                " +
    "            <isempty>0</isempty>                     " +
    "            <subparam>                               " +
    "              <listParam>                            " +
    "                <param>                              " +
    "                  <ecode>channelId</ecode>           " +
    "                  <reqdesc>渠道id1111</reqdesc>      " +
    "                  <reqtype>String</reqtype>          " +
    "                  <isempty>0</isempty>               " +
    "                </param>                             " +
    "                <param>                              " +
    "                  <ecode>channelId222</ecode>        " +
    "                  <reqdesc>渠道id</reqdesc>          " +
    "                  <reqtype>String</reqtype>          " +
    "                  <isempty>0</isempty>               " +
    "                </param>                             " +
    "              </listParam>                           " +
    "            </subparam>                              " +
    "          </param>                                   " +
    "        </listParam>                                 " +
    "      </subparam>                                    " +
    "    </param>                                         " +
    "    <param>                                          " +
    "      <ecode>message</ecode>                         " +
    "      <reqdesc>返回信息</reqdesc>                    " +
    "      <reqtype>String</reqtype>                      " +
    "      <isempty>0</isempty>                           " +
    "    </param>                                         " +
    "  </allparams>                                       " +
    "</params>                                            ";


function addInput() {
    var input = "<div style='margin-top: 10px;' class='jsonparam form-inline jsonparamdiv'>" +
        "<label class='control-label'>参数::</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +
        "<label class='control-label'>说明:</label><input class='form-control form-input param-input' style='width:80px' type='text'/>" +
        "<label class='control-label'>参数类型:</label>" +
        "<select class='param-input form-input' style='width:80px'> " +
        "<option value='String'>String</option>     " +
        "<option value='int'>int</option>           " +
        "<option value='boolean'>boolean</option>   " +
        "<option value='Object'>Object</option>     " +
        "<option value='List'>List</option>         " +
        "</select>" +
        "  <label class=\"control-label\">参数位置:</label>" +
        " <select id=\"parampos\" class='param-input form-input' style=\"width:80px;height: 25px;\"> " +
        "       <option value=\"0\" selected = \"selected\">请求体</option>" +
        "      <option value=\"4\">请求头</option>" +
        "      <option value=\"1\">url上的参数</option>" +
        "      <option value=\"2\">响应头</option>" +
        "      <option value=\"3\">响应体</option>" +
        "      <option value=\"5\">xml请求体属性</option>" +
        "      <option value=\"6\">xml响应体属性</option>" +
        "</select>" +
        "<label class=\"control-label maxlength\">最大长度:</label>" +
        "<input class=\"form-control param-input maxlength\" style=\"width:80px\" type=\"text\" id=\"maxlength\"/>" +
        "<label class='control-label'>是否必传项:</label>" +
        "<select class='param-input form-input' style='width:80px'>" +
        "<option value='0'>否</option> " +
        "<option value='1'>是</option> " +
        "</select>" +
        "<button type='button' class='btn btn-default deleteparam' aria-label='Left Align' >" +
        "<span class='glyphicon glyphicon-minus' aria-hidden='true'></span>" +
        "</button>" +
        "</div>";
    $("#paramset").append(input);

    $("#paramset").find(".deleteparam").each(function (i, e) {
        $(this).unbind("click");
        $(this).bind("click", function () {
            $(this).parent().remove();
            if ($("#allparamset").height() < $("#parammodal-body").height()) {
                $("#parammodal-body").attr("style", "overflow-y:visible;");
            }
        })
    });

    if ($("#parammodal-body").height() > 400) {
        $("#parammodal-body").attr("style", "overflow-y:scroll;height:40%");
    }
}
//响应参数添加 之后的结果
function addParam() {
    var divinput = $(".jsonparam");
    var objarr = new Array();

    var ecode = $("#ecode").val();
    var reqdesc = $("#reqdesc").val();
    var reqtype = $("#reqtype").val();
    var parampos = $("#parampos").val();
    var isempty = $("#isempty").val();
    var type = $("#type").val();
    var maxlength = $("#maxlength").val();

    if ($("#paramadd-update").val() == "1") {
        $.ajax({
            "url": webpath + "/reqparam/update",
            "type": "POST",
            async: false,
            data: {
                "id": $("#paramid-set").val(),
                "ecode": ecode,
                "reqdesc": reqdesc,
                "reqtype": reqtype,
                "parampos": parampos,
                "isempty": isempty,
                "type": type,
                "maxlength": maxlength
            },
            success: function (data) {
                manager.reload();
                initParamTable();
            }
        })
    } else {
        var parentId = $("#parmParentId").val();
        var reqobj1 = new reqObj(ecode, reqdesc, reqtype, parampos, isempty, type, maxlength, parentId);
        objarr.push(reqobj1);
        for (var i = 0; i < divinput.length; i++) {
            var arr = new Array();
            $(divinput[i]).find(".form-input").each(function (i, e) {
                arr.push($(this).val());
            })
            var reqobj = new reqObj(arr[0], arr[1], arr[2], arr[3], arr[4], type, arr[5], parentId);
            objarr.push(reqobj);
        }

        $.ajax({
            "url": webpath + "/reqparam/insert",
            "type": "POST",
            dataType: "json",
            async: false,
            data: {
                "reqobj": JSON.stringify(objarr),
                "pubid": $("#pubid").val()

            },
            success: function (data) {
                initParamTable();
            }

        })
    }
}
function reqObj(ecode, reqdesc, reqtype, parampos, isempty, type, maxlength, parentId) {
    this.ecode = ecode;
    this.reqdesc = reqdesc;
    this.reqtype = reqtype;
    this.parampos = parampos;
    this.isempty = isempty;
    this.type = type;
    this.maxlength = maxlength;
    this.parentId = parentId;
}
//获取文件名称
function getFileName(o) {
    var pos = o.lastIndexOf("\\");
    return o.substring(pos + 1);
}
function uploadFile(pubid, filePath) {
    $.ajaxFileUpload({
        type: "POST",
        url: webpath + "/file/importPicFile.do?pubid=" + pubid + "&filePath=" + filePath,
        secureuri: false,//是否启用安全提交，默认为false
        fileElementId: 'ImportPicInput',//文件选择框的id属性
        dataType: 'json',//服务器返回的格式
        async: false,
        success: function (data) {
            if (data.result == 'success') {
                //coding
            } else {
                //coding
            }
        }
    });

}

function changeTag(type,tablefalg){

	$("#clickTag").val("1");
	if($("#pubname").val() == ""||$("#pubname").val() == null){
		layer.msg('请填写服务名称！', {time: 1000, icon:2});
	}else{
		//开始按钮上的菜单操作
		if($("#apinodeType").val() == "circle"){
			if(parent.idflowChartId == ""||parent.idflowChartId == null){
    			$("#modaltype").val("");
    			//添加服务
    			var fid = getuuid();
    			addPub("ROOT1","3",fid);
    			
    			$("#type").val(type);
    			initParamTable();

    		}else if(parent.idflowChartId != ""&&parent.idflowChartId != null){
    			//开始按钮有值，直接显示
    			$("#type").val(type);
    			if(tablefalg){
    				initParamTable();
    			}
    		}
		}
		
		//矩形
		else if($("#apinodeType").val() == "rectangle"){
			if($("#pubid").val() == ""||$("#pubid").val() == null){
    			//添加接口
				$("#modaltype").val("");
    			addPub("normalRoot",$(":radio[name='typeId']").val());
    			$("#type").val(type);
    			if(tablefalg){
    				initParamTable();
    			}
    		}
    		else{
    			$("#type").val(type);
    			if(tablefalg){
    				initParamTable();
    			}

    		}
		}
	}
}


//实现订阅（在注册的时候调该方法，使默认订阅）
function addOrderInterface(id) {
    var appIdtoken = getTokenId();
    var stylecode;
    //插入数据
    $.ajax({
        "url": webpath + "/interfaceOrder/insertorderinterface",
        "type": "POST",
        async: false,
        data: {
            "name": $("#pubname").val(),
            "ordercode": $("#punname").val(),
            "reqformat": $("#pubreqformat").val(),
            "respformat": $("#respformat").val(),
            "pubapiId": id,
            "type": "2",
            "appId": appIdtoken.appId,
            "token_id": appIdtoken.token_id,
            "stylecode":stylecode
        },
        success: function (data) {
            //$(".diagram_title").text($("#flowChartName").val());
        }
    });

}

function getTokenId() {
    var appIdtoken;
    $.ajax({
        "url": webpath + "/security/getTokenId",
        "type": "GET",
        async: false,
        success: function (data) {
            appIdtoken = {
                "appId": data.appId,
                "token_id": data.token_id
            }
        }
    });
    return appIdtoken;
}
