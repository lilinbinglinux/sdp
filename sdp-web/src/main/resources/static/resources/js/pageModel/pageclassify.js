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
      var pen = nodes.length > 0 ? true : false;

      for ( var i = 0; i < nodes.length; i++ ){
        if ( nodes[i].parent ) {
          nodes[i].isParent = true;
        }
      }

      if ( filterCount == 1 ){
        if ( pageTypeId !== 'root' ) {
          $.ajax({
            async:false,//设置同步/异步的参数[true异步  false同步]
            url: path+"/v1/pageType/detail/"+pageTypeId,
            success: function(data){
              nodes.splice(0,0,{ id: pageTypeId, pId:0, name: data.pageTypeName, open: pen, isParent: pen, parent: pen });
            }
          });
        }else {
          nodes.splice(0,0,{ id: "root", pId:0, name:"页面分类", open: pen, isParent: pen, parent: pen });
        }
        filterCount++;
      }
      if (!data) return null;
      return nodes;
    }
  },
  callback: {
    beforeEditName: beforeEditName,
    beforeRemove: beforeRemove,
    onClick: onClick
  }
};

/**
 *
 * @param treeId
 * @param treeNode
 * @returns {boolean}
 */
function beforeEditName(treeId, treeNode) {
  var zTree = $.fn.zTree.getZTreeObj("treeDemo");
  zTree.selectNode(treeNode);
  editNode( treeNode.id );
  return false;
}

/**
 *
 * @param treeId
 * @param treeNode
 * @returns {boolean}
 */
function showRemoveBtn(treeId, treeNode) {
  return !treeNode.isParent && treeNode.tId != "treeDemo_1";
}

/**
 *
 * @param treeId
 * @param treeNode
 * @returns {boolean}
 */
function showRenameBtn(treeId, treeNode) {
  return treeNode.id != 'root';
}

/**
 *
 * @param treeId
 * @param treeNode
 * @returns {boolean}
 */
