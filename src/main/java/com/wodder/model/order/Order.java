package com.wodder.model.order;

import com.wodder.model.menu.*;
import com.wodder.model.users.*;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.tuple.*;

import java.math.*;
import java.util.*;

public class Order {

    private static int ORDER_NUM = 0;
    private final int orderNum;
    private final List<Pair<MenuItem, Integer>> itemQtyList;
    private final Vector<String> formattedOrder;
    private int numOfItems;
    private BigDecimal total;
    private final User owner;

    public Order(User owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Unable to create order without an owner");
        }
        this.orderNum = ++ORDER_NUM;
        itemQtyList = new ArrayList<>();
        formattedOrder = new Vector<>();
        total = new BigDecimal("0.00", MathContext.DECIMAL64);
        this.owner = owner.clone();
    }

    public int orderNum() {
        return this.orderNum;
    }

    public String orderTotal() {
        return total.toString();
    }

    public User owner() {
        return owner;
    }

    public void addItemToOrder(MenuItem menuItem) {
        updateTotal(menuItem);
        numOfItems++;
        for (int i = 0; i < itemQtyList.size(); i++) {
            Pair<MenuItem, Integer> p = itemQtyList.get(i);
            if (p.getKey().getName().equalsIgnoreCase(menuItem.getName())) {
                p.setValue(p.getRight() + 1);
                updateFormattedItem(i, p.getValue());
                return;
            }
        }
        itemQtyList.add(MutablePair.of(menuItem, 1));
        addNewFormattedItem(menuItem);
    }

    private void updateTotal(MenuItem menuItem) {
        total = total.add(new BigDecimal(menuItem.getPrice()));
    }

    private void addNewFormattedItem(MenuItem menuItem) {
        String formattedItem = StringUtils.center(String.format("x%d", 1), 15) +
                StringUtils.center(menuItem.getName(), 60);
        formattedOrder.add(formattedItem);
    }

    private void updateFormattedItem(int idx, int value) {
        String s = formattedOrder.get(idx);
        int qty = s.indexOf("x") + 1;
        formattedOrder.setElementAt(s.replace(s.charAt(qty), Character.forDigit(value, 10)), idx);
    }

    public int getTotalItems() {
        return numOfItems;
    }

    public Vector<String> getFormattedOrder() {
        return formattedOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;

        return this.orderNum == order1.orderNum;
    }

    @Override
    public int hashCode() {
        int result = orderNum();
        result = 31 * result + (itemQtyList != null ? itemQtyList.hashCode() : 0);
        return result;
    }
}
