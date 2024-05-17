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
    $('#request .btn-success').click(function () {
        var withdrawAmount = $('#withdrawAmountInput').val();
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
        // Hiển thị modal "Đang chờ xử lý"
        // $('#processingModal').modal('show');
        // Lấy dữ liệu từ các trường input
        var withdrawAmount = removeCommas($('#withdrawAmountInput').val());
        var bankAccountNumber = $('#bankAccountNumberInput').val();
        var bankAccountName = $('#bankAccountNameInput').val();
        var bankName = $('#bankNameInput').val();
        // Thực hiện kiểm tra dữ liệu ở đây (ví dụ: kiểm tra xem các trường có rỗng không)

        // Gửi dữ liệu đến máy chủ bằng AJAX
        $.ajax({
            url: 'createwithdraw', // Thay đổi thành URL của máy chủ của bạn
            type: 'POST',
            data: {
                withdrawAmount: withdrawAmount,
                bankAccountNumber: bankAccountNumber,
                bankAccountName: bankAccountName,
                bankName: bankName
            },
            dataType: 'json',
            success: function (response) {
                // $('#processingModal').modal('hide'); // Ẩn modal "Đang chờ xử lý"
                // Kiểm tra giá trị của response.action
                if (response.action === true) { // Kiểm tra là chuỗi "true"
                    $('#successModal').modal('show');
                } else if (response.action === false) { // Kiểm tra là chuỗi "false"
                    $('#errorModal').modal('show');
                } else {
                    // Nếu response.action không phải là chuỗi "true" hoặc "false"
                    console.error("Invalid value for response.action:", response.action);
                }
                // Đóng modal sau khi gửi dữ liệu thành công
                $('#request').modal('hide');
                // Làm mới bảng dữ liệu hoặc thực hiện các thao tác cần thiết khác
                table.draw();
            },
            error: function (xhr, status, error) {
                // Xử lý lỗi khi không thể gửi dữ liệu đến máy chủ
                // Hiển thị modal thông báo thất bại
                // $('#processingModal').modal('hide');
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


