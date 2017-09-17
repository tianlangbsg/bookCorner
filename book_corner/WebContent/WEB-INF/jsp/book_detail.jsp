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
	var commentButtonFlag=false;
	$(function() {
		var wjx_s = "★";
		var wjx_k = "☆";
		//1. 给所有的li注册mouseenter事件
		$(".comment li").mouseenter(function() {
			//2. 让当前li和前面所有的li变成实心,让后面所有的变成空心
			//前面：prevAll()：
			//后面：nextAll()：
			$(this).text(wjx_s).prevAll().text(wjx_s);
			$(this).nextAll(wjx_k);
		});
		//2. 离开ul的时候，把所有的li变成空心
		$(".comment").mouseleave(function() {
			$(this).children().not(":first").text(wjx_k);
			$("li.current").text(wjx_s).prevAll().text(wjx_s)
		});
		//3. 给所有的li注册点击事件，点击的时候，留下点东西(class)
		$(".comment li").click(function() {
			document.getElementById("comment_grade").value=this.value;
			$(this).addClass("current").siblings().removeClass("current")
		})
	})
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
	function commentButton(){
		if(commentButtonFlag == false){
			document.getElementById("commentBlock").style.display="";
			commentButtonFlag = true;
		}else{
			document.getElementById("commentBlock").style.display="none";
			commentButtonFlag = false;
		}
/* 		if(commentButtonFlag != false){
			document.getElementById("commentBlock").style.display="none";
			commentButtonFlag = true;
		} */
	}
</script>
</head>

