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

	function backToLastStep() {
		window.history.go(-1);
	}
	function formCheck(){
		var defaultBorrowDays = parseInt(document.getElementById("DEFAULT_BORROW_DAYS").value);
		var maxBorrowCount = parseInt(document.getElementById("MAX_BORROW_COUNT").value);
		//判断员是否是数字和空
		if(isNaN(defaultBorrowDays)||defaultBorrowDays==""){
			alert("默认借阅周期请输入数字！");
		}else{
			if(isNaN(maxBorrowCount)||maxBorrowCount==""){
				alert("最大借阅本数请输入数字！");
			}else{
				//提交修改请求
				var applyurl = "/book_corner/bookSet?defaultBorrowDays="+defaultBorrowDays+"&maxBorrowCount="+maxBorrowCount;
				window.location=applyurl;
			}
		}
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
			<h1 align="center">设置</h1>
		</div>
	</div>
	<div class="container">
		<form class="form-signin" action="">
		<c:forEach items="${systemParmsets}" var="systemParmset">
			<div align="center">
				${systemParmset.parmTitle}:<input type="text" id="${systemParmset.parmNameEn}"
				name="${systemParmset.parmNameEn}" value="${systemParmset.setValues}"  required placeholder="${systemParmset.setValues}">${systemParmset.unitName}
			</div>
		</c:forEach>
			<button onclick="formCheck()"
				class="btn btn-lg btn-primary btn-block" type="button">提交修改</button>
		</form>
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