/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Collecting form data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
         String jdbcURL = "jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1"; // Replace with your database name
         String dbUser = "root"; // Replace with your database username
        String dbPassword = "root"; 
        try {
            // JDBC Connection
          Class.forName("com.mysql.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // SQL Query
            String query = "INSERT INTO Users (full_name, email, phone, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            // Setting Parameters
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);

            // Executing Query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("index.html");
                out.println("<h2>Registration Successful! Welcome, " + fullName + ".</h2>");
            } else {
                out.println("<h2>Registration Failed! Please try again.</h2>");
            }

            // Closing resources
            stmt.close();
            connection.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
    }
}

