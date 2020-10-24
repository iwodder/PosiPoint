package com.wodder.gui.dialogs;

import javax.swing.*;

public class NoOrdersDialog {

    public NoOrdersDialog() {

    }

    public void display() {
        JOptionPane.showMessageDialog(
                null, "No orders found.", "Orders", JOptionPane.INFORMATION_MESSAGE);
    }
}
