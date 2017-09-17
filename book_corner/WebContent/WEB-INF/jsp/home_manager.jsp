<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<base href="/book_corner/">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>web</title>
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/mystyle.css">
<link href="static/css/main.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript" src="static/jquery/jquery-1.3.2.js"></script>
<script src="static/bootstrap/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var message = "<%=request.getAttribute("message")%>";
		if (message != "null") {
			$(".modal-body").text(message);
			$("#myModal").modal('show');
			setTimeout(function() {
				$("#myModal").modal("hide")
			}, 3000);
		}
	});
	function backToLastStep() {
		window.history.go(-1);
	}
/* 	window.onbeforeunload = function(){
		var applyurl = "/book_corner/userLogOut";
		window.location=applyurl;
	} */
</script>
<body>
	<!-- <iframe  id="content" src="static/managerHeader.jsp" width="100%"></iframe> -->
	<div class="container">
		<ul class="menu">
			<!-- <li><a  onclick="backToLastStep()">返回</a></li> -->
			<li onclick="backToLastStep()"><a  >返回</a></li>
			<li><a>书籍管理</a>
				<ul class="submenu">
					<li><a href="/book_corner/backBookManageListQuery">图书管理</a></li>
					<li><a href="/book_corner/insertBookPage">录入新书</a></li>
					<li><a
						href="/book_corner/bookTypePage">图书类别</a></li>
					<li><a
						href="/book_corner/bookSourceTypePage">图书来源</a></li>
					<li><a
						href="/book_corner/bookSetPage">设置</a></li>
				</ul></li>
			<li class="active"><a>成员管理</a>
				<ul class="submenu">
					<li><a
						href="/book_corner/userReviewPage">审核名单</a></li>
					<li><a
						href="/book_corner/validUsers">成员名单</a></li>
					<li><a
						href="/book_corner/userChartBorrow">用户排行</a></li>
					<li><a href="/book_corner/bookBorrowRecord">借阅记录查询</a></li>
				</ul></li>
			<li><a>系统管理</a>
				<ul class="submenu">
					<li><a
						href="/book_corner/userAdminPage">权限管理</a></li>
				</ul></li>
			<li><a href="/book_corner/homeManagerPage">当前管理员：${sessionScope.system_manager.getManagerName()!=null?sessionScope.system_manager.getManagerName():"游客"}</a>
				<ul class="submenu">
					<li><a href="/book_corner/user">用户中心</a></li>
					<li><a href="/book_corner/onlineUsersListPage">在线用户列表</a></li>
				</ul></li>
			<li><a
				href="/book_corner/managerLogOut">退出登录</a></li>
		</ul>
	</div>
	<h1 align="center">管理中心</h1>
	<br/>
	<div align="center">
		<table style="font-weight:bold; font-size:20px;width:600px"  border="1">
			<tr>
				<td width="290"><strong style="color: #008080">书籍管理</strong></td>
				<td width="294"><div align="right"></div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/bookManageListPage">图书管理</a></td>
				<td><div align="right">${bookCount}本书上架</div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/insertBookPage">录入新书</a></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/bookTypePage">图书类别</a></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/bookSourceTypePage">图书来源</a></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/bookSetPage">设置</a></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td><strong style="color: #008080">成员管理</strong></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/userReviewPage">审核名单</a></td>
				<td><div align="right">待审核：${notReviewUserCount}人</div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/validUsers">成员名单</a></td>
				<td><div align="right">有效成员：${validUserCount}人</div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/userChartBorrow">用户排行</a></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td>●<a style="color: #FF6666" href="/book_corner/bookBorrowRecord">借阅记录查询</a></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td><strong style="color: #008080">系统管理</strong></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td><p>
						●<a style="color: #FF6666" href="/book_corner/userAdminPage">权限管理</a>
					</p></td>
				<td><div align="right"></div></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><a href="/book_corner/backHomeBookQuery"><strong style="color: #008080">进入用户界面</strong></a></td>
				<td><div align="right"></div></td>
			</tr>
		</table>
		当前在线用户列表<br/>
		<c:forEach items="${onlineUsersMap}" var="user">
			${user.value}
			<br/>
		</c:forEach>
	</div>
	<!-- 对话框开始 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="text-align:center">提示</h4>
				</div>
				<div class="modal-body" style="text-align:center"></div>
			</div>
		</div>
	</div>
	<!-- 对话框结束 -->
</body>
</html>