/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CorrespondDAO;
import DAO.OrderDAO;
import DAO.TransactionDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Model.Order;
import Model.OrderListPendingSingleton;
import Model.OrderQueueSingletonBuyer;
import Model.OrderQueueSingletonSeller;
import Model.Product;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Queue;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import utils.UserTokenMap;

/**
 *
 * @author User
 */
public class OrderCreatedController extends HttpServlet {

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
            out.println("<title>Servlet OrderCreated</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderCreated at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserDAO userdao = new UserDAO();
        OrderDAO orderDao = new OrderDAO();
        TransactionDAO tsDao = new TransactionDAO();

        String productId = request.getParameter("productId");
        String benChiuPhi = request.getParameter("benChiuPhi");
        String totalPriceBuyer = request.getParameter("totalPriceBuyer");
        String sellerReceivedTrueMoney = request.getParameter("sellerReceivedTrueMoney");
        double totalNeededPriceBuyer = Double.parseDouble(totalPriceBuyer);
        double sellerTrueMoney = Double.parseDouble(sellerReceivedTrueMoney);
        User account = (User) session.getAttribute("account");
        // ép kiểu productId từ string về int
        int id = Integer.parseInt(productId);
        //lấy ra token của người đang đăng nhập
        String jwtLoggedInUser = (String) session.getAttribute("jwt");
        UserTokenMap mapToken = UserTokenMap.getInstance();

        String usernameBuyer = mapToken.getUsernameByToken(jwtLoggedInUser);
        //không dựa vào attribute "account" ở session mà dựa vào token của người dùng để kiểm tra đó là người dùng nào
        User userAccountBuyer = userdao.getUserByUsername(usernameBuyer);
        if (userAccountBuyer.getUserId() == id) {
            request.getRequestDispatcher("ProductDetailPage.jsp?id=" + id).forward(request, response);
        }

        //--------------tạo đơn mua của tôi-------------------------
        ProductDAO pddao = new ProductDAO();
        Product product = pddao.getProductInformation(productId);//lấy thông tin sản phẩm

        Product p = getProductCreateByName(product);//dựa vào thông tin sản phẩm đã có, lấy thông tin user dựa trên trường product.createdBy
        String sellerUserName = p.getUser().getUserName();//lấy tên người bán
//        userdao.updateUserWallet(totalPriceBuyer, totalNeededPriceBuyer);
        User sellerInfor = userdao.getUserByUsername(sellerUserName);//lấy thông tin người bán dựa vào tên

        //Người mua hiện tại mua sản phẩm của chính mình
        if (product.getCreatedBy() == userAccountBuyer.getUserId()) {
            StringBuilder htmlBuilder = new StringBuilder();
            //trả về trang public market khi số dư không đủ (hiển thị modal và option chuyển hướng tới nạp tiền)
            System.out.println("Không thể mua sản phẩm của chính mình.");
//                        if (productFilter == "true") {
//                            htmlBuilder.append("PublicMarketFilter.jsp?notenoughpid=").append(product.getId());
//                        } else {
            htmlBuilder.append("PublicMarket.jsp?myProduct=true&pId=").append(product.getId());
//                        }
            out.println(htmlBuilder.toString());
        } else {
            //lấy dữ liệu wallet từ database // bỏ
            //lấy dựa vào token riêng
            double accountBalanceBuyer = userdao.getUserWalletByUsername(usernameBuyer);
            System.out.println("\n\n\nAccount balance buyer: " + accountBalanceBuyer);
            Queue q = new LinkedList();
            try {
                boolean isProductAlreadyProcessed = orderDao.checkIfProductProcessed(product.getId());
                if (!isProductAlreadyProcessed) {//nếu productId trong đơn hàng chưa từng được xử lý (chưa từng được mua)
                    //mặc định là ở trạng thái sẵn sàng giao dịch (1) (sau khi sản phẩm được đăng lên)
                    int status = 2; //Đã thanh toán, "Chờ xác nhận" từ phía người mua
                    orderDao.createNewBuyOrder(userAccountBuyer, product, totalNeededPriceBuyer, sellerTrueMoney, benChiuPhi, status);//sau khi ấn "Mua" => Tạo bản ghi mới vào order, correspond, transaction

                    Order recentlyOrder = orderDao.getRecentlyBuyOrder(product, totalNeededPriceBuyer);
                    q.add(recentlyOrder);
                    while (!q.isEmpty()) {
                        Order order = (Order) q.poll();//lấy order từ queue ra để xử lý lần lượt
                        if (accountBalanceBuyer >= order.getTotalPrice()) { //số dư người mua đủ để thanh toán
                            //Trường hợp link đơn hàng đã được tạo, và copy + paste vào session của người bán => Không cho phép người bán mua sản phẩm của chính mình

                            OrderListPendingSingleton orderListPendingSingleton = OrderListPendingSingleton.getInstance();
                            orderListPendingSingleton.addOrderToPendingList(usernameBuyer, order);//đưa đơn hàng người mua mới tạo, vào danh sách chờ xác nhận

                            List<Order> orderListPending = orderListPendingSingleton.getOrdersFromPendingList(usernameBuyer);
                            for (Order order1 : orderListPending) {
                                System.out.println("orderpending: " + order1);
                            }

                            //cập nhật trạng thái của sản phẩm: isPurchased - true (đã được mua và không hiển thị ở publicmarket), updateable - false (người bán không thể cập nhật sản phẩm)
                            pddao.updateProductAfterBought(productId);

                            //trừ tiền của accountBalance của người mua
                            //buộc phải dùng queue duy nhất để trừ dồn các phần tiền từ nhiều đơn mua của buyer khi họ mua 1 lúc nhiều đơn
                            //logic xử lý tương tự như cộng tiền cho seller
                            OrderQueueSingletonBuyer orderQueueSingletonBuyer = OrderQueueSingletonBuyer.getInstance();

                            // Lấy danh sách các đơn hàng từ queue
                            // Trừ tiền của accountBalance của người mua
                            double totalPriceForBuyer = order.getTotalPrice();
                            orderQueueSingletonBuyer.addOrder(usernameBuyer, order, totalPriceForBuyer);
                            System.out.println("Số tiền tổng mà người mua cần trả: " + totalPriceForBuyer + ", usernameBuyer: " + usernameBuyer);
//                        System.out.println("Số tiền tổng mà người mua cần trả: "+totalNeededPriceBuyer);
                            double amountToDeduct = orderQueueSingletonBuyer.getTotalPriceForBuyer(usernameBuyer);
                            System.out.println("Tổng số tiền khấu trừ: " + amountToDeduct + ", usernameBuyer: " + usernameBuyer);
                            double newWalletBalance = accountBalanceBuyer - amountToDeduct;
                            System.out.println("Số dư mới sau khi khấu trừ: " + newWalletBalance + ", usernameBuyer: " + usernameBuyer);
                            if (newWalletBalance < 0) {
                                // Reset the total price for the buyer in the OrderQueueSingletonBuyer
                                orderQueueSingletonBuyer.resetTotalPriceForBuyer(usernameBuyer);
                                //nếu số dư không đủ => xoá bản ghi mới thêm (ở các bảng order -> correspond -> transaction)
                                //xoá bản ghi transaction
                                TransactionDAO transactionDao = new TransactionDAO();
                                transactionDao.deleteNewestTransaction();

                                String orderId = order.getId();
                                //xoá bản ghi order
                                orderDao.deleteAnRecordOfOrderByOrderId(orderId);
                                //xoá bản ghi correspond
                                CorrespondDAO crpDao = new CorrespondDAO();
                                crpDao.deleteAnRecordOfCorrespondByOrderId(orderId);

                                StringBuilder htmlBuilder = new StringBuilder();
                                //trả về trang public market khi số dư không đủ (hiển thị modal và option chuyển hướng tới nạp tiền)
                                System.out.println("Số dư không đủ để thực hiện giao dịch");
//                        if (productFilter == "true") {
//                            htmlBuilder.append("PublicMarketFilter.jsp?notenoughpid=").append(product.getId());
//                        } else {
                                htmlBuilder.append("PublicMarket.jsp?productNotEnoughId=true&pId=").append(product.getId());
//                        }
                                out.println(htmlBuilder.toString());
                            } else {
                                userdao.updateUserWalletById(userAccountBuyer, newWalletBalance);
                                updateBalance(usernameBuyer, newWalletBalance);
                                System.out.println("Cập nhật số dư của người dùng thành công" + ", usernameBuyer: " + usernameBuyer);
                                // Reset the total price for the buyer in the OrderQueueSingletonBuyer
                                orderQueueSingletonBuyer.resetTotalPriceForBuyer(usernameBuyer);

                            }

                            //cập nhật lại total - biến "getTotalPriceWhenUserConfirmedForBuyer" về 0
//                        orderQueueSingletonBuyer.resetTotalPriceForBuyer(userAccountBuyer.getUserName());
                            //Đặt thời gian exprise là 3 ngày sau khi tạo đơn
                            //Nếu user không xác nhận đơn thành công (status của đơn mua vẫn là trạng thái 2: bên mua kiểm tra đơn hàng
                            // hoặc trạng thái vẫn là 5: bên bán xác định đơn hàng đúng)
                            // thì tự động chuyển thành trạng thái 3: hoàn tất giao dịch
//                            String orderStatus = (String) session.getAttribute("orderStatus");
                            int statusRecently = order.getStatus();
                            OrderQueueSingletonSeller orderQueueSingletonSeller2 = OrderQueueSingletonSeller.getInstance();
//                        orderQueueSingleton.getInstancetotalPriceWhenUserConfirmedForSellerInstance();
                            if (2 == statusRecently) {//và chưa cập nhật trạng thái "hoàn tất" trong vòng 1 ngày kể từ thời điểm tạo đơn hàng
                                System.out.println("Kich hoat xu ly cong viec duoc schedule sau 1 phut: status 2");

                                ScheduledExecutorService schedulerForConfirmStatus = Executors.newScheduledThreadPool(1);

                                // Lấy thời điểm hiện tại
                                Instant nowFromCreated = Instant.now();

                                // Tính thời điểm kích hoạt công việc sau 1 phút
                                Instant activationTimeFromCreated = nowFromCreated.plus(3600, ChronoUnit.SECONDS);

                                // Tính khoảng thời gian giữa hiện tại và thời điểm kích hoạt
                                long initialDelayTime = Duration.between(nowFromCreated, activationTimeFromCreated).toMillis();

                                // Tạo và kích hoạt công việc sau khoảng thời gian cố định
                                schedulerForConfirmStatus.schedule(() -> {
                                    try {
                                        //Lấy dữ liệu status mới nhất của order sau khoảng thời gian đã định
                                         //Nếu sau khoảng thời gian đếm ngược mà status vẫn bằng 2 thì mới thực hiện
                                        String orSttNewest = orderDao.getOrderStatusById(order.getId());
                                        
                                        if (orSttNewest == "2") {
                                            orderDao.updateOrderStatusWithoutConfirm(order.getId());

                                            // Lấy danh sách các đơn hàng từ queue của người bán cụ thể
                                            Queue<Order> orderQueueSeller = orderQueueSingletonSeller2.getSellerOrderQueue(sellerUserName);
                                            orderQueueSeller.add(order);

                                            // Duyệt qua queue của các order chưa được xác nhận để cộng dồn số tiền
                                            while (!orderQueueSeller.isEmpty()) {
                                                Order orderUnconfirmed = orderQueueSeller.poll();
                                                double truemoneysellergot = orderUnconfirmed.getSellerReceivedTrueMoney();
                                                // Cộng dồn số tiền cho người bán cụ thể
                                                orderQueueSingletonSeller2.setSellerTotalPrice(sellerUserName, truemoneysellergot);
                                            }

                                            // Thực hiện phần cộng tiền cho người bán chỉ sau khi người mua xác nhận đơn hàng thành công - status: 3
                                            double accountBalanceSeller = sellerInfor.getWallet();
                                            double newWalletSeller = accountBalanceSeller + orderQueueSingletonSeller2.getSellerTotalPrice(sellerUserName);
//                                    userdao.updateUserWallet(sellerUserName, (accountBalanceSeller + orderQueueSingletonSeller2.getSellerTotalPrice(sellerUserName)));
                                            updateBalance(sellerUserName, newWalletSeller);
                                            System.out.println("SellerWallet before: " + accountBalanceSeller + ", sellerInfor: " + sellerUserName);
                                            System.out.println("SellerTotalGot: " + (orderQueueSingletonSeller2.getSellerTotalPrice(sellerUserName)) + ", sellerInfor: " + sellerUserName);
                                            System.out.println("SellerFinal: " + (newWalletSeller) + ", sellerInfor: " + sellerUserName);

                                            //chèn bản ghi vào lịch sử giao dịch khi người bán đã được cộng tiền
                                            tsDao.insertNewTransactionAfterOrderSuccess(sellerInfor, order.getId(), sellerTrueMoney);

                                            // Đóng scheduler sau khi công việc được thực hiện
                                            schedulerForConfirmStatus.shutdown();
                                            //Xoá đơn hàng khỏi danh sách chờ xác nhận nếu đã xong
                                            orderListPendingSingleton.removeOrderFromPendingList(usernameBuyer, order);
                                            System.out.println("cong viec da duoc thuc hien (1 minute later): status 2");
                                        } else {
                                            System.out.println("Cong viec da hoan thanh truoc do va khong can cap nhat lai trang thai 2");
                                        }

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }, initialDelayTime, TimeUnit.MILLISECONDS);
                                //cập nhật lại total - biến "getTotalPriceWhenUserConfirmedForSeller" về 0
                                orderQueueSingletonSeller2.resetTotalPriceWhenUserConfirmedForSeller(sellerUserName);
                            } else if (statusRecently == 5) {//Trạng thái liên quan tới xử lý khiếu nại 
                                // Nếu người mua không phản hồi lại, sau khi người bán xác nhận người mua chơi xấu
                                //Thì sẽ tự động xác nhận đơn hàng của người mua và thanh toán tiền cho người bán
                                ScheduledExecutorService schedulerForConfirmStatus = Executors.newScheduledThreadPool(1);
                                System.out.println("Kich hoat xu ly cong viec duoc schedule sau 1 phut: status 5");
                                // Lấy thời điểm hiện tại
                                Instant nowFromCreated = Instant.now();

                                // Tính thời điểm kích hoạt công việc sau 1 phút
                                Instant activationTimeFromCreated = nowFromCreated.plus(3600, ChronoUnit.SECONDS);

                                // Tính khoảng thời gian giữa hiện tại và thời điểm kích hoạt
                                long initialDelayTime = Duration.between(nowFromCreated, activationTimeFromCreated).toMillis();
                                // Tạo và kích hoạt công việc sau khoảng thời gian cố định
                                schedulerForConfirmStatus.schedule(() -> {
                                    try {
                                        //Lấy dữ liệu status mới nhất của order sau khoảng thời gian đã định
                                         //Nếu sau khoảng thời gian đếm ngược mà status vẫn bằng 2 thì mới thực hiện
                                        String orSttNewest = orderDao.getOrderStatusById(order.getId());
                                        
                                        if (orSttNewest == "5") {
                                            //Nếu sau khoảng thời gian đếm ngược mà status vẫn bằng 5 thì mới thực hiện    
                                            orderDao.updateOrderStatusWithoutConfirm(order.getId());

                                            // Lấy danh sách các đơn hàng từ queue của người bán cụ thể
                                            Queue<Order> orderQueueSeller = orderQueueSingletonSeller2.getSellerOrderQueue(sellerUserName);
                                            orderQueueSeller.add(order);

                                            // Duyệt qua queue của các order chưa được xác nhận để cộng dồn số tiền
                                            while (!orderQueueSeller.isEmpty()) {
                                                Order orderUnconfirmed = orderQueueSeller.poll();
                                                double truemoneysellergot = orderUnconfirmed.getSellerReceivedTrueMoney();
                                                // Cộng dồn số tiền cho người bán cụ thể
                                                orderQueueSingletonSeller2.setSellerTotalPrice(sellerUserName, truemoneysellergot);
                                            }

                                            // Thực hiện phần cộng tiền cho người bán chỉ sau khi người mua xác nhận đơn hàng thành công - status: 3
                                            double accountBalanceSeller = sellerInfor.getWallet();
                                            double newWalletSeller = accountBalanceSeller + orderQueueSingletonSeller2.getSellerTotalPrice(sellerUserName);
//                                    userdao.updateUserWallet(sellerUserName, (accountBalanceSeller + orderQueueSingletonSeller2.getSellerTotalPrice(sellerUserName)));
                                            updateBalance(sellerUserName, newWalletSeller);
                                            System.out.println("SellerWallet before: " + accountBalanceSeller + ", sellerInfor: " + sellerUserName);
                                            System.out.println("SellerTotalGot: " + (orderQueueSingletonSeller2.getSellerTotalPrice(sellerUserName)) + ", sellerInfor: " + sellerUserName);
                                            System.out.println("SellerFinal: " + (newWalletSeller) + ", sellerInfor: " + sellerUserName);

                                            //chèn bản ghi vào lịch sử giao dịch khi người bán đã được cộng tiền
                                            tsDao.insertNewTransactionAfterOrderSuccess(sellerInfor, order.getId(), sellerTrueMoney);

                                            // Đóng scheduler sau khi công việc được thực hiện
                                            schedulerForConfirmStatus.shutdown();
                                            //Xoá đơn hàng khỏi danh sách chờ xác nhận nếu đã xong
                                            orderListPendingSingleton.removeOrderFromPendingList(usernameBuyer, order);
                                            System.out.println("cong viec da duoc thuc hien (1 minute later): status 5");
                                        }else{
                                            System.out.println("Cong viec da hoan thanh truoc do va khong can cap nhat lai trang thai 5");
                                        }

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }, initialDelayTime, TimeUnit.MILLISECONDS);
                                //cập nhật lại total - biến "getTotalPriceWhenUserConfirmedForSeller" về 0
                                orderQueueSingletonSeller2.resetTotalPriceWhenUserConfirmedForSeller(sellerUserName);
                            }

//                        System.out.println("Số dư khả dụng");
                            StringBuilder htmlBuilder = new StringBuilder();
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Mã trung gian</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getId()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Chủ đề trung gian</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getProductname()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Nội dung ẩn</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getHiddenField()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Loại sản phẩm</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getType()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Trạng thái</td>");
                            if (recentlyOrder.getStatus() == 1) {
                                htmlBuilder.append("<td>Sẵn sàng giao dịch</td>");
                            } else if (recentlyOrder.getStatus() == 2) {
                                htmlBuilder.append("<td>Chờ xác nhận từ người mua</td>");
                            }
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Người bán</td>");

                            User u = userdao.getUserbyId(recentlyOrder.getProductCreatedBy());
                            htmlBuilder.append("<td>").append(u.getUserName()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Phương thức liên hệ</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getProductcontact()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Phạm vi</td>");
                            if (recentlyOrder.getIsPrivate() == 0) {
                                htmlBuilder.append("<td>Công khai</td>");
                            } else {
                                htmlBuilder.append("<td>Riêng tư</td>");
                            }
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Giá tiền</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getProductprice()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Phí trung gian</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getTransactionfee()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Bên chịu phí trung gian</td>");
                            if (recentlyOrder.isFeePayer() == false) {
                                htmlBuilder.append("<td>Bên bán</td>");
                            } else {
                                htmlBuilder.append("<td>Bên mua</td>");
                            }
                            htmlBuilder.append("</tr>");

                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Tổng tiền bên mua đã thanh toán</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getTotalPrice()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Tổng tiền bên bán thực nhận</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getSellerReceivedTrueMoney()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Thời gian tạo</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getCreatedAt()).append("</td>");
                            htmlBuilder.append("</tr>");
                            htmlBuilder.append("<tr>");
                            htmlBuilder.append("<td>Cập nhật cuối</td>");
                            htmlBuilder.append("<td>").append(recentlyOrder.getUpdatedAt()).append("</td>");
                            htmlBuilder.append("</tr>");
                            out.println(htmlBuilder.toString());
                        } else {//nếu số dư không đủ => xoá bản ghi mới thêm (ở các bảng order -> correspond -> transaction)
                            //xoá bản ghi transaction
                            TransactionDAO transactionDao = new TransactionDAO();
                            transactionDao.deleteNewestTransaction();

                            String orderId = order.getId();
                            //xoá bản ghi order
                            orderDao.deleteAnRecordOfOrderByOrderId(orderId);
                            //xoá bản ghi correspond
                            CorrespondDAO crpDao = new CorrespondDAO();
                            crpDao.deleteAnRecordOfCorrespondByOrderId(orderId);

                            StringBuilder htmlBuilder = new StringBuilder();
                            //trả về trang public market khi số dư không đủ (hiển thị modal và option chuyển hướng tới nạp tiền)
//                        System.out.println("Số dư không đủ để thực hiện giao dịch");
//                        if (productFilter == "true") {
//                            htmlBuilder.append("PublicMarketFilter.jsp?notenoughpid=").append(product.getId());
//                        } else {
                            htmlBuilder.append("PublicMarket.jsp?productNotEnoughId=true&pId=").append(product.getId());
//                        }
                            out.println(htmlBuilder.toString());
                        }
                    }
                } else {
                    StringBuilder htmlBuilder = new StringBuilder();
                    htmlBuilder.append("PublicMarket.jsp?productProcessedId=true&pId=").append(product.getId());
//                }
                    out.println(htmlBuilder.toString());
                }
            } catch (SQLException ex) {
                System.out.println(ex);
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
        processRequest(request, response);
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

    public synchronized void updateBalance(String username, double newbalance) {
        UserDAO dao = new UserDAO();
        dao.updateUserWallet(username, newbalance);
    }
}
