<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.item.dto.CartDto" %>
<%@ page import="com.yohanmarcus.webshop.item.dto.ItemDto" %>
<%@ page import="com.yohanmarcus.webshop.user.dto.UserDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="error.jsp"/>
<%
    CartDto cart = (CartDto) request.getSession().getAttribute("cart");
    UserDto user = (UserDto) request.getSession().getAttribute("user");
    List<ItemDto> cartItems = cart.getItems();
%>

<main>
    <form action="/cart" method="POST">
        <h2>Products</h2>
        <table style="width: 100%;">
            <thead style="text-align: left;">
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>amount</th>
                <th>Description</th>
                <th>Category</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (ItemDto item : cartItems) {
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
                    <button type="submit" name="itemId" value="<%= item.getId() %>">Remove from cart</button>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </form>

    <h4>
        Total: $<%= cart.getTotal() %>
    </h4>

    <%
        if (user != null) {
    %>
    <form method="POST" action="/order">
        <button type="submit">Order</button>
    </form>
    <%
    } else {
    %>
    <p>Login to order items in cart!</p>
    <%
        }
    %>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
