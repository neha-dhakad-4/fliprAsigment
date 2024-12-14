
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int customerId = Integer.parseInt(request.getParameter("cust_id"));

        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psClearCart = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");
            
            // Set auto-commit to false for transaction management
            conn.setAutoCommit(false);

            // Insert new order
            String insertOrderSQL = "INSERT INTO orders (cust_id) VALUES (?)";
            psOrder = conn.prepareStatement(insertOrderSQL);
            psOrder.setInt(1, customerId);
            psOrder.executeUpdate();

            
            

            // Clear the cart for this customer
            String clearCartSQL = "DELETE FROM cart WHERE cust_id = ?";
            psClearCart = conn.prepareStatement(clearCartSQL);
            psClearCart.setInt(1, customerId);
            psClearCart.executeUpdate();

            // Commit the transaction
            conn.commit();

            response.sendRedirect("orderConfirmation.jsp");
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (psOrder != null) psOrder.close();
                if (psClearCart != null) psClearCart.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
