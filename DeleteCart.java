import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/DeleteCart")
public class DeleteCart extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cartId = request.getParameter("cartId");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");

            String query = "DELETE FROM cart WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(cartId));
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         response.sendRedirect("cart.jsp");
    }
}
