<%@page import="entities.Product"%>
<%@page import="entities.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .center {
                text-align: center;
            }
            .form-group {
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <h1>Product Page</h1>
        <br>

        <!-- SEARCH FORM -->
        <form action="ListProductsController" method="POST">
            <div class="form-group">
                <label for="productName">Product Name:</label>
                <input type="text" id="productName" name="productName" 
                       value="<%= request.getAttribute("productName") != null ? request.getAttribute("productName") : ""%>">
            </div>

            <div class="form-group">
                <label for="category">Category:</label>
                <select name="category" id="category">
                    <option value="">All Categories</option>
                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("categories");
                        String selectedCategory = (String) request.getAttribute("category");
                        if (categories != null)
                        {
                            for (Category category : categories)
                            {
                    %>
                    <option value="<%= category.getId()%>" 
                            <%= selectedCategory != null && !selectedCategory.isEmpty() && category.getId() == Integer.parseInt(selectedCategory) ? "selected" : ""%>>
                        <%= category.getName()%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <button type="submit">Search</button>
        </form>
                <!-- ADD NEW PRODUCT -->
        <form action="MainController" method="GET">
            <input type="hidden" name="action" value="create">
            <button type="submit">Add New Product</button>
        </form>
        <br>

        <!-- PRODUCT LIST -->
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            String msg = (String) request.getAttribute("msg");
            if (products == null || products.isEmpty())
            {
        %>
        <h3 class="center" style="color: red;"><%= msg != null ? msg : "No products found."%></h3>
        <%
        } else
        {
        %>
        <table>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Year</th>
                    <th>Image</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    for (Product p : products)
                    {
                %>
                <tr>
                    <td><%= count++%></td>
                    <td><%= p.getName()%></td>
                    <td>$<%= p.getPrice()%></td>
                    <td><%= p.getProductYear()%></td>
                    <td><img src="<%= p.getImage()%>" alt="Product Image" width="100" height="100"></td>
                    <td><%= p.getCategory().getName()%></td>
                    <td>
                        <form action="MainController" method="GET" style="display:inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="productId" value="<%= p.getId()%>">
                            <button type="submit">Update</button>
                        </form>
                        <form action="MainController" method="POST" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="productId" value="<%= p.getId()%>">
                            <button type="submit" onclick="return confirm('Are you sure you want to delete this product?');">Remove</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            }
        %>
        <br>

        
    </body>
</html>
