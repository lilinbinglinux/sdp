var base = '../resources/plugin/raphael/img';

var processHtml =
    '<div class="designer" id="designer">' +
    '    <div class="designer_header">' +
    '        <div class="toolbar">' +
    '            <div id="btn_back">' +
    '               <img src="' + base + '/tool-return.png" class="toolimg">' +
    '               <span>返回</span>' +
    '           </div>' +
    '           <div id="save_btn">' +
    '               <img src="' + base + '/tool-save.png" class="toolimg">' +
    '               <span>保存</span>' +
    '           </div>' +
    '           <div id="shortcutDesc" style="width: auto;">快捷键说明</div>' +
    '           <div class="conHref" style="width: auto;">' +
    '               <a href="https://code.bonc.com.cn/confluence/pages/viewpage.action?pageId=14581995"' +
    '                   target="_blank">confluence说明文档</a>' +
    '           </div>' +
    '       </div>' +
    '   </div>' +
    '   <div class="designer_body">' +
    '       <div class="shape_panel">' +
    '<div class="shape_header"><div class="title">可视化编辑器</div></div>' +
    '<div class="shape_search"></div>' +

    '           <div class="shape_list" >' +
    '           <div class="shapes"><div class="shape_img "  nodetype="rectangle" nodeStyle="3">' +
    '                <img src="' + base + '/icon-setting.png" class=" ">' +
    '               <span class="tooltips">接口</span>' +
    '           </div></div>' +
    '           <div class="shapes"><div class="shape_img" nodetype="circle" nodeStyle="5">' +
    '                <img src="' + base + '/icon-aggregationStart.png" class=" "  />' +
    '               <span class="tooltips">聚合开始</span>' +
    '           </div></div>' +
    '           <div class="shapes"><div class="shape_img" nodetype="circle" nodeStyle = "6">' +
    '                <img src="' + base + '/icon-aggregationEnd.png" class=" " >' +
    '               <span class="tooltips">聚合结束</span>' +
    '           </div></div>' +
    '           </div>' +
    '           <div class="imgtips"></div>' +
    '       </div>' +
    '       <div class="designer_viewport">' +
    '           <div class="designer_layout">' +
    '               <div id="canvas_container">' +
    '                   <div id="designer_canvas">' +
    '                   </div>' +
    '               </div>' +
    '           </div>' +
    '       </div>' +
    '   </div>' +
    '</div>';