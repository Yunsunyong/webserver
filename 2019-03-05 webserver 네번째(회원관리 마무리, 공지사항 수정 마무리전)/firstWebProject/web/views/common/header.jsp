<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<style type="text/css">
header nav ul { list-style: none; }
header nav ul li { 
	float: left;
	margin-right: 5px;
	background: deepskyblue;
}
header nav ul li a {
	display: block;
	text-align: center;
	padding-top: 5px;
	padding-bottom: 0;
	text-decoration: none;
	width: 120px;
	height: 35px;
	color: white;
	font-weight: bold;
}
header nav ul li a:hover {
	background: fuchsia;
	text-shadow: 5px 5px 5px lightcyan;
	box-shadow: 4px 4px 4px ghostwhite;
}
</style>
</head>
<body>
<header>
<nav>
<ul>
	<li><a href="/first/index.jsp">Home</a></li>
	<li><a href="/first/nlist">공지사항</a></li>
	<li><a href="/first/blist?page=1">게시글</a></li>
	<li><a href="">메뉴</a></li>
	<li><a href="">메뉴</a></li>
</ul>
</nav>
</header>
</body>
</html>