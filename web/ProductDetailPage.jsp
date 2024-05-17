
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
<%@page import = "Model.*" %>


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
        <!--<link rel="stylesheet" href="./style_CSS/publicMarket.css">-->
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <style>
            .card{
                margin: 0 10%;
                width: 80%;
            }
            .position-relative{
                margin: 20px 0;
            }
        </style>
        <%
            // lấy id từ method GET của ProductDetailController
          String id = request.getParameter("pid");
          User u = (User) session.getAttribute("account");
          int uid =  (u != null) ? u.getUserId() : 0;
//          session.removeAttribute("account");
        %>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

            <div class="row">
                <div class="col-md-12 mt-3 mb-3 text-center">
                    <h3>Thông tin chi tiết sản phẩm</h3>
                </div>
            </div>
            <div class="container" id="productDetail">


            </div>
        <jsp:include page="footer.jsp"></jsp:include>
            <script>
                let pid = '<%=id%>';
                let uid = '<%= uid %>';


                let isBenBanUpdateSelected = false;
                let isPublicUpdateSelected = false;
                
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
                console.log(pid + 'asdadadad');
                console.log(uid);
                $(document).ready(function () {
                    jQuery.ajax({
                        url: "productdetail",
                        type: "POST",
                        data: {
                            pid: pid
                        },
                        success: function (product) {
                            let user = product.user;
                            let detail = document.getElementById('productDetail');
                            console.log(user.userId);
                            let detailHtml = '';
                            if (product !== null) {
                                if (product.isPurchased === true) {
                                    window.location.href = "404.jsp";
                                } else {
                                    productPrice = product.price;
                                    detailHtml += '<div class="row">';
                                    detailHtml += '<div class="col-md-12">';
                                    detailHtml += '<div class="card-group">';
                                    detailHtml += '<div class="card">';
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

                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Chủ đề trung gian</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input id="lname" rows="2" class="form-control" value="' + product.name + '">';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    } else {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Chủ đề trung gian</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<textarea rows="2" disabled class="form-control">' + product.name + '</textarea>';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }


                                    //
                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Giá tiền</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input id="lprice" type="text" class="form-control" value="' + product.price + '">';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    } else {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Giá tiền</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input id="productPrice" type="text" class="form-control" disabled value="' + product.price + '">';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }
                                    //
                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Bên chịu phí trung gian</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<button type="button" id="lbenban" data-product-id="' + product.id + '"  class="' + (product.feePayer === false ? 'btn btn-success' : 'btn btn-secondary') + '">BÊN BÁN</button>';
                                        detailHtml += '<button type="button" id="lbenmua" data-product-id="' + product.id + '" class="' + (product.feePayer !== false ? 'btn btn-success' : 'btn btn-secondary') + '">BÊN MUA</button>';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    } else {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Bên chịu phí trung gian</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<button type="button" disabled id="buttonBenBanNo' + product.id + '"  class="' + (product.feePayer === false ? 'btn btn-success' : 'btn btn-secondary') + '">BÊN BÁN</button>';
                                        detailHtml += '<button type="button" disabled id="buttonBenMuaNo' + product.id + '"  class="' + (product.feePayer !== false ? 'btn btn-success' : 'btn btn-secondary') + '">BÊN MUA</button>';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }

                                    //
                                    if (user.userId != uid || uid === '0') {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Phí trung gian</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input type="text" class="form-control" disabled value="' + (product.price * 5 / 100) + '">';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    } else {
                                        detailHtml += '<div></div>';
                                    }

                                    // 
                                    if (user.userId != uid || uid === '0') {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Tổng tiền cần thanh toán</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input id="totalNeededAmount" type="text" class="form-control totalNeededAmount" disabled>';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }

                                    // 
                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Mô tả</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input type="text" id="ldescription" class="form-control overflow-auto" value="' + product.description + '">';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    } else {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Mô tả</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input type="text" class="form-control overflow-auto" value="' + product.description + '" disabled>';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }

                                    //
                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Phương thức liên hệ</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<input rows="2" id="lcontact" type="text" class="form-control overflow-auto" placeholder="Số điện thoại/Zalo/Facebook/Telegram/Discord..." value="' + product.contact + '">';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    } else {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Phương thức liên hệ</b></label>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">';
                                        detailHtml += '<textarea rows="2" placeholder="Số điện thoại/Zalo/Facebook/Telegram/Discord..." class="form-control" disabled >' + product.contact + '</textarea>';
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }

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

                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<div class="position-relative row form-group">';
                                        detailHtml += '<div class="form-label-horizontal col-md-3">';
                                        detailHtml += '<label><b>Hiện công khai</b></label>'
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="col-md-9">'
                                        detailHtml += '<button id="lbuttonPrivate" type="button" value="1"  class="' + (product.isPrivate === 1 ? 'btn btn-success' : 'btn btn-secondary') + '">Chỉ ai có link mới xem được</button>'
                                        detailHtml += '<button id="lbuttonPublic" type="button" value="0"  class="' + (product.isPrivate === 0 ? 'btn btn-success' : 'btn btn-secondary') + '">Công khai</button>'
                                        detailHtml += '</div>';
                                        detailHtml += '</div>';
                                    }
                                    //
                                    detailHtml += '<div class="position-relative row form-group">';
                                    detailHtml += '<div class="form-label-horizontal col-md-3">';
                                    detailHtml += '<label><b>Link của đơn trung gian (dùng khi ở đơn ở trạng thái riêng tư)</b></label>';
                                    detailHtml += '</div>';
                                    detailHtml += '<div class="col-md-9">';
                                    detailHtml += '<input type="text" value="' + product.shareLink + '" disabled class="form-control" value=>';
                                    detailHtml += '</div>';
                                    detailHtml += '</div>';
                                    detailHtml += '</div>'; // Kết thúc card-body

                                    detailHtml += '<div class="modal-footer">';
                                    if (user.userId == uid && product.updateable === true) {
                                        detailHtml += '<button type="button" class="btn btn-warning d-flex mx-auto mb-4" onclick="updateProduct('+ product.id+')" >Cập Nhật</button>';
                                    } else if (user.userId != uid || uid === '0') {
                                        detailHtml += '<button type="button" class="btn btn-success d-flex mx-auto mb-4" onclick="buyProduct(' + product.id + ')" >Mua</button>';
                                        detailHtml += '</div>';
                                        detailHtml += '<div class="modal fade" id="confirmModal' + product.id + '" tabindex="-1" aria-labelledby="confirmModalLabel' + product.id + '" aria-hidden="true">'
                                                + '   <div class="modal-dialog">'
                                                + '        <div class="modal-content">'
                                                + '            <div class="modal-header">'
                                                + '                <h1 class="modal-title fs-5" id="confirmModalLabel' + product.id + '">Xác nhận mua sản phẩm</h1>'
                                                + '                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'
                                                + '            </div>'
                                                + '            <div class="modal-body">'
                                                + '                <div>Bạn có chắc chắn muốn mua sản phẩm này?</div>'
                                                + '            </div>'
                                                + '            <div class="modal-footer">'
                                                + '                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>'
                                                + '<button type="button" id="confirm-information-ok' + product.id + '" class="btn btn-success">Xác nhận</button>'
                                                + '            </div>'
                                                + '        </div>'
                                                + '    </div>'
                                                + '</div>';
                                    }



                                    detailHtml += '</div>'; // Kết thúc card
                                    detailHtml += '</div>'; // Kết thúc card-group
                                    detailHtml += '</div>'; // Kết thúc col-md-12
                                    detailHtml += '</div>'; // Kết thúc row
                                }
                            }
                            console.log(product.name);
                            // Đặt nội dung HTML vào phần tử detail
                            jQuery('#productDetail').html(detailHtml);
                            processTotalAmount(product);
                        },
                        error: function (error) {
                            console.log(error);
                        }
                    });
                });
                
                
                // ham danh cho cap nhat san pham
                function updateProduct(productId) {
                // Lấy dữ liệu từ các trường trong modal
                let updatedName = $('#lname').val();
                console.log("updatedName: " + updatedName);
                let updatedPrice = $('#lprice').val();
                console.log("updatedPrice: " + updatedPrice);
                let updatedType = $('#ltype').val();
                console.log("updatedType: " + updatedType);
                let updatedDescription = $('#ldescription').val();
                console.log("updatedDescription: " + updatedDescription);
                let updatedHiddenField = $('#lhiddenField').val();
                console.log("updatedHiddenField: " + updatedHiddenField);
                let updatedContact = $('#lcontact').val();
                console.log("updatedContact: " + updatedContact);

                let updatedFeePayer;
                if ($('#lbenban').hasClass('btn-success')) {
                    updatedFeePayer = $('#lbenban').val();
                    console.log('benChiuPhi: ' + updatedFeePayer);
                } else {
                    if ($('#lbenmua').hasClass('btn-success'))
                        updatedFeePayer = $('#lbenmua').val();
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
                if (confirmUpdate){
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
                            $('[id^=lexampleModan]').modal('hide');
                        },
                        error: function () {
                            console.log("Error in AJAX request");
                        }
                    });
                    alert("Cập nhật thành công!");
                }
            }
                
                
                // ham tinh tong tien
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
                
                
                // ham danh cho mua 1 san pham moi
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
                        if (uid === '0') {
                            window.location.href = "login";
                        }
                        window.location.href = "IntermediateOrderInformation.jsp?productId=" + productId + "&benChiuPhi=" + benChiuPhi + "&totalPriceBuyer=" + totalNeededPrice + "&sellerReceivedTrueMoney=" + totalPriceSeller;
                    });
                }
        </script>
    </body>
</html>
