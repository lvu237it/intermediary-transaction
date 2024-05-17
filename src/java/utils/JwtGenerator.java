/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author User
 */
import Model.User;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import org.json.JSONObject;

public class JwtGenerator {

    private static final long EXPIRATION_TIME = 3600000;// 3600 seconds //  1 hour

    public String generateJwt(User user) {
        try {
            SecretKey secretKey = generateSecretKey();
            long nowMillis = System.currentTimeMillis();
            long expirationMillis = nowMillis + EXPIRATION_TIME;

            // Tạo một đối tượng JSON
            JSONObject payloadJson = new JSONObject(user);
            payloadJson.put("exp", expirationMillis / 1000); // chuyển thành giây

            // Chuyển đối tượng JSON thành chuỗi
            String payload = payloadJson.toString();

            //mã hoá header và payload để tạo thành data
            String header = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}";
            String headerEncode = base64urlEncode(header.getBytes(StandardCharsets.UTF_8));
            String payloadEncode = base64urlEncode(payload.getBytes(StandardCharsets.UTF_8));
            String data = headerEncode + "." + payloadEncode;

            //Sử dụng thuật toán HmacSHA256 để tính toán mã xác thực (MAC) cho dữ liệu đầu vào -  để tạo chữ ký số (signature) cho JWT
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(secretKey);

            //toán mã xác thực (MAC) cho dữ liệu đầu vào "data" và mã hoá nó thành signature
            byte[] hashedData = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String signature = base64urlEncode(hashedData);

            return data + "." + signature; // JWT includes: headerEncode.payloadEncode.signature
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            // Giải mã payload từ token
            String payload = extractPayload(token);

            // Chuyển đối payload từ chuỗi JSON sang đối tượng JSON
            JSONObject payloadJson = new JSONObject(payload);

            // Lấy thời gian hết hạn từ payload
            long expirationTime = payloadJson.getLong("exp") * 1000; // chuyển đổi từ giây sang mili giây

            // Kiểm tra xem thời gian hết hạn có trước thời điểm hiện tại không
            return expirationTime < System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Nếu có lỗi, xem xét token đã hết hạn để tránh sử dụng nó
        }
    }

    // Phương thức giúp trích xuất payload từ token
    private String extractPayload(String token) {
        try {
            String[] parts = token.split("\\.");
            return new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        return keyGenerator.generateKey();
    }

    private String base64urlEncode(byte[] input) {
        return Base64.getUrlEncoder().encodeToString(input);
    }
}
