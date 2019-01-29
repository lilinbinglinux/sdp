/*树结构*/
var resTypeId = "root" ;
var filterCount = 1;
var choosedRes = [];//每次选择的资源数组
var pageResArray = [];//已引入的资源数组
var moduleTypeId = '';
var setting = {
  check: {
    enable: true
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
      if (data){
        var nodes = data.nodes;

        for ( var i = 0; i < nodes.length; i++ ){
          if ( nodes[i].parent ) {
            nodes[i].isParent = true;
            nodes[i].nocheck = true;
          }

          for ( var j = 0; j < pageResArray.length; j++ ) {

            if (nodes[i].id == pageResArray[j].resId ) {
              nodes[i].nocheck = true;
              break;
            }
          }

        }
        if (filterCount == 1){
          nodes.splice(0,0,{ id: "root", pId:0, name:"资源分类", open: true, isParent: true, parent: true });
          filterCount++;
        }
        if (!data) return null;
        return nodes;
      }
    }
  },
  callback: {
    onClick: onClick,
    onCheck: onCheck
  }
};

function onClick(event, treeId, treeNode, clickFlag) {
  resTypeId = treeNode.id;
  if ( treeNode.children === undefined ){
    treeNode.nocheck = false;
    refreshNode( {data: {type:"refresh", silent:false}} );
  }
}
function onCheck(e, treeId, treeNode) {
  if ( treeNode.id == 'root' ) {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getCheckedNodes(true);
    nodes.splice(0,1);
    choosedRes = nodes;
  }else {
    if( treeNode.checked ){
      choosedRes.push(treeNode);
    } else {
      for ( var i = 0; i < choosedRes.length; i++ ) {
        if ( treeNode.id == choosedRes[i].id ){
          choosedRes.splice(i,1);
          break;
        }
      }
    }
  }

  var html = "";
  for ( var i = 0; i < choosedRes.length; i++ ) {
    html += "<span>"+(i+1)+":&nbsp;"+choosedRes[i].name+"&nbsp;</span>";
  }

  document.getElementById('chooseListContent').innerHTML = html;
  // console.log("[ "+getTime()+" onCheck ]" + treeNode.name );
}

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
function getAsyncUrl( treeId , treeNode ) {
  var id = (treeNode != undefined) ? treeNode.id : "root";
  var url = path+"/v1/pageRes/res/tree/"+ id;
  return url;
}

function getTime() {
  var now= new Date(),
    h=now.getHours(),
    m=now.getMinutes(),
    s=now.getSeconds(),
    ms=now.getMilliseconds();
  return (h+":"+m+":"+s+ " " +ms);
}

/*组件修改相关js*/
var codeGloble = {};

$(document).ready(function(){
  $.fn.zTree.init($("#treeDemo"), setting );

  $.ajax({
    async:false,//设置同步/异步的参数[true异步  false同步]
    type: "get",
    url: path+"/v1/pageModule/module/"+moduleId,
    success: function(data){
      if (data){
        moduleTypeId = data.moduleTypeId;

        formatPageResIds(data.pageResIds);

        editorialHtmlHelper.setValue(data.moduleText);
        editorialJsHelper.setValue(data.moduleJs);
        editorialCsHelper.setValue(data.moduleStyle);
        
        document.getElementById("moduleTip").value = data.moduleTip;
        document.getElementById("moduleName").value = data.moduleName;
        data.pubFlag == 0 ? document.getElementById("pubFlag0").checked = true : document.getElementById("pubFlag").checked = true;
        document.getElementById("sortId").value = data.sortId.toString();

        var choosedListContent = '';
        var frame = document.getElementById("showIframe");

        $(frame).load(function () {
          if (pageResArray.length > 0){
        	
            document.getElementById("showIframe").contentWindow.getResFromAddModule(pageResArray,'edit');
            
            for(var j = 0; j < pageResArray.length; j++){
              choosedListContent += "<span>"+(j+1)+":&nbsp;"+pageResArray[j].resName+"&nbsp;</span><span onclick='deleteChoosedRes(\""+pageResArray[j].resId+"\")' style='font-size: 12px;color: #337ab7;' class='glyphicon glyphicon-trash'></span>&nbsp;";
            }
            document.getElementById("choosedListContent").innerHTML = choosedListContent;
          }
          document.getElementById("showIframe").contentWindow.addCode({html:data.moduleText, js: data.moduleJs, css: data.moduleStyle});
        });
       
      }
    }
  });

  //格式化 pageResIds
  function formatPageResIds(pageResIdsStr) {
    var array = pageResIdsStr.split(',');
    for (var i = 0; i < array.length; i++){
      var arr = array[i].split(':');
      $.ajax({
        async: false,
        type: "get",
        url: path+"/v1/pageRes/res/"+arr[1],
        success: function(data){
          if (data){
            pageResArray.push(data);
          }
        }
      });
    }
  }

  //输入组件详细信息之后 修改
  $("#addNodeBtn").click(function () {
    if (checkAddNodeForm()){
      var jsonData = {};
      var pageResIds = '';//已引入的资源id 字符串

      var t = $('#addNodeForm').serializeArray();

      for (var i = 0; i < pageResArray.length; i++){

        /*var str = (i+1) +':'+pageResArray[i].resId;*/
        var str = pageResArray[i].resType +':'+pageResArray[i].resId;

        if(i != pageResArray.length-1){
          str += ',';
        }
        pageResIds += str;
      }


      jsonData.moduleText = codeGloble.html;
      jsonData.moduleStyle = codeGloble.css;
      jsonData.moduleJs = codeGloble.js;
      jsonData.pageResIds = pageResIds;
      //console.log(pageResIds)

      for ( var i = 0; i < t.length; i++ ) {
        jsonData[ t[i].name ] = t[i].value;
      }

      $.ajax({
        type: "put",
        url: path+"/v1/pageModule/module/update/"+moduleId,
        data: { "moduleName": jsonData.moduleName, "moduleTip": jsonData.moduleTip,"moduleText": jsonData.moduleText, "moduleStyle": jsonData.moduleStyle, "moduleJs": jsonData.moduleJs, "pubFlag": jsonData.pubFlag, "sortId": jsonData.sortId, "pageResIds": jsonData.pageResIds },
        success: function(data){
          //console.log(data);
          if (data.code == 201) {
            location.href = path+"/v1/pageModuleType/moduleClassify";
            $("#myModal").modal('hide');
          }else {
            alert(data.message);
          }
        }
      });
    }

  });
});

