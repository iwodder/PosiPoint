package com.wodder.gui.dialogs;

import javax.swing.*;

public class LoggedOutDialog extends JOptionPane {

    public LoggedOutDialog() {

    }

    public void display() {
        JOptionPane.showMessageDialog(null,
                "You have been logged out.",
                "Logged out",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
