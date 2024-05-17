/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Correspond;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class CorrespondDAO extends MyDAO {

    ArrayList<Correspond> list = new ArrayList<>();

    public void deleteAnRecordOfCorrespondByOrderId(String orderId) {
        xSql = "DELETE FROM Correspond WHERE oId = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, orderId);
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

    public ArrayList<Correspond> getAllCorrespond() {
        xSql = "select * from `Correspond`";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Correspond(rs.getInt(1), rs.getInt(2)));
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
        return list;
    }

    public void insertNewCorrespond(String orderId, String productId) {
        xSql = "insert into `correspond`(`oid`, `pid`)\n"
                + "values (?,?);";
        try{
            ps = con.prepareStatement(xSql);
            ps.setString(1,orderId);
            ps.setString(2, productId);
            ps.executeUpdate();
            ps.close(); System.out.println("Closing connection successfully");
        }catch(Exception e){
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

    public static void main(String[] args) {
        CorrespondDAO dao = new CorrespondDAO();
        ArrayList<Correspond> correspondList = dao.getAllCorrespond();
        for (Correspond correspond : correspondList) {
            System.out.println(correspond);
        }
    }
}
