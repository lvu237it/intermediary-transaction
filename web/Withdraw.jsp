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
                                    <h3 class="m-0">Yêu cầu Rút Tiền</h3>
                                    <button class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#request">Tạo yêu cầu rút tiền mới</button>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="example" class="table table-bordered table-hover text-center">
                                            <thead class="text-center">
                                                <tr>
                                                    <th scope="col">Mã Yêu cầu</th>
                                                    <th scope="col">Trạng thái xử lý</th>
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
        <!--Modal cho việc yêu cầu rút tiền-->
        <div id="request" class="modal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Yêu cầu rút tiền</h5>
                    </div>
                    <div class="modal-body">
                        <div autocomplete="off">
                            <div class="position-relative row form-group">
                                <div class="form-label-horizontal col-md-4">
                                    <label >
                                        <p>Số tiền rút (*) </p>
                                    </label>
                                </div>
                                <div class="col-md-8">
                                    <input id="withdrawAmountInput" type="text" class="form-control" value="" oninput="formatCurrency(this)">
                                    <div></div>
                                </div>
                            </div>
                            <div class="position-relative row form-group">
                                <div class="form-label-horizontal col-md-4">
                                    <label >
                                        <p>Số tài khoản (*) </p>
                                    </label>
                                </div>
                                <div class="col-md-8">
                                    <input id="bankAccountNumberInput" type="text" class="form-control" value="" >
                                </div>
                            </div>
                            <div class="position-relative row form-group">
                                <div class="form-label-horizontal col-md-4">
                                    <label >
                                        <p>Chủ tài khoản (*) </p>
                                    </label>
                                </div>
                                <div class="col-md-8">
                                    <input id="bankAccountNameInput" type="text" class="form-control" value="" >
                                </div>
                            </div>
                            <div class="position-relative row form-group">
                                <div class="form-label-horizontal col-md-4">
                                    <label >
                                        <p>Tên ngân hàng (*) </p>
                                    </label>
                                </div>
                                <div class="col-md-8">
                                    <input id="bankNameInput" type="text" class="form-control" value="" placeholder="Ex: MBBank(MB), VietcomBank(VCB)">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-success" >Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
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
                        <p></p>
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
                        <p>Yêu cầu của bạn đã được xử lý thành công.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal thông báo cập nhật thành công -->
        <div class="modal fade" id="successUpdateModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="successModalLabel">Thành công!</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Cập nhật thành công.</p>
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
                        "url": "withdraw",
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
                        {"data": "withdrawAmount"},
                        {"data": "bankAccountNumber"},
                        {"data": "bankAccountName"},
                        {"data": "bankName"},
                        {
                            "data": "response",
                            "render": function (data, type, row) {
                                return data ? data : "";
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
                $('#request .btn-success').click(function () {
                    // Lấy dữ liệu từ các trường input
                    var withdrawAmount = removeCommas($('#withdrawAmountInput').val());
                    var bankAccountNumber = $('#bankAccountNumberInput').val();
                    var bankAccountName = $('#bankAccountNameInput').val();
                    var bankName = $('#bankNameInput').val();

                    // Kiểm tra xem các trường thông tin có được điền đầy đủ hay không
                    if (withdrawAmount === '' || bankAccountNumber === '' || bankAccountName === '' || bankName === '') {
                        alert('Vui lòng điền đầy đủ thông tin.');
                        return;
                    }

                    // Kiểm tra xem số tiền nhập vào có phải là số không
                    if (isNaN(withdrawAmount)) {
                        alert('Số tiền phải là một số.');
                        return;
                    }

                    // Kiểm tra xem số tiền nhập vào có lớn hơn hoặc bằng 100,000 không
                    if (parseInt(withdrawAmount) < 100000) {
                        alert('Số tiền phải lớn hơn hoặc bằng 100,000 đồng.');
                        return;
                    }

                    // Nếu thông tin hợp lệ, tiếp tục quá trình gửi yêu cầu
                    // Hiển thị giá trị số tiền rút vào modal xác nhận
                    $('#withdrawAmount').text(withdrawAmount);
                    // Cập nhật nội dung của phần body trong modal xác nhận
                    $('#confirmRequestModal .modal-body p').text('Bạn có chắc chắn muốn gửi yêu cầu rút tiền số tiền ' + withdrawAmount + ' vào tài khoản?');
                    // Hiển thị modal xác nhận trước khi gửi yêu cầu
                    $('#confirmRequestModal').modal('show');
                });

                // Xử lý sự kiện khi nhấn nút "Xác nhận" trong modal xác nhận
                $('#confirmRequestModal .btn-confirm').click(function () {
                    // Ẩn modal xác nhận
                    $('#confirmRequestModal').modal('hide');
                    // Lấy dữ liệu từ các trường input
                    var withdrawAmount = removeCommas($('#withdrawAmountInput').val());
                    var bankAccountNumber = $('#bankAccountNumberInput').val();
                    var bankAccountName = $('#bankAccountNameInput').val();
                    var bankName = $('#bankNameInput').val();


                    $.ajax({
                        url: 'createwithdraw',
                        type: 'POST',
                        data: {
                            withdrawAmount: withdrawAmount,
                            bankAccountNumber: bankAccountNumber,
                            bankAccountName: bankAccountName,
                            bankName: bankName
                        },
                        dataType: 'json',
                        success: function (response) {
                            // Kiểm tra giá trị của response.action
                            if (response.action === true) {
                                $('#successModal').modal('show');
                            } else if (response.action === false) {
                                $('#errorModal').modal('show');
                            } else {
                                console.error("Invalid value for response.action:", response.action);
                            }
                            // Đóng modal sau khi gửi dữ liệu thành công
                            $('#request').modal('hide');
                            // Làm mới bảng dữ liệu hoặc thực hiện các thao tác cần thiết khác
                            table.draw();
                        },
                        error: function (xhr, status, error) {
                            console.error(error);
                        }
                    });
                });
            });
            $('#example').on('click', '.btn-detail', function () {

                var id = $(this).data('id'); // Lấy ID từ thuộc tính data-id của nút
                // Gửi AJAX request để lấy dữ liệu
                $.ajax({
                    url: 'withdrawdetails',
                    type: 'POST',
                    data: {id: id}, // Gửi ID trong request
                    success: function (response) {
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
                        // Withdraw Amount
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Số tiền rút:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.withdrawAmount + '">';
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Bank Account Number
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Số tài khoản ngân hàng:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (response.status === 1) {
                            detailHtml += '<input id = "bankAccountNumber" type="text" class="form-control" value="' + response.bankAccountNumber + '">';
                        } else {
                            detailHtml += '<input id = "bankAccountNumber" type="text" class="form-control" disabled value="' + response.bankAccountNumber + '">';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        // Bank Account Name
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Tên chủ tài khoản:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (response.status === 1) {
                            detailHtml += '<input id = "bankAccountName" type="text" class="form-control" value="' + response.bankAccountName + '">';
                        } else {
                            detailHtml += '<input id = "bankAccountName" type="text" class="form-control" disabled value="' + response.bankAccountName + '">';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        // Bank Name
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Ngân hàng:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        if (response.status === 1) {
                            detailHtml += '<input id = "bankName" type="text" class="form-control" value="' + response.bankName + '">';
                        } else {
                            detailHtml += '<input id = "bankName" type="text" class="form-control" disabled value="' + response.bankName + '">';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';
                        // Response
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                        detailHtml += '<label><b>Phản hồi:</b></label>';
                        detailHtml += '</div>';
                        detailHtml += '<div class="col-md-9">';
                        detailHtml += '<input type="text" class="form-control" disabled value="' + response.response + '">';
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
                        // Thêm nút "Cập nhật" vào modal chi tiết và chỉ hiển thị khi status là 1
                        detailHtml += '<div class="position-relative row form-group">';
                        detailHtml += '<div class="col-md-12">';
                        if (response.status === 1) {
                            detailHtml += '<button class="btn btn-primary btn-update" data-id="' + response.id + '">Cập nhật</button>';
                        }
                        detailHtml += '</div>';
                        detailHtml += '</div>';

                        detailHtml += '</div>'; // Kết thúc card-body
                        detailHtml += '</div>'; // Kết thúc card
                        detailHtml += '</div>'; // Kết thúc card-group
                        detailHtml += '</div>'; // Kết thúc col-md-12
                        detailHtml += '</div>'; // Kết thúc row

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
            $(document).on('click', '.btn-update', function () {
                var id = $(this).data('id'); // Lấy ID từ thuộc tính data-id của nút

                // Lấy giá trị mới của các trường chính sửa
                var bankAccountNumber = $('#bankAccountNumber').val();
                var bankAccountName = $('#bankAccountName').val();
                var bankName = $('#bankName').val();

                // Kiểm tra xem các trường thông tin có được điền đầy đủ hay không
                if (bankAccountNumber === '' || bankAccountName === '' || bankName === '') {
                    alert('Vui lòng điền đầy đủ thông tin.');
                    return;
                }
                // Gửi AJAX request để cập nhật thông tin
                $.ajax({
                    url: 'updatewithdrawal',
                    type: 'POST',
                    data: {
                        id: id,
                        bankAccountNumber: bankAccountNumber,
                        bankAccountName: bankAccountName,
                        bankName: bankName
                    },
                    dataType: 'json',
                    success: function (response) {
                        if (response.action === true) {
                            $('#successUpdateModal').modal('show');
                        } else {
                            $('#errorModal').modal('show');
                        }
                        $('#detailModal').modal('hide');
                        table.draw();
                    },
                    error: function (xhr, status, error) {
                        console.error(error);
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

        </script>       
    </body>
</html>