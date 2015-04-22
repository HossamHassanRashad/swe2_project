<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/social/likePage" method="post">
<p> page owner </p>  <input type="text" name="owner" value="${it.owner}"><br>
<p> page name </p>  <input type="text" name="name" value="${it.name}"><br>
<p> page category </p>  <input type="text" name="category" value="${it.category}"><br>
<p> page type </p>   <input type="text" name="type" value="${it.type}"><br>
<p> page likes </p>  <input type="text" name="likes" value="${it.likes}"><br>
					 <input type="hidden" name="myemail" value="${it.myemail}"><br>
					 <input type="submit" name="like" value="like">
</form>
</body>
</html>