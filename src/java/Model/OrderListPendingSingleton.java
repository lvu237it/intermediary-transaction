/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListPendingSingleton {

    private static OrderListPendingSingleton instance;
    private final Map<String, List<Order>> mapUserOrderPending;//1 user tương ứng với 1 order list - người mua

    private OrderListPendingSingleton() {
        mapUserOrderPending = new HashMap<>();
    }

    public static synchronized OrderListPendingSingleton getInstance() {
        if (instance == null) {
            instance = new OrderListPendingSingleton();
        }
        return instance;
    }

    public synchronized void addOrderToPendingList(String username, Order order) {
        List<Order> userOrders = mapUserOrderPending.getOrDefault(username, new ArrayList<>());
        userOrders.add(order);
        mapUserOrderPending.put(username, userOrders);
    }

    public synchronized void removeOrderFromPendingList(String username, Order order) {
        List<Order> userOrders = mapUserOrderPending.getOrDefault(username, new ArrayList<>());
        userOrders.remove(order);
        mapUserOrderPending.put(username, userOrders);
    }

    public synchronized List<Order> getOrdersFromPendingList(String username) {
        return mapUserOrderPending.getOrDefault(username, new ArrayList<>());
    }
}
