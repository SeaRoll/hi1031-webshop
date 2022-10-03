<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.item.domain.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Items</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<%
    List<Item> itemList = (List<Item>) request.getAttribute("items");
%>

<main>
    <h2>Admin-Items</h2>
    <table style="width: 100%;">
        <thead style="text-align: left;">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Description</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Item item : itemList) {
        %>
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
            <td>
                <a href="/admin/item/change?id=<%= item.getId() %>">Edit</a>
                <a href="/admin/item/delete?id=<%= item.getId() %>">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</main>


<jsp:include page="footer.jsp"/>
</body>
</html>
