<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<%
    List<User> userList = (List<User>) request.getAttribute("users");
%>

<main>
    <h2>Users</h2>
    <table style="width: 100%;">
        <tbody>

        <%
            for (User user : userList) {
        %>
        <form action="/admin/users" method="post">

            <label for="id">ID:</label>
            <input type="text" id="id" disabled value="<%= user.getId() %>">
            <label for="username">Username:</label>
            <input type="text" id="username" value="<%= user.getUsername() %>">

            <label for="role">Role:</label>
            <select name="role" id="role">
                <option value="<%= user.getRole() %>" disabled selected><%= user.getRole() %>
                </option>
                <option value="ADMIN">ADMIN</option>
                <option value="STAFF">STAFF</option>
                <option value="USER">USER</option>
            </select>

            <input type="submit" value="Register changes">
            <br><br>
        </form>

        <%
            }
        %>
        </tbody>
    </table>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>