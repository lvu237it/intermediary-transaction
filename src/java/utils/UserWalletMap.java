/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import DAO.UserDAO;
import Model.User;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 *
 * @author User
 */
public class UserWalletMap {
    public HashMap<String, Double> walletMap = new HashMap<>();
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Định dạng số với 2 chữ số sau dấu thập phân
    
    public void updateUserWalletMapByUsername(String username, double newBalance){
        walletMap.put(username, newBalance);
    }
    
    public HashMap<String, Double> getUserWalletMap(User user){
        UserDAO userDao = new UserDAO();
        double balance = userDao.getUserWalletByUsername(user.getUserName());//lấy số dư tương ứng của user từ database
        String formattedBalance = decimalFormat.format(balance); // Làm tròn số balance
        walletMap.put(user.getUserName(), Double.parseDouble(formattedBalance)); // Chuyển đổi lại thành Double
        return walletMap;
    }
}   
