<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//스크립트릿(scriptlet) 태그 영역
	//자바 코드 작성 영역임
	String message = (String)request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<h1>회원서비스 오류</h1>
메세지 :<%= message %> 		<br>
<br>
<a href="/first/index.jsp">시작페이지로 이동</a>
</body>
</html>
