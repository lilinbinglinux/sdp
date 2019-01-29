var successCode = "1",//成功代码
    failCode = "-1";//请求失败代码

/**
 * 公用js
 */
function changeSingleBoxBg(ele){
	$('.radio').removeClass('active');
	$(ele).addClass('active');
}
function changeCheckBoxBg(ele){
	$(ele).toggleClass('active');
}

//获取浏览器类型
var browser = function() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //判断是否Opera浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("Chrome") > -1) {
        return "Chrome";
    }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //判断是否Safari浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //判断是否IE浏览器, ie11判断不了，ie10以下可以判断出来
    //如果都没出去返回
    return "Can not judge";
}

/**
阻止向上冒泡
*/
function preventEvent(e) {
    if (e && e.stopPropagation) {
        e.stopPropagation()
    } else {
        window.event.cancelBubble = true
    }
}
/**
阻止默认方法
*/
function preventDefault(e) {
    if (e && e.preventDefault) {
        e.preventDefault();
    } else {
        window.event.returnValue = false;
        return false;
    }
}


/**获得body的高度**/
function getBodyHeight(){
	return $("body").height();
}

/**获得body的宽度*/
function getBodyWidth(){
	return $("body").width();
}


/**
 * 清空表单内容
 * */
var form = {
		clear:function(form){//清空表单内容
			var target = form;
			if(!target){
				return;
			}
			$('input,select,textarea', target).each(function(){
				var t = this.type, tag = this.tagName.toLowerCase();
				if (t == 'text' || t == 'hidden' || t == 'password' || tag == 'textarea'){
					this.value = '';
				} else if (t == 'file'){
					var file = $(this);
					file.after(file.clone().val(''));
					file.remove();
				} else if (t == 'checkbox' || t == 'radio'){
					this.checked = false;
				} else if (tag == 'select'){
					this.selectedIndex = -1;
				}
			});
			
		},
		load:function(form, data){//将json格式的数据根据name加载到form中，只支持普通元素，复杂元素（如下拉树）需要单独写或扩充该方法
			for(var name in data){
				var val = data[name];
				var rr = false;
				var cc = form.find('[switchbuttonName="'+name+'"]');
				if (cc.length){
					cc.switchbutton('uncheck');
					cc.each(function(){
						if (_isChecked($(this).switchbutton('options').value, val)){
							$(this).switchbutton('check');
						}
					});
					rr = true;
				}
				cc = form.find('input[name="'+name+'"][type=radio], input[name="'+name+'"][type=checkbox]');
				if (cc.length){
					cc.prop('checked', false);
					cc.each(function(){
						if (_isChecked($(this).val(), val)){
							$(this).prop('checked', true);
						}
					});
					rr =  true;
					continue;
				}
				if (!rr){
					$('input[name="'+name+'"]', form).val(val);
					$('textarea[name="'+name+'"]', form).val(val);
					$('select[name="'+name+'"]', form).val(val);
				}
			}
		},
		serializeJson:function(form){//将form表单序列化成jsonObject格式
			var str = this.serializeStr(form);
			return JSON.parse(str);
		},
		serializeStr:function(form){//将form表单序列化成jsonStr格式
	    	var formInfo = jQueryExt.serialize(form);
	    	formInfo = decodeURIComponent(formInfo, true);
	    	return jQueryExt.par2Json(formInfo, true);
		},
		isValidator:function(form){//当没有异步验证的时候使用该方法校验表单
			return form.isValid();
		},
		isSynValidator:function(form,callback){//当有异步验证使用该方法进行表单提交
			form.isValid(function(v){
			    if (v) {
			        callback();
			    }
			});
		},
		cleanValidator:function(form){
			form.validator('cleanUp');
		}
}



function _isChecked(v, val){
	if (v == String(val) || $.inArray(v, $.isArray(val)?val:[val]) >= 0){
		return true;
	} else {
		return false;
	}
}

$(function(){
	//内容区高度=整个父级高度-同级已知部分的高度
	var $ele = $('.common-content');
	$ele.css('height', $ele.parent('.common-wrapper').height()-$ele.siblings('.common-part').height());
});
/*******************************
 *字符串进行编码


 *******************************/
function getEncodeStr(str) {
	return encode(getValidStr(str));
	//return escape(getValidStr(str));
}

/********************************
 *编码规则:1) ~43~48~45~4e~48~41~4f
 *         2) ^7a0b^7389
 *字符编码,解决传输出现乱码问题
 ********************************/
function encode(strIn)
{
	var intLen=strIn.length;
	var strOut="";
	var strTemp;

	for(var i=0; i<intLen; i++)
	{
		strTemp=strIn.charCodeAt(i);
		if (strTemp>255)
		{
			tmp = strTemp.toString(16);
			for(var j=tmp.length; j<4; j++) tmp = "0"+tmp;
			strOut = strOut+"^"+tmp;
		}
		else
		{
			if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122)
			{
				tmp = strTemp.toString(16);
				for(var j=tmp.length; j<2; j++) tmp = "0"+tmp;
				strOut = strOut+"~"+tmp;
			}
			else
			{
				strOut=strOut+strIn.charAt(i);
			}
		}
	}
	return (strOut);
}
/****************************
 *取得合法的字符串
 ****************************/
function getValidStr(str) 
{
	str += "";
	if (str=="undefined" || str=="null" || str=="NaN")
		return "";
	else
		return reNew(str);
		
}
function reNew(str)
{
    var re;
	re=/%26amp;/g;
	str=str.replace(re,"&");
	re=/%26apos;/g;  
	str=str.replace(re,"'");
	re=/%26lt;/g;  
	str=str.replace(re,"<");
	re=/%26gt;/g;  
	str=str.replace(re,">");
	re=/%26quot;/g;  
	str=str.replace(re,"\"");
	re=/%25/g;
	str=str.replace(re,"%");
	re=/````/g;
	str=str.replace(re,",");
	return(str);		
}


/****************************
 *解析url查询参数
 ****************************/
function getQueryString() {
  var name, value;
  var str = location.href; //取得整个地址栏
  var num = str.indexOf("?");
  str = str.substr(num + 1); //取得所有参数
  var arr = str.split("&"); //各个参数放到数组里
  for (var i = 0; i < arr.length; i++) {
    num = arr[i].indexOf("=");
    if (num > 0) {
      name = arr[i].substring(0, num);
      value = arr[i].substr(num + 1);
      this[name] = value;
    }
  }
}

/****************************
 *时间格式化（yyyyMMddHHmmss）
 ****************************/
function formatDateTime (date) {
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  m = m < 10 ? ('0' + m) : m;
  var d = date.getDate();
  d = d < 10 ? ('0' + d) : d;
  var h = date.getHours();
  h=h < 10 ? ('0' + h) : h;
  var minute = date.getMinutes();
  minute = minute < 10 ? ('0' + minute) : minute;
  var second=date.getSeconds();
  second=second < 10 ? ('0' + second) : second;
  return y + m + d + h + minute + second;
}

/****************************
 *生成uuid
 ****************************/
/*function getuuid() {
    var s, uuid;
    s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
    uuid = s.join("").replace(/\-/g, "");
    return uuid;
}*/

/****************************
 *生成uuid
 * len：长度，例如 8，代表8位
 * radix：基数，例如 16，代表16进制
 ****************************/
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
        // rfc4122, version 4 form
        var r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random()*16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
}













