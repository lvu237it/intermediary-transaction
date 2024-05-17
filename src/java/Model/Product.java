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
public class Product {
    private String id;
    private String name;
    private String description;
    private boolean feePayer;
    private int isPrivate;
    private String hiddenField;
    private double price;
    private double feeOnSuccess;
    private String contact;
    private boolean isPurchased; //added: 3/3/2024 -- kiểm tra xem đơn hàng đã được mua hay chưa  :1 - rồi, 0 - chưa
    private String type; //phân loại sản phẩm
    private boolean updateable;
    private String shareLink;
    private boolean isDeleted;
    private int createdBy; //corresponding to userId of user table
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    
    //Additional field for create other constructors
    private User user;
    private int status;
    private int orderCreatedBy; //corresponding to userId of user table
    private String orderId;
    private String buyerName;
    
    public Product() {
    }


    public Product(String id, String name, String description, int status, int orderCreatedBy, String hiddenField, String contact, String shareLink, int isPrivate, double price, boolean feePayer, String type, int createdBy, String orderId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.hiddenField = hiddenField;
        this.price = price;
        this.contact = contact;
        this.shareLink = shareLink;
        this.type = type;
        this.createdBy = createdBy;
        this.orderCreatedBy = orderCreatedBy;
        this.orderId = orderId;
        this.feePayer = feePayer;
        this.createdBy = createdBy;
        this.status = status;
    }
    
   public Product(String id, String name, String description, int status, String buyerName, String hiddenField, String contact, String shareLink, int isPrivate, double price, boolean feePayer, String type, int createdBy, String orderId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.hiddenField = hiddenField;
        this.price = price;
        this.contact = contact;
        this.shareLink = shareLink;
        this.type = type;
        this.createdBy = createdBy;
        this.buyerName = buyerName;
        this.orderId = orderId;
        this.feePayer = feePayer;
        this.createdBy = createdBy;
        this.status = status;
    }

    public Product(String id, String name, String description, boolean feePayer, int isPrivate, double price, double feeOnSuccess, String contact, boolean isPurchased, String type, boolean updateable, String shareLink, boolean isDeleted, int createdBy, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.feePayer = feePayer;
        this.isPrivate = isPrivate;
        this.price = price;
        this.feeOnSuccess = feeOnSuccess;
        this.contact = contact;
        this.isPurchased = isPurchased;
        this.type = type;
        this.updateable = updateable;
        this.shareLink = shareLink;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }



public Product(String id, String name, String description, boolean feePayer, int isPrivate, String hiddenField, double price, double feeOnSuccess, String contact, boolean isPurchased,String type, boolean updateable,String shareLink, boolean isDeleted, int createdBy, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.feePayer = feePayer;
        this.isPrivate = isPrivate;
        this.hiddenField = hiddenField;
        this.price = price;
        this.feeOnSuccess = feeOnSuccess;
        this.contact = contact;
        this.isPurchased = isPurchased;
        this.type = type;
        this.updateable = updateable;
        this.shareLink = shareLink;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
    
    
    public Product(String id, String name, String description, boolean feePayer, int isPrivate, String hiddenField, double price, String contact, String type, boolean isDeleted, int createdBy, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.feePayer = feePayer;
        this.isPrivate = isPrivate;
        this.hiddenField = hiddenField;
        this.price = price;
        this.feeOnSuccess = feeOnSuccess;
        this.contact = contact;
        this.isPurchased = isPurchased;
        this.type = type;
        this.updateable = updateable;
        this.shareLink = shareLink;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public int getOrderCreatedBy() {
        return orderCreatedBy;
    }

    public void setOrderCreatedBy(int orderCreatedBy) {
        this.orderCreatedBy = orderCreatedBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeePayer() {
        return feePayer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    public void setFeePayer(boolean feePayer) {
        this.feePayer = feePayer;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getHiddenField() {
        return hiddenField;
    }

    public void setHiddenField(String hiddenField) {
        this.hiddenField = hiddenField;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price <= 0){
            this.price = 0;
        }
        this.price = price;
    }

    public double getFeeOnSuccess() {
        return feeOnSuccess;
    }

    public void setFeeOnSuccess(double feeOnSuccess) {
        this.feeOnSuccess = feeOnSuccess;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUpdateable() {
        return updateable;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", feePayer=" + feePayer + ", isPrivate=" + isPrivate + ", hiddenField=" + hiddenField + ", price=" + price + ", feeOnSuccess=" + feeOnSuccess + ", contact=" + contact + ", isPurchased=" + isPurchased + ", type=" + type + ", updateable=" + updateable + ", shareLink=" + shareLink + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", user=" + user + ", status=" + status + ", orderCreatedBy=" + orderCreatedBy + ", orderId=" + orderId + '}';
    }
}

