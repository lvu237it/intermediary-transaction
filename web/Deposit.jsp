
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.accountSession == null}">
    <c:redirect url="login"/> 
</c:if>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Nạp tiền</title>
        <!-- Bootstrap core CSS -->
        <link href="/vnpay_jsp/assets/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="/vnpay_jsp/assets/jumbotron-narrow.css" rel="stylesheet">      
        <script src="/vnpay_jsp/assets/jquery-1.11.3.min.js"></script>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./style_CSS/homePage.css">
        <style>
            .position-relative{
                margin-top: 20px;
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
                                <div class="card-header">
                                    <div class="md-0">
                                        <h3>Nạp tiền</h3>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <form action="vnpay" id="frmCreateOrder" method="post">
                                    <div class="position-relative row form-group">
                                        <div class="form-label-horizontal col-md-3">
                                            <label><b>Phương thức nạp tiền: </b></label>
                                        </div>
                                        <div class="col-md-9">
                                            <button type="button" class="form-control btn btn-success active">Cổng thanh toán (phát sinh thêm phí dịch vụ nếu cần)</button>
                                        </div>
                                    </div>
                                    <div class="position-relative row form-group">
                                        <div class="form-label-horizontal col-md-3">
                                            <label for="amount"><b>Số tiền (VND): </b></label>
                                        </div>
                                        <div class="col-md-9">
                                            <input id="amount" class="form-control"  data-val="true" data-val-number="Trường này bắt buộc phải là số" data-val-required="Bạn chưa nhập trường này" max="100000000" min="1" name="amount" type="text" value="">
                                        </div>
                                    </div>
                                    <div class="position-relative row form-group">
                                        <div class="form-label-horizontal col-md-3">
                                            <label for="description"><b>Mô tả: </b></label>
                                        </div>
                                        <div class="col-md-9">
                                            <textarea id="description" name="description" class="form-control" style="height: 200px"></textarea>
                                        </div>
                                    </div>
                                    <button class="position-relative btn btn-success d-flex mx-auto">Xác nhận</button>
                                    </form>
                                </div>
                            </div>
                        </div>         
                    </div>
                </div>
            </div>
        <jsp:include page="footer.jsp"></jsp:include>

    </div>

    <link href="https://pay.vnpay.vn/lib/vnpay/vnpay.css" rel="stylesheet" />
    <script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"
    ></script>
    <script type="text/javascript">
        $("#frmCreateOrder").submit(function () {
            var postData = $("#frmCreateOrder").serialize();
            var submitUrl = $("#frmCreateOrder").attr("action");
            $.ajax({
                type: "POST",
                url: submitUrl,
                data: postData,
                dataType: 'JSON',
                success: function (x) {
                    if (x.code === '00') {
                        if (window.vnpay) {
                            vnpay.open({width: 768, height: 600, url: x.data});
                        } else {
                            location.href = x.data;
                        }
                        return false;
                    } else {
                        alert(x.Message);
                    }
                }
            });
            return false;
        });
    </script>       
</body>
</html>