<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style>
* {
	padding: 0;
	margin: 0;
}

.comment {
	font-size: 35px;
	color: darksalmon;
}

.comment li {
	float: left;
}

ul {
	list-style: none;
}
</style>
<base href="/book_corner/">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>书架</title>
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/mystyle.css">
<link href="static/css/main.css" rel="stylesheet" type="text/css">
<script src="static/jquery/jquery-1.3.2.js"></script>
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
</script>
</head>

<body>
	<div class="container">
		<ul class="menu">
			<li><a onclick="backToLastStep()">返回</a></li>
			<li><a>书籍管理</a>
				<ul class="submenu">
					<li><a href="/book_corner/backBookManageListQuery">图书管理</a></li>
					<li><a href="/book_corner/insertBookPage">录入新书</a></li>
					<li><a href="/book_corner/bookTypePage">图书类别</a></li>
					<li><a href="/book_corner/bookSourceTypePage">图书来源</a></li>
					<li><a href="/book_corner/bookSetPage">设置</a></li>
				</ul></li>
			<li class="active"><a>成员管理</a>
				<ul class="submenu">
					<li><a href="/book_corner/userReviewPage">审核名单</a></li>
					<li><a href="/book_corner/validUsers">成员名单</a></li>
					<li><a href="/book_corner/userChartBorrow">用户排行</a></li>
					<li><a href="/book_corner/bookBorrowRecord">借阅记录查询</a></li>
				</ul></li>
			<li><a>系统管理</a>
				<ul class="submenu">
					<li><a href="/book_corner/userAdminPage">权限管理</a></li>
				</ul></li>
			<li><a href="/book_corner/homeManagerPage">当前管理员：${sessionScope.system_manager.getManagerName()!=null?sessionScope.system_manager.getManagerName():"游客"}</a>
				<ul class="submenu">
					<li><a href="/book_corner/user">用户中心</a></li>
					<li><a href="/book_corner/onlineUsersListPage">在线用户列表</a></li>
				</ul></li>
			<li><a href="/book_corner/managerLogOut">退出登录</a></li>
		</ul>
	</div>

	<div align="center">
		<div align="center">
			<h1 align="center">图书详情</h1>
		</div>
		<table style="width: 450" border="1">
			<tr>
				<td width="95"><div align="right">书名：</div></td>
				<td width="185">${book.bookName}</td>
				<td width="198" rowspan="7"><c:if
						test='${!book.picture1.equals("")}'>
						<a href="/book_corner/bookPicList?book_id=${book.bookId}"><img
							width="200" height="200" src="/book_corner/pics/${book.picture1}" /></a>
					</c:if> <c:if test='${book.picture1.equals("")}'>
						<a href="/book_corner/bookPicList?book_id=${book.bookId}"><img
							width="200" height="200" src="/book_corner/pics/noImage.png" /></a>
					</c:if></td>
			</tr>
			<tr>
				<td><div align="right">来源类型：</div></td>
				<td>${book.sourceName}</td>
			</tr>
			<tr>
				<td><div align="right">录入时间：</div></td>
				<td><fmt:formatDate value='${book.createdAt}'
						pattern="yyyy-MM-dd" /></td>
			</tr>
			<tr>
				<td><div align="right">借阅历史：</div></td>
				<td>${book.borrowCount}</td>
			</tr>
			<tr>
				<td><div align="right">收藏：</div></td>
				<td>${book.favouriteCount}</td>
			</tr>
			<tr>
				<td><div align="right">点赞：</div></td>
				<td>${book.praiseCount}</td>
			</tr>
			<tr>
				<td><div align="right">评论：</div></td>
				<td>${book.commentCount}</td>
			</tr>
			<tr>
				<td><div align="right">状态：</div></td>
				<td><c:if test="${book.borrowFlag=='0'}">
						待还
					</c:if> <c:if test="${book.borrowFlag=='1'}">
						可借
					</c:if></td>
				<td><form id="form1" name="form1" method="post"
						action="/book_corner/bookPicList?book_id=${book.bookId}">
						<div align="right">
							<input type="submit" name="button" id="button" value="更多图片>>" />
						</div>
					</form></td>
			</tr>
		</table>
	</div>
	<br />
	<div align="center">
		<br />
		<table style="width: 500;word-wrap: break-word; word-break: break-all;" border="1" >
			<tr>
				<td width="100px"><div align="center">简介</div></td>
				<td width="400px" colspan="3">${book.bookIntro}</td>
			</tr>
		</table>
	</div>
	<br />
	<div align="center">
		<table width="300" border="0">
			<tr>
				<td width="100"><div align="center">
						<form id="form2" name="form2" method="post"
							action="/book_corner/bookEdit">
							<input type="hidden" name="book_id" value="${book.bookId}">
							<input type="hidden" name="page" value="${page}" /> <input
								type="submit" name="button2" id="button2" class="btn btn-lg btn-primary btn-block" value="编辑" />
						</form>
					</div></td>
				<td width="100"><div align="center">
						<form id="form2" name="form2" method="post"
							action="/book_corner/bookBorrowHistory">
							<input type="hidden" name="book_id" value="${book.bookId}">
							<input type="submit" name="button2" id="button2" class="btn btn-lg btn-primary btn-block" value="借阅历史" />
						</form>
					</div></td>
				<td width="100"><div align="center">
						<c:if test="${book.delFlag=='0'}">
							<form id="form2" name="form2" method="post"
								action="/book_corner/disableBook">
								<input type="hidden" name="book_id" value="${book.bookId}">
								<input type="submit" name="button2" class="btn btn-lg btn-primary btn-block" id="button2" value="下架" />
							</form>
						</c:if>
						<c:if test="${book.delFlag=='1'}">
							<form id="form2" name="form2" method="post"
								action="/book_corner/enableBook">
								<input type="hidden" name="book_id" value="${book.bookId}">
								<input type="submit" name="button2" id="button2" class="btn btn-lg btn-primary btn-block" style="background-color:#C33" value="上架" />
							</form>
						</c:if>
					</div></td>
			</tr>
		</table>
	</div>
	<div align="center">
		<table style="width: 800;word-wrap: break-word; word-break: break-all;" border="1"
			class="table table-bordered table-hover">
			<tr>
				<td colspan="4"><div align="center">书评</div></td>
			</tr>
			<tr>
				<td width="126"><div align="center">用户名</div></td>
				<td width="129"><div align="center">评分</div></td>
				<td width="375"><div align="center">评论</div></td>
				<td width="142"><div align="center">发表日期</div></td>
			</tr>
			<c:forEach items="${bookComments}" var="bookComment">
				<tr>
					<td width="126"><div align="center">
							<c:if test='${bookComment.anonymouFlag.equals("0")}'>
							${bookComment.userName}
						</c:if>
							<c:if test='${bookComment.anonymouFlag.equals("1")}'>
							${bookComment.userName}(匿名)
						</c:if>
						</div></td>
					<td width="129"><div align="center">${bookComment.commentGrade}</div></td>
					<td width="375"><div align="left">${bookComment.comment}</div></td>
					<td width="142"><div align="center">
							<fmt:formatDate value='${bookComment.createdAt}'
								pattern="yyyy-MM-dd" />
						</div></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<p>&nbsp;</p>
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
</body>
</html>