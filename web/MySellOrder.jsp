
<%-- 
    Document   : MySellOrder
    Created on : Feb 17, 2024, 11:02:54 PM
    Author     : HP
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${sessionScope.account == null}">
    <c:redirect url="login"/> <!-- Chuyển hướng đến trang login nếu session không tồn tại -->
</c:if>      

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đơn bán của tôi</title>
        <link rel="icon" href="./logo_icon_blabla/ITS_logo_4.jpg">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
        <!--<link rel="stylesheet" href="./style_CSS/publicMarket.css">-->
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <link rel="stylesheet" href="./style_CSS/mysellorder.css">
        <script src="https://cdn.tiny.cloud/1/bx3vwiu5lz8ije9a243zzdi3n6s24zrhj5lz4omwryza2mai/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
        <script src="https://cdn.datatables.net/2.0.1/js/dataTables.min.js"></script>
        <script src="https://cdn.rawgit.com/alvaro-prieto/colResizable/master/colResizable-1.6.min.js"></script>
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.1/css/dataTables.dataTables.css">
        <style>
            .position-relative{
                margin-bottom: 20px
            }
            #sellProduct td {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            #sellProduct th {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                text-align: center;
            }
        </style>

        <script>
            tinymce.init({
                selector: 'p'
            });
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <!-- Header -->
        <div class=" row d-flex mt-5 mx-5 mb-4" style="margin: auto" >
            <div class="col">
                <h3>Đơn bán của tôi</h3>
            </div>
            <div class="col-md-3 text-center">
                <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#addProductModal" >Thêm mới</button>
            </div>
        </div>
        <div class="container-fluid" style="margin: auto">
            <table class="table table-responsive table-bordered table-hover text-center" id="sellProduct">
                <thead class="text-center">
                    <tr>
                        <th scope="col" >Mã trung gian</th>
                        <th scope="col" >Chủ đề trung gian</th>
                        <th scope="col" >Trạng thái</th>
                        <th scope="col">Người mua</th>
                        <th scope="col" >Phương thức liên hệ</th>
                        <th scope="col">Công khai/Riêng tư</th>
                        <th scope="col" >Giá tiền</th>
                        <th scope="col" >Bên chịu phí</th>                 
                        <th scope="col" >Loại sản phẩm</th>                 
                        <th scope="col" >Hành động</th>          
                    </tr>
                </thead>
                <tbody id="tablebody">

                </tbody>
            </table>
        </div>

        <div id="addProductModal" class="modal" tabindex="-1">
            <div class="modal-dialog modal-dialog-scrollable modal-lg">
                <div class="modal-content">
                    <div class="modal-body" id="productDetail">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card-group">
                                    <div class="card">
                                        <div class="card-header">
                                            <h3 class="mb-0">Thông tin mặt hàng trung gian</h3>
                                        </div>
                                        <div class="card-body">
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Chủ đề trung gian</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <input id="name" rows="2" class="form-control"/>
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Giá tiền</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <input id="price" type="text" class="form-control" value="">
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Bên chịu phí trung gian</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <button id="buttonBenBan" type="button" value="0"  class="btn btn-secondary">BÊN BÁN</button>
                                                    <button id="buttonBenMua" type="button" value="1"  class="btn btn-secondary">BÊN MUA</button>
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Loại sản phẩm</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <select class="form-select" id="type">
                                                        <option selected>Chọn loại sản phẩm muốn đăng</option>
                                                        <option value="tailieu">Tài liệu</option>
                                                        <option value="taikhoan">Tài khoản</option>
                                                        <option value="phanmem">Phần mềm</option>
                                                        <option value="khac">Khác</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Mô tả</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <textarea id="description" rows="2"  class="form-control"  ></textarea>
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Nội dung ẩn (chỉ hiển thị khi khi đã có người mua sản phẩm)</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <textarea id="hiddenField" rows="2"  class="form-control"  ></textarea>
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Phương thức liên hệ</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <input id="contact" type="text" class="form-control overflow-auto" placeholder="Số điện thoại/Zalo/Facebook/Telegram/Discord..." value="" >
                                                </div>
                                            </div>
                                            <div class="position-relative row form-group">
                                                <div class="form-label-horizontal col-md-3">
                                                    <label><b>Hiện công khai</b></label>
                                                </div>
                                                <div class="col-md-9">
                                                    <button id="privateButton" type="button" value="1"  class="btn btn-secondary">Chỉ ai có link mới xem được</button>
                                                    <button id="publicButton" type="button" value="0"  class="btn btn-secondary">Công khai</button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                            <button type="button" class="btn btn-success" id="addNewProduct" onclick="">Thêm</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal xác nhận huỷ đơn-->
        <div class="modal fade" id="confirmCancel" tabindex="-1" aria-labelledby="confirmCancelLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="confirmCancelLabel">Xác nhận huỷ đơn hàng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Bạn có chắc chắn muốn huỷ đơn hàng?</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="button" class="btn btn-success" id="acceptConfirmCancel">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal huỷ đơn thành công-->
        <div class="modal fade" id="cancelSuccessfull" tabindex="-1" aria-labelledby="acceptLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="acceptLabel">Xác nhận huỷ đơn hàng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Huỷ đơn hàng thành công.</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal Thông tin đúng, yêu cầu bên mua kiểm tra lại-->
        <div class="modal fade" id="informationOK" tabindex="-1" aria-labelledby="informationOKLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="informationOKLabel">Xác nhận thông tin đúng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Bạn chắc chắn rằng thông tin đã chính xác và yêu cầu bên mua kiểm tra lại?</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="button" class="btn btn-success" id="acceptInformationOK">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal chắc chắn thông tin đúng, bên mua kiểm tra lại-->
        <div class="modal fade" id="acceptInformationSuccess" tabindex="-1" aria-labelledby="acceptInformationSuccessLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="acceptInformationSuccessLabel">Xác nhận thông tin đúng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Xác nhận thành công và chờ phản hồi từ bên mua.</div>
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
        <!--Modal yêu cầu admin giải quyết-->
        <div class="modal fade" id="requireAdminHandle" tabindex="-1" aria-labelledby="c" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="requireAdminHandleLabel">Yêu cầu giải quyết khiếu nại</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>Bạn chắc chắc muốn yêu cầu admin giải quyết khiếu nại do nghi ngờ bên mua chơi xấu?</div>
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

            let isBenBanSelected = false;
            let isPublicSelected = false;
            let isBenBanUpdateSelected = false;
            let isPublicUpdateSelected = false;
            // datatable
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
                var table = $('#sellProduct').DataTable({
                    "dom": 'lrtip',
                    "processing": true,
                    "serverSide": true,
                    "lengthMenu": [[5, 10, 15, 20, 25], [5, 10, 15, 20, 25]],
                    "language": customLanguage,
                    "ajax": {
                        "url": "sell",
                        "type": "GET",
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
                        {"data": "orderId"},
                        {"data": "name"},
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
                                        return "Hủy đơn hàng - Bên bán xác nhận thông tin đơn hàng không chính xác";
                                    case 7:
                                        return "Yêu cầu khiếu nại đang được Admin giải quyết";
                                }
                            }
                        },
                        {"data": "buyerName"},
                        {"data": "contact"},
                        {"data": "isPrivate",
                            "render": function (data, type, row) {
                                switch (data) {
                                    case 0:
                                        return "Công khai";
                                    case 1:
                                        return "Riêng tư";
                                }
                            }
                        },
                        {"data": "price"},
                        {"data": "feePayer",
                            "render": function (data, type, row) {
                                switch (data) {
                                    case false:
                                        return "Bên bán";
                                    case true:
                                        return "Bên mua";
                                }
                            }
                        },
                        {"data": "type"},
                        {
                            "data": "id",
                            "render": function (data, type, row) {
                                return '<button type="button" id="button-Detail' + data + '" class="btn btn-success" data-id="' + data + '">Chi tiết</button>';
                            }
                        }
                    ]
                });
                table.on('init.dt', function () {
                    order = table.order();
                });

                $("#sellProduct").colResizable({
                    liveDrag: true,
                    gripInnerHtml: "<div class='grip'></div>",
                    draggingClass: "dragging",
                    resizeMode: 'fit'
                });

                $(document).on('click', "[id^=button-Detail]", function () {
                    var productId = $(this).data('id'); // Lấy giá trị của thuộc tính data-id
                    // Gửi productId đến máy chủ qua Ajax
                    console.log("productID: " + productId);
                    $.ajax({
                        url: 'sellmodaldetail',
                        type: 'GET',
                        data:
                                {productId: productId}, // Truyền productId như một tham số
                        success: function (product) {
                            console.log("Ajax successfully when get information of product by productID (SellModalDetail");
                            console.log("product: ");
                            console.log(product);
                            $('#productDetailModal').empty(); // Xóa nội dung cũ của modal body trước khi thêm mới
                            let detailHtml = '';
                            if (product.status === 1) {
                                //Trạng thái sẵn sàng giao dịch
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
                                detailHtml += '<input value="' + product.name + '" id="lname' + product.id + '" rows="2" class="form-control"/>';
                                detailHtml += '</div>';
                                detailHtml += '</div>';

                                detailHtml += '<div class="position-relative row form-group">';
                                detailHtml += '<div class="form-label-horizontal col-md-3">';
                                detailHtml += '<label><b>Giá tiền</b></label>';
                                detailHtml += '</div>';
                                detailHtml += '<div class="col-md-9">';
                                detailHtml += '<input value="' + product.price + '" id="lprice' + product.id + '" type="text" class="form-control" value="">';
                                detailHtml += '</div>';
                                detailHtml += '</div>';

                                detailHtml += '<div class="position-relative row form-group">';
                                detailHtml += '<div class="form-label-horizontal col-md-3">';
                                detailHtml += '<label><b>Bên chịu phí trung gian</b></label>';
                                detailHtml += '</div>';
                                detailHtml += '<div class="col-md-9">';
                                if (product.feePayer === false) {
                                    detailHtml += '<button value="0" id="lbenban' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-success">BÊN BÁN</button>';
                                    detailHtml += '<button value="1" id="lbenmua' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-secondary">BÊN MUA</button>';
                                }
                                if (product.feePayer === true) {
                                    detailHtml += '<button value="0" id="lbenban' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-secondary">BÊN BÁN</button>';
                                    detailHtml += '<button value="1" id="lbenmua' + product.id + '" data-product-id="' + product.id + '" type="button" class="btn btn-success">BÊN MUA</button>';
                                }
                                detailHtml += '</div>';
                                detailHtml += '</div>';

                                detailHtml += '<div class="position-relative row form-group">';
                                detailHtml += '<div class="form-label-horizontal col-md-3">';
                                detailHtml += '<label><b>Loại sản phẩm</b></label>';
                                detailHtml += '</div>';
                                detailHtml += '<div class="col-md-9">';
                                detailHtml += '<select class="form-select" id="ltype' + product.id + '">';
                                detailHtml += '<option>Chọn loại sản phẩm muốn đăng</option>';
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
                                detailHtml += '<textarea id="ldescription' + product.id + '" rows="2"  class="form-control"  >' + product.description + '</textarea>';
                                detailHtml += '</div>';
                                detailHtml += '</div>';

                                detailHtml += '<div class="position-relative row form-group">';
                                detailHtml += '<div class="form-label-horizontal col-md-3">';
                                detailHtml += '<label><b>Nội dung ẩn (chỉ hiển thị khi khi đã có người mua sản phẩm)</b></label>';
                                detailHtml += '</div>';
                                detailHtml += '<div class="col-md-9">';
                                detailHtml += '<textarea id="lhiddenField' + product.id + '" rows="2"  class="form-control"  >' + product.hiddenField + '</textarea>';
                                detailHtml += '</div>';
                                detailHtml += '</div>';

                                detailHtml += '<div class="position-relative row form-group">';
                                detailHtml += '<div class="form-label-horizontal col-md-3">';
                                detailHtml += '<label><b>Phương thức liên hệ</b></label>';
                                detailHtml += '</div>';
                                detailHtml += '<div class="col-md-9">';
                                detailHtml += '<input value="' + product.contact + '" id="lcontact' + product.id + '" type="text" class="form-control overflow-auto" placeholder="Số điện thoại/Zalo/Facebook/Telegram/Discord..." value="" >';
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
                                    detailHtml += '<button id="lbuttonPrivate' + product.id + '" type="button" value="1"  class="btn btn-success">Chỉ ai có link mới xem được</button>';
                                    detailHtml += '<button id="lbuttonPublic' + product.id + '" type="button" value="0"  class="btn btn-secondary">Công khai</button>';
                                } else {
                                    detailHtml += '<button id="lbuttonPrivate' + product.id + '" type="button" value="1"  class="btn btn-secondary">Chỉ ai có link mới xem được</button>';
                                    detailHtml += '<button id="lbuttonPublic' + product.id + '" type="button" value="0"  class="btn btn-success">Công khai</button>';
                                }
                                detailHtml += '</div>';
                                detailHtml += '</div>';
                                detailHtml += '</div>'; // đóng của thẻ card-body
                                detailHtml += '<div class="modal-footer">';
                                detailHtml += '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>';
                                detailHtml += '<button type="button" onclick="updateProduct(' + product.id + ')" class="btn btn-success" id="lupdateProduct' + product.id + '">Cập nhật</button>';
                                detailHtml += '</div>'; // đóng thẻ modal-footer
                                detailHtml += '</div>'; // đóng thẻ card
                                detailHtml += '</div>'; // đóng thẻ card group
                                detailHtml += '</div>'; // dóng thẻ col-md-12
                                detailHtml += '</div>'; // đóng thẻ row
                            } else {
                                //Các trạng thái khác (not 1) thì không cho cập nhật
                                console.log("ok");
                                detailHtml += '<div class="row">';
                                detailHtml += '<div class="col-md-12">';
                                detailHtml += '<div class="card-group">';
                                detailHtml += '<div class="card">';
                                detailHtml += '<div class="card-header">';
                                detailHtml += '<h3 class="mb-0">Thông tin mặt hàng trung gian</h3>';
                                detailHtml += '</div>';
                                detailHtml += '<div class="card-body">';
                                if (product.status === 4) {
                                    detailHtml += '<div class="d-flex justify-content-end" ><button type="button" class="btn btn-danger mb-3" id="reportAdminBySeller' + product.id + '">Yêu cầu Admin giải quyết</button></div>';
                                }
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
                                detailHtml += '<input disabled value="' + product.price + '" id="lprice' + product.id + '" type="text" class="form-control" value="">';
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
                            }

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
            });

            //Sự kiện cập nhật cho thay đổi của button bên bán/mua trong Modal Chi tiết
            $(document).on('click', '[id^=lbenban]', function () {
                isBenBanUpdateSelected = true;
                updateButtonFeePayerModal();
            });

            $(document).on('click', '[id^=lbenmua]', function () {
                isBenBanUpdateSelected = false;
                updateButtonFeePayerModal();
            });

            function updateButtonFeePayerModal() {
                if (isBenBanUpdateSelected) {
                    $('[id^=lbenban]').removeClass('btn-secondary').addClass('btn-success');
                    $('[id^=lbenmua]').removeClass('btn-success').addClass('btn-secondary');
                } else {
                    $('[id^=lbenmua]').removeClass('btn-secondary').addClass('btn-success');
                    $('[id^=lbenban]').removeClass('btn-success').addClass('btn-secondary');
                }
            }

            //Setup cho việc cập nhật thông tin sản phẩm trong trường hợp chưa có người mua
            //Sự kiện cập nhật cho thay đổi của button công khai/riêng tư trong Modal Chi tiết
            $(document).on('click', '[id^=lbuttonPublic]', function () {
                isPublicUpdateSelected = true;
                updateButtonPublicPrivateModal();
            });
            $(document).on('click', '[id^=lbuttonPrivate]', function () {
                isPublicUpdateSelected = false;
                updateButtonPublicPrivateModal();
            });
            function updateButtonPublicPrivateModal() {
                if (isPublicUpdateSelected) {
                    $('[id^=lbuttonPublic]').removeClass('btn-secondary').addClass('btn-success');
                    $('[id^=lbuttonPrivate]').removeClass('btn-success').addClass('btn-secondary');
                } else {
                    $('[id^=lbuttonPrivate]').removeClass('btn-secondary').addClass('btn-success');
                    $('[id^=lbuttonPublic]').removeClass('btn-success').addClass('btn-secondary');
                }
            }


            function updateProduct(productId) {
                // Lấy dữ liệu từ các trường trong modal
                let updatedName = $('#lname' + productId).val();
                console.log("updatedName: " + updatedName);
                let updatedPrice = $('#lprice' + productId).val();
                console.log("updatedPrice: " + updatedPrice);
                let updatedType = $('#ltype' + productId).val();
                console.log("updatedType: " + updatedType);
                let updatedDescription = $('#ldescription' + productId).val();
                console.log("updatedDescription: " + updatedDescription);
                let updatedHiddenField = $('#lhiddenField' + productId).val();
                console.log("updatedHiddenField: " + updatedHiddenField);
                let updatedContact = $('#lcontact' + productId).val();
                console.log("updatedContact: " + updatedContact);
                let updatedFeePayer;
                if ($('#lbenban' + productId).hasClass('btn-success')) {
                    updatedFeePayer = $('#lbenban' + productId).val();
                    console.log('benChiuPhi: ' + updatedFeePayer);
                } else {
                    if ($('#lbenmua' + productId).hasClass('btn-success'))
                        updatedFeePayer = $('#lbenmua' + productId).val();
                    console.log('benChiuPhi: ' + updatedFeePayer);
                }

                let updatedIsPrivate;
                if ($('[id^=lbuttonPublic]').hasClass('btn-success')) {
                    updatedIsPrivate = $('[id^=lbuttonPublic]').val();
                    console.log('isPrivate ' + updatedIsPrivate);
                } else {
                    if ($('[id^=lbuttonPrivate]').hasClass('btn-success'))
                        updatedIsPrivate = $('[id^=lbuttonPrivate]').val();
                    console.log('isPrivate: ' + updatedIsPrivate);
                }
                let confirmUpdate = confirm("Bạn có chắc chắn muốn cập nhật thông tin sản phẩm?");
                if (confirmUpdate && updatedPrice !== "" && updatedPrice > 0 && updatedType !== "" && updatedDescription !== "" && updatedContact !== "") {
                    // Gửi dữ liệu cập nhật lên server bằng AJAX
                    $.ajax({
                        url: 'productselleredit', // Thay đổi đường dẫn URL tương ứng với servlet hoặc controller xử lý yêu cầu này
                        type: 'GET',
                        data: {
                            productId: productId,
                            updatedName: updatedName,
                            updatedPrice: updatedPrice,
                            updatedType: updatedType,
                            updatedDescription: updatedDescription,
                            updatedHiddenField: updatedHiddenField,
                            updatedContact: updatedContact,
                            updatedFeePayer: updatedFeePayer,
                            updatedIsPrivate: updatedIsPrivate
                        },
                        success: function (response) {
                            console.log("Update successful");
//                            $('[id^=lexampleModan]').modal('hide');
                            $('#openProductDetail').modal('hide');
                        },
                        error: function () {
                            console.log("Error in AJAX request");
                        }
                    });
                    alert("Cập nhật thành công!");
                } else {
                    alert("Thông tin sản phẩm không hợp lệ. Vui lòng kiểm tra lại.");
                }
            }



            //Setup cho việc thêm sản phẩm mới
            // Lắng nghe sự kiện click trên nút "BÊN BÁN"
            $('#buttonBenBan').click(function () {
                isBenBanSelected = true;
                updateButtonStates();
            });
            // Lắng nghe sự kiện click trên nút "BÊN MUA"
            $('#buttonBenMua').click(function () {
                isBenBanSelected = false;
                updateButtonStates();
            });
            // Hàm cập nhật trạng thái của nút "BÊN BÁN" và "BÊN MUA"
            function updateButtonStates() {
                if (isBenBanSelected) {
                    $('#buttonBenBan').removeClass('btn-secondary').addClass('btn-success');
                    $('#buttonBenMua').removeClass('btn-success').addClass('btn-secondary');
                } else {
                    $('#buttonBenMua').removeClass('btn-secondary').addClass('btn-success');
                    $('#buttonBenBan').removeClass('btn-success').addClass('btn-secondary');
                }
            }

            $('#publicButton').click(function () {
                isPublicSelected = true;
                updateButtonStates1();
            });
            $('#privateButton').click(function () {
                isPublicSelected = false;
                updateButtonStates1();
            });
            function updateButtonStates1() {
                if (isPublicSelected) {
                    $('#publicButton').removeClass('btn-secondary').addClass('btn-success');
                    $('#privateButton').removeClass('btn-success').addClass('btn-secondary');
                } else {
                    $('#privateButton').removeClass('btn-secondary').addClass('btn-success');
                    $('#publicButton').removeClass('btn-success').addClass('btn-secondary');
                }
            }

            $('#addNewProduct').click(function () {
                let name = $('#name').val();
                console.log('Name: ' + name);
                let price = $('#price').val();
                console.log('price: ' + price);
                let benChiuPhi;
                if ($('#buttonBenBan').hasClass('btn-success')) {
                    benChiuPhi = $('#buttonBenBan').val();
                    console.log('benChiuPhi: ' + benChiuPhi);
                } else {
                    if ($('#buttonBenMua').hasClass('btn-success'))
                        benChiuPhi = $('#buttonBenMua').val();
                    console.log('benChiuPhi: ' + benChiuPhi);
                }
                let type = $('#type').val();
                console.log('type: ' + type);
                let description = $('#description').val();
                console.log('description: ' + description);
                let hiddenField = $('#hiddenField').val();
                console.log('hiddenField: ' + hiddenField);
                let contact = $('#contact').val();
                console.log('contact: ' + contact);
                let isPublic;
                if ($('#publicButton').hasClass('btn-success')) {
                    isPublic = $('#publicButton').val();
                    console.log('isPublic: ' + isPublic);
                } else {
                    if ($('#privateButton').hasClass('btn-success'))
                        isPublic = $('#privateButton').val();
                    console.log('isPublic: ' + isPublic);
                }

                let confirmed = confirm("Bạn chắc chắn muốn thêm sản phẩm này?");
                if (confirmed && name !== "" && price !== "" && price > 0 && benChiuPhi !== "" && type !== "" && description !== "" && hiddenField !== "" && contact !== "" && isPublic !== "") {
                    $.ajax({
                        type: 'POST',
                        url: 'newproduct', // Thay đổi bằng URL của servlet
                        data: {
                            name: name,
                            price: price,
                            benChiuPhi: benChiuPhi,
                            type: type,
                            description: description,
                            hiddenField: hiddenField,
                            contact: contact,
                            isPublic: isPublic
                        },
                        success: function (response) {
                            // Xử lý kết quả trả về từ servlet
                            console.log("ko co loi");
                            $('#addProductModal').modal('hide');
                        },
                        error: function (xhr, status, error) {
                            // Xử lý lỗi nếu có
                            console.log(error);
                        }
                    });
                    alert("Thêm sản phẩm thành công!");
                }else{
                    alert("Thông tin sản phẩm không hợp lệ. Vui lòng thử lại.");
                }
            });


            let productIdGlobal;
            //                Trường hợp dữ liệu trên ở session == null => tức là không có thông tin bắt nguồn từ trang "Thông tin đơn trung gian" - "IntermediateOrderInformation.jsp"
            //                Thì phải lấy dữ liệu trực tiếp từ trang này (MyPurchaseOrder.jsp) để gửi ajax 
            $(document).on("click", "[id^=button-Detail]", function (event) {
                let clickedButtonId = event.target.id;
                //                    // In ra console để kiểm tra
                console.log("Clicked button with id: " + clickedButtonId + " at onclick");
                let orderIdString = clickedButtonId.replace(/\D/g, '');
                productIdGlobal = orderIdString;
                console.log(productIdGlobal);
            });
            //Xác nhận huỷ đơn hàng => Đổi trạng thái: 6 - Huỷ đơn - Bên bán xác nhận thông tin sai
            //Hoàn lại tiền cho người mua (tiền trả: bằng tiền sản phẩm đó)
            $(document).on('click', '[id^=informationFalse]', function () {
                $('#openProductDetail').modal('hide');
                $('#confirmCancel').modal('show');
                $(document).on('click', '[id^=acceptConfirmCancel]', function () {
                    console.log("productId at update status 6: " + productIdGlobal);
                    $.ajax({
                        url: 'updateorderstatusreport',
                        type: 'GET',
                        data: {
                            productId: productIdGlobal,
                            orderStt: 6
                        },
                        success: function (response) {
                            console.log("Ajax successfully when sending request to update order status (6)!");
                            $('#confirmCancel').modal('hide');
                            $('#cancelSuccessfull').modal('show');
                            $('#informationFalse' + productIdGlobal).remove();
                            $('#informationTrue' + productIdGlobal).remove();
                        },
                        error: function () {
                            console.log("Error in updating order status, productId: " + productIdGlobal);
                        }
                    });

                });
            });

            //Xác nhận thông tin đúng, yêu cầu bên mua kiểm tra lại
            $(document).on('click', '[id^=informationTrue]', function () {
                $('#openProductDetail').modal('hide');
                $('#informationOK').modal('show');
                $(document).on('click', '[id^=acceptInformationOK]', function () {
                    $.ajax({
                        url: 'updateorderstatusreport',
                        type: 'GET',
                        data: {
                            productId: productIdGlobal,
                            orderStt: 5
                        },
                        success: function (response) {
                            console.log("Ajax successfully when sending request to update order status (5)!");
                            $('#informationOK').modal('hide');
                            $('#acceptInformationSuccess').modal('show');
                            $('#informationFalse' + productIdGlobal).remove();
                            $('#informationTrue' + productIdGlobal).remove();
                        },
                        error: function () {
                            console.log("Error in updating order status");
                        }
                    });
                });
            });

            //Người bán yêu cầu admin giải quyết khiếu nại
            $(document).on("click", "[id^='reportAdminBySeller']", function () {
                $("[id^='openProductDetail']").modal('hide');
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
                            console.log("Ajax successfully when sending request to update order status (7) by seller!");
                        },
                        error: function () {
                            console.log("Error in updating order status");
                        }
                    });
                    $("#requireAdminHandle").modal('hide');
                    $("#acceptRequireAdmin").modal('show');
                });
            });

        </script> 
    </body>
</html>
