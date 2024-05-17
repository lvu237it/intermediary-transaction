<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Yêu cầu rút tiền</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
            />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.1/js/dataTables.min.js"></script>
        <script src="https://cdn.rawgit.com/alvaro-prieto/colResizable/master/colResizable-1.6.min.js"></script>
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.1/css/dataTables.dataTables.css">
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
            <div class="container mb-5">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card-group mt-3">
                            <div class="card">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h3 class="m-0">Xử lí khiếu nại</h3>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="example" class="table table-bordered table-hover text-center">
                                            <thead class="text-center">
                                                <tr>
                                                    <th scope="col" >Mã khiếu nại</th>
                                                    <th scope="col" >Mẫ đơn cần khiếu nại</th>
                                                    <th scope="col" >Người khiếu nại</th>
                                                    <th scope="col" >Trạng thái xử lý</th>
                                                    <th scope="col" >Người mua</th>
                                                    <th scope="col" >Người bán</th>
                                                    <th scope="col" >Thời gian tạo</th>
                                                    <th scope="col" >Thời gian cập nhật</th>
                                                    <th scope="col" >Hành động</th>   
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Dữ liệu của bảng sẽ được thêm ở đây -->
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>         
                    </div>
                </div>
            </div>
        <jsp:include page="footer.jsp"></jsp:include>

        <!--Modal hiển thị chi tiết khiếu nại-->
        <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="detailModalLabel">Chi tiết khiếu nại</h5>
                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="detailModalBody">
                        <!-- Thông tin chi tiết sẽ được hiển thị ở đây -->
                    </div>
                    <div class="modal-footer">
                        <button id="oneBtn" class="btn btn-primary" data-action="2" data-value="2">Thực hiện trung gian</button>
                        <button id="twoBtn" class="btn btn-warning" data-action="3" data-value="3">Xác nhận bên mua đúng </button>
                        <button id="threeBtn" class="btn btn-success" data-action="4" data-value="4">Xác nhận bên bán đúng</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal hiển thị chi tiết người dùng-->
        <div class="modal fade" id="userModal" tabindex="-1" aria-labelledby="confirmRequestModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmRequestModalLabel">Thông tin người dùng</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="userModalBody">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal xác nhận hành động-->
        <div class="modal fade" id="confirmRequestModal" tabindex="-1" aria-labelledby="confirmRequestModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmRequestModalLabel">Xác nhận</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc muốn thực hiện điều này?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-primary btn-confirm">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal thông báo cập nhật thành công -->
        <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="successModalLabel">Thành công!</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Thực hiện thành công.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal thông báo thất bại -->
        <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="errorModalLabel">Thất bại!</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Đã xảy ra lỗi khi thực hiện. Vui lòng thử lại sau.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!--Modal thông tin chi tiết sản phẩm trong đơn bán-->
        <div id="openProductDetail" class="modal" tabindex="-1">
            <div class="modal-dialog modal-dialog-scrollable modal-lg">
                <div class="modal-content">
                    <div class="modal-body" id="productDetailModal">
                    </div>
                </div>
            </div>
        </div>

        <script>
            var customLanguage = {
                "sLengthMenu": "Hiển thị _MENU_ bản ghi mỗi trang",
                "sInfo": "Hiển thị _START_ đến _END_ trong tổng số _TOTAL_ bản ghi",
                "sInfoEmpty": "Hiển thị từ 0 đến 0 trong tổng số 0 bản ghi",
                "sInfoFiltered": "(đã lọc từ tổng số _MAX_ bản ghi)",
                "sZeroRecords": "Không tìm thấy bản ghi phù hợp",
                "sSearch": "Tìm kiếm:"
            };
            var table = null;
            $(document).ready(function () {
                var order = null;
                table = $('#example').DataTable({
                    "dom": 'lrtip',
                    "processing": true,
                    "serverSide": true,
                    "lengthMenu": [[5, 10, 15, 20, 25], [5, 10, 15, 20, 25]],
                    "language": customLanguage,
                    "ajax": {
                        "url": "reports-manage",
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
                        {"data": "id"},
                        {"data": "orderId"},
                        {"data": "createdByFullname"},
                        {"data": "status",
                            "render": function (data, type, row) {
                                switch (data) {
                                    case 1:
                                        return "Mới tạo";
                                    case 2:
                                        return "Tham gia trung gian";
                                    case 3:
                                        return "Bên mua đúng";
                                    case 4:
                                        return "Bên bán đúng";
                                }
                            }
                        },
                        {"data": "buyerFullname"},
                        {"data": "sellerFullname"},
                        {"data": "createdAt"},
                        {"data": "updatedAt"},
                        {
                            "data": "id",
                            "render": function (data, type, row) {
                                return '<button class="btn btn-info btn-detail" data-id="' + data + '">Chi tiết</button>';
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
            var reportId;
            var reportStatus;
            $('#example').on('click', '.btn-detail', function () {

                var id = $(this).data('id'); // Lấy ID từ thuộc tính data-id của nút
                // Gửi AJAX request để lấy dữ liệu
                $.ajax({
                    url: 'reportdetails',
                    type: 'POST',
                    data: {id: id}, // Gửi ID trong request
                    success: function (response) {
                        // Hiển thị dữ liệu trong modal
                        reportId = response.id;
                        reportStatus = response.status;
                        $('#detailModalBody').empty(); // Xóa nội dung cũ của modal body trước khi thêm mới
                        var detailHtml = '<div class="row">';
                        detailHtml += '<div class="col-md-12">';
                        detailHtml += '<div class="card-group">';
                        detailHtml += '<div class="card">';
                        detailHtml += '<div class="card-header">';
                        detailHtml += '<h3 class="mb-0"></h3>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="card-body">';
                        // ID
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Mã khiếu nại:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.id + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // orderId
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Mã đơn cần khiếu nại:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-6">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.orderId + '">';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-3">';
                        detailHtml += '<button class="btn btn-info btn-order-detail" data-id="' + response.orderId + '">Chi tiết đơn</button>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // createdBy
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Người tạo :</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.createdBy + '|' + response.createdByFullname + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Status
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Trạng thái xử lý:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        let statusText = '';
                        switch (response.status) {
                            case 1:
                                statusText = 'Mới tạo';
                                break;
                            case 2:
                                statusText = 'Tham gia trung gian';
                                break;
                            case 3:
                                statusText = 'Bên mua đúng';
                                break;
                            case 4:
                                statusText = 'Bên bán đúng';
                                break;
                            default:
                                statusText = response.status;
                        }
                        detailHtml += '<input type="text" class="form-control" disabled value="' + statusText + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        // Buyer
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Người mua:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-6">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.buyerId + '|' + response.buyerFullname + '">';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-3">';
                        detailHtml += '<button class="btn btn-info btn-user-detail" data-id="' + response.buyerId + '">Chi tiết</button>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Seller
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Người bán:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-6">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.sellerId + '|' + response.sellerFullname + '">';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-3">';
                        detailHtml += '<button class="btn btn-info btn-user-detail" data-id="' + response.sellerId + '">Chi tiết</button>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        //Description
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Chi tiết:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.description + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Created At
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Thời gian tạo:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.createdAt + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Updated At
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Cập nhật lần cuối:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.updatedAt + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '</div>'; // Kết thúc card-body
                        detailHtml += '</div>'; // Kết thúc card
                        detailHtml += '</div>'; // Kết thúc card-group
                        detailHtml += '</div>'; // Kết thúc col-md-12
                        detailHtml += '</div>'; // Kết thúc row

                        // Ẩn tất cả các nút trước khi hiển thị nút tương ứng với trạng thái
                        $('#oneBtn, #twoBtn, #threeBtn').hide();

                        // Hiển thị nút tương ứng với trạng thái của yêu cầu rút tiền
                        switch (response.status) {
                            case 1:
                                $('#oneBtn').show();
                                break;
                            case 2:
                                $('#twoBtn').show();
                                $('#threeBtn').show();
                                break;
                            default:
                                break;
                        }
                        $('#detailModalBody').append(detailHtml);
                        // Hiển thị modal
                        $('#detailModal').modal('show');
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi khi không thể lấy dữ liệu từ server
                        console.error(error);
                    }
                });
            });
            $('#detailModal').on('click', '.btn-order-detail', function () {
                var orderId = $(this).data('id'); // Lấy ID đơn hàng từ thuộc tính data-id của nút
                // Gọi hàm AJAX để lấy chi tiết đơn hàng
                $.ajax({
                    url: 'orderdetails',
                    type: 'POST',
                    data:
                            {orderId: orderId},
                    success: function (product) {
                        console.log("Ajax successfully when get information of product by productID (SellModalDetail");
                        console.log("product: ");
                        console.log(product);
                        $('#productDetailModal').empty(); // Xóa nội dung cũ của modal body trước khi thêm mới
                        let detailHtml = '';
                        console.log("ok");
                        detailHtml += '<div class="row">';
                        detailHtml += '<div class="col-md-12">';
                        detailHtml += '<div class="card-group">';
                        detailHtml += '<div class="card">';
                        detailHtml += '<div class="card-header">';
                        detailHtml += '<h3 class="mb-0">Thông tin mặt hàng trung gian</h3>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="card-body">';
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Chủ đề trung gian</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input disabled value="' + product.name + '" id="lname' + product.id + '" rows="2" class="form-control"/>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Giá tiền</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input disabled value="' + formatNumber(product.price) + ' VNĐ" id="lprice' + product.id + '" type="text" class="form-control" value="">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Bên chịu phí trung gian</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (product.feePayer === false) {
                            detailHtml += '<button value="0" disabled id="lbenban' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-success">BÊN BÁN</button>';
                            detailHtml += '<button value="1" disabled id="lbenmua' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-secondary">BÊN MUA</button>';
                        }
                        if (product.feePayer === true) {
                            detailHtml += '<button value="0" disabled id="lbenban' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-secondary">BÊN BÁN</button>';
                            detailHtml += '<button value="1" disabled id="lbenmua' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-success">BÊN MUA</button>';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Loại sản phẩm</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<select disabled class="form-select" id="ltype' + product.id + '">';
                        detailHtml += '<option value="">Chọn loại sản phẩm muốn đăng</option>';
                        if (product.type === "Tài Liệu") {
                            detailHtml += '<option selected value="tailieu">Tài liệu</option>';
                            detailHtml += '<option value="taikhoan">Tài khoản</option>';
                            detailHtml += '<option value="phanmem">Phần mềm</option>';
                            detailHtml += '<option value="khac">Khác</option>';
                        } else if (product.type === "Tài khoản") {
                            detailHtml += '<option value="tailieu">Tài liệu</option>';
                            detailHtml += '<option selected value="taikhoan">Tài khoản</option>';
                            detailHtml += '<option value="phanmem">Phần mềm</option>';
                            detailHtml += '<option value="khac">Khác</option>';
                        } else if (product.type === "Phần mềm") {
                            detailHtml += '<option value="tailieu">Tài liệu</option>';
                            detailHtml += '<option value="taikhoan">Tài khoản</option>';
                            detailHtml += '<option selected value="phanmem">Phần mềm</option>';
                            detailHtml += '<option value="khac">Khác</option>';
                        } else if (product.type === "Khác") {
                            detailHtml += '<option value="tailieu">Tài liệu</option>';
                            detailHtml += '<option value="taikhoan">Tài khoản</option>';
                            detailHtml += '<option value="phanmem">Phần mềm</option>';
                            detailHtml += '<option selected value="khac">Khác</option>';
                        }
                        detailHtml += '</select>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Mô tả</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<textarea disabled id="ldescription' + product.id + '" rows="2"  class="form-control"  >' + product.description + '</textarea>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Nội dung ẩn (chỉ hiển thị khi khi đã có người mua sản phẩm)</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<textarea disabled id="lhiddenField' + product.id + '" rows="2"  class="form-control"  >' + product.hiddenField + '</textarea>';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Phương thức liên hệ</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input disabled value="' + product.contact + '" id="lcontact' + product.id + '" type="text" class="form-control overflow-auto" placeholder="Số điện thoại/Zalo/Facebook/Telegram/Discord..." value="" >';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Link chia sẻ</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input disabled value="' + product.shareLink + '" id="lsharelink' + product.id + '" type="text" class="form-control overflow-auto" >';
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Hiện công khai</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (product.isPrivate === 1) {
                            detailHtml += '<button disabled id="lbuttonPrivate' + product.id + '" type="button" value="1"  class="btn btn-success">Chỉ ai có link mới xem được</button>';
                            detailHtml += '<button disabled id="lbuttonPublic' + product.id + '" type="button" value="0"  class="btn btn-secondary">Công khai</button>';
                        } else {
                            detailHtml += '<button disabled id="lbuttonPrivate' + product.id + '" type="button" value="1"  class="btn btn-secondary">Chỉ ai có link mới xem được</button>';
                            detailHtml += '<button disabled id="lbuttonPublic' + product.id + '" type="button" value="0"  class="btn btn-success">Công khai</button>';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        detailHtml += '</div>'; // đóng của thẻ card-body

                        detailHtml += '<div class="modal-footer">';
                        if (product.status === 4) {
                            detailHtml += '<button type="button" class="btn btn-success" id="informationFalse' + product.id + '" >Thông tin sai, xác nhận hủy đơn</button>';
                            detailHtml += '<button type="button" class="btn btn-warning" id="informationTrue' + product.id + '" >Thông tin đúng, yêu cầu bên mua kiểm tra lại</button>';
                        }
                        detailHtml += '</div>'; // đóng thẻ modal-footer
                        detailHtml += '</div>'; // đóng thẻ card
                        detailHtml += '</div>'; // đóng thẻ card group
                        detailHtml += '</div>'; // dóng thẻ col-md-12
                        detailHtml += '</div>'; // đóng thẻ row


                        $('#productDetailModal').html(detailHtml);

                        // Hiển thị modal
                        $('#openProductDetail').modal('show');
                    },
                    error: function (xhr) {
                        console.log("error: ");
                        console.log(xhr);
                    }
                });
            });
            var actionStatus = "";

            $('#detailModal').on('shown.bs.modal', function () {
                $('.btn-user-detail').on('click', function () {
                    var id = $(this).data('id'); // Lấy ID từ thuộc tính data-id của nút được nhấp

                    $.ajax({
                        url: 'userdetails',
                        type: 'POST',
                        data: {id: id},
                        success: function (response) {
                            $('#userModalBody').empty();
                            var detailHtml = '<div class="row">';
                            detailHtml += '<div class="col-md-12">';
                            detailHtml += '<div class="card-group">';
                            detailHtml += '<div class="card">';
                            detailHtml += '<div class="card-header">';
                            detailHtml += '<h3 class="mb-0"></h3>';
                            detailHtml += '</div>';
                            detailHtml += '<div class="card-body">';

                            // ID
                            detailHtml += '<div class="position-relative row form-group">';
                            detailHtml += '<div class="form-label-horizontal col-md-3">';
                            detailHtml += '<label><b>ID:</b></label>';
                            detailHtml += '</div>';
                            detailHtml += '<div class="col-md-9">';
                            detailHtml += '<input type="text" class="form-control" disabled value="' + response.userId + '">';
                            detailHtml += '</div>';
                            detailHtml += '</div>';

                            // Fullname
                            detailHtml += '<div class="position-relative row form-group">';
                            detailHtml += '<div class="form-label-horizontal col-md-3">';
                            detailHtml += '<label><b>Họ tên: </b></label>';
                            detailHtml += '</div>';
                            detailHtml += '<div class="col-md-9">';
                            detailHtml += '<input type="text" class="form-control" disabled value="' + response.fullName + '">';
                            detailHtml += '</div>';
                            detailHtml += '</div>';

                            // Wallet
                            detailHtml += '<div class="position-relative row form-group">';
                            detailHtml += '<div class="form-label-horizontal col-md-3">';
                            detailHtml += '<label><b>Số dư: </b></label>';
                            detailHtml += '</div>';
                            detailHtml += '<div class="col-md-9">';
                            detailHtml += '<input type="text" class="form-control" disabled value="' + formatNumber(response.wallet) + ' VNĐ">';
                            detailHtml += '</div>';
                            detailHtml += '</div>';

                            // Gmail
                            detailHtml += '<div class="position-relative row form-group">';
                            detailHtml += '<div class="form-label-horizontal col-md-3">';
                            detailHtml += '<label><b>Email:</b></label>';
                            detailHtml += '</div>';
                            detailHtml += '<div class="col-md-9">';
                            detailHtml += '<input type="text" class="form-control" disabled value="' + response.gmail + '">';
                            detailHtml += '</div>';
                            detailHtml += '</div>';

                            // Phone Number
                            detailHtml += '<div class="position-relative row form-group">';
                            detailHtml += '<div class="form-label-horizontal col-md-3">';
                            detailHtml += '<label><b>Số điện thoại:</b></label>';
                            detailHtml += '</div>';
                            detailHtml += '<div class="col-md-9">';
                            detailHtml += '<input type="text" class="form-control" disabled value="' + response.phoneNumber + '">';
                            detailHtml += '</div>';
                            detailHtml += '</div>';

                            detailHtml += '</div>'; // Kết thúc card-body
                            detailHtml += '</div>'; // Kết thúc card
                            detailHtml += '</div>'; // Kết thúc card-group
                            detailHtml += '</div>'; // Kết thúc col-md-12
                            detailHtml += '</div>'; // Kết thúc row

                            $('#userModalBody').append(detailHtml);
                            // Hiển thị modal
                            $('#userModal').modal('show');
                        },
                        error: function (xhr, status, error) {
                            // Xử lý lỗi nếu có
                        }
                    });
                });
            });
            // Thêm sự kiện click cho các nút chức năng
            $('#oneBtn, #twoBtn, #threeBtn').on('click', function () {
                // Lưu hành động được chọn vào biến selectedAction từ thuộc tính data-action
                actionStatus = $(this).data('action');
                // Mở modal xác nhận
                $('#confirmRequestModal').modal('show');
            });

            // Thêm sự kiện click cho nút xác nhận trong modal xác nhận
            $('.btn-confirm').on('click', function () {
                // Ẩn modal xác nhận
                $('#confirmRequestModal').modal('hide');
                // Gửi Ajax với hành động đã được chọn
                $.ajax({
                    url: 'handlereport',
                    type: 'POST',
                    data: {
                        actionStatus: actionStatus,
                        id: reportId,
                        status: reportStatus
                    },
                    success: function (response) {
                        if (response.action === true) {
                            $('#successModal').modal('show');
                        } else if (response.action === false) {
                            $('#errorModal').modal('show');
                        }
                        $('#detailModal').modal('hide');
                        table.draw();
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi
                        $('#detailModal').modal('hide');
                    }
                });
            });
            function formatNumber(num) {
                return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }

        </script>       
    </body>
</html>
