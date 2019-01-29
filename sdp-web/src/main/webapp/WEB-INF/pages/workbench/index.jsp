<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String webpath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../common-head.jsp"></jsp:include>
	<title>我的工作台</title>
	<link rel="stylesheet" href="<%=webpath %>/resources/css/workbench/index.css" />
</head>
<body>
	<div class="container-fluid content row">
			<div class="col-lg-4 col-md-4">
				<div class="part userInfo">
					<img src="<%=webpath %>/resources/img/workbench/userIcon.png" class="userIcon">
					<div class="userMain">
						<div class="info">
							<p><span>你好，</span><span class="userName">Jerry</span></p>
							<p><span>今天是：</span><span class="today">2016年12月20日     星期二</span></p>
							<p><span>上次登录：</span><span class="lastLoginTime">12月4日    11：00</span></p>
							<p><span>地区：</span><span class="LaseLoginPos">吉林省长春市</span></p>
						</div>
						<div class="editLinkWrapper">
							<div class="editLinks">
								<a href="#" class="fl likeIcon"><i class="icon"></i><span>我的粉丝</span></a>
								<a href="#" class="fl personalIcon"><i class="icon"></i><span>个人设置</span></a>
								<a href="#" class="fl editIcon"><i class="icon"></i><span>个人设置</span></a>
							</div>
						</div>
						
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part appList">
					<p class="title">应用列表</p>
					<hr>
					<div class="applistWrap">
						<a href="#" class="fl"><i class="list1 icon"></i><span>电费</span></a>
						<a href="#" class="fl"><i class="list2 icon"></i><span>水费</span></a>
						<a href="#" class="fl"><i class="list3 icon"></i><span>燃气费</span></a>
						<a href="#" class="fl"><i class="list4 icon"></i><span>固话宽带</span></a>
						<a href="#" class="fl"><i class="list5 icon"></i><span>交通罚款</span></a>
						<a href="#" class="fl"><i class="list6 icon"></i><span>取暖</span></a>
						<a href="#" class="fl"><i class="list7 icon"></i><span>物业费</span></a>
						<a href="#" class="fl"><i class="list8 icon"></i><span>有线电视</span></a>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part sortList">
					<p class="title">最新新闻</p>
					<hr>
					<div class="sortListWrap">
						<ul>
							<li class="firSort"><span class="sort">01</span><a href="#"><span class="newsName">提高工作效率是有秘诀的</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">02</span><a href="#"><span class="newsName">虚拟机安装Centos6.4</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">03</span><a href="#"><span class="newsName">h1、h2、h3标签及strong标签</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">04</span><a href="#"><span class="newsName">内部链接十二条网页黄金规则 </span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">05</span><a href="#"><span class="newsName">更多周报管理日程安排</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">06</span><a href="#"><span class="newsName">提高工作效率是有秘诀的</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">07</span><a href="#"><span class="newsName">提高工作效率是有秘诀的</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">08</span><a href="#"><span class="newsName">虚拟机安装Centos6.4</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">09</span><a href="#"><span class="newsName">h1、h2、h3标签及strong标签</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">10</span><a href="#"><span class="newsName">内部链接十二条网页黄金规则 </span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">11</span><a href="#"><span class="newsName">更多周报管理日程安排</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">12</span><a href="#"><span class="newsName">提高工作效率是有秘诀的</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">13</span><a href="#"><span class="newsName">提高工作效率是有秘诀的</span></a><span class="newsTime">12/12</span></li>
							<li><span class="sort">14</span><a href="#"><span class="newsName">提高工作效率是有秘诀的</span></a><span class="newsTime">12/12</span></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part history">
					<p class="title">用户充值历史</p>
					<hr>
					<div id="historyWrap">
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4">
				<div class="part summary">
					<p class="title">薪资信息概要</p>
					<hr>
					<div class="summaryWrap">
						<p class="red"><span class="sumTit">基本信息 :</span></p>
						<div class="container-fluid basicTopInfo">
							<div class="col-md-4 basicName">
								<p class="red">姓名</p>
								<p>爱德华</p>
							</div>
							<div class="col-md-4 basicApm">
								<p class="red">部门</p>
								<p>联通事业部</p>
							</div>
							<div class="col-md-4 basicId">
								<p class="red">人员编号</p>
								<p>999999</p>
							</div>
						</div>
						<hr>
						<div class="container-fluid basicTopInfo">
							<div class="col-md-6 basicAcount">
								<p class="red">账户</p>
								<p>99999988889</p>
							</div>
							<div class="col-md-6">
								<p class="red">实际收入</p>
								<p>9999999999</p>
							</div>
						</div>
					</div>
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
		</div>
	
<script>
	var webpath = '<%=webpath%>';
</script>
<jsp:include page="../common-js.jsp"></jsp:include>
<script src="<%=webpath %>/resources/plugin/echarts3/echarts.min.js"></script>
<script src="<%=webpath %>/resources/js/workbench/index.js"></script>
</body>
</html>