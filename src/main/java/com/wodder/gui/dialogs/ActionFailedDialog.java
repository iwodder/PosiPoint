package com.wodder.gui.dialogs;

import javax.swing.*;
import java.awt.*;

public class ActionFailedDialog extends JOptionPane {

    public ActionFailedDialog() {

    }

    public void display(Component owner) {
        JOptionPane.showMessageDialog(owner, "Action failed, please retry later.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
