<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<style type="text/css">
	div {
		border: 1px dotted black;
		width: 300px;
		height: 100px;
	}
</style>
</head>
<body>
<div align="center">
	<h3><%= (String)request.getAttribute("message") %></h3>
	<a href="javascript:history.go(-1);">이전 페이지로 이동</a>
</div>
</body>
</html>