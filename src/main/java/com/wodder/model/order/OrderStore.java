package com.wodder.model.order;

import com.wodder.model.users.*;

import java.util.*;

public interface OrderStore {

    void saveOrder(Order order);

    Optional<Order> lookupOrder(User user, int id);

    Iterable<Order> getOrders(User user);

    void deleteOrder(User user, int id);
}
