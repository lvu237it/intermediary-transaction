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
public class Withdraw {

    private int id;
    private int status;
    private double withdrawAmount;
    private String bankAccountNumber;
    private String bankAccountName;
    private String bankName;
    private String response;
    private Timestamp createdAt;
    private int createdBy;
    private Timestamp updatedAt;
    private String evident;

    private User user;

    public Withdraw(int id, int status, double withdrawAmount, String bankAccountNumber, String bankAccountName, String bankName, String response, Timestamp createdAt, int createdBy, Timestamp updatedAt, String evident) {
        this.id = id;
        this.status = status;
        this.withdrawAmount = withdrawAmount;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountName = bankAccountName;
        this.bankName = bankName;
        this.response = response;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.evident = evident;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEvident() {
        return evident;
    }

    public void setEvident(String evident) {
        this.evident = evident;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Withdraw{" + "id=" + id + ", status=" + status + ", withdrawAmount=" + withdrawAmount + ", bankAccountNumber=" + bankAccountNumber + ", bankAccountName=" + bankAccountName + ", bankName=" + bankName + ", response=" + response + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", evident=" + evident + ", user=" + (user == null ? "null" : user.toString()) + '}';
    }

}
