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
</head>
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
	function load(){
		document.getElementById("bookNameOrBarcode").value="<%=request.getAttribute("bookNameOrBarcode") != null? request.getAttribute("bookNameOrBarcode"):" "%>";
		if (document.getElementById("bookNameOrBarcode").value == " ") {
			document.getElementById("bookNameOrBarcode").value = ""
		}
		document.getElementById("borrow_times").value =<%=request.getAttribute("borrow_times")%>;
		document.getElementById("created_time").value =<%=request.getAttribute("created_time")%>;
		document.getElementById("book_type").value =<%=request.getAttribute("book_type")%>;
	}
	function backToLastStep() {
		window.history.go(-1);
	}
</script>
<body onload="load()">
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
	<h1 align="center">书架</h1>

	<div align="center">
		<!-- 条件查询 -->
		<form action="/book_corner/homeBookQuery" method="post">
			<table width="900">
				<tr>
					<td width="20%"><div align="center">
							<input type="text" id="bookNameOrBarcode"
								name="bookNameOrBarcode" placeholder="请输入书名或条形码">
						</div></td>
					<td width="20%"><div align="center">
							借阅次数 <select id="borrow_times" name="borrow_times">
								<option value="0">请选择</option>
								<option value="1">降序</option>
								<option value="2">升序</option>
							</select>
						</div></td>
					<td width="30%"><div align="center">
							录入时间 <select id="created_time" name="created_time">
								<option value="0">请选择</option>
								<option value="1">降序</option>
								<option value="2">升序</option>
							</select>
						</div></td>
					<td width="40%"><div align="center">
							图书类别 <select id="book_type" name="book_type">
								<option value="0">请选择</option>
								<c:forEach items="${bookTypes}" var="bookType">
									<option value="${bookType.bookTypeId}">${bookType.bookTypeName}</option>
								</c:forEach>
							</select>
						</div></td>
					<td width="20%"><div align="center">
							<input type="hidden" name="page" value="1" />
							<button type="submit" class="btn btn-lg btn-primary btn-block">查询</button>
						</div></td>
				</tr>
			</table>
		</form>
	</div>

	<div>
		<!--class="content"  -->
		<div class="main">
			<div class="t_list">
				<table class="table table-bordered table-hover">
					<thead>
						<tr class="info">
							<th>条形码</th>
							<th>编号</th>
							<th>书名</th>
							<th>封面</th>
							<th>类型</th>
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
								<td width="450"><a
									href="/book_corner/bookDetail?book_id=${book.bookId}">${book.bookName}</a>
								</td>
								<td width="50"><c:if test='${!book.picture1.equals("")}'>
										<a href="/book_corner/bookDetail?book_id=${book.bookId}"><img
											width="50" height="40"
											src="/book_corner/pics/${book.picture1}" /></a>
									</c:if> <c:if test='${book.picture1.equals("")}'>
										<a href="/book_corner/bookDetail?book_id=${book.bookId}"><img
											width="50" height="40" src="/book_corner/pics/noImage.png" /></a>
									</c:if></td>
								<td bgcolor="${book.bookTypeColor}">${book.bookType}</td>
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
										<form action='/book_corner/homeBorrowBook'>
											<input type="hidden" name="book_id" value="${book.bookId}">
											<button type="submit" class="btn btn-default delBtn"
												style="top: 0;">借书</button>
										</form>
									</c:if>
									<c:if test="${book.currentUserBorrow==1}">
										<form action='/book_corner/homeReturnBook'>
											<input type="hidden" name="book_id" value="${book.bookId}">
											<button type="submit" class="btn btn-default delBtn"
												style="top: 0;background-color:#3F0">还书</button>
										</form>
									</c:if>
									</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page_count!=null}">
					<div align="center">共有${book_count}条结果，分为${page_count}页</div>
				</c:if>
				<c:if test="${page_count!=null}"></c:if>
				<div id="page_list" align="center">
					<c:if test="${page_count!=null}">
						<table style="width: 600">
							<tr>
								<td width="100"><div align="center">
										<a
											href='/book_corner/homeBookQuery?page=1&
										bookNameOrBarcode=<%=request.getAttribute("bookNameOrBarcode")%>&
										borrow_times=<%=request.getAttribute("borrow_times")%>&
										created_time=<%=request.getAttribute("created_time")%>&
										book_type=<%=request.getAttribute("book_type")%>'>首页</a>
										&nbsp;&nbsp; <a
											href='/book_corner/homeBookQuery?page=${page-1>0?page-1:1}&
										bookNameOrBarcode=<%=request.getAttribute("bookNameOrBarcode")%>&
										borrow_times=<%=request.getAttribute("borrow_times")%>&
										created_time=<%=request.getAttribute("created_time")%>&
										book_type=<%=request.getAttribute("book_type")%>'>上一页</a>
									</div></td>
								<td width="319">
									<div align="center">
										<table>
											<c:forEach begin="${page-5>0?page-5:1}"
												end="${page+5<page_count?page+5:page_count}" step="1"
												var="i">
												<c:if test="${page==i}">
													<td><a style="color: #F0F; border-width: 0px; top: 0;"
														href='/book_corner/homeBookQuery?page=${i}&
										bookNameOrBarcode=<%=request.getAttribute("bookNameOrBarcode")%>&
										borrow_times=<%=request.getAttribute("borrow_times")%>&
										created_time=<%=request.getAttribute("created_time")%>&
										book_type=<%=request.getAttribute("book_type")%>'>${i}</a>
														&nbsp;</td>
												</c:if>
												<c:if test="${page!=i}">
													<td><a style="border-width: 0px; top: 0;"
														href='/book_corner/homeBookQuery?page=${i}&
										bookNameOrBarcode=<%=request.getAttribute("bookNameOrBarcode")%>&
										borrow_times=<%=request.getAttribute("borrow_times")%>&
										created_time=<%=request.getAttribute("created_time")%>&
										book_type=<%=request.getAttribute("book_type")%>'>${i}</a>
														&nbsp;</td>
												</c:if>
											</c:forEach>
										</table>
									</div>
								</td>
								<td width="100"><div align="center">
										<a
											href='/book_corner/homeBookQuery?page=${page+1<page_count?page+1:page_count}&
										bookNameOrBarcode=<%=request.getAttribute("bookNameOrBarcode")%>&
										borrow_times=<%=request.getAttribute("borrow_times")%>&
										created_time=<%=request.getAttribute("created_time")%>&
										book_type=<%=request.getAttribute("book_type")%>'>下一页</a>
										&nbsp;&nbsp; <a
											href='/book_corner/homeBookQuery?page=${page_count}&
										bookNameOrBarcode=<%=request.getAttribute("bookNameOrBarcode")%>&
										borrow_times=<%=request.getAttribute("borrow_times")%>&
										created_time=<%=request.getAttribute("created_time")%>&
										book_type=<%=request.getAttribute("book_type")%>'>尾页</a>
									</div></td>
							</tr>
						</table>
					</c:if>
				</div>
			</div>
			<form id="form" action="/web-test/deleteUser" method="post">
				<input type="hidden" name="id" value="${book.bookId}"> <input
					type="hidden" id="message" name="message" value="${message}">
			</form>
		</div>
	</div>
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