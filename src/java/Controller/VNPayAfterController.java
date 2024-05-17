/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.*;
import DAO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import vnpay.Config;

/**
 *
 * @author ADMIN
 */
public class VNPayAfterController extends HttpServlet {

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
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);

        double amount = Double.parseDouble(request.getParameter("vnp_Amount")) / 100;

        String id = request.getParameter("vnp_TxnRef");

        String transactionNo = request.getParameter("vnp_TransactionNo");

        //khai báo các biến cần thiết cho việc truy xuất dữ liệu
        TransactionDAO transdao = new TransactionDAO();
        Transaction trans = new Transaction();
        VNPayTransactionDAO vnpdao = new VNPayTransactionDAO();
        UserDAO udao = new UserDAO();
        VNPayTransaction vnptran = vnpdao.getVnpTransByCode(id);

        //
        HttpSession session = request.getSession();
        User userTemp = (User) session.getAttribute("account");

        String vnpstatus = vnptran.getStatus();
        // kiểm tra lại trạng thái giao dịch của đơn đang được yêu cầu
        if (vnpstatus.equalsIgnoreCase("Success") || vnpstatus.equalsIgnoreCase("Fail") || vnpstatus.equalsIgnoreCase("Invalid signature")) {
            // nếu đã là Success/Fail/Ivalid signature thì không xử lý nữa
            request.setAttribute("error", "Giao dịch đã được xử lý, xin vui lòng kiểm tra lại");
            request.getRequestDispatcher("HomePage.jsp").forward(request, response);
        } else { // nếu trạng thái trong bảng VnPayTransaction đang là pending         
            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                    // cập nhật trạng thái của đơn trong bảng vnpay_transaction
                    vnpdao.updateVNPTransactionStatus("Success", vnptran);
                    // cập nhật ví tiền cho nguoi dung dang nhap
                    synchronized (this) {
                        // Get the current balance
                        double currentBalance = userTemp.getWallet();

                        // Add the deposit amount to the current balance
                        double newBalance = currentBalance + amount;

                        // Update the balance
                        udao.updateUserWalletById(userTemp, newBalance);
                    }

                    trans.setAmount(amount);
                    trans.setCreatedBy(userTemp.getUserId());
                    trans.setDetail("Xử lý thành công giao dịch: " + transactionNo + " ");
                    trans.setStatus(true);
                    trans.setType(true);
                    transdao.insertNewTransaction(trans);
                    try {
                        // reset lại session để hiển thị tiền mới lên trang
                        int id1 = userTemp.getUserId();
                        User user2 = udao.getUserbyId(id1);
                        session.setAttribute("account", user2);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    // trả về trang return
                    request.getRequestDispatcher("DepositResult.jsp").forward(request, response);
                } else {
                    // nếu trạng thái của giao dịch không phải là 00 "Success" thì cập nhật trạng thái "Fail" cho đơn trong bảng vnpay_transaction
                    vnpdao.updateVNPTransactionStatus("Fail", vnptran);
                    trans.setAmount(amount);
                    trans.setCreatedBy(userTemp.getUserId());
                    trans.setDetail("Xử lý không thành công giao dịch:" + transactionNo + " ");
                    trans.setStatus(false);
                    trans.setType(true);
                    transdao.insertNewTransaction(trans);

                    // trả về trang return
                    request.getRequestDispatcher("DepositResult.jsp").forward(request, response);
                }
            } else {
                // invalid signature
                vnpdao.updateVNPTransactionStatus("Invalid signature", vnptran);

                request.setAttribute("error", "Có lỗi trong khi tiến hành giao dịch, quý khách vui lòng kiểm tra lại tài khoản của mình!");
                request.getRequestDispatcher("HomePage.jsp").forward(request, response);
            }
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
