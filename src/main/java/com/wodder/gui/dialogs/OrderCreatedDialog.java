package com.wodder.gui.dialogs;

import javax.swing.*;

public class OrderCreatedDialog {

    public OrderCreatedDialog() {

    }

    public void display() {
        JOptionPane.showMessageDialog(null, "No current order, new order created.",
                "New order", JOptionPane.INFORMATION_MESSAGE);
    }
}
