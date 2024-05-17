//*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import Model.*;
import DAO.*;
import com.google.gson.Gson;

/**
 *
 * @author ADMIN
 */
public class ProductDetailController extends HttpServlet {

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
        String productID = request.getParameter("id");
        System.out.println("productId from ProductDetailPage.jsp: " + productID);
//        request.setAttribute("pid", productID);
        System.out.println("null ak: " + productID);
        request.getRequestDispatcher("ProductDetailPage.jsp" + "?pid=" + productID).forward(request, response);
//        response.sendRedirect("ProductDetailPage.jsp?id=" + productID
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
        ProductDAO pDAO = new ProductDAO();
        String pId = request.getParameter("pid");
        System.out.println(pId);//ok
        Product p1 = pDAO.getProductInformation(pId); // failed
        System.out.println("p1: " + p1);//null
        Product p2 = getProductCreateByName(p1);
        System.out.println("p2: " + p2);

        String jsonResult = new Gson().toJson(p2);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResult);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private Product getProductCreateByName(Product product) {
        System.out.println("chay xuong day roi");
        int userId = product.getCreatedBy();
        System.out.println(userId);
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserbyId(userId);

        product.setUser(user);
        return product;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


