$('#imageMenuFirst').on('click',function () {
    if($('.image-submenu').is(":hidden")) {
        $('.image-submenu').show(200);
        $('#imageMenuFirst span').addClass('glyphicon-triangle-top').removeClass('glyphicon-triangle-bottom');
    }else{
        $('.image-submenu').hide(200);
        $('#imageMenuFirst span').addClass('glyphicon-triangle-bottom').removeClass('glyphicon-triangle-top');
    }
});

$('.nav-collapse-left').on('click',function () {
    $('.image-menu').css('width','186px');
    $('.image-content').css('left','186px');
    $('.image-menu').show();
    $('.nav-collapse-right').show();
    $('.nav-collapse-left').hide();
});
$('.nav-collapse-right').on('click',function () {
    $('.image-menu').css('width','0px');
    $('.image-content').css('left','0px');
    $('.image-menu').hide();
    $('.nav-collapse-right').hide();
    $('.nav-collapse-left').show();
});