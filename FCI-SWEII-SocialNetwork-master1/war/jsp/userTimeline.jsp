<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c"
					uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<c:forEach items ="${it.emaillist}" var ="Post">
<form action = "/social/createPost" method = "Post">
<input type="text" name="content">
<input type="text" name="feel">
<input type="text" name="privacy">
<input type="submit" value="post">
<input type="hidden" name="email" value="${Post.to}">
<input type="hidden" name="myemail" value="${Post.owner}">
</form>
</c:forEach>



<c:forEach items ="${it.postlist}" var ="Post">
<form action="/social/likePost" method="post">
<c:out value="${Post.owner}"></c:out> >> <br>
<c:out value="${Post.content}"></c:out>  <br>
feeling : <c:out value="${Post.feel}"></c:out><br>
likes : <c:out value="${Post.likes}"></c:out>
<input type="hidden" name = "postID" value = "<c:out value="${Post.postID}"></c:out>">
<input type = "submit" value ="like">
</form>
<br><br>
______________________________________________________________________________
</c:forEach>


</body>
</html>