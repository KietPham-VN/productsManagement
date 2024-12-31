<%@page import="entities.Product"%>
<%@page import="java.util.List"%>
<%@page import="entities.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
    </head>
    <body>
        <h1>Update Product</h1>
        <%
            String error = (String) request.getAttribute("msg");
            Product product = (Product) request.getAttribute("product");
        %>
        <% if (error != null)
            {%>
        <p style="color: red;"><%= error%></p>
        <% }%>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="update">

            <input type="hidden" name="productId" value="<%= product.getId()%>">

            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" value="<%= product.getName()%>" required><br><br>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" value="<%= product.getPrice()%>" step="0.01" required><br><br>

            <label for="productYear">Product Year:</label>
            <input type="number" id="productYear" name="productYear" value="<%= product.getProductYear()%>" required><br><br>

            <label for="image">Image URL:</label>
            <input type="text" id="image" name="image" value="<%= product.getImage()%>"><br><br>

            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null)
                    {
                        for (Category category : categories)
                        {
                %>
                <option value="<%= category.getId()%>" 
                        <%= category.getId() == product.getCategory().getId() ? "selected" : ""%>>
                    <%= category.getName()%>
                </option>
                <%
                        }
                    }
                %>
            </select><br><br>
            <button type="submit">Update Product</button>
        </form>
    </body>
</html>
