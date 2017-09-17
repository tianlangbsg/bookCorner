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
</script>
<body>
	<table style="background-color: #78FAF7"  width="100%">
		<tr>
			<td width="15%"><div align="center">
					<input onclick="backToLastStep()" type="button" name="button2"
						id="button2" value="返回" />
				</div></td>
			<td width="15%"><div align="center">
					<a href="/book_corner/start">登录页面</a>
				</div></td>
			<td width="15%"><div align="center">
					<a href="/book_corner/backHomeBookQuery">全部图书</a>
				</div></td>
			<td width="15%"><div align="center">
					<a href="/book_corner/bookChartQuery">图书排行</a>
				</div></td>
			<td width="15%"><div align="center">
					<a href="/book_corner/user">用户中心</a>
				</div></td>
			<td width="25%"><div align="right">当前用户：${sessionScope.user.getUserFullname()!=null?sessionScope.user.getUserFullname():"游客"}</div></td>
		</tr>
	</table>
	<h1 align="center">图书排行榜</h1>
	<div align="center">
		<table  style="width:600px">
			<tr>
				<td><form id="form1" method="post"
						action="/book_corner/getLastMonthChart">
						<div align="center">
							<input type="submit" name="button" id="button" value="上月排行榜" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/getBorrowChart">
						<div align="center">
							<input type="submit" name="button" id="button" value="借阅排行榜" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/getFavoriteChart">
						<div align="center">
							<input type="submit" name="button" id="button" value="收藏排行榜" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/getPraiseChart">
						<div align="center">
							<input type="submit" name="button" id="button" value="人气排行榜" />
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
								<td width="120">${book.book_barcode}</td>
								<td width="80">${book.book_id}</td>
								<td width="500"><a
									href="/book_corner/bookDetail?book_id=${book.book_id}">${book.book_name}</a>
									<%-- <form id="book_detail_form" action="/book_corner/bookDetail"
										method="post">
										<input type="hidden" name="book_id" value="${book.book_id}">
										<input type="submit"
											style="width: 540px; border-width: 0px; background-color: 0x000000;"
											value="${book.book_name}" />
									</form> --%></td>
								<td bgcolor="${book.book_type_color}">${book.book_type}</td>
								<td><fmt:formatDate value='${book.created_at}'
										pattern="yyyy-MM-dd" /></td>
								<td><div align="right">${book.borrow_count}</div></td>
								<td><div align="right">${book.favourite_count}</div></td>
								<td><div align="right">${book.praise_count}</div></td>
								<td><div align="right">${book.comment_count}</div></td>
								<c:if test="${book.borrow_flag=='0'}">
									<td>待还</td>
								</c:if>
								<c:if test="${book.borrow_flag=='1'}">
									<td>可借</td>
								</c:if>

								<td>
								<c:if test="${book.borrow_flag=='0'}">
									</c:if> <c:if test="${book.borrow_flag=='1'}">
										<form action='/book_corner/bookDetail'>
											<input type="hidden" name="book_id" value="${book.book_id}">
											<button type="submit" class="btn btn-default delBtn"
												style="top: 0;">借书</button>
										</form>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

<%-- 				<div align="center">
					<c:if test="${page_count!=null}">
						<table width="600" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100"><div align="center">
										<a href="/book_corner/homeBookLimit?page=1">首页</a>
										&nbsp;&nbsp; <a
											href="/book_corner/homeBookLimit?page=${page-1>0?page-1:1}">上一页</a>
									</div></td>
								<td width="319">
									<div align="center">
										<table>
											<c:forEach begin="${page-5>0?page-5:1}"
												end="${page+5<page_count?page+5:page_count}" step="1"
												var="i">
												<c:if test="${page==i}">
													<td>
														<form action="/book_corner/homeBookLimit" method="post">
															<input type="hidden" name="page" value="${i}">
															<button type="submit"
																style="color: #F0F; border-width: 0px; top: 0;"
																value="${i}">${i}</button>
														</form>
													</td>
												</c:if>
												<c:if test="${page!=i}">
													<td>
														<!--  class="btn btn-default delBtn" -->
														<form action="/book_corner/homeBookLimit" method="post">
															<input type="hidden" name="page" value="${i}">
															<button type="submit" style="border-width: 0px; top: 0;"
																value="${i}">${i}</button>
														</form>
													</td>
												</c:if>
											</c:forEach>
										</table>
									</div>
								</td>
								<td width="100"><div align="center">
										<a
											href="/book_corner/homeBookLimit?page=${page+1<page_count?page+1:page_count}">下一页</a>
										&nbsp;&nbsp; <a
											href="/book_corner/homeBookLimit?page=${page_count}">尾页</a>

									</div></td>
							</tr>
						</table>
					</c:if>
				</div> --%>
			</div>
			<form id="form" action="/web-test/deleteUser" method="post">
				<input type="hidden" name="id" value="${book.book_id}"> <input
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
	<!-- 对话框结束 -->
</body>
</html>