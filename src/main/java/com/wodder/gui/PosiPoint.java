package com.wodder.gui;

import com.wodder.controllers.*;
import com.wodder.gui.dialogs.*;
import com.wodder.gui.menu.*;
import com.wodder.gui.observers.*;
import com.wodder.gui.order.*;
import com.wodder.model.system.*;
import com.wodder.model.users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PosiPoint extends JFrame implements MouseListener, UserObserver {
    private final SystemController controller;
    private final Controllers controllers;
    private final SystemModel model;
    private LoginDialog loginDialog;
    private MenuDisplay menuDisplay;
    private OrderDisplay orderPanel;
    private JPanel menuPanel;
    private JLabel userName;
    private JLabel role;

    public PosiPoint(Controllers cf) {
        this.controllers = cf;
        this.controller = cf.getSystemController();
        this.model = controller.getSystemModel();
        this.controller.setPosiPoint(this);
        model.registerUserObserver(this);
        model.registerUserObserver(controller);
        menuDisplay = new MenuDisplay(controllers);
    }

    public void createAndShow() {
        setFrameProperties();
        setupMenuDisplay();
        addComponents();
        loginDialog = new LoginDialog(this, this.controller);
        pack();
        setVisible(true);
    }

    private void setFrameProperties() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("PosiPoint Sales System");
        setLocation(new Point(0, 0));
        addMouseListener(this);
        setLayout(new BorderLayout());
    }

    private void setupMenuDisplay() {
        model.registerUserObserver(menuDisplay);
        menuDisplay.createDisplay();
        menuDisplay.addMouseListener(this);
    }

    private void addComponents() {
        add(createOrderPanel(), BorderLayout.EAST);
        add(createMenuPanel(), BorderLayout.CENTER);
    }

    private JPanel createOrderPanel() {
        orderPanel = new OrderDisplay(controllers);
        orderPanel.createDisplay();
        orderPanel.addMouseListener(this);
        return orderPanel;
    }

    private JPanel createMenuPanel() {
        menuPanel = new JPanel(new BorderLayout(5, 5));
        menuPanel.add(createUserPanel(), BorderLayout.NORTH);
        menuPanel.add(menuDisplay, BorderLayout.CENTER);
        menuPanel.add(orderPanel.orderControls(), BorderLayout.SOUTH);
        return menuPanel;
    }

    private JPanel createUserPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(createUserInfoPanel(), BorderLayout.WEST);
        topPanel.add(logoutBtnPanel(), BorderLayout.EAST);
        return topPanel;
    }

    private JPanel createUserInfoPanel() {
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel userLbl = new JLabel("User:");
        JLabel roleLbl = new JLabel("Role:");
        userName = new JLabel();
        role = new JLabel();
        userInfoPanel.add(userLbl);
        userInfoPanel.add(userName);
        userInfoPanel.add(roleLbl);
        userInfoPanel.add(role);
        return userInfoPanel;
    }

    private JPanel logoutBtnPanel() {
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,0));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener((e) -> controller.logout());
        logoutPanel.add(logoutBtn);
        return logoutPanel;
    }

    public void displayLogin() {
        loginDialog.display();
    }

    public void hideLogin() {
        loginDialog.setVisible(false);
    }

    public void addManagerTab() {
        menuDisplay.addManagerTab();
    }

    public void removeManagerTab() {
        menuDisplay.removeManagerTab();
    }

    public void displayLoggedOutMsg() {
        new LoggedOutDialog().display();
    }

    @Override
    public void userLoggedOut() {
        userName.setText(null);
        role.setText(null);
    }

    @Override
    public void userLoggedIn(User user) {
        userName.setText(model.userName());
        role.setText(model.roleName());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.userActive();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
