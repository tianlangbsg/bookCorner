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
  <script src="static/bootstrap/bootstrap.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	var message=$("#message").val();
    	//alert(message);
    	if(message!=""){
    		 $(".modal-body").text(message);
    		// $(".modal-body").html("message");
    		 $("#myModal").modal('show');
    		// $("#myModal").modal('hide');
    		 setTimeout(function(){$("#myModal").modal("hide")},3000);
    	}
    	});
    
    </script>
  <body>

    <div class="container">
      <form class="form-signin" method="post" action="/web-test/login">

        <label for="inputEmail" class="sr-only">员工号</label> <span id=error  style="color:#F00"></span>
        <input type="text" id="inputAccountName" name="code" class="form-control"  required  placeholder="员工号"  value="${user.user_code}" >
        <label for="inputPassword" class="sr-only">用户名</label>
        <input type="password" id="inputPassword" class="form-control" name="password"  required  placeholder="密码"  value="${user.user_fullname}">
              <input type="hidden" id="message" name="message" value="${message}">
        <button class="btn btn-lg btn-primary btn-block" type="submit">登 录</button>
      
      </form>
    </div> 
<!-- 对话框开始 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel" style="text-align:center">提示</h4>
      </div>
      <div class="modal-body">
      </div>
    </div>
  </div>
</div> 
<!-- 对话框结束 --> 
</body></html>