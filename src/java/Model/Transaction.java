/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class Transaction {
    private int id;
    private double amount;
    private boolean type; // type of "+" or "-" transaction
    private String detail;
    private boolean status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int createdBy;//userId of user table

    public Transaction() {
    }

    public Transaction(int id, double amount, boolean type, String detail, boolean status, Timestamp createdAt, Timestamp updatedAt, int createdBy) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.detail = detail;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
    }

    public Transaction(double amount, boolean type, String detail, boolean status, int createdBy) {
        this.amount = amount;
        this.type = type;
        this.detail = detail;
        this.status = status;
        this.createdBy = createdBy;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", amount=" + amount + ", type=" + type + ", detail=" + detail + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + '}';
    }
    
}
