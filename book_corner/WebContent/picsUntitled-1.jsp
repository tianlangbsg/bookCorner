<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>

<body>
<div align="center">
  <table width="400" border="1">
    <tr>
      <td width="200"><div align="right">条形码：</div></td>
      <td width="200"><label for="barcode"></label>
      <input width="200" type="text" name="barcode" id="barcode" /></td>
    </tr>
    <tr>
      <td><div align="right">图书名称：</div></td>
      <td><input width="200" type="text" name="barcode2" id="barcode2" /></td>
    </tr>
    <tr>
      <td><div align="right">图书详情：</div></td>
      <td><label for="book_intro"></label>
      <textarea name="book_intro" id="book_intro" cols="36" rows="4"></textarea></td>
    </tr>
    <tr>
      <td><div align="right">图书分类：</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><div align="right">图书来源：</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><div align="right">捐赠人ID：</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><div align="right">上传图片：</div></td>
      <td><label style="width:10px" for="fileField"></label>
      <input style="width:180px" type="file" name="fileField" id="fileField" /></td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">
        <input type="submit" name="button" id="button" value="提交" />
      </div></td>
    </tr>
  </table>
  <p>
    <label for="select"></label>
    <select style="width:150px" name="select" id="select">
    </select>
  </p>
</div>
</body>
</html>