/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class Report {
    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int createdBy;
    private String orderId;
    private int buyerId;
    private int sellerId;
    private int status;
    private String description;
    
    private String createdByFullname;
    private String buyerFullname;
    private String sellerFullname;

    
    public Report() {
    }
    
    public Report(int id, Timestamp createdAt, Timestamp updatedAt, int createdBy, String orderId, int buyerId, int sellerId, int status, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.status = status;
        this.description = description;
    }

    public Report(int id, Timestamp createdAt, Timestamp updatedAt, int createdBy, String orderId, int buyerId, int sellerId, int status, String description, String createdByFullname, String buyerFullname, String sellerFullname) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.status = status;
        this.description = description;
        this.createdByFullname = createdByFullname;
        this.buyerFullname = buyerFullname;
        this.sellerFullname = sellerFullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedByFullname() {
        return createdByFullname;
    }

    public void setCreatedByFullname(String createdByFullname) {
        this.createdByFullname = createdByFullname;
    }

    public String getBuyerFullname() {
        return buyerFullname;
    }

    public void setBuyerFullname(String buyerFullname) {
        this.buyerFullname = buyerFullname;
    }

    public String getSellerFullname() {
        return sellerFullname;
    }

    public void setSellerFullname(String sellerFullname) {
        this.sellerFullname = sellerFullname;
    }   

    @Override
    public String toString() {
        return "Report{" + "id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", orderId=" + orderId + ", buyerId=" + buyerId + ", sellerId=" + sellerId + ", status=" + status + ", description=" + description + ", createdByFullname=" + createdByFullname + ", buyerFullname=" + buyerFullname + ", sellerFullname=" + sellerFullname + '}';
    }
    
}
