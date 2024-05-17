/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderDAO;
import DAO.UserDAO;
import Model.Order;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import utils.UserTokenMap;

/**
 *
 * @author User
 */
public class PurchasedModalDetailController extends HttpServlet {

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
            out.println("<title>Servlet PurchasedModalDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchasedModalDetailController at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        OrderDAO orderdao = new OrderDAO();
        HttpSession session = request.getSession();
        String productIdGlobal = request.getParameter("id");
        System.out.println("productID: "+ productIdGlobal);
        UserDAO userDao = new UserDAO();
        //lấy ra token của người đang đăng nhập
        String jwtLoggedInUser = (String) session.getAttribute("jwt");
        UserTokenMap mapToken = UserTokenMap.getInstance();

        String usernameBuyer = mapToken.getUsernameByToken(jwtLoggedInUser);
        //không dựa vào attribute "account" ở session mà dựa vào token của người dùng để kiểm tra đó là người dùng nào
        User userAccount = userDao.getUserByUsername(usernameBuyer);

        Order order = orderdao.getOrderByUserNProductId(userAccount, productIdGlobal);

        UserDAO userdao = new UserDAO();

        User u = userdao.getUserbyId(order.getProductCreatedBy());
        Order newOrder = new Order(order.getId(), order.getProductname(), order.getHiddenField(), order.getSellerReceivedTrueMoney(), order.getStatus(), u.getUserName(), order.getProductcontact(), order.getIsPrivate(), order.getProductprice(), order.isFeePayer(), order.getTransactionfee(), order.getTotalPrice(), order.getType(), order.getShareLink(), order.getCreatedAt(), order.getUpdatedAt());
        System.out.println("New order: "+newOrder);
//        Gson gson = new Gson();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("recordsTotal", recordsTotal);
//        jsonObject.addProperty("recordsFiltered", recordsFiltered);
//        jsonObject.add("data", gson.toJsonTree(newOrder));
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        out.print(jsonObject.toString());
        
        
         String jsonResult = new Gson().toJson(newOrder);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResult);
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
        processRequest(request, response);
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
