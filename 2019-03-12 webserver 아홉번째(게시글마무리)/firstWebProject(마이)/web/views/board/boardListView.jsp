<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, board.model.vo.Board, member.model.vo.Member" %>    
<%
	Member loginUser = (Member)session.getAttribute("loginUser");
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	int listCount = ((Integer)request.getAttribute("listCount")).intValue();
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue();
	int startPage = ((Integer)request.getAttribute("startPage")).intValue();
	int endPage = ((Integer)request.getAttribute("endPage")).intValue();
	int maxPage = ((Integer)request.getAttribute("maxPage")).intValue();
	
	String search = request.getAttribute("search").toString();
	String keyword = null;
	java.sql.Date begin = null, end = null;
	)
	eyword = (String)request.getAttribute("keyword");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<script type="text/javascript" src="/first/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function showWriterForm(){
	location.href = "/first/views/board/boardWriteForm.jsp";
}

$(function(){
	showDiv();
	
	$("input[name=item]").on("change", function(){
		showDiv();
	});
});

function showDiv(){
	if($("input[name=item]").eq(0).is(":checked")){
		$("#titleDiv").css("display", "block");
		$("#writerDiv").css("display", "none");
		$("#dateDiv").css("display", "none");
	}else if($("input[name=item]").eq(1).is(":checked")){
		$("#titleDiv").css("display", "none");
		$("#writerDiv").css("display", "block");
		$("#dateDiv").css("display", "none");
	}else if($("input[name=item]").eq(2).is(":checked")){
		$("#titleDiv").css("display", "none");
		$("#writerDiv").css("display", "none");
		$("#dateDiv").css("display", "block");
	}
}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<hr style="clear:both">
<h1 align="center">게시글 목록</h1>
<h4 align="center">총 게시글 갯수 : <%=listCount %></h4>
<%if(loginUser != null){ %>
	<div style="align:center; text-align:center;">
	<button onclick="showWriterForm();">글쓰기</button>
	</div>
<%}%>
<%-- 검색기능 --%>
<center>
<div>
	<h2>검색할 항목을 선택하시오.</h2>
	<input type="radio" name="item" value="title" checked> 제목 
	&nbsp; &nbsp; &nbsp;
	<input type="radio" name="item" value="writer"> 작성자 
	&nbsp; &nbsp; &nbsp;
	<input type="radio" name="item" value="date"> 날짜 
	&nbsp; &nbsp; &nbsp;
</div>
<div id="titleDiv">
	<form action="/first/bsearcht" method="post">
	<label>검색할 제목을 입력하시오 :
	<input type="text" name="keyword"></label>
	<input type="submit" value="검색">
	</form>
</div>
<div id="writerDiv">
	<form action="/first/bsearchw" method="post">
	<label>검색할 작성자아아디를 입력하시오 :
	<input type="text" name="keyword"></label>
	<input type="submit" value="검색">
	</form>
</div>
<div id="dateDiv">
<form action="/first/bsearchd" method="post">
	<label>검색할 날짜를 선택하시오 :
	<input type="date" name="begin">~
	<input type="date" name="end"></label>
	<input type="submit" value="검색">
	</form>
</div>
</center>
<br>
<table align="center" border="5" cellspacing="0" >
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>날짜</th>
		<th>조회수</th>
		<th>첨부파일</th>
	</tr>
	<%for(Board b : list){ %>
		<tr>
			<td align="center"><%=b.getBoardNum() %></td>
			<td>
			<%-- 댓글일 때는 제목을 들여쓰기함 --%>
			<% if(b.getBoardReplyLev() == 1) { //원글의 댓글일 때 %>
				&nbsp; &nbsp; ☞
			<% }else if(b.getBoardReplyLev() == 2) { //댓글의 댓글일 때%>
				&nbsp; &nbsp; &nbsp; &nbsp; ☞☞
			<% }%>
			<%-- 로그인 상태일 때만 상세보기 링크 설정함 --%>
			<% if(loginUser != null){ %>
				<a href="/first/bdetail?bnum=<%=b.getBoardNum()%>&page=<%=currentPage%>"><%=b.getBoardTitle() %></a>
			<% }else{ %>
				<%=b.getBoardTitle() %>
			<% } %>
			</td>
			<td align="center"><%=b.getBoardWriter() %></td>
			<td align="center"><%=b.getBoardDate() %></td>
			<td align="center"><%=b.getBoardReadCount() %></td>
			<td align="center">
			<%if(b.getBoardOriginalFileName() != null){ %>
				㉻
			<%}else{ %>
				&nbsp;
			<%} %>
			</td>
		</tr>
	<% } %>
</table>
<br>
<%-- 페이징 처리 --%>
<div style="text-align:center;">
	<% if(currentPage <= 1){ %>
		[맨처음]&nbsp;
	<% }else { %>
		<a href="/first/blist?page=1">[맨처음]</a>&nbsp;
	<% } %>
	<% if((currentPage - 5) < startPage && (currentPage - 5) > 1){ %>
		<a href="/first/blist?page=<%=startPage - 5 %>">[prev]</a>
	<% }else { %>
		[prev]
	<% } %>
	<%-- 현재 페이지가 포함된 페이지 그룹 숫자 출력 처리 --%>
	<% for(int p = startPage; p <= endPage; p++){ 
		if(p == currentPage){%>
			<font color="#00ff00" size="6"><b>[<%= p %>]</b></font>
	<%}else{ 
		if(search.equals("title")){%>
		<a href="/first/bseracht?keyword=<%=keyword%>&search=<%=search%>"></a>
		<%}else if(search.equals("writer")){ %>
		<a href="/first/blist?page=<%= p %>"><%= p %></a>	
	<% }} %>
	<% if((currentPage + 5) > endPage && (currentPage + 5) < maxPage){ %>
		<a href="/first/blist?page=<%= endPage + 5 %>">[daum]</a>		
	<% }else { %>
		[daum]&nbsp;
	<% } %>
	<% if(currentPage >= maxPage){ %>
		[final]
	<% }else{ %>
		<a href="/first/blist?page=<%=maxPage%>">[final]</a>
	<%} %>
</div>
<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>