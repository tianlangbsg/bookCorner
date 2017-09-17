<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  	 <meta charset="utf-8">
    <base href="/web-test/">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>web</title>
    
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/mystyle.css">
  </head>
 <script type="text/javascript" src="static/jquery/jquery-1.3.2.js"></script>
 
  <body>
  <h1 align="center">系统管理员登录</h1>
    <div class="container">
      <form class="form-signin" method="get" action="/book_corner/right_manage">
        <label for="inputName" class="sr-only">系统管理员名</label> <span id=error  style="color:#F00"></span>
        <input type="text" id="inputManageName" name="manager_name" class="form-control"  required  placeholder="请输入用户名" >
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="text" id="inputManagePassword" name="password" class="form-control"  required  placeholder="请输入密码" >
        <button class="btn btn-lg btn-primary btn-block" type="submit">登 录</button>
      </form>
    </div> 

</body></html>