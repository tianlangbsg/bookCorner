<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
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
	function backToLastStep() {
		window.history.go(-1);
	}
	function init() {
		var chart_type =<%=request.getAttribute("chart_type")%>;
		if (chart_type != "null") {
			if (chart_type == 1) {
				document.getElementById("button1").style.backgroundColor = "#FF0000";
			}
			if (chart_type == 2) {
				document.getElementById("button2").style.backgroundColor = "#FF0000";
			}
			if (chart_type == 3) {
				document.getElementById("button3").style.backgroundColor = "#FF0000";
			}
			if (chart_type == 4) {
				document.getElementById("button4").style.backgroundColor = "#FF0000";
			}
		}
	}
</script>
<body onload="init()">
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
	<h1 align="center">图书排行榜</h1>
	<div align="center">
		<table style="width: 600px">
			<tr>
				<td><form id="form1" method="post"
						action="/book_corner/bookChartQuery?chart_page=1&chart_type=1">
						<div align="center">
							<input type="submit" name="button1" id="button1" class="btn btn-lg btn-primary btn-block" value="上月排行榜" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/bookChartQuery?chart_page=1&chart_type=2">
						<div align="center">
							<input type="submit" name="button2" id="button2" class="btn btn-lg btn-primary btn-block" value="借阅排行榜" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/bookChartQuery?chart_page=1&chart_type=3">
						<div align="center">
							<input type="submit" name="button3" id="button3" class="btn btn-lg btn-primary btn-block" value="收藏排行榜" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/bookChartQuery?chart_page=1&chart_type=4">
						<div align="center">
							<input type="submit" name="button4" id="button4" class="btn btn-lg btn-primary btn-block" value="人气排行榜" />
						</div>
					</form></td>
			</tr>
		</table>
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
								<td width="80">${book.bookId}</td>
								<td width="500"><a
									href="/book_corner/bookDetail?book_id=${book.bookId}">${book.bookName}</a>
									<%-- <form id="book_detail_form" action="/book_corner/bookDetail"
										method="post">
										<input type="hidden" name="book_id" value="${book.book_id}">
										<input type="submit"
											style="width: 540px; border-width: 0px; background-color: 0x000000;"
											value="${book.book_name}" />
									</form> --%></td>
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
										<form action='/book_corner/bookDetail'>
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
			<!-- 排行榜页码 -->
			<c:if test="${chart_page_count!=null}">
				<div align="center">共有${chart_book_count}条结果，分为${chart_page_count}页</div>
			</c:if>
			<div id="page_list" align="center">
				<c:if test="${chart_page_count!=null}">
					<table style="width: 600">
						<tr>
							<td width="100"><div align="center">
									<a
									href='/book_corner/bookChartQuery?chart_page=1&chart_type=<%=request.getAttribute("chart_type")%>'>首页</a>
									&nbsp;&nbsp; <a
									href='/book_corner/bookChartQuery?chart_page=${chart_page-1>0?chart_page-1:1}&
									chart_type=<%=request.getAttribute("chart_type")%>'>上一页</a>
								</div></td>
							<td width="319">
								<div align="center">
									<table>
										<c:forEach begin="${chart_page-5>0?chart_page-5:1}"
											end="${chart_page+5<chart_page_count?chart_page+5:chart_page_count}" step="1" var="i">
											<c:if test="${chart_page==i}">
												<td><a style="color: #F0F; border-width: 0px; top: 0;"
													href='/book_corner/bookChartQuery?chart_page=${i}&
													chart_type=<%=request.getAttribute("chart_type")%>'>${i}</a>
													&nbsp;</td>
											</c:if>
											<c:if test="${chart_page!=i}">
												<td><a style="border-width: 0px; top: 0;"
													href='/book_corner/bookChartQuery?chart_page=${i}&
													chart_type=<%=request.getAttribute("chart_type")%>'>${i}</a>
													&nbsp;</td>
											</c:if>
										</c:forEach>
									</table>
								</div>
							</td>
							<td width="100"><div align="center">
									<a
										href='/book_corner/bookChartQuery?chart_page=${chart_page+1<chart_page_count?chart_page+1:chart_page_count}&
										chart_type=<%=request.getAttribute("chart_type")%>
										'>下一页</a>
									&nbsp;&nbsp; <a
										href='/book_corner/bookChartQuery?chart_page=${chart_page_count}&
										chart_type=<%=request.getAttribute("chart_type")%>'>尾页</a>
								</div></td>
						</tr>
					</table>
				</c:if>
			</div>
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
	<!-- 对话框结束 -->
</body>
</html>