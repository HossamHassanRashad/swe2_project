<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/social/sendRequest" method="POST">
<p>${it.name}</p>
<input type="text" name="email" value="${it.email}"><br>
<input type="hidden" name="myemail" value="${it.myemail}">
<input type="submit" value="Add">
</form>

<form action="/social/showNewMsg" method="POST">
<input type="hidden" name="myemail" value="${it.myemail}">
<input type="hidden" name="email" value="${it.email}">
<input type="submit" value="showMesseges">
</form>

<form action="/social/viewSearchTimeline" method="POST">
<input type="hidden" name="myemail" value="${it.myemail}">
<input type="hidden" name="email" value="${it.email}">
<input type="submit" value="view ${it.email}'s timeline">
</form>
</body>
</html>