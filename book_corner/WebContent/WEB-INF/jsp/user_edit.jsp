<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
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
		//var message = $("#message").val();
		var message = "<%=request.getAttribute("message")%>";
		if (message != "null") {
			$(".modal-body").text(message);
			$("#myModal").modal('show');
			setTimeout(function() {
				$("#myModal").modal("hide")
			}, 3000);
		}
	});
	function formCheck(){
		var userCode = parseInt(document.getElementById("userCode").value);
		var userFullname = document.getElementById("userFullname").value;
		//判断员工号是否是数字
		if(isNaN(userCode)){
			alert("员工号限定为1-999的整数！");
		}else{
			//判断输入是否符合格式要求
			if(userCode<1||userCode>999){
				alert("员工号限定为1-999的整数！");
			}else{
				if(userFullname!=""){
					var applyurl = "/book_corner/editUser?userId="+${user.userId}+"&userCode="+userCode+"&userFullname="+userFullname;
					window.location=applyurl;
					//window.open(applyurl);
				}else{
				alert("用户名不能为空！");
				}
			}
		}
	}
	function backToLastStep() {
		window.history.go(-1);
	}
</script>

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

	<h1 align="center">修改用户信息</h1>

	<div class="container">
		<form class="form-signin" action="">
			员工号：<input type="text" id="userCode" name="userCode"
				class="form-control" value="${userCode}" required placeholder="员工号">
			用户名：<input type="text" id="userFullname" name="userFullname"
				class="form-control" value="${user.userFullname}" required
				placeholder="用户名">
				<button onclick="formCheck()"
					class="btn btn-lg btn-primary btn-block" type="button">修改</button>
			<c:if test='${user.validFlag.equals("0")}'>
				<a href="/book_corner/disableUser?userId=${user.userId}"><button class="btn btn-lg btn-primary btn-block" type="button">失效</button></a>
			</c:if>
			<c:if test='${user.validFlag.equals("1")}'>
				<a href="/book_corner/enableUser?userId=${user.userId}"><button class="btn btn-lg btn-primary btn-block" style="background-color:#F03" type="button">生效</button></a>
			</c:if>
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