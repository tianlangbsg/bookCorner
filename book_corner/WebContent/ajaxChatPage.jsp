<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	function backToLastStep() {
		window.history.go(-1);
	}
	//发送消息
	function sendmsg() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("inputBox").value = "";
			}
		}
		xmlhttp.open("POST", "/book_corner/userChatSendMsg", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		//取得要发送的消息内容
		var msg = document.getElementById("inputBox").value;
		//取得接收人员工号
		var receiveUserCode = document.getElementById("receiveUserCode").value;
		xmlhttp.send("msg="+msg+"&sendUserCode=${sessionScope.user.getUserCode()}&sendUserName=${sessionScope.user.getUserFullname()}&receiveUserCode="+receiveUserCode);
	}
	//定时刷新消息
	function getmsg() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("msgBox").value = xmlhttp.responseText;
			}
		}
		xmlhttp.open("POST", "/book_corner/userChatReceiveMsg", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("userCode=${sessionScope.user.getUserCode()}");
	}
	window.setInterval(getmsg,1000);
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
				</ul>
			</li>
			<c:if test="${sessionScope.system_manager==null}">
				<li><a href="/book_corner/user">当前用户：${sessionScope.user.getUserFullname()!=null?sessionScope.user.getUserFullname():"游客"}</a></li>
			</c:if>
			<c:if test="${sessionScope.system_manager!=null}">
				<li><a href="/book_corner/homeManagerPage">当前管理员：${sessionScope.system_manager.getManagerName()!=null?sessionScope.system_manager.getManagerName():"游客"}</a>
					<ul class="submenu">
						<li><a href="/book_corner/user">用户中心</a></li>
					</ul>
				</li>
			</c:if>
			<li><a href="/book_corner/userLogOut">退出登录</a></li>
		</ul>
	</div>
	<h1 align="center">消息中心</h1>
	
	<div align="center">
		<textarea name="msgBox" style="font-size: 16px"
			id="msgBox" cols="45" rows="15"></textarea><br/>
		<textarea name="inputBox" style="font-size: 16px"
			id="inputBox" cols="45" rows="5"></textarea><br/>
		<button onclick="sendmsg()" type="button">发送消息</button>
	</div>

	<textarea name="receiveUserCode" style="font-size: 16px"
		id="receiveUserCode" cols="45" rows="5">DJB001</textarea><br/>
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