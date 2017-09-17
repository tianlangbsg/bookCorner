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
<script src="static/jquery/jquery-1.3.2.js"></script>
<script src="static/bootstrap/bootstrap.js"></script>
</head>

<body>
	<div align="center">
		<table width="500" border="1">
			<tr>
				<td colspan="3"><h1 align="center">图书详情</h1></td>
			</tr>
			<tr>
				<td width="95"><div align="right">书名：</div></td>
				<td width="185">&nbsp;</td>
				<td width="198" rowspan="7"><img src="${book.pic_path} " /></td>
			</tr>
			<tr>
				<td><div align="right">来源类型：</div></td>
				<td>${book.pic_path}</td>
			</tr>
			<tr>
				<td><div align="right">录入时间：</div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><div align="right">借阅历史：</div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><div align="right">收藏：</div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><div align="right">点赞：</div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><div align="right">评论：</div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><div align="right">状态：</div></td>
				<td>&nbsp;</td>
				<td><form id="form1" name="form1" method="post" action="">
						<div align="right">
							<input type="submit" name="button" id="button" value="更多图片>>" />
						</div>
					</form></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<br />
	<div align="center">
		<table width="500" border="1">
			<tr>
				<td><div align="center">借阅</div></td>
				<td><div align="center">收藏</div></td>
				<td><div align="center">点赞</div></td>
				<td><div align="center">评论</div></td>
			</tr>
			<tr>
				<td><div align="center">简介</div></td>
				<td colspan="3">&nbsp;</td>
			</tr>
		</table>
	</div>
	<p>&nbsp;</p>
</body>
</html>