import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get the product ID from the request parameter
        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            // Handle case when ID is not provided or empty
            out.println("<center><h1>Error: Product ID is required!</h1></center>");
            return;
        }

        try {
            // Try to parse the ID to integer
            int productId = Integer.parseInt(id);

            // Database connection and deletion logic
            try {
                // Register JDBC driver and open a connection
                Class.forName("com.mysql.jdbc.Driver"); // Updated JDBC driver class name
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");

                // Prepare the DELETE SQL query
                PreparedStatement pst = con.prepareStatement("DELETE FROM products WHERE id = ?");
                pst.setInt(1, productId); // Set the product ID to delete

                // Execute the query and get the number of rows deleted
                int rowsDeleted = pst.executeUpdate();

                // Provide feedback based on the result of the deletion
                if (rowsDeleted > 0) {
                    out.println("<center><h1>Product deleted successfully!</h1></center>");
                } else {
                    out.println("<center><h1>No product found with that ID!</h1></center>");
                }

                // Close the PreparedStatement and Connection
                pst.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                out.println("<center><h1>Error: " + e.getMessage() + "</h1></center>");
            }

        } catch (NumberFormatException e) {
            // If the ID is not a valid integer, display an error message
            out.println("<center><h1>Error: Invalid Product ID format!</h1></center>");
        }
    }
}
