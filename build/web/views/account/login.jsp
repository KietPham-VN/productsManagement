<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title> 
    </head>
    <body>
        <%
            String error = (String) request.getAttribute("error");
        %>
        <h1>Login Page</h1> 
        <%
            if (error != null)
            {
        %>
        <p><%=error%></p>
        <%
            }
        %>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="login">
            <label>Username: </label>
            <input type="text" name="username">
            <label>Password: </label>
            <input type="password" name="password">
            <input type="submit" value="login">
        </form>
    </body>
</html>
