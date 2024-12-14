import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/UpdateCart")
public class UpdateCart extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String cartId = request.getParameter("cartId");
        String quantity = request.getParameter("quantity");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");

            String query = "UPDATE cart SET quantity = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(quantity));
            ps.setInt(2, Integer.parseInt(cartId));
            ps.executeUpdate();
//        out.println("<center><h1>Product update Succesfully......</h1></center>");
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         response.sendRedirect("cart.jsp");
    }
}
