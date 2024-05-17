/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import Model.Withdraw;
import java.sql.SQLException;
import java.util.*;
import utils.Encryption;

/**
 *
 * @author User
 */
public class UserDAO extends MyDAO {

    ArrayList<User> list = new ArrayList<>();

    public boolean checkUsernameExist(String accountName) {
        xSql = "SELECT * FROM User where User.username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, accountName);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return false;
    }

    public User login(String accountName, String password) {
        try {
            xSql = "SELECT * FROM User where User.username = ? and User.password = ?";
            ps = con.prepareStatement(xSql);
            ps.setString(1, accountName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getDouble(10), rs.getString(11), rs.getString(12),
                        rs.getTimestamp(13));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public String getHashedPassword(String accountName, String password) {
        try {
            xSql = "SELECT * FROM User where User.username = ? and User.password = ?";
            ps = con.prepareStatement(xSql);
            ps.setString(1, accountName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                return password;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public void insertNewAccount(String username, String password, String gmail, String fullname, String phone, String description) {
        xSql = "insert into user(`username`, `password`, `gmail`, `fullname`, `phoneNumber`, `wallet`, `description`)\n"
                + "values (?,?,?,?,?,0,?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, gmail);
            ps.setString(4, fullname);
            ps.setString(5, phone);
            ps.setString(6, description);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public void updateUserInformation(String fullname, String phoneNumber, String description, String username) {
        xSql = "update user\n"
                + "set\n"
                + "	`fullname` = ?,\n"
                + "	`phoneNumber` = ?,\n"
                + "    `description` = ?\n"
                + "where `username` = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, fullname);
            ps.setString(2, phoneNumber);
            ps.setString(3, description);
            ps.setString(4, username);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public String getEmailByUsername(String username) {
        xSql = "select User.gmail from User where User.username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return "";
    }

    public void updatePasswordByUsername(String newPassword, String username) {
        xSql = "UPDATE User SET User.password = ? WHERE User.username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public String getPasswordByUsername(String username) {
        xSql = "select User.password from User where User.username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public User getUserbyId(int id) {
        xSql = "select * from user where id =?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getDouble(10), rs.getString(11), rs.getString(12),
                        rs.getTimestamp(13));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        xSql = "select * from user where username =?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getDouble(10), rs.getString(11), rs.getString(12),
                        rs.getTimestamp(13));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public void updateInformationbyId(String fullname, String phoneNumber, String description, String gmail, String id) {
        xSql = "Update User\n"
                + "set `fullname` = ?,\n"
                + "	`phoneNumber` = ?,\n"
                + "    `description` = ?,\n"
                + "    `gmail` = ?\n"
                + "    where id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, fullname);
            ps.setString(2, phoneNumber);
            ps.setString(3, description);
            ps.setString(4, gmail);
            ps.setString(5, id);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }

    }


    public ArrayList<User> getAllUserInformation() {
        ArrayList<User> list = new ArrayList<>();
        User u = null;


        xSql = "SELECT * \n"
                + "FROM `user`\n"
                + "where isDeleted = 0\n"
                + "order by id \n";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getDouble(10), rs.getString(11),
                        rs.getString(12), rs.getTimestamp(13));
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public int countAllUser() {
        xSql = "select count(*) from user;";

        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return 0;
    }

    public void deleteAnUser(int id) {
        xSql = "update user\n"
                + "set `isDeleted` = 1\n"
                + "where id = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");

        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public void updateUserWallet(String username, double newBalance) {
        xSql = "UPDATE User\n"
                + "SET wallet = ?\n"
                + "WHERE username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDouble(1, newBalance);
            ps.setString(2, username);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }
    
    public double getUserWalletByUsername(String username) {
        xSql = "select wallet from User where User.username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getDouble(1);
            }
            ps.close(); System.out.println("Closing connection successfully");
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return 0;
    }
    
    public void updateUserWalletById(User user, double balance) {
        xSql = "update User Set wallet = ? where User.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDouble(1, balance);
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try {
                if (ps != null) {
                    ps.close(); System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }
    public void updateUserWalletById(int uId, double balance) {
        xSql = "update User Set wallet = ? where User.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDouble(1, balance);
            ps.setInt(2, uId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public String getFullnameByUserid(int userId){
        xSql = "select fullname from User where User.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString(1);
            }
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    

    public static void main(String[] args) {
       
        UserDAO dao = new UserDAO();
        WithdrawDAO dao2 = new WithdrawDAO();
        ArrayList<Withdraw> list = dao2.loadWithDrawByAdmin(0, 20, null, null);
        for (Withdraw withdraw : list) {
            User user = new User(dao.getFullnameByUserid(withdraw.getCreatedBy()));
            withdraw.setUser(user);
            System.out.println(withdraw);
        }
//        //test case for Encryption
//        Encryption encrypt = new Encryption();
//        String username = "admin";
//        String password = "123";
//
//        //Mã hoá mật khẩu nhập vào
//        String encryptedPass = encrypt.toSHA1(password);
////        User user = new User();
//            for (int i = 0; i < 20; i++) {
//            System.out.println(dao.getUserbyId(i));
//        }
////        //kiểm tra pass được encrypt có khớp với pass trong database hay không?
//        if (encryptedPass == dao.getHashedPassword(username, encryptedPass)) {
//            System.out.println("true");
//        }
//        dao.updatePasswordByUsername(encryptedPass, username);
//        //kiểm tra đăng nhập với mật khẩu đã được mã hoá => kiểm tra có khớp với mật khẩu trong database hay không
//        User user = dao.login(username, encryptedPass);
//        System.out.println(user.getFullName());
//    }

//        ArrayList<User> userList = dao.getAllUser();
//        for (User obj : userList) {
//            System.out.println(obj);
//        }
//     dao.insertNewAccount(username, encryptedPass, "intermediarysystem1755@gmail.com");
//        User anUser = dao.login("u", "123");
//        System.out.println(anUser);
//        User user = dao.login("peterparker", "234211");
//        System.out.println(user);
//      dao.updateInformationbyId("Tô Trọng Nghĩa", "0985783409", "fdfddf", "tonghia2003@gmail.com", "1");
//        boolean checkUserNameExist = dao.checkUserNameExist("xx");
//        dao.updateUserWallet("user1", 9029.0);
    }

}
