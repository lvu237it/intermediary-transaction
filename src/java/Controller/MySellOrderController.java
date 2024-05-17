/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductDAO;
import DAO.UserDAO;
import Model.Order;
import Model.Product;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import utils.UserTokenMap;

/**
 *
 * @author HP
 */
public class MySellOrderController extends HttpServlet {

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
            out.println("<title>Servlet MySellOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MySellOrderController at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<Product> pdList;
        ProductDAO dao = new ProductDAO();
        HttpSession session = request.getSession();
        UserDAO userDao = new UserDAO();
        //lấy ra token của người đang đăng nhập
        String jwtLoggedInUser = (String) session.getAttribute("jwt");
        UserTokenMap mapToken = UserTokenMap.getInstance();

        String usernameBuyer = mapToken.getUsernameByToken(jwtLoggedInUser);
        //không dựa vào attribute "account" ở session mà dựa vào token của người dùng để kiểm tra đó là người dùng nào
        User userAccount = userDao.getUserByUsername(usernameBuyer);
        
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        pdList = dao.getAllSellByUserLimitOffset(userAccount.getUserId(), start, length);

       

        ArrayList<Product> newListProduct = new ArrayList<>();
        
        for (Product product : pdList) {
            User userTemp = userDao.getUserbyId(product.getOrderCreatedBy());
            System.out.println(userTemp + "1");
           if(userTemp == null){
                newListProduct.add(new Product(product.getId(), product.getName(), product.getDescription(), product.getStatus(), 
                    "Không", product.getHiddenField(), product.getContact(), product.getShareLink(), 
                    product.getIsPrivate(), product.getPrice(), product.isFeePayer(), product.getType(), product.getCreatedBy(), "Không"));
           }else{
                newListProduct.add(new Product(product.getId(), product.getName(), product.getDescription(), product.getStatus(), 
                    userTemp.getUserName(), product.getHiddenField(), product.getContact(), product.getShareLink(), 
                    product.getIsPrivate(), product.getPrice(), product.isFeePayer(), product.getType(), product.getCreatedBy(), product.getOrderId()));
           }   
        }
       
        int recordsTotal = dao.getTotalRecordsByUser(userAccount);
        int recordsFiltered = dao.getTotalRecordsByUser(userAccount);
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("recordsTotal", recordsTotal);
        jsonObject.addProperty("recordsFiltered", recordsFiltered);
        jsonObject.add("data", gson.toJsonTree(newListProduct));
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonObject.toString());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    // servlet lấy chi tiết thông tin trong "Đơn bán của tôi"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ProductDAO pdao = new ProductDAO();
        
        String productId = request.getParameter("productId");
        out.print(productId);
        int pid = Integer.parseInt(productId);
       
        Product product = pdao.getProductbyProductID(pid);
//        out.print(product);
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.add("data", gson.toJsonTree(product));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonObject.toString());
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
