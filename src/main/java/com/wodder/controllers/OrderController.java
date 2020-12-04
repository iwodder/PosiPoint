package com.wodder.controllers;

import com.wodder.gui.dialogs.*;
import com.wodder.gui.observers.*;
import com.wodder.gui.order.*;
import com.wodder.model.menu.*;
import com.wodder.model.order.*;
import com.wodder.model.users.*;

import javax.swing.table.*;

public class OrderController implements OrderObserver {

    private final OrderModel orderModel;
    private OrderDisplay orderDisplay;

    public OrderController(OrderModel orderModel) {
        this.orderModel = orderModel;
        this.orderModel.registerOrderObs(this);
    }

    public OrderModel getOrderModel() {
        return this.orderModel;
    }

    public void setOrderDisplay(OrderDisplay display) {
        this.orderDisplay = display;
    }

    public void addItemToOrder(MenuItem menuItem) {
        if (orderModel.noCurrentOrder()) {
            orderDisplay.disableCreateOrderBtn();
            orderDisplay.enableSaveOrderBtn();
            orderDisplay.enableDeleteOrderBtn();
            new OrderCreatedDialog().display();
        }
        orderModel.addItemToOrder(menuItem);
    }

    public void createOrder(User user) {
        orderModel.createNewOrder(user);
        orderDisplay.enableDeleteOrderBtn();
        orderDisplay.enableSaveOrderBtn();
        orderDisplay.disableCreateOrderBtn();
    }

    public void deleteOrder(User user, int orderId) {
        orderModel.deleteOrder(user, orderId);
        orderDisplay.resetDisplay();
        orderDisplay.enableCreateOrderBtn();
        orderDisplay.disableSaveOrderBtn();
        orderDisplay.disableDeleteOrderBtn();
        new OrderDeletedDialog().display();
    }

    public void saveOrder() {
        orderDisplay.disableSaveOrderBtn();
        if (orderModel.saveOrder()) {
            orderDisplay.resetDisplay();
            new OrderSavedDialog().display();
            orderDisplay.enableCreateOrderBtn();
            orderDisplay.disableDeleteOrderBtn();
        }
    }

    public void getOrders(User user) {
        TableModel orders = orderModel.lookupOrders(user);
        if (orders.getRowCount() > 0) {
            orderDisplay.showOrders(orderModel.lookupOrders(user));
        } else {
            new NoOrdersDialog().display();
        }
    }

    public void lookupOrder(User user, int id) {
        orderModel.lookupOrder(user, id);
        orderDisplay.enableSaveOrderBtn();
        orderDisplay.enableDeleteOrderBtn();
    }

    @Override
    public void orderCreated() {
    }

    @Override
    public void itemAdded() {
    }

    @Override
    public void orderDeleted() {
    }

    @Override
    public void activeOrderChanged() {
    }
}
