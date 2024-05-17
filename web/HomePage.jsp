<%-- 
    Document   : HomePage
    Created on : Jan 15, 2024, 10:41:45 PM
    Author     : User
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
            <div class="body-productlist container mb-3 mt-3">
                <h3 class="text-center">Danh mục sản phẩm</h3>
                <div class="row mt-3">
                    <div class="col-md-3 col-6 d-flex justify-content-center">
                        <div class="card card-item" style="height: 100%">
                            <img style="height: 65%"
                                 src="./logo_icon_blabla/document.jpg"
                                 class="card-img-top"
                                 alt="..."
                                 />
                            <div class="card-body">
                                <h5 class="card-title">Tài liệu</h5>
                                <p class="card-text">
                                    Tài liệu học tập cho các môn học, chuyên ngành...
                                </p>
                            <c:url value="PublicMarketFilter.jsp" var="productTypeURL">
                                <c:param name="productType" value="Tài liệu" />
                            </c:url>
                            <a href="${productTypeURL}" class="btn btn-success d-block text-center">Chi tiết</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-6 d-flex justify-content-center">
                    <div class="card card-item" style="height: 100%">
                        <img style="height: 65%"
                             src="./logo_icon_blabla/account.jpg"
                             class="card-img-top"
                             alt="..."
                             />
                        <div class="card-body">
                            <h5 class="card-title">Tài khoản</h5>
                            <p class="card-text">
                                Tài khoản mạng xã hội, giáo dục, thương mại điện tử...
                            </p>
                            <c:url value="PublicMarketFilter.jsp" var="productTypeURL">
                                <c:param name="productType" value="Tài khoản" />
                            </c:url>
                            <a href="${productTypeURL}" class="btn btn-success d-block text-center">Chi tiết</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-6 d-flex justify-content-center">
                    <div class="card card-item" style="height: 100%">
                        <img style="height: 65%"
                             src="./logo_icon_blabla/software.jpg"
                             class="card-img-top"
                             alt="..."
                             />
                        <div class="card-body">
                            <h5 class="card-title">Phần mềm</h5>
                            <p class="card-text">
                                Các phần mềm, công cụ hỗ trợ quản lý...
                            </p>
                            <c:url value="PublicMarketFilter.jsp" var="productTypeURL">
                                <c:param name="productType" value="Phần mềm" />
                            </c:url>
                            <a href="${productTypeURL}" class="btn btn-success d-block text-center">Chi tiết</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-6 d-flex justify-content-center">
                    <div class="card card-item" style="height: 100%">
                        <img style="height: 65%"
                             src="./logo_icon_blabla/other.jpg"
                             class="card-img-top"
                             alt="..."
                             />
                        <div class="card-body">
                            <h5 class="card-title">Khác</h5>
                            <p class="card-text">
                                Các loại sản phẩm giao dịch trung gian khác
                            </p>
                            <c:url value="PublicMarketFilter.jsp" var="productTypeURL">
                                <c:param name="productType" value="Khác" />
                            </c:url>
                            <a href="${productTypeURL}" class="btn btn-success d-block text-center">Chi tiết</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                            <jsp:include page="footer.jsp"></jsp:include>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"
        ></script>
    </body>
</html>