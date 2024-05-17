/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Transaction;
import Model.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TransactionDAO extends MyDAO {

    ArrayList<Transaction> list = new ArrayList<>();

    public ArrayList<Transaction> getAllTransaction() {
        xSql = "select * from `Transaction`";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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

    public void deleteNewestTransaction() {
        xSql = "DELETE FROM Transaction\n"
                + "WHERE id = (\n"
                + "    SELECT * FROM (\n"
                + "        SELECT MAX(id) as maxTransactionId FROM Transaction\n"
                + "    ) as temp\n"
                + ");";
        try {
            ps = con.prepareStatement(xSql);
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

    //Tìm kiếm lịch sử giao dịch của 1 user bằng id
    public ArrayList<Transaction> getAllUserTransactionWithPagination(String page, int recordsPerPage) {
        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;// xác định điểm bắt đầu của bản ghi tiếp theo
        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)
        xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                + "`Transaction` "
                + "Group by Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy "
                + "ORDER BY Transaction.createdAt DESC "
                + "LIMIT ? OFFSET ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, recordsPerPage);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
    //Tìm kiếm lịch sử giao dịch của 1 user bằng id
    public ArrayList<Transaction> getUserTransactionHistoryById(int id, String page, int recordsPerPage) {
        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;// xác định điểm bắt đầu của bản ghi tiếp theo
        //ví dụ: page hiện tại - page 1 => offset = 0 (bỏ qua 0 bản ghi đầu tiên) => hiển thị 10 bản ghi đầu ở page 1
        //       page hiện tại - page 2 => offset = 10 (bỏ qua 10 bản ghi đầu tiên) => hiển thị 10 bản ghi tiếp theo ở page 2 (tính từ bản ghi thứ 11)
        xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy WHERE User.id = ? "
                + "Group by Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy "
                + "ORDER BY Transaction.createdAt DESC "
                + "LIMIT ? OFFSET ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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

    //Tìm kiếm lịch sử giao dịch của 1 user bằng loại sản phẩm (type of product)
    public ArrayList<Transaction> getUserTransactionHistoryByTypeOfProduct(String productType, String page, int recordsPerPage, User account) {
        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;

        if (account.getRole() == 1) { // Admin
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                    + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy "
                    + "JOIN `Product` ON User.id = Product.createdBy WHERE Product.type = ? "
                    + "ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, productType);
                ps.setInt(2, recordsPerPage);
                ps.setInt(3, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }

        if (account.getRole() == 0) { // User
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                    + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy "
                    + "JOIN `Product` ON User.id = Product.createdBy WHERE Product.type = ? and User.id = ? "
                    + "ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, productType);
                ps.setInt(2, account.getUserId());
                ps.setInt(3, recordsPerPage);
                ps.setInt(4, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }
        return list;
    }

    public ArrayList<Transaction> getUserTransactionHistoryByStatus(String status, String page, int recordsPerPage, User account) {
        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;

        if (account.getRole() == 1) {
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                    + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy "
                    + " WHERE Transaction.status = ? "
                    + "ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, status);
                ps.setInt(2, recordsPerPage);
                ps.setInt(3, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }

        if (account.getRole() == 0) {
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                    + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy "
                    + " WHERE Transaction.status = ? and User.id = ? "
                    + "ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, status);
                ps.setInt(2, account.getUserId());
                ps.setInt(3, recordsPerPage);
                ps.setInt(4, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }
        return list;
    }

    public ArrayList<Transaction> getUserTransactionHistoryByAmount(String priceFrom, String priceTo, String page, int recordsPerPage, User account) {
        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;

        if (account.getRole() == 1) {
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM \n"
                    + "                  `Transaction` JOIN `User` ON User.id = Transaction.createdBy \n"
                    + "                  WHERE Transaction.amount BETWEEN ? AND ? \n"
                    + "                  ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "                  LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, priceFrom);
                ps.setString(2, priceTo);
                ps.setInt(3, recordsPerPage);
                ps.setInt(4, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }

        if (account.getRole() == 0) {
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM \n"
                    + "                  `Transaction` JOIN `User` ON User.id = Transaction.createdBy \n"
                    + "                  WHERE Transaction.amount BETWEEN ? AND ? AND User.id = ? \n"
                    + "                  ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "                  LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setString(1, priceFrom);
                ps.setString(2, priceTo);
                ps.setInt(3, account.getUserId());
                ps
                        .setInt(4, recordsPerPage);
                ps.setInt(5, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }
        return list;
    }

    public ArrayList<Transaction> getUserTransactionHistoryByTypeOfTransaction(String transactionType, String page, int recordsPerPage, User account) {
        int offset = (Integer.parseInt(page) - 1) * recordsPerPage;

        if (account.getRole() == 1) {
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                    + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy"
                    + " WHERE Transaction.type = ? "
                    + "ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setBoolean(1, Boolean.parseBoolean(transactionType));
                ps.setInt(2, recordsPerPage);
                ps.setInt(3, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }

        if (account.getRole() == 0) {
            xSql = "SELECT Transaction.id, Transaction.amount, Transaction.type, Transaction.detail, Transaction.status, Transaction.createdAt, Transaction.updatedAt, Transaction.createdBy FROM "
                    + "`Transaction` JOIN `User` ON User.id = Transaction.createdBy"
                    + " WHERE Transaction.type = ? and User.id = ? "
                    + "ORDER BY Transaction.createdAt DESC " // Sắp xếp theo thời gian gần nhất
                    + "LIMIT ? OFFSET ?";
            try {
                ps = con.prepareStatement(xSql);
                ps.setBoolean(1, Boolean.parseBoolean(transactionType));
                ps.setInt(2, account.getUserId());
                ps.setInt(3, recordsPerPage);
                ps.setInt(4, offset);
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8)));
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
        }
        return list;
    }

    public void insertNewTransaction(Transaction x) {
        xSql = "insert into `transaction`(`amount`, `type`, `detail`, `status`, `createdBy`)\n"
                + "values (?,?,?,?,?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDouble(1, x.getAmount());
            ps.setBoolean(2, x.isType());
            ps.setString(3, x.getDetail());
            ps.setBoolean(4, x.isStatus());
            ps.setInt(5, x.getCreatedBy());
            ps.executeUpdate();
            ps.close();
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

    public void insertNewTransactionAfterOrderSuccess(User sellerInfor, String oId, double sellerReceivedTrueMoney) {
        // Câu truy vấn SELECT để kiểm tra xem đã tồn tại bản ghi và cột "detail" có thông tin liên quan tới oId tồn tại hay không
        String selectSql = "SELECT * FROM `transaction` WHERE `detail` LIKE ?";

        try {
            // Thực hiện truy vấn SELECT
            ps = con.prepareStatement(selectSql);
            ps.setString(1, "%giao dịch trung gian số " + oId + "%");
            rs = ps.executeQuery();

            // Nếu không tìm thấy bản ghi đã tồn tại liên quan tới oId
            if (!rs.next()) {
                // Câu truy vấn INSERT để chèn bản ghi mới
                String insertSql = "INSERT INTO `transaction` (`amount`, `type`, `detail`, `status`, `createdBy`) VALUES (?, ?, ?, ?, ?)";

                // Thực hiện câu truy vấn INSERT
                ps = con.prepareStatement(insertSql);
                ps.setDouble(1, sellerReceivedTrueMoney); //số tiền người bán nhận được
                ps.setBoolean(2, true); //giao dịch cộng tiền
                ps.setString(3, "Nhận tiền giao dịch trung gian số " + oId);
                ps.setInt(4, 1); //1 - đã xử lý
                ps.setInt(5, sellerInfor.getUserId());
                ps.executeUpdate();
                ps.close();
            }

            // Đóng kết nối và các tài nguyên
            rs.close();
            ps.close();
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

    public void insertTransactionAfterCancelBySeller(double productPrice, String oId, User buyer) {
        String insertSql = "INSERT INTO `transaction` (`amount`, `type`, `detail`, `status`, `createdBy`) VALUES (?, ?, ?, ?, ?)";

        try {
            // Thực hiện câu truy vấn INSERT
            ps = con.prepareStatement(insertSql);
            ps.setDouble(1, productPrice); //số tiền người bán nhận được
            ps.setBoolean(2, true); //giao dịch cộng tiền
            ps.setString(3, "Hoàn tiền do người bán huỷ đơn hàng " + oId);
            ps.setInt(4, 1); //1 - đã xử lý
            ps.setInt(5, buyer.getUserId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("lỗi khi chèn bản ghi mới vào transaction: " + e);
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

    public static void main(String[] args) {
        TransactionDAO dao = new TransactionDAO();
//        ArrayList<Transaction> paymentList = dao.getAllTransaction();
//        for (Transaction payment : paymentList) {
//            System.out.println(payment);
//        }
        UserDAO userdao = new UserDAO();
        User account = userdao.login("user1", "0f73528d68d75f9ba15719a25e19b3881718e6a1");
        System.out.println(account);
        ArrayList<Transaction> transactionHistory = dao.getUserTransactionHistoryByTypeOfTransaction("true", "1", 4, account);
        System.out.println(transactionHistory.isEmpty());
        for (Transaction payment : transactionHistory) {
            System.out.println(payment);
        }

        dao.insertNewTransactionAfterOrderSuccess(account, "15", 100);
    }
}
