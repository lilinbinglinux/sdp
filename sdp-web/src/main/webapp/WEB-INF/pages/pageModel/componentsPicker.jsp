<%--
 Version v0.0.1
 User songshuzhong@bonc.com.cn
 Copyright (C) 1997-present BON Corporation All rights reserved.
 ------------------------------------------------------------
 Date         Author          Version            Description
 ------------------------------------------------------------
 2018年8月9日 songshuzhong    v0.0.1            修复组件通信
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <title>组件分类</title>
    <link rel="shortcut icon" href="<%= contextPath %>/resources/img/pageModel/component.png">
    <link rel="stylesheet" href="<%= contextPath %>/resources/css/pageModel/baseclassify.css">
    <link rel="stylesheet" href="<%= contextPath %>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
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
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/jquery/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/js/util_common.js"></script>
<script>
    var componentTypeId = "root";
    var contextPath = "${ contextPath }";
    var pagesTreeRoot = { id: "root", name: "组件分类", isParent: true, open: true };
    var componentCount = Math.floor( $( '.item-aside' ).height()/100 ) * 5 + 1;
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
            onClick: handleComponentsTreeClick,
            onRename: handleComponentsTreeRename,
            onRemove: handleComponentsTreeRemove
        }
    };

    function getAsyncUrl( treeId , treeNode ) {
        var id = ( treeNode!= undefined ) ? treeNode.id : componentTypeId;

        return "<%= contextPath %>/v1/pageModule/module/type/detail/" + id;
    }

    function handleDataFilter( treeId, parentNode, childNodes ) {
        var nodes = childNodes.nodes;

        for ( var i = 0, length = nodes.length; i < length; i++ ){
            nodes[i].parent? nodes[i].isParent = true: null;
        }

        !parentNode? nodes.unshift( pagesTreeRoot ): null;

        return nodes;
    }

    function handleComponentsTreeClick( event, treeId, treeNode ) {
        $.ajax({
            type: 'get',
            url: "<%= contextPath %>/v1/pageModule/module/type/moduleinfo/" + treeNode.id + "/0/" + componentCount,
            success : function( data ) {
                if ( data != null ) {
                    data = data.data;
                    if( data !== null && data.length !== 0 ){
                        renderLeafPeer( data );
                        renderBreadCrumb();
                    } else {
                        $( '.item-aside' ).html( '' );
                    }
                }
            },
            error : function() {
                alert("请求错误！");
            }
        });
    }

    function handleComponentsTreeRename( event, treeId, treeNode, isCancel ) {
        treeNode.id?
                $.ajax({
                    type: "put",
                    dataType: 'json',
                    url: "<%= contextPath %>/v1/pageModuleType/module/type/update/" + treeNode.id,
                    data: { moduleTypeName: treeNode.name, moduleParentId: treeNode.id },
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
                    url: "<%= contextPath %>/v1/pageModuleType/module/type",
                    data: { moduleTypeName: treeNode.name, moduleParentId: treeNode.pId },
                    success: function( data ){
                        if ( data.code === 201 ){
                            alert( "添加节点成功！" );
                        }else {
                            alert("添加节点失败！");
                        }
                    }
                });
    }

    function handleComponentsTreeRemove( event, treeId, treeNode ) {
        $.ajax({
            type: "delete",
            dataType: 'json',
            url: "<%= contextPath %>/v1/pageModuleType/module/type/delete/" + treeNode.id,
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

    function handleComponentPreview( pId, e ) {
        window.location.href = '<%= contextPath %>/v1/pageModule/moduleShow?moduleId=' + pId;
    }

    function handleComponentModuleEditor( pId, e ) {
        window.location.href = '<%= contextPath %>/v1/pageModule/editModule/' + pId;
    }

    function handleComponentModuleDelete( pId, e ) {
        if ( confirm( "确定要删除该条资源吗？" ) ){
            $.ajax({
                type: "delete",
                url: "<%= contextPath %>/v1/pageModule/module/delete/" + pId,
                success: function( data ) {
                    if ( data.code == 201 ){
                        $( e.target ).parent().parent().remove();
                    } else {
                        alert( data.message );
                    }
                }
            });
        }
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
            leafs += "<artical class='item-artical' data-pageId='" + leaf.moduleId +
                    "' data-updateDate='" + leaf.updateDate +
                    "' data-createBy='" + leaf.createBy +
                    "'data-createDate='" + leaf.createDate + "'>" +
                    "  <img style='width: 50px; height: 50px;' src='<%= contextPath %>/resources/img/pageModel/component.png' >" +
                    "  <span>" + leaf.moduleName + "</span>" +
                    "  <menu class='artical-menu'>" +
                    "    <div class='menu-btn' data-type='0'>预览</div>" +
                    "    <div class='menu-btn' data-type='1'>编辑</div>" +
                    "    <div class='menu-btn' data-type='2'>删除</div>" +
                    "  </menu>" +
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

    $( document ).ready( function () {
        window.pickerTree = $.fn.zTree.init( $( "#pageTypeTree" ), setting );
        handleComponentsTreeClick( null, null, { id: 'root' } );
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
            .delegate( '.menu-btn', 'click', function( e ) {
                var type = $( e.target ).attr( 'data-type' );
                var pId = $( e.target ).parent().parent().attr( 'data-pageId' );
                var pageName = $( e.target ).parent().prev( 'span' ).html();
                switch ( type ) {
                    case '0': handleComponentPreview( pId, pageName, e ); break;
                    case '1': handleComponentModuleEditor( pId, e ); break;
                    case '2': handleComponentModuleDelete( pId, e ); break;
                }
            } )

    $( '#menu-btn-add' ).click( function() {
        window.location.href = '<%= contextPath %>/v1/pageModule/addModule/' + window.pickerTree.getSelectedNodes()[0].id;
    } );
</script>
</body>
</html>
