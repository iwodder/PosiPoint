package com.wodder.model.order;

import com.wodder.gui.observers.*;
import com.wodder.model.menu.*;
import com.wodder.model.users.*;

import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.util.function.*;

public class OrderModel implements OrderSubject, UserObserver {

    private final List<OrderObserver> orderObservers;

    private Order order;
    private User currentUser;
    private final OrderStore orderStore;

    public OrderModel(OrderStore orderStore) {
        orderObservers = new ArrayList<>();
        this.orderStore = orderStore;
    }

    public void createNewOrder(User owner) {
        if (order != null) {
            saveOrder();
        }
        this.order = new Order(owner);
        notifyObservers(OrderObserver::orderCreated);
    }

    public void deleteOrder(User user, int orderId) {
        orderStore.deleteOrder(user, orderId);
        this.order = null;
        notifyObservers(OrderObserver::orderDeleted);
    }

    public boolean noCurrentOrder() {
        return order == null;
    }

    public int getCurrentOrderId() {
        if (order != null) {
            return order.orderNum();
        } else {
            throw new IllegalStateException("Order must be created before getting id");
        }
    }

    public String getOrderTotal() {
        if (order != null) {
            return order.orderTotal();
        } else {
            throw new IllegalStateException("Order must be created before getting price");
        }
    }

    public Vector<String> getOrderList() {
        if (order != null) {
            return order.getFormattedOrder();
        } else {
            throw new IllegalStateException("Order must be created before getting list");
        }
    }

    public void addItemToOrder(MenuItem mi) {
        if (order == null) createNewOrder(currentUser);
        order.addItemToOrder(mi);
        notifyObservers(OrderObserver::itemAdded);
    }

    public boolean saveOrder() {
        if (order != null) {
            orderStore.saveOrder(order);
            order = null;
            return true;
        } else {
            return false;
        }
    }

    public void lookupOrder(User user, int id) {
        Optional<Order> order = orderStore.lookupOrder(user, id);
        order.ifPresent((o) -> {
            this.order = o;
            notifyObservers(OrderObserver::activeOrderChanged);
        });
    }

    public TableModel lookupOrders(User user) {
        List<Object[]> tableData = createData(orderStore.getOrders(user));
        return new OrderTableModel(tableData);
    }

    private List<Object[]> createData(Iterable<Order> orders) {
        List<Object[]> data = new ArrayList<>();
        for (Order o : orders) {
            data.add(new Object[]{o.orderNum(), o.owner().userName(), o.orderTotal(), o.getTotalItems()});
        }
        return data;
    }

    private void notifyObservers(Consumer<OrderObserver> action) {
        orderObservers.forEach(action);
    }

    @Override
    public void registerOrderObs(OrderObserver orderObserver) {
        if (!orderObservers.contains(orderObserver)) {
            orderObservers.add(orderObserver);
        }
    }

    @Override
    public void removeOrderObserver(OrderObserver orderObserver) {
        int idx = orderObservers.indexOf(orderObserver);
        if (idx > -1) {
            orderObservers.remove(idx);
        }
    }

    @Override
    public void userLoggedOut() {
        currentUser = null;
    }

    @Override
    public void userLoggedIn(User user) {
        currentUser = user;
    }

    private class OrderTableModel implements TableModel {
        private final String[] columns = {"Order Id", "Owner", "Total", "Item Count"};
        private final List<Object[]> data;

        private OrderTableModel(List<Object[]> data) {
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columns[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        }

        @Override
        public void addTableModelListener(TableModelListener l) {

        }

        @Override
        public void removeTableModelListener(TableModelListener l) {

        }
    }
}
