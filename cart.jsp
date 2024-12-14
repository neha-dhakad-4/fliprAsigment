<%-- 
    Document   : cart
    Created on : 13-Dec-2024, 8:11:49 pm
    Author     : neha0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    double totalAmount = 0.0;

    try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");
        stmt = con.createStatement();

        String query = "SELECT c.id AS cart_id, c.quantity, p.name, p.price, (c.quantity * p.price) AS total_price " +
                       "FROM cart c JOIN products p ON c.product_id = p.id";
        rs = stmt.executeQuery(query);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
            margin: 20px auto;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        .total {
            text-align: right;
            font-size: 18px;
            margin-right: 10%;
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">Cart</h1>
    <table>
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
                <th>Actions</th>
                <th>order</th>
            </tr>
        </thead>
        <tbody>
        <%
            while (rs.next()) {
                int cartId = rs.getInt("cart_id");
                String productName = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("total_price");
                totalAmount += totalPrice;
        %>
            <tr>
                <td><%= productName %></td>
                <td><%= price %></td>
                <td>
                    <form action="UpdateCart" method="POST">
                        <input type="hidden" name="cartId" value="<%= cartId %>">
                        <input type="number" name="quantity" value="<%= quantity %>" min="1" required>
                        <button type="submit">Update</button>
                    </form>
                </td>
                <td><%= totalPrice %></td>
                <td>
                    <form action="DeleteCart" method="POST">
                        <input type="hidden" name="cartId" value="<%= cartId %>">
                        <button type="submit">Delete</button>
                    </form>
                </td>
                 
                 <td>
                    
                        <button onclick="window.location.href='order.jsp'">Order Now</button>
                  
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <div class="total">
        <b>Total Amount: </b> <%= totalAmount %>
    </div>
</body>
</html>
<%
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (con != null) con.close();
    }
%>

