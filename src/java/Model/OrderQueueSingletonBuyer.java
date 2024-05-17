/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class OrderQueueSingletonBuyer {
    private static OrderQueueSingletonBuyer instance;  //lưu trữ một phiên bản duy nhất của lớp OrderQueueSingletonBuyer.
    private final Queue<Order> orderQueue;//lưu trữ các đơn hàng của người mua.
    private final Map<String, Double> totalPriceMap;//lưu trữ tổng giá tiền của từng người mua.

    private OrderQueueSingletonBuyer() {
        orderQueue = new LinkedList<>();
        totalPriceMap = new HashMap<>();
    }

    public static synchronized OrderQueueSingletonBuyer getInstance() {
        //  trả về phiên bản duy nhất của lớp OrderQueueSingletonBuyer, đảm bảo rằng chỉ có một phiên bản được tạo ra và sử dụng trong toàn bộ ứng dụng.
        if (instance == null) {
            instance = new OrderQueueSingletonBuyer();
        }
        return instance;
    }

    public double getTotalPriceForBuyer(String buyerName) {
        //trả về tổng giá tiền của một người mua cụ thể.
        return totalPriceMap.getOrDefault(buyerName, 0.0);
    }

    public void addOrder(String buyerName, Order order, double price) {
        //thêm một đơn hàng mới vào hàng đợi và cập nhật tổng giá tiền của người mua tương ứng.
        orderQueue.add(order);
        double currentTotalPrice = totalPriceMap.getOrDefault(buyerName, 0.0);
        totalPriceMap.put(buyerName, currentTotalPrice + price);
    }

    public void resetTotalPriceForBuyer(String buyerName) {
        //đặt tổng giá tiền của một người mua về 0 sau khi giao dịch được thực hiện.
        totalPriceMap.put(buyerName, 0.0);
    }
}