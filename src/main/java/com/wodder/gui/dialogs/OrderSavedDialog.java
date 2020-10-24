package com.wodder.gui.dialogs;

import javax.swing.*;

public class OrderSavedDialog {

    public OrderSavedDialog() {

    }

    public void display() {
        JOptionPane.showMessageDialog(
                null, "Order successfully saved.", "Order saved", JOptionPane.INFORMATION_MESSAGE);
    }
}
