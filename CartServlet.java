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
import java.sql.ResultSet;

@WebServlet("CartServlet")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve parameters
            String productIdParam = request.getParameter("productId");
            String quantityParam = request.getParameter("quantity");

            // Validate input
            if (productIdParam == null || quantityParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Product ID and quantity are required.\"}");
                return;
            }

            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            if (quantity <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Quantity must be a positive integer.\"}");
                return;
            }

            // Database connection
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?useUnicode=true&characterEncoding=utf8", "root", "root");

                // Check if product exists
                PreparedStatement checkProduct = con.prepareStatement("SELECT * FROM products WHERE id = ?");
                checkProduct.setInt(1, productId);
                ResultSet productRs = checkProduct.executeQuery();

                if (!productRs.next()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"error\": \"Invalid product ID.\"}");
                    return;
                }
                  // fetch cust_id from users table
//                PreparedStatement checkProduct = con.prepareStatement("SELECT * FROM products WHERE id = ?");
//                checkProduct.setInt(1, productId);
//                ResultSet productRs = checkProduct.executeQuery();

                // Check if product is already in the cart
                PreparedStatement checkCart = con.prepareStatement("SELECT * FROM cart WHERE product_id = ?");
                checkCart.setInt(1, productId);
                ResultSet cartRs = checkCart.executeQuery();

                if (cartRs.next()) {
                    // Update quantity if product is already in the cart
                    int currentQuantity = cartRs.getInt("quantity");
                    int newQuantity = currentQuantity + quantity;

                    PreparedStatement updateCart = con.prepareStatement("UPDATE cart SET quantity = ? WHERE product_id = ?");
                    updateCart.setInt(1, newQuantity);
                    updateCart.setInt(2, productId);
                    updateCart.executeUpdate();
                } else {
                    // Insert new entry into the cart
                    PreparedStatement insertCart = con.prepareStatement("INSERT INTO cart (product_id, quantity) VALUES (?, ?)");
                    insertCart.setInt(1, productId);
                    insertCart.setInt(2, quantity);
                    insertCart.executeUpdate();
                }

                // Fetch updated cart details
                PreparedStatement fetchCart = con.prepareStatement(
                    "SELECT p.id, p.name, p.price, c.quantity, (p.price * c.quantity) AS total " +
                    "FROM cart c INNER JOIN products p ON c.product_id = p.id");
                ResultSet updatedCart = fetchCart.executeQuery();

                // Construct response JSON
                StringBuilder cartJson = new StringBuilder("[");
                while (updatedCart.next()) {
                    if (cartJson.length() > 1) cartJson.append(",");
                    cartJson.append("{")
                            .append("\"productId\": ").append(updatedCart.getInt("id")).append(",")
                            .append("\"name\": \"").append(updatedCart.getString("name")).append("\",")
                            .append("\"price\": ").append(updatedCart.getDouble("price")).append(",")
                            .append("\"quantity\": ").append(updatedCart.getInt("quantity")).append(",")
                            .append("\"total\": ").append(updatedCart.getDouble("total"))
                            .append("}");
                }
                cartJson.append("]");

                // Return success response
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Product added to cart successfully.\", \"cart\": " + cartJson + "}");

                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
            }
        }
    }
}
