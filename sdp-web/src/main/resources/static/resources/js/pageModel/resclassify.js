var resTypeId = "root" ;
var filterCount = 1;
var editTypeId = "root";
var clickResTypeName = 'root';
var setting = {
  view: {
    addHoverDom: addHoverDom,
    removeHoverDom: removeHoverDom,
    selectedMulti: false
  },
  edit: {
    enable: true,
    drag: {
      isCopy: false,
      isMove: false
    },
    showRemoveBtn: showRemoveBtn,
    showRenameBtn: showRenameBtn

  },
  data: {
    simpleData: {
      enable: true
    }
  },
  async: {
    enable: true,
    type: "get",
    url: getAsyncUrl,
    dataFilter:  function(treeId, parentNode, data) {
      var nodes = data.nodes;

      for ( var i = 0; i < nodes.length; i++ ){
        if ( nodes[i].parent ) {
          nodes[i].isParent = true;
        }
      }

      if ( filterCount == 1 ){
        nodes.splice(0,0,{ id: "root", pId:0, name:"资源分类", open: true, isParent: true, parent: true });
        filterCount++;
      }
      if (!data) return null;
      return nodes;
    }
  },
  callback: {
    beforeEditName: beforeEditName,
    beforeRemove: beforeRemove,
    onClick: onClick,
    onAsyncError: onAsyncError,
    onAsyncSuccess: onAsyncSuccess
  }
};

function beforeEditName(treeId, treeNode) {
  var zTree = $.fn.zTree.getZTreeObj("treeDemo");
  zTree.selectNode(treeNode);
  editNode( treeNode.id );
  return false;
}
function showRemoveBtn(treeId, treeNode) {
  return !treeNode.isParent && treeNode.tId != "treeDemo_1";
}
function showRenameBtn(treeId, treeNode) {
  return treeNode.id != 'root';
}
function beforeRemove(treeId, treeNode) {
  var f = false;
  if ( confirm("确认删除 节点 :  " + treeNode.name + " 吗？") ) {
    $.ajax({
      type: "delete",
      async: false,   // 太关键了，学习了，同步和异步的参数
      url: path+"/v1/pageResType/restype/delete/"+treeNode.id,
      success: function(data){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if ( data.code == 201 ){
          f = true;
          var preNodes = treeNode.getParentNode();
          if (!preNodes.children) { preNodes.isParent = false; preNodes.parent = false; zTree.updateNode(preNodes); }
        }else {
          alert(data.message);
        }
      }
    });
  }
  return f ;
}
function onClick(event, treeId, treeNode, clickFlag) {
  resTypeId = treeNode.id;
  clickResTypeName = treeNode.id == 'root' ? 'root' : treeNode.name;
  if ( treeNode.children === undefined ){
    refreshNode( {data: {type:"refresh", silent:false}} );
  }
  table.ajax.reload();

  console.log("[ "+getTime()+" onClick ]-------clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
  console.log(treeNode.id);
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
  //console.log("[ "+getTime()+" onAsyncError ]-------" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
  //console.log("[ "+getTime()+" onAsyncSuccess ]-------" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
}
function addHoverDom(treeId, treeNode) {
  var sObj = $("#" + treeNode.tId + "_span");

  if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0
    || $("#editBtn_" + treeNode.tId).length > 0
    || $("#delBtn_" + treeNode.tId).length > 0)
    return;
  var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
    + "' title='add node' onclick='openAddModal(\""+treeNode.id+"\")' onfocus='this.blur();'></span>";
  sObj.after(addStr);
};
function removeHoverDom(treeId, treeNode) {
  $("#addBtn_"+treeNode.tId).unbind().remove();
};

function refreshNode(e) {
  //console.log('refreshnode---------------------------')
  var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
    type = e.data.type,
    silent = e.data.silent,
    nodes = zTree.getSelectedNodes();

  if (nodes.length == 0) {
    alert("请先选择一个父节点");
  }
  for (var i=0, l=nodes.length; i<l; i++) {
    zTree.reAsyncChildNodes(nodes[i], type, silent);
    if (!silent) zTree.selectNode(nodes[i]);
  }
}

