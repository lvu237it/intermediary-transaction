<%-- 
    Document   : Transaction
    Created on : Feb 7, 2024, 3:01:00 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.accountSession == null}">
    <c:redirect url="login"/> <!-- Chuyển hướng đến trang login nếu session không tồn tại -->
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý giao dịch</title>
        <link rel="icon" href="./logo_icon_blabla/ITS_logo_4.jpg">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <link rel="stylesheet" href="./style_CSS/transactionManagement.css">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <h3 class="text-center mt-3 mb-3">Lịch sử giao dịch</h3>
        <div class="container" style="margin: auto">
            <div class="container d-flex justify-content-around justify-content-center" style="gap: 0 1rem">
                <div class="row" style="gap: 1rem 1rem">
                    <!------------------- Tìm lịch sử giao dịch theo giá sản phẩm from - to ----------------------------->
                    <form class="col" onsubmit="searchValidation2(); search(); return false">
                        <div class="d-flex justify-content-center align-items-center gap-select-search" style="margin: auto">
                            <input id="input-valid-price-from" class="select-input form-control" style="width: 6.3rem;" placeholder="Từ" pattern="^[0-9]+(\.[0-9]+)?$" name="priceFrom">
                            <input id="input-valid-price-to" class="select-input form-control" style="width: 6.3rem;" placeholder="Đến" pattern="^[0-9]+(\.[0-9]+)?$" name="priceTo">
                            <button class="btn btn-outline-success">Tìm</button>
                        </div>
                    </form>
                </div>
                <div class="row" style="gap: 1rem 1rem">
                    <!------------------- Tìm lịch sử giao dịch theo loại giao dịch (+ hoặc -) ----------------------------->
                    <form class="col" onsubmit="searchValidation3(); search(); return false">
                        <div class="d-flex justify-content-center align-items-center gap-select-search" style="margin: auto">
                            <select id="transactionType" name="optionTransactionType" class="select-input form-control">
                                <option value="">-- Loại giao dịch --</option>
                                <option value="true">+</option>
                                <option value="false">-</option>
                            </select>
                            <button class="btn btn-outline-success">Tìm</button>
                        </div>
                    </form> 
                    <!------------------- Tìm lịch sử giao dịch theo trạng thái ----------------------------->
                    <form class="col" onsubmit="searchValidation4(); search(); return false">
                        <div class="d-flex justify-content-center align-items-center gap-select-search" style="margin: auto">
                            <select id="transactionStatus" name="optionStatus" class="select-input form-control">
                                <option value="">-- Trạng thái --</option>
                                <option value="1">Đã xử lý</option>
                                <option value="0">Chưa xử lý</option>
                            </select>
                            <button class="btn btn-outline-success">Tìm</button>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <button class="btn btn-outline-success" onclick="clearFilter();">Bỏ lọc</button>
                </div>
            </div>
            <!-- Bảng hiển thị lịch sử giao dịch -->
            <table class="table table-responsive table-bordered table-hover mt-3">
                <thead class="text-center">
                <th>Mã giao dịch</th>
                <th>Số tiền</th>
                <th>Loại giao dịch</th>
                <th>Trạng thái xử lý</th>
                <th>Người sở hữu</th>
                <th>Thời gian tạo</th>  
                <th>Cập nhật cuối</th>
                <th>Hành động</th>
                </thead>
                <tbody id="transactionTableBody">
                    <!----- Dữ liệu được phản hồi lại từ servlet sau khi gửi yêu cầu ajax ----->
                </tbody>
            </table>
            <h5 id="notification" class="text-center mt-3 mb-3"></h5>
        </div>
        <nav class="mt-4" id="nav-pagination" style="display: none">
            <ul class="pagination justify-content-center">
                <!-- Nút "Previous" -->
                <li class="page-item">
                    <a id="preButton" class="page-link button-fake" onclick="updateDirection('previous')">Previous</a>
                </li>
                <li class="page-item"><a id="currentPage" class="page-link button-fake">1</a></li>
                <!-- Nút "Next" -->
                <li class="page-item">
                    <a id="nextButton" class="page-link button-fake" onclick="updateDirection('next')">Next</a>

                </li>
            </ul>
        </nav>
        <script>
            function searchValidation2() {
                let priceFrom = document.getElementById('input-valid-price-from').value;
                let priceTo = document.getElementById('input-valid-price-to').value;
                if (priceFrom === "" || priceTo === "") {
                    alert('Vui lòng nhập đầy đủ giá sản phẩm.');
                    return;
                }
            }
            function searchValidation3() {
                let transactionType = document.getElementById('transactionType').value;
                if (transactionType === "") {
                    alert('Vui lòng chọn loại giao dịch.');
                    return;
                }
            }
            function searchValidation4() {
                let transactionStatus = document.getElementById('transactionStatus').value;
                if (transactionStatus === "") {
                    alert('Vui lòng chọn trạng thái giao dịch.');
                    return;
                }
            }

            document.addEventListener("DOMContentLoaded", loadContent());

            function loadContent() {
                let currentPage = document.getElementById('currentPage').innerHTML;
                let notifications = document.getElementById('notification');

                $.ajax({
                    url: 'transactionmanagement',
                    type: 'GET',
                    data: {
                        currentPage: currentPage
                    },
                    success: function (response) {
                        // Xóa nội dung cũ trong bảng trước khi hiển thị dữ liệu mới
                        let row = document.getElementById("transactionTableBody");
                        row.innerHTML = "";
                        if (response !== null && response.trim() !== "") {
                            // Thêm dữ liệu mới vào bảng
                            row.innerHTML += response;
                            document.getElementById("nav-pagination").style.display = 'block';
                            notifications.innerHTML = "";
                        } else {//hết dữ liệu để hiển thị
                            notifications.innerHTML = "--- Không còn bản ghi phù hợp ---";
                            document.getElementById("nextButton").style.display = 'none';
                        }
                        // Ẩn nút "Previous" khi ở trang đầu tiên
                        if (currentPage === "1") {
                            preButton.style.display = 'none';
                        } else {
                            preButton.style.display = 'block';
                        }
                    },
                    error: function () {
                        console.log("Error in Ajax request");
                    }
                });
            }


            function search() {
                let priceFrom = document.getElementById('input-valid-price-from').value;
                let priceTo = document.getElementById('input-valid-price-to').value;
                let transactionType = document.getElementById('transactionType').value;
                let transactionStatus = document.getElementById('transactionStatus').value;
                let currentPage = document.getElementById('currentPage').innerHTML;
                let notifications = document.getElementById('notification');

                $.ajax({
                    url: 'transactionmanagement',
                    type: 'POST',
                    data: {
                        priceFrom: priceFrom,
                        priceTo: priceTo,
                        optionTransactionType: transactionType,
                        transactionStatus: transactionStatus,
                        currentPage: currentPage
                    },
                    success: function (response) {
                        // Xóa nội dung cũ trong bảng trước khi hiển thị dữ liệu mới
                        let row = document.getElementById("transactionTableBody");
                        row.innerHTML = "";
                        if (response !== null && response.trim() !== "") {
                            // Thêm dữ liệu mới vào bảng
                            row.innerHTML += response;
                            document.getElementById("nav-pagination").style.display = 'block';
                            notifications.innerHTML = "";
                        } else {//hết dữ liệu để hiển thị

                            notifications.innerHTML = "--- Không còn bản ghi phù hợp ---";
                            document.getElementById("nextButton").style.display = 'none';
                        }
                        // Ẩn nút "Previous" khi ở trang đầu tiên
                        if (currentPage === "1") {
//                            JavaScript có khả năng truy cập các biến toàn cục (global variables) từ bất kỳ hàm nào, 
//                            kể cả khi biến đó được khai báo bên ngoài hoặc trong một hàm khác.
//                            Ở đây preButton đã được gọi thông qua câu lệnh document.getElementById("preButton")
//                              Vì vậy có thể trỏ tới nó bằng id "preButton" mà không cần khai báo lại
                            preButton.style.display = 'none';
                        } else {
                            preButton.style.display = 'block';
                        }
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
                document.getElementById("currentPage").innerHTML = "1";

                // Ẩn thông báo và bảng kết quả
                document.getElementById("notification").innerHTML = "";
                document.getElementById("transactionTableBody").innerHTML = "";
                document.getElementById("nav-pagination").style.display = 'none';
                document.getElementById("preButton").style.display = 'none';
                document.getElementById("nextButton").style.display = 'block';
                loadContent();
            }

            function updateDirection(direction) {
                let currentPageElement = document.getElementById('currentPage');
                let currentPage = parseInt(currentPageElement.innerHTML);
                let preButton = document.getElementById('preButton');
                let nextButton = document.getElementById('nextButton');

                if (direction === 'previous') {
                    if (currentPage > 1) {
                        currentPage--;
                        document.getElementById("nextButton").style.display = 'block';
                    }
                } else {
                    currentPage++;
                }
                currentPageElement.innerHTML = currentPage.toString();
                let priceFrom = document.getElementById('input-valid-price-from').value;
                let priceTo = document.getElementById('input-valid-price-to').value;
                let transactionType = document.getElementById('transactionType').value;
                let transactionStatus = document.getElementById('transactionStatus').value;
                if (!priceFrom.trim() && !priceTo.trim() && !transactionType.trim() && !transactionStatus.trim()) {
                    loadContent();
                } else {
                    search();
                }
            }
        </script>
    </body>
</html>
