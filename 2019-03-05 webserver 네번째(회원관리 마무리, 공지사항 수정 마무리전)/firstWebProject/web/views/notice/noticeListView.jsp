<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="notice.model.vo.Notice, java.util.HashMap, member.model.vo.Member, notice.model.vo.NoticeNoDescending, java.util.List" %>
<%
	HashMap<Integer, Notice> noticeMap = (HashMap<Integer, Notice>)request.getAttribute("noticeMap");
	Member loginUser = (Member)session.getAttribute("loginUser");
	//Collection<Notice> list = noticeMap.values();
	ArrayList<Notice> list = new ArrayList<Notice>(noticeMap.values()); 
	Collections.sort(list, new NoticeNoDescending());
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<hr style="clear:both;">
<h2 align="center">공지사항 목록 보기</h2>
<table border="1">
<tr>
   <th>번호</th>
   <th>제목</th>
   <th>작성자</th>
   <th>날짜</th>
   <th>첨부파일</th>
</tr>
<% for(Notice notice : list){ %>
<tr>
	<td align="center"><%=notice.getNoticeNo()%></td>
	<td>
	<% if(loginUser != null){//로그인한 상태 %>
		<a href="/first/ndetail?no=<%= notice.getNoticeNo()%>"><%=notice.getNoticeTitle()%></a>
	<% }else {%>
		<%=notice.getNoticeTitle() %>
	<% } %>
	</td>
	<td align="center"><%=notice.getNoticeWriter() %></td>
	<td align="center"><%=notice.getNoticeDate()%></td>
	<td align="center">
		<% if(notice.getOriginalFliePath() != null){ //첨부파일이 있으면%>
			◈
		<% }else{ //첨부파일이 없다면 %>
			&nbsp;
		<% }%> 
	</td>
</tr>
<% } %>
</table>
<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>