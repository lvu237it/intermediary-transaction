<%-- 
    Document   : 401
    Created on : Feb 22, 2024, 10:39:48 AM
    Author     : ADMIN
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ITS - Trang giao dịch trung gian</title>
        <link rel="icon" href="./logo_icon_blabla/ITS_logo_4.jpg">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
            />
        <link rel="stylesheet" href="./style_CSS/homePage.css">
    </head>
    <!------------------------------new body-------------------------------------->
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div class="alert alert-danger text-center">
            <p>Đường dẫn không tồn tại</p>
        </div>

        <div class="d-flex justify-content-center">
            <a class="btn btn-primary btn-sm" href="homepage">
                Quay về trang chủ 
            </a>
        </div>
        <div class="footer container">
            <h6 class="text-center">Copyright <i class="fa-regular fa-copyright"></i> ITS 2024 | All Rights Reserved.</h6>
        </div>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"
        ></script>
    </body>
</html>