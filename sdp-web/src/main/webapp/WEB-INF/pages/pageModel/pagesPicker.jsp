<%--
 Version v0.0.1
 User songshuzhong@bonc.com.cn
 Copyright (C) 1997-present BON Corporation All rights reserved.
 ------------------------------------------------------------
 Date         Author          Version            Description
 ------------------------------------------------------------
 2018年9月20日 songshuzhong    v0.0.1            添加快捷菜单
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <title>页面分类</title>
    <link rel="shortcut icon" href="<%= contextPath %>/resources/img/pageModel/jsp.png">
    <link rel="stylesheet" href="<%= contextPath %>/resources/css/pageModel/baseclassify.css">
    <link rel="stylesheet" href="<%= contextPath %>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="<%= contextPath %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <style type="text/css">
        .grid-container {
            margin: 0;
            display: grid;
            grid-template-columns: 20% 80%;
            grid-template-rows: 35px calc(100vh - 135px) auto;
            grid-template-areas: "header header" "nav aside" "nav footer";
            overflow: hidden;
            font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
            font-size: 14px;
        }
        .item-header {
            height: 100%;
            grid-area: header;
            display: flex;
            align-items: center;
        }
        .item-nav {
            grid-area: nav;
        }
        .item-aside {
            display: flex;
            flex-wrap: wrap;
            grid-area: aside;
            align-content: start;
            overflow-x: hidden;
            overflow-y: scroll;
        }
        .item-footer {
            grid-area: footer;
        }
        .item-nav:before {
            width: 100%;
            height: 1px;
            background-color: grey;
            position: absolute;
            content: '';
        }
        .item-aside:before {
            width: 1px;
            height: 100%;
            background-color: grey;
            position: absolute;
            content: '';
        }
        .item-footer:before {
            width: 80%;
            height: 1px;
            background-color: grey;
            position: absolute;
            content: '';
        }
        .item-aside .item-artical {
            width: 20%;
            height: 100px;
            box-sizing: border-box;
            padding: 10px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            position: relative;
            overflow: hidden;
        }
        .artical-active {
            border: 1px solid #7DA2CE;
            border-radius: 2px;
            box-shadow: 2px 4px 4px #888888;
            background: linear-gradient( to bottom right, #DBEAFC, #C1DCFC );
         }
        .item-footer span {
            padding: 3px 20px;
            display: block;
            box-sizing: border-box;
        }
        .item-breadcrumb {
            width: 60%;
            padding: 8px 15px;
            list-style: none;
        }
        .item-breadcrumb>li {
            display: inline-block;
        }
        .item-breadcrumb>li:before {
            padding: 0 5px;
            color: #ccc;
            content: "/\00a0";
        }
        .item-breadcrumb>li>a {
            color: #337ab7;
            text-decoration: none;
        }
        .artical-menu {
            width: 26%;
            height: 100px;
            top: 0;
            left: 100%;
            padding: 0;
            margin: 0;
            justify-content: center;
            position: absolute;
            display: flex;
            flex-direction: column;
            text-align: right;
            transition: all .5s;
        }
        .menu-btn {
            margin: 3px;
            padding: 3px;
            text-align: center;
            border-radius: 4px;
            border: 1px solid #2e6da4;
            background-color: #337ab7;
            font-size: .75em;
            color: white;
            cursor: pointer;
        }
        .item-nav-bar {
            width: 40%;
            padding: 8px 15px;
            display: flex;
            flex-direction: row;
            justify-content: flex-end;
            list-style: none;
        }
    </style>
</head>
<body class="grid-container">
<header class="item-header">
    <ol class="item-breadcrumb"></ol>
    <ol class="item-nav-bar">
        <li>
            <input placeholder="请输入检索关键字" />
            <button id="menu-btn-search" class="menu-btn">查找</button>
        </li>
        <li>
            <button id="menu-btn-add" class="menu-btn">新建</button>
        </li>
    </ol>
</header>
<nav class="item-nav">
    <ul id="pageTypeTree" class="ztree"></ul>
</nav>
<aside class="item-aside"></aside>
<footer class="item-footer"></footer>
<ul id="v-quickMenu" class="dropdown-menu" style="display: none;">
    <li><a id="v-quickMenu-edit" href="javascript:void(0)"><span class="glyphicon glyphicon-edit"></span>&nbsp;编辑</a></li>
    <li class="divider"></li>
    <li><a id="v-quickMenu-trash" href="javascript:void(0)"><span class="glyphicon glyphicon-trash"></span>&nbsp;删除</a></li>
    <li class="divider"></li>
    <li><a id="v-quickMenu-copy" href="javascript:void(0)"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;复制</a></li>
    <li class="divider"></li>
    <li><a id="v-quickMenu-rename" href="javascript:void(0)"><span class="glyphicon glyphicon-tag"></span>&nbsp;重命名</a></li>
</ul>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/jquery/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/js/util_common.js"></script>
<script>
    var pageTypeId = "${ pageTypeId }";
    var contextPath = "${ contextPath }";
    var pagesTreeRoot = { id: "root", name: "页面分类", isParent: true, open: true };
    var setting = {
        async: {
            type: "get",
            enable: true,
            url: getAsyncUrl,
            dataFilter: handleDataFilter
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            addHoverDom: handleAddHoverDom,
            removeHoverDom: handleRemoveHoverDom,
        },
        edit: {
            enable: true
        },
        callback: {
            onClick: handlePagesTreeClick,
            onRename: handlePagesTreeRename,
            onRemove: handlePagesTreeRemove
        }
    };

    function getAsyncUrl( treeId , treeNode ) {
        var id = ( treeNode!= undefined ) ? treeNode.id : pageTypeId;

        return "<%= contextPath %>/v1/pageType/childDetail/" + id;
    }

    function handleDataFilter( treeId, parentNode, childNodes ) {
        var nodes = childNodes.nodes;

        for ( var i = 0, length = nodes.length; i < length; i++ ){
            nodes[i].parent? nodes[i].isParent = true: null;
        }

        !parentNode? nodes.unshift( pagesTreeRoot ): null;

        return nodes;
    }

    function handlePagesTreeClick( event, treeId, treeNode ) {
        $.ajax({
            type: 'get',
            url: "<%= contextPath %>/v1/pageModel/childDetail/" + treeNode.id,
            success : function( data ) {
                if ( data != null ) {
                    data = data.data;
                    if( data !== null && data.length !== 0 ){
                        renderLeafPeer( data );
                        renderBreadCrumb();
                    } else {
                        confirm( '该节点下没有叶子节点！' );
                        $( '.item-aside' ).html( '' );
                    }
                }
            },
            error : function() {
                alert("请求错误！");
            }
        });
    }

    function handlePagesTreeRename( event, treeId, treeNode, isCancel ) {
        treeNode.id?
        $.ajax({
            type: "put",
            dataType: 'json',
            url: "<%= contextPath %>/v1/pageType/" + treeNode.id,
            data: { pageTypeName: treeNode.name },
            success: function( data ){
                alert( data.message );
            },
            error : function() {
                alert("请求错误！");
            }
        })
        :
        $.ajax({
            type: "post",
            url: "<%= contextPath %>/v1/pageType/savePageType",
            data: { pageTypeName: treeNode.name, pageParentId: treeNode.pId },
            success: function( data ){
                if ( data.code === 201 ){
                    alert( "添加节点成功！" );
                }else {
                    alert("添加节点失败！");
                }
            }
        });
    }

    function handlePagesTreeRemove( event, treeId, treeNode ) {
        $.ajax({
            type: "delete",
            dataType: 'json',
            url: "<%= contextPath %>/v1/pageType/" + treeNode.id,
            data: { pageTypeName: treeNode.name },
            success: function( data ){
                alert( data.message );
            },
            error : function() {
                alert("请求错误！");
            }
        });
    }

    function handleAddHoverDom(treeId, treeNode) {
        var sObj = $( "#" + treeNode.tId + "_span" );
        if ( treeNode.editNameFlag || $( "#addBtn_" + treeNode.tId ).length > 0 ) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $( "#addBtn_" + treeNode.tId );
        if ( btn ) btn.bind( "click", function() {
            window.pickerTree.addNodes( treeNode, { pId: treeNode.id, name: "new node" } );
            return false;
        } );
    }

    function handleRemoveHoverDom(treeId, treeNode) {
        $( '#addBtn_' +treeNode.tId) .unbind().remove();
    }

    function handlePageModuleRename( pId, pageName, e ) {
        var target = $( '.item-artical[data-pageid="'+ pId +'"]' );
        var input = $( "<input id='tempInput' value='" + pageName+ "' placeholder='请确认文件名称......' />" );

        target.find( 'span' ).remove();
        target.find( 'img' ).after( input );

        $( '#v-quickMenu' ).css( 'display', 'none' );
        $( '#tempInput' ).focus();
    }

    function handlePageModuleEditor( pId, e ) {
        var url = "<%= contextPath %>/v1/pageModel/pageVisualize/" + pId;
        //window.open( url,'', 'toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
        window.open( url,'', '');
    }

    function handlePageModuleDelete( pId, e ) {
        if ( confirm( "确定要删除该条资源吗？" ) ){
            $.ajax({
                type: "delete",
                url: "<%= contextPath %>/v1/pageModel/" + pId,
                success: function( data ) {
                    if ( data.code == 201 ){
                        $( '.item-artical[data-pageid="'+ pId +'"]' ).remove();
                    } else {
                        alert( data.message );
                    }
                }
            });
        }
    }

    function handleAddPageModule( copyId, pageName ) {
        copyId = copyId? copyId: '';
        pageName += '_副本';
        var artical =  "<artical class='item-artical'>" +
                "  <img style='width: 50px; height: 50px;' src='<%= contextPath %>/resources/img/pageModel/jsp.png' >" +
                "  <input id='tempInput' value='"+ pageName +"' data-copyid='"+ copyId +"' placeholder='请确认文件名称......' />" +
                "  <menu class='artical-menu'>" +
                "    <div class='menu-btn' data-type='0'>重命名</div>" +
                "    <div class='menu-btn' data-type='1'>编辑</div>" +
                "    <div class='menu-btn' data-type='2'>删除</div>" +
                "  </menu>" +
                "</artical>";

        $( '.item-aside' ).prepend( artical );
        $( '#tempInput' ).focus();
    }

    function renderBreadCrumb() {
        var selectNode = window.pickerTree.getSelectedNodes()[0];
        var activeCrumb = "";

        for( var i = 0; true; i++ ) {
            if ( selectNode ) {
                i === 0? activeCrumb = "<li>" + selectNode.name + "</li>" + activeCrumb: activeCrumb = "<li><a href='#'>" + selectNode.name + "</a></li>" + activeCrumb;
                selectNode = selectNode.getParentNode();
            } else {
                break;
            }
        }

        $( '.item-breadcrumb' ).html( "<li><a href='#'>root</a></li>" + activeCrumb );
    }

    function renderLeafPeer( data ) {
        var leafs = "";

        data.forEach( function( leaf, index ) {
            leafs += "<artical class='item-artical' data-pageId='" + leaf.pageId +
                     "' data-updateDate='" + leaf.updateDate +
                     "' data-createBy='" + leaf.createBy +
                     "'data-createDate='" + leaf.createDate + "'>" +
                     "  <img style='width: 50px; height: 50px;' src='<%= contextPath %>/resources/img/pageModel/jsp.png' >" +
                     "  <span>" + leaf.pageName + "</span>" +
                     "</artical>"
        } );

        $( '.item-aside' ).html( '' ).html( leafs );
    }

    function renderLeafDetail( pageId, createBy, createDate, updateDate ) {
        var detail = "<span>编号：" + pageId + "</span>" +
                "<span>创建者：" + createBy + "</span>" +
                "<span>创建日期：" + timestampToTime( parseInt( createDate ) ) + "</span>" +
                "<span>更新日期：" + timestampToTime( parseInt( updateDate ) ) + "</span>";

        $( '.item-footer' ).html( '' ).html( detail );
    }

    function renderQuickMenu( e ) {
        var pId = $( e.target ).parent().attr( 'data-pageid' );
        var pageName = $( e.target ).next( 'span' ).html();

        $( '#v-quickMenu' ).css( { display: 'block', top: e.pageY + 'px', left: e.pageX + 'px' } ).attr( 'data-pid', pId ).attr( 'data-pagename', pageName );
    }

    $( document ).ready( function () {
        window.pickerTree = $.fn.zTree.init( $( "#pageTypeTree" ), setting );
        document.oncontextmenu = function(){
            return false;
        };
        handlePagesTreeClick( null, null, { id: 'root' } );
    } );

    $( document ).click( function( e ) { $( '#v-quickMenu' ).css( 'display', 'none' ); } )
            .delegate( '.item-artical', 'mousedown', function( e ) {
                e.button===2&&e.target.src? renderQuickMenu( e ): $( '#v-quickMenu' ).css( 'display', 'none' );
            } )
            .delegate( '#v-quickMenu-edit', 'click', function( e ) {
                var pId = $( e.target ).parents('ul#v-quickMenu').attr( 'data-pid' );
                !pId ? alert('该页面尚未成功添加，无法进行操作！') : handlePageModuleEditor( pId );
            } )
            .delegate( '#v-quickMenu-trash', 'click', function( e ) {
                var pId = $( e.target ).parents('ul#v-quickMenu').attr( 'data-pid' );
                !pId ? alert('该页面尚未成功添加，无法进行操作！') : handlePageModuleDelete( pId );
            } )
            .delegate( '#v-quickMenu-copy', 'click', function( e ) {
                var copyId = $( e.target ).parents('ul#v-quickMenu').attr( 'data-pid' );
                var pageName = $( e.target ).parent().parent().attr( 'data-pagename' );
                !copyId ? alert('该页面尚未成功添加，无法进行操作！') : handleAddPageModule( copyId, pageName );
            } )
            .delegate( '#v-quickMenu-rename', 'click', function( e ) {
                var pId = $( e.target ).parents('ul#v-quickMenu').attr( 'data-pid' );
                var pageName = $( e.target ).parent().parent().attr( 'data-pagename' );
                !pId ? alert('该页面尚未成功添加，无法进行操作！') : handlePageModuleRename( pId, pageName, e )
            } );

    $( '.item-aside' ).delegate( 'artical', 'mousemove', function( e ) {
        $( this ).addClass( 'artical-active' ).siblings().removeClass( 'artical-active' );
        $( this ).children( '#tempInput' )[0]? null: $( this ).children( 'menu' ).css( 'left', '75%' );
        var target = $( e.target ).is( 'artical' )? $( e.target ): $( e.target ).parent();
        var pageId = target.attr( 'data-pageId' );
        var createBy = target.attr( 'data-createby' );
        var createDate = target.attr( 'data-createDate' );
        var updateDate = target.attr( 'data-updateDate' );
        renderLeafDetail( pageId, createBy, createDate, updateDate );
      } )
            .delegate( 'artical', 'mouseleave', function( e ) {
               $( this ).removeClass( 'artical-active' ).children( 'menu' ).css( 'left', '100%' ); } )
            .delegate( '#tempInput', 'keypress', function( e ) {
                if ( e.keyCode !== 13 ) return;

                var copyId = e.target.getAttribute( 'data-copyid' );
                var pageName = e.target.value;
                var pageId = e.target.parentNode.getAttribute( 'data-pageid' );
                try {
                    var node = window.pickerTree.getSelectedNodes()[ 0 ];
                    if ( copyId ) {
                        $.ajax({
                            type: "post",
                            url: "<%= contextPath %>/v1/pageModel/copyPageModel",
                            data: { "pageName": pageName, "pageTypeId": node.id, "pageId": copyId },
                            success: function(data){
                                if ( data.code == 201 ){
                                    alert("添加页面成功！");
                                    $( '#tempInput' ).before( "<span>" + pageName + "</span>" ).parent().find( 'input' ).remove();
                                    handlePagesTreeClick( null, null, node );
                                } else {
                                    alert("添加页面失败！");
                                }
                            }
                        });                     //pageModel复制
                    } else if ( pageId ) {
                        $.ajax({
                            type: "put",
                            url: "<%= contextPath %>/v1/pageModel/" + pageId,
                            data: { "pageName": pageName, "pageTypeId": node.id },
                            success: function(data){
                                if ( data.code == 201 ){
                                    alert("更新页面成功！");
                                    $( '#tempInput' ).before( "<span>" + pageName + "</span>" ).parent().find( 'input' ).remove();
                                    handlePagesTreeClick( null, null, node );
                                } else {
                                    alert("更新页面失败！");
                                }
                            }
                        });                       //pageModel更新
                    } else {
                        $.ajax({
                            type: "post",
                            url: "<%= contextPath %>/v1/pageModel/savePageModel",
                            data: { "pageName": pageName, "pageTypeId": node.id },
                            success: function(data){
                                if ( data.code == 201 ){
                                    alert("添加页面成功！");
                                    $( '#tempInput' ).before( "<span>" + pageName + "</span>" ).parent().find( 'input' ).remove();
                                    handlePagesTreeClick( null, null, node );
                                } else {
                                    alert("添加页面失败！");
                                }
                            }
                        });                     //pageModel创建
                    }
                } catch ( e ) {
                    alert( "请先确认目标节点！");
                }
            } );

    $( '#menu-btn-add' ).click( function() { handleAddPageModule() } );
    $( '#menu-btn-search' ).click( function( e ) {
        var key = e.target.value;
        var currNode = window.pickerTree.getSelectedNodes()[0];
    } );
</script>
</body>
</html>