function beforeRemove(treeId, treeNode) {
  var f = false;
  if ( confirm("确认删除 节点 :  " + treeNode.name + " 吗？") ) {
    $.ajax({
      type: "delete",
      async: false,   // 太关键了，学习了，同步和异步的参数
      url: path+"/v1/pageType/"+treeNode.id,
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

/**
 *
 * @param event
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function onClick(event, treeId, treeNode, clickFlag) {
  pageTypeId = treeNode.id;
  clickResTypeName = treeNode.id == 'root' ? 'root' : treeNode.name;

  if ( treeNode.children === undefined ){
    refreshNode( {data: {type:"refresh", silent:false}} );
  }
  table.ajax.reload();

}

/**
 *
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
  var sObj = $("#" + treeNode.tId + "_span");

  if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0
    || $("#editBtn_" + treeNode.tId).length > 0
    || $("#delBtn_" + treeNode.tId).length > 0)
    return;
  var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
    + "' title='add node' onclick='openAddModal(\""+treeNode.id+"\")' onfocus='this.blur();'></span>";
  sObj.after(addStr);
}

/**
 *
 * @param treeId
 * @param treeNode
 */
function removeHoverDom(treeId, treeNode) {
  $("#addBtn_"+treeNode.tId).unbind().remove();
}

/**
 *
 * @param e
 */
function refreshNode(e) {
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

/**
 *
 * @param treeId
 * @param treeNode
 * @returns {string}
 */
function getAsyncUrl( treeId , treeNode ) {
  var id = (treeNode!= undefined) ? treeNode.id : pageTypeId;
  var url = path+"/v1/pageType/childDetail/"+ id;
  return url;
}

/**
 *
 * @param id
 */
function openAddModal(id) {
  $("#myModal").modal('show');
  document.getElementById("addNodeForm").reset();
  $("#pageTypeNameError").html("");
  $("#pageSortIdError").html("");
}

/**
 *
 * @param id
 */
function editNode(id) {
  $("#myEditModal").modal('show');

  $("#pageTypeName1Error").html("");
  $("#pageSortId1Error").html("");

  //编辑回显
  $.ajax({
    type: "get",
    url: path+"/v1/pageType/detail/"+id,
    success: function(data){
      if (data){
        document.getElementById("pageTypeName1").value = data.pageTypeName;
        document.getElementById("pageSortId1").value = data.pageSortId.toString();
      }
    }
  });

  editTypeId = id;
}

function addNodeFormBtn() {

  if (checkAddNodeForm()){
    var jsonData = {};
    var t = $('#addNodeForm').serializeArray();

    jsonData.pageParentId = pageTypeId;

    for ( var i = 0; i < t.length; i++ ) {
      jsonData[ t[i].name ] = t[i].value;
    }

    $.ajax({
      type: "post",
      url: path+"/v1/pageType/savePageType",
      data: { "pageTypeName": jsonData.pageTypeName, "pageParentId": jsonData.pageParentId, "pageSortId": jsonData.pageSortId },
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

function editNodeFormBtn() {
  if (checkEditNodeForm()){
    var jsonData = {};
    var t = $('#editNodeForm').serializeArray();

    jsonData.pageParentId = editTypeId;

    for ( var i = 0; i < t.length; i++ ) {
      jsonData[ t[i].name ] = t[i].value;
    }

    $.ajax({
      type: "put",
      url: path+"/v1/pageType/"+editTypeId,
      data: { "pageTypeName": jsonData.pageTypeName, "pageParentId": jsonData.pageParentId, "pageSortId": jsonData.pageSortId },
      success: function(data){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if ( data.code == 201 ){
          var nodes = zTree.getSelectedNodes();
          nodes[0].name = jsonData.pageTypeName;
          zTree.updateNode(nodes[0]);
        }else {
          alert("修改节点失败！");
        }
        $("#myEditModal").modal('hide');
      }
    });
  }
}

/**
 * add page model
 */
function addPageModelForm() {
  var jsonData = {};
  var t = $('#addPageModelModalForm').serializeArray();

  jsonData.pageParentId = pageTypeId;

  for ( var i = 0; i < t.length; i++ ) {
    jsonData[ t[i].name ] = t[i].value;
  }

  $.ajax({
    type: "post",
    url: path+"/v1/pageModel/savePageModel",
    data: { "pageName": jsonData.pageName, "pageTypeId": jsonData.pageParentId, "pageSortId": jsonData.pageSortId },
    success: function(data){
      if ( data.code == 201 ){
        alert("添加页面成功！");
        table.ajax.reload();
      } else {
        alert("添加页面失败！");
      }
      $("#addPageModelModal").modal('hide');
    }
  });

}

/**
 * @desc update page model form
 */
function updatePageModelForm() {
  var jsonData = {};
  var t = $('#updatePageModelModalForm').serializeArray();

  jsonData.pageParentId = pageTypeId;

  for ( var i = 0; i < t.length; i++ ) {
    jsonData[ t[i].name ] = t[i].value;
  }

  $.ajax({
    type: "put",
    url: path+"/v1/pageModel/" + jsonData.pageId,
    data: { "pageName": jsonData.pageName, "pageTypeId": jsonData.pageParentId, "pageSortId": jsonData.pageSortId },
    success: function(data){
      if ( data.code == 201 ){
        alert("修改页面成功！");
        table.ajax.reload();
      } else {
        alert("修改页面失败！");
      }
      $("#updatePageModelModal").modal('hide');
    }
  });

}

/**
 *
 * @param id
 * @desc update page model
 */
function editPageModelForm(id) {
  $("#pageNameError3").html("");
  $("#pageSortId3Error").html("");

  $.ajax({
    type: "get",
    url: path+"/v1/pageModel/detail/"+id,
    success: function(data){
      if (data){
        $("#pageId").val( data.pageId );
        $("#pageName3").val( data.pageName );
        $("#pageSortId3").val( data.pageSortId.toString() );
        $("#updatePageModelModal").modal('show');
      }
    },
    error: function() {
      alert( 'error.' );
    }
  });
}


/**
 *
 * @param resId
 * @desc 删除资源信息
 */
function deleteResModal(resId) {
  if (confirm("确定要删除该条资源吗？")){
    $.ajax({
      type: "delete",
      url: path+"/v1/pageModel/"+resId,
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

/**
 * @returns {boolean}
 * @desc 表单验证（新增分类）
 */
function checkAddNodeForm(){
  var pageTypeName = document.getElementById('pageTypeName').value;
  var pageSortId = document.getElementById('pageSortId').value;

  if (pageTypeName == ''){
    $("#pageTypeNameError").html("资源分类名称不能为空！");
    return false;
  }else {
    $("#pageTypeNameError").html("");
  }

  if (pageSortId == ''){
    $("#pageSortIdError").html("排序不能为空！");
    return false;
  }else if(!sortIdReg.test(pageSortId)){
    $("#pageSortIdError").html("排序只能是数字格式！");
    return false;
  }else {
    $("#pageSortIdError").html("");
  }

  return true;
}


/**
 * @returns {boolean}
 * @desc 表单验证（编辑分类）
 */
function checkEditNodeForm(){
  var pageTypeName1 = document.getElementById('pageTypeName1').value;
  var pageSortId1 = document.getElementById('pageSortId1').value;

  if (pageTypeName1 == ''){
    $("#pageTypeName1Error").html("资源分类名称不能为空！");
    return false;
  }else {
    $("#pageTypeName1Error").html("");
  }

  if (pageSortId1 == ''){
    $("#pageSortId1Error").html("排序不能为空！");
    return false;
  }else if(!sortIdReg.test(pageSortId1)){
    $("#pageSortId1Error").html("排序只能是数字格式！");
    return false;
  }else {
    $("#pageSortId1Error").html("");
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

  $( "#addPageModelBtn" ).click( function() {
    $( "#addPageModelModal" ).modal( "show" );
    document.getElementById( "addPageModelModalForm" ).reset();
    $( "#pageName" ).html( "" );
    $( "#pageSortId2" ).html( "" );
  } );
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
    "data": "pageName"
  },
  {
    "data": "pageTypeId"
  },
  {
    "data": "createDate"
  },
  {
    "data": null
  }
];

var columnDefs =  [
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
    //指定是第三列
    "targets": 2,
    "orderable": false,
    "searchable": false,
    "render": function(data, type, row, meta) {

      return '<div style="width:120px; height: 20px; margin: 0 auto; white-space: nowrap; overflow: hidden;text-overflow: ellipsis;" '+
        'title="'+clickResTypeName +'">'+clickResTypeName +'</div>';
    }
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
      var html =
        "<a href='javascript:void(0);' onclick='editPageModelForm(\""+row.pageId+"\")'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
        //"<a href='"+path+"/v1/pageModel/pageVisualize/"+row.pageId+"' target='_blank'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
        "<a href='javascript:void(0);' onclick='openWindow(\"" + row.pageId + "\")'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
        "<a href='javascript:void(0);' onclick='deleteResModal(\""+row.pageId+"\")'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;";
      return html;
    }
  }];

function openWindow( pageId ) {
  var url = path + "/v1/pageModel/pageVisualize/" + pageId;
  window.open( url,'', 'toolbar=no,menubar=no,scrollbars=no,resizable=no,location=yes,status=yes');
}

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