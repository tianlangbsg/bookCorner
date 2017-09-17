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
		//点击复选框时阻止点击事件穿透
		$(":checkbox").click(function(event){
			//IE
			event.cancelBubble = true;
			//其他浏览器
			event.stopPropagation();
		});
	});
	function backToLastStep() {
		window.history.go(-1);
	}
	function init() {
		var chart_type =<%=request.getAttribute("review_type")%>;
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
	//批量通过
	function adoptSelected(){
		var inputs = document.getElementsByTagName("input");
		var idList="";
		for(i=0;i<inputs.length;i++){
			if(inputs[i].type=="checkbox"){
				if(inputs[i].checked==true){
					idList = idList + inputs[i].id+",";
				}
			}
		}
		var applyurl = "/book_corner/adoptUsers?idList="+idList;
		window.location=applyurl;
	}
	//批量拒绝
	function rejectSelected(){
		var inputs = document.getElementsByTagName("input");
		var idList="";
		for(i=0;i<inputs.length;i++){
			if(inputs[i].type=="checkbox"){
				if(inputs[i].checked==true){
					idList = idList + inputs[i].id+",";
				}
			}
		}
		var applyurl = "/book_corner/rejectUsers?idList="+idList;
		window.location=applyurl;
	}
	function selectThis(thisrow){
		if(document.getElementById(thisrow.cells[0].children[0].id).checked == false){
			document.getElementById(thisrow.cells[0].children[0].id).checked = true;
		}else{
			document.getElementById(thisrow.cells[0].children[0].id).checked = false;
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

	<h1 align="center">审核名单</h1>
	<div align="center">
		<table style="width: 600px">
			<tr>
				<td><form id="form1" method="post"
						action="/book_corner/userReviewPage">
						<div align="center">
							<input type="submit" name="button1" id="button1"
								class="btn btn-lg btn-primary btn-block" value="等待审核" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/adoptReviewUsers">
						<div align="center">
							<input type="submit" name="button2" id="button2"
								class="btn btn-lg btn-primary btn-block" value="通过审核" />
						</div>
					</form></td>
				<td><form id="form1" method="post"
						action="/book_corner/rejectReviewUsers">
						<div align="center">
							<input type="submit" name="button3" id="button3"
								class="btn btn-lg btn-primary btn-block" value=拒绝申请 />
						</div>
					</form></td>
			</tr>
		</table>
	</div>
	
	<c:if test='${review_type==1}'>
		<div class="main">
			<div class="t_list">
				<table class="table table-bordered table-hover">
					<thead>
						<tr class="info">
							<th width="10%"><input id="selectAll" onclick="selectAll()"
								type="button" value="全选"></th>
							<th width="10%">编号</th>
							<th width="20%">OPENID</th>
							<th width="20%">员工号</th>
							<th width="20%">用户名</th>
							<th width="20%">申请日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userReviews}" var="userReview">
							<tr onclick="selectThis(this)">
								<td width="10%"><input type="checkBox"
									id="${userReview.reviewId}"></td>
								<td width="10%">${userReview.reviewId}</td>
								<td width="20%">${userReview.openId}</td>
								<td width="20%">${userReview.userCode}</td>
								<td width="20%"><div align="center">${userReview.userName}</div></td>
								<td width="20%"><div align="center">${userReview.applyAt}</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div align="center">
					<table width="600px">
						<tr>
							<td width="200px"><div align="center">
									<button onclick="adoptSelected()" type="button"
										class="btn btn-lg btn-primary btn-block">通过</button>
								</div></td>
							<td width="200px"></td>
							<td width="200px"><div align="center">
									<button onclick="rejectSelected()" type="button"
										class="btn btn-lg btn-primary btn-block">拒绝</button>
								</div></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test='${review_type==2}'>
		<div class="main">
			<div class="t_list">
				<table class="table table-bordered table-hover">
					<thead>
						<tr class="info">
							<th width="20%">编号</th>
							<th width="20%">OPENID</th>
							<th width="15%">员工号</th>
							<th width="15%">用户名</th>
							<th width="15%">申请日期</th>
							<th width="15%">加入日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userReviews}" var="userReview">
							<tr>
								<td width="20%">${userReview.reviewId}</td>
								<td width="20%">${userReview.openId}</td>
								<td width="15%">${userReview.userCode}</td>
								<td width="15%"><div align="center">${userReview.userName}</div></td>
								<td width="15%"><div align="center">${userReview.applyAt}</div></td>
								<td width="15%"><div align="center"><fmt:formatDate value='${userReview.updatedAt}'
										pattern="yyyy-MM-dd" /></div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
	
	<c:if test='${review_type==3}'>
		<div class="main">
			<div class="t_list">
				<table class="table table-bordered table-hover">
					<thead>
						<tr class="info">
							<th width="20%">编号</th>
							<th width="20%">OPENID</th>
							<th width="20%">员工号</th>
							<th width="20%">用户名</th>
							<th width="20%">申请日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userReviews}" var="userReview">
							<tr>
								<td width="20%">${userReview.reviewId}</td>
								<td width="20%">${userReview.openId}</td>
								<td width="20%">${userReview.userCode}</td>
								<td width="20%"><div align="center">${userReview.userName}</div></td>
								<td width="20%"><div align="center">${userReview.applyAt}</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
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