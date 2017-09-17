<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
</head>
<script type="text/javascript" src="static/jquery/jquery-1.3.2.js"></script>
<script src="static/bootstrap/bootstrap.js"></script>

<script>
	$(function() {
		var wjx_s = "★";
		var wjx_k = "☆";
		//1. 给所有的li注册mouseenter事件
		$(".comment li").mouseenter(function() {
			//2. 让当前li和前面所有的li变成实心,让后面所有的变成空心
			//前面：prevAll()：
			//后面：nextAll()：
			$(this).text(wjx_s).prevAll().text(wjx_s);
			$(this).nextAll(wjx_k);
		});
		//2. 离开ul的时候，把所有的li变成空心
		$(".comment").mouseleave(function() {
			$(this).children().not(":first").text(wjx_k);
			$("li.current").text(wjx_s).prevAll().text(wjx_s)
		});
		//3. 给所有的li注册点击事件，点击的时候，留下点东西(class)
		$(".comment li").click(function() {
			document.getElementById("comment_grade").value=this.value;
			$(this).addClass("current").siblings().removeClass("current")
		})
	})
</script>
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
					<a href="/book_corner/bookChart">图书排行</a>
				</div></td>
			<td width="15%"><div align="center">
					<a href="/book_corner/user">用户中心</a>
				</div></td>
			<td width="25%"><div align="right">当前用户：${sessionScope.user.getUserFullname()!=null?sessionScope.user.getUserFullname():"游客"}</div></td>
		</tr>
	</table>
	<h1 align="center">图书评论</h1>

	<div align="center">
		<form id="form2" name="form2" method="post"
			action="/book_corner/attachComment">
			<input type="hidden" name="comment_grade" id="comment_grade"
				value="1" /> <input type="hidden" name="book_id" id="book_id"
				value="${book_id}" /> <input type="hidden" name="page" id="page"
				value="${page}" />
			<table  style="width:400" border="1">
				<tr>
					<td><div align="center">
							<strong>评分：</strong>
						</div></td>
					<td><div align="center">
							<ul class="comment">
								<li value="1">★</li>
								<li value="2">☆</li>
								<li value="3">☆</li>
								<li value="4">☆</li>
								<li value="5">☆</li>
							</ul>
						</div></td>
					<td width="160"><div align="center">
							<div align="center">
								<label for="checkbox"></label> <input type="checkbox"
									name="anonymou_flag" id="anonymou_flag" value="1" /> <label
									for="anonymou_flag">匿名发表</label>
							</div>
						</div></td>
				</tr>
				<tr>
					<td height="140" colspan="3">
						<p>
							<label for="book_comment"></label>
							<textarea name="book_comment" style="font-size: 20px"
								id="book_comment" cols="45" rows="10"></textarea>
						</p>
						<p align="center">
							<input type="submit" name="button" id="button" value="提交" />
						</p>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>