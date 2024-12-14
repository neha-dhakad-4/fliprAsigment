<%-- 
    Document   : GetAllProducts
    Created on : 12-Dec-2024, 11:48:14 pm
    Author     : neha0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Image Show</title>
    </head>
    
    <style>
        
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
        
        .table{
            margin-top: 50px;
        }
    </style>
    <body>
        <button class="top-right-button" onclick="window.location.href='cart.jsp'">Cart</button>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");
                Statement st = con.createStatement();
                String sql = "SELECT id,name,description,price,filename FROM products";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                  int id = rs.getInt("id");
                    String firstname = rs.getString("name");
                    String lastname = rs.getString("description");
                    int filename = rs.getInt("price");
                    String lastnam = rs.getString("filename");
//                    String path = rs.getString("path");
        %>

        <table  class="table" style="width:100%" border="2">
            <tr>
                <th>name</th>
                <th>description</th>
                <th>price</th>
                <th>Image</th>
                <th>cart button</th>
<!--                <th>Path</th>-->
            </tr>
            <tr>
                <td><%= firstname%></td>
                <td><%=lastname%></td>
                <td><%=filename%></td>
                <td><image src="<%=lastnam%>" width="150" height="200"/></td>
                <td>
             <form action="CartServlet" method="POST">
                    <input type="hidden" name="productId" value="<%= id %>">
                    <input type="number" name="quantity" min="1" value="1" required>
                    <button type="submit">Add to Cart</button>
                </form>
                </td>
            </tr>
        </table>
        <%
                }
            } catch (Exception e) {
                out.println(e);
            }
        %>
         
        
    </body>
</html>

