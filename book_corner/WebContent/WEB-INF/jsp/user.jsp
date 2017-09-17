<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
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
	function init() {
		var mode =<%=request.getAttribute("mode")%>;
		if (mode != "null") {
			if (mode == 1) {
				document.getElementById("button1").style.backgroundColor = "#FF0000";
			}
			if (mode == 2) {
				document.getElementById("button2").style.backgroundColor = "#FF0000";
			}
			if (mode == 3) {
				document.getElementById("button3").style.backgroundColor = "#FF0000";
			}
			if (mode == 4) {
				document.getElementById("button4").style.backgroundColor = "#FF0000";
			}
		}
	}
	function backToLastStep() {
		window.history.go(-1);
	}
	var i = 0;
	function changeReturnDate(that) {
		//alert(that.value);
		if(i==0){
			document.getElementById(that.value).innerHTML = "<form method='post' action='/book_corner/changePlanReturnDate'>"
					+ "<div align='center'><input type='date' name='planReturnDate'><br/>"
					+ "<input type='hidden' name='book_id' value='"
					+ that.value.replace(/change/, "")
					+ "' />&nbsp;&nbsp;"
					+ "<input type='submit' name='button' value='提交' />&nbsp;&nbsp;"
					+ "<button type='button' name='button' value='"
					+ that.value
					+ "' onclick='cancelChange(this)'>取消</button></div></form>";
					i=1;
					//break;
		}else if(i==1){
			cancelChange(that);
			i=0;
		}
	}
	function cancelChange(that) {
		document.getElementById(that.value).innerHTML = "";
	}
</script>
</head>

