<%--
  Created by IntelliJ IDEA.
  User: songshuzhong@bonc.com.cn
  Date: 2018/7/13
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath(); %>
<html>
<head>
    <title>数据库建模页面管理</title>
    <link rel="shortcut icon" href="<%= contextPath %>/resources/img/pageModel/jsp.png">
    <style type="text/css">
        .grid-container {
            margin: 0;
            display: grid;
            grid-template-columns: 100%;
            grid-template-rows: 35px 530px auto;
            grid-template-areas: "header" "aside" "footer";
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
        .item-aside {
            display: flex;
            flex-wrap: wrap;
            grid-area: aside;
            align-content: start;
            overflow-x: hidden;
            overflow-y: scroll;
        }
        .item-aside:before {
            width: 100%;
            height: 1px;
            background-color: grey;
            position: absolute;
            content: '';
        }
        .item-footer {
            grid-area: footer;
        }
        .item-footer:before {
            width: 100%;
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
<aside class="item-aside"></aside>
<footer class="item-footer"></footer>
<script type="text/javascript" src="<%= contextPath %>/resources/plugin/jquery/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="<%= contextPath %>/resources/js/util_common.js"></script>
<script>
    var pageTypeId = "${ pageTypeId }";
    var contextPath = "${ contextPath }";

    function handlePagesFetch() {
        $.ajax({
            type: 'get',
            url: "<%= contextPath %>/v1/pageModel/childDetail/" + pageTypeId,
            success : function( data ) {
                if ( data != null ) {
                    data = data.data;
                    if( data !== null && data.length !== 0 ){
                        renderLeafPeer( data );
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

    function handlePageModuleRename( pId, pageName, e ) {
        var input = "<input id='tempInput' value='" + pageName+ "' placeholder='请确认文件名称......' />";

        $( e.target ).parent().css( 'left', '216px');
        $( e.target ).parent().prev( 'span' ).remove();
        $( e.target ).parent().before( input );
        $( '#tempInput' ).focus();
    }

    function handlePageModuleEditor( pId, e ) {
        var url = "<%= contextPath %>/v1/pageModel/pageVisualize/" + pId;
        window.open( url,'', 'toolbar=no,menubar=no,scrollbars=no,resizable=no,location=yes,status=yes');
    }

    function handlePageModuleDelete( pId, e ) {
        if ( confirm( "确定要删除该条资源吗？" ) ){
            $.ajax({
                type: "delete",
                url: "<%= contextPath %>/v1/pageModel/" + pId,
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

    function renderLeafPeer( data ) {
        var leafs = "";

        data.forEach( function( leaf, index ) {
            leafs += "<artical class='item-artical' data-pageId='" + leaf.pageId +
                    "' data-updateDate='" + leaf.updateDate +
                    "' data-createBy='" + leaf.createBy +
                    "'data-createDate='" + leaf.createDate + "'>" +
                    "  <img style='width: 50px; height: 50px;' src='<%= contextPath %>/resources/img/pageModel/jsp.png' >" +
                    "  <span>" + leaf.pageName + "</span>" +
                    "  <menu class='artical-menu'>" +
                    "    <div class='menu-btn' data-type='0'>重命名</div>" +
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
        handlePagesFetch();
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
                    case '0': handlePageModuleRename( pId, pageName, e ); break;
                    case '1': handlePageModuleEditor( pId, e ); break;
                    case '2': handlePageModuleDelete( pId, e ); break;
                }
            } )
            .delegate( '#tempInput', 'keypress', function( e ) {
                if ( e.keyCode !== 13 ) return;

                var pageName = e.target.value;
                try {
                    $.ajax({
                        type: "post",
                        url: "<%= contextPath %>/v1/pageModel/savePageModel",
                        data: { "pageName": pageName, "pageTypeId": pageTypeId },
                        success: function(data){
                            if ( data.code == 201 ){
                                alert("添加页面成功！");
                                $( '#tempInput' ).before( "<span>" + pageName + "</span>" ).parent().find( 'input' ).remove();
                            } else {
                                alert("添加页面失败！");
                            }
                        }
                    });
                } catch ( e ) {
                    alert( "请先确认目标节点！");
                    return;
                }
            } );

    $( '#menu-btn-add' ).click( function() {
        var artical =  "<artical class='item-artical'>" +
                "  <img style='width: 50px; height: 50px;' src='<%= contextPath %>/resources/img/pageModel/jsp.png' >" +
                "  <input id='tempInput' value='new_page' placeholder='请确认文件名称......' />" +
                "  <menu class='artical-menu'>" +
                "    <div class='menu-btn' data-type='0'>重命名</div>" +
                "    <div class='menu-btn' data-type='1'>编辑</div>" +
                "    <div class='menu-btn' data-type='2'>删除</div>" +
                "  </menu>" +
                "</artical>";

        $( '.item-aside' ).prepend( artical );
        $( '#tempInput' ).focus();
    } );
</script>
</body>
</html>