<body>
	<div class="container">
		<ul class="menu">
			<li><a onclick="backToLastStep()">返回</a></li>
			<li class="active"><a href="/book_corner/backHomeBookQuery">全部图书</a>
			</li>
			<li><a href="/book_corner/bookChartQuery">图书排行</a>
				<ul class="submenu">
					<li><a
						href="/book_corner/bookChartQuery?chart_page=1&chart_type=1">上月排行</a></li>
					<li><a
						href="/book_corner/bookChartQuery?chart_page=1&chart_type=2">借阅排行</a></li>
					<li><a
						href="/book_corner/bookChartQuery?chart_page=1&chart_type=3">收藏排行</a></li>
					<li><a
						href="/book_corner/bookChartQuery?chart_page=1&chart_type=4">人气排行</a></li>
				</ul></li>
			<li><a href="/book_corner/user">用户中心</a>
				<ul class="submenu">
					<li><a href="/book_corner/user">借还详情</a></li>
					<li><a href="/book_corner/getMyFavorite">我的收藏</a></li>
					<li><a href="/book_corner/getMyPraise">我的点赞</a></li>
					<li><a href="/book_corner/getMyComment">我的书评</a></li>
				</ul></li>
			<c:if test="${sessionScope.system_manager==null}">
				<li><a href="/book_corner/user">当前用户：${sessionScope.user.getUserFullname()!=null?sessionScope.user.getUserFullname():"游客"}</a>
				<ul class="submenu">
					<li><a href="/book_corner/onlineUsersListPage">在线用户列表</a></li>
				</ul></li>
			</c:if>
			<c:if test="${sessionScope.system_manager!=null}">
				<li><a href="/book_corner/homeManagerPage">当前管理员：${sessionScope.system_manager.getManagerName()!=null?sessionScope.system_manager.getManagerName():"游客"}</a>
				<ul class="submenu">
					<li><a href="/book_corner/onlineUsersListPage">在线用户列表</a></li>
				</ul></li>
			</c:if>
			<li><a href="/book_corner/userLogOut">退出登录</a></li>
		</ul>
	</div>

	<div align="center">
		<div align="center">
			<h1 align="center">图书详情</h1>
		</div>
		<table  style="width:450" border="1">
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
		<table  style="width:500">
			<tr>
				<td width="125"><div align="center">
						<c:if test="${book.borrowFlag=='1'}">
							<form id="form2" name="form2" method="post"
								action="/book_corner/borrowBook">
								<input type="hidden" name="book_id" value="${book.bookId}">
								<input type="hidden" name="page" value="${page}" /> <input
									type="submit" name="button2" id="button2" value="借阅" />
							</form>
						</c:if>
						<c:if test="${book.borrowFlag!='1'}">
					待还
					</c:if>
						<td width="125">
							<div align="center">
								<c:if test="${favorite==true}">
									<form id="form2" name="form2" method="post"
										action="/book_corner/bookCancelFavorite">
										<input type="hidden" name="book_id" value="${book.bookId}" />
										<input type="submit" name="button2"
											style="background-color: #F03; color: #FFF" id="button2"
											value="已收藏" />
									</form>
								</c:if>
								<c:if test="${favorite!=true}">
									<form id="form2" name="form2" method="post"
										action="/book_corner/bookFavorite">
										<input type="hidden" name="book_id" value="${book.bookId}">
										<input type="submit" name="button2" id="button2" value="收藏" />
									</form>
								</c:if>
							</div>
						</td>
						<td width="125"><div align="center">
								<c:if test="${praise==true}">
									<form id="form2" name="form2" method="post"
										action="/book_corner/bookCancelPraise">
										<input type="hidden" name="book_id" value="${book.bookId}">
										<input type="submit" name="button2"
											style="background-color: #F03; color: #FFF" id="button2"
											value="已点赞" />
									</form>
								</c:if>
								<c:if test="${praise!=true}">
									<form id="form2" name="form2" method="post"
										action="/book_corner/bookPraise">
										<input type="hidden" name="book_id" value="${book.bookId}">
										<input type="submit" name="button2" id="button2" value="点赞" />
									</form>
								</c:if>
							</div></td>
						<td width="125"><div align="center">
								<form id="form2" name="form2" method="post"
									action="/book_corner/bookComment?page=${page}">
									<input type="hidden" name="book_id" value="${book.bookId}">
									<!-- <input type="submit" name="button2" id="button2" value="评论" /> -->
									<input type="button" onclick="commentButton()" name="button2" id="button2" value="评论" />
								</form>
							</div></td>
			</tr>
		</table>
		<br />
		<table  style="width:500;word-wrap: break-word; word-break: break-all;" border="1">
			<tr>
				<td width="100px"><div align="center">简介</div></td>
				<td width="400px" colspan="3">${book.bookIntro}</td>
			</tr>
		</table>
	</div>
	<br />
	<div id="commentBlock" align="center" style="display:none;word-wrap: break-word; word-break: break-all;">
		<form id="form2" name="form2" method="post"
			action="/book_corner/attachComment">
			<input type="hidden" name="comment_grade" id="comment_grade"
				value="1" /> <input type="hidden" name="book_id" id="book_id"
				value="${book.bookId}" /> <input type="hidden" name="page" id="page"
				value="${page}" />
			<table  style="width:400" border="1">
				<tr>
					<td><div align="center">
							<strong>评分：</strong>
						</div></td>
					<td><div align="center">
							<ul class="comment">
								<li value="1">★</li>
								<li value="2">☆</li>
								<li value="3">☆</li>
								<li value="4">☆</li>
								<li value="5">☆</li>
							</ul>
						</div></td>
					<td width="160"><div align="center">
							<div align="center">
								<label for="checkbox"></label> <input type="checkbox"
									name="anonymou_flag" id="anonymou_flag" value="1" /> <label
									for="anonymou_flag">匿名发表</label>
							</div>
						</div></td>
				</tr>
				<tr>
					<td height="140" colspan="3">
						<p>
							<label for="book_comment"></label>
							<textarea name="book_comment" style="font-size: 20px"
								id="book_comment" cols="45" rows="5"></textarea>
						</p>
						<p align="center">
							<input type="submit" name="button" id="button" value="提交" />
						</p>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<table  style="width:800;word-wrap: break-word; word-break: break-all;" border="1" class="table table-bordered table-hover">
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
							匿名用户
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