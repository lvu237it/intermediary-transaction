/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.VNPayTransaction;

/**
 *
 * @author ADMIN
 */
public class VNPayTransactionDAO extends MyDAO {
    
    
    public void insertNewVnPayTrans(VNPayTransaction x) {
        xSql = "insert into `vnpaytransaction`(`id`, `status`, `amount`, `createdBy`)\n"
                + "values(?, ?, ?, ?);";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, x.getId());
            ps.setString(2, x.getStatus());
            ps.setDouble(3, x.getAmount());
            ps.setInt(4, x.getCreatedBy());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public VNPayTransaction getVnpTransByCode(String id) {
        xSql = "select * from `vnpaytransaction`\n"
                + "where id = ?;";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new VNPayTransaction(rs.getString(1),rs.getString(2),rs.getDouble(3),
                rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateVNPTransactionStatus(String status, VNPayTransaction vnp) {
        xSql = "update `vnpaytransaction`\n"
                + "set `status` = ?\n"
                + "where `id` = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            ps.setString(2, vnp.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

    
    public static void main(String[] args) {
        
//        VNPayTransactionDAO dao = new VNPayTransactionDAO();
//        
//        VNPayTransaction v = dao.getVnpTransByCode("54200999");
//      
//        
//        System.out.println(v.getStatus());
    }
}
