package com.wodder.controllers;

import com.wodder.model.menu.*;
import com.wodder.model.order.*;
import com.wodder.model.system.*;

public class Controllers {

    private static final Controllers cf = new Controllers();
    private final SystemController systemController;
    private final MenuPanelController menuPanelController;
    private final OrderController orderController;

    private Controllers() {
        SystemModel systemModel = new SystemModel();
        MenuModel menuModel = new MenuModel();
        OrderModel orderModel = new OrderModel(new InMemory());
        systemModel.registerUserObserver(orderModel);
        systemController = new SystemController(systemModel);
        menuPanelController = new MenuPanelController(menuModel, orderModel);
        orderController = new OrderController(orderModel);
    }

    public static Controllers getInstance() {
        return cf;
    }

    public MenuPanelController getMenuPanelController() {
        return this.menuPanelController;
    }

    public SystemController getSystemController() {
        return this.systemController;
    }

    public OrderController getOrderController() {
        return this.orderController;
    }
}
