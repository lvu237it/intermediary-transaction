/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.*;
import Model.*;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HandleReportController extends HttpServlet {

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
            out.println("<title>Servlet HandleReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HandleReportController at " + request.getContextPath() + "</h1>");
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
        UserDAO udao = new UserDAO();
        ReportDAO rpdao = new ReportDAO();
        OrderDAO odao = new OrderDAO();
        TransactionDAO tdao = new TransactionDAO();
        ProductDAO pdao = new ProductDAO();
        boolean action = false;
        int status = Integer.parseInt(request.getParameter("status"));
        int actionStatus = Integer.parseInt(request.getParameter("actionStatus"));
        int id = Integer.parseInt(request.getParameter("id")); // id khiếu nại
        
        Report instanceReport = rpdao.getReportDetails(id);
        // lấy orderId theo bảng report
        String orderId = String.valueOf(instanceReport.getOrderId());
        
        // lấy id của bên bán và bên mua thông qua report
        int idBenMua = instanceReport.getBuyerId();
        int idBenBan = instanceReport.getSellerId();
        // khởi tạo 2 người dùng bên bán và bên mua
            User benban = udao.getUserbyId(idBenBan);
            User benmua = udao.getUserbyId(idBenMua);
            
         // lấy ví của bên bán và bên mua
         double viBenMua = udao.getUserWalletByUsername(benmua.getUserName());
         double viBenBan = udao.getUserWalletByUsername(benban.getUserName());
        if (status == 1 && actionStatus == 2) {
            //Kiểm tra xem ví của 2 bên có đủ 50k ko trước khi giữ 50k của 2 bên, chuyển status thành 2
            //Nếu ko đủ tiền thì action là false
            if(viBenMua >= 50000 && viBenBan >= 50000){
                //trừ tiền bên mua
                double tempBenMua = viBenMua - 50000;
                udao.updateUserWalletById(benmua, tempBenMua);
                Transaction tBenMua =  new Transaction(50000, false, "Hệ thống tạm giữ phí khiếu nại", true , idBenMua);
                tdao.insertNewTransaction(tBenMua);
                // trừ tiền bên bán
                double tempBenBan = viBenBan - 50000;
                udao.updateUserWalletById(benban, tempBenBan);
                Transaction tBenBan =  new Transaction(50000, false, "Hệ thống tạm giữ phí khiếu nại", true , idBenBan);
                tdao.insertNewTransaction(tBenBan);
                // chuyển status thành 2 
                rpdao.updateReportStatus(id, 2);
                action = true;
            }        
        }
        if (status == 2) {
            if(actionStatus == 3){
                //Bên mua đúng, trả 50k cho bên mua, xử lí việc hủy order, chuyển status thành 3
                // trả lại cho bên mua 50k
                int oid = Integer.parseInt(orderId);
                int productId = odao.getProductIdbyOrderId(oid);
                // lấy product dựa vào productId lấy từ orderId
                Product p = pdao.getProductInformation(String.valueOf(productId));
                // cộng tiền cho người mua
                double tempBenMua = viBenMua + 50000 + p.getPrice();
                udao.updateUserWalletById(benmua, tempBenMua);
                Transaction tBenMua =  new Transaction(50000, true, "Hệ thống hoàn trả phí khiếu nại", true , idBenMua);
                tdao.insertNewTransaction(tBenMua);
                // set status lại thành 3
                rpdao.updateReportStatus(id, 3);
                // chuyển trạng thái cho đơn có id = orderId thành trạng thái hủy đơn - OrderStatus = 6
                // insert vào bảng transaction việc cộng tiền sản phẩm lại cho người bán
                Transaction tHoanTienChoBenMua =  new Transaction(p.getPrice(), true, "Hệ thống hoàn trả tiền thu giữ đơn hàng " + oid +" sau khi khiếu nại", true , idBenMua);
                tdao.insertNewTransaction(tHoanTienChoBenMua);
                 // cập nhật trạng thái đơn
                odao.updateOrderStatusByID(orderId, 6);
                action = true;
            }
            if(actionStatus == 4){
                //Bên bán đúng, trả 50k cho bên bán, xử lí việc hoàn thành order, chuyển status thành 4
                // trả lại ben ban 50k
                int oid = Integer.parseInt(orderId);
                // lay ra order tuong ung voi orderId trong bang report
                Order order = odao.getOrderDetails(orderId);
                // cong tien thuc nhan cho nguoi ban
                double tempBenBan = viBenBan + 50000 + order.getSellerReceivedTrueMoney();
                udao.updateUserWalletById(benban, tempBenBan);
                Transaction tBenBan =  new Transaction(50000, true, "Hệ thống hoàn trả phí khiếu nại", true , idBenBan);
                tdao.insertNewTransaction(tBenBan);
                Transaction tBenBanThucNhan =  new Transaction(order.getSellerReceivedTrueMoney(), true, "Nhận tiền giao dịch trung gian số" + order.getId() + " sau khi khiếu nại", true , idBenBan);
                tdao.insertNewTransaction(tBenBanThucNhan);
                // set status thanh 4
                rpdao.updateReportStatus(id, 4);
                // bên mua phải trả lại tiền lại cho người bán đúng như giao dịch
                double tempBenMua = viBenMua - order.getTotalPrice();
                udao.updateUserWalletById(benmua, tempBenMua);
                Transaction tBenMuaPhaiTra =  new Transaction(order.getTotalPrice(), false, "Thu phí tạo yêu cầu trung gian số" + order.getId() + " sau khi khiếu nại", true , idBenMua);
                tdao.insertNewTransaction(tBenMuaPhaiTra);
                // chuyển trạng thái cho đơn có id = orderId thành trạng thái thành công - OrderStatus = 3
                odao.updateOrderStatusByID(orderId, 3);
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
