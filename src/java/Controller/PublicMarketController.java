/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductDAO;
import DAO.UserDAO;
import Model.Product;
import Model.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class PublicMarketController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PublicMarketController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PublicMarketController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("PublicMarket.jsp").forward(request, response); 

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ProductDAO productdao = new ProductDAO();
        HttpSession session = request.getSession();
        User userAccount = (User) session.getAttribute("account");
        String nameProduct = request.getParameter("nameProduct");
        String optionProductType = request.getParameter("optionProductType");
        String priceProductFrom = request.getParameter("priceProductFrom");
        String priceProductTo = request.getParameter("priceProductTo");
        StringBuilder htmlBuilder = new StringBuilder();
        ArrayList<Product> pdList = null;

        //Tìm kiếm trong phạm vi trang public market
        if (nameProduct == "" && optionProductType == "" && priceProductFrom == "" && priceProductTo == "") {
            //Hiển thị all sản phẩm khi mới load page
            if (userAccount == null) {
                pdList = getProductsCreateByName(productdao.getAllProductWithNotPurchased());//ok
                
            } else {
                pdList = getProductsCreateByName(productdao.getAllProductWithNotPurchased(userAccount));
            }
            // Tìm kiếm và lấy dữ liệu từ cơ sở dữ liệu dựa trên các trường được chọn
        } else {
            if (userAccount == null) {
                pdList = getProductsCreateByName(productdao.getAllProductBySearching(nameProduct, priceProductFrom, priceProductTo, optionProductType));
            } else {
                pdList = getProductsCreateByName(productdao.getAllProductBySearching(nameProduct, priceProductFrom, priceProductTo, optionProductType, userAccount));
            }
        }
        
        String pdListJson = new Gson().toJson(pdList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(pdListJson);
    }

    private ArrayList<Product> getProductsCreateByName(ArrayList<Product> products) {


        for (Product p : products) {

            int userId = p.getCreatedBy();
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserbyId(userId);
            User user2 = new User(user.getFullName());
            p.setUser(user2);

        }

        return products;

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