function getAsyncUrl( treeId , treeNode ) {
  var id = (treeNode != undefined) ? treeNode.id : "root";
  var url = path+"/v1/pageResType/restype/detail/"+ id;
  //console.log(url);
  return url;
}

function openAddModal(id) {

  $("#myModal").modal('show');
  document.getElementById("addNodeForm").reset();
  $("#resTypeNameError").html("");
  $("#sortIdError").html("");

  //console.log('openAddModal---------------');
}

function addNodeFormBtn() {

  if (checkAddNodeForm()){
    var jsonData = {};
    var t = $('#addNodeForm').serializeArray();

    jsonData.resParentId = resTypeId;

    for ( var i = 0; i < t.length; i++ ) {
      jsonData[ t[i].name ] = t[i].value;
    }
    //console.log('addNode---------------')
    $.ajax({
      type: "post",
      url: path+"/v1/pageResType/restype",
      data: { "resTypeName": jsonData.resTypeName, "resParentId": jsonData.resParentId, "pubFlag": jsonData.pubFlag, "sortId": jsonData.sortId },
      success: function(data){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if ( data.code == 201 ){
          var nodes = zTree.getSelectedNodes();
          if (!nodes[0].isParent) { nodes[0].isParent = true; nodes[0].parent = true; zTree.updateNode(nodes[0]); }
          refreshNode( {data: {type:"refresh", silent:false}} );
        }else {
          alert("添加节点失败！");
        }
        $("#myModal").modal('hide');
      }
    });
  }

}

function editNode(id) {
  $("#myEditModal").modal('show');

  $("#resTypeName1Error").html("");
  $("#sortId1Error").html("");

  //编辑回显
  $.ajax({
    type: "get",
    url: path+"/v1/pageResType/restype/"+id,
    success: function(data){
      if (data){
        document.getElementById("resTypeName1").value = data.resTypeName;
        data.pubFlag == 0 ? document.getElementById("pubFlag2").checked = true : document.getElementById("pubFlag1").checked = true;
        document.getElementById("sortId1").value = data.sortId.toString();
      }
    }
  });

  editTypeId = id;

}

function editNodeFormBtn() {

  if (checkEditNodeForm()){
    var jsonData = {};
    var t = $('#editNodeForm').serializeArray();

    jsonData.resParentId = editTypeId;

    for ( var i = 0; i < t.length; i++ ) {
      jsonData[ t[i].name ] = t[i].value;
    }

    $.ajax({
      type: "put",
      url: path+"/v1/pageResType/restype/update/"+editTypeId,
      data: { "resTypeName": jsonData.resTypeName, "resParentId": jsonData.resParentId, "pubFlag": jsonData.pubFlag, "sortId": jsonData.sortId },
      success: function(data){
        //console.log(data)
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if ( data.code == 201 ){
          var nodes = zTree.getSelectedNodes();
          nodes[0].name = jsonData.resTypeName;
          zTree.updateNode(nodes[0]);
        }else {
          alert("修改节点失败！");
        }
        $("#myEditModal").modal('hide');
      }
    });
  }
}

//修改资源信息（模态框）
function editResModal(resId){
  $("#editResourceModal").modal("show");

  $("#editResSortIdError").html("");
  $("#editResNameError").html("");

  $.ajax({
    type: "get",
    url: path+"/v1/pageRes/res/"+resId,
    success: function(data){
      if (data){
        document.getElementById("resId").value = data.resId;
        document.getElementById("resTypeId").value = data.resTypeId;
        document.getElementById("editResName").value = data.resName;
        document.getElementById("editResType"+data.resType).checked = true;
        document.getElementById("editResSortId").value = data.sortId.toString();
      }
    }
  });
}

//删除资源信息
function deleteResModal(resId) {
  if (confirm("确定要删除该条资源吗？")){
    $.ajax({
      type: "delete",
      url: path+"/v1/pageRes/res/delete/"+resId,
      success: function(data){
        if (data.code == 201){
          table.ajax.reload();
        }else {
          alert(data.message);
        }
      }
    });
  }
}

