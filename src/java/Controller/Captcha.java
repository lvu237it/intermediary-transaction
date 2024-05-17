


package Controller;

import utils.*;
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Captcha extends HttpServlet {

    public static final String FILE_TYPE = "jpeg";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String captcha = Captcha.generateCaptcha(4);
        // gọi session
        HttpSession ss = request.getSession();
        //trường hợp đã có captchas ở trên ss (có trước đó, nhưng có thể chưa/gần hết hạn) => xoá captcha cũ
        ss.removeAttribute("captchas"); 
        //tạo captcha với thời gian hiệu lực mới
        ss.setAttribute("captchas", captcha);//đặt 1 thuộc tính captcha mới vào ss đó
        ss.setMaxInactiveInterval(30);//đặt thời gian 30s cho 1 captcha
        int width = 140, height = 35;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        // Set màu nền
        Color backgroundColor = new Color(255, 207, 20); // Màu vàng
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        // Vẽ dòng kẻ ngang chéo màu đỏ
        graphics.setColor(Color.RED);
        graphics.drawLine(0, height - 1, width - 1, 0);
        // Vẽ dòng kẻ ngang giữa màu xanh
        graphics.setColor(Color.BLUE);
        graphics.drawLine(0, height / 2, width - 1, height / 2);
        // Vẽ chữ Captcha
        graphics.setFont(new Font("Arial", Font.BOLD, 25)); //24
        graphics.setColor(new Color(30, 144, 255));
        graphics.drawString(captcha, 10, 27);

        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, FILE_TYPE, outputStream);
        outputStream.close();
    }

   public static String generateCaptcha(int otpLength) {
       
        StringBuilder otp = new StringBuilder();

        Random r = new Random();
        for (int i = 0; i < otpLength; i++) {
            otp.append(r.nextInt(10));
        }
        
        return otp.toString();
    }     

}
