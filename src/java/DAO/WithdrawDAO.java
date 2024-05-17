/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Withdraw;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class WithdrawDAO extends MyDAO {

    ArrayList<Withdraw> list = new ArrayList();

    public ArrayList<Withdraw> loadWithDrawByUser(int start, int length, int id, String columnName, String direction) {
        xSql = "select * from Withdraw where createdBy = ?";
        if (columnName != null) {
            switch (columnName) {
                case "0":
                    xSql += " order by id " + direction;
                    break;
                case "1":
                    xSql += " order by status " + direction;
                    break;
                case "2":
                    xSql += " order by withdrawAmount " + direction;
                    break;
                case "3":
                    xSql += " order by bankAccountNumber " + direction;
                    break;
                case "4":
                    xSql += " order by bankAccountName " + direction;
                    break;
                case "5":
                    xSql += " order by bankName " + direction;
                    break;
                case "6":
                    xSql += " order by response " + direction;
                    break;
                case "7":
                    xSql += " order by createdAt " + direction;
                    break;
                case "8":
                    xSql += " order by updatedAt " + direction;
                    break;
                case "9":
                    xSql += " order by id " + direction;
                    break;
                default:
                    break;
            }
        }
        xSql += " limit ? offset ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            ps.setInt(2, length);
            ps.setInt(3, start);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Withdraw(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8), rs.getInt(9), rs.getTimestamp(10), rs.getString(11)));
            }
            ps.close();
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Withdraw> loadWithDrawByAdmin(int start, int length, String columnName, String direction) {
        xSql = "select * from Withdraw";
        if (columnName != null) {
            switch (columnName) {
                case "0":
                    xSql += " order by id " + direction;
                    break;
                case "1":
                    xSql += " order by status " + direction;
                    break;
                case "2":
                    xSql += " order by createdBy " + direction;
                    break;
                case "3":
                    xSql += " order by withdrawAmount " + direction;
                    break;
                case "4":
                    xSql += " order by bankAccountNumber " + direction;
                    break;
                case "5":
                    xSql += " order by bankAccountName " + direction;
                    break;
                case "6":
                    xSql += " order by bankName " + direction;
                    break;
                case "7":
                    xSql += " order by response " + direction;
                    break;
                case "8":
                    xSql += " order by createdAt " + direction;
                    break;
                case "9":
                    xSql += " order by updatedAt " + direction;
                    break;
                case "10":
                    xSql += " order by id " + direction;
                    break;
                default:
                    break;
            }
        }
        xSql += " limit ? offset ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, length);
            ps.setInt(2, start);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Withdraw(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8), rs.getInt(9), rs.getTimestamp(10), rs.getString(11)));
            }
            ps.close();
        } catch (Exception e) {
        }
        return list;
    }
    public int getTotalRecordsByUser(int userId) {
        try {
            xSql = "SELECT COUNT(*) AS total FROM Withdraw where createdBy = ?";
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            rs.next();
            int total = rs.getInt("total");
            return total;

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }
    public int getTotalRecordsByAdmin() {
        try {
            xSql = "SELECT COUNT(*) AS total FROM Withdraw";
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            rs.next();
            int total = rs.getInt("total");
            return total;

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }

    public Withdraw getWithdrawalDetails(int id) {
        xSql = "select * from Withdraw where id =?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Withdraw(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8), rs.getInt(9), rs.getTimestamp(10), rs.getString(11));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public void updateWithdrawalByUser(int wId,String bankAccountNumber, String bankAccountName,String bankName ){
         xSql = "UPDATE `Withdraw` \n"
                + "SET bankAccountNumber = ?, \n"
                + "bankAccountName = ?, \n"
                + "bankName = ? \n"
                + "WHERE id = ? \n"
                + "LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1,bankAccountNumber);
            ps.setString(2,bankAccountName);
            ps.setString(3,bankName);
            ps.setInt(4, wId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void createNewWithdraw(int status, double withdrawAmount, String bankAccountNumber, String bankAccountName, String bankName, int createdBy){
        xSql = "INSERT INTO `Withdraw` (status, withdrawAmount, bankAccountNumber, bankAccountName, bankName, createdBy, evident, response)\n" +
"VALUES (?, ?, ?, ?, ?, ?, '', '');";
        try{
            ps = con.prepareStatement(xSql);
            ps.setInt(1, status);
            ps.setDouble(2, withdrawAmount);
            ps.setString(3, bankAccountNumber);
            ps.setString(4, bankAccountName);
            ps.setString(5, bankName);
            ps.setInt(6, createdBy);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void updateWithdrawStatus(int wId, int status) {
        xSql = "UPDATE `Withdraw` \n"
                + "SET status = ? \n"
                + "WHERE id = ? \n"
                + "LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, status);
            ps.setInt(2, wId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateWithdraw(int wId, int status, String response, String imgUrl) {
        xSql = "UPDATE `Withdraw` \n"
                + "SET status = ?, \n"
                + "response = ?, \n"
                + "evident = ? \n"
                + "WHERE id = ? \n"
                + "LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, status);
            ps.setString(2,response);
            ps.setString(3, imgUrl);
            ps.setInt(4, wId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
WithdrawDAO dao = new WithdrawDAO();
        System.out.println(1);
        ArrayList<Withdraw> Wlist = dao.loadWithDrawByUser(0, 5, 1, null, null);
        for (Withdraw withdraw : Wlist) {
            System.out.println(withdraw.toString());
        }
    }

}
