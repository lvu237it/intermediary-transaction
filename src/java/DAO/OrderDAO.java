/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Order;
import Model.Product;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class OrderDAO extends MyDAO {

    ArrayList<Order> list = new ArrayList<>();

    public ArrayList<Order> getAllOrder() {
        xSql = "select * from `Order`";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getTimestamp(7), rs.getTimestamp(8)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public String getOrderCreatedByWithOrderId(String orderId) {
        xSql = "select o.createdBy from `Order` o where o.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, orderId);
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
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public String getOrderStatusById(String oId) {
        xSql = "select status from `Order` where `Order`.id = ?";
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
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public int getTotalRecordsByUser(User buyer) {
        try {
            xSql = "SELECT COUNT(*) AS total FROM \n"
                + "          `Order` o\n"
                + "      JOIN \n"
                + "          `Correspond` c ON o.id = c.oId\n"
                + "      JOIN \n"
                + "       `Product` product ON c.pId = product.id\n"
                + "       JOIN \n"
                + "          `User` u ON o.createdBy = u.id\n"
                + "       WHERE  u.username = ?";
            ps = con.prepareStatement(xSql);
            ps.setString(1,buyer.getUserName());
            rs = ps.executeQuery();
            rs.next();
            int total = rs.getInt("total");
            return total;

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }

    public ArrayList<Order> getOrderByUserLimitOffset(User user, int start, int length, String columnName, String direction) {
        xSql = "SELECT \n"
                + "       o.id, \n"
                + "      product.name,\n"
                + "           product.hiddenField,\n"
                + "         o.sellerReceivedTrueMoney,\n"
                + "         o.status,\n"
                + "         product.createdBy,\n"
                + "           product.contact,\n"
                + "          product.isPrivate,\n"
                + "           product.price,\n"
                + "          product.feePayer,\n"
                + "          product.price * 0.05 AS transactionFee,\n"
                + "           CASE \n"
                + "            WHEN product.feePayer = 0 THEN product.price\n"
                + "           ELSE product.price + (product.price * 0.05) \n"
                + "         END AS totalPrice,\n"
                + "           product.type,\n" 
                + "        product.shareLink,\n"
                + "            o.createdAt,\n"
                + "        o.updatedAt\n"
                + "       FROM \n"
                + "          `Order` o\n"
                + "      JOIN \n"
                + "          `Correspond` c ON o.id = c.oId\n"
                + "      JOIN \n"
                + "       `Product` product ON c.pId = product.id\n"
                + "       JOIN \n"
                + "          `User` u ON o.createdBy = u.id\n"
                + "       WHERE  u.username = ?";
//        if (columnName != null) {
//            switch (columnName) {
//                case "0":
//                    xSql += " order by id " + direction;
//                    break;
//                case "1":
//                    xSql += " order by status " + direction;
//                    break;
//                case "2":
//                    xSql += " order by withdrawAmount " + direction;
//                    break;
//                case "3":
//                    xSql += " order by bankAccountNumber " + direction;
//                    break;
//                case "4":
//                    xSql += " order by bankAccountName " + direction;
//                    break;
//                case "5":
//                    xSql += " order by bankName " + direction;
//                    break;
//                case "6":
//                    xSql += " order by response " + direction;
//                    break;
//                case "7":
//                    xSql += " order by createdAt " + direction;
//                    break;
//                case "8":
//                    xSql += " order by updatedAt " + direction;
//                    break;
//                case "9":
//                    xSql += " order by id " + direction;
//                    break;
//                default:
//                    break;
//            }
//        }
        xSql += " limit ? offset ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, user.getUserName());
            ps.setInt(2, length);
            ps.setInt(3, start);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getBoolean(10), rs.getDouble(11), rs.getDouble(12), rs.getString(13), rs.getString(14), rs.getTimestamp(15), rs.getTimestamp(16)));
            }
            ps.close();
            System.out.println("Closing connection successfully");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }

    public Order getOrderByUserNProductId(User user, String productIdGlobal) {
        xSql = "SELECT \n"
                + "       o.id, \n"
                + "      product.name,\n"
                + "           product.hiddenField,\n"
                + "         o.sellerReceivedTrueMoney,\n"
                + "         o.status,\n"
                + "         product.createdBy,\n"
                + "           product.contact,\n"
                + "          product.isPrivate,\n"
                + "           product.price,\n"
                + "          product.feePayer,\n"
                + "          product.price * 0.05 AS transactionFee,\n"
                + "           CASE \n"
                + "            WHEN product.feePayer = 0 THEN product.price\n"
                + "           ELSE product.price + (product.price * 0.05) \n"
                + "         END AS totalPrice,\n"
                + "           product.type,\n"
                + "           product.shareLink,\n"
                + "            o.createdAt,\n"
                + "        o.updatedAt\n"
                + "       FROM \n"
                + "          `Order` o\n"
                + "      JOIN \n"
                + "          `Correspond` c ON o.id = c.oId\n"
                + "      JOIN \n"
                + "       `Product` product ON c.pId = product.id\n"
                + "       JOIN \n"
                + "          `User` u ON o.createdBy = u.id\n"
                + "       WHERE  u.username = ? and o.id = ?";
//        if (columnName != null) {
//            switch (columnName) {
//                case "0":
//                    xSql += " order by id " + direction;
//                    break;
//                case "1":
//                    xSql += " order by status " + direction;
//                    break;
//                case "2":
//                    xSql += " order by withdrawAmount " + direction;
//                    break;
//                case "3":
//                    xSql += " order by bankAccountNumber " + direction;
//                    break;
//                case "4":
//                    xSql += " order by bankAccountName " + direction;
//                    break;
//                case "5":
//                    xSql += " order by bankName " + direction;
//                    break;
//                case "6":
//                    xSql += " order by response " + direction;
//                    break;
//                case "7":
//                    xSql += " order by createdAt " + direction;
//                    break;
//                case "8":
//                    xSql += " order by updatedAt " + direction;
//                    break;
//                case "9":
//                    xSql += " order by id " + direction;
//                    break;
//                default:
//                    break;
//            }
//        }
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, user.getUserName());
            ps.setString(2, productIdGlobal);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getBoolean(10), rs.getDouble(11), rs.getDouble(12), rs.getString(13), rs.getString(14), rs.getTimestamp(15), rs.getTimestamp(16));
            }
            ps.close();
            System.out.println("Closing connection successfully");
        } catch (Exception e) {
            System.out.println("Loi khi truy van: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public Order getOrderByUserForReport(String productId) {
        xSql = "  SELECT \n"
                + "                    o.id, \n"
                + "                      product.name,\n"
                + "                        product.hiddenField,\n"
                + "                    o.sellerReceivedTrueMoney,\n"
                + "                        o.status,\n"
                + "                   product.createdBy,\n"
                + "                        product.contact,\n"
                + "                        product.isPrivate,\n"
                + "                        product.price,\n"
                + "                   product.feePayer,\n"
                + "                       product.price * 0.05 AS transactionFee,\n"
                + "                         CASE \n"
                + "                         WHEN product.feePayer = 0 THEN product.price\n"
                + "                          ELSE product.price + (product.price * 0.05) \n"
                + "                         END AS totalPrice,\n"
                + "                      product.type,\n"
                + "        product.shareLink,\n"
                + "                         o.createdAt,\n"
                + "                    o.updatedAt\n"
                + "                  FROM \n"
                + "                       `Order` o\n"
                + "                 JOIN \n"
                + "                        `Correspond` c ON o.id = c.oId\n"
                + "                  JOIN \n"
                + "                `Product` product ON c.pId = product.id\n"
                + "                   JOIN\n"
                + "                    `User` u ON o.createdBy = u.id\n"
                + "				WHERE product.id = ?";
//        if (columnName != null) {
//            switch (columnName) {
//                case "0":
//                    xSql += " order by id " + direction;
//                    break;
//                case "1":
//                    xSql += " order by status " + direction;
//                    break;
//                case "2":
//                    xSql += " order by withdrawAmount " + direction;
//                    break;
//                case "3":
//                    xSql += " order by bankAccountNumber " + direction;
//                    break;
//                case "4":
//                    xSql += " order by bankAccountName " + direction;
//                    break;
//                case "5":
//                    xSql += " order by bankName " + direction;
//                    break;
//                case "6":
//                    xSql += " order by response " + direction;
//                    break;
//                case "7":
//                    xSql += " order by createdAt " + direction;
//                    break;
//                case "8":
//                    xSql += " order by updatedAt " + direction;
//                    break;
//                case "9":
//                    xSql += " order by id " + direction;
//                    break;
//                default:
//                    break;
//            }
//        }
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getBoolean(10), rs.getDouble(11), rs.getDouble(12), rs.getString(13), rs.getString(14), rs.getTimestamp(15), rs.getTimestamp(16));
            }
            ps.close();
            System.out.println("Closing connection successfully");
        } catch (Exception e) {
            System.out.println("Loi khi truy van: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public void updateOrderStatusByID(String oId, int newStatus) {
        xSql = "update `Order` set `Order`.status = ? where `Order`.id = ? LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, newStatus);
            ps.setString(2, oId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("loi khi update order status: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public void updateOrderStatusWithoutConfirm(String oId) {
        xSql = "UPDATE `Order`\n"
                + "SET status = 3\n"
                + "WHERE (status = 2 OR status = 5) AND id = ?\n"
                + "LIMIT 1";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, oId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    public Order getOrderByProductId(String productId) {
        xSql = "SELECT o.*\n"
                + "FROM `Order` o\n"
                + "JOIN `Correspond` c ON o.id = c.oId\n"
                + "JOIN `Product` p ON c.pId = p.id\n"
                + "WHERE p.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Order(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getTimestamp(7), rs.getTimestamp(8));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public void deleteAnRecordOfOrderByOrderId(String orderId) {
        xSql = "DELETE FROM Order WHERE id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

    //    //Tạo đơn mua mới -- cần sửa (có sự thay đổi trong database)
    public void createNewBuyOrder(User user, Product product, double totalPriceBuyer, double sellerReceivedTrueMoney, String benChiuPhi, int transactionStatus) {
        xSql = "INSERT INTO `Order` (`id`, `createdBy`, `totalPrice`, `sellerReceivedTrueMoney`, `status`) VALUES (?, ?, ?, ?, ?)";

        try {
            con.setAutoCommit(false); // Bắt đầu một giao dịch

            // Kiểm tra trạng thái của sản phẩm
            boolean isProductAlreadyProcessed = checkIfProductProcessed(product.getId());

            if (!isProductAlreadyProcessed) {
                // Nếu sản phẩm chưa được xử lý, tiếp tục thêm bản ghi
                OrderDAO dao = new OrderDAO();
                list = dao.getAllOrder();
                int length = list.size();
                int oId = length + 1;
                ps = con.prepareStatement(xSql);
                ps.setString(1, String.valueOf(oId));
                ps.setInt(2, user.getUserId());
                ps.setDouble(3, totalPriceBuyer);
//                ps.setDouble(4, totalPrice - product.getFeeOnSuccess());
                ps.setDouble(4, sellerReceivedTrueMoney);
                ps.setInt(5, transactionStatus);
                ps.executeUpdate();

                // Kiểm tra xem Bản ghi Tương ứng đã Tồn tại chưa
                xSql = "SELECT * FROM `Correspond` WHERE `oId` = ? AND `pId` = ?";
                ps = con.prepareStatement(xSql);
                ps.setInt(1, oId);
                ps.setInt(2, Integer.valueOf(product.getId()));
                ResultSet correspondResult = ps.executeQuery();

                if (!correspondResult.next()) { // Nếu không có bản ghi tương ứng tồn tại
                    // Chèn vào Correspond
                    xSql = "INSERT INTO `Correspond` VALUES (?, ?)";
                    ps = con.prepareStatement(xSql);
                    ps.setInt(1, oId);
                    ps.setInt(2, Integer.valueOf(product.getId()));
                    ps.executeUpdate();

                    // Chèn vào Transaction
                    xSql = "INSERT INTO `Transaction` (`amount`, `type`, `detail`, `status`, `createdBy`) VALUES (?, ?, ?, ?, ?)";
                    ps = con.prepareStatement(xSql);
                    ps.setDouble(1, totalPriceBuyer); //trừ số tiền này trong wallet của người mua
                    ps.setBoolean(2, false);
                    ps.setString(3, "Thu phí tạo yêu cầu đơn trung gian số " + oId);
                    ps.setInt(4, 1);
                    ps.setInt(5, user.getUserId());
                    ps.executeUpdate();
                }

                con.commit(); // Hoàn thành giao dịch
            } else {
                System.out.println("Sản phẩm đã được xử lý trước đó. Không thể thêm bản ghi mới.");
            }

            con.setAutoCommit(true); // Thiết lập lại chế độ tự động commit
        } catch (SQLException e) {
            try {
                con.rollback(); // Quay lại trạng thái trước đó nếu có lỗi
                con.setAutoCommit(true); // Thiết lập lại chế độ tự động commit
            } catch (SQLException rollbackException) {
                System.out.println("Lỗi transaction rollback:" + rollbackException);
            }
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
    }

// Hàm kiểm tra xem sản phẩm đã được xử lý chưa
    public boolean checkIfProductProcessed(String productId) throws SQLException {
        String checkSql = "SELECT COUNT(*) AS count FROM `Correspond` WHERE `pId` = ?";
        PreparedStatement checkPs = con.prepareStatement(checkSql);
        checkPs.setString(1, productId);
        ResultSet checkResult = checkPs.executeQuery();

        if (checkResult.next()) {
            int count = checkResult.getInt("count");
            return count > 0;
        } else {
            return false;
        }
    }

    //Hiển thị trên đơn mua của tôi
    public ArrayList<Order> getBuyOrder(User user) {
        xSql = "SELECT \n"
                + "       o.id, \n"
                + "      product.name,\n"
                + "           product.hiddenField,\n"
                + "         o.sellerReceivedTrueMoney,\n"
                + "         o.status,\n"
                + "         product.createdBy,\n"
                + "           product.contact,\n"
                + "          product.isPrivate,\n"
                + "           product.price,\n"
                + "          product.feePayer,\n"
                + "          product.price * 0.05 AS transactionFee,\n"
                + "           CASE \n"
                + "            WHEN product.feePayer = 0 THEN product.price\n"
                + "           ELSE product.price + (product.price * 0.05) \n"
                + "         END AS totalPrice,\n"
                + "           product.type,\n"
                + "        product.shareLink,\n"
                + "            o.createdAt,\n"
                + "        o.updatedAt\n"
                + "       FROM \n"
                + "          `Order` o\n"
                + "      JOIN \n"
                + "          `Correspond` c ON o.id = c.oId\n"
                + "      JOIN \n"
                + "       `Product` product ON c.pId = product.id\n"
                + "       JOIN \n"
                + "          `User` u ON o.createdBy = u.id\n"
                + "       WHERE  u.username = ?\n"
                + "       ORDER BY\n"
                + "           CASE WHEN o.status <> 3 THEN 0 ELSE 1 END,\n"
                + "          o.updatedAt DESC\n";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, user.getUserName());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getBoolean(10), rs.getDouble(11), rs.getDouble(12), rs.getString(13), rs.getString(14), rs.getTimestamp(15), rs.getTimestamp(16)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return list;
    }
//    public ArrayList<Order> getBuyOrder(User user, String page, int recordsPerPage) {
//        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;// xác định điểm bắt đầu của bản ghi tiếp theo
//        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
//        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)
//
//        xSql = "SELECT \n"
//                + "       o.id, \n"
//                + "      product.name,\n"
//                + "           product.hiddenField,\n"
//                + "         o.sellerReceivedTrueMoney,\n"
//                + "         o.status,\n"
//                + "         product.createdBy,\n"
//                + "           product.contact,\n"
//                + "          product.isPrivate,\n"
//                + "           product.price,\n"
//                + "          product.feePayer,\n"
//                + "          product.price * 0.05 AS transactionFee,\n"
//                + "           CASE \n"
//                + "            WHEN product.feePayer = 0 THEN product.price\n"
//                + "           ELSE product.price + (product.price * 0.05) \n"
//                + "         END AS totalPrice,\n"
//                + "           product.type,\n"
//                + "            o.createdAt,\n"
//                + "        o.updatedAt\n"
//                + "       FROM \n"
//                + "          `Order` o\n"
//                + "      JOIN \n"
//                + "          `Correspond` c ON o.id = c.oId\n"
//                + "      JOIN \n"
//                + "       `Product` product ON c.pId = product.id\n"
//                + "       JOIN \n"
//                + "          `User` u ON o.createdBy = u.id\n"
//                + "       WHERE  u.username = ?\n"
//                + "       ORDER BY\n"
//                + "           CASE WHEN o.status <> 3 THEN 0 ELSE 1 END,\n"
//                + "          o.updatedAt DESC\n"
//                + "LIMIT ? OFFSET ?";
//        try {
//            ps = con.prepareStatement(xSql);
//            ps.setString(1, user.getUserName());
//            ps.setInt(2, recordsPerPage);
//            ps.setInt(3, offset);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                list.add(new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getBoolean(10), rs.getDouble(11), rs.getDouble(12), rs.getString(13), rs.getTimestamp(14), rs.getTimestamp(15)));
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close(); System.out.println("Closing connection successfully")
//                }
//            } catch (SQLException e) {
//                System.out.println("Error closing resources: " + e);
//            }
//        }
//        return list;
//    }

    //Lấy đơn vừa được buyer tạo
    public Order getRecentlyBuyOrder(Product product, double totalPrice) {
        // Assuming createNewBuyOrder function adds records to Order, Correspond, etc.
        // Retrieve information for the recently purchased product
        xSql = "SELECT `order`.id, product.name, product.hiddenField, `order`.sellerReceivedTrueMoney,`order`.status,\n"
                + "    product.createdBy, product.contact, product.isPrivate, product.price,\n"
                + "                 product.feePayer, product.price * 0.05 AS transactionFee, \n"
                + "                        ? AS totalPrice,\n"
                + "                             product.type, product.shareLink, `order`.createdAt, `order`.updatedAt\n"
                + "                         FROM Product \n"
                + "                            JOIN `Correspond` correspond ON product.id = correspond.pid\n"
                + "JOIN `Order` ON correspond.oId = `order`.id \n"
                + "                       WHERE product.id = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setDouble(1, totalPrice);
            ps.setString(2, product.getId());  // Use the correct method to set the product ID
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getBoolean(10), rs.getDouble(11), rs.getDouble(12), rs.getString(13), rs.getString(14), rs.getTimestamp(15), rs.getTimestamp(16));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở phương thức getRecentlyBuyOrder: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return null;
    }

    public int getProductIdbyOrderId(int orderId) {
        xSql = "SELECT product.id FROM Product JOIN `Correspond` correspond ON product.id = correspond.pid\n"
                + "JOIN `Order` ON correspond.oId = `order`.id WHERE order.id = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    System.out.println("Closing connection successfully");
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e);
            }
        }
        return -1;
    }

    public Order getOrderDetails(String orderId) {
        xSql = "select * from `Order` where `id` = ?;";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Order(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4),
                        rs.getInt(5), rs.getInt(6), rs.getTimestamp(7), rs.getTimestamp(8));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        OrderDAO dao = new OrderDAO();
//        ArrayList<Order> orderList = dao.getSellOrder("user1", "1", 1);
        UserDAO userdao = new UserDAO();
        System.out.println(dao.getOrderDetails("1").getSellerReceivedTrueMoney());
//        User user = userdao.getUserbyId(2);
//        ProductDAO pddao = new ProductDAO();
//        Product pd = pddao.getProductInformation(10);
//        Order newestOrder = dao.getLastestBuyOrder(pd);
////        ArrayList<Order> orderList = dao.getAllOrder();
//        System.out.println("Đơn mới nhất: \n" + newestOrder);
//        ArrayList<Order> orderList = dao.getSellOrder(user, "1", 1);
//        ArrayList<Order> orderList = dao.getBuyOrder(user, "1", 100);
        ArrayList<Order> orderList = dao.getAllOrder();
//        for (Order order : orderList) {
//            System.out.println(order);
//        }
//        System.out.println(orderList.size());
//        Order o = dao.getBuyOrder("1");
//        for (Order order : orderList) {
//            System.out.println(order);
//        }
//        ProductDAO productdao = new ProductDAO();
////        ArrayList<Product> pdList = dao.getAllProduct("1", 100);
//        Product product = productdao.getProductInformation(38);
//
//        dao.createNewBuyOrder(user, product, "benmua40", 2);
//        System.out.println(orderList.size());
    }
}
