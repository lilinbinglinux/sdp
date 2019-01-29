var manager;
$(document).ready(function () {
    initFlowchartTable();
    // 为datatable外的父级设置高度
    $('#id-flowchartTable_wrapper').css('height', $('.panel-body').height() - 60);
    // 动态为表格添加父级
    $('#id-flowchartTable').wrap('<div class="tab-wrapper"></div>');
    $('.tab-wrapper').niceScroll({cursorcolor: "#ccc", horizrailenabled: false});

    $("#id-layoutaddBtn").click(function () {
        //location.href = webpath + "/pubFlowChart/index";
        location.href = webpath + "/serprocess/index?serFlowType="+$("#typeId").val()+"&serFlowTypeName="+$("#typeName").val() +"&typeId="+typeId+"&typeName="+typeName;
    });

    $("#id-searchBtn").click(function(){
		var serName = $("#serName").val();
		var serCode = $("#serCode").val();
		manager.options.parms = {"serName":serName,
				"serCode":serCode};
		manager.reload(1);
	});

    $("#id-layoutimportBtn").bind("click", xmlTodb);

    $('input[id=lefile]').change(function () {
        $('#importFileinput').val($(this).val());
    });
});

function initFlowchartTable(value) {

  console.log("123");
	var version = $("#typeId").val();
	console.log(form.serializeStr($("#id-flowchartSearchForm")));
	var requestUrl="";
	if(version==null||version==""){
		requestUrl+=webpath + "/processTable/getTreeData";
	}else{
		requestUrl+=webpath + "/processTable/v2/getTreeData"
	}
    manager = $("#id-flowchartTable").ligerGrid({
        columns: [
            {display: '名称', name: 'serName', id: 'serName', width: "25%", align: 'center'},
            {display: '版本', name: 'serVersion', width: "15%", type: 'String', align: 'center'},
            {display: '编码', name: 'serCode', width: "15%", type: 'String', align: 'center'},
            //{display: '创建用户', name: 'loginId', width: "15%", type: 'String', align: 'center'},
            {
                display: '创建时间', name: 'creatTime', width: "20%", type: 'String', align: 'center',
                render: function (item) {
                    var date = item.creatTime;
                    //var html = formatDate(date, "yyyy-MM-dd HH:mm:ss");
                    return date;
                }
            },
            {
                display: '操作', width: "23%", align: 'center',
                render: function (item) {
                    var serVerId = item.serVerId;
                    var serId = item.serId;
                    var serName = item.serName;
                    var serType = item.serType;
                    var pushState = item.pushState;

                    var html = '<a href="javascript:void(0);" onclick="updateSerProcess(\'' + serVerId + '\',\'' + serId + '\',\'' + serName + '\',\'' + $("#typeId").val() + '\',\''+$("#typeName").val()+'\')" class="icon-wrap" title="修改"><i class="iconfont i-btn">&#xe66f;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="deleteSerProcess(\'' + serVerId + '\',\'' + serId + '\',\'' + item.parentId + '\',\'' + pushState + '\')" class="icon-wrap" title="删除"><i class="iconfont i-btn">&#xe614;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="dbToxml(\'' + serVerId + '\',\'' + serId + '\',\'' + serName + '\')" class="icon-wrap" title="导出xml"><i class="iconfont i-btn">&#xe612;</i></a>';
                    html += '&nbsp;&nbsp;';
                    html += '<a href="javascript:void(0);" onclick="dbToApiStore(\'' + serId + '\',\'' + serVerId + '\',\'' + pushState + '\')" class="icon-wrap" title="发布到商店"><i class="iconfont i-btn">&#xe66a;</i></a>';
                    return html;
                }
            }
        ],
        width: '100%',
        height: '100%',
        enabledEdit: false,
        rownumbers: false,
        url: requestUrl,
        parms: {"serName":$("#serName").val(),"typeId":typeId,"typeName":typeName,"serCode":$("#serCode").val()},
        usePager: true,
        tree: {
            columnId: 'serName',
            idField: 'serVerId',
            isParent: function (row) {
                if (row.children != undefined && row.children.length > 0) {
                    return true;
                } else {
                    return false;
                }

            }
        },
        onAfterAppend: function () {
            g.collapseAll();
        },
        onSuccess: function (data) {
        		console.log(data);
	        	if(data != null){
	        		var data = data.list;
	        		if(data != null){
	        			for (var i = 0; i < data.length; i++) {
		                    var obj = data[i];
		                    if (obj.children == null || obj.children.length == 0) {
		                        obj.children = null;
		                    }else{
		                        obj.isextend = false;
		                    }
		
		                }
	        		}
	        	}
        }
    });

    manager.collapseAll();

}

