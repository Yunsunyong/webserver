<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업로드</title>
</head>
<body>
<form action="/first/upload" method="post" enctype="multipart/form-data">
	<input type="file" name="uploadfile" />
	<input type="submit" value="업로드" />
</form>
</body>
</html>