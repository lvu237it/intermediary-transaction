<%-- 
    Document   : header
    Created on : Jan 28, 2024, 4:10:58 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import = "utils.UserWalletMap" %>
<%@page import = "Model.User" %>
<%@page import = "java.util.HashMap" %>

<div class="header header-top d-flex bg-success">
    <div class="header-left">
        <div class="logo-home-page">
            <a id="logo-homepage" class="fas fa-balance-scale" href="homepage" style="text-decoration: none; color: aqua">ITS</a> 
        </div>
    </div>
    <div class="header-center">
        <h1 class="text-white">GIAO DỊCH TRUNG GIAN</h1>
    </div>

    <div class="header-right">
        <c:if test="${sessionScope.accountSession != null}">
            <%
                UserWalletMap userWalletMap = new UserWalletMap();
                User account = (User) session.getAttribute("account");
                HashMap<String, Double> walletMap = userWalletMap.getUserWalletMap(account);//chứa key-value là username và account balance
                double balance = walletMap.get(account.getUserName());//lấy ra số dư tương ứng của username có trong walletMap
            %>
            <div id="walletValue" class="wallet-info">
                <!--                        <span class="wallet-label">Số  dư:</span>-->
                <span class="wallet-amount"><b id="account-balance"><%=balance%></b><b>VNĐ</b></span> 
            </div>
            <div>
                <i style="font-size: 22px;" class="fa-regular fa-bell mx-3" onclick=""></i>
            </div>
            <div class="dropdown">
                <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <i id="iconuser" class="fa-solid fa-user"></i>
                </a>
                <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                    <li><a class="dropdown-item" href="profile">Hồ Sơ</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                </ul>
            </div>
        </c:if>          
        <c:if test="${sessionScope.accountSession == null}">
            <div class="login">
                <a class="btn btn-dark" href="login">Đăng nhập</a>
            </div>
        </c:if>
    </div>
</div>

<!--đã đăng nhập  - user / admin --> 
<c:if test="${sessionScope.account != null}">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let amountStr = document.getElementById('account-balance').innerHTML;
            console.log('your account balance: ' + amountStr);

            // Format số dư thành chuỗi có dấu phẩy ngăn cách hàng nghìn
            let formattedAmount = parseFloat(amountStr).toLocaleString('en');

            // Gán lại chuỗi đã định dạng vào phần tử HTML
            document.getElementById("account-balance").innerHTML = formattedAmount;
            console.log("Chuỗi số sau khi được format theo VNĐ: " + formattedAmount);
        });

    </script>
    <!--thằng 1 làm admin--> 
    <c:if test="${sessionScope.accountSession == 1}">
        <nav class="navbar navbar-expand-md navbar-dark bg-dark" aria-label="Fourth navbar example">
            <div class="container-fluid">

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarsExample04">
                    <ul class="navbar-nav me-auto mb-2 mb-md-0" style="margin: auto; gap: 0 35px">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="homepage">Trang chủ</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="PublicMarket.jsp">Chợ công khai</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">Giao Dịch</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="purchase">Đơn mua của tôi</a></li>
                                <li><a class="dropdown-item" href="MySellOrder.jsp">Đơn bán của tôi</a></li>

                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="TransactionManagement.jsp">Lịch sử giao dịch</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="userinformation">Quản lý người dùng</a>
                        </li> 

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">Thanh Toán</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="vnpay">Nạp Tiền</a></li>
                                <li><a class="dropdown-item" href="withdraw">Yêu Cầu Rút Tiền</a></li>

                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="withdraw-manage">Quản lý rút tiền</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="reports-manage">Quản lý khiếu nại</a>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>
    </c:if>

    <!--thằng 0 làm user-->
    <c:if test="${sessionScope.accountSession == 0}">
        <nav class="navbar navbar-expand-md navbar-dark bg-dark" aria-label="Fourth navbar example">
            <div class="container-fluid">

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarsExample04">
                    <ul class="navbar-nav me-auto mb-2 mb-md-0" style="margin: auto; gap: 0 35px">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="homepage">Trang chủ</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="PublicMarket.jsp">Chợ công khai</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">Giao Dịch</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="purchase">Đơn mua của tôi</a></li>
                                <li><a class="dropdown-item" href="MySellOrder.jsp">Đơn bán của tôi</a></li>

                            </ul>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="TransactionManagement.jsp">Lịch sử giao dịch</a>
                        </li>
                        <!--                            <li class="nav-item">
                                                        <a class="nav-link" href="UserManagement.jsp">Quản lý tài khoản</a>
                                                    </li> -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">Thanh Toán</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="vnpay">Nạp Tiền</a></li>
                                <li><a class="dropdown-item" href="withdraw">Yêu Cầu Rút Tiền</a></li>

                            </ul>
                        </li>

                    </ul>

                </div>
            </div>
        </nav>
    </c:if>
</c:if>

<!----chưa đăng nhập - guest ---->
<c:if test="${sessionScope.accountSession == null}">
    <nav class="navbar navbar-expand-md navbar-dark bg-dark" aria-label="Fourth navbar example">
        <div class="container-fluid">

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarsExample04">
                <ul class="navbar-nav me-auto mb-2 mb-md-0" style="margin: auto; gap: 0 35px">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="homepage">Trang chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="PublicMarket.jsp">Chợ công khai</a>
                    </li>
                </ul>

            </div>
        </div>
    </nav>
</c:if>