<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1.0, user-scalable=yes"/>
<link rel="Bookmark" href="<%=request.getContextPath() %>/resources/img/favicon.ico" />
<link rel="Shortcut Icon" href="<%=request.getContextPath() %>/resources/img/favicon.ico" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/lib/codemirror.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/foldgutter.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/theme/dracula.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/foldgutter.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/theme/eclipse.css">
<!--[if lte IE 9]>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/forIE.css" />
<![endif]-->
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/plugin/jquery/jquery-2.0.0.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/lib/codemirror.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/foldcode.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/foldgutter.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/brace-fold.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/xml-fold.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/indent-fold.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/markdown-fold.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/fold/comment-fold.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/hint/show-hint.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/hint/anyword-hint.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/hint/css-hint.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/hint/xml-hint.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/hint/html-hint.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/addon/hint/javascript-hint.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/mode/javascript/javascript.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/mode/xml/xml.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/mode/css/css.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/plugin/codemirror-5.36.0/mode/htmlmixed/htmlmixed.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/js/pageModel/codeMirror-format.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/js/pageModel/codeMirror-cs.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/js/pageModel/codeMirror-xml.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/js/pageModel/codeMirror-js.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/js/pageModel/codeMirror-html.js"></script>
<script type="text/javascript" defer src="<%= request.getContextPath() %>/resources/js/pageModel/codeMirror-setting.js"></script>
