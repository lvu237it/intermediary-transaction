<%-- 
    Document   : Profile
    Created on : Jan 25, 2024, 10:14:08 PM
    Author     : HP
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${sessionScope.accountSession == null}">
    <c:redirect url="login"/> <!-- Chuyển hướng đến trang login nếu session không tồn tại -->
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="./style_CSS/profile.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>Hồ sơ cá nhân</title>
    </head>
    <body>
        <div class="container rounded bg-white mt-5 mb-5">
            <div class="row">
                <div id="bd" class="col-md-3 heighde">
                    <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"></div>
                </div>
                <div id="bd" class="col-md-5">
                    <div class="p-3 py-5">
                        <div class= text-center mb-3">
                            <h4 class="text-right ">Hồ sơ người dùng</h4>
                        </div>
                        <form action="profile" method="post">
                            <div class="row mt-3">
                                <div class="col-md-12"><label class="labels fw-bold">Họ và Tên</label><input type="text" name="fullname" class="form-control" placeholder="" value="${profile.fullName}" required=""></div>
                                <div class="col-md-12"><label class="labels fw-bold">Số điện thoại</label><input id="phonecheck" name="phone" pattern="^0[0-9]{9}$" type="text" class="form-control" placeholder="Nhập tối thiểu 10 chữ số" value="${profile.phoneNumber}" required=""></div>
                                <div class="col-md-12"><label class="labels fw-bold">Gmail</label><input type="text" name="gmail" class="form-control" readonly placeholder="" value="${profile.gmail}" required="" pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"></div>
                                <div  class="col-md-12"><label class="labels fw-bold ">Mô tả</label><input type="text" name="des" class="form-control" rows="5" placeholder="" value="${profile.description}" required=""/></div>                   
                            </div>
                            <div class="d-flex justify-content-around">
                                <div class="mt-5 text-center"><button id="back" class="btn btn-primary profile-button" type="button">Về trang chủ</button></div>
                                <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="submit">Lưu</button></div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <form onsubmit="changePassword(); return false;"> 
                        <div class="p-3 py-5">
                            <div class= text-center mb-3">
                                <h4 class="text-right">Thay đổi mật khẩu</h4>
                            </div>
                            <p id="messageChangePassword" class="text-center">${message}</p>
                            <div class="col-md-12 "><label class="labels fw-bold">Mật khẩu cũ</label><input id="oldPassword" type="password" class="form-control"  value="${oldPass}" name="oldPassword"></div> <br>
                            <div class="col-md-12 "><label class="labels fw-bold">Mật khẩu mới</label><input id="newPassword" type="password" class="form-control"  value="" name="newPassword"></div> <br>
                            <div id="mat" class="col-md-12"><label class="labels fw-bold">Xác nhận mật khẩu mới</label><input id="confirmNewPass" type="password" class="form-control" placeholder="" value="" name="confirmNewPass"></div>
                        </div>
                        <div class="mt-3 text-center"><button class="btn btn-primary profile-button" type="submit">Cập nhật</button></div>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script>
        document.getElementById("back").addEventListener('click', function () {
            window.location.href = 'homepage';
        });

        function clearInputFields() {
            document.getElementById("oldPassword").value = "";
            document.getElementById("newPassword").value = "";
            document.getElementById("confirmNewPass").value = "";
        }

        function changePassword() {
            let oldPassword = document.getElementById("oldPassword").value;
            let newPassword = document.getElementById("newPassword").value;
            let confirmNewPass = document.getElementById('confirmNewPass').value;

            if (oldPassword === "" || newPassword === "" || confirmNewPass === "") {
                document.getElementById("messageChangePassword").innerHTML = "Vui lòng điền đầy đủ thông tin.";
                return;
            }

            $.ajax({
                url: 'updatepassword',
                type: 'POST',
                data: {
                    oldPassword: oldPassword,
                    newPassword: newPassword,
                    confirmNewPass: confirmNewPass
                },
                success: function (response) {
                    // Hiển thị phản hồi từ servlet trực tiếp trong thẻ p
                    document.getElementById("messageChangePassword").innerHTML = response;

                    if (response.indexOf("Mật khẩu cũ không đúng") !== -1 || response.indexOf("Đổi mật khẩu thành công") !== -1) {
                        //tìm thấy chuỗi "Mật khẩu cũ không đúng" hoặc "Đổi mật khẩu thành công" khi servlet phản hồi 
                        clearInputFields();//Xóa giá trị của các trường nhập
                    }
                    if (response.indexOf("Mật khẩu mới không trùng khớp") !== -1) {
                        //chỉ xoá trường của mật khẩu mới khi nhập đúng mật khẩu cũ
                        document.getElementById("newPassword").value = "";
                        document.getElementById("confirmNewPass").value = "";
                    }
                },
                error: function () {
                    console.log("Error in Ajax request");
                }
            });
        }

    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</html>
