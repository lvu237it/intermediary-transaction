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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import utils.UserTokenMap;

/**
 *
 * @author User
 */
public class MyPurchaseOrderController extends HttpServlet {

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
            out.println("<title>Servlet MyPurchaseOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyPurchaseOrderController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("MyPurchaseOrder.jsp").forward(request, response);

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
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        OrderDAO orderdao = new OrderDAO();
        HttpSession session = request.getSession();

        UserDAO userDao = new UserDAO();
        //lấy ra token của người đang đăng nhập
        String jwtLoggedInUser = (String) session.getAttribute("jwt");
        UserTokenMap mapToken = UserTokenMap.getInstance();

        String usernameBuyer = mapToken.getUsernameByToken(jwtLoggedInUser);
        //không dựa vào attribute "account" ở session mà dựa vào token của người dùng để kiểm tra đó là người dùng nào
        User userAccount = userDao.getUserByUsername(usernameBuyer);

        int recordsPerPage = 8;
        
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String columnIndex = request.getParameter("columnIndex");
        String columnDirection = request.getParameter("columnDirection");
//        String currentPage = request.getParameter("currentPage");
        ArrayList<Order> orderList = orderdao.getOrderByUserLimitOffset(userAccount, start, length, columnIndex, columnDirection);

        UserDAO userdao = new UserDAO();

        ArrayList<Order> newOrderList = new ArrayList<>();

        for (Order order : orderList) {
            User u = userdao.getUserbyId(order.getProductCreatedBy());
            newOrderList.add(new Order(order.getId(), order.getProductname(), order.getHiddenField(), order.getSellerReceivedTrueMoney(), order.getStatus(), u.getUserName(), order.getProductcontact(), order.getIsPrivate(), order.getProductprice(), order.isFeePayer(), order.getTransactionfee(), order.getTotalPrice(), order.getType(), order.getShareLink(),order.getCreatedAt(), order.getUpdatedAt()));
        }

        StringBuilder htmlBuilder = new StringBuilder();

//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("account");
//        WithdrawDAO dao = new WithdrawDAO();
//        ArrayList<Withdraw> list = dao.loadWithDrawByUser(start, length, user.getUserId(), columnIndex, columnDirection);

        int recordsTotal = orderdao.getTotalRecordsByUser(userAccount);
        int recordsFiltered = recordsTotal;
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("recordsTotal", recordsTotal);
        jsonObject.addProperty("recordsFiltered", recordsFiltered);
        jsonObject.add("data", gson.toJsonTree(newOrderList));
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
