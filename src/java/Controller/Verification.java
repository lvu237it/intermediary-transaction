/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.User;
import DAO.UserDAO;
import utils.*;
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
public class Verification extends HttpServlet {

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
        HttpSession session = request.getSession();
        // lấy lại email trên session
        String emailSignup = (String) session.getAttribute("emailSignup");
        // gỡ otp trước đó đã lưu vào session
        session.removeAttribute("otpSignup");
        // sinh OTP ngẫu nhiên mới và lưu lại vào session
        String otpSignup = Encryption.generateOTP();
        session.setAttribute("otpSignup", otpSignup);
        // gửi OTP đó về mail cho người dùng
        SendEmail.sendOTP(emailSignup,"Xác thực tài khoản tại hệ thống giao dịch trung gian GD77_G6", otpSignup);
        session.setMaxInactiveInterval(3 * 60); // 3 phút

        response.sendRedirect("ConfirmSignupOTP.jsp");

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
        PrintWriter out = response.getWriter();
        // khai báo các object cần thiết để sử dụng
        HttpSession session = request.getSession();
        UserDAO uDAO = new UserDAO();
        User u = new User();
        // lấy toàn bộ thông tin được lưu tạm lên session

        String usernameSignup = (String) session.getAttribute("usernameSignup");
        String passwordSignup = (String) session.getAttribute("passwordSignup");
        String emailSignup = (String) session.getAttribute("emailSignup");

        Encryption encrypt = new Encryption();
        //Mã hoá mật khẩu và lưu tạm vào session
        String encryptedPass = encrypt.toSHA1(passwordSignup);
        
        
        String otpSignup = (String) session.getAttribute("otpSignup");
        // lấy OTP từ web confirm về servlet
        String otpConfirm = "";
        try {
            otpConfirm = request.getParameter("otp");
        } catch (Exception e) {
            // chưa biết xử lý lỗi gì
        }

        if (otpConfirm.equals(otpSignup)) {
            // bỏ session lưu tạm của username, password, email và otp

            session.removeAttribute("otpSignup");

            //chèn account mới sau khi đã sign up thành công
            //uDAO.insertNewAccount(usernameSignup, encryptedPass, emailSignup);
            
            session.removeAttribute("passwordSignup");
            session.removeAttribute("emailSignup");
            
            out.print(usernameSignup + ", " + passwordSignup + ", " + emailSignup);
            request.setAttribute("message", "Mã OTP hợp lệ!");
            response.sendRedirect("CreateProfile.jsp");
        } else {
            request.setAttribute("message", "Mã OTP không hợp lệ! Vui lòng thử lại!");
            request.getRequestDispatcher("ConfirmSignupOTP.jsp").forward(request, response);
        }

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
