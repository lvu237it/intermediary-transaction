/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.*;

/**
 *
 * @author ADMIN
 */
public class VerifyEmailController extends HttpServlet {

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
            out.println("<title>Servlet VerifyEmailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyEmailController at " + request.getContextPath() + "</h1>");
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

        //Lấy action từ session
        HttpSession session = request.getSession();
        String action = (String) session.getAttribute("action") + "";

        //Truy cập vào trang nếu action đúng
        if (action.equals("forget") || action.equals("signup")) {
            request.getRequestDispatcher("VerifyEmailPage.jsp").forward(request, response);
        } //Chuyển về trang đăng nhập và thông báo lỗi nếu action không đúng
        else {
            session.invalidate();
            request.setAttribute("message", "Bạn không có quyền truy cập vào trang này!");
            request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
        }
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

        //Lấy action từ session
        HttpSession session = request.getSession();
        String action = (String) session.getAttribute("action") + "";

        if (action == null) {

            //Chuyển về trang đăng nhập và thông báo lỗi nếu action không đúng
            session.invalidate();
            request.setAttribute("message", "Bạn không có quyền truy cập vào trang gửi otp ");
            request.getRequestDispatcher("Login.jsp").forward(request, response);

        } else {

            //Lấy một số thông tin từ session
            String otp = (String) session.getAttribute("otp") + "";
            String email = (String) session.getAttribute("email") + "";

            //Lấy thông tin otp action ở trang xác thực 
            String otpAction = request.getParameter("action");

            //Xử lí khi otp action là xác nhận
            if (otpAction.equals("submit")) {

                //Lấy otp nhập từ trang web
                String num1 = request.getParameter("num1").trim();
                String num2 = request.getParameter("num2").trim();
                String num3 = request.getParameter("num3").trim();
                String num4 = request.getParameter("num4").trim();
                String num5 = request.getParameter("num5").trim();
                String num6 = request.getParameter("num6").trim();
                String otpInput = num1 + num2 + num3 + num4 + num5 + num6;

                //Lấy số lần xác thực otp từ trang web
                Integer otpSubmitCount = (Integer) session.getAttribute("otpSubmitCount");

                //Xử lí khi số lần xác thực bằng 5
                if (otpSubmitCount == 5) {

                    //Xóa otp hiện tại 
                    session.removeAttribute("otp");
                    session.setAttribute("otp", "");

                    //Gửi thông báo về trang web
                    request.setAttribute("message", "Mã OTP không chính xác, vui lòng thử lại.");
                    request.getRequestDispatcher("VerifyEmailPage.jsp").forward(request, response);

                    //Xử lí khii số lần xác thực dưới 5
                } else {

                    //Xử lí khi otp khớp
                    if (otpInput.equals(otp)) {

                        //Lấy hạn otp và thời gian hiện tại
                        Long otpExpiredTime = (Long) session.getAttribute("otpExpiredTime");
                        long currentTime = System.currentTimeMillis();

                        //Xóa otp khỏi session
                        session.removeAttribute("otp");

                        //Xử lí khi quá hạn otp để xác thực
                        if (otpExpiredTime < currentTime) {

                            //Khởi tạo otp rỗng trong session
                            session.setAttribute("otp", "");

                            //Tăng số lần nhập
                            otpSubmitCount++;
                            session.removeAttribute("otpSubmitCount");
                            session.setAttribute("otpSubmitCount", otpSubmitCount);

                            //Gửi thông báo tới trang web
                            request.setAttribute("message", "Mã OTP không chính xác, vui lòng thử lại.");
                            request.getRequestDispatcher("VerifyEmailPage.jsp").forward(request, response);
                        } else {

                            //Lưu trạng thái vào session
                            session.setAttribute("verify", "ok");

                            //Xóa thông tin không cần thiết nữa khỏi session
                            session.removeAttribute("otpSubmitCount");
                            session.removeAttribute("otpResendCount");
                            session.removeAttribute("otpExpiredTime");

                            //Chuyển hướng sang trang web khác
                            if (action.equals("forget")) {
                                response.sendRedirect("/swp391_g6_se1755_net/reset-password");
                            } else {
                                response.sendRedirect("/swp391_g6_se1755_net/createprofile");
                            }

                        }

                        //Xử lí khi otp không khớp
                    } else {

                        //Tăng số lần nhập và gửi thông báo trên trang web
                        request.setAttribute("message", "Mã OTP không chính xác, vui lòng thử lại.");
                        otpSubmitCount++;
                        session.removeAttribute("otpSubmitCount");
                        session.setAttribute("otpSubmitCount", otpSubmitCount);
                        request.getRequestDispatcher("VerifyEmailPage.jsp").forward(request, response);
                    }
                }

                //Xử lí khi otp action là gửi lại    
            } else if (otpAction.equals("resend")) {

                //Lấy một số thông tin về otp từ session
                Integer otpResendCount = (Integer) session.getAttribute("otpResendCount");
                Long firstOtpTime = (Long) session.getAttribute("firstOtpTime");
                Long lastOtpTime = (Long) session.getAttribute("lastOtpTime");

                //Lấy thời gian hiện tại
                long oneMinuteInMillis = 60 * 1000;
                long currentTime = System.currentTimeMillis();

                //Xử lí khi số lần gửi là 5 và khoảng thời gian nhập chứa quá 1 phút
                //và thời gian đợi chưa quá 1 phút
                if ((otpResendCount == 5) && (lastOtpTime - firstOtpTime <= oneMinuteInMillis)
                        && (currentTime - lastOtpTime <= oneMinuteInMillis)) {

                    //Gửi thông báo len trang web
                    request.setAttribute("message", "Yêu cầu gửi quá nhiều lần. Vui lòng đợi trong giây lát");
                    request.getRequestDispatcher("VerifyEmailPage.jsp").forward(request, response);
                } else {
                    //TH1: Lần đầu yêu cầu gửi lại
                    //TH2: Số lần gửi dưới 5, thời gian từ hiện tại tới lần nhập đầu quá 1 phút 
                    //TH3: Số lần gửi là 5, thời gian từ hiện tại tới lần gửi đầu không quá 1 phút,
                    //thời gian từ hiện tại tới lần gửi cuối cùng quá 1 phút
                    if ((otpResendCount == 0) || (lastOtpTime == null)
                            || (firstOtpTime == null)
                            || ((currentTime - firstOtpTime > oneMinuteInMillis) && (otpResendCount < 5))
                            || (otpResendCount == 5) && (lastOtpTime - firstOtpTime <= oneMinuteInMillis)
                            && (currentTime - lastOtpTime > oneMinuteInMillis)) {

                        //Đặt lại số lần gửi là 1
                        otpResendCount = 1;

                        //Lưu thông tin về thời gian gửi lần đầu mới lên session
                        session.removeAttribute("firstOtpTime");
                        session.setAttribute("firstOtpTime", currentTime);
                    } else {

                        //Tăng số lần gửi
                        otpResendCount++;
                    }
                }
                //Xóa một số thông tin về otp trên session
                session.removeAttribute("otp");
                session.removeAttribute("otpExpiredTime");
                session.removeAttribute("otpResendCount");
                session.removeAttribute("lastOtpTime");

                //Lưu số lần gửi otp và thời gian gửi cuối cùng lên session
                session.setAttribute("otpResendCount", otpResendCount);
                session.setAttribute("lastOtpTime", currentTime);

                //Đặt số lần xác thực otp về 0
                session.removeAttribute("otpSubmitCount");
                session.setAttribute("otpSubmitCount", 0);

                //Tạo otp và gửi về email
                otp = Encryption.generateOTP();
                session.setAttribute("otp", otp);
                SendEmail.threadSendEmail(email, "Xác thực tài khoản", otp);

                //Lưu thời hạn otp lên session
                long otpExpireTime = System.currentTimeMillis() + 5 * 60 * 1000;
                session.setAttribute("otpExpiredTime", otpExpireTime);

                //Gửi thông báo lên trang web
                request.setAttribute("message", "Mail đã được gửi. Vui lòng kiểm tra hòm thư của bạn.");
                request.getRequestDispatcher("VerifyEmailPage.jsp").forward(request, response);
            }
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
