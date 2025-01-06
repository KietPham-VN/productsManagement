<%@page import="entities.Product"%>
<%@page import="java.util.List"%>
<%@page import="entities.Category"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
    </head>
    <body>
        <h1>Update Product</h1>

        <c:if test="${not empty msg}">
            <p style="color: red;">${msg}</p>
        </c:if>

        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="productId" value="${product.id}">

            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" value="${product.name}" required><br><br>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" value="${product.price}" step="0.01" required><br><br>

            <label for="productYear">Product Year:</label>
            <input type="number" id="productYear" name="productYear" value="${product.productYear}" required><br><br>

            <label for="image">Image URL:</label>
            <input type="text" id="image" name="image" value="${product.image}"><br><br>

            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}" 
                            <c:if test="${category.id == product.category.id}">selected</c:if>>
                        ${category.name}
                    </option>
                </c:forEach>
            </select><br><br>

            <button type="submit">Update Product</button>
        </form>
    </body>
</html>