var sortIdReg = /^[0-9]*$/;//数字正则
//表单验证（新增分类）
function checkAddNodeForm(){
  var resTypeName = document.getElementById('resTypeName').value;
  var sortId = document.getElementById('sortId').value;

  if (resTypeName == ''){
    $("#resTypeNameError").html("资源分类名称不能为空！");
    return false;
  }else {
    $("#resTypeNameError").html("");
  }

  if (sortId == ''){
    $("#sortIdError").html("排序不能为空！");
    return false;
  }else if(!sortIdReg.test(sortId)){
    $("#sortIdError").html("排序只能是数字格式！");
    return false;
  }else {
    $("#sortIdError").html("");
  }

  return true;
}

//表单验证（编辑分类）
function checkEditNodeForm(){
  var resTypeName1 = document.getElementById('resTypeName1').value;
  var sortId1 = document.getElementById('sortId1').value;

  if (resTypeName1 == ''){
    $("#resTypeName1Error").html("资源分类名称不能为空！");
    return false;
  }else {
    $("#resTypeName1Error").html("");
  }

  if (sortId1 == ''){
    $("#sortId1Error").html("排序不能为空！");
    return false;
  }else if(!sortIdReg.test(sortId1)){
    $("#sortId1Error").html("排序只能是数字格式！");
    return false;
  }else {
    $("#sortId1Error").html("");
  }

  return true;
}

//表单验证（新增资源）
function checkAddResourceForm(){
  var resName = document.getElementById('resName').value;
  var uploadFile = document.getElementById('uploadFile').value;
  var addResSortId = document.getElementById('addResSortId').value;

  if (resName == ''){
    $("#resNameError").html("资源名称不能为空！");
    return false;
  }else {
    $("#resNameError").html("");
  }

  if (uploadFile == ''){
    $("#uploadFileError").html("上传文件不能为空！");
    return false;
  }else {
    $("#uploadFileError").html("");
  }

  if (addResSortId == ''){
    $("#addResSortIdError").html("排序不能为空！");
    return false;
  }else if(!sortIdReg.test(addResSortId)){
    $("#addResSortIdError").html("排序只能是数字格式！");
    return false;
  }else {
    $("#addResSortIdError").html("");
  }

  return true;
}

//表单验证（修改资源）
function checkEditResourceForm(){
  var editResName = document.getElementById('editResName').value;
  //var editUploadFile = document.getElementById('editUploadFile').value;
  var editResSortId = document.getElementById('editResSortId').value;

  if (editResName == ''){
    $("#editResNameError").html("资源名称不能为空！");
    return false;
  }else {
    $("#editResNameError").html("");
  }

  if (editResSortId == ''){
    $("#editResSortIdError").html("排序不能为空！");
    return false;
  }else if(!sortIdReg.test(editResSortId)){
    $("#editResSortIdError").html("排序只能是数字格式！");
    return false;
  }else {
    $("#editResSortIdError").html("");
  }

  return true;
}

function getTime() {
  var now= new Date(),
    h=now.getHours(),
    m=now.getMinutes(),
    s=now.getSeconds(),
    ms=now.getMilliseconds();
  return (h+":"+m+":"+s+ " " +ms);
}

$(document).ready(function(){
  $.fn.zTree.init($("#treeDemo"), setting );

  $('#addResForm').submit(function (event) {
    event.preventDefault();
    if(checkAddResourceForm()){
      var form = $(this);
      // mulitipart form,如文件上传类
      var formData = new FormData(this);
      $.ajax({
        type: form.attr('method'),
        url: form.attr('action')+resTypeId,
        data: formData,
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false
      }).success(function (data) {
        var obj = JSON.parse(data);
        if ( obj.code == 201 ){
          table.ajax.reload();
          //console.log(obj.message);
        }else {
          alert(obj.message);
        }

        document.getElementById("addResForm").reset();
        $("#addResourceModal").modal('hide');
      });
    }
  });

  $('#editResForm').submit(function (event) {
    event.preventDefault();//禁用默认动作

    if (checkEditResourceForm()){
      var resId = document.getElementById("resId").value;
      var form = $(this);
      // mulitipart form,如文件上传类
      var formData = new FormData(this);
      $.ajax({
        type: form.attr('method'),
        url: form.attr('action')+resId,
        data: formData,
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false
      }).success(function (data) {
        var obj = JSON.parse(data);
        if ( obj.code == 201 ){
          table.ajax.reload();
          //console.log(obj.message);
        }else {
          alert(obj.message);
        }

        $("#editResourceModal").modal('hide');
      });
    }
  });
});

