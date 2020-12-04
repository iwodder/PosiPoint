package com.wodder.gui.menu;

import com.wodder.controllers.*;
import com.wodder.model.menu.MenuItem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private MenuButton[] items;
    private OrderController orderController;
    private SystemController systemController;
    private int btnNum;
    private JPopupMenu menu;
    private JMenuItem outOfStockItem;
    private JMenuItem inStockItem;
    private final MouseListener popupListener = new PopupListener();

    public MenuPanel(OrderController orderController, SystemController systemController) {
        super(new GridLayout(5,5, 5, 5));
        this.orderController = orderController;
        this.systemController = systemController;
        createDisplay();
    }

    private void createDisplay() {
        this.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
        items = new MenuButton[25];
        btnNum = 0;
        for (int i = 0; i < 25; i++) {
            MenuButton btn = new MenuButton();
            btn.setLayout(new BorderLayout());
            btn.setVisible(false);
            btn.setEnabled(false);
            this.add(btn);
            items[i] = btn;
        }
        createPopupMenu();
    }

    private void createPopupMenu() {
        menu = new JPopupMenu();
        outOfStockItem = new JMenuItem("86 Item");
        inStockItem = new JMenuItem("Un-86 Item");
        outOfStockItem.addActionListener(new OutOfStockAction());
        inStockItem.addActionListener(new BackInStockAction());
        menu.add(inStockItem);
        menu.add(outOfStockItem);
    }

    public void addItem(MenuItem item) {
        if (btnNum < 25) {
            MenuButton newBtn = items[btnNum++];
            newBtn.addItem(item);
            newBtn.setVisible(true);
            newBtn.addActionListener(clickListener);
            newBtn.addMouseListener(popupListener);
            revalidate();
        }
    }

    public void inactivateButtons() {
        for (MenuButton mb : items) {
            mb.setEnabled(false);
        }
    }

    public void activateButtons() {
        for (MenuButton mb : items) {
            if (mb.inStock()) {
                mb.setEnabled(true);
            }
        }
    }

    public boolean full() {
        return btnNum == 24;
    }

    public boolean notFull() {
        return !full();
    }

    private final ActionListener clickListener = e -> {
        if (e.getSource() instanceof MenuButton mb) {
            this.orderController.addItemToOrder(mb.getMenuItem());
        }
    };

    private class PopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger() && systemController.userIsManager()) {
                if (e.getComponent() instanceof MenuButton mb) {
                    setContext(mb);
                    if (mb.getMenuItem().isOutOfStock()) {
                       inStockItem.setEnabled(true);
                       outOfStockItem.setEnabled(false);
                    } else {
                        inStockItem.setEnabled(false);
                        outOfStockItem.setEnabled(true);
                    }
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }

        private void setContext(MenuButton mb) {
            ((StockAction) outOfStockItem.getActionListeners()[0]).setContext(mb);
            ((StockAction) inStockItem.getActionListeners()[0]).setContext(mb);
        }
    }

    private interface StockAction {
         void setContext(MenuButton context);
    }

    private static class OutOfStockAction implements ActionListener, StockAction {

        private MenuButton context;

        private OutOfStockAction() {};

        @Override
        public void setContext(MenuButton context) {
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (context!=null) {
                context.setOutOfStock();
            }
        }

    }

    private static class BackInStockAction implements ActionListener, StockAction {

        private MenuButton context;

        private BackInStockAction() {}

        @Override
        public void setContext(MenuButton context) {
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (context != null) {
                context.setInStock();
            }
        }

    }
}
