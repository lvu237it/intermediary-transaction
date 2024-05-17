/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderDAO;
import DAO.ProductDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Model.Order;
import Model.OrderListPendingSingleton;
import Model.Product;
import Model.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class UpdateOrderStatusNSellerWalletController extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrderStatusNSellerWalletController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrderStatusNSellerWalletController at " + request.getContextPath() + "</h1>");
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
        String productId = request.getParameter("productId");//duoc gui tu intermediateOrderInformation
        String sellerReceivedTrueMoney = request.getParameter("sellerReceivedTrueMoney");//duoc gui tu intermediateOrderInformation
        String productIdByOrderId = request.getParameter("productIdByOrderId");//lay bang cach trich xuat productId by orderId khi user click vao 1 modal "Chi tiet" bat ki
        String orderSttReport = request.getParameter("orderStt");
        System.out.println("orderStt report: "+orderSttReport);
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        int newStatus = 3;
        UserDAO userdao = new UserDAO();
        OrderDAO orderDao = new OrderDAO();
        ProductDAO pddao = new ProductDAO();
        TransactionDAO tsDao = new TransactionDAO();

        //Cập nhật khi người mua xác nhận ngay sau khi xem đơn hàng ở trang IntermediateOrderInformation.jsp
        if (productId != null && sellerReceivedTrueMoney != null) {
            //Xác nhận đơn hàng trực tiếp từ trang intermediateInformation
            double sellerTrueMoneyDouble = Double.parseDouble(sellerReceivedTrueMoney);

            Order o = orderDao.getOrderByProductId(productId);

            Product product = getProductCreateByName(pddao.getProductInformation(productId));
            String sellerUserName = product.getUser().getUserName();

            orderDao.updateOrderStatusByID(o.getId(), newStatus);

            //--thực hiện phần cộng tiền cho người bán sau khi người mua xác nhận đơn hàng thành công 
            User sellerInfor = userdao.getUserByUsername(sellerUserName);
            double accountBalanceSeller = sellerInfor.getWallet();
            userdao.updateUserWallet(sellerUserName, (accountBalanceSeller + sellerTrueMoneyDouble));

            //chèn bản ghi vào lịch sử giao dịch khi người bán đã được cộng tiền
            tsDao.insertNewTransactionAfterOrderSuccess(sellerInfor, o.getId(), sellerTrueMoneyDouble);
        } else if (productIdByOrderId != null) {
            //Xác nhận ở trang MyPurchasedOrder   
            Order orderPendingByproductId = orderDao.getOrderByProductId(productIdByOrderId);
            double sellerTrueMoney = orderPendingByproductId.getSellerReceivedTrueMoney();

            Product product = getProductCreateByName(pddao.getProductInformation(productIdByOrderId));
            String sellerUserName = product.getUser().getUserName();

            //Nếu order này khớp với order có trong list pending, thì cập nhật và xoá order đó khỏi list pending sau khi đã hoàn tất
            OrderListPendingSingleton orderListPendingSingleton = OrderListPendingSingleton.getInstance();
            List<Order> listOrderPending = orderListPendingSingleton.getOrdersFromPendingList(account.getUserName());
            for (Order order : listOrderPending) {
                if (order.getId().equals(orderPendingByproductId.getId())) {
                    // Xác nhận là đơn hàng tương ứng mà user click vào để xác nhận - có nằm trong order list pending
                    //Cập nhật thông tin đơn hàng
                    orderDao.updateOrderStatusByID(orderPendingByproductId.getId(), newStatus);

                    //--thực hiện phần cộng tiền cho người bán sau khi người mua xác nhận đơn hàng thành công
                    User sellerInfor = userdao.getUserByUsername(sellerUserName);
                    double accountBalanceSeller = sellerInfor.getWallet();
                    userdao.updateUserWallet(sellerUserName, (accountBalanceSeller + sellerTrueMoney));

                    //chèn bản ghi vào lịch sử giao dịch khi người bán đã được cộng tiền
                    tsDao.insertNewTransactionAfterOrderSuccess(sellerInfor, orderPendingByproductId.getId(), sellerTrueMoney);
                    if(orderPendingByproductId.getStatus() == 3){ //Chỉ khi order này ở trạng thái "Hoàn tất" thì mới remove khỏi list pending
                        orderListPendingSingleton.removeOrderFromPendingList(account.getUserName(), orderPendingByproductId);
                    }
                    break;
                }
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
//        processRequest(request, response);
        PrintWriter out = response.getWriter();
        String orderId = request.getParameter("orderId");
        ProductDAO pddao = new ProductDAO();
        String pId = pddao.getProductIdbyOrderId(orderId);

        String pIdJSON = new Gson().toJson(pId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(pIdJSON);
    }

    private Product getProductCreateByName(Product product) {
        int userId = product.getCreatedBy();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserbyId(userId);
        product.setUser(user);
        return product;
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
