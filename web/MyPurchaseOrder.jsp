<%-- 
    Document   : MyPurchaseOrder
    Created on : Feb 17, 2024, 10:48:34 PM
    Author     : HP
--%>

<%@page import = "DAO.OrderDAO" %>
<%@page import = "DAO.CorrespondDAO" %>
<%@page import = "DAO.TransactionDAO" %>
<%@page import = "DAO.ProductDAO" %>
<%@page import = "DAO.UserDAO" %>
<%@page import = "Model.Order" %>
<%@page import = "Model.Product" %>
<%@page import = "Model.User" %>
<%@page import = "Model.Correspond" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đơn mua của tôi</title>
        <link rel="icon" href="./logo_icon_blabla/ITS_logo_4.jpg">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
        <script src="https://cdn.datatables.net/2.0.1/js/dataTables.min.js"></script>
        <script src="https://cdn.rawgit.com/alvaro-prieto/colResizable/master/colResizable-1.6.min.js"></script>
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.1/css/dataTables.dataTables.css">
        <!--<link rel="stylesheet" href="./style_CSS/publicMarket.css">-->
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <style>
            .position-relative{
                margin-top: 20px;
            }
            #example td {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            #example th {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <!-- Header -->
        <div class="d-flex mt-5 mx-5 mb-4">
            <h3>Đơn mua của tôi</h3>
        </div>

        <div class="container-fluid" >
            <table class="table table-responsive table-bordered table-hover text-center" id="example">
                <thead>
                    <tr>
                        <th scope="col">Mã trung gian</th>
                        <th scope="col">Chủ đề trung gian</th>
                        <!--<th scope="col">Nội dung ẩn</th>-->
                        <!--<th scope="col">Loại sản phẩm</th>--> <!-- xem "Chi tiết" để biết thêm thông tin --> 
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Người bán</th>
                        <!--<th scope="col">Phương thức liên hệ</th>--> <!-- xem "Chi tiết" để biết thêm thông tin --> 
                        <!--<th scope="col">Công khai/Riêng tư</th>--> <!-- xem "Chi tiết" để biết thêm thông tin --> 
                        <th scope="col">Giá tiền</th>
                        <th scope="col">Bên chịu phí</th>
                        <!--<th scope="col">Phí giao dịch</th>-->  <!-- xem "Chi tiết" để biết thêm thông tin --> 
                        <th scope="col">Tổng tiền bên mua đã thanh toán</th>
                        <th scope="col">Tổng tiền bên bán thực nhận</th>
                        <!--<th scope="col">Thời gian tạo</th>--> <!-- xem "Chi tiết" để biết thêm thông tin --> 
                        <!--<th scope="col">Cập nhật cuối</th>--> <!-- xem "Chi tiết" để biết thêm thông tin --> 
                        <th scope="col">Hành động</th>   
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!--hiển thị phần dữ liệu được phản hồi từ servlet sau khi gửi yêu cầu ajax thành công tới servlet từ jsp-->

                </tbody>
            </table>

            <h5 id="notification" class="text-center mt-3 mb-3"></h5>
            <nav class="mt-4" id="nav-pagination" style="display: none">
                <!--mặc định là để display none cho pagination vì lúc này chưa hiển thị bản ghi-->
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
        </div>

        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thông tin đơn trung gian</h5>
                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="detailModalBody">
                        <!-- Thông tin chi tiết yêu cầu rút tiền sẽ được hiển thị ở đây -->
                    </div>
                    <div class="modal-footer" id="modalFooter">


                    </div>
                </div>
            </div>
        </div>

        <!-- Second Nested Modal - Hoàn tất giao dịch -->
        <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="confirmModalLabel">Xác nhận thông tin đơn hàng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Bạn xác nhận rằng thông tin bên bán cung cấp là đúng mô tả?</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Huỷ</button>
                        <button id="confirm-information-ok" type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#successModal">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Third Nested Modal - Hoàn tất giao dịch -->
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

        <!--Modal "Đã xác nhận rồi, không thể khiếu nại"-->
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
        <!--Modal confirm lần 2 từ người mua--> 
        <div class="modal fade" id="confirmModalAfterCheckAgain" tabindex="-1" aria-labelledby="c" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="confirmModalAfterCheckAgainLabel">Xác nhận thông tin đơn hàng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Bạn xác nhận rằng thông tin bên bán cung cấp là đúng mô tả?</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Huỷ</button>
                        <button id="after-check-again" type="button" class="btn btn-success" data-bs-dismiss="modal">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal confirm thành công (confirm lần 2)-->
        <div class="modal fade" id="successModalAfterCheckAgain" tabindex="-1" aria-labelledby="c" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="successModalAfterCheckAgainLabel">Hoàn tất giao dịch</h1>
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
        <!--Modal yêu cầu admin giải quyết-->
        <div class="modal fade" id="requireAdminHandle" tabindex="-1" aria-labelledby="c" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="requireAdminHandleLabel">Yêu cầu giải quyết khiếu nại</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Bạn chắc chắc muốn yêu cầu admin giải quyết khiếu nại do nghi ngờ bên bán chơi xấu?</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Huỷ</button>
                        <button id="confirmRequireAdmin" type="button" class="btn btn-success" data-bs-dismiss="modal">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal xác nhận yêu cầu admin giải quyết-->
        <div class="modal fade" id="acceptRequireAdmin" tabindex="-1" aria-labelledby="c" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="acceptRequireAdminLabel">Yêu cầu giải quyết khiếu nại</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Yêu cầu giải quyết khiếu nại của bạn đã được gửi tới admin. Vui lòng chờ.</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            //                document.addEventListener("DOMContentLoaded", function () {
            //                    search();
            //                });

            var customLanguage = {
                "sLengthMenu": "Hiển thị _MENU_ bản ghi mỗi trang",
                "sInfo": "Hiển thị _START_ đến _END_ trong tổng số _TOTAL_ bản ghi",
                "sInfoEmpty": "Hiển thị từ 0 đến 0 trong tổng số 0 bản ghi",
                "sInfoFiltered": "(đã lọc từ tổng số _MAX_ bản ghi)",
                "sZeroRecords": "Không tìm thấy bản ghi phù hợp",
                "sSearch": "Tìm kiếm:"
            };

            $(document).ready(function () {
                var order = null;
                var table = $('#example').DataTable({
                    "dom": 'lrtip',
                    "processing": true,
                    "serverSide": true,
                    "lengthMenu": [[5, 10, 15, 20, 25], [5, 10, 15, 20, 25]],
                    "language": customLanguage,
                    "ajax": {
                        "url": "purchase",
                        "type": "POST",
                        "data": function (d) {
                            if (order !== null) {
                                var columnIndex = order[0][0];
                                var columnDirection = order[0][1];
                                d.columnIndex = columnIndex;
                                d.columnDirection = columnDirection;
                            }
                            return d;
                        }
                    },
                    "columns": [
                        {"data": "id"}, {"data": "productname"},
                        //                        {"data": "hiddenField"},
                        {"data": "status",
                            "render": function (data, type, row) {
                                switch (data) {
                                    case 1:
                                        return "Sẵn sàng giao dịch";
                                    case 2:
                                        return "Đang chờ người mua xác nhận";
                                    case 3:
                                        return "Hoàn tất";
                                    case 4:
                                        return "Bên mua khiếu nại sản phẩm không đúng mô tả";
                                    case 5:
                                        return "Bên bán xác nhận sản phẩm đúng mô tả";
                                    case 6:
                                        return "Huỷ đơn hàng - Bên bán xác nhận thông tin đơn hàng không chính xác";
                                    case 7:
                                        return "Yêu cầu khiếu nại đang được Admin giải quyết";
                                }
                            }
                        },
                        {"data": "sellerName"},
                        {"data": "productprice"},
                        {"data": "feePayer",
                            "render": function (data, type, row) {
                                if (data === true) {
                                    return "Bên mua";
                                } else {
                                    return "Bên bán";
                                }
                            }
                        },
                        {"data": "totalPrice"},
                        {"data": "sellerReceivedTrueMoney"},
                        {
                            "data": "id",
                            "render": function (data, type, row) {
                                return '<button type="button" data-id="' + data + '" id="button-exampleModal' + data + '" class="btn btn-success">Chi tiết</button>';
                            }
                        }
                    ]
                });

                table.on('init.dt', function () {
                    order = table.order();
                });

                $("#example").colResizable({
                    liveDrag: true,
                    gripInnerHtml: "<div class='grip'></div>",
                    draggingClass: "dragging",
                    resizeMode: 'fit'
                });

            });

            $(document).on("click", "[id^=button-exampleModal]", function () {
                // Đóng modal hiện tại (nếu có)
//                     $('#exampleModal').modal('hide');
//                      $('.modal').modal('hide');
                //Gửi yêu cầu ajax để lấy chi tiết thông tin của đơn mua
                var id = $(this).data('id'); // Lấy ID từ thuộc tính data-id của nút

                $.ajax({
                    url: 'purchasedmodaldetail',
                    type: 'GET',
                    data: {id: id}, // Gửi ID trong request
                    success: function (response) {
                        console.log('Ajax successfully when get order detail by product id');
                        console.log(response);
// Hiển thị dữ liệu trong modal
                        $('#detailModalBody').empty(); // Xóa nội dung cũ của modal body trước khi thêm mới
                        $('#modalFooter').empty(); // Xóa nội dung cũ của modal body trước khi thêm mới
                        var detailHtml = '<div class="row">';
                        detailHtml += '<div class="col-md-12">';
                        detailHtml += '<div class="card-group">';
                        detailHtml += '<div class="card">';
                        detailHtml += '<div class="card-header">';
                        detailHtml += '<h3 class="mb-0"></h3>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="card-body">';
                        if (response.status === 5) {//Yêu cầu admin giải quyết khiếu nại từ phía người mua
                            detailHtml += '<div class="d-flex justify-content-end"><button type="button" class="btn btn-danger mb-3" id="reportAdminByUser' + response.id + '">Yêu cầu Admin giải quyết</button></div>';
                        }
// ID
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Mã trung gian:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input id="oId" type="text" class="form-control" disabled value="' + response.id + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Chủ đề trung gian:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.productname + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Nội dung ẩn:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.hiddenField + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Loại sản phẩm:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.type + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';


// Status
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Trạng thái:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        let statusText = '';
                        switch (response.status) {
                            case 1:
                                statusText = 'Sẵn sàng giao dịch';
                                break;
                            case 2:
                                statusText = 'Đang chờ người mua xác nhận';
                                break;
                            case 3:
                                statusText = 'Hoàn tất';
                                break;
                            case 4:
                                statusText = 'Bên mua khiếu nại sản phẩm không đúng mô tả';
                                break;
                            case 5:
                                statusText = 'Bên bán xác nhận sản phẩm đúng mô tả';
                                break;
                            case 6:
                                statusText = 'Huỷ đơn hàng - Bên bán xác nhận thông tin đơn hàng không chính xác';
                                break;
                            case 7:
                                statusText = 'Yêu cầu khiếu nại đang được Admin giải quyết';
                                break;
                            default:
                                statusText = response.status;
                        }
                        detailHtml += '<input type="text" class="form-control" disabled value="' + statusText + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';


                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Người bán:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.sellerName + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Phương thức liên hệ:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.productcontact + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Phạm vi:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (response.isPrivate === 0) {
                            detailHtml += '<input type="text" class="form-control" disabled value="Công khai">';
                        } else {
                            detailHtml += '<input type="text" class="form-control" disabled value="Riêng tư">';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Giá tiền:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.productprice + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Phí trung gian:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.transactionfee + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Bên chịu phí trung gian:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (!response.feePayer) {
                            detailHtml += '<input type="text" class="form-control" disabled value="Bên bán">';
                        } else {
                            detailHtml += '<input type="text" class="form-control" disabled value="Bên mua">';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Tổng tiền bên mua đã thanh toán:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.totalPrice + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Tổng tiền bên bán thực nhận:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input id="truemoneysellergot' + response.id + ' type="text" class="form-control" disabled value="' + response.sellerReceivedTrueMoney + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Thời gian tạo:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.createdAt + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Cập nhật cuối:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.updatedAt + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Link chia sẻ:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.shareLink + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '</div>'; // Kết thúc card-body
                        detailHtml += '</div>'; // Kết thúc card
                        detailHtml += '</div>'; // Kết thúc card-group
                        detailHtml += '</div>'; // Kết thúc col-md-12
                        detailHtml += '</div>'; // Kết thúc row
//                   
                        $('#detailModalBody').append(detailHtml);

                        var footerHtml = '';
                        //          "<!-- First Nested Modal - Xác nhận thông tin đơn hàng -->");
                        if (response.status === 2) {//đơn hàng đang chờ xác nhận từ người mua
                            footerHtml += '<div id="button-report" class="d-flex col justify-content-center">';
                            footerHtml += '<button id="report-order' + response.id + ' type="button" class="btn" style="background-color: #ff3333; color: white">Khiếu nại</button>';
                            footerHtml += '</div>';
                            footerHtml += '<div id="button-confirm-info" class="d-flex col justify-content-center">';
                            footerHtml += '<button onclick="confirmModalButton()" type="button" class="btn btn-success">Xác nhận</button>';
                            footerHtml += '</div>';
                            footerHtml += '<div class="d-flex col justify-content-center">';
                            footerHtml += '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>';
                            footerHtml += '</div>';
                        } else if (response.status === 5) {
                            footerHtml += '<div id="button-confirm-info" class="d-flex col justify-content-center">';
                            footerHtml += '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>';
                            footerHtml += '</div>';
                            footerHtml += '<div id="button-confirm-info" class="d-flex col justify-content-center">';
                            footerHtml += '<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#confirmModalAfterCheckAgain">Xác nhận</button>';
                            footerHtml += '</div>';
                        }

                        $('#modalFooter').append(footerHtml);
                        // Hiển thị modal
                        $('#exampleModal').modal('show');

                    },
                    error: function (xhr, status, error) {
// Xử lý lỗi khi không thể lấy dữ liệu từ server
                        console.error(error);
                    }
                });
            });

            let productIdGlobal;
            $('#exampleModal').on('shown.bs.modal', function () {
                var oId = $('#oId').val(); // Lấy giá trị của thẻ input có id là "oId"
//            var orderIdString = parseInt(oId.match(/\d+$/)[0]); // Sử dụng regular expression để lấy ra chữ số phía sau và chuyển đổi thành số nguyên
                // Kiểm tra nếu orderIdString là một số hợp lệ trước khi sử dụng nó
                // Gọi ajax với orderIdString đã lấy được
                $.ajax({
                    url: 'updateorderstatusnsellerwallet',
                    type: 'POST',
                    data: {orderId: oId},
                    success: function (productId) {
                        productIdGlobal = productId;
                        console.log("Ajax successfully! And ProductIdGlobal is " + productIdGlobal);
                        // Xử lý sau khi nhận được productIdGlobal
                    },
                    error: function () {
                        console.log("Error when get data from servlet updateorderstatusnsellerwallet POST");
                    }
                });
            });

            //Người mua yêu cầu admin giải quyết khiếu nại
            $(document).on("click", "[id^='reportAdminByUser']", function () {
                $("[id^='exampleModal']").modal('hide');
                $("#requireAdminHandle").modal('show');
                $(document).on("click", "#confirmRequireAdmin", function () {
                    $.ajax({
                        url: 'updateorderstatusreport',
                        type: 'GET',
                        data: {
                            productId: productIdGlobal,
                            orderStt: 7
                        },
                        success: function (response) {
                            console.log("Ajax successfully when sending request to update order status (7) by buyer!");
                        },
                        error: function () {
                            console.log("Error in updating order status");
                        }
                    });
                    $("#requireAdminHandle").modal('hide');
                    $("#acceptRequireAdmin").modal('show');
                });
            });

            $(document).on("click", "[id^='confirm-information-ok']", function () {
                $("#confirmModal").modal('hide');
                confirmOrder();
            });

//Xác nhận đơn hàng bằng cách click vào button "Xác nhận" ở trang MyPurchasedOrder.jsp
            function confirmOrder() {
                $.ajax({
                    url: 'updateorderstatusnsellerwallet',
                    type: 'GET',
                    data: {
                        productIdByOrderId: productIdGlobal
                    },
                    success: function (response) {
                        console.log("Ajax updating order status and seller wallet successfully!");
                        $('#button-report').remove();
                        $('#button-confirm-info').remove();
                    },
                    error: function () {
                        console.log("Error in updating order status");
                    }
                });
            }

            $(document).on('shown.bs.modal', '[id^=confirmModalAfterCheckAgain]', function (event) {
                $("[id^='exampleModal']").modal('hide');
                $(document).on("click", "[id^='after-check-again']", function () {
                    $("#confirmModalAfterCheckAgain").modal('hide');
                    console.log("productId after confirm one more time: " + productIdGlobal);
                    confirmOrderAfterBuyerCheckAgain();
                });
            });

//Xác nhận đơn hàng sau khi kiểm tra lại lần 2 
            function confirmOrderAfterBuyerCheckAgain() {
                $.ajax({
                    url: 'updateorderstatusreport',
                    type: 'GET',
                    data: {
                        productIdByOrderId: productIdGlobal,
                        orderStt: 3
                    },
                    success: function (response) {
                        console.log("Ajax updating order status and seller wallet successfully!");
                        $("#successModalAfterCheckAgain").modal('show');
                    },
                    error: function () {
                        console.log("Error in updating order status");
                    }
                });
            }

            function confirmModalButton() {
                $("#confirmModal").modal('show');
                $("[id^='exampleModal']").modal('hide');
            }

            $(document).on("click", "[id^='report-order']", function () {
                $("[id^='exampleModal']").modal('hide');
                $('#reportModal').modal('show');
// Khi button trong modal được click
                $(document).on('click', '#confirmButtonReport', function () {
                    $('#reportModal').modal('hide');
                    $('#acceptedConfirmReport').modal('show');
// Thực hiện AJAX request với productIdGlobal
                    let orderStt = 4;
                    $.ajax({
                        url: 'updateorderstatusreport',
                        type: 'GET',
                        data: {
                            productId: productIdGlobal,
                            orderStt: orderStt
                        },
                        success: function (response) {
                            console.log("Ajax successfully when sending request to update order status (4)!");
                            $('#button-confirm-info').remove();
                            $('#button-report').remove();
                        },
                        error: function () {
                            console.log("Error in updating order status");
                        }
                    });
                });
            });

        </script>
    </body>
</html>
