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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class CreateWithdrawRequestController extends HttpServlet {

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
            out.println("<title>Servlet CreateWithdrawRequestController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateWithdrawRequestController at " + request.getContextPath() + "</h1>");
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
        UserDAO uDAO = new UserDAO();
        WithdrawDAO wDAO = new WithdrawDAO();
        TransactionDAO tDAO = new TransactionDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        double wallet = uDAO.getUserWalletByUsername(user.getUserName());
        double withdrawAmount = -1;
        try {
            withdrawAmount = Double.parseDouble(request.getParameter("withdrawAmount"));
        } catch (Exception e) {
            System.out.println(e);
        }
        String bankAccountNumber = request.getParameter("bankAccountNumber").trim();
        String bankAccountName = request.getParameter("bankAccountName").trim();
        String bankName = request.getParameter("bankName").trim();
        if (withdrawAmount != -1 && withdrawAmount >= 100000 && !bankAccountName.equals("") && !bankAccountNumber.equals("") && !bankName.equals("")) {
            if (withdrawAmount <= wallet) {
                uDAO.updateUserWallet(user.getUserName(), wallet - withdrawAmount);
                wDAO.createNewWithdraw(1, withdrawAmount, bankAccountNumber, bankAccountName, bankName, user.getUserId());
                tDAO.insertNewTransaction(new Transaction(withdrawAmount, false, "Tạo yêu cầu rút tiền mã số " + withdrawAmount, true, user.getUserId()));
                action = true;
            }
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
