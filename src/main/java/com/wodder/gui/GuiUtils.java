package com.wodder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class GuiUtils {

    private GuiUtils() {
        //Empty on purpose
    }

    public static GridBagConstraints createConstraints(int xPos, int yPos, int height, int width, int fill, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = yPos;
        gbc.gridheight = height;
        gbc.gridwidth = width;
        gbc.fill = fill;
        gbc.anchor = anchor;
        gbc.insets = new Insets(5, 5, 5,5);
        return gbc;
    }

    public static void displayToolTip(Component component) {
        ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(component, 0, 0, 0, 0, 0, 0, false));
    }

}
