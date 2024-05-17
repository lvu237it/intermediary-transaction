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
import utils.Encryption;
import utils.SendEmail;

/**
 *
 * @author ADMIN
 */
public class SignupController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        // khởi tạo các object cần thiết
        HttpSession session = request.getSession();
        UserDAO u = new UserDAO();
        // lấy thông tin như username, password và mail từ form đăng ký về đây
        String username = request.getParameter("username");

        String password = Encryption.toSHA1(request.getParameter("password")) ;

        String email = request.getParameter("email");

        // lấy captcha từ form đăng ký về đây
        String captcha = request.getParameter("captcha");

        String captchas = "";

        // gọi captcha từ session và gán nó vào biến captchas
        try {
            captchas = session.getAttribute("captchas").toString();
            session.removeAttribute("captchas");
        } catch (Exception ex) {

        }
        // so sánh xem captcha lấy từ form đăng ký với captcha lấy từ session
        if (captchas.equals(captcha)) {
            // nếu đúng captcha và thỏa mãn các điều kiện trên frontend thì gán
            // username, password và gmail lên session để dùng sau khi nhập OTP
            if (u.checkUsernameExist(username)) {
                request.setAttribute("message", "Tên đăng nhập này đã tồn tại trong hệ thống! Vui lòng thử lại!");
                request.getRequestDispatcher("SignUpPage.jsp").forward(request, response);
            }
            if(!email.contains("@gmail.com")){
                request.setAttribute("message", "Thông tin email không hợp lệ. Vui lòng thử lại!");
                request.getRequestDispatcher("SignUpPage.jsp").forward(request, response);
            }
            if(password.matches("^[a-zA-Z0-9]+$")){
                request.setAttribute("message", "Thông tin mật khẩu không hợp lệ. Vui lòng thử lại!");
                request.getRequestDispatcher("SignUpPage.jsp").forward(request, response);
            }
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("email", email);
            // sinh OTP ngẫu nhiên và lưu vào session
            String otp = Encryption.generateOTP();
            session.setAttribute("otp", otp);
            long otpExpireTime = System.currentTimeMillis() + 5 * 60 * 1000;
            int otpResendCount = 0;
            int otpSubmitCount = 0;
            session.setAttribute("otpExpiredTime", otpExpireTime);
            session.setAttribute("action", "signup");
            session.setAttribute("otpResendCount", otpResendCount);
            session.setAttribute("otpSubmitCount", otpSubmitCount);
            // gửi OTP đó về mail cho người dùng
            SendEmail.threadSendEmail(email,"Xác thực tài khoản", otp);
            // chuyển hướng về trang nhập OTP
            response.sendRedirect("/swp391_g6_se1755_net/verify-email");
        } else {
            // nếu sai catpcha thì lưu lại thông tin người dùng đã nhập
            // đẩy ngược lại lên SignupPage.jsp
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("message", "Mã captcha không đúng! Vui lòng thử lại");
            request.getRequestDispatcher("SignUpPage.jsp").forward(request, response);
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
