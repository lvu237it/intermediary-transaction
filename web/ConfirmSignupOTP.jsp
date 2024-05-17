<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <style>
            body {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
                margin: 0;
            }

            .otp-form {
                max-width: 600px;
                width: 100%;
                padding: 15px;
                margin: auto;
                background-color:aliceblue;
            }
               
        </style>
        <title>Confirm your OTP</title>
    </head>
    <body>
        <form class="otp-form form-control" action="verification" method="post">
            <p class="mb-4"> Chúng tôi đã tiến hành gửi mã OTP đến tài khoản email ${requestScope.emailValue}. <br/> 
                Bạn hãy kiểm tra và vui lòng xác nhận OTP ở trường bên dưới. 
    
                <input type="text" class="form-control mt-3 mb-4" name="otp" value="" required>
                
                <button type="submit" class="btn btn-primary mx-auto d-block" name="confirm">Confirm</button>

            <p> ${requestScope.message} </p>
            <p class="text-center">Hãy yêu cầu gửi lại tại <a href="verification">đây</a>
        </form>

    </body>
</html>
