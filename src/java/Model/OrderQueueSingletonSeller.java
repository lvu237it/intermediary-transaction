/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author User
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class OrderQueueSingletonSeller {

    private static OrderQueueSingletonSeller instance;
    private final Map<String, Queue<Order>> sellerOrderQueues;
    private final Map<String, Double> sellerTotalPrices;

    private OrderQueueSingletonSeller() {
        sellerOrderQueues = new HashMap<>();
        sellerTotalPrices = new HashMap<>();
    }

    public static synchronized OrderQueueSingletonSeller getInstance() {
        // Kiểm tra xem đã có một phiên bản của lớp hay chưa, nếu chưa thì tạo mới
        if (instance == null) {
            instance = new OrderQueueSingletonSeller();
        }
        return instance;
    }

    public double getSellerTotalPrice(String sellerUsername) {
        return sellerTotalPrices.getOrDefault(sellerUsername, 0.0);
    }

    public void setSellerTotalPrice(String sellerUsername, double additionalAmount) {
        double currentTotal = getSellerTotalPrice(sellerUsername);
        sellerTotalPrices.put(sellerUsername, currentTotal + additionalAmount);
    }

    public Queue<Order> getSellerOrderQueue(String sellerUsername) {
        return sellerOrderQueues.computeIfAbsent(sellerUsername, k -> new LinkedList<>());
    }

    // Thêm phương thức để reset giá trị về 0 sau khi công việc được thực hiện
    public void resetTotalPriceWhenUserConfirmedForSeller(String sellerUsername) {
        sellerTotalPrices.put(sellerUsername, 0.0);
    }
}