/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author ADMIN
 */
public class SendEmail {

    public static boolean sendOTP(String toEmail, String subject, String otp) {
        // Thông tin tài khoản email
        // account: intermediarysytem1755@gmail.com
        // password: dhml xuky bjle gfzx
        String username = "intermediarysystem1755@gmail.com";
        String password = "dhml xuky bjle gfzx";
        // Cấu hình properties cho việc gửi email qua SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        String content = "";

//        if (subject.equals("Xác thực tài khoản tại hệ thống giao dịch trung gian GD77_G6")) {
//            content += "Chào mừng bạn đến với bước đầu tiên trong việc đăng ký thành một thành viên mới. Đầu tiên bạn cần kích hoạt tài khoản của mình. Vui lòng không chia sẻ mã này với bất kỳ ai.\n"
//                    + "Mã OTP của bạn là: ";
//        }
        if (subject.equals("Xác thực tài khoản")) {
            content += "<div\n"
                    + "      style=\"\n"
                    + "      width: 50vw;\n"
                    + "      margin: 0 auto;\n"
                    + "      padding: 20px 100px;\n"
                    + "      text-align: center; \n"
                    + "      border: 3px solid black; \n"
                    + "      border-radius: 10px\n"
                    + "      \"\n"
                    + "    >\n"
                    + "    <h1>Xác thực email của bạn trên hệ thống giao dịch trung gian</h1>\n"
                    + "      <h3>Cảm ơn bạn đã là một trong những khách hàng quý giá của chúng tôi</h3>\n"
                    + "      <small\n"
                    + "        >Vui lòng nhập mã này vào liên kết xác minh để xác nhận danh tính của bạn.</small>\n"
                    + "      <div\n"
                    + "        style=\"\n"
                    + "          background-color: rgb(171, 168, 168);\n"
                    + "          border-radius: 10px;\n"
                    + "          padding: 10px;\n"
                    + "          width: 30%;\n"
                    + "          margin: 20px auto;\n"
                    + "          letter-spacing: 10px;\">\n"
                    + "        <h1>" + otp + "</h1>\n"
                    + "      </div>\n"
                    + "      <small\n"
                    + "        >Nếu bạn không yêu cầu mã, bạn có thể bỏ qua email này.</small\n"
                    + "      >\n"
                    + "    </div>";
        }

        MimeMessage message = new MimeMessage(session);
        try {
            // Tạo đối tượng MimeMessage

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // Thiết lập thông tin người gửi, người nhận, và nội dung email
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject, "UTF-8");
            message.setContent(content, "text/HTML; charset=UTF-8");

            // Gửi email
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void threadSendEmail(String toEmail, String subject, String otp) {
        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                sendOTP(toEmail, subject, otp);
            }
        });
         thread.start();     
    }
    
    
    
}
