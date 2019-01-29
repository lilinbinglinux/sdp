var url, tag;
$(function () {
    $('#process').append(processHtml);
    //2、图标允许移动到流程图
    $(".shape_img").each(function () {
        $(this).draggable({
            appendTo: "body",
            helper: "clone",
            cursor: "move",
            opacity: 0.8,
            revert: "invalid",//不放到目标址地就会返回
            scroll: false
        });
    });
    //3、流程图接收图标 并且做出对应的事件
    //判断拖动的图标是否超过了画布的范围，如果是，重新定义画布的大小，网格的大小，包裹层的大小
    //addNode()增加节点
    $(".designer_layout").droppable({
        accept: ".shape_img",
        drop: function (event, ui) {
            var offset = ui.offset;
            addShapNode(ui);
            var x = offset.left - $("#designer_canvas").offset().left;
            if (x > $("#designer_canvas").width()) {
                tag.changeCanvasSize(x);
            }
        }
    });
    // 4、宽高参数定义
    var winHeight = $(window).height();//浏览器窗口的高度
    var headerHeight = $('.designer_header').height();//头部的高度
    var canvasHeight = winHeight;
    var panelWidth = '180';//图标工具栏的宽度
    var winWidth = $(window).width();
    var canvasWidth = winWidth - panelWidth;
    $('.designer_layout').css({'height': canvasHeight, 'width': canvasWidth});//最下面的一层
    $('#canvas_container').css({'width': canvasWidth});//最下面的一层
    //6、流程图节点的回显
    tag = $("#designer_canvas").raphael().init({
        cwidth: canvasWidth, //画布宽度
        cheight: canvasHeight, //画布高度,
        url: webpath + '/serprocess/findNode',  //url 获取该画布流程图信息
        parms: {
            "flowChartId": idflowChartId,   //流程图ID
            "serVerId": serVerId,
            "serFlowType": serFlowType
        },
        base: '../resources/plugin/raphael/img',
        onContextMenu: (function (node) {
        }),
        onClickline: (function (line) {
            lineClick(line);
        }),
        onClick: (function (node) {
        }),
        onClose: (function (element) {
            nodeClose(element)
        }),
        onNodeDblclick: function (node) {
            nodeClick(node);
        },
        afterDrawLine: function (line) {
            afterDrawLine(line);
        },
        changeLine: function (line) {
            changeLine(line);
        }
    });
    
    //10、设置图标的显隐藏
    initIcon();
    function initIcon() {
        $('[nodetype=complex]').hide().parent().siblings().hide();
        $('[nodetype=personal]').hide().parent().siblings().hide();

    }

    //tag 初始化之后  重新设计画布的宽高
    tag.afterLoad = function () {
        tag.changeCanvasSize();
        initLineText();
    };

    //新建事件
    $("#new_btn").click(function () {
        location.href = webpath + "/serprocess/index?serFlowType=root2";
    });

    //返回事件
    $("#btn_back").click(function () {
        var typeflag = iscasType(serFlowType);
        if (typeflag) {
            window.location.href = webpath + "/serspLogin/index";
        } else {
            window.location.href = webpath + "/processTable/index";
        }
    });


    //快捷键ctrl+s
    Mousetrap.bind(['ctrl+s', 'command+s'], function () {
        $("#save_btn").trigger("click");
        return false;
    });
    //快捷键backspace
    Mousetrap.bind('del', function () {
        var element = tag.getSelectedNode();
        if (element != null) {
            if (!nodeClose(element)) {
                return;
            }
            tag.closeElement(element);
        }
        return true;
    });
    //放大事件
    $(".zoomscaling").click(function () {
        var canvas = tag.getCanvas();
        var w = canvas.width;
        var h = canvas.height;
        tag.changeCanvasSize(w * 2, h * 2);
    });
    //缩小事件
    $(".zoomout").click(function () {
        var canvas = tag.getCanvas();
        var w = canvas.width;
        var h = canvas.height;
        tag.changeCanvasSize(w / 2, h / 2);
    });
    //快捷键说明
    $('#shortcutDesc').unbind('click');
    $('#shortcutDesc').bind('click', function () {
        var shortcutHtml =
            '<div>保存 ctrl+s</div>' +
            '<div>删除 backspace</div>' +
            '<div>节点上移 up</div>' +
            '<div>节点左移 left</div>' +
            '<div>节点下移 down</div>' +
            '<div>节点右移 right</div>';
        layer.open({
            title: '快捷键说明',
            closeBtn: 0,
            content: shortcutHtml
        });
    });

    $('.toolbar  > div').on('click', function (e) {
        $(this).css({
            'background': '#ccc',
        });
        $(this).siblings().css({
            'background': '#f1f1f1',
        });
    });

});