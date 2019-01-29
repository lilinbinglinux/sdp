+function ($) {

    var Tab = function (element) {
        this.element = $(element);

        this.switch();//点击事件

        this.invoke();//默认选中

        this.hover();//鼠标滑过
    };

    Tab.version = '1.0.0';

    function openActive($active, $this) {
        var index = $this.find("> .summer-tabs .tab-item").index($active);
        var activeWidth = $active.width();

        lineTranslate(index, activeWidth, $this);

        //打开对应content
        var $content = $this.find(".summer-tab-content .tab-content:nth-child("+(index+1)+")");
        $content.css("display","block").siblings().css("display","none");
    }

    function lineTranslate( index, aimWidth, $this ) {
        var $summerLine = $this.find("> .summer-tabs .summer-line");
        var prevLeft = 0;//前几个元素的宽度总和
        for( var i = 0; i < index; i++ ){
            var currentWidth = $this.find("> .summer-tabs .tab-item:nth-child("+(i+1)+")")[0].getBoundingClientRect().width;
            if( i === 0 ){
                prevLeft += currentWidth ;
            }else {
                prevLeft += currentWidth ;
            }
        }
        if( index === 0 ){
            $summerLine.width(aimWidth);
            $summerLine.css("transform", "translateX(0px)");
        } else {
            $summerLine.width(aimWidth);
            $summerLine.css("transform", "translateX("+(prevLeft + 20)+"px)");
        }
    }

    Tab.prototype = {
        "switch": function () {
            var $this = this.element;
            $this.find(".tab-item").click(function (e) {
                var $active = $(this);
                $active.addClass("active").siblings().removeClass("active");
                openActive($active, $this);
            });
        },
        "invoke": function () {
            var $this = this.element;
            var $active = $this.find("> .summer-tabs .active");
            if( $active.length > 0 ){
                openActive($active, $this);
            }
        },
        "hover": function () {
            var $this = this.element;
            var $active , activeIndex, activeWidth;
            $this.find(".tab-item").mouseenter(function () {
                var $hover = $(this);
                var hoverIndex = $this.find("> .summer-tabs .tab-item").index($hover);
                var hoverWidth = $hover.width();

                $active = $this.find("> .summer-tabs .active");
                activeIndex = $this.find("> .summer-tabs .tab-item").index($active);
                activeWidth = $active.width();

                $this.find(".tab-item").removeClass("hover");
                $hover.addClass("hover");

                lineTranslate(hoverIndex, hoverWidth, $this);
            });
            $this.find(".tab-item").mouseleave(function () {
                $this.find(".tab-item").removeClass("hover");
                if( $(this).hasClass("active") ){
                    return;
                } else {
                    lineTranslate(activeIndex, activeWidth, $this);
                }
            })
        }
    };

    $.fn.extend({
        tab: function () {
            this.each(function () {
                new Tab($(this))
            });
            return this;
        },
        open: function (tableItem) {
            var $this = this;
            this.each(function () {
                var $tableItem = $(tableItem);
                $tableItem.addClass("active").siblings().removeClass("active");
                openActive($tableItem, $this);
            });
            return this;
        }
    });

    window.Tab = Tab;
}(jQuery);
