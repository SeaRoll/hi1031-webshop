<%@ page import="com.yohanmarcus.webshop.user.domain.UserRole" %>
<%@ page import="com.yohanmarcus.webshop.item.dto.CartDto" %>
<%@ page import="com.yohanmarcus.webshop.user.dto.UserDto" %>
<h1>90s Ecommerce</h1>
<img src="/resources/ia-cat.webp" alt="cat-logo">
<%
    CartDto cart = (CartDto) request.getSession().getAttribute("cart");
    UserDto user = (UserDto) request.getSession().getAttribute("user");
%>
<ul>
    <li><a href="/">Home</a></li>
    <li>
        <a href="/cart">
            Cart (<%= cart.getItems().size() %>)
        </a>
    </li>
    <% if (user != null) { %>
    <li>
        <a href="/logout">Hello, <%= user.getUsername() %>! Click here to log out</a>
    <li>_______________________________</li>
    <li><a href="/order">Orders</a></li>
    </li>
    <%} else { %>
    <li>
        <a href="/login">Login</a>
    </li>
    <%}%>

    <% if (user != null && !user.getRole().equals(UserRole.USER)) { %>
    <li>_______________________________</li>
    <li><a href="/staff/order">Staff - Orders</a></li>
    <%}%>

    <% if (user != null && user.getRole().toString().equals("ADMIN")) { %>
    <li>_______________________________</li>
    <li><a href="/admin/users">Admin - Users</a></li>
    <li><a href="/admin/item">Admin - Items</a></li>
    <%}%>
</ul>
<hr>