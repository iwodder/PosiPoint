package com.wodder.gui.dialogs;

import javax.swing.*;

public class OrderDeletedDialog extends JDialog {

    public OrderDeletedDialog() {
    }

    public void display() {
        JOptionPane.showMessageDialog(
                null, "Order successfully deleted.", "Order deleted", JOptionPane.INFORMATION_MESSAGE);
    }
}
