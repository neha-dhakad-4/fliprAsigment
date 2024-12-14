<%-- 
    Document   : UpdateProduct
    Created on : 11-Dec-2024, 4:56:39 pm
    Author     : neha0
--%>

<form action="UpdateProductServlet" method="post" enctype="multipart/form-data">
    <h1>Update product</h1>>
    <label for="id">Product ID:</label>
    <input type="text" id="id" name="id" required><br><br>

    <label for="name">Product Name:</label>
    <input type="text" id="name" name="name" required><br><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description" required></textarea><br><br>

    <label for="price">Price:</label>
    <input type="text" id="price" name="price" required><br><br>

    <label for="file">Upload File:</label>
    <input type="file" id="file" name="file" required><br><br>

    <button type="submit">Update Product</button>
</form>

