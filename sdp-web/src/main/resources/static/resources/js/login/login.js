/**
 * 登录页js
 */
$(function(){
	// 设置头部位置
	var logoWidth = $('.logo').width();
	$('.logo').css('margin-left',-logoWidth/2);
	
	setInpStyle();
	save();
});


// 输入框效果
function setInpStyle(){
	$('.txt-wrapper input').on('focus', function(){
		$(this).parents('.txt-wrapper').css({'border-color':'#e25f56'});
	}).on('blur', function(){
		$(this).parents('.txt-wrapper').css({'border-color':'#d4d1d3'});
	}).on('keyup', function(){
		$('.tips').html('');
	});
	
	$('.txt-wrapper input:eq(0)').on('focus', function(){
		$('.username .login-img i').css({'background-position':'-22px -22px'});		
	}).on('blur', function(){
		$('.username .login-img i').css({'background-position':'-22px -2px'});			
	});
	
	$('.txt-wrapper input:eq(1)').on('focus', function(){
		$('.password .login-img i').css({'background-position':'-43px -22px'});		
	}).on('blur', function(){
		$('.password .login-img i').css({'background-position':'-43px -2px'});			
	});
	
	
	// 键盘事件-按回车键input光标自动换行
	$('input#userName').on('keyup',function (event) {
    if (event.keyCode == 13) {
      if($(this).val().length > 32 || $(this).val().length < 4){
        $('.tips').html('用户名的长度需大于4小于32');
      }else {
        $('.tips').html('');
      	if($('input#password').val() == '') {
          $('input#password').focus();
				}else if ($('input#password').val().length > 32 || $('input#password').val().length < 4){
          $('.tips').html('密码的长度需大于4小于32');
        }else {
          loginFun();
				}
			}
    }
  });
  $('input#password').on('keyup',function (event) {
    if (event.keyCode == 13) {
      if($('input#userName').val() == '' || $('input#userName').val().length > 32 || $('input#userName').val().length < 4) {
        $('input#userName').focus();
      }
      loginFun();
    }
  })
}
// 单选、复选框选中状态切换
function chooseCheckbox(ele){
	$(ele).toggleClass('active');
}
// 保存cookie，页面一加载先执行存储
function save(){
	if ($.cookie("embUser") == "true") {
		/*$('.rem-wrap a').hasClass('active');
		$('.login-name input').val($.cookie("username"));
		$('.login-pass input').val($.cookie("password"));*/
    $('input#userName').val($.cookie("username"));
    $('input#password').val($.cookie("password"));
    $('input[type="checkbox"]').attr('checked','checked');
	}
}
// 记住用户名和密码
function rememberName(){
	/*var username = $('.login-name input').val();
	var password = $('.login-pass input').val();
	if($('.rem-wrap a').hasClass('active')){
		$.cookie("username", username, {expires: 7, path:'/'});
		$.cookie("password", password, {expires: 7, path:'/'});
		$.cookie("embUser", "true", {expires: 7});
	}else{
		$.cookie("username", "", { expires: -1, path: '/'}); 
		$.cookie("password", "", { expires: -1, path: '/'});
		$.cookie("embUser", "false", {expires: -1});
	}*/
  var username = $('input#userName').val();
  var password = $('input#password').val();
  if($('input[type="checkbox"]').is(':checked')){
    $.cookie("username", username, {expires: 7, path:'/'});
    $.cookie("password", password, {expires: 7, path:'/'});
    $.cookie("embUser", "true", {expires: 7});
  }else{
    $.cookie("username", "", { expires: -1, path: '/'});
    $.cookie("password", "", { expires: -1, path: '/'});
    $.cookie("embUser", "false", {expires: -1});
  }
}

//登陆函数
function loginFun(){
	rememberName();
	//判断合法与否&给出提示或提交表单
	var loginId = $('#userName').val();
	var password = $('#password').val();
	var str = '', islegal = true;
	if(loginId.length > 32 || loginId.length < 4){
		str += '用户名的长度需大于4小于32';
		islegal = false;
	}else if(password.length > 32 || password.length < 4){
		str += '密码的长度需大于4小于32';
		islegal = false;
	}
	if(islegal){
		$("#loginForm").submit();
	}else{
		$('.tips').html(str);
	}
}

// 当记住用户名复选框被选中时，想使用回车键直接登录，此时只会取消复选框选中状态，页面却不能跳转提交表单，因此要在body上添加键盘事件
function keyEnter(){
  if(event.keyCode==13) $('#login').click();
}