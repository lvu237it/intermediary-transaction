<%-- 
    Document   : PublicMarket.jsp
    Created on : Feb 5, 2024, 4:02:55 PM
    Author     : User
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import = "DAO.OrderDAO" %>
<%@page import = "DAO.CorrespondDAO" %>
<%@page import = "DAO.TransactionDAO" %>
<%@page import = "DAO.ProductDAO" %>
<%@page import = "DAO.UserDAO" %>
<%@page import = "Model.Order" %>
<%@page import = "Model.Product" %>
<%@page import = "Model.User" %>
<%@page import = "java.util.List" %>
<%@page import = "java.util.ArrayList" %>
<%@page import = "java.sql.SQLException" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chợ công khai</title>
        <link rel="icon" href="./logo_icon_blabla/ITS_logo_4.jpg">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
        <link rel="stylesheet" href="./style_CSS/publicMarket.css">
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <link rel="stylesheet" href="Pagination.js/pagination.css">

        <style>
            .select-input{
                width: 10rem;
            }
            .gap-select-search{
                gap: 0.5rem;
            }
            .font-size-icon{
                font-size: 1.5rem;
            }
            .button-fake{
                cursor: pointer;
            }
            .position-relative{
                margin-bottom: 20px
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

            <div class="container mt-5">
                <div class="container d-flex justify-content-around justify-content-center mt-4 mb-4">
                    <div class="row" style="gap: 1rem 1rem">
                        <form class="col" onsubmit=" search();  return false">
                            <div class="d-flex justify-content-center align-items-center gap-select-search" style="margin: auto">
                                <input id="nameProduct" class="select-input form-control" name="nameProduct" placeholder="Tên sản phẩm" type="text">
                                <select id="optionProductType" name="optionProductType" class="select-input form-control">
                                    <option value="">-- Loại sản phẩm --</option>
                                    <option value="Tài khoản">Tài khoản</option>
                                    <option value="Tài liệu">Tài liệu</option>
                                    <option value="Phần mềm">Phần mềm</option>
                                    <option value="Khác">Khác</option>
                                </select>
                                <input id="priceProductFrom" class="select-input form-control" style="width: 6.3rem;" placeholder="Giá SP từ" pattern="^[0-9]+(\.[0-9]+)?$" name="priceProductFrom">
                                <input id="priceProductTo" class="select-input form-control" style="width: 6.3rem;" placeholder="Giá SP đến" pattern="^[0-9]+(\.[0-9]+)?$" name="priceProductTo">
                                <button class="btn btn-outline-success">Tìm</button>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="col d-flex justify-content-center align-items-center gap-select-search" style="gap: 1rem 1rem">
                            <button style="height: 100%" class="btn btn-outline-success" onclick="clearFilter();">Bỏ lọc</button>
                        </div>

                    </div>
                </div>

                <!--modal hiển thị khi kiểm tra sản phẩm đã được mua hoặc không đủ số dư để mua sản phẩm-->
                <!-- Modal không đủ số dư -->
                <div class="modal fade" id="notEnoughBalanceModal" tabindex="-1" aria-labelledby="notEnoughBalanceModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="notEnoughBalanceModalLabel">Thông báo</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>Không đủ số dư để mua sản phẩm.</p>
                                <p>Vui lòng nạp tiền vào tài khoản của bạn.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                <button id="todeposit" type="button" class="btn btn-primary" onclick="toDeposit()">Nạp tiền</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal sản phẩm đã được mua -->
                <div class="modal fade" id="productAlreadyProcessedModal" tabindex="-1" aria-labelledby="productAlreadyProcessedModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="productAlreadyProcessedModalLabel">Thông báo</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>Sản phẩm đã được mua và không còn tồn tại.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- Modal sản phẩm của bạn - không thể mua -->
                <div class="modal fade" id="myProductCantBuydModal" tabindex="-1" aria-labelledby="myProductCantBuydModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="myProductCantBuydModalLabel">Thông báo</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>Không thể mua sản phẩm của chính mình.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row" id="data-container"> 
                    <!--hiển thị phần dữ liệu (product) được phản hồi từ servlet sau khi gửi yêu cầu ajax thành công tới servlet từ jsp-->

                </div>
                <h5 id="notification" class="text-center mt-3 mb-3"></h5>
            </div>
            <!--            <nav class="mt-4" id="nav-pagination" style="display: none">
                            mặc định là để display none cho pagination vì lúc này chưa hiển thị bản ghi
                            <ul class="pagination justify-content-center">
                                 Nút "Previous" 
                                <li class="page-item">
                                    <a id="preButton" class="page-link button-fake" onclick="updateDirection('previous')">Previous</a>
                                </li>
                                <li class="page-item"><a id="currentPage" class="page-link button-fake">1</a></li>
                                 Nút "Next" 
                                <li class="page-item">
                                    <a id="nextButton" class="page-link button-fake" onclick="updateDirection('next')">Next</a>
                                </li>
                            </ul>
                        </nav>-->

            <div class="d-flex">
                <div class="mt-3 mx-auto" id="pagination-container"></div>
            </div>
            <div id="openProductDetail" class="modal" tabindex="-1">
                <div class="modal-dialog modal-dialog-scrollable modal-lg">
                    <div class="modal-content">

                        <div class="modal-body" id="productDetail">
                            <!--                        <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                        <button type="button" class="btn btn-success" id="">Mua</button>
                                                    </div>-->
                        </div>
                    </div>
                </div>
            </div>
            <script src="Pagination.js/pagination.js"></script>
            <script>
            <% 
            String productNotEnoughId = request.getParameter("productNotEnoughId");//product mua nhưng không đủ số dư
            String productProcessedId = request.getParameter("productProcessedId");//product đã được mua
            String myProduct = request.getParameter("myProduct");//sản phẩm của chính người bán // ko được mua
            
            OrderDAO orderDao = new OrderDAO();
            User accountSession = (User) session.getAttribute("account");
            %>
                
                                try {
                                    let productNotEnoughId = '<%=productNotEnoughId%>';
                                    let productProcessedId = '<%=productProcessedId%>';
                                    let myProduct = '<%=myProduct%>';
                                    let currentURL = window.location.href;

                                    // kiểm tra có phải đường dẫn publicmarket không
                                    if (currentURL.indexOf("publicmarket") === -1) {//ko phải /publicmarket

                                        //ko đủ tiền
                                        if (productNotEnoughId === "true") {
                                            console.log("So du khong du de thuc hien giao dich - public market");
                                            var j = jQuery.noConflict();
                                            j(document).ready(function ($) {
                                                $('#notEnoughBalanceModal').modal('show');
                                            });
                                        }
                                        // đơn hàng đã được xử lý // ko được phép mua lại
                                        else if (productProcessedId === "true") {
                                            console.log("San pham nay da duoc mua va khong con ton tai - public market");
                                            var j = jQuery.noConflict();
                                            j(document).ready(function ($) {
                                                $('#productAlreadyProcessedModal').modal('show');
                                            });
                                        //sản phẩm của chính mình // ko được mua
                                        }else if(myProduct === "true"){
                                            console.log("khong the mua san pham cua chinh minh - public market");
                                            var j = jQuery.noConflict();
                                            j(document).ready(function ($) {
                                                $('#myProductCantBuydModal').modal('show');
                                            });
                                            }
                                    }
                                } catch (ex) {
                                    console.log(ex);
                                }


            <% String productType = request.getParameter("productType");%>
                                    let productType = '<%=productType%>';
                                    document.addEventListener("DOMContentLoaded", function () {
                                        var maxDescriptionLength = 100;
                                        var descriptions = document.querySelectorAll('.product-description');
                                        //hiển thị độ dài kí tự tối đa cho phần mô tả sản phẩm: 100 kí tự
                                        descriptions.forEach(function (description) {
                                            var text = description.innerText;
                                            if (text.length > maxDescriptionLength) {
                                                var shortenedText = text.substring(0, maxDescriptionLength - 3) + '...';
                                                description.innerHTML = shortenedText;
                                            }
                                        });
                                        search();
                                    });


                                    function search() {
                                        let nameProduct = document.getElementById("nameProduct").value;
                                        let optionProductType = document.getElementById("optionProductType").value;
                                        let priceProductFrom = document.getElementById('priceProductFrom').value;
                                        let priceProductTo = document.getElementById('priceProductTo').value;
                                        jQuery.ajax({
                                            url: 'publicmarket',
                                            type: 'POST',
                                            data: {
                                                nameProduct: nameProduct,
                                                optionProductType: optionProductType,
                                                priceProductFrom: priceProductFrom,
                                                priceProductTo: priceProductTo
                                            },
                                            success: function (listProduct) {
                                                console.log('Khong co loi gi');
                                                // Xóa nội dung cũ trong bảng trước khi hiển thị dữ liệu mới
                                                initializePagination(listProduct);
                                            },
                                            error: function () {
                                                console.log("Error in Ajax request");
                                            }
                                        });
                                    }

                                    function initializePagination(data) {
                                        jQuery('#pagination-container').pagination({
                                            dataSource: data,
                                            pageSize: 8,
                                            callback: function (data, pagination) {
                                                jQuery('#data-container').empty();
                                                if (data.length === 0) {
                                                    jQuery('#data-container').html('<div class="text-center" role="alert">--- Không còn bản ghi phù hợp ---</div>');
                                                    return;
                                                }
                                                jQuery.each(data, function (index, product) {
                                                    let user = product.user;
                                                    let imageUrl = getImageUrlByType(product.type);
                                                    let tableHtml = '';
                                                    tableHtml += '<div class="col-md-3 mb-4">';
                                                    tableHtml += '<div class="card" style="height: 100%">';
                                                    tableHtml += '<img style="height: 65%" src="' + imageUrl + '" class="card-img-left img-fluid" alt="...">';
                                                    tableHtml += '<div class="card-body">';
                                                    tableHtml += '<h5 class="card-title">Tên sản phẩm:' + product.name + ' </h5>';
                                                    tableHtml += '<p class="card-text">Người bán: ' + user.fullName + '</p>';
                                                    tableHtml += '<p class="card-text product-description" id="productDescription_' + product.id + '"> Mô tả:' + product.description + '</p>';
                                                    tableHtml += '<p class="card-text">Giá:' + product.price + ' </p>';
                                                    tableHtml += '<button type="button" value="' + product.id + '" class="btn btn-outline-success d-block text-center product-detail" data-bs-toggle="modal" data-bs-target="#openProductDetail">Chi tiết</button>';
                                                    tableHtml += '</div>';
                                                    tableHtml += '</div>';
                                                    tableHtml += '</div>';
                                                    jQuery('#data-container').append(tableHtml);
                                                });
                                            }
                                        });
                                    }

                                    function searchAgain() {
                                        let nameProduct = document.getElementById("nameProduct").value;
                                        let optionProductType = document.getElementById("optionProductType").value;
                                        let priceProductFrom = document.getElementById('priceProductFrom').value;
                                        let priceProductTo = document.getElementById('priceProductTo').value;
                                        jQuery.ajax({
                                            url: 'publicmarket',
                                            type: 'POST',
                                            data: {
                                                nameProduct: nameProduct,
                                                optionProductType: optionProductType,
                                                priceProductFrom: priceProductFrom,
                                                priceProductTo: priceProductTo
                                            },
                                            success: function (product) {
                                                initializePagination(product);
                                            },
                                            error: function () {
                                                console.log("Error in Ajax request");
                                            }
                                        });
                                    }
                                    function clearFilter() {
                                        // Xóa giá trị của tất cả các ô input và select-option
                                        var allInputs = document.querySelectorAll("input, select");
                                        for (var i = 0; i < allInputs.length; i++) {
                                            var currentInput = allInputs[i];
                                            if (currentInput.tagName.toLowerCase() === "input" && currentInput.type === "text") {
                                                currentInput.value = "";
                                            } else if (currentInput.tagName.toLowerCase() === "select") {
                                                currentInput.selectedIndex = 0; // Chọn lại option mặc định
                                            }
                                        }

                                        // Đặt lại trang về trang đầu tiên
                                        searchAgain();

                                    }

                                    function searchValidation() {
                                        let nameProduct = document.getElementById("nameProduct").value;
                                        let optionProductType = document.getElementById("optionProductType").value;
                                        let priceProductFrom = document.getElementById('priceProductFrom').value;
                                        let priceProductTo = document.getElementById('priceProductTo').value;
                                        if (nameProduct === "") {
                                            alert('Vui lòng nhập tên sản phẩm.');
                                            return;
                                        } else if (optionProductType === "") {
                                            alert('Vui lòng chọn loại sản phẩm.');
                                            return;
                                        } else if (priceProductFrom === "" || priceProductTo === "") {
                                            alert('Vui lòng nhập đầy đủ giá sản phẩm.');
                                            return;
                                        }
                                    }

                                    function toDeposit() {
                                        window.location.href = "vnpay";
                                    }


                                    function getImageUrlByType(type) {
                                        switch (type) {
                                            case "Phần mềm":
                                                return  "./logo_icon_blabla/software.jpg";
                                            case "Tài liệu":
                                                return "./logo_icon_blabla/document.jpg";
                                            case "Tài khoản":
                                                return "./logo_icon_blabla/account.jpg";
                                            case "Khác":
                                                return "./logo_icon_blabla/other.jpg";
                                            default:
                                                return "";
                                        }
                                    }

                                    let productPrice;

                                    // lấy id của sản phẩm và show ra thông tin của sản phẩm đó
                                    jQuery(document).on('click', '.product-detail', function () {
                                        let pid = jQuery(this).val();
                                        jQuery.ajax({
                                            url: 'productdetail',
                                            type: 'POST',
                                            data: {
                                                pid: pid
                                            },
                                            success: function (product) {
                                                let user = product.user;
                                                let detail = document.getElementById('productDetail');
                                                let detailHtml = '';
                                                if (product !== null) {
                                                    productPrice = product.price;
                                                    detailHtml += '<div class="row">';
                                                    detailHtml += '<div class="col-md-12">';
                                                    detailHtml += '<div class="card-group">';
                                                    detailHtml += '<div class="card">';
                                                    detailHtml += '<div class="card-header">';
                                                    detailHtml += '<h3 class="mb-0">Thông tin mặt hàng trung gian</h3>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="card-body">';
                                                    // Thêm các thông tin chi tiết của sản phẩm
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Mã sản phẩm</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" class="form-control" disabled value="' + product.id + '">';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Người bán</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" class="form-control" disabled value="' + user.fullName + '">';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Chủ đề trung gian</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<textarea rows="2" class="form-control" disabled >' + product.name + '</textarea>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Giá tiền</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input id="productPrice" type="text" class="form-control" disabled value="' + product.price + '">';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Bên chịu phí trung gian</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<button type="button" disabled id="buttonBenBanNo' + product.id + '"  class="' + (product.feePayer === false ? 'btn btn-success' : 'btn btn-secondary') + '">BÊN BÁN</button>';
                                                    detailHtml += '<button type="button" disabled id="buttonBenMuaNo' + product.id + '"  class="' + (product.feePayer !== false ? 'btn btn-success' : 'btn btn-secondary') + '">BÊN MUA</button>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Phí trung gian</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" class="form-control" disabled value="' + (product.price * 5 / 100) + '">';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    // 
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Tổng tiền cần thanh toán</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';

                                                    detailHtml += '<input id="totalNeededAmount" type="text" class="form-control totalNeededAmount" disabled>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    // 
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Mô tả</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" class="form-control overflow-auto" value="' + product.description + '" disabled>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Phương thức liên hệ</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<textarea rows="2" placeholder="Số điện thoại/Zalo/Facebook/Telegram/Discord..." class="form-control" disabled >' + product.contact + '</textarea>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Thời gian tạo</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" class="form-control" value="' + product.createdAt + '" disabled>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Cập nhật lần cuối</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" class="form-control" value="' + product.updatedAt + '" disabled>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    //
                                                    detailHtml += '<div class="position-relative row form-group">';
                                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                                    detailHtml += '<label><b>Link của đơn trung gian (dùng khi ở đơn ở trạng thái riêng tư)</b></label>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '<div class="col-md-9">';
                                                    detailHtml += '<input type="text" disabled class="form-control" value="' + product.shareLink + '">';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>';
                                                    detailHtml += '</div>'; // Kết thúc card-body

                                                    detailHtml += '<div class="modal-footer">';
                                                    detailHtml += '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>';
                                                    detailHtml += '<button type="button" class="btn btn-success" onclick="buyProduct(' + product.id + ')" >Mua</button>';
                                                    detailHtml += '</div>';

                                                    detailHtml += '<div class="modal fade" id="confirmModal' + product.id + '" tabindex="-1" aria-labelledby="confirmModalLabel' + product.id + '" aria-hidden="true">'
                                                            + '   <div class="modal-dialog">'
                                                            + '        <div class="modal-content">'
                                                            + '            <div class="modal-header">'
                                                            + '                <h1 class="modal-title fs-5" id="confirmModalLabel' + product.id + '">Xác nhận mua sản phẩm</h1>'
                                                            + '                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'
                                                            + '            </div>'
                                                            + '            <div class="modal-body">'
                                                            + '                <div>Bạn có chắc chắn muốn mua sản phẩm này và đồng ý thanh toán?</div>'
                                                            + '            </div>'
                                                            + '            <div class="modal-footer">'
                                                            + '                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>'
                                                            + '<button type="button" id="confirm-information-ok' + product.id + '" class="btn btn-success">Xác nhận</button>'
                                                            + '            </div>'
                                                            + '        </div>'
                                                            + '    </div>'
                                                            + '</div>';


                                                    detailHtml += '</div>'; // Kết thúc card
                                                    detailHtml += '</div>'; // Kết thúc card-group
                                                    detailHtml += '</div>'; // Kết thúc col-md-12
                                                    detailHtml += '</div>'; // Kết thúc row

                                                }
                                                console.log(product.name);
                                                // Đặt nội dung HTML vào phần tử detail
                                                jQuery('#productDetail').html(detailHtml);
                                                processTotalAmount(product);
                                            },
                                            error: function () {
                                                console.log("Error in Ajax");
                                            }
                                        });
                                    }
                                    );

                                    function processTotalAmount(product) {
                                        // Your logic to calculate and update "Tổng tiền cần thanh toán"
                                        let totalNeededPrice = product.price;
                                        let buttonClassBenBan = jQuery('#buttonBenBanNo' + product.id).attr('class');
                                        let buttonClassBenMua = jQuery('#buttonBenMuaNo' + product.id).attr('class');

                                        console.log(buttonClassBenBan);
                                        console.log(buttonClassBenMua);

                                        if (buttonClassBenBan && buttonClassBenBan.includes('btn-success')) {
                                            jQuery('#productDetail #totalNeededAmount').val(totalNeededPrice);
                                        }

                                        if (buttonClassBenMua && buttonClassBenMua.includes('btn-success')) {
                                            totalNeededPrice = product.price + (product.price * 5 / 100);
                                            jQuery('#productDetail #totalNeededAmount').val(totalNeededPrice);
                                        }
                                    }

                                    function buyProduct(productId) {
                                        jQuery('#confirmModal' + productId).modal('show');

                                        let benChiuPhi;
                                        let totalPriceSeller;
                                        const originalPrice = productPrice;
                                        let totalNeededPrice = jQuery('#productDetail #totalNeededAmount').val(); //xác định rõ số tiền cần thanh toán

                                        let buttonClassBenBan = document.getElementById('buttonBenBanNo' + productId).className;
                                        if (buttonClassBenBan.includes('btn-success')) {
                                            benChiuPhi = "benban" + productId;
                                            totalPriceSeller = originalPrice - (originalPrice * 0.05);
                                            console.log(benChiuPhi);
                                            console.log(totalNeededPrice);
                                        }

                                        let buttonClassBenMua = document.getElementById('buttonBenMuaNo' + productId).className;
                                        if (buttonClassBenMua.includes('btn-success')) {
                                            benChiuPhi = "benmua" + productId;
                                            totalPriceSeller = originalPrice;
                                            console.log(benChiuPhi);
                                            console.log(totalNeededPrice);
                                        }

                                        jQuery(document).on('click', '#confirm-information-ok' + productId, function () {
            <%
        if(accountSession == null){
            %>
                                            window.location.href = "login";
            <%}%>

                                            window.location.href = "IntermediateOrderInformation.jsp?productId=" + productId + "&benChiuPhi=" + benChiuPhi + "&totalPriceBuyer=" + totalNeededPrice + "&sellerReceivedTrueMoney=" + totalPriceSeller;
                                        });
                                    }

        </script>
    </body>
</html>