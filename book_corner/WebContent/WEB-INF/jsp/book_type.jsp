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
	function colorChange(colorBox){
		//设置单元格背景色
		document.getElementById("typeColor"+colorBox.id).style.backgroundColor=colorBox.value;
		//将颜色字符显示出来
		document.getElementById("text"+colorBox.id).innerHTML=colorBox.value.replace("#","").toUpperCase();
		//alert(colorBox.value);
	}
	//新建分类颜色动作
	function newTypeColorChange(colorBox){
		//设置单元格背景色
		document.getElementById("newTypeColor").style.backgroundColor=colorBox.value;
		//将颜色字符显示出来
		document.getElementById("newTypeText").innerHTML=colorBox.value.replace("#","").toUpperCase();
		//alert(colorBox.value);
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

	<h1 align="center">图书类型</h1>
	<br/>
	${book.delFlag}
	<div align="center">
		<table class="table table-bordered table-hover">
			<thead>
				<tr class="info">
					<th width="10%">类别ID</th>
					<th width="15%">类别名称</th>
					<th width="15%">类别颜色</th>
					<th width="15%">类别颜色预览</th>
					<th width="15%">生效状态</th>
					<th width="15%">编辑</th>
					<th width="15%">删除</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bookTypes}" var="bookType">
					<form action='/book_corner/updateBookType'>
						<input type="hidden" name="bookTypeId"
							value="${bookType.bookTypeId}">
						<table width="100%" cellspacing="5" height="50">
							<tr>
								<td width="10%"><div align="center">${bookType.bookTypeId}</div></td>
								<td width="15%"><div align="center">
										<input type="text" name="bookTypeName"
											value="${bookType.bookTypeName}" />
									</div></td>
								<td width="15%"><div align="center">
										<div id="text${bookType.bookTypeId}">${bookType.bookTypeColor}</div>
										<input type="color" id="${bookType.bookTypeId}"
											name="bookTypeColor" value="#${bookType.bookTypeColor}"
											onchange="colorChange(this)" />
									</div></td>
								<td width="15%" id="typeColor${bookType.bookTypeId}"
									bgcolor="${bookType.bookTypeColor}"></td>
								<c:if test='${bookType.delFlag.equals("0")}'>
									<td width="15%"><div style="text-align: center">
											<a
												href='/book_corner/disableBookType?bookTypeId=${bookType.bookTypeId}'>
												<button type="button" class="btn btn-default delBtn"
													style="top: 0; background-color: #3F0">失效</button>
											</a>
										</div></td>
								</c:if>
								<c:if test='${bookType.delFlag.equals("1")}'>
									<td width="15%"><div style="text-align: center">
											<a
												href='/book_corner/enableBookType?bookTypeId=${bookType.bookTypeId}'>
												<button type="button" class="btn btn-default delBtn"
													style="top: 0; background-color: #F03">生效</button>
											</a>
										</div></td>
								</c:if>
								<td width="15%"><div align="center">

										<input type="hidden" name="bookTypeId"
											value="${bookType.bookTypeId}">
										<button value="${bookType.bookTypeId}"
											onclick="editBookType(this)" type="submit"
											class="btn btn-default delBtn"
											style="top: 0; background-color: #3F0">更新</button>

									</div></td>
								<td width="15%"><div align="center">
										<a
											href='/book_corner/deleteBookType?bookTypeId=${bookType.bookTypeId}'>
											<button type="button" class="btn btn-default delBtn"
												style="top: 0; background-color: #3F0">删除</button>
										</a>
									</div></td>
							</tr>
						</table>
					</form>
				</c:forEach>
				<form action='/book_corner/insertBookType'>
					<table width="100%" cellspacing="5" height="50">
						<tr>
							<td width="10%"><div align="center">新建分类</div></td>
							<td width="15%"><div align="center">
									<input type="text" name="bookTypeName" placeholder="类别名称" />
								</div></td>
							<td width="15%"><div align="center">
									<div id="newTypeText"></div>
									<input type="color" name="bookTypeColor"
										onchange="newTypeColorChange(this)" />
								</div></td>
							<td width="15%" id="newTypeColor"></td>
							<td width="15%"><div align="center">
								<button value="${bookType.bookTypeId}"
									onclick="editBookType(this)" type="submit"
									class="btn btn-default delBtn"
									style="top: 0; background-color: #3F0">添加分类</button></div>
							</td>
							<td width="15%"></td>
							<td width="15%"></td>
						</tr>
					</table>
				</form>
			</tbody>
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