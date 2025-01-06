<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title> 
    </head>
    <body>
        <h1>Login Page</h1>

        <c:if test="${not empty error}">
            <p>${error}</p>
        </c:if>

        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="login">
            <label>Username: </label>
            <input type="text" name="username">
            <label>Password: </label>
            <input type="password" name="password">
            <input type="submit" value="Login">
        </form>
    </body>
</html>
