/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.SendEmail;
import utils.Encryption;

/**
 *
 * @author ADMIN
 */
public class ForgetPasswordController extends HttpServlet {

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
            out.println("<title>Servlet ResetPasswordController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("ForgetPasswordPage.jsp").forward(request, response);
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
        
        //Lấy username từ client
        String username = request.getParameter("username");
        
        //Lấy email từ database
        UserDAO userDAO = new UserDAO();
        String email = userDAO.getEmailByUsername(username);
        
        //Khởi tạo otp và các thông tin liên quan
        String otp = Encryption.generateOTP();
        long otpExpireTime = System.currentTimeMillis() + 5 * 60 * 1000;
        int otpResendCount = 0;
        int otpSubmitCount = 0;
        
        //Khởi tạo session
        HttpSession session = request.getSession();
        
        //Lưu các giá trị cần thiết vào session
        session.setAttribute("email", email);
        session.setAttribute("username", username);
        session.setAttribute("otp", otp);
        session.setAttribute("otpExpiredTime", otpExpireTime);
        session.setAttribute("action", "forget");
        session.setAttribute("otpResendCount", otpResendCount);
        session.setAttribute("otpSubmitCount", otpSubmitCount);
        
        //Gửi otp đến email cần xác thực nếu có
        SendEmail.threadSendEmail(email,"Xác thực tài khoản", otp);
        
        //Chuyển hướng sang trang xác thực
        response.sendRedirect("/swp391_g6_se1755_net/verify-email");
        
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
