<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.order.dto.OrderWithItems" %>
<%@ page import="com.yohanmarcus.webshop.order.dto.OrderItemsDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="error.jsp"/>
<%
    List<OrderWithItems> orders = (List<OrderWithItems>) request.getAttribute("orders");
%>

<main>
    <h2>Staff - Orders</h2>
    <% for (OrderWithItems order : orders) { %>
    <hr>
    <h3>
        Order <%= order.getOrder().getId() %>
    </h3>
    <p>
        User Id: <%= order.getOrder().getUserId() %>
    </p>
    <p>
        Status: <%= order.getOrder().getStatus() %>
    </p>
    <table style="width: 100%;">
        <thead style="text-align: left;">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>amount</th>
            <th>Description</th>
            <th>Category</th>
        </tr>
        </thead>
        <tbody>
        <% for (OrderItemsDto item : order.getItems()) { %>
        <tr>
            <td>
                <%= item.getName() %>
            </td>
            <td>
                $<%= item.getPrice() %>
            </td>
            <td>
                <%= item.getQuantity() %>
            </td>
            <td>
                <%= item.getDescription() %>
            </td>
            <td>
                <%= item.getCategory() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <p>
        Total: $<%= order.getTotal() %>
    </p>
    <a href="/staff/order/edit?id=<%= order.getOrder().getId() %>">Edit</a>
    <% } %>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
