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
		//点击复选框时阻止点击事件穿透
		$(":checkbox").click(function(event){
			event.cancelBubble = true;
			event.stopPropagation();
		});
	});
	function backToLastStep() {
		window.history.go(-1);
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
	//删除选中的来源
	function deleteSelected(){
		var inputs = document.getElementsByTagName("input");
		var idList="";
		for(i=0;i<inputs.length;i++){
			if(inputs[i].type=="checkbox"){
				if(inputs[i].checked==true){
					idList = idList + inputs[i].id+",";
				}
			}
		}
		var applyurl = "/book_corner/deleteBookSourceTypes?idList="+idList;
		window.location=applyurl;
	}
	function selectThis(thisrow){
		if(document.getElementById(thisrow.children[0].children[0].id).checked == false){
			document.getElementById(thisrow.children[0].children[0].id).checked = true;
		}else{
			document.getElementById(thisrow.children[0].children[0].id).checked = false;
		}
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

	<h1 align="center">图书来源</h1>
	<br/>
	${book.delFlag}
	<div align="center">
		<table class="table table-bordered table-hover">
			<thead>
				<tr class="info">
					<th width="15%"><input id="selectAll" onclick="selectAll()"
						type="button" value="全选"></th>
					<th width="20%">来源ID</th>
					<th width="20%">来源名称</th>
					<th width="15%">更新</th>
					<th width="15%">生效</th>
					<th width="15%">删除</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bookSourceTypes}" var="bookSourceType">
					<form action='/book_corner/updateBookSourceType'>
						<input type="hidden" name="bookSourceTypeId"
							value="${bookSourceType.sourceTypeId}">
						<table width="100%" cellspacing="5" height="50">
							<tr >
								<td width="15%" onclick="selectThis(this)"><div align="center">
										<input type="checkbox"
											id="${bookSourceType.sourceTypeId}" />
									</div></td>
								<td width="20%"><div align="center">
										${bookSourceType.sourceTypeId}</div></td>
								<td width="20%"><div align="center">
										<input type="text" name="bookSourceTypeName"
											value="${bookSourceType.sourceTypeName}" placeholder="来源名称" />
									</div></td>
								<td width="15%"><div align="center">
										<button type="submit" class="btn btn-default delBtn"
											style="top: 0; background-color: #3F0">更新</button>
									</div></td>
								<td width="15%"><div align="center">
									<c:if test='${bookSourceType.delFlag.equals("0")}'>
										<a
											href="/book_corner/disableBookSourceType?bookSourceTypeId=${bookSourceType.sourceTypeId}">
											<button type="button" class="btn btn-default delBtn"
												style="top: 0; background-color: #3F0">失效</button>
										</a>
										</c:if>
									<c:if test='${bookSourceType.delFlag.equals("1")}'>
										<a
											href="/book_corner/enableBookSourceType?bookSourceTypeId=${bookSourceType.sourceTypeId}">
											<button type="button" class="btn btn-default delBtn"
												style="top: 0; background-color: #F03">生效</button>
										</a>
										</c:if>
									</div></td>
								<td width="15%"><div align="center">
										<a
											href="/book_corner/deleteBookSourceType?bookSourceTypeId=${bookSourceType.sourceTypeId}"><button
												type="button" class="btn btn-default delBtn"
												style="top: 0; background-color: #3F0">删除</button></a>
									</div></td>
							</tr>
						</table>
					</form>
				</c:forEach>

			</tbody>
		</table>

		<form action='/book_corner/insertBookSourceType'>
			<table width="100%" cellspacing="5" height="50">
				<tr>
					<td width="20%"><div align="center">添加来源类型：</div></td>
					<td width="20%"><div align="center">
							<input type="text" name="bookSourceTypeName" placeholder="来源名称" />
						</div></td>
					<td width="20%"><div align="center">
							<button value="${bookType.bookTypeId}" type="submit"
								class="btn btn-default delBtn"
								style="top: 0; background-color: #3F0">添加分类</button>
						</div></td>
					<td width="20%"></td>
					<td width="20%"><div align="center">
							<button value="${bookType.bookTypeId}" onclick="deleteSelected()"
								type="button" class="btn btn-default delBtn"
								style="top: 0; background-color: #3F0">删除选中项</button>
						</div></td>
				</tr>
			</table>
		</form>
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