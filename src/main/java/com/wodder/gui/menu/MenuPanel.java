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
    private int btnNum;

    public MenuPanel(OrderController orderController) {
        super(new GridLayout(5,5, 5, 5));
        this.orderController = orderController;
        createDisplay();
    }

    private void createDisplay() {
        this.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
        items = new MenuButton[25];
        btnNum = 0;
        for (int i = 0; i < 25; i++) {
            MenuButton btn = new MenuButton();
            btn.setVisible(false);
            btn.setEnabled(false);
            this.add(btn);
            items[i] = btn;
        }
    }

    public void addItem(MenuItem item) {
        if (btnNum < 25) {
            MenuButton newBtn = items[btnNum++];
            newBtn.addItem(item);
            newBtn.setVisible(true);
            newBtn.addActionListener(clickListener);
        }
    }

    private final ActionListener clickListener = e -> {
      if (e.getSource() instanceof MenuButton) {
          MenuButton mb = (MenuButton) e.getSource();
          this.orderController.addItemToOrder(mb.getMenuItem());
      }
    };

    public void inactivateButtons() {
        for (MenuButton mb : items) {
            mb.setEnabled(false);
        }
    }

    public void activateButtons() {
        for (MenuButton mb : items) {
            mb.setEnabled(true);
        }
    }

    public boolean full() {
        return btnNum == 24;
    }

    public boolean notFull() {
        return !full();
    }
}
