package com.wodder.gui.dialogs;

import com.wodder.controllers.*;
import com.wodder.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MenuItemDialog extends JDialog implements KeyListener {

    private final JPanel owner;
    private MenuPanelController controller;
    private JTextField menuItemName;
    private JTextField menuItemPrice;
    private JButton okBtn;

    public MenuItemDialog(MenuPanelController controller, JPanel owner) {
        this.controller = controller;
        this.owner = owner;
        createDialog();
        this.pack();
    }

    public void createDialog() {
        this.setTitle("Add New Menu Item");
        this.setLayout(new GridBagLayout());
        this.add(menuItemName(), GuiUtils.createConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST));
        this.add(menuItemPrice(), GuiUtils.createConstraints(0, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST));
        this.add(btnPanel(), GuiUtils.createConstraints(0, 2, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER));
        this.addKeyListener(this);
    }

    private JPanel menuItemName() {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(new JLabel("Menu Item Name"));
        menuItemName = new JTextField();
        menuItemName.setFocusable(true);
        menuItemName.setColumns(30);
        menuItemName.addKeyListener(this);
        jPanel.add(menuItemName);
        return jPanel;
    }

    private JPanel menuItemPrice() {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(new JLabel("Menu Item Price"));
        menuItemPrice = new JTextField();
        menuItemPrice.setFocusable(true);
        menuItemPrice.setColumns(30);
        menuItemPrice.addKeyListener(this);
        jPanel.add(menuItemPrice);
        return jPanel;
    }

    private JPanel btnPanel() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okBtn = new JButton("OK");
        okBtn.addActionListener(okAction);
        okBtn.addKeyListener(this);
        jPanel.add(okBtn);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(closeAction);
        jPanel.add(cancel);
        return jPanel;
    }

    public void display() {
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
    }

    private final ActionListener closeAction = (e) -> {
        menuItemName.setText(null);
        menuItemPrice.setText(null);
        this.setVisible(false);
    };

    private final ActionListener okAction = (e) -> {
        notifyParent();
        if (validInput()) {
            controller.addMenuItem(menuItemName.getText(), menuItemPrice.getText());
        }
    };

    private void notifyParent() {
        MouseEvent e = new MouseEvent(this, 0, 0, 0, 0,0,1,false, 0);
        Arrays.stream(owner.getMouseListeners()).forEach((m) -> m.mouseClicked(e));
    }

    private boolean validInput() {
        if (menuItemName.getText().length() == 0) {
            menuItemName.setToolTipText("Menu Item Name");
            menuItemName.grabFocus();
            GuiUtils.displayToolTip(menuItemName);
            return false;
        }
        String price = menuItemPrice.getText();
        if (price.length() == 0 || price.indexOf('.') == -1) {
            menuItemPrice.setToolTipText("Menu Item Price");
            menuItemPrice.grabFocus();
            GuiUtils.displayToolTip(menuItemPrice);
            return false;
        }
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            okBtn.doClick();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
