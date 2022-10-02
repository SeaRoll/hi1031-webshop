<%@ page import="com.yohanmarcus.webshop.item.domain.Cart" %>
<%@ page import="com.yohanmarcus.webshop.user.domain.User" %>
<h1>90s Ecommerce</h1>
<img src="/resources/ia-cat.webp" alt="cat-logo">
<%
    Cart cart = (Cart) request.getSession().getAttribute("cart");
    User user = (User) request.getSession().getAttribute("user");
%>
<ul>
    <li><a href="/">Home</a></li>
    <li>
        <a href="/cart">
            Cart (<%= cart.getCartItems().size() %>)
        </a>
    </li>
    <% if (user != null) { %>
    <li>
        <a href="/logout">Hello, <%= user.getUsername() %>! Click here to log out</a>
    <li>_______________________________</li>
    <li><a href="/orders">Orders</a></li>
    </li>
    <%} else { %>
    <li>
        <a href="/login">Login</a>
    </li>
    <%}%>

    <% if (user != null && user.getRole().toString().equals("ADMIN")) { %>
    <li>_______________________________</li>
    <li><a href="#">Admin - Items</a></li>
    <li><a href="#">Admin - Orders</a></li>
    <li><a href="#">Admin - Users</a></li>
    <%}%>
</ul>
<hr>