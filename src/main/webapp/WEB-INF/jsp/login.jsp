<%--
  Created by IntelliJ IDEA.
  User: searoll
  Date: 2022-10-01
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<h2>Log In</h2>
<jsp:include page="error.jsp"/>
<form action="/login" method="POST">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username" value="" placeholder="User"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" value="" placeholder="********"><br><br>
    <input type="submit" value="Login">
</form>
<a href="/register">First time? Register here</a>

<jsp:include page="footer.jsp"/>
</body>
</html>
