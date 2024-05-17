/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Order;
import Model.Product;
import Model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.GenerateUUID;

/**
 *
 * @author User
 */
public class ProductDAO extends MyDAO {

    static GenerateUUID uuidString = new GenerateUUID();
    ArrayList<Product> list = new ArrayList<>();

    public String getUserByProductId(String pid) {
        xSql = "select u.username from User u\n"
                + "                    join `Order` o on u.id = o.createdBy\n"
                + "                    join `Correspond` c on c.oId = o.id\n"
                + "                    join `Product` p on p.id = c.pId\n"
                + "                    where p.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, pid);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public Product getProductbyProductID(int productId) {
        xSql = "SELECT p.id, p.name, p.description,\n"
                + "       CASE WHEN o.status IS NOT NULL THEN o.status ELSE 1 END AS orderStatus, \n"
                + "       CASE WHEN o.createdBy IS NOT NULL THEN o.createdBy ELSE NULL END AS orderCreatedBy, \n"
                + "       p.hiddenField, p.contact, p.shareLink, p.isPrivate, p.price, p.feePayer, p.type, p.createdBy, COALESCE(o.id, '')\n"
                + "FROM Product p\n"
                + "LEFT JOIN Correspond c ON c.pId = p.id\n"
                + "LEFT JOIN `Order` o ON o.id = c.oId\n"
                + "LEFT JOIN `User` u ON u.id = p.createdBy\n"
                + "WHERE p.id = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getDouble(10), rs.getBoolean(11), rs.getString(12), rs.getInt(13), rs.getString(14));
            }
        } catch (Exception e) {
            System.out.println("error when query sell order: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null; 
    }

    public String getProductIdbyOrderId(String oId) {
        xSql = "		SELECT product.id\n"
                + "FROM `order`\n"
                + "JOIN correspond ON `order`.id = correspond.oId\n"
                + "JOIN product ON correspond.pId = product.id\n"
                + "where `order`.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, oId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public void updateProductAfterBought(String productId) {
        //Trạng thái đơn hàng hoàn tất 
        //=> Cập nhật isPurchased của product từ 0(false - chưa ai mua) => 1(true - đã có người mua) 
        //=> Cập nhật updateable: true -> có thể update, false -> ko thể update
        xSql = "update `Product` set `Product`.isPurchased = true,\n"
                + "`Product`.updateable = false where `Product`.id = ? LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public void updateProductBySellerEdit(String productId, String updatedName, String updatedPrice, String updatedType, String updatedDescription, String updatedHiddenField, String updatedContact, String updatedFeePayer, String updatedIsPrivate) {
        xSql = "update `Product` "
                + "set `Product`.name = ?, "
                + "`Product`.price = ?, "
                + "`Product`.type = ?, "
                + "`Product`.description = ?, "
                + "`Product`.hiddenField = ?, "
                + "`Product`.contact = ?, "
                + "`Product`.feePayer = ?, "
                + "`Product`.isPrivate = ?"
                + " where `Product`.id = ? LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, updatedName);
            double price = Double.parseDouble(updatedPrice);
            ps.setDouble(2, price);
            if (updatedType == "tailieu") {
                ps.setString(3, "Tài liệu");
            } else if (updatedType == "taikhoan") {
                ps.setString(3, "Tài khoản");
            } else if (updatedType == "phanmem") {
                ps.setString(3, "Phần mềm");
            } else {
                ps.setString(3, "Khác");
            }
            ps.setString(4, updatedDescription);
            ps.setString(5, updatedHiddenField);
            ps.setString(6, updatedContact);
            if (updatedFeePayer.equals("1")) {
                ps.setBoolean(7, true);
            } else if (updatedFeePayer.equals("0")) {
                ps.setBoolean(7, false);
            }
            if (updatedIsPrivate.equals("1")) {
                ps.setString(8, "1");
            } else if (updatedIsPrivate.equals("0")) {
                ps.setString(8, "0");
            }
            ps.setString(9, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public ArrayList<Product> getAllProductByUserName(String username) {
        xSql = "SELECT * \n"
                + "FROM `Product` \n"
                + "WHERE `createdBy` = (SELECT `id` FROM `User` WHERE `username` = ?)"
                + " ORDER BY createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }

        return list;
    }

    public ArrayList<Product> getAllProduct() {
        xSql = "select * from `Product` ORDER BY createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }

        return list;
    }

    public ArrayList<Product> getAllProductWithNotPurchased() {
        xSql = "select * from `Product`"
                + " where isPurchased = 0 "
                + "ORDER BY createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }

        return list;
    }

    public ArrayList<Product> getAllProductWithNotPurchased(User userAccount) {
        xSql = "select * from `Product` p "
                + "where p.id not in (select pid from Correspond) "
                + "and p.createdBy <> ? "
                + "ORDER BY p.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userAccount.getUserId());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    //Tìm kiếm sản phẩm theo type
    public ArrayList<Product> getAllProductBySpecificType(String type) {
        xSql = "select * from Product where Product.type = ? ORDER BY Product.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public ArrayList<Product> getAllProductBySpecificType(String type, User userAccount) {
        xSql = "select * from `Product` p "
                + "where p.type = ? "
                + "and not exists (select 1 from Correspond c where c.pId = p.id) "
                + "and p.createdBy <> ?"
                + " ORDER BY p.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, type);
            ps.setInt(2, userAccount.getUserId());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    //Tìm kiếm sản phẩm theo tên
    public ArrayList<Product> getAllProductByName(String productName) {
        xSql = "select * from Product where Product.name LIKE ? "
                + "ORDER BY Product.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, '%' + productName + '%');
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public ArrayList<Product> getAllProductByName(String productName, User userAccount) {
        xSql = "select * from `Product` p "
                + "where p.name like ? "
                + "and not exists (select 1 from Correspond c where c.pId = p.id) "
                + "and p.createdBy <> ? "
                + "ORDER BY p.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, '%' + productName + '%');
            ps.setInt(2, userAccount.getUserId());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    //Tìm kiếm sản phẩm theo giá from - to
    public ArrayList<Product> getAllProductByPriceFromTo(String priceFrom, String priceTo) {
        xSql = "select * from Product where Product.price BETWEEN ? AND ? "
                + "ORDER BY Product.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, priceFrom);
            ps.setString(2, priceTo);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public ArrayList<Product> getAllProductByPriceFromTo(String priceFrom, String priceTo, User userAccount) {
        xSql = "select * from `Product` p "
                + "where p.price between ? and ? "
                + "and not exists (select 1 from Correspond c where c.pId = p.id) "
                + "and p.createdBy <> ? "
                + "ORDER BY p.createdAt DESC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, priceFrom);
            ps.setString(2, priceTo);
            ps.setInt(3, userAccount.getUserId());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public Product getProductInformation(String pid) {

        xSql = "select * from product\n"
                + "where id = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, pid);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public ArrayList<Product> getAllProductBySearching(String productName, String priceFrom, String priceTo, String type) {
        int paramIndex = 1;
        xSql = "SELECT * FROM Product WHERE 1=1";

        if (!productName.isEmpty()) {
            xSql += " AND Product.name LIKE ? ORDER BY Product.createdAt DESC";
        }

//        if (!priceFrom.isEmpty() && !priceTo.isEmpty()) {
//            xSql += " AND Product.price BETWEEN ? AND ?";
//        }
        if (!priceFrom.isEmpty()) {
            xSql += " AND Product.price >= ? ORDER BY Product.createdAt DESC";
        }

        if (!priceTo.isEmpty()) {
            xSql += " AND Product.price <= ? ORDER BY Product.createdAt DESC";
        }

        if (!type.isEmpty()) {
            xSql += " AND Product.type = ? ORDER BY Product.createdAt DESC";
        }

        try {
            ps = con.prepareStatement(xSql);
            if (!productName.isEmpty()) {
                ps.setString(paramIndex, '%' + productName + '%');
                paramIndex++;
            }
//            if (!priceFrom.isEmpty() && !priceTo.isEmpty()) {
//                ps.setString(paramIndex, priceFrom);
//                paramIndex++;
//                ps.setString(paramIndex, priceTo);
//                paramIndex++;
//            }
            if (!priceFrom.isEmpty()) {
                Double priceFromDouble = Double.parseDouble(priceFrom);
                ps.setDouble(paramIndex, priceFromDouble);
                paramIndex++;
            }

            if (!priceTo.isEmpty()) {
                Double priceToDouble = Double.parseDouble(priceTo);
                ps.setDouble(paramIndex, priceToDouble);
                paramIndex++;
            }
            if (!type.isEmpty()) {
                System.out.println(paramIndex);
                ps.setString(paramIndex, type);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;

    }

    public ArrayList<Product> getAllProductBySearching(String productName, String priceFrom, String priceTo, String type, User userAccount) {
        int paramIndex = 1;
        xSql = "SELECT * FROM `Product` p WHERE 1=1";

        if (!productName.isEmpty()) {
            xSql += " AND p.name LIKE ?";
        }

        if (!priceFrom.isEmpty()) {
            xSql += " AND p.price >= ?";
        }

        if (!priceTo.isEmpty()) {
            xSql += " AND p.price <= ?";
        }

        if (!type.isEmpty()) {
            xSql += " AND p.type = ?";
        }

        xSql += " AND not exists (select 1 from Correspond c where c.pId = p.id)";
        xSql += " AND p.createdBy <> ? ORDER BY p.createdAt DESC";
        try {

            ps = con.prepareStatement(xSql);
            if (!productName.isEmpty()) {
                ps.setString(paramIndex, '%' + productName + '%');
                paramIndex++;
            }

            if (!priceFrom.isEmpty()) {
                Double priceFromDouble = Double.parseDouble(priceFrom);
                ps.setDouble(paramIndex, priceFromDouble);
                paramIndex++;
            }

            if (!priceTo.isEmpty()) {
                Double priceToDouble = Double.parseDouble(priceTo);
                ps.setDouble(paramIndex, priceToDouble);
                paramIndex++;
            }
            if (!type.isEmpty()) {
                System.out.println(paramIndex);
                ps.setString(paramIndex, type);
                paramIndex++;
            }
            ps.setInt(paramIndex, userAccount.getUserId());

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getBoolean(12), rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getTimestamp(16), rs.getTimestamp(17), rs.getTimestamp(18)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;

    }

    public void insertNewProduct2(String name, String description, String benChiuPhi, String isPrivate, String hiddenField, double price, double feeTrungGian, String contact, boolean isPurchased, String type, boolean updateable, boolean isDeleted, int createdby) {
        String newSqlStatement = "INSERT INTO `Product` (`id`, `name`, `description`, `feePayer`, `isPrivate`, `hiddenField`, `price`, `feeOnSuccess`, `contact`, `isPurchased`,`type`, `updateable`,`shareLink`,`isDeleted`, `createdBy`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            ArrayList<Product> pdList = getAllProduct();
            int size = pdList.size();
            System.out.println("productID before: " + size);
            int productId = size + 1;
            String pId = String.valueOf(productId);
            System.out.println("productID after: " + pId);
            ps = con.prepareStatement(newSqlStatement);
            ps.setString(1, pId);
            ps.setString(2, name);
            ps.setString(3, description);
            if (benChiuPhi.equals("1")) {
                ps.setBoolean(4, true);
            } else if (benChiuPhi.equals("0")) {
                ps.setBoolean(4, false);
            }
            if (isPrivate.equals("1")) {
                ps.setString(5, "1");
            } else if (isPrivate.equals("0")) {
                ps.setString(5, "0");
            }
            ps.setString(6, hiddenField);
            ps.setDouble(7, price);
            ps.setDouble(8, feeTrungGian);
            ps.setString(9, contact);
            ps.setBoolean(10, isPurchased);
            ps.setString(11, type);
            ps.setBoolean(12, updateable);
            ps.setString(13, "http://localhost:9999/swp391_g6_se1755_net/productdetail?id=" + pId);
            ps.setBoolean(14, isDeleted);
            ps.setInt(15, createdby);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when create new product: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    //lấy tất cả các đơn mà người bán đã đăng (chưa có người mua) và đã bán (có người mua)
    public ArrayList<Product> getAllSellByUserLimitOffset(int userId, int start, int length) {
        // xác định điểm bắt đầu của bản ghi tiếp theo
        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)

        xSql = "SELECT p.id, p.name, p.description,\n"
                + "       CASE WHEN o.status IS NOT NULL THEN o.status ELSE 1 END AS orderStatus, \n"
                + "       CASE WHEN o.createdBy IS NOT NULL THEN o.createdBy ELSE NULL END AS orderCreatedBy, \n"
                + "       p.hiddenField, p.contact, p.shareLink, p.isPrivate, p.price,p.feePayer, p.type, p.createdBy, COALESCE(o.id, '')\n"
                + "FROM Product p\n"
                + "LEFT JOIN Correspond c ON c.pId = p.id\n"
                + "LEFT JOIN `Order` o ON o.id = c.oId\n"
                + "LEFT JOIN `User` u ON u.id = p.createdBy\n"
                + "WHERE p.createdBy = ?"
                + " ORDER BY o.createdAt DESC "
                + "LIMIT ? OFFSET ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            ps.setInt(2, length);
            ps.setInt(3, start);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getDouble(10), rs.getBoolean(11), rs.getString(12), rs.getInt(13), rs.getString(14)));
            }
        } catch (Exception e) {
            System.out.println("error when query sell order: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }
    
    
    public Product getProductByUserModal(int userId, String productId) {
        // xác định điểm bắt đầu của bản ghi tiếp theo
        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)

        xSql = "SELECT p.id, p.name, p.description,\n"
                + "       CASE WHEN o.status IS NOT NULL THEN o.status ELSE 1 END AS orderStatus, \n"
                + "       CASE WHEN o.createdBy IS NOT NULL THEN o.createdBy ELSE NULL END AS orderCreatedBy, \n"
                + "       p.hiddenField, p.contact, p.shareLink, p.isPrivate, p.price,p.feePayer, p.type, p.createdBy, COALESCE(o.id, '')\n"
                + "FROM Product p\n"
                + "LEFT JOIN Correspond c ON c.pId = p.id\n"
                + "LEFT JOIN `Order` o ON o.id = c.oId\n"
                + "LEFT JOIN `User` u ON u.id = p.createdBy\n"
                + "WHERE p.createdBy = ? and p.id = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            ps.setString(2, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getDouble(10), rs.getBoolean(11), rs.getString(12), rs.getInt(13), rs.getString(14));
            }
        } catch (Exception e) {
            System.out.println("error when query sell order: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }
public Product getProductByAdminModal(int productId) {
        // xác định điểm bắt đầu của bản ghi tiếp theo
        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)

        xSql = "SELECT p.id, p.name, p.description,\n"
                + "       CASE WHEN o.status IS NOT NULL THEN o.status ELSE 1 END AS orderStatus, \n"
                + "       CASE WHEN o.createdBy IS NOT NULL THEN o.createdBy ELSE NULL END AS orderCreatedBy, \n"
                + "       p.hiddenField, p.contact, p.shareLink, p.isPrivate, p.price,p.feePayer, p.type, p.createdBy, COALESCE(o.id, '')\n"
                + "FROM Product p\n"
                + "LEFT JOIN Correspond c ON c.pId = p.id\n"
                + "LEFT JOIN `Order` o ON o.id = c.oId\n"
                + "LEFT JOIN `User` u ON u.id = p.createdBy\n"
                + "WHERE p.id = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getDouble(10), rs.getBoolean(11), rs.getString(12), rs.getInt(13), rs.getString(14));
            }
        } catch (Exception e) {
            System.out.println("error when query sell order: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }
    public ArrayList<Product> getAllSellByUser(User u) {
        // xác định điểm bắt đầu của bản ghi tiếp theo
        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)

        xSql = "SELECT p.id, p.name, p.description,\n"
                + "       CASE WHEN o.status IS NOT NULL THEN o.status ELSE 1 END AS orderStatus, \n"
                + "       CASE WHEN o.createdBy IS NOT NULL THEN o.createdBy ELSE NULL END AS orderCreatedBy, \n"
                + "       p.hiddenField, p.contact, p.shareLink, p.isPrivate, p.price,p.feePayer, p.type, p.createdBy, COALESCE(o.id, '')\n"
                + "FROM Product p\n"
                + "LEFT JOIN Correspond c ON c.pId = p.id\n"
                + "LEFT JOIN `Order` o ON o.id = c.oId\n"
                + "LEFT JOIN `User` u ON u.id = p.createdBy\n"
                + "WHERE p.createdBy = ?"
                + " ORDER BY o.createdAt DESC ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, u.getUserId());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getDouble(10), rs.getBoolean(11), rs.getString(12), rs.getInt(13), rs.getString(14)));
            }
        } catch (Exception e) {
            System.out.println("error when query sell order: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public int getTotalRecordsByUser(User user) {
        try {
            xSql = "SELECT COUNT(*) AS total "
                  + "FROM Product p\n"
                + "LEFT JOIN Correspond c ON c.pId = p.id\n"
                + "LEFT JOIN `Order` o ON o.id = c.oId\n"
                + "LEFT JOIN `User` u ON u.id = p.createdBy\n"
                + "WHERE p.createdBy = ?";
                    
            ps = con.prepareStatement(xSql);
            ps.setInt(1,user.getUserId());
            rs = ps.executeQuery();
            rs.next();
            int total = rs.getInt("total");
            return total;

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
//        ArrayList<Product> pdList = dao.getAllProduct("1", 100);
        UserDAO userdao = new UserDAO();
        User user = userdao.getUserbyId(1);
//        ArrayList<Product> pdList = dao.getAllProductBySpecificType("Tài khoản", user);
//        ArrayList<Product> pdList = dao.getAllProduct();
//        System.out.println(pdList);
//         ArrayList<Product> pdList = dao.getAllProductBySearching("z", "50", "150", "Tài khoản");
////        ArrayList<Product> pdList = dao.getAllSell(2, "1", 100);
//        for (Product product : pdList) {
//            System.out.println(product);

//        ArrayList<Product> pdList = dao.getAllProduct();
//        for (Product product : pdList) {
//            System.out.println(product);
//        }
//        Product pd = dao.getProductInformation("1");
//        System.out.println(pd);
//    OrderDAO orderdao = new OrderDAO();
//    int status = 2; //Đã thanh toán, "Chờ xác nhận" từ phía người mua
//        orderdao.createNewBuyOrder(user, pd, "benmua7", status);//chèn bản ghi vào bảng order và correspond
//
//        Order newestOrder = orderdao.getLastestBuyOrder(user);
    }

}
