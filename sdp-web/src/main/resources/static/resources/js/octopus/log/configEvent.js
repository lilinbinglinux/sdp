$('.configer_content_input').dblclick(function (e) {
        $(this).removeAttr('readonly');
    });
    $('.option_edit').click(function (e) {
        var $this = $(this);
        var $parent = $this.parent().parent();
        var $inp = $parent.find('.configer_content_input');

        $inp.attr('focused','autofocus');
        $inp.removeAttr('readonly');

    });
(function () {
    var $contents = $('.config_content_ul');
    $contents.each(function () {
        $(this).click(function (e) {
            var $option = e.target;
            var $nodename = $option.nodeName;
            if($nodename === 'LI' || $nodename === 'UL' || $nodename==='IMG'){
                show_content(this);
            }
            if($nodename === 'A' && $option.className === 'option_more'){
                show_content(this);
            }
        });
    });
}());