var sortIdReg = /^[0-9]*$/;//数字正则
//表单验证（新增组件）
function checkAddNodeForm(){
  var moduleName = document.getElementById('moduleName').value;
  var sortId = document.getElementById('sortId').value;

  if (moduleName == ''){
    $("#moduleNameError").html("资源分类名称不能为空！");
    return false;
  }else {
    $("#moduleNameError").html("");
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

function ckEditorAddEle(str) {
  //console.log("ckEditorAddEle..............");
  var ckcontent = editorialHtmlHelper.getValue();
  ckcontent = ckcontent + str;
  editorialHtmlHelper.setValue(ckcontent);
}

//保存组件
function saveCom(){

  var code = {
    html: editorialHtmlHelper.getValue(),
    js: editorialJsHelper.getValue(),
    css: editorialCsHelper.getValue()
  };

  codeGloble = code;

  $("#myModal").modal('show');

  $("#sortIdError").html("");
  $("#moduleNameError").html("");

  var html1 = "";
  for ( var i = 0; i < pageResArray.length; i++ ) {
    html1 += "<span>"+(i+1)+". "+pageResArray[i].resName+"</span>&nbsp;&nbsp;";
  }
  document.getElementById("saveRes").innerHTML = html1;
}

//预览组件
function getCode() {

  var code = {
    html: editorialHtmlHelper.getValue(),
    js: editorialJsHelper.getValue(),
    css: editorialCsHelper.getValue()
  };

  document.getElementById("showIframe").contentWindow.addCode(code);
}

//打开选择资源的modal
function openAddResModal() {
  $('#myChooseModal').modal('show');
  var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
  treeObj.checkAllNodes(false);
  var node = treeObj.getNodeByParam("id", "root", null);
  treeObj.reAsyncChildNodes(node, "refresh", false);
}

//添加资源到预览
function addResToShowPage() {
  //console.log("addResToShowPage..............");
  var choosedListContent = '';

  for (var i = 0; i < choosedRes.length; i++){

    $.ajax({
      async: false,//设置同步/异步的参数[true异步  false同步]
      url: path+"/v1/pageRes/res/" + choosedRes[i].id,
      success: function (data) {
        pageResArray.push(data);
        choosedRes[i].resType = data.resType;
        choosedRes[i].resId = data.resId;
      }
    });

  }

  if (pageResArray.length > 0){
    document.getElementById("showIframe").contentWindow.getResFromAddModule(choosedRes);

    for(var j = 0; j < pageResArray.length; j++){
      choosedListContent += "<span>"+(j+1)+":&nbsp;"+pageResArray[j].resName+"&nbsp;</span><span onclick='deleteChoosedRes(\""+pageResArray[j].resId+"\")' style='font-size: 12px;color: #337ab7;' class='glyphicon glyphicon-trash'></span>&nbsp;";
    }

    document.getElementById("choosedListContent").innerHTML = choosedListContent;
  }

  choosedRes = [];
  document.getElementById('chooseListContent').innerHTML = '';
  $('#myChooseModal').modal('hide');

}

function deleteChoosedRes(resId) {

  if (confirm("确认取消引入该资源吗？") ){
    if (pageResArray.length > 0){

      for(var i = 0; i < pageResArray.length; i++){
        if(pageResArray[i].resId == resId){
          pageResArray.splice(i,1);
        }
      }

      var choosedListContent = "";
      for(var j = 0; j < pageResArray.length; j++){
        choosedListContent += "<span>"+(j+1)+":&nbsp;"+pageResArray[j].resName+"&nbsp;</span><span onclick='deleteChoosedRes(\""+pageResArray[j].resId+"\")' style='font-size: 12px;color: #337ab7;' class='glyphicon glyphicon-trash'></span>&nbsp;";

      }

      document.getElementById("choosedListContent").innerHTML = choosedListContent;

      document.getElementById("showIframe").contentWindow.deleteRes(resId);

      var doms = document.getElementsByClassName("cke_wysiwyg_frame cke_reset")[0].contentWindow.document.getElementsByClassName(resId);
      if (doms.length > 0){
        for (var i = 0; i < doms.length; i++){
          doms[i].parentNode.removeChild(doms[i]);
        }
      }
    }

    $('#myChooseModal').modal('hide');
  }
}