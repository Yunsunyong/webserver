<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<h1>firstWebProject</h1>
<form action="/first/login" method="post">
아이디 : <input type="text" name="userid" required placeholder="ID"><br>
암   호 : <input type="password" name="userpwd" required placeholder="Password"><br>
<input type="submit" value="로그인">
</form>
</body>
</html>