<%-- 
    Document   : IntermediateOrderInformation
    Created on : Feb 23, 2024, 11:40:53 PM
    Author     : User
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${sessionScope.accountSession == null}">
    <c:redirect url="login"/> <!-- Chuyển hướng đến trang login nếu session không tồn tại -->
</c:if>
<%@page import = "DAO.OrderDAO" %>
<%@page import = "DAO.CorrespondDAO" %>
<%@page import = "DAO.TransactionDAO" %>
<%@page import = "DAO.ProductDAO" %>
<%@page import = "DAO.UserDAO" %>
<%@page import = "Model.Order" %>
<%@page import = "Model.Product" %>
<%@page import = "Model.User" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thông tin đơn trung gian</title>
        <link rel="icon" href="./logo_icon_blabla/ITS_logo_4.jpg">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
        <!--<link rel="stylesheet" href="./style_CSS/publicMarket.css">-->
        <link rel="stylesheet" href="./style_CSS/homePage.css">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div id="title-page" class="d-flex mt-5 mx-5 mb-4 justify-content-center" style="margin: auto" >
                <h1>Thông tin đơn trung gian</h1>
            </div>
            <div class="container">
                <table class="table table-responsive table-bordered table-hover text-center" style="margin: auto" id="tableBody">

                </table>

                <!--hiển thị nút "Xác nhận đơn hàng" - confirm bạn có chắc chắn thông tin người bán cung cấp là hợp lệ?-->
                <div class="row justify-content-center mb-3 mt-3">
                    <div id="button-return" class="col  col-2 d-flex justify-content-center">
                        <button id="back-to-publicmarket" class="btn btn-secondary">Quay lại</button>
                    </div>
                    <div id="button-report" class="col col-2 d-flex justify-content-center">
                        <button id="report-order" class="btn" style="background-color: #ff3333; color: white">Khiếu nại</button>
                    </div>
                    <div id="button-confirm" class="col col-2 d-flex justify-content-center">
                        <button id="confirm-information-ok" class="btn btn-success" >Xác nhận</button>
                    </div>
                </div>
            </div>

            <!--Modal Xác nhận thông tin đơn hàng-->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Xác nhận thông tin đơn hàng</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div>Bạn xác nhận rằng thông tin bên bán cung cấp là đúng mô tả?</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="button" class="btn btn-success" id="confirmButton">Xác nhận</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal "Hoàn tất giao dịch" -->
            <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="successLabel">Hoàn tất giao dịch</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div>Giao dịch của bạn đã được hoàn tất. Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--Modal Xác nhận khiếu nại-->
            <div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="reportLabel">Khiếu nại thông tin đơn hàng</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div>Bạn cho rằng thông tin đơn hàng của bên bán cung cấp không chính xác?</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="button" class="btn btn-success" id="confirmButtonReport">Xác nhận</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal "Hoàn tất khiếu nại" -->
            <div class="modal fade" id="acceptedConfirmReport" tabindex="-1" aria-labelledby="acceptLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="acceptLabel">Khiếu nại thông tin đơn hàng</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div>Khiếu nại thành công và đang chờ người bán kiểm tra lại sản phẩm.</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
             <!--Modal "Đã xác nhận rồi không thể khiếu nại"-->
            <div class="modal fade" id="alreadyConfirmModal" tabindex="-1" aria-labelledby="c" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="alreadyLabel">Xác nhận thông tin đơn hàng</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div>Bạn đã xác nhận đơn hàng này trước đó và không thể khiếu nại.</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
            <script>
            <%
                String productId = request.getParameter("productId");
                String benChiuPhi = request.getParameter("benChiuPhi");
                String totalPriceBuyer = request.getParameter("totalPriceBuyer");
                String sellerReceivedTrueMoney = request.getParameter("sellerReceivedTrueMoney");
            %>

                document.addEventListener("DOMContentLoaded", function () {
                    sendAjaxRequest();//gửi thông tin tạo đơn trung gian
                });

                function sendAjaxRequest() {
                    $.ajax({
                        url: 'ordercreated',
                        type: 'GET',
                        data: {
                            productId: '<%=productId%>',
                            benChiuPhi: '<%=benChiuPhi%>',
                            totalPriceBuyer: '<%=totalPriceBuyer%>',
                            sellerReceivedTrueMoney: '<%=sellerReceivedTrueMoney%>'
                        },
                        success: function (response) {
                            console.log("ôk");
                            if (response.includes("productNotEnoughId") || response.includes("productProcessedId") || response.includes("myProduct")) {
                                console.log("AJAX request successful:", response);
                                $('#title-page').remove();
                                $('#back-to-publicmarket').remove();
                                $('#report-order').remove();
                                $('#confirm-information-ok').remove();
                                window.location.href = response;
                            } else {
                                console.log('Khong co loi gi');
                                let row = document.getElementById("tableBody");
                                // Thêm dữ liệu mới vào bảng
                                row.innerHTML = response;
                                console.log('respone not null');
                                // Kiểm tra nếu phản hồi khác null, hiển thị div confirm-information-ok
                            }
                        },
                        error: function () {
                            console.log("Error in Ajax request");
                        }
                    });
                }

                $(document).on("click", "#report-order", function () {
                    reportConfirm();
                });

                $(document).on("click", "#confirm-information-ok", function () {
                    confirmOrder();
                });

                $(document).on("click", "#back-to-publicmarket", function () {
                    window.location.href = "publicmarket";
                });
                function reportConfirm() {
                    $('#reportModal').modal('show');

                    $(document).on("click", "#confirmButtonReport", function () {
                            $('#reportModal').modal('hide');
                            $('#acceptedConfirmReport').modal('show');
                            $.ajax({
                                url: 'updateorderstatusreport',
                                type: 'GET',
                                data: {
                                    productId: '<%=productId%>',
                                    orderStt: "4"//Gửi yêu cầu ajax để cập nhật trạng thái thành đơn hàng đang được người mua khiếu nại
                                },
                                success: function (response) {
                                    console.log("Ajax successfully when sending request to update order status (4)!");
                                },
                                error: function () {
                                    console.log("Error in updating order status");
                                }
                            });
                            $('#button-confirm').remove();
                            $('#button-report').remove();
                        });
                    }

                function confirmOrder() {
                    $('#exampleModal').modal('show');

                    $(document).on("click", "#confirmButton", function () {
                        // Đóng modal xác nhận
                        $('#exampleModal').modal('hide');

                        $.ajax({
                            url: 'updateorderstatusnsellerwallet',
                            type: 'GET',
                            data: {
                                productId: '<%=productId%>',
                                sellerReceivedTrueMoney: <%=sellerReceivedTrueMoney%>
                            },
                            success: function (response) {
                                console.log("Ajax successfully!");
                            },
                            error: function () {
                                console.log("Error in updating order status");
                            }
                        });

                        // Hiển thị modal "Hoàn tất giao dịch"
                        $('#successModal').modal('show');
                        $('#button-confirm').remove();
                        $('#button-report').remove();
                    });
                }
        </script>
    </body>
</html>
