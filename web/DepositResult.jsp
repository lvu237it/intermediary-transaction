<%@page import="java.net.URLEncoder" %>
<%@page import="java.nio.charset.StandardCharsets" %>
<%@page import="vnpay.Config" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<button%@page import="java.util.HashMap" %>

    <!DOCTYPE html>
    <html lang="en">

        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
            <meta name="description" content="">
            <meta name="author" content="">
            <title>Kết quả thanh toán</title>
            <!-- Bootstrap core CSS -->
            <link href="/vnpay_jsp/assets/bootstrap.min.css" rel="stylesheet" />
            <!-- Custom styles for this template -->
            <link href="/vnpay_jsp/assets/jumbotron-narrow.css" rel="stylesheet">
            <script src="/vnpay_jsp/assets/jquery-1.11.3.min.js"></script>
            <link rel="stylesheet"
                  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
            <link
                href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                rel="stylesheet"
                integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
                crossorigin="anonymous" />
            <link rel="stylesheet" href="./style_CSS/homePage.css">
            <style>
                .position-relative {
                    margin-top: 20px;
                }
            </style>
            <%
                Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
                                            
        double amount = Double.parseDouble(request.getParameter("vnp_Amount")) / 100;
                                            
            %>
        </head>

        <body>
            <jsp:include page="header.jsp"></jsp:include>
                <!--Begin display -->
                <div class="container mb-5">
                    <div class="card">
                        <h3 class="card-header">Kết quả thanh toán</h3>
                        <div class="card-body">
                            <div class="position-relative row form-group">
                                <div class="form-label-horizontal col-md-3">
                                    <label><b>Mã giao dịch:</b></label>
                                </div>
                                <div class="col-md-9">
                                    <p><%=request.getParameter("vnp_TxnRef")%></p>
                            </div>
                        </div>
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3">
                                <label><b>Số tiền</b></label>
                            </div>
                            <div class="col-md-9">
                                <p><%= amount %></p>
                            </div>
                        </div>
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3">
                                <label><b>Trạng thái giao dịch:</b></label>
                            </div>
                            <div class="col-md-9">
                                <p><%
                                    if (signValue.equals(vnp_SecureHash)) {
                                        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                                            out.print("Thành công");
                                            } else {
                                            out.print("Không thành công");
                                                }

                                            } else {
                                                out.print("invalid signature");
                                                    }
                                                                        
                                    %></p>
                            </div>
                        </div>
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3">
                                <label><b>Mô tả giao dịch:</b></label>
                            </div>
                            <div class="col-md-9">
                                <p><%= request.getParameter("vnp_OrderInfo") %></p>
                            </div>
                        </div>
                        <button onclick="backToHomePage()" class="btn btn-success">Trở về trang chủ</button>
                    </div>
                </div>
            </div>

            <jsp:include page="footer.jsp"></jsp:include>

            <link href="https://pay.vnpay.vn/lib/vnpay/vnpay.css"
                  rel="stylesheet" />
            <script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>
            <script
                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
        </body>
        <script>
                            function backToHomePage() {
                                window.location.href = "homepage";
                            }
        </script>
    </html>