//datatables相关js
var language = {
  "sProcessing":   "处理中...",
  "sLengthMenu":   "每页 _MENU_ 项",
  "sZeroRecords":  "没有匹配结果",
  "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
  "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
  "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
  "sInfoPostFix":  "",
  "sSearch":       "搜索:",
  "sUrl":          "",
  "sEmptyTable":     "表中数据为空",
  "sLoadingRecords": "载入中...",
  "sInfoThousands":  ",",
  "oPaginate": {
    "sFirst":    "首页",
    "sPrevious": "上页",
    "sNext":     "下页",
    "sLast":     "末页",
    "sJump":     "跳转"
  },
  "oAria": {
    "sSortAscending":  ": 以升序排列此列",
    "sSortDescending": ": 以降序排列此列"
  }
};

var columns = [
  {
    "data": null //此列不绑定数据源，用来显示序号
  },
  {
    "data": "resName"
  },
  {
    "data": "resTypeId"
  },
  {
    "data": "createDate"
  },
  {
    "data": null
  }
];

var columnDefs = [
  {
    "targets": 0,
    "orderable": false,
    "searchable": false
  },
  {
    "targets": 1,
    "orderable": false,
    "render":function(data,type,row){
      return '<div style="width:120px; height: 20px; margin: 0 auto; white-space: nowrap; overflow: hidden;text-overflow: ellipsis;" '+
        'title="'+data +'">'+data +'</div>';
    }
  },
  {
    "render": function(data, type, row, meta) {

      return '<div style="width:120px; height: 20px; margin: 0 auto; white-space: nowrap; overflow: hidden;text-overflow: ellipsis;" '+
        'title="'+clickResTypeName +'">'+clickResTypeName +'</div>';

    },
    //指定是第三列
    "targets": 2,
    "orderable": false,
    "searchable": false
  },
  {
    "targets": 3,
    "orderable": false,
    "searchable": false,
    "render":function(data,type,row){
    	return timestampToTime(data);
    }
  },
  {
    // 定义操作列,######以下是重点########
    "targets" : 4,//操作按钮目标列
    "data" : null,
    "orderable": false,
    "render" : function(data, type, row) {
      var html = "<a href='javascript:void(0);' onclick='editResModal(\""+row.resId+"\")'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
        "<a href='javascript:void(0);' onclick='deleteResModal(\""+row.resId+"\")'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
        "<a href='"+path+"/v1/pageRes/res/file/"+row.resId +"' target='_blank'>预览</a>";;
      return html;
    }
  }];

//分页
function appendSkipPage() {
  var table = $("#example").dataTable();
  var template =
    $("<li class='paginate_button active'>" +
      "   <div class='input-group' style='margin-left:3px;'>" +
      "       <span class='input-group-addon' style='padding:0px 8px;background-color:#fff;font-size: 12px;'>跳转页</span>" +
      "       <input type='text' class='form-control' style='text-align:center;padding: 8px 2px;height:30px;width:40px;' />" +
      "       <span class='input-group-addon active' style='padding:0px 8px;'><a href='javascript:void(0)'> Go </a></span>" +
      "   </div>" +
      "</li>");

  template.find("a").click(function () {
    var pn = template.find("input").val();
    if (pn > 0) {
      --pn;
    } else {
      pn = 0;
    }
    table.fnPageChange(pn);
  });

  $("ul.pagination").append(template);
}