<body onload="init()">
	<div class="container">
		<ul class="menu">
			<li><a  onclick="backToLastStep()">返回</a></li>
			<li class="active"><a href="/book_corner/backHomeBookQuery">全部图书</a>
			</li>
			<li><a href="/book_corner/bookChartQuery">图书排行</a>
				<ul class="submenu">
					<li><a href="/book_corner/bookChartQuery?chart_page=1&chart_type=1">上月排行</a></li>
					<li><a href="/book_corner/bookChartQuery?chart_page=1&chart_type=2">借阅排行</a></li>
					<li><a href="/book_corner/bookChartQuery?chart_page=1&chart_type=3">收藏排行</a></li>
					<li><a href="/book_corner/bookChartQuery?chart_page=1&chart_type=4">人气排行</a></li>
				</ul>
			</li>
			<li><a href="/book_corner/user">用户中心</a>
				<ul class="submenu">
					<li><a href="/book_corner/user">借还详情</a></li>
					<li><a href="/book_corner/getMyFavorite">我的收藏</a></li>
					<li><a href="/book_corner/getMyPraise">我的点赞</a></li>
					<li><a href="/book_corner/getMyComment">我的书评</a></li>
				</ul>
			</li>
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
			<h1 align="center">用户中心</h1>
		</div>
	</div>
	<div align="center">
		<table  style="width:500" border="1">
			<tr>
				<td width="100"><div align="center">已借</div></td>
				<td width="100"><div align="center">待还</div></td>
				<td width="100"><div align="center">收藏</div></td>
				<td width="100"><div align="center">点赞</div></td>
				<td width="100"><div align="center">书评</div></td>
			</tr>
			<tr>
				<td><div align="center">${userInfo.borrowCount}</div></td>
				<td><div align="center">${userInfo.staystillCount}</div></td>
				<td><div align="center">${userInfo.favouriteCount}</div></td>
				<td><div align="center">${userInfo.praiseCount}</div></td>
				<td><div align="center">${userInfo.commentCount}</div></td>
			</tr>
		</table>
	</div>
	<br />
	<div align="center">
		<table  style="width:600px">
			<tr>
				<td><form id="form1" method="post" action="/book_corner/user">
						<div align="center">
							<input type="submit" name="button" id="button1" class="btn btn-lg btn-primary btn-block" value="借还详情" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/getMyFavorite">
						<div align="center">
							<input type="submit" name="button" id="button2" class="btn btn-lg btn-primary btn-block" value="我的收藏" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/getMyPraise">
						<div align="center">
							<input type="submit" name="button" id="button3" class="btn btn-lg btn-primary btn-block" value="我的点赞" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/getMyComment">
						<div align="center">
							<input type="submit" name="button" id="button4" class="btn btn-lg btn-primary btn-block" value="我的书评" />
						</div>
					</form></td>
			</tr>
		</table>
	</div>
	<br />
	<c:if test="${mode==1}">
		<div align="center">
			<table class="table table-bordered table-hover">
				<thead>
					<tr class="info">
						<th width="4%"><div align="center">编号</div></th>
						<th width="30%"><div align="center">书名</div></th>
						<th width="8%"><div align="center">状态</div></th>
						<th width="12%"><div align="center">借阅日期</div></th>
						<th width="12%"><div align="center">计划归还日期</div></th>
						<th width="12%"><div align="center">还书日期</div></th>
						<th width="12%"><div align="center">修改还书日期</div></th>
						<th width="10%"><div align="center">还书</div></th>
					</tr>
				</thead>
				<c:forEach items="${myBorrows}" var="myBorrow">
					<tr>
						<td><div align="center">${myBorrow.bookId}</div></td>
						<td><div align="center">
								<a href="/book_corner/bookDetail?book_id=${myBorrow.bookId}">${myBorrow.bookName}</a>
							</div></td>
						<td><div align="center">
								<c:if test='${myBorrow.returnDate!=null}'>
								已还
							</c:if>
								<c:if test='${myBorrow.returnDate==null}'>
								待还
							</c:if>
							</div></td>
						<td><div align="center">${myBorrow.borrowDate}</div></td>
						<td><div align="center">${myBorrow.planReturnDate}</div></td>
						<td><div align="center">${myBorrow.returnDate}</div></td>
						<td><div align="center">
								<c:if test='${myBorrow.returnDate!=null}'>
								</c:if>
								<c:if test='${myBorrow.returnDate==null}'>
									<form action='/book_corner/changeReturnDate'>
										<input type="hidden" name="book_id"
											value="${myBorrow.bookId}">
										<button value="change${myBorrow.bookId}"
											onclick="changeReturnDate(this)" type="button"
											class="btn btn-default delBtn" style="top: 0;">修改还书日期</button>
									</form>
									<div id="change${myBorrow.bookId}"></div>
								</c:if>
							</div></td>
						<td><div align="center">
								<c:if test='${myBorrow.returnDate!=null}'>
								</c:if>
								<c:if test='${myBorrow.returnDate==null}'>
									<form action='/book_corner/returnBook'>
										<input type="hidden" name="book_id"
											value="${myBorrow.bookId}">
										<button type="submit" class="btn btn-default delBtn"
											style="top: 0;">还书</button>
									</form>
								</c:if>
							</div></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

	<c:if test="${mode==2}">
		<div align="center">
			<table class="table table-bordered table-hover">
				<thead>
					<tr class="info">
						<th>条形码</th>
						<th>编号</th>
						<th>书名</th>
						<th>录入日期</th>
						<th>借阅历史</th>
						<th>收藏</th>
						<th>点赞</th>
						<th>评论</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${books}" var="book">
						<tr>
							<td width="120">${book.bookBarcode}</td>
							<td width="80"><div align="right">${book.bookId}</div></td>
							<td width="500"><a
								href="/book_corner/bookDetail?book_id=${book.bookId}">${book.bookName}</a>
							</td>
							<td><fmt:formatDate value='${book.createdAt}'
									pattern="yyyy-MM-dd" /></td>
							<td><div align="right">${book.borrowCount}</div></td>
							<td><div align="right">${book.favouriteCount}</div></td>
							<td><div align="right">${book.praiseCount}</div></td>
							<td><div align="right">${book.commentCount}</div></td>
							<c:if test="${book.borrowFlag=='0'}">
								<td>待还</td>
							</c:if>
							<c:if test="${book.borrowFlag=='1'}">
								<td>可借</td>
							</c:if>

							<td><c:if test="${book.borrowFlag=='0'}">
								</c:if> <c:if test="${book.borrowFlag=='1'}">
									<form action='/book_corner/userFavoriteBorrowBook'>
										<input type="hidden" name="book_id" value="${book.bookId}">
										<button type="submit" class="btn btn-default delBtn"
											style="top: 0;">借书</button>
									</form>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>

	<c:if test="${mode==3}">
		<div align="center">
			<table class="table table-bordered table-hover">
				<thead>
					<tr class="info">
						<th>条形码</th>
						<th>编号</th>
						<th>书名</th>
						<th>录入日期</th>
						<th>借阅历史</th>
						<th>收藏</th>
						<th>点赞</th>
						<th>评论</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${books}" var="book">
						<tr>
							<td width="120">${book.bookBarcode}</td>
							<td width="80"><div align="right">${book.bookId}</div></td>
							<td width="500"><a
								href="/book_corner/bookDetail?book_id=${book.bookId}">${book.bookName}</a>
							</td>
							<td><fmt:formatDate value='${book.createdAt}'
									pattern="yyyy-MM-dd" /></td>
							<td><div align="right">${book.borrowCount}</div></td>
							<td><div align="right">${book.favouriteCount}</div></td>
							<td><div align="right">${book.praiseCount}</div></td>
							<td><div align="right">${book.commentCount}</div></td>
							<c:if test="${book.borrowFlag=='0'}">
								<td>待还</td>
							</c:if>
							<c:if test="${book.borrowFlag=='1'}">
								<td>可借</td>
							</c:if>

							<td><c:if test="${book.borrowFlag=='0'}">
								</c:if> <c:if test="${book.borrowFlag=='1'}">
									<form action='/book_corner/userPraiseBorrowBook'>
										<input type="hidden" name="book_id" value="${book.bookId}">
										<button type="submit" class="btn btn-default delBtn"
											style="top: 0;">借书</button>
									</form>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>

	<c:if test="${mode==4}">
		<div align="center">
			<table class="table table-bordered table-hover" style="word-wrap: break-word; word-break: break-all;">
				<tr class="info">
					<th width="5%"><div align="center">编号</div></th>
					<th width="30%"><div align="center">书名</div></th>
					<th width="10%"><div align="center">是否匿名</div></th>
					<th width="10%"><div align="center">评论日期</div></th>
					<th width="5%"><div align="center">评级</div></th>
					<th width="35%"><div align="center">评论</div></th>
					<th width="5%"><div align="center">操作</div></th>
				</tr>
				<c:forEach items="${bookComments}" var="bookComment">
					<tr>
						<td align="center">${bookComment.commentId}</td>
						<td align="center"><a
								href="/book_corner/bookDetail?book_id=${bookComment.bookId}">${bookComment.bookName}</a></td>
						<td align="center">
							<c:if test="${bookComment.anonymouFlag=='0'}">否</c:if>
							<c:if test="${bookComment.anonymouFlag=='1'}">是</c:if>
						</td>
						<td align="center"><fmt:formatDate value='${bookComment.createdAt}'
									pattern="yyyy-MM-dd" /></td>
						<td align="left">${bookComment.commentGrade}</td>
						<td align="left">${bookComment.comment}</td>
						<td align="center"><form action='/book_corner/deleteComment'>
										<input type="hidden" name="commentId" value="${bookComment.commentId}">
										<button type="submit" class="btn btn-default delBtn"
											style="top: 0;">删除</button>
									</form></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

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