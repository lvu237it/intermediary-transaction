/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.TransactionDAO;
import DAO.UserDAO;
import DAO.WithdrawDAO;
import Model.Transaction;
import Model.User;
import Model.Withdraw;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class HandleWithdrawRequestController extends HttpServlet {

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
            out.println("<title>Servlet HandleWithdrawRequestController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HandleWithdrawRequestController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        boolean action = false;
        int selectedAction = Integer.parseInt(request.getParameter("selectedAction"));
        int withdrawRequestId = Integer.parseInt(request.getParameter("withdrawRequestId"));
        String responseWithdraw = request.getParameter("responseWithdraw");
        String imgUrl = request.getParameter("imgUrl");
        WithdrawDAO wDAO = new WithdrawDAO();
        UserDAO uDAO = new UserDAO();
        TransactionDAO tDAO = new TransactionDAO();
        Withdraw withdraw = wDAO.getWithdrawalDetails(withdrawRequestId);
        switch (withdraw.getStatus()) {
            case 1:
                if (selectedAction == 2) {
                    wDAO.updateWithdraw(withdrawRequestId, 2, responseWithdraw, imgUrl);
                    action = true;
                } else if (selectedAction == 4) {
                    wDAO.updateWithdraw(withdrawRequestId, 4, responseWithdraw, imgUrl);
                    User user = uDAO.getUserbyId(withdraw.getCreatedBy());
                    double amount = withdraw.getWithdrawAmount() * 90 / 100;
                    uDAO.updateUserWalletById(withdraw.getCreatedBy(), user.getWallet() + amount);
                    tDAO.insertNewTransaction(new Transaction(amount, true, "Từ chối yêu cầu rút tiền mã số " + withdraw.getId() + ". Hoàn 90% số tiền yêu cầu rút. ", true, 1));
                    action = true;
                }

                break;
            case 2:
                if (selectedAction == 3) {
                    wDAO.updateWithdraw(withdrawRequestId, 3, responseWithdraw, imgUrl);
                    action = true;
                } else if (selectedAction == 5) {
                    wDAO.updateWithdraw(withdrawRequestId, 5, responseWithdraw, imgUrl);
                    User user = uDAO.getUserbyId(withdraw.getCreatedBy());
                    double amount = withdraw.getWithdrawAmount();
                    uDAO.updateUserWalletById(withdraw.getCreatedBy(), user.getWallet() + amount);
                    tDAO.insertNewTransaction(new Transaction(amount, true, "Bị lỗi yêu cầu rút tiền mã số " + withdraw.getId() + ". Hoàn 100% số tiền yêu cầu rút. ", true, 1));
                    action = true;
                }
                break;
        }
        // Tạo một đối tượng JSON
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("action", action);

        // Chuyển đổi đối tượng JSON thành chuỗi JSON
        String jsonResult = jsonResponse.toString();

        // Thiết lập các đặc tính của phản hồi HTTP
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Ghi chuỗi JSON vào phản hồi
        response.getWriter().write(jsonResult);
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
