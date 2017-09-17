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
	function sourceTypeChange(){
		var typeId = document.getElementById("sourceType").value;
		if(typeId==1){
			document.getElementById("donatorId1").style.display = "";
			document.getElementById("donatorId2").style.display = "";
		}else{
			document.getElementById("donatorId1").style.display = "none";
			document.getElementById("donatorId2").style.display = "none";
			document.getElementById("contributor_id").value = 0;
			document.getElementById("conInputBox").value = "";
		}
	}
	function sourceIdChange(){
		var index = document.getElementById("contributor_id").value;
		var contributors = document.getElementById("contributor_id").children;
		for (var i = 0; i < contributors.length; i++) {
			if(contributors[i].value==index){
				document.getElementById("conInputBox").value=contributors[i].text;
				break;
			}
		}
	}
	function addMorePic(i){
		document.getElementById("dpicture"+i).style.display = "";
	}
	function checkPicType(that){
		var fileName = that.value;
		var strs = fileName.split(".");
		var extend = strs[strs.length-1];
		//alert(extend);
		//判断是否是图片文件，如果不是则进行清空操作,同时弹出警告
		if(extend=="jpg"||extend=="jpeg"||extend=="gif"||extend=="bmp"||extend=="png"){
			preview(that);
			var id = parseInt(that.name.replace("picture","")) + 1;
			addMorePic(id);
		}else{
			//alert("请选择图片文件！");
			$(".modal-body").text("请选择图片文件！");
			$("#myModal").modal('show');
			setTimeout(function() {
				$("#myModal").modal("hide")
			}, 3000);
			var picPreview = document.getElementById("preview"+that.name.replace("picture",""));
			picPreview.style.width="0px";
			picPreview.style.height="0px";
			picPreview.innerHTML = "";
			that.value="";
		}
	}
	//显示图片预览
	function preview(file){
		var picPreview = document.getElementById("preview"+file.name.replace("picture",""));
		picPreview.style.width="200px";
		picPreview.style.height="200px";
		if(file.files&&file.files[0]){
			var reader = new  FileReader();
			reader.onload = function(evt){
				picPreview.innerHTML = '<img width="200px" height="200px" src="'+evt.target.result+'"/>';
			}
			reader.readAsDataURL(file.files[0]);
		}else{
			picPreview.innerHTML = "<img width='200px' height='200px' src="+file.value+"/>"
			alert(file.value);
		}
	}
	function load(){
		//初始化两个下拉框的选中值
		document.getElementById("book_type").value =${book.bookTypeId};
		document.getElementById("sourceType").value =${book.sourceId};
		if(document.getElementById("sourceType").value==1){
			document.getElementById("donatorId1").style.display = "";
			document.getElementById("donatorId2").style.display = "";
			document.getElementById("contributor_id").value = ${book.contributorId};
			document.getElementById("conInputBox").value = ${book.contributorId};
		}
	}
	//根据输入自动选定捐赠员工号
	function findContributor(inputContent) {
		var idOrName = inputContent.value;
		var contributors = document.getElementById("contributor_id1").children;
		//清空员工号下拉框
		document.getElementById("contributor_id").options.length=0;
		//添加一个基础“请选择”项
		document.getElementById("contributor_id").options.add(new Option("请选择",0));
		//如果符合条件，就添加一个option
		for (var i = 0; i < contributors.length; i++) {
			if(contributors[i].text.indexOf(idOrName)>=0){
				document.getElementById("contributor_id").options.add(new Option(contributors[i].text,contributors[i].value));
			}
		}
		var newcontributors = document.getElementById("contributor_id").children;
		document.getElementById("contributor_id").value = newcontributors[1].value;
	}
