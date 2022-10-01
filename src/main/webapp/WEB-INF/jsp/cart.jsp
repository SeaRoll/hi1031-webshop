<%@ page import="com.yohanmarcus.webshop.item.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<%
    List<ItemDto> cartItems = (List<ItemDto>) request.getSession().getAttribute("cart");
    if (cartItems == null) {
        cartItems = new ArrayList<>();
        request.getSession().setAttribute("cart", cartItems);
    }
%>

<main>
    <form action="/cart" method="POST">
        <h2>Products</h2>
        <table style="width: 100%;">
            <thead style="text-align: left;">
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Category</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (ItemDto itemDto : cartItems) {
            %>
            <tr>
                <td>
                    <%= itemDto.name() %>
                </td>
                <td>
                    $<%= itemDto.price() %>
                </td>
                <td>
                    <%= itemDto.description() %>
                </td>
                <td>
                    <%= itemDto.category() %>
                </td>
                <td>
                    <button type="submit" name="itemId" value="<%= itemDto.id() %>">Remove from cart</button>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </form>

    <h4>
        Total: $<%= cartItems.stream().mapToInt(ItemDto::price).sum() %>
    </h4>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
