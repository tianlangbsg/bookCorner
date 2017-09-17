<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
	function init() {
		document.getElementById("return_status").value = "<%=request.getAttribute("return_status")%>";
		document.getElementById("start_date").value = "<%=request.getAttribute("start_date")%>";
		document.getElementById("end_date").value = "<%=request.getAttribute("end_date")%>";
	}
	//检查日期区间是否合法
	function checkDate(){
		var start_date = document.getElementById("start_date").value;
		var end_date = document.getElementById("end_date").value;
		if(start_date!=""&&end_date!=""){
			if(start_date>end_date){
				alert("起始日期不能晚于终止日期，请重新输入！");
				document.getElementById("start_date").value = "";
				document.getElementById("end_date").value = "";
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

	<h1 align="center">借阅记录查询</h1>

	<div align="center">
		<form id="form2" name="form2" method="post"
			action="/book_corner/bookBorrowRecord">
			<table width="600">
				<tr>
					<td width="25%"><div align="center">
							状态 <select id="return_status" name="return_status">
								<option value="0">全部</option>
								<option value="1">已还</option>
								<option value="2">待还</option>
							</select>
						</div></td>
					<td width="50%"><div align="center">
							日期:<input type="date" name="start_date" id="start_date" onchange="checkDate()">
						</div></td>
					<td width="25%"><div align="center">
							<input type="date" name="end_date" id="end_date" onchange="checkDate()">
						</div></td>
					<td width="25%"><button type="submit"
							class="btn btn-lg btn-primary btn-block">查询</button>
						<div align="center"></div></td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<table width="80%" border="1" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%"><div align="center">记录编号</div></td>
				<td width="20%"><div align="center">借阅人ID</div></td>
				<td width="20%"><div align="center">借阅人姓名</div></td>
				<td width="20%"><div align="center">借阅时间</div></td>
				<td width="20%"><div align="center">归还时间</div></td>
			</tr>
			<c:forEach items="${bookBorrows}" var="bookBorrow">
				<tr>
					<td><div align="center">${bookBorrow.borrowId}</div></td>
					<td><div align="center">${bookBorrow.userId}</div></td>
					<td><div align="center">${bookBorrow.userName}</div></td>
					<td><div align="center">${bookBorrow.borrowDate}</div></td>
					<td><div align="center">${bookBorrow.returnDate}</div></td>
				</tr>
			</c:forEach>
		</table>
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