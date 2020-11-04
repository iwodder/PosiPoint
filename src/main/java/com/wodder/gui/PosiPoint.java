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
    private OrderDisplay orderPanel;
    private JLabel userName;
    private JLabel role;

    public PosiPoint(Controllers cf) {
        this.controllers = cf;
        this.controller = cf.getSystemController();
        this.model = controller.getSystemModel();
        this.controller.setPosiPoint(this);
        model.registerUserObserver(this);
        model.registerUserObserver(controller);
    }

    public void createAndShow() {
        setFrameProperties();
        addComponents();
        loginDialog = new LoginDialog(this, controller);
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

    private void addComponents() {
        add(createUserPanel(), BorderLayout.NORTH);
        add(createOrderPanel(), BorderLayout.EAST);
        add(createControlPanel(), BorderLayout.CENTER);
    }

    private JPanel createOrderPanel() {
        orderPanel = new OrderDisplay(controllers);
        orderPanel.createDisplay();
        orderPanel.addMouseListener(this);
        return orderPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(5, 5));
        controlPanel.add(createControls(), BorderLayout.CENTER);
        controlPanel.add(orderPanel.orderControls(), BorderLayout.SOUTH);
        return controlPanel;
    }

    private JTabbedPane createControls() {
        ControlDisplay result = new ControlDisplay(controllers);
        result.createDisplay();
        result.addMouseListener(this);
        return result;
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
