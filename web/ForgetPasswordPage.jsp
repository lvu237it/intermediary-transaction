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
        <title>QUÊN MẬT KHẨU</title>
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
                            <form action="forget-password" method="post" onsubmit="return checkInputValid()">
                                <h1>Quên mật khẩu</h1>
                                <p id="message">${message}</p>
                                <div class="field">
                                    <p class="control has-icons-left has-icons-right">
                                        <input id="username" class="input is-medium" type="text" placeholder="Tên đăng nhập" name="username" value=""/>
                                        <span class="icon is-medium is-left">
                                            <i class="fas fa-user"></i>
                                        </span>
                                        <span class="icon is-medium is-right">
                                            <i class="fas fa-check"></i>
                                        </span>
                                    </p>
                                </div>
                                <button class="button is-block is-info is-large is-fullwidth" type="submit">Gửi OTP</button><br/>
                                <div>Chưa có tài khoản? <a href="SignUpPage.jsp">Đăng kí</a></div>

                            </form>


                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script>
          
            function checkInputValid() {
                const usernameInput = document.getElementById('username');
                if (usernameInput.value === "") {
                    document.getElementById("message").innerHTML = "Bạn chưa nhập tên tài khoản.";
                    return false;
                }

            }


        </script>
    </body>
</html>
