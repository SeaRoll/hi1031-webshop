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
