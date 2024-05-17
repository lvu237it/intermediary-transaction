/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.TransactionDAO;
import DAO.UserDAO;
import Model.Transaction;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TransactionManagementController extends HttpServlet {

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
            out.println("<title>Servlet TransactionManagementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransactionManagementController at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        User accountAttribute = (User) session.getAttribute("account");
        System.out.println(accountAttribute.getFullName());//ok
        TransactionDAO paymentDao = new TransactionDAO();
        UserDAO userDao = new UserDAO();

        String currentPage = request.getParameter("currentPage");
        int recordsPerPage = 10;
        ArrayList<Transaction> transactionList1;
        
        //Hiển thị tất cả bản ghi của người đăng nhập hiện tại
        //Nếu là admin thì hiển thị tất cả giao dịch của user lẫn admin
        if(accountAttribute.getRole() == 1){
            transactionList1 = paymentDao.getAllUserTransactionWithPagination(currentPage, recordsPerPage);
        }else{//Nếu là user thì hiển thị giao dịch của chính user đó
            transactionList1 = paymentDao.getUserTransactionHistoryById(accountAttribute.getUserId(), currentPage, recordsPerPage);
        }
        
        StringBuilder htmlBuilder = new StringBuilder();

        for (Transaction o : transactionList1) {
            User anUser = userDao.getUserbyId(o.getCreatedBy());
            htmlBuilder.append("<tr class=\"text-center\">\n")
                    .append("    <td>").append(o.getId()).append("</td>\n")
                    .append("    <td>").append(o.getAmount()).append("</td>\n");

            if (!o.isType()) {
                htmlBuilder.append("    <td>-</td>\n");
            } else {
                htmlBuilder.append("    <td>+</td>\n");
            }

            if (!o.isStatus()) {
                htmlBuilder.append("    <td>Chưa xử lý</td>\n");
            } else {
                htmlBuilder.append("    <td>Đã xử lý</td>\n");
            }

            htmlBuilder.append("    <td>").append(anUser.getFullName()).append("</td>\n");
                    htmlBuilder.append("    <td>").append(o.getCreatedAt()).append("</td>\n")
                    .append("    <td>").append(o.getUpdatedAt()).append("</td>\n");

                htmlBuilder.append("    <td><button type=\"button\" class=\"btn btn-outline-success\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal").append(o.getId()).append("\">Chi tiết</button></td>\n");
                htmlBuilder.append("<div class=\"modal fade\" id=\"exampleModal").append(o.getId()).append("\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel").append(o.getId()).append("\" aria-hidden=\"true\">\n")
                        .append("    <div class=\"modal-dialog\">\n")
                        .append("        <div class=\"modal-content\">\n")
                        .append("            <div class=\"modal-header\">\n")
                        .append("                <h1 class=\"modal-title fs-5\" id=\"exampleModalLabel").append(o.getId()).append("\">Giao dịch số ").append(o.getId()).append("</h1>\n")
                        .append("                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-body\">\n")
                        .append("                <div>Mã giao dịch: ").append(o.getId()).append("</div>\n"
                        + "                        <div>Số tiền: ").append(o.getAmount()).append("</div>\n");
                if (!o.isType()) {
                    htmlBuilder.append("    <div>Loại giao dịch: Trừ tiền</div>\n");
                } else {
                    htmlBuilder.append("    <div>Loại giao dịch: Cộng tiền</div>\n");
                }
                htmlBuilder.append("    <div>Mô tả: ").append(o.getDetail()).append("</div>\n");
                if (!o.isStatus()) {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Chưa xử lý</div>\n");
                } else {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Đã xử lý</div>\n");
                }
                
                htmlBuilder.append("    <div>Người sở hữu: ").append(anUser.getFullName()).append("</div>\n")
                        .append("    <div>Thời gian tạo: ").append(o.getCreatedAt()).append("</div>\n")
                        .append("    <div>Cập nhật cuối: ").append(o.getUpdatedAt()).append("</div>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-footer\">\n")
                        .append("                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n")
                        .append("            </div>\n")
                        .append("        </div>\n")
                        .append("    </div>\n")
                        .append("</div>\n")
                        .append("</tr>");
        }

        out.println(htmlBuilder.toString());
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        TransactionDAO paymentDao = new TransactionDAO();
        UserDAO userDao = new UserDAO();

        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");
        String transactionType = request.getParameter("optionTransactionType");
        String transactionStatus = request.getParameter("transactionStatus");
        String currentPage = request.getParameter("currentPage");
        int recordsPerPage = 10;
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        
            // Tìm kiếm và lấy dữ liệu từ cơ sở dữ liệu dựa trên các trường được chọn
            if (priceFrom != null && priceTo != null && !priceFrom.isEmpty() && !priceTo.isEmpty()) {
                ArrayList<Transaction> transactionList2 = paymentDao.getUserTransactionHistoryByAmount(priceFrom, priceTo, currentPage, recordsPerPage, account);

                StringBuilder htmlBuilder = new StringBuilder();

                for (Transaction o : transactionList2) {
                    User anUser = userDao.getUserbyId(o.getCreatedBy());
                    htmlBuilder.append("<tr class=\"text-center\">\n")
                            .append("    <td>").append(o.getId()).append("</td>\n")
                            .append("    <td>").append(o.getAmount()).append("</td>\n");

                    if (!o.isType()) {
                        htmlBuilder.append("    <td>-</td>\n");
                    } else {
                        htmlBuilder.append("    <td>+</td>\n");
                    }
                    if (!o.isStatus()) {
                        htmlBuilder.append("    <td>Chưa xử lý</td>\n");
                    } else {
                        htmlBuilder.append("    <td>Đã xử lý</td>\n");
                    }

                    htmlBuilder.append("    <td>").append(anUser.getFullName()).append("</td>\n")
                            .append("    <td>").append(o.getCreatedAt()).append("</td>\n")
                            .append("    <td>").append(o.getUpdatedAt()).append("</td>\n");
                htmlBuilder.append("    <td><button type=\"button\" class=\"btn btn-outline-success\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal").append(o.getId()).append("\">Chi tiết</button></td>\n");
                htmlBuilder.append("<div class=\"modal fade\" id=\"exampleModal").append(o.getId()).append("\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel").append(o.getId()).append("\" aria-hidden=\"true\">\n")
                        .append("    <div class=\"modal-dialog\">\n")
                        .append("        <div class=\"modal-content\">\n")
                        .append("            <div class=\"modal-header\">\n")
                        .append("                <h1 class=\"modal-title fs-5\" id=\"exampleModalLabel").append(o.getId()).append("\">Giao dịch số ").append(o.getId()).append("</h1>\n")
                        .append("                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-body\">\n")
                        .append("                <div>Mã giao dịch: ").append(o.getId()).append("</div>\n"
                        + "                        <div>Số tiền: ").append(o.getAmount()).append("</div>\n");
                if (!o.isType()) {
                    htmlBuilder.append("    <div>Loại giao dịch: Trừ tiền</div>\n");
                } else {
                    htmlBuilder.append("    <div>Loại giao dịch: Cộng tiền</div>\n");
                }
                htmlBuilder.append("    <div>Mô tả: ").append(o.getDetail()).append("</div>\n");
                if (!o.isStatus()) {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Chưa xử lý</div>\n");
                } else {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Đã xử lý</div>\n");
                }

                htmlBuilder.append("    <div>Người sở hữu: ").append(anUser.getFullName()).append("</div>\n")
                        .append("    <div>Thời gian tạo: ").append(o.getCreatedAt()).append("</div>\n")
                        .append("    <div>Cập nhật cuối: ").append(o.getUpdatedAt()).append("</div>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-footer\">\n")
                        .append("                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n")
                        .append("            </div>\n")
                        .append("        </div>\n")
                        .append("    </div>\n")
                        .append("</div>\n")
                        .append("</tr>");
                }
                out.println(htmlBuilder.toString());
            } else if (transactionType != null && !transactionType.isEmpty()) {
                ArrayList<Transaction> transactionList3 = paymentDao.getUserTransactionHistoryByTypeOfTransaction(transactionType, currentPage, recordsPerPage, account);
                StringBuilder htmlBuilder = new StringBuilder();

                for (Transaction o : transactionList3) {
                    User anUser = userDao.getUserbyId(o.getCreatedBy());
                    htmlBuilder.append("<tr class=\"text-center\">\n")
                            .append("    <td>").append(o.getId()).append("</td>\n")
                            .append("    <td>").append(o.getAmount()).append("</td>\n");

                    if (!o.isType()) {
                        htmlBuilder.append("    <td>-</td>\n");
                    } else {
                        htmlBuilder.append("    <td>+</td>\n");
                    }

                    if (!o.isStatus()) {
                        htmlBuilder.append("    <td>Chưa xử lý</td>\n");
                    } else {
                        htmlBuilder.append("    <td>Đã xử lý</td>\n");
                    }

                    htmlBuilder.append("    <td>").append(anUser.getFullName()).append("</td>\n")
                            .append("    <td>").append(o.getCreatedAt()).append("</td>\n")
                            .append("    <td>").append(o.getUpdatedAt()).append("</td>\n");
                htmlBuilder.append("    <td><button type=\"button\" class=\"btn btn-outline-success\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal").append(o.getId()).append("\">Chi tiết</button></td>\n");
                htmlBuilder.append("<div class=\"modal fade\" id=\"exampleModal").append(o.getId()).append("\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel").append(o.getId()).append("\" aria-hidden=\"true\">\n")
                        .append("    <div class=\"modal-dialog\">\n")
                        .append("        <div class=\"modal-content\">\n")
                        .append("            <div class=\"modal-header\">\n")
                        .append("                <h1 class=\"modal-title fs-5\" id=\"exampleModalLabel").append(o.getId()).append("\">Giao dịch số ").append(o.getId()).append("</h1>\n")
                        .append("                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-body\">\n")
                        .append("                <div>Mã giao dịch: ").append(o.getId()).append("</div>\n"
                        + "                        <div>Số tiền: ").append(o.getAmount()).append("</div>\n");
                if (!o.isType()) {
                    htmlBuilder.append("    <div>Loại giao dịch: Trừ tiền</div>\n");
                } else {
                    htmlBuilder.append("    <div>Loại giao dịch: Cộng tiền</div>\n");
                }
                htmlBuilder.append("    <div>Mô tả: ").append(o.getDetail()).append("</div>\n");
                if (!o.isStatus()) {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Chưa xử lý</div>\n");
                } else {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Đã xử lý</div>\n");
                }

                htmlBuilder.append("    <div>Người sở hữu: ").append(anUser.getFullName()).append("</div>\n")
                        .append("    <div>Thời gian tạo: ").append(o.getCreatedAt()).append("</div>\n")
                        .append("    <div>Cập nhật cuối: ").append(o.getUpdatedAt()).append("</div>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-footer\">\n")
                        .append("                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n")
                        .append("            </div>\n")
                        .append("        </div>\n")
                        .append("    </div>\n")
                        .append("</div>\n")
                        .append("</tr>");
                }

                out.println(htmlBuilder.toString());
            } else if (transactionStatus != null && !transactionStatus.isEmpty()) {
                ArrayList<Transaction> transactionList4 = paymentDao.getUserTransactionHistoryByStatus(transactionStatus, currentPage, recordsPerPage, account);
                StringBuilder htmlBuilder = new StringBuilder();

                for (Transaction o : transactionList4) {
                    User anUser = userDao.getUserbyId(o.getCreatedBy());
                    htmlBuilder.append("<tr class=\"text-center\">\n")
                            .append("    <td>").append(o.getId()).append("</td>\n")
                            .append("    <td>").append(o.getAmount()).append("</td>\n");

                    if (!o.isType()) {
                        htmlBuilder.append("    <td>-</td>\n");
                    } else {
                        htmlBuilder.append("    <td>+</td>\n");
                    }

                    if (!o.isStatus()) {
                        htmlBuilder.append("    <td>Chưa xử lý</td>\n");
                    } else {
                        htmlBuilder.append("    <td>Đã xử lý</td>\n");
                    }

                    htmlBuilder.append("    <td>").append(anUser.getFullName()).append("</td>\n")
                            .append("    <td>").append(o.getCreatedAt()).append("</td>\n")
                            .append("    <td>").append(o.getUpdatedAt()).append("</td>\n");
                htmlBuilder.append("    <td><button type=\"button\" class=\"btn btn-outline-success\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal").append(o.getId()).append("\">Chi tiết</button></td>\n");
           htmlBuilder.append("<div class=\"modal fade\" id=\"exampleModal").append(o.getId()).append("\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel").append(o.getId()).append("\" aria-hidden=\"true\">\n")
                        .append("    <div class=\"modal-dialog\">\n")
                        .append("        <div class=\"modal-content\">\n")
                        .append("            <div class=\"modal-header\">\n")
                        .append("                <h1 class=\"modal-title fs-5\" id=\"exampleModalLabel").append(o.getId()).append("\">Giao dịch số ").append(o.getId()).append("</h1>\n")
                        .append("                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-body\">\n")
                        .append("                <div>Mã giao dịch: ").append(o.getId()).append("</div>\n"
                        + "                        <div>Số tiền: ").append(o.getAmount()).append("</div>\n");
                if (!o.isType()) {
                    htmlBuilder.append("    <div>Loại giao dịch: Trừ tiền</div>\n");
                } else {
                    htmlBuilder.append("    <div>Loại giao dịch: Cộng tiền</div>\n");
                }
                htmlBuilder.append("    <div>Mô tả: ").append(o.getDetail()).append("</div>\n");
                if (!o.isStatus()) {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Chưa xử lý</div>\n");
                } else {
                    htmlBuilder.append("    <div>Trạng thái xử lý: Đã xử lý</div>\n");
                }

                htmlBuilder.append("    <div>Người sở hữu: ").append(anUser.getFullName()).append("</div>\n")
                        .append("    <div>Thời gian tạo: ").append(o.getCreatedAt()).append("</div>\n")
                        .append("    <div>Cập nhật cuối: ").append(o.getUpdatedAt()).append("</div>\n")
                        .append("            </div>\n")
                        .append("            <div class=\"modal-footer\">\n")
                        .append("                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Đóng</button>\n")
                        .append("            </div>\n")
                        .append("        </div>\n")
                        .append("    </div>\n")
                        .append("</div>\n")
                        .append("</tr>");
                }

                out.println(htmlBuilder.toString());
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
