<%@ page import="com.yohanmarcus.webshop.item.dto.ItemDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ItemDto item = (ItemDto) request.getAttribute("item");
    boolean editing = true;
    if (item == null) {
        editing = false;
        item = ItemDto.from(null, "", 0, 0, "", "");
    }
%>
<html>
<head>
    <title>
        Admin - Items <%= editing ? "Edit" : "New" %>
    </title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h2>
    <%= editing ? "Edit" : "New" %> Item
</h2>
<form action="/admin/item/change" method="POST">
    <input type="hidden" name="editing" value="<%= editing %>">
    <input type="hidden" name="id" value="<%= item.getId() %>">
    <label for="name">Name</label>
    <input type="text" id="name" name="name" value="<%= item.getName() %>">
    <label for="price">Price</label>
    <input type="number" id="price" name="price" value="<%= item.getPrice() %>">
    <label for="quantity">Quantity</label>
    <input type="number" id="quantity" name="quantity" value="<%= item.getQuantity() %>">
    <label for="description">Description</label>
    <input type="text" id="description" name="description" value="<%= item.getDescription() %>">
    <label for="category">Category</label>
    <input type="text" id="category" name="category" value="<%= item.getCategory() %>">
    <button type="submit"><%= editing ? "Edit" : "Create" %>
    </button>
</form>

<jsp:include page="footer.jsp"/>
</body>
</html>
