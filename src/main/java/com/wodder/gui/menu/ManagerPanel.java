package com.wodder.gui.menu;

import com.wodder.controllers.*;
import com.wodder.gui.dialogs.*;

import javax.swing.*;
import java.awt.*;

public class ManagerPanel extends JPanel {

    private final MenuPanelController menuPanelController;
    private final SystemController systemController;
    private final MenuItemDialog menuItemDialog;
    private final CreateUserDialog createUserDialog;

    public ManagerPanel(MenuPanelController menuPanelController, SystemController systemController) {
        super(new GridLayout(5,1,10,10));
        this.menuPanelController = menuPanelController;
        this.systemController = systemController;
        menuItemDialog = new MenuItemDialog(this.menuPanelController, this);
        createUserDialog = new CreateUserDialog(this.systemController, this);
        createDisplay();
    }

    private void createDisplay() {
        this.add(createAddUserButton());
        this.add(createAddMenuButton());
    }

    private JButton createAddUserButton() {
        JButton addUserBtn = new JButton("Add User");
        addUserBtn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        addUserBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        addUserBtn.addActionListener((e) -> showCreateUserDialog());
        return addUserBtn;
    }

    private JButton createAddMenuButton() {
        JButton addMenuBtn = new JButton("Add Menu Item");
        addMenuBtn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        addMenuBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        addMenuBtn.addActionListener((e) -> showAddItemDialog());
        return addMenuBtn;
    }

    public void showAddItemDialog() {
        menuItemDialog.display();
    }

    public void hideAddItemDialog() {
        menuItemDialog.setVisible(false);
    }

    public void showCreateUserDialog() {
        createUserDialog.display();
    }

    public void hideCreateUserDialog() {
        createUserDialog.setVisible(false);
    }
}
