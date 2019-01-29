/*
 treeMenu - jQuery plugin
 version: 0.4

 Copyright 2014 Stepan Krapivin

*/

(function($){
  $.fn.openActive = function(activeSel) {
    activeSel = activeSel || ".active";

    var c = this.attr("class");

    this.find(activeSel).each(function(){
      var el = $(this).parent();
      while (el.attr("class") !== c) {
        if(el.prop("tagName") === 'UL') {
          el.show();
        } else if (el.prop("tagName") === 'LI') {
          el.removeClass('tree-closed');
          el.addClass("tree-opened");
        }

        el = el.parent();
      }
    });

    return this;
  };

  $.fn.treemenu = function(options) {
    options = options || {};
    options.delay = options.delay || 0;
    options.openActive = options.openActive || false;
    options.activeSelector = options.activeSelector || "";

    this.addClass("treemenu");
    this.find("> li").each(function() {
      e = $(this);
      var subtree = e.find('> ul');
      var button = e.find('span').eq(0).addClass('toggler');

      if( button.length == 0) {
        var button = $('<span class="fa fa-chevron-right"></span>');
        button.addClass('toggler');
        e.prepend(button);
      } else {
        button.addClass('toggler');
      }

      if(subtree.length > 0) {
        subtree.hide();

        e.addClass('tree-closed');

        e.find(button).click(function() {
          var li = $(this).parent('li');
          var liParent = li.parent();
          li.find('> ul').slideToggle(options.delay);
          li.toggleClass('tree-opened');
          li.toggleClass('tree-closed');
          li.toggleClass(options.activeSelector);

          liParent.find("> li").not(li).find('> ul').slideUp(options.activeSelector);
          var notLis = liParent.find("> li").not(li);
          for(var i = 0; i < notLis.length; i++ ) {
            if( $(notLis[i]).hasClass("tree-opened") ){
              $(notLis[i]).toggleClass('tree-opened');
              $(notLis[i]).toggleClass('tree-closed');
            }
          }
        });

        $(this).find('> ul').treemenu(options);
      } else {
        $(this).addClass('tree-empty');
      }
    });

    if (options.openActive) {
      this.openActive(options.activeSelector);
    }

    return this;
  }
})(jQuery);
