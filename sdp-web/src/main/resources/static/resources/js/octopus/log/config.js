/**
 * Created by renxuanwei on 2017/7/16.
 */
function show_content(obj) {
    var $this = $(obj);
    var $next = $(obj).next();
    var $parent = $(obj).parent();
    var $option = $this.children('li').eq(1);
    if($next.css('display') == 'none'){
        $next.css('display','');
        $parent.removeAttr('class','config_content_fold cursor_pointer');
        $parent.attr('class','config_content_unfold ');
        $option.find('.more_or_less').attr('src','/icon/less.png');
    }else{
        $next.css('display','none');
        $parent.attr('class','config_content_fold');
        $option.find('.more_or_less').attr('src','/icon/more_unfold.png');
    }
}
function configer_save(obj,type) {
    var $parent = $(obj).parent().parent().parent();
    var $inps= $parent.find('.configer_content_input');
    
    $inps.each(function () {
        $(this).attr('readonly','readonly');
       // swal("保存成功!")
    });
	
	var requestUrl = ctx + "rest/api/config/update/prop/" + type;
	var requestData;
	if(type == "shortMessage"){
		requestData = $("#ShortMessageForm").serialize();
	}else if(type == "email"){
		requestData = $("#EmailForm").serialize();
	}else if(type == "weiChat"){
		requestData = $("#WeiChatForm").serialize();
	}
	
	  $.ajax({
  		url : requestUrl,
  		type: "POST",
  		data: requestData,
  		success : function(data) {
  			data = eval("(" + data + ")");
  			if (data.status=="200") {
  				layer.alert("保存成功");
  				 setTimeout(function() {
                     window.location.reload();
                 },500);
  			} else if (data.status=="500") {
  				layer.alert("保存失败");
  				 setTimeout(function() {
                     window.location.reload();
                 },500);
  			}
  		}
  	});
	
}