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
<div align="center">
<%if(exception != null){ %>
<h3>JSP 오류 발생 : <%=exception.getMessage() %></h3>
<h3>JSP 오류 종류 : <%=exception.getClass().getName() %></h3>
<%}else{ %>
<h3>서블릿 오류 발생 : <%= (String)request.getAttribute("message") %></h3>
<%} %>
	<a href="javascript:history.go(-1);">이전 페이지로 이동</a>
</div>
</body>
</html>