</script>
<body onload="load()">
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

	<h1 align="center">编辑图书信息</h1>
	<div align="center">
		<form enctype="multipart/form-data" method="post"
			action="/book_corner/updateBook">
			<input type="hidden" name="book_id" value="${book.bookId}" />
			<table style="line-height: 36px; width: 800px" border="1">
				<tr>
					<td width="200"><div align="left">条形码：</div></td>
					<td width="200" colspan="3"><input width="500" type="text"
						name="barcode" id="barcode" value="${book.bookBarcode}" /></td>
				</tr>
				<tr>
					<td width="200"><div align="left">图书名称：</div></td>
					<td colspan="3"><input width="200" type="text"
						name="book_name" id="book_name" value="${book.bookName}" /></td>
				</tr>
				<tr>
					<td><div align="right">图书详情：</div></td>
					<td colspan="3"><textarea name="book_intro" id="book_intro"
							cols="80" rows="4">${book.bookIntro}</textarea></td>
				</tr>
				<tr>
					<td><div align="right">图书分类：</div></td>
					<td colspan="3"><select style="width: 150px" id="book_type"
						name="book_type">
							<option value="0">请选择</option>
							<c:forEach items="${bookTypes}" var="bookType">
								<option value="${bookType.bookTypeId}">${bookType.bookTypeName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><div align="right">图书来源：</div></td>
					<td colspan="3"><select id="sourceType"
						onchange="sourceTypeChange()" style="width: 150px"
						id="book_source_type" name="book_source_type">
							<option value="0">请选择</option>
							<c:forEach items="${bookSourceTypes}" var="bookSourceType">
								<option value="${bookSourceType.sourceTypeId}">${bookSourceType.sourceTypeName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><div id="donatorId1" style="display: none" align="right">捐赠人ID：</div></td>
					<td colspan="3"><div id="donatorId2" style="display: none"
							align="left">
							<input width="180" id="conInputBox" type="text"
								onkeyup="findContributor(this)" onblur="findContributor(this)" />
							<select onchange="sourceIdChange()" style="width: 150px"
								id="contributor_id" name="contributor_id">
								<option value="0">请选择</option>
								<c:forEach items="${contributors}" var="contributor">
									<option value="${contributor.userId}">${contributor.userCode}:${contributor.userFullname}</option>
								</c:forEach>
							</select>
						</div></td>
				</tr>
				<tr>
					<td rowspan="3"><div align="right">上传图片：</div></td>
					<td>
						<div id="dpicture1" align="left">
							<div id="preview1" align="left">
								<c:if
									test='${!book.picture1.equals("") and book.picture1!=null}'>
									<img id="oldPicture1" width='200px' height='200px'
										src="/book_corner/pics/${book.picture1}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=1&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture1"
								id="picture1" onchange="checkPicType(this)" />
						</div>
					</td>
					<td><div id="dpicture2" align="left">
							<div id="preview2" align="left">
								<c:if
									test='${!book.picture2.equals("") and book.picture2!=null}'>
									<img id="oldPicture2" width='200px' height='200px'
										src="/book_corner/pics/${book.picture2}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=2&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture2"
								id="picture2" onchange="checkPicType(this)" />
						</div></td>
					<td><div id="dpicture3" align="left">
							<div id="preview3" align="left">
								<c:if
									test='${!book.picture3.equals("") and book.picture3!=null}'>
									<img id="oldPicture3" width='200px' height='200px'
										src="/book_corner/pics/${book.picture3}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=3&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture3"
								id="picture3" onchange="checkPicType(this)" />
						</div></td>
				</tr>
				<tr>
					<td><div id="dpicture4" align="left">
							<div id="preview4" align="left">
								<c:if
									test='${!book.picture4.equals("") and book.picture4!=null}'>
									<img id="oldPicture4" width='200px' height='200px'
										src="/book_corner/pics/${book.picture4}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=4&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture4"
								id="picture4" onchange="checkPicType(this)" />
						</div></td>
					<td><div id="dpicture5" align="left">
							<div id="preview5" align="left">
								<c:if
									test='${!book.picture5.equals("") and book.picture5!=null}'>
									<img id="oldPicture5" width='200px' height='200px'
										src="/book_corner/pics/${book.picture5}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=5&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture5"
								id="picture5" onchange="checkPicType(this)" />
						</div></td>
					<td><div id="dpicture6" align="left">
							<div id="preview6" align="left">
								<c:if
									test='${!book.picture6.equals("") and book.picture6!=null}'>
									<img id="oldPicture6" width='200px' height='200px'
										src="/book_corner/pics/${book.picture6}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=6&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture6"
								id="picture6" onchange="checkPicType(this)" />
						</div></td>
				</tr>
				<tr>
					<td><div id="dpicture7" align="left">
							<div id="preview7" align="left">
								<c:if
									test='${!book.picture7.equals("") and book.picture7!=null}'>
									<img id="oldPicture7" width='200px' height='200px'
										src="/book_corner/pics/${book.picture7}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=7&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture7"
								id="picture7" onchange="checkPicType(this)" />
						</div></td>
					<td><div id="dpicture8" align="left">
							<div id="preview8" align="left">
								<c:if
									test='${!book.picture8.equals("") and book.picture8!=null}'>
									<img id="oldPicture8" width='200px' height='200px'
										src="/book_corner/pics/${book.picture8}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=8&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture8"
								id="picture8" onchange="checkPicType(this)" />
						</div></td>
					<td><div id="dpicture9" align="left">
							<div id="preview9" align="left">
								<c:if
									test='${!book.picture9.equals("") and book.picture9!=null}'>
									<img id="oldPicture19" width='200px' height='200px'
										src="/book_corner/pics/${book.picture9}" />
								</c:if>
							</div>
							<div align="center">
								<a
									href="/book_corner/deleteBookPic?picId=9&book_id=${book.bookId}">删除</a>
							</div>
							<input style="width: 180px" type="file" name="picture9"
								id="picture9" onchange="checkPicType(this)" />
						</div></td>
				</tr>
				<tr>
					<td colspan="4"><div align="center">
							<input style="width: 180px" type="submit" name="button"
								id="button" class="btn btn-lg btn-primary btn-block" value="提交" />
						</div></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none">
		<select style="width: 150px" id="contributor_id1"
			name="contributor_id1">
			<c:forEach items="${contributors}" var="contributor">
				<option value="${contributor.userId}">${contributor.userCode}:${contributor.userFullname}</option>
			</c:forEach>
		</select>
	</div>
	<div id="preview" style="width: 200px; height: 200px"></div>
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