/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author ADMIN
 */
import java.security.MessageDigest;
import java.util.Random;


public class Encryption {
    // md5
    // sha-1 => thường được sử dụng

    public static String toSHA1(String input) {
        
        String salt = "asjrlkmcoewj@tjle;oxqskjhdjksjf1jurVn";// Make passwords complex
        String result = null;
        input = input + salt;
        try {
            
            // Initialize MessageDigest with SHA-1 algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            
            // Update digest with input bytes
            md.update(input.getBytes("UTF-8"));
            
            // Calculate the digest
            byte[] digest = md.digest();

            // Builder to store hex string
            StringBuilder hexString = new StringBuilder();

            for (byte b : digest) {
                // Append byte as 2-digit hex to builder
                hexString.append(String.format("%02x", b));
            }

            // Return the hexString
            return hexString.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(toSHA1("123456"));
    }

     public static String generateOTP() {
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();

        Random r = new Random();
        for (int i = 0; i < otpLength; i++) {
            otp.append(r.nextInt(10));
        }
        
        return otp.toString();
    }
      
}
