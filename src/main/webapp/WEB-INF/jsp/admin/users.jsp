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
    <form action="/admin/users" method="POST">
        <h2>Users</h2>
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
                    $<%= user.getUsername() %>
                </td>
                <td>
                    <%= user.getRole() %>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </form>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>