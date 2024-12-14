/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


//import static UpdateProductServlet.UPLOAD_DIR;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/AddProductServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)

public class AddProductServlet extends HttpServlet {
    /*
     create images folder at, C:\Users\Dharmesh Mourya\Documents\NetBeansProjects\ImageProject\build\web\images
     */

    public static final String UPLOAD_DIR = "images";
    public String dbFileName = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

//        int id = Integer.parseInt(request.getParameter("id"));
        String Name = request.getParameter("name");
        String Description = request.getParameter("description");
        int price= Integer.parseInt(request.getParameter("price"));
       
        Part part = request.getPart("file");//
        String fileName = extractFileName(part);//file name
      

         
            
        String applicationPath = getServletContext().getRealPath("");
       String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
       System.out.println("applicationPath:" + applicationPath);
       File fileUploadDirectory = new File(uploadPath);
       if (!fileUploadDirectory.exists()) {
           fileUploadDirectory.mkdirs();
       }
       String savePath = uploadPath + File.separator + fileName;
       System.out.println("savePath: " + savePath);
      String sRootPath = new File(savePath).getAbsolutePath();
      System.out.println("sRootPath: " + sRootPath);
      part.write(savePath + File.separator);
      File fileSaveDir1 = new File(savePath);
      /*if you may have more than one files with same name then you can calculate some random characters
        and append that characters in fileName so that it will  make your each image name identical.*/
       dbFileName = UPLOAD_DIR + File.separator + fileName;
       part.write(savePath + File.separator);
       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/flipr_db?characterEncoding=latin1", "root", "root");
            PreparedStatement pst = con.prepareStatement("insert into products (name,description,price,filename,path) values(?,?,?,?,?)");
//            pst.setInt(1, id);
            pst.setString(1, Name);
            pst.setString(2, Description);
            pst.setInt(3,price);
            pst.setString(4, dbFileName);
            pst.setString(5, savePath);
            pst.executeUpdate();
            out.println("<center><h1>Product Add Succesfully......</h1></center>");
//            out.println("<center><a href='display.jsp?id="'>Display</a></center>");
        } catch (Exception e) {
            out.println(e);
        }

    }
    // file name of the upload file is included in content-disposition header like this:
    //form-data; name="dataFile"; filename="PHOTO.JPG"

    private String extractFileName(Part part) {//This method will print the file name.
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}