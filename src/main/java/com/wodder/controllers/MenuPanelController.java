package com.wodder.controllers;

import com.wodder.gui.dialogs.*;
import com.wodder.gui.menu.*;
import com.wodder.gui.observers.*;
import com.wodder.model.menu.*;
import com.wodder.model.order.*;

import java.util.*;


public class MenuPanelController implements MenuObserver {

    private ControlDisplay controlDisplay;
    private final MenuModel menuModel;
    private final OrderModel orderModel;

    public MenuPanelController(MenuModel model, OrderModel orderModel) {
        this.menuModel = model;
        this.orderModel = orderModel;
    }

    public void setMenuDisplay(ControlDisplay md) {
        this.controlDisplay = md;
    }

    public MenuModel getMenuModel() {
        return this.menuModel;
    }

    public ControlDisplay getMenuDisplay() {
        return controlDisplay;
    }

    public void addMenuItem(String name, String price) {
        try {
            menuModel.addMenuItem(new MenuItem(name, price));
            controlDisplay.closeAddItemDialog();
        } catch (Exception e) {
            new ActionFailedDialog().display(controlDisplay);
        }
    }

    public Iterator<MenuItem> getInitialItems() {
        return menuModel.getMenuItems();
    }

    @Override
    public void menuItemAdded(MenuItem mi) {

    }
}
