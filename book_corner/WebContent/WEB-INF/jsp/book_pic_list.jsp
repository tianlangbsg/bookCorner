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
</script>
</head>
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
	<h1 align="center">图片详情</h1>
	<div align="center">
<%-- 		<c:if test='${book.picPath!=""}'>
			<c:if test='${book.picPath!=null}'>
				<a href="pics/${book.picPath}"><img src="pics/${book.picPath}"
					width="500" height="500" /></a>
			</c:if>
		</c:if>
		<br /> <br /> --%>
		<c:if test='${book.picture1!=""}'>
			<c:if test='${book.picture1!=null}'>
				<a href="pics/${book.picture1}"><img src="pics/${book.picture1}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture2!=""}'>
			<c:if test='${book.picture2!=null}'>
				<a href="pics/${book.picture2}"><img src="pics/${book.picture2}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture3!=""}'>
			<c:if test='${book.picture3!=null}'>
				<a href="pics/${book.picture3}"><img src="pics/${book.picture3}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture4!=""}'>
			<c:if test='${book.picture4!=null}'>
				<a href="pics/${book.picture4}"><img src="pics/${book.picture4}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture5!=""}'>
			<c:if test='${book.picture5!=null}'>
				<a href="pics/${book.picture5}"><img src="pics/${book.picture5}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture6!=""}'>
			<c:if test='${book.picture6!=null}'>
				<a href="pics/${book.picture6}"><img src="pics/${book.picture6}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture7!=""}'>
			<c:if test='${book.picture7!=null}'>
				<a href="pics/${book.picture7}"><img src="pics/${book.picture7}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture8!=""}'>
			<c:if test='${book.picture8!=null}'>
				<a href="pics/${book.picture8}"><img src="pics/${book.picture8}"
					width="500" height="500" /></a> 
				<br />
				<br />
			</c:if>
		</c:if>
		<c:if test='${book.picture9!=""}'>
			<c:if test='${book.picture9!=null}'>
				<a href="pics/${book.picture9}"><img src="pics/${book.picture9}"
					width="500" height="500" /></a>
				<br />
				<br />
			</c:if>
		</c:if>
	</div>
</body>
</html>