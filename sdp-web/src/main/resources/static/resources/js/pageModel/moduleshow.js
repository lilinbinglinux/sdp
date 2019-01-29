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

function addCssCode( code ) {
  var styleDom = document.getElementById('cssStyle');
  styleDom.textContent = code;
}

function addJsCode( code ) {
  var scriptTag = document.createElement('script');
  scriptTag.type = "text/javascript";
  scriptTag.id = "jsByMe";
  scriptTag.textContent = code;

  var dom = document.getElementById("jsByMe");
  try{
	  if(!dom ){
	    $("#headId").html($("#headId").html()  +  scriptTag.outerHTML);
	  }else {
	    document.getElementById("headId").removeChild(dom);
	    $("#headId").html($("#headId").html()  +  scriptTag.outerHTML);
	  } 
  }catch(e){
	  
  }
 
  //console.log($("#headId").html());
}

function addHtmCode( code ) {
  var htmlDom = document.getElementById('html-div');
  htmlDom.innerHTML = code;
}

function addCode( code ) {
  addHtmCode(code.html);
  addCssCode(code.css);

  addJsCode(code.js);

}

function getResFromAddModule( choosedRes, type ) {
  // console.log("getResFromAddModule............")
  //console.log(choosedRes);
  var scripts = [];
  var txtPic = '';
  scriptHtmls = '';
  console.log(choosedRes);
  for ( var i = 0; i < choosedRes.length; i++ ) {
    var data = choosedRes[i];

    if (data.resType == 0){//txt
      if (!type){
        var htmlDiv = document.createElement('div');
        htmlDiv.style.width = "100%";
        htmlDiv.className = data.resId;
        $.ajax({
          async: false,//设置同步/异步的参数[true异步  false同步]
          url: path+"/v1/pageRes/res/file/" + data.resId,
          success: function (data) {
            htmlDiv.innerHTML = data;
            txtPic += htmlDiv.outerHTML;
            document.getElementById('html-div').appendChild(htmlDiv);
          }
        });
      }
    }else if (data.resType == 1){//js
      var scriptEle = document.createElement('script');

      scriptEle.className = data.resId;
      scriptEle.type = "text/javascript";
      scriptEle.src = path+"/v1/pageRes/res/file/" + data.resId;
      scripts.push(scriptEle);
      scriptHtmls += scriptEle.outerHTML;
      //document.getElementsByTagName('head')[0].appendChild(scriptEle);
    }else if (data.resType == 2){//css
      var styleEle = document.createElement('link');

      styleEle.className = data.resId;
      styleEle.rel = "stylesheet";
      styleEle.type = "text/css";
      styleEle.href = path+"/v1/pageRes/res/file/" + data.resId;
      scripts.push(styleEle);
      scriptHtmls += styleEle.outerHTML;
     // document.getElementsByTagName('head')[0].appendChild(styleEle);
    }else if (data.resType == 3){//image
      if (!type){
        var img = document.createElement('img');
        img.style.width = "300px";
        img.className = data.resId;
        img.src = path+"/v1/pageRes/res/file/"+data.resId;
        txtPic += img.outerHTML;
        document.getElementById('html-div').appendChild(img);
      }
      //window.parent.ckEditorAddEle(img.outerHTML);
    }else if (data.resType == 4){//flash
      if (!type){
        var url = path + '/v1/pageRes/res/file/' + data.resId;
        var flashStr =
        '<object class="'+data.resId+'" classid="'+data.resId+'">' +
          '<param name="allowFullScreen" value="true" /><param name="quality" value="high" />' +
          '<param name="movie" value="' + url + '" />' +
          '<embed allowfullscreen="true" quality="high" src="' + url + '" type="application/x-shockwave-flash">&nbsp;</embed>' +
        '</object>';
        txtPic += flashStr;
        document.getElementById('html-div').innerHTML = document.getElementById('html-div').innerHTML + flashStr;
      }

    }else if (data.resType == 5){//video
      if (!type){
        var src = path+"/v1/pageRes/res/file/"+data.resId;
        var videoStr = '<video class="'+data.resId+'" width="320" height="240" controls>\n' +
          '  <source src="'+src+'" type="video/mp4">\n' +
          '  <source src="'+src+'" type="video/ogg">\n' +
          '  <source src="'+src+'" type="video/webm">\n' +
          '  <object data="'+src+'" width="320" height="240">\n' +
          '    <embed src="'+src+'" width="320" height="240">\n' +
          '  </object> \n' +
          '</video>';
        txtPic += videoStr;
        document.getElementById('html-div').innerHTML = document.getElementById('html-div').innerHTML + videoStr;
      }
    }
  }

  if (!type){
    window.parent.ckEditorAddEle(txtPic);
  }

  if (scripts.length>0){
	  try{
		  $("#headId").html($("#headId").html() + scriptHtmls);
	  }catch(e){
		 
	  }
    //console.log("res-script--------------------")
  }
}

function getResFile(id) {
  $.ajax({
    async: false,//设置同步/异步的参数[true异步  false同步]
    url: path+"/v1/pageRes/res/file/" + id,
    success: function (data) {

    }
  });
}

function deleteRes(resId) {
  var doms = document.getElementsByClassName(resId);
  if (doms.length > 0){
    for (var i = 0; i < doms.length; i++){
      doms[i].parentNode.removeChild( doms[i]);
    }
  }

}