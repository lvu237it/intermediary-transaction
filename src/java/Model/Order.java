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
public class Order{

    private String id;
    private int createdBy; //userId of user table
    private double totalPrice;//phi san pham + phi giao dich (ben chiu phi)
    private double sellerReceivedTrueMoney; 
    private int status;    
    private int customerId; //Unused
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    //Additional field for create other constructors
    private String productname;
    private String productcontact;
    private int isPrivate;
    private double productprice;
    private boolean feePayer;
    private double transactionfee;
    
    private String type;
    private String hiddenField;
    private int productCreatedBy;
    private String sellerName;
    private String shareLink;

    public Order() {
    }
    
    public Order(String id, int createdBy, double totalPrice, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.createdBy = createdBy;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(String id, int createdBy, double totalPrice, double sellerReceivedTrueMoney, int status, int customerId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.createdBy = createdBy;
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sellerReceivedTrueMoney = sellerReceivedTrueMoney;
    }

    //14 param
    public Order(String id,String productname, String hiddenField,int status, int productCreatedBy, String productcontact, int isPrivate, double productprice,boolean feePayer, double transactionfee, double totalPrice,  String type,  Timestamp createdAt, Timestamp updatedAt    ) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productname = productname;
        this.productcontact = productcontact;
        this.isPrivate = isPrivate;
        this.productprice = productprice;
        this.feePayer = feePayer;
        this.transactionfee = transactionfee;
        this.type = type;
        this.hiddenField = hiddenField;
        this.productCreatedBy = productCreatedBy;
    }

    //16 param
    public Order(String id, String productname, String hiddenField, double sellerReceivedTrueMoney,int status, int productCreatedBy, String productcontact, int isPrivate, double productprice,boolean feePayer, double transactionfee, double totalPrice,  String type, String shareLink, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.hiddenField = hiddenField;
        this.productCreatedBy = productCreatedBy;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productname = productname;
        this.productcontact = productcontact;
        this.isPrivate = isPrivate;
        this.productprice = productprice;
        this.feePayer = feePayer;
        this.transactionfee = transactionfee;
        this.sellerReceivedTrueMoney = sellerReceivedTrueMoney;
        this.type = type;
        this.shareLink = shareLink;
    }
    
    //16 param - change int productCreatedBy to sellerName
    public Order(String id, String productname, String hiddenField, double sellerReceivedTrueMoney,int status, String sellerName, String productcontact, int isPrivate, double productprice,boolean feePayer, double transactionfee, double totalPrice,  String type, String shareLink, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.hiddenField = hiddenField;
        this.sellerName = sellerName;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productname = productname;
        this.productcontact = productcontact;
        this.isPrivate = isPrivate;
        this.productprice = productprice;
        this.feePayer = feePayer;
        this.transactionfee = transactionfee;
        this.sellerReceivedTrueMoney = sellerReceivedTrueMoney;
        this.type = type;
        this.shareLink = shareLink;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductcontact() {
        return productcontact;
    }

    public void setProductcontact(String productcontact) {
        this.productcontact = productcontact;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public double getProductprice() {
        return productprice;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }

    public boolean isFeePayer() {
        return feePayer;
    }

    public void setFeePayer(boolean feePayer) {
        this.feePayer = feePayer;
    }

    public double getTransactionfee() {
        return transactionfee;
    }

    public void setTransactionfee(double transactionfee) {
        this.transactionfee = transactionfee;
    }

    public double getSellerReceivedTrueMoney() {
        return sellerReceivedTrueMoney;
    }

    public void setSellerReceivedTrueMoney(double sellerReceivedTrueMoney) {
        this.sellerReceivedTrueMoney = sellerReceivedTrueMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHiddenField() {
        return hiddenField;
    }

    public void setHiddenField(String hiddenField) {
        this.hiddenField = hiddenField;
    }

    public int getProductCreatedBy() {
        return productCreatedBy;
    }

    public void setProductCreatedBy(int productCreatedBy) {
        this.productCreatedBy = productCreatedBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }
    
    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", createdBy=" + createdBy + ", totalPrice=" + totalPrice + ", sellerReceivedTrueMoney=" + sellerReceivedTrueMoney + ", status=" + status + ", customerId=" + customerId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", productname=" + productname + ", productcontact=" + productcontact + ", isPrivate=" + isPrivate + ", productprice=" + productprice + ", feePayer=" + feePayer + ", transactionfee=" + transactionfee + ", type=" + type + ", hiddenField=" + hiddenField + ", productCreatedBy=" + productCreatedBy + '}';
    }

}
