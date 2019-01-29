<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../common-head.jsp"></jsp:include>
	<title>我的demo页面</title>
	<link rel="stylesheet" href="<%=webpath %>/resources/plugin/webuploader-0.1.5/webuploader.css">
	<link rel="stylesheet" href="<%=webpath %>/resources/css/demo/index.css" />
	<%@ include file="../common-layer-ext.jsp/"%>
</head>
<body>
	<div class="container-fluid content">
		<div class="row">
			<div class="col-lg-12 col-md-12">
				<div class="part">
					<p class="title">按钮</p>
					<hr>
					<div class="btnsWrap">
						<button type="button" class="b-redBtn b-delBtn"><span>删除</span></button>
						<button type="button" class="b-redBtn btn-i"><i class="iconfont i-btn" title="删除">&#xe614;</i><span>删除</span></button>
					</div>
					<p class="codeTitle">代码依次如下：</p>
					<code class="language-html" data-lang="html">
						<pre>&lt;button type="button" class="<em>b-redBtn b-delBtn</em>"&gt;&lt;span&gt;删除&lt;/span&gt;&lt;/button&gt;
&lt;button type="button" class="<em>b-redBtn btn-i</em>"&gt;&lt;i class="<em>iconfont i-btn</em>" title="删除"&gt;&amp#xe614;&lt;/i&gt;&lt;span&gt;删除&lt;/span&gt;&lt;/button&gt;</pre>
					</code>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">默认图标的树</p>
					<hr>
					<div id="default-tree">
						<ul id="defaultTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">修改图标的树</p>
					<hr>
					<div id="idTreeContent" class="identity-tree">
						<ul id="identityTree" class="ztree"></ul>
					
						<!-- 右键文件夹的菜单 -->
						<div id="folderContextMenu" class="dropdown bootstrapMenu" style="z-index: 10000; position: absolute; display: none; height: 23px; width: 158px;">
						      <ul class="dropdown-menu" style="position:static;display:block;font-size:0.9em;">
						          <li>
						              <a href="javascript:void(0);" class="addFolder">
						                 <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i> 
						                 <span>添加文件夹</span>
						              </a>
						          </li>
						          <li>
						              <a href="javascript:void(0);" class="addInnerLink">
						                 <i class="fa fa-fw fa-lg glyphicon glyphicon-link"></i> 
						                 <span>添加内链接</span>
						              </a>
						          </li>
						          <li>
						              <a href="javascript:void(0);" class="addOuterLink">
						                 <i class="fa fa-fw fa-lg glyphicon glyphicon-paperclip"></i> 
						                 <span>添加外链接</span>
						              </a>
						          </li>
						          <li>
						              <a href="javascript:void(0);" class="updateResource">
						                 <i class="fa fa-fw fa-lg glyphicon glyphicon-edit"></i> 
						                 <span>修改资源</span>
						              </a>
						          </li>
						          <li>
						              <a href="javascript:void(0);" class="deleteResource">
						                 <i class="fa fa-fw fa-lg glyphicon glyphicon-trash"></i> 
						                 <span>删除资源</span>
						              </a>
						          </li>
						      </ul>
						</div>
						<!-- 右键菜单 -->
						<div id="linkContextMenu" class="dropdown bootstrapMenu" style="z-index: 10000; position: absolute; display: none; height: 23px; width: 158px;">
					    	<ul class="dropdown-menu" style="position:static;display:block;font-size:0.9em;">
					    	    <li>
					    	        <a href="javascript:void(0);" class="addFolder">
					    	           <i class="fa fa-fw fa-lg glyphicon glyphicon-folder-open"></i> 
					    	           <span>添加文件夹</span>
					    	        </a>
					    	    </li>
					    	    <li>
					    	        <a href="javascript:void(0);" class="addInnerLink">
					    	           <i class="fa fa-fw fa-lg glyphicon glyphicon-link"></i> 
					    	           <span>添加内链接</span>
					    	        </a>
					    	    </li>
					    	    <li>
					    	        <a href="javascript:void(0);" class="addOuterLink">
					    	           <i class="fa fa-fw fa-lg glyphicon glyphicon-paperclip"></i> 
					    	           <span>添加外链接</span>
					    	        </a>
					    	    </li>
					    	</ul>
						</div>
					</div>	
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">表单样式</p>
					<hr>
					<form class="form-inline" id="form">
						<div class="form-group">
							<label for="loginId">输入框:</label>
							<input type="text" class="form-control input-sm" />
						</div>
						<div class="form-group">
							<label for="loginId">下拉框:</label>							
							<div class="input-select">
								<input type="text" class="form-control input-sm" id="formSel" onclick="showMenu();" />
								<span id="menuBtn" class="select-btn" onclick="showMenu(); return false;"></span>							
								<div id="menuContent" class="menu-content">
									<ul id="downTree" class="ztree"></ul>
								</div>
							</div>
						</div>
						<br>	
						<div class="form-group">
							单选框：
							<a href="javascript:;" class="radio radio-l active" onclick="changeSingleBoxBg(this);">
								<label for="radioYes">是</label>
								<i id="radioYes" class="radio-img"></i>
							</a>
							<a href="javascript:;" class="radio" onclick="changeSingleBoxBg(this);">
								<label for="radioNo">否</label>
								<i id="radioNo" class="radio-img"></i>
							</a>
						</div>	
						<br>
						<div class="form-group">
							特殊单选框：
							<a href="javascript:;" class="circle-checkbox" onclick="changeCheckBoxBg(this);"></a>
						</div>
						<div class="form-group">
							复选框：
							<a href="javascript:;" class="checkbox" onclick="changeCheckBoxBg(this);"></a>
						</div>				
					</form>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part date">
					<p class="title">日程安排</p>
					<hr>
					<div class="dateWrap">
						<div id="dateArrange"></div>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">插件API链接</p>
					<hr>
					<div id="links">
						<a href="//v3.bootcss.com/css/#grid" target="_blank">Bootstrap栅格化布局</a>
						<a href="//www.datatables.net/reference/api/"  target="_blank">datatables API 官方网站</a>
						<a href="//www.treejs.cn/v3/api.php"  target="_blank">zTree API</a>
						<a href="//jqueryui.com/datepicker/"  target="_blank">JqueryUI datepicker官网</a>
						<a href="//www.my97.net/index.asp"  target="_blank">my97官网</a>
						<a href="//www.areaaperta.com/nicescroll/"  target="_blank">nicescroll官网</a>
						<a href="//www.layui.com/doc/modules/layer.html"  target="_blank">layer官网</a>
						<a href="//echarts.baidu.com/option.html#title"  target="_blank">echarts官网</a>
						<a href="//www.iconfont.cn/"  target="_blank">iconfont（更新图标字体需要在原来的基础上加）</a>
						<a href="//github.com/DiegoLopesLima/Validate#readme"  target="_blank">validator 官网</a>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">日期控件例子</p>
					<hr>
					jquery-ui日历插件：
					<div id="dateExp" class="form-group">
						<input class="form-control input-sm" id="jqueryDatePicker1" placeholder="开始日期" >
						<input class="form-control input-sm" id="jqueryDatePicker2" placeholder="结束日期" >
					</div>
					my97日历插件：
					<div class="my97Date">
						<input id="dStart" class="form-control input-sm" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'dEnd\')||\'2020-10-01\'}'})" placeholder="开始日期" /> 
						<input id="dEnd" class="form-control input-sm" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'dStart\')}',maxDate:'2020-10-01'})" placeholder="结束日期" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">			
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">上传文件</p>
					<hr>					
					<div id="uploader" class="wu-example">
						<!--用来存放文件信息-->
					    <div id="thelist" class="uploader-list"></div>
					    <div class="btns clearfix">
					        <div id="picker" class="fl picker"><i class="iconfont">&#xe696;</i>选择文件</div>
					        <button id="ctlBtn" class="b-redBtn btn-i fl">开始上传</button>
					    </div>
					</div>
				</div>
			</div>	
			<div class="col-lg-4 col-md-4">
				<div class="part">
					<p class="title">弹出层</p>
					<hr>					
					<div>
						<button type="button" class="b-redBtn btn-i" onclick="showLayer();"><span>普通弹出层</span></button>
						<button type="button" class="b-redBtn btn-i" onclick="showHrefLayer();"><span>带子页面的弹出层</span></button>						
					</div>
				</div>
			</div>			
			<div class="col-lg-12 col-md-12">
				<div class="part">
					<p class="title">表格</p>
					<hr>					
					<div class="table-wrapper">
						<div class="searchWrap">
                    		<form class="form-inline" id="userSearchForm">
								<div class="form-group">
 									<label for="loginId">登录账号:</label>
 									<input type="text" class="form-control input-sm" name="loginId" id="loginId" />
								</div>
								<div class="form-group">
 									<label for="userName">用户姓名:</label>
 									<input type="text" class="form-control input-sm" name="userName" id="userName"/>
								</div>
								<div class="form-group">
 									<label for="orgName">所属组织:</label>
 									<input type="text" class="form-control input-sm" name="orgName" id="orgName" readonly="readonly" />
								</div>
								<button type="button" class="b-redBtn btn-i" id="searchBtn"><i class="iconfont">&#xe67a;</i>查询</button>
								<button type="button" class="b-redBtn btn-i" id="resetBtn"><i class="iconfont">&#xe647;</i>重置</button>
								<button type="button" class="b-redBtn btn-i" id="addUserBtn"><i class="iconfont">&#xe635;</i>新建用户</button>
							</form>
               			</div>
						<table id="userTable">  
                       		<thead>
           						<tr>
               						<th>用户姓名</th>
               						<th>登录账号</th>
               						<th>电子邮箱</th>
              						<th>手机号码</th>
               						<th>用户状态</th>
               						<th>锁定次数</th>
               						<th>注册时间</th>
               						<th>操作</th>
           						</tr>
       						</thead>
                   		</table>
					</div>
				</div>
			</div>
		</div>			
	</div>
	
<script>
	var webpath = '<%=webpath%>';
</script>
<jsp:include page="../common-js.jsp"></jsp:include>
<script src="<%=webpath %>/resources/plugin/echarts3/echarts.min.js"></script>
<script src="<%=webpath %>/resources/plugin/webuploader-0.1.5/webuploader.min.js"></script>
<script src="<%=webpath %>/resources/js/demo/index.js"></script>

</body>
</html>