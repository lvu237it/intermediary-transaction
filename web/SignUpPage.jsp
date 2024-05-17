<%-- 
    Document   : SignUpPage
    Created on : Jan 15, 2024, 11:13:38 PM
    Author     : User
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>TRANG ĐĂNG KÝ</title>
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
        <link rel="stylesheet" href="./style_CSS/signUp.css">
        <style>.Captcha.dang-ky-wrapper {
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
                            <form action="signup" method="post" onsubmit="return checkInputValid()">
                                <h1>Đăng Ký</h1>
                                <p id="message">${message}</p>
                                <div class="field">
                                    <p class="control has-icons-left has-icons-right">
                                        <input id="username" class="input is-medium" type="text" placeholder="Tên đăng nhập" name="username" value="${requestScope.usernameValue}"/>
                                        <span class="icon is-medium is-left">
                                            <i class="fas fa-user"></i>
                                        </span>
                                        <span class="icon is-medium is-right">
                                            <i class="fas fa-check"></i>
                                        </span>
                                    </p>
                                </div>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input id="password" class="input is-medium" type="password" placeholder="Mật khẩu" name="password" value=""/>
                                        <span class="icon is-small is-left">
                                            <i class="fas fa-lock"></i>
                                        </span>
                                    </p>
                                </div>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input id="repass" class="input is-medium" type="password" placeholder="Nhập Lại Mật khẩu" name="re-password" value=""/>
                                        <span class="icon is-small is-left">
                                            <i class="fas fa-lock"></i>
                                        </span>
                                    </p>

                                </div>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input id="email" class="input is-medium" type="email" pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$" placeholder="Nhập Email" name="email" value="${requestScope.emailValue}"/>
                                        <span class="icon is-small is-left">
                                            <i class="fas fa-envelope"></i>
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
                                <button onclick="openOTP()" class="button is-block is-info is-large is-fullwidth" type="submit">Đăng Ký</button><br/>

                                <div>Nếu đã có tài khoản, hãy đăng nhập tại <a href="login">đây</a></div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script>
            const captchaImage = document.getElementById('captcha-image');
            const captchaInput = document.getElementById('captcha-input');
            const refreshCaptchaButton = document.getElementById('reloadCaptcha');
            // Gán sự kiện click cho nút "Refresh"
            refreshCaptchaButton.addEventListener('click', function refreshCaptcha(event) {
                event.preventDefault();
                captchaInput.value = ''; // Xóa giá trị captcha cũ

                var s = 'captcha?' + new Date().getTime(); // Gọi lại servlet Captcha với thời gian mới để cập nhật hình ảnh captcha
                captchaImage.src = s;
            });
           



            function checkInputValid() {
                const usernameInput = document.getElementById('username');
                const passwordInput = document.getElementById('password');
                const repassInput = document.getElementById('repass');
                const emailInput = document.getElementById('email');
                
                if (usernameInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập tên tài khoản.";
                    return false;
                }
                if(passwordInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập mật khẩu.";
                    return false;
                }
                if(emailInput.value === ""){
                    document.getElementById("message").innerHTML = "Bạn chưa nhập email.";
                    return false;
                }
                if (captchaInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập mã captcha.";
                    return false;
                }

                if (passwordInput.value !== repassInput.value) {
                    document.getElementById('message').innerHTML = "Mật khẩu xác nhận không khớp! Vui lòng thử lại."
                    return false;
                }
            }


        </script>

        <!--        <script>
                    // Initialization for ES Users
                    import { Input, Ripple, initMDB } from "mdb-ui-kit";
                    initMDB({Input, Ripple});
                </script>-->
    </body>
</html>
