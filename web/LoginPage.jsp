
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="author" content="Aldi Duzha" />
        <meta name="description" content="Free Bulma Login Template, part of Awesome Bulma Templates" />
        <meta name="keywords" content="bulma, login, page, website, template, free, awesome" />
        <link rel="canonical" href="https://aldi.github.io/bulma-login-template/" />
        <title>TRANG ĐĂNG NHẬP</title>
        <link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma-social@1/bin/bulma-social.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.13.0/css/all.min.css" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
            />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" crossorigin="anonymous" />
        <link rel="stylesheet" href="./style_CSS/loginPage.css" />
        <style>
            @import url("https://fonts.googleapis.com/css2?family=Poppins");
            * {
                box-sizing: border-box;
            }
            .form-group.invalid .form-control {
                border-color: #f33a58;
            }

            .form-group.invalid .form-message {
                color: #f33a58;
            }
            .Captcha.dang-ky-wrapper {
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .Captcha .column {
                margin: 0;
            }

            .refresh-captcha {
                border-radius: 0;
                background-color: #4bb6b7;
                color: #fff;
                font-size: 24px;
                margin: 0;
                padding: 0;
                transition: 0.3s ease-in-out;
                background-color: white;
                border: 0 solid #fff;
                margin-left: 6px;
                color: black;
                margin-right: 6px;
            }
        </style>
    </head>
    <body>
        
        <section class="hero is-fullheight">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <div class="box">
                            <p class="subtitle is-4"></p>
                            <form action="login" method="post" onsubmit="return checkInputValid()">
                                <h1>Đăng nhập</h1>
                                <p id="message">${message}</p>
                                <div class="field">
                                    <p class="control has-icons-left has-icons-right">
                                        <input autofocus id="username" class="input is-medium" type="text" placeholder="Tên đăng nhập" name="username" value="${userNameValue}"/>
                                        <span class="icon is-medium is-left">
                                            <i class="fas fa-user"></i>
                                        </span>
                                        <span class="icon is-medium is-right">
                                        </span>
                                    </p>
                                </div>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input id="password" class="input is-medium" type="password" placeholder="Mật khẩu" name="password" value="${passwordValue}"/>
                                        <span class="icon is-small is-left">
                                            <i class="fas fa-lock"></i>
                                        </span>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <div class="Captcha dang-ky-wrapper">
                                        <div class="column">
                                            <img style="border-radius: 10px" id="captcha-image" src="captcha" alt="Captcha Image">
                                        </div>
                                        <button class="refresh-captcha" id="reloadCaptcha">
                                            <i class="fas fa-sync-alt"></i>
                                        </button>
                                        <div class="column dang-ky">
                                            <input style="font-size: 14px; font-family: Arial;font-weight: bold" name="captcha" id="captcha-input" type="text" placeholder="Nhập Captcha" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="buttons mt-3 mb-3">
                                    <button class="button is-block is-info is-large is-fullwidth" type="submit">Đăng nhập</button>
                                </div>
                            </form>
                            <div class="d-flex justify-content-center justify-content-around">
                                <button class="button is-block is-info" id="signup" style="padding: 0 10%">Đăng ký</button>
                                <button class="button is-block is-info" id="forgetpassword" style="padding: 0 10%">Quên mật khẩu</button>  
                            </div>
                                        <a class="d-block mt-4" href="HomePage.jsp" style="font-size: 18px; font-weight: bold; text-decoration: none">Quay trở lại trang chủ</a>
                        </div>
                    </div>
                </div>
        </section>
        <script>
            //sự kiện khi click vào sign up button
            document.getElementById("signup").addEventListener('click', function () {
                window.location.href = 'SignUpPage.jsp';
            });
            
            document.getElementById("forgetpassword").addEventListener('click', function () {
                window.location.href = 'forget-password';
            });

            const captchaImage = document.getElementById('captcha-image');
            const refreshCaptchaButton = document.getElementById('reloadCaptcha');
            const captchaInput = document.getElementById('captcha-input');
            // Gán sự kiện click cho nút "Refresh"
            refreshCaptchaButton.addEventListener('click', function refreshCaptcha(event) {
                event.preventDefault();
                captchaInput.value = ''; // Xóa giá trị captcha cũ

                var s = 'captcha?' + new Date().getTime(); // Gọi lại servlet Captcha với thời gian mới để cập nhật hình ảnh captcha
                captchaImage.src = s;
            });

            document.addEventListener("keydown", function (event) {
                if (event.key === "Enter") {
                    const activeElement = document.activeElement;//lấy phần tử đang được focus trên trang (ví dụ: user đang select 1 ô input cụ thể)

                    if (activeElement.tagName === "INPUT") {//Kiểm tra phần tử đang được focus có phải là ô input không
                        event.preventDefault();//Chặn sự kiện "Enter" để ngăn trường hợp người dùng không input mà vẫn Enter để submit
                    }
                }
            });


            function checkInputValid() {
                const usernameInput = document.getElementById("username");
                const passwordInput = document.getElementById("password");
                const captchaInput = document.getElementById('captcha-input');
                if (usernameInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập tên tài khoản";
                    return false;
                }
                if (passwordInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập mật khẩu";
                    return false;
                }
                if (captchaInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập mã captcha";
                    return false;
                }
            }
        </script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"
        ></script>
        <script src="https://kit.fontawesome.com/01f3e8de31.js" crossorigin="anonymous"></script>
        </body>
</html>