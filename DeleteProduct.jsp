<%-- 
    Document   : DeleteProduct
    Created on : 11-Dec-2024, 4:57:09 pm
    Author     : neha0
--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Product</title>
</head>
<body>
   <form action="DeleteProductServlet" method="POST">
    <label for="id">Product ID:</label>
    <input type="number" id="id" name="id" required><br><br>
    <button type="submit">Delete Product</button>
</form>


</body>
</html>

