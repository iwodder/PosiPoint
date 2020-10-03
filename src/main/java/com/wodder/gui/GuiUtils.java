package com.wodder.gui;

import java.awt.GridBagConstraints;

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
        return gbc;
    }
}
