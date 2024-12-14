<%-- 
    Document   : Welcome
    Created on : 11-Dec-2024, 4:39:23 pm
    Author     : neha0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 50px;
        }
        .button {
            width: 300px;
            height: 50px;
            margin: 10px;
            font-size: 16px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
        }
        .button:hover {
            background-color: #45a049;
        }
        
        .top-right-button {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }
        .top-right-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Product Management</h1>
    
        <button class="button" onclick="window.location.href='addProduct.jsp'" type="submit" name="action" value="add">Add Product</button><br>
        <button class="button" onclick="window.location.href='UpdateProduct.jsp'" type="submit" name="action" value="update">Update Product</button><br>
         <button class="button" onclick="window.location.href='DeleteProduct.jsp'" type="submit" name="action" value="delete">Delete Product</button><br>
        <button class="button"  onclick="window.location.href='GetAllProducts.jsp'" type="submit" name="action" value="getAll">Get All Products</button>
        <button class="top-right-button" onclick="window.location.href='cart.jsp'">Cart</button>
</body>
</html>

