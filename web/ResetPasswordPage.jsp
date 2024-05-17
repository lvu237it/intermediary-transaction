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
        <title>ĐẶT LẠI MẬT KHẨU</title>
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
        <link rel="stylesheet" href="./style_CSS/forgetPassword.css">
        
    </head>
    <body>
        <section class="hero is-fullheight">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <div class="box">
                            <p class="subtitle is-4"></p>
                            <form action="reset-password" method="post" onsubmit="return checkInputValid()">
                                <h1>Đặt lại mật khẩu</h1>
                                <p id="message">${message}</p>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input id="password" class="input is-medium" type="password" placeholder="Mật khẩu mới" name="password" value=""/>
                                        <span class="icon is-small is-left">
                                            <i class="fas fa-lock"></i>
                                        </span>
                                    </p>
                                </div>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input id="repass" class="input is-medium" type="password" placeholder="Nhập Lại Mật khẩu" name="repassword" value=""/>
                                        <span class="icon is-small is-left">
                                            <i class="fas fa-lock"></i>
                                        </span>
                                    </p>

                                </div>
                                
                                <button class="button is-block is-info is-large is-fullwidth" type="submit">Xác nhận</button><br/>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script>
            function checkInputValid() {
                const passwordInput = document.getElementById('password');
                const repassInput = document.getElementById('repass');
                
                if(passwordInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập mật khẩu.";
                    return false;
                }
                if(repassInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập lại mật khẩu.";
                    return false;
                }
                if (passwordInput.value !== repassInput.value) {
                    document.getElementById('message').innerHTML = "Mật khẩu xác nhận không khớp! Vui lòng thử lại.";
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
