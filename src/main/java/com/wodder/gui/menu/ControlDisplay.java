package com.wodder.gui.menu;

import com.wodder.controllers.*;
import com.wodder.gui.observers.*;
import com.wodder.model.menu.MenuItem;
import com.wodder.model.menu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlDisplay extends JTabbedPane implements MenuObserver {
    private final MenuPanelController menuPanelController;
    private final SystemController systemController;
    private ManagerPanel managerPanel;
    private final MenuModel menuModel;
    private MenuPanel menuPanel;
    private final OrderController orderController;
    private int menuCnt;

    public ControlDisplay(Controllers cf) {
        super(JTabbedPane.TOP);
        this.menuPanelController = cf.getMenuPanelController();
        this.menuPanelController.setMenuDisplay(this);
        this.systemController = cf.getSystemController();
        this.systemController.setMenuDisplay(this);
        this.menuModel = menuPanelController.getMenuModel();
        this.orderController = cf.getOrderController();
        menuModel.registerMenuObserver(this);
    }

    public void createDisplay() {
        this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        managerPanel = new ManagerPanel(menuPanelController, systemController);
        addMenuPanel();
    }

    public void addMenuPanel() {
        String title = String.format("Menu Page #%d", (menuCnt + 1));
        menuPanel = new MenuPanel(this.orderController);
        menuPanelController.getInitialItems().forEachRemaining(this::addMenuItem);
        this.insertTab(title, null, menuPanel, null, menuCnt++);
    }

    public void addManagerTab() {
        int cnt = this.getTabCount();
        this.insertTab("Manager Functions", null, managerPanel, null, cnt);
    }

    public void removeManagerTab() {
        int i = this.indexOfComponent(managerPanel);
        if (i != -1) {
            this.removeTabAt(i);
        }
    }

    public void inactivateMenuBtns() {
        menuPanel.inactivateButtons();
    }

    public void activateMenuBtns() {
        menuPanel.activateButtons();
    }

    public void closeAddItemDialog() {
        managerPanel.hideAddItemDialog();
    }

    public void closeAddUserDialog() {
        managerPanel.hideCreateUserDialog();
    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        super.addMouseListener(mouseListener);
        if (managerPanel != null) {
            managerPanel.addMouseListener(mouseListener);
        }
    }

    @Override
    public void menuItemAdded(MenuItem mi) {
        addMenuItem(mi);
    }

    private void addMenuItem(MenuItem mi) {
        if (menuPanel.full()) {
            addMenuPanel();
        }
        menuPanel.addItem(mi);
    }
}
