<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<style type="text/css">
	div {
		border: 1px dotted black;
		width: 400px;
		height: 200px;
	}
</style>
</head>
<body>
<h1>게시글 서비스 오류 발생</h1>
<div align="center">
<% if(exception != null){ %>
<h3>JSP 예외 발생 : <%=exception.getMessage() %></h3>
<%}else { %>
<h3>서블릿 오류 발생 : <%=request.getAttribute("message") %></h3>
<%} %>
</div>
</body>
</html>