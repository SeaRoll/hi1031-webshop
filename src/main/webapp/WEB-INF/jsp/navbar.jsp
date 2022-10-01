<%@ page import="com.yohanmarcus.webshop.item.domain.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.yohanmarcus.webshop.user.dto.UserDto" %>
<%@ page import="com.yohanmarcus.webshop.item.dto.ItemDto" %>
<h1>90s Ecommerce</h1>
<img src="/resources/ia-cat.webp" alt="cat-logo">
<%
    List<ItemDto> cartItems = (List<ItemDto>) request.getSession().getAttribute("cart");
    UserDto user = (UserDto) request.getSession().getAttribute("user");
    if (cartItems == null) {
        cartItems = new ArrayList<>();
        request.getSession().setAttribute("cart", cartItems);
    }
%>
<ul>
    <li><a href="/">Home</a></li>
    <li>
        <a href="/cart">
            Cart (<%= cartItems.size() %>)
        </a>
    </li>
    <% if (user != null) { %>
    <li>
        <a href="/logout">Hello, <%= user.username() %>! Click here to log out</a>
    <li>_______________________________</li>
    <li><a href="/orders">Orders</a></li>
    </li>
    <%} else { %>
    <li>
        <a href="/login">Login</a>
    </li>
    <%}%>

    <% if (user != null && user.role().equals("ADMIN")) { %>
    <li><a href="#">User - Orders</a></li>
    <li>_______________________________</li>
    <li><a href="#">Admin - Items</a></li>
    <li><a href="#">Admin - Orders</a></li>
    <li><a href="#">Admin - Users</a></li>
    <%}%>
</ul>
<hr>