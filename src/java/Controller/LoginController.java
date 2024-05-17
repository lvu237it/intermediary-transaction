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
import java.util.Map;
import utils.Encryption;
import utils.JwtGenerator;
import utils.UserTokenMap;
import utils.UserWalletMap;

/**
 *
 * @author User
 */
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("accountSession") != null) {
            request.getRequestDispatcher("HomePage.jsp").forward(request, response);
        } else {
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
//        processRequest(request, response);
        String accountName = request.getParameter("username");
        request.setAttribute("userNameValue", accountName);
        String pass = request.getParameter("password");
        request.setAttribute("passwordValue", pass);
        String captchaInput = request.getParameter("captcha");

        UserDAO dao = new UserDAO();

        //Mã hoá mật khẩu nhập vào
        String encryptedPass = Encryption.toSHA1(pass);

        User anAccount = dao.login(accountName, encryptedPass);
//        System.out.println(anAccount);
        String captchas;
        HttpSession ss = request.getSession();

        //Lấy captcha của session hiện tại
        captchas = ss.getAttribute("captchas") != null ? ss.getAttribute("captchas").toString() : "";

        //So sánh captcha người dùng nhập vào với captcha của session hiện tại
        if (captchaInput.equals(captchas)) {//nhập đúng captcha
            //Đăng nhập tài khoản với mật khẩu là password đã được hash từ inputted password

            if (anAccount == null) {//Sai tài khoản hoặc mật khẩu
                request.setAttribute("message", "Tài khoản hoặc mật khẩu không đúng");
                request.removeAttribute("passwordValue");//Xoá mật khẩu hiện tại có trên trường input
                request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
            } else {//Đăng nhập thành công
                // Đăng nhập thành công, tạo JWT tương ứng với thông tin tài khoản của mỗi người dùng
                JwtGenerator jwtGenerate = new JwtGenerator();
                String jwt = jwtGenerate.generateJwt(anAccount);

                UserTokenMap mapToken = UserTokenMap.getInstance();
                // Kiểm tra xem token có hết hạn hay không
                if (jwtGenerate.isTokenExpired(jwt)) {
                    //Đặt token mới tương ứng cho user đăng nhập
                    String newJwt = jwtGenerate.generateJwt(anAccount);
                    mapToken.putUserToTokenMap(anAccount, newJwt);
                    ss.setAttribute("jwt", newJwt);
                } else {
                    // Token hợp lệ, tiếp tục xử lý đăng nhập
                    mapToken.putUserToTokenMap(anAccount, jwt);
                    ss.setAttribute("jwt", jwt);
                    System.out.println("JWT for user is logging in: " + jwt);

                    //đưa mật khẩu đã mã hoá vào session để sử dụng cho việc đổi password
                    ss.setAttribute("oldPassEncrypted", encryptedPass);
                    if (anAccount.getRole() == 1) {
                        HttpSession session = request.getSession();
                        session.setAttribute("accountSession", "1");
                        session.setAttribute("account", anAccount);
                        session.setMaxInactiveInterval(60*60);
//                    request.getRequestDispatcher("HomePage.jsp").forward(request, response);
                        session.removeAttribute("captchas");
                        response.sendRedirect(request.getContextPath() + "/homepage");//vẫn chuyển hướng tới homepage nhưng với context path là /homepage thay vì /login
                    }
                    if (anAccount.getRole() == 0) {
                        HttpSession session = request.getSession();
                        session.setAttribute("accountSession", "0");
                        session.setAttribute("account", anAccount);
                        session.setMaxInactiveInterval(60000);//a session with 600 seconds = 10 minutes
//                    request.getRequestDispatcher("HomePage.jsp").forward(request, response);
                        session.removeAttribute("captchas");
                        response.sendRedirect(request.getContextPath() + "/homepage");//vẫn chuyển hướng tới homepage nhưng với context path là /homepage thay vì /login
                    }
                }
                //hiển thị Map bao gồm các user với token tương ứng đang tồn tại trong hệ thống
                System.out.println("MapToken: ");
                for (Map.Entry<String, String> entry : mapToken.entrySet()) {
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                }
            }
        } else {//nhập sai captcha hoặc captcha đã hết hiệu lực
            request.setAttribute("userNameValue", accountName);
            request.setAttribute("passwordValue", pass);
            request.setAttribute("message", "Mã captcha không đúng hoặc đã hết hiệu lực");
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
