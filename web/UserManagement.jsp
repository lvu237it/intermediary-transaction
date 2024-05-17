<%-- 
    Document   : UserManagement
    Created on : Jan 28, 2024, 4:28:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.accountSession != 1}">
    <c:redirect url="homepage"/> 
</c:if> <!-- Chuyển hướng tới trang homepage nếu người vào trang quản lý user không phải là admin -->

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý người dùng</title>

        <style>
            html,
            body,
            .intro {
                height: 100%;
            }

            table td,
            table th {
                text-overflow: ellipsis;
                white-space: nowrap;
                overflow: hidden;
            }

            .card {
                border-radius: .5rem;
            }

            .mask-custom {
                background: rgba(24, 24, 16, .2);
                border-radius: 2em;
                backdrop-filter: blur(25px);
                border: 2px solid rgba(255, 255, 255, 0.05);
                background-clip: padding-box;
                box-shadow: 10px 10px 10px rgba(46, 54, 68, 0.03);
            }
        </style>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous"
            />
        <link>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
            />
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <link rel="stylesheet" href="Pagination.js/pagination.css">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <section class="intro">
            <div class="h-100">
                <h3 class="text-center mt-3 mb-3">Quản lý người dùng hệ thống</h3>
                <div class="mask d-flex justify-content-end h-100">
                    <div class="container">
                        <div class="row">
                            <div class="input-group mb-3 col">
                                <input type="text" style="max-width: 250px" class="form-control" placeholder="Tìm thông qua username" aria-label="Type something" aria-describedby="button-addon2">
                                <button class="btn btn-outline-success" type="button" id="button-addon2">Tìm</button>    
                            </div>
                        </div>


                        <div class="row justify-content-center">
                            <div class="col-12">
                                <div class="card shadow-2-strong" style="background-color: #f5f7fa;">
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-borderless mb-0">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" />
                                                            </div>
                                                        </th>
                                                        <th scope="col">ID</th>
                                                        <th scope="col">FULLNAME</th>
                                                        <th scope="col">USERNAME</th>
                                                        <th scope="col">SỐ ĐIỆN THOẠI</th>
                                                        <th scope="col">GMAIL</th>
                                                        <th scope="col">THỜI GIAN TẠO</th>
                                                        <th scope="">CẬP NHẬT CUỐI</th>
                                                        <th scope="col">XÓA</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="data-container">
                                                    <!-- phần này được fill bởi ajax bên dưới  -->





                                                </tbody>
                                            </table>
                                            <!-- phần modal xác nhận xem có xóa bản ghi này không -->
                                            <div id="deleteConfirmationModal" class="modal" tabindex="-1">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Xác nhận việc xóa tài khoản</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>Bạn có chắc chắn rằng muốn xóa tài khoản này ra khỏi hệ thống?</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                            <button type="button" class="btn btn-success" id="confirmDeleteBtn">Xóa</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex">
                            <div class="mt-3 mx-auto" id="pagination-container"></div>
                        </div>

                    </div>
                </div>
            </div>
        </section>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
        <script src="Pagination.js/pagination.js"></script>
        <script>
            $(document).ready(function () {
                loadDataAndInitializePagination();
            });


            function loadDataAndInitializePagination() {
                $.ajax({
                    url: 'userinformation',
                    type: 'post',
                    data: {

                    },
                    success: function (users) {


                        initializePagination(users);
                    },
                    error: function (xhr) {
                        console.error('Đã xảy ra lỗi: ' + xhr.status);
                    }
                });
            }
            ;

            function initializePagination(data) {
                $('#pagination-container').pagination({
                    dataSource: data,
                    pageSize: 10,
                    callback: function (data, pagination) {
                        $('#data-container').empty();
                        $.each(data, function (index, user) {
                            let tableHtml = '';
                            tableHtml += '<tr>';
                            tableHtml += '<th scope="row">' +
                                    '<div class="form-check">' +
                                    '<input class="form-check-input" type="checkbox" value="" id="flexCheckDefault1"/>' +
                                    '</div>' +
                                    '</th>';
                            tableHtml += '<td>' + user.userId + '</td>';
                            tableHtml += '<td>' + user.fullName + '</td>';
                            tableHtml += '<td>' + user.userName + '</td>';
                            tableHtml += '<td>' + user.phoneNumber + '</td>';
                            tableHtml += '<td>' + user.gmail + '</td>';
                            tableHtml += '<td>' + user.createdAt + '</td>';
                            tableHtml += '<td>' + user.updatedAt + '</td>';
                            tableHtml += '<td>' +
                                    '<button type="button" class="btn btn-danger btn-sm px-3 delete-btn" \n\
                                                                     value="' + user.userId + '" data-bs-toggle="modal" data-bs-target="#deleteConfirmationModal">' +
                                    '<i class="fas fa-times"></i>' +
                                    '</button>' +
                                    '</td>';
                            tableHtml += '</tr>';
                            $('#data-container').append(tableHtml);
                        });
                    }
                });
            }
            // vì toàn bộ dữ liệu bao gồm class delete-btn không nằm trong file jsp/html mà 
            // được đẩy từ servlet lên và viết vào trong bảng, thế nên gọi bằng $('.delete-btn').click()...
            // thì nó sẽ không hiểu, vậy nên phải gọi qua document để gọi toàn bộ lại trang 1 lần và chỉ địng rõ
            // trang sẽ bắt sự kiện nào và class nào
            $(document).on('click', '.delete-btn', function () {
                let userId = $(this).val();

                $('#deleteConfirmationModal').data('user-id', userId);
            });

            $('#confirmDeleteBtn').click(function () {

                let userId = $('#deleteConfirmationModal').data('user-id');
                console.log(userId);
                $.ajax({
                    url: 'deleteuser',
                    type: 'get',
                    data: {
                        userId: userId
                    },
                    success: function (response) {
                        console.log('xoa thanh cong!');

                        loadDataAndInitializePagination();

                        $('#deleteConfirmationModal').modal('hide');
                    },
                    error: function (xhr) {
                        console.error('Đã xảy ra lỗi: ' + xhr.status);
                    }

                });

            });
        </script>                   

    </body>
</html>
