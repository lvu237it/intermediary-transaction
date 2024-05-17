<%-- 
    Document   : CreateProfile
    Created on : Jan 21, 2024, 4:21:30 PM
    Author     : ADMIN
--%>

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
        <title>Hoàn thiện profile của bạn</title>
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
    </head>
    <body>

        <div class="form-control box">
            <p class="subtitle is-4"></p>
            <form action="createprofile" method="post">
                <h2>Hoàn thiện thông tin cá nhân của bạn!</h2>
                <p id="message">${message}</p>
                
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="fullName" id="fullname" placeholder="">
                    <label for="fullname">Nhập họ và tên</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" pattern="^0[0-9]{9}$" placeholder="">
                    <label for="phoneNumber">Nhập số điện thoại</label>
                </div>
                
                <div class="form-floating mb-4">
                    <textarea class="form-control" placeholder="Mô tả về bạn" name="description" id="floatingTextarea2" style="height: 300px"></textarea>
                    <label for="floatingTextarea2">Mô tả về bạn</label>
                </div>

                <button class="btn btn-primary mx-auto d-block" type="submit">Xác nhận</button><br/>

            </form>

        </div>

    </body>
</html>
