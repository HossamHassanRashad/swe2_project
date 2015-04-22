<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/social/createPage" method="post">
${it.myemail}
<input name ="myemail" type="hidden" value ="${it.myemail}" >
<p> page name </p> <input name ="name" type="text">
<p> page type </p> <input name ="type" type="text">
<p> page category </p> <input name ="category" type="text">
<input name = "create" type = "submit">
</form>
</body>
</html>