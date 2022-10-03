<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.item.domain.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<%
    List<Item> itemList = (List<Item>) request.getAttribute("items");
%>

<main>
    <form action="/" method="POST">
        <h2>Products</h2>
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
                    <%
                        if (item.getQuantity() > 0) {
                    %>
                    <button type="submit" name="itemId" value="<%= item.getId() %>">Add to cart</button>
                    <%
                    } else {
                    %>
                    <p>out of stock</p>
                    <%
                        }
                    %>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </form>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
