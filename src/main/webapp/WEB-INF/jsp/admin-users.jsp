<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Users</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<%
    List<User> userList = (List<User>) request.getAttribute("users");
%>

<main>
    <h2>Admin - Users</h2>
    <table style="width: 100%;">
        <thead style="text-align: left;">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (User user : userList) {
        %>
        <tr>
            <td>
                <%= user.getId() %>
            </td>
            <td>
                <%= user.getUsername() %>
            </td>
            <td>
                <%= user.getRole() %>
            </td>

            <td>
                <a href="/admin/users/change?id=<%= user.getId() %>">Edit</a>
                <a href="/admin/users/delete?id=<%= user.getId() %>">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <br><br>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>