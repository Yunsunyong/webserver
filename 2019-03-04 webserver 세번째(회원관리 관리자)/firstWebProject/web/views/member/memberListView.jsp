<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="member.model.vo.Member, java.util.ArrayList"%>
<%
	Member loginUser = (Member)session.getAttribute("loginUser");
	ArrayList<Member> list = (ArrayList<Member>)request.getAttribute("list");
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
	<%@ include file="../common/adminHeader.jsp" %>
<%}else { %>
	<%@ include file="../common/header.jsp" %>
<%} %>
<hr style="clear:left;">
<h2 align="center">회원 전체 관리 페이지</h2>

</body>
<hr>
<%@ include file="../common/footer.jsp" %>
</html>