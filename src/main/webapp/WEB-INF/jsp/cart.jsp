<%@ page import="java.util.List" %>
<%@ page import="com.yohanmarcus.webshop.item.domain.Cart" %>
<%@ page import="com.yohanmarcus.webshop.item.domain.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<%
    Cart cart = (Cart) request.getSession().getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        request.getSession().setAttribute("cart", cart);
    }
    List<Item> cartItems = cart.getCartItems();
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
                for (Item item : cartItems) {
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
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
