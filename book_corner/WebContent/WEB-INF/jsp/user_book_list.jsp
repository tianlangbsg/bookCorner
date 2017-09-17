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
		var chart_type =<%=request.getAttribute("user_type")%>;
		if (chart_type != "null") {
			if (chart_type == 1) {
				document.getElementById("button1").style.backgroundColor = "#FF0000";
			}
			if (chart_type == 2) {
				document.getElementById("button2").style.backgroundColor = "#FF0000";
			}
		}
	}
	//全选与反选
	function selectAll(){
		var inputs = document.getElementsByTagName("input");
		for(i=0;i<inputs.length;i++){
			if(inputs[i].type=="checkbox"){
				if(inputs[i].checked==false){
					inputs[i].checked=true;
				}else{
					inputs[i].checked=false;
				}
			}
		}
	}
</script>
<body onload="init()">
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

	<h1 align="center">${user.userFullname}的书单</h1>
		<div class="main">
			<div class="t_list">
				<table class="table table-bordered table-hover">
					<thead>
						<tr class="info">
							<th width="20%">记录编号</th>
							<th width="20%">图书编号</th>
							<th width="20%">图书名称</th>
							<th width="20%">借阅日期</th>
							<th width="20%">归还日期</th>
						</tr>
					</thead>
				<tbody>
					<c:forEach items="${bookBorrows}" var="bookBorrow">
						<tr>
							<td width="20%">${bookBorrow.borrowId}</td>
							<td width="20%">${bookBorrow.bookId}</td>
							<td width="20%"><a href="/book_corner/bookDetail?book_id=${bookBorrow.bookId}">${bookBorrow.bookName}</a></td>
							<td width="20%">${bookBorrow.borrowDate}</td>
							<c:if test="${bookBorrow.returnDate!=null}">
								<td width="20%">${bookBorrow.returnDate}</td>
							</c:if>
							<c:if test="${bookBorrow.returnDate==null}">
								<td width="20%">尚未归还</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
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