//流程图更新
function updateSerProcess(serVerId, serId, serName, serType,serTypeName) {
    var url = webpath + "/serprocess/index?id=" + serId + "&serVerId=" + serVerId
        + '&serFlowType=' + serType+'&serFlowTypeName='+serTypeName;
    window.location.href = url;
}

//流程图删除
function deleteSerProcess(serVerId, serId, parentId, pushState) {
    var poobj = {
        "serVerId": serVerId,
        "serId": serId,
        "parentId": parentId
    };

    //判断状态，当状态为 1 时不推送
    if (pushState == "1"){
        layer.msg('该服务已推送,不可删除！', {time: 1000, icon: 2});
    }else{
        layer.confirm('确认删除？', {
            icon: 3,
            btn: ['是', '否'] //按钮
        }, function (index, layero) {
            $.ajax({
                url: webpath + "/processTable/deleteSerProcess",
                type: 'POST',
                data: {
                    serVerId: serVerId,
                    serId: serId,
                    parentId: parentId
                },
                success: function (data) {
                    reloadTableData();
                    layer.close(index);
                }
            })
        });
    }

}

//------------------------------流程图导入导出--------------------------------
//导出
function dbToxml(serVerId, serId, serName) {
    $.ajax({
        url: webpath + "/serProcessXmlSys/dbToXmlSyc",
        type: 'POST',
        data: {
            "serVerId": serVerId,
            "serId": serId,
            "serName": serName
        },
        success: function (data) {
            if (data.result == "success") {
                window.location.href = webpath + "/serProcessXmlSys/downLoadFile?serName=" + getEncodeStr(data.fileName);
            } else {
                layer.msg('导出失败！请重试', {time: 1000, icon: 2});
            }
        }
    })
}

//导入
function xmlTodb() {
    layer.open({
        type: 1,
        title: '',
        btn: ["确认", "取消"],
        area: ['540px', '210px'],
        content: $("#importFilediv").css("display", "block"),
        shade: 0.1,
        yes: function (index, layero) {
            uploadFile(index);
        }
    });
}
//下载
function uploadFile(index) {
    var file = $("#importFileinput").val();
    var fileName = getFileName(file);
    $.ajaxFileUpload({
        url: webpath + "/serProcessXmlSys/xmlTodbSyc?fileName=" + fileName,
        fileElementId: 'lefile',//文件上传空间的id属性
        dataType: 'json',//返回值类型一般设置为json
        success: function (data) {
            resultdata = data;
            if (data.result == 'success') {
                reloadTableData();
                layer.close(index);
            } else {
                layer.msg('导入失败！请重试', {time: 1000, icon: 2});
                layer.close(index);
            }
        }
    });
}
//获取文件名称
function getFileName(o) {
    var pos = o.lastIndexOf("\\");
    return o.substring(pos + 1);
}


//------------------------------发布到APIManager商店--------------------------------
//发布至api商店
function dbToApiStore(id, serVerId, pushState) {
    if (pushState == "1"){
        layer.msg('该服务已推送!请不要重复推送', {time: 1000, icon: 2});
    }else{
        layer.confirm('确认发布至商店吗？', {
            btn: ['确认', '取消'] //可以无限个按钮
        }, function (index) {
            pushApi(id, serVerId, "");
            layer.close(index);
        }, function (index) {
            layer.close(index);
        });
    }
}

//推送
function pushApi(idflowChartId, serVerId, flowType) {
    if (flowType == "docker" || flowType == "openstack") {
    } else {
        $.ajax({
            url: webpath + '/dockingController/pushESB',
            type: "POST",
            data: {
                "serId": idflowChartId,
                "serVerId": serVerId
            },
            success: function (data) {
                layer.msg('推送成功', {time: 1000, icon: 1});
                console.log(data);
                //initFlowchartTable();
                reloadTableData();
            },
            error: function () {
            	 	layer.msg('推送失败', {time: 1000, icon: 2});
                console.log("推送失败");
            }
        })
    }
}


//刷新数据  true  整个刷新      false 保留当前页刷新
function reloadTableData() {
    //initFlowchartTable();
	manager.reload();
}

