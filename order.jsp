<%-- 
    Document   : order
    Created on : 14-Dec-2024, 10:46:03 am
    Author     : neha0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="OrderServlet" method="post">
            <label for="Cust_id">Cust_id:</label>
        <input type="text" id="cust_id" name="cust_id" required><br><br>
        
        <input type="submit" value="order">
        </form>
    </body>
</html>
