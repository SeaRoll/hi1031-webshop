<%@ page import="com.yohanmarcus.webshop.order.domain.OrderStatus" %>
<%@ page import="com.yohanmarcus.webshop.order.dto.OrderDto" %><%--
  Created by IntelliJ IDEA.
  User: searoll
  Date: 2022-10-03
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    OrderDto order = (OrderDto) request.getAttribute("order");
%>
<html>
<head>
    <title>Change order <%= order.getId() %>
    </title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="error.jsp"/>

<main>
    <h2>
        Change order <%= order.getId() %>
    </h2>
    <form method="post" action="/staff/order/edit">
        <input type="hidden" name="id" value="<%= order.getId() %>">
        <label for="status">Choose a new status:</label>
        <select id="status" name="status">
            <% for (OrderStatus status : OrderStatus.values()) { %>
            <option value="<%= status.name() %>">
                <%= status.name() %>
            </option>
            <% } %>
        </select>
        <button type="submit" name="Submit">Edit</button>
    </form>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
