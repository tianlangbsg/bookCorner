<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	function checkForm(){
		var userCode = parseInt(document.getElementById("userCode").value);
		//判断员工号是否是数字
		if(isNaN(userCode)){
			alert("员工号限定为1-999的整数！");
			document.getElementById("userCode").focus();
		}else{
			//判断输入是否符合格式要求
			if(userCode<1||userCode>999){
				alert("员工号限定为1-999的整数！");
				document.getElementById("userCode").focus();
			}
		}
	}
	function checkName(){
		var userFullname = document.getElementById("userFullname").value;
		if(userFullname==""){
			alert("用户名不能为空！");
			document.getElementById("userFullname").focus();
		}
	}
</script>
<body>
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
					<li><a href="/book_corner/ajaxChatPage">消息中心</a></li>
				</ul>
			</li>
			<c:if test="${sessionScope.system_manager==null}">
				<li><a href="/book_corner/user">当前用户：${sessionScope.user.getUserFullname()!=null?sessionScope.user.getUserFullname():"游客"}</a></li>
			</c:if>
			<c:if test="${sessionScope.system_manager!=null}">
				<li><a href="/book_corner/homeManagerPage">当前管理员：${sessionScope.system_manager.getManagerName()!=null?sessionScope.system_manager.getManagerName():"游客"}</a></li>
			</c:if>			<li><a href="/book_corner/userLogOut">退出登录</a></li>
		</ul>
	</div>
	<h1 align="center">用户登录</h1>
	<div class="container">
		<form class="form-signin" method="post" action="/book_corner/login">
			<label for="inputEmail" class="sr-only">员工号</label> <span id=error
				style="color: #F00"></span> <input type="text" value="${userCode}"
				id="userCode" name="userCode" class="form-control" required
				placeholder="员工号" onblur="checkForm()"> <label for="inputPassword"
				class="sr-only">用户名</label> <input type="text" value="${userName}"
				id="userFullname" name="userFullname" class="form-control"
				required placeholder="用户名" >
			<button class="btn btn-lg btn-primary btn-block" type="submit">登
				录</button>
		</form>
	</div>
	<div class="container">
		<form class="form-signin" action="/book_corner/apply">
			<button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
		</form>
	</div>
	<h1 align="center">系统管理员登录</h1>
	<div class="container">
		<form class="form-signin" method="post"
			action="/book_corner/right_manage">
			<label for="manager_name" class="sr-only">员工号</label> <span id=error
				style="color: #F00"></span> <input type="text" value="王康"
				id="manager_name" name="manager_name" class="form-control" required
				placeholder="管理员用户名"> <label for="password" class="sr-only">用户名</label>
			<input type="password" value="wangkang" id="password" name="password"
				class="form-control" required placeholder="管理员密码">
			<button class="btn btn-lg btn-primary btn-block" type="submit">登
				录</button>
		</form>
	</div>
	<%-- 	<%=request.getParameter("userName")%>
	<%=request.getParameter("userCode")%> --%>
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