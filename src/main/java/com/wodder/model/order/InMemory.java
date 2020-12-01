package com.wodder.model.order;

import com.wodder.model.users.*;

import java.util.*;

public class InMemory implements OrderStore {
    private final Map<User, Map<Integer, Order>> store;

    public InMemory() {
        store = new HashMap<>();
    }

    @Override
    public void saveOrder(Order order) {
        if (store.containsKey(order.owner())) {
            Map<Integer, Order> orders = store.get(order.owner());
            orders.putIfAbsent(order.orderNum(), order);
        } else {
            Map<Integer, Order> orders = new HashMap<>();
            orders.put(order.orderNum(), order);
            store.putIfAbsent(order.owner(), orders);
        }
    }

    @Override
    public Optional<Order> lookupOrder(User user, int id) {
        if (store.containsKey(user)) {
            Map<Integer, Order> orders = store.get(user);
            if (orders.containsKey(id)) {
                return Optional.of(orders.get(id));
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Order> getOrders(User user) {
        List<Order> orders = new ArrayList<>();
        if (store.containsKey(user)) {
            orders.addAll(store.get(user).values());
        }
        return orders;
    }

    @Override
    public void deleteOrder(User user, int id) {
        if (store.containsKey(user)) {
            Map<Integer, Order> orders = store.get(user);
            orders.remove(id);
        }
    }
}
