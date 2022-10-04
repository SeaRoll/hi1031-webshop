<%@ page import="com.yohanmarcus.webshop.user.domain.User" %>
<%@ page import="com.yohanmarcus.webshop.user.domain.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) request.getAttribute("user");
%>

<html>
<head>
    <title>Admin - Edit User</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h2>
    Edit User
</h2>
<form action="/admin/users/change" method="POST">

    <input type="hidden" id="id" name="id" value="<%= user.getId() %>">

    <label for="username">Username</label>
    <input type="text" id="username" name="username" value="<%= user.getUsername() %>">

    <label for="role">Role:</label>
    <select name="role" id="role">
        <option value="ADMIN" <%= user.getRole().equals(UserRole.ADMIN) ? "selected" : "" %> >ADMIN</option>
        <option value="STAFF" <%= user.getRole().equals(UserRole.STAFF) ? "selected" : "" %>>STAFF</option>
        <option value="USER" <%= user.getRole().equals(UserRole.USER) ? "selected" : "" %>>USER</option>
    </select>

    <button type="submit">Edit</button>
</form>

<jsp:include page="footer.jsp"/>
</body>
</html>
