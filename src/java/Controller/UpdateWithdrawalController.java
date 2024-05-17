/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.WithdrawDAO;
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
public class UpdateWithdrawalController extends HttpServlet {

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
            out.println("<title>Servlet UpdateWithdrawalController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateWithdrawalController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User userAccount = (User) session.getAttribute("account");
        WithdrawDAO dao = new WithdrawDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        Withdraw withdraw = dao.getWithdrawalDetails(id);
        if (withdraw.getCreatedBy()==userAccount.getUserId())
        {
            String bankAccountNumber = request.getParameter("bankAccountNumber");
            String bankAccountName = request.getParameter("bankAccountName");
            String bankName = request.getParameter("bankName");
            if(!bankAccountName.equals("") && !bankAccountNumber.equals("") && !bankName.equals("")){
                dao.updateWithdrawalByUser(id, bankAccountNumber, bankAccountName, bankName);
                action =true;
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
