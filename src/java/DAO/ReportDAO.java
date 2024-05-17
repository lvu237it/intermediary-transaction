/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Report;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class ReportDAO extends MyDAO {

    
    ArrayList<Report> list = new ArrayList<>();

    public ArrayList<Report> loadReports(int start, int length, String columnIndex, String direction) {
        xSql = "select * from Report";
        if (columnIndex != null) {
            switch (columnIndex) {
                case "0":
                    xSql += " order by id " + direction;
                    break;
                case "1":
                    xSql += " order by orderId " + direction;
                    break;
                case "2":
                    xSql += " order by createdBy " + direction;
                    break;
                case "3":
                    xSql += " order by status " + direction;
                    break;
                case "4":
                    xSql += " order by buyerId " + direction;
                    break;
                case "5":
                    xSql += " order by sellerId " + direction;
                    break;
                case "6":
                    xSql += " order by createdAt " + direction;
                    break;
                case "7":
                    xSql += " order by updatedAt " + direction;
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
                list.add(new Report(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
            }
            ps.close();
        } catch (Exception e) {
        }
        return list;
    }
    
    public void InsertNewReport(String createdBy, String orderId, String buyerId, String sellerId, String status, String description) {

        ArrayList<Report> rpList = getAllReport();
        if (rpList.isEmpty()) {
            xSql = "insert into `report`(`id`, `createdBy`, `orderId`, `buyerId`,`sellerId`, `status`, `description`)\n"
                    + "values(?,?,?,?,?,?,?);";

            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, "1");//người bán
                ps.setString(2, createdBy);//
                ps.setString(3, orderId);
                ps.setString(4, buyerId);
                ps.setString(5, sellerId);
                ps.setString(6, status);
                ps.setString(7, description);
                ps.executeUpdate();
                ps.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            xSql = "insert into `report`(`createdBy`, `orderId`, `buyerId`,`sellerId`, `status`, `description`)\n"
                    + "values(?,?,?,?,?,?);";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, createdBy);//người bán
                ps.setString(2, orderId);//
                ps.setString(3, buyerId);
                ps.setString(4, sellerId);
                ps.setString(5, status);
                ps.setString(6, description);
                ps.executeUpdate();
                ps.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
    
    public ArrayList<Report> getAllReport() {
        try {
            xSql = "select * from Report";

            ps = con.prepareStatement(xSql);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Report(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public int getTotalRecords() {
        try {
            xSql = "SELECT COUNT(*) AS total FROM Report";
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

    public Report getReportDetails(int id) {
        xSql = "select * from Report where id =?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Report(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateReportStatus(int reportId, int status) {
        xSql = "update `report`\n"
                + "set `status` = ? \n"
                + "where `id` = ?;";
        try{
            ps = con.prepareStatement(xSql);
            ps.setInt(1, status);
            ps.setInt(2, reportId);
            ps.executeUpdate();
            ps.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        

    }
}
