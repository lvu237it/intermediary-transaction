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
        <title>Quản lí yêu cầu rút tiền</title>
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.3/css/lightbox.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.3/js/lightbox.min.js"></script>
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
                                    <h3 class="m-0">Quản lý rút tiền</h3>                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="example" class="table table-bordered table-hover text-center">
                                            <thead class="text-center">
                                                <tr>
                                                    <th scope="col">Mã Yêu cầu</th>
                                                    <th scope="col">Trạng thái xử lý</th>
                                                    <th scope="col">Người tạo</th>
                                                    <th scope="col">Số tiền rút</th>
                                                    <th scope="col">Số tài khoản</th>
                                                    <th scope="col">Chủ tài khoản</th>
                                                    <th scope="col">Tên ngân hàng</th>
                                                    <th scope="col">Phản hồi</th>
                                                    <th scope="col">Thời gian tạo</th>                 
                                                    <th scope="col">Cập nhật cuối</th>   
                                                    <th scope="col">Hành động</th>    
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
        <!--Modal hiển thị chi tiết-->
        <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="detailModalLabel">Chi tiết Yêu cầu Rút Tiền</h5>
                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="detailModalBody">
                        <!-- Thông tin chi tiết yêu cầu rút tiền sẽ được hiển thị ở đây -->
                    </div>
                    <div class="modal-footer">
                        <button id="transferBtn" class="btn btn-primary" data-action="2" data-value="2">Thực hiện chuyển khoản</button>
                        <button id="rejectBtn" class="btn btn-danger" data-action="4" data-value="4">Từ chối</button>
                        <button id="completeBtn" class="btn btn-success" data-action="3" data-value="3">Hoàn thành chuyển khoản</button>
                        <button id="errorBtn" class="btn btn-warning" data-action="5" data-value="5">Lỗi chuyển khoản</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal xác nhận yêu cầu -->
        <div class="modal fade" id="confirmRequestModal" tabindex="-1" aria-labelledby="confirmRequestModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmRequestModalLabel">Xác nhận yêu cầu</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn thực hiện việc này?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-primary btn-confirm">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal cho trạng thái "Đang chờ xử lý" -->
        <div class="modal fade" id="processingModal" tabindex="-1" aria-labelledby="processingModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body text-center">
                        <p>Đang xử lý...</p>
                        <div class="spinner-border" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal thông báo thành công -->
        <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="successModalLabel">Thành công!</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Xử lý thành công.</p>
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
                        <p>Đã xảy ra lỗi khi xử lý yêu cầu của bạn. Vui lòng thử lại sau.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
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
                        "url": "withdraw-manage",
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
                        {"data": "status",
                            "render": function (data, type, row) {
                                switch (data) {
                                    case 1:
                                        return "Mới tạo";
                                    case 2:
                                        return "Chờ chuyển khoản";
                                    case 3:
                                        return "Hoàn thành";
                                    case 4:
                                        return "Bị từ chối";
                                    case 5:
                                        return "Bị lỗi";
                                }
                            }
                        },
                        {"data": "user.fullName"},
                        {"data": "withdrawAmount"},
                        {"data": "bankAccountNumber"},
                        {"data": "bankAccountName"},
                        {"data": "bankName"},
                        {
                            "data": "response",
                            "render": function (data, type, row) {
                                return data ? data : ""; // Nếu data không null thì trả về data, ngược lại trả về chuỗi trống
                            }
                        },
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
                const detailModal = new bootstrap.Modal(document.getElementById('detailModal'));
                $(document).on('click', '#detailModal .btn-close', function () {
                    detailModal.hide();
                });
            });
            var withdrawRequestId;
            $('#example').on('click', '.btn-detail', function () {
                var id = $(this).data('id'); // Lấy ID từ thuộc tính data-id của nút
                // Gửi AJAX request để lấy dữ liệu
                $.ajax({
                    url: 'withdrawdetails',
                    type: 'POST',
                    data: {id: id}, // Gửi ID trong request
                    success: function (response) {
                        withdrawRequestId = response.id;
                        responseWithdraw = response.response;
                        // Hiển thị dữ liệu trong modal
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
                        detailHtml += '<label><b>ID:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.id + '">';
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
                                statusText = 'Chờ chuyển khoản';
                                break;
                            case 3:
                                statusText = 'Hoàn thành';
                                break;
                            case 4:
                                statusText = 'Bị từ chối';
                                break;
                            case 5:
                                statusText = 'Bị lỗi';
                                break;
                            default:
                                statusText = response.status;
                        }
                        detailHtml += '<input type="text" class="form-control" disabled value="' + statusText + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // User Fullname
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Người tạo:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.user.fullName + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
// Withdraw Amount
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Số tiền rút:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + formatNumber(response.withdrawAmount) + ' VNĐ">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
// Bank Account Number
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Số tài khoản ngân hàng:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.bankAccountNumber + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
// Bank Account Name
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Tên chủ tài khoản:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.bankAccountName + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
// Bank Name
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Ngân hàng:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.bankName + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
// Response
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Phản hồi:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (response.status === 2 || response.status === 1) {
                            detailHtml += '<input type="text" class="form-control response-input" value="' + (response.response || '') + '">';
                        } else {
                            detailHtml += '<input type="text" class="form-control" disabled value="' + (response.response || '') + '">';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Evident
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Bằng chứng:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        // Kiểm tra nếu response.status bằng 2 hoặc 1
                        if (response.status === 2 || response.status === 1) {
                            detailHtml += '<input type="file" class="form-control-file" id="imageInput">';
                        } else {
                            detailHtml += '<a href="' + response.evident + '" data-lightbox="evident-image">';
                            detailHtml += '<img src="' + response.evident + '" class="img-fluid evidence-img" alt="Evident Image">';
                            detailHtml += '</a>';
                        }
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

                        $('#detailModalBody').append(detailHtml);
                        // Ẩn tất cả các nút trước khi hiển thị nút tương ứng với trạng thái
                        $('#transferBtn, #rejectBtn, #completeBtn, #errorBtn').hide();

                        // Hiển thị nút tương ứng với trạng thái của yêu cầu rút tiền
                        switch (response.status) {
                            case 1:
                                $('#transferBtn').show();
                                $('#rejectBtn').show();
                                break;
                            case 2:
                                $('#completeBtn').show();
                                $('#errorBtn').show();
                                break;
                            default:
                                break;
                        }
                        // Hiển thị modal
                        $('#detailModal').modal('show');
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi khi không thể lấy dữ liệu từ server
                        console.error(error);
                    }
                });
            });
            var selectedAction = "";
            var responseWithdraw = "";
            var imgUrl = "";

            $('#detailModal').on('shown.bs.modal', function () {
                $('.response-input').on('change', function () {
                    responseWithdraw = $(this).val();
                });
                $('#imageInput').on('change', function () {
                    var file = this.files[0];
                    if (file) {
                        // Kiểm tra loại file
                        var validTypes = ['image/jpeg', 'image/png', 'image/gif'];
                        if (validTypes.includes(file.type)) {
                            // Hình ảnh hợp lệ, tiến hành tải lên Cloudinary
                            var formData = new FormData();
                            formData.append('file', file);
                            formData.append('upload_preset', 'upload-withdrawal'); // Thay thế bằng upload preset của bạn

                            $.ajax({
                                url: 'https://api.cloudinary.com/v1_1/drb41o4sh/image/upload',
                                method: 'POST',
                                data: formData,
                                processData: false,
                                contentType: false,
                                success: function (data) {
                                    console.log('Upload successful');
                                    imgUrl = data.secure_url;
                                },
                                error: function (error) {
                                    console.error('Error:', error);
                                    alert('An error occurred while uploading the image.');
                                }
                            });
                        } else {
                            // Loại file không hợp lệ
                            alert('Please select a valid image file (JPG, PNG, GIF).');
                        }
                    }
                });
                
            });
            // Thêm sự kiện click cho các nút chức năng
            $('#transferBtn, #rejectBtn, #completeBtn, #errorBtn').on('click', function () {
                // Lưu hành động được chọn vào biến selectedAction từ thuộc tính data-action
                selectedAction = $(this).data('action');
                // Mở modal xác nhận
                $('#confirmRequestModal').modal('show');
            });

            // Thêm sự kiện click cho nút xác nhận trong modal xác nhận
            $('.btn-confirm').on('click', function () {
                // Ẩn modal xác nhận
                $('#confirmRequestModal').modal('hide');
                // Gửi Ajax với hành động đã được chọn và withdrawRequestId
                $.ajax({
                    url: 'handlewithdraw',
                    type: 'POST',
                    data: {
                        selectedAction: selectedAction,
                        withdrawRequestId: withdrawRequestId,
                        responseWithdraw: responseWithdraw,
                        imgUrl: imgUrl
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
            function removeCommas(input) {
                return input.replace(/,/g, '');
            }
            function formatCurrency(input) {
                // Xóa tất cả các dấu phẩy trước khi định dạng
                let value = input.value.replace(/\,/g, '');
                // Định dạng lại số tiền thành chuỗi có dấu phẩy phân cách hàng nghìn
                value = value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                // Gán giá trị đã định dạng trở lại vào trường input
                input.value = value;
            }
            function formatNumber(num) {
                return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
            
        </script>    
    </body>
</html>