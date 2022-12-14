<%@ page import="com.yohanmarcus.webshop.user.domain.UserRole" %>
<%@ page import="com.yohanmarcus.webshop.user.dto.UserDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UserDto user = (UserDto) request.getAttribute("user");
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
        <% for (UserRole role : UserRole.values()) { %>
        <option value="<%= role.toString() %>" <%= user.getRole().equals(role) ? "selected" : "" %>><%= role.toString() %>
        </option>
        <% } %>
    </select>

    <button type="submit">Edit</button>
</form>

<jsp:include page="footer.jsp"/>
</body>
</html>
