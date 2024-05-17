/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderDAO;
import DAO.ProductDAO;
import DAO.ReportDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Model.Order;
import Model.OrderListPendingSingleton;
import Model.Product;
import Model.Transaction;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import utils.UserTokenMap;
import utils.UserWalletMap;

/**
 *
 * @author User
 */
public class UpdateOrderStatusReport extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrderStatusReport</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrderStatusReport at " + request.getContextPath() + "</h1>");
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
        ReportDAO rpdao = new ReportDAO();
        ProductDAO pddao = new ProductDAO();
        UserDAO userdao = new UserDAO();
        OrderDAO orderDao = new OrderDAO();
        TransactionDAO tsdao = new TransactionDAO();
        HttpSession session = request.getSession();
        String productId = request.getParameter("productId");//duoc gui tu intermediateOrderInformation.jsp
        String orderSttReport = request.getParameter("orderStt");
        String productIdByOrderId = request.getParameter("productIdByOrderId");//lấy từ MyPurchasedOrder.jsp
        Order order = orderDao.getOrderByProductId(productId);

        if (orderSttReport.equals("4")) { //Bên mua khiếu nại
            System.out.println("orderStt report: " + orderSttReport);
            int newStatus = 4;
            String buyerName = pddao.getUserByProductId(productId);

            //Thêm order vào listPending của người bán (trường hợp người bán có thể có nhiều đơn hàng đang cần giải quyết khiếu nại)
            //Ví dụ: người bán huỷ nhiều đơn cùng lúc => Trả lại đầy đủ số tiền cho người mua
            Product product = getProductCreateByName(pddao.getProductInformation(productId));
            System.out.println("product after 2 time check: " + product);//ok
            String sellerUserName = product.getUser().getUserName();
            System.out.println("sellername: " + sellerUserName);//ok

            OrderListPendingSingleton orderListPendingSingleton = OrderListPendingSingleton.getInstance();
            //Đưa các đơn hàng người mua khiếu nại, vào list pending của người bán
            orderListPendingSingleton.addOrderToPendingList(sellerUserName, order);

            orderDao.updateOrderStatusByID(order.getId(), newStatus);
        } else if (orderSttReport.equals("6")) {//Huỷ đơn - bên bán xác nhận thông tin sai - hoàn tiền cho người mua
            int newStatus = 6;
//            Product product = pddao.getProductInformation(productId);
            Product p = getProductCreateByName(pddao.getProductInformation(productId));
            System.out.println("product after 2 time check: " + p);//ok
            String sellerUserName = p.getUser().getUserName();
            System.out.println("sellername: " + sellerUserName);//ok

            //Lấy ra listPending của người bán
            OrderListPendingSingleton orderListPendingSingleton = OrderListPendingSingleton.getInstance();
            List<Order> listOrderPendingSeller = orderListPendingSingleton.getOrdersFromPendingList(sellerUserName);//list pending của người bán
            //Lấy ra thông tin order (orderId) tương ứng mà người mua đang click vào để cập nhật trạng thái (huỷ đơn hàng)
            Order orderCheckCancel = orderDao.getOrderByProductId(productId);

            Iterator<Order> iterator = listOrderPendingSeller.iterator();
            while (iterator.hasNext()) {
                //Trong list của seller có thể có nhiều đơn pending
                Order orderPendingCancel = iterator.next();
                //Huỷ đơn

                if (orderPendingCancel.getId() == null ? orderCheckCancel.getId() == null : orderPendingCancel.getId().equals(orderCheckCancel.getId())) {
                    orderDao.updateOrderStatusByID(orderPendingCancel.getId(), newStatus);

                    //Hoàn tiền cho người mua
                    String pId = pddao.getProductIdbyOrderId(orderPendingCancel.getId());
                    String buyerName = pddao.getUserByProductId(pId);
                    User buyer = userdao.getUserByUsername(buyerName);

                    double balance = userdao.getUserWalletByUsername(buyerName);
                    System.out.println("số dư hiện tại của bên mua: " + balance);
                    Product product = pddao.getProductInformation(pId);
                    double newBalance = product.getPrice() + balance;
                    System.out.println("số tiền sản phẩm: " + order.getProductprice());
                    System.out.println("số dư sau khi được hoàn tiền mua sản phẩm: " + newBalance);
                    userdao.updateUserWallet(buyerName, newBalance);

                    tsdao.insertTransactionAfterCancelBySeller(product.getPrice(), order.getId(), buyer);

                    //Xoá khỏi list pending của người mua lan nguoi ban khi đơn hàng đã bị huỷ
                    iterator.remove();
                }
            }

        } else if (orderSttReport.equals("5")) {//Bên bán xác nhận đơn hàng đúng - yêu cầu bên mua kiểm tra lại
            int newStatus = 5;

            orderDao.updateOrderStatusByID(order.getId(), newStatus);
        } else if (orderSttReport.equals("3")) {
            int newStatus = 3;
            String buyerName = pddao.getUserByProductId(productIdByOrderId);
            System.out.println("buyerName: " + buyerName); //ok
            System.out.println("ProductId after 2 time check: " + productIdByOrderId);//ok
            //Xác nhận ở trang MyPurchasedOrder   
            Order orderPendingByproductId = orderDao.getOrderByProductId(productIdByOrderId);
            System.out.println("Order by productId: " + orderPendingByproductId);//ok
            double sellerTrueMoney = orderPendingByproductId.getSellerReceivedTrueMoney();//ok

            Product product = getProductCreateByName(pddao.getProductInformation(productIdByOrderId));
            System.out.println("product after 2 time check: " + product);//ok
            String sellerUserName = product.getUser().getUserName();
            System.out.println("sellername: " + sellerUserName);//ok
            //Nếu order này khớp với order có trong list pending, thì cập nhật và xoá order đó khỏi list pending sau khi đã hoàn tất
            OrderListPendingSingleton orderListPendingSingleton = OrderListPendingSingleton.getInstance();
            List<Order> listOrderPending = orderListPendingSingleton.getOrdersFromPendingList(buyerName);//list pending của người mua
            System.out.println("orderlistpending:");
            for (Order order1 : listOrderPending) {
                System.out.println(order1);
            }
            for (Order o : listOrderPending) {
                if (o.getId().equals(orderPendingByproductId.getId())) {
                    // Xác nhận là đơn hàng tương ứng mà user click vào để xác nhận - có nằm trong order list pending
                    //Cập nhật thông tin đơn hàng
                    orderDao.updateOrderStatusByID(orderPendingByproductId.getId(), newStatus);

                    //--thực hiện phần cộng tiền cho người bán sau khi người mua xác nhận đơn hàng thành công
                    User sellerInfor = userdao.getUserByUsername(sellerUserName);
                    double accountBalanceSeller = sellerInfor.getWallet();
                    userdao.updateUserWallet(sellerUserName, (accountBalanceSeller + sellerTrueMoney));

                    //chèn bản ghi vào lịch sử giao dịch khi người bán đã được cộng tiền
                    tsdao.insertNewTransactionAfterOrderSuccess(sellerInfor, orderPendingByproductId.getId(), sellerTrueMoney);
//                    if(orderPendingByproductId.getStatus() == 3){ //Chỉ khi order này ở trạng thái "Hoàn tất" thì mới remove khỏi list pending
//                        orderListPendingSingleton.removeOrderFromPendingList(buyerName, o);
//                    }
                    break;
                }
            }
        } else if (orderSttReport.equals("7")) {
            int newStatus = 7;
            //lấy ra token của người đang đăng nhập
            String jwtLoggedInUser = (String) session.getAttribute("jwt");
            UserTokenMap mapToken = UserTokenMap.getInstance();

            String username = mapToken.getUsernameByToken(jwtLoggedInUser);
            //không dựa vào attribute "account" ở session mà dựa vào token của người dùng để kiểm tra đó là người dùng nào
            User userAccountBuyer = userdao.getUserByUsername(username);
            System.out.println(userAccountBuyer);
//            
//            User userAccountBuyer = (User) session.getAttribute("account");
            System.out.println("orderStt report: " + orderSttReport);
            
            //Thêm order vào listPending của người bán (trường hợp người bán có thể có nhiều đơn hàng đang cần giải quyết khiếu nại)
            //Ví dụ: người bán huỷ nhiều đơn cùng lúc => Trả lại đầy đủ số tiền cho người mua
            Product product = getProductCreateByName(pddao.getProductInformation(productId));
            System.out.println("product after 2 time check: " + product);//ok
            String sellerUserName = product.getUser().getUserName();
            System.out.println("sellername: " + sellerUserName);//ok

//            OrderListPendingSingleton orderListPendingSingleton = OrderListPendingSingleton.getInstance();
//            //Đưa các đơn hàng người mua khiếu nại, vào list pending của người bán
//            orderListPendingSingleton.addOrderToPendingList(sellerUserName, order);

            //lấy order giao dịch giữa người bán và người mua 
            Order orderForReport = orderDao.getOrderByUserForReport(productId);
            //Cập nhật trạng thái order thành "Yêu cầu khiếu nại tới admin"
            orderDao.updateOrderStatusByID(orderForReport.getId(), newStatus);
            
            System.out.println("nguoi tao khieu nai: " + userAccountBuyer.getUserName());
            System.out.println("ben ban: " + product.getCreatedBy());
//            System.out.println("ben mua: " + product.getOrderCreatedBy());
            String orderCreatedBy = orderDao.getOrderCreatedByWithOrderId(orderForReport.getId());
            System.out.println("ben mua: " + orderCreatedBy);
            rpdao.InsertNewReport(String.valueOf(userAccountBuyer.getUserId()), String.valueOf(orderForReport.getId())
                    , orderCreatedBy//Lấy ra người mua (người tạo order)
                    , String.valueOf(product.getCreatedBy()), "1" , "Đơn khiếu nại từ người dùng " + userAccountBuyer.getUserName() + "");
        } 
    }

    private Product getProductCreateByName(Product product) {
        int userId = product.getCreatedBy();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserbyId(userId);
        product.setUser(user);
        return product;
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
        processRequest(request, response);

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
