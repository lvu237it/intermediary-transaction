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
public class User {
    
    private int userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String userName;
    private String password;
    private String gmail;
    private int role;
    private String fullName;
    private String phoneNumber;
    private double wallet;
    private String description;
    private String isDeleted;
    private Timestamp deletedAt;

    public User() {
    }

    public User(String fullName) {
        this.fullName = fullName;
    }
    
    public User(int userId, String gmail, String fullName, double wallet) {
        this.userId = userId;
        this.gmail = gmail;
        this.fullName = fullName;
        this.wallet = wallet;
    }

    public User(int userId, Timestamp createdAt, Timestamp updatedAt, String userName, String password, String gmail,int role, String fullName, String phoneNumber, double wallet, String description, String isDeleted, Timestamp deletedAt) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
        this.password = password;
        this.gmail = gmail;
        this.role = role;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.wallet = wallet;
        this.description = description;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

  
   

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
    
    

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", userName=" + userName + ", password=" + password + ", gmail=" + gmail + ", role=" + role + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", wallet=" + wallet + ", description=" + description + ", isDeleted=" + isDeleted + ", deletedAt=" + deletedAt + '}';
    }

    
}
