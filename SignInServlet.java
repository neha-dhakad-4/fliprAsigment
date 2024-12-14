/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SignInServlet")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");
 
         String jdbcURL = "jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1"; // Replace with your database name
         String dbUser = "root"; // Replace with your database username
        String dbPassword = "root"; 
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
      
            
            // SQL Query to validate user
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement stmt1 = connection.prepareStatement(query);
            stmt1.setString(1, email);
            stmt1.setString(2, password);

            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                // User authenticated
                request.setAttribute("message", "Welcome, " + rs.getString("full_name") + "!");
                request.getRequestDispatcher("Welcome.jsp").forward(request, response);
            } else {
                // Invalid credentials
                request.setAttribute("message", "Invalid email or password. Please try again.");
                request.getRequestDispatcher("signin.jsp").forward(request, response);
            }

             
            // Close resources
            rs.close();
            stmt1.close();
            connection.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
    }
}
