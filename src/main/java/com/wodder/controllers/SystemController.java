package com.wodder.controllers;

import com.wodder.authentication.*;
import com.wodder.gui.*;
import com.wodder.gui.menu.*;
import com.wodder.gui.observers.*;
import com.wodder.model.system.*;
import com.wodder.model.users.*;

import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

public class SystemController implements UserObserver, ActionListener {
    private final SystemModel systemModel;
    private PosiPoint posiPoint;
    private ControlDisplay controls;
    private final Timer logoutTimer;
    private final static int LOGOUT_DELAY_MS = 120_000;

    public SystemController(SystemModel systemModel) {
        this.systemModel = systemModel;
        logoutTimer = new Timer(LOGOUT_DELAY_MS, this);
    }

    public void setPosiPoint(PosiPoint p) {
        this.posiPoint = p;
    }

    public void setMenuDisplay(ControlDisplay controlDisplay) {
        this.controls = controlDisplay;
    }

    public SystemModel getSystemModel() {
        return this.systemModel;
    }

    public void userActive() {
        if (systemModel.isLoggedIn()) {
            logoutTimer.restart();
        } else {
            posiPoint.displayLogin();
        }
    }

    public boolean userIsManager() {
        return systemModel.userIsManager();
    }

    public void login(Credentials credentials) {
        systemModel.login(credentials);
    }

    public void logout() {
        systemModel.logoutUser();
    }

    public Iterator<String> getUserRoles() {
        return systemModel.getRoleNames();
    }

    public void addUser(String fName, String lName, String password, String role) {
        systemModel.addUser(fName, lName, password, role);
        controls.closeAddUserDialog();
    }

    @Override
    public void userLoggedOut() {
        controls.removeManagerTab();
        controls.inactivateMenuBtns();
        posiPoint.displayLoggedOutMsg();
        posiPoint.displayLogin();
        logoutTimer.stop();
    }

    @Override
    public void userLoggedIn(User user) {
        posiPoint.hideLogin();
        if (systemModel.userIsManager()) {
            controls.addManagerTab();
        }
        controls.activateMenuBtns();
        logoutTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            systemModel.logoutUser();
        }
    }
}
