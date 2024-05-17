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
import utils.*;
/**
 *
 * @author ADMIN
 */
public class ResetPasswordController extends HttpServlet {

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
        
        //Lấy verify từ session
        HttpSession session = request.getSession();
        String verify = (String) session.getAttribute("verify") + "";
        
        //Truy cập vào trang nếu verify đúng
        if (verify.equals("ok")) {
            request.getRequestDispatcher("ResetPasswordPage.jsp").forward(request, response);
        }
        
        //Chuyển về trang đăng nhập và thông báo lỗi nếu action không đúng
        else{
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
        
        //Lấy mật khẩu từ request
        String pass = request.getParameter("password");
        
        // Lấy thông tin từ session
        HttpSession session = request.getSession();
        String verify = (String) session.getAttribute("verify");
        String username = (String) session.getAttribute("username") + "";
        pass=Encryption.toSHA1(pass);
        
        //Xử lí khi verify đúng
        if (verify.equals("ok")) {
            
            //Cập nhật mật khẩu trên database
            UserDAO userDAO = new UserDAO();
            userDAO.updatePasswordByUsername(pass, username);
            
            //Xóa session
            session.invalidate();
            
            //Chuyển hướng sang trang web khác và gửi thông báo
            request.setAttribute("message", "Mật khẩu đã được thay đổi vui lòng đăng nhập lại .");
            request.getRequestDispatcher("LoginPage.jsp").forward(request, response);

        } else {
            
            //Chuyển hướng sang trang web khác và gửi thông báo
            session.invalidate();
            request.setAttribute("message", "Bạn không có quyền truy cập vào trang này!");
            request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
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
