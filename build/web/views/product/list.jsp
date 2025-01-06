<%@page import="entities.Product"%>
<%@page import="entities.Category"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

        <form action="ListProductsController" method="POST">
            <div class="form-group">
                <label for="productName">Product Name:</label>
                <input type="text" id="productName" name="productName" 
                       value="${param.productName != null ? param.productName : ''}">
            </div>

            <div class="form-group">
                <label for="category">Category:</label>
                <select name="category" id="category">
                    <option value="">All Categories</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}" 
                                <c:if test="${category.id == param.category}">selected</c:if>>
                            ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit">Search</button>
        </form>

        <form action="MainController" method="GET">
            <input type="hidden" name="action" value="create">
            <button type="submit">Add New Product</button>
        </form>
        <br>

        <c:if test="${empty products}">
            <h3 class="center" style="color: red;">
                ${msg != null ? msg : 'No products found.'}
            </h3>
        </c:if>

        <c:if test="${not empty products}">
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
                    <c:forEach var="p" items="${products}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${p.name}</td>
                            <td>$${p.price}</td>
                            <td>${p.productYear}</td>
                            <td><img src="${p.image}" alt="Product Image" width="100" height="100"></td>
                            <td>${p.category.name}</td>
                            <td>
                                <form action="MainController" method="GET" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <button type="submit">Update</button>
                                </form>
                                <form action="MainController" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <button type="submit" onclick="return confirm('Are you sure you want to delete this product?');">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <br>
    </body>
</html>
