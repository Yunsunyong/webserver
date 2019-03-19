<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="member.model.vo.Member"%>
<%
	Member loginUser = (Member)session.getAttribute("loginUser");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<script type="text/javascript" src="/first/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//주기적으로 반복 실행되게 하려면	
			setInterval(function(){
				var date = new Date();
				var hour = date.getHours();
				var minute = date.getMinutes();
				var second = date.getSeconds();
				$("#time_div").html(hour + " : " + minute + " : " + second);
			}, 10);
			//시간은 1000이 1초임
		//게시글 조회수 많은 순으로 5개 조회 결과 출력 처리
		$.ajax({
			url: "/first/btop5",
			type: "get",
			dataType: "json",
			success: function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				
				var values = "";
				for(var i in json.list){
					<% if(loginUser != null){%>
					values += "<tr><td>" + json.list[i].bnum
					+"</td><td><a href='/first/bdetail?bnum="+ json.list[i].bnum + "&page=1'>" 
					+ decodeURIComponent(json.list[i].btitle).replace(/\+/gi," ")
					+"</a></td><td>" + json.list[i].rcount
					+"</td></tr>";
					<% }else{ %>
						values += "<tr><td>" + json.list[i].bnum
								+"</td><td>" + decodeURIComponent(json.list[i].btitle).replace(/\+/gi," ")
								+"</td><td>" + json.list[i].rcount
								+"</td></tr>";
					<% } %>
				}//for in
				
				//테이블에 추가
				$("#toplist").html($("#toplist").html() + values);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("error : " + jqXHR + ", " + textStatus + ", " + errorThrown);
			}
		});
		
		//최근 등록된 공지글 5개 조회 출력 처리
		$.ajax({
			url: "/first/ntop5",
			type: "get",
			dataType: "json",
			success: function(data){
				console.log("success : " + data);
				
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				
				var values ="";
				for(var i in json.list){
					<% if(loginUser != null){ %>
					values += "<tr><td>" + json.list[i].no +
					"</td><td><a href='/first/ndetail?no=" + json.list[i].no +
						"'>" + decodeURIComponent(json.list[i].ntitle).replace(/\+/gi," ") +
					"</a></td><td>" + json.list[i].ndate + "</td></tr>";
					<% }else { %>
						values += "<tr><td>" + json.list[i].no +
						"</td><td>" + decodeURIComponent(json.list[i].ntitle).replace(/\+/gi," ") +
						"</td><td>" + json.list[i].ndate + "</td></tr>";
					<% } %>
				}	//for in
				$("#newnotice").html($("#newnotice").html() + values);					
				
			},error: function(jqXHR, textStatus, errorThrown){
				alert("error : " + jqXHR + ", " + textStatus + ", " + errorThrown);
			}
			
		});
		
	});
	function clickpopup(){	
	 	open("/first/views/popup/popup1.html", "팝업", "width=50, height=50")
	 	,open("/first/views/popup/popup2.html", "팝업1", "width=50, height=50");
	 	
	}
</script>
<style type="text/css">
	.form_size {
		border: 1px solid black;
		width: 600px;
		height: 400px;
	}
	.d1 {
		float: left;
	}
	.d2 {
		float: left;
		margin-left: 20px;
	}
</style>
<script type="text/javascript">
	/* function popup(){
		var url = "views/file/upload.jsp";
		var name = "popup";
		open(url, name, "width=500, height=500, toolbar=no, status=no, loaction=no, scrollbars=yes, memubar=no, resizable=yes, left=100, top=100");
	} */
</script>
</head>
<body>
<h1>firstWebProject</h1>
<% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
	<%@ include file="views/common/adminHeader.jsp" %>
<%}else { %>
	<%@ include file="views/common/header.jsp" %>
<%} %>
<hr style="clear:left;">
<% if(loginUser == null){ 	//로그인 하지 않은 상태일 때	%>
<div>
   <form action="/first/login" method="post">
      <table>
         <tr>
            <th>아이디 :</th>
            <td><input type="text" name="userid" required="required"></td>
         </tr>

         <tr>
            <th>비밀번호:</th>
            <td><input type="password" name="userpwd" required="required"></td>
         </tr>
         <tr>
            <th colspan="2"><input type="submit" value="전송">&nbsp;&nbsp;
               <a href="/first/views/member/enroll.html">회원가입</a></th>
         </tr>
         <tr>
            <th colspan="2"><a href="">아이디찾기</a>&nbsp;&nbsp; <a href="">암호찾기</a>
            </th>
         </tr>
         
      </table>
      </form>
      </div>   
<% }else {	//로그인 상태 일때  %>
<div style="border:1px solid red;width:200px;float:left;">
<table>
<tr>
<th><%= loginUser.getUserName() %>님</th> 
<td><a href="/first/logout">로그아웃</a></td>
</tr>		
<tr>
	<th colspan="2">
		<a href="">쪽지</a>
		&nbsp; &nbsp;
		<a href="/first/myinfo?userid=<%= loginUser.getUserId()%>">내 정보 보기</a>
	</th>
</tr>
</table>		
</div>
<%} %>
<div id="time_div" style="border:1px solid red;width:200px;height:30px;float:left;">

</div>
<section>
<div style="border:1px solid red;padding:5px;margin:5px;clear:left;">
	<h4>인기 게시글</h4>
	<table id="toplist" border="1" cellspacing="0">
		<tr>
			<th>번호</th>
			<th width="200">제목</th>
			<th>조회수</th>
		</tr>
	</table>
</div>
<div style="float:left;border:1px solid red;padding:5px;margin:5px;">
	<h4>신규 공지글</h4>
	<table id="newnotice" border="1" cellspacing="0">
		<tr>
			<th>번호</th>
			<th width="200">제목</th>
			<th>날짜</th>
		</tr>
	</table>
</div>
</section>
<br style="clear:left;"><br><br>
<br><br><br><br><br><br>
<br><br><br><br><br><br>
<button onclick="clickpopup();">팝업창띄우기</button>
<hr style="clear:left;">
<%@ include file="views/common/footer.jsp" %>
<!-- <a href="views/file/upload.jsp">업로드창</a> -->
</body>
</html>