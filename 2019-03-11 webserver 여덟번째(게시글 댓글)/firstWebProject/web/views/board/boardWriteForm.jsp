<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="boardError.jsp"%>
<%@ page import="member.model.vo.Member" %>    
<%
	Member loginUser = (Member)session.getAttribute("loginUser");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<hr style="clear:both">
<h1 align="center">게시글 글쓰기</h1>
<form action="/first/binsert" method="post" enctype="multipart/form-data">
	<table align="center">
		<tr>
			<td>제목</td>
			<td><input type="text" name="btitle"></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="bwriter" value="<%=loginUser.getUserId() %>" readonly></td>
		</tr>
		<tr>
			<td>첨부파일</td>
			<td><input type="file" name="bupfile"></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea cols="100" row="10" name="bcontent"></textarea></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<input type="submit" value="등록하기"> &nbsp;			
				<input type="reset" value="입력취소"> &nbsp;
				<a href="/first/blist?page=1">[목록]</a>			
			</td>
		</tr>
	</table>
</form>






<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>