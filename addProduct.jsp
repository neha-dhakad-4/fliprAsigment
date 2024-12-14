<%-- 
    Document   : addProduct
    Created on : 11-Dec-2024, 4:55:57 pm
    Author     : neha0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
    <h1>Add Product</h1>
    <form action="AddProductServlet" method="post" enctype="multipart/form-data">
        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        
        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br><br>
        
        <label for="price">Price:</label>
        <input type="number" step="0.01" id="price" name="price" required><br><br>
        
        <label for="image">Product Image:</label>
        <input type="file" id="file" name="file" accept="image/*" required><br><br>
        
        <button type="submit">Add Product</button>
    </form>
</body>
</html>

