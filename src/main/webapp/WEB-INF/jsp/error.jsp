<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String errorMessage = (String) request.getAttribute("error");
    if (errorMessage != null) {
%>
<br>
<h5>
    Error: <%= errorMessage %>
</h5>
<br>
<%
    }
%>

</body>
